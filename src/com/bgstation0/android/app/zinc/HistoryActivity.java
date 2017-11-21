// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// ヒストリーアクティビティクラスHistoryActivity
public class HistoryActivity extends Activity implements OnItemClickListener {	// AdapterView.OnItemClickListenerインターフェースの追加.

	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_history);	// setContentViewでR.layout.activity_historyをセット.
        
        // 履歴のロード.
        loadHistories();	// loadHistoriesで履歴をロード.
        
    }
    
    // リストビューのアイテムが選択された時.
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// 選択されたアイテムの取得.
    	final ListView lv = (ListView)parent;	// parentをListViewオブジェクトlvにキャスト.
    	final HistoryItem item = (HistoryItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
    	
    	// タイトルとURLを送り返す.
    	setReturnItem(item.title, item.url);	// setReturnItemでitem.titleとitem.urlを送り返すデータとしてセット.
    	finish();	// finishでこのアクティビティを閉じる.
    	
    }
    
    // 履歴一覧の取得.
    public List<HistoryItem> getAllHistories(){
    	
    	// historiesの作成
    	List<HistoryItem> histories = new ArrayList<HistoryItem>();	// 履歴historiesの生成.
    
    	// 全てのヒストリーを取得し, アイテムに追加.
        String[] projection = new String[]{	// 取得したいカラム名の配列projection.
        		Browser.BookmarkColumns._ID,	// ID.
        		Browser.BookmarkColumns.TITLE,	// タイトル.
        		Browser.BookmarkColumns.URL,	// URL.
        		Browser.BookmarkColumns.DATE,	// 日時.
        		Browser.BookmarkColumns.BOOKMARK,	// ブックマークフラグ.
        };
        Cursor c = getContentResolver().query(Browser.BOOKMARKS_URI, projection, Browser.BookmarkColumns.BOOKMARK + " = 0", null, Browser.BookmarkColumns.DATE + " desc");	// getContentResolver().queryで履歴取得.(Browser.BookmarkColumns.DATE + " desc"で降順ソート, 第3引数にBrowser.BookmarkColumns.BOOKMARK + " = 0"を指定すると履歴となる.)
        if (c.moveToFirst()){	// 最初の位置に移動.
        	do{
        		String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// titleの取得.
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// urlの取得.
        		long date = c.getLong(c.getColumnIndex(Browser.BookmarkColumns.DATE));	// dateの取得.
        		HistoryItem item = new HistoryItem();	// itemを生成.
                item.title = title;	// item.titleにtitleをセット.
                item.url = url;	// item.urlにurlをセット.
                //item.date = Long.toString(date);	// dateをLong.toStringで文字列変換してitem.dateにセット.
                Date dtDate = new Date(date);	// dateを基にDateオブジェクトdtDateを作成.
                item.date = dtDate.toString();	// item.dateにdtDate.toStringで変換した日時文字列をセット.
                histories.add(item);	// histories.addでitemを追加.
        	} while(c.moveToNext());	// 次へ移動.
        }
        c.close();	// c.closeで閉じる.
        
        // historiesを返す.
        return histories;	// returnでhistoriesを返す.
        
    }
    
    // 履歴のロード.
    public void loadHistories(){
    	
    	// adapterの生成
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.adapter_history_item, getAllHistories());	// アダプタadapterの生成.(getAllHistories()で履歴リストを取得し, 第3引数にセット.)
        
        // ListViewの取得
        ListView lvHistory = (ListView)findViewById(R.id.listview_history);	// リストビューlvHistoryの取得.
        
        // リストビューにアダプタをセット.
        lvHistory.setAdapter(adapter);	//  lvHistory.setAdapterでadapterをセット.
        
        // 更新.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
        // AdapterView.OnItemClickListenerのセット.
        lvHistory.setOnItemClickListener(this);	// this(自身)をセット.
        
    }
    
    // 送り返すインテントにタイトルとURLをセット.
    public void setReturnItem(String title, String url){
    	
    	// 送り返すインテントを準備し, finishすることで戻った先にデータが返る.
    	Intent data = new Intent();	// Intentオブジェクトdataの作成.
    	Bundle bundle = new Bundle();	// Bundleオブジェクトbundleの作成.
    	bundle.putString("title", title);	// bundle.putStringでキー"title", 値titleを登録.
    	bundle.putString("url", url);	// bundle.putStringでキー"url", 値urlを登録.
    	data.putExtras(bundle);	// data.putExtrasでbundleを登録.
    	setResult(RESULT_OK, data);	// setResultでRESULT_OKとdataをセット.
    	
    }
    
}