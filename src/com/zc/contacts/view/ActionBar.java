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
 * 		actionbar——导航条 ，自定义组合控件
 * @author Administrator zc
 *	因为导航条由三个控件组成，是一个水平的LinearLayout，用本类去继承LinearLayout。然后需要重写两个构造函数
 */
public class ActionBar extends LinearLayout{
	public static final int ID_NULL = -1;//如果传递是-1，表示不需要图片
	private ImageView left,right;
	private TextView middle;
	
	
	//XML文件中创建控件专用的构造函数
	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		LogUtil.printlog("MyActionBar", "XML中创建控件");
	//先加载布局到ActionBar的控件上   用View中的布局加载器
		/*  inflate(context, resource, root);
		*	resource——将一个布局文件
		*	root——加到哪里去		*/
	inflate(context, R.layout.actionbar, this);
		//从布局文件中找到三个控件
	left = (ImageView) findViewById(R.id.actionbar_left);
	right = (ImageView) findViewById(R.id.actionbar_right);
	middle = (TextView) findViewById(R.id.actionbar_middle);
	}
	
	
	//用代码创建布局的构造函数
	public ActionBar(Context context) {
		super(context);
		//TextView tv = new TextView(this);
		LogUtil.printlog("MyActionBar", "代码创建控件");
	}


	/**
	 *将控件数据传递到参数中，initActionBar函数初始化，最后设置到ActionBar控件上
	 */
	public void initActionBar(int left, String middle, int right,OnClickListener on) {
		if (left != -1 ) {//需要图片就显示
			this.left.setVisibility(View.VISIBLE);
			this.left.setImageResource(left);
			//图片的监听
			this.left.setOnClickListener(on);
		}else {
			this.left.setVisibility(View.INVISIBLE);
		}
		if (right != -1 ) {//需要图片就显示
			this.right.setVisibility(View.VISIBLE);
			this.right.setImageResource(right);
			//图片的监听
			this.right.setOnClickListener(on);
		}else {
			this.right.setVisibility(View.INVISIBLE);
		}
		this.middle.setText(middle);
		//设置
		
	}
	
	
	
	
}
