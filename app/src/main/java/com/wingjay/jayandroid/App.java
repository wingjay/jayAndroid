package com.wingjay.jayandroid;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.wingjay.jayandroid.dagger.component.AppComponent;
import com.wingjay.jayandroid.dagger.component.DaggerAppComponent;
import com.wingjay.jayandroid.dagger.component.DaggerSubComponent;
import com.wingjay.jayandroid.dagger.component.SubComponent;
import com.wingjay.jayandroid.dagger.modules.AppModule;
import com.wingjay.jayandroid.dagger.modules.ChatModule;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.GlowForumComponentProvider;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.component.DaggerGlowAppComponent;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.component.GlowAppComponent;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.module.GlowAppModule;
import com.wingjay.jayandroid.daggerForGlow.fakeForum.component.GlowForumComponent;

import io.realm.Realm;

/**
 * Created by Jay on 4/20/16.
 */
public class App extends Application implements GlowForumComponentProvider {

    AppComponent appComponent;
    SubComponent subComponent;

    GlowAppComponent glowAppComponent;

    @Override
    public void onCreate() {
      super.onCreate();

      Realm.init(this);
      Stetho.initializeWithDefaults(this);

      LeakCanary.install(this);

      appComponent = DaggerAppComponent.builder()
              .appModule(new AppModule(this))
              .build();
      subComponent = DaggerSubComponent.builder()
              .appComponent(appComponent)
              .chatModule(new ChatModule())
              .build();

      glowAppComponent = DaggerGlowAppComponent.builder()
                          .glowAppModule(new GlowAppModule(this))
                          .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public SubComponent getSubComponent() {
        return subComponent;
    }

    public GlowAppComponent getGlowAppComponent() {
        return glowAppComponent;
    }

    @Override
    public GlowForumComponent getGlowForumComponent() {
        return glowAppComponent;
    }
}
