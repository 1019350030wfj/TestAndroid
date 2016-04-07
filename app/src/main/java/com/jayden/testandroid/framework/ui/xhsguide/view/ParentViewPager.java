package com.jayden.testandroid.framework.ui.xhsguide.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 顶层ViewPager，包含2个Fragment childView
 * 处于第一个fragment，所有事件将被拦截在本层view，手动分发到指定子View
 * 处于第二个fragment，释放拦截
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class ParentViewPager extends ViewPager {

    public static boolean mLoginPagerLock = false;

    public ParentViewPager(Context context) {
        super(context);
    }

    public ParentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
