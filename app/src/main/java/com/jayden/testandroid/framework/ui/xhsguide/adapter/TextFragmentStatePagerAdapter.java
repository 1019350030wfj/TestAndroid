package com.jayden.testandroid.framework.ui.xhsguide.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.base.LoginAnimTextBaseFragment;
import com.jayden.testandroid.framework.ui.xhsguide.bean.TextBean;

import java.util.ArrayList;

/**
 * 下层文字动画有4页
 * Created by Jayden on 2016/4/8.
 */
public class TextFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<TextBean> mTextBeans;

    public TextFragmentStatePagerAdapter(FragmentManager fm, ArrayList<TextBean> tbs) {
        super(fm);
        if(tbs != null){
            mTextBeans = tbs;
        }
        else{
            mTextBeans = new ArrayList<TextBean>();
        }
    }

    @Override
    public Fragment getItem(int position) {
        return new LoginAnimTextBaseFragment(mTextBeans.get(position));
    }

    @Override
    public int getCount() {
        return mTextBeans.size();
    }
}
