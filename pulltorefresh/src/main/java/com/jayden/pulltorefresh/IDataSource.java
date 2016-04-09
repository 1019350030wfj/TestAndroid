package com.jayden.pulltorefresh;

/**
 * 数据源
 * Created by Jayden on 2016/4/9.
 */
public interface IDataSource<T> {

    /**
     * 获取刷新数据
     * @return
     * @throws Exception
     */
    T refresh() throws Exception;

    /**
     * 获取加载更多的数据
     * @return
     * @throws Exception
     */
    T loadMore() throws Exception;

    /**
     * 是否还可以加载更多
     * @return
     */
    boolean hasMore();
}
