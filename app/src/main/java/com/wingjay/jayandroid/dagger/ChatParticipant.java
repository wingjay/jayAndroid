package com.wingjay.jayandroid.dagger;

import com.wingjay.jayandroid.dagger.bean.People;

/**
 * Created by Jay on 4/21/16.
 */
public class ChatParticipant {

    public ChatParticipant(People people) {
        this.people = people;
    }

    public String name;
    public People people;

}
