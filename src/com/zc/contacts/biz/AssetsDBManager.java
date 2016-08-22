package com.zc.contacts.biz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.base.utils.ToastUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

/**
 * 该类用于管理工程中assets文件夹下的数据库文件
 * @author Administrator
 *
 */
public class AssetsDBManager {
	public static final String TAG = "AssetsDBManager";
	
	/**拷贝Assets文件夹下的db文件到手机中 
	 * 拷贝某个文件到相应的位置   
	 * @param context
	 * 		上下文
	 * @param from
	 * 		要拷贝文件的路径
	 * @param to
	 * 		拷贝到哪里去的路径
	 */
	public static void copyAssetsDBFileToPhoneSD(Context context,String from,String to){
		AssetManager am = context.getAssets();//通过getAssets函数获取AssetManager对象
		
		//IO流读写文件   字节流
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			InputStream is = am.open(from);
			
			 bis = new BufferedInputStream(is);
			 bos = new BufferedOutputStream(new FileOutputStream(to));
		
		int x;
		byte [] b = new byte [1024 * 2];
		while((x = bis.read(b)) != -1){
			bos.write(b, 0, x);
		}
		bos.flush();//刷新
		} catch (IOException e) {
			//数据库文件拷贝失败信息提示
			ToastUtil.show(context, "数据库文件拷贝失败....", Toast.LENGTH_SHORT);
			
		}finally{
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					LogUtil.printlog(TAG, "拷贝数据库文件关流异常");
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					LogUtil.printlog(TAG, "拷贝数据库文件关流异常");
				}
			}
		}
		
		
		
	}
	
	
	
}
