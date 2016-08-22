package com.zc.contacts.base.utils;

import com.zc.contacts.biz.MemoryManager;

import android.content.Context;
import android.text.format.Formatter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 内存转化工具类
 * @author Administrator
 *
 */
public final class MemoryUtil {
	private static TextView mphonememory,msdmemory;//获取手机可用内存和SD卡的可用内存
	private static ProgressBar mphonePB,msdPB;//手机内存的进度条和SD卡的进度条
	
	public static void phoneSDmemory(Context context){
		
				long phoneAll = MemoryManager.getphonememory();
				long phoneFree = MemoryManager.getphoneFreememory();
				long SDAll = MemoryManager.getSDmemory();
				long SDFree = MemoryManager.getSDFreememory();
				
				//用户函数字节转换——M		formatFileSize(context, number)文件的大小
				String phoneAllMB = Formatter.formatFileSize(context, phoneAll);
				String phoneFreeMB = Formatter.formatFileSize(context, phoneFree);
				String SDAllMB = Formatter.formatFileSize(context, SDAll);
				String SDFreeMB = Formatter.formatFileSize(context, SDFree);
				
				LogUtil.printlog("内存信息", phoneAllMB+"——"+phoneFreeMB+"——"+SDAllMB+"——"+SDFreeMB);
				
				//设置文字 
				mphonememory.setText("可用 :"+phoneFreeMB+"/"+phoneAllMB);
				msdmemory.setText("可用 :"+SDFreeMB+"/"+SDAllMB);
				//设置进度条 
				/* 手机总内存减去剩余内存就是进度条上显示的内存
				*得出一个百分比  然后四舍五入
				 * 
				 */
				mphonePB.setProgress((int)Math.round((phoneAll - phoneFree) / (double)phoneAll*100));
				msdPB.setProgress((int)Math.round((SDAll - SDFree) / (double)SDAll*100));
		
	}
	
	
}
