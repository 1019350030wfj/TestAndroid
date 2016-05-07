package com.jayden.testandroid.customview.canvas_paint_path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jayden on 2016/4/25.
 * Email : 1570713698@qq.com
 */
public class SaveAndRestore extends View {

    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    public SaveAndRestore(Context context) {
        this(context, null);
    }

    public SaveAndRestore(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaveAndRestore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mPaint);

        //画刻度盘
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2 + 10, mWidth / 2, mHeight / 2 - mWidth / 2 + 60, mPaint);

        //写数字
        mPaint.setTextSize(30.0f);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawText("12",mWidth / 2 - 20, mHeight / 2 - mWidth / 2 + 100,mPaint);

        canvas.save();

        mPaint.setColor(Color.BLUE);
        for (int i = 1; i < 12; i++) {
            canvas.rotate(30, mWidth / 2, mHeight / 2);
            canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2 + 10, mWidth / 2, mHeight / 2 - mWidth / 2 + 60, mPaint);
            canvas.drawText(String.valueOf(i), mWidth / 2 - 20, mHeight / 2 - mWidth / 2 + 100, mPaint);
        }

        canvas.restore();

        mPaint.setColor(Color.RED);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2 + 150, mHeight / 2, mPaint);
    }
}
