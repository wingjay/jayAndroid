package com.wingjay.jayandroid.retrofitOkhttpUpgrade;

import android.os.Bundle;
import android.util.Log;

import com.wingjay.jayandroid.BaseActivity;

import java.io.IOException;
import java.security.cert.Certificate;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jay on 7/24/16.
 */
public class RetrofitOkhttpUpgradeActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final OkHttpClient client = new OkHttpClient.Builder()
        .certificatePinner(new CertificatePinner.Builder()
            .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=").build())
        .build();

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Request request = new Request.Builder()
              .url("https://publicobject.com/robots.txt")
              .build();
          Response response = client.newCall(request).execute();
          Log.d("jaydebug", "response");
          if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
          for (Certificate certificate : response.handshake().peerCertificates()) {
            Log.d("jaydebug", CertificatePinner.pin(certificate));
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();

//    Retrofit retrofit = new Retrofit.Builder()
//        .baseUrl("https://api.github.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//        .build();
//
//    final UserService userService = retrofit.create(UserService.class);

    Log.d("jaydebug", "start0");
//    userService.listRepos("wingjay").subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Subscriber<GithubUser>() {
//          @Override
//          public void onCompleted() {
//            Log.d("jaydebug", "onCompleted");
//          }
//
//          @Override
//          public void onError(Throwable e) {
//            // HttpException non 2xx
//            // Exception: such as UnKnownHostException
//            e.printStackTrace();
//            Log.d("jaydebug", "onError" + e.toString());
//          }
//
//          @Override
//          public void onNext(GithubUser githubUser) {
//            Log.d("jaydebug", "githubuser is null?" + (githubUser == null));
//          }
//        });

//      userService.listRepos3("wingjay").enqueue(new Callback<GithubUser>() {
//        @Override
//        public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
//          Log.d("jaydebug", "response " + response.body().company);
//        }
//
//        @Override
//        public void onFailure(Call<GithubUser> call, Throwable t) {
//          Log.d("jaydebug", "failure");
//        }
//      });

//    new Thread(new Runnable() {
//      @Override
//      public void run() {
//        try {
//          Response<GithubUser> response = userService.listRepos3("wingjay").execute();
//          Log.d("jaydebug", "response " + response.body().company);
//        } catch (IOException e) {
//          Log.d("jaydebug", "failure");
//          e.printStackTrace();
//        }
//
//      }
//    }).start();


  }
}
