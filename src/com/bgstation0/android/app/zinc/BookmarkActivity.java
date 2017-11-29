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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

// ブックマークアクティビティクラスBookmarkActivity
public class BookmarkActivity extends Activity implements OnItemClickListener, OnItemLongClickListener{	// AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListenerインターフェースの追加.

	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_bookmark);	// setContentViewでR.layout.activity_bookmarkをセット.
        
        // ブックマークのロード.
        loadBookmarks();	// loadBookmarksでブックマークをロードして表示.
        
    }
    
    // リストビューのアイテムが選択された時.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// 選択されたアイテムの取得.
    	final ListView lv = (ListView)parent;	// parentをListViewオブジェクトlvにキャスト.
    	final BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
    	
    	// タイトルとURLを送り返す.
    	setReturnItem(item.title, item.url);	// setReturnItemでitem.titleとitem.urlを送り返すデータとしてセット.
    	finish();	// finishでこのアクティビティを閉じる.
    	
    }
    
    // リストビューのアイテムが長押しされた時.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// 選択されたアイテムの取得.
    	final ListView lv = (ListView)parent;	// parentをListViewオブジェクトlvにキャスト.
    	BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
    	
    	// 長押しされたアイテムをブックマークから削除.
    	removeBookmark(item);	// removeBookmarkでitemを削除.
    	
    	// 通常の選択を有効にさせないためにはtrueを返す.
    	return true;	// returnでtrueを返す.
    	
    }
    
    // ブックマーク一覧の取得.
    public List<BookmarkItem> getAllBookmarks(){
    	
    	// bookmarksの作成
        List<BookmarkItem> bookmarks = new ArrayList<BookmarkItem>();	// ブックマークbookmarksの生成.

        // 全てのブックマークを取得し, アイテムに追加.
        String[] projection = new String[]{	// 取得したいカラム名の配列projection.
        		Browser.BookmarkColumns._ID,	// ID.
        		Browser.BookmarkColumns.TITLE,	// タイトル.
        		Browser.BookmarkColumns.URL,	// URL.
        		Browser.BookmarkColumns.BOOKMARK	// ブックマークフラグ.
        };
        Cursor c = getContentResolver().query(Browser.BOOKMARKS_URI, projection, Browser.BookmarkColumns.BOOKMARK + " = 1", null, Browser.BookmarkColumns.DATE + " desc");	// getContentResolver().queryでブックマーク取得.(Browser.BookmarkColumns.DATE + " desc"で降順ソート, 第3引数にBrowser.BookmarkColumns.BOOKMARK + " = 1"を指定するとブックマークとなる.)
        if (c.moveToFirst()){	// 最初の位置に移動.
        	do{
        		String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// titleの取得.
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// urlの取得.
        		BookmarkItem item = new BookmarkItem();	// itemを生成.
                item.title = title;	// item.titleにtitleをセット.
                item.url = url;	// item.urlにurlをセット.
                bookmarks.add(item);	// bookmarks.addでitemを追加.
        	} while(c.moveToNext());	// 次へ移動.
        }
        c.close();	// c.closeで閉じる.
        
        // bookmarksを返す.
        return bookmarks;	// returnでbookmarksを返す.
        
    }
    
    // ブックマークのロード.
    public void loadBookmarks(){
    	
    	// adapterの生成
        BookmarkAdapter adapter = new BookmarkAdapter(this, R.layout.adapter_bookmark_item, getAllBookmarks());	// アダプタadapterの生成.(getAllBookmarks()でブックマークリストを取得し, 第3引数にセット.)
        
        // ListViewの取得
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// リストビューlvBookmarkの取得.
        
        // リストビューにアダプタをセット.
        lvBookmark.setAdapter(adapter);	//  lvBookmark.setAdapterでadapterをセット.
        
        // 更新.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
        // AdapterView.OnItemClickListenerのセット.
        lvBookmark.setOnItemClickListener(this);	// this(自身)をセット.
        
        // AdapterView.OnItemLongClickListenerのセット.
        lvBookmark.setOnItemLongClickListener(this);	// this(自身)をセット.
        
    }
    
    // 送り返すインテントにタイトルとURLをセット.
    public void setReturnItem(String title, String url){
    	
    	// 送り返すインテントを準備し, finishすることで戻った先にデータが返る.
       	Intent data = new Intent();	// Intentオブジェクトdataの作成.
   	    Bundle bundle = new Bundle();	// Bundleオブジェクトbundleの作成.
   	    bundle.putString("title", title);	// bundle.putStringキー"title", 値titleを登録.
   	    bundle.putString("url", url);	// bundle.putStringでキー"url", 値urlを登録.
   	    data.putExtras(bundle);	// data.putExtrasでbundleを登録.
   	    setResult(RESULT_OK, data);	// setResultでRESULT_OKとdataをセット.
    	    	
    }
    
    // ブックマークの削除.
    public void removeBookmark(BookmarkItem item){
    	
    	// ContentResolverからの削除.
    	int row = getContentResolver().delete(Browser.BOOKMARKS_URI, Browser.BookmarkColumns.URL + "=?", new String[]{item.url});

    	// ListViewの取得
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// リストビューlvBookmarkの取得.
        
        // adapterの取得.
        BookmarkAdapter adapter = (BookmarkAdapter)lvBookmark.getAdapter();	// lvBookmark.getAdapterでadapterを取得.        
        
        // 削除.
        adapter.remove(item);	// adapter.removeでitemを削除.
        
        // 更新.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
    }
    
}