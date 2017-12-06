// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.HashMap;
import java.util.Map;
import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// メインアプリケーションクラスMainApplication
public class MainApplication extends Application {

	// メンバフィールドの初期化
	private static final String TAG = "MainApplication";	// TAGを"MainApplication"に初期化.
	public Map<String, TabInfo> mTabMap = null;	// タブmTabMapをnullで初期化.
	public int mNextViewNo = 0;	// mNextViewNoを0に初期化.
	
	// アプリケーションが生成された時.
	@Override
	public void onCreate(){
		
		// Toastで"OnCreate"を表示.
		//Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();	// "onCreate"をToastで表示.
		Log.d(TAG, "onCreate");	// Log.dで"onCreate"を記録.
		
		// タブマップの生成.
		mTabMap = new HashMap<String, TabInfo>();	// mTabMapをHashMapで作成.
		mNextViewNo = 0;	// mNextViewNoを0としておく.
		
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
	
}