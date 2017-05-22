package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.versionopo.R;

/**
 * Created by Jayden on 2017/4/20.
 */

public class RealActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
    }
}
