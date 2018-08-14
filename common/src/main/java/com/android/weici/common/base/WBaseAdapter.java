package com.android.weici.common.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class WBaseAdapter<T> extends BaseAdapter {


    protected List<T> mList = new ArrayList<>();
    protected Context mContext;

    public WBaseAdapter(){}
    public WBaseAdapter(List<T> list) {
        mList = list;
    }

    public WBaseAdapter(List<T> list, Context context) {
        if(list != null && list.size()>0)
        mList.addAll(list);
        this.mContext = context;
    }

    public void remove(T t){
        mList.remove(t);
        notifyDataSetChanged();
    }

    public void notifyData(List<T> list){
        if(list == null) list = new ArrayList<>();
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(int index, List<T> list){
        if(list == null) list = new ArrayList<>();
        if(index == -1)
        mList.addAll(list);
        else mList.addAll(index, list);
        notifyDataSetChanged();
    }

    public List<T> getList(){
        List<T> list = new ArrayList<>();
        if(mList != null && mList.size()>0) list.addAll(mList);

        return list;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        if(position>= getCount()) return null;
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract View getView(int position, View convertView, ViewGroup parent, T data);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent, getItem(position));
    }
}
