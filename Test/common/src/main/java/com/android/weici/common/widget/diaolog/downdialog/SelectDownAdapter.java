package com.android.weici.common.widget.diaolog.downdialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.weici.common.R;

import java.util.List;

public class SelectDownAdapter extends BaseAdapter{

	private List<SortListItem> mListString;
	private Context mContext;
	private int mSelectIndex = -1;
	public SelectDownAdapter(Context mContext, List<SortListItem> mListString) {
		this.mContext = mContext;
		this.mListString = mListString;
	}

	public void setSelectIndex(int index){
		mSelectIndex = index;
	}

	@Override
	public int getCount() {
		return null==mListString?0:mListString.size();
	}

	@Override
	public SortListItem getItem(int position) {
		return mListString.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null==convertView){
			convertView = View.inflate(mContext, R.layout.select_down_item, null);
			holder = new ViewHolder();
			holder.btn = convertView.findViewById(R.id.item);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		final SortListItem item = mListString.get(position);
		holder.btn.setText(item.itemText);
		holder.btn.setTextColor(ContextCompat.getColor(mContext, (item.isSelected || (mSelectIndex != -1 && mSelectIndex == position))
				?R.color.theme1:R.color.color_393a3f));
		holder.btn.setClickable(false);
		holder.btn.setFocusable(false);
		return convertView;
	}

	private class ViewHolder{
		
		TextView btn;
	}
	
}
