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
 * ����������ı�ͼ�Ի�
 * @author zc
 *
 */
public class CircleMemory extends View {
	

	private Paint paint,phone,SDcard;//��
	private RectF mrectf;//���Ʊ�ͼ�ľ���
	private int width,height;//��ȡ���Ŀ�͸�
	private float startphoneAngle,startsdAngle,phonesweep,sdsweep;//��ʼ�ĽǶȺͽ����ĽǶ�
	private Thread thread;
	public boolean flag = true;//�߳�Ĭ���ǿ�����
	private Runnable run = new Runnable() {
		
		@Override
		public void run() {
			while(flag){
				try {
					//ÿ�ε���onDraw���������ֻ���sd��������
					postInvalidate();
					
					Thread.sleep(50);
					if (startphoneAngle >= phonesweep) {//�ж��ֻ��Ƕ��Ƿ�ﵽ���Ƕȣ��ﵽ�Ļ��ֻ��ǶȻ��ƹ�������
						startphoneAngle = phonesweep;//�ﵽ���ǶȵĻ���ֹͣ�����ֻ��Ƕȹ̶�����
					}if (startsdAngle >= sdsweep) {//sd���ǶȻ��ƹ�������
						startsdAngle = sdsweep;//��sd���Ƕȹ̶�����
					}
					startphoneAngle++;//�Ƕ�ÿ�ζ����ӣ�����
					startsdAngle++;
					//��������Ƕȶ��Ѵﵽ���Ƕȣ����л��ƹ����ͽ���
					if (startphoneAngle == phonesweep && startsdAngle == sdsweep) {
						flag = false;//���л��ƹ������������߳̾Ϳ��Թر� 
					}
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	public CircleMemory(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();//����
		phone = new Paint();//�ֻ����ÿռ�
		SDcard = new Paint(); //sd���ռ�
		paint.setColor(Color.LTGRAY);//Color.LTGRAY
		phone.setColor(Color.BLUE);
		SDcard.setColor(Color.CYAN);
		
		
	}
	
	//��д������������ȡ�ؼ��Ŀ�� 
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		 width = getMeasuredWidth();//��ȡ�����Ŀ��
		 height = getMeasuredHeight();//��ȡ�����ĸ߶�
		
		 //0,0 ����㡪����ǰ�ؼ���xml�ļ��е����Ͻǳ�ʼ����㣬������
		 mrectf = new RectF(0, 0, width, height);
		 
		LogUtil.printlog("width", width+"");
		LogUtil.printlog("height", height+"");
		//�������һ��Ҫд�����򱨴�
		//�ѻ�ȡ���Ĳ�����͸�������ȥ
		setMeasuredDimension(width, height);
		
	}
	
	//���Ʊ�ͼ
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//�������ڴ��Բ
		canvas.drawArc(mrectf, -90, 360, true, paint);
		//���ֻ���
		canvas.drawArc(mrectf, -90, startphoneAngle, true, phone);
		//��sd����
		canvas.drawArc(mrectf, -90 + phonesweep, startsdAngle, true, SDcard);
	}
	
	//���ֻ���SD���ڴ�ռ������
	public void setsweep(float phonesweep,float sdsweep){
		//�����ȡ���������Ƕ�
		this.phonesweep = phonesweep;
		this.sdsweep = sdsweep;
		//����������߳��к������̡߳�����������ͼ�ε�
		thread = new Thread(run);
		thread.start();
		
	}
	
	
	//����д����
	public CircleMemory(Context context) {
		super(context);
		paint = new Paint();//����
		phone = new Paint();//�ֻ����ÿռ�
		SDcard = new Paint(); //sd���ռ�
		
		paint.setColor(color.turquoise);
		phone.setColor(color.saddlebrown);
		SDcard.setColor(color.royalblue);
	}
}
