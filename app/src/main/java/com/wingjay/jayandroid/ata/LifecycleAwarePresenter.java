package com.wingjay.jayandroid.ata;

import com.wingjay.jayandroid.ata.inner.IView;
import com.wingjay.jayandroid.autolifecycle.ActivityLifecycle;
import com.wingjay.jayandroid.autolifecycle.AutoLifecycle;
import com.wingjay.jayandroid.autolifecycle.AutoLifecycleEvent;
import com.wingjay.jayandroid.autolifecycle.ILifecycleProvider;

/**
 * LifecycleAwarePresenter
 *
 * @author wingjay
 * @date 2017/10/28
 */
@SuppressWarnings("unused, WeakerAccess")
public class LifecycleAwarePresenter {

    private IView mIView;

    public LifecycleAwarePresenter(IView iView,
                                   ILifecycleProvider iLifecycleProvider) {
        this.mIView = iView;
        AutoLifecycle.getInstance().init(this, iLifecycleProvider);
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.CREATE)
    private void onCreate() {
        bindView(mIView);
    }

    @AutoLifecycleEvent(activity = ActivityLifecycle.DESTROY)
    private void onDestroy() {
        unBindView();
    }

    private void bindView(IView iView) {}
    private void unBindView() {}
}

    //private void loadDataFromServer() {}
    //private IView getBindView() {
    //    return mIView;
    //}
    //
    //private boolean isViewActive() {
    //    return true;
    //}
    //
    //@AutoLifecycleEvent(activity = ActivityLifecycle.PRE_INFLATE)
    //private void preInflate() {
    //    loadDataFromServer();
    //}


//Observable<Object> mTopApiObservable = Observable.create(new OnSubscribe<Object>() {
//    @Override
//    public void call(Subscriber<? super Object> subscriber) {
//
//    }
//});

//mTopApiObservable
//    .compose(bindUntilEvent(ActivityLifecycle.DESTROY))
//    .subscribeOn(Schedulers.io())
//    .observeOn(AndroidSchedulers.mainThread())
//    .subscribe(new Subscriber<Object>() {
//        @Override
//        public void onNext(Object data) {
//            // 直接显示数据
//            getBindView().displayData(data);
//        }
//
//        @Override
//        public void onCompleted() {}
//
//        @Override
//        public void onError(Throwable e) {}
//    });
//
