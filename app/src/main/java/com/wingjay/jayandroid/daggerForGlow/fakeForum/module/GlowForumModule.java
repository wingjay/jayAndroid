package com.wingjay.jayandroid.daggerForGlow.fakeForum.module;

import com.wingjay.jayandroid.daggerForGlow.fakeForum.obj.Author;
import com.wingjay.jayandroid.daggerForGlow.fakeForum.obj.Topic;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 6/26/16.
 */
@Module
public class GlowForumModule {

  @Provides
  Author provideAuthor() {
    return new Author("author jay", "i am author jay");
  }

  @Provides
  Topic provideTopic() {
    return new Topic(20160701, "topic title", "topic content");
  }

}
