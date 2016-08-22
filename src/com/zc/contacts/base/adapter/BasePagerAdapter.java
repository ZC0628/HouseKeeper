package com.zc.contacts.base.adapter;

import java.util.ArrayList;

import com.zc.contacts.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BasePagerAdapter extends PagerAdapter {
	//ViewPager数据源一般有两种类型 ，  1.View	2.Fragment
	private ArrayList<View> views = new ArrayList<View>();
	private LayoutInflater minflater;
	private Context mcontext;
	private int [] pics = {R.drawable.lead1,R.drawable.lead3,R.drawable.lead2}; 
	
	public BasePagerAdapter(Context context){
		this.mcontext = context;
		minflater = LayoutInflater.from(context);
		//将三个视图加载成View对象，并且设置图片
		for(int x = 0;x < pics.length;x++){
			View v = minflater.inflate(R.layout.activtiy_lead_viewpager_item, null);
			ImageView img = (ImageView) v.findViewById(R.id.img_lead_viewpager_item);
			img.setImageResource(pics[x]);
			//将视图加载进适配器
			addViewToAdapter(v);
		}
	}
	
	/**
	 * 添加视图对象到适配器
	 * @param v		视图
	 */
	public void addViewToAdapter(View v){
		views.add(v);
	}
	
	/**
	 * 返回数据源集合
	 * @return	数据源
	 */
	public ArrayList<View> getViews(){
		return views;
	}
	
	
	@Override
	public int getCount() {
		return views.size();
	}

	//官方的
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	/*
	 * 将视图添加进视图组
	 * 参数1：视图组对象
	 * 参数2：索引
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v = views.get(position);
		container.addView(v);
		return v;
	}
	
	//将视图移出视图组
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}
	
	
}
