package com.jayden.testandroid.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Jayden on 2016/6/24.
 * Email : 1570713698@qq.com
 */
public class CouponDisplayerView extends LinearLayout {

    public CouponDisplayerView(Context context) {
        super(context);
        init(context);
    }

    public CouponDisplayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CouponDisplayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Paint mPaint;
    private float mRadius;//圆的半径
    private float mGap;  //锯齿的宽度

    private int mCircleNum; //圆的个数
    private float mRemain; //不能整除剩余的宽度

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        mRadius = 20;
        mGap = 16;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mRemain == 0) {
            mRemain = (w - mGap) % (2 * mRadius + mGap);
        }
        mCircleNum = (int) ((w - mGap) / (mRadius + mGap));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mCircleNum; i++) {
            float x = mRemain / 2 + mGap + mRadius + (mRadius * 2 + mGap) * i;
            canvas.drawCircle(x, 0, mRadius, mPaint);
            canvas.drawCircle(x, getHeight(), mRadius, mPaint);
        }
    }
}
