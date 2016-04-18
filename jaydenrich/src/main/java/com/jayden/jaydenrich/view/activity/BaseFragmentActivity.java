package com.jayden.jaydenrich.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.jayden.jaydenrich.view.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jayden on 2016/4/18.
 * Email : 1570713698@qq.com
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    protected FragmentManager mFragmentManager;
    private HashMap<Integer, String> mCurrentFragments;
    private int mResId = -1;
    private boolean isFirst = false;//避免每次进来都重新加载数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mFragmentManager = getSupportFragmentManager();
        if (this.mCurrentFragments == null) {
            this.mCurrentFragments = new HashMap();
        }
        Log.d("jayden","onCreate = mCurrentFragments.length" + mCurrentFragments.size());
        for (Map.Entry<Integer, String> entry : this.mCurrentFragments
                .entrySet()) {
            int resId = ((Integer) entry.getKey()).intValue();
            String tag = (String) entry.getValue();
            Log.d("jayden","onCreate = resId" + resId);
            Log.d("jayden","onCreate = tag" + tag);
            changeFragment(resId, findFragmentByTag(resId, tag), tag);
        }
        initCreate(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus && !isFirst) {
            loadData();
            isFirst = true;
        }
        super.onWindowFocusChanged(hasFocus);
    }

    protected void initCreate(Bundle savedInstanceState) {
        initViews();
        initData(savedInstanceState);
    }

    /**
     * 初始化界面和控件
     */
    public abstract void initViews();

    /**
     * 加载数据
     */
    public abstract void loadData();


    /**
     * 初始化数据
     */
    public abstract void initData(Bundle savedInstanceState);

    public void setFragmentViewId(int mResId) {
        Log.d("jayden","setFragmentViewId mResId = " + mResId);
        this.mResId = mResId;
    }

    public final BaseFragment findFragmentByTag(int resId, String tag) {
        Log.d("jayden","findFragmentByTag  resId " + resId + " tag = " + tag);
        return (BaseFragment) this.mFragmentManager.findFragmentByTag(resId + "@" + tag);
    }

    public final BaseFragment findFragmentByTag(String tag) {
        return findFragmentByTag(this.mResId, tag);
    }

    public final BaseFragment changeFragment(BaseFragment fragment, String tag) {
        return changeFragment(this.mResId, fragment, tag);
    }

    public final BaseFragment changeFragment(int resId, BaseFragment fragment,
                                             String tag) {
        FragmentTransaction ft = this.mFragmentManager.beginTransaction();
        BaseFragment exitFragment = null;
        String exitTag = (String) this.mCurrentFragments.get(Integer
                .valueOf(resId));

        Log.d("jayden","changeFragment resId = " + resId);
        Log.d("jayden","changeFragment exitTag = " + exitTag);

        if (exitTag != null) {
            exitFragment = findFragmentByTag(resId, exitTag);
        }
        onCreateAnimation(resId, fragment, exitFragment, ft, false);
        this.mCurrentFragments.put(Integer.valueOf(resId), tag);
        if (this.mFragmentManager.getFragments() != null) {
            for (Fragment f : this.mFragmentManager.getFragments()) {
                Log.d("jayden","mFragmentManager.getFragments() != null ,length == " + this.mFragmentManager.getFragments().size());
                if ((f != null) && (f.getId() == resId)) {
                    Log.d("jayden","mFragmentManager.getFragments() != null ,f.getId()== " + f.getId());
                    ft.hide(f);
                }
            }
        }
        if (findFragmentByTag(resId, tag) == null) {
            Log.d("jayden","findFragmentByTag(resId, tag) == null");
            ft.add(resId, fragment, resId + "@" + tag);
        } else {
            Log.d("jayden","findFragmentByTag(resId, tag) != null");
            fragment = findFragmentByTag(resId, tag);
        }
        ft.show(fragment);
        ft.commitAllowingStateLoss();

        return fragment;
    }

    public void onCreateAnimation(int resId, BaseFragment fragment, BaseFragment exitFragment, FragmentTransaction ft, boolean b) {

    }
}
