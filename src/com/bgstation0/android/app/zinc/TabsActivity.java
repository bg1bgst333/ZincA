// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

// タブスアクティビティクラスTabsActivity
public class TabsActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {	// AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListenerインターフェースの追加.

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
    	
    	// 選択されたアイテムの取得.
    	final ListView lv = (ListView)parent;	// parentをListViewオブジェクトlvにキャスト.
    	final TabItem item = (TabItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
    	
    	// タブ名とタイトルを送り返す.
    	setReturnItem(item.tabName, item.title);	// setReturnItemでitem.tabName, item.titleを送り返すデータとしてセット.
    	finish();	// finishでこのアクティビティを閉じる.
    	
    }
    
    // リストビューのアイテムが長押しされた時.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// 選択されたアイテムの取得.
    	final ListView lv = (ListView)parent;	// parentをListViewオブジェクトlvにキャスト.
    	TabItem item = (TabItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
    	
    	// 長押しされたアイテムをタブから削除.
    	removeTab(item);	// removeTabでitemを削除.
    	
    	// 通常の選択を有効にさせないためにはtrueを返す.
    	return true;	// returnでtrueを返す.
    	
    }
    
    // タブ一覧の取得.
    public List<TabItem> getActiveTabs(){
    	
    	// tabsの作成
        List<TabItem> tabs = new ArrayList<TabItem>();	// タブスtabsの生成.
        
        // ソート済みエントリーリストからTabInfoを取り出して, TabItemにセット.
        for (Entry<String, TabInfo> entry : mApp.getTabMapEntryList()){
        	TabItem item = new TabItem();	// TabItemオブジェクトitemを生成.
        	TabInfo tabInfo = entry.getValue();	// tabInfo取得.
    		item.title = tabInfo.title;	// title
    		item.url = tabInfo.url;	// url
    		item.tabName = tabInfo.tabName;	// タブ名.
    		tabs.add(item);	// tabs.addでitem追加.
        }
    	
    	// tabsを返す.
    	return tabs;	// returnでtabsを返す.
    	
    }
    
    // タブのロード.
    public void loadTabs(){

    	// adapterの生成
        TabAdapter adapter = new TabAdapter(this, R.layout.adapter_tab_item, getActiveTabs());	// アダプタadapterの生成.(getActiveTabs()を第3引数にセット.)
        
        // ListViewの取得
        ListView lvTabs = (ListView)findViewById(R.id.listview_tabs);	// リストビューlvTabsの取得.
        
        // リストビューにアダプタをセット.
        lvTabs.setAdapter(adapter);	//  lvTabs.setAdapterでadapterをセット.
        
        // 更新.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
        // AdapterView.OnItemClickListenerのセット.
        lvTabs.setOnItemClickListener(this);	// this(自身)をセット.

        // AdapterView.OnItemLongClickListenerのセット.
        lvTabs.setOnItemLongClickListener(this);	// this(自身)をセット.
        
    }
    
    // 送り返すインテントにタブ名をセット.
    public void setReturnItem(String tabName, String title){
    	
    	// 送り返すインテントを準備し, finishすることで戻った先にデータが返る.
       	Intent data = new Intent();	// Intentオブジェクトdataの作成.
   	    Bundle bundle = new Bundle();	// Bundleオブジェクトbundleの作成.
   	    bundle.putString("tabName", tabName);	// bundle.putStringキー"tabName", 値tabNameを登録.
   	    bundle.putString("title", title);	// bundle.putStringキー"title", 値titleを登録.
   	    data.putExtras(bundle);	// data.putExtrasでbundleを登録.
   	    setResult(RESULT_OK, data);	// setResultでRESULT_OKとdataをセット.
    	    	
    }
    
    // タブの削除.
    public void removeTab(TabItem item){
    	
    	// itemのタブ名を取得.
    	String tabName = item.tabName;	// item.tabNameでtabNameを取得.
    	
    	// タブの削除.
    	mApp.mTabMap.remove(tabName);	// tabNameで登録されたtabInfoをmTabMapから削除.
    	
    	// ListViewの取得
    	ListView lvTabs = (ListView)findViewById(R.id.listview_tabs);	// リストビューlvTabsの取得.
    	
    	// adapterの取得.
    	TabAdapter adapter = (TabAdapter)lvTabs.getAdapter();	// lvTabs.getAdapterでadapterを取得.
    	
    	// 削除.
    	adapter.remove(item);	// adapter.removeでitemを削除.
    	
    	// 更新.
    	adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
    	
    }
    
}