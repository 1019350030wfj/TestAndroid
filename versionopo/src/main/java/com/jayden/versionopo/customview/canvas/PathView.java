package com.jayden.versionopo.customview.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jayden on 2017/5/5.
 */

public class PathView extends View {

    private Path mPath;
    private Paint mPaint;

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth() / 2, getHeight() / 2);
        mPath.addRect(-200, -200, 200, 200, Path.Direction.CCW);
        mPath.setLastPoint(-300,300);
        canvas.drawPath(mPath,mPaint);
    }
}
