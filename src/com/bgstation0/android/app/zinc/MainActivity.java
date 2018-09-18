// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

//メインアクティビティクラスMainActivity
public class MainActivity extends Activity/*ActivityGroup*//*TabActivity*/ implements /*TabContentFactory,*/ OnEditorActionListener/*Activity*/ /*implements OnClickListener, OnEditorActionListener*/{	// View.OnClickListener, TextView.OnEditorActionListenerインターフェースの追加.

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
	public Context mContext = null;	// mContextにnullをセット.
	public LocalActivityManager mLAM = null;	// mLAMにnullをセット.
	public FrameLayout mFL = null;	// mFLにnullをセット.
	public TabHost mTabHost = null;
	
	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;	// mContextにnullをセット.
        //Toast.makeText(this, "MainActivity.onCreate()", Toast.LENGTH_LONG).show();
        
        // メインアプリケーションの取得.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContextで取得したMainApplicationオブジェクトをmAppに格納.
    	mApp.mMainActivity = this;
    	mTabHost = (TabHost)this.findViewById(android.R.id.tabhost);
    	mTabHost.setup();
    	
    	/*
    	mLAM = getLocalActivityManager();	// mLAMの取得.
    	mFL = (FrameLayout)this.findViewById(R.id.frame_main);
    	if (mApp.mHlpr != null){
    		TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();
    		if (tabInfo != null){
    			//Toast.makeText(this, "last", Toast.LENGTH_LONG).show();
    			// SubActivityのIntent作成.
    			Intent intent = new Intent(this, SubActivity.class);
    			Bundle args = new Bundle();
    			args.putString("tag", tabInfo.tabName);
    			intent.putExtra("args", args);
    			mLAM.removeAllActivities();
    			Window window = mLAM.startActivity(tabInfo.tabName, intent);
    			View view = window.getDecorView();
    			mFL.addView(view);
    			mCurrentTabName = tabInfo.tabName;
    		}
    		else{
    			//Toast.makeText(this, "new", Toast.LENGTH_LONG).show();
    			registTab();
    			TabInfo ti = mApp.mHlpr.getLastTabInfo();
    			Intent intent = new Intent(this, SubActivity.class);
    			Bundle args = new Bundle();
    			args.putString("tag", ti.tabName);
    			intent.putExtra("args", args);
    			mLAM.removeAllActivities();
    			Window window = mLAM.startActivity(ti.tabName, intent);
    			View view = window.getDecorView();
    			mFL.addView(view);
    			mCurrentTabName = ti.tabName;
    		}
    	}
    	*/
    	/*
    	if (mApp.mHlpr != null){	// mApp.mHlprがnullでない.
    		//Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
            TabHost tabHost = getTabHost();	// getTabHostでtabHostを取得.
            mApp.mTabHost = tabHost;	// tabHostをもっておく.
    		List<TabInfo> tabInfoList = mApp.mHlpr.getTabInfoList();
    		if (tabInfoList != null){
    			//Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
    			int last = tabInfoList.size() - 1;	// tabInfoの数-1をlastに格納.
    			for (int i = last, j = 0; i >= 0; i--){	// lastから0まで繰り返す.
    				//Toast.makeText(this, "4", Toast.LENGTH_LONG).show();
    				TabInfo tabInfo = tabInfoList.get(i);
    				TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabInfo.tabName);	// tabName
    				//tabSpec.setIndicator(tabInfo.title);	// title.
    				final String tag = tabInfo.tabName;
    				final CustomTabWidget widget = new CustomTabWidget(this, tabInfo.title, tabInfo.tabName, new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//Toast.makeText(mContext, "click(1) : " + tag, Toast.LENGTH_LONG).show();
							removeTab(tag);
						}
						
					});
    				tabSpec.setIndicator(widget);
    		        Intent intent = new Intent(this, SubActivity.class);	// intentを生成.
    		        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		        //intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    		        Bundle args = new Bundle();	// args作成.
    		        args.putString("tag", tabInfo.tabName);	// ("tag", tabInfo.tabName)で登録.
    		        args.putBoolean("remove", false);
    		        intent.putExtras(args);	// args登録.
    		        //tabSpec.setContent(intent);	// intentをセット.
    		        tabSpec.setContent(this);
    		        tabHost.addTab(tabSpec);	// tabSpecを追加.
    		        mApp.mTabNameList.add(tabInfo.tabName);
    		        tabHost.setCurrentTab(j);
    		        j++;
    			}
    			//tabHost.setCurrentTab(last);	// 最後のタブをカレントにセット.
    		}
    		else{
    			//Toast.makeText(this, "3b", Toast.LENGTH_LONG).show();
    			registTab();	// registTabで新規タブを登録.
    			TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();
    			TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabInfo.tabName);	// tabName
    			//tabSpec.setIndicator(tabInfo.title);	// title.
				final String tag = tabInfo.tabName;
				tabInfo.title = tabInfo.tabName;
				final CustomTabWidget widget = new CustomTabWidget(this, tabInfo.title, tabInfo.tabName, new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(mContext, "click(2) : " + tag, Toast.LENGTH_LONG).show();
						removeTab(tag);
					}
					
				});
				tabSpec.setIndicator(widget);
		        Intent intent = new Intent(this, SubActivity.class);	// intentを生成.
		        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        //intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		        Bundle args = new Bundle();	// args作成.
		        args.putString("tag", tabInfo.tabName);	// ("tag", tabInfo.tabName)で登録.
		        args.putBoolean("remove", false);
		        intent.putExtras(args);	// args登録.
		        //tabSpec.setContent(intent);	// intentをセット.
		        tabSpec.setContent(this);
		        tabHost.addTab(tabSpec);	// tabSpecを追加.
		        mApp.mTabNameList.add(tabInfo.tabName);
		        mApp.mHlpr.updateTabInfo(tag, tabInfo);
    		}
    	}
    	*/
    	
