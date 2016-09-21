package com.wingjay.jayandroid.daggerForGlow.fakeApp.module;

import android.app.Application;

import com.wingjay.jayandroid.daggerForGlow.fakeApp.obj.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 6/26/16.
 */
@Module
public class GlowAppModule {

  private Application application;

  public GlowAppModule(Application application) {
    this.application = application;
  }

  @Provides User provideUser() {
    return new User("app User", 20);
  }

}
