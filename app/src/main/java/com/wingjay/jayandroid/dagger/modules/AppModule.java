package com.wingjay.jayandroid.dagger.modules;

import android.app.Application;

import com.wingjay.jayandroid.dagger.ForApplication;
import com.wingjay.jayandroid.dagger.bean.People;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
