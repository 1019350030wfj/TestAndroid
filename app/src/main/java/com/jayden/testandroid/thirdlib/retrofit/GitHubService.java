package com.jayden.testandroid.thirdlib.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jayden on 2017/3/23.
 */
public interface GitHubService {

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(@Path("owner") String owner,
            @Path("repo") String repo);

//    @GET("users/{user}/repos")
//    Call<List<Repo>> listRepos(@Path("user") String user);
}
