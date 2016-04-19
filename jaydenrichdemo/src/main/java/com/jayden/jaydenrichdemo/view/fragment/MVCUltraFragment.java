package com.jayden.jaydenrichdemo.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jayden.jaydenrich.view.fragment.BaseTitleFragment;
import com.jayden.jaydenrichdemo.R;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.jaydenrichdemo.presenter.MeiziDataSource;
import com.jayden.jaydenrichdemo.view.adapter.MeiziAdapter;
import com.jayden.pulltorefresh.MVCHelper;
import com.jayden.pulltorefresh.MVCUltraHelper;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Jayden on 2016/3/29.
 * Email : 1570713698@qq.com
 */
public class MVCUltraFragment extends BaseTitleFragment {

    private MVCHelper<List<Meizi>> listViewHelper;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_mvchelper_ultra;
    }

    @Override
    public void initView(View view) {
        PtrClassicFrameLayout mPtrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listViewHelper = new MVCUltraHelper<List<Meizi>>(mPtrFrameLayout);
    }

    @Override
    public void initTitleBar(TitleBar titleBar) {
        super.initTitleBar(titleBar);
    }

    @Override
    public void initData() {
        // 设置数据源
        listViewHelper.setAsyncDataSource(new MeiziDataSource(getActivity()));
        // 设置适配器
        listViewHelper.setAdapter(new MeiziAdapter(getActivity()));

        // 加载数据
        listViewHelper.refresh();
    }

    @Override
    public void initListener() {
        //自动加载
        listViewHelper.setAutoLoadMore(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放资源
        listViewHelper.destroy();
    }
}
