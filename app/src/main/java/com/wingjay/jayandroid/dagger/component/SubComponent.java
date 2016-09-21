package com.wingjay.jayandroid.dagger.component;

import com.wingjay.jayandroid.dagger.host.ChatActivity;
import com.wingjay.jayandroid.dagger.modules.ChatModule;
import com.wingjay.jayandroid.dagger.UserScope;

import dagger.Component;

/**
 * Created by Jay on 4/21/16.
 */
@UserScope
@Component(dependencies = AppComponent.class, modules = ChatModule.class)
public interface SubComponent {
    void inject(ChatActivity chatActivity);
}
