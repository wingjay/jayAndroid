package com.wingjay.jayandroid.daggerForGlow.fakeApp.obj;

import javax.inject.Inject;

/**
 * Created by Jay on 6/26/16.
 */
public class DailyLog {

  public int type;
  public String date;

  @Inject
  public DailyLog() {
    this(2, "DailyLog 20160701");
  }

  public DailyLog(int type, String date) {
    this.type = type;
    this.date = date;
  }
}
