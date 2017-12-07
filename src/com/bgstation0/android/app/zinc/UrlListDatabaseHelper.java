// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Browser;
import android.util.Log;
import android.widget.Toast;

// URLリストデータベースヘルパークラスUrlListDatabaseHelper
public class UrlListDatabaseHelper extends SQLiteOpenHelper {

	// メンバフィールドの定義.
	private static final String TAG = "UrlListDatabaseHelper";	// TAGを"UrlListDatabaseHelper"に初期化.
	private static final String DB = "urllist.db";	// DB名は"urllist.db".
	private static final int DB_VERSION = 1;	// DBバージョンは1.
	private static final String TABLE_TABS = "tabs";	// tabsテーブル.
	private static final String CREATE_TABLE_TABS = "create table " + TABLE_TABS + " ( _id integer primary key, tabname string, title string, url string, datemillisec long);";	// TABLE_TABSのCREATE_TABLE文.(sqliteシステムが使っている_idはintegerなのでlongにすると別のカラムとして扱われるのでintegerにする.)
	private static final String DROP_TABLE_TABS = "drop table " + TABLE_TABS + ";";	// TABLE_TABSのDROP_TABLE文.
	private Context mContext;	// コンテキスト.
	
	// コンストラクタ
	public UrlListDatabaseHelper(Context context){
		super(context, DB, null, DB_VERSION);	// 親クラスのコンストラクタに任せる.
		mContext = context;	// メンバに代入.
	}
	
	// DB作成時.
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// テーブル作成実行.
		try{	// tryで囲む.
			db.execSQL(CREATE_TABLE_TABS);	// db.execSQLでCREATE_TABLE_TABSを実行.
		}
		catch (Exception ex){	// 例外をcatch.
			Log.d(TAG, ex.toString());	// ex.toStringをログに出力.
		}
		
	}

	// DBバージョンアップグレード時.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		// テーブル再作成.
		db.execSQL(DROP_TABLE_TABS);	// db.execSQLでDROP_TABLE_TABSを実行.
		onCreate(db);	// onCreateにdbを渡して再作成.
		
	}
	
	// タブの追加.
	public long insertRowTab(String tabName, String title, String url, long datemillisec){
		
		// 変数の初期化.
		long id = -1;	// 行IDを-1で初期化.
		SQLiteDatabase sqlite = null;	// SQLiteDatabaseオブジェクトsqliteをnullで初期化.

		// 挿入.
		try{	// tryで囲む.
			sqlite = getWritableDatabase();	// getWritableDatabaseでsqliteを取得.
			ContentValues values = new ContentValues();	// ContentValuesオブジェクトvaluesを生成.
			values.put("tabname", tabName);	// tabNameを登録.
			values.put("title", title);	// titleを登録.
			values.put("url", url);	// urlを登録.
			values.put("datemillisec", datemillisec);	// datemillisecを登録.
			id = sqlite.insertOrThrow(TABLE_TABS, null, values);	// sqlite.insertOrThrowで挿入.
			return id;	// idを返す.
		}
		catch (Exception ex){	// 例外をcatch.
			id = -1;	// idに-1を代入.
			Log.d(TAG, ex.toString());	// ex.toStringをログに出力.
			return id;	// idを返す.
		}
		finally{	// 必ず行う処理.
			if (sqlite != null){	// sqliteがnullでなければ.
				sqlite.close();	// sqlite.closeで閉じる.
				sqlite = null;	// sqliteにnullを格納.
			}
		}
		
	}
	
	// タブ名の更新.
	public boolean updateTabName(long targetId, String tabName){

		// 変数の初期化.
		SQLiteDatabase sqlite = null;	// SQLiteDatabaseオブジェクトsqliteをnullで初期化.
		
		// 更新.
		try{	// tryで囲む.
			sqlite = getWritableDatabase();	// getWritableDatabaseでsqliteを取得.
			ContentValues values = new ContentValues();	// ContentValuesオブジェクトvaluesを生成.
			values.put("tabname", tabName);	// tabNameを登録.
			//Cursor c = sqlite.query(TABLE_TABS, new String[]{"_id"},null, null, null, null, null);
			//c.moveToFirst();
			//int n = c.getCount();
			//long ida = c.getLong(c.getColumnIndex("_id"));
			//c.moveToNext();
			//long idb = c.getLong(c.getColumnIndex("_id"));
			int row = sqlite.update(TABLE_TABS, values, "_id = ?", new String[]{String.valueOf(targetId)});	// targetIdのtabNameをupdate.
			//c.close();
			if (row == 1){	// 1つだけ変更された.
				return true;
			}
			else{	// それ以外.
				throw new Exception("not row == 1!");
			}
		}
		catch (Exception ex){	// 例外をcatch.
			//String s, t;
			//s = ex.toString();
			//t = s;
			//Toast.makeText(mContext, ex.toString(), Toast.LENGTH_LONG).show();
			Log.d(TAG, ex.toString());	// ex.toStringをログに出力.
			return false;
		}
		finally{	// 必ず行う処理.
			if (sqlite != null){	// sqliteがnullでなければ.
				sqlite.close();	// sqlite.closeで閉じる.
				sqlite = null;	// sqliteにnullを格納.
			}
		}
		
	}
	
}