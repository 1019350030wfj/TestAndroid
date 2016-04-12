package com.jayden.pulltorefresh;

import android.view.View;

/**
 * 下拉刷新接口
 * Created by Jayden on 2016/4/11.
 */
public interface IRefreshView {

    /**
     * 获取内容布局
     * @return
     */
    View getContentView();

    /**
     * 通过替换这个View实现切换失败，成功，无数据布局
     *
     * @return
     */
    View getSwitchView();

    /**
     * 设置刷新事件
     * @param listener
     */
    void setOnRefreshListener(OnRefreshListener listener);

    /**
     * 显示刷新完成
     */
    void showRefreshComplete();

    /**
     * 显示正在刷新
     */
    void showRefreshing();

    public static interface OnRefreshListener{
        void onRefresh();
    }
}
