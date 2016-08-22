package com.zc.contacts.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zc.contacts.R;
import com.zc.contacts.base.adapter.BaseBaseAdapter;
import com.zc.contacts.entity.PhonetestInfor;
/**
 * ÊÖ»ú¼ì²âµÄÊÊÅäÆ÷
 * @author Administrator
 *
 */
public class PhonetestAdapter extends BaseBaseAdapter<PhonetestInfor> {

	public PhonetestAdapter(Context context) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			 convertView = minflater.inflate(R.layout.activity_phonetest_listview, null);
		}
		ImageView icon = (ImageView) convertView.findViewById(R.id.phonetest_listview_icon);
		TextView title = (TextView) convertView.findViewById(R.id.phonetest_listview_title);
		TextView text = (TextView) convertView.findViewById(R.id.phonetest_listview_text);
		
		icon.setImageDrawable((minfors.get(position).icon));
		title.setText(minfors.get(position).title);
		text.setText(minfors.get(position).content);
		return convertView;
	}
}
