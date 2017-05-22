package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.jayden.versionopo.R;

/**
 * Created by Jayden on 2017/4/26.
 */

public class BluetoothWebview extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_webview);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http://192.168.3.157:8000/100vr/");
//        mWebView.loadUrl("http://www.baidu.com");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("jayden", "dispatchKeyEvent = " );
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("jayden", "onKeyDown = " + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP: {//上
                Log.e("jayden", "Up = " + event.getAction());
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_DOWN: {//下
                Log.e("jayden", "down = " + event.getAction());
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_LEFT: {//左
                Log.e("jayden", "left = " + event.getAction());
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT: {//右
                Log.e("jayden", "right = " + event.getAction());
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
