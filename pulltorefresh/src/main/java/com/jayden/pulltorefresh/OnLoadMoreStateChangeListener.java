package com.jayden.pulltorefresh;

/**
 * 加载更多的状态监听者
 * Created by Jayden on 2016/4/11.
 */
public interface OnLoadMoreStateChangeListener<T> {
    void onStartLoadMore(IDataAdapter<T> adapter);

    void onEndLoadMore(IDataAdapter<T> adapter,T result);
}
