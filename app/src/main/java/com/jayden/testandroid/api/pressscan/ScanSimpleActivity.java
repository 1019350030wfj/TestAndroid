package com.jayden.testandroid.api.pressscan;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jayden.testandroid.R;

/**
 * Created by Bolex on 2017/4/7.
 */

public class ScanSimpleActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_simple);
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        webView.loadUrl("http://upload-images.jianshu.io/upload_images/1110736-d2375992ed5499ea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
        webView.loadUrl("https://zybuluo.com/bolex/note/329731");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        initEvent();
    }

    private void initEvent() {
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //todo:调用扫描工具

                ScanTools.scanCode(v, new ScanTools.ScanCall() {
                    @Override
                    public void getCode(String code) {
                        Toast.makeText(ScanSimpleActivity.this,code,Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
    }

}
