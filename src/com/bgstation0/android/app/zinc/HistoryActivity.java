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
        
        // historyの作成
        List<HistoryItem> history = new ArrayList<HistoryItem>();	// ブックマークhistoryの生成.

        // adapterの生成
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.adapter_history_item, history);	// アダプタadapterの生成.
        
        // ListViewの取得
        ListView lvHistory = (ListView)findViewById(R.id.listview_history);	// リストビューlvHistoryの取得.
        
        // リストビューにアダプタをセット.
        lvHistory.setAdapter(adapter);	//  lvHistory.setAdapterでadapterをセット.
        
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
                adapter.add(item);	// adapter.addでitemを追加.
        	} while(c.moveToNext());	// 次へ移動.
        }
        c.close();	// c.closeで閉じる.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
        // AdapterView.OnItemClickListenerのセット.
        lvHistory.setOnItemClickListener(this);	// this(自身)をセット.
        
    }
    
    // リストビューのアイテムが選択された時.
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// 選択されたアイテムの取得.
    	final ListView lv = (ListView)parent;	// parentをListViewオブジェクトlvにキャスト.
    	final HistoryItem item = (HistoryItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
    	
    	// 送り返すインテントを準備し, finishすることで戻った先にデータが返る.
    	Intent data = new Intent();	// Intentオブジェクトdataの作成.
    	Bundle bundle = new Bundle();	// Bundleオブジェクトbundleの作成.
    	bundle.putString("title", item.title);	// bundle.putStringでキー"title", 値item.titleを登録.
    	bundle.putString("url", item.url);	// bundle.putStringでキー"url", 値item.urlを登録.
    	data.putExtras(bundle);	// data.putExtrasでbundleを登録.
    	setResult(RESULT_OK, data);	// setResultでRESULT_OKとdataをセット.
    	finish();	// finishでこのアクティビティを閉じる.
    	
    }
    
}