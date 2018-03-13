package com.wingjay.jayandroid.ata;

import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.Log;
import com.wingjay.jayandroid.ata.inner.IView;
import com.wingjay.jayandroid.autolifecycle.ActivityLifecycle;
import com.wingjay.jayandroid.autolifecycle.AutoLifecycle;
import com.wingjay.jayandroid.autolifecycle.AutoLifecycleEvent;
import com.wingjay.jayandroid.autolifecycle.CommonLifecycle;
import com.wingjay.jayandroid.autolifecycle.DialogFragmentLifecycle;
import com.wingjay.jayandroid.autolifecycle.FragmentLifecycle;
import com.wingjay.jayandroid.autolifecycle.ILifecycleProvider;
import com.wingjay.jayandroid.util.ThreadUtil;

/**
 * LifecycleAwarePresenter
 *
 * @author wingjay
 * @date 2017/10/28
 */
@SuppressWarnings("WeakerAccess")
public class LifecycleAwarePresenter<V extends IView> {

    private static final String TAG = "LifecycleAwarePresenter";
    private V mIView;

    /**
     * ILifecycleProvider is used by rx network request
     */
    private @Nullable
    ILifecycleProvider mLifecycleProvider;

    /**
     * Remember to call {@link #bindView(IView)} when it's ready.
     */
    public LifecycleAwarePresenter() {}

    /**
     * Note: You MUST construct it when you define this valuable, or in
     *       Activity#initBundle, Fragment#onAttach、#onCreate.
     * @param mIView XiamiUiBaseActivity、XiamiUiBaseFragment
     */
    public LifecycleAwarePresenter(V mIView) {
        bindView(mIView);
    }

    public void bindView(V mIView) {
        if (this.mIView != null) {
            throw new IllegalArgumentException("IView has already been binded.");
        }

        if (!(mIView instanceof ILifecycleProvider)) {
            throw new IllegalArgumentException("IView must implements LifecycleProvider, such as XiamiBaseActivity");
        }

        mLifecycleProvider = (ILifecycleProvider) mIView;
        this.mIView = mIView;

        AutoLifecycle.getInstance().init(this, mLifecycleProvider);
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.PRE_INFLATE,
        fragment = FragmentLifecycle.PRE_INFLATE,
        dialog = DialogFragmentLifecycle.CREATE)
    private void autoPreInflate() {
        Log.d(TAG, "onInitDataBeforeInflate");
        onInitDataBeforeInflate();
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.CREATE,
        fragment = FragmentLifecycle.CREATE,
        dialog = DialogFragmentLifecycle.VIEW_CREATED)
    private void autoCreated() {
        Log.d(TAG, "onHostCreated");
        onHostCreated();
    }

    @AutoLifecycleEvent(common = CommonLifecycle.START)
    private void autoStarted() {
        Log.d(TAG, "onHostStarted");
        onHostStarted();
    }

    @AutoLifecycleEvent(common = CommonLifecycle.RESUME)
    private void autoResumed() {
        Log.d(TAG, "onHostResumed");
        onHostResumed();
    }

    @AutoLifecycleEvent(common = CommonLifecycle.PAUSE)
    private void autoPause() {
        Log.d(TAG, "onHostPause");
        onHostPause();
    }

    @AutoLifecycleEvent(common = CommonLifecycle.STOP)
    private void autoStop() {
        Log.d(TAG, "onHostStop");
        onHostStop();
    }

    @AutoLifecycleEvent(common = CommonLifecycle.DESTROY)
    private void autoDestroy() {
        Log.d(TAG, "onHostDestroy");
        mIView = null;
        onHostDestroy();
    }

    /**
     * Init your data (like api-call) before Inflating view to short ui-drawing time.
     * Don't {@link #getBindView()} here due to View isn't inflated yet.
     */
    protected void onInitDataBeforeInflate() {}

    /**
     * Invoked <b>After</b> Activity#onCreate #onStart #onResume
     */
    protected void onHostCreated() {}
    protected void onHostStarted() {}
    protected void onHostResumed() {}

    /**
     * Invoked <b>Exactly Before</b> Activity#onPause #onStop #onDestroy
     */
    protected void onHostPause() {}
    protected void onHostStop() {}
    protected void onHostDestroy() {}

    @UiThread
    public V getBindView() {
        if (!ThreadUtil.isUIThread()) {
            throw new RuntimeException("getBindView not in UI Thread!");
        }
        return mIView;
    }

    @UiThread
    public boolean isViewActive() {
        return mIView != null;
    }

    @Nullable
    public ILifecycleProvider getLifecycleProvider() {
        return mLifecycleProvider;
    }
}

