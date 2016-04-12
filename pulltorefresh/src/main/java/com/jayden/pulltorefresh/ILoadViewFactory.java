package com.jayden.pulltorefresh;

import android.view.View;

/**
 * 正在加载中、加载失败、空数据、加载更多的布局生产工厂
 * Created by Jayden on 2016/4/9.
 * Email : 1570713698@qq.com
 */
public interface ILoadViewFactory {

    ILoadMoreView madeLoadMoreView();

    ILoadingView madeLoadingView();

    /**
     * 正在加载中、加载失败、空数据
     */
    public static interface ILoadingView{

        /**
         * 初始化
         * @param switchView  哪个view上面显示，切换布局，
         * @param onClickListener  失败点击重新加载
         */
        void init(View switchView, View.OnClickListener onClickListener);

        /**
         * 显示加载数据为空，布局
         */
        void showEmpty();

        /**
         * 显示加载数据中，布局
         */
        void showLoading();

        /**
         * 显示加载数据失败，布局
         */
        void showFail(Exception e);

        /**
         * 已经有数据了，然后在加载数据失败，显示toast提示
         */
        void tipFail(Exception e);

        /**
         *显示原先的布局
         */
        void restore();
    }

    /**
     * 列表控件，上啦加载更多的布局
     */
    public static interface ILoadMoreView {

        /**
         * 初始化
         * @param footViewAdder 加载更多布局
         * @param onClickListener  点击加载更多事件
         */
        void init(FootViewAdder footViewAdder, View.OnClickListener onClickListener);

        /**
         * 显示普通布局
         */
        void showNormal();

        /**
         * 加载完成，显示没有更多数据的布局
         */
        void showNoMore();

        /**
         * 显示加载中的布局
         */
        void showLoading();

        /**
         * 显示加载失败的布局
         */
        void showFail(Exception e);
    }

    public static interface FootViewAdder {

        View addFootView(View view);

        View addFootView(int layoutId);
    }
}
