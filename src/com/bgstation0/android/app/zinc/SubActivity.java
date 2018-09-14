package com.bgstation0.android.app.zinc;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SubActivity extends Activity implements OnEditorActionListener{

	// メンバフィールドの定義.
	public String mPhoneUA = "";	// 電話用ユーザエージェントmPhoneUA.
	public String mPCUA = "";	// PC用ユーザエージェントmPCUA.
	public String mCurrentUA = "";	// 現在のユーザエージェントmCurrentUA.
	public static final String PC_WIN_UA_SUBSTRING = "(Windows NT 10.0; Win64; x64)";	// WindowsPCのユーザエージェントであることを示す部分.
	MainApplication mApp = null;	// mAppをnullにする.
	public static final String SEARCH_URL_GOOGLE = "https://www.google.co.jp/search?q=";	// SEARCH_URL_GOOGLEを"https://www.google.co.jp/search?q="とする.
	String mTabName = "";	// mTabNameを""で初期化.
	MainActivity mMainActivity = null;	// mMainActivityにnullをセット.
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        
        // タグ(タブ名)の取得.
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("args");
        String tag = args.getString("tag");
        //Toast.makeText(this, "tag = " + tag, Toast.LENGTH_LONG).show();

        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();
        
        // メインアプリケーションの取得.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContextで取得したMainApplicationオブジェクトをmAppに格納.
    	mTabName = tag;
        initUrlBar();
    	initProgressBar();
    	initWebView();
    	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
    	if (tabInfo == null){
    		Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
    	}
    	if (tabInfo != null){
    		if (tabInfo.url != null){
    			Toast.makeText(this, "url = " + tabInfo.url, Toast.LENGTH_LONG).show();
    			if (!tabInfo.url.equals("")){
    				Toast.makeText(this, "url = " + tabInfo.url, Toast.LENGTH_LONG).show();
    				setUrlOmit(tabInfo.url);
    				loadUrl();
    			}
    		}
    		TabInfo ti = mApp.mHlpr.getTabInfo(tag);
        	View rootView = getWindow().getDecorView();
    		View content = rootView.findViewById(R.id.layout_sub);
        	ti.view = content;
        	mApp.mTabMap.put(tag, ti);
    	}
        /*
        //Toast.makeText(this, "SubActivity.onCreate()", Toast.LENGTH_LONG).show();
        // メインアプリケーションの取得.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContextで取得したMainApplicationオブジェクトをmAppに格納.
    	//Toast.makeText(this, "mTabMap = " + String.valueOf(mApp.mTabMap.size()), Toast.LENGTH_LONG).show();
        // Bundleをチェック.
        Bundle args = getIntent().getExtras();	// args取得.
        if (args != null){	// nullでない.
	        String tag = args.getString("tag");	// tag取得.
	        boolean remove = args.getBoolean("remove");
	        if (remove){
	        	TabInfo mapti = mApp.mTabMap.get(tag);
	        	//Toast.makeText(this, "view = " + mapti.view.toString(), Toast.LENGTH_LONG).show();
	        	setContentView(mapti.view);
	        }
	        else{
		        //TextView tv = (TextView)findViewById(R.id.textview_sub);	// tv取得.
	        	//tv.setText(tag);	// tagをセット.
		        mTabName = tag;	// mTabNameにtagをセット.
	        	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
	        	tabInfo.title = tag;
	        	mApp.mHlpr.updateTabInfo(tag, tabInfo);
	        	initUrlBar();
	        	initProgressBar();
	        	initWebView();
	        	if (tabInfo.url != null){
	        		if (!tabInfo.url.equals("")){
	        			
	        			//TabWidget widget = mApp.mTabHost.getTabWidget();
	        			//if (widget != null){
	        			//	Toast.makeText(this, "10", Toast.LENGTH_LONG).show();
	        			//}
	        			//int c = widget.getChildCount();
	        			//Toast.makeText(this, "c = " + String.valueOf(c), Toast.LENGTH_LONG).show();
	        			//View v = widget.getChildAt(0);
	        			//Toast.makeText(this,  "v = " + v.toString(), Toast.LENGTH_LONG).show();
	        			//ViewGroup vg = (ViewGroup)v;
	        			//int c2 = vg.getChildCount();
	        			//Toast.makeText(this, "c2 = " + String.valueOf(c2), Toast.LENGTH_LONG).show();
	        			//View v2 = vg.getChildAt(0);
	        			//Toast.makeText(this, "v2 = " + v2.toString(), Toast.LENGTH_LONG).show();
	        			//View v3 = vg.getChildAt(1);
	        			//Toast.makeText(this, "v3 = " + v3.toString(), Toast.LENGTH_LONG).show();
	        			//TextView tv = (TextView)widget.findViewById(android.R.id.title);
	        			//tv.setText("hogehoge");
	        			
	        			//setUrlOmit(tabInfo.url);
	        			loadUrl();
	        		}
	        	}
	        	// タブマップにビューを保存.
	        	TabInfo ti = mApp.mHlpr.getTabInfo(tag);
	        	View rootView = getWindow().getDecorView();	// getWindow().getDecorViewでrootViewを取得.
	    		View content = rootView.findViewById(R.id.layout_sub);	// rootViewからlayout_subを抜き出す.
	        	ti.view = content;
	        	//Toast.makeText(this, "ti.view = " + ti.view.toString(), Toast.LENGTH_LONG).show();
	        	mApp.mTabMap.put(tag, ti);
	        }
        }
        */
        
    }
    
    // バックキーが押された時.
    @Override
    public void onBackPressed(){
    	
    	Toast.makeText(this, "Sub", Toast.LENGTH_LONG).show();
    	
    	WebView webView = (WebView)findViewById(R.id.webview_sub);
    	if (webView.canGoBack()){	// バック可能な場合.
    		Toast.makeText(this, "B", Toast.LENGTH_LONG).show();
    		webView.goBack();	// webView.goBackで戻る.
    	}
    	else{	// そうでない時.
    		Toast.makeText(this, "C", Toast.LENGTH_LONG).show();
    		super.onBackPressed();	// 親クラスのonBackPressedを呼ぶ.
    	}
    	
    	// バックキーにおけるウェブビューの動作.
    	//onBackPressedWebView();	// onBackPressedWebViewに任せる.
    	
    }
    
    // Activityが開始されたとき.
    protected void onStart() {	// onStartの定義
    	
    	// 親クラスの処理
    	super.onStart();	// super.onStartで親クラスの既定処理.
    	
    	// onStartのログを表示.
    	//Log.v(TAG, "onStart()");	// Log.vで"onStart()"と出力.
    	//Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
    }
    
    // Activityが開始されたとき.
    protected void onResume() {	// onResumeの定義
    	
    	// 親クラスの処理
    	super.onResume();	// super.onStartで親クラスの既定処理.
    	
    	// onStartのログを表示.
    	//Log.v(TAG, "onStart()");	// Log.vで"onStart()"と出力.
    	//Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    	
    	//TabInfo ti = mApp.mTabMap.get(mTabName);
    	//if (ti != null){
    		//Toast.makeText(this, "about:blank", Toast.LENGTH_LONG).show();
			//WebView wv = (WebView)findViewById(R.id.webview_sub);
			//wv.loadUrl("about:blank");
    		//if (ti.url != null){
    			//if (ti.url.equals("")){	// url ""
    				//Toast.makeText(this, "about:blank", Toast.LENGTH_LONG).show();
    				//WebView wv2 = (WebView)findViewById(R.id.webview_sub);
    				//wv2.loadUrl("about:blank");
    			//}
    		//}
    		//else{	// url null
    			
    		//}
    	//}
    	//else{	// null
    		//Toast.makeText(this, "about:blank", Toast.LENGTH_LONG).show();
			//WebView wv = (WebView)findViewById(R.id.webview_sub);
			//wv.loadUrl("about:blank");
			//EditText et = (EditText)findViewById(R.id.edittext_sub_urlbar);
			//et.setText("");
    	//}
    	
    }
    
    @Override
    protected void onNewIntent(Intent intent){
    	super.onNewIntent(intent);
    	//Toast.makeText(this, "onNewIntent", Toast.LENGTH_LONG).show();
    }
    
 // Activityが破棄されたとき.
    protected void onDestroy() {	// onDestroyの定義
    	
    	// 親クラスの処理
    	super.onDestroy();	// super.onDestroyで親クラスの既定処理.
    	
    	// onDestroyのログを表示.
    	//Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
    }
    
    // URLバーの初期化.
    public void initUrlBar(){
    	
    	// etUrlを取得し, OnEditorActionListenerとして自身(this)をセット.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_sub_urlbar);	// findViewByIdでR.id.edittext_sub_urlbarからEditTextオブジェクトetUrlを取得.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListeneでthis(自身)をセット.
    	
    }
    
 // プログレスバーの初期化.
    public void initProgressBar(){
    	
    	// progressBarを取得し, 最初は非表示にしておく.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_sub);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibilityで非表示にする.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibilityで非表示(View.GONE)にする.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibilityで最初から表示にする.
    }
    
    // ウェブビューの初期化.
    public void initWebView(){
    	
    	// webViewの取得.
        WebView webView = (WebView)findViewById(R.id.webview_sub);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
        // JavaScript有効化.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabledでJavaScriptを有効にする.
        // デフォルトのユーザエージェントを取得.
        mPhoneUA = webView.getSettings().getUserAgentString();	// webView.getSettings().getUserAgentStringで取得したUAをmPhoneUAに格納.(最初は電話用と思われるので, mPhoneUAに格納.)
        //Toast.makeText(this, mPhoneUA, Toast.LENGTH_LONG).show();	// mPhoneUAをToastで表示.
        mPCUA = generatePCUserAgentString(mPhoneUA);	// mPhoneUAからPC用ユーザエージェント文字列を生成.
        //Toast.makeText(this, mPCUA, Toast.LENGTH_LONG).show();	// mPCUAをToastで表示.
        mCurrentUA = mPhoneUA;	// 現在のユーザエージェントをmPhoneUAとする.
        // CustomWebViewClientのセット.
        webView.setWebViewClient(new CustomWebViewClient(this, mTabName));	// newで生成したCustomWebViewClientオブジェクト(コンストラクタの引数にthisを渡す.)をwebView.setWebViewClientでセット.
        // CustomWebChromeClientのセット.
        webView.setWebChromeClient(new CustomWebChromeClient(this));	// newで生成したCustomWebChromeClientオブジェクト(コンストラクタの引数にthisを渡す.)をwebView.setWebChromeClientでセット.
    	
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
    
    // URLバーにURLをセット.
    public void setUrl(String url){
    	
    	// etUrlを取得し, urlをセット.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_sub_urlbar);	// findViewByIdでR.id.edittext_sub_urlbarからEditTextオブジェクトetUrlを取得.
    	if (etUrl == null){
    		//Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
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
    	EditText etUrl = (EditText)findViewById(R.id.edittext_sub_urlbar);	// findViewByIdでR.id.edittext_sub_urlbarからEditTextオブジェクトetUrlを取得.
    	return etUrl.getText().toString();	// etUrl.getText().toStringでURLを返す.
    	
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
    	WebView webView = (WebView)findViewById(R.id.webview_sub);	// findViewByIdでR.id.webview_subからWebViewオブジェクトwebViewを取得.
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
    
    // プログレスバーに進捗度をセット.
    public void setProgressValue(int progress){
    	
    	// プログレスバーを取得し, 指定された進捗度をセット.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_sub);	// findViewByIdでR.id.progressbar_subからProgressBarオブジェクトprogressBarを取得.
    	progressBar.setProgress(progress);	// progressBar.setProgressでprogressをセット.
    	
    }
    
    // プログレスバーの表示/非表示をセット.
    public void setProgressBarVisible(boolean visible){
    	
    	// プログレスバーを取得し, 表示/非表示をセット.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_sub);	// findViewByIdでR.id.progressbar_subからProgressBarオブジェクトprogressBarを取得.
    	if (visible){	// trueなら表示.
    		progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibilityでVISIBLE.
    	}
    	else{	// falseなら非表示.
    		//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibilityでINVISIBLE.
    		progressBar.setVisibility(View.GONE);	// progressBar.setVisibilityでGONE.
    	}
    	
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
    
}