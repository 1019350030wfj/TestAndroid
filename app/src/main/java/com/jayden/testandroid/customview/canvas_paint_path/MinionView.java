package com.jayden.testandroid.customview.canvas_paint_path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * http://www.tuicool.com/articles/EBfu2m2
 *
 * Created by Jayden on 2016/4/25.
 * Email : 1570713698@qq.com
 */
public class MinionView extends View {

    private static final int DEFAULT_SIZE = 200; //View默认大小
    private int widthForUnspecified;
    private int heightForUnspecified;

    private Paint mPaint;
    private float bodyWidth;
    private float bodyHeight;

    private float mStrokeWidth = 4;//描边宽度
    private float offset;//计算时，部分需要 考虑找边偏移
    private float radius;//身体上下半圆的半径
    private int colorClothes = Color.rgb(32, 116, 160);//衣服的颜色
    private int colorBody = Color.rgb(249, 217, 70);//衣服的颜色
    private int colorStroke = Color.BLACK;
    private RectF bodyRect;
    private float handsHeight;//计算出吊带的高度时，可以用来做手的高度
    private float footHeigh;//脚的高度，用来画脚部阴影时用


    private static final float BODY_SCALE = 0.6f;//身体主干占整个view的比重
    private static final float BODY_WIDTH_HEIGHT_SCALE = 0.6f; //        身体的比例设定为 w:h = 3:5

    public MinionView(Context context) {
        this(context, null);
    }

    public MinionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MinionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initParams();
    }

    private void initParams() {
        bodyWidth = Math.min(getWidth(),getHeight() * BODY_WIDTH_HEIGHT_SCALE) * BODY_SCALE;
        bodyHeight = Math.min(getWidth(),getHeight() *BODY_WIDTH_HEIGHT_SCALE) / BODY_WIDTH_HEIGHT_SCALE * BODY_SCALE;

        mStrokeWidth = Math.max(bodyWidth / 50,mStrokeWidth);
        offset = mStrokeWidth / 2;

        bodyRect = new RectF();
        bodyRect.top = getHeight() / 2 - bodyHeight / 2;
        bodyRect.left = (getWidth() - bodyWidth) / 2;
        bodyRect.right = bodyRect.left + bodyWidth;
        bodyRect.bottom = bodyRect.top + bodyHeight;

        radius = bodyWidth / 2;
        footHeigh = radius * 0.4333f;

        handsHeight = (getHeight() + bodyHeight) / 2 + offset - radius * 1.65f;
    }

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        } else {
            mPaint.reset();
        }
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec,true),measure(heightMeasureSpec,false));
    }

    private int measure(int measureSpec,boolean isWidth) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        int specMode = MeasureSpec.getMode(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST: {
                result = specSize;
                if (isWidth) {
                    widthForUnspecified = result;
                } else {
                    heightForUnspecified = result;
                }
                break;
            }
            case MeasureSpec.UNSPECIFIED:
            default: {
                //宽或高未指定的情况下，可以由另一端推算出来 - -如果两边都没指定就用默认值
                if (isWidth) {
                    result = (int) (heightForUnspecified * BODY_WIDTH_HEIGHT_SCALE);
                } else {
                    result = (int) (widthForUnspecified / BODY_WIDTH_HEIGHT_SCALE);
                }
                if (result == 0) {
                    result = DEFAULT_SIZE;
                }
            }

        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBody(canvas);
        drawBodyStroke(canvas);
        drawClothes(canvas);
    }

    //1、画身体
    private void drawBody(Canvas canvas) {
        initPaint();
        mPaint.setColor(colorBody);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawRoundRect(bodyRect,radius,radius,mPaint);
    }

    //2、画边框
    private void drawBodyStroke(Canvas canvas) {
        initPaint();
        mPaint.setColor(colorStroke);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);

        canvas.drawRoundRect(bodyRect,radius,radius,mPaint);
    }

    //3、画衣服（a、裤子）
    private void drawClothes(Canvas canvas) {
        initPaint();

        RectF rect = new RectF();
        rect.left = (getWidth() - bodyWidth) / 2 + offset;
        rect.top = (getHeight() + bodyHeight) / 2 - radius * 2 + offset;
        rect.right = rect.left + bodyWidth - offset * 2;
        rect.bottom = rect.top + radius * 2 - offset * 2;

        mPaint.setColor(colorClothes);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rect,0,180,true,mPaint);//半圆

        int h = (int) (radius * 0.5);
        int w = (int) (radius * 0.3);

        rect.left += w;
        rect.top = rect.top + radius - h;
        rect.right -= w;
        rect.bottom = rect.top + h;
        canvas.drawRect(rect,mPaint);//长方形

        //画线
        initPaint();
        mPaint.setColor(colorStroke);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);
        float[] pts = new float[20];//5条线

        pts[0] = rect.left - w;//startX
        pts[1] = rect.top + h;//startY
        pts[2] = pts[0] + w;//stopX
        pts[3] = pts[1];//stopY

        pts[4] = pts[2];
        pts[5] = pts[3] + offset;
        pts[6] = pts[4];
        pts[7] = pts[3] - h;

        pts[8] = pts[6] - offset;
        pts[9] = pts[7];
        pts[10] = pts[8] + (radius - w) * 2;
        pts[11] = pts[9];

        pts[12] = pts[10];
        pts[13] = pts[11] - offset;
        pts[14] = pts[12];
        pts[15] = pts[13] + h;

        pts[16] = pts[14] - offset;
        pts[17] = pts[15];
        pts[18] = pts[16] + w;
        pts[19] = pts[17];
        canvas.drawLines(pts, mPaint);

        //画左吊带
        initPaint();
        mPaint.setColor(colorClothes);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.moveTo(rect.left - w - offset,handsHeight);
        path.lineTo(rect.left + h / 4, rect.top + h / 2);
        final float smallW = w / 2 * (float) Math.sin(Math.PI / 4);
        path.lineTo(rect.left + h / 4 + smallW, rect.top + h / 2 - smallW);
        final float smallW2 = w / (float) Math.sin(Math.PI / 4) / 2;
        path.lineTo(rect.left - w - offset, handsHeight - smallW2);

        canvas.drawPath(path, mPaint);
    }
}
