package com.zc.contacts.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.contacts.R;
import com.zc.contacts.base.adapter.BaseBaseAdapter;
import com.zc.contacts.entity.AppInfors;
/**
 * Ӧ�ó����б�������� ����ѡ�򡪡�Ӧ�ó����ͼ�꣬���ƣ��������汾�ţ�
 * @author Administrator
 *
 */
public class AppinforsAdapter extends BaseBaseAdapter<AppInfors> implements OnCheckedChangeListener{

	public AppinforsAdapter(Context context) {
		super(context);
	
	}
	
	private Holder holder;
	
	@Override
	public View getMyView(final int position, View convertView, ViewGroup parent) {
		 holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = minflater.inflate(R.layout.activity_softwaremanage_list_itemapp, null);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.softwaremanage_listview_checkbox);
			holder.apppicture = (ImageView) convertView.findViewById(R.id.softwaremanage_listview_AppPicture);
			holder.appName = (TextView) convertView.findViewById(R.id.softwaremanage_listview_AppName);
			holder.appPickageName = (TextView) convertView.findViewById(R.id.softwaremanage_listview_AppPickageName);
			holder.AppVersion = (TextView) convertView.findViewById(R.id.softwaremanage_listview_Appversion);
			
			//��Holder��convertView����һ��
			convertView.setTag(holder);		
		}else {
			//ȡ��
			holder = (Holder) convertView.getTag();
		}
		
		//����checkbox�ļ���(�������ʱ�����)  
		//�����ѡ�����б��д������⣬��Ҫ����ѡ���״̬��¼����
		/*holder.checkbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//���checkboxѡ����������ӵ�������
				if (!holder.checkbox.isChecked()) {
					//��������Դ
					minfors.get(position).ischecked = true;
				}else {
					minfors.get(position).ischecked = false;
				}
			}
		});*/
		
		//����ѡ��״̬���л���ʱ��
		holder.checkbox.setOnCheckedChangeListener(AppinforsAdapter.this);
		//���Ը��ؼ�Я��һЩ����     (Я��һ������)
		holder.checkbox.setTag(position);
		
		//��������
		AppInfors infor = minfors.get(position);
		holder.checkbox.setChecked(infor.ischecked);
		holder.apppicture.setImageDrawable(infor.icon);
		holder.appName.setText(infor.name);
		holder.appPickageName.setText(infor.packagename);
		holder.AppVersion.setText(infor.version);
		return convertView;
	}
	
	//���Ҫʹ�õ�����
	class Holder{
		CheckBox checkbox;
		ImageView apppicture;
		TextView appName,appPickageName,AppVersion;
	}
	
	//isChecked������ѡ��״̬�Ƿ�ѡ��
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//��ô��ݹ�����position����
		int position = (Integer) buttonView.getTag();
		AppInfors infor = minfors.get(position);
		//����״̬
		infor.ischecked = isChecked;
	}
	
	
}
