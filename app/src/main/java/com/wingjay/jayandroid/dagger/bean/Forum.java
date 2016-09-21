package com.wingjay.jayandroid.dagger.bean;

/**
 * Created by Jay on 4/20/16.
 */
public class Forum {

    public People people;

    public Topic topic;

    public Forum(People people, Topic topic) {
        this.people = people;
        this.topic = topic;
    }
}
