package com.wingjay.jayandroid.daggerForGlow.fakeApp.component;

import com.wingjay.jayandroid.daggerForGlow.fakeApp.DaggerForGlowAppActivity;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.module.GlowAppModule;
import com.wingjay.jayandroid.daggerForGlow.fakeForum.component.GlowForumComponent;
import com.wingjay.jayandroid.daggerForGlow.fakeForum.module.GlowForumModule;

import dagger.Component;

/**
 * Created by Jay on 6/26/16.
 */
@Component(modules = {GlowAppModule.class, GlowForumModule.class})
public interface GlowAppComponent extends GlowForumComponent {
  // activites
  void inject(DaggerForGlowAppActivity daggerForGlowAppActivity);

}
