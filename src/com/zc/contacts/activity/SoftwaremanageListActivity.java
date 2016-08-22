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
 * ������� ���� ϵͳ��� ���� �û����
 * @author Administrator
 *
 */
public class SoftwaremanageListActivity extends MyActionBarActivity implements MyDataListener{
	private ProgressBar mCircleBar;//Բ�ν�����
	private ListView mAppitem;//Ӧ�ó����б�
	private AppinforsAdapter mAppAdapter;//Ӧ�ó����б��������
	private AppInforsManager mappmanager;//Ӧ�ó�����Ϣ������
	private String title;//���⣨���������ϵͳ������û������
	private CheckBox mAllCheck;//ȫѡ��
	private Button mdeleate;//ж����ѡ����İ�ť
	private AppdeleteReceiver mBcRece;//�㲥������
	
	
	//Handler�� �����ڰ�׿����Ҫ���ڷ�����Ϣ�Լ����ܺʹ�����Ϣ��Ƶ����
	private Handler mhandler = new Handler(){
		/*
		 * Ϊ�˴�����Ϣ��������Handler�����������дHandler����ĺ��� ����handleMessage(non-Javadoc)
		 * ������android.os.Message ���� һ����Ϣ����
		 * �ú�����UI�̣߳����̣߳���ִ��
		 */
		public void handleMessage(android.os.Message msg) {
			//����Ϣ������������
			if (msg.what == 1) {
				//��׿�ǲ����������߳��и���UI���û����棩
				mCircleBar.setVisibility(View.INVISIBLE);//��ת����֮���������ʧ
				//��ListView��ʾ
				mAppitem.setVisibility(View.VISIBLE);
				//���ݱ���title�����ж�
				if (title.equals("�������")) {
					//�����ݷ���������
					mAppAdapter.addDatatoadapter(mappmanager.getAllpackageinfors());
				}else if (title.equals("ϵͳ���")) {
					mAppAdapter.addDatatoadapter(mappmanager.getSystempackageinfors());
				}else {
					mAppAdapter.addDatatoadapter(mappmanager.getUserpackageinfors());
				}
				mAppitem.setAdapter(mAppAdapter);//���������ݺ�����������
				
			}
		};
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_softwaremanage_list);
		initview();
		
		getData();
		
		
		/*//��������ת�������ʧ ��������  ����ģ���
		//android�в��������߳�˯�ߣ��൱���߳̿�ס��˯����5������ANR��������Ӧ�쳣������ִ�н�����������
		try {
			Thread.sleep(2000);
			mCircleBar.setVisibility(View.INVISIBLE);//��ת����֮���������ʧ
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
			//��ȫѡ��ťѡ��״̬�ı��ʱ��	
				mAllCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					//isChecked�����Ƿ�ѡ��
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						//���Ȼ�ȡ������		��Ϊ���ص��Ǹ��࣬����Ҫǿ������ת��������
						AppinforsAdapter adapter = (AppinforsAdapter) mAppitem.getAdapter();
						//��ȡ������Դ
						ArrayList<AppInfors> appinfor = adapter.getitemInfos();
							for (AppInfors appInfors : appinfor) {
								//����ѡ��״̬���������ѡ��״̬�ľ�ȫ�����ó�ѡ��
								appInfors.ischecked = isChecked;
							}
							//֪ͨ����������UI
							mAppAdapter.notifyDataSetChanged();
					}
				});
				
				//���ж����ѡ�����ť		
				mdeleate.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//���Ȼ�ȡ��ЩӦ����Ҫɾ��
						//��ȡ������		��Ϊ���ص��Ǹ��࣬����Ҫǿ������ת��������
						AppinforsAdapter adapter = (AppinforsAdapter) mAppitem.getAdapter();
						//��ȡ������Դ
						ArrayList<AppInfors> appinfor = adapter.getitemInfos();
						for (AppInfors appInfors : appinfor) {
							if (appInfors.ischecked) {//ѡ��״̬
								//��ʽ��ͼ��ת����׿�Դ��Ľ���
								Intent it = new Intent(Intent.ACTION_DELETE);//ж��
								//����ϵͳҪɾ����Ӧ�ð���
								it.setData(Uri.parse("package:"+appInfors.packagename));
								//��ת����׿ɾ�������һ����ɾ�������������ﲻ�������ɾ������
								startActivity(it);
							}
						}
						//ɾ�����֮�����UI
						//��������������Դ���ݸ��£����������е�getview�����ᱻ���µ���
						//mAppAdapter.notifyDataSetChanged();
						
						//�����������
						mAppAdapter.clearAllData();
					}
				});
				
				
	}


	//����Դ
	private void getAppData() {
		/**
		 * �̺߳�Handler����ϣ��̷߳�����Ϣ��Handler������Ϣ�Լ�������Ϣ
		 */
		new Thread(){
			public void run() {
				//���ֻ��л�ȡӦ�ó�����Ϣ
				mappmanager = new AppInforsManager(SoftwaremanageListActivity.this);
				mappmanager.addAllpackageinfors();
				
				//����һ���յ���Ϣ����Я��һ������
				mhandler.sendEmptyMessage(1);
				
				mAppAdapter.notifyDataSetChanged();
			};
		}.start();
	}

	

	@Override
	protected void initview() {
		Bundle bundle = getIntent().getBundleExtra("bundle");
		 title = bundle.getString("ActionBarTitle");
		//������
		initActionBar(R.drawable.returned, title, ActionBar.ID_NULL, on);
		
		mCircleBar = (ProgressBar) findViewById(R.id.softwaremanage_progressBar_cilcle);//��ʼ��Բ�ν�����
		mAppitem = (ListView) findViewById(R.id.softwaremanage_butttom_listview);//��ʼ��Ӧ�ó����б�
		//��ȡȫѡ��
		mAllCheck = (CheckBox) findViewById(R.id.sofewaremanage_list_checkAll);
		//ж����ѡ����İ�ť
		mdeleate = (Button) findViewById(R.id.softwaremanage_listview_delete);
		//����SoftwareManageActivity���ݹ����ı��
		mAppAdapter = new AppinforsAdapter(this);
		//�����㲥
		mBcRece = new AppdeleteReceiver();
		mBcRece.setMyDataListener(this);
		//ע��㲥������registerReceiver(receiver, filter)����receiver�����㲥�����ߵĶ���filter����������Ƶ��
		IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
		//���һ��Э�飬�����
		filter.addDataScheme("package");
		//����Ƶ��(������Ҫɾ����Ƶ��)��򿪹㲥 ����������̬ע�᷽ʽ���ڴ�����ע���ע���㲥
		registerReceiver(mBcRece, filter);
		
	}
	
	
	//���صļ���
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				SoftwaremanageListActivity.this.finish();
			}
		};
		
		
		//ע���㲥
		@Override
		protected void onDestroy() {
			super.onDestroy();
			unregisterReceiver(mBcRece);
		}


		//�ӿ��ǿ��Դ������ݵ�
		@Override
		public void getData() {
			//����getAppData����
			getAppData();
		};
		
}
