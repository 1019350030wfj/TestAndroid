package com.jayden.pulltorefresh;

/**
 * 请求处理
 * 用于取消请求
 *
 * Created by Jayden on 2016/4/11.
 */
public interface RequestHandle {

    void cancle();

    boolean isRunning();
}
