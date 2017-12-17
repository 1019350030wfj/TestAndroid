package com.jayden.versionopo.util;

/**
 * 对字节的处理
 * Created by Jayden on 2016/9/24.
 */

public class ByteUtils {

    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        int length1 = byte_1 == null ? 0 : byte_1.length;
        int length2 = byte_2 == null ? 0 : byte_2.length;
        byte[] byte_3 = new byte[length1 + length2];
        if (byte_1 != null) {
            System.arraycopy(byte_1, 0, byte_3, 0, length1);
        }
        if (byte_2 != null) {
            System.arraycopy(byte_2, 0, byte_3, length1, length2);
        }
        return byte_3;
    }

    public static byte[] byteSplit(int tempLength,byte[] byte_1){
        byte[] byte_3 = new byte[byte_1.length-tempLength];
        System.arraycopy(byte_1, tempLength, byte_3, 0, byte_3.length);
        return byte_3;
    }
}
