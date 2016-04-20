package com.jayden.jaydenrich.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jayden.jaydenrich.R;
import com.jayden.jaydenrich.presenter.AbsRecyclerPresenter;
import com.jayden.jaydenrich.view.adapter.BaseRecyclerAdapter;
import com.jayden.jaydenrich.view.iview.IRecyclerView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * RecyclerView的抽象Fragment
 *
 * Created by Jayden on 2016/3/29.
 * Email : 1570713698@qq.com
 */
public abstract class AbsRecyclerFragment<D> extends Fragment implements IRecyclerView<D> {

    protected View mContentView;//内容区域
    protected AbsRecyclerPresenter mPresenter;

    private XRecyclerView rvTest;
    private BaseRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recylerview, container, false);
        rvTest = (XRecyclerView) view.findViewById(R.id.recyclerView);
        initPresenter();
        return view;
    }

    protected abstract void initPresenter();

    protected abstract BaseRecyclerAdapter initAdapter(Context context);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mContentView = view;

        initView(view);
        initListener();
        initData(savedInstanceState);
    }

    public void setLoadMoreProgressStyle(int mLoadMoreProgressStyle) {
        rvTest.setLoadingMoreProgressStyle(mLoadMoreProgressStyle == 0 ? ProgressStyle.BallScaleMultiple : mLoadMoreProgressStyle);
    }

    public void setRefreshProgressStyle(int mRefreshProgressStyle) {
        rvTest.setRefreshProgressStyle(mRefreshProgressStyle == 0 ? ProgressStyle.BallScale : mRefreshProgressStyle);

    }

    @Override
    public void onSuccess(List<D> data) {
        rvTest.refreshComplete();
        mAdapter.setData(data);
    }

    @Override
    public void onLoadMoreSuccess(List<D> data) {
        //设置加载更多完成
        rvTest.loadMoreComplete();
        mAdapter.addData(data);
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
        Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadMoreError() {
        rvTest.loadMoreComplete();
        Toast.makeText(getActivity(), "加载更多失败", Toast.LENGTH_LONG).show();
    }

    public void initView(View view) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTest.setLayoutManager(manager);

        setRefreshProgressStyle(0);
        setLoadMoreProgressStyle(0);
    }


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

    protected void initData(Bundle savedInstanceState) {
        mAdapter = initAdapter(getActivity());
        rvTest.setAdapter(mAdapter);
        mPresenter.refresh();
    }
}
