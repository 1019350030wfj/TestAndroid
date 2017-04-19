package com.jayden.versionopo.api.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Jayden on 2017/4/15.
 */
public class ClientActivity extends Activity implements AdapterView.OnItemClickListener {
    // 获取到蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    // 用来保存搜索到的设备信息
    private List<String> bluetoothDevices = new ArrayList<String>();
    // ListView组件
    private ListView lvDevices;
    // ListView的字符串数组适配器
    private ArrayAdapter<String> arrayAdapter;
    // UUID，蓝牙建立链接需要的
//    private final UUID MY_UUID = UUID.fromString("00001124-0000-1000-8000-00805f9b34fb");
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //    private final UUID MY_UUID = UUID.fromString("00000000-0000-1000-8000-00805f9b34fb");
    // 为其链接创建一个名称
    private final String NAME = "Bluetooth_Socket";
    // 选中发送数据的蓝牙设备，全局变量，否则连接在方法执行完就结束了
    private BluetoothDevice selectDevice;
    // 获取到选中设备的客户端串口，全局变量，否则连接在方法执行完就结束了
    private BluetoothSocket clientSocket;
    // 获取到向设备写的输出流，全局变量，否则连接在方法执行完就结束了
    private OutputStream os;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);

        // 获取到ListView组件
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        // 为listview设置字符换数组适配器
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                bluetoothDevices);
        // 为listView绑定适配器
        lvDevices.setAdapter(arrayAdapter);
        // 为listView设置item点击事件侦听
        lvDevices.setOnItemClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permissions, 10);
        } else {
            initBlueTooth();
        }
    }

    private void initBlueTooth() {

        // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "前手机不支持ble", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 获取到蓝牙默认的适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }


        // 用Set集合保持已绑定的设备00001101-0000-1000-8000-00805f9b34fb
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : devices) {
                // 保存到arrayList集合中
                bluetoothDevices.add(bluetoothDevice.getName() + ":"
                        + bluetoothDevice.getAddress() + "\n");
            }
        }
        // 因为蓝牙搜索到设备和完成搜索都是通过广播来告诉其他应用的
        // 这里注册找到设备和完成搜索广播
        IntentFilter filter = new IntentFilter(
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            initBlueTooth();
        }
    }

    public void onClick_Search(View view) {
        bluetoothDevices.clear();
        // 点击搜索周边设备，如果正在搜索，则暂停搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 注册广播接收者
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            // 获取到广播的action
            String action = intent.getAction();
            // 判断广播是搜索到设备还是搜索完成
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                // 找到设备后获取其设备
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 判断这个设备是否是之前已经绑定过了，如果是则不需要添加，在程序初始化的时候已经添加了
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    // 设备没有绑定过，则将其保持到arrayList集合中
                    bluetoothDevices.add(device.getName() + ":"
                            + device.getAddress() + "\n");
                    // 更新字符串数组适配器，将内容显示在listView中
                    arrayAdapter.notifyDataSetChanged();
                }
            } else if (action
                    .equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                Toast.makeText(ClientActivity.this, "搜索完成", Toast.LENGTH_LONG).show();
            }
        }
    };

    class ConnectThread extends Thread {
        private int mPos;

        public ConnectThread(int pos) {
            this.mPos = pos;
        }

        @Override
        public void run() {
            // 获取到这个设备的信息
            String s = arrayAdapter.getItem(mPos);
            // 对其进行分割，获取到这个设备的地址
            String address = s.substring(s.indexOf(":") + 1).trim();
            // 判断当前是否还是正在搜索周边设备，如果是则暂停搜索
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            // 如果选择设备为空则代表还没有选择设备
            if (selectDevice == null) {
                //通过地址获取到该设备
                selectDevice = mBluetoothAdapter.getRemoteDevice(address);
            }
            // 这里需要try catch一下，以防异常抛出
            try {
                clientSocket = selectDevice.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (Exception e) {
                Log.e("jayden", "Error creating socket");
            }

            try {
                clientSocket.connect();
                ReadThread readThread = new ReadThread();
                readThread.start();
                Log.e("jayden", "Connected");
            } catch (IOException e) {
                Log.e("jayden", e.getMessage());
                try {
                    Log.e("jayden", "trying fallback...");

                    clientSocket = (BluetoothSocket) selectDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(selectDevice, 2);
                    clientSocket.connect();
                    ReadThread readThread = new ReadThread();
                    readThread.start();
                    Log.e("jayden", "Connected");
                } catch (Exception e2) {
                    Log.e("jayden", "Couldn't establish Bluetooth connection!");
                }
            }

            // 判断客户端接口是否为空
//            if (clientSocket == null) {
//                try {  // 获取到客户端接口
//                    clientSocket = selectDevice.createRfcommSocketToServiceRecord(MY_UUID);
//                    clientSocket.connect();
//                    ReadThread readThread = new ReadThread();
//                    readThread.start();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
////                ParcelUuid[] parcelUuids = selectDevice.getUuids();
////                for (ParcelUuid parcelUuid : parcelUuids) {
////                    try {  // 获取到客户端接口
////                        clientSocket = selectDevice.createRfcommSocketToServiceRecord(parcelUuid.getUuid());
////                        ReadThread readThread = new ReadThread();
////                        readThread.start();
////                        break;
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                        // 如果发生异常则告诉用户发送失败
//////                        Toast.makeText(this, "连接蓝牙失败", Toast.LENGTH_LONG).show();
////                    }
////                }
//            }
        }
    }

    /**
     * 将16进制 转换成10进制
     *
     * @param str
     * @return
     */
    public static String print10(String str) {

        StringBuffer buff = new StringBuffer();
        String array[] = str.split(" ");
        for (int i = 0; i < array.length; i++) {
            int num = Integer.parseInt(array[i], 16);
            buff.append(String.valueOf((char) num));
        }
        return buff.toString();
    }

    /**
     * byte转16进制
     *
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {

        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }


    // 点击listView中的设备，传送数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        new ConnectThread(position).start();
    }

    // 创建handler，因为我们接收是采用线程来接收的，在线程中无法操作UI，所以需要handler
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 通过msg传递过来的信息，吐司一下收到的信息
            Toast.makeText(ClientActivity.this, "Client Receive" + msg.obj, Toast.LENGTH_LONG).show();
        }
    };

    // 客户端接收信息线程
    private class ReadThread extends Thread {
        private InputStream is;// 获取到输入流

        public ReadThread() {
        }

        public void run() {
            // 向服务端发送连接
            try {
                if (clientSocket.isConnected()) {
                    os = clientSocket.getOutputStream();
                    this.is = clientSocket.getInputStream();
                    // 无线循环来接收数据
                    while (true) {
                        // 创建一个128字节的缓冲
                        byte[] buffer = new byte[128];
                        // 每次读取128字节，并保存其读取的角标
                        int count = is.read(buffer);
                        // 创建Message类，向handler发送数据
                        Message msg = new Message();
                        // 发送一个String的数据，让他向上转型为obj类型
//                        msg.obj = new String(buffer, 0, count, "utf-8");
                        msg.obj = print10(byte2HexStr(buffer));
                        // 发送数据
                        handler.sendMessage(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        is = null;
                    }
                }
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        clientSocket = null;
                    }

                }
            }

        }
    }
}
