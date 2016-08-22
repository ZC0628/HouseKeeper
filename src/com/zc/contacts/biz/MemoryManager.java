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
 * 获取内存管理器
 * @author Administrator
 *
 */
public class MemoryManager {
	/**
	 * 获取手机自带总内存
	 * @return	手机自带总内存
	 */
	//获取手机自带总内存     data——手机内存的一块		cacahe——手机内存缓存区		root——属于手机里的
	public static long getphonememory(){
		//Environment可以获得手机内存卡和SD卡
		//返回的是root文件的路径
		File rootfile = Environment.getRootDirectory();
		//获取当前路径下的内存大小
		StatFs rootFS = new StatFs(rootfile.getPath());
		//获取每个内存块的大小
		int rootsize = rootFS.getBlockSize();
		//获取所有的内存块的个数
		int rootcount = rootFS.getBlockCount();
		//获取root文件的内存（每个内存的大小  * 所有的内存卡的个数）
		int root = rootsize * rootcount;
		
		//获取缓存文件的大小
		File cachefile = Environment.getDownloadCacheDirectory();
		StatFs cacheFS = new StatFs(cachefile.getPath());
		int cache = cacheFS.getBlockSize() * cacheFS.getBlockCount();
				
		//获取Data文件的大小
		File datafile = Environment.getDataDirectory();
		StatFs dataFS = new StatFs(cachefile.getPath());
		int data = dataFS.getBlockSize() * dataFS.getBlockCount();
		
		return root + cache + data;
	}
	
	
	/**
	 * 获取手机剩余内存
	 * @return	手机剩余内存
	 */
	//获取手机剩余内存     data——手机内存的一块		cacahe——手机内存缓存区		root——属于手机里的
		public static long getphoneFreememory(){
			//Environment可以获得手机内存卡和SD卡
			//返回的是root文件的路径
			File rootfile = Environment.getRootDirectory();
			//获取当前路径下的内存大小
			StatFs rootFS = new StatFs(rootfile.getPath());
			//获取每个内存块的大小
			int rootsize = rootFS.getBlockSize();
			//获取有效内存块的个数
			int rootcount = rootFS.getAvailableBlocks();
			//获取root文件的内存（每个内存的大小  * 所有的内存卡的个数）
			int root = rootsize * rootcount;
			
			//获取缓存文件的大小
			File cachefile = Environment.getDownloadCacheDirectory();
			StatFs cacheFS = new StatFs(cachefile.getPath());
			int cache = cacheFS.getBlockSize() * cacheFS.getAvailableBlocks();
					
			//获取Data文件的大小
			File datafile = Environment.getDataDirectory();
			StatFs dataFS = new StatFs(cachefile.getPath());
			int data = dataFS.getBlockSize() * dataFS.getAvailableBlocks();
			
			return root + cache + data;
		}
	
		
		/**
		 * 获取SD卡总内存
		 * @return	SD卡总内存 
		 */
		//获取SD卡总内存     
		public static long getSDmemory(){
			//Environment可以获得手机内存卡和SD卡
			//返回的是sd卡文件的路径————getExternalStorageDirectory();获取外部存储卡的文件夹
			File sdcardfile = Environment.getExternalStorageDirectory();
			//获取当前路径下的内存大小
			StatFs sdcardFS = new StatFs(sdcardfile.getPath());
			//获取每个内存块的大小
			int size = sdcardFS.getBlockSize();
			//获取所有的内存块的个数
			int count = sdcardFS.getBlockCount();
			//获取SD卡文件的内存（每个内存的大小  * 所有的内存卡的个数）
			int sdcard = size * count;
			
			return sdcard;
		}
		
		
		/**
		 * 获取SD卡剩余的内存 
		 * @return	SD卡剩余的内存 
		 */
				//获取SD卡剩余的内存     
				public static long getSDFreememory(){
					//Environment可以获得手机内存卡和SD卡
					//返回的是sd卡文件的路径————getExternalStorageDirectory();获取外部存储卡的文件夹
					File sdcardfile = Environment.getExternalStorageDirectory();
					//获取当前路径下的内存大小
					StatFs sdcardFS = new StatFs(sdcardfile.getPath());
					//获取有效内存块的个数
					int size = sdcardFS.getBlockSize();
					//获取所有的内存块的个数
					int count = sdcardFS.getAvailableBlocks();
					//获取SD卡文件的内存（每个内存的大小  * 所有的内存卡的个数）
					int sdcard = size * count;
					
					return sdcard;
				}
		
		
			/**
			 * 获取手机最大运行内存 （运存）
			* @return	手机最大运存 
			* data文件/proc/meminfo
			*/		
		public static long getMaxRam(){
			//手机管理器中data文件/proc/meminfo文件，该文件存放了手机运行时的一些内存信息，包括最大运存和空闲运存
			//用IO流去读取数据，读取的时候读取第一行即可
			BufferedReader br = null;
			try {
				 br = new BufferedReader(new FileReader("proc/meminfo"));
				try {
					String line = br.readLine();//只读一行
					//根据空格切割    由于空格较多，可以使用正则表达式表示一个或多个
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
						LogUtil.printlog("MemoryManager", "关流异常");
					}
				}
			}
			return 0;
		}
		
		
		private static ActivityManager mActManager;		
		/**
		 * 获取手机空闲内存 （运存）
		* @return	 空闲内存
		* 获取空闲内存使用ActivityManager
		*/
		public static long getFreeRam(Context context){
			if (mActManager == null ) {
				//获取空闲内存使用ActivityManager管理器
				mActManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
			}
			//会将获取内存信息存放在MemoryInfo对象中
			MemoryInfo outInfo = new MemoryInfo();
			mActManager.getMemoryInfo(outInfo);
			return outInfo.availMem;//av开头的都是空闲有效内存
		} 
		
		
		
		
}
