package com.jayden.testandroid.fenghuangchuangyi;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Jayden on 2017/3/2.
 */

public class TestOnesoftNavActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Onesoft3DNavigationLayout layout = new Onesoft3DNavigationLayout(this);
        layout.setIOnesoft3dNav(new Onesoft3DNavigationLayout.IOnesoft3dNav() {
            @Override
            public void onAction(int action, Object object) {
                if (action == 0){

                }
            }
        });
        setContentView(layout);
    }
}
