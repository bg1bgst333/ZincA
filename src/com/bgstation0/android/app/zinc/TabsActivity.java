// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// タブスアクティビティクラスTabsActivity
public class TabsActivity extends Activity implements OnItemClickListener {	// AdapterView.OnItemClickListenerインターフェースの追加.

	// メンバフィールドの初期化.
	public MainApplication mApp = null;	// MainApplicationオブジェクトmAppをnullで初期化.
	
	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_tabs);	// setContentViewでR.layout.activity_tabsをセット.
        mApp = (MainApplication)getApplicationContext();	// getApplicationContextでmAppを取得.
        
        // タブのロード.
        loadTabs();	// loadTabsでタブをロードして表示.
        
    }
    
    // リストビューのアイテムが選択された時.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){  	
    	
    }
    
    // タブのロード.
    public void loadTabs(){

    	// tabsの作成
        List<TabItem> tabs = new ArrayList<TabItem>();	// タブスtabsの生成.
        
    	for (String key : mApp.mViewMap.keySet()){
    		View v = (View)mApp.mViewMap.get(key);
    		WebView wv = (WebView)v.findViewById(R.id.webview);
    		TabItem item = new TabItem();
    		item.title = wv.getTitle();
    		item.url = wv.getUrl();
    		item.tabName = key;
    		tabs.add(item);
    	}
    	
    	// adapterの生成
        TabAdapter adapter = new TabAdapter(this, R.layout.adapter_tab_item, tabs);	// アダプタadapterの生成.(tabsを第3引数にセット.)
        
        // ListViewの取得
        ListView lvTabs = (ListView)findViewById(R.id.listview_tabs);	// リストビューlvTabsの取得.
        
        // リストビューにアダプタをセット.
        lvTabs.setAdapter(adapter);	//  lvTabs.setAdapterでadapterをセット.
        
        // 更新.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
        // AdapterView.OnItemClickListenerのセット.
        lvTabs.setOnItemClickListener(this);	// this(自身)をセット.
            
    }
    
}