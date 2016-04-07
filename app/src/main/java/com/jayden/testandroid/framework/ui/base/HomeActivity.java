package com.jayden.testandroid.framework.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/3/29.
 * Email : 1570713698@qq.com
 */
public class HomeActivity extends AbsActivity implements View.OnClickListener {

    private static final String SELECT_POS = "selected_pos";

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;

    // 我的
    private ImageView mIVPerson;
    private TextView mTVPerson;

    // 电商
    private ImageView mIVShop;
    private TextView mTVShop;

    // 颜值圈
    private ImageView mIVGroup;
    private TextView mTVGroup;

    // 首页
    private ImageView mIVIndex;
    private TextView mTVIndex;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_home);
        setFragmentViewId(R.id.content);
        mIVIndex = findImage(R.id.iv_index);
        mTVIndex = findText(R.id.tv_index);
        setOnClickListenerEvent(R.id.ll_index);
        mIVGroup = findImage(R.id.iv_group);
        mTVGroup = findText(R.id.tv_group);
        setOnClickListenerEvent(R.id.ll_group);
        mIVShop = findImage(R.id.iv_shop);
        mTVShop = findText(R.id.tv_shop);
        setOnClickListenerEvent(R.id.ll_shop);
        mIVPerson = findImage(R.id.iv_person);
        mTVPerson = findText(R.id.tv_person);
        setOnClickListenerEvent(R.id.ll_person);
    }

    private void setOnClickListenerEvent(int id) {
        findViewById(id).setOnClickListener(this);
    }

    private TextView findText(int id) {
        return (TextView) findViewById(id);
    }

    private ImageView findImage(int id) {
        return (ImageView) findViewById(id);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            fragment1 = (Fragment1) findFragmentByTag(Fragment1.class.getSimpleName());
            fragment2 = (Fragment2) findFragmentByTag(Fragment2.class.getSimpleName());
            fragment3 = (Fragment3) findFragmentByTag(Fragment3.class.getSimpleName());
            fragment4 = (Fragment4) findFragmentByTag(Fragment4.class.getSimpleName());
            selected_pos = savedInstanceState.getInt(SELECT_POS);
        }
        if (fragment1 == null) {
            fragment1 = new Fragment1();
        }
        if (fragment2 == null) {
            fragment2 = new Fragment2();
        }
        if (fragment3 == null) {
            fragment3 = new Fragment3();
        }
        if (fragment4 == null) {
            fragment4 = new Fragment4();
        }

        if (selected_pos == 0) {
            selected_pos = R.id.ll_index;
        }
        onClick(findViewById(selected_pos));
    }

    private int selected_pos = 0;

    @Override
    public void onClick(View view) {
        selected_pos = view.getId();
        chooseStatus(selected_pos);
    }

    private void chooseStatus(int id) {
        switch (id) {
            case R.id.ll_index:
                changeFragment(fragment1, Fragment1.class.getSimpleName());
                break;
            case R.id.ll_group:
                changeFragment(fragment2, Fragment2.class.getSimpleName());
                break;
            case R.id.ll_shop:
                changeFragment(fragment3, Fragment3.class.getSimpleName());
                break;
            case R.id.ll_person:
                changeFragment(fragment4, Fragment4.class.getSimpleName());
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(SELECT_POS, selected_pos);
    }
}
