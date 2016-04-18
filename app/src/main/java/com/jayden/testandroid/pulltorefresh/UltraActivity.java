package com.jayden.testandroid.pulltorefresh;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;

import com.jayden.pulltorefresh.MVCHelper;
import com.jayden.pulltorefresh.MVCUltraHelper;
import com.jayden.testandroid.R;

import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class UltraActivity extends Activity {

    private MVCHelper<List<Book>> listMVCHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ultra);

        PtrClassicFrameLayout mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
//        MaterialHeader header = new MaterialHeader(getApplicationContext());
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1,-2));
//        header.setPadding(0,dipToPx(getApplicationContext(),15),0,dipToPx(getApplicationContext(),10));
//        header.setPtrFrameLayout(mPtrFrameLayout);
//        mPtrFrameLayout.setLoadingMinTime(800);
//        mPtrFrameLayout.setDurationToCloseHeader(800);
//        mPtrFrameLayout.setHeaderView(header);
//        mPtrFrameLayout.addPtrUIHandler(header);

        listMVCHelper = new MVCUltraHelper<List<Book>>(mPtrFrameLayout);
        //设置数据源
        listMVCHelper.setDataSource(new BooksDataSource());
        //设置适配器
        listMVCHelper.setAdapter(new BooksAdapter(this));

        listMVCHelper.setAutoLoadMore(false);
        //加载数据
        listMVCHelper.refresh();

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
