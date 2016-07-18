package com.jayden.testandroid.customview.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jayden.testandroid.R;

import java.io.IOException;

/**
 * Created by Jayden on 2016/6/13.
 * Email : 1570713698@qq.com
 */
public class TestRotateDrawable extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private ImageView btnPre, btnPlay, btnNext;
    private ImageView cdBox, handerd;

    private AnimatorState state = AnimatorState.State_Stop;  //动画状态
    private AudioState audioState = AudioState.STATE_STOP;   //音乐播放器状态

    private boolean flag = false;  //标记，控制唱片旋转

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rotate_drawable);

        cdBox = (ImageView) findViewById(android.R.id.progress);
        handerd = (ImageView) findViewById(android.R.id.background);

        btnPre = (ImageView) findViewById(android.R.id.button1);
        btnPlay = (ImageView) findViewById(android.R.id.button2);
        btnNext = (ImageView) findViewById(android.R.id.button3);
        btnPre.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        new MyThread().start();//开启线程，一直在那边跑，通过标志位来判断是否执行
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stop(1);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        setAudioState(AudioState.STATE_PAUSE);
        start();
    }

    private final static int DURATION = 360;

    /**
     * 开启动画
     */
    private void start() {
        ValueAnimator animator = ValueAnimator.ofInt(0,10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //中间值，不断改变level，从而实现旋转动画
                int level = (int) animation.getAnimatedValue();
                handerd.getDrawable().setLevel(level);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束，开启音频
                state = AnimatorState.State_Stop;
                audioStart();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                state = AnimatorState.State_Playing;
            }
        });
        animator.setDuration(DURATION);
        animator.start();
    }

    /**
     * 动画状态
     */
    public enum AnimatorState {
        State_Stop,
        State_Playing
    }

    @Override
    public void onClick(View v) {
        //一切按键必须在动画完成下才能被触发
        if (state == AnimatorState.State_Stop) {
            switch (v.getId()) {
                case android.R.id.button1: {
                    flag = false;
                    stop(-1);
                    break;
                }
                case android.R.id.button2: {
                    //如果不是在播放
                    if (audioState != AudioState.STATE_PLAYING) {
                        //
                        if (audioState == AudioState.STATE_STOP) {
                            prepareMusic();
                        } else {
                            //可能是暂停，所以开始播放
                            start();
                        }
                    } else {
                        //正在播放，则停止
                        pause();
                    }
                    break;
                }
                case android.R.id.button3: {
                    flag = false;
                    stop(1);
                    break;
                }
            }
        }
    }

    /**
     * 暂停播放
     */
    private void pause() {
        ValueAnimator animator = ValueAnimator.ofInt(10000,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int level = (int) animation.getAnimatedValue();
                handerd.getDrawable().setLevel(level);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                state = AnimatorState.State_Stop;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                state = AnimatorState.State_Playing;
                audioPause();
            }
        });
        animator.setDuration(DURATION);
        animator.start();
    }

    public enum AudioState {
        STATE_STOP,
        STATE_PAUSE,
        STATE_PREPARE,
        STATE_PLAYING
    }

    /**
     * 停止动画，主要用于切歌
     *
     * @param type
     */
    private void stop(final int type) {
        if (audioState == AudioState.STATE_PLAYING) {
            ValueAnimator animator01 = ValueAnimator.ofInt(10000, 0);
            animator01.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int level = (int) animation.getAnimatedValue();
                    handerd.getDrawable().setLevel(level);
                }
            });

            animator01.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    //停止音频
                    audioStop();
                    cdBox.getDrawable().setLevel(0);//停止旋转
                    state = AnimatorState.State_Playing;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    state = AnimatorState.State_Stop;
                    switch (type) {
                        case -1:{
                            audioPre();
                            break;
                        }
                        case 0:{
                            audioStop();
                            break;
                        }
                        case 1:{
                            audioNext();
                            break;
                        }
                    }
                }
            });
        } else {
            audioStop();
            switch (type) {
                case -1:{
                    audioPre();
                    break;
                }
                case 0:{
                    audioStop();
                    break;
                }
                case 1:{
                    audioNext();
                    break;
                }
            }
        }
    }

    /**
     * 前一首歌
     */
    private void audioPre() {
        if (audioState == AudioState.STATE_STOP) {
            //先释放前一首的资源
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }

            //这首歌的索引
            position --;
            if (position < 0) {
                position = names.length - 1;
            }

            //准备这首歌的资源
            prepareMusic();
        }
    }

    /**
     * 后一首歌
     */
    private void audioNext() {
        if (audioState == AudioState.STATE_STOP) {
            //先释放前一首的资源
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }

            //这首歌的索引
            position ++;
            if (position > names.length - 1) {
                position = 0;
            }

            prepareMusic();
        }
    }

    private MediaPlayer mediaPlayer;

    /**
     * 音乐停止
     */
    private void audioStop() {
        if (null != mediaPlayer && audioState != AudioState.STATE_STOP) {
            setAudioState(AudioState.STATE_STOP);
            mediaPlayer.stop();
            flag = false;
        }
    }

    /**
     * 音乐暂停
     */
    private void audioPause() {
        if (null != mediaPlayer && audioState != AudioState.STATE_PAUSE) {
            setAudioState(AudioState.STATE_PAUSE);
            mediaPlayer.pause();
            flag = false;
        }
    }

    /**
     * 音乐播放
     */
    private void audioStart() {
        if (null != mediaPlayer && (audioState == AudioState.STATE_PAUSE || audioState == AudioState.STATE_PREPARE)) {
            setAudioState(AudioState.STATE_PLAYING);
            mediaPlayer.start();
            flag = true;
        } else {
            if (mediaPlayer == null) {
                prepareMusic();
            }
        }
    }

    private String names[] = {"demo01.mp3", "demo02.mp3"};
    private int position = 0;

    private void prepareMusic() {
        //从assets文件读取音乐文件
        cdBox.getDrawable().setLevel(0);
        try {
            AssetFileDescriptor fileDescriptor = this.getAssets().openFd(names[position]);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAudioState(AudioState audioState) {
        this.audioState = audioState;
        if (audioState == AudioState.STATE_PLAYING) {
            btnPlay.setImageResource(R.drawable.landscape_player_btn_pause_normal);
        } else {
            btnPlay.setImageResource(R.drawable.landscape_player_btn_play_normal);
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    Thread.sleep(500);
                    if (flag) {
                        //flag 为true的时候才旋转
                        handler.sendMessage(Message.obtain());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int level = cdBox.getDrawable().getLevel();
            level += 200;
            if (level > 10000) {
                level -= 10000;
            }
            cdBox.getDrawable().setLevel(level);
        }
    };
}
