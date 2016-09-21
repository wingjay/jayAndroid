package com.wingjay.jayandroid.daggerForGlow.fakeForum.component;

import android.content.Context;

import com.google.common.base.Preconditions;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.GlowForumComponentProvider;

/**
 * Created by Jay on 6/26/16.
 */
public class GlowForumComponentGetter {

  public static GlowForumComponent get(Context context) {
    Preconditions.checkNotNull(context);
    Preconditions.checkState(context.getApplicationContext() instanceof GlowForumComponentProvider);
    return ((GlowForumComponentProvider) context.getApplicationContext()).getGlowForumComponent();
  }

}
