package com.jayden.jaydenrichdemo.view.fragment;

import android.content.Context;

import com.jayden.jaydenrich.view.adapter.BaseRecyclerAdapter;
import com.jayden.jaydenrich.view.fragment.AbsRecyclerFragment;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.jaydenrichdemo.presenter.RecyclePresenterImpl;
import com.jayden.jaydenrichdemo.view.adapter.MeiziAdapter;

/**
 * Created by Jayden on 2016/4/19.
 */
public class RecyclerFragmentImpl extends AbsRecyclerFragment<Meizi> {

    @Override
    protected void initPresenter() {
        mPresenter = new RecyclePresenterImpl(getActivity(),this);
    }

    @Override
    protected BaseRecyclerAdapter initAdapter(Context context) {
        return new MeiziAdapter(context);
    }
}
