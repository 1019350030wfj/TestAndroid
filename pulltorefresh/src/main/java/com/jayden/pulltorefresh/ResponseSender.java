package com.jayden.pulltorefresh;

/**
 * 获取数据通知
 * 获取数据成功
 * 获取数据失败
 * Created by Jayden on 2016/4/11.
 */
public interface ResponseSender<T> {

    void sendData(T data);

    void sendError(Exception exception);
}
