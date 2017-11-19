// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Browser;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

// カスタムウェブビュークライアントクラスCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// メンバフィールドの初期化
	private static final String TAG = "CustomWebViewClient";	// TAGをCustomWebViewClientに初期化.
	Context mContext = null;	// Context型mContextをnullに初期化.
		
	// 引数付きコンストラクタ
	CustomWebViewClient(Context context){
			
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
			
	}
	
	// ページのロードが開始された時.
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon){
			
		// 既定の処理
		super.onPageStarted(view, url, favicon);	// 親クラスのonPageStartedを呼ぶ.

		// urlをログに出力.
		Log.d(TAG, "onPageStarted: url = " + url);	// Log.dでurlを出力.
		
		// mContextからActivityを取得し,　そこから各Viewを取得.
		if (mContext != null){	// mContextがnullでなければ.
			
			// URLバーに反映.
			Activity activity = (Activity)mContext;	// mContextをActivityにキャストし, activityに格納.
			Toast.makeText(activity, "onPageStarted: url = " + url, Toast.LENGTH_LONG).show();	// Toastでurlを表示.
			EditText etUrl = (EditText)activity.findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
			etUrl.setText(url);	// etUrl.SetTextでURLバーのetUrlにurlをセット.
			
		}
			
	}
		
	// ロードするURLが変更された時.
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// urlをログに出力.
		Log.d(TAG, "shouldOverrideUrlLoading: url = " + url);	// Log.dでurlを出力.
				
		// mContextからActivityを取得し,　そこから各Viewを取得.
		if (mContext != null){	// mContextがnullでなければ.
			Activity activity = (Activity)mContext;	// mContextをActivityにキャストし, activityに格納.
			Toast.makeText(activity, "shouldOverrideUrlLoading: url = " + url, Toast.LENGTH_LONG).show();	// Toastでurlを表示.
			EditText etUrl = (EditText)activity.findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
			etUrl.setText(url);	// etUrl.SetTextでURLバーのetUrlにurlをセット.
		}
				
		// Chromeなど既定のブラウザで開かないようにするにはfalseを返す.
		return false;	// falseを返す.
		
	}
	
	// ページのロードが終了した時.
	@Override
	public void onPageFinished(WebView view, String url){
		
		// 既定の処理
		super.onPageFinished(view, url);	// 親クラスのonPageFinishedを呼ぶ.

		// urlをログに出力.
		Log.d(TAG, "onPageFinished: url = " + url);	// Log.dでurlを出力.
			
		// mContextからActivityを取得し, それのgetContentResolverを使う.
		if (mContext != null){	// mContextがnullでなければ.
			
			// このURLを履歴に登録.
			Activity activity = (Activity)mContext;	// mContextをActivityにキャストし, activityに格納.
			Toast.makeText(activity, "onPageFinished: url = " + url, Toast.LENGTH_LONG).show();	// Toastでurlを表示.
			ContentValues values = new ContentValues();	// ContentValuesオブジェクトvaluesの生成.
			values.put(Browser.BookmarkColumns.TITLE, view.getTitle());	// values.putでview.getTitleで取得したタイトルを登録.
			values.put(Browser.BookmarkColumns.URL, url);	// values.putでurlを登録.
			values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// 現在時刻を登録.
			values.put(Browser.BookmarkColumns.BOOKMARK, "0");	// values.putでBOOKMARKフラグは"0"として登録.
			try{	// tryで囲む.
				activity.getContentResolver().insert(Browser.BOOKMARKS_URI, values);	// activity.getContentResolver().insertでvaluesを挿入.
			}
			catch (Exception ex){	// 例外のcatch.
				Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();	// ex.getMessageで取得した例外メッセージをToastで表示.
			}
					
		}
				
	}
	
}