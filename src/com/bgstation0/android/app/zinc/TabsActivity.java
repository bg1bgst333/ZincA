// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
	public static final int DIALOG_ID_CONFIRM_TAB_REMOVE = 0;	// タブ削除確認のダイアログID.
	
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
    	
    	// 削除確認ダイアログ
    	showConfirmTabRemove(position);	// showConfirmTabRemoveで削除確認ダイアログの表示.(positionを渡す.)
    	
    	// 通常の選択を有効にさせないためにはtrueを返す.
    	return true;	// returnでtrueを返す.
    	
    }
    
    // ダイアログの作成時.
    @Override
    protected Dialog onCreateDialog(final int id, final Bundle args){
    	
    	// idごとに振り分け.
    	if (id == DIALOG_ID_CONFIRM_TAB_REMOVE){	// タブ削除確認.

    		// アラートダイアログの作成.
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);	// builderの作成.
    		builder.setTitle(getString(R.string.dialog_title_confirm_tab_remove));	// タイトルのセット.
    		builder.setMessage(getString(R.string.dialog_message_confirm_tab_remove));	// メッセージのセット.
    		builder.setPositiveButton(getString(R.string.dialog_button_positive_yes), new DialogInterface.OnClickListener() {
    			
    			// "はい"ボタンが選択された時.
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int position = args.getInt("position");	// positionを取得.
			    	final ListView lv = (ListView)findViewById(R.id.listview_tabs);	// リストビューlvの取得.
			    	final TabItem item = (TabItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
			    	removeTab(item);	// removeTabで削除.
			    	removeDialog(id);	// removeDialogでダイアログを削除.
				}				
				
			});
    		Dialog dialog = builder.create();	// builder.createでdialogを作成.(ただし, まだ返さない.)
    		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {	// キャンセル時の動作を追加.

    			// ダイアログのキャンセル時.
				@Override
				public void onCancel(DialogInterface dialog) {
					removeDialog(id);	// removeDialogでダイアログを削除.
				}
				
			});
    		return dialog;	// dialogを返す.
    		
    	}
    	
    	// nullを返す.
    	return null;	// returnでnullを返す.
    	
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
    
    // タブの削除確認ダイアログ.
    private void showConfirmTabRemove(int position){
    	
    	// ダイアログの表示.
    	Bundle bundle = new Bundle();	// bundle作成.
    	bundle.putInt("position", position);	// positionを登録.
    	showDialog(DIALOG_ID_CONFIRM_TAB_REMOVE, bundle);	// showDialogにDIALOG_ID_CONFIRM_TAB_REMOVEとbundleを渡す.
    	
    }
    
}