package com.jayden.testandroid.framework.ui.bannerviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jayden.testandroid.R;
import com.jayden.testandroid.framework.model.ImageHandler;

import java.util.Arrays;
import java.util.List;

import static com.jayden.testandroid.R.id.item_banner_img;

/**
 * Created by Jayden on 2016/4/6.
 * Email : 1570713698@qq.com
 */
public class BannerViewActivity extends Activity {

    private BannerView bannerView;
    private List<String> banners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
        initData();
    }

    private void initData() {

        //图片来自百度
        banners = Arrays.asList("http://f.hiphotos.baidu.com/zhidao/pic/item/241f95cad1c8a7862495b8776109c93d70cf5008.jpg",
                "http://ww2.sinaimg.cn/crop.0.0.1080.1080.1024/7d5d4351jw8es526khhycj20u00u0q5b.jpg",
                "http://img1.3lian.com/2015/a1/107/d/65.jpg",
                "http://img1.3lian.com/2015/a1/93/d/225.jpg",
                "http://img1.3lian.com/img013/v4/96/d/44.jpg");

        bannerView.setAdapter(new BannerView.Adapter() {
            @Override
            public boolean isEmpty() {
                return banners.size() > 0 ? false : true;
            }

            @Override
            public View getView(int position) {
                View view = LayoutInflater.from(BannerViewActivity.this).inflate(R.layout.item_bannerview,null);
                ImageView imageView = (ImageView) view.findViewById(item_banner_img);
                ImageHandler.displayImage(BannerViewActivity.this,imageView,banners.get(position));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BannerViewActivity.this,"Banner Click",Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }

            @Override
            public int getCount() {
                return banners.size();
            }
        });

    }

    private void initView() {
        bannerView = (BannerView) findViewById(R.id.CarouselView);
        bannerView.setSwitchTime(2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bannerView != null) {
            bannerView.cancelTimer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bannerView != null) {
            bannerView.startTimer(0);
        }
    }
}
