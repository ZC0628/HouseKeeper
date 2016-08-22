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
 * ��׿�ܼ�������
 * @author Administrator
 *
 */
public class MainActivity extends MyActionBarActivity {
	private AppInforsManager appinfors;
	private TextView point;//�ٷֱ�
	private MainCircle maincircle;
	private int temp;//��¼�Ƕ�,һ��ʼ�и��Ƕȴ���temp��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initview();
		
		//����
		 maincircle = (MainCircle) findViewById(R.id.Main_MainCircle);
		 temp = getpoint(360);
		 
		maincircle.setSweep(360,temp);//Ҫ��Ƕȵľͳ���360
		
		
		
	}
	
	//����������
	protected OnClickListener on = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.actionbar_left:
				//��ת�����ڽ���
				openActivity(AboutActivity.class);
				break;

			case R.id.actionbar_right:
				//��ת�����ý���
				openActivity(SettingActivity.class);
				break;
			}
		}
	};
	
	//���ͼƬ��ת��ĳĳ����
	public void doclick(View view){
		switch (view.getId()) {
		//�ֻ�����
		case R.id.main_phonespeed:
			openActivity(PhoneSpeedActivity.class);
			break;

		//�������
		case R.id.main_softwaremanage:
			openActivity(SoftwareManageActivity.class);
			break;
		
		//�ֻ����	
		case R.id.main_phonetest:
			openActivity(PhoneTestActivity.class);
			break;
		
		//ͨѶ��ȫ	
		case R.id.main_phonecall:
			openActivity(TelephoneActivity.class);
			break;	
		
		//�ļ�����	
		case R.id.main_filemanagee:
			openActivity(FileManageActivity.class);
			break;	
		
		//����������չ��
		case R.id.main_cleaner:
			openActivity(CleanerActivity.class);
			break;
		}
		
	}
	
	//�������ϵ��ֻ�����,����Ӧ�ó���
	public void cleanApp(View view){
		//��������Ӧ�ó���
		appinfors.cleanApp(this);
		LogUtil.printlog("temp", temp+"");
		maincircle.setSweep(temp,(temp = getpoint(360)));//Ҫ��Ƕȵľͳ���360
		//�����˴�ʹ���ʵİٷֱ�
		int result = getpoint(100);
		LogUtil.printlog("temp", temp+"");
		point.setText(result+"");
	}
	
	/**
	 * ����ٷֱ�
	 * @param bai  Ҫ��ٷֱȵľͳ���100
	 * @return
	 */
	private int getpoint(int bai) {
		//�����˴�ʹ���ʵİٷֱ�
		long allRAM = MemoryManager.getMaxRam();
		long useRAM = allRAM - MemoryManager.getFreeRam(this);
		//ת���ֽڵ�MB
//		String Aram = Formatter.formatFileSize(this, allRAM);
//		String Useram = Formatter.formatFileSize(this, useRAM);
		int result = Math.round((float)useRAM / allRAM * bai  );
		return result;
	}
	
	
	@Override
	protected void initview() {
		//����ActionBar���ҵ�ͼƬ���м������
		initActionBar(R.drawable.housekeeper,getString(R.string.main_title),R.drawable.setting,on);
		appinfors = new AppInforsManager(this);   
		
		point = (TextView) findViewById(R.id.main_number);//�ٷֱ�
		
		int result = getpoint(100);
		point.setText(result+"");
	}

}
