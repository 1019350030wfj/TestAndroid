package com.jayden.versionopo.multimedia.audio;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jayden.versionopo.R;
import com.jayden.versionopo.multimedia.audio.wav.WavFileReader;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/7.
 */

public class AudioPlayer extends AppCompatActivity {

 private static final String DEFAULT_TEST_FILE = Environment.getExternalStorageDirectory() + "/test.wav";

    private AudioPlayerHelper mAudioPlayerHelper;
    private WavFileReader mWavFileReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_capture);
    }

    public void startCapture(View view){
        mAudioPlayerHelper = new AudioPlayerHelper();
        mWavFileReader = new WavFileReader();
        try {
            mWavFileReader.openFile(DEFAULT_TEST_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mAudioPlayerHelper.initAudioPlayer(1);

        new Thread(AudioPlayRunnable).start();
    }

    public void stopCapture(View view){
        mIsTestingExit = true;
        mAudioPlayerHelper.releaseAudioPlayer();
        try {
            mWavFileReader.closeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int SAMPLES_PER_FRAME = 1024;

    private volatile boolean mIsTestingExit = false;

    private Runnable AudioPlayRunnable = new Runnable() {
        @Override
        public void run() {
            byte[] buffer = new byte[SAMPLES_PER_FRAME * 2];
            while (!mIsTestingExit && mWavFileReader.readData(buffer, 0, buffer.length) > 0) {
                mAudioPlayerHelper.receiveAudioData(buffer);
            }
            stopCapture(null);
        }
    };
}
