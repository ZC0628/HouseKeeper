package com.zc.contacts.view;

import java.io.IOException;

import com.zc.contacts.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
/**
 *    Gif��̬ͼ������
 * @author Administrator
 *	
 *	�˹������У�ֻ����2�����顣1,���췽����2����д��onDraw��������
 *	��android��Ĭ�ϵĿؼ��ǲ�֧��gif��ʽ��ͼƬ�ģ�ֻ����ʾͼƬ�ĵ�һ֡��������Ҫ������Movie�ࡣ
 *	��ͼƬ���н������š�����ʹ��һ�ִ�������Զ���ؼ������ַ�ʽʹ�÷��㣬
 *	����֧����ImageView֮������ֱ��ͨ���������Կ�������Ҫ���ŵĶ�����
 *	������ͨ�����췽����ͼƬ��R�ļ��µ�ID���롣
 *
 *	Android�������ṩ��һ��Movie�࣬����������ʵ�ּ���gif��ʽ��Դ��Ŀ�ꡣ
 *	������Ҫ����android.graphics.Movie���������Ȼ���Ҳ��Android�Դ��ġ�
 *	�������ǵ���Ҫ�����Ǽ̳�һ��ImageView�����࣬ͨ����д���е�onDraw��������gif��Դ��
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
		 //��ȡϵͳ��ǰʱ��
		long now = android.os.SystemClock.uptimeMillis();
		// �����һ֡����¼��ʼʱ��  
		 //��Ϊ��һ�μ��أ���ʼʱ����Ϊnow
		if (movieStart == 0) { // ��һ֡
			movieStart = now;
		}
		if (movie != null) { //�ݴ���
			//��ȡgif����ʱ��            ���gif����ʱ��Ϊ100������Ϊ��gif��Դ����������
			int dur = movie.duration();
			if (dur == 0) {
				dur = 1000;
			}
			//��ȡgif��ǰ֡����ʾ����ʱ���
			int relTime = (int) ((now - movieStart) % dur);
			movie.setTime(relTime);
			 //��Ⱦgifͼ��
			movie.draw(canvas, 0, 0);
			invalidate();
		}
	}
	
}
