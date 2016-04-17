package com.jayden.testandroid.pulltorefresh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jayden.pulltorefresh.IDataAdapter;
import com.jayden.pulltorefresh.ILoadViewFactory;
import com.jayden.pulltorefresh.MVCHelper;
import com.jayden.pulltorefresh.MVCUltraHelper;
import com.jayden.pulltorefresh.recyclerview.HFAdapter;
import com.jayden.pulltorefresh.recyclerview.HFRecyclerViewAdapter;
import com.jayden.pulltorefresh.viewhandle.RecyclerViewHandler;
import com.jayden.testandroid.R;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class UltraRecyclerViewActivity extends Activity {

    private MVCHelper<List<Book>> listMVCHelper;
    private TextView headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ultra_recyclerview);

        initHeaderView();

        PtrClassicFrameLayout mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);


//        MaterialHeader header = new MaterialHeader(getApplicationContext());
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1,-2));
//        header.setPadding(0,dipToPx(getApplicationContext(),15),0,dipToPx(getApplicationContext(),10));
//        header.setPtrFrameLayout(mPtrFrameLayout);


        StoreHouseHeader header = new StoreHouseHeader(getApplicationContext());
        header.setPadding(0, dipToPx(getApplicationContext(),20), 0, dipToPx(getApplicationContext(),20));
        header.initWithString("Ultra PTR");
        header.setTextColor(Color.RED);

        mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);

//        mPtrFrameLayout.setLoadingMinTime(800);
//        mPtrFrameLayout.setDurationToCloseHeader(800);
//        mPtrFrameLayout.setHeaderView(header);
//        mPtrFrameLayout.addPtrUIHandler(header);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listMVCHelper = new MVCUltraHelper<List<Book>>(mPtrFrameLayout);
        //设置数据源
        listMVCHelper.setDataSource(new BooksDataSource());
        //设置适配器
        listMVCHelper.setAdapter(new ReBooksAdapter(this),new RecyclerViewHandlerAddHeader());

        listMVCHelper.setAutoLoadMore(false);

        //加载数据
        listMVCHelper.refresh();

    }

    private void initHeaderView() {
        headerView = (TextView) LayoutInflater.from(this).inflate(com.jayden.pulltorefresh.R.layout.layout_listview_footer,null);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jayden","header View onclick");
            }
        });
    }

    private class RecyclerViewHandlerAddHeader extends RecyclerViewHandler {

        @Override
        public boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter, ILoadViewFactory.ILoadMoreView iloadMoreView, View.OnClickListener onClickListener) {
            final RecyclerView recyclerView = (RecyclerView) contentView;
            boolean hasInit = false;
            RecyclerView.Adapter<?> adapter2 = (RecyclerView.Adapter<?>) adapter;
            if (iloadMoreView != null) {
                final HFAdapter hfAdapter;
                if (adapter instanceof HFAdapter) {
                    hfAdapter = (HFAdapter) adapter;
                } else {
                    hfAdapter = new HFRecyclerViewAdapter(adapter2);
                }
                adapter2 = hfAdapter;
                hfAdapter.addHeader(headerView);
                hfAdapter.setOnItemClickListener(new HFAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(HFAdapter adapter, RecyclerView.ViewHolder vh, int postion) {
                        Toast.makeText(UltraRecyclerViewActivity.this,"pos = " + postion,Toast.LENGTH_SHORT).show();
                        Log.d("jayden","pos = " + postion);
                    }
                });
                final Context context = recyclerView.getContext().getApplicationContext();
                iloadMoreView.init(new ILoadViewFactory.FootViewAdder() {
                    @Override
                    public View addFootView(View view) {
                        hfAdapter.addFooter(view);
                        return view;
                    }

                    @Override
                    public View addFootView(int layoutId) {
                        View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
                        return addFootView(view);
                    }
                },onClickListener);
                hasInit = true;
            }
            recyclerView.setAdapter(adapter2);
            return hasInit;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        listMVCHelper.destroy();
    }

    /**
     * 根据dip值转化成px值
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, int dip) {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
        return size;
    }
}
