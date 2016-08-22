package com.zc.contacts.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.entity.AppInfors;
import com.zc.contacts.entity.ProgressInfors;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.view.View;

/**
 * 应用程序信息管理器
 * @author Administrator
 *用于获取所有应用程序的图标，名称，包名，版本号
 */
public class AppInforsManager {

	private  PackageManager mpamanager;	//包管理器
	private ActivityManager mActManager;//活动管理器，获得正在运行的应用程序，杀死进程等
	private ArrayList<AppInfors> allpackageinfors = new ArrayList<AppInfors>();//所有软件
	private ArrayList<AppInfors> systempackageinfors = new ArrayList<AppInfors>();//系统软件
	private ArrayList<AppInfors> userpackageinfors = new ArrayList<AppInfors>();//用户软件
	
	//用于区分系统的和用户程序的键
	public static final int SYSTEM_PROCESS = 1;
	public static final int USER_PROCESS = 2;
	
	
	public AppInforsManager(Context context){
		if (mpamanager == null) {
			mpamanager = context.getPackageManager();//获取一个包管理器
		}
	}
	
	public ArrayList<AppInfors> getAllpackageinfors() {
		return allpackageinfors;
	}

	public void setAllpackageinfors(ArrayList<AppInfors> allpackageinfors) {
		this.allpackageinfors = allpackageinfors;
	}
	
	public ArrayList<AppInfors> getSystempackageinfors() {
		return systempackageinfors;
	}

	public void setSystempackageinfors(ArrayList<AppInfors> systempackageinfors) {
		this.systempackageinfors = systempackageinfors;
	}

	public ArrayList<AppInfors> getUserpackageinfors() {
		return userpackageinfors;
	}

	public void setUserpackageinfors(ArrayList<AppInfors> userpackageinfors) {
		this.userpackageinfors = userpackageinfors;
	}

	

