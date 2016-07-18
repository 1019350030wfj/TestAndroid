package com.test.plugina;

import android.app.Activity;
import android.content.res.Resources;

/**
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class BaseActivity extends Activity {

    @Override
    public Resources getResources() {
        return getApplication().getResources();
    }
}
