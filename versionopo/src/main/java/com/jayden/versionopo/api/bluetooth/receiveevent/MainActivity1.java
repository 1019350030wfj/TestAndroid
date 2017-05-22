package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jayden.versionopo.R;

import java.util.Iterator;
import java.util.Set;

/**
 * 这个类是可以手动搜索蓝牙设备，显示蓝牙设备列表
 * Created by Jayden on 2017/4/20.
 */
public class MainActivity1 extends AppCompatActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private BlueBroadcastReceiver mBlueBroadcastReceiver;
    private BluetoothDeviceAdapter mMyBluetoothAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_receive_event);
        initview();

        initBlue();
        //初始化已连接hid列表
        initPair();
    }

    private void initview() {
        mListView = (ListView) findViewById(R.id.listview);
        Button search = (Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyBluetoothAdapter != null) {
                    mMyBluetoothAdapter.clear();
                }
                //扫描蓝牙设备
                if (!mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.startDiscovery();
                }
            }
        });
    }

    private void initPair() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持蓝牙功能", Toast.LENGTH_LONG).show();
            //不支持蓝牙
            return;
        }
        //得到已经配对的列表
        Set<BluetoothDevice> mSet = mBluetoothAdapter.getBondedDevices();
        final Iterator mIterator = mSet.iterator();
        while (mIterator.hasNext()) {
            BluetoothDevice mBluetoothDevice = (BluetoothDevice) mIterator
                    .next();
            if (mMyBluetoothAdapter == null) {
                mMyBluetoothAdapter = new BluetoothDeviceAdapter(MainActivity1.this);
                mListView.setAdapter(mMyBluetoothAdapter);
            }
            mMyBluetoothAdapter.addData(mBluetoothDevice);
            mMyBluetoothAdapter.notifyDataSetChanged();
        }
    }

    private void initBlue() {
        //初始化广播接收
        mBlueBroadcastReceiver = new BlueBroadcastReceiver();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBlueBroadcastReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    public void registerReceiver() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        localIntentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        localIntentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        localIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        localIntentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        localIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        this.registerReceiver(mBlueBroadcastReceiver, localIntentFilter);
    }

    private class BlueBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getAction();
            Log.i("jayden", "onReceive" + str);
            //通过广播接收到了BluetoothDevice
            final BluetoothDevice localBluetoothDevice = (BluetoothDevice) intent
                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            if (localBluetoothDevice != null) {
                if (mMyBluetoothAdapter == null) {
                    mMyBluetoothAdapter = new BluetoothDeviceAdapter(MainActivity1.this);
                    mListView.setAdapter(mMyBluetoothAdapter);
                }
                mMyBluetoothAdapter.addData(localBluetoothDevice);
                mMyBluetoothAdapter.notifyDataSetChanged();
            }
        }
    }
}