	/**
	 * 添加应用程序信息(所有软件——系统软件——用户软件)
	 */
	public void addAllpackageinfors(){
		//获得所有已安装的应用程序，包括系统的和用户的       | ——有一个true则为true，
		List<PackageInfo> packageInfors = mpamanager.getInstalledPackages
				(PackageManager.GET_ACTIVITIES | PackageManager.GET_UNINSTALLED_PACKAGES);
		/*PackageInfo ————表示一个应用程序的信息，对应项目中的AndroidManifest.xml文件
		 * PackageInfo对象存放的就是<menifest>里面的内容（包名/版本号/版本名称）
		 * ApplicationInfo对象中存放的就是<application>里面的内容（应用程序名称/应用程序图标）
		 * 
		 */
		for (int x = 0; x < packageInfors.size(); x++) {
			//创建一个AppInfors对象，用来存储每个应用程序的信息
			AppInfors infors = new AppInfors();
			
			PackageInfo pinfor = packageInfors.get(x);
			LogUtil.printlog("PackageInfo", pinfor.packageName+"——"+pinfor.versionName+"——"+pinfor.versionCode);
			
			//获取ApplicationInfo。      apinfor.loadLabel——应用程序的标题，apinfor.loadIcon——应用程序的图标
			ApplicationInfo apinfor = pinfor.applicationInfo;
			LogUtil.printlog("ApplicationInfo", apinfor.loadLabel(mpamanager)+"——"+apinfor.loadIcon(mpamanager));
			
			//获取应用程序信息——应用程序的图标，名称，包名，版本号
			infors.name = apinfor.loadLabel(mpamanager).toString();//应用程序的名称
			infors.icon = apinfor.loadIcon(mpamanager);//应用程序的图标
			infors.version = pinfor.versionName;//应用程序的包名
			infors.packagename = pinfor.packageName;//应用程序的版本名称
		if ((pinfor.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
			systempackageinfors.add(infors);
			}else {
				userpackageinfors.add(infors);
			}
			allpackageinfors.add(infors);
		}
	}
	
	
	/**
	 * 获取正在运行的应用程序信息集合
	 * @return 系统进程和用户进程
	 */
	public HashMap<Integer, ArrayList<ProgressInfors>> getRunningProcess(Context context){
		//由于有系统进程集合和用户进程集合，所以将他们统一存放在Map集合中
		HashMap<Integer, ArrayList<ProgressInfors>> map = new HashMap<Integer, ArrayList<ProgressInfors>>();
		//创建两个集合分别存放系的用户的
		ArrayList<ProgressInfors> system = new ArrayList<ProgressInfors>();
		ArrayList<ProgressInfors> user = new ArrayList<ProgressInfors>();
		
		
		//获得进程信息需要ActivityManager
		mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		//获得正在运行的进程	runningProcess——存放的是系统和用户都在一起的
		ArrayList<RunningAppProcessInfo> runningProcess = (ArrayList<RunningAppProcessInfo>) mActManager.getRunningAppProcesses();
			//循环进程的集合
			for (int x = 0; x < runningProcess.size(); x++) {
				RunningAppProcessInfo runProcess = runningProcess.get(x);
				String packageName = runProcess.processName;//进程名—包名
				int processID= runProcess.pid;//当前进程的ID
				int importance = runProcess.importance;//进程的优先级（清理能够清理的进程）。前台进程——> 可视进程 ——>服务进程 ——>后台进程 ——>空进程 
				//判断进程的优先级，如果高于服务进程则不加入集合  (服务进程会复活)
				if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {//空进程/后台进程
					//创建进程对象，准备存放数据
					ProgressInfors proInfors = new ProgressInfors();
					
					try {
						//首先获取应用程序信息对象    0表示不需要获取额外的信息
						ApplicationInfo applicationinfor = mpamanager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
						//获取图标
						Drawable icon = mpamanager.getApplicationIcon(packageName);
						//获取应用名
						String AppName = mpamanager.getApplicationLabel(applicationinfor).toString();
						//获取内存大小
						Debug.MemoryInfo[] AppSize = mActManager.getProcessMemoryInfo(new int []{processID});
						Debug.MemoryInfo MInfor = AppSize[0];
						int pss = (int) Math.round(MInfor.getTotalPss() / 1024.0);
						
						LogUtil.printlog("AppName", AppName+"——"+MInfor.getTotalPss() / 1024.0+"");
						//是否是系统信息
						if ((applicationinfor.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {//系统的
							proInfors.icon = icon;
							proInfors.memory = pss;
							proInfors.isSystem = true;
							proInfors.name = AppName;
							proInfors.PackageName = packageName;
							system.add(proInfors);//添加到系统集合中
						}else {//用户的
							proInfors.icon = icon;
							proInfors.memory = pss;
							proInfors.isSystem = false;
							proInfors.name = AppName;
							proInfors.PackageName = packageName;
							user.add(proInfors);//添加到用户集合中
						}
						//根据键的不同添加到Map集合中
						map.put(SYSTEM_PROCESS, system);
						map.put(USER_PROCESS, user);
					} catch (NameNotFoundException e) {
						e.printStackTrace();
						LogUtil.printlog("AppName", "应用名称没有找到");
					}
				}
			}
		return map;
	}
	
	
	/**
	 * 返回 ActivityManager
	 * @param context
	 * @return  ActivityManager的对象
	 */
	public ActivityManager getActivityManager(Context context){
		mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		return mActManager;
	}
	
	
	/**
	 * 主界面上的手机加速,清理应用程序
	 * @param context
	 */
		public void cleanApp(Context context){
			mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
			
			//获得正在运行的进程	runningProcess——存放的是系统和用户都在一起的
			ArrayList<RunningAppProcessInfo> runningProcess = (ArrayList<RunningAppProcessInfo>) mActManager.getRunningAppProcesses();
				//循环进程的集合
				for (int x = 0; x < runningProcess.size(); x++) {
					RunningAppProcessInfo runProcess = runningProcess.get(x);
					String packageName = runProcess.processName;//进程名—包名
					int importance = runProcess.importance;//进程的优先级（清理能够清理的进程）。前台进程——> 可视进程 ——>服务进程 ——>后台进程 ——>空进程 
					//判断进程的优先级，如果高于服务进程则不加入集合  (服务进程会复活)
					if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {//空进程/后台进程
						
						try {
							//首先获取应用程序信息对象    0表示不需要获取额外的信息
							ApplicationInfo applicationinfor = mpamanager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
							//是否是系统信息
							if ((applicationinfor.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {//系统的
								
							}else {//用户的
								//杀死进程
								//获得进程信息需要ActivityManager
								mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
								mActManager.killBackgroundProcesses(packageName);
							}
						} catch (NameNotFoundException e) {
							e.printStackTrace();
							LogUtil.printlog("AppName", "应用名称没有找到");
						}
					}
				}
		}
	
	
	
	/**
	 * 获得系统应用程序信息
	 *//*
	public ArrayList<AppInfors> getsystempackageinfors(){
		ArrayList<AppInfors> systempackageinfors = new ArrayList<AppInfors>();//系统软件
		//获得所有已安装的应用程序，包括系统的和用户的       | ——有一个true则为true，
		List<PackageInfo> packageInfors = mpamanager.getInstalledPackages
				(PackageManager.GET_ACTIVITIES | PackageManager.GET_UNINSTALLED_PACKAGES);
		PackageInfo ————表示一个应用程序的信息，对应项目中的AndroidManifest.xml文件
		 * PackageInfo对象存放的就是<menifest>里面的内容（包名/版本号/版本名称）
		 * ApplicationInfo对象中存放的就是<application>里面的内容（应用程序名称/应用程序图标）
		 * 
		 
		for (int x = 0; x < packageInfors.size(); x++) {
			//创建一个AppInfors对象，用来存储每个应用程序的信息
			AppInfors infors = new AppInfors();
			
			PackageInfo pinfor = packageInfors.get(x);
			LogUtil.printlog("PackageInfo", pinfor.packageName+"——"+pinfor.versionName+"——"+pinfor.versionCode);
			
			
			//判断是否是系统应用       应用程序信息中有一个标记 与 系统标记 不等于0 （两个条件都不成立则为true）
			if ((pinfor.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				
				//获取应用程序信息——应用程序的图标，名称，包名，版本号
				//获取ApplicationInfo。      apinfor.loadLabel——应用程序的标题，apinfor.loadIcon——应用程序的图标
				ApplicationInfo apinfor = pinfor.applicationInfo;
				LogUtil.printlog("系统", apinfor.loadLabel(mpamanager)+"");
				LogUtil.printlog("ApplicationInfo", apinfor.loadLabel(mpamanager)+"——"+apinfor.loadIcon(mpamanager));
				
				
				infors.name = apinfor.loadLabel(mpamanager).toString();//应用程序的名称
				infors.icon = apinfor.loadIcon(mpamanager);//应用程序的图标
				infors.version = pinfor.packageName;//应用程序的包名
				infors.packagename = pinfor.versionName;//应用程序的版本名称
				systempackageinfors.add(infors);
			}
		}
		return systempackageinfors;
	}
	
	
	
	*//**
	 * 获得用户应用程序信息
	 *//*
		public ArrayList<AppInfors> getuserpackageinfors(){
			ArrayList<AppInfors> userpackageinfors = new ArrayList<AppInfors>();//用户软件
			//获得所有已安装的应用程序，包括系统的和用户的       | ——有一个true则为true，
			List<PackageInfo> packageInfors = mpamanager.getInstalledPackages
					(PackageManager.GET_ACTIVITIES | PackageManager.GET_UNINSTALLED_PACKAGES);
			PackageInfo ————表示一个应用程序的信息，对应项目中的AndroidManifest.xml文件
			 * PackageInfo对象存放的就是<menifest>里面的内容（包名/版本号/版本名称）
			 * ApplicationInfo对象中存放的就是<application>里面的内容（应用程序名称/应用程序图标）
			 * 
			 
			for (int x = 0; x < packageInfors.size(); x++) {
				//创建一个AppInfors对象，用来存储每个应用程序的信息
				AppInfors infors = new AppInfors();
				
				PackageInfo pinfor = packageInfors.get(x);
				LogUtil.printlog("PackageInfo", pinfor.packageName+"——"+pinfor.versionName+"——"+pinfor.versionCode);
				
				
				//判断是否是系统应用       应用程序信息中有一个标记 与 系统标记 不等于0 （两个条件都不成立则为true）
				if ((pinfor.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
					
					//获取应用程序信息——应用程序的图标，名称，包名，版本号
					//获取ApplicationInfo。      apinfor.loadLabel——应用程序的标题，apinfor.loadIcon——应用程序的图标
					ApplicationInfo apinfor = pinfor.applicationInfo;
					LogUtil.printlog("用户", apinfor.loadLabel(mpamanager)+"");
					LogUtil.printlog("ApplicationInfo", apinfor.loadLabel(mpamanager)+"——"+apinfor.loadIcon(mpamanager));
					
					
					infors.name = apinfor.loadLabel(mpamanager).toString();//应用程序的名称
					infors.icon = apinfor.loadIcon(mpamanager);//应用程序的图标
					infors.version = pinfor.packageName;//应用程序的包名
					infors.packagename = pinfor.versionName;//应用程序的版本名称
					userpackageinfors.add(infors);
				}
			}
			return userpackageinfors;
		}*/
	
	
}
