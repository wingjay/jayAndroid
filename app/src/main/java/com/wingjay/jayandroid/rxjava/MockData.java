package com.wingjay.jayandroid.rxjava;

import android.text.TextUtils;

/**
 * Created by Jay on 3/28/16.
 */
public class MockData extends ILoggable implements Comparable {

  public MockData(String name) {
    this(name, null);
  }

  public MockData(String name, String imageUrl) {
    this.name = name;
    this.imageUrl = imageUrl;
  }

  protected String name;
  protected String imageUrl;

  public String getName() {
    return name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  @Override
  public int compareTo(Object another) {
    return (another instanceof MockData && TextUtils.equals(((MockData) another).getName(), this.getName())) ? 1 : 0;
  }

  @Override
  public String getLoggingString() {
    return "name: " + name + ", imageUrl: " + imageUrl;
  }
}
