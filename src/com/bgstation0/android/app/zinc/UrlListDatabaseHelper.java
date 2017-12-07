// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
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
	private static final int DB_VERSION = 1;	// DB�o�[�W������1.
	private static final String TABLE_TABS = "tabs";	// tabs�e�[�u��.
	private static final String CREATE_TABLE_TABS = "create table " + TABLE_TABS + " ( _id integer primary key, tabname string, title string, url string, datemillisec long);";	// TABLE_TABS��CREATE_TABLE��.(sqlite�V�X�e�����g���Ă���_id��integer�Ȃ̂�long�ɂ���ƕʂ̃J�����Ƃ��Ĉ�����̂�integer�ɂ���.)
	private static final String DROP_TABLE_TABS = "drop table " + TABLE_TABS + ";";	// TABLE_TABS��DROP_TABLE��.
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
	
}