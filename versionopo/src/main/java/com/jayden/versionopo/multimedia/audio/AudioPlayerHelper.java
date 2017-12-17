package com.jayden.versionopo.multimedia.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.jayden.versionopo.util.ByteUtils;

/**
 * Android 音频播放帮助类
 * Created by Administrator on 2017/11/30.
 */
public class AudioPlayerHelper {
    /**
     *  提供了三种播放音频的方式  SoundPool MediaPlayer AudioTrack
     *  1、MediaPlayer 有延时，且占用资源较多， 适用于播放音乐
     *  2、AudioTrack的工作流程（步骤）：
     *      A、配置参数，初始化内部的音频播放缓冲区
     *      B、开启线程，并调用播放方法，然后循环不断地向AudioTrack缓冲区“写入”音频数据，当出现underrun
     *      错误时，说明应用层没有及时“送入”音频数据，导致内部的音频播放缓冲区为空
     *      C、停止播放线程，停止播放，释放AudioTrack资源
     */


    private AudioTrack mAudioTrack;
    private boolean mAudioPlayReleased = false;
    private boolean mPlayThreadExitFlag = false;            // 播放线程退出标志
    private PlayAudioThread mPlayAudioThread = null;        // 播放线程

    /**
     * 初始化音频播放器
     * @param profile 通道数
     * @return
     */
    public int initAudioPlayer(int profile) {
        if (mAudioTrack != null)
            return 0;
        int channel, samplerate, samplebit;
        // 根据上层设定的profile来配置参数
        if (profile == 1) {
            samplerate = 8000;//采样频率
            channel = AudioFormat.CHANNEL_CONFIGURATION_MONO;//频道数
            samplebit = AudioFormat.ENCODING_PCM_16BIT;//采样位数
        } else if (profile == 2) {
            samplerate = 44100;
            channel = AudioFormat.CHANNEL_CONFIGURATION_STEREO;
            samplebit = AudioFormat.ENCODING_PCM_16BIT;
        } else {
            return -1;
        }
        try {
            mAudioPlayReleased = false;
            // 获得构建对象的最小缓冲区大小
            int mMinPlayBufSize = AudioTrack.getMinBufferSize(samplerate, channel, samplebit);
            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, samplerate, channel, samplebit, mMinPlayBufSize, AudioTrack.MODE_STREAM);

            //音频编码器初始化
//            mAudioDecoder = new AudioDecoder(new AudioCoderCallback() {
//                @Override
//                public void onData(byte[] bytes) {
//                    mAudioTrack.write(bytes, 0, bytes.length);//解码成功,放入缓冲区播放
//                }
//
//                @Override
//                public void onError(int i, String s) {//解码错误
//                }
//            }, channel - 1, mMinPlayBufSize / (channel - 1) / samplebit, samplerate);

            if (mPlayAudioThread == null) {
                mPlayThreadExitFlag = false;
                mPlayAudioThread = new PlayAudioThread();
                mPlayAudioThread.start();
            }
        } catch (Exception e) {
            return -1;
        }
        return 0;
    }


    /*
     *  音频播放线程
     */
    class PlayAudioThread extends Thread {
        @Override
        public void run() {
            if (mAudioTrack == null)
                return;
            try {
                android.os.Process.setThreadPriority(
                        android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
            } catch (Exception e) {
            }
            try {
                mAudioTrack.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (!mPlayThreadExitFlag) {
                try {
                    // 获取音频数据
                    byte[] data = FetchAudioPlayBuffer();
                    if (data != null && data.length > 0) {
                        //解码
//                        mAudioDecoder.decode(data);
//                        mAudioTrack.write(data, 0, data.length);//解码成功,播放
                        mAudioTrack.write(data, 0, data.length);//解码成功,放入缓冲区播放
                    }
                } catch (Exception e) {
                    break;
                }
            }
        }
    }


    /**
     * 销毁音频播放器
     */
    public void releaseAudioPlayer() {
        if (mAudioPlayReleased)
            return;
        mAudioPlayReleased = true;
        if (mPlayAudioThread != null) {//停止播放线程
            mPlayThreadExitFlag = true;
            try {
                mPlayAudioThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mPlayAudioThread = null;
        }

        if (mAudioTrack != null) { //释放音频播放资源
            try {
                mAudioTrack.stop();
                mAudioTrack.release();
                mAudioTrack = null;
            } catch (Exception e) {

            }
        }
        // 解码
//        if (mAudioDecoder != null) {
//            mAudioDecoder.release();
//            mAudioDecoder = null;
//        }
    }

    private byte[] mBytes;
    /**
     * 获取声音播放数据
     *
     * @return
     */
    public byte[] FetchAudioPlayBuffer() {
        byte[] temp;
        synchronized (AudioPlayerHelper.class) {
            temp = mBytes;
            mBytes = null;
        }
        return temp;
    }

    /**
     * 从C++服务器获取音频数据，然后解码完的数据放入mBytes
     *
     * @param t
     */
    public void receiveAudioData(byte[] t) {
        synchronized (AudioPlayerHelper.class) {
            if (mBytes == null) {
                mBytes = t;
            } else {
                mBytes = ByteUtils.byteMerger(mBytes, t);
            }
        }
    }
}
