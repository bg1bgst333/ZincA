// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Browser;
import android.util.Log;
import android.widget.Toast;

// URL���X�g�f�[�^�x�[�X�w���p�[�N���XUrlListDatabaseHelper
public class UrlListDatabaseHelper extends SQLiteOpenHelper {

	// �����o�t�B�[���h�̒�`.
	private static final String TAG = "UrlListDatabaseHelper";	// TAG��"UrlListDatabaseHelper"�ɏ�����.
	private static final String DB = "urllist.db";	// DB����"urllist.db".
	private static final int DB_VERSION = 3;	// DB�o�[�W������3.(TABLE_HISTORIES��ǉ���������.)
	private static final String TABLE_TABS = "tabs";	// tabs�e�[�u��.
	private static final String TABLE_BOOKMARKS = "bookmarks";	// bookmarks�e�[�u��.
	private static final String TABLE_HISTORIES = "histories";	// histories�e�[�u��.
	private static final String CREATE_TABLE_TABS = "create table " + TABLE_TABS + " ( _id integer primary key, tabname string, title string, url string, datemillisec long);";	// TABLE_TABS��CREATE_TABLE��.(sqlite�V�X�e�����g���Ă���_id��integer�Ȃ̂�long�ɂ���ƕʂ̃J�����Ƃ��Ĉ�����̂�integer�ɂ���.)
	private static final String CREATE_TABLE_BOOKMARKS = "create table " + TABLE_BOOKMARKS + " ( _id integer primary key, title string, url string, datemillisec long);";	// TABLE_BOOKMARKS��CREATE_TABLE��.
	private static final String CREATE_TABLE_HISTORIES = "create table " + TABLE_HISTORIES + " ( _id integer primary key, title string, url string, datemillisec long);";	// TABLE_HISTORIES��CREATE_TABLE��.
	private static final String DROP_TABLE_TABS = "drop table " + TABLE_TABS + ";";	// TABLE_TABS��DROP_TABLE��.
	private static final String DROP_TABLE_BOOKMARKS = "drop table " + TABLE_BOOKMARKS + ";";	// TABLE_BOOKMARKS��DROP_TABLE��.
	private static final String DROP_TABLE_HISTORIES = "drop table " + TABLE_HISTORIES + ";";	// TABLE_HISTORIES��DROP_TABLE��.
	private Context mContext;	// �R���e�L�X�g.
	
	// �R���X�g���N�^
	public UrlListDatabaseHelper(Context context){
		super(context, DB, null, DB_VERSION);	// �e�N���X�̃R���X�g���N�^�ɔC����.
		mContext = context;	// �����o�ɑ��.
	}
	
