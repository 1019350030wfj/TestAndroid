package com.jayden.versionopo.multimedia.audio;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jayden.versionopo.R;
import com.jayden.versionopo.multimedia.audio.wav.WavFileWriter;

import java.io.IOException;

/**
 * Created by Administrator on 2017/12/7.
 */

public class AudioCapture extends AppCompatActivity implements AudioRecorderHelper.OnAudioFrameCapturedListener{

    private static final String DEFAULT_TEST_FILE = Environment.getExternalStorageDirectory() + "/test.wav";

    private AudioRecorderHelper mAudioRecorderHelper;
    private WavFileWriter mWavFileWriter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_capture);
    }

    public void startCapture(View view){
        mAudioRecorderHelper = new AudioRecorderHelper();
        mWavFileWriter = new WavFileWriter();
        try {
            mWavFileWriter.openFile(DEFAULT_TEST_FILE,8000,1, 16);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        mAudioRecorderHelper.setOnAudioFrameCapturedListener(this);
        mAudioRecorderHelper.initAudioRecorder(1);
    }

    public void stopCapture(View view){
        mAudioRecorderHelper.releaseAudioRecorder();
        mAudioRecorderHelper = null;
        try {
            mWavFileWriter.closeFile();
            mWavFileWriter = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAudioFrameCaptured(byte[] audioData) {
        mWavFileWriter.writeData(audioData, 0, audioData.length);
    }
}
