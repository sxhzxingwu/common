package com.android.weici.common.base.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.weici.common.R;
import com.android.weici.common.Tools;
import com.android.weici.common.base.WBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by weici on 2017/11/6.
 */

public abstract class BaseListFragment<T> extends BaseCommonFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    protected WBaseAdapter<T> mAdapter;
    protected ListView mListView;
    protected List<String> mSelectIds = new ArrayList<>();
    @Override
    protected void initView() {
        mListView = mRoot.findViewById(R.id.listview);
        addHeadView();
        mAdapter = getAdapter(null);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    protected WBaseAdapter<T> getAdapter(List<T> list){
        return new ListAdapter(list);
    }
    protected void setOnItemLongClickListener(){
        mListView.setOnItemLongClickListener(this);
    }

    protected void addHeadView(){
    }

    protected List<T> getList(){
        return mAdapter.getList();
    }

    protected void adapterNotifyDataSetInvalidated(){
        if(mAdapter != null)
        mAdapter.notifyDataSetInvalidated();
    }
    @Override
    protected int onCreateContent() {
        return R.layout.fragment_listview;
    }

    @Override
    protected String getPageName() {
        return "";
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        onItemLongClick(view, position, mAdapter.getItem(position - mListView.getHeaderViewsCount()));
        return true;
    }

    public void onItemLongClick(View view, int position, T t) {

    }

    protected void remove(T t){
        mAdapter.remove(t);
    }

    protected class ListAdapter extends WBaseAdapter<T>{
        public ListAdapter(List<T> list) {
            super(list, getContext());
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup, T data) {
            return BaseListFragment.this.getView(i, view, data);
        }
    }

    protected void setData(List<T> list){
        if(null == list || list.size() == 0 || mAdapter == null) return;
        mAdapter.notifyData(list);
    }

    protected void addData(int index, List<T> list) {
        mAdapter.addData(index, list);
    }

    protected void update(List<T> list){
        if(mAdapter == null) return;
        if(null == list) list = new ArrayList<>();
        mAdapter.notifyData(list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        T item = mAdapter.getItem(position - mListView.getHeaderViewsCount());
        if(isNeedSelect()) {
            String value = getSelectKeyValue(item);
            if (mSelectIds.contains(value)) mSelectIds.remove(value);
            else mSelectIds.add(value);
        }
        onItemClick(position, item);
    }

    protected boolean isSelect(T t){
        return mSelectIds.contains(getSelectKeyValue(t));
    }

    protected boolean isNeedSelect(){
        return false;
    }

    protected String getSelectKeyValue(T t){
        return "";
    }

    public int getSelectSize(){
        return mSelectIds.size();
    }

    public String getSelectValues(){
        int size = mSelectIds.size();
        StringBuilder values = new StringBuilder();
        for(int i=0; i<size; i++){
            values.append(mSelectIds.get(i));
            if(i<size -1) values.append(",");
        }
        return values.toString();
    }

    protected void setDividerType1(){
        mListView.setDivider(ContextCompat.getDrawable(getContext(), R.drawable.listview_divider));
        mListView.setDividerHeight(1);
    }

    protected void setDividerType2(){
        mListView.setDivider(new ColorDrawable(0xfff4f4f4));
        mListView.setDividerHeight(1);
    }

    protected void setDividerType3(){
        mListView.setDivider(new ColorDrawable(0xfff4f4f4));
        mListView.setDividerHeight(Tools.dip2px(getContext(), 10));
    }

    protected void setDividerType4(){
        mListView.setDivider(new ColorDrawable(0x00000000));
        mListView.setDividerHeight(Tools.dip2px(getContext(), 0));
    }

    protected abstract View getView(int position, View convertView, T item);
    protected void onItemClick(int position, T item){

    };
}
