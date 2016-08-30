package com.jayden.testandroid.customview.circlemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jayden on 2016/8/15.
 */

public class SuperCircleView extends View {

    public SuperCircleView(Context context) {
        this(context,null);
    }

    public SuperCircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SuperCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
}
