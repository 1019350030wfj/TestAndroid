package com.jayden.testandroid.customview.rotate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jayden.testandroid.R;

import java.util.Calendar;

/**
 * Created by Jayden on 2016/8/10.
 */

public class RotateView extends View {

    private static final String TAG = "Jayden";
    private static final int DAMPING = 10;
    // 圆心坐标
    private Bitmap bitmapBig;//随手指转动的图片
    private float mPointX = 0, mPointY = 0;
    // 旋转角度
    private int mCanvasAngle = 0;
    private int beginAngle = 0, currentAngle = 0;
    boolean isTouch = false;
    long beginTime, endTime;
    private Calendar now;

    private int mWidth;
    private int mHeight;

    public RotateView(Context context) {
        this(context, null);
    }

    public RotateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmapBig = BitmapFactory.decodeResource(getResources(), R.drawable.rotate_01)
                .copy(Bitmap.Config.ARGB_8888, true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mPointX = mWidth / 2;
        mPointY = mHeight / 2;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        // 计算当前canvas 旋转的角度  减去阻尼系数
        mCanvasAngle += (currentAngle - beginAngle);
        if (mCanvasAngle < 0) {
            mCanvasAngle += 360;
        }
        if (mCanvasAngle > 360) {
            mCanvasAngle -= 360;
        }
        canvas.save();
        canvas.rotate(mCanvasAngle, mPointX, mPointY);
        Log.i(TAG, "mCanvasAngle:" + mCanvasAngle);

        canvas.drawBitmap(bitmapBig,
                mWidth / 2 - bitmapBig.getWidth() / 2,
                mHeight / 2 - bitmapBig.getHeight() / 2,
                null);
        canvas.restore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                now = Calendar.getInstance();
                beginTime = now.getTimeInMillis();
                beginAngle = computeCurrentAngle(e.getX(), e.getY());
                //如果点击触摸范围在圈外，则不处理
                if (getDistance(e.getX(), e.getY()) > bitmapBig.getWidth() / 2) {
                    isTouch = false;
                } else {
                    isTouch = true;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!isTouch) {
                    return true;
                }
                currentAngle = computeCurrentAngle(e.getX(), e.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (!isTouch) {
                    return true;
                }
                now = Calendar.getInstance();
                endTime = now.getTimeInMillis();
        }

        return false;
    }

    // 子控件位置改变重新计算角度
    private int computeCurrentAngle(float x, float y) {
        // 根据圆心坐标计算角度
        float distance = (float) Math
                .sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY)
                        * (y - mPointY)));
        int degree = (int) (Math.acos((x - mPointX) / distance) * 180 / Math.PI);
        if (y < mPointY) {
            degree = -degree;
        }
        if (degree < 0) {
            degree += 360;
        }

        return degree;
    }

    // 获取距离圆心的距离
    private float getDistance(float x, float y) {
        // 根据圆心坐标计算角度
        float distance = (float) Math
                .sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY)
                        * (y - mPointY)));
        return distance;
    }
}
