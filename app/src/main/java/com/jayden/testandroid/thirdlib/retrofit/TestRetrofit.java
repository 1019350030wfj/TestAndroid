package com.jayden.testandroid.thirdlib.retrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.testandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 1、创建retrofit对象
 * 2、动态代理获取定义的接口
 * 3、调用接口里面的
 * Created by Jayden on 2017/3/23.
 */
public class TestRetrofit extends Activity{

    public static final String API_URL = "https://api.github.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService gitHubService = retrofit.create(GitHubService.class);
        Call<List<Contributor>> call = gitHubService.contributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                for (Contributor contributor : response.body()){
                    System.out.println(contributor.login + " (" + contributor.contributions + ")");
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });
    }

}
