package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.base.activity.BaseActivity;
import com.zc.contacts.base.adapter.BasePagerAdapter;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.service.MusicService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 安卓管家引导界面
 * @author Administrator
 *
 */
public class LeadActivity extends BaseActivity {
	private ViewPager mViewPager;//滑动界面
	private BasePagerAdapter mBasePagerAdapter;
	private ImageView [] points = new ImageView [3];//三个圆点
	private TextView mtxt;//直接跳过的文字
	private Intent mMusicIntent;
	private String clsname;//获得的数据
	private SharedPreferences mshare;//简单编辑器
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		//获取从SettingActivity界面传递过来的数据
		Bundle bun = getIntent().getBundleExtra("bundle");
		//判断数据包是否为空
		if (bun != null) {
			clsname =  bun.getString("classname");//从数据包中获取String类型的值，获取传递过来的数据
		}
	    LogUtil.printlog("LeadActivity", clsname+"");//"null"
	    
	    
		
		//判断是否是第一次打开应用过程序   参数一：文件名（随便）	参数二：模式，你当前应用程序才能够访问，私有的
		 mshare = getSharedPreferences("first", Context.MODE_PRIVATE);
		boolean isfirst = mshare.getBoolean("isFirst", true);//默认false
		if (!isfirst) {//如果不是第一次登录
			startActivity(this, LogoActivity.class);
		}else {//如果是第一次登录
			mMusicIntent = new Intent(this,MusicService.class);
			//启动服务播放音乐
			startService(mMusicIntent);
			Editor editor = mshare.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();//提交
		}
		
		setContentView(R.layout.activity_lead);
		
		initview();
		
		//给文字设置监听，点击 “直接跳过”跳转到LogoActivity界面
		mtxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (clsname != null && clsname.equals(SettingActivity.class.getSimpleName())) {
					//如果相同的从设置界面跳过来的,结束引导界面返回设置界面
					LeadActivity.this.finish();
				}else {
					startActivity(LeadActivity.this, LogoActivity.class);
				}
			}
		});
		
		//设置引导页的监听
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			//当页面切换的时候
			@Override
			public void onPageSelected(int position) {
				LogUtil.printlog("onPageSelected", position+"");
				//设置图片
				for (int x = 0; x < points.length; x++) {
					points[x].setImageResource(R.drawable.slide01);
				}
				points[position].setImageResource(R.drawable.slide02);
				//设置文字显示状态
				if(position == points.length - 1){
					mtxt.setVisibility(View.VISIBLE);
				}else {
					mtxt.setVisibility(View.INVISIBLE);
				}
			}
			
			//当页面正在滚动的时候多次调用
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				LogUtil.printlog("onPageScrolled", arg0+","+arg1+","+arg2);
				
			}
			
			//当滚动状态切换的时候调用（向左和向右）
			@Override
			public void onPageScrollStateChanged(int position) {
				LogUtil.printlog("onPageScrollStateChanged", position+"");
			}
		});
		mViewPager.setAdapter(mBasePagerAdapter);
	}

	
	@Override
	protected void initview() {
		mViewPager = (ViewPager) findViewById(R.id.vp_lead);
		mBasePagerAdapter = new BasePagerAdapter(this);
		//获取三个ImageView圆点对象
		points[0] = (ImageView) findViewById(R.id.vp_lead_left);
		points[1] = (ImageView) findViewById(R.id.vp_lead_middle);
		points[2] = (ImageView) findViewById(R.id.vp_lead_right);
		//获取文字对象
		mtxt = (TextView) findViewById(R.id.txt_lead);
		
	}
	
	//引导页结束音乐停止进入Logo界面，第二次进入应用音乐不会播放
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mMusicIntent != null) {
			stopService(mMusicIntent);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lead, menu);
		return true;
	}
	
}
