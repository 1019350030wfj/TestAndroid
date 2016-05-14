package com.jayden.testandroid.lollipop.animation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.jayden.testandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/5/12.
 * Email : 1570713698@qq.com
 */
public class TestShareElement extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Actor> actors;

    private static String[] names = {"朱茵", "张柏芝", "张敏", "莫文蔚", "黄圣依", "赵薇", "如花"};

    private static int[] pics = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recycler);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);//布局宽高不变
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initData();

        TestShareElementAdapter adapter = new TestShareElementAdapter(actors);
        adapter.setOnItemClickListener(new TestShareElementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ImageView imageView,int pos) {
                startActivity(imageView,pos);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    //共享元素
    public void startActivity(ImageView imageView,int pos) {
        Intent intent = new Intent(this,ShareElementActivity.class);
        intent.putExtra("pos",pos);
        intent.putExtra("drawableId",pics[pos]);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,imageView,pos+"pic");
            Bundle bundle = options.toBundle();
            startActivity(intent,bundle);
        } else {
            startActivity(intent);
        }
    }

    private void initData() {
        actors = new ArrayList<>();
        for (int i=0; i < names.length; i++) {
            Actor actor = new Actor(names[i],pics[i]);
            actors.add(actor);
        }
    }
}
