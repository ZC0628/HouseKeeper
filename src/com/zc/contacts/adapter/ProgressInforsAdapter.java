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
 * 	进程管理的适配器
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
		
		//给每个checkbox添加索引
		holder.checkbox.setTag(position);
		//给多选框添加监听       当checkbox勾选状态改变的时候  ————解决checkbox错乱问题
		holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//获得传递过来的position索引
				int pos = (Integer) buttonView.getTag();
				//保存状态
				minfors.get(pos).ischecked = isChecked;
			}
		});
		
		holder.checkbox.setChecked(minfors.get(position).ischecked);
		holder.icon.setImageDrawable(minfors.get(position).icon);
		holder.name.setText(minfors.get(position).name);
		holder.memory.setText("内存："+minfors.get(position).memory+"M");
		holder.isSystem.setText("系统进程");
		//判断是否是系统进程，如果是显示“系统进程”的标记
		if (minfors.get(position).isSystem) {
			holder.isSystem.setVisibility(View.VISIBLE);
		}else {
			holder.isSystem.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}
	
	class Holder{
		CheckBox checkbox;//全选框
		ImageView icon;//应用程序图标 
		TextView name;//应用程序名称
		TextView memory;//内存
		TextView isSystem;//是否是系统进程
	}
	
	
}
