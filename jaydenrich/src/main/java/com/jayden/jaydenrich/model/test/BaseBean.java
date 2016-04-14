package com.jayden.jaydenrich.model.test;

import com.google.gson.annotations.SerializedName;

/**
 * json格式，基础数据格式
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class BaseBean<T> {
    /**
     * 提示信息
     */
    @SerializedName("info")
    public String info;

    @SerializedName("status")
    public String code;

    public T data;

    @SerializedName("error")
    public String error;
}