    	//Toast.makeText(this, "5", Toast.LENGTH_LONG).show();
        /*
    	// tabHostの取得.
        TabHost tabHost = getTabHost();	// getTabHostでtabHostを取得.
        
        // tabSpecの作成.
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("MainTab");	// tabSpec作成.        
        // テキストのセット.
        tabSpec.setIndicator("MainTab");	// テキストは"MainTab".
        // コンテンツのセット.
        tabSpec.setContent(R.id.main_content);	// R.id.main_contentをセット.
        // タブの追加.
        tabHost.addTab(tabSpec);	// tabSpecを追加.
        */
        // tabSpec2の作成.(これで追加すると, 最初のタブのテキストMainContentが表示されない.)
        /*
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("MainTab2");	// tabSpec2作成.      
        // テキストのセット.
        tabSpec2.setIndicator("MainTab2");	// テキストは"MainTab2".
        // コンテンツのセット.
        View v = (View)findViewById(R.id.main_content);	// vを取得.
        TextView tv = (TextView)v.findViewById(R.id.textview1);	// tvを取得.
        tv.setText("MainContent2");	// テキストを変更.
        tabSpec2.setContent(R.id.main_content);	// R.id.main_contentをセット.
        // タブの追加.
        tabHost.addTab(tabSpec2);	// tabSpec2を追加.
        */
        /*
        // tabSpec2の作成.(IntentでActivityを追加.)
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("MainTab2");	// tabSpec2作成.
        // テキストのセット.
        tabSpec2.setIndicator("MainTab2");	// テキストは"MainTab2".
        // コンテンツのセット.
        tabSpec2.setContent(new Intent(this, SubActivity.class));	// SubActivityをセット.
        // タブの追加.
        tabHost.addTab(tabSpec2);	// tabSpec2を追加.
        
        // tabSpec3の作成.(IntentでActivityを追加.)
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("MainTab3");	// tabSpec3作成.
        // テキストのセット.
        tabSpec3.setIndicator("MainTab3");	// テキストは"MainTab3".
        // コンテンツのセット.
        Intent intent = new Intent(this, SubActivity.class);	// intentを生成.
        Bundle args = new Bundle();	// args作成.
        args.putString("tag", "Activity3");	// ("tag", "Activity3")で登録.
        intent.putExtras(args);	// args登録.
        tabSpec3.setContent(intent);	// intentをセット.
        // タブの追加.
        tabHost.addTab(tabSpec3);	// tabSpec3を追加.
        */
        
    	/*
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_main);	// setContentViewでR.layout.activity_mainをセット.
        initUrlBar();	// initUrlBarでetUrlを初期化.
        initProgressBar();	// initProgressBarでprogressbarを初期化.
        initWebView();	// initWebViewでwebViewを初期化.
        initDownloadManager();	// initDownloadManagerでmDownloadManagerを初期化.
        //loadUrlFromIntent();	// loadUrlFromIntentでインテントで指定されたURLをロード.
        initMainApplication();	// initMainApplicationでメインアプリケーションを初期化.
        */
        
    }
    
    /*
    @Override
    public View createTabContent(String tag){
    	//Toast.makeText(this, "createTabContent tag = " + tag, Toast.LENGTH_LONG).show();
    	LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
    	View view = inflater.inflate(R.layout.activity_sub, null);
    	initUrlBar2(view);
    	initProgressBar2(view);
    	initWebView2(view, tag);
    	
    	// タブ情報をタブマップから取得.
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		//Toast.makeText(this, "TabMapExist", Toast.LENGTH_LONG).show();
    		return ti.view;
    	}
    	else{
    		// タブ情報をタブDBから取得.
    		TabInfo ti2 = mApp.mHlpr.getTabInfo(tag);
    		if (ti2 != null){
    			//Toast.makeText(this, "TabDBExist", Toast.LENGTH_LONG).show();
    			ti2.view = view;
    			mApp.mTabMap.put(tag, ti2);
    			if (ti2.url != null){
	        		if (!ti2.url.equals("")){
	        			setUrlOmit(ti2.url, tag);
	        			loadUrl();
	        		}
    			}
    			return ti2.view;
    		}
    		else{
    			//Toast.makeText(this, "TabNotExist", Toast.LENGTH_LONG).show();
    			return view;
    		}
    	}    	
    }
    */
    
    // URLバーの初期化.
    public void initUrlBar2(View view){
    	
    	// etUrlを取得し, OnEditorActionListenerとして自身(this)をセット.
    	EditText etUrl = (EditText)view.findViewById(R.id.edittext_sub_urlbar);	// findViewByIdでR.id.edittext_sub_urlbarからEditTextオブジェクトetUrlを取得.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListeneでthis(自身)をセット.
    	
    }
    
    // プログレスバーの初期化.
    public void initProgressBar2(View view){
    	
    	// progressBarを取得し, 最初は非表示にしておく.
    	ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressbar_sub);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibilityで非表示にする.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibilityで非表示(View.GONE)にする.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibilityで最初から表示にする.
    }
    
    // ウェブビューの初期化.
    public void initWebView2(View view, String tag){
    	
    	// webViewの取得.
        WebView webView = (WebView)view.findViewById(R.id.webview_sub);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
        // JavaScript有効化.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabledでJavaScriptを有効にする.
        // デフォルトのユーザエージェントを取得.
        mPhoneUA = webView.getSettings().getUserAgentString();	// webView.getSettings().getUserAgentStringで取得したUAをmPhoneUAに格納.(最初は電話用と思われるので, mPhoneUAに格納.)
        //Toast.makeText(this, mPhoneUA, Toast.LENGTH_LONG).show();	// mPhoneUAをToastで表示.
        mPCUA = generatePCUserAgentString(mPhoneUA);	// mPhoneUAからPC用ユーザエージェント文字列を生成.
        //Toast.makeText(this, mPCUA, Toast.LENGTH_LONG).show();	// mPCUAをToastで表示.
        mCurrentUA = mPhoneUA;	// 現在のユーザエージェントをmPhoneUAとする.
        // CustomWebViewClientのセット.
        webView.setWebViewClient(new CustomWebViewClient(this, tag));	// newで生成したCustomWebViewClientオブジェクト(コンストラクタの引数にthisを渡す.)をwebView.setWebViewClientでセット.
        // CustomWebChromeClientのセット.
        webView.setWebChromeClient(new CustomWebChromeClient(this, tag));	// newで生成したCustomWebChromeClientオブジェクト(コンストラクタの引数にthisを渡す.)をwebView.setWebChromeClientでセット.
    	
    }
    
    // 既存のインスタンスが再利用され, インテントが飛んで来た時.
    @Override
    protected void onNewIntent(Intent intent){
    	
    	// OnNewIntentに来た事をトーストで表示.
    	//Toast.makeText(this, "OnNewIntent", Toast.LENGTH_LONG).show();	// "OnNewIntent"とToastで表示.
    	
    }
    
    // バックキーが押された時.
    @Override
    public void onBackPressed(){
    	
    	//Toast.makeText(this, "Main", Toast.LENGTH_LONG).show();
    	
    	// バックキーにおけるウェブビューの動作.
    	onBackPressedWebView();	// onBackPressedWebViewに任せる.
    	
    }
    
    // 起動したアクティビティの結果が返って来た時.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	//Toast.makeText(this, "0a", Toast.LENGTH_LONG).show();
    	// 既定の処理.
    	super.onActivityResult(requestCode, resultCode, data);	// 親クラスのonActivityResultを呼ぶ.
    	//Toast.makeText(this, "0b", Toast.LENGTH_LONG).show();
    	// キャンセルの場合.
    	if (requestCode != REQUEST_CODE_TAB && resultCode == RESULT_CANCELED){	// requestCodeがREQUEST_CODE_TABではなく, resultCodeがRESULT_CANCELEDの場合.
    		//Toast.makeText(this, "0c", Toast.LENGTH_LONG).show();
    		return;	// 何もせず終了.
    	}
    	//Toast.makeText(this, "0d", Toast.LENGTH_LONG).show();
    	
    	// 起動したアクティビティが閉じた時の結果に対する処理.
    	Bundle bundle = null;	// Bundle型bundleをnullで初期化.
    	if (data != null){	// dataがnullでなければ.
    		bundle = data.getExtras();	// data.getExtrasでbundleを取得.
    	}
    	//Toast.makeText(this, "0e", Toast.LENGTH_LONG).show();
    	
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
    			//Toast.makeText(this, "0", Toast.LENGTH_LONG).show();
    			// タブのアイテムが選択されたとき.
    			if (resultCode == RESULT_OK){	// RESULT_OKの場合.
    				if (bundle != null){	// bundleがnullでなければ.
    					String tabName = bundle.getString("tabName");	// bundleからtabNameを取得.
    					//String title = bundle.getString("title");	// bundleからtitleを取得.
    					if (mApp.mTabMap.containsKey(tabName)){	// タブマップにある場合.
    						//setContentViewByTabName(tabName, title);	// setContentViewByTabNameでtabNameからビューをセット.(タイトルもセットしておく.)
    						setViewByTabName(tabName);
    					}
    					else{	// マップには無い場合.
    						// タブDBからの取得.
    						TabInfo ti = mApp.mTabMap.get(mCurrentTabName);
    				    	ti.date = System.currentTimeMillis();
    				    	ti.view = mFL.findViewById(R.id.layout_sub);
    				    	mApp.mHlpr.updateTabInfo(mCurrentTabName, ti);
    				    	
    						TabInfo tabInfo = mApp.mHlpr.getTabInfo(tabName);	// tabNameからtabInfo取得
    						if (tabInfo != null){	// DBにはある場合.
    							Intent intent = new Intent(this, SubActivity.class);
    			    			Bundle args = new Bundle();
    			    			args.putString("tag", tabInfo.tabName);
    			    			intent.putExtra("args", args);
    			    			mFL.removeAllViews();
    			    			Window window = mLAM.startActivity(tabInfo.tabName, intent);
    			    			View view = window.getDecorView();
    			    			mFL.addView(view);
    			    			mCurrentTabName = tabInfo.tabName;
    			    			ActionBar act = getActionBar();
    			        		if (act != null){
    			        			act.setTitle(tabInfo.title);
    			        		}
    							/*
    							// メインアクティビティを起動する.(タブを復元する.)
	    				    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
	    				    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassNameで".MainActivity"をセット.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// これだと起動するアクティビティ以外は破棄される.
	    				    	intent.putExtra("LaunchMode", "RestoreTab");	// "LaunchMode"をキー, "RestoreTab"を値として登録.
	    				    	intent.putExtra("TabName", tabInfo.tabName);	// "TabName"でtabinfo.tabNameを登録.
	    				    	intent.putExtra("Url", tabInfo.url);	// "Url"をキーにして, tabInfo.urlを送る.
	    				    	startActivity(intent);	// startActivityにintentを渡す.
	    				    	*/
    						}
    						/*
    						else{	// DBにもない場合.(ここにくることはない.)
	    						// メインアクティビティを起動する.(タブの新規作成する.)
	    				    	String packageName = getPackageName();	// getPackageNameでpackageNameを取得.
	    				    	Intent intent = new Intent();	// Intentオブジェクトintentを作成.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassNameで".MainActivity"をセット.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// これだと起動するアクティビティ以外は破棄される.
	    				    	intent.putExtra("LaunchMode", "NewTab");	// "LaunchMode"をキー, "NewTab"を値として登録.
	    				    	startActivity(intent);	// startActivityにintentを渡す.
    						}
    						*/
    					}
    				}
    			}
    			else if (resultCode == RESULT_CANCELED){	// RESULT_CANCELEDの場合.
    				//Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
    				if (!mApp.mTabMap.containsKey(mCurrentTabName)){
    					//Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
    					TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();	// 消されたカレントタブ以外で直近のタブを探す.
    					if (tabInfo != null){	// 直近タブがみつかった.
    						//Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
    						TabInfo ti = mApp.mTabMap.get(tabInfo.tabName);	// マップから取得.
    						if (ti != null){	// マップにあった.
    							//Toast.makeText(this, "map exist", Toast.LENGTH_LONG).show();
    							// SubActivityのIntent作成.
    							Intent intent = new Intent(this, SubActivity.class);
    							Bundle args = new Bundle();
    							args.putString("tag", ti.tabName);
    							intent.putExtra("args", args);
    							mFL.removeAllViews();
    							Window window = mLAM.startActivity(ti.tabName, intent);
    							View view = window.getDecorView();
    							mFL.addView(view);
    							mCurrentTabName = ti.tabName;
    							ActionBar act = getActionBar();
    				    		if (act != null){
    				    			if (ti.title.equals("")){
    				    				act.setTitle("Zinc");
    				    			}
    				    			else{
    				    				act.setTitle(ti.title);
    				    			}
    				    		}    				    		
    						}
    						else{	// マップに無い.
    							
    							//Toast.makeText(this, "db last", Toast.LENGTH_LONG).show();
    			    			// SubActivityのIntent作成.
    			    			Intent intent = new Intent(this, SubActivity.class);
    			    			Bundle args = new Bundle();
    			    			args.putString("tag", tabInfo.tabName);
    			    			intent.putExtra("args", args);
    			    			mFL.removeAllViews();
    			    			Window window = mLAM.startActivity(tabInfo.tabName, intent);
    			    			View view = window.getDecorView();
    			    			mFL.addView(view);
    			    			mCurrentTabName = tabInfo.tabName;
    			    			
    						}
    					}
    					else{	// タブがない.
    						
    						//Toast.makeText(this, "all delete new", Toast.LENGTH_LONG).show();
    		    			registTab();
    		    			TabInfo ti2 = mApp.mHlpr.getLastTabInfo();
    		    			Intent intent = new Intent(this, SubActivity.class);
    		    			Bundle args = new Bundle();
    		    			args.putString("tag", ti2.tabName);
    		    			intent.putExtra("args", args);
    		    			mLAM.removeAllActivities();
    		    			Window window = mLAM.startActivity(ti2.tabName, intent);
    		    			View view = window.getDecorView();
    		    			mFL.addView(view);
    		    			mCurrentTabName = ti2.tabName;
    		    			ActionBar act = getActionBar();
				    		if (act != null){
				    			if (ti2.title.equals("")){
				    				act.setTitle("Zinc");
				    			}
				    			else{
				    				act.setTitle(ti2.title);
				    			}
				    		}   
				    		
    					}
    					
    				}
    				/*
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
    				*/
    				
    				// そのまま終われば帰ってきたとき, タブは再生成されない.
    				//Toast.makeText(mContext, "RESULT_CANCEL", Toast.LENGTH_LONG).show();
    				
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
    		//addTab();	// addTabで追加.
    		//addTabToActivity();	// addTabToActivityで追加.
    		addTabToLAM();
    		
    	}
    	else if (id == R.id.menu_item_tabs_show){	// R.id.menu_item_tabs_show("タブ一覧の表示")の時.

    		// タブ一覧の表示.
    		showTabs();	// showTabsで追加.
    		
    	}
    	/*
    	else if (id == R.id.menu_item_remove_tab){	// R.id.menu_item_remove_tab("タブの削除")の時.
    		
    		// タブの削除.
    		removeTab();	// removeTabで削除.
    		
    	}
    	*/
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
    	/*
    	else if (id == R.id.menu_item_download){	// R.id.menu_item_download("ダウンロード")の時.
    		
    		// ダウンロード.
    		download();	// downloadでダウンロード.
    		
    	}
    	else if (id == R.id.menu_item_pc_site_browser){	// R.id.menu_item_pc_site_browser("PCサイトブラウザ")の時. 

    		// 表示の切り替え.
    		changePhonePCSite(item);	// changePhonePCSiteにitemを渡す.
    		
    	}
    	*/
    	
    	// あとは既定の処理に任せる.
    	return super.onOptionsItemSelected(item);	// 親クラスのonOptionsItemSelectedを呼ぶ.
    	
    }
    
    // ボタンが押された時.
    /*
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
    */
    
    // エディットテキストでEnterキーが押された時.
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
    	
    	// Enterキーが押された時.
    	if (actionId == EditorInfo.IME_ACTION_DONE){	// Enterキーが押された時.(IME_ACTION_DONE)
    		
    		// ソフトウェアキーボードの非表示.
    		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);	// getSystemServiceからinputMethodManagerを取得.
    		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);	// inputMethodManager.hideSoftInputFromWindowで非表示.
    		
    		// URLのロード.
    		//loadUrl();	// loadUrlでURLバーのURLをロード.
    	
    		// trueを返す.
    		return true;	// returnでtrueを返す.
    		
    	}
    	
    	// falseを返す.
    	return false;	// returnでfalseを返す.
    	
    }
    
    // URLバーの初期化.
    public void initUrlBar(){
    	
    	// etUrlを取得し, OnEditorActionListenerとして自身(this)をセット.
    	/*
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListeneでthis(自身)をセット.
    	*/
    }
    
    // プログレスバーの初期化.
    public void initProgressBar(){
    	
    	// progressBarを取得し, 最初は非表示にしておく.
    	/*
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibilityで非表示にする.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibilityで非表示(View.GONE)にする.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibilityで最初から表示にする.
    	*/
    }
    
    // ウェブビューの初期化.
    public void initWebView(){
    	
    	/*
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
        */
    	
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
    	/*
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
		*/
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
    	/*
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
		*/
    }
    
    // タブ情報からタブを生成.
    public void loadTab(TabInfo tabInfo){
    	
    	// マップ側の更新.
    	/*
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
		*/
    }
    
    // タブ名から取得したビューをセット.
    public void setViewByTabName(String tabName){
    	
    	TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);
    	tabInfo.date = System.currentTimeMillis();
    	tabInfo.view = mFL.findViewById(R.id.layout_sub);
    	mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);
    	
    	TabInfo tabInfo2 = mApp.mTabMap.get(tabName);
    	if (tabInfo2 != null){    		
    		//Toast.makeText(this, "change", Toast.LENGTH_LONG).show();
			// SubActivityのIntent作成.
			Intent intent = new Intent(this, SubActivity.class);
			Bundle args = new Bundle();
			args.putString("tag", tabInfo2.tabName);
			intent.putExtra("args", args);
			mFL.removeAllViews();
			Window window = mLAM.startActivity(tabInfo2.tabName, intent);
			View view = window.getDecorView();
			mFL.addView(view);
			mCurrentTabName = tabInfo2.tabName;
			ActionBar act = getActionBar();
    		if (act != null){
    			if (tabInfo2.title.equals("")){
    				act.setTitle("Zinc");
    			}
    			else{
    				act.setTitle(tabInfo2.title);
    			}
    		}
    	}
    	
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
    public void setUrl(String url, String tag){
    	
    	// etUrlを取得し, urlをセット.
    	//EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
				EditText etUrl = (EditText)ti.view.findViewById(R.id.edittext_sub_urlbar);
				if (etUrl == null){
					//Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
				}
				else{
					etUrl.setText(url);	// etUrl.SetTextでURLバーのetUrlにurlをセット.
				}
    		}
    	}
    	
    }
    
    /*
    // カレントタブにURLをセット.
    public void setUrlOmit(String url){
    	
    	// カレントタブにセット.
    	String tag = this.getTabHost().getCurrentTabTag();
    	setUrlOmit(url ,tag);
    	
    }
    */
    
    // URLバーにURLをセットする時に"http"の場合は省略する.
    public void setUrlOmit(String url, String tag){
    	
    	// 先頭文字列から省略するかを判定.
    	if (url.startsWith("http://")){	// "http"の時.
    		String omitUrl = url.substring(7);	// url.substringで7文字目からの文字列をomitUrlに返す.
    		setUrl(omitUrl, tag);	// setUrlでomitUrlをセット.
    	}
    	else{
    		setUrl(url, tag);	// setUrlでそのままurlを渡す.
    	}
    	
    }
    
    /*
    // URLバーからURLを取得.
    public String getUrl(){
    	
    	// etUrlのURLを取得.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			EditText etUrl = (EditText)ti.view.findViewById(R.id.edittext_sub_urlbar);	// findViewByIdでR.id.edittext_sub_urlbarからEditTextオブジェクトetUrlを取得.
    	    	return etUrl.getText().toString();	// etUrl.getText().toStringでURLを返す.
    		}
    	}
    	return "";
    	
    }
    */
    
    // ウェブビューからタイトルを取得.
    public String getWebTitle(){
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	WebView webView = (WebView)subActivity.findViewById(R.id.webview_sub);
    	return webView.getTitle();
    	/*
    	// WebViewのURLを取得.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			return webView.getTitle();
    		}
    	}
    	return "";
    	*/
    	
    }
    
    // ウェブビューからURLを取得.
    public String getWebUrl(){
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	WebView webView = (WebView)subActivity.findViewById(R.id.webview_sub);
    	return webView.getUrl();
    	/*
    	// webViewのURLを取得.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
    	return webView.getUrl();	// returnでwebView.getUrlで取得したURLを返す.
    	*/
    	//return null;
    	
    	/*
    	// WebViewのURLを取得.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			return webView.getUrl();
    		}
    	}
    	*/
    	//return "";
    	
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
    
    /*
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
    */
    
    // 指定されたURLをロード.
    public void loadUrl(String url){
    	
    	/*
    	// webViewを取得し, urlをロード.
    	//WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			webView.loadUrl(url);	// webView.loadUrlでurlの指すWebページをロード.    	
    		}
    	}
    	*/
    	
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
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	subActivity.setUrlOmit(url);
    	subActivity.loadUrl();
    	//setUrlOmit(url);	// setUrlOmitでURLバーにURLをセット.
		//loadUrl();	// loadUrlでURLバーのURLをロード.
		
    }
    
    // インテントで指定されたURLをロード.
    public void loadUrlFromIntent(){
    	
    	/*
    	// 起動時のインテントからURLを取得し, それを使ってロード.
        Intent intent = getIntent();	// getIntentでintentを取得.
        String action = intent.getAction();	// intent.getActionでactionを取得.
        String schema = intent.getScheme();	// intent.getSchemaでschemaを取得.
        String url = intent.getDataString();	// intent.getDataStringでurlを取得.
        if (action != null && action.equals(Intent.ACTION_VIEW) && (schema.equals("http") || schema.equals("https"))){	// ACTION_VIEWでhttpまたはhttpsの時.
        	//setUrlOmit(url);	// setUrlOmitでURLバーにURLをセット.
    		loadUrl();	// loadUrlでURLバーのURLをロード.
        }
        */
        
    }
    
    // プログレスバーに進捗度をセット.
    public void setProgressValue(int progress, String tag){
    	
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
		    	// プログレスバーを取得し, 指定された進捗度をセット.
		    	//ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
		    	//progressBar.setProgress(progress);	// progressBar.setProgressでprogressをセット.
		    	ProgressBar progressBar = (ProgressBar)ti.view.findViewById(R.id.progressbar_sub);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
		    	progressBar.setProgress(progress);	// progressBar.setProgressでprogressをセット.
    		}
    	}
    	
    }
    
    // プログレスバーの表示/非表示をセット.
    public void setProgressBarVisible(boolean visible, String tag){
    	
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
				// プログレスバーを取得し, 表示/非表示をセット.
				//ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewByIdでR.id.progressbarからProgressBarオブジェクトprogressBarを取得.
				ProgressBar progressBar = (ProgressBar)ti.view.findViewById(R.id.progressbar_sub);
				if (visible){	// trueなら表示.
					progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibilityでVISIBLE.
				}
				else{	// falseなら非表示.
					//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibilityでINVISIBLE.
					progressBar.setVisibility(View.GONE);	// progressBar.setVisibilityでGONE.
				}
    		}
    	}
    	
    }
    
    // アクションバーのタイトルをセット.
    public void setActionBarTitle(String title){
    	
    	// アクションバーを取得し, 指定されたタイトルをセット.
    	getActionBar().setTitle(title);	// getActionBar().setTitleでtitleをセット.
    	
    }
    
    // バックキーが押された時のWebViewの動作.
    public void onBackPressedWebView(){
    	
    	//Toast.makeText(this, "A", Toast.LENGTH_LONG).show();
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	WebView webView = (WebView)subActivity.findViewById(R.id.webview_sub);
    	if (webView.canGoBack()){	// バック可能な場合.
    		//Toast.makeText(this, "B", Toast.LENGTH_LONG).show();
    		webView.goBack();	// webView.goBackで戻る.
    	}
    	else{	// そうでない時.
    		//Toast.makeText(this, "C", Toast.LENGTH_LONG).show();
    		super.onBackPressed();	// 親クラスのonBackPressedを呼ぶ.
    	}
    	/*
    	// 戻れる場合は, 1つ前のページに戻る.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
    	if (webView.canGoBack()){	// バック可能な場合.
    		webView.goBack();	// webView.goBackで戻る.
    	}
    	else{	// そうでない時.
    		super.onBackPressed();	// 親クラスのonBackPressedを呼ぶ.
    	}
    	*/
    	
    	/*
    	// WebViewのURLを取得.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			if (webView.canGoBack()){	// バック可能な場合.
    	    		webView.goBack();	// webView.goBackで戻る.
    	    	}
    	    	else{	// そうでない時.
    	    		super.onBackPressed();	// 親クラスのonBackPressedを呼ぶ.
    	    	}
    		}
    	}
    	*/
    	
    }
    
    // タブの追加.(Intent版.)
    public void addTabByIntent(){
    	
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
    
    // タブの追加.(LocalActivityManager版.)
    public void addTabToLAM(){
    	
    	//TabInfo tabInfo = mApp.mHlpr.getTabInfo(mCurrentTabName);
    	TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);
    	tabInfo.date = System.currentTimeMillis();
    	tabInfo.view = mFL.findViewById(R.id.layout_sub);
    	mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);
    	
    	//Toast.makeText(this, "newtab", Toast.LENGTH_LONG).show();
    	
    	registTab();
    	TabInfo newTabInfo = mApp.mHlpr.getLastTabInfo();
		Intent intent = new Intent(this, SubActivity.class);
		Bundle args = new Bundle();
		args.putString("tag", newTabInfo.tabName);
		intent.putExtra("args", args);
		mFL.removeAllViews();
		mLAM.destroyActivity(newTabInfo.tabName, true);
		Window window = mLAM.startActivity(newTabInfo.tabName, intent);
		View view = window.getDecorView();
		mFL.addView(view);
		mCurrentTabName = newTabInfo.tabName;
		ActionBar act = getActionBar();
		if (act != null){
			if (newTabInfo.title.equals("")){
				act.setTitle("Zinc");
			}
		}
		
    }
    
    // タブの追加.(TabActivity版.)
    public void addTabToActivity(){
    	
    	// タブ状態の保存.(簡易的. ロード中の切り替えを考慮していない.)
    	String tag = mApp.mTabHost.getCurrentTabTag();
    	//Toast.makeText(mContext, "tag = " + tag, Toast.LENGTH_LONG).show();
    	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
    	tabInfo.date = System.currentTimeMillis();
    	mApp.mHlpr.updateTabInfo(tag, tabInfo);
    	
    	// 新規タブの追加.
    	registTab();	// registTabで新規タブを登録.
		TabInfo newTabInfo = mApp.mHlpr.getLastTabInfo();
		//Toast.makeText(this, "url = " + newTabInfo.url, Toast.LENGTH_LONG).show();
		TabHost.TabSpec tabSpec = mApp.mTabHost.newTabSpec(newTabInfo.tabName);	// tabName
		//tabSpec.setIndicator(tabInfo.title);	// title.
		final String tag2 = newTabInfo.tabName;
		newTabInfo.title = newTabInfo.tabName;
		final CustomTabWidget widget = new CustomTabWidget(this, newTabInfo.title, newTabInfo.tabName, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(mContext, "click(3) : " + tag2, Toast.LENGTH_LONG).show();
				removeTab(tag2);
			}
			
		});
		tabSpec.setIndicator(widget);
		mApp.mTabNameList.add(newTabInfo.tabName);
        Intent intent = new Intent(this, SubActivity.class);	// intentを生成.
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle args = new Bundle();	// args作成.
        args.putString("tag", newTabInfo.tabName);	// ("tag", newTabInfo.tabName)で登録.
        args.putBoolean("remove", false);
        intent.putExtras(args);	// args登録.
        //tabSpec.setContent(intent);	// intentをセット.
        //tabSpec.setContent(this);
        mApp.mTabHost.addTab(tabSpec);	// tabSpecを追加.
        int last = mApp.mTabNameList.size() - 1;	// last.
        //getTabHost().setCurrentTab(last);
        mApp.mHlpr.updateTabInfo(newTabInfo.tabName, newTabInfo);
    }
    
 // タブの削除.(現在表示されているタブを削除.)
    public void removeTab(){
    
    	String tag = mApp.mTabHost.getCurrentTabTag();
    	//Toast.makeText(this, "before", Toast.LENGTH_LONG).show();
    	int i = mApp.mTabHost.getCurrentTab();
    	//mApp.mTabHost.getTabWidget().removeAllViews();
    	mApp.mTabHost.clearAllTabs();
    	//Toast.makeText(this, "after", Toast.LENGTH_LONG).show();
    	boolean b = mApp.mHlpr.removeRowTab(tag);
    	if (b){
    		//Toast.makeText(this, "true", Toast.LENGTH_LONG).show();
    	}
    	else{
    		//Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
    	}
    	mApp.mTabMap.remove(tag);
    	mApp.mTabNameList.remove(i);
    	int s = mApp.mTabMap.size();
    	//Toast.makeText(this, "s = " + String.valueOf(s), Toast.LENGTH_LONG).show();
    	for (TabInfo ti : mApp.mTabMap.values()){
    		TabHost.TabSpec tabSpec = mApp.mTabHost.newTabSpec(ti.tabName);
    		//tabSpec.setIndicator(tabInfo.title);	// title.
    		final String tag2 = ti.tabName;
    		if (ti.title.equals("")){
    			//Toast.makeText(this, "add tab title = " + ti.tabName, Toast.LENGTH_LONG).show();
    			ti.title = ti.tabName;
    		}
    		final CustomTabWidget widget = new CustomTabWidget(this, ti.title, ti.tabName, new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				//Toast.makeText(mContext, "click(4) : " + tag2, Toast.LENGTH_LONG).show();
    				removeTab(tag2);
    			}
    			
    		});
    		tabSpec.setIndicator(widget);
    		Intent intent = new Intent(this, SubActivity.class);	// intentを生成.
            Bundle args = new Bundle();	// args作成.
            args.putString("tag", ti.tabName);	// ("tag", ti.tabName)で登録.
            args.putBoolean("remove", true);
            intent.putExtras(args);	// args登録.
            //tabSpec.setContent(intent);	// intentをセット.
            //tabSpec.setContent(this);
            mApp.mTabHost.addTab(tabSpec);	// tabSpecを追加.
            //Toast.makeText(this, "add tabName = " + ti.tabName + " title = " + ti.title, Toast.LENGTH_LONG).show();
    	}
    	
    }
    
    // タブの削除.（押されたタブを削除.)
    public void removeTab(String tag){
    
    	//String tag = mApp.mTabHost.getCurrentTabTag();
    	//Toast.makeText(this, "before", Toast.LENGTH_LONG).show();
    	//int i = mApp.mTabHost.getCurrentTab();
    	int i = -1;
    	for (int ii = 0; ii < mApp.mTabNameList.size(); ii++){
    		if (mApp.mTabNameList.get(ii).equals(tag)){
    			i = ii;
    		}
    	}
    	//mApp.mTabHost.getTabWidget().removeAllViews();
    	mApp.mTabHost.clearAllTabs();
    	//Toast.makeText(this, "after", Toast.LENGTH_LONG).show();
    	boolean b = mApp.mHlpr.removeRowTab(tag);
    	if (b){
    		//Toast.makeText(this, "true", Toast.LENGTH_LONG).show();
    	}
    	else{
    		//Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
    	}
    	mApp.mTabMap.remove(tag);
    	if (i != -1){
    		mApp.mTabNameList.remove(i);
    	}
    	int s = mApp.mTabMap.size();
    	//Toast.makeText(this, "s = " + String.valueOf(s), Toast.LENGTH_LONG).show();
    	for (TabInfo ti : mApp.mTabMap.values()){
    		TabHost.TabSpec tabSpec = mApp.mTabHost.newTabSpec(ti.tabName);
    		//tabSpec.setIndicator(tabInfo.title);	// title.
    		if (ti.title.equals("")){
    			//Toast.makeText(this, "add tab title = " + ti.tabName, Toast.LENGTH_LONG).show();
    			ti.title = ti.tabName;
    		}
    		final String tag2 = ti.tabName;
    		final CustomTabWidget widget = new CustomTabWidget(this, ti.title, ti.tabName, new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				//Toast.makeText(mContext, "click(4) : " + tag2, Toast.LENGTH_LONG).show();
    				removeTab(tag2);
    			}
    			
    		});
    		tabSpec.setIndicator(widget);
    		Intent intent = new Intent(this, SubActivity.class);	// intentを生成.
            Bundle args = new Bundle();	// args作成.
            args.putString("tag", ti.tabName);	// ("tag", ti.tabName)で登録.
            args.putBoolean("remove", true);
            intent.putExtras(args);	// args登録.
            //tabSpec.setContent(intent);	// intentをセット.
            //tabSpec.setContent(this);
            mApp.mTabHost.addTab(tabSpec);	// tabSpecを追加.
            //Toast.makeText(this, "add tabName = " + ti.tabName + " title = " + ti.title, Toast.LENGTH_LONG).show();
    	}
    	
    }
    
    // タブ状態の保存.
    public void saveTabState(){
    	
    	TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);
    	tabInfo.date = System.currentTimeMillis();
    	tabInfo.view = mFL.findViewById(R.id.layout_sub);
    	mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);
    	
    	/*
    	// 現在のタブをマップに保存.
    	View rootView = getWindow().getDecorView();	// getWindow().getDecorViewでrootViewを取得.
		View content = rootView.findViewById(R.id.layout_main);	// rootViewからlayout_mainを抜き出す.
		TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);	// tabInfo取得.
		*/
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
    	/*
		tabInfo.view = content;	// contentを保存.
		tabInfo.tabName = mCurrentTabName;	// 現在のタブ名をセット.
		tabInfo.title = getActionBar().getTitle().toString();	// タイトルを取得.(アクションバーから取得が確実かも.)
		tabInfo.url = getUrl();
		tabInfo.date = System.currentTimeMillis();	// 現在日時をセット.
		mApp.mTabMap.put(mCurrentTabName, tabInfo);	// mCurrentTabNameをキーにtabInfoを登録.
		//}
		
		// DBにも保存.
		mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);	// updateTabInfoでtabInfoを更新.
		*/
		
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
    	
    	/*
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
		*/
		
    }
    
    // ブックマークへの追加.(独自DB版.)
    public void addBookmarkToDB(){
    	
    	// カレントタブからタイトルとURLを取得.
    	String title = getWebTitle();
    	String url = getWebUrl();
    	long datemillisec = System.currentTimeMillis();	// System.currentTimeMillisで現在時刻を取得し, datemillisecに格納.
    	//Toast.makeText(this, "title = " + title + ", url = " + url, Toast.LENGTH_LONG).show();
    	// このURLをブックマークへ追加.
    	long id = mApp.mHlpr.insertRowBookmark(title, url, datemillisec);	// mApp.mHlpr.insertRowBookmarkでtitle, url, datemillisecを追加.
		if (id <= 0){	// 更新失敗.
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_errorに定義されたメッセージをToastで表示.
		}
		else{
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_successに定義されたメッセージをToastで表示.
		}
    	/*
    	// webViewを取得し, URLとタイトルを取得.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
		String title = webView.getTitle();	// webView.getTitleでタイトルを取得.
		String url = webView.getUrl();	// webView.getUrlでURLを取得.
		long datemillisec = System.currentTimeMillis();	// System.currentTimeMillisで現在時刻を取得し, datemillisecに格納.
		
		// このURLをブックマークへ追加.
		long id = mApp.mHlpr.insertRowBookmark(title, url, datemillisec);	// mApp.mHlpr.insertRowBookmarkでtitle, url, datemillisecを追加.
		if (id <= 0){	// 更新失敗.
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_errorに定義されたメッセージをToastで表示.
		}
		else{
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_successに定義されたメッセージをToastで表示.
		}
		*/
		
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
    	
    	/*
    	// webViewの取得.
    	mCurrentUA = strUA;	// mCurrentUAにstrUAをセット.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
        webView.getSettings().setUserAgentString(mCurrentUA);	// webView.getSettings().setUserAgentStringでmCurrentUAをセット.
        webView.reload();	// webView.reloadでリロード.
        */
    	
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