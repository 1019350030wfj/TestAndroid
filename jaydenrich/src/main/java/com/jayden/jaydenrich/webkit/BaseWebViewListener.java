package com.jayden.jaydenrich.webkit;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * Created by Jayden on 2016/03/14.
 * Email : 1570713698@qq.com
 */
public interface BaseWebViewListener {

    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);
}
