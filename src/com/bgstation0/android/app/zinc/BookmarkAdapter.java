// パッケージの名前空間
package com.bgstation0.android.app.zinc;

//パッケージのインポート
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// ブックマークアダプタクラスBookmarkAdapter
public class BookmarkAdapter extends ArrayAdapter<BookmarkItem> {

	// メンバフィールドの定義
	private LayoutInflater inflater;	// LayoutInflaterオブジェクトinflater
	
	// コンストラクタ
	public BookmarkAdapter(Context context, int resource){
		
		// インフレータの取得
		super(context, resource);	// 親コンストラクタを呼ぶ.
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);	// context.getSystemServiceでLAYOUT_INFLATER_SERVICEを取得し, inflaterに格納.
				
	}
		
	// コンストラクタ
	public BookmarkAdapter(Context context, int resource, List<BookmarkItem> objects){
		
		// インフレータの取得
		super(context, resource, objects);	// 親コンストラクタを呼ぶ.
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);	// context.getSystemServiceでLAYOUT_INFLATER_SERVICEを取得し, inflaterに格納.
		
	}
	
	// ビューの取得
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		// convertViewから取得したそれぞれのビューにアイテムの各要素をセットする.
		if (convertView == null){	// convertViewがnullの時.
			// インフレータがリソースから生成.
			convertView = inflater.inflate(R.layout.adapter_bookmark_item, null);	// inflater.inflateでR.layout.adapter_bookmark_itemからconvertViewを生成.
		}
		TextView tvTitle = (TextView)convertView.findViewById(R.id.textview_bookmark_item_title);	// findViewByIdでR.id.textview_bookmark_item_titleからtvTitleを取得.
		TextView tvUrl = (TextView)convertView.findViewById(R.id.textview_bookmark_item_url);	// findViewByIdでR.id.textview_bookmark_item_urlからtvUrlを取得.
		tvTitle.setText(getItem(position).title);	// getItem(position)で取得したBookmarkItemのtitleをtvTitle.setTextでセット.
		tvUrl.setText(getItem(position).url);	// getItem(position)で取得したBookmarkItemのurlをtvUrl.setTextでセット.
		return convertView;	// convertViewを返す.
		
	}
	
}