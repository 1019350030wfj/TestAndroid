package com.jayden.testandroid.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jayden.testandroid.R;


/**
 * Created by Jayden on 2016/5/11.
 * Email : 1570713698@qq.com
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("列表");
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,VideoListFragment.newInstance(VideoListFragment.LOCAL))
                    .commit();
        }

        init();
    }

    private void init() {
        Toast.makeText(this,"启动成功",Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String luckyURL = uri.getQueryParameter("url");
                Intent web = new Intent();
                web.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(luckyURL);
                web.setData(content_url);
                startActivity(web);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.enable_local_video: {
                if (!item.isChecked()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,VideoListFragment.newInstance(VideoListFragment.LOCAL))
                            .commit();
                }
                break;
            }
            case R.id.enable_online_video: {
                if (!item.isChecked()) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,VideoListFragment.newInstance(VideoListFragment.ONLINE))
                            .commit();
                }
                break;
            }
        }

        //改变视频选中状态
        item.setChecked(!item.isChecked());
        return true;
    }
}
