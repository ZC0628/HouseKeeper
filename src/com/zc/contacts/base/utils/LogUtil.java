package com.zc.contacts.base.utils;

import android.util.Log;

/**
 * ͳһ������Ŀ�е�Log��־
 * @author Administrator
 *��������Ϊtrue�����ʾ����־
 *��������Ϊfalse�����ʾ�ر���־
 *
 *һ��ʽ��û������Լ̳е�     ���Կ��Լ�final
 */

public final class LogUtil {
	//�ж��Ƿ���ʾ��־������
	public static boolean isDebug = true;//���е���  Ĭ��Ϊ�򿪵�
	
	/**
	 * ��ӡ��ͨ��Ϣlog��־�ĺ���
	 * 
	 * @param tag
	 * ��ӡ��ǩ����
	 * @param msg
	 * ��ӡ��־������
	 */
	public static void printlog(String tag,String msg){
		if(isDebug = true)//�����־�Ǵ򿪵ľʹ�ӡ��־
		Log.i(tag, msg);
	} 
	
	
	
	
	/**
	 * ��ӡ������Ϣlog��־�ĺ���
	 * 
	 * @param tag
	 * ��ӡ��ǩ����
	 * @param msg
	 * ��ӡ��־������
	 */
	public static void printseriouslog(String tag,String msg){
		if(isDebug = true)//�����־�Ǵ򿪵ľʹ�ӡ��־
		Log.i(tag, msg);
	} 
	
}
