package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.base.activity.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
/**
 * 用于显示通讯录logo界面
 * @author  zc
 *
 */
public class LogoActivity extends BaseActivity {
	private ImageView mlogo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		//Logo界面开始播放动画的图片对象
		mlogo = (ImageView) findViewById(R.id.logo_anime_picture);
		//TranslateAnimation  平移动画的函数
		//AlphaAnimation创建透明度动画对象，指定开始和结束的透明度
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setFillAfter(true);//保存最后动画结束的状态
		alpha.setDuration(3000);//动画持续的时间
		alpha.setRepeatCount(0);//重复播放次数
		//设置动画的监听   动画是否播放结束
		alpha.setAnimationListener(new AnimationListener() {
			
			//当动画开始
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			//当动画播放
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			//当动画结束的时候调用的函数  （logo界面跳转到主界面）
			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(LogoActivity.this, MainActivity.class);
			}
		});
		mlogo.startAnimation(alpha);//开始动画
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logo, menu);
		return true;
	}

	
	@Override
	protected void initview() {
		// TODO Auto-generated method stub
		
	}

}
