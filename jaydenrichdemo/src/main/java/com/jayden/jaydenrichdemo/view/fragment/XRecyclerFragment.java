package com.jayden.jaydenrichdemo.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.jayden.jaydenrich.view.fragment.BaseFragment;
import com.jayden.jaydenrichdemo.R;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.jaydenrichdemo.presenter.FuliPresenter;
import com.jayden.jaydenrichdemo.view.adapter.XMeiziAdapter;
import com.jayden.jaydenrichdemo.view.iview.IFuliView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
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
        rvTest.refreshComplete();
        mAdapter.setData(meizis);
    }

    @Override
    public void onLoadMoreSuccess(List<Meizi> meizis) {
        //设置加载更多完成
        rvTest.loadMoreComplete();
        mAdapter.addData(meizis);
    }

    @Override
    public void onRefreshEnable(boolean isEnable) {
        //设置是否可以下拉刷新
        rvTest.setPullRefreshEnabled(isEnable);
    }

    @Override
    public void onLoadMoreEnable(boolean isEnable) {
        //设置是否可以加载更多
        rvTest.setLoadingMoreEnabled(isEnable);
    }

    @Override
    public void refreshError() {
        rvTest.refreshComplete();
        Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadMoreError() {
        rvTest.loadMoreComplete();
        Toast.makeText(getActivity(),"加载更多失败",Toast.LENGTH_LONG).show();
    }

    @Override
    public void initView(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTest.setLayoutManager(manager);

        rvTest.setRefreshProgressStyle(ProgressStyle.BallZigZag);
        rvTest.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
    }

    @Override
    public void initListener() {
        rvTest.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }

            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mAdapter = new XMeiziAdapter(getActivity());
        rvTest.setAdapter(mAdapter);
        mPresenter.refresh();
    }
}
