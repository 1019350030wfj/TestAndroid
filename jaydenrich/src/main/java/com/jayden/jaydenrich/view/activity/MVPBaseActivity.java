package com.jayden.jaydenrich.view.activity;

import android.app.Activity;
import android.os.Bundle;

import com.jayden.jaydenrich.presenter.MVPPresenter;

/**
 * Created by Jayden on 2016/7/19.
 */
public abstract class MVPBaseActivity<V,T extends MVPPresenter<V>> extends Activity{

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
