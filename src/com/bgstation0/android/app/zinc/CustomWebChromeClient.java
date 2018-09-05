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
	public String mTag = "";	// mTagを""で初期化.
	
	// 引数付きコンストラクタ
	CustomWebChromeClient(Context context){
				
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
			
	}
	
	// 引数付きコンストラクタ
	CustomWebChromeClient(Context context, String tag){
				
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
		mTag = tag;	// mTagにtagをセット.
		
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
			//SubActivity subActivity = (SubActivity)mContext;	// mContextをSubActivityにキャストし, subActivityに格納.
			//subActivity.setProgressValue(progress);	// subActivity.setProgressValueにprogressをセット.
			MainActivity mainActivity = (MainActivity)mContext;
			mainActivity.setProgressValue(progress, mTag);
			
		}
		
	}
				
}