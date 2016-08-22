package com.zc.contacts.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.zc.contacts.base.utils.LogUtil;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

/**
 * ��ȡ�ڴ������
 * @author Administrator
 *
 */
public class MemoryManager {
	/**
	 * ��ȡ�ֻ��Դ����ڴ�
	 * @return	�ֻ��Դ����ڴ�
	 */
	//��ȡ�ֻ��Դ����ڴ�     data�����ֻ��ڴ��һ��		cacahe�����ֻ��ڴ滺����		root���������ֻ����
	public static long getphonememory(){
		//Environment���Ի���ֻ��ڴ濨��SD��
		//���ص���root�ļ���·��
		File rootfile = Environment.getRootDirectory();
		//��ȡ��ǰ·���µ��ڴ��С
		StatFs rootFS = new StatFs(rootfile.getPath());
		//��ȡÿ���ڴ��Ĵ�С
		int rootsize = rootFS.getBlockSize();
		//��ȡ���е��ڴ��ĸ���
		int rootcount = rootFS.getBlockCount();
		//��ȡroot�ļ����ڴ棨ÿ���ڴ�Ĵ�С  * ���е��ڴ濨�ĸ�����
		int root = rootsize * rootcount;
		
		//��ȡ�����ļ��Ĵ�С
		File cachefile = Environment.getDownloadCacheDirectory();
		StatFs cacheFS = new StatFs(cachefile.getPath());
		int cache = cacheFS.getBlockSize() * cacheFS.getBlockCount();
				
		//��ȡData�ļ��Ĵ�С
		File datafile = Environment.getDataDirectory();
		StatFs dataFS = new StatFs(cachefile.getPath());
		int data = dataFS.getBlockSize() * dataFS.getBlockCount();
		
		return root + cache + data;
	}
	
	
	/**
	 * ��ȡ�ֻ�ʣ���ڴ�
	 * @return	�ֻ�ʣ���ڴ�
	 */
	//��ȡ�ֻ�ʣ���ڴ�     data�����ֻ��ڴ��һ��		cacahe�����ֻ��ڴ滺����		root���������ֻ����
		public static long getphoneFreememory(){
			//Environment���Ի���ֻ��ڴ濨��SD��
			//���ص���root�ļ���·��
			File rootfile = Environment.getRootDirectory();
			//��ȡ��ǰ·���µ��ڴ��С
			StatFs rootFS = new StatFs(rootfile.getPath());
			//��ȡÿ���ڴ��Ĵ�С
			int rootsize = rootFS.getBlockSize();
			//��ȡ��Ч�ڴ��ĸ���
			int rootcount = rootFS.getAvailableBlocks();
			//��ȡroot�ļ����ڴ棨ÿ���ڴ�Ĵ�С  * ���е��ڴ濨�ĸ�����
			int root = rootsize * rootcount;
			
			//��ȡ�����ļ��Ĵ�С
			File cachefile = Environment.getDownloadCacheDirectory();
			StatFs cacheFS = new StatFs(cachefile.getPath());
			int cache = cacheFS.getBlockSize() * cacheFS.getAvailableBlocks();
					
			//��ȡData�ļ��Ĵ�С
			File datafile = Environment.getDataDirectory();
			StatFs dataFS = new StatFs(cachefile.getPath());
			int data = dataFS.getBlockSize() * dataFS.getAvailableBlocks();
			
			return root + cache + data;
		}
	
		
		/**
		 * ��ȡSD�����ڴ�
		 * @return	SD�����ڴ� 
		 */
		//��ȡSD�����ڴ�     
		public static long getSDmemory(){
			//Environment���Ի���ֻ��ڴ濨��SD��
			//���ص���sd���ļ���·����������getExternalStorageDirectory();��ȡ�ⲿ�洢�����ļ���
			File sdcardfile = Environment.getExternalStorageDirectory();
			//��ȡ��ǰ·���µ��ڴ��С
			StatFs sdcardFS = new StatFs(sdcardfile.getPath());
			//��ȡÿ���ڴ��Ĵ�С
			int size = sdcardFS.getBlockSize();
			//��ȡ���е��ڴ��ĸ���
			int count = sdcardFS.getBlockCount();
			//��ȡSD���ļ����ڴ棨ÿ���ڴ�Ĵ�С  * ���е��ڴ濨�ĸ�����
			int sdcard = size * count;
			
			return sdcard;
		}
		
		
		/**
		 * ��ȡSD��ʣ����ڴ� 
		 * @return	SD��ʣ����ڴ� 
		 */
				//��ȡSD��ʣ����ڴ�     
				public static long getSDFreememory(){
					//Environment���Ի���ֻ��ڴ濨��SD��
					//���ص���sd���ļ���·����������getExternalStorageDirectory();��ȡ�ⲿ�洢�����ļ���
					File sdcardfile = Environment.getExternalStorageDirectory();
					//��ȡ��ǰ·���µ��ڴ��С
					StatFs sdcardFS = new StatFs(sdcardfile.getPath());
					//��ȡ��Ч�ڴ��ĸ���
					int size = sdcardFS.getBlockSize();
					//��ȡ���е��ڴ��ĸ���
					int count = sdcardFS.getAvailableBlocks();
					//��ȡSD���ļ����ڴ棨ÿ���ڴ�Ĵ�С  * ���е��ڴ濨�ĸ�����
					int sdcard = size * count;
					
					return sdcard;
				}
		
		
			/**
			 * ��ȡ�ֻ���������ڴ� ���˴棩
			* @return	�ֻ�����˴� 
			* data�ļ�/proc/meminfo
			*/		
		public static long getMaxRam(){
			//�ֻ���������data�ļ�/proc/meminfo�ļ������ļ�������ֻ�����ʱ��һЩ�ڴ���Ϣ����������˴�Ϳ����˴�
			//��IO��ȥ��ȡ���ݣ���ȡ��ʱ���ȡ��һ�м���
			BufferedReader br = null;
			try {
				 br = new BufferedReader(new FileReader("proc/meminfo"));
				try {
					String line = br.readLine();//ֻ��һ��
					//���ݿո��и�    ���ڿո�϶࣬����ʹ��������ʽ��ʾһ������
					String [] arrLines = line.split(" +");
					LogUtil.printlog("arrLines", Arrays.toString(arrLines));
					return Integer.parseInt(arrLines[1]) * 1024;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
						LogUtil.printlog("MemoryManager", "�����쳣");
					}
				}
			}
			return 0;
		}
		
		
		private static ActivityManager mActManager;		
		/**
		 * ��ȡ�ֻ������ڴ� ���˴棩
		* @return	 �����ڴ�
		* ��ȡ�����ڴ�ʹ��ActivityManager
		*/
		public static long getFreeRam(Context context){
			if (mActManager == null ) {
				//��ȡ�����ڴ�ʹ��ActivityManager������
				mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
			}
			//�Ὣ��ȡ�ڴ���Ϣ�����MemoryInfo������
			MemoryInfo outInfo = new MemoryInfo();
			mActManager.getMemoryInfo(outInfo);
			return outInfo.availMem;//av��ͷ�Ķ��ǿ�����Ч�ڴ�
		} 
		
		
		
		
}
