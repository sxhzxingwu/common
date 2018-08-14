package com.android.weici.common.base.ui.activity;


import com.android.weici.common.R;
import com.android.weici.common.base.ui.BaseLoadMorePresenter;
import com.android.weici.common.base.ui.IBaseLoadMoreView;
import com.android.weici.common.widget.WcSwipeRefreshLayout;
import com.android.weici.common.widget.xlistview.XListView;

import java.util.List;

/**
 *
 * Created by weici on 2017/12/1.
 */

public abstract class BaseXListViewActivity<T> extends BaseListViewActivity<T> implements IBaseLoadMoreView<T>
        ,XListView.IXListViewListener, WcSwipeRefreshLayout.OnRefreshListener {

    private WcSwipeRefreshLayout swipe;
    protected boolean mPullLoadEnable = true;
    @Override
    protected void initView() {
        initSwipeLayout();
        ((BaseLoadMorePresenter<T>) getPresenter()).loadData();
    }

    protected void initSwipeLayout(){
        mPresenter = getPresenter();
        getXListView().setPullLoadEnable(false);
        getXListView().setPullRefreshEnable(false);
        getXListView().setXListViewListener(this);
        swipe = findViewById(R.id.swipe_refresh);
        swipe.setOnRefreshListener(this);

        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
    }

    public void setPullRefreshEnable(boolean enable){
        swipe.setEnabled(enable);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_xlistview;
    }
    protected XListView getXListView(){
        return ((XListView)mListView);
    }

    @Override
    public void loadDataSuccess(List<T> list) {
        setData(list);
    }

    @Override
    public void loadRefreshSuccess(List<T> list, int code) {
        setData(list);
        //getXListView().stopRefresh(code);
        getXListView().setFootText(code);
        swipe.setRefreshing(false);
    }

    @Override
    public void loadMoreSuccess(List<T> list) {
        addData(-1, list);
        getXListView().stopLoadMore();
    }

    @Override
    public void hasMore(boolean has) {
        boolean showText = mAdapter == null || mAdapter.getCount() <((BaseLoadMorePresenter<T>) mPresenter).mPageSize;
        if(mPullLoadEnable)
        getXListView().setPullLoadEnable(has, !showText);
    }

    @Override
    public void onRefresh() {
        ((BaseLoadMorePresenter<T>) mPresenter).loadRefresh();
    }

    @Override
    public void onLoadMore() {
        ((BaseLoadMorePresenter<T>) mPresenter).loadMore();
    }
}
