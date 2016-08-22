package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.base.utils.MemoryUtil;
import com.zc.contacts.biz.MemoryManager;
import com.zc.contacts.view.ActionBar;
import com.zc.contacts.view.CircleMemory;

import android.os.Bundle;
import android.app.Activity;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 软件管理界面		
 * @author zc
 */
public class SoftwareManageActivity extends MyActionBarActivity {
	private TextView mphonememory,msdmemory;//获取手机可用内存和SD卡的可用内存
	private ProgressBar mphonePB,msdPB;//手机内存的进度条和SD卡的进度条
	private CircleMemory mcirclememory;//软件管理界面的饼图自绘

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_software_manage);
		
		initview();
		
		//获取手机内存和SD卡内存
		long phoneAll = MemoryManager.getphonememory();
		long phoneFree = MemoryManager.getphoneFreememory();
		long SDAll = MemoryManager.getSDmemory();
		long SDFree = MemoryManager.getSDFreememory();
		
		//用户函数字节转换——M		formatFileSize(context, number)文件的大小
		String phoneAllMB = Formatter.formatFileSize(this, phoneAll);
		String phoneFreeMB = Formatter.formatFileSize(this, phoneFree);
		String SDAllMB = Formatter.formatFileSize(this, SDAll);
		String SDFreeMB = Formatter.formatFileSize(this, SDFree);
		
		LogUtil.printlog("内存信息", phoneAllMB+"——"+phoneFreeMB+"——"+SDAllMB+"——"+SDFreeMB);
		
		//计算手机已用内存占用的角度
		float phonesweep = (phoneAll - phoneFree) / (float)(phoneAll+ SDAll) * 360;
		float sdsweep = (SDAll - SDFree) / (float)(phoneAll+ SDAll) * 360;
		//计算SD卡已用内存占用的角度
		mcirclememory.setsweep(phonesweep, sdsweep);
		
		//设置文字 
		mphonememory.setText("可用："+phoneFreeMB+"/"+phoneAllMB);
		msdmemory.setText("可用："+SDFreeMB+"/"+SDAllMB);
		//设置进度条 	
		 /*手机总内存减去剩余内存就是进度条上显示的内存
		*得出一个百分比  然后四舍五入*/
		 
		mphonePB.setProgress((int)Math.round((phoneAll - phoneFree) / (double)phoneAll*100));
		msdPB.setProgress((int)Math.round((SDAll - SDFree) / (double)SDAll*100));
		
		
	}
	
	

	@Override
	protected void initview() {
		initActionBar(R.drawable.returned, getString(R.string.activity_main_softwaremanage), ActionBar.ID_NULL, on);
		mphonememory = (TextView) findViewById(R.id.phonememory);
		msdmemory = (TextView) findViewById(R.id.sdmemory);
		mphonePB = (ProgressBar) findViewById(R.id.softwaremanage_PhoneprogressBar);
		msdPB = (ProgressBar) findViewById(R.id.softwaremanage_SDprogressBar);
		
		mcirclememory = (CircleMemory) findViewById(R.id.softwaremanage_circle);
	}

	//返回键的监听
	protected OnClickListener on = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SoftwareManageActivity.this.finish();
		}
	};
	
	
	//所有软件的按键监听
	public void allsoftware(View view){
		Bundle bundle = new Bundle();
		bundle.putString("ActionBarTitle", "所有软件");
		openActivityExtra(SoftwaremanageListActivity.class, bundle);
	}
	
	
	//系统软件的按键监听
	public void systemsoftware(View view){
		Bundle bundle = new Bundle();
		bundle.putString("ActionBarTitle", "系统软件");
		openActivityExtra(SoftwaremanageListActivity.class, bundle);
	}
	
	
	//用户软件的按键监听
	public void usersoftware(View view){
		Bundle bundle = new Bundle();
		bundle.putString("ActionBarTitle", "用户软件");
		openActivityExtra(SoftwaremanageListActivity.class, bundle);
		}
	
	
}
