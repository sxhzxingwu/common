package com.android.weici.common.base.ui;

import com.android.weici.common.base.api.ApiBody;
import com.android.weici.common.base.api.ApiRequest;
import com.android.weici.common.base.api.ApiResult;

import java.util.List;

/**
 * 上拉加载，下拉刷新可用
 * Created by weici on 2017/12/19.
 */

public abstract  class BaseLoadMorePresenter<T> extends BasePresenter {
    protected IBaseLoadMoreView<T> mView;
    protected int mPageIndex = 1;
    public int mPageSize = 10;
    protected boolean mLoadMoreNeedPageSize = true;
    public BaseLoadMorePresenter(IBaseLoadMoreView<T> view) {
        super(null);
        mView = view;
    }

    public void loadData(){
        mView.showLoadingState();
        ApiRequest.execute(getApiBody(), new ApiRequest.OnCallBackListener() {
            @Override
            public void callBack(ApiResult result) {
                executeOnUiThread(0, result);
            }
        });
    }

    public void loadRefresh(){
        mPageIndex = 1;
        ApiRequest.execute(getApiBody(), new ApiRequest.OnCallBackListener() {
            @Override
            public void callBack(ApiResult result) {
                executeOnUiThread(1, result);
            }
        });
    }

    public void loadMore(){
        mPageIndex ++ ;
        ApiRequest.execute(getApiBody(), new ApiRequest.OnCallBackListener() {
            @Override
            public void callBack(ApiResult result) {
                executeOnUiThread(2, result);
            }
        });
    }

    protected abstract ApiBody getApiBody();
    @Override
    public void onMainThreadResult(int code, String msg, ApiResult result, Object object) {
        if(code == 15){
            hasMore((List<T>) result.object, true);
            return;
        }
        if(code == 0){
            loadDataResult(result);
        }else if(code == 1){
            loadRefreshResult(result);
        }else if(code == 2){
            loadMoreResult(result);
        }

        hasLoadMore();
    }

    protected void loadDataResult(ApiResult result){
        if(result.code == 200){
            List<T> list = (List<T>) result.object;
            hasMore(list, false);
            mView.showSuccessState();
            mView.loadDataSuccess(list);
        }else if(result.code == 201){
            mView.showFailureState(getFailureMessage(result), result.code);
        }else{
            mView.showFailureState(result.getErrorMsg(), result.code);
        }
    }

    //预留自定义信息
    protected String getFailureMessage(ApiResult result){
        return result.getErrorMsg();
    }

    private void loadRefreshResult(ApiResult result){
        List<T> list = null;
        if(result.code == 200 || result.code == 201){
            list = (List<T>) result.object;
            hasMore(list, false);
        }
        mView.loadRefreshSuccess(list, result.code);
    }

    private void loadMoreResult(ApiResult result){
        List<T> list = null;
        if(result.code == 200 || result.code == 201){
            list = (List<T>) result.object;
            hasMore(list, false);
        }
        mView.loadMoreSuccess(list);
    }

    private void hasMore(List<T> list, boolean isEnable){
        if(!isEnable && !mLoadMoreNeedPageSize) return;
        boolean need = !(list == null || list.size()<mPageSize);
        if(!mLoadMoreNeedPageSize) need = !(list == null || list.size() == 0);
        mView.hasMore(need);
    }
    @Override
    public void prepareData() {
        loadData();
    }


    protected ApiBody getNoPageSizeApiBody(){
        return null;
    }

    private void hasLoadMore(){
        ApiBody apiBody = getNoPageSizeApiBody();
        if(apiBody == null) return;
        ApiRequest.execute(apiBody, new ApiRequest.OnCallBackListener() {
            @Override
            public void callBack(ApiResult result) {
                executeOnUiThread(15, result);
            }
        });
    }
}
