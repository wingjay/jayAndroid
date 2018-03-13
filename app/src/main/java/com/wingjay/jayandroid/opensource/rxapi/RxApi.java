package com.wingjay.jayandroid.opensource.rxapi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.BaseFragment;
import com.wingjay.jayandroid.ata.LifecycleAwarePresenter;
import com.wingjay.jayandroid.autolifecycle.ActivityLifecycle;
import com.wingjay.jayandroid.autolifecycle.ILifecycleProvider;
import rx.Observable;

/**
 * RxApi
 * 基于 RxJava 的网络请求框架。
 * @author wingjay
 * @date 2018/03/13
 */
public class RxApi {
    /**
     * 不会监听生命周期
     */
    public static <T> void execute(@NonNull Observable<T> request,
                                   @NonNull RxSubscriber<T> rxSubscriber) {
        innerExecute(null, request, rxSubscriber);
    }

    /**
     * 在 BaseFragment 里的网络请求。
     *
     * 当发生 {@link BaseFragment#onDestroyView()} 时，请求会自动停止
     */
    public static <T> void execute(@NonNull BaseFragment baseFragment,
                                   @NonNull Observable<T> request,
                                   @NonNull RxSubscriber<T> rxSubscriber) {
        innerExecute((ILifecycleProvider) baseFragment, request, rxSubscriber);
    }

    /**
     * 在 BaseActivity 里的网络请求。
     *
     * 当发生 {@link BaseActivity#onDestroy()} 时，请求会自动停止
     */
    public static <T> void execute(@NonNull BaseActivity baseActivity,
                                   @NonNull Observable<T> request,
                                   @NonNull RxSubscriber<T> rxSubscriber) {
        innerExecute((ILifecycleProvider) baseActivity, request, rxSubscriber);
    }

    /**
     * 在 LifecycleAwarePresenter 里的网络请求
     */
    public static <T> void execute(LifecycleAwarePresenter presenter,
                                   @NonNull Observable<T> request,
                                   @NonNull RxSubscriber<T> rxSubscriber) {
        execute(presenter.getLifecycleProvider(), request, rxSubscriber);
    }

    /**
     * 自动监听 ILifecycleProvider 生命周期的网络请求。
     *
     * @param lifecycleProvider {@link BaseActivity}
     *                          {@link BaseFragment}
     *                          {@link LifecycleAwarePresenter#getLifecycleProvider()}
     * @param request mtop 请求.
     * @param rxSubscriber RxSubscriber处理对象
     */
    public static <T> void execute(@Nullable ILifecycleProvider lifecycleProvider,
                                   @NonNull Observable<T> request,
                                   @NonNull RxSubscriber<T> rxSubscriber) {
        innerExecute(lifecycleProvider, request, rxSubscriber);
    }

    private static <T> void innerExecute(@Nullable ILifecycleProvider lifecycleProvider,
                                         @NonNull Observable<T> request,
                                         @NonNull RxSubscriber<T> rxSubscriber) {
        request
            .compose(RxApi.<T>networkRequestTransformer(lifecycleProvider))
            .subscribe(rxSubscriber);
    }

    /**
     * 1. 网络请求线程切换；
     * 2. 监听ILifecycleProvider生命周期
     *
     * 使用方法:
     * ```
     *  MTop.getUser(1)
     *      .compose(RxApi.<User>networkRequestTransformer(lifecycleProvider))
     *      .subscribe(new RxSubscriber() {});
     *```
     */
    public static <T> Observable.Transformer<T, T> networkRequestTransformer(
        @Nullable final ILifecycleProvider lifecycleProvider) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> source) {
                Observable<T> scheduledObservable = source.compose(RxSchedulers.<T>networkThenMain());
                if (lifecycleProvider != null) {
                    if (lifecycleProvider instanceof BaseActivity) {
                        BaseActivity baseActivity = (BaseActivity) lifecycleProvider;
                        return scheduledObservable.compose(
                            baseActivity.<T>bindUntilEvent(ActivityLifecycle.DESTROY));
                    }
                    if (lifecycleProvider instanceof BaseFragment) {
                        BaseFragment baseFragment = (BaseFragment) lifecycleProvider;
                        //return scheduledObservable.compose(
                            //baseFragment.<T>bindUntilEvent(FragmentLifecycle.DESTROY_VIEW));
                    }
                }
                return scheduledObservable;
            }
        };
    }
}
