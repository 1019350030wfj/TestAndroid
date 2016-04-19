package com.jayden.jaydenrich.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 */
public class RecycleImageView extends ImageView {

    public RecycleImageView(Context context) {
        super(context);
    }

    public RecycleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecycleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}
