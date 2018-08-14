package com.android.weici.common.base.ui;

import java.util.List;

public interface IBaseLoadMoreView<T> extends IBaseView {
    void loadDataSuccess(List<T> list);
    void loadRefreshSuccess(List<T> list, int code);
    void loadMoreSuccess(List<T> list);
    void hasMore(boolean has);
}
