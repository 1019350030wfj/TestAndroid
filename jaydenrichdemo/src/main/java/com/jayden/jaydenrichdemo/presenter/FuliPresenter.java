package com.jayden.jaydenrichdemo.presenter;

import android.content.Context;

import com.jayden.jaydenrich.model.dao.HttpHandler;
import com.jayden.jaydenrich.presenter.BasePresenter;
import com.jayden.jaydenrichdemo.model.bean.BaseBean;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.jaydenrichdemo.model.dao.ApiService;
import com.jayden.jaydenrichdemo.view.iview.IFuliView;

import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class FuliPresenter extends BasePresenter<IFuliView> {

    private int mPage;

    public FuliPresenter(Context context, IFuliView iView) {
        super(context, iView);
        mPage = 1;
    }

    public void refresh() {
        ApiService.getFuli(mContext, 10, 1, new HttpHandler.ResultCallback<BaseBean<List<Meizi>>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseBean<List<Meizi>> response) {
                if (response != null) {
                    iView.onSuccess(response.results);
                }
            }
        });
    }

    public void loadMore() {
        ApiService.getFuli(mContext, 10, mPage + 1, new HttpHandler.ResultCallback<BaseBean<List<Meizi>>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseBean<List<Meizi>> response) {
                if (response !=null) {
                    mPage += 1;
                }
            }
        });
    }
}
