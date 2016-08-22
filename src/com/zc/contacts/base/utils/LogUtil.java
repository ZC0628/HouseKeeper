package com.zc.contacts.base.utils;

import android.util.Log;

/**
 * 统一管理项目中的Log日志
 * @author Administrator
 *设置属性为true，则表示打开日志
 *设置属性为false，则表示关闭日志
 *
 *一般式是没有类可以继承的     所以可以加final
 */

public final class LogUtil {
	//判断是否显示日志的属性
	public static boolean isDebug = true;//进行调试  默认为打开的
	
	/**
	 * 打印普通信息log日志的函数
	 * 
	 * @param tag
	 * 打印标签名称
	 * @param msg
	 * 打印日志的内容
	 */
	public static void printlog(String tag,String msg){
		if(isDebug = true)//如果日志是打开的就打印日志
		Log.i(tag, msg);
	} 
	
	
	
	
	/**
	 * 打印严重信息log日志的函数
	 * 
	 * @param tag
	 * 打印标签名称
	 * @param msg
	 * 打印日志的内容
	 */
	public static void printseriouslog(String tag,String msg){
		if(isDebug = true)//如果日志是打开的就打印日志
		Log.i(tag, msg);
	} 
	
}
