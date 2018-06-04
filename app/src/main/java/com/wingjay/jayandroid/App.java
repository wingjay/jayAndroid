package com.wingjay.jayandroid;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
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
import com.wingjay.jayandroid.richlist.uibase.IRichProvider;
import com.wingjay.jayandroid.richlist.uibase.IViewHolderMapper;
import com.wingjay.jayandroid.richlist.uibase.RichViewHolderFactory;
import com.wingjay.jayandroid.richlist.uibase.ViewHolderMapperImpl;
import com.wingjay.jayandroid.weex.WeexImageAdapter;
import io.realm.Realm;

/**
 * Created by Jay on 4/20/16.
 */
public class App extends MultiDexApplication implements GlowForumComponentProvider, IRichProvider {

    AppComponent appComponent;
    SubComponent subComponent;

    GlowAppComponent glowAppComponent;

    ViewHolderMapperImpl viewHolderMapper = new ViewHolderMapperImpl();

    @Override
    public void onCreate() {
      super.onCreate();

      RichViewHolderFactory.init(this);
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

      initWeex();

      //TrackerManager.getInstance().init(this, true, true, true);
    }

  private void initWeex() {
    WXSDKEngine.initialize(this,
        new InitConfig.Builder().setImgAdapter(new WeexImageAdapter()).build());
  }

  public Context getContext() {
      return this.getApplicationContext();
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

    @Override
    public IViewHolderMapper getIViewHolderMapper() {
        return viewHolderMapper;
    }
}
