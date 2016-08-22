package com.zc.contacts.base.adapter;

import java.util.ArrayList;

import com.zc.contacts.entity.Telephonename;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 所有适配器的父类，提供添加/删除数据的函数，子类必须重写getMyView函数
 * @param <T>	数据的类型
 */
public abstract class BaseBaseAdapter<T> extends BaseAdapter {

	//protected ArrayList<T> mtelephonename = new ArrayList<T>();
	protected ArrayList<T> minfors = new ArrayList<T>();
	protected Context mcontext;
	protected LayoutInflater minflater;//布局加载器
	
	public BaseBaseAdapter(Context context){
		mcontext = context;
		minflater = LayoutInflater.from(context);
	}
	
	/**
	 * 返回TelephonelistActivity中item的数据
	 * 返回数据源
	 * @return   数据源
	 */
	public ArrayList<T> getitemInfos(){
		return minfors;
	}
	
	/**
	 * 添加数据到适配器最后的方法
	 * @param  telnames  要添加的数据
	 */
	public void addDatatoadapter(T infor){
		minfors.add(infor);
	}
	
	/**
	 * 添加数据到适配器到指定位置的方法
	 * @param  telnames  要添加的数据
	 * @param  index  要添加的数据的位置
	 */
	public void addDatatoadapter(T infor,int index){
		minfors.add(index, infor);
	}
	
	/**
	 * 添加集合中数据到适配器中的方法
	 * @param  telnames  要添加的数据
	 */
	public void addDatatoadapter(ArrayList<T> minfors){
		this.minfors.addAll(minfors);//将集合中的数据加到集合中去
	}
	
	/**
	 * 清空所有数据
	 * @param
	 */
	public void clearAllData(){
		minfors.clear();
	}
	
	@Override
	public int getCount() {
		return minfors.size();
	}
	
	//获取集合里面的对象
	@Override
	public Object getItem(int position) {
		return minfors.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return getMyView(position,convertView,parent);
	}
	
	public abstract View getMyView(int position, View convertView, ViewGroup parent);
}
