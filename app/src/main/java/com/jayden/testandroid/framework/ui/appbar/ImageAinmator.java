package com.jayden.testandroid.framework.ui.appbar;

import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jayden on 2016/3/31.
 * Email : 1570713698@qq.com
 *
 * 渐变动画
 */
public class ImageAinmator {

    private static final float FACTOR = 0.1f;

    private SimpleAdapter mAdapter;
    private ImageView mTargetImage;
    private ImageView mOutgoingImage;

    private int mActualStart; //实际起始位置
    private int mStart;
    private int mEnd;

    public ImageAinmator(SimpleAdapter adapter,ImageView targetImage,ImageView outgoingImage) {
        this.mAdapter = adapter;
        this.mTargetImage = targetImage;
        this.mOutgoingImage = outgoingImage;
    }

    /**
     * 启动动画, 之后选择向前或向后滑动
     *
     * @param startPosition 起始位置
     * @param endPosition   终止位置
     */
    public void start(int startPosition, int endPosition) {
        mActualStart = startPosition;

        // 终止位置的图片
        @DrawableRes int incomeId = mAdapter.getDrawable(endPosition);

        // 原始图片
        mOutgoingImage.setImageDrawable(mTargetImage.getDrawable()); // 原始的图片

        // 起始图片
        mOutgoingImage.setTranslationX(0f);

        mOutgoingImage.setVisibility(View.VISIBLE);
        mOutgoingImage.setAlpha(1.0f);

        // 目标图片
        mTargetImage.setImageResource(incomeId);

        mStart = Math.min(startPosition, endPosition);
        mEnd = Math.max(startPosition, endPosition);
    }

    /**
     * 滑动结束的动画效果
     *
     * @param endPosition 滑动位置
     */
    public void end(int endPosition) {
        @DrawableRes int incomeId = mAdapter.getDrawable(endPosition);
        mTargetImage.setTranslationX(0f);

        // 设置原始图片
        if (endPosition == mActualStart) {
            mTargetImage.setImageDrawable(mOutgoingImage.getDrawable());
        } else {
            mTargetImage.setImageResource(incomeId);
            mTargetImage.setAlpha(1f);
            mOutgoingImage.setVisibility(View.GONE);
        }
    }

    // 向前滚动, 比如0->1, offset滚动的距离(0->1), 目标渐渐淡出
    public void forward(float positionOffset) {
        Log.e("DEBUG-WCL", "forward-positionOffset: " + positionOffset);
        int width = mTargetImage.getWidth();
        mOutgoingImage.setTranslationX(-positionOffset * (FACTOR * width));
        mTargetImage.setTranslationX((1 - positionOffset) * (FACTOR * width));

        mTargetImage.setAlpha(positionOffset);
    }

    // 向后滚动, 比如1->0, offset滚动的距离(1->0), 目标渐渐淡入
    public void backwards(float positionOffset) {
        Log.e("DEBUG-WCL", "backwards-positionOffset: " + positionOffset);
        int width = mTargetImage.getWidth();
        mOutgoingImage.setTranslationX((1 - positionOffset) * (FACTOR * width));
        mTargetImage.setTranslationX(-(positionOffset) * (FACTOR * width));

        mTargetImage.setAlpha(1 - positionOffset);
    }

    // 判断停止
    public boolean isWithin(int position) {
        return position >= mStart && position < mEnd;
    }
}
