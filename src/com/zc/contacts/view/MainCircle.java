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
 * 		�������ͼ���������ֻ�����
 * @author Administrator
 *
 */
public class MainCircle extends View {
	
	private Paint paint;//����
	private RectF rectf ;//�������򡪡�����
	private static final int START_AWEEP = -90;//��ʼ�Ƕ�
	private float sweep;//�Ƕ�
	//Բ�α��ϵ����Σ��ȷ��أ�Ȼ��Խ��Խ�죨���˵ĽǶȣ�
	private int[] returnback = {-1,-1,-2,-2,-2,-4,-4,-4,-6,-6,-6,-8,-8,-8,-12,-12,-12,};
	private int returnbackIndex;//���ص�����
	private int flag = 0;// 0��������
	
	
	public MainCircle(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setAntiAlias(true);
	}
	 
	//��������
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//�Ӵ��ݹ��������������������͸�
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		rectf = new RectF(0,0,width, height);
		//�ѻ�ȡ���Ĳ�����͸�������ȥ
		//�������һ��Ҫд�����򱨴�
		setMeasuredDimension(width, height);
	}
	
	//���ݽǶȵĺ���
	public void setSweep(float startsweep,final float sweepTwo){
		flag = 0;//�����⺯���Ŀ�ʼ��360���˵�
		this.sweep = startsweep;//�Ƕȱ�������
		final Timer timer = new Timer();//��ʱ��   ���ֲ��ڲ���Ҫ��final��
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				switch (flag) {
				case 0:
//					��ÿ�μ�ȥ�Ƕȡ�������˵ĽǶ�
					MainCircle.this.sweep += returnback[returnbackIndex];
					returnbackIndex++;
					//����������Χ�Ļ����ж�
					if (returnbackIndex >= returnback.length - 1) {
						returnbackIndex = returnback.length - 1;//���������һ������
					}
					
					if (MainCircle.this.sweep <= 0) {
						MainCircle.this.sweep = 0;
						flag = 1;//���˽�����ʼǰ��
						postInvalidate();
						//����֮������ȡ������ʱ������
					}
					postInvalidate();//���Ƕȡ�����������onDraw������
					break;
				case 1:
//					��ÿ�μ�ȥ�Ƕȡ�������˵ĽǶ�
					MainCircle.this.sweep -= returnback[returnbackIndex];
					returnbackIndex++;
					//����������Χ�Ļ����ж�
					if (returnbackIndex >= returnback.length - 1) {
						returnbackIndex = returnback.length - 1;//���������һ������
					}
					//����ĽǶȴ��ڵڶ����Ƕ�˵����ͷ��
					if (MainCircle.this.sweep >= sweepTwo) {
						MainCircle.this.sweep = sweepTwo;
						flag = 0;//���˽�����ʼǰ��
						postInvalidate();
						//����֮������ȡ������ʱ������
						timer.cancel();
					}
					postInvalidate();//���Ƕȡ�����������onDraw������
					break;
				}
			}
		};
		timer.schedule(task, 100, 100);//����ִ�� ��delay�����ӳ�ʱ�䡣  period�������ٺ���ִ��һ��
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
