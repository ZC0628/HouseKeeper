package com.zc.contacts.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.adapter.ProgressInforsAdapter;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.ByteToMbUtil;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.biz.AppInforsManager;
import com.zc.contacts.biz.MemoryManager;
import com.zc.contacts.biz.PhoneInforsManager;
import com.zc.contacts.entity.ProgressInfors;
import com.zc.contacts.view.ActionBar;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhoneSpeedActivity extends MyActionBarActivity {
	private ListView mphonespeedLit;//手机加速中的列表
	private ProgressBar mphonespeedPrpBar,mCilclePrpBar;//进度条       圆形进度条
	private Button mOneClean,mShowProgress;//一键清理和只显示用户进程
	private TextView brand,version,memory;//型号，版本号，运存
	private CheckBox mCheckAll;//全选框
	private ProgressInforsAdapter mProInforAdapter;//进程适配器
	private boolean isSystem;//判断是否要显示系统进程
	private AppInforsManager appinforsmanager;//声明
	
	
	//创建Thread的话会start很多次，可能会出现重复开启的异常，所以 用Runnable
	private Runnable run = new Runnable(){
			//1.Thread与Handler配合	2.Thread与runOnUiThread();配合
			//创建线程执行耗时操作
				public void run() {
					AppInforsManager appInManer = new AppInforsManager(PhoneSpeedActivity.this);
					//获取数据源
					HashMap<Integer, ArrayList<ProgressInfors>> Map = appInManer.getRunningProcess(PhoneSpeedActivity.this);
					LogUtil.printlog("map", Map+"");
					
					if (isSystem) {//系统的
						//将获取到的数据源添加到适配器中       一开始默认的是用户的集合
						mProInforAdapter.addDatatoadapter(Map.get(AppInforsManager.USER_PROCESS));
					}else {//用户的
						mProInforAdapter.addDatatoadapter(Map.get(AppInforsManager.SYSTEM_PROCESS));
					}
					//该函数是执行在UI主线程上的
					runOnUiThread(new Runnable() {
						public void run() {
							//让进度条消失，让Listview显示
							mCilclePrpBar.setVisibility(View.INVISIBLE);
							mphonespeedLit.setVisibility(View.VISIBLE);
							//更新UI
							mProInforAdapter.notifyDataSetChanged();
							
							//重新设置内存文字和进度条
							setMemoryTextAndProgressBar();
						}
					});
					
				};
			};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_speed);
		initview();
		
		//监听Listview滚动状态
		mphonespeedLit.setOnScrollListener(new OnScrollListener() {
			//滚动状态改变的时候		scrollState——当前状态
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING://快速滑动中
					LogUtil.printlog("onScrollStateChanged", "快速滑动中");
					break;
				case OnScrollListener.SCROLL_STATE_IDLE://结束滑动
					LogUtil.printlog("onScrollStateChanged", "结束滑动");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://开始滑动
					LogUtil.printlog("onScrollStateChanged", "开始滑动");
					break;	
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
			}
		});
		
		
		//设置要显示用户的还是系统的
		mShowProgress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//先清空原来集合中的数据
				mProInforAdapter.clearAllData();
				
				AppInforsManager appInManer = new AppInforsManager(PhoneSpeedActivity.this);
				//获取数据源
				HashMap<Integer, ArrayList<ProgressInfors>> Map = appInManer.getRunningProcess(PhoneSpeedActivity.this);
				if (isSystem) {
					isSystem = false;//不是系统 的就添加用户的
					//isSystem标记一改 ，Runnable中就会进行判断是否是系统进程
					mShowProgress.setText("只显示用户进程");
				}else {
					isSystem = true;//如果是系统的，添加系统进程
					mShowProgress.setText("只显示系统进程");
				}
				//让进度条显示，让Listview消失
				mCilclePrpBar.setVisibility(View.VISIBLE);
				mphonespeedLit.setVisibility(View.INVISIBLE);
				//新建一个线程然后开启
				new Thread(run).start();
				//更新UI
				mProInforAdapter.notifyDataSetChanged();
			}
		});
		
		//设置多选框的监听（ 全选和取消）
		mCheckAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//isChecked——是否被选中
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//首先获取适配器		因为返回的是父类，所以要强制类型转换成子类
				ProgressInforsAdapter adapter = (ProgressInforsAdapter) mphonespeedLit.getAdapter();
				//获取数据源
				ArrayList<ProgressInfors> ProInfors = adapter.getitemInfos();
				for (ProgressInfors progressInfors : ProInfors) {
					//设置选中状态——如果是选中状态的就全部设置成选中
					progressInfors.ischecked = isChecked;
				}
				//通知适配器更新UI(该函数会调用适配器中的getview函数)
				mProInforAdapter.notifyDataSetChanged();
			}
		});
		
		//一键清理
		mOneClean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//由于杀死进程需要用到ActivityManager，所以先获取对象
				appinforsmanager  = new AppInforsManager(PhoneSpeedActivity.this);
				ActivityManager ActManager = appinforsmanager.getActivityManager(PhoneSpeedActivity.this);
				//循环数据源集合，判断哪些应用程序进程是需要杀死的
				ArrayList<ProgressInfors>  infors = mProInforAdapter.getitemInfos();
				for (int x = 0; x < infors.size(); x++) {
					//判断：如果集合中的数据的属性是被选中的，就是要杀死的进程
					//杀死进程需要在清单文件中添加权限 
					if (infors.get(x).ischecked) {
						ActManager.killBackgroundProcesses(infors.get(x).PackageName);
					}
					
				}
				//一键清理之后清空适配器中的数据源,然后通过线程重新获取一次
				mProInforAdapter.clearAllData();
				//显示进度条和列表消失
				mphonespeedPrpBar.setVisibility(View.VISIBLE);
				mphonespeedLit.setVisibility(View.INVISIBLE);
				
				//应用程序杀死后，还需要更新列表（重新获取正在运行的应用程序）
				new Thread(run).start();
			}
		});
		
	}

	@Override
	protected void initview() {
		initActionBar(R.drawable.returned, getString(R.string.activity_main_phonespeed), ActionBar.ID_NULL, on);
		mphonespeedPrpBar = (ProgressBar) findViewById(R.id.phonespeed_progressbar);
		mOneClean = (Button) findViewById(R.id.phonespeed_onechean);
		mShowProgress = (Button) findViewById(R.id.phonespeed_listview_showprogress);
		brand = (TextView) findViewById(R.id.phonespeed_brand);
		version = (TextView) findViewById(R.id.phonespeed_version);
		memory = (TextView) findViewById(R.id.phonespeed_memory);
		mCheckAll = (CheckBox) findViewById(R.id.phonespeed_listview_checkAll);
		mphonespeedLit = (ListView) findViewById(R.id.phonespeed_listview);
		mCilclePrpBar = (ProgressBar) findViewById(R.id.phonespeed_progressBar_cilcle);
		
		mProInforAdapter = new ProgressInforsAdapter(this);
		mphonespeedLit.setAdapter(mProInforAdapter);//加载适配器
		
		//设置品牌/型号/版本号
		brand.setText(PhoneInforsManager.getPhoneBrand());
		version.setText(PhoneInforsManager.getPhoneModel()+" Android "+PhoneInforsManager.getPhonVersion());
		//设置内存文字和进度条
		setMemoryTextAndProgressBar();
		//新建一个线程然后开启
		new Thread(run).start();
	}
	
	/**
	 * 设置内存文字和进度条
	 */
	private void setMemoryTextAndProgressBar() {
		//设置内存文字
		double freeRam = ByteToMbUtil.byteTomb(MemoryManager.getFreeRam(this));//空闲运存
		double maxRam = ByteToMbUtil.byteTomb(MemoryManager.getMaxRam());//手机最大运存
		//获取已用内存
		int useRam = (int) Math.round((maxRam - freeRam) / maxRam * 100);
		memory.setText("已用运存："+ useRam + "MB /" + maxRam + "MB");
				
		//设置进度条进度
		mphonespeedPrpBar.setProgress((int)((maxRam - freeRam)/maxRam * 100));
	}

	//返回键的监听
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				PhoneSpeedActivity.this.finish();
			}
		};

}
