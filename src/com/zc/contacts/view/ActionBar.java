package com.zc.contacts.view;

import com.zc.contacts.R;
import com.zc.contacts.base.utils.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 		actionbar���������� ���Զ�����Ͽؼ�
 * @author Administrator zc
 *	��Ϊ�������������ؼ���ɣ���һ��ˮƽ��LinearLayout���ñ���ȥ�̳�LinearLayout��Ȼ����Ҫ��д�������캯��
 */
public class ActionBar extends LinearLayout{
	public static final int ID_NULL = -1;//���������-1����ʾ����ҪͼƬ
	private ImageView left,right;
	private TextView middle;
	
	
	//XML�ļ��д����ؼ�ר�õĹ��캯��
	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LogUtil.printlog("MyActionBar", "XML�д����ؼ�");
	//�ȼ��ز��ֵ�ActionBar�Ŀؼ���   ��View�еĲ��ּ�����
		/*  inflate(context, resource, root);
		*	resource������һ�������ļ�
		*	root�����ӵ�����ȥ		*/
	inflate(context, R.layout.actionbar, this);
		//�Ӳ����ļ����ҵ������ؼ�
	left = (ImageView) findViewById(R.id.actionbar_left);
	right = (ImageView) findViewById(R.id.actionbar_right);
	middle = (TextView) findViewById(R.id.actionbar_middle);
	}
	
	
	//�ô��봴�����ֵĹ��캯��
	public ActionBar(Context context) {
		super(context);
		//TextView tv = new TextView(this);
		LogUtil.printlog("MyActionBar", "���봴���ؼ�");
	}


	/**
	 *���ؼ����ݴ��ݵ������У�initActionBar������ʼ����������õ�ActionBar�ؼ���
	 */
	public void initActionBar(int left, String middle, int right,OnClickListener on) {
		if (left != -1 ) {//��ҪͼƬ����ʾ
			this.left.setVisibility(View.VISIBLE);
			this.left.setImageResource(left);
			//ͼƬ�ļ���
			this.left.setOnClickListener(on);
		}else {
			this.left.setVisibility(View.INVISIBLE);
		}
		if (right != -1 ) {//��ҪͼƬ����ʾ
			this.right.setVisibility(View.VISIBLE);
			this.right.setImageResource(right);
			//ͼƬ�ļ���
			this.right.setOnClickListener(on);
		}else {
			this.right.setVisibility(View.INVISIBLE);
		}
		this.middle.setText(middle);
		//����
		
	}
	
	
	
	
}
