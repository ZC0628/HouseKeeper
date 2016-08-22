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
 * 	�ֻ����
 * @author Administrator
 *
 */
public class PhoneTestActivity extends MyActionBarActivity {

	private int totalbattery,nowbattery,temperature;//�ܵ�������ǰ����������¶�
	private BatteryReceiver mBatteryReceiver;//�㲥������
	private ProgressBar mBatteryproBar,mBatteryproBar2;//��ص����Ľ�����
	private TextView mBatteryText;//��ص����İٷֱ�
	private ListView mListview;//�����Ϣ�µ��б�
	private PhonetestAdapter mphonetestadapter;//�ֻ����������
	private ProgressBar mphonetestCircle;//Բ�ν�����
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_test);
		initview();
		
		getadapterinfor();//��ȡ�������е�����
		
		
	}
	
	
	/**
	 *   ��ȡ�������е�����
	 */
	private void getadapterinfor() {
		new Thread(){
			public void run() {
				LogUtil.printlog("test", "1");
				//�豸���ƺͰ汾��
				String devicename = PhoneInforsManager.getPhoneBrand();
				String deviceversion = PhoneInforsManager.getPhonVersion();
				Drawable icon1 = getResources().getDrawable(R.drawable.shebei);
				final PhonetestInfor infor1 = new PhonetestInfor(icon1, "�豸���ƣ�"+devicename, "ϵͳ�汾��"+deviceversion);
				
				
				LogUtil.printlog("test", "2");
				//�ֻ�����˴�Ϳ����˴�
				String totalRam = Formatter.formatFileSize(PhoneTestActivity.this, MemoryManager.getMaxRam());
				String freeRam = Formatter.formatFileSize(PhoneTestActivity.this, MemoryManager.getFreeRam(PhoneTestActivity.this));
				Drawable icon2 = getResources().getDrawable(R.drawable.ram);
				final PhonetestInfor infor2 = new PhonetestInfor(icon2, "ȫ�������ڴ棺"+totalRam,  "ʣ�������ڴ棺"+freeRam);
				
				
				LogUtil.printlog("test", "3");
				//CPU�����ƺ�CPU������
				String CPUname = PhoneInforsManager.getCPUname();
				String CPUnumber = PhoneInforsManager.getCPUnumber()+"";
				Drawable icon3 = getResources().getDrawable(R.drawable.cpu);
				final PhonetestInfor infor3 = new PhonetestInfor(icon3, "CPU���ƣ�"+CPUname, "CPU������"+CPUnumber);
				
				
				LogUtil.printlog("test", "4");
				//�ֻ���Ļ�ķֱ��ʺ�����ķֱ���
				String displayMetrics = PhoneInforsManager.getDisplayMetrics(PhoneTestActivity.this);
				String cameraMetrics = PhoneInforsManager.getcameraMetrics();
				Drawable icon4 = getResources().getDrawable(R.drawable.camera); 
				final PhonetestInfor infor4 = new PhonetestInfor(icon4, "�ֻ��ֱ��ʣ�"+displayMetrics, "����ֱ��ʣ�"+cameraMetrics);
				
				
				LogUtil.printlog("test", "5");
				//�����汾 �Ƿ�root
				String basebandversion = PhoneInforsManager.getBasebandversion();
				String isRoot = PhoneInforsManager.isRoot()?"��":"��";
				Drawable icon5 = getResources().getDrawable(R.drawable.root);
				final PhonetestInfor infor5 = new PhonetestInfor(icon5, "�����汾��"+basebandversion, "�Ƿ�Root:"+isRoot);
				
				
				//���������߳���
				LogUtil.printlog("test", "6");
				runOnUiThread(new Runnable() {
					
					public void run() {
						LogUtil.printlog("test", "7");
						mphonetestadapter.addDatatoadapter(infor1);
						mphonetestadapter.addDatatoadapter(infor2);
						mphonetestadapter.addDatatoadapter(infor3);
						mphonetestadapter.addDatatoadapter(infor4);
						mphonetestadapter.addDatatoadapter(infor5);
						
						//�ý�������ʧ��Listview��ʾ
						mphonetestCircle.setVisibility(View.INVISIBLE);
						mListview.setVisibility(View.VISIBLE);
						
						//����UI
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
		//ע�ᣨ�������㲥����������avtivity��ִ�е�
		registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		
		mBatteryproBar = (ProgressBar) findViewById(R.id.phonetest_progressbar);
		mBatteryproBar2 = (ProgressBar) findViewById(R.id.phonetest_progressbar2);
		mBatteryText = (TextView) findViewById(R.id.phonetest_battery_text);
		mListview = (ListView) findViewById(R.id.phonetest_listview);
		mphonetestCircle = (ProgressBar) findViewById(R.id.phonetest_progressBar_cilcle);
		mphonetestadapter = new PhonetestAdapter(this);
		
		
		mListview.setAdapter(mphonetestadapter);
	}

	
	//���ؼ��ļ���
			protected OnClickListener on = new OnClickListener() {
				@Override
				public void onClick(View v) {
					PhoneTestActivity.this.finish();
				}
			};
			
			
			
	//�ڲ��ࡪ����Ϊ����㲥�����������������ģ�����Ҳ���Դ����ڱ�����
		class BatteryReceiver extends BroadcastReceiver{

			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){
				//�ܵ���
				 totalbattery = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
				//��ǰ����	
				 nowbattery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				//����¶�
				 temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
				LogUtil.printlog("BatteryReceiver",totalbattery+","+ nowbattery+","+temperature);
				
				
				//�������ֺͽ�����
				mBatteryproBar.setMax(totalbattery);
				mBatteryproBar.setProgress(nowbattery);
				
				//���õ����İٷֱ�
				mBatteryText.setText((float)nowbattery / totalbattery * 100 + "%");
				}
			}
		}
		
		//����������ʾ�Ի���
		public void showbattery(View view){
			new AlertDialog.Builder(this)
			.setTitle("��ص�����Ϣ")
			/*
			 *���öԻ����е���Ŀ
			 *����1����Ŀ����������
			 *����2����Ŀ����ļ���
			 */
			.setItems(new String []{"��ǰ������"+nowbattery,"����¶ȣ�"+temperature}, null).show();
		}
		
		
		
		
		//����㲥������
		@Override
		protected void onDestroy() {
			super.onDestroy();
			unregisterReceiver(mBatteryReceiver);
		}
		
}
