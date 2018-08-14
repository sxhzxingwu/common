package com.android.weici.common.base.ui.fragment;

import android.graphics.drawable.ColorDrawable;

import com.android.weici.common.R;
import com.android.weici.common.base.ui.BaseLoadMorePresenter;
import com.android.weici.common.base.ui.IBaseLoadMoreView;
import com.android.weici.common.widget.WcSwipeRefreshLayout;
import com.android.weici.common.widget.xlistview.XListView;

import java.util.List;

/**
 *
 * Created by weici on 2017/12/20.
 */

public abstract class BaseXListViewFragment<T> extends BaseListFragment<T> implements IBaseLoadMoreView<T>,XListView.IXListViewListener, WcSwipeRefreshLayout.OnRefreshListener {

    private WcSwipeRefreshLayout swipe;
    @Override
    protected void initView() {
        super.initView();
        mListView.setDivider(new ColorDrawable(0x00000000));
        mListView.setDividerHeight(0);

        getXListView().setPullLoadEnable(false);
        getXListView().setPullRefreshEnable(false);
        getXListView().setXListViewListener(this);
        if(mPresenter == null)
            mPresenter = getPresenter();
        if(mPresenter != null)
        ((BaseLoadMorePresenter<T>)mPresenter).loadData();
        swipe = mRoot.findViewById(R.id.swipe_refresh);
        swipe.setOnRefreshListener(this);

        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
    }

    @Override
    protected int onCreateContent() {
        return R.layout.fragment_xlistview;
    }

    protected abstract BaseLoadMorePresenter<T> getPresenter();
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
        swipe.setRefreshing(false);
        getXListView().setFootText(code);
    }

    @Override
    public void loadMoreSuccess(List<T> list) {
        addData(-1, list);
        getXListView().stopLoadMore();
    }

    @Override
    public void hasMore(boolean has) {
        getXListView().setPullLoadEnable(has);
    }

    @Override
    public void onRefresh() {
        ((BaseLoadMorePresenter<T>)mPresenter).loadRefresh();
    }

    @Override
    public void onLoadMore() {
        ((BaseLoadMorePresenter<T>)mPresenter).loadMore();
    }
}
