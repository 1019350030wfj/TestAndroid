package com.jayden.pulltorefresh.view.vary;

import android.content.Context;
import android.view.View;

/**
 * Created by Jayden on 2016/4/9.
 */
public interface IVaryViewHelper {

    View getCurrentLayout();

    void restoreView();//恢复原来的内容布局

    void showLayout(View view);//显示加载中等布局

    void showLayout(int layoutId);

    View inflate(int layoutId);

    Context getContext();

    View getView();
}
