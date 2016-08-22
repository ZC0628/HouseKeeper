package com.zc.contacts.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
/**
 * 		主界面饼图————手机加速
 * @author Administrator
 *
 */
public class MainCircle extends View {
	
	private Paint paint;//画笔
	private RectF rectf ;//矩形区域————
	private static final int START_AWEEP = -90;//起始角度
	private float sweep;//角度
	//圆形边上的扇形，先返回，然后越来越快（回退的角度）
	private int[] returnback = {-1,-1,-2,-2,-2,-4,-4,-4,-6,-6,-6,-8,-8,-8,-12,-12,-12,};
	private int returnbackIndex;//返回的索引
	private int flag = 0;// 0——回退
	
	
	public MainCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
	}
	 
	//测量方法
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//从传递过来的两个参数计算出宽和高
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		rectf = new RectF(0,0,width, height);
		//把获取到的测量宽和高设置上去
		//这个函数一定要写，否则报错
		setMeasuredDimension(width, height);
	}
	
	//传递角度的函数
	public void setSweep(float startsweep,final float sweepTwo){
		flag = 0;//调用这函数的开始是360回退的
		this.sweep = startsweep;//角度保存下来
		final Timer timer = new Timer();//计时器   （局部内部类要加final）
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				switch (flag) {
				case 0:
//					先每次减去角度——会回退的角度
					MainCircle.this.sweep += returnback[returnbackIndex];
					returnbackIndex++;
					//索引超出范围的话就判断
					if (returnbackIndex >= returnback.length - 1) {
						returnbackIndex = returnback.length - 1;//定格在最后一个索引
					}
					
					if (MainCircle.this.sweep <= 0) {
						MainCircle.this.sweep = 0;
						flag = 1;//回退结束开始前进
						postInvalidate();
						//画完之后任务取消，计时器结束
					}
					postInvalidate();//画角度————调用onDraw函数、
					break;
				case 1:
//					先每次减去角度——会回退的角度
					MainCircle.this.sweep -= returnback[returnbackIndex];
					returnbackIndex++;
					//索引超出范围的话就判断
					if (returnbackIndex >= returnback.length - 1) {
						returnbackIndex = returnback.length - 1;//定格在最后一个索引
					}
					//给你的角度大于第二个角度说明到头了
					if (MainCircle.this.sweep >= sweepTwo) {
						MainCircle.this.sweep = sweepTwo;
						flag = 0;//回退结束开始前进
						postInvalidate();
						//画完之后任务取消，计时器结束
						timer.cancel();
					}
					postInvalidate();//画角度————调用onDraw函数、
					break;
				}
			}
		};
		timer.schedule(task, 100, 100);//任务执行 。delay——延迟时间。  period——多少毫秒执行一次
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawArc(rectf, START_AWEEP, sweep, true, paint);
	}
	
	
	
	public MainCircle(Context context) {
		super(context);
	}


}
