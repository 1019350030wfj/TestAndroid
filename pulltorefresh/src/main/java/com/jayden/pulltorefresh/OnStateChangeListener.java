package com.jayden.pulltorefresh;

/**
 * 加载的状态监听者
 *
 * Created by Jayden on 2016/4/11.
 */
public interface OnStateChangeListener<T> extends OnRefreshStateChangeListener<T>,
        OnLoadMoreStateChangeListener<T> {
}
