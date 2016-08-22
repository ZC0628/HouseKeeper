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
 * ������ʾͨѶ¼logo����
 * @author  zc
 *
 */
public class LogoActivity extends BaseActivity {
	private ImageView mlogo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		//Logo���濪ʼ���Ŷ�����ͼƬ����
		mlogo = (ImageView) findViewById(R.id.logo_anime_picture);
		//TranslateAnimation  ƽ�ƶ����ĺ���
		//AlphaAnimation����͸���ȶ�������ָ����ʼ�ͽ�����͸����
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setFillAfter(true);//������󶯻�������״̬
		alpha.setDuration(3000);//����������ʱ��
		alpha.setRepeatCount(0);//�ظ����Ŵ���
		//���ö����ļ���   �����Ƿ񲥷Ž���
		alpha.setAnimationListener(new AnimationListener() {
			
			//��������ʼ
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			//����������
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			//������������ʱ����õĺ���  ��logo������ת�������棩
			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(LogoActivity.this, MainActivity.class);
			}
		});
		mlogo.startAnimation(alpha);//��ʼ����
		
		
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
