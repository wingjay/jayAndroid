package com.wingjay.jayandroid.dagger.modules;

import com.wingjay.jayandroid.dagger.ChatParticipant;
import com.wingjay.jayandroid.dagger.ForForum;
import com.wingjay.jayandroid.dagger.bean.People;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 4/21/16.
 */
@Module
public class ChatModule {

    @Provides ChatParticipant provideChatParticipant(@ForForum People people) {
        return new ChatParticipant(people);
    }

}
