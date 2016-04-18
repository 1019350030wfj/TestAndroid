package com.jayden.testandroid.framework.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jayden on 2016/3/29.
 * Email : 1570713698@qq.com
 */
public abstract class BaseFragment extends Fragment {

    protected View mContentView;//内容区域

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mContentView = view;

        initView(view);
        initData(savedInstanceState);
    }

    //获取内容布局的资源id
    protected abstract int getLayoutId();

    public void initView(View view) {

    }

    protected void initData(Bundle savedInstanceState) {

    }
}
