package com.jayden.testandroid.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Jayden on 2016/8/11.
 */

public class MyWebView extends WebView {

    private Context mActivity;
    private WebSettings settings;

    private boolean isHandle = true;

    public void setHandle(boolean handle) {
        isHandle = handle;
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = context;
        initWebView();
    }

    //webView的初始化设置
    private void initWebView() {

        settings = MyWebView.this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSavePassword(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setPluginState(WebSettings.PluginState.ON);

        setWebViewClient(new WebViewClient() {

            //设置在webView中跳转，而不是浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadUrl(url);
                return true;
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            //在页面加载开始时调用
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //在页面加载结束时调用
            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
            }

            //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        setWebChromeClient(new AppCacheWebChromeClient());
        //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度
        setWebChromeClient(new WebChromeClient() {

            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                result.confirm();
                return true;
            }

            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                result.confirm();
                return true;
            }

            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, final JsPromptResult result) {
                result.confirm();
                return true;
            }

            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isHandle) {
            return false;
        }
        return super.onTouchEvent(event);
    }

    /*************************************
     * webView的一些方法
     **********************************/

    //webview初始化设置中WebChromeClient类
    private class AppCacheWebChromeClient extends WebChromeClient {
        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded,
                                             long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
            quotaUpdater.updateQuota(spaceNeeded * 2);
        }
    }

}