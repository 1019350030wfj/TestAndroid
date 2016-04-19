package com.jayden.jaydenrichdemo.presenter;

import android.content.Context;

import com.jayden.jaydenrich.model.dao.HttpHandler;
import com.jayden.jaydenrich.presenter.AbsRecyclerPresenter;
import com.jayden.jaydenrich.view.iview.IRecyclerView;
import com.jayden.jaydenrichdemo.model.bean.BaseBean;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.jaydenrichdemo.model.dao.ApiService;

import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/4/19.
 */
public class RecyclePresenterImpl extends AbsRecyclerPresenter {

    private int mPage;
    public RecyclePresenterImpl(Context context, IRecyclerView iView) {
        super(context, iView);
        mPage = 1;
    }

    @Override
    public void refresh() {
        ApiService.getFuli(mContext, 10, 1, new HttpHandler.ResultCallback<BaseBean<List<Meizi>>>() {
            @Override
            public void onError(Request request, Exception e) {
                iView.refreshError();
            }

            @Override
            public void onResponse(BaseBean<List<Meizi>> response) {
                if (response != null) {
                    iView.onSuccess(response.results);
                } else {
                    iView.refreshError();
                }
            }
        });
    }

    @Override
    public void loadMore() {
        ApiService.getFuli(mContext, 10, mPage + 1, new HttpHandler.ResultCallback<BaseBean<List<Meizi>>>() {
            @Override
            public void onError(Request request, Exception e) {
                iView.loadMoreError();
            }

            @Override
            public void onResponse(BaseBean<List<Meizi>> response) {
                if (response !=null) {
                    mPage += 1;
                    iView.onLoadMoreSuccess(response.results);
                    if (mPage == 3) {
                        iView.onLoadMoreEnable(false);
                    }
                } else {
                    iView.loadMoreError();
                }
            }
        });
    }
}
