package com.jayden.jaydenrich.view.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jayden.jaydenrich.view.activity.IActivityView;
import com.jayden.jaydenrich.view.activity.MVPAbsActivity;

/**
 * IActivityView是对应这个view和Presenter之间的通信桥梁
 * JTestPresenter是对应这个view的业务层
 * Created by Jayden on 2016/7/23.
 */
public class JTestActivity extends MVPAbsActivity<IActivityView,JTestPresenter> implements IActivityView {
    @Override
    protected JTestPresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initContentView() {

    }

    @Override
    public View initContentView(LayoutInflater inflater) {
        return null;
    }

    @Override
    public void showToolbar() {

    }

    @Override
    public void hideToolbar() {

    }

    @Override
    public FrameLayout getmContainer() {
        return null;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
