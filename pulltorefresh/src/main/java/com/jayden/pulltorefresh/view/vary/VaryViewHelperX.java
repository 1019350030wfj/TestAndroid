package com.jayden.pulltorefresh.view.vary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 用一个新布局覆盖在元布局上面
 * 覆盖不是替换
 * <p/>
 * Created by Jayden on 2016/4/9.
 * Email : 1570713698@qq.com
 */
public class VaryViewHelperX implements IVaryViewHelper {

    private IVaryViewHelper helper;
    private View view;

    public VaryViewHelperX(View view) {
        super();
        this.view = view;
        ViewGroup group = (ViewGroup) view.getParent();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        group.removeView(view); //删除原来的view
        group.addView(frameLayout, params); //然后在原来的位置，添加framelayout

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View floatView = new View(view.getContext());//这个View就是盖在原来布局上面的新布局
        frameLayout.addView(view, layoutParams);//先将原来的布局添加到frame布局
        frameLayout.addView(floatView, layoutParams); //然后将要显示的加载中的布局添加到frame布局，就能够盖住
        helper = new VaryViewHelper(floatView); //接下来显示加载中、失败等布局，就跟替换原来布局一样
    }

    @Override
    public View getCurrentLayout() {
        return helper.getCurrentLayout();
    }

    @Override
    public void restoreView() {
        helper.restoreView();
    }

    @Override
    public void showLayout(View view) {
        helper.showLayout(view);
    }

    @Override
    public void showLayout(int layoutId) {
        showLayout(inflate(layoutId));
    }

    @Override
    public View inflate(int layoutId) {
        return helper.inflate(layoutId);
    }

    @Override
    public Context getContext() {
        return helper.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
