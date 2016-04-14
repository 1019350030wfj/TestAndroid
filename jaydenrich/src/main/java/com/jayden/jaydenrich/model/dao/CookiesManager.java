package com.jayden.jaydenrich.model.dao;

import android.content.Context;

import com.jayden.jaydenrich.utils.LogUtils;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 自动管理Cookies
 *
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class CookiesManager implements CookieJar {

    private final PersistentCookieStore cookieStore;

    private static CookiesManager sInstance;

    private CookiesManager(Context context) {
        cookieStore = new PersistentCookieStore(context.getApplicationContext());
    }

    public static CookiesManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (CookiesManager.class) {
                if (sInstance == null) {
                    sInstance = new CookiesManager(context);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                LogUtils.d("jayden","saveFromResponse url host = " + url.host());
                LogUtils.d("jayden","saveFromResponse url = " + url + " ||cookie value= " + item.value());
                LogUtils.d("jayden","saveFromResponse url = " + url + " ||cookie domain = " + item.domain());
                LogUtils.d("jayden","saveFromResponse url = " + url + " ||cookie path = " + item.path());
                LogUtils.d("jayden","saveFromResponse url = " + url + " ||cookie toString= " + item.toString());
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        if (cookies != null && cookies.size() > 0) {
            LogUtils.d("jayden","loadForRequest url = " + url + " ||cookies size = " + cookies.size());

            LogUtils.d("jayden","loadForRequest url = " + url + " ||cookies  = " + cookies.get(0).toString());
        }
        return cookies;
    }
}
