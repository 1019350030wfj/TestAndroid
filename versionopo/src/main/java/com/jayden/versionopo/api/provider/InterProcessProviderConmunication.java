package com.jayden.versionopo.api.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * 1. 在另一个进程编写自定义的contentProvider
 * 2. 在manifest中注册，且编写authorities也就是URI和exported="true"、android:permission=""
 * 3. 在进程2中的manifest要申明需要 和第2步骤中android:permission=""一致
 */
public class InterProcessProviderConmunication extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        setContentView(textView);
        textView.setText("Demo of Crossing process provider");


        /**
         * 对user表进行操作
         */
        //设置URI
        Uri uri = Uri.parse("content://cn.jayden.myprovider/user");

        //插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name", "Iverson");
        values.put("_id", 4);
        values.put("name", "yinping");

        //获取ContentResolver
        ContentResolver resolver = getContentResolver();
        //通过ContentResolver 根据 URI 向ContentProvider中插入数据
        resolver.insert(uri, values);

        //通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri, new String[]{"_id", "name"}, null, null, null);
        while (cursor.moveToNext()){
            //将表中数据全部输出
            System.out.println("query book:" + cursor.getInt(0) + " " + cursor.getString(1));
        }
        cursor.close();//关闭游标

        /**
         * 对job表进行操作
         */
        //和上述类似，只是URI需要修改， 从而匹配不同的URI_CODE，从而找到不同的数据源
        Uri uri_job = Uri.parse("content://cn.jayden.myprovider/job");

        //插入表中数据
        ContentValues values2 = new ContentValues();
        values2.put("_id", 3);
        values2.put("job", "NBA Player");
        values2.put("_id", 4);
        values2.put("job", "iOS");

        //获取ContentResolver
        ContentResolver resolver2 = getContentResolver();
        //通过ContentResolver 根据URI向ContentProvider中插入数据
        resolver2.insert(uri_job, values2);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor2 = resolver2.query(uri_job, new String[]{"_id","job"}, null, null, null);
        while (cursor2.moveToNext()){
            System.out.println("query job:" + cursor2.getInt(0) +" "+ cursor2.getString(1));
            // 将表中数据全部输出
        }
        cursor2.close();
        // 关闭游标
    }
}
