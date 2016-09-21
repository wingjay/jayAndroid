package com.wingjay.jayandroid.rxjava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 3/28/16.
 */
public class RxJavaUtils {

  public static List<MockData> getMockGankItemList() {
    List<MockData> itemList = new ArrayList<>();
    itemList.add(new MockData("1"));
    itemList.add(new MockData("2"));
    itemList.add(new MockData("3"));
    itemList.add(new MockData("4"));
    itemList.add(new MockData("5"));
    itemList.add(new MockData("6"));
    itemList.add(new MockData("20"));
    itemList.add(new MockData("21"));
    itemList.add(new MockData("22"));
    itemList.add(new MockData("23"));
    itemList.add(new MockData("24"));

    return itemList;
  }

}
