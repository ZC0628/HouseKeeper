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
	//ViewPager����Դһ������������ ��  1.View	2.Fragment
	private ArrayList<View> views = new ArrayList<View>();
	private LayoutInflater minflater;
	private Context mcontext;
	private int [] pics = {R.drawable.lead1,R.drawable.lead3,R.drawable.lead2}; 
	
	public BasePagerAdapter(Context context){
		this.mcontext = context;
		minflater = LayoutInflater.from(context);
		//��������ͼ���س�View���󣬲�������ͼƬ
		for(int x = 0;x < pics.length;x++){
			View v = minflater.inflate(R.layout.activtiy_lead_viewpager_item, null);
			ImageView img = (ImageView) v.findViewById(R.id.img_lead_viewpager_item);
			img.setImageResource(pics[x]);
			//����ͼ���ؽ�������
			addViewToAdapter(v);
		}
	}
	
	/**
	 * �����ͼ����������
	 * @param v		��ͼ
	 */
	public void addViewToAdapter(View v){
		views.add(v);
	}
	
	/**
	 * ��������Դ����
	 * @return	����Դ
	 */
	public ArrayList<View> getViews(){
		return views;
	}
	
	
	@Override
	public int getCount() {
		return views.size();
	}

	//�ٷ���
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	/*
	 * ����ͼ��ӽ���ͼ��
	 * ����1����ͼ�����
	 * ����2������
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v = views.get(position);
		container.addView(v);
		return v;
	}
	
	//����ͼ�Ƴ���ͼ��
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}
	
	
}
