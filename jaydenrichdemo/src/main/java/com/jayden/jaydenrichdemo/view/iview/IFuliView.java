package com.jayden.jaydenrichdemo.view.iview;

import com.jayden.jaydenrich.view.iview.IBaseView;
import com.jayden.jaydenrichdemo.model.bean.Meizi;

import java.util.List;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public interface IFuliView extends IBaseView {
    void onSuccess(List<Meizi> meizis);
}
