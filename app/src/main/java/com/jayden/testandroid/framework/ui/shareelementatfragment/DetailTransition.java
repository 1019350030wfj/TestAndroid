package com.jayden.testandroid.framework.ui.shareelementatfragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Created by Jayden on 2016/4/1.
 * Email : 1570713698@qq.com
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DetailTransition extends TransitionSet {

    public DetailTransition() {
        init();
    }

    // 允许资源文件使用
    public DetailTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).//改变目标视图的布局边界
                addTransition(new ChangeTransform()).//改变目标视图的缩放比例和旋转角度
                addTransition(new ChangeImageTransform());//改变目标图片的大小和缩放比例
    }
}
