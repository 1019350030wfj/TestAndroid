package com.jayden.testandroid.customview.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jayden.testandroid.BaseRecyclerAdapter;
import com.jayden.testandroid.R;
import com.jayden.testandroid.statusbar.StatusBarCompat;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/3/9.
 */
public class TestRecyclerViewActivity extends Activity {

    private XRecyclerView mRecyclerView;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_test_recyclerview);
        StatusBarCompat.compat(this);
        mRecyclerView = (XRecyclerView) findViewById(R.id.rv_test);

        //setLayoutManager
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        //add Header
        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header,(ViewGroup) findViewById(android.R.id.content),false);
        mRecyclerView.addHeaderView(header);
//        View header1 = LayoutInflater.from(this).inflate(R.layout.recyclerview_header1,(ViewGroup) findViewById(android.R.id.content),false);
//        mRecyclerView.addHeaderView(header1);


        //set pull refresh unable
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallScale);

        //setAdapter
        mDatas = new ArrayList<String>();
        for(int i = 0; i < 15 ;i++){
            mDatas.add("item" + (i + mDatas.size()) );
        }
        final TestRecyclerAdapter adapter = new TestRecyclerAdapter();
        adapter.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<String>() {
            @Override
            public void onItemClicked(View item, String data, int position) {
                Log.d("jayden","Position = " + position);
            }
        });
        adapter.setData(mDatas);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData("add item");
            }
        });
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }


}
