package com.jayden.testandroid.lollipop.animation;

import android.content.Context;

/**
 * 演员信息
 * Created by Jayden on 2016/5/12.
 * Email : 1570713698@qq.com
 */
public class Actor {
    String name;

    int picName;


    public Actor(String name, int picName)
    {
        this.name = name;
        this.picName = picName;
    }


    public int getImageResourceId( Context context, String picName)
    {
        try
        {
            return context.getResources().getIdentifier(picName, "drawable", context.getPackageName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }

}
