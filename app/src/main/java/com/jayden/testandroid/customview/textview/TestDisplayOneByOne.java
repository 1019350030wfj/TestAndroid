package com.jayden.testandroid.customview.textview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * 测试文字一个一个显示
 * Created by Jayden on 2016/9/10.
 */

public class TestDisplayOneByOne extends AppCompatActivity {

//    private TextView mTextView1;
//    private TextView mTextView2;
//    private TextView mTextView3;
//    private TextView mTextView4;
//    private TextView mTextView5;
//    private TextView mTextView6;
//    private TextView mTextView7;
//    private TextView mTextView8;

//    private TextDisplayOneByOneHelper mOneByOneHelper;

    private TextShowOneByOneView xtv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_display_onebyone);
        xtv = (TextShowOneByOneView) findViewById(R.id.xtv);
//        mTextView1 = (TextView) findViewById(R.id.textView1);
//        mTextView2 = (TextView) findViewById(R.id.textView2);
//        mTextView3 = (TextView) findViewById(R.id.textView3);
//        mTextView4 = (TextView) findViewById(R.id.textView4);
//        mTextView5 = (TextView) findViewById(R.id.textView5);
//        mTextView6 = (TextView) findViewById(R.id.textView6);
//        mTextView7 = (TextView) findViewById(R.id.textView7);
//        mTextView8 = (TextView) findViewById(R.id.textView8);

//        mOneByOneHelper = new TextDisplayOneByOneHelper();
    }

    public void show(View view) {
        String s = "http://www.jianshu.com/p/6d97b4a10b18\n" +
                "ubuntu12.04(64bit)编译android4.4源码、sdk及kernel\n" +
                "字数2619 阅读286 评论6 喜欢16\n" +
                "Get ready for the next version of Android! Test your apps on Nexus and other devices." +
                " Support new system behaviors to save power and memory. Extend your apps with multi-window UI," +
                " direct reply notifications and more.";
        xtv.setTextContent(getResources().getString(R.string.str_start_page1));
        xtv.setDelayPlayTime(200);
        xtv.setTextAlignment("normal");
//        mOneByOneHelper.setText(mTextView1,"RAM TEST :END");
//        mOneByOneHelper.setText(mTextView2,"ROM TEST :END [60W3C]");
//        mOneByOneHelper.setText(mTextView3,"DRAM ID :0004002C");
//        mOneByOneHelper.setText(mTextView4,"SRAM ID :FF030000");
//        mOneByOneHelper.setText(mTextView5,"FROM ID :FFE30000");
//        mOneByOneHelper.setText(mTextView6,"*** MESSAGE ***");
//        mOneByOneHelper.setText(mTextView7,"LOADING CNC DATA-1          600000/000000");
//        mOneByOneHelper.setText(mTextView8,"END");
    }

    public void hide(View view) {
//        mTextView1.setText("");
//        mTextView2.setText("");
//        mTextView3.setText("");
//        mTextView4.setText("");
//        mTextView5.setText("");
//        mTextView6.setText("");
//        mTextView7.setText("");
//        mTextView8.setText("");
    }
}
