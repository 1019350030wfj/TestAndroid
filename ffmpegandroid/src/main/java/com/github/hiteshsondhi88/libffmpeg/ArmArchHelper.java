package com.github.hiteshsondhi88.libffmpeg;

/**
 * Created by Jayden on 2017/6/27.
 */

public class ArmArchHelper {
    static {
        System.loadLibrary("ARM_ARCH");
    }

    public static native String cpuArchFromJNI();

    boolean isARM_v7_CPU(String cpuInfoString) {
        return cpuInfoString.contains("v7");
    }

    boolean isNeonSupported(String cpuInfoString) {
        // check cpu arch for loading correct ffmpeg lib
        return cpuInfoString.contains("-neon");
    }
}
