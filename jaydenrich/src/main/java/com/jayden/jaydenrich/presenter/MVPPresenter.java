package com.jayden.jaydenrich.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Jayden on 2016/7/19.
 */
public class MVPPresenter<T> {

    protected Reference<T> mViewRef; //view 接口的弱引用

    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view); //建立关联
    }

    protected T getView() {
        return mViewRef.get();//获得view
    }

    public boolean isViewAttach() {
        //判断是否与view建立关联
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        //接触关联
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
