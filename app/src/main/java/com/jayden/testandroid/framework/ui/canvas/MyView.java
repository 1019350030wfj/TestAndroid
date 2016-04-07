package com.jayden.testandroid.framework.ui.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/3/29.
 * Email : 1570713698@qq.com
 */
public class MyView extends View {

    private Paint mPaint;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(25.0f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("自定义view，canvas对象已经存在！ 画家已经在了， 现在需要画笔和画布",30,30,mPaint);

        canvas.drawRect(getWidth() / 2 - 10,getHeight() / 2 - 10,getWidth() / 2 + 10,getHeight() / 2 + 10,mPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_beauty_diary);

        canvas.drawBitmap(bitmap,getWidth()/2 , getHeight() / 2 + 30,mPaint);
    }
}
