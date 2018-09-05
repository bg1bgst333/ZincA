package com.bgstation0.android.app.zinc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomTabWidget extends FrameLayout {

	// メンバフィールドの定義.
	LayoutInflater mInflater = null;	// mInflaterをnullで初期化.
	String mTag = "";	// mTagを""で初期化.
	View mView = null;	// mViewをnullで初期化.
	
	// コンストラクタ
	public CustomTabWidget(Context context){
		super(context);	// 親のコンストラクタ.
	}
	
	// コンストラクタ
	public CustomTabWidget(Context context, String title, String tag, View.OnClickListener listener){
		this(context);
		mInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		mView = mInflater.inflate(R.layout.tabwidget_custom, null);
		mTag = tag;
		TextView tv = (TextView)mView.findViewById(R.id.textview_title);
		tv.setText(title);
		ImageButton ib = (ImageButton)mView.findViewById(R.id.button_close);
		ib.setTag(mTag);
		ib.setOnClickListener(listener);
		addView(mView);
	}
	
}
