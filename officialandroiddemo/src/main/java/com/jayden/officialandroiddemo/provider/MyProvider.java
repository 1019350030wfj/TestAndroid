package com.jayden.officialandroiddemo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyProvider extends ContentProvider {

    private Context mContext;
    DBHelper mDBHelper = null;
    SQLiteDatabase db = null;

    public static final String AUTOHORITY = "cn.jayden.myprovider";

    public static final int USER_CODE = 1;
    public static final int JOB_CODE = 2;

    private static final UriMatcher mMatcher;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //初始化
        mMatcher.addURI(AUTOHORITY, "user", USER_CODE);
        mMatcher.addURI(AUTOHORITY, "job", JOB_CODE);
        // 若URI资源路径 = content://cn.jayden.myprovider/user, 则返回注册码 USER_CODE
        // 若URI资源路径 = content://cn.jayden.myprovider/job, 则返回注册码 JOB_CODE
    }

    // 以下是ContentProvider的6个方法

    /**
     * 初始化 ContentProvider
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        mContext = getContext();
        //在ContentProvider创建时对数据库进行初始化
        //运行在主线程，故不能做耗时操作，此处仅作展示
        mDBHelper = new DBHelper(getContext());
        db = mDBHelper.getWritableDatabase();

        //初始化两个表的数据（先清空两个表，再各加入一个记录）
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1, 'jayden');");
        db.execSQL("insert into user values(2, 'kobe');");

        db.execSQL("delete from job");
        db.execSQL("insert into job values(1, 'Android');");
        db.execSQL("insert into job values(2, 'iOS');");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //根据uri匹配URI_CODE ,从而匹配ContentProvider中相应的表名
//        该方法在最下面
        String table = getTableName(uri);

        //通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println("personid = " + personid);
        return db.query(table, projection, selection, selectionArgs, null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //根据URI匹配 URI_CODE,从而匹配contentProvider中相应的表名
        //该方法在最下面
        String table = getTableName(uri);

        //向该表添加数据
        db.insert(table, null, values);

        //当该URI的ContentProvider数据发生变化时， 通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);

        //通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println("personid = " + personid);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri){
        String tableName = null;
        switch (mMatcher.match(uri)){
            case USER_CODE:{
                tableName = DBHelper.USER_TABLE_NAME;
                break;
            }
            case JOB_CODE:{
                tableName = DBHelper.JOB_TABLE_NAME;
                break;
            }
        }
        return tableName;
    }
}
