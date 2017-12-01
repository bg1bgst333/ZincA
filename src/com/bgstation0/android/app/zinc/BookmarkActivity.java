// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

// ブックマークアクティビティクラスBookmarkActivity
public class BookmarkActivity extends Activity implements OnItemClickListener, OnItemLongClickListener{	// AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListenerインターフェースの追加.

	// メンバフィールドの初期化.
	public static final int DIALOG_ID_CONFIRM_BOOKMARK_REMOVE = 0;	// ブックマーク削除確認のダイアログID.
	
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
    	
    	// 削除確認ダイアログ
    	showConfirmBookmarkRemove(position);	// showConfirmBookmarkRemoveで削除確認ダイアログの表示.(positionを渡す.)
    	
    	// 通常の選択を有効にさせないためにはtrueを返す.
    	return true;	// returnでtrueを返す.
    	
    }
    
    // ダイアログの作成時.
    @Override
    protected Dialog onCreateDialog(final int id, final Bundle args){
    
    	// idごとに振り分け.
    	if (id == DIALOG_ID_CONFIRM_BOOKMARK_REMOVE){	// ブックマーク削除確認.

    		// アラートダイアログの作成.
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);	// builderの作成.
    		builder.setTitle(getString(R.string.dialog_title_confirm_bookmark_remove));	// タイトルのセット.
    		builder.setMessage(getString(R.string.dialog_message_confirm_bookmark_remove));	// メッセージのセット.
    		builder.setPositiveButton(getString(R.string.dialog_button_positive_yes), new DialogInterface.OnClickListener() {
    			
    			// "はい"ボタンが選択された時.
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int position = args.getInt("position");	// positionを取得.
			    	final ListView lv = (ListView)findViewById(R.id.listview_bookmark);	// リストビューlvの取得.
			    	final BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPositionでitemを取得.
			    	removeBookmark(item);	// removeBookmarkで削除.
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
    	
    	// BOOKMARKフラグを降ろす.
    	ContentValues values = new ContentValues();	// ContentValuesオブジェクトvaluesの生成.
    	values.put(Browser.BookmarkColumns.BOOKMARK, "0");	// values.putでBOOKMARKフラグは"0"として登録.
    	int row = getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{item.url});	// getContentResolver().updateでURLが同じ行を更新.
    	
    	// ListViewの取得
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// リストビューlvBookmarkの取得.
        
        // adapterの取得.
        BookmarkAdapter adapter = (BookmarkAdapter)lvBookmark.getAdapter();	// lvBookmark.getAdapterでadapterを取得.
        
        // 削除.
        adapter.remove(item);	// adapter.removeでitemを削除.
        
        // 更新.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChangedで更新.
        
    }
    
    // ブックマークの削除確認ダイアログ.
    private void showConfirmBookmarkRemove(int position){
    
    	// ダイアログの表示.
    	Bundle bundle = new Bundle();	// bundle作成.
    	bundle.putInt("position", position);	// positionを登録.
    	showDialog(DIALOG_ID_CONFIRM_BOOKMARK_REMOVE, bundle);	// showDialogにDIALOG_ID_CONFIRM_BOOKMARK_REMOVEとbundleを渡す.
    	
    }    
    
}