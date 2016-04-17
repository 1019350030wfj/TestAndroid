package com.jayden.testandroid.mpchart;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/4/16.
 * Email : 1570713698@qq.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpchart_main);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content,new BarCharFragment());
        fragmentTransaction.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        int id = item.getItemId();
        switch (id) {
            case R.id.BarChart://条状图
                BarCharFragment barCharFragment = new BarCharFragment();
                transaction.replace(R.id.content, barCharFragment);
                transaction.commit();
                return true;
            case R.id.LineChart://线形图
                LineCharFragment lineCharFragment = new LineCharFragment();
                transaction.replace(R.id.content, lineCharFragment);
                transaction.commit();
                return true;
            case R.id.RadarChart://雷达图
                RadarCharFragment radarCharFragment = new RadarCharFragment();
                transaction.replace(R.id.content, radarCharFragment);
                transaction.commit();
                return true;
            case R.id.PieChart://饼图
                PieCharFragment pieCharFragment = new PieCharFragment();
                transaction.replace(R.id.content, pieCharFragment);
                transaction.commit();
                return true;
            case R.id.ScatterChart://散列图
                ScatterChartFragment scatterChartFragment = new ScatterChartFragment();
                transaction.replace(R.id.content, scatterChartFragment);
                transaction.commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
