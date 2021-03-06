// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.R.integer;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

// メインアプリケーションクラスMainApplication
public class MainApplication extends Application {

	// メンバフィールドの初期化
	private static final String TAG = "MainApplication";	// TAGを"MainApplication"に初期化.
	public Map<String, TabInfo> mTabMap = null;	// タブmTabMapをnullで初期化.
	//public int mNextViewNo = 0;	// mNextViewNoを0に初期化.
	public UrlListDatabaseHelper mHlpr = null;	// UrlListDatabaseHelperオブジェクトmHlprをnullに初期化.
	TabHost mTabHost = null;	// mTabHostをnullにセット.
	ArrayList<String> mTabNameList = null;
	MainActivity mMainActivity = null;
	
	// アプリケーションが生成された時.
	@Override
	public void onCreate(){
		
		// Toastで"OnCreate"を表示.
		//Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();	// "onCreate"をToastで表示.
		Log.d(TAG, "onCreate");	// Log.dで"onCreate"を記録.
		
		// タブDBヘルパーの生成.
		mHlpr = new UrlListDatabaseHelper(this);	// UrlListDatabaseHelperにthisを渡して生成.
		
		// タブマップの生成.
		//mTabMap = new HashMap<String, TabInfo>();	// mTabMapをHashMapで作成.
		mTabMap = new LinkedHashMap<String, TabInfo>();
		//mNextViewNo = 0;	// mNextViewNoを0としておく.
		
		// タブネームリストの生成.
		mTabNameList = new ArrayList<String>();
		
	}
	
	// アプリケーションが終了した時.
	@Override
	public void onTerminate(){
		
		// Toastで"onTerminate"を表示.
		//Toast.makeText(this, "onTerminate", Toast.LENGTH_LONG).show();	// "onTerminate"をToastで表示.
		Log.d(TAG, "onTerminate");	// Log.dで"onTerminate"を記録.
		
	}
	
	// 使用できるメモリが少なくなった時.
	@Override
	public void onLowMemory(){
		
		// Toastで"onLowMemory"を表示.
		//Toast.makeText(this, "onLowMemory", Toast.LENGTH_LONG).show();	// "onLowMemory"をToastで表示.
		Log.d(TAG, "onLowMemory");	// Log.dで"onLowMemory"を記録.
		
	}
	
	// エントリーリストの取得.
	public List<Entry<String, TabInfo>> getTabMapEntryList(){
		
		// 日時でソート.
        List<Entry<String, TabInfo>> list_entries = new ArrayList<Entry<String, TabInfo>>(mTabMap.entrySet());	// エントリーリストの生成.
        Collections.sort(list_entries, new Comparator<Entry<String, TabInfo>>(){	// Collections.sortでソート.
        	public int compare(Entry<String, TabInfo> obj1, Entry<String, TabInfo> obj2){	// 比較関数compare.
        		TabInfo tabInfo1 = obj1.getValue();
        		TabInfo tabInfo2 = obj2.getValue();
        		if (tabInfo1.date < tabInfo2.date){
        			return 1;	// 1を返す.
        		}
        		else{
        			return -1;	// -1を返す.
        		}
        	}
        });
        return list_entries;	// list_entriesを返す.
        
	}
	
	// タブタイトルの変更.
	public void changeTabTitle(String title, String tag){
		if (mTabHost != null){
			TabWidget widget = mTabHost.getTabWidget();
			View v = mTabHost.findViewWithTag(tag);
			int i = mTabNameList.indexOf(tag);
			
			int c = widget.getChildCount();
			//Toast.makeText(this, "c = " + String.valueOf(c), Toast.LENGTH_LONG).show();
			//int i = mTabHost.getCurrentTab();
			View v2 = (View)widget.getChildAt(i);
			//View v = widget.findViewWithTag(tag);
			if (v == null){
				//Toast.makeText(this, "v == null: " + String.valueOf(i), Toast.LENGTH_LONG).show();
			}
			if (v2 != null){
				//TextView tv = (TextView)v2.findViewById(android.R.id.title);
				TextView tv = (TextView)v2.findViewById(R.id.textview_title);
				if (tv != null){
					tv.setText(title);
				}
			}
		}
	}
	
}