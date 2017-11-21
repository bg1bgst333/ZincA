// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//メインアクティビティクラスMainActivity
public class MainActivity extends Activity implements OnClickListener {	// View.OnClickListenerインターフェースの追加.

	// メンバフィールドの初期化.
	public static final int REQUEST_CODE_BOOKMARK = 1001;	// REQUEST_CODE_BOOKMARKを1001とする.
	public static final int REQUEST_CODE_HISTORY = 1002;	// REQUEST_CODE_HISTORYを1002とする.
	
	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_main);	// setContentViewでR.layout.activity_mainをセット.
        initNavigateButton();	// initNavigateButtonでnavigateButtonを初期化.
        initCustomWebViewClient();	// initCustomWebViewClientでCustomWebViewClientを初期化.
        
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
    	if (resultCode == RESULT_CANCELED){	// resultCodeがRESULT_CANCELEDの場合.
    		return;	// 何もせず終了.
    	}
    	
    	// 起動したアクティビティが閉じた時の結果に対する処理.
    	Bundle bundle = data.getExtras();	// data.getExtrasでbundleを取得.
    	
    	// 処理の振り分け.
    	switch (requestCode){	// requestCodeごとに振り分け.
    	
    		// ブックマーク一覧.
    		case REQUEST_CODE_BOOKMARK:	// ブックマークの一覧から戻ってきた場合.
    		// 履歴一覧.
    		case REQUEST_CODE_HISTORY:	// 履歴の一覧から戻ってきた場合.
    			
    			// ブックマークおよび履歴のアイテムが選択された時.
    			if (resultCode == RESULT_OK){	// RESULT_OKの場合.
    				loadSelectedUrl(bundle);	// loadSelectedUrlでbundleで渡したURLをロード.
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
    	if (id == R.id.menu_item_bookmark_add){	// R.id.menu_item_bookmark_add("ブックマークの追加")の時.

    		// ブックマークの追加.
    		addBookmark();	// addBookmarkで追加.
    		
    	}
    	else if (id == R.id.menu_item_bookmark_show){	// R.id.menu_item_bookmark_show("ブックマークの一覧")の時.
    	
    		// ブックマークの表示.
    		showBookmark();	// showBookmarkで表示.
    		
    	}
    	else if (id == R.id.menu_item_history_show){	// R.id.menu_item_history_show("履歴の一覧")の時.
    		
    		// 履歴の表示.
    		showHistory();	// showHistoryで表示.
    		
    	}
    	
    	// あとは既定の処理に任せる.
    	return super.onOptionsItemSelected(item);	// 親クラスのonOptionsItemSelectedを呼ぶ.
    	
    }
    
    // navigateButton("送信")が押された時.
    @Override
    public void onClick(View v){	// OnClickListener.onClickをオーバーライド.
    	
    	// ボタンごとに振り分ける.
    	switch (v.getId()){	// v.getId()でView(Button)のidを取得.
    	
	    	// R.id.button_navigate("送信")の時.
			case R.id.button_navigate:
				
				// button_navigateブロック
				{
					
					// ウェブビューで入力されたURLのWebページを表示.
		    		loadUrl();	// loadUrlでURLバーのURLをロード.
	
				}
				
				// 抜ける.
				break;	// breakで抜ける.
    			
    		// それ以外の時.
    		default:
    			
    			// 抜ける.
    			break;	// breakで抜ける.
    	
    	}
    	
    }
    
    // "送信"ボタンの初期化.
    void initNavigateButton(){
    	
    	// navigateButtonを取得し, OnClickListenerとして自身(this)をセット.
        Button navigateButton = (Button)findViewById(R.id.button_navigate);	// findViewByIdでR.id.button_navigateからButtonオブジェクトnavigateButtonを取得.
        navigateButton.setOnClickListener(this);	// navigateButton.setOnClickListenerでthis(自身)をセット.
        
    }
    
    // カスタムウェブビュークライアントの初期化.
    void initCustomWebViewClient(){
    	
    	// CustomWebViewClientのセット.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabledでJavaScriptを有効にする.
        webView.setWebViewClient(new CustomWebViewClient(this));	// newで生成したCustomWebViewClientオブジェクト(コンストラクタの引数にthisを渡す.)をwebView.setWebViewClientでセット.
        
    }
    
    // URLバーにURLをセット.
    void setUrl(String url){
    	
    	// etUrlを取得し, urlをセット.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    	etUrl.setText(url);	// etUrl.SetTextでURLバーのetUrlにurlをセット.
    	
    }
    
    // URLバーからURLを取得.
    String getUrl(){
    	
    	// etUrlのURLを取得.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    	return etUrl.getText().toString();	// etUrl.getText().toStringでURLを返す.
    	
    }
    
    //　URLバーのURLをロード.
    void loadUrl(){
    	
    	// URLを取得し, ロード.
    	loadUrl(getUrl());	// getUrlしたURLをloadUrlでロード.
    	
    }
    
    // 指定されたURLをロード.
    void loadUrl(String url){
    	
    	// webViewを取得し, urlをロード.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
		webView.loadUrl(url);	// webView.loadUrlでurlの指すWebページをロード.
		
    }
    
    // 選択されたURLをロード.
    void loadSelectedUrl(Bundle bundle){
    	
    	// bundleからURLを取得しロード.
    	String url = bundle.getString("url");	// bundle.getStringでurlを取得.
		setUrl(url);	// setUrlでURLバーにURLをセット.
		loadUrl();	// loadUrlでURLバーのURLをロード.
		
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
    
    // ブックマークへの追加.
    public void addBookmark(){
    	
    	// webViewを取得し, URLとタイトルをブックマークに登録.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
		String title = webView.getTitle();	// webView.getTitleでタイトルを取得.
		String url = webView.getUrl();	// webView.getUrlでURLを取得.
		Browser.saveBookmark(this, title, url);	// Browser.saveBookmarkでブックマークに追加.
		
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
    
}