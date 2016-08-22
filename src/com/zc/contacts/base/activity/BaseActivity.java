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
 * 所有avtivity的父类
 * 包含了跳转的函数
 */
public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected abstract void initview();
	
	/**
	 * 从一个界面直接跳转到另一个界面
	 * @param cla	跳转到哪个界面
	 */
	public void openActivity(Class<?> cla){
		//跳转到通讯录主界面
		Intent it = new Intent(this,cla);
		startActivity(it);
	}
	
	
	/**
	 * 从一个界面跳转到另一个界面
	 * @param act	从哪个界面开始跳转
	 * @param cla	跳转到哪个界面
	 */
	public void startActivity(Activity act,Class<?> cla){
		//跳转到通讯录主界面
		Intent it = new Intent(act,cla);
		startActivity(it);
		//销毁当前界面
		act.finish();
	}
	
	/**
	 * 从一个界面跳转到系统界面
	 * @param action	跳转到哪个系统界面
	 */
	public void startActivity(String action){
		Intent i = new Intent(action);
		startActivity(i);
	}	
	
	/**
	 * 从一个界面跳转到另一个界面,并且携带数据
	 * @param act	从哪个界面开始跳转
	 * @param cla	跳转到哪个界面
	 */
	public void startActivity(Class<?> cla,int position){
		//跳转到TelephonelistActivity界面
		Intent i = new Intent(this,cla);
		//由于到TelephonelistActivity中需要查询对应的表格中的数据，所以要将position传递过去
		i.putExtra("index", position);
		startActivity(i);
	}
	
	/**
	 * 界面跳转并且携带数据
	 * @param cls		跳转到哪个界面
	 * @param bundle	数据包
	 */
		public void openActivityExtra(Class<?> cla, Bundle bundle) {
			Intent it = new Intent(this, cla);
			it.putExtra("bundle", bundle);
			startActivity(it);
			//overridePendingTransition(R.anim.in, R.anim.exit);
		}
	
	
	
	/**
	 * 从一个界面跳转到另一个界面,并且带有动画效果
	 * @param act	从哪个界面开始跳转
	 * @param cla	跳转到哪个界面
	 */
	protected void startActicity(Class<?> cla,int in,int out){
		//跳转到通讯录主界面
			Intent it = new Intent(this,cla);
			/*
			 * 界面与界面之间切换，一个是进去的动画，一个是出来的动画（从上往下）
			 * 两个进去和出去的平移XML文件
			 * 出去：X轴的0%  到 0%   Y轴的0% 到 -200%
			 * 进去：X轴的0%  到 0%   Y轴的200%到0%
			 * 界面跳转动画必须加载在startActivity或者finish();后面
			 * 
			 * //当动画结束的时候调用的函数
				@Override
				public void onAnimationEnd(Animation animation) {
					startActicity(LogoActivity.this, TelephoneActivity.class,in,out);
				}
			 */
			overridePendingTransition(in, out);
			startActivity(it);
			//销毁当前界面
			this.finish();
	}
	
}
