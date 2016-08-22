package com.zc.contacts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.contacts.R;
import com.zc.contacts.base.adapter.BaseBaseAdapter;
import com.zc.contacts.entity.ProgressInfors;
/**
 * 	���̹����������
 * @author Administrator
 *
 */
public class ProgressInforsAdapter extends BaseBaseAdapter<ProgressInfors> {

	public ProgressInforsAdapter(Context context) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = minflater.inflate(R.layout.activity_phonespeed_list_item, null);
			holder.checkbox = (CheckBox) convertView.findViewById(R.id.phonespeed_listview_checkbox);
			holder.icon = (ImageView) convertView.findViewById(R.id.phonespeed_listview_AppPicture);
			holder.name  = (TextView) convertView.findViewById(R.id.phonespeed_listview_AppName);
			holder.memory = (TextView) convertView.findViewById(R.id.phonespeed_listview_Appmemory);
			holder.isSystem = (TextView) convertView.findViewById(R.id.phonespeed_listview_isSystem);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		
		//��ÿ��checkbox�������
		holder.checkbox.setTag(position);
		//����ѡ����Ӽ���       ��checkbox��ѡ״̬�ı��ʱ��  �����������checkbox��������
		holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//��ô��ݹ�����position����
				int pos = (Integer) buttonView.getTag();
				//����״̬
				minfors.get(pos).ischecked = isChecked;
			}
		});
		
		holder.checkbox.setChecked(minfors.get(position).ischecked);
		holder.icon.setImageDrawable(minfors.get(position).icon);
		holder.name.setText(minfors.get(position).name);
		holder.memory.setText("�ڴ棺"+minfors.get(position).memory+"M");
		holder.isSystem.setText("ϵͳ����");
		//�ж��Ƿ���ϵͳ���̣��������ʾ��ϵͳ���̡��ı��
		if (minfors.get(position).isSystem) {
			holder.isSystem.setVisibility(View.VISIBLE);
		}else {
			holder.isSystem.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
	class Holder{
		CheckBox checkbox;//ȫѡ��
		ImageView icon;//Ӧ�ó���ͼ�� 
		TextView name;//Ӧ�ó�������
		TextView memory;//�ڴ�
		TextView isSystem;//�Ƿ���ϵͳ����
	}
	
	
}
