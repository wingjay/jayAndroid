package com.wingjay.jayandroid.retrofitOkhttpUpgrade;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit API.
 */
public interface UserService {

  @GET("users/{user}")
  Observable<GithubUser> listRepos(@Path("user") String user);

  @GET("users/{user}")
  GithubUser listRepos2(@Path("user") String user);

  @GET("users/{user}")
  Call<GithubUser> listRepos3(@Path("user") String user);


}
