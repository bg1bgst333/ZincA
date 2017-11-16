// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
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
        
        // 仮アイテムの追加.
        BookmarkItem item1 = new BookmarkItem();	// item1を生成.
        item1.title = "test1";	// titleは"test1".
        item1.url = "http://test1.com";	// urlは"http://test1.com"
        BookmarkItem item2 = new BookmarkItem();	// item2を生成.
        item2.title = "test2";	// titleは"test2".
        item2.url = "http://test2.com";	// urlは"http://test2.com"
        BookmarkItem item3 = new BookmarkItem();	// item3を生成.
        item3.title = "test3";	// titleは"test3".
        item3.url = "http://test3.com";	// urlは"http://test3.com"
        adapter.add(item1);	// adapter.addでitem1を追加.
        adapter.add(item2);	// adapter.addでitem2を追加.
        adapter.add(item3);	// adapter.addでitem3を追加.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
    }
    
}