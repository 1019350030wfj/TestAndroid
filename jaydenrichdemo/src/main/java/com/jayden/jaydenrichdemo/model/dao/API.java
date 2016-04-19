package com.jayden.jaydenrichdemo.model.dao;

import java.net.URLEncoder;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class API {

    public static final String HOST = "http://gank.io/api/";

    public static final String FULI = "福利";

    /**
     * http://gank.io/api/data/数据类型/请求个数/第几页
     * @param size  请求个数
     * @param page  第几页
     * @return
     */
    public static String buildFuliUrl(int size ,int page) {
        return HOST + "data/"+ URLEncoder.encode(FULI) +"/" + size +"/" + page ;
    }
}
