package com.zc.contacts.base.utils;

import android.content.Context;
import android.widget.Toast;
/**
 * ��ӡ����Ϣ������      ��Toast�����ǵ�����
 * @author Administrator
 *
 */
public final class ToastUtil {
	//Ϊ�˵���show������ʱ��Toast����Ҫÿ�ζ�������ֻ��Ҫ����һ������
	private static Toast toast;
	/**
	 * 
	 * ��ӡ����Ϣ
	 * @param context  ������
	 * @param text		��������
	 * @param duration	����Ϣ����ʱ��
	 */
	public static void show(Context context,String text,int duration){
		
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		}
		//�޸�����
		toast.setText(text);
		toast.setDuration(duration);
		//��ʾ����
		toast.show();
	}
	
	
	
}
