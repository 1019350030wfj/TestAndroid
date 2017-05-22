package com.jayden.versionopo.api.bluetooth.sec;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jayden.versionopo.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Jayden on 2017/4/18.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ClientActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;//必须大于 0
    private static final int MESSAGE_READ = 1;//必须大于 0

    // 获取到蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    // 用来保存搜索到的设备信息
    private List<String> bluetoothDevices = new ArrayList<String>();
    // ListView组件
    private ListView lvDevices;
    // ListView的字符串数组适配器
    private ArrayAdapter<String> mArrayAdapter;

    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);

        // 获取到ListView组件
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        // 为listview设置字符换数组适配器
        mArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                bluetoothDevices);
        // 为listView绑定适配器
        lvDevices.setAdapter(mArrayAdapter);
        // 为listView设置item点击事件侦听
        lvDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //连接为客户端
                //1、使用BluetoothDevice
                // 获取到这个设备的信息
                String s = mArrayAdapter.getItem(position);
                // 对其进行分割，获取到这个设备的地址
                String address = s.substring(s.indexOf(":") + 1).trim();
                // 如果选择设备为空则代表还没有选择设备 通过地址获取到该设备
                mConnectThread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(address));
                mConnectThread.start();

            }
        });

        //一、设置蓝牙

        // 1、获取到蓝牙默认的适配器  BluetoothAdapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 2、为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //二、查询配对的设备
        // 用Set集合保持已绑定的设备
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + ":" + device.getAddress());
                Log.e("jayden", "type = " + device.getType());
            }
        }

        //三、发现设备
        // 因为蓝牙搜索到设备和完成搜索都是通过广播来告诉其他应用的
        // 这里注册找到设备和完成搜索广播
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BluetoothClass bluetoothClass = intent.getParcelableExtra(BluetoothDevice.EXTRA_CLASS);
                // 判断这个设备是否是之前已经绑定过了，如果是则不需要添加，在程序初始化的时候已经添加了
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 设备没有绑定过，则将其保持到arrayList集合中
                    bluetoothDevices.add(device.getName() + ":" + device.getAddress());
                    // 更新字符串数组适配器，将内容显示在listView中
                    mArrayAdapter.notifyDataSetChanged();
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Log.e("jayden", "搜索完成");
            }
        }
    };

    public void onClick_Search(View view) {
        bluetoothDevices.clear();
        // 点击搜索周边设备，如果正在搜索，则暂停搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnectThread != null) {
            mConnectThread.cancel();
        }
    }

    // UUID，蓝牙建立链接需要的
    private final UUID MY_UUID = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
//    private final UUID MY_UUID = UUID.fromString("00001124-0000-1000-8000-00805f9b34fb");
//    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//        private final UUID MY_UUID = UUID.fromString("00000000-0000-1000-8000-00805f9b34fb");
//    private final UUID MY_UUID = UUID.fromString("00000100-0000-1000-8000-00805f9b34fb");

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            createSocket();
        }

        private void createSocket() {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            mBluetoothAdapter.cancelDiscovery();
            try {
//                device.fetchUuidsWithSdp();
                Class<?> clazz = mmDevice.getClass();
                Class<?>[] paramTypes = new Class<?>[]{Integer.TYPE};

                Method m = clazz.getMethod("createRfcommSocket", paramTypes);
                Object[] params = new Object[]{Integer.valueOf(port)};

                tmp = (BluetoothSocket) m.invoke(mmDevice, params);
//                fallbackSocket.connect();
//                Method m =device.getClass().getMethod("createRfcommSocket",new Class[]{int.class});
//                tmp = (BluetoothSocket) m.invoke(device,29);
//                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
            if (isException) {
                isException = false;
                connect();
            }
        }

        public void connect() {
            // 判断当前是否还是正在搜索周边设备，如果是则暂停搜索
            // Cancel discovery because it will slow down the connection
//            mBluetoothAdapter.cancelDiscovery();
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                if (!mmSocket.isConnected()) {
                    mmSocket.connect();
                }
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                    isException = true;
                    port--;
                    if (port > 0) {
                        createSocket();
                    }
                } catch (IOException closeException) {
                }
            }
        }

        boolean isException = false;
        int port = 16;

        public void run() {
            // 判断当前是否还是正在搜索周边设备，如果是则暂停搜索
            // Cancel discovery because it will slow down the connection
//            mBluetoothAdapter.cancelDiscovery();
            connect();

            if (!isException) {
                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();
            }
        }

        /**
         * Will cancel an in-progress connection, and close the socket
         */
        public void cancel() {
            try {
                mmSocket.close();
                if (mConnectedThread != null) {
                    mConnectedThread.cancel();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 管理连接（接收数据和发送数据）
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        private volatile boolean isFlag;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            isFlag = true;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (isFlag) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                isFlag = false;
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_READ: {
                    // 通过msg传递过来的信息，吐司一下收到的信息
                    Toast.makeText(ClientActivity.this, "Client Receive" + msg.obj, Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    };


}
