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
 * ��׿�ܼ���������
 * @author Administrator
 *
 */
public class LeadActivity extends BaseActivity {
	private ViewPager mViewPager;//��������
	private BasePagerAdapter mBasePagerAdapter;
	private ImageView [] points = new ImageView [3];//����Բ��
	private TextView mtxt;//ֱ������������
	private Intent mMusicIntent;
	private String clsname;//��õ�����
	private SharedPreferences mshare;//�򵥱༭��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		//��ȡ��SettingActivity���洫�ݹ���������
		Bundle bun = getIntent().getBundleExtra("bundle");
		//�ж����ݰ��Ƿ�Ϊ��
		if (bun != null) {
			clsname =  bun.getString("classname");//�����ݰ��л�ȡString���͵�ֵ����ȡ���ݹ���������
		}
	    LogUtil.printlog("LeadActivity", clsname+"");//"null"
	    
	    
		
		//�ж��Ƿ��ǵ�һ�δ�Ӧ�ù�����   ����һ���ļ�������㣩	��������ģʽ���㵱ǰӦ�ó�����ܹ����ʣ�˽�е�
		 mshare = getSharedPreferences("first", Context.MODE_PRIVATE);
		boolean isfirst = mshare.getBoolean("isFirst", true);//Ĭ��false
		if (!isfirst) {//������ǵ�һ�ε�¼
			startActivity(this, LogoActivity.class);
		}else {//����ǵ�һ�ε�¼
			mMusicIntent = new Intent(this,MusicService.class);
			//�������񲥷�����
			startService(mMusicIntent);
			Editor editor = mshare.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();//�ύ
		}
		
		setContentView(R.layout.activity_lead);
		
		initview();
		
		//���������ü�������� ��ֱ����������ת��LogoActivity����
		mtxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (clsname != null && clsname.equals(SettingActivity.class.getSimpleName())) {
					//�����ͬ�Ĵ����ý�����������,�����������淵�����ý���
					LeadActivity.this.finish();
				}else {
					startActivity(LeadActivity.this, LogoActivity.class);
				}
			}
		});
		
		//��������ҳ�ļ���
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			//��ҳ���л���ʱ��
			@Override
			public void onPageSelected(int position) {
				LogUtil.printlog("onPageSelected", position+"");
				//����ͼƬ
				for (int x = 0; x < points.length; x++) {
					points[x].setImageResource(R.drawable.slide01);
				}
				points[position].setImageResource(R.drawable.slide02);
				//����������ʾ״̬
				if(position == points.length - 1){
					mtxt.setVisibility(View.VISIBLE);
				}else {
					mtxt.setVisibility(View.INVISIBLE);
				}
			}
			
			//��ҳ�����ڹ�����ʱ���ε���
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				LogUtil.printlog("onPageScrolled", arg0+","+arg1+","+arg2);
				
			}
			
			//������״̬�л���ʱ����ã���������ң�
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
		//��ȡ����ImageViewԲ�����
		points[0] = (ImageView) findViewById(R.id.vp_lead_left);
		points[1] = (ImageView) findViewById(R.id.vp_lead_middle);
		points[2] = (ImageView) findViewById(R.id.vp_lead_right);
		//��ȡ���ֶ���
		mtxt = (TextView) findViewById(R.id.txt_lead);
		
	}
	
	//����ҳ��������ֹͣ����Logo���棬�ڶ��ν���Ӧ�����ֲ��Ქ��
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
