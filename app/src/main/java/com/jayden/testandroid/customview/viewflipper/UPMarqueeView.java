package com.jayden.testandroid.customview.viewflipper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.jayden.testandroid.R;

import java.util.List;

/**
 * 一、设置动画，动画时间
 * Created by Jayden on 2016/7/29.
 */

public class UPMarqueeView extends ViewFlipper {

    private boolean isSetAnimTime = false;
    private int interval = 2000;

    private int animationDuration = 500;

    public UPMarqueeView(Context context) {
        this(context, null);
    }

    public UPMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setFlipInterval(interval);
        Animation animIn = AnimationUtils.loadAnimation(context, R.anim.push_up_in);
        if (isSetAnimTime) {
            animIn.setDuration(animationDuration);
        }
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(context, R.anim.push_up_out);
        if (isSetAnimTime) {
            animOut.setDuration(animationDuration);
        }
        setOutAnimation(animOut);
    }

    /**
     * 动态添加view
     * 启动动画
     * @param views
     */
    public void setViews(List<View> views) {
        if (views == null || views.size() == 0) {
            return;
        }
        removeAllViews();
        int length = views.size();
        for (int i = 0; i < length; i++) {
            //添加view点击回调
            final int position = i;
            views.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position,view);
                    }
                }
            });
            addView(views.get(i));
        }

        startFlipping();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * item_view的接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
