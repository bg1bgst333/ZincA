// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.widget.ListView;

// ブックマークアクティビティクラスBookmarkActivity
public class BookmarkActivity extends Activity {

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
        Cursor c = Browser.getAllBookmarks(getContentResolver());	// getContentResolver()で取得したContentResolverをBrowser.getAllBookmarksに渡して, ブックマークのカーソルを取得.
        if (c.moveToFirst()){	// 最初の位置に移動.
        	do{
        		//String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// titleの取得.(実際には-1が返り, タイトルが取得できない.)
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// urlの取得.
        		BookmarkItem item = new BookmarkItem();	// itemを生成.
                item.title = "";//title;	// item.titleにtitleをセット.(タイトルを取得できないので, とりあえず空欄にする.)
                item.url = url;	// item.urlにurlをセット.
                adapter.add(item);	// adapter.addでitemを追加.
        	} while(c.moveToNext());	// 次へ移動.
        }
        c.close();	// c.closeで閉じる.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
    }
    
}