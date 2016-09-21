package com.wingjay.jayandroid.retrofitOkhttpUpgrade;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jay on 7/24/16.
 */
public class GithubUser {
  @SerializedName("name")
  String name;

  @SerializedName("company")
  String company;

  String url;

  String email;

}
