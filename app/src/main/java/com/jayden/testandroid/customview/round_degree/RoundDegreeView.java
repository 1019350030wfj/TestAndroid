package com.jayden.testandroid.customview.round_degree;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by Jayden on 2016/3/20.
 */
public class RoundDegreeView extends View {

    private Paint mArcPaint; //画最外面的圆弧
    private int mWidth;
    private int mHeight;

    public RoundDegreeView(Context context) {
        this(context, null);
    }

    public RoundDegreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundDegreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private float mMinSize;
    private int mStrokeWidth;
    private SweepGradient mGradient;
    private float mArcCenterX;
    private float mArcCenterY;
    private float mArcRadius;
    private RectF mArcRectF;

    private Paint mTextPaint; //画文字的笔

    private Paint mInnerArcPaint; //内圆弧
    private float mInnerArcRadius;
    private int mInnerStrokeWidth = 5;
    private RectF mInnerRect ;

    private Path arrow; //通过路径画三角形
    private Paint mArrowPaint;

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setDither(true);
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND); //笔触风格
        mArcPaint.setPathEffect(new CornerPathEffect(10));

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(30);

        mInnerArcPaint = new Paint();
        mInnerArcPaint.setAntiAlias(true);
        mInnerArcPaint.setColor(Color.RED);
        mInnerArcPaint.setStyle(Paint.Style.STROKE);
        mInnerArcPaint.setStrokeWidth(mInnerStrokeWidth);
        mInnerArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mInnerArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerArcPaint.setPathEffect(new CornerPathEffect(10));
        mInnerArcPaint.setDither(true);

        mArrowPaint = new Paint();
        mArrowPaint.setStyle(Paint.Style.FILL);
        mArrowPaint.setColor(Color.WHITE);

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;
        mMinSize = mWidth / 600f;
        mStrokeWidth = (int) (mMinSize * 20);

        mArcCenterX = mWidth / 2;
        mArcCenterY = mHeight / 2;
        mArcRadius = 250 * mMinSize;
        mInnerArcRadius = 190 * mMinSize;
        mArcRectF = new RectF();
        mArcRectF.left = mArcCenterX - mArcRadius;
        mArcRectF.right = mArcCenterX + mArcRadius;
        mArcRectF.top = mArcCenterY - mArcRadius;
        mArcRectF.bottom = mArcCenterY + mArcRadius;
        int[] colors = {0xFFff0000, 0xFF00ff00, 0xFF0000ff};
        mGradient = new SweepGradient(mArcCenterX,mArcCenterY,colors,null);

        mInnerRect = new RectF();

        mInnerRect.left = mArcCenterX - mInnerArcRadius;
        mInnerRect.top = mArcCenterY - mInnerArcRadius;
        mInnerRect.right = mArcCenterX + mInnerArcRadius;
        mInnerRect.bottom = mArcCenterY + mInnerArcRadius;


        arrow = new Path();
        arrow.moveTo(mArcCenterX - mInnerArcRadius + 3,mArcCenterY);
        arrow.lineTo(mArcCenterX - mInnerArcRadius + 24, mArcCenterY + 20);
        arrow.lineTo(mArcCenterX - mInnerArcRadius + 24,mArcCenterY - 20);
        arrow.close();
    }

    private float arrowRotateDegree = -30 + 3;

    @Override
    protected void onDraw(Canvas canvas) {
        mArcPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setShader(mGradient);
        canvas.drawArc(mArcRectF, -210, 240, false, mArcPaint);

        //对画布进行旋转，先保存
        canvas.save();
        canvas.rotate(-120, mArcCenterX, mArcCenterY);
        for (int i=0; i< 16; i++) {
            canvas.drawText(String.valueOf(i)+"K",mArcCenterX,mArcCenterY - mArcRadius + 50,mTextPaint);
            canvas.rotate(15.3f,mArcCenterX,mArcCenterY);
        }
        canvas.restore();

        canvas.drawArc(mInnerRect,-210,240,false,mInnerArcPaint);

        //rotate 是对坐标的旋转
        canvas.rotate(arrowRotateDegree,mArcCenterX,mArcCenterY);
        canvas.drawPath(arrow,mArrowPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                animatorStart();
                break;
            }
        }

        return true;
    }

    private void animatorStart() {
        Random random = new Random();
        int number = Math.abs(random.nextInt()) % 15;
        moveTo(number * 15.3f - 30 + 4);
    }

    private void moveTo(float toDegree) {
        float currentDegree = arrowRotateDegree;
        ValueAnimator animator = ValueAnimator.ofFloat(currentDegree,toDegree);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arrowRotateDegree = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(1500).start();
    }
}
