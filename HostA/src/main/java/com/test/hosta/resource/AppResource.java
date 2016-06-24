package com.test.hosta.resource;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 整个AppResource的托管
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class AppResource extends Resources {

    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     * @param assets  Previously created AssetManager.
     * @param metrics Current display metrics to consider when
     *                selecting/computing resource values.
     * @param config  Desired device configuration to consider when
     */
    public AppResource(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }
}
