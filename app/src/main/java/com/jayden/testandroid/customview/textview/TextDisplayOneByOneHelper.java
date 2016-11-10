package com.jayden.testandroid.customview.textview;

import android.os.Handler;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jayden.testandroid.R;

/**
 * android 文字逐个显示 帮助类
 * Created by Jayden on 2016/9/10.
 */

public class TextDisplayOneByOneHelper {

    private Handler mHandler;

    public TextDisplayOneByOneHelper() {
        mHandler = new Handler();
    }

    /**
     * 逐个显示文字
     * @param textview 显示的控件
     * @param content 显示的文字
     */
    public void setText(final TextView textview, String content) {
        setText(textview,content,
                AnimationUtils.loadAnimation(textview.getContext(), R.anim.alpha),
                2000);
    }
    /**
     * 逐个显示文字
     * @param textview 显示的控件
     * @param content 显示的文字
     * @param animation 显示的动画
     * @param duration 每个字显示动画的时间
     */
    public void setText(final TextView textview, String content, final Animation animation, int duration) {
        final StringBuilder builder = new StringBuilder();
        int time = 0;
//        textview.setAnimation(animation);
        if (!TextUtils.isEmpty(content)){
            char[] characters = content.toCharArray();
            for(final char c : characters) {
                //遍历传入的字符串的每个字符

                //每隔duration时间，播放下一个TextView的动画
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        builder.append(c);
                        textview.setText(builder.toString());
                    }
                }, time);
                time += 1000;
            }
        }
    }
}
