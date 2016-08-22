package com.zc.contacts.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.base.activity.BaseActivity;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.ByteToMbUtil;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.biz.AppInforsManager;
import com.zc.contacts.biz.MemoryManager;
import com.zc.contacts.entity.ProgressInfors;
import com.zc.contacts.view.ActionBar;
import com.zc.contacts.view.MainCircle;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 安卓管家主界面
 * @author Administrator
 *
 */
public class MainActivity extends MyActionBarActivity {
	private AppInforsManager appinfors;
	private TextView point;//百分比
	private MainCircle maincircle;
	private int temp;//记录角度,一开始有个角度存在temp中

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initview();
		
		//测试
		 maincircle = (MainCircle) findViewById(R.id.Main_MainCircle);
		 temp = getpoint(360);
		 
		maincircle.setSweep(360,temp);//要求角度的就乘以360
		
		
		
	}
	
	//导航条监听
	protected OnClickListener on = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.actionbar_left:
				//跳转到关于界面
				openActivity(AboutActivity.class);
				break;

			case R.id.actionbar_right:
				//跳转到设置界面
				openActivity(SettingActivity.class);
				break;
			}
		}
	};
	
	//点击图片跳转到某某界面
	public void doclick(View view){
		switch (view.getId()) {
		//手机加速
		case R.id.main_phonespeed:
			openActivity(PhoneSpeedActivity.class);
			break;

		//软件管理
		case R.id.main_softwaremanage:
			openActivity(SoftwareManageActivity.class);
			break;
		
		//手机检测	
		case R.id.main_phonetest:
			openActivity(PhoneTestActivity.class);
			break;
		
		//通讯大全	
		case R.id.main_phonecall:
			openActivity(TelephoneActivity.class);
			break;	
		
		//文件管理	
		case R.id.main_filemanagee:
			openActivity(FileManageActivity.class);
			break;	
		
		//垃圾清理（扩展）
		case R.id.main_cleaner:
			openActivity(CleanerActivity.class);
			break;
		}
		
	}
	
	//主界面上的手机加速,清理应用程序
	public void cleanApp(View view){
		//清理所有应用程序
		appinfors.cleanApp(this);
		LogUtil.printlog("temp", temp+"");
		maincircle.setSweep(temp,(temp = getpoint(360)));//要求角度的就乘以360
		//计算运存使用率的百分比
		int result = getpoint(100);
		LogUtil.printlog("temp", temp+"");
		point.setText(result+"");
	}
	
	/**
	 * 计算百分比
	 * @param bai  要求百分比的就乘以100
	 * @return
	 */
	private int getpoint(int bai) {
		//计算运存使用率的百分比
		long allRAM = MemoryManager.getMaxRam();
		long useRAM = allRAM - MemoryManager.getFreeRam(this);
		//转换字节到MB
//		String Aram = Formatter.formatFileSize(this, allRAM);
//		String Useram = Formatter.formatFileSize(this, useRAM);
		int result = Math.round((float)useRAM / allRAM * bai  );
		return result;
	}
	
	
	@Override
	protected void initview() {
		//设置ActionBar左右的图片和中间的文字
		initActionBar(R.drawable.housekeeper,getString(R.string.main_title),R.drawable.setting,on);
		appinfors = new AppInforsManager(this);   
		
		point = (TextView) findViewById(R.id.main_number);//百分比
		
		int result = getpoint(100);
		point.setText(result+"");
	}

}
