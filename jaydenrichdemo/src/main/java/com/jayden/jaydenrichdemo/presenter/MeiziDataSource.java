package com.jayden.jaydenrichdemo.presenter;

import android.content.Context;

import com.jayden.jaydenrich.model.dao.HttpHandler;
import com.jayden.jaydenrichdemo.model.bean.BaseBean;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.jaydenrichdemo.model.dao.ApiService;
import com.jayden.pulltorefresh.IAsyncDataSource;
import com.jayden.pulltorefresh.RequestHandle;
import com.jayden.pulltorefresh.ResponseSender;

import java.util.List;

import okhttp3.Request;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class MeiziDataSource implements IAsyncDataSource<List<Meizi>>{

    private int mPage = 1;
    private int maxPage = 5;
    private Context context;

    public MeiziDataSource(Context context) {
        this.context = context;
    }

    @Override
    public RequestHandle refresh(ResponseSender<List<Meizi>> sender) throws Exception {
        return loadMeizi(1,sender);
    }

    private RequestHandle loadMeizi(final int page, final ResponseSender<List<Meizi>> sender) {
        ApiService.getFuli(context, 10, page, new HttpHandler.ResultCallback<BaseBean<List<Meizi>>>() {
            @Override
            public void onError(Request request, Exception e) {
                sender.sendError(e);
            }

            @Override
            public void onResponse(BaseBean<List<Meizi>> response) {
                if (response != null) {
                    mPage = page;
                   sender.sendData(response.results);
                }
            }
        });
        return null;
    }

    @Override
    public RequestHandle loadMore(ResponseSender<List<Meizi>> sender) throws Exception {
        return loadMeizi(mPage + 1,sender);
    }

    @Override
    public boolean hasMore() {
        return mPage < maxPage;
    }
}
