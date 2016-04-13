package com.jayden.jaydenrich.webkit;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Jayden on 2016/4/13.
 * Email : 1570713698@qq.com
 */
public abstract class AbsWebManager {

    private static final String JS_ANDROID = "bridge";

    private SimpleWebChromeClient chromeClient;
    private BaseWebViewListener listener;
    private WebView view;

    public void setup(final WebView view, BaseWebViewListener listener) {
        this.listener = listener;
        this.view = view;
        setupWebView();
    }

    @SuppressLint("JavascriptInterface")
    @SuppressWarnings("deprecation")
    private void setupWebView() {
        WebSettings settings = view.getSettings();

        //支持获取手势焦点，输入用户名、密码或其他
        view.requestFocusFromTouch();

        settings.setJavaScriptEnabled(true);//支持js

        //支持通过JS打开新窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小

        settings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。

        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        settings.supportMultipleWindows();  //多窗口

        //WebSettings.LOAD_CACHE_ELSE_NETWORK关闭webview中缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        settings.setAllowFileAccess(true);  //设置可以访问文件
        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        settings.setPluginState(WebSettings.PluginState.ON);

        chromeClient = new SimpleWebChromeClient();
        view.setWebViewClient(new SimpleWebViewClient());
        view.setWebChromeClient(chromeClient);
        view.addJavascriptInterface(getBridge(listener), JS_ANDROID);
    }

    public abstract BaseJSBridge getBridge(BaseWebViewListener listener);

    /**
     * WebChromeClient是辅助WebView处理Javascript的对话框，
     * 网站图标，网站title，加载进度等
     */
    private class SimpleWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    private class SimpleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //打开网页时不调用系统浏览器， 而是在本WebView中显示：
            view.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            //重写此方法才能够处理在浏览器中的按键事件。
            return super.shouldOverrideKeyEvent(view, event);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            if (listener != null) {
                listener.onPageFinished(view, url);
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (listener != null) {
                listener.onPageStarted(view, url, favicon);
            }
        }
    }
}
