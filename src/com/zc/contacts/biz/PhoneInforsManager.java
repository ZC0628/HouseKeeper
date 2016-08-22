package com.zc.contacts.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.zc.contacts.base.utils.LogUtil;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Filter;


/**
 * 手机信息管理器  ————型号，版本号，运存
 * @author Administrator
 *
 */
public final class PhoneInforsManager {
	/**
	 * 获取手机的型号
	 * @return
	 */
	public static String getPhoneModel(){
		
		return Build.MODEL;
	}
	
	/**
	 * 获取手机的品牌
	 * @return
	 */
	public static String getPhoneBrand(){
		
		return Build.BRAND;
	}
	
	/**
	 * 获取手机的版本
	 * @return
	 */
	public static String getPhonVersion(){
		
		return Build.VERSION.RELEASE;
	}
	
	
	/**
	 * 获取手机CPU数量
	 * @return    cpu.length的长度
	 */
	public static int getCPUnumber(){
		File file = new File("sys/devices/system/cpu/");
		File[] cpu = file.listFiles(new FileFilter() {
			/*
			 * FileFilter —— 文件过滤器借口
			 * 获得设备CPU数量
			 * 
			 */
			@Override
			public boolean accept(File pathname) {
				//如果你的路径的名称是 cpu[0-9]的，  0就是有一个cpu。满足条件就返回true
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return  true;
				}
				return false;
			}
		});
		return cpu.length;
	}
	
	/**
	 * 获取cpu名称
	 * @return cpu的名称 
	 */
	public static String getCPUname(){
		FileReader  filereader = null;
		BufferedReader bufferedreader = null;
		
		try {
			filereader = new FileReader("/proc/cpuinfo");
			bufferedreader = new BufferedReader(filereader);
			try {
				String text = bufferedreader.readLine();
				String [] array = text.split(":\\s+", 2);
				LogUtil.printlog("CPUname", Arrays.toString(array));
				for (int x = 0; x < array.length; x++) {
					
				}
				return array[1];
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (filereader != null) {
				try {
					filereader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bufferedreader != null) {
				try {
					bufferedreader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
		
	}
	
	/**
	 * 获取手机屏幕像素
	 * @param activity
	 * @return
	 */
	public static String getDisplayMetrics(Activity activity){
		//获取屏幕信息  
		DisplayMetrics disme = new DisplayMetrics();
		WindowManager winmanager = activity.getWindowManager();
		Display display = winmanager.getDefaultDisplay();
		display.getMetrics(disme);
		LogUtil.printlog("width", disme.widthPixels+"");
		LogUtil.printlog("height", disme.heightPixels+"");
		
		return  disme.widthPixels+" * "+disme.heightPixels;
	}
	
	/**
	 * 获取相机分辨率
	 * @return
	 */
	public static String getcameraMetrics(){
		Camera camera = Camera.open();
		if (camera == null) {
			return "模拟器没有摄像头";
		}
		Parameters parameters =   camera.getParameters();
		//List<Size> ——size用来存放尺寸的
		List<Size> Listsize = parameters.getSupportedPictureSizes();
		//由于size中存放的量比较多，而我们需要的是最大的尺寸
		Size size = Listsize.get(0);//自己定义的,先获取第一个然后把第一个赋值给Size，循环集合
		for (int x = 0; x < Listsize.size(); x++) {
			Size  s = Listsize.get(x);//集合中获取的
			//获取集合中最大的尺寸    如果size中存放的尺寸小于集合中的尺寸，就获取最大尺寸
			if (size.width * size.height < s.width * s.height ) {
				size = s;
			}
		}
		//相机开启后需要关闭，不然会报错
		camera.stopPreview();
		camera.release();
		camera = null;
		
		return size.width +" * "+ size.height;
	}
	
	/**
	 * 获取基带版本
	 * @return
	 */
	public static String getBasebandversion(){
		return Build.RADIO;
	}
	
	/**
	 * 判断手机是否Root
	 * @return
	 */
	public static boolean isRoot(){
		boolean bool = false;
		if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
			bool = false;
		}else {
			bool = true;
		}
		return bool;
	}
	
	
}


