package com.jayden.testandroid.framework.ui.xhsguide.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.outlayer.welcomelayer.LoginAnimImageFirstFragment;
import com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.outlayer.welcomelayer.LoginAnimImageSecondFragment;
import com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.outlayer.welcomelayer.LoginAnimImageThridFragment;

import java.util.ArrayList;

/**
 * 总共上层有3页动画,写死在adapter
 * Created by Jayden on 2016/4/8.
 */
public class ImageFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> mFragments;
    LoginAnimImageFirstFragment mLoginAnimImageFristFragment;
    LoginAnimImageSecondFragment mLoginAnimImageSecondFragment;
    LoginAnimImageThridFragment mLoginAnimImageThridFragment;

    public ImageFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
        if(mFragments == null){
            mFragments = new ArrayList<Fragment>();
            mLoginAnimImageFristFragment = new LoginAnimImageFirstFragment();
            mFragments.add(mLoginAnimImageFristFragment);
            mLoginAnimImageSecondFragment = new LoginAnimImageSecondFragment();
            mFragments.add(mLoginAnimImageSecondFragment);
            mLoginAnimImageThridFragment = new LoginAnimImageThridFragment();
            mFragments.add(mLoginAnimImageThridFragment);
        }
    }

    public Fragment getFragement(int position) {
        switch (position) {
            case 0:
                return mLoginAnimImageFristFragment;
            case 1:
                return mLoginAnimImageSecondFragment;
            case 2:
                return mLoginAnimImageThridFragment;
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mLoginAnimImageFristFragment;
            case 1:
                return mLoginAnimImageSecondFragment;
            case 2:
                return mLoginAnimImageThridFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
