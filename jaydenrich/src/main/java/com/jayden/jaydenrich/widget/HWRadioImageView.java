package com.jayden.jaydenrich.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.jayden.jaydenrich.R;

/**
 * Created by Jayden on 2016/03/12.
 * Email : 1570713698@qq.com
 */
public class HWRadioImageView extends RecycleImageView {

    private float radio;

    public HWRadioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public HWRadioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HWRadioImageView(Context context) {
        this(context, null);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HWRadioImageView);
        radio = a.getFloat(R.styleable.HWRadioImageView_hwRadio, 1);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * radio);
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setRadio(float radio) {
        this.radio = radio;
        requestLayout();
    }

}
