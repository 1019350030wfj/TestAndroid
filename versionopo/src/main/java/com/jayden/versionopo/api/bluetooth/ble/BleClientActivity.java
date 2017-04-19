package com.jayden.versionopo.api.bluetooth.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jayden.versionopo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jayden on 2017/4/18.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BleClientActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;//必须大于 0
    private static final int MESSAGE_READ = 1;//必须大于 0

    // 获取到蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    // 用来保存搜索到的设备信息
    private List<String> bluetoothDevices = new ArrayList<String>();
    // ListView组件
    private ListView lvDevices;
    // ListView的字符串数组适配器
    private LeDeviceListAdapter mArrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);

        // 获取到ListView组件
        lvDevices = (ListView) findViewById(R.id.lvDevices);

        // 为listView设置item点击事件侦听
        lvDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BluetoothDevice device = mArrayAdapter.getDevice(position);
                if (device == null) return;
                final Intent intent = new Intent(BleClientActivity.this, DeviceControlActivity.class);
                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                if (mScanning) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanning = false;
                }
                startActivity(intent);
            }
        });

        //一、设置蓝牙

        // 1、获取到蓝牙默认的适配器  BluetoothAdapter
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this,"not", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onClick_Search(View view){
        mArrayAdapter.clear();
        scanLeDevice(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 2、为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        // 为listview设置字符换数组适配器
        mArrayAdapter = new LeDeviceListAdapter();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.addDevice(device);
                // Add the name and address to an array adapter to show in a ListView
//                mArrayAdapter.add(device.getName() + ":" + device.getAddress());
            }
        }
        // 为listView绑定适配器
        lvDevices.setAdapter(mArrayAdapter);
        scanLeDevice(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        mArrayAdapter.clear();
    }

    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private boolean mScanning;
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String name =  device.getName();
                            Log.d("jayden", "run:sring "+name);
                            if(!TextUtils.isEmpty(name)) {
//                            if(!TextUtils.isEmpty(name) && "Mojing4".contains(name)) {
                                mArrayAdapter.addDevice(device);
                                mArrayAdapter.notifyDataSetChanged();
                            }

                        }
                    });
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_READ:{
                    // 通过msg传递过来的信息，吐司一下收到的信息
                    Toast.makeText(BleClientActivity.this, "Client Receive" + msg.obj, Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    };


}
