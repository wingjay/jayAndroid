package com.wingjay.jayandroid.daggerForGlow.fakeApp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.wingjay.jayandroid.App;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.obj.DailyLog;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.obj.User;
import com.wingjay.jayandroid.daggerForGlow.fakeForum.DaggerForGlowForumActivity;
import com.wingjay.jayandroid.daggerForGlow.fakeForum.obj.Author;
import com.wingjay.jayandroid.daggerForGlow.fakeForum.obj.Topic;

import javax.inject.Inject;

/**
 * Created by Jay on 6/26/16.
 */
public class DaggerForGlowAppActivity extends BaseActivity {
  @Inject
  User user;

  @Inject
  DailyLog dailyLog;

  @Inject
  Author author;

  @Inject
  Topic topic;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((App) getApplication()).getGlowAppComponent().inject(DaggerForGlowAppActivity.this);

    Log.d("jaydebug", "user: " + user.name + ", dailylog: " + dailyLog.type + ", author: " + author.name + ", topic: " + topic.title);

    startActivity(new Intent(DaggerForGlowAppActivity.this, DaggerForGlowForumActivity.class));
    finish();
  }
}
