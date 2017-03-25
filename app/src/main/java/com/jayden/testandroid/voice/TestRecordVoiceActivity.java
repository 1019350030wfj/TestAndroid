package com.jayden.testandroid.voice;

import android.Manifest;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jayden.testandroid.R;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jayden on 2017/2/20.
 */

public class TestRecordVoiceActivity extends Activity {

    private static final int REQUECT_CODE_RECODE_VOICE = 0x111;

    private Button mButtonStop;
    private Button mButtonPause;
    private Button mButtonPlay;
    private Button mButtonRecord;

    private ProgressBar mProgressBar;

    // 语音文件
    private String fileName = null;
    // 音频文件保存的路径
    private String path = "";
    // 语音操作对象
    private MediaPlayer mPlayer = null;// 播放器
    private MediaRecorder mRecorder = null;// 录音器
    private boolean isPause = false;// 当前录音是否处于暂停状态

    private ArrayList<String> mList = new ArrayList<String>();// 待合成的录音片段

    private boolean isPausePlayer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_travel_record_voice);
        path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/onesoft/" + getPackageName() + "/Record";
        mButtonPause = (Button) findViewById(R.id.btn_pause);
        mButtonStop = (Button) findViewById(R.id.btn_stop);
        mButtonPlay = (Button) findViewById(R.id.btn_play);
        mButtonRecord = (Button) findViewById(R.id.btn_record);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//播放
                // 播放录音
                playRecord();
            }
        });
        mButtonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//录制
                MPermissions.requestPermissions(TestRecordVoiceActivity.this, REQUECT_CODE_RECODE_VOICE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO});
            }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//停止
                if (mPlayer != null) {// 停止播放
                    mButtonPlay.setEnabled(true);
                    mButtonRecord.setEnabled(true);
                    mButtonStop.setEnabled(false);
                    mButtonPause.setEnabled(false);
                    // 释放资源
                    // 对MediaPlayer多次使用而不释放资源就会出现MediaPlayer create faild 的异常
                    mPlayer.release();
                    mPlayer = null;
                } else {//停止录制
                    stopRecord();
                }
            }
        });
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//暂停录音
                if (mPlayer != null && !isPausePlayer) {// 暂停播放
                    mPlayer.pause();
                    isPausePlayer = true;
                    mButtonPause.setEnabled(false);
                    mButtonPlay.setEnabled(true);
                } else {//暂停录制
                    try {
                        pauseRecord();
                    } catch (InterruptedException e) {
                        // 当一个线程处于等待，睡眠，或者占用，也就是说阻塞状态，而这时线程被中断就会抛出这类错误
                        // 上百次测试还未发现这个异常，但是需要捕获
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 暂停录制
     */
    private void pauseRecord() throws InterruptedException {
        if (System.currentTimeMillis() - limitTime < 1100) {
            //录音文件不得低于一秒钟
            Toast.makeText(this, "录音时间长度不得低于1秒钟！", Toast.LENGTH_SHORT).show();
            return;
        }
        mButtonStop.setEnabled(true);
        mRecorder.stop();
        mRecorder.release();
        timer.cancel();
        isPause = true;

        mButtonRecord.setEnabled(true);
    }

    // 完成录音
    private void stopRecord() {
        mRecorder.release();
        mRecorder = null;
        isPause = false;
        mButtonRecord.setEnabled(true);
        mButtonPlay.setEnabled(true);
        mButtonStop.setEnabled(false);
        mButtonPause.setEnabled(false);
        if (timerPlay != null){
            timerPlay.cancel();
            timerPlay = null;
        }
        timer.cancel();
        // 最后合成的音频文件
        fileName = path + "/travel.amr";
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream = null;
        try {
            for (int i = 0; i < mList.size(); i++) {
                File file = new File(mList.get(i));
                // 把因为暂停所录出的多段录音进行读取
                fileInputStream = new FileInputStream(file);
                byte[] mByte = new byte[fileInputStream.available()];
                int length = mByte.length;
                // 第一个录音文件的前六位是不需要删除的
                if (i == 0) {
                    while (fileInputStream.read(mByte) != -1) {
                        fileOutputStream.write(mByte, 0, length);
                    }
                }
                // 之后的文件，去掉前六位
                else {
                    while (fileInputStream.read(mByte) != -1) {
                        fileOutputStream.write(mByte, 6, length - 6);
                    }
                }
            }
        } catch (Exception e) {
            // 这里捕获流的IO异常，万一系统错误需要提示用户
            e.printStackTrace();
            Toast.makeText(this, "录音合成出错，请重试！", Toast.LENGTH_LONG).show();
        } finally {
            try {
                fileOutputStream.flush();
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 录音结束 、时间归零
            second = 0;
            handler.sendEmptyMessage(1);
        }
        // 不管合成是否成功、删除录音片段
        for (int i = 0; i < mList.size(); i++) {
            File file = new File(mList.get(i));
            if (file.exists()) {
                file.delete();
            }
        }
    }

    // 播放录音
    private void playRecord() {
        if (TextUtils.isEmpty(fileName)){
            return;
        }
        mButtonRecord.setEnabled(false);
        mButtonPause.setEnabled(true);
        mButtonPlay.setEnabled(false);
        mButtonStop.setEnabled(true);
        if (isPausePlayer && mPlayer != null) {
            isPausePlayer = false;
            mPlayer.start();
            return;
        }
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        mPlayer = new MediaPlayer();
        // 播放完毕的监听
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 播放完毕改变状态，释放资源
                timerPlay.cancel();
                timerPlay = null;
                mPlayer.release();
                mPlayer = null;
                mButtonRecord.setEnabled(true);
                mButtonPlay.setEnabled(true);
                mButtonStop.setEnabled(false);
                mButtonPause.setEnabled(false);
            }
        });
        try {
            // 播放所选中的录音
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
            // 将录音片段加入列表
            mList.add(fileName);
            /*监听缓存 事件，在缓冲完毕后，开始播放*/
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mProgressBar.setMax(mPlayer.getDuration());
                }
            });
            //监听播放时回调函数
            timerPlay = new Timer();
            timerPlay.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mPlayer != null){
                        mProgressBar.setProgress(mPlayer.getCurrentPosition());
                    }
                }
            }, 0, 1000);
        } catch (Exception e) {
            // 若出现异常被捕获后，同样要释放掉资源
            // 否则程序会不稳定，不适合正式项目上使用
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
            Toast.makeText(this, "播放失败,可返回重试！", Toast.LENGTH_LONG).show();
            mButtonStop.setEnabled(false);
            mButtonPause.setEnabled(false);
        }
    }

    // 获得当前时间
    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH：mm：ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String time = formatter.format(curDate);
        return time;
    }

    private long limitTime = 0;// 录音文件最短事件1秒

    private Timer timer;
    private Timer timerPlay;

    /**
     * 开始录制
     */
    private void startRecord() {
        mProgressBar.setMax(60);//60秒
        mButtonPlay.setEnabled(false);
        mButtonRecord.setEnabled(false);
        mButtonPause.setEnabled(true);
        mButtonStop.setEnabled(true);
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!isPause) {
            // 新录音清空列表
            mList.clear();
        }
        fileName = path + "/" + getTime() + ".amr";//录音文件名称
        isPause = false;
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 选择amr格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (Exception e) {
            // 若录音器启动失败就需要重启应用，屏蔽掉按钮的点击事件。 否则会出现各种异常。
            Toast.makeText(this, "录音器启动失败，请返回重试！", Toast.LENGTH_LONG).show();
            mButtonPlay.setEnabled(false);
            mButtonPause.setEnabled(false);
            mButtonRecord.setEnabled(false);
            mButtonStop.setEnabled(false);
            mRecorder.release();
            mRecorder = null;
        }
        if (mRecorder != null) {
            mRecorder.start();
            limitTime = System.currentTimeMillis();
        }
    }

    // 计时器异步更新界面
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //更新进度条
            mProgressBar.setProgress(second);
        }
    };

    // 相关变量
    private int second = 0;

    // 录音计时
    private void recordTime() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                second++;
                if (second >= 60) {
                    //大于60秒，就让它结束录音
                    stopRecord();
                }
                handler.sendEmptyMessage(1);
            }

        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);
    }

    /*============权限要求===============*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUECT_CODE_RECODE_VOICE)
    public void requestSdcardSuccess() {
        // 开始录音
        startRecord();
        // 录音计时
        recordTime();
    }

    @PermissionDenied(REQUECT_CODE_RECODE_VOICE)
    public void requestSdcardFailed() {
        Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    /*============权限要求===============*/
}
