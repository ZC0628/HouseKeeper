package com.zc.contacts.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.zc.contacts.R;
import com.zc.contacts.base.adapter.BaseBaseAdapter;
import com.zc.contacts.base.utils.FiletypeUtil;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.entity.AppInfors;
import com.zc.contacts.entity.FileInfors;
/**
 * 	�ļ������ӽ������ĿListview��������
 * @author Administrator
 *
 */
public class FileManageshowAdapter extends BaseBaseAdapter<FileInfors> implements OnCheckedChangeListener{
	
	//SDK < 10 ����ʹ�������ü���SoftReference<T>���Bitmap�ڴ���������⡣ Bitmap��ŵ����򣬻�����
	//��һ�δ�Ӧ�ó������ص�ͼƬ�ȴ����SD���У�Ȼ���ǻ�������
//	private ArrayList<SoftReference<Bitmap>> list = new ArrayList<SoftReference<Bitmap>>();
	
	//SDK >= 12,��ʹ��LruCache���桪��LRU�㷨����������ʹ���㷨������Ҫ�ͷ��ڴ�ʱ���������ջ�����������ʹ�õģ��ٰ��µ�������ӽ�ȥ
	private LruCache<String, Bitmap> cache ;
	
	
	public FileManageshowAdapter(Context context) {
		super(context);
		//�����Ҫ�������ٻ���ռ� ���������ֽ�   maxSize���� ��4��ͼƬ�Ļ���32M�˴������˴����8M
		LogUtil.printlog("maxMemory", Runtime.getRuntime().maxMemory()+"");
		//�ж��ֻ�SDK�汾
//		if (Build.VERSION.SDK_INT >= 12) {
			cache = new LruCache<String, Bitmap>((int)Runtime.getRuntime().maxMemory()/8){
				//�Զ������ռ��С������дsizeOf���������ͼƬ��С�ģ�
				@Override
				protected int sizeOf(String key, Bitmap value) {
					//Bitmap�ĸ߶�  * Bitmapÿһ�е��ֽ���
					//����ÿ��ͼƬ��ռ�ռ��С
					//һ��������Bitmap��С������С����length/4 �ͻ�������ʹ�ô������ٵģ�Ȼ������µ�����
					return value.getHeight() * value.getRowBytes();
				}
			};
		/*}else {
			//�������ֻ��Ƚ����ˣ�����͸��ĵ�һ��
			cache = new LruCache<String, Bitmap>(3){
				//�Զ������ռ��С������дsizeOf���������ͼƬ��С�ģ�
				@Override
				protected int sizeOf(String key, Bitmap value) {
					//Bitmap�ĸ߶�  * Bitmapÿһ�е��ֽ���
					//����ÿ��ͼƬ��ռ�ռ��С
					//һ��������Bitmap��С������С����length/4 �ͻ�������ʹ�ô������ٵģ�Ȼ������µ�����
					return value.getHeight() * value.getRowBytes();
				}
			};
		}*/
		
		
	}
	
	private Holder holder;
	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = minflater.inflate(R.layout.activity_filemanageshow_list_item, null);
			holder.filecheckbox = (CheckBox) convertView.findViewById(R.id.filemanageshow_listview_checkbox);
			holder.fileicon = (ImageView) convertView.findViewById(R.id.filemanageshow_listview_filePicture);
			holder.filename = (TextView) convertView.findViewById(R.id.filemanageshow_listview_fileName);
			holder.filelasttime = (TextView) convertView.findViewById(R.id.filemanageshow_listview_filelasttime);
			holder.filesize = (TextView) convertView.findViewById(R.id.filemanageshow_listview_filesize);
			
			
			//��Holder��convertView����һ��
			convertView.setTag(holder);
		}else {
			//ȡ��
			holder = (Holder) convertView.getTag();
		}
		
		//����ѡ��״̬���л���ʱ��
		holder.filecheckbox.setOnCheckedChangeListener(FileManageshowAdapter.this);
		//���Ը��ؼ�Я��һЩ����     (Я��һ������)
		holder.filecheckbox.setTag(position);
		
		//��������
		FileInfors fileinfor = minfors.get(position);
		
		String type = fileinfor.type;
		//����ļ���������ͼƬ������ʾͼƬ�����ͼƬ������ʹ��drawable�е�ͼƬ
		Bitmap bitmap = null;
		if (type.equals(FiletypeUtil.TYPE_IMAGE)) {
			//�����ļ���·����ȡ�����ͼƬ
			Bitmap bit = cache.get(fileinfor.path);
			//����ǿյģ����ǻ�����û������ͼƬ
			if (bit == null) {
				//��·��ת��ͼƬ
				bitmap = BitmapFactory.decodeFile(fileinfor.path);
				//��ͼƬ�浽������
				cache.put(fileinfor.path, bitmap);
			}
			 holder.fileicon.setImageBitmap(cache.get(fileinfor.path)); 
		}else {
			/*
			 * ����һ��res�ļ����µ�drawable�µ�int����
			 * getResources().getIdentifier(name, defType, defPackage)
			 * ����1��ͼƬ���ַ�������
			 * ����2��ͼƬ�����ļ�������
			 * ����3��Ӧ�ó���İ���
			 */
			int icon = mcontext.getResources().getIdentifier(fileinfor.iconName, 
					"drawable", mcontext.getPackageName());
			holder.fileicon.setImageResource(icon);
		}
		/**
		 * BitmapͼƬ�ֽд����ӣ�����Ŀ�зǳ�ռ���ڴ�ġ���Bitmap��Listview�ж�Ļ����������OutofMemory�ڴ������OOM��
		 * 
		 */
		
		
		holder.filename.setText(fileinfor.name);
		holder.filelasttime.setText(fileinfor.lastTime);
		holder.filesize.setText(fileinfor.size);
		holder.filecheckbox.setChecked(minfors.get(position).ischecked);
		
		return convertView;
	}
	
	//���Ҫʹ�õ�����
	class Holder{
		CheckBox filecheckbox;
		ImageView fileicon;
		TextView filename,filelasttime,filesize;
	}
	
	//isChecked������ѡ��״̬�Ƿ�ѡ��
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//��ô��ݹ�����position����
		int position = (Integer) buttonView.getTag();
		minfors.get(position).ischecked = isChecked;
		
	}

		
}
	


