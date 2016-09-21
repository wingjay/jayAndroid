package com.wingjay.jayandroid.dagger.host;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.wingjay.jayandroid.App;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.dagger.ForApplication;
import com.wingjay.jayandroid.dagger.bean.Forum;
import com.wingjay.jayandroid.dagger.bean.People;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Jay on 4/19/16.
 */
public class DaggerHostActivity extends BaseActivity {

    @Inject
    Application application;

    @ForApplication
    @Inject
    People people;

    @Inject
    Lazy<Forum> LazyForum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getAppComponent().inject(this);

        Log.i("jaydebug", "people " + people.getName() + ", forum topic " + LazyForum.get().topic.getTitle());
    }
}
