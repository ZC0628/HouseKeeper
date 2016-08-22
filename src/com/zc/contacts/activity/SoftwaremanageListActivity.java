package com.zc.contacts.activity;

import java.util.ArrayList;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.adapter.AppinforsAdapter;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.biz.AppInforsManager;
import com.zc.contacts.entity.AppInfors;
import com.zc.contacts.receiver.AppdeleteReceiver;
import com.zc.contacts.receiver.AppdeleteReceiver.MyDataListener;
import com.zc.contacts.view.ActionBar;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
/**
 * 所有软件 —— 系统软件 —— 用户软件
 * @author Administrator
 *
 */
public class SoftwaremanageListActivity extends MyActionBarActivity implements MyDataListener{
	private ProgressBar mCircleBar;//圆形进度条
	private ListView mAppitem;//应用程序列表
	private AppinforsAdapter mAppAdapter;//应用程序列表的适配器
	private AppInforsManager mappmanager;//应用程序信息管理器
	private String title;//标题（所有软件，系统软件，用户软件）
	private CheckBox mAllCheck;//全选框
	private Button mdeleate;//卸载所选软件的按钮
	private AppdeleteReceiver mBcRece;//广播接收者
	
	
	//Handler类 ——在安卓中主要用于发送信息以及接受和处理信息（频繁）
	private Handler mhandler = new Handler(){
		/*
		 * 为了处理消息——创建Handler的子类对象，重写Handler里面的函数 ——handleMessage(non-Javadoc)
		 * 参数：android.os.Message —— 一个消息对象
		 * 该函数在UI线程（主线程）中执行
		 */
		public void handleMessage(android.os.Message msg) {
			//打开消息获得里面的内容
			if (msg.what == 1) {
				//安卓是不允许在子线程中更新UI（用户界面）
				mCircleBar.setVisibility(View.INVISIBLE);//旋转两秒之后进度条消失
				//让ListView显示
				mAppitem.setVisibility(View.VISIBLE);
				//根据标题title进行判断
				if (title.equals("所有软件")) {
					//将数据放入适配器
					mAppAdapter.addDatatoadapter(mappmanager.getAllpackageinfors());
				}else if (title.equals("系统软件")) {
					mAppAdapter.addDatatoadapter(mappmanager.getSystempackageinfors());
				}else {
					mAppAdapter.addDatatoadapter(mappmanager.getUserpackageinfors());
				}
				mAppitem.setAdapter(mAppAdapter);//加载完数据后设置适配器
				
			}
		};
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_softwaremanage_list);
		initview();
		
		getData();
		
		
		/*//进度条旋转两秒后消失 ————  错误的！！
		//android中不允许主线程睡眠（相当于线程卡住，睡眠吗5秒会出现ANR——无响应异常）不能执行接下来的内容
		try {
			Thread.sleep(2000);
			mCircleBar.setVisibility(View.INVISIBLE);//旋转两秒之后进度条消失
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
			//当全选按钮选中状态改变的时候	
				mAllCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					//isChecked——是否被选中
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						//首先获取适配器		因为返回的是父类，所以要强制类型转换成子类
						AppinforsAdapter adapter = (AppinforsAdapter) mAppitem.getAdapter();
						//获取到数据源
						ArrayList<AppInfors> appinfor = adapter.getitemInfos();
							for (AppInfors appInfors : appinfor) {
								//设置选中状态——如果是选中状态的就全部设置成选中
								appInfors.ischecked = isChecked;
							}
							//通知适配器更新UI
							mAppAdapter.notifyDataSetChanged();
					}
				});
				
				//点击卸载所选软件按钮		
				mdeleate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//首先获取哪些应用需要删除
						//获取适配器		因为返回的是父类，所以要强制类型转换成子类
						AppinforsAdapter adapter = (AppinforsAdapter) mAppitem.getAdapter();
						//获取到数据源
						ArrayList<AppInfors> appinfor = adapter.getitemInfos();
						for (AppInfors appInfors : appinfor) {
							if (appInfors.ischecked) {//选中状态
								//隐式意图跳转到安卓自带的界面
								Intent it = new Intent(Intent.ACTION_DELETE);//卸载
								//告诉系统要删除的应用包名
								it.setData(Uri.parse("package:"+appInfors.packagename));
								//跳转到安卓删除界面后不一定会删除，所有在这里不能随意的删除对象
								startActivity(it);
							}
						}
						//删除软件之后更新UI
						//当适配器的数据源内容更新，则适配器中的getview函数会被重新调用
						//mAppAdapter.notifyDataSetChanged();
						
						//清空所有数据
						mAppAdapter.clearAllData();
					}
				});
				
				
	}


	//数据源
	private void getAppData() {
		/**
		 * 线程和Handler的组合，线程发送消息，Handler接受消息以及处理消息
		 */
		new Thread(){
			public void run() {
				//从手机中获取应用程序信息
				mappmanager = new AppInforsManager(SoftwaremanageListActivity.this);
				mappmanager.addAllpackageinfors();
				
				//发送一个空的消息，可携带一个参数
				mhandler.sendEmptyMessage(1);
				
				mAppAdapter.notifyDataSetChanged();
			};
		}.start();
	}

	

	@Override
	protected void initview() {
		Bundle bundle = getIntent().getBundleExtra("bundle");
		 title = bundle.getString("ActionBarTitle");
		//导航条
		initActionBar(R.drawable.returned, title, ActionBar.ID_NULL, on);
		
		mCircleBar = (ProgressBar) findViewById(R.id.softwaremanage_progressBar_cilcle);//初始化圆形进度条
		mAppitem = (ListView) findViewById(R.id.softwaremanage_butttom_listview);//初始化应用程序列表
		//获取全选框
		mAllCheck = (CheckBox) findViewById(R.id.sofewaremanage_list_checkAll);
		//卸载所选软件的按钮
		mdeleate = (Button) findViewById(R.id.softwaremanage_listview_delete);
		//接收SoftwareManageActivity传递过来的标记
		mAppAdapter = new AppinforsAdapter(this);
		//创建广播
		mBcRece = new AppdeleteReceiver();
		mBcRece.setMyDataListener(this);
		//注册广播接收者registerReceiver(receiver, filter)。。receiver——广播接收者的对象。filter——调整的频道
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
		//添加一个协议，必须的
		filter.addDataScheme("package");
		//调整频道(调整成要删除的频道)与打开广播 ————动态注册方式，在代码中注册和注销广播
		registerReceiver(mBcRece, filter);
		
	}
	
	
	//返回的监听
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				SoftwaremanageListActivity.this.finish();
			}
		};
		
		
		//注销广播
		@Override
		protected void onDestroy() {
			super.onDestroy();
			unregisterReceiver(mBcRece);
		}


		//接口是可以传递数据的
		@Override
		public void getData() {
			//调用getAppData函数
			getAppData();
		};
		
}
