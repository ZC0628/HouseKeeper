package com.zc.contacts.base.activity;

import com.zc.contacts.R;
import com.zc.contacts.activity.LogoActivity;
import com.zc.contacts.activity.TelephoneActivity;
import com.zc.contacts.activity.TelephonelistActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
/**
 * ����avtivity�ĸ���
 * ��������ת�ĺ���
 */
public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected abstract void initview();
	
	/**
	 * ��һ������ֱ����ת����һ������
	 * @param cla	��ת���ĸ�����
	 */
	public void openActivity(Class<?> cla){
		//��ת��ͨѶ¼������
		Intent it = new Intent(this,cla);
		startActivity(it);
	}
	
	
	/**
	 * ��һ��������ת����һ������
	 * @param act	���ĸ����濪ʼ��ת
	 * @param cla	��ת���ĸ�����
	 */
	public void startActivity(Activity act,Class<?> cla){
		//��ת��ͨѶ¼������
		Intent it = new Intent(act,cla);
		startActivity(it);
		//���ٵ�ǰ����
		act.finish();
	}
	
	/**
	 * ��һ��������ת��ϵͳ����
	 * @param action	��ת���ĸ�ϵͳ����
	 */
	public void startActivity(String action){
		Intent i = new Intent(action);
		startActivity(i);
	}	
	
	/**
	 * ��һ��������ת����һ������,����Я������
	 * @param act	���ĸ����濪ʼ��ת
	 * @param cla	��ת���ĸ�����
	 */
	public void startActivity(Class<?> cla,int position){
		//��ת��TelephonelistActivity����
		Intent i = new Intent(this,cla);
		//���ڵ�TelephonelistActivity����Ҫ��ѯ��Ӧ�ı���е����ݣ�����Ҫ��position���ݹ�ȥ
		i.putExtra("index", position);
		startActivity(i);
	}
	
	/**
	 * ������ת����Я������
	 * @param cls		��ת���ĸ�����
	 * @param bundle	���ݰ�
	 */
		public void openActivityExtra(Class<?> cla, Bundle bundle) {
			Intent it = new Intent(this, cla);
			it.putExtra("bundle", bundle);
			startActivity(it);
			//overridePendingTransition(R.anim.in, R.anim.exit);
		}
	
	
	
	/**
	 * ��һ��������ת����һ������,���Ҵ��ж���Ч��
	 * @param act	���ĸ����濪ʼ��ת
	 * @param cla	��ת���ĸ�����
	 */
	protected void startActicity(Class<?> cla,int in,int out){
		//��ת��ͨѶ¼������
			Intent it = new Intent(this,cla);
			/*
			 * ���������֮���л���һ���ǽ�ȥ�Ķ�����һ���ǳ����Ķ������������£�
			 * ������ȥ�ͳ�ȥ��ƽ��XML�ļ�
			 * ��ȥ��X���0%  �� 0%   Y���0% �� -200%
			 * ��ȥ��X���0%  �� 0%   Y���200%��0%
			 * ������ת�������������startActivity����finish();����
			 * 
			 * //������������ʱ����õĺ���
				@Override
				public void onAnimationEnd(Animation animation) {
					startActicity(LogoActivity.this, TelephoneActivity.class,in,out);
				}
			 */
			overridePendingTransition(in, out);
			startActivity(it);
			//���ٵ�ǰ����
			this.finish();
	}
	
}
