package com.jayden.testandroid.framework.ui.shareelementatfragment;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jayden on 2016/4/1.
 * Email : 1570713698@qq.com
 */
public class DetailFragment extends Fragment {

    private static final String ARG_NUMBER = "arg_number";
    private ArrayList<DetailData> mDetailDatas;

    @BindView(R.id.detail_image)
    ImageView mImage;

    @BindView(R.id.detail_head)
    TextView mHead;

    @BindView(R.id.detail_body)
    TextView mBody;

    public static DetailFragment newInstance(@IntRange(from = 0,to = 5) int number) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUMBER,number);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

        int number = getArguments().getInt(ARG_NUMBER);
        mImage.setImageResource(mDetailDatas.get(number).getImage());
        mHead.setText(mDetailDatas.get(number).getHead());
        mBody.setText(mDetailDatas.get(number).getBody());

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    // 初始化数据
    private void initData() {
        mDetailDatas = new ArrayList<>();
        mDetailDatas.add(new DetailData(R.drawable.taeyeon, R.string.taeyeon, R.string.taeyeon_detail));
        mDetailDatas.add(new DetailData(R.drawable.jessica, R.string.jessica, R.string.jessica_detail));
        mDetailDatas.add(new DetailData(R.drawable.sunny, R.string.sunny, R.string.sunny_detail));
        mDetailDatas.add(new DetailData(R.drawable.tiffany, R.string.tiffany, R.string.tiffany_detail));
        mDetailDatas.add(new DetailData(R.drawable.yuri, R.string.yuri, R.string.yuri_detail));
        mDetailDatas.add(new DetailData(R.drawable.yoona, R.string.yoona, R.string.yoona_detail));
    }

    // 定义类
    private static class DetailData {
        private int mImage;
        private int mHead;
        private int mBody;

        public DetailData(int image, int head, int body) {
            mImage = image;
            mHead = head;
            mBody = body;
        }

        public int getImage() {
            return mImage;
        }

        public int getHead() {
            return mHead;
        }

        public int getBody() {
            return mBody;
        }
    }
}
