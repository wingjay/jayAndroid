package com.wingjay.jayandroid.ata;

import com.wingjay.jayandroid.autolifecycle.ActivityLifecycle;
import com.wingjay.jayandroid.autolifecycle.AutoLifecycle;
import com.wingjay.jayandroid.autolifecycle.AutoLifecycleEvent;
import com.wingjay.jayandroid.autolifecycle.ILifecycleProvider;

/**
 * IKnowLifecycle
 *
 * @author 冲灵
 * @date 2017/10/28
 */
@SuppressWarnings("unused")
public class IKnowLifecycle {

    public IKnowLifecycle(ILifecycleProvider iLifecycleProvider) {
        // ILifecycleProvider是我们的 BaseActivity/BaseFragment
        AutoLifecycle.getInstance().init(this, iLifecycleProvider);
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.CREATE)
    public void onCreate() {

    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.RESUME)
    public void onResume() {

    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.STOP)
    public void onStop() {

    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.DESTROY)
    public void onDestroy() {

    }
}
