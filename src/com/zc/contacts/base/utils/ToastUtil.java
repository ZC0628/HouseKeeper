package com.zc.contacts.base.utils;

import android.content.Context;
import android.widget.Toast;
/**
 * 打印短消息工具类      ，Toast对象是单例的
 * @author Administrator
 *
 */
public final class ToastUtil {
	//为了调用show函数的时候，Toast对象不要每次都创建，只需要创建一个即可
	private static Toast toast;
	/**
	 * 
	 * 打印短消息
	 * @param context  上下文
	 * @param text		文字内容
	 * @param duration	短消息持续时间
	 */
	public static void show(Context context,String text,int duration){
		
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		}
		//修改内容
		toast.setText(text);
		toast.setDuration(duration);
		//显示内容
		toast.show();
	}
	
	
	
}
