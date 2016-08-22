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
 * Ӧ�ó�����Ϣ������
 * @author Administrator
 *���ڻ�ȡ����Ӧ�ó����ͼ�꣬���ƣ��������汾��
 */
public class AppInforsManager {

	private  PackageManager mpamanager;	//��������
	private ActivityManager mActManager;//�������������������е�Ӧ�ó���ɱ�����̵�
	private ArrayList<AppInfors> allpackageinfors = new ArrayList<AppInfors>();//�������
	private ArrayList<AppInfors> systempackageinfors = new ArrayList<AppInfors>();//ϵͳ���
	private ArrayList<AppInfors> userpackageinfors = new ArrayList<AppInfors>();//�û����
	
	//��������ϵͳ�ĺ��û�����ļ�
	public static final int SYSTEM_PROCESS = 1;
	public static final int USER_PROCESS = 2;
	
	
	public AppInforsManager(Context context){
		if (mpamanager == null) {
			mpamanager = context.getPackageManager();//��ȡһ����������
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
	 * ���Ӧ�ó�����Ϣ(�����������ϵͳ��������û����)
	 */
	public void addAllpackageinfors(){
		//��������Ѱ�װ��Ӧ�ó��򣬰���ϵͳ�ĺ��û���       | ������һ��true��Ϊtrue��
		List<PackageInfo> packageInfors = mpamanager.getInstalledPackages
				(PackageManager.GET_ACTIVITIES | PackageManager.GET_UNINSTALLED_PACKAGES);
		/*PackageInfo ����������ʾһ��Ӧ�ó������Ϣ����Ӧ��Ŀ�е�AndroidManifest.xml�ļ�
		 * PackageInfo�����ŵľ���<menifest>��������ݣ�����/�汾��/�汾���ƣ�
		 * ApplicationInfo�����д�ŵľ���<application>��������ݣ�Ӧ�ó�������/Ӧ�ó���ͼ�꣩
		 * 
		 */
		for (int x = 0; x < packageInfors.size(); x++) {
			//����һ��AppInfors���������洢ÿ��Ӧ�ó������Ϣ
			AppInfors infors = new AppInfors();
			
			PackageInfo pinfor = packageInfors.get(x);
			LogUtil.printlog("PackageInfo", pinfor.packageName+"����"+pinfor.versionName+"����"+pinfor.versionCode);
			
			//��ȡApplicationInfo��      apinfor.loadLabel����Ӧ�ó���ı��⣬apinfor.loadIcon����Ӧ�ó����ͼ��
			ApplicationInfo apinfor = pinfor.applicationInfo;
			LogUtil.printlog("ApplicationInfo", apinfor.loadLabel(mpamanager)+"����"+apinfor.loadIcon(mpamanager));
			
			//��ȡӦ�ó�����Ϣ����Ӧ�ó����ͼ�꣬���ƣ��������汾��
			infors.name = apinfor.loadLabel(mpamanager).toString();//Ӧ�ó��������
			infors.icon = apinfor.loadIcon(mpamanager);//Ӧ�ó����ͼ��
			infors.version = pinfor.versionName;//Ӧ�ó���İ���
			infors.packagename = pinfor.packageName;//Ӧ�ó���İ汾����
		if ((pinfor.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
			systempackageinfors.add(infors);
			}else {
				userpackageinfors.add(infors);
			}
			allpackageinfors.add(infors);
		}
	}
	
	
	/**
	 * ��ȡ�������е�Ӧ�ó�����Ϣ����
	 * @return ϵͳ���̺��û�����
	 */
	public HashMap<Integer, ArrayList<ProgressInfors>> getRunningProcess(Context context){
		//������ϵͳ���̼��Ϻ��û����̼��ϣ����Խ�����ͳһ�����Map������
		HashMap<Integer, ArrayList<ProgressInfors>> map = new HashMap<Integer, ArrayList<ProgressInfors>>();
		//�����������Ϸֱ���ϵ���û���
		ArrayList<ProgressInfors> system = new ArrayList<ProgressInfors>();
		ArrayList<ProgressInfors> user = new ArrayList<ProgressInfors>();
		
		
		//��ý�����Ϣ��ҪActivityManager
		mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		//����������еĽ���	runningProcess������ŵ���ϵͳ���û�����һ���
		ArrayList<RunningAppProcessInfo> runningProcess = (ArrayList<RunningAppProcessInfo>) mActManager.getRunningAppProcesses();
			//ѭ�����̵ļ���
			for (int x = 0; x < runningProcess.size(); x++) {
				RunningAppProcessInfo runProcess = runningProcess.get(x);
				String packageName = runProcess.processName;//������������
				int processID= runProcess.pid;//��ǰ���̵�ID
				int importance = runProcess.importance;//���̵����ȼ��������ܹ�����Ľ��̣���ǰ̨���̡���> ���ӽ��� ����>������� ����>��̨���� ����>�ս��� 
				//�жϽ��̵����ȼ���������ڷ�������򲻼��뼯��  (������̻Ḵ��)
				if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {//�ս���/��̨����
					//�������̶���׼���������
					ProgressInfors proInfors = new ProgressInfors();
					
					try {
						//���Ȼ�ȡӦ�ó�����Ϣ����    0��ʾ����Ҫ��ȡ�������Ϣ
						ApplicationInfo applicationinfor = mpamanager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
						//��ȡͼ��
						Drawable icon = mpamanager.getApplicationIcon(packageName);
						//��ȡӦ����
						String AppName = mpamanager.getApplicationLabel(applicationinfor).toString();
						//��ȡ�ڴ��С
						Debug.MemoryInfo[] AppSize = mActManager.getProcessMemoryInfo(new int []{processID});
						Debug.MemoryInfo MInfor = AppSize[0];
						int pss = (int) Math.round(MInfor.getTotalPss() / 1024.0);
						
						LogUtil.printlog("AppName", AppName+"����"+MInfor.getTotalPss() / 1024.0+"");
						//�Ƿ���ϵͳ��Ϣ
						if ((applicationinfor.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {//ϵͳ��
							proInfors.icon = icon;
							proInfors.memory = pss;
							proInfors.isSystem = true;
							proInfors.name = AppName;
							proInfors.PackageName = packageName;
							system.add(proInfors);//��ӵ�ϵͳ������
						}else {//�û���
							proInfors.icon = icon;
							proInfors.memory = pss;
							proInfors.isSystem = false;
							proInfors.name = AppName;
							proInfors.PackageName = packageName;
							user.add(proInfors);//��ӵ��û�������
						}
						//���ݼ��Ĳ�ͬ��ӵ�Map������
						map.put(SYSTEM_PROCESS, system);
						map.put(USER_PROCESS, user);
					} catch (NameNotFoundException e) {
						e.printStackTrace();
						LogUtil.printlog("AppName", "Ӧ������û���ҵ�");
					}
				}
			}
		return map;
	}
	
	
	/**
	 * ���� ActivityManager
	 * @param context
	 * @return  ActivityManager�Ķ���
	 */
	public ActivityManager getActivityManager(Context context){
		mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		return mActManager;
	}
	
	
	/**
	 * �������ϵ��ֻ�����,����Ӧ�ó���
	 * @param context
	 */
		public void cleanApp(Context context){
			mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
			
			//����������еĽ���	runningProcess������ŵ���ϵͳ���û�����һ���
			ArrayList<RunningAppProcessInfo> runningProcess = (ArrayList<RunningAppProcessInfo>) mActManager.getRunningAppProcesses();
				//ѭ�����̵ļ���
				for (int x = 0; x < runningProcess.size(); x++) {
					RunningAppProcessInfo runProcess = runningProcess.get(x);
					String packageName = runProcess.processName;//������������
					int importance = runProcess.importance;//���̵����ȼ��������ܹ�����Ľ��̣���ǰ̨���̡���> ���ӽ��� ����>������� ����>��̨���� ����>�ս��� 
					//�жϽ��̵����ȼ���������ڷ�������򲻼��뼯��  (������̻Ḵ��)
					if (importance >= RunningAppProcessInfo.IMPORTANCE_SERVICE) {//�ս���/��̨����
						
						try {
							//���Ȼ�ȡӦ�ó�����Ϣ����    0��ʾ����Ҫ��ȡ�������Ϣ
							ApplicationInfo applicationinfor = mpamanager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
							//�Ƿ���ϵͳ��Ϣ
							if ((applicationinfor.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {//ϵͳ��
								
							}else {//�û���
								//ɱ������
								//��ý�����Ϣ��ҪActivityManager
								mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
								mActManager.killBackgroundProcesses(packageName);
							}
						} catch (NameNotFoundException e) {
							e.printStackTrace();
							LogUtil.printlog("AppName", "Ӧ������û���ҵ�");
						}
					}
				}
		}
	
	
	
	/**
	 * ���ϵͳӦ�ó�����Ϣ
	 *//*
	public ArrayList<AppInfors> getsystempackageinfors(){
		ArrayList<AppInfors> systempackageinfors = new ArrayList<AppInfors>();//ϵͳ���
		//��������Ѱ�װ��Ӧ�ó��򣬰���ϵͳ�ĺ��û���       | ������һ��true��Ϊtrue��
		List<PackageInfo> packageInfors = mpamanager.getInstalledPackages
				(PackageManager.GET_ACTIVITIES | PackageManager.GET_UNINSTALLED_PACKAGES);
		PackageInfo ����������ʾһ��Ӧ�ó������Ϣ����Ӧ��Ŀ�е�AndroidManifest.xml�ļ�
		 * PackageInfo�����ŵľ���<menifest>��������ݣ�����/�汾��/�汾���ƣ�
		 * ApplicationInfo�����д�ŵľ���<application>��������ݣ�Ӧ�ó�������/Ӧ�ó���ͼ�꣩
		 * 
		 
		for (int x = 0; x < packageInfors.size(); x++) {
			//����һ��AppInfors���������洢ÿ��Ӧ�ó������Ϣ
			AppInfors infors = new AppInfors();
			
			PackageInfo pinfor = packageInfors.get(x);
			LogUtil.printlog("PackageInfo", pinfor.packageName+"����"+pinfor.versionName+"����"+pinfor.versionCode);
			
			
			//�ж��Ƿ���ϵͳӦ��       Ӧ�ó�����Ϣ����һ����� �� ϵͳ��� ������0 ��������������������Ϊtrue��
			if ((pinfor.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				
				//��ȡӦ�ó�����Ϣ����Ӧ�ó����ͼ�꣬���ƣ��������汾��
				//��ȡApplicationInfo��      apinfor.loadLabel����Ӧ�ó���ı��⣬apinfor.loadIcon����Ӧ�ó����ͼ��
				ApplicationInfo apinfor = pinfor.applicationInfo;
				LogUtil.printlog("ϵͳ", apinfor.loadLabel(mpamanager)+"");
				LogUtil.printlog("ApplicationInfo", apinfor.loadLabel(mpamanager)+"����"+apinfor.loadIcon(mpamanager));
				
				
				infors.name = apinfor.loadLabel(mpamanager).toString();//Ӧ�ó��������
				infors.icon = apinfor.loadIcon(mpamanager);//Ӧ�ó����ͼ��
				infors.version = pinfor.packageName;//Ӧ�ó���İ���
				infors.packagename = pinfor.versionName;//Ӧ�ó���İ汾����
				systempackageinfors.add(infors);
			}
		}
		return systempackageinfors;
	}
	
	
	
	*//**
	 * ����û�Ӧ�ó�����Ϣ
	 *//*
		public ArrayList<AppInfors> getuserpackageinfors(){
			ArrayList<AppInfors> userpackageinfors = new ArrayList<AppInfors>();//�û����
			//��������Ѱ�װ��Ӧ�ó��򣬰���ϵͳ�ĺ��û���       | ������һ��true��Ϊtrue��
			List<PackageInfo> packageInfors = mpamanager.getInstalledPackages
					(PackageManager.GET_ACTIVITIES | PackageManager.GET_UNINSTALLED_PACKAGES);
			PackageInfo ����������ʾһ��Ӧ�ó������Ϣ����Ӧ��Ŀ�е�AndroidManifest.xml�ļ�
			 * PackageInfo�����ŵľ���<menifest>��������ݣ�����/�汾��/�汾���ƣ�
			 * ApplicationInfo�����д�ŵľ���<application>��������ݣ�Ӧ�ó�������/Ӧ�ó���ͼ�꣩
			 * 
			 
			for (int x = 0; x < packageInfors.size(); x++) {
				//����һ��AppInfors���������洢ÿ��Ӧ�ó������Ϣ
				AppInfors infors = new AppInfors();
				
				PackageInfo pinfor = packageInfors.get(x);
				LogUtil.printlog("PackageInfo", pinfor.packageName+"����"+pinfor.versionName+"����"+pinfor.versionCode);
				
				
				//�ж��Ƿ���ϵͳӦ��       Ӧ�ó�����Ϣ����һ����� �� ϵͳ��� ������0 ��������������������Ϊtrue��
				if ((pinfor.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
					
					//��ȡӦ�ó�����Ϣ����Ӧ�ó����ͼ�꣬���ƣ��������汾��
					//��ȡApplicationInfo��      apinfor.loadLabel����Ӧ�ó���ı��⣬apinfor.loadIcon����Ӧ�ó����ͼ��
					ApplicationInfo apinfor = pinfor.applicationInfo;
					LogUtil.printlog("�û�", apinfor.loadLabel(mpamanager)+"");
					LogUtil.printlog("ApplicationInfo", apinfor.loadLabel(mpamanager)+"����"+apinfor.loadIcon(mpamanager));
					
					
					infors.name = apinfor.loadLabel(mpamanager).toString();//Ӧ�ó��������
					infors.icon = apinfor.loadIcon(mpamanager);//Ӧ�ó����ͼ��
					infors.version = pinfor.packageName;//Ӧ�ó���İ���
					infors.packagename = pinfor.versionName;//Ӧ�ó���İ汾����
					userpackageinfors.add(infors);
				}
			}
			return userpackageinfors;
		}*/
	
	
}
