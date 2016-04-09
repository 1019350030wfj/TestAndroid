package com.jayden.pulltorefresh;

/**
 * 数据适配
 * Created by Jayden on 2016/4/9.
 */
public interface IDataAdapter<T> {

    void notifyDataChanged(T data, boolean isRefresh);

    T getData();

    boolean isEmpty();
}
