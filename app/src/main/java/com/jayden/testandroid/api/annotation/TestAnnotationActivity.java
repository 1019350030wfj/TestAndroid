package com.jayden.testandroid.api.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/3/25.
 */

public class TestAnnotationActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        Person person = new Person();
        System.out.println("start " + " person.name = " + person.getName() + " person.Property="+person.getProperty());
        AnnoInjection.getBean(person);
        System.out.println("end " + " person.name = " + person.getName() + " person.Property="+person.getProperty());
    }
}
