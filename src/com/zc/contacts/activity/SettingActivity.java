package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.base.utils.NotificationUtil;
import com.zc.contacts.view.ActionBar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;
/**
 * ���ý���
 * @author Administrator
 *
 */
public class SettingActivity extends MyActionBarActivity {
	private ToggleButton mToggleButton;//����
	private NotificationUtil mnotification;//����֪ͨ�͹ر�֪ͨ�Ĺ�����

	private SharedPreferences mshare;//�򵥱༭��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initview();//��ʼ��
		
		//���л�״̬�ı��ʱ��setOnCheckedChangeListener�������֪ͨ��ť����֪ͨ�͹ر�֪ͨ  
		//
		mToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//buttonView����ToggleButton��		isChecked������ť��û�д��ڴ�״̬��true-�򿪡�false-�ر�
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {//����֪ͨ
					mnotification.showNotification(SettingActivity.this);
				}else {//�ر�֪ͨ
					mnotification.closeNotification();
				}
				//����֪ͨ����״̬
				mshare = getSharedPreferences("status", Context.MODE_PRIVATE);
				mshare.edit().putBoolean("isorno", isChecked).commit();
			}
		});
		
	}

	

	@Override
	protected void initview() {
		initActionBar(ActionBar.ID_NULL, getString(R.string.setting_title), R.drawable.returned, on);
		mshare = getSharedPreferences("status", Context.MODE_PRIVATE);
		//��ȡ����֮ǰ�ļ�¼״̬
		boolean isChecked = mshare.getBoolean("isorno",false);
		
		mToggleButton = (ToggleButton) findViewById(R.id.setting_notification);
		//���ÿ��ص�״̬
		mToggleButton.setChecked(isChecked);
		mnotification = new NotificationUtil(this);
	}
	
	
	//���صļ���
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingActivity.this.finish();
			}
		};
		
		
		public void doclick(View view){
			switch (view.getId()) {
			//����˵��
			case R.id.setting_help:
				//�����ת���������棬�������淵��ʱ���ص����ý���
				//�������ݰ����������ݰ���������
				Bundle bundle = new Bundle();
				//SettingActivity.class.getSimpleName()   ��ȡ��ǰ�������
				bundle.putString("classname",SettingActivity.class.getSimpleName());
				LogUtil.printlog("classname", SettingActivity.class.getSimpleName());
				//Ҫ��������������Ҫ�޸��ļ��е�ֵ���ĳ�true
				mshare = getSharedPreferences("first", Context.MODE_PRIVATE);
				mshare.edit().putBoolean("isFirst", true).commit();//������
				
				openActivityExtra(LeadActivity.class, bundle);
				break;
			//��������
			case R.id.setting_aboutus:
				openActivity(AboutActivity.class);
				break;
			}
			
		}
		
}
