package com.wingjay.jayandroid.dagger.modules;

import com.wingjay.jayandroid.dagger.ForForum;
import com.wingjay.jayandroid.dagger.bean.Forum;
import com.wingjay.jayandroid.dagger.bean.People;
import com.wingjay.jayandroid.dagger.bean.Topic;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jay on 4/20/16.
 */
@Module
public class ForumModule {

    @ForForum @Provides People providePeople() {
        return new People(23, "Forum people");
    }

    @Provides Topic provideTopic() {
        return new Topic("topic title");
    }

    @Provides Forum provideForum(@ForForum People people, Topic topic) {
        return new Forum(people, topic);
    }
}
