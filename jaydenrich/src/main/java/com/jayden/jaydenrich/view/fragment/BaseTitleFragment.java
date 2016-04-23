package com.jayden.jaydenrich.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.jayden.jaydenrich.R;

/**
 * Created by Jayden on 2016/4/18.
 * Email : 1570713698@qq.com
 */
public abstract class BaseTitleFragment extends Fragment {

    private LinearLayout rootView;
    private View mContentView;
    private TitleBar titleBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = new LinearLayout(getActivity());
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setBackgroundColor(getResources().getColor(android.R.color.white));

        //标题栏
        int headLayoutId = onCreateTitleBar();
        if (headLayoutId > 0) {
            View headView = inflater.inflate(headLayoutId,rootView,false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rootView.addView(headView,params);
            titleBar = new TitleBar(headView);
            initTitleBar(titleBar);
        }

        //内容
        mContentView = inflater.inflate(getContentLayoutId(),null);
        if (mContentView != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mContentView.setLayoutParams(params);
            attachToRoot(mContentView);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView(mContentView);
        initListener();
        initData();
    }

    /* ===============================  销毁释放资源  ================================ */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(rootView);
        if(rootView.getParent()!=null){
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        rootView = null;
        titleBar = null;
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
    /* ===============================  销毁释放资源  ================================ */

    /* ===============================  内容区域  ================================ */
     protected abstract int getContentLayoutId();

    /**
     * 添加子View到此Fragment的顶层容器rootView
     * @param view
     */
    protected void attachToRoot(View view) {
        if (view == null) {
            return;
        }
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        rootView.addView(view);
    }

    public void initView(View view) {

    }

    public void initListener() {

    }

    public void initData(){

    }

     /* ===============================  内容区域  ================================ */

    /* ===============================  标题栏  ================================ */
    /**
     * 获取titleBar的布局
     * @return
     */
    public int onCreateTitleBar() {
        return R.layout.layout_base_header;
    }

    /**
     * 设置titleBar的标题，返回的图标等等
     * @param titleBar
     */
    public void initTitleBar(TitleBar titleBar){
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }

    public static class TitleBar {

        public View headView;
        public View title;
        public View leftPart;
        public View rightPart;

        public TitleBar(View headView){
            this.headView = headView;
            this.title = headView.findViewById(R.id.head_title);
            this.leftPart = headView.findViewById(R.id.head_left);
            this.rightPart = headView.findViewById(R.id.head_right);
        }
    }

    /* ===============================  标题栏  ================================ */
}
