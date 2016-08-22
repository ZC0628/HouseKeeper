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
 * �ֻ���Ϣ������  ���������ͺţ��汾�ţ��˴�
 * @author Administrator
 *
 */
public final class PhoneInforsManager {
	/**
	 * ��ȡ�ֻ����ͺ�
	 * @return
	 */
	public static String getPhoneModel(){
		
		return Build.MODEL;
	}
	
	/**
	 * ��ȡ�ֻ���Ʒ��
	 * @return
	 */
	public static String getPhoneBrand(){
		
		return Build.BRAND;
	}
	
	/**
	 * ��ȡ�ֻ��İ汾
	 * @return
	 */
	public static String getPhonVersion(){
		
		return Build.VERSION.RELEASE;
	}
	
	
	/**
	 * ��ȡ�ֻ�CPU����
	 * @return    cpu.length�ĳ���
	 */
	public static int getCPUnumber(){
		File file = new File("sys/devices/system/cpu/");
		File[] cpu = file.listFiles(new FileFilter() {
			/*
			 * FileFilter ���� �ļ����������
			 * ����豸CPU����
			 * 
			 */
			@Override
			public boolean accept(File pathname) {
				//������·���������� cpu[0-9]�ģ�  0������һ��cpu�����������ͷ���true
				if (Pattern.matches("cpu[0-9]", pathname.getName())) {
					return  true;
				}
				return false;
			}
		});
		return cpu.length;
	}
	
	/**
	 * ��ȡcpu����
	 * @return cpu������ 
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
	 * ��ȡ�ֻ���Ļ����
	 * @param activity
	 * @return
	 */
	public static String getDisplayMetrics(Activity activity){
		//��ȡ��Ļ��Ϣ  
		DisplayMetrics disme = new DisplayMetrics();
		WindowManager winmanager = activity.getWindowManager();
		Display display = winmanager.getDefaultDisplay();
		display.getMetrics(disme);
		LogUtil.printlog("width", disme.widthPixels+"");
		LogUtil.printlog("height", disme.heightPixels+"");
		
		return  disme.widthPixels+" * "+disme.heightPixels;
	}
	
	/**
	 * ��ȡ����ֱ���
	 * @return
	 */
	public static String getcameraMetrics(){
		Camera camera = Camera.open();
		if (camera == null) {
			return "ģ����û������ͷ";
		}
		Parameters parameters =   camera.getParameters();
		//List<Size> ����size������ųߴ��
		List<Size> Listsize = parameters.getSupportedPictureSizes();
		//����size�д�ŵ����Ƚ϶࣬��������Ҫ�������ĳߴ�
		Size size = Listsize.get(0);//�Լ������,�Ȼ�ȡ��һ��Ȼ��ѵ�һ����ֵ��Size��ѭ������
		for (int x = 0; x < Listsize.size(); x++) {
			Size  s = Listsize.get(x);//�����л�ȡ��
			//��ȡ���������ĳߴ�    ���size�д�ŵĳߴ�С�ڼ����еĳߴ磬�ͻ�ȡ���ߴ�
			if (size.width * size.height < s.width * s.height ) {
				size = s;
			}
		}
		//�����������Ҫ�رգ���Ȼ�ᱨ��
		camera.stopPreview();
		camera.release();
		camera = null;
		
		return size.width +" * "+ size.height;
	}
	
	/**
	 * ��ȡ�����汾
	 * @return
	 */
	public static String getBasebandversion(){
		return Build.RADIO;
	}
	
	/**
	 * �ж��ֻ��Ƿ�Root
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


