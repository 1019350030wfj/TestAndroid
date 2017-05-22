package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Jayden on 2017/4/25.
 */

public class JaydenWebview extends WebView {

    public JaydenWebview(Context context) {
        this(context, null);
    }

    public JaydenWebview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JaydenWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setSavePassword(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setAppCacheEnabled(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 15);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString("; Onesoft_AndroidPadBrowser 1.1.8 ;; Onesoft_interfaceVersion 1.0.1 ; " + ua);

        setBackgroundColor(0);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("jayden", "onKey = " + keyCode);
                return false;
            }
        });
        requestFocus();
        setFocusableInTouchMode(true);

        setWebViewClient(new WebViewClient() {
            @Override  //设置在webView中跳转，而不是浏览器
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                Log.e("jayden", "shouldOverrideKeyEvent = " + event.getKeyCode());
                return true;
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                Log.e("jayden", "onUnhandledKeyEvent = " + event.getKeyCode());
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
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
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
            AlertDialog.Builder b = new AlertDialog.Builder(getContext());
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
            super.onReceivedTitle(view, title);
        }
    }

}
