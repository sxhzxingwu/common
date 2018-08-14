package com.android.weici.common.widget.popup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * 继承自 {@link VUIPopup}，在 {@link VUIPopup} 的基础上，支持显示一个列表。
 *
 * @author cginechen
 * @date 2016-11-16
 */

public class VUIListPopup extends VUIPopup {
    private ListView mListView;
    private BaseAdapter mAdapter;

    /**
     * Constructor.
     *
     * @param context   Context
     * @param direction
     */
    public VUIListPopup(Context context, int direction, BaseAdapter adapter) {
        super(context, direction);
        mAdapter = adapter;
    }

    public void create(int width, int maxHeight, AdapterView.OnItemClickListener onItemClickListener) {
        mListView = new VUIWrapContentListView(mContext, maxHeight);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        mListView.setLayoutParams(lp);
        mListView.setAdapter(mAdapter);
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setOnItemClickListener(onItemClickListener);
        mListView.setDivider(null);
        setContentView(mListView);
    }

    public void create(View view) {
        setContentView(mListView);
    }
}
