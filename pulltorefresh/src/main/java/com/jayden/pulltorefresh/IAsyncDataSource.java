package com.jayden.pulltorefresh;

/**
 * Created by Jayden on 2016/4/11.
 */
public interface IAsyncDataSource<T> {

    RequestHandle refresh(ResponseSender<T> sender) throws Exception;

    RequestHandle loadMore(ResponseSender<T> sender) throws Exception;

    boolean hasMore();
}
