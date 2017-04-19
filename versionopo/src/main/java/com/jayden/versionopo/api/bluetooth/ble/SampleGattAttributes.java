package com.jayden.versionopo.api.bluetooth.ble;

import java.util.HashMap;

/**
 * Created by Jayden on 2017/4/18.
 */

public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HAND_SET = "00001124-0000-1000-8000-00805f9b34fb";
//    public static String HAND_SET = "00001101-0000-1000-8000-00805F9B34FB";
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put(HAND_SET, "Hand set");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}
