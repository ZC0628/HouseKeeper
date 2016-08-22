package com.zc.contacts.adapter;

import java.util.ArrayList;

import com.zc.contacts.R;
import com.zc.contacts.base.adapter.BaseBaseAdapter;
import com.zc.contacts.biz.TelDBReaderManager;
import com.zc.contacts.entity.Telephonename;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 
 *TelephoneActivity中Listview用于显示Telephonelistviewitem子视图数据的适配器 
 * @author Administrator
 *
 */
public class TelephonelistAdapter extends BaseBaseAdapter<Telephonename> {
	
	public TelephonelistAdapter(Context context){
		super(context);
		//mtelephonename = DBReader.gettelephonename(context);
	}
	
	
	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = minflater.inflate(R.layout.activity_telephonelist_item, null);
			vh.txt = (TextView) convertView.findViewById(R.id.tv_telephonelist_name);
			vh.txt2 = (TextView) convertView.findViewById(R.id.tv_telephonelist_number);
			convertView.setTag(vh);//convertView与vh绑定在一起
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.txt.setText(minfors.get(position).name);
		vh.txt2.setText(minfors.get(position).number);
		return convertView;
	}
	
	
class ViewHolder{
		TextView txt,txt2;
		
	}
}
