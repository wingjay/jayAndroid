package com.wingjay.jayandroid.abot;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jay on 10/14/16.
 */

public interface ApiAiService {

  @GET("query")
  Observable<ApiAiResponse> query(@Query("query") String queryString);

}
