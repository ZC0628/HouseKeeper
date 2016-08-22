package com.zc.contacts.base.adapter;

import java.util.ArrayList;

import com.zc.contacts.entity.Telephonename;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * �����������ĸ��࣬�ṩ���/ɾ�����ݵĺ��������������дgetMyView����
 * @param <T>	���ݵ�����
 */
public abstract class BaseBaseAdapter<T> extends BaseAdapter {

	//protected ArrayList<T> mtelephonename = new ArrayList<T>();
	protected ArrayList<T> minfors = new ArrayList<T>();
	protected Context mcontext;
	protected LayoutInflater minflater;//���ּ�����
	
	public BaseBaseAdapter(Context context){
		mcontext = context;
		minflater = LayoutInflater.from(context);
	}
	
	/**
	 * ����TelephonelistActivity��item������
	 * ��������Դ
	 * @return   ����Դ
	 */
	public ArrayList<T> getitemInfos(){
		return minfors;
	}
	
	/**
	 * ������ݵ����������ķ���
	 * @param  telnames  Ҫ��ӵ�����
	 */
	public void addDatatoadapter(T infor){
		minfors.add(infor);
	}
	
	/**
	 * ������ݵ���������ָ��λ�õķ���
	 * @param  telnames  Ҫ��ӵ�����
	 * @param  index  Ҫ��ӵ����ݵ�λ��
	 */
	public void addDatatoadapter(T infor,int index){
		minfors.add(index, infor);
	}
	
	/**
	 * ��Ӽ��������ݵ��������еķ���
	 * @param  telnames  Ҫ��ӵ�����
	 */
	public void addDatatoadapter(ArrayList<T> minfors){
		this.minfors.addAll(minfors);//�������е����ݼӵ�������ȥ
	}
	
	/**
	 * �����������
	 * @param
	 */
	public void clearAllData(){
		minfors.clear();
	}
	
	@Override
	public int getCount() {
		return minfors.size();
	}
	
	//��ȡ��������Ķ���
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
