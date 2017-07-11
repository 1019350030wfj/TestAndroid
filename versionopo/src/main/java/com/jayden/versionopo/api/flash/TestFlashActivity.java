package com.jayden.versionopo.api.flash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jayden.versionopo.R;

import java.util.List;

/**
 * Created by Jayden on 2017/6/19.
 */

public class TestFlashActivity extends Activity {

    private WebView mWebView;
    private Handler mHandler = new Handler();
    private String mFlashFilename;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_flash);
        mWebView = (WebView) findViewById(R.id.webview);
        setTitle("flash播放器");
        setTitleColor(Color.RED);
//        mWebView.getSettings().setPluginsEnabled(true);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        Intent intent = getIntent();
        String str = intent.getStringExtra("flashName");
        if (str == null)
            mFlashFilename = new String("file:///android_asset/test.swf");
        else
            mFlashFilename = str;

        try {
            Thread.sleep(500);// 主线程暂停下，否则容易白屏，原因未知
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mProgressDialog = ProgressDialog.show(this, "请稍等...", "加载flash中...", true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.dismiss();
                        }
                    }, 500);
                }
            }
        });
        mWebView.loadUrl(mFlashFilename);
//        if (checkinstallornotadobeflashapk()) {
//            mWebView.loadUrl(mFlashFilename);
//        } else {
//            installadobeapk();
//        }

    }

    //退出时关闭flash播放
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        this.finish();
        System.gc();
    }

    //按下Back按键时关闭flash播放
    @Override
    public void onBackPressed() {
        mWebView.destroy();
        this.finish();
        System.gc();
        super.onBackPressed();
    }

    //后台运行
    @Override
    protected void onUserLeaveHint() {
        mWebView.destroy();
        this.finish();
        System.gc();
        super.onUserLeaveHint();
    }

    //检查机子是否安装的有Adobe Flash相关APK
    private boolean checkinstallornotadobeflashapk() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> infoList = pm
                .getInstalledPackages(PackageManager.GET_SERVICES);
        for (PackageInfo info : infoList) {
            if ("com.adobe.flashplayer".equals(info.packageName)) {
                return true;
            }
        }
        return false;
    }

    //安装Adobe Flash APK
    private void installadobeapk() {
//        mWebView.addJavascriptInterface(new AndroidBridge(), "android");
//        mWebView.loadUrl("file:///android_asset/go_market.html");
        Intent installIntent = new Intent(
                "android.intent.action.VIEW");
        installIntent.setData(Uri.parse("market://details?id=com.adobe.flashplayer"));
        startActivity(installIntent);
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void goMarket() {
            mHandler.post(new Runnable() {
                public void run() {
                    Intent installIntent = new Intent(
                            "android.intent.action.VIEW");
                    installIntent.setData(Uri.parse("market://details?id=com.adobe.flashplayer"));
                    startActivity(installIntent);
                }
            });
        }
    }
}
