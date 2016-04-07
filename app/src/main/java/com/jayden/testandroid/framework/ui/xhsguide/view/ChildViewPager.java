package com.jayden.testandroid.framework.ui.xhsguide.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 底层ViewPager所有事件由父层ViewPage仍然手动分发
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class ChildViewPager extends ViewPager {

    public boolean mIsLockScroll = false;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
