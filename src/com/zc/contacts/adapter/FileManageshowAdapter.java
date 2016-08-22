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
 * 	文件管理子界面的条目Listview的适配器
 * @author Administrator
 *
 */
public class FileManageshowAdapter extends BaseBaseAdapter<FileInfors> implements OnCheckedChangeListener{
	
	//SDK < 10 ——使用软引用加上SoftReference<T>解决Bitmap内存溢出的问题。 Bitmap存放的区域，缓存区
	//第一次打开应用程序下载的图片先存放在SD卡中，然后是缓存区中
//	private ArrayList<SoftReference<Bitmap>> list = new ArrayList<SoftReference<Bitmap>>();
	
	//SDK >= 12,则使用LruCache缓存——LRU算法（近期最少使用算法）当需要释放内存时，垃圾回收会回收最近最少使用的，再把新的数据添加进去
	private LruCache<String, Bitmap> cache ;
	
	
	public FileManageshowAdapter(Context context) {
		super(context);
		//计算出要给出多少缓存空间 ，参数是字节   maxSize—— 有4张图片的话，32M运存分配的运存就是8M
		LogUtil.printlog("maxMemory", Runtime.getRuntime().maxMemory()+"");
		//判断手机SDK版本
//		if (Build.VERSION.SDK_INT >= 12) {
			cache = new LruCache<String, Bitmap>((int)Runtime.getRuntime().maxMemory()/8){
				//自定义对象空间大小——重写sizeOf方法（检测图片大小的）
				@Override
				protected int sizeOf(String key, Bitmap value) {
					//Bitmap的高度  * Bitmap每一行的字节数
					//返回每张图片所占空间大小
					//一个个计算Bitmap大小，当大小超过length/4 就会回收最近使用次数最少的，然后添加新的数据
					return value.getHeight() * value.getRowBytes();
				}
			};
		/*}else {
			//如果你的手机比较老了，缓存就给的低一点
			cache = new LruCache<String, Bitmap>(3){
				//自定义对象空间大小——重写sizeOf方法（检测图片大小的）
				@Override
				protected int sizeOf(String key, Bitmap value) {
					//Bitmap的高度  * Bitmap每一行的字节数
					//返回每张图片所占空间大小
					//一个个计算Bitmap大小，当大小超过length/4 就会回收最近使用次数最少的，然后添加新的数据
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
			
			
			//将Holder与convertView绑定在一起
			convertView.setTag(holder);
		}else {
			//取出
			holder = (Holder) convertView.getTag();
		}
		
		//当多选框状态被切换的时候
		holder.filecheckbox.setOnCheckedChangeListener(FileManageshowAdapter.this);
		//可以给控件携带一些数据     (携带一个索引)
		holder.filecheckbox.setTag(position);
		
		//加载数据
		FileInfors fileinfor = minfors.get(position);
		
		String type = fileinfor.type;
		//如果文件的类型是图片，则显示图片本身的图片，否则使用drawable中的图片
		Bitmap bitmap = null;
		if (type.equals(FiletypeUtil.TYPE_IMAGE)) {
			//根据文件的路径获取缓存的图片
			Bitmap bit = cache.get(fileinfor.path);
			//如果是空的，就是缓存中没有这张图片
			if (bit == null) {
				//将路径转换图片
				bitmap = BitmapFactory.decodeFile(fileinfor.path);
				//把图片存到缓存中
				cache.put(fileinfor.path, bitmap);
			}
			 holder.fileicon.setImageBitmap(cache.get(fileinfor.path)); 
		}else {
			/*
			 * 返回一个res文件夹下的drawable下的int类型
			 * getResources().getIdentifier(name, defType, defPackage)
			 * 参数1：图片的字符串名称
			 * 参数2：图片所在文件夹名称
			 * 参数3：应用程序的包名
			 */
			int icon = mcontext.getResources().getIdentifier(fileinfor.iconName, 
					"drawable", mcontext.getPackageName());
			holder.fileicon.setImageResource(icon);
		}
		/**
		 * Bitmap图片又叫大胖子，是项目中非常占用内存的。而Bitmap在Listview中多的话，容易造成OutofMemory内存溢出（OOM）
		 * 
		 */
		
		
		holder.filename.setText(fileinfor.name);
		holder.filelasttime.setText(fileinfor.lastTime);
		holder.filesize.setText(fileinfor.size);
		holder.filecheckbox.setChecked(minfors.get(position).ischecked);
		
		return convertView;
	}
	
	//存放要使用的数据
	class Holder{
		CheckBox filecheckbox;
		ImageView fileicon;
		TextView filename,filelasttime,filesize;
	}
	
	//isChecked——多选框状态是否被选中
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		//获得传递过来的position索引
		int position = (Integer) buttonView.getTag();
		minfors.get(position).ischecked = isChecked;
		
	}

		
}
	


