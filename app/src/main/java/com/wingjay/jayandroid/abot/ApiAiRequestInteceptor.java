package com.wingjay.jayandroid.abot;

import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jay on 10/14/16.
 */

public class ApiAiRequestInteceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    Request.Builder builder = request.newBuilder();

    HttpUrl url = request.url().newBuilder()
        .addQueryParameter("v", "20150910")
        .addQueryParameter("timezone", TimeZone.getDefault().getDisplayName())
        .addQueryParameter("lang", Locale.getDefault().getDisplayName())
        .addQueryParameter("sessionId", "1234567890")
        .build();

    Request newRequest = builder
        .url(url)
        .addHeader("Authorization", "Bearer " + ApiAiToken.DEVELOPER_TOKEN)
        .build();
    return chain.proceed(newRequest);
  }
}
