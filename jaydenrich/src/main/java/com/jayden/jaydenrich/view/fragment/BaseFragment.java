package com.jayden.jaydenrich.view.fragment;

/**
 * Created by Jayden on 2016/4/18.
 * Email : 1570713698@qq.com
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayden.jaydenrich.presenter.BasePresenter;

import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected View mContentView;//内容区域
    protected T mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mContentView = view;

        initView(view);
        initListener();
        initData(savedInstanceState);
    }

    public void initView(View view) {

    }

    public void initListener() {

    }

    protected void initData(Bundle savedInstanceState) {

    }

    //获取内容布局的资源id
    protected abstract int getLayoutResId();

    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}