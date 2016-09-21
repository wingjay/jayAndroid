package com.wingjay.jayandroid.rxjava;

/**
 * Created by Jay on 3/28/16.
 */
public class TransformedMockData extends MockData {

  private int nameLength;

  public TransformedMockData(String name, int nameLength) {
    super(name);
    this.nameLength = nameLength;
  }

  public void setNameLength(int nameLength) {
    this.nameLength = nameLength;
  }

  public int getNameLength() {
    return nameLength;
  }

}
