package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.drawable;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.adapter.PhonetestAdapter;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.biz.MemoryManager;
import com.zc.contacts.biz.PhoneInforsManager;
import com.zc.contacts.entity.PhonetestInfor;
import com.zc.contacts.view.ActionBar;

import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
/**
 * 	手机检测
 * @author Administrator
 *
 */
public class PhoneTestActivity extends MyActionBarActivity {

	private int totalbattery,nowbattery,temperature;//总电量，当前电量，电池温度
	private BatteryReceiver mBatteryReceiver;//广播接收者
	private ProgressBar mBatteryproBar,mBatteryproBar2;//电池电量的进度条
	private TextView mBatteryText;//电池电量的百分比
	private ListView mListview;//电池信息下的列表
	private PhonetestAdapter mphonetestadapter;//手机检测适配器
	private ProgressBar mphonetestCircle;//圆形进度条
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_test);
		initview();
		
		getadapterinfor();//获取适配器中的数据
		
		
	}
	
	
	/**
	 *   获取适配器中的数据
	 */
	private void getadapterinfor() {
		new Thread(){
			public void run() {
				LogUtil.printlog("test", "1");
				//设备名称和版本号
				String devicename = PhoneInforsManager.getPhoneBrand();
				String deviceversion = PhoneInforsManager.getPhonVersion();
				Drawable icon1 = getResources().getDrawable(R.drawable.shebei);
				final PhonetestInfor infor1 = new PhonetestInfor(icon1, "设备名称："+devicename, "系统版本："+deviceversion);
				
				
				LogUtil.printlog("test", "2");
				//手机最大运存和可用运存
				String totalRam = Formatter.formatFileSize(PhoneTestActivity.this, MemoryManager.getMaxRam());
				String freeRam = Formatter.formatFileSize(PhoneTestActivity.this, MemoryManager.getFreeRam(PhoneTestActivity.this));
				Drawable icon2 = getResources().getDrawable(R.drawable.ram);
				final PhonetestInfor infor2 = new PhonetestInfor(icon2, "全部运行内存："+totalRam,  "剩余运行内存："+freeRam);
				
				
				LogUtil.printlog("test", "3");
				//CPU的名称和CPU的数量
				String CPUname = PhoneInforsManager.getCPUname();
				String CPUnumber = PhoneInforsManager.getCPUnumber()+"";
				Drawable icon3 = getResources().getDrawable(R.drawable.cpu);
				final PhonetestInfor infor3 = new PhonetestInfor(icon3, "CPU名称："+CPUname, "CPU数量："+CPUnumber);
				
				
				LogUtil.printlog("test", "4");
				//手机屏幕的分辨率和相机的分辨率
				String displayMetrics = PhoneInforsManager.getDisplayMetrics(PhoneTestActivity.this);
				String cameraMetrics = PhoneInforsManager.getcameraMetrics();
				Drawable icon4 = getResources().getDrawable(R.drawable.camera); 
				final PhonetestInfor infor4 = new PhonetestInfor(icon4, "手机分辨率："+displayMetrics, "相机分辨率："+cameraMetrics);
				
				
				LogUtil.printlog("test", "5");
				//基带版本 是否root
				String basebandversion = PhoneInforsManager.getBasebandversion();
				String isRoot = PhoneInforsManager.isRoot()?"是":"否";
				Drawable icon5 = getResources().getDrawable(R.drawable.root);
				final PhonetestInfor infor5 = new PhonetestInfor(icon5, "基带版本："+basebandversion, "是否Root:"+isRoot);
				
				
				//运行在主线程上
				LogUtil.printlog("test", "6");
				runOnUiThread(new Runnable() {
					
					public void run() {
						LogUtil.printlog("test", "7");
						mphonetestadapter.addDatatoadapter(infor1);
						mphonetestadapter.addDatatoadapter(infor2);
						mphonetestadapter.addDatatoadapter(infor3);
						mphonetestadapter.addDatatoadapter(infor4);
						mphonetestadapter.addDatatoadapter(infor5);
						
						//让进度条消失，Listview显示
						mphonetestCircle.setVisibility(View.INVISIBLE);
						mListview.setVisibility(View.VISIBLE);
						
						//更新UI
						mphonetestadapter.notifyDataSetChanged();
						LogUtil.printlog("test", "8");
					}
				});
			};
		}.start();
		
		
	}

	@Override
	protected void initview() {
		
		initActionBar(R.drawable.returned, getString(R.string.activity_main_phonetest), ActionBar.ID_NULL, on);
		mBatteryReceiver = new BatteryReceiver();
		//注册（启动）广播接收者是在avtivity中执行的
		registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		
		mBatteryproBar = (ProgressBar) findViewById(R.id.phonetest_progressbar);
		mBatteryproBar2 = (ProgressBar) findViewById(R.id.phonetest_progressbar2);
		mBatteryText = (TextView) findViewById(R.id.phonetest_battery_text);
		mListview = (ListView) findViewById(R.id.phonetest_listview);
		mphonetestCircle = (ProgressBar) findViewById(R.id.phonetest_progressBar_cilcle);
		mphonetestadapter = new PhonetestAdapter(this);
		
		
		mListview.setAdapter(mphonetestadapter);
	}

	
	//返回键的监听
			protected OnClickListener on = new OnClickListener() {
				@Override
				public void onClick(View v) {
					PhoneTestActivity.this.finish();
				}
			};
			
			
			
	//内部类——因为这个广播接收者是属于这个类的，所以也可以创建在本类中
		class BatteryReceiver extends BroadcastReceiver{

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
				//总电量
				 totalbattery = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
				//当前电量	
				 nowbattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				//电池温度
				 temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
				LogUtil.printlog("BatteryReceiver",totalbattery+","+ nowbattery+","+temperature);
				
				
				//设置文字和进度条
				mBatteryproBar.setMax(totalbattery);
				mBatteryproBar.setProgress(nowbattery);
				
				//设置电量的百分比
				mBatteryText.setText((float)nowbattery / totalbattery * 100 + "%");
				}
			}
		}
		
		//当点击电池显示对话框
		public void showbattery(View view){
			new AlertDialog.Builder(this)
			.setTitle("电池电量信息")
			/*
			 *设置对话框中的条目
			 *参数1：条目的内容文字
			 *参数2：条目点击的监听
			 */
			.setItems(new String []{"当前电量："+nowbattery,"电池温度："+temperature}, null).show();
		}
		
		
		
		
		//解除广播接收者
		@Override
		protected void onDestroy() {
			super.onDestroy();
			unregisterReceiver(mBatteryReceiver);
		}
		
}
