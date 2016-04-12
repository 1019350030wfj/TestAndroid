package com.jayden.pulltorefresh;

/**
 *  DataSource 可以实现这个接口，进行加载缓存数据
 *
 * Created by Jayden on 2016/4/12.
 */
public interface IDataCacheLoader<T> {

    /**
     * 加载缓存
     * 注意这个方法执行与UI线程，不要做太多耗时懂得操作
     * 每次刷新的时候出发该方法，该方法咋Data Source refresh之前执行
     *
     * @param isEmpty adapter是否有数据，这个值是adapter。isEmpty（）决定
     *
     * @return 加载的数据，返回后会执行adapter.notifyDataChanged（data，true）
     *          相当于refresh执行后adapter.notifyDataChanged(data,true)
     */
    T loadCache(boolean isEmpty);
}
