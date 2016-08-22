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
 *TelephoneActivity中Listview用于显示数据的适配器 
 * @author Administrator
 *
 */
public class TelephoneAdapter extends BaseBaseAdapter<Telephonename> {
	
	public TelephoneAdapter(Context context){
		super(context);
		//mtelephonename = DBReader.gettelephonename(context);
	}
	
	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = minflater.inflate(R.layout.activity_telephone_item, null);
			vh.txt = (TextView) convertView.findViewById(R.id.txt_telephone_item);
			convertView.setTag(vh);//convertView与vh绑定在一起
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		
		vh.txt.setText(minfors.get(position).name);
		return convertView;
	}
	
	
class ViewHolder{
		TextView txt;
		
	}
}
