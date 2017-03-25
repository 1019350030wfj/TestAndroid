package com.jayden.testandroid.customview.dragviewhelper;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jayden.testandroid.R;
import com.jayden.testandroid.customview.MyWebView;

/**
 * Created by Jayden on 2016/8/10.
 */

public class TestJaydenDragActivity extends AppCompatActivity {

    private JaydenDragLayout mDragLayout;

    private TextView mTextView;
    private int time = 0;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mTextView.setText((time++) + "S");
            mTextView.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jayden_drag);
        mDragLayout = (JaydenDragLayout) findViewById(R.id.drawerlayout);
        final MyWebView webView = (MyWebView) findViewById(R.id.webview);
        mDragLayout.setWebView(webView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("http://www.100vr.com");
            }
        });

        mTextView = (TextView) findViewById(R.id.time);
        mRunnable.run();
//        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_group);
//        ll.addView(webView);

    }

    //webView的初始化设置
    private void initWebView(final WebView webView) {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setSavePassword(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setAppCacheEnabled(false);
        settings.setAppCacheMaxSize(1024 * 1024 * 15);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setPluginState(WebSettings.PluginState.ON);

        // 安卓pad ： Onesoft_PadBrowser
        // 安卓手机 ： Onesoft_AndroidPhoneBrowser
        String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua + "; Onesoft_AndroidPadBrowser 1.1.8 ");


        webView.setWebViewClient(new WebViewClient() {

            //设置在webView中跳转，而不是浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
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
        webView.setWebChromeClient(new AppCacheWebChromeClient());
        //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度
        webView.setWebChromeClient(new WebChromeClient() {

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

    //webview初始化设置中WebChromeClient类
    private class AppCacheWebChromeClient extends WebChromeClient {
        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded,
                                             long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {
            quotaUpdater.updateQuota(spaceNeeded * 2);
        }
    }
}
