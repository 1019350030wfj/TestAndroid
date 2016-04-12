package com.jayden.pulltorefresh;

/**
 * 下拉刷新加载状态监听者
 * Created by Jayden on 2016/4/11.
 */
public interface OnRefreshStateChangeListener<T> {

    void onStartRefresh(IDataAdapter<T> adapter);

    void onEndRefresh(IDataAdapter<T> adapter,T result);
}
