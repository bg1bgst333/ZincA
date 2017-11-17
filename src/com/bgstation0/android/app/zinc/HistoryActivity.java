// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import android.app.Activity;
import android.os.Bundle;

// ヒストリーアクティビティクラスHistoryActivity
public class HistoryActivity extends Activity {

	// アクティビティが作成された時.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// ビューのセット
        super.onCreate(savedInstanceState);	// 親クラスのonCreateを呼ぶ.
        setContentView(R.layout.activity_history);	// setContentViewでR.layout.activity_historyをセット.
        
    }
    
}