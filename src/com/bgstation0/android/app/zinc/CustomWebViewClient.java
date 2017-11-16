// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.webkit.WebView;
import android.webkit.WebViewClient;

// カスタムウェブビュークライアントクラスCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// ロードするURLが変更された時.
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// Chromeなど既定のブラウザで開かないようにするにはfalseを返す.
		return false;	// falseを返す.
		
	}
	
}