// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

//メインアクティビティクラスMainActivity
public class MainActivity extends Activity implements OnClickListener, OnEditorActionListener{	// View.OnClickListener, TextView.OnEditorActionListenerインターフェースの追加.

	// メンバフィールドの初期化.
	public static final int REQUEST_CODE_BOOKMARK = 1001;	// REQUEST_CODE_BOOKMARKを1001とする.
	public static final int REQUEST_CODE_HISTORY = 1002;	// REQUEST_CODE_HISTORYを1002とする.
	public static final int REQUEST_CODE_TAB = 1003;	// REQUEST_CODE_TABを1003とする.
	public static final String SEARCH_URL_GOOGLE = "https://www.google.co.jp/search?q=";	// SEARCH_URL_GOOGLEを"https://www.google.co.jp/search?q="とする.
	public DownloadManager mDownloadManager = null;	// mDownloadManagerをnullで初期化.
	public String mPhoneUA = "";	// 電話用ユーザエージェントmPhoneUA.
	public String mPCUA = "";	// PC用ユーザエージェントmPCUA.
	public String mCurrentUA = "";	// 現在のユーザエージェントmCurrentUA.
	public static final String PC_WIN_UA_SUBSTRING = "(Windows NT 10.0; Win64; x64)";	// WindowsPCのユーザエージェントであることを示す部分.
	public MainApplication mApp = null;	// MainApplicationオブジェクトmAppをnullで初期化.
	public String mCurrentTabName = "";	// mCurrentTabNameを""で初期化.
	
	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_main);	// setContentViewでR.layout.activity_mainをセット.
        initUrlBar();	// initUrlBarでetUrlを初期化.
        initProgressBar();	// initProgressBarでprogressbarを初期化.
        initWebView();	// initWebViewでwebViewを初期化.
        initDownloadManager();	// initDownloadManagerでmDownloadManagerを初期化.
        //loadUrlFromIntent();	// loadUrlFromIntentでインテントで指定されたURLをロード.
        initMainApplication();	// initMainApplicationでメインアプリケーションを初期化.
        
    }
    
    // 既存のインスタンスが再利用され, インテントが飛んで来た時.
    @Override
    protected void onNewIntent(Intent intent){
    	
    	// OnNewIntentに来た事をトーストで表示.
    	Toast.makeText(this, "OnNewIntent", Toast.LENGTH_LONG).show();	// "OnNewIntent"とToastで表示.
    	
    }
    
    // バックキーが押された時.
    @Override
    public void onBackPressed(){
    	
    	// バックキーにおけるウェブビューの動作.
    	onBackPressedWebView();	// onBackPressedWebViewに任せる.
    	
    }
    
    // 起動したアクティビティの結果が返って来た時.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	// 既定の処理.
    	super.onActivityResult(requestCode, resultCode, data);	// 親クラスのonActivityResultを呼ぶ.
    	
    	// キャンセルの場合.
    	if (requestCode != REQUEST_CODE_TAB && resultCode == RESULT_CANCELED){	// requestCodeがREQUEST_CODE_TABではなく, resultCodeがRESULT_CANCELEDの場合.
    		return;	// 何もせず終了.
    	}
    	
    	// 起動したアクティビティが閉じた時の結果に対する処理.
    	Bundle bundle = null;	// Bundle型bundleをnullで初期化.
    	if (data != null){	// dataがnullでなければ.
    		bundle = data.getExtras();	// data.getExtrasでbundleを取得.
    	}
    	
    	// 処理の振り分け.
    	switch (requestCode){	// requestCodeごとに振り分け.
    	
    		// ブックマーク一覧.
    		case REQUEST_CODE_BOOKMARK:	// ブックマークの一覧から戻ってきた場合.
    		// 履歴一覧.
    		case REQUEST_CODE_HISTORY:	// 履歴の一覧から戻ってきた場合.
    			
    			// ブックマークおよび履歴のアイテムが選択された時.
    			if (resultCode == RESULT_OK){	// RESULT_OKの場合.
    				if (bundle != null){	// bundleがnullでなければ.
    					loadSelectedUrl(bundle);	// loadSelectedUrlでbundleで渡したURLをロード.
    				}
    			}
    			
    			// 抜ける.
    			break;	// breakで抜ける.
    		
    		// タブ一覧.
    		case REQUEST_CODE_TAB:	// タブ一覧から戻ってきた場合.
    			
    			// タブのアイテムが選択されたとき.
    			if (resultCode == RESULT_OK){	// RESULT_OKの場合.
    				if (bundle != null){	// bundleがnullでなければ.
    					String tabName = bundle.getString("tabName");	// bundleからtabNameを取得.
    					String title = bundle.getString("title");	// bundleからtitleを取得.
    					if (mApp.mTabMap.containsKey(tabName)){	// タブマップにある場合.
    						setContentViewByTabName(tabName, title);	// setContentViewByTabNameでtabNameからビューをセット.(タイトルもセットしておく.)
    					}
    					else{	// マップには無い場合.
    						// タブDBからの取得.
    						TabInfo tabInfo = mApp.mHlpr.getTabInfo(tabName);	// tabNameからtabInfo取得
    						if (tabInfo != null){	// DBにはある場合.
    							// メインアクティビティを起動する.(タブを復元する.)
	    				    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
	    				    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassNameで".MainActivity"をセット.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// これだと起動するアクティビティ以外は破棄される.
	    				    	intent.putExtra("LaunchMode", "RestoreTab");	// "LaunchMode"をキー, "RestoreTab"を値として登録.
	    				    	intent.putExtra("TabName", tabInfo.tabName);	// "TabName"でtabinfo.tabNameを登録.
	    				    	intent.putExtra("Url", tabInfo.url);	// "Url"をキーにして, tabInfo.urlを送る.
	    				    	startActivity(intent);	// startActivityにintentを渡す.
    						}
    						else{	// DBにもない場合.(ここにくることはない.)
	    						// メインアクティビティを起動する.(タブの新規作成する.)
	    				    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
	    				    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassNameで".MainActivity"をセット.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// これだと起動するアクティビティ以外は破棄される.
	    				    	intent.putExtra("LaunchMode", "NewTab");	// "LaunchMode"をキー, "NewTab"を値として登録.
	    				    	startActivity(intent);	// startActivityにintentを渡す.
    						}
    					}
    				}
    			}
    			else if (resultCode == RESULT_CANCELED){	// RESULT_CANCELEDの場合.
    				if (!mApp.mTabMap.containsKey(mCurrentTabName)){	// 表示していたタブが消えた時.
    					// 直近タブかどうか.
    					TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();	// 直近のタブを取得.
    					if (tabInfo != null){	// 1つはみつかった.
	    					if (mApp.mTabMap.containsKey(tabInfo.tabName)){	// マップにある場合.
	     				        setContentViewByTabName(tabInfo.tabName, tabInfo.title);	// tabInfoのビューをセット.
	    					}
	    					else{	// マップに無い.
	    						// メインアクティビティを起動する.(タブを復元する.)
	    				    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
	    				    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassNameで".MainActivity"をセット.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// これだと起動するアクティビティ以外は破棄される.
	    				    	intent.putExtra("LaunchMode", "RestoreTab");	// "LaunchMode"をキー, "RestoreTab"を値として登録.
	    				    	intent.putExtra("TabName", tabInfo.tabName);	// "TabName"でtabinfo.tabNameを登録.
	    				    	intent.putExtra("Url", tabInfo.url);	// "Url"をキーにして, tabInfo.urlを送る.
	    				    	startActivity(intent);	// startActivityにintentを渡す.
	    					}
    					}
    					else{	// 1つもない.
    						// メインアクティビティを起動する.(タブの新規作成する.)
    				    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
    				    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassNameで".MainActivity"をセット.
    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// これだと起動するアクティビティ以外は破棄される.
    				    	intent.putExtra("LaunchMode", "NewTab");	// "LaunchMode"をキー, "NewTab"を値として登録.
    				    	startActivity(intent);	// startActivityにintentを渡す.
    					}
    				}
    			}
    			
    			// 抜ける.
    			break;	// breakで抜ける.
    			
    		// それ以外の時.
    		default:
    			
    			break;	// breakで抜ける.
    			
    	}
    	
    }
    
    // メニューが作成された時.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	// リソースからメニューを作成.
    	getMenuInflater().inflate(R.menu.menu_main, menu);	// getMenuInflater().inflateでR.menu.menu_mainからメニューを作成.
    	return true;	// trueを返す.
    	
    }
    
    // メニューが選択された時.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	// 選択されたメニューアイテムごとに振り分ける.
    	int id = item.getItemId();	// item.getItemIdで選択されたメニューアイテムのidを取得.
    	if (id == R.id.menu_item_new_tab){	// R.id.menu_item_new_tab("新しいタブ")の時.
    		
    		//　新しいタブの追加.
    		addTab();	// addTabで追加.
    		
    	}
    	else if (id == R.id.menu_item_tabs_show){	// R.id.menu_item_tabs_show("タブ一覧の表示")の時.

    		// タブ一覧の表示.
    		showTabs();	// showTabsで追加.
    		
    	}
    	else if (id == R.id.menu_item_bookmark_add){	// R.id.menu_item_bookmark_add("ブックマークの追加")の時.

    		// ブックマークの追加.
    		addBookmarkToDB();	// addBookmarkToDBで追加.
    		
    	}
    	else if (id == R.id.menu_item_bookmark_show){	// R.id.menu_item_bookmark_show("ブックマークの一覧")の時.
    	
    		// ブックマークの表示.
    		showBookmark();	// showBookmarkで表示.
    		
    	}
    	else if (id == R.id.menu_item_history_show){	// R.id.menu_item_history_show("履歴の一覧")の時.
    		
    		// 履歴の表示.
    		showHistory();	// showHistoryで表示.
    		
    	}
    	else if (id == R.id.menu_item_download){	// R.id.menu_item_download("ダウンロード")の時.
    		
    		// ダウンロード.
    		download();	// downloadでダウンロード.
    		
    	}
    	else if (id == R.id.menu_item_pc_site_browser){	// R.id.menu_item_pc_site_browser("PCサイトブラウザ")の時. 

    		// 表示の切り替え.
    		changePhonePCSite(item);	// changePhonePCSiteにitemを渡す.
    		
    	}
    	
    	// あとは既定の処理に任せる.
    	return super.onOptionsItemSelected(item);	// 親クラスのonOptionsItemSelectedを呼ぶ.
    	
    }
    
    // ボタンが押された時.
    @Override
    public void onClick(View v){	// OnClickListener.onClickをオーバーライド.
    	
    	// ボタンごとに振り分ける.
    	switch (v.getId()){	// v.getId()でView(Button)のidを取得.
    			
    		// それ以外の時.
    		default:
    			
    			// 抜ける.
    			break;	// breakで抜ける.
    	
    	}
    	
    }
    
    // エディットテキストでEnterキーが押された時.
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
    	
    	// Enterキーが押された時.
    	if (actionId == EditorInfo.IME_ACTION_DONE){	// Enterキーが押された時.(IME_ACTION_DONE)
    		
    		// ソフトウェアキーボードの非表示.
    		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);	// getSystemServiceからinputMethodManagerを取得.
    		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);	// inputMethodManager.hideSoftInputFromWindowで非表示.
    		
    		// URLのロード.
    		loadUrl();	// loadUrlでURLバーのURLをロード.
    	
    		// trueを返す.
    		return true;	// returnでtrueを返す.
    		
    	}
    	
    	// falseを返す.
    	return false;	// returnでfalseを返す.
    	
    }
    
    // URLバーの初期化.
    public void initUrlBar(){
    	
    	// etUrlを取得し, OnEditorActionListenerとして自身(this)をセット.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListeneでthis(自身)をセット.
    	
    }
    
    // プログレスバーの初期化.
    public void initProgressBar(){
    	
    	// progressBarを取得し, 最初は非表示にしておく.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibilityで非表示にする.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibilityで非表示(View.GONE)にする.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibilityで最初から表示にする.
    	
    }
    
    // ウェブビューの初期化.
    public void initWebView(){
    	
    	// webViewの取得.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
        // JavaScript有効化.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabledでJavaScriptを有効にする.
        // デフォルトのユーザエージェントを取得.
        mPhoneUA = webView.getSettings().getUserAgentString();	// webView.getSettings().getUserAgentStringで取得したUAをmPhoneUAに格納.(最初は電話用と思われるので, mPhoneUAに格納.)
        //Toast.makeText(this, mPhoneUA, Toast.LENGTH_LONG).show();	// mPhoneUAをToastで表示.
        mPCUA = generatePCUserAgentString(mPhoneUA);	// mPhoneUAからPC用ユーザエージェント文字列を生成.
        //Toast.makeText(this, mPCUA, Toast.LENGTH_LONG).show();	// mPCUAをToastで表示.
        mCurrentUA = mPhoneUA;	// 現在のユーザエージェントをmPhoneUAとする.
        // CustomWebViewClientのセット.
        webView.setWebViewClient(new CustomWebViewClient(this));	// newで生成したCustomWebViewClientオブジェクト(コンストラクタの引数にthisを渡す.)をwebView.setWebViewClientでセット.
        // CustomWebChromeClientのセット.
        webView.setWebChromeClient(new CustomWebChromeClient(this));	// newで生成したCustomWebChromeClientオブジェクト(コンストラクタの引数にthisを渡す.)をwebView.setWebChromeClientでセット.
        
    }
    
    // ダウンロードマネージャーの初期化.
    public void initDownloadManager(){
    	
    	// DownloadManagerの取得.
    	if (mDownloadManager == null){	// mDownloadManagerがnullの時.
    		mDownloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);	// getSystemServiceでmDownloadManagerを取得.
    	}
    	
    }
    
    // メインアプリケーションの初期化.
    public void initMainApplication(){
    
    	// メインアプリケーションの取得.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContextで取得したMainApplicationオブジェクトをmAppに格納.
    	//String tabName = getTabNameFromIntent();	// tabName取得.
    	//if (tabName == null){	// tabNameは指定されていない.
    	//registTabMap();	// registTabMapで追加.
    	String launchMode = getTabLaunchModeFromIntent();	// getTabLaunchModeFromIntentで起動モード取得.
    	if (launchMode != null && launchMode.equals("NewTab")){
    		registTab();	// registTabで新規タブを登録.
    	}
    	else if (launchMode != null && launchMode.equals("RestoreTab")){
    		String tabName = getIntent().getExtras().getString("TabName");	// tabName取得.
    		TabInfo tabInfo = mApp.mHlpr.getTabInfo(tabName);	// tabNameからtabInfo取得.
    		loadTab(tabInfo);	// loadTabでDB上のタブを復元.
    	}
    	else{
    		TabInfo lastTabInfo = mApp.mHlpr.getLastTabInfo();	// 直近のタブを取得.
    		if (lastTabInfo == null){	// lastTabInfoがnullの時.
    			registTab();	// registTabで新規タブを登録.
    		}	
    		else{
    			loadTab(lastTabInfo);	// loadTabでDB上のタブを復元.
    		}
    	}
    	
    	//}
    	//else{	// tabNameがある場合.
    	//	setContentViewByTabName(tabName, "Zinc");	// setContentViewByTabNameでビューをセット.(タイトルは"Zinc"にしておく.)
    	//}
    	
    }
    
    // タブ名を付けて起動された場合のタブ名を取得.
    public String getTabNameFromIntent(){
    	
    	// インテントにセットされている起動タブ名を返す.
    	Intent intent = getIntent();	// getIntentでintent取得.
    	String tabName = intent.getStringExtra("tabName");	// intent.getStringExtraでtabName取得.
    	return tabName;	// returnでtabNameを返す.
    	
    }
    
    // タブの起動モードを取得.
    public String getTabLaunchModeFromIntent(){
    	
    	// インテントにセットされた起動モードを返す.
    	Intent intent = getIntent();
    	String launchMode = intent.getStringExtra("LaunchMode");	// intent.getStringExtraで"LaunchMode"を取得.
    	return launchMode;	// returnでlaunchModeを返す.
    	
    }
    
    // タブ名とビューのペアをタブマップに登録.
    public void registTabMapx(){
    
    	// 現在のタブを新規作成し, ビューマップに追加.
    	//mCurrentTabName = "web" + String.valueOf(mApp.mNextViewNo);	// 現在のタブ名を新規作成.
		View rootView = getWindow().getDecorView();	// getWindow().getDecorViewでrootViewを取得.
		View content = rootView.findViewById(R.id.layout_main);	// rootViewからlayout_mainを抜き出す.
		TabInfo tabInfo = new TabInfo();	// tabInfoを作成.
		tabInfo.tabName = mCurrentTabName;	// tabNameをセット.
		tabInfo.title = "";	// とりあえず空.
		tabInfo.url = "";	// とりあえず空.
		tabInfo.date = System.currentTimeMillis();	// 現在時刻をセット.
		tabInfo.view = content;	//contentをセット.
		mApp.mTabMap.put(mCurrentTabName, tabInfo);	// tabInfoを登録.
		//mApp.mNextViewNo++;	// mApp.mNextViewNoを増やす.
		
    }
    
    // タブの登録.
    public void registTab(){
    	
    	// DB側の更新.
    	long datemillisec = System.currentTimeMillis();	// datemillisecを取得.
    	long id = mApp.mHlpr.insertRowTab("", "", "", datemillisec);	// mApp.mHlpr.insertRowTabでまずは空のタブ名で登録し, idを取得.
    	String currentTabName = "web" + String.valueOf(id);	// 現在のタブ名を作成.
    	boolean b = mApp.mHlpr.updateTabName(id, currentTabName);	// タブ名の更新.
    	//boolean c;
    	//c = b;
    	
    	// マップ側の更新.
    	View rootView = getWindow().getDecorView();	// getWindow().getDecorViewでrootViewを取得.
		View content = rootView.findViewById(R.id.layout_main);	// rootViewからlayout_mainを抜き出す.
		TabInfo tabInfo = new TabInfo();	// tabInfoを作成.
		tabInfo.tabName = currentTabName;	// tabNameをセット.
		tabInfo.title = "";	// とりあえず空.
		tabInfo.url = "";	// とりあえず空.
		tabInfo.date = datemillisec;	// 現在時刻をセット.
		tabInfo.view = content;	//contentをセット.
		mApp.mTabMap.put(currentTabName, tabInfo);	// tabInfoを登録.
		mCurrentTabName = currentTabName;	// mCurrentTabNameにセット.
		
    }
    
    // タブ情報からタブを生成.
    public void loadTab(TabInfo tabInfo){
    	
    	// マップ側の更新.
    	View rootView = getWindow().getDecorView();	// getWindow().getDecorViewでrootViewを取得.
		View content = rootView.findViewById(R.id.layout_main);	// rootViewからlayout_mainを抜き出す.
		tabInfo.date = System.currentTimeMillis();	// 現在時刻をセット.
		tabInfo.view = content;	//contentをセット.
		setUrlOmit(tabInfo.url);	// tabInfo.urlをsetUrlOmitでセット.
		if (!tabInfo.url.equals("")){	// ""でなければ.
			loadUrl();	// loadUrlでロード.
		}
		mCurrentTabName = tabInfo.tabName;	// tabInfo.tabNameをセット.
		mApp.mTabMap.put(mCurrentTabName, tabInfo);	// tabInfoを登録.
		
    }
    
    // タブ名から取得したビューをセット.
    public void setContentViewByTabName(String tabName, String title){
    
    	// tabNameからcontentを取得し, セット.
    	TabInfo tabInfo = mApp.mTabMap.get(tabName);	// mApp.mTabMap.getでtabInfoを取得.
    	if (tabInfo != null){	// tabInfoがnullでなければ.
    		View content = tabInfo.view;	// tabInfo.viewをcontentにセット.
    		ViewGroup vg = (ViewGroup)content.getParent();	// content.getParentでいったん親のViewGroup取得.
			if (vg != null){	// nullでなければ.(nullの場合で落ちてたので, nullの場合は削除しない.)
				vg.removeView(content);	// removeViewしろとエラーが出たので, vg.removeViewで削除.
			}
			setContentView(content);	// setContentViewでcontentをセット.
			initUrlBar();	// initUrlBarでetUrlを初期化.
	        initProgressBar();	// initProgressBarでprogressbarを初期化.
	        initWebView();	// initWebViewでwebViewを初期化.
	        if (title.equals("")){	// titleが空.
	        	setActionBarTitle(tabInfo.title);	// setActionBarTitleでtabInfo.titleをセット.
	        }
	        else{
	        	setActionBarTitle(title);	// setActionBarにtitleをセット.
	        }
			mCurrentTabName = tabName;	// 現在のタブ名とする.
    	}
    	/*
		else{
			//registTabMap();	// 無い時は生成.
			registTab();	// registTabで新規タブを登録.
		}
		*/
		
    }
    
    // URLバーにURLをセット.
    public void setUrl(String url){
    	
    	// etUrlを取得し, urlをセット.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    	if (etUrl == null){
    		Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
    	}
    	else{
    		etUrl.setText(url);	// etUrl.SetTextでURLバーのetUrlにurlをセット.
    	}
    }
    
    // URLバーにURLをセットする時に"http"の場合は省略する.
    public void setUrlOmit(String url){
    	
    	// 先頭文字列から省略するかを判定.
    	if (url.startsWith("http://")){	// "http"の時.
    		String omitUrl = url.substring(7);	// url.substringで7文字目からの文字列をomitUrlに返す.
    		setUrl(omitUrl);	// setUrlでomitUrlをセット.
    	}
    	else{
    		setUrl(url);	// setUrlでそのままurlを渡す.
    	}
    	
    }
    
    // URLバーからURLを取得.
    public String getUrl(){
    	
    	// etUrlのURLを取得.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    	return etUrl.getText().toString();	// etUrl.getText().toStringでURLを返す.
    	
    }
    
    // ウェブビューからURLを取得.
    public String getWebUrl(){
    	
    	// webViewのURLを取得.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
    	return webView.getUrl();	// returnでwebView.getUrlで取得したURLを返す.
    	
    }
    
    // ダウンロード時に終端が'/'の場合, "index.html"を補完してURLを返す.
    public String getDownloadUrl(){
    	
    	// URLの取得.
    	String url = getWebUrl();	// getWebUrlでurlを取得.
    	
    	// '/'で終わっていたら, "index.html"を付加.
    	if (url.charAt(url.length() - 1) == '/'){	// 最後が'/'の場合.
    		url = url + "index.html";	// urlに"index.html"を付加する.
    	}
    	
    	// urlを返す.
    	return url;	// returnでurlを返す.
    	
    }
    
    // ダウンロードURLのファイル名の部分だけ取得.
    public String getDownloadFileName(String url){
    	
    	// Uriの生成.
    	Uri downloadUri = Uri.parse(url);	// Uri.parseでurlをパースしてdownloadUriに格納.
    	String downloadFileName = downloadUri.getLastPathSegment();	// downloadUri.getLastPathSegmentでファイル名部分だけを取得し, downloadFileNameに格納.
    	return downloadFileName;	// returnでdownloadFileNameを返す.
    	
    }
    
    // URLか検索キーワードかを判定する.
    public boolean isUrl(String url){
    	
    	// '.'があって, かつ, ' 'がない.
    	if (url.contains(".") && !url.contains(" ")){	// url.contains(".")がtrue, かつ, url.contains(" ")がfalseの時.
    		return true;	// URLの場合, trueを返す.
    	}
    	else{
    		return false;	// 検索キーワードの場合, falseを返す.
    	}
    	
    }
    
    // Google検索URLを生成する.
    public String generateSearchUrlGoogle(String url){
    
    	// URLエンコード.
    	String encoded = null;	// String型encodedをnullに初期化.
    	try{	// tryで囲む.
    		encoded = URLEncoder.encode(url, "utf-8");	// URLEncoder.encodeでurlをutf-8に変換し, encodedに格納.
    	}
    	catch (Exception ex){	// catchで例外のキャッチ.
    		encoded = "";	// encodedに""をセット.
    		return encoded;	// encodedを返す.
    	}
    	
    	// 先頭にGoogle検索URLを付加.
    	String searchUrl = SEARCH_URL_GOOGLE + encoded;	// searchUrlにSEARCH_URL_GOOGLEとencodedを連結したものをセット.
    	return searchUrl;	// searchUrlを返す.
    	
    }
    
    //　URLバーのURLを検索キーワードかどうかを判定してから, 適切にロード.
    public void loadUrl(){
    	
    	// URLか検索キーワードかを判定する.
    	String url = getUrl();	// URLバーから, getUrlでurlを取得.
    	if (isUrl(url)){	// isUrlでurlがURLの場合.
    		// urlをロード.
        	loadUrlComplement(url);	// urlをloadUrlComplement()でロード.
    	}
    	else{	// 検索キーワードの場合.
    		String searchUrl = generateSearchUrlGoogle(url);	// searchUrlにgenerateSearchUrlGoogleで生成したURLをセット.
    		loadUrlComplement(searchUrl);	// searchUrlをloadUrlComplement()でロード.
    	}
    	
    }
    
    // 指定されたURLをロード.
    public void loadUrl(String url){
    	
    	// webViewを取得し, urlをロード.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
		webView.loadUrl(url);	// webView.loadUrlでurlの指すWebページをロード.
		
    }
    
    // 指定されたURLを"http"を補完してロード.
    public void loadUrlComplement(String url){
    	
    	// 先頭文字列から補完するかを判定.
    	if (url.startsWith("https://") || url.startsWith("http://")){	// "https"または"http"の時.
    		loadUrl(url);	// loadUrl(String url)をそのまま呼ぶ.
    	}
    	else{
    		String complementUrl = "http://" + url;	// 先頭に"http://"を付加.
    		loadUrl(complementUrl);	// // loadUrl(String url)にcomplementUrlを渡す.
    	}
    	
    }
    
    // 選択されたURLをロード.
    public void loadSelectedUrl(Bundle bundle){
    	
    	// bundleからURLを取得しロード.
    	String url = bundle.getString("url");	// bundle.getStringでurlを取得.
		setUrlOmit(url);	// setUrlOmitでURLバーにURLをセット.
		loadUrl();	// loadUrlでURLバーのURLをロード.
		
    }
    
    // インテントで指定されたURLをロード.
    public void loadUrlFromIntent(){
    	
    	// 起動時のインテントからURLを取得し, それを使ってロード.
        Intent intent = getIntent();	// getIntentでintentを取得.
        String action = intent.getAction();	// intent.getActionでactionを取得.
        String schema = intent.getScheme();	// intent.getSchemaでschemaを取得.
        String url = intent.getDataString();	// intent.getDataStringでurlを取得.
        if (action != null && action.equals(Intent.ACTION_VIEW) && (schema.equals("http") || schema.equals("https"))){	// ACTION_VIEWでhttpまたはhttpsの時.
        	setUrlOmit(url);	// setUrlOmitでURLバーにURLをセット.
    		loadUrl();	// loadUrlでURLバーのURLをロード.
        }
        
    }
    
    // プログレスバーに進捗度をセット.
    public void setProgressValue(int progress){
    	
    	// プログレスバーを取得し, 指定された進捗度をセット.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
    	progressBar.setProgress(progress);	// progressBar.setProgressでprogressをセット.
    	
    }
    
    // プログレスバーの表示/非表示をセット.
    public void setProgressBarVisible(boolean visible){
    	
    	// プログレスバーを取得し, 表示/非表示をセット.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
    	if (visible){	// trueなら表示.
    		progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibilityでVISIBLE.
    	}
    	else{	// falseなら非表示.
    		//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibilityでINVISIBLE.
    		progressBar.setVisibility(View.GONE);	// progressBar.setVisibilityでGONE.
    	}
    	
    }
    
    // アクションバーのタイトルをセット.
    public void setActionBarTitle(String title){
    	
    	// アクションバーを取得し, 指定されたタイトルをセット.
    	getActionBar().setTitle(title);	// getActionBar().setTitleでtitleをセット.
    	
    }
    
    // バックキーが押された時のWebViewの動作.
    public void onBackPressedWebView(){
    	
    	// 戻れる場合は, 1つ前のページに戻る.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
    	if (webView.canGoBack()){	// バック可能な場合.
    		webView.goBack();	// webView.goBackで戻る.
    	}
    	else{	// そうでない時.
    		super.onBackPressed();	// 親クラスのonBackPressedを呼ぶ.
    	}
    	
    }
    
    // タブの追加.
    public void addTab(){
    	
    	// 現在のタブをマップに保存.
    	saveTabState();	// saveTabStateで保存.
    	
    	// メインアクティビティを起動する.
    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassNameで".MainActivity"をセット.
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// これだと起動するアクティビティ以外は破棄される.
    	intent.putExtra("LaunchMode", "NewTab");	// "LaunchMode"をキー, "NewTab"を値として登録.
    	startActivity(intent);	// startActivityにintentを渡す.
    	
    }
    
    // タブ状態の保存.
    public void saveTabState(){
    	
    	// 現在のタブをマップに保存.
    	View rootView = getWindow().getDecorView();	// getWindow().getDecorViewでrootViewを取得.
		View content = rootView.findViewById(R.id.layout_main);	// rootViewからlayout_mainを抜き出す.
		TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);	// tabInfo取得.
		/*
		if (tabInfo == null){	// タブが無い.
			tabInfo = new TabInfo();	// 生成.
			tabInfo.view = content;	// contentを保存.
			tabInfo.tabName = mCurrentTabName;	// 現在のタブ名をセット.
			tabInfo.title = getActionBar().getTitle().toString();	// タイトルを取得.(アクションバーから取得が確実かも.)
			tabInfo.url = getUrl();
			tabInfo.date = System.currentTimeMillis();	// 現在日時をセット.
			mApp.mTabMap.put(mCurrentTabName, tabInfo);	// mCurrentTabNameをキーにtabInfoを登録.
		}
		else{
		*/
		tabInfo.view = content;	// contentを保存.
		tabInfo.tabName = mCurrentTabName;	// 現在のタブ名をセット.
		tabInfo.title = getActionBar().getTitle().toString();	// タイトルを取得.(アクションバーから取得が確実かも.)
		tabInfo.url = getUrl();
		tabInfo.date = System.currentTimeMillis();	// 現在日時をセット.
		mApp.mTabMap.put(mCurrentTabName, tabInfo);	// mCurrentTabNameをキーにtabInfoを登録.
		//}
		
		// DBにも保存.
		mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);	// updateTabInfoでtabInfoを更新.
		
    }
    
    // タブ一覧の表示.
    public void showTabs(){
    	
    	// 現在のタブをマップに保存.
    	saveTabState();	// saveTabStateで保存.
    	
    	// タブスアクティビティを起動する.
    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
    	intent.setClassName(packageName, packageName + ".TabsActivity");	// intent.setClassNameで".TabsActivity"をセット.
    	startActivityForResult(intent, REQUEST_CODE_TAB);	// startActivityForResultにintentとREQUEST_CODE_TABを渡す. 			
    			
    }
    
    // ブックマークへの追加.(Browserクラス版.)
    public void addBookmarkToBrowser(){
    	
    	// webViewを取得し, URLとタイトルを取得.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
		String title = webView.getTitle();	// webView.getTitleでタイトルを取得.
		String url = webView.getUrl();	// webView.getUrlでURLを取得.
		
		// このURLをブックマークに登録.
		ContentValues values = new ContentValues();	// ContentValuesオブジェクトvaluesの生成.
		values.put(Browser.BookmarkColumns.BOOKMARK, "1");	// values.putでBOOKMARKフラグは"1"として登録.
		try{	// tryで囲む.
			int row = getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// getContentResolver().updateでURLが同じ行を更新.
			if (row <= 0){	// 更新失敗.
				Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_errorに定義されたメッセージをToastで表示.
			}
			else{
				Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_successに定義されたメッセージをToastで表示.
			}
		}
		catch (Exception ex){	// 例外のcatch.
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_errorに定義されたメッセージをToastで表示.
		}
		
    }
    
    // ブックマークへの追加.(独自DB版.)
    public void addBookmarkToDB(){
    	
    	// webViewを取得し, URLとタイトルを取得.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
		String title = webView.getTitle();	// webView.getTitleでタイトルを取得.
		String url = webView.getUrl();	// webView.getUrlでURLを取得.
		long datemillisec = System.currentTimeMillis();	// System.currentTimeMillisで現在時刻を取得し, datemillisecに格納.
		
		// このURLをブックマークへ追加.
		mApp.mHlpr.insertRowBookmark(title, url, datemillisec);	// mApp.mHlpr.insertRowBookmarkでtitle, url, datemillisecを追加.
		
    }
    
    // ブックマークの表示.
    public void showBookmark(){
    	
    	// ブックマークアクティビティを起動する.
		String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
		Intent intent = new Intent();	// Intentオブジェクトintentを作成.
		intent.setClassName(packageName, packageName + ".BookmarkActivity");	// intent.setClassNameで".BookmarkActivity"をセット.
		startActivityForResult(intent, REQUEST_CODE_BOOKMARK);	// startActivityForResultにintentとREQUEST_CODE_BOOKMARKを渡す.
		
    }
    
    // 履歴の表示.
    public void showHistory(){
    	
    	// ヒストリーアクティビティを起動する.
		String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
		Intent intent = new Intent();	// Intentオブジェクトintentを作成.
		intent.setClassName(packageName, packageName + ".HistoryActivity");	// intent.setClassNameで".HistoryActivity"をセット.
		startActivityForResult(intent, REQUEST_CODE_HISTORY);	// startActivityForResultにintentとREQUEST_CODE_HISTORYを渡す.
		
    }
    
    // ダウンロードリクエストの作成.
    public Request createDownloadRequest(Uri downloadUri, String downloadFileName){
    	
    	// ダウンロードリクエストを作成する.
    	Request request = new Request(downloadUri);	// downloadUriを指定して, requestを作成.
    	request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "/" + downloadFileName);	// request.setDestinationInExternalFilesDirでダウンロード先を指定.
    	request.setTitle("Zinc - ダウンロード");		// "Zinc - ダウンロード"をセット.
    	request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);	// ネットワークタイプはモバイルとWiFi両方.
    	request.setMimeType("application/octet-stream");	// MIMEタイプは"application/octet-stream".
    	return request;	// requestを返す.
    	
    }
    
    // ダウンロード.
    public void download(){
    	
    	// ダウンロードURIの取得.
    	Uri downloadUri = Uri.parse(getWebUrl());	// getWebUrlで取得した生URLをUri.parseでパースしてdownloadUriを取得.
    	
    	// ダウンロードファイル名の取得.
    	String downloadFileName = getDownloadFileName(getDownloadUrl());	// getDownloadUrlの補完済みURLからgetDownloadFileNameでファイル名部分だけ取り出す.
    	
    	// ダウンロードリクエストの作成.
    	Request request = createDownloadRequest(downloadUri, downloadFileName);	// createDownloadRequestでrequestを作成.
    	
    	// ダウンロードレシーバーの作成.
    	DownloadReceiver receiver = new DownloadReceiver(this);	// DownloadReceiverオブジェクトreceiverの作成.
    	
    	// レシーバーの登録.
    	registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));	// registerReceiverでレシーバー登録.

    	// ダウンロードタスクの追加.
    	mDownloadManager.enqueue(request);	//  mDownloadManager.enqueueでrequestをタスクに追加.
    	
    }
    
    // PC用ユーザエージェント文字列の作成.
    public String generatePCUserAgentString(String phoneUserAgentString){
    
    	// 最初の括弧を探す.
    	int s = phoneUserAgentString.indexOf('(');	// phoneUserAgentString.indexOfで最初の括弧の位置を取得.
    	int e = phoneUserAgentString.indexOf(')');	// phoneUserAgentString.indexOfで最初の閉じ括弧の位置を取得.
    	if (s < e){	// sよりeのほうが後ろなので大きいはず.
    		String substring = phoneUserAgentString.substring(s, e + 1);	// sからeまでの文字列を取得.
    		String pcUA = phoneUserAgentString.replace(substring, PC_WIN_UA_SUBSTRING);	// phoneUserAgentString.replaceでPC_WIN_UA_SUBSTRINGに置き換える.
    		return pcUA;	// pcUAを返す.
    	}
    	return "";	// 条件外の場合は""を返す.
    	
    }
    
    // ユーザエージェントの変更.
    public void setUserAgent(String strUA){
    	
    	// webViewの取得.
    	mCurrentUA = strUA;	// mCurrentUAにstrUAをセット.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
        webView.getSettings().setUserAgentString(mCurrentUA);	// webView.getSettings().setUserAgentStringでmCurrentUAをセット.
        webView.reload();	// webView.reloadでリロード.
        
    }
    
    // 電話/PCサイトブラウザの表示切替.
    public void changePhonePCSite(MenuItem item){
    	
    	// チェックがついていない場合.
		if (!item.isChecked()){	// item.isCheckedがfalse.
			// チェックを付ける.
			item.setChecked(true);	//item.setCheckedにtrueをセットして, チェック済み.
			// PC用ユーザエージェントをセット.
			setUserAgent(mPCUA);	// setUserAgentでmPCUAをセット.
		}
		else{	// チェックがついている場合.
			// チェックを外す.
			item.setChecked(false);	// item.setCheckedにfalseをセットして, チェックを外す.
			// 電話用ユーザエージェントをセット.
			setUserAgent(mPhoneUA);	// setUserAgentでmPhoneUAをセット.
		}
		
    }
    
}