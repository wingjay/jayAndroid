package com.wingjay.jayandroid.opensource.rxapi;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RxSchedulers. RxJava 的线程切换类
 *
 * 例子：
 * Observable.just(null)
 *   map(new Fun1<>() {
 *       fun1(); // 在 io 线程执行
 *   })
 *   .compose(RxSchedulers.ioThenMain()) // 先 IO 再回主线程
 *   .map(new Fun1<>() {
 *       fun2(); // 在 Main 线程执行
 *   })
 *
 * @author wingjay
 * @date 2018/03/13
 */
public class RxSchedulers {
    /**
     * 先在 worker 线程执行异步任务；再回到 Main 线程
     */
    public static <T> Observable.Transformer<T, T> workerThenMain() {
        return construct(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }

    /**
     * 先在 io 线程执行如文件／数据库操作; 再回到主线程
     */
    public static <T> Observable.Transformer<T, T> ioThenMain() {
        return construct(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * 先在 network 线程执行网络请求; 再回到 Main 线程
     */
    public static <T> Observable.Transformer<T, T> networkThenMain() {
        return construct(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * 先在 computation 线程执行复杂计算; 再回到 Main 线程
     */
    public static <T> Observable.Transformer<T, T> computationThenMain() {
        return construct(Schedulers.computation(), AndroidSchedulers.mainThread());
    }

    /**
     * 先在 network 线程执行网络请求；再回到 IO 线程
     */
    public static <T> Observable.Transformer<T, T> networkThenIo() {
        return construct(Schedulers.io(), Schedulers.io());
    }

    private static <T> Observable.Transformer<T, T> construct(final Scheduler subscribeOn,
                                                              final Scheduler observeOn) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> source) {
                return source.subscribeOn(subscribeOn).observeOn(observeOn);
            }
        };
    }
}
