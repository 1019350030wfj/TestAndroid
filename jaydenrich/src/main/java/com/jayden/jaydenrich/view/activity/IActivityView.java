package com.jayden.jaydenrich.view.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Jayden on 2015/12/6.
 * Email : 1570713698@qq.com
 */
public interface IActivityView {

    //内容区域
    View initContentView(LayoutInflater inflater);

    //显示toolbar
    void showToolbar();

    //隐藏toolbar
    void hideToolbar();

    //获取内容显示父容器
    FrameLayout getmContainer();

    void initView();

    void initListener();

    void initData();
}
