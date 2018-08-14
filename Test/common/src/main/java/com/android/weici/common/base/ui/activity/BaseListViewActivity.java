package com.android.weici.common.base.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.weici.common.R;
import com.android.weici.common.Tools;
import com.android.weici.common.base.WBaseAdapter;
import com.android.weici.common.widget.VUITitleBar;

import java.util.List;


/**
 * Created by weici on 2017/10/30.
 * 避免在每次使用ListView时创建新的Adapter
 */

public abstract class BaseListViewActivity<T> extends BaseCommonActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    protected ListView mListView;
    protected VUITitleBar mTitleBar;
    protected WBaseAdapter<T> mAdapter;

    private void init(){
        mListView = findViewById(R.id.listview);
        mTitleBar = findViewById(R.id.title_bar);
        if(mTitleBar != null && !TextUtils.isEmpty(getTitleText()))
            mTitleBar.setTitle(getTitleText());
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    protected void initView(){

    }
    @Override
    protected void initContentView() {
        init();
        initView();
    }

    protected void onClickListener(View.OnClickListener listener, int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    protected void onClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    /**
     * 该布局文件如果不能满足需求时，可以重新写布局文件，
     * ListView和titleBar的id必须一样
     *
     * @return 布局文件ID
     */
    protected int getContentView() {
        return R.layout.activity_list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        position = position - mListView.getHeaderViewsCount();
        onItemClick(position, mAdapter.getItem(position));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        position = position - mListView.getHeaderViewsCount();
        onItemLongClick(mAdapter.getItem(position));
        return true;
    }

    protected List<T> getData(){
        return mAdapter == null?null:mAdapter.getList();
    }
    private class ListAdapter extends WBaseAdapter<T> {

        public ListAdapter(List<T> list) {
            super(list, BaseListViewActivity.this);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent, T data) {
            return BaseListViewActivity.this.getView(position, convertView, data);
        }
    }

    protected void onItemLongClick(T t){

    }

    protected WBaseAdapter<T> getAdapter(List<T> list){
        return new ListAdapter(list);
    }
    protected void setData(List<T> list) {
        if (null == list) return;
        if(mAdapter == null) {
            mAdapter = getAdapter(list);
            mListView.setAdapter(mAdapter);
        }else{
            mAdapter.notifyData(list);
        }
    }

    protected void addData(int index, List<T> list) {
        if (null == list) return;
        if(mAdapter == null) {
            mAdapter = getAdapter(list);
            mListView.setAdapter(mAdapter);
        }else{
            mAdapter.addData(index, list);
        }
    }

    protected void setDividerType1(){
        mListView.setDivider(ContextCompat.getDrawable(this, R.drawable.listview_divider));
        mListView.setDividerHeight(1);
    }

    protected void setDividerType2(){
        mListView.setDivider(new ColorDrawable(0xfff4f4f4));
        mListView.setDividerHeight(1);
    }

    protected void setDividerType3(){
        mListView.setDivider(new ColorDrawable(0xfff4f4f4));
        mListView.setDividerHeight(Tools.dip2px(this, 10));
    }

    protected void setDividerType4(){
        mListView.setDivider(new ColorDrawable(0x00000000));
        mListView.setDividerHeight(0);
    }
    protected void notifyAdapter(){
        mAdapter.notifyDataSetInvalidated();
    }
    protected abstract View getView(int position, View convertView, T item);
    protected void onItemClick(int position, T item){};

    protected String getTitleText(){return null;}
}
