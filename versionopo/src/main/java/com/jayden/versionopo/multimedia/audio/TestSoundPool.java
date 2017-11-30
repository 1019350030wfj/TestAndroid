package com.jayden.versionopo.multimedia.audio;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jayden.versionopo.R;

/**
 *  声音短、文件小、延时慢
 * Created by Administrator on 2017/11/30.
 */

public class TestSoundPool extends Activity{
    /**
     *  开发步骤：
     *  1、将音乐文件拷贝到工程下，raw
     *  2、新建一个SoundPool实例
     *  3、使用load（）方法，让SoundPool实例加载音效资源，该方法会返回一个整形的soundId，在第4步会用到
     *  4、使用play（）方法播放音效，该方法返回一个整型的streamID，这个返回值可以保存起来，后面暂停、恢复、停止播放时都需要用到
     *
     *  遇到的问题：
     *  1、假如load完之后，马上调用play的话， 有可能会没有声音。因为在load完成之前，soundID是空的。解决办法：解决办法是,
     *  SoundPool提供了一个监听加载资源完成的回调public void setOnLoadCompleteListener(OnLoadCompleteListener listener)只需要在这个回调中播放声音就好啦:
     */

    private SoundPool mSoundPool;
    private int streamID;
    private int soundID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundpool);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
        soundID = mSoundPool.load(this, R.raw.outgoing, 1);
    }

    public void play(View view){
        streamID = mSoundPool.play(soundID, // sound resource
                0.3f, // left volume
                0.3f, // right volume
                1,    // priority
                -1,   // loop，0 is no loop，-1 is loop forever
                1);   // playback rate (1.0 = normal playback, range 0.5 to 2.0)
    }

    public void stop(View view){
        if (mSoundPool != null){
            mSoundPool.stop(streamID);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (streamID != 0 && mSoundPool != null){
            mSoundPool.resume(streamID);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (streamID != 0 && mSoundPool != null){
            mSoundPool.pause(streamID);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundID != 0 && mSoundPool != null){
            mSoundPool.unload(soundID);//从音频池中卸载音频资源ID为soundID的资源
            mSoundPool.release();//释放音频池资源
            mSoundPool = null;
        }
    }
}
