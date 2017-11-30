package com.jayden.versionopo.multimedia.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Android 音频采集帮助类
 * Created by Administrator on 2017/11/30.
 */
public class AudioRecorderHelper {
    /**
     * 提供了两套音频采集的API MediaRecorder  和  AudioRecorder
     * <p>
     * MediaRecorder 是比较上层的API，它能够直接将手机麦克风采集的声音编码压缩为（AMR，MP3）等格式的文件，并保存
     * AudioRecorder 更接近底层，采集的是一帧帧原始的PCM数据。 如果我们需要对音频比如自己算法处理、编码、网络传输等，就用AudioRecorder
     * <p>
     * 步骤：
     * 1、配置参数，初始化内部的音频缓冲区
     * 2、需要开启一个线程，开始采集，并不断从缓冲区将音频数据“读”出来，注意这个过程要及时，否则会出现“overrun”的错误，该错误在音频开发中比较常见，意味着
     * 应用如果没有及时“取走”音频数据，导致内部的音频缓冲区溢出
     * 3、停止采集线程，停止采集，并释放AudioRecorder资源
     */

    private AudioRecord mAudioRecord;
    private int mMinRecordBufSize = 0;
    private boolean isSendAudio = true;         //是否发送音频 默认是发送
    private boolean mAudioRecordReleased = false;
    private boolean mRecordThreadExitFlag = false;            // 采集线程退出标志
    private RecordAudioThread mRecordAudioThread = null;    // 采集线程

    /**
     *  初始化音频采集设备
     *  @param profile 单声道，还是双声道（立体声）
     */
    public int initAudioRecorder(int profile) {
        if (mAudioRecord != null)
            return 0;
        int channel, samplerate, samplebit;
        // 根据上层设定的profile来配置参数
        if (profile == 1) {
            samplerate = 8000;
            channel = AudioFormat.CHANNEL_CONFIGURATION_MONO;
            samplebit = AudioFormat.ENCODING_PCM_16BIT;
        } else if (profile == 2) {
            samplerate = 44100;
            channel = AudioFormat.CHANNEL_CONFIGURATION_STEREO;
            samplebit = AudioFormat.ENCODING_PCM_16BIT;
        } else {
            return -1;
        }
        try {
            mAudioRecordReleased = false;
            // 获得构建对象的最小缓冲区大小 = 采样次数（音频帧数） * 通道数 * 每次采样的大小
            mMinRecordBufSize = AudioRecord.getMinBufferSize(samplerate, channel, samplebit);
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, samplerate, channel, samplebit, mMinRecordBufSize);

            //音频编码器初始化
//            mAudioEncoder = new AudioEncoder(new AudioCoderCallback() {
//                @Override
//                public void onData(byte[] bytes) {
//                    EMClient.getInstance().callManager().InputAudioData(bytes);//发给C++
//                }
//
//                @Override
//                public void onError(int i, String s) {//编码错误
//                }
//            }, channel - 1, mMinRecordBufSize / (channel - 1) / samplebit, samplerate);
            // 设置AnyChat的外部音频输入参数

            if (mRecordAudioThread == null) {
                mRecordThreadExitFlag = false;
                mRecordAudioThread = new RecordAudioThread();
                mRecordAudioThread.start();
            }
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }

    /**
     * 设置是否发送声音
     *
     * @param sendAudio
     */
    public void setSendAudio(boolean sendAudio) {
        isSendAudio = sendAudio;
    }

    /*
    *  音频采集线程
    */
    class RecordAudioThread extends Thread {
        @Override
        public void run() {
            if (mAudioRecord == null)
                return;
            try {
                android.os.Process.setThreadPriority(
                        android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            } catch (Exception e) {
            }
            try {
                mAudioRecord.startRecording();
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] recordbuf = new byte[mMinRecordBufSize];
            while (!mRecordThreadExitFlag) {
                if (isSendAudio) {//允许发送声音的
                    try {
                        int ret = mAudioRecord.read(recordbuf, 0, recordbuf.length);
                        if (ret == AudioRecord.ERROR_INVALID_OPERATION || ret == AudioRecord.ERROR_BAD_VALUE)
                            break;
                        // 通过callManager的外部音频输入接口将音频采样数据传入内核
//                        mAudioEncoder.encode(recordbuf);//编码
//                    EMClient.getInstance().callManager().InputAudioData(recordbuf);//发给C++
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }
    }


    /**
     * 销毁音频采集设备
     */
    public void releaseAudioRecorder() {
        if (mAudioRecordReleased)
            return;
        mAudioRecordReleased = true;
        if (mRecordAudioThread != null) {//采集线程停
            mRecordThreadExitFlag = true;
            try {
                mRecordAudioThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mRecordAudioThread = null;
        }

        if (mAudioRecord != null) {//释放采集资源
            try {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            } catch (Exception e) {

            }
        }
        // 关闭编码
//        if (mAudioEncoder != null) {
//            mAudioEncoder.release();
//            mAudioEncoder = null;
//        }
    }
}