	// DB�쐬��.
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// �e�[�u���쐬���s.
		try{	// try�ň͂�.
			db.execSQL(CREATE_TABLE_TABS);	// db.execSQL��CREATE_TABLE_TABS�����s.
			db.execSQL(CREATE_TABLE_BOOKMARKS);	// db.exeqSQL��CREATE_TABLE_BOOKMARKS�����s.
			db.execSQL(CREATE_TABLE_HISTORIES);	// db.execSQL��CREATE_TABLE_HISTORIES�����s.
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
		}
		
	}

	// DB�o�[�W�����A�b�v�O���[�h��.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		// �e�[�u���č쐬.
		db.execSQL(DROP_TABLE_TABS);	// db.execSQL��DROP_TABLE_TABS�����s.
		db.execSQL(DROP_TABLE_BOOKMARKS);	// db.execSQL��DROP_TABLE_BOOKMARKS�����s.
		db.execSQL(DROP_TABLE_HISTORIES);	// db.execSQL��DROP_TABLE_HISTORIES�����s.
		onCreate(db);	// onCreate��db��n���čč쐬.
		
	}
	
	// �^�u�̒ǉ�.
	public long insertRowTab(String tabName, String title, String url, long datemillisec){
		
		// �ϐ��̏�����.
		long id = -1;	// �sID��-1�ŏ�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.

		// �}��.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�𐶐�.
			values.put("tabname", tabName);	// tabName��o�^.
			values.put("title", title);	// title��o�^.
			values.put("url", url);	// url��o�^.
			values.put("datemillisec", datemillisec);	// datemillisec��o�^.
			id = sqlite.insertOrThrow(TABLE_TABS, null, values);	// sqlite.insertOrThrow�ő}��.
			return id;	// id��Ԃ�.
		}
		catch (Exception ex){	// ��O��catch.
			id = -1;	// id��-1����.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return id;	// id��Ԃ�.
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
	// �u�b�N�}�[�N�̒ǉ�.
	public long insertRowBookmark(String title, String url, long datemillisec){
		
		// �ϐ��̏�����.
		long id = -1;	// �sID��-1�ŏ�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.

		// �}��.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�𐶐�.
			values.put("title", title);	// title��o�^.
			values.put("url", url);	// url��o�^.
			values.put("datemillisec", datemillisec);	// datemillisec��o�^.
			id = sqlite.insertOrThrow(TABLE_BOOKMARKS, null, values);	// sqlite.insertOrThrow�ő}��.
			return id;	// id��Ԃ�.
		}
		catch (Exception ex){	// ��O��catch.
			id = -1;	// id��-1����.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return id;	// id��Ԃ�.
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
	// �u�b�N�}�[�N�̒ǉ�.
	public long insertRowHistory(String title, String url, long datemillisec){
		
		// �ϐ��̏�����.
		long id = -1;	// �sID��-1�ŏ�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.

		// �}��.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�𐶐�.
			values.put("title", title);	// title��o�^.
			values.put("url", url);	// url��o�^.
			values.put("datemillisec", datemillisec);	// datemillisec��o�^.
			id = sqlite.insertOrThrow(TABLE_HISTORIES, null, values);	// sqlite.insertOrThrow�ő}��.
			return id;	// id��Ԃ�.
		}
		catch (Exception ex){	// ��O��catch.
			id = -1;	// id��-1����.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return id;	// id��Ԃ�.
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
	// �^�u���̍X�V.
	public boolean updateTabName(long targetId, String tabName){

		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
		
		// �X�V.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�𐶐�.
			values.put("tabname", tabName);	// tabName��o�^.
			//Cursor c = sqlite.query(TABLE_TABS, new String[]{"_id"},null, null, null, null, null);
			//c.moveToFirst();
			//int n = c.getCount();
			//long ida = c.getLong(c.getColumnIndex("_id"));
			//c.moveToNext();
			//long idb = c.getLong(c.getColumnIndex("_id"));
			int row = sqlite.update(TABLE_TABS, values, "_id = ?", new String[]{String.valueOf(targetId)});	// targetId��tabName��update.
			//c.close();
			if (row == 1){	// 1�����ύX���ꂽ.
				return true;
			}
			else{	// ����ȊO.
				throw new Exception("not row == 1!");
			}
		}
		catch (Exception ex){	// ��O��catch.
			//String s, t;
			//s = ex.toString();
			//t = s;
			//Toast.makeText(mContext, ex.toString(), Toast.LENGTH_LONG).show();
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
	// �^�u���̍X�V.
	public boolean updateTabInfo(String tabName, TabInfo tabInfo){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
		
		// �X�V.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�𐶐�.
			values.put("title", tabInfo.title);	// title
			values.put("url", tabInfo.url);	// url
			values.put("datemillisec", tabInfo.date);	// datemillisec
			int row = sqlite.update(TABLE_TABS, values, "tabname = ?", new String[]{tabName});	// update��title, url, datemillisec���X�V.
			if (row == 1){	// 1�����ύX���ꂽ.
				return true;
			}
			else{	// ����ȊO.
				throw new Exception("not row == 1!");
			}
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
	// �^�u�̍폜.
	public boolean removeRowTab(String tabName){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
				
		// �폜.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			int row = sqlite.delete(TABLE_TABS, "tabname = ?", new String[]{tabName});	// sqlite.delete��tabname��tabName�ȍs���폜.
			return true;
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
	// �^�u�̑S�폜.
	public boolean removeAllTabs(){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
		
		// �폜.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			int row = sqlite.delete(TABLE_TABS, null, null);	// sqlite.delete�ł��ׂĂ̍s���폜.
			return true;
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
	// �u�b�N�}�[�N�̍폜.
	public boolean removeRowBookmark(int id){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
						
		// �폜.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			int row = sqlite.delete(TABLE_BOOKMARKS, "_id = ?", new String[]{String.valueOf(id)});	// sqlite.delete��_id��id�ȍs���폜.
			return true;
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
				
	}
	
	// �u�b�N�}�[�N�̑S�폜.
	public boolean removeAllBookmarks(){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
		
		// �폜.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			int row = sqlite.delete(TABLE_BOOKMARKS, null, null);	// sqlite.delete�ł��ׂĂ̍s���폜.
			return true;
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
	}
	
	// �����̍폜.
	public boolean removeRowHistory(int id){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
						
		// �폜.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			int row = sqlite.delete(TABLE_HISTORIES, "_id = ?", new String[]{String.valueOf(id)});	// sqlite.delete��_id��id�ȍs���폜.
			return true;
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
				
	}
	
	// �����̑S�폜.
	public boolean removeAllHistories(){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
		
		// �폜.
		try{	// try�ň͂�.
			sqlite = getWritableDatabase();	// getWritableDatabase��sqlite���擾.
			int row = sqlite.delete(TABLE_HISTORIES, null, null);	// sqlite.delete�ł��ׂĂ̍s���폜.
			return true;
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return false;
		}
		finally{	// �K���s������.
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
	}
	
	// �^�u�ꗗ�̎擾.
	public List<TabInfo> getTabInfoList(){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		"_id",	// ID.
        		"tabname",	// �^�u��.
        		"title",	// �^�C�g��.
        		"url",	// URL.
        		"datemillisec"	// ����.
        };
        List<TabInfo> tabInfoList = new ArrayList<TabInfo>();	// tabInfoList�̐���.
        Cursor c = null;	// c��null�ɏ�����.
        
        // DB����^�u���𓾂�.
		try{	// try�ň͂�.
			sqlite = getReadableDatabase();	// getReadableDatabase��sqlite���擾.
			c = sqlite.query(TABLE_TABS, projection, null, null, null, null, "datemillisec desc");	// sqlite.query�ňꗗ�擾.("datemillisec desc"�œ����~��.)
			c.moveToFirst();	// �擪�ɃZ�b�g.
			do{
				TabInfo tabInfo = new TabInfo();	// tabInfo�̐���.
				tabInfo.tabName = c.getString(c.getColumnIndex("tabname"));	// tabname.
				tabInfo.title = c.getString(c.getColumnIndex("title"));	// title.
				tabInfo.url = c.getString(c.getColumnIndex("url"));	// url.
				tabInfo.date = c.getLong(c.getColumnIndex("datemillisec"));	// datemillisec.
				tabInfoList.add(tabInfo);	// tabInfoList.add��tabInfo��ǉ�.
			} while(c.moveToNext());
			return tabInfoList;	// tabInfoList��Ԃ�.
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return null;	// null��Ԃ�.
		}
		finally{	// �K���s������.
			if (c != null){	// c��null�łȂ����.
				c.close();	// c.close�ŕ���.
				c = null;	// c��null���i�[.
			}
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
	}
	
	// �u�b�N�}�[�N�ꗗ�̎擾.
	public List<BookmarkInfo> getBookmarkList(){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		"_id",	// ID.
        		"title",	// �^�C�g��.
        		"url",	// URL.
        		"datemillisec"	// ����.
        };
        List<BookmarkInfo> bookmarkList = new ArrayList<BookmarkInfo>();	// bookmarkList�̐���.
        Cursor c = null;	// c��null�ɏ�����.
        
        // DB����u�b�N�}�[�N���𓾂�.
		try{	// try�ň͂�.
			sqlite = getReadableDatabase();	// getReadableDatabase��sqlite���擾.
			c = sqlite.query(TABLE_BOOKMARKS, projection, null, null, null, null, "datemillisec desc");	// sqlite.query�ňꗗ�擾.("datemillisec desc"�œ��t�~��.)
			if (c.getCount() <= 0){	// 1���Ȃ�.
				return null;
			}
			c.moveToFirst();	// �擪�ɃZ�b�g.
			do{
				BookmarkInfo bookmarkInfo = new BookmarkInfo();	// bookmarkInfo�̐���.
				bookmarkInfo.title = c.getString(c.getColumnIndex("title"));	// title.
				bookmarkInfo.url = c.getString(c.getColumnIndex("url"));	// url.
				bookmarkInfo.date = c.getLong(c.getColumnIndex("datemillisec"));	// datemillisec.
				bookmarkInfo.id = c.getInt(c.getColumnIndex("_id"));	// _id.
				bookmarkList.add(bookmarkInfo);	// bookmarkList.add��bookmarkInfo��ǉ�.
			} while(c.moveToNext());
			return bookmarkList;	// bookmarkList��Ԃ�.
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return null;	// null��Ԃ�.
		}
		finally{	// �K���s������.
			if (c != null){	// c��null�łȂ����.
				c.close();	// c.close�ŕ���.
				c = null;	// c��null���i�[.
			}
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
				
	}
	
	// �����ꗗ�̎擾.
	public List<HistoryInfo> getHistoryList(){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		"_id",	// ID.
        		"title",	// �^�C�g��.
        		"url",	// URL.
        		"datemillisec"	// ����.
        };
        List<HistoryInfo> historyList = new ArrayList<HistoryInfo>();	// historyList�̐���.
        Cursor c = null;	// c��null�ɏ�����.
        
        // DB���痚�����𓾂�.
		try{	// try�ň͂�.
			sqlite = getReadableDatabase();	// getReadableDatabase��sqlite���擾.
			c = sqlite.query(TABLE_HISTORIES, projection, null, null, null, null, "datemillisec desc");	// sqlite.query�ňꗗ�擾.("datemillisec desc"�œ��t�~��.)
			if (c.getCount() <= 0){	// 1���Ȃ�.
				return null;
			}
			c.moveToFirst();	// �擪�ɃZ�b�g.
			do{
				HistoryInfo historyInfo = new HistoryInfo();	// historyInfo�̐���.
				historyInfo.title = c.getString(c.getColumnIndex("title"));	// title.
				historyInfo.url = c.getString(c.getColumnIndex("url"));	// url.
				historyInfo.date = c.getLong(c.getColumnIndex("datemillisec"));	// datemillisec.
				historyInfo.id = c.getInt(c.getColumnIndex("_id"));	// _id.
				historyList.add(historyInfo);	// historyList.add��historyInfo��ǉ�.
			} while(c.moveToNext());
			return historyList;	// historyList��Ԃ�.
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return null;	// null��Ԃ�.
		}
		finally{	// �K���s������.
			if (c != null){	// c��null�łȂ����.
				c.close();	// c.close�ŕ���.
				c = null;	// c��null���i�[.
			}
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
				
	}
	
	// �Ō�ɍX�V�����^�u�����擾.
	public TabInfo getLastTabInfo(){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		"_id",	// ID.
        		"tabname",	// �^�u��.
        		"title",	// �^�C�g��.
        		"url",	// URL.
        		"datemillisec"	// ����.
        };
        Cursor c = null;	// c��null�ɏ�����.

        // DB����^�u���𓾂�.
		try{	// try�ň͂�.
			sqlite = getReadableDatabase();	// getReadableDatabase��sqlite���擾.
			//sqlite = getWritableDatabase();	// ����������Ȃ��ƃG�~�����[�^�ŗ�����. -> �֌W�Ȃ�����.(�G�~�����[�^���Ɩ���A�b�v�O���[�h�����.)
			c = sqlite.query(TABLE_TABS, projection, null, null, null, null, "datemillisec desc");	// sqlite.query�ňꗗ�擾.("datemillisec desc"�œ����~��.)
			if (c.getCount() == 0){	// �^�u��0.
				return null;	// null��Ԃ�.
			}
			c.moveToFirst();	// �擪�ɃZ�b�g.
			TabInfo tabInfo = new TabInfo();	// tabInfo�̐���.
			tabInfo.tabName = c.getString(c.getColumnIndex("tabname"));	// tabname.
			tabInfo.title = c.getString(c.getColumnIndex("title"));	// title.
			tabInfo.url = c.getString(c.getColumnIndex("url"));	// url.
			tabInfo.date = c.getLong(c.getColumnIndex("datemillisec"));	// datemillisec.
			return tabInfo;	// tabInfo��Ԃ�.
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return null;	// null��Ԃ�.
		}
		finally{	// �K���s������.
			if (c != null){	// c��null�łȂ����.
				c.close();	// c.close�ŕ���.
				c = null;	// c��null���i�[.
			}
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
	
	}
	
	// �w��̃^�u�����擾.
	public TabInfo getTabInfo(String tabName){
		
		// �ϐ��̏�����.
		SQLiteDatabase sqlite = null;	// SQLiteDatabase�I�u�W�F�N�gsqlite��null�ŏ�����.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		"_id",	// ID.
        		"tabname",	// �^�u��.
        		"title",	// �^�C�g��.
        		"url",	// URL.
        		"datemillisec"	// ����.
        };
        Cursor c = null;	// c��null�ɏ�����.

        // DB����^�u���𓾂�.
		try{	// try�ň͂�.
			sqlite = getReadableDatabase();	// getReadableDatabase��sqlite���擾.
			c = sqlite.query(TABLE_TABS, projection, "tabname = ?", new String[]{tabName}, null, null, "datemillisec desc");	// sqlite.query�ł��Ă͂܂�1���擾.("datemillisec desc"�œ����~��.)
			if (c.getCount() == 1){	// 1�Ȃ�.
				c.moveToFirst();	// �擪�ɃZ�b�g.
				TabInfo tabInfo = new TabInfo();	// tabInfo�̐���.
				tabInfo.tabName = c.getString(c.getColumnIndex("tabname"));	// tabname.
				tabInfo.title = c.getString(c.getColumnIndex("title"));	// title.
				tabInfo.url = c.getString(c.getColumnIndex("url"));	// url.
				tabInfo.date = c.getLong(c.getColumnIndex("datemillisec"));	// datemillisec.
				return tabInfo;	// tabInfo��Ԃ�.
			}
			else{	// ����ȊO��null.
				return null;	// null��Ԃ�.
			}
		}
		catch (Exception ex){	// ��O��catch.
			Log.d(TAG, ex.toString());	// ex.toString�����O�ɏo��.
			return null;	// null��Ԃ�.
		}
		finally{	// �K���s������.
			if (c != null){	// c��null�łȂ����.
				c.close();	// c.close�ŕ���.
				c = null;	// c��null���i�[.
			}
			if (sqlite != null){	// sqlite��null�łȂ����.
				sqlite.close();	// sqlite.close�ŕ���.
				sqlite = null;	// sqlite��null���i�[.
			}
		}
		
	}
	
}