// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

// カスタムウェブビュークライアントクラスCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// メンバフィールドの初期化
	Context mContext = null;	// Context型mContextをnullに初期化.
		
	// 引数付きコンストラクタ
	CustomWebViewClient(Context context){
			
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
			
	}
		
	// ロードするURLが変更された時.
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// mContextからActivityを取得し,　そこから各Viewを取得.
		if (mContext != null){	// mContextがnullでなければ.
			Activity activity = (Activity)mContext;	// mContextをActivityにキャストし, activityに格納.
			EditText etUrl = (EditText)activity.findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
			etUrl.setText(url);	// etUrl.SetTextでURLバーのetUrlにurlをセット.
		}
				
		// Chromeなど既定のブラウザで開かないようにするにはfalseを返す.
		return false;	// falseを返す.
		
	}
	
}