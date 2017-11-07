package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by Administrator on 2016/7/20.
 */

public class MyWebView extends WebView {
    private MainActivity mActivity;
    private WebSettings settings;
    private int majorType = 0;

    private boolean hasdongle = true;
    private String macAddr = null;


    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (MainActivity) context;
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
        String appCachePath = mActivity
                .getDir("netCache", Context.MODE_PRIVATE).getAbsolutePath();
        settings.setAppCacheEnabled(false);
        settings.setAppCachePath(appCachePath);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 15);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setPluginState(WebSettings.PluginState.ON);

        // 安卓pad ： Onesoft_AndroidPadBrowser 安卓手机 ： Onesoft_AndroidPhoneBrowser
        String ua = settings.getUserAgentString();
        settings.setUserAgentString("; Onesoft_AndroidPadBrowser 1.1.8 ;; Onesoft_interfaceVersion 1.0.1 ; " + ua);

        setBackgroundColor(0);

        setWebViewClient(new WebViewClient() {
            @Override  //设置在webView中跳转，而不是浏览器
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                Log.e("jayden", "shouldOverrideKeyEvent = "+event.getKeyCode() );
                return true;
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                Log.e("jayden", "onUnhandledKeyEvent = "+event.getKeyCode() );
                super.onUnhandledKeyEvent(view, event);
            }
        });

        //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度
        setWebChromeClient(new AppCacheWebChromeClient());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("jayden", "dispatchKeyEvent web= ");
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("jayden", "onKeyDown web= " + keyCode);
        return super.onKeyDown(keyCode, event);
    }

    public void setMajorType(int majorType) {
        this.majorType = majorType;
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

        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            AlertDialog.Builder b = new AlertDialog.Builder(mActivity);
            b.setTitle("Alert");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setCancelable(false);
            b.create().show();
            return true;
        }

        public boolean onJsConfirm(WebView view, String url,
                                   String message, final JsResult result) {
            AlertDialog.Builder b = new AlertDialog.Builder(mActivity);
            b.setTitle("Alert");
            b.setMessage(message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            });
            b.setCancelable(false);
            b.create().show();
            return true;
        }

        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            return true;
        }

        public void onReceivedTitle(WebView view, String title) {
            mActivity.setTitle(title);
            super.onReceivedTitle(view, title);
        }
    }


    public void LoadUrl(final String url) {//加载url
        mActivity.runOnUiThread(new Runnable() {
            public void run() {
                loadUrl(url);
            }
        });
    }

    public void releaseWebviewCache() { //释放webview缓存方法
        stopLoading();
        clearAnimation();
        clearCache(true);//包括清空磁盘缓存
        clearHistory();
        if (canGoBack()) {
            goBack();
        }
    }

}