package com.test.plugina;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jayden on 2016/6/16.
 * Email : 1570713698@qq.com
 */
public class Utils {

    /**
     * 计算 a+b
     *
     * @param context
     * @param a
     * @param b
     * @param name
     */
    public int printSum(Context context, int a, int b, String name){
        int sum = a + b;
        Toast.makeText(context, name+":"+sum, Toast.LENGTH_SHORT).show();
        return sum;
    }

    public void printFileName(Context context,String name){
        new FileUtils().print(context,name);
    }
}
