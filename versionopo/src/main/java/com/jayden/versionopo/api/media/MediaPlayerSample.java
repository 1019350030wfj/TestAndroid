package com.jayden.versionopo.api.media;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jayden.versionopo.R;
import com.jayden.versionopo.api.reflection.classobject.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2017/11/23.
 */

public class MediaPlayerSample extends Activity implements SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {

    private EditText mEditPath;
    private MediaPlayer mMediaPlayer;
    private SeekBar mSeekBar;
    private TextView mCurrentTime;
    private TextView mTotalTime;

    private final int NORMAL = 0;//闲置
    private final int PLAYING = 1;//播放中
    private final int PAUSING = 2;//暂停
    private final int STOPPING = 3;//停止中

    private int mCurrentState = NORMAL;//播放器当前的状态，默认是空闲状态

    //用行动打消忧虑
    private SurfaceHolder mSurfaceHolder;
    private boolean isStopUpdatingProgress=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        mEditPath = (EditText) findViewById(R.id.et_path);
        mSeekBar = (SeekBar) findViewById(R.id.sb_progress);
        mCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mTotalTime = (TextView) findViewById(R.id.tv_total_time);

        mSeekBar.setOnSeekBarChangeListener(this);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        mSurfaceHolder = surfaceView.getHolder();

        //不是采用自己内部的双缓冲区，而是等待别人推送数据
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void start(View view){
        if (mCurrentState == PLAYING){
            return;
        }
        if (mMediaPlayer != null){
            if (mCurrentState == PAUSING){
                mMediaPlayer.start();
                mCurrentState = PLAYING;
                isStopUpdatingProgress = false;//每次在调用刷新线程时，都要设为false
                new Thread(new UpdateProgressRunnable()).start();
                return;
            } else if (mCurrentState == STOPPING){//下面这个判断完美的解决了停止后重新播放，释放两个资源的问题
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        }
        play();
    }

    private void play() {//播放输入框的文件
        String path = mEditPath.getText().toString().trim();

        try {
            mMediaPlayer = new MediaPlayer();//Idle 状态
            //设置数据类型
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置以下播放器显示的位置
            mMediaPlayer.setDisplay(mSurfaceHolder);

            mMediaPlayer.setDataSource(path);//Initialized 状态
            mMediaPlayer.prepare();// prepared 状态
            mMediaPlayer.start(); //start 状态

            mMediaPlayer.setOnCompletionListener(this);

            //把当前播放器的状态设置为 ： 播放中
            mCurrentState = PLAYING;

            //把音乐文件的总长度取出来，设置给seekbar作为最大值
            int duration = mMediaPlayer.getDuration();//只有在 prepared start paused stop completed 状态获取才可以
            mSeekBar.setMax(duration);
            //把总时间显示在TextView上
            int m = duration / 1000 / 60;
            int s = duration / 1000 % 60;

            mTotalTime.setText("/"+m+":"+s);
            mCurrentTime.setText("00:00");

            isStopUpdatingProgress = false;
            new Thread(new UpdateProgressRunnable()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause(View view){
        if (mMediaPlayer != null && mCurrentState == PLAYING){
            mMediaPlayer.pause();
            mCurrentState = PAUSING;
            isStopUpdatingProgress = true;//停止刷新主线程
        }
    }

    public void stop(View view){
        if (mMediaPlayer != null){
            mMediaPlayer.stop();
        }
    }

    public void restart(View view){
        if (mMediaPlayer != null){
            mMediaPlayer.reset();
            mMediaPlayer.release();
            play();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isStopUpdatingProgress = true;//当开始拖动时，那么就开始停止刷新线程
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        //播放器切换到指定的进度位置上
        mMediaPlayer.seekTo(progress);
        isStopUpdatingProgress = false;
        new Thread(new UpdateProgressRunnable()).start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this, "播放完了，重新再播放", Toast.LENGTH_SHORT).show();
        mMediaPlayer.start();
    }

    class UpdateProgressRunnable implements Runnable{
        @Override
        public void run() {
            //每隔1秒钟取一下当前正在播放的进度，设置给seekbar
            while (! isStopUpdatingProgress){
                //得到当前进度
                int currentPosition = mMediaPlayer.getCurrentPosition();
                mSeekBar.setProgress(currentPosition);
                final int m=currentPosition/1000/60;
                final int s=currentPosition/1000%60;

                //此方法给定的runable对象，会执行主线程（UI线程中）
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        mCurrentTime.setText(m+":"+s);

                    }

                });
                SystemClock.sleep(1000);

            }
        }
    }
}
