package com.jayden.testandroid.framework.ui.appbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/3/31.
 * Email : 1570713698@qq.com
 * 使用toolbar要设置这个activity的主题为没有actionBar
 */
public class MainAppBarActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ImageView mIVOutGoing;
    private ImageView mIVTarget;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tl_tab);
        mViewPager = (ViewPager) findViewById(R.id.main_vp_container);
        mIVOutGoing = (ImageView) findViewById(R.id.toolbar_tv_outgoing);
        mIVTarget = (ImageView) findViewById(R.id.toolbar_iv_target);

        setSupportActionBar(mToolbar);
        setTitle("Jayden");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SimpleAdapter adapter = new SimpleAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(PagerChangeListener.newInstance(adapter, mIVTarget, mIVOutGoing));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
