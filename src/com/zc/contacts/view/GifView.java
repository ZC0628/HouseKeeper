package com.zc.contacts.view;

import java.io.IOException;

import com.zc.contacts.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
/**
 *    Gif动态图工具类
 * @author Administrator
 *	
 *	此工具类中，只做了2件事情。1,构造方法；2，重写了onDraw（）方法
 *	在android中默认的控件是不支持gif格式的图片的，只能显示图片的第一帧，这里需要借助于Movie类。
 *	将图片进行解析播放。下面使用一种纯代码的自定义控件，这种方式使用方便，
 *	但不支持像ImageView之类的组件直接通过设置属性可以设置要播放的动画。
 *	这里是通过构造方法将图片在R文件下的ID传入。
 *
 *	Android给我们提供了一个Movie类，可以让我们实现加载gif格式资源的目标。
 *	我们需要导入android.graphics.Movie这个包，当然这个也是Android自带的。
 *	所以我们的主要方法是继承一个ImageView的子类，通过改写其中的onDraw方法加载gif资源。
 *
 */
public class GifView extends View {
	
	private Movie movie;
	private long movieStart;

	public GifView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GifView(Context context) {
		super(context);
		init(context);
	}

	public GifView(Context context, int gifId) {
		super(context);
		init(context, gifId);
	}

	private void init(Context context) {
		try {
			movie = Movie.decodeStream(context.getAssets().open("loading.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init(Context context, int gifId) {
		try {
			movie = Movie.decodeStream(getResources().openRawResource(gifId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onDraw(Canvas canvas) {
		 //获取系统当前时间
		long now = android.os.SystemClock.uptimeMillis();
		// 如果第一帧，记录起始时间  
		 //若为第一次加载，开始时间置为now
		if (movieStart == 0) { // 第一帧
			movieStart = now;
		}
		if (movie != null) { //容错处理
			//获取gif持续时间            如果gif持续时间为100，可认为非gif资源，跳出处理
			int dur = movie.duration();
			if (dur == 0) {
				dur = 1000;
			}
			//获取gif当前帧的显示所在时间点
			int relTime = (int) ((now - movieStart) % dur);
			movie.setTime(relTime);
			 //渲染gif图像
			movie.draw(canvas, 0, 0);
			invalidate();
		}
	}
	
}
