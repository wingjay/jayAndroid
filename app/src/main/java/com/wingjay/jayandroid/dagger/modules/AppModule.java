package com.wingjay.jayandroid.dagger.modules;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.wingjay.jayandroid.BuildConfig;
import com.wingjay.jayandroid.abot.ApiAiRequestInteceptor;
import com.wingjay.jayandroid.abot.ApiAiService;
import com.wingjay.jayandroid.abot.ForApiAi;
import com.wingjay.jayandroid.dagger.ForApplication;
import com.wingjay.jayandroid.dagger.bean.People;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jay on 4/19/16.
 */
@Singleton
@Module
public class AppModule {

    public Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton Application provideApplication() {
        return application;
    }

    @ForApplication @Provides
    People providePeople() {
        return new People(29, "App People");
    }

    @ForApiAi
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .addInterceptor(new ApiAiRequestInteceptor())
            .addNetworkInterceptor(new StethoInterceptor());

      HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
      httpLoggingInterceptor.setLevel(
          BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
      builder.addInterceptor(httpLoggingInterceptor);

      return builder.build();
    }

    @ForApiAi
    @Provides
    Retrofit provideRetrofit(@ForApiAi OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl("https://wtf/v1/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    ApiAiService provideApiAiService(@ForApiAi Retrofit retrofit) {
        return retrofit.create(ApiAiService.class);
    }
}
