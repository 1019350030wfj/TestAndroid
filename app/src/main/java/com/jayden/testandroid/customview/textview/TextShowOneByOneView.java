package com.jayden.testandroid.customview.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.jayden.testandroid.R;

import java.util.ArrayList;

/**
 * 文本逐字显示
 * Created by Jayden on 2016/9/10.
 */

public class TextShowOneByOneView extends View {

    private static final int DETAULT_SIZE = 4;
    private TextPaint textPaint;
    private float density;
    private String textContent;
    private int textColor;
    private String textAlignment;
    private float textSize;
    private float textSpacingAdd;
    private float textSpacingMult;

    public TextShowOneByOneView(Context context) {
        this(context, null, 0);
    }

    public TextShowOneByOneView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextShowOneByOneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextShowOneByOneView);
        textContent = a.getString(R.styleable.TextShowOneByOneView_textContent);
        textColor = a.getColor(R.styleable.TextShowOneByOneView_textColor, Color.BLACK);
        textAlignment = a.getString(R.styleable.TextShowOneByOneView_textXAlignment);
        textSize = a.getDimension(R.styleable.TextShowOneByOneView_textSize, DETAULT_SIZE);
        textSpacingAdd = a.getFloat(R.styleable.TextShowOneByOneView_textSpacingAdd, 0.0F);
        textSpacingMult = a.getFloat(R.styleable.TextShowOneByOneView_textSpacingMult, 1.0F);
        a.recycle();
        init();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextSpacingAdd(float textSpacingAdd) {
        this.textSpacingAdd = textSpacingAdd;
    }

    public void setTextSpacingMult(float textSpacingMult) {
        this.textSpacingMult = textSpacingMult;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextAlignment(String textAlignment) {
        this.textAlignment = textAlignment;
    }

    public void setTextContent(final String content) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                contents = ShowTextOneByOneUtils.getContentList(content);
                postInvalidate();
            }
        }.start();

    }

    private ArrayList<String> contents;

    private void init() {
        density = getResources().getDisplayMetrics().density;

        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawText(canvas);
    }

    private int cnt = 0;
    private String totalText = "";

    private String totalText10000 = "";//记录10000之前的文字
    private int index10000 = 0;

    private void drawText(Canvas canvas) {
        if (contents == null || cnt >= contents.size()) {
            return;
        }
        if ("100000".equals(contents.get(cnt)) && index10000<=5){
            if (index10000 == 0) {//第一次才赋值给
                totalText10000 = totalText;
            }
            index10000++;
            totalText =totalText10000+ (index10000*100000)+"/000000";
            if(index10000 == 6){
                cnt += 3;
            }
        } else {
            totalText += contents.get(cnt);
            cnt++;
        }

        Log.e("jayden","cnt = " + cnt);
        Log.e("jayden","totalText = " + totalText);
        StaticLayout layout = null;
        if (textAlignment.equals("normal")) {
            //textPaint(TextPaint 类型)设置了字符串格式及属性的画笔,240为设置画多宽后换行，后面的参数是对齐方式及行间距
            layout = new StaticLayout(totalText, textPaint, getWidth() - (int) (DETAULT_SIZE * density), Layout.Alignment.ALIGN_NORMAL, textSpacingMult, textSpacingAdd, true);
        } else if (textAlignment.equals("center")) {
            layout = new StaticLayout(totalText, textPaint, getWidth() - (int) (DETAULT_SIZE * density), Layout.Alignment.ALIGN_CENTER, textSpacingMult, textSpacingAdd, true);
        } else if (textAlignment.equals("opposite")) {
            layout = new StaticLayout(totalText, textPaint, getWidth() - (int) (DETAULT_SIZE * density), Layout.Alignment.ALIGN_OPPOSITE, textSpacingMult, textSpacingAdd, true);
        }

        //从 (10,10)的位置开始绘制
        canvas.translate(10 * density, 10 * density);

        layout.draw(canvas);

        startText();
    }

    public void startText() {
        if (cnt != contents.size()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, time);
        }
    }

    private long time = 200;

    public void setDelayPlayTime(long time) {
        this.time = time;
    }

}
