package com.wingjay.jayandroid.dagger.component;

import com.wingjay.jayandroid.abot.ABotActivity;
import com.wingjay.jayandroid.dagger.ForForum;
import com.wingjay.jayandroid.dagger.bean.People;
import com.wingjay.jayandroid.dagger.host.DaggerHostActivity;
import com.wingjay.jayandroid.dagger.modules.AppModule;
import com.wingjay.jayandroid.dagger.modules.ForumModule;
import com.wingjay.jayandroid.weex.HelloWeexActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Jay on 4/20/16.
 */
@Singleton
@Component(modules = {AppModule.class, ForumModule.class})
public interface AppComponent {
    // for subcomponent usage
    @ForForum People people();

    void inject(DaggerHostActivity daggerHostActivity);

    void inject(ABotActivity aBotActivity);

    void inject(HelloWeexActivity obj);
}
