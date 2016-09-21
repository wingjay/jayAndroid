package com.wingjay.jayandroid.dagger.host;

import android.os.Bundle;

import com.wingjay.jayandroid.App;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.dagger.ChatParticipant;
import com.wingjay.jayandroid.dagger.ForForum;
import com.wingjay.jayandroid.dagger.bean.People;

import javax.inject.Inject;

/**
 * Created by Jay on 4/21/16.
 */
public class ChatActivity extends BaseActivity {

    @Inject
    ChatParticipant chatParticipant;

    @ForForum
    @Inject
    People people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication()).getSubComponent().inject(this);
    }
}
