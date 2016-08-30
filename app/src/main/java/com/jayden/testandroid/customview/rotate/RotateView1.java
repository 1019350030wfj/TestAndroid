package com.jayden.testandroid.customview.rotate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jayden.testandroid.R;

import java.util.Calendar;

/**
 * Created by Jayden on 2016/8/10.
 */

public class RotateView1 extends View {

    private Paint mPaint = new Paint();

    private Bitmap bitmaplittele;//中间不动的图片
    private Bitmap bitmapBig;//随手指转动的图片
    private Bitmap bitmapOut;//外围不动的图片
    // 圆心坐标
    private float mPointX = 0, mPointY = 0;

    private int flag = 0;
    // 半径
    private int mRadius = 0;
    // 旋转角度
    private int mAngle = 0;
    private int beginAngle = 0, currentAngle = 0;
    private String TAG = "NewView";
    int bitMap[] = { R.drawable.welcome_logo_qq, R.drawable.welcome_logo_wechat, R.drawable.welcome_logo_weibo };
    int imageIndex = 0;
    boolean isUp = false,isTouch=false;
    Context mContext;
    RotateViewListener listener;
    long beginTime,endTime;
    private Calendar now;

    public RotateView1(Context context, int px, int py, int radius, RotateViewListener listener) {
        super(context);
        mContext = context;
        this.listener = listener;
        mPointX = px;
        mPointY = py;
        mRadius = radius;
        bitmaplittele = BitmapFactory.decodeResource(getResources(),
                R.drawable.landscape_player_btn_pre_normal).copy(Bitmap.Config.ARGB_8888, true);
        bitmapBig = BitmapFactory.decodeResource(getResources(), bitMap[0])
                .copy(Bitmap.Config.ARGB_8888, true);
        bitmapOut = BitmapFactory.decodeResource(getResources(),
                R.drawable.landscape_player_btn_next_normal).copy(Bitmap.Config.ARGB_8888, true);
        setBackgroundResource(R.drawable.welcome_shield);
        Log.e(TAG, "RotateViewBegin");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                now = Calendar.getInstance();
                beginTime = now.getTimeInMillis();
                beginAngle = computeCurrentAngle(e.getX(), e.getY());
                isUp = false;
                //如果点击触摸范围在圈外，则不处理
                if (getDistance(e.getX(), e.getY())>bitmapOut.getWidth()/2) {
                    isTouch=false;
                }else {
                    isTouch=true;
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
                isUp = true;
                if (!isTouch) {
                    return true;
                }
                now = Calendar.getInstance();
                endTime = now.getTimeInMillis();
                if (SetClick(e.getX(), e.getY())) {
                    return true;
                }
                if (mAngle > 0) {
                    int count = mAngle / 120 + (mAngle % 120 > 60 ? 1 : 0);
                    imageIndex = (imageIndex + count) % 3;
                } else if (mAngle < 0) {
                    mAngle = -mAngle;
                    int count = mAngle / 120 + (mAngle % 120 > 60 ? 1 : 0);
                    imageIndex = (imageIndex + 3 - count) % 3;
                }
                bitmapBig = BitmapFactory.decodeResource(getResources(),
                        bitMap[imageIndex]).copy(Bitmap.Config.ARGB_8888, true);
                bitmapBig = adjustPhotoRotation(bitmapBig, imageIndex * 120);
                invalidate();
                if (mAngle >= 60) {
                    listener.onModChange(imageIndex);
                }
                return true;
        }

        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        // Log.i(TAG, "onDraw");
        // 大圆
        drawInCenter(canvas, bitmapOut, mPointX, mPointY, TAG);
        // 外圈
        if (isUp) {
            mAngle = 0;
        } else {
            mAngle = currentAngle - beginAngle;
        }

        Bitmap tempBig = adjustPhotoRotation(bitmapBig, mAngle);
        // Log.i(TAG, "mAngle:"+mAngle);
        drawInCenter(canvas, tempBig, mPointX, mPointY + 10, TAG);
        // 小圆(中间的圆心)
        drawInCenter(canvas, bitmaplittele, mPointX, mPointY - 10, TAG);
    }

    Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
        if (orientationDegree == 0) {
            return bm;
        }
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2,
                (float) bm.getHeight() / 2);

        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), m, true);

            return bm1;

        } catch (OutOfMemoryError ex) {
        }

        return null;

    }

    private void drawInCenter(Canvas canvas, Bitmap bitmap, float left,
                              float top, String text) {
        canvas.drawBitmap(bitmap, left - bitmap.getWidth() / 2,
                top - bitmap.getHeight() / 2, null);
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

        // Log.i("RoundSpinView", "x:" + x + ",y:" + y + ",degree:" + degree);
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

    //点击
    private boolean SetClick(float x, float y) {
        float distance = getDistance(x, y);
        if (mAngle>10||mAngle<-10) {
            return false;
        }else if(endTime-beginTime>1000){
            return false;
        }
        if (distance < bitmapBig.getWidth() / 2) {
            int mod = 0;

            if (beginAngle < 90 || 330 < beginAngle) {
                mod = (imageIndex+3-1)%3;
            }
            else if (90 < beginAngle && 210 > beginAngle) {
                mod = (imageIndex+3-2)%3;
            }
            else{
                mod = imageIndex;
            }
            //回调到主界面进行处理。
            listener.onModClick(mod);
        }
        return true;
    }

    public interface RotateViewListener {
        void onModClick(int mode);
        void onModChange(int mode);
    }


    public RotateView1(Context context) {
        super(context);
    }

    public RotateView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
