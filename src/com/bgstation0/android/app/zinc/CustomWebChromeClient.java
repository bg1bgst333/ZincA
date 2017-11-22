// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

//カスタムウェブクロームクライアントクラスCustomWebChromeClient
public class CustomWebChromeClient extends WebChromeClient {
	
	// メンバフィールドの初期化
	private Context mContext = null;	// Context型mContextをnullに初期化.
	
	// 引数付きコンストラクタ
	CustomWebChromeClient(Context context){
				
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
			
	}
	
	// 進捗度が変化した時.
	@Override
	public void onProgressChanged(WebView view, int progress){
		
		// 進捗度をセット.
		setProgress(progress);	// setProgressで進捗度progressをセット.
		
	}
	
	// 進捗度のセット.
	public void setProgress(int progress){
		
		// mContextからMainActivityを取得し, MainActivityのプログレスバーにセット.
		if (mContext != null){	// mContextがnullでなければ.
					
			// プログレスバーに反映.
			MainActivity mainActivity = (MainActivity)mContext;	// mContextをMainActivityにキャストし, mainActivityに格納.
			mainActivity.setProgressValue(progress);	// mainActivity.setProgressValueにprogressをセット.
					
		}
		
	}
				
}