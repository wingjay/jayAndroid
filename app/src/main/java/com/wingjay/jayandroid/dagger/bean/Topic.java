package com.wingjay.jayandroid.dagger.bean;

/**
 * Created by Jay on 4/20/16.
 */
public class Topic {

    private String title;
    private String content;
    private long createTime;

    public Topic(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}
