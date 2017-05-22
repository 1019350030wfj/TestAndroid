package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.jayden.versionopo.R;

import java.util.ArrayList;
import java.util.Set;

/**
 * 获取以配对的蓝牙设备，然后判断特定蓝牙设备是否配对且连接
 * 没有的话，提示去配对和连接
 * Created by Jayden on 2017/4/20.
 */
public class MainActivity2 extends Activity {

    private static final String HID_DEVICE_NAME = "00:00:00:33:7B:E4";//Mojing4-A
    private HidConncetUtil mHidConncetUtil;
    private BluetoothAdapter mBluetoothAdapter;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_receive_event);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http://www.baidu.com");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙功能", Toast.LENGTH_LONG).show();
            //不支持蓝牙
            return;
        }
        //如果没有打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        //初始化已连接hid列表
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙功能", Toast.LENGTH_LONG).show();
            //不支持蓝牙
            return;
        }
        mHidConncetUtil = new HidConncetUtil(this);
        //得到已经配对的列表
        Set<BluetoothDevice> mSet = mBluetoothAdapter.getBondedDevices();
        //判断以配对列表，是否有特定设备
        final boolean[] hasConnect = {false};
        boolean isWait = false;
        for (final BluetoothDevice device : mSet) {
            if (HID_DEVICE_NAME.equals(device.getAddress())) {
                if (BluetoothDevice.BOND_BONDED == device.getBondState()) {//配对且连接
                    isWait = true;
                    mHidConncetUtil.getHidConncetList(new HidConncetUtil.GetHidConncetListListener() {
                        @Override
                        public void getSuccess(ArrayList<BluetoothDevice> list) {
                            //判断连接列表中是否有该设备
                            for (BluetoothDevice bluetoothDevice1 : list) {
                                if (device.getAddress().equals(bluetoothDevice1.getAddress())) {
                                    hasConnect[0] = true;//设备已经连接
                                }
                            }
                            throw new RuntimeException();
                        }
                    });
                    break;
                }
            }
        }
        try {
            if (isWait){
                Looper.loop();//等待判断
            }
        } catch (RuntimeException e) {

        }

        if (!hasConnect[0]) {//提示去配对或者连接
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("魔镜手柄蓝牙设备未连接\n请到手机设置-蓝牙界面进行配对连接")
                    .setTitle("提示")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }

    /**
     * 判断设备是否连接
     *
     * @param device
     * @return
     */
    private boolean isConnect(final BluetoothDevice device) {
        final boolean[] isConnect = {false};
        if (mHidConncetUtil != null) {
            mHidConncetUtil.getHidConncetList(new HidConncetUtil.GetHidConncetListListener() {
                @Override
                public void getSuccess(ArrayList<BluetoothDevice> list) {
                    //判断连接列表中是否有该设备
                    for (BluetoothDevice bluetoothDevice1 : list) {
                        if (device.getAddress().equals(bluetoothDevice1.getAddress())) {
                            isConnect[0] = true;//设备已经连接
                            break;
                        }
                    }
                }
            });
        }
        return isConnect[0];
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("jayden", "dispatchKeyEvent = " );
//        switch (event.getKeyCode()) {
//            case KeyEvent.KEYCODE_DPAD_UP: {//上
//                Log.e("jayden", "Up = " + event.getAction());
//                return true;
//            }
//            case KeyEvent.KEYCODE_DPAD_DOWN: {//下
//                Log.e("jayden", "down = " + event.getAction());
//                return true;
//            }
//            case KeyEvent.KEYCODE_DPAD_LEFT: {//左
//                Log.e("jayden", "left = " + event.getAction());
//                return true;
//            }
//            case KeyEvent.KEYCODE_DPAD_RIGHT: {//右
//                Log.e("jayden", "right = " + event.getAction());
//                return true;
//            }
//        }
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

}
