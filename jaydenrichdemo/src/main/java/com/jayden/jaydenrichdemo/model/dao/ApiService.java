package com.jayden.jaydenrichdemo.model.dao;

import android.content.Context;

import com.jayden.jaydenrich.model.dao.HttpHandler;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class ApiService {

    /**
     * 获取福利
     * @param context
     * @param size
     * @param page
     * @param callback
     */
    public static void getFuli(Context context, int size,int page, HttpHandler.ResultCallback callback) {
        HttpHandler.getInstance(context)
                .getAsync(context,
                            API.buildFuliUrl(size,page),
                            callback);
    }
}
