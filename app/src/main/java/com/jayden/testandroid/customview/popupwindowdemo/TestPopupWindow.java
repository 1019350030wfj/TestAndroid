package com.jayden.testandroid.customview.popupwindowdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.jayden.testandroid.R;

/**
 * 和dialog的区别：
 * 1、显示的位置： dialog显示的位置是固定； 而popupwindow是不固定的
 *
 * 2、阻塞UI线程: dialog不阻塞UI线程， 而popupwindow是阻塞主线程的
 *
 *
 * Created by Jayden on 2016/6/13.
 * Email : 1570713698@qq.com
 */
public class TestPopupWindow extends AppCompatActivity{

    private Button mBtnShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_popupwindow);
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {
        PopupWindow popupWindow = new PopupWindow(this);

        //如果要显示就必须设置width height contentView
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.popup_test,null));

        //响应点击事件
        /*============================= 返回按钮 关闭弹窗
                                        点击外部区域，关闭弹窗

        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        =================================*/

         /*============================= 返回按钮 关闭弹窗
                                        点击外部区域，关闭弹窗

        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        =================================*/

        /*============================= 返回按钮 退出当前activity
                                        点击外部区域，不会关闭弹窗

        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        =================================*/

        /*============================= 返回按钮 退出当前activity
                                        点击外部区域，关闭弹窗

        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        =================================*/

        /*============================= 返回按钮 没有反应
                                        点击外部区域，没有反应

        popupWindow.setBackgroundDrawable(null);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        =================================*/

        /*============================= 返回按钮 退出当前activity
                                        点击外部区域，没有反应

        popupWindow.setBackgroundDrawable(null);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        =================================*/

        /*============================= 返回按钮 退出当前activity
                                        点击外部区域，没有反应

        popupWindow.setBackgroundDrawable(null);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);

        =================================*/

        /*============================= 返回按钮 没有反应
                                        点击外部区域，没有反应
        =================================*/
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);



        popupWindow.showAsDropDown(mBtnShow);
    }
}
