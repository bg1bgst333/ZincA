// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	private static final String TAG = "CustomWebViewClient";	// TAGを"CustomWebViewClient"に初期化.
	private Context mContext = null;	// Context型mContextをnullに初期化.
	public MainApplication mApp = null;	// MainApplicationオブジェクトmAppをnullで初期化.
	private String mStartUrl = "";	// mStartUrlを""で初期化.
	//private String mFinishUrl = "";	// mFinishUrlを""で初期化.
	private int mCount = 0;	// mCountを""で初期化.
	public String mTag = "";	// mTagを""で初期化.
	
	// 引数付きコンストラクタ
	CustomWebViewClient(Context context){
			
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
		mApp = (MainApplication)mContext.getApplicationContext();	// getApplicationContextでmAppを取得.
		
	}
	
	// 引数付きコンストラクタ
	CustomWebViewClient(Context context, String tag){
				
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
		mApp = (MainApplication)mContext.getApplicationContext();	// getApplicationContextでmAppを取得.
		mTag = tag;	// mTagにtagをセット.
		
	}
	
	// ページのロードが開始された時.
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon){
			
		// 既定の処理
		super.onPageStarted(view, url, favicon);	// 親クラスのonPageStartedを呼ぶ.

		// urlをログに出力.
		Log.d(TAG, "onPageStarted: url = " + url);	// Log.dでurlを出力.
		Toast.makeText(this.mContext, "load: " + url, Toast.LENGTH_LONG).show();
		// URLバーにURLをセット.
		setUrl(url, mTag);	// setUrlでurlをセット.
		//setUrl(url);
		
		// プログレスバーを表示.
		setProgressBarVisible(true, mTag);	// setProgressBarVisible(true)で表示.
		//setProgressBarVisible(true);
		
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
		setUrl(url, mTag);	// setUrlでurlをセット.
		//setUrl(url);
		
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
		//Toast.makeText(mContext, "finished0", Toast.LENGTH_LONG).show();
		
		// タイトルの変更.
		//setActionBarTitle(view.getTitle());	// view.getTitleで取得したタイトルをsetActionBarTitleでセット.
		
		// 履歴登録条件を満たすかどうかを判定.
		if (url.equals(mStartUrl) && mCount == 0){	// 直近の開始URLで一番最初の時.
			addHistoryToDB(view, url);	// addHistoryToDBでurlを履歴に登録.
			//Toast.makeText(mContext, "finished", Toast.LENGTH_LONG).show();
			updateTabInfoToDBAndMap(view.getTitle(), url);	// タブ情報の更新.
			//SubActivity subActivity = (SubActivity)mContext;
			//mApp.changeTabTitle(view.getTitle(), subActivity.mTabName);
			//MainActivity mainActivity = (MainActivity)mContext;
			mApp.changeTabTitle(view.getTitle(), mTag);
		}
		mCount++;	// mCountを1増やす.
		
		// プログレスバーを非表示.
		setProgressBarVisible(false, mTag);	// setProgressBarVisible(false)で非表示.
		//setProgressBarVisible(false);
		setProgress(0, mTag);	// setProgressで進捗度を0にセット.
		//setProgress(0);
		
	}
	
	// URLバーにURLをセット.
	public void setUrl(String url){
		
		if (mContext != null){
			SubActivity subActivity = (SubActivity)mContext;
			subActivity.setUrlOmit(url);
		}
		
	}
	
	// URLバーにURLをセット.
	public void setUrl(String url, String tag){
		
		// mContextからMainActivityを取得し, MainActivityのURLバーにセット.
		if (mContext != null){	// mContextがnullでなければ.
					
			// URLバーに反映.
			//SubActivity subActivity = (SubActivity)mContext;	// mContextをSubActivityにキャストし, subActivityに格納.
			//subActivity.setUrlOmit(url);	// subActivity.setUrlOmitでURLバーにURLをセット.
			MainActivity mainActivity = (MainActivity)mContext;
			mainActivity.setUrlOmit(url, tag);
		}
				
	}
	
	// プログレスバーの表示/非表示をセット.
	public void setProgressBarVisible(boolean visible){
	
		if (mContext != null){
			SubActivity subActivity = (SubActivity)mContext;
			subActivity.setProgressBarVisible(visible);
		}
		
	}
	
	// プログレスバーの表示/非表示をセット.
	public void setProgressBarVisible(boolean visible, String tag){
		
		// mContextからMainActivityを取得し, MainActivityのプログレスバーにセット.
		if (mContext != null){	// mContextがnullでなければ.
							
			// プログレスバーに反映.
			// mContextをSubActivityにキャストし, subActivityに格納.
			//subActivity.setProgressBarVisible(visible);	// subActivity.setProgressBarVisibleにvisibleをセット.
			MainActivity mainActivity = (MainActivity)mContext;
			mainActivity.setProgressBarVisible(visible, tag);
			Toast.makeText(this.mContext, "visible = " + visible, Toast.LENGTH_LONG).show();
			
		}
				
	}
	
	// 進捗度のセット.
	public void setProgress(int progress){
			
		if (mContext != null){
			SubActivity subActivity = (SubActivity)mContext;
			subActivity.setProgressValue(progress);
		}
		
	}
	
	// 進捗度のセット.
	public void setProgress(int progress, String tag){
			
		// mContextからMainActivityを取得し, MainActivityのプログレスバーにセット.
		if (mContext != null){	// mContextがnullでなければ.
						
			// プログレスバーに反映.
			//SubActivity subActivity = (SubActivity)mContext;	// mContextをSubActivityにキャストし, subActivityに格納.
			//subActivity.setProgressValue(progress);	// subActivity.setProgressValueにprogressをセット.
			MainActivity mainActivity = (MainActivity)mContext;
			mainActivity.setProgressValue(progress, tag);
			
		}
			
	}
		
	// アクションバーのタイトルをセット.
    public void setActionBarTitle(String title){
    	
    	// mContextからMainActivityを取得し, MainActivityのアクションバーのタイトルにセット.
    	if (mContext != null){	// mContextがnullでなければ.
    	
    		// アクションバーのタイトルに反映.
    		//MainActivity mainActivity = (MainActivity)mContext;	// mContextをMainActivityにキャストし, mainActivityに格納.
    		MainActivity mainActivity = mApp.mMainActivity;
    		//mainActivity.setTitle(title);	// mainActivity.setTitleでtitleをセット.
    		
    		// 上記のsetTitleだけだと反映されない時があるので, アクションバーからもセットする.
    		ActionBar act = mainActivity.getActionBar();	// mainActivity.getActionBarでactを取得.
    		if (act != null){	// actがnullでなければ.
    			act.setTitle(title);	// act.setTitleでタイトルをセット.
    		}
    		
    	}
    	
    }
    
	// 履歴にURLを登録.(Rrowserクラス版.)
	public void addHistoryToBrowser(WebView view, String url){
		
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
			values.put(Browser.BookmarkColumns.VISITS, "1");	// values.putでVISITSは"1"として登録.
			try{	// tryで囲む.
				Uri uri = mainActivity.getContentResolver().insert(Browser.BOOKMARKS_URI, values);	// mainActivity.getContentResolver().insertでvaluesを挿入.(Uriオブジェクトuriに格納.)
				if (uri == null){	// 既に挿入されている場合, nullが返る模様.
					// VISITSを取りたい.
					String[] projection = new String[]{
							Browser.BookmarkColumns.URL,	// URL.
							Browser.BookmarkColumns.DATE,	// 日時.
							Browser.BookmarkColumns.VISITS	// 訪問回数?
					};
					int visits;	// 訪問回数.
					Cursor c = mainActivity.getContentResolver().query(Browser.BOOKMARKS_URI, projection, Browser.BookmarkColumns.URL + "=?", new String[]{url}, Browser.BookmarkColumns.DATE + " desc");	// 合致する行を取得.
					if (c.getCount() == 1){
						if (c.moveToFirst()){
							visits = c.getInt(c.getColumnIndex(Browser.BookmarkColumns.VISITS));	// visitsの取得.
							visits++;	// visitsを1増やす.
							values = new ContentValues();	// ContentValuesオブジェクトvaluesの生成.
							values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// 現在時刻を登録.
							String strVisits = String.valueOf(visits);	// visitsを文字列に変換.
							values.put(Browser.BookmarkColumns.VISITS, strVisits);	// 訪問回数を登録.
							int row = mainActivity.getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// mainActivity.getContentResolver().updateでURLが同じ行を更新.
							if (row <= 0){
								Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_errorに定義されたメッセージをToastで表示.
							}
							else{
								//Toast.makeText(mainActivity, "visits = " + strVisits, Toast.LENGTH_LONG).show();	// とりあえずvisitsを出力.
							}
						}
					}
					else{
						Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_errorに定義されたメッセージをToastで表示.
					}
				}
				else{
					//Toast.makeText(mainActivity, "visits = 1", Toast.LENGTH_LONG).show();	// とりあえずvisitsを1として出力.
				}
			}
			catch (Exception ex){	// 例外のcatch.
				Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_errorに定義されたメッセージをToastで表示.
			}
			
		}
		
	}
	
	// 履歴にURLを登録.(独自DB版.)
	public void addHistoryToDB(WebView view, String url){
		
		// URLとタイトルを取得.
		String title = view.getTitle();	// view.getTitleでタイトルを取得.
		long datemillisec = System.currentTimeMillis();	// System.currentTimeMillisで現在時刻を取得し, datemillisecに格納.
		
		// SubActivityにキャスト.
		//SubActivity subActivity = (SubActivity)mContext;	// mContextをSubActivityにキャストし, subActivityに格納.
		MainActivity mainActivity = (MainActivity)mContext;
		
		// このURLを履歴へ追加.
		long id = mApp.mHlpr.insertRowHistory(title, url, datemillisec);	// mApp.mHlpr.insertRowHistoryでtitle, url, datemillisecを追加.
		if (id == -1){	// -1なら.
			Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_errorに定義されたメッセージをToastで表示.
		}
	
	}
	
	// タブ情報を更新.(独自DBとマップ版.)
	public void updateTabInfoToDBAndMap(String title, String url){
		
		// SubActivityにキャスト.
		//SubActivity subActivity = (SubActivity)mContext;	// mContextをSubActivityにキャストし, subActivityに格納.
				
		// タブ情報の取得.
		TabInfo tabInfo = mApp.mHlpr.getTabInfo(mTag);
		if (tabInfo != null){
			tabInfo.title = title;
			tabInfo.url = url;
			tabInfo.date = System.currentTimeMillis();
			mApp.mHlpr.updateTabInfo(tabInfo.tabName, tabInfo);
			//Toast.makeText(subActivity, "tabInfo.url = " + tabInfo.url, Toast.LENGTH_LONG).show();
			TabInfo mapti = mApp.mTabMap.get(tabInfo.tabName);
			if (mapti != null){
				mapti.title = title;
				mapti.url = url;
				mapti.date = tabInfo.date;
			}
		}
		
	}
	
}