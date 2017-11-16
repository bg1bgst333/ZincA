// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

// ブックマークアクティビティクラスBookmarkActivity
public class BookmarkActivity extends Activity implements OnItemClickListener {	// AdapterView.OnItemClickListenerインターフェースの追加.

	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_bookmark);	// setContentViewでR.layout.activity_bookmarkをセット.

        // bookmarkの作成
        List<BookmarkItem> bookmark = new ArrayList<BookmarkItem>();	// ブックマークbookmarkの生成.

        // adapterの生成
        BookmarkAdapter adapter = new BookmarkAdapter(this, R.layout.adapter_bookmark_item, bookmark);	// アダプタadapterの生成.
        
        // ListViewの取得
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// リストビューlvBookmarkの取得.
        
        // リストビューにアダプタをセット.
        lvBookmark.setAdapter(adapter);	//  lvBookmark.setAdapterでadapterをセット.
        
        // 全てのブックマークを取得し, アイテムに追加.
        String[] projection = new String[]{	// 取得したいカラム名の配列projection.
        		Browser.BookmarkColumns._ID,	// ID.
        		Browser.BookmarkColumns.TITLE,	// タイトル.
        		Browser.BookmarkColumns.URL	// URL.
        };
        Cursor c = getContentResolver().query(Browser.BOOKMARKS_URI, projection, null, null, Browser.BookmarkColumns._ID + " desc");	// getContentResolver().queryでブックマーク取得.(Browser.BookmarkColumns._ID + " desc"で降順ソート.)
        if (c.moveToFirst()){	// 最初の位置に移動.
        	do{
        		String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// titleの取得.
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// urlの取得.
        		BookmarkItem item = new BookmarkItem();	// itemを生成.
                item.title = title;	// item.titleにtitleをセット.
                item.url = url;	// item.urlにurlをセット.
                adapter.add(item);	// adapter.addでitemを追加.
        	} while(c.moveToNext());	// 次へ移動.
        }
        c.close();	// c.closeで閉じる.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
        // AdapterView.OnItemClickListenerのセット.
        lvBookmark.setOnItemClickListener(this);	// this(自身)をセット.
        
    }
    
    // リストビューのアイテムが選択された時.
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// 選択されたアイテムの取得.
    	final ListView lv = (ListView)parent;	// parentをListViewオブジェクトlvにキャスト.
    	final BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
    	
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