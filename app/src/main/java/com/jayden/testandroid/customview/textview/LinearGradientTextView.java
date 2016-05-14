package com.jayden.testandroid.customview.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Jayden on 2016/5/10.
 * Email : 1570713698@qq.com
 */
public class LinearGradientTextView extends TextView {

    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private int mTranslate;

    public LinearGradientTextView(Context context) {
        super(context);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mLinearGradient = new LinearGradient(0,0,mViewWidth,0,
                        new int[]{Color.BLUE,Color.GRAY,Color.RED,Color.GREEN},null, Shader.TileMode.MIRROR);
                getPaint().setShader(mLinearGradient);
                mMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate -= mViewWidth;
            }
            mMatrix.setTranslate(mTranslate,0);
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100);
        }
    }
}
