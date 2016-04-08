package com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.outlayer.loginlayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/4/8.
 */
public class RegisterFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_register, null);
        return view;
    }

}
