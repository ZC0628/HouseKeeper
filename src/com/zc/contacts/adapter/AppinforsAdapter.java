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
 * 应用程序列表的适配器 （多选框——应用程序的图标，名称，包名，版本号）
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
			
			//将Holder与convertView绑定在一起
			convertView.setTag(holder);		
		}else {
			//取出
			holder = (Holder) convertView.getTag();
		}
		
		//设置checkbox的监听(当点击的时候调用)  
		//解决多选框在列表中错乱问题，需要将多选框的状态记录下来
		/*holder.checkbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果checkbox选中则将索引添加到集合中
				if (!holder.checkbox.isChecked()) {
					//更改数据源
					minfors.get(position).ischecked = true;
				}else {
					minfors.get(position).ischecked = false;
				}
			}
		});*/
		
		//当多选框状态被切换的时候
		holder.checkbox.setOnCheckedChangeListener(AppinforsAdapter.this);
		//可以给控件携带一些数据     (携带一个索引)
		holder.checkbox.setTag(position);
		
		//加载数据
		AppInfors infor = minfors.get(position);
		holder.checkbox.setChecked(infor.ischecked);
		holder.apppicture.setImageDrawable(infor.icon);
		holder.appName.setText(infor.name);
		holder.appPickageName.setText(infor.packagename);
		holder.AppVersion.setText(infor.version);
		return convertView;
	}
	
	//存放要使用的数据
	class Holder{
		CheckBox checkbox;
		ImageView apppicture;
		TextView appName,appPickageName,AppVersion;
	}
	
	//isChecked——多选框状态是否被选中
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//获得传递过来的position索引
		int position = (Integer) buttonView.getTag();
		AppInfors infor = minfors.get(position);
		//保存状态
		infor.ischecked = isChecked;
	}
	
	
}
