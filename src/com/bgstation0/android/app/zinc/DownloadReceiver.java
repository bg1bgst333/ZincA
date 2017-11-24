// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

// ダウンロードレシーバークラスDownloadReceiver
public class DownloadReceiver extends BroadcastReceiver {

	// メンバフィールドの定義.
	private Context mContext = null;	// Context型mContextをnullに初期化.
	
	// 引数付きコンストラクタ
	DownloadReceiver(Context context){
				
		// 引数をメンバにセット.
		mContext = context;	// mContextにcontextをセット.
			
	}
		
	// ブロードキャストインテントを受け取った時.
	@Override
	public void onReceive(Context context, Intent intent) {
	
		// アクション文字列ごとに処理を行う.
		String action = intent.getAction();	// intent.getActionでactionを取得.
		if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){	// ダウンロード完了の時.
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,  -1);	// intentからダウンロードIDを取得.
			Query query = new Query();	// Queryオブジェクトqueryを作成.
			query.setFilterById(id);	// query.setFilterByIdにidをセット.
			if (mContext != null){	// mContextがnullでなければ.
				MainActivity mainActivity = (MainActivity)mContext;	// mContextをmainActivityにキャスト.
				if (mainActivity.mDownloadManager != null){	// mDownloadManagerがnullでなければ.
					Cursor c = mainActivity.mDownloadManager.query(query);	// mainActivity.mDownloadManager.queryにqueryを渡し, Cursorのcを取得.
					if (c.moveToFirst()){	// 先頭に移動.
						int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));	// ステータス取得.
						if (status == DownloadManager.STATUS_SUCCESSFUL){	// 成功.
							Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_download_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_download_successで定義されたメッセージをToastで表示.
						}
					}
					c.close();	// c.closeで閉じる.
				}
			}
		}
		
	}

}
