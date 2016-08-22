package com.zc.contacts.view;

import com.zc.contacts.R.color;
import com.zc.contacts.base.utils.LogUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/**
 * 软件管理界面的饼图自绘
 * @author zc
 *
 */
public class CircleMemory extends View {
	

	private Paint paint,phone,SDcard;//画
	private RectF mrectf;//绘制饼图的矩形
	private int width,height;//获取到的宽和高
	private float startphoneAngle,startsdAngle,phonesweep,sdsweep;//开始的角度和结束的角度
	private Thread thread;
	public boolean flag = true;//线程默认是开启的
	private Runnable run = new Runnable() {
		
		@Override
		public void run() {
			while(flag){
				try {
					//每次调用onDraw函数绘制手机和sd卡的扇形
					postInvalidate();
					
					Thread.sleep(50);
					if (startphoneAngle >= phonesweep) {//判断手机角度是否达到最大角度，达到的话手机角度绘制工作结束
						startphoneAngle = phonesweep;//达到最大角度的话的停止，把手机角度固定下来
					}if (startsdAngle >= sdsweep) {//sd卡角度绘制工作结束
						startsdAngle = sdsweep;//把sd卡角度固定下来
					}
					startphoneAngle++;//角度每次都增加，绘制
					startsdAngle++;
					//如果两个角度都已达到最大角度，所有绘制工作就结束
					if (startphoneAngle == phonesweep && startsdAngle == sdsweep) {
						flag = false;//所有绘制工作都结束，线程就可以关闭 
					}
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	public CircleMemory(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();//背景
		phone = new Paint();//手机内置空间
		SDcard = new Paint(); //sd卡空间
		paint.setColor(Color.LTGRAY);//Color.LTGRAY
		phone.setColor(Color.BLUE);
		SDcard.setColor(Color.CYAN);
		
		
	}
	
	//重写测量函数，获取控件的宽高 
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		 width = getMeasuredWidth();//获取测量的宽度
		 height = getMeasuredHeight();//获取测量的高度
		
		 //0,0 坐标点——当前控件在xml文件中的左上角初始坐标点，长方形
		 mrectf = new RectF(0, 0, width, height);
		 
		LogUtil.printlog("width", width+"");
		LogUtil.printlog("height", height+"");
		//这个函数一定要写，否则报错
		//把获取到的测量宽和高设置上去
		setMeasuredDimension(width, height);
		
	}
	
	//绘制饼图
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//画所有内存的圆
		canvas.drawArc(mrectf, -90, 360, true, paint);
		//画手机的
		canvas.drawArc(mrectf, -90, startphoneAngle, true, phone);
		//画sd卡的
		canvas.drawArc(mrectf, -90 + phonesweep, startsdAngle, true, SDcard);
	}
	
	//画手机和SD卡内存占用扇形
	public void setsweep(float phonesweep,float sdsweep){
		//保存获取到的两个角度
		this.phonesweep = phonesweep;
		this.sdsweep = sdsweep;
		//把任务放在线程中和启动线程——用来绘制图形的
		thread = new Thread(run);
		thread.start();
		
	}
	
	
	//代码写布局
	public CircleMemory(Context context) {
		super(context);
		paint = new Paint();//背景
		phone = new Paint();//手机内置空间
		SDcard = new Paint(); //sd卡空间
		
		paint.setColor(color.turquoise);
		phone.setColor(color.saddlebrown);
		SDcard.setColor(color.royalblue);
	}
}
