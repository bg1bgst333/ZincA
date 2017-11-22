// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Browser;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

// カスタムウェブビュークライアントクラスCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// メンバフィールドの初期化
	private static final String TAG = "CustomWebViewClient";	// TAGをCustomWebViewClientに初期化.
	private Context mContext = null;	// Context型mContextをnullに初期化.
	private String mStartUrl = "";	// mStartUrlを""で初期化.
	//private String mFinishUrl = "";	// mFinishUrlを""で初期化.
	private int mCount = 0;	// mCountを""で初期化.
	
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
		
		// URLバーにURLをセット.
		setUrl(url);	// setUrlでurlをセット.
		
		// プログレスバーを表示.
		setProgressBarVisible(true);	// setProgressBarVisible(true)で表示.
		
		// ロードを開始したURLを保持しておく.
		mStartUrl = url;	// mStartUrlにurlをセット.
		mCount = 0;	// mCountも0にしておく.
					
	}
		
	// ロードするURLが変更された時.
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// urlをログに出力.
		Log.d(TAG, "shouldOverrideUrlLoading: url = " + url);	// Log.dでurlを出力.
				
		// URLバーにURLをセット.
		setUrl(url);	// setUrlでurlをセット.
		
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
			
		// 履歴登録条件を満たすかどうかを判定.
		if (url.equals(mStartUrl) && mCount == 0){	// 直近の開始URLで一番最初の時.
			addHistory(view, url);	// addHistoryでurlを履歴に登録.
		}
		mCount++;	// mCountを1増やす.
		
		// プログレスバーを非表示.
		setProgressBarVisible(false);	// setProgressBarVisible(false)で非表示.
					
	}
	
	// URLバーにURLをセット.
	public void setUrl(String url){
		
		// mContextからMainActivityを取得し, MainActivityのURLバーにセット.
		if (mContext != null){	// mContextがnullでなければ.
					
			// URLバーに反映.
			MainActivity mainActivity = (MainActivity)mContext;	// mContextをMainActivityにキャストし, mainActivityに格納.
			mainActivity.setUrlOmit(url);	// mainActivity.setUrlOmitでURLバーにURLをセット.
			
		}
				
	}
	
	// プログレスバーの表示/非表示をセット.
	public void setProgressBarVisible(boolean visible){
		
		// mContextからMainActivityを取得し, MainActivityのプログレスバーにセット.
		if (mContext != null){	// mContextがnullでなければ.
							
			// プログレスバーに反映.
			MainActivity mainActivity = (MainActivity)mContext;	// mContextをMainActivityにキャストし, mainActivityに格納.
			mainActivity.setProgressBarVisible(visible);	// mainActivity.setProgressBarVisibleにvisibleをセット.
							
		}
				
	}
	
	// 履歴にURLを登録.
	public void addHistory(WebView view, String url){
		
		// mContextからMainActivityを取得し, それのgetContentResolverを使う.
		if (mContext != null){	// mContextがnullでなければ.
			
			// MainActivityにキャスト.
			MainActivity mainActivity = (MainActivity)mContext;	// mContextをMainActivityにキャストし, mainActivityに格納.
					
			// このURLを履歴に登録.
			ContentValues values = new ContentValues();	// ContentValuesオブジェクトvaluesの生成.
			values.put(Browser.BookmarkColumns.TITLE, view.getTitle());	// values.putでview.getTitleで取得したタイトルを登録.
			values.put(Browser.BookmarkColumns.URL, url);	// values.putでurlを登録.
			values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// 現在時刻を登録.
			values.put(Browser.BookmarkColumns.BOOKMARK, "0");	// values.putでBOOKMARKフラグは"0"として登録.
			try{	// tryで囲む.
				Uri uri = mainActivity.getContentResolver().insert(Browser.BOOKMARKS_URI, values);	// mainActivity.getContentResolver().insertでvaluesを挿入.(Uriオブジェクトuriに格納.)
				if (uri == null){	// 既に挿入されている場合, nullが返る模様.
					values = new ContentValues();	// ContentValuesオブジェクトvaluesの生成.
					values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// 現在時刻を登録.
					int row = mainActivity.getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// mainActivity.getContentResolver().updateでURLが同じ行を更新.
					if (row < 0){
						Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_errorに定義されたメッセージをToastで表示.
					}
				}
			}
			catch (Exception ex){	// 例外のcatch.
				Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_errorに定義されたメッセージをToastで表示.
			}
			
		}
		
	}
	
}