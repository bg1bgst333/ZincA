// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.app.Activity;
import android.os.Bundle;
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

	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_main);	// setContentViewでR.layout.activity_mainをセット.
        
        // navigateButtonを取得し, OnClickListenerとして自身(this)をセット.
        Button navigateButton = (Button)findViewById(R.id.button_navigate);	// findViewByIdでR.id.button_navigateからButtonオブジェクトnavigateButtonを取得.
        navigateButton.setOnClickListener(this);	// navigateButton.setOnClickListenerでthis(自身)をセット.
        
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
    		// URLをトーストで表示.
    		EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
    		Toast.makeText(this, etUrl.getText(), Toast.LENGTH_LONG).show();	// etUrl.getText()で取得したURLをToastで表示.
    	}
    	return super.onOptionsItemSelected(item);	// 親クラスのonOptionsItemSelectedを呼ぶ.
    	
    }
    
    // navigateButton("送信")が押された時.
    public void onClick(View v){	// OnClickListener.onClickをオーバーライド.
    	
    	// ボタンごとに振り分ける.
    	switch (v.getId()){	// v.getId()でView(Button)のidを取得.
    	
	    	// R.id.button_navigate("送信")の時.
			case R.id.button_navigate:
				
				// button_navigateブロック
				{
					
					// ウェブビューで入力されたURLのWebページを表示.
		    		EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewByIdでR.id.edittext_urlbarからEditTextオブジェクトetUrlを取得.
		    		WebView webView = (WebView)findViewById(R.id.webview);	// findViewByIdでR.id.webviewからWebViewオブジェクトwebViewを取得.
		    		String url = etUrl.getText().toString();	// etUrl.getText().toString()で入力されたURL文字列を取得し, urlに格納.
		    		webView.loadUrl(url);	// webView.loadUrlでurlの指すWebページをロード.
	
				}
				
				// 抜ける.
				break;	// breakで抜ける.
    			
    		// それ以外の時.
    		default:
    			
    			// 抜ける.
    			break;	// breakで抜ける.
    	
    	}
    	
    }
    
}