package com.jayden.jaydenrichdemo.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class BaseBean<T> {

    @SerializedName("error")
    public boolean error;

    @SerializedName("results")
    public T results;
}
