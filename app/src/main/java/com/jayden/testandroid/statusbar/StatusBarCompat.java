package com.jayden.testandroid.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jayden on 2016/3/13.
 * 判断如果是5.0以下的话， 就加入一个和状态同样高度的view
 */
public class StatusBarCompat {

    private static final int COLOR_DEFAULT = Color.parseColor("#20FF4081");
    private static final int INVALID_VAL = -1;

    public static void compat(Activity activity,int statusColor) {
        //如果是5.0及以上版本的话，直接通过 setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                activity.getWindow().setStatusBarColor(statusColor);
                return;
            }
        }

        //>= 4.4 && <= 5.0, 创建一个view， 然后加入到根root view （android.R.id.content）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View view = new View(activity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusHeight(activity));
            view.setBackgroundColor(color);
            contentView.addView(view,0,layoutParams);
        }
    }

    public static void compat(Activity activity)
    {
        compat(activity, INVALID_VAL);
    }

    public static int getStatusHeight(Context context) {
        int result = 0;

        int resourceID = context.getResources().getIdentifier("status_bar_height","dimen","android");

        if (resourceID > 0) {
            result = context.getResources().getDimensionPixelSize(resourceID);
        }

        return result;

    }
}
