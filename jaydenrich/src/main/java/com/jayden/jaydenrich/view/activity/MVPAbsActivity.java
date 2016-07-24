package com.jayden.jaydenrich.view.activity;

import android.os.Bundle;

import com.jayden.jaydenrich.presenter.MVPPresenter;

/**
 * 需要处理业务逻辑的Activity
 * Created by Jayden on 2016/7/19.
 */
public abstract class MVPAbsActivity<V,T extends MVPPresenter<V>> extends JAbsActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();//创建Presenter
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
