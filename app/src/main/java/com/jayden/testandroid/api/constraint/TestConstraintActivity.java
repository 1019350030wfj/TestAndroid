package com.jayden.testandroid.api.constraint;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/4/5.
 */

public class TestConstraintActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_constraint);
    }
}
