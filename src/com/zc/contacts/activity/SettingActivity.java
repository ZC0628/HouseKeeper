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
 * 设置界面
 * @author Administrator
 *
 */
public class SettingActivity extends MyActionBarActivity {
	private ToggleButton mToggleButton;//开关
	private NotificationUtil mnotification;//发送通知和关闭通知的工具类

	private SharedPreferences mshare;//简单编辑器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initview();//初始化
		
		//当切换状态改变的时候（setOnCheckedChangeListener），点击通知按钮发送通知和关闭通知  
		//
		mToggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//buttonView——ToggleButton，		isChecked——按钮有没有处于打开状态，true-打开。false-关闭
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {//发送通知
					mnotification.showNotification(SettingActivity.this);
				}else {//关闭通知
					mnotification.closeNotification();
				}
				//保存通知开关状态
				mshare = getSharedPreferences("status", Context.MODE_PRIVATE);
				mshare.edit().putBoolean("isorno", isChecked).commit();
			}
		});
		
	}

	

	@Override
	protected void initview() {
		initActionBar(ActionBar.ID_NULL, getString(R.string.setting_title), R.drawable.returned, on);
		mshare = getSharedPreferences("status", Context.MODE_PRIVATE);
		//获取开关之前的记录状态
		boolean isChecked = mshare.getBoolean("isorno",false);
		
		mToggleButton = (ToggleButton) findViewById(R.id.setting_notification);
		//设置开关的状态
		mToggleButton.setChecked(isChecked);
		mnotification = new NotificationUtil(this);
	}
	
	
	//返回的监听
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingActivity.this.finish();
			}
		};
		
		
		public void doclick(View view){
			switch (view.getId()) {
			//帮助说明
			case R.id.setting_help:
				//点击跳转到引导界面，引导界面返回时返回到设置界面
				//创建数据包，传递数据包引导界面
				Bundle bundle = new Bundle();
				//SettingActivity.class.getSimpleName()   获取当前类的名称
				bundle.putString("classname",SettingActivity.class.getSimpleName());
				LogUtil.printlog("classname", SettingActivity.class.getSimpleName());
				//要进入引导界面需要修改文件中的值，改成true
				mshare = getSharedPreferences("first", Context.MODE_PRIVATE);
				mshare.edit().putBoolean("isFirst", true).commit();//存数据
				
				openActivityExtra(LeadActivity.class, bundle);
				break;
			//关于我们
			case R.id.setting_aboutus:
				openActivity(AboutActivity.class);
				break;
			}
			
		}
		
}
