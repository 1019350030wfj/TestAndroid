package com.jayden.jaydenrichdemo.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jayden.jaydenrich.view.fragment.BaseFragment;
import com.jayden.jaydenrichdemo.R;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.jaydenrichdemo.presenter.FuliPresenter;
import com.jayden.jaydenrichdemo.view.adapter.XMeiziAdapter;
import com.jayden.jaydenrichdemo.view.iview.IFuliView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Jayden on 2016/3/29.
 * Email : 1570713698@qq.com
 */
public class XRecyclerFragment extends BaseFragment<FuliPresenter> implements IFuliView{

    @Bind(R.id.rv_test)
    XRecyclerView rvTest;

    private XMeiziAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_xrecylerview;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new FuliPresenter(getContext(),this);
    }


    @Override
    public void onSuccess(List<Meizi> meizis) {

    }

    @Override
    public void initView(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTest.setLayoutManager(manager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }
}
