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
	private ListView mphonespeedLit;//�ֻ������е��б�
	private ProgressBar mphonespeedPrpBar,mCilclePrpBar;//������       Բ�ν�����
	private Button mOneClean,mShowProgress;//һ�������ֻ��ʾ�û�����
	private TextView brand,version,memory;//�ͺţ��汾�ţ��˴�
	private CheckBox mCheckAll;//ȫѡ��
	private ProgressInforsAdapter mProInforAdapter;//����������
	private boolean isSystem;//�ж��Ƿ�Ҫ��ʾϵͳ����
	private AppInforsManager appinforsmanager;//����
	
	
	//����Thread�Ļ���start�ܶ�Σ����ܻ�����ظ��������쳣������ ��Runnable
	private Runnable run = new Runnable(){
			//1.Thread��Handler���	2.Thread��runOnUiThread();���
			//�����߳�ִ�к�ʱ����
				public void run() {
					AppInforsManager appInManer = new AppInforsManager(PhoneSpeedActivity.this);
					//��ȡ����Դ
					HashMap<Integer, ArrayList<ProgressInfors>> Map = appInManer.getRunningProcess(PhoneSpeedActivity.this);
					LogUtil.printlog("map", Map+"");
					
					if (isSystem) {//ϵͳ��
						//����ȡ��������Դ��ӵ���������       һ��ʼĬ�ϵ����û��ļ���
						mProInforAdapter.addDatatoadapter(Map.get(AppInforsManager.USER_PROCESS));
					}else {//�û���
						mProInforAdapter.addDatatoadapter(Map.get(AppInforsManager.SYSTEM_PROCESS));
					}
					//�ú�����ִ����UI���߳��ϵ�
					runOnUiThread(new Runnable() {
						public void run() {
							//�ý�������ʧ����Listview��ʾ
							mCilclePrpBar.setVisibility(View.INVISIBLE);
							mphonespeedLit.setVisibility(View.VISIBLE);
							//����UI
							mProInforAdapter.notifyDataSetChanged();
							
							//���������ڴ����ֺͽ�����
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
		
		//����Listview����״̬
		mphonespeedLit.setOnScrollListener(new OnScrollListener() {
			//����״̬�ı��ʱ��		scrollState������ǰ״̬
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_FLING://���ٻ�����
					LogUtil.printlog("onScrollStateChanged", "���ٻ�����");
					break;
				case OnScrollListener.SCROLL_STATE_IDLE://��������
					LogUtil.printlog("onScrollStateChanged", "��������");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://��ʼ����
					LogUtil.printlog("onScrollStateChanged", "��ʼ����");
					break;	
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
			}
		});
		
		
		//����Ҫ��ʾ�û��Ļ���ϵͳ��
		mShowProgress.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//�����ԭ�������е�����
				mProInforAdapter.clearAllData();
				
				AppInforsManager appInManer = new AppInforsManager(PhoneSpeedActivity.this);
				//��ȡ����Դ
				HashMap<Integer, ArrayList<ProgressInfors>> Map = appInManer.getRunningProcess(PhoneSpeedActivity.this);
				if (isSystem) {
					isSystem = false;//����ϵͳ �ľ�����û���
					//isSystem���һ�� ��Runnable�оͻ�����ж��Ƿ���ϵͳ����
					mShowProgress.setText("ֻ��ʾ�û�����");
				}else {
					isSystem = true;//�����ϵͳ�ģ����ϵͳ����
					mShowProgress.setText("ֻ��ʾϵͳ����");
				}
				//�ý�������ʾ����Listview��ʧ
				mCilclePrpBar.setVisibility(View.VISIBLE);
				mphonespeedLit.setVisibility(View.INVISIBLE);
				//�½�һ���߳�Ȼ����
				new Thread(run).start();
				//����UI
				mProInforAdapter.notifyDataSetChanged();
			}
		});
		
		//���ö�ѡ��ļ����� ȫѡ��ȡ����
		mCheckAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			//isChecked�����Ƿ�ѡ��
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//���Ȼ�ȡ������		��Ϊ���ص��Ǹ��࣬����Ҫǿ������ת��������
				ProgressInforsAdapter adapter = (ProgressInforsAdapter) mphonespeedLit.getAdapter();
				//��ȡ����Դ
				ArrayList<ProgressInfors> ProInfors = adapter.getitemInfos();
				for (ProgressInfors progressInfors : ProInfors) {
					//����ѡ��״̬���������ѡ��״̬�ľ�ȫ�����ó�ѡ��
					progressInfors.ischecked = isChecked;
				}
				//֪ͨ����������UI(�ú���������������е�getview����)
				mProInforAdapter.notifyDataSetChanged();
			}
		});
		
		//һ������
		mOneClean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//����ɱ��������Ҫ�õ�ActivityManager�������Ȼ�ȡ����
				appinforsmanager  = new AppInforsManager(PhoneSpeedActivity.this);
				ActivityManager ActManager = appinforsmanager.getActivityManager(PhoneSpeedActivity.this);
				//ѭ������Դ���ϣ��ж���ЩӦ�ó����������Ҫɱ����
				ArrayList<ProgressInfors>  infors = mProInforAdapter.getitemInfos();
				for (int x = 0; x < infors.size(); x++) {
					//�жϣ���������е����ݵ������Ǳ�ѡ�еģ�����Ҫɱ���Ľ���
					//ɱ��������Ҫ���嵥�ļ������Ȩ�� 
					if (infors.get(x).ischecked) {
						ActManager.killBackgroundProcesses(infors.get(x).PackageName);
					}
					
				}
				//һ������֮������������е�����Դ,Ȼ��ͨ���߳����»�ȡһ��
				mProInforAdapter.clearAllData();
				//��ʾ���������б���ʧ
				mphonespeedPrpBar.setVisibility(View.VISIBLE);
				mphonespeedLit.setVisibility(View.INVISIBLE);
				
				//Ӧ�ó���ɱ���󣬻���Ҫ�����б����»�ȡ�������е�Ӧ�ó���
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
		mphonespeedLit.setAdapter(mProInforAdapter);//����������
		
		//����Ʒ��/�ͺ�/�汾��
		brand.setText(PhoneInforsManager.getPhoneBrand());
		version.setText(PhoneInforsManager.getPhoneModel()+" Android "+PhoneInforsManager.getPhonVersion());
		//�����ڴ����ֺͽ�����
		setMemoryTextAndProgressBar();
		//�½�һ���߳�Ȼ����
		new Thread(run).start();
	}
	
	/**
	 * �����ڴ����ֺͽ�����
	 */
	private void setMemoryTextAndProgressBar() {
		//�����ڴ�����
		double freeRam = ByteToMbUtil.byteTomb(MemoryManager.getFreeRam(this));//�����˴�
		double maxRam = ByteToMbUtil.byteTomb(MemoryManager.getMaxRam());//�ֻ�����˴�
		//��ȡ�����ڴ�
		int useRam = (int) Math.round((maxRam - freeRam) / maxRam * 100);
		memory.setText("�����˴棺"+ useRam + "MB /" + maxRam + "MB");
				
		//���ý���������
		mphonespeedPrpBar.setProgress((int)((maxRam - freeRam)/maxRam * 100));
	}

	//���ؼ��ļ���
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				PhoneSpeedActivity.this.finish();
			}
		};

}
