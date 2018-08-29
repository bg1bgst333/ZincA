package com.bgstation0.android.app.zinc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends Activity {

	// メンバフィールドの定義.
	MainApplication mApp = null;	// mAppをnullにする.
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        
        // メインアプリケーションの取得.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContextで取得したMainApplicationオブジェクトをmAppに格納.
    	
        // Bundleをチェック.
        Bundle args = getIntent().getExtras();	// args取得.
        if (args != null){	// nullでない.
	        String tag = args.getString("tag");	// tag取得.
	        TextView tv = (TextView)findViewById(R.id.textview_sub);	// tv取得.
        	tv.setText(tag);	// tagをセット.
        	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
        	tabInfo.title = tag;
        	mApp.mHlpr.updateTabInfo(tag, tabInfo);
        }
    }
    
}