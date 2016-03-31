package com.jayden.testandroid.framework.ui.appbar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/3/31.
 * Email : 1570713698@qq.com
 */
public class SimpleFragment extends Fragment {

    private static final String ARG_SELECTION_NUM = "arg_selection_num";

    private static final int[] TEXTS = {R.string.tiffany_text, R.string.taeyeon_text, R.string.yoona_text};

    private TextView mTextView;

    public static SimpleFragment newInstance(int selectNum) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SELECTION_NUM,selectNum);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.d("jayden","attach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("jayden","onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("jayden","onCreateView");
        View view = inflater.inflate(R.layout.fragment_main_appbar,container,false);
        mTextView = (TextView) view.findViewById(R.id.main_tv_text);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView.setText(TEXTS[getArguments().getInt(ARG_SELECTION_NUM)]);
    }
}
