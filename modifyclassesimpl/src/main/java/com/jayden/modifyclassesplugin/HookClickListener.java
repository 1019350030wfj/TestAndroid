package com.jayden.modifyclassesplugin;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Jayden on 2017/7/21.
 */

public class HookClickListener {

    public static void onClick(View v) {
        Toast.makeText(v.getContext(),"Hook Click Listener",Toast.LENGTH_LONG).show();
        Log.d("jayden","Hook Click Listener");
    }
}
