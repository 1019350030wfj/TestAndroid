package com.jayden.testandroid.api.xmlparser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.jayden.testandroid.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Jayden on 2016/8/29.
 */

public class ParserResultActivity extends Activity {
    private static List<Map<String, String>> views;
    private static Map<String, String> idNames;
    private static Map<String, String> idValues;

    public static void startResultActivity(Context context,
                                           List<Map<String, String>> views,
                                           Map<String, String> idNames,
                                           Map<String, String> idValues) {
        ParserResultActivity.views = views;
        ParserResultActivity.idNames = idNames;
        ParserResultActivity.idValues = idValues;

        Intent intent = new Intent(context,ParserResultActivity.class);
        context.startActivity(intent);
    }

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_parser_result);
        frameLayout = (FrameLayout)findViewById(R.id.container);

        addViews();
    }

    private void addViews() {
//        for (Map<String,String> view : views) {
//            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                    (int) ConvertUtils.dipToPx(this,Float.parseFloat(view.get("width"))),
//                    (int) ConvertUtils.dipToPx(this,Float.parseFloat(view.get("height"))));
//            layoutParams.leftMargin = (int) ConvertUtils.dipToPx(this,Float.parseFloat(view.get("X")));
//            layoutParams.topMargin = (int) ConvertUtils.dipToPx(this,Float.parseFloat(view.get("Y")));
//            EditText editText = new EditText(this);
//            editText.setTextColor(Color.parseColor(view.get("TextColor")));
//            editText.setTextSize(Float.parseFloat(view.get("name")));
//            editText.setText(idValues.get(view.get("id"))+"test");
//            editText.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//            frameLayout.addView(editText,layoutParams);
//        }
        for (Map<String,String> view : views) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    Integer.parseInt(view.get("width"))*3+20,Integer.parseInt(view.get("height"))*3+20);
            layoutParams.leftMargin = Integer.parseInt(view.get("X"))*3;
            layoutParams.topMargin = Integer.parseInt(view.get("Y"))*3;
            EditText editText = new EditText(this);
            editText.setTextColor(Color.parseColor(view.get("TextColor")));
            editText.setTextSize(Float.parseFloat(view.get("name")));
            editText.setText(idValues.get(view.get("id"))+"test");
            editText.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            editText.setPadding(10,10,10,10);
            frameLayout.addView(editText,layoutParams);
        }
    }
}
