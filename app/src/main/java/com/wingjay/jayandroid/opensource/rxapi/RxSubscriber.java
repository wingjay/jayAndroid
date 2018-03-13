package com.wingjay.jayandroid.opensource.rxapi;

import rx.Subscriber;

/**
 * RxSubscriber
 * 专门处理网络请求结果的框架。提供以下回调函数：
 * 只要实现 success() 即可；如果要处理网络错误，再实现 onError()或者某种具体的错误 即可
 *
 * 1. success:                  {@link #success(Object)}
 * 2. common error:             {@link #onError(Throwable)}
 *   - network error:           {@link #networkError()}
 *   - unknown error:           {@link #unknownError(Throwable)}
 *
 * @author wingjay
 * @date 2018/03/13
 */
public abstract class RxSubscriber <T> extends Subscriber<T> {

    public RxSubscriber() {}

    @Override
    public void onCompleted() {
        // no-op
    }

    @Override
    public void onError(Throwable throwable) {
        // noNetwork();
        // networkError();
    }

    @Override
    public void onNext(T t) {
        //
        success(t);
    }

    @SuppressWarnings("WeakerAccess")
    protected abstract void success(T t);

    @SuppressWarnings("WeakerAccess")
    protected void noNetwork() {
        // no-op
    }

    @SuppressWarnings("WeakerAccess")
    protected void networkError() {
        // no-op
    }

    @SuppressWarnings("WeakerAccess")
    protected void unknownError(Throwable throwable) {
        // no-op
    }
}
