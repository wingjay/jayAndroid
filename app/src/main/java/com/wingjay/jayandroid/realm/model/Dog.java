package com.wingjay.jayandroid.realm.model;

import io.realm.RealmObject;

// Your model just have to extend RealmObject.
// This will inherit an annotation which produces proxy getters and setters for all fields

public class Dog extends RealmObject {

  public String name;


}
