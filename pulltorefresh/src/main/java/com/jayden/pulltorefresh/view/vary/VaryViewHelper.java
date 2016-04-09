package com.jayden.pulltorefresh.view.vary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 用于切换布局，用一个新的布局替换原来的布局
 * 替换不是覆盖
 * <p/>
 * Created by Jayden on 2016/4/9.
 * Email : 1570713698@qq.com
 */
public class VaryViewHelper implements IVaryViewHelper {

    private View view; //要被替换的内容布局
    private ViewGroup parentView;
    private int viewIndex;//记录被替换view的索引（位置），方便后面直接取出那个位置的view
    private ViewGroup.LayoutParams params;
    private View currentView;

    public VaryViewHelper(View view) {
        super();
        this.view = view;
    }

    private void init() {
        params = view.getLayoutParams();
        if (view.getParent() != null) {
            //如果不是根布局
            parentView = (ViewGroup) view.getParent();
        } else {
            //如果是根布局
            parentView = (ViewGroup) view.getRootView().findViewById(android.R.id.content);
        }
        int count = parentView.getChildCount();
        //找到要被替换view的位置
        for (int i = 0; i < count; i++) {
            if (view == parentView.getChildAt(i)) {
                viewIndex = i;
                break;
            }
        }
        //记录当前的view
        currentView = view;
    }

    @Override
    public View getCurrentLayout() {
        return currentView;
    }

    @Override
    public void restoreView() {
        showLayout(view);//恢复内容布局
    }

    @Override
    public void showLayout(View view) {
        if (parentView == null) {
            init();
        }
        this.currentView = view;
        //如果已经是那个View，那就不需要再进行替换操作
        if (parentView.getChildAt(viewIndex) != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                //要先把它从其它父布局中清除， 一个view只能被加入一个父view
                parent.removeView(view);
            }
            parentView.removeViewAt(viewIndex);
            parentView.addView(view,viewIndex,params);
        }
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(view.getContext()).inflate(layoutId, null);
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
