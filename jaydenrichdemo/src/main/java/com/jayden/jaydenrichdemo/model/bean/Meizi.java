package com.jayden.jaydenrichdemo.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class Meizi {

    @SerializedName("used")
    public boolean used;

    @SerializedName("type")
    public String type;//干货类型，如Android，iOS，福利等

    @SerializedName("url")
    public String url;//链接地址

    @SerializedName("who")
    public String who;//作者

    @SerializedName("desc")
    public String desc;//干货内容的描述

    @SerializedName("createdAt")
    public Date createdAt;

    @SerializedName("updatedAt")
    public Date updatedAt;

    @SerializedName("publishedAt")
    public Date publishedAt;

    @SerializedName("_id")
    public String id;

    @SerializedName("source")
    public String source;
}
