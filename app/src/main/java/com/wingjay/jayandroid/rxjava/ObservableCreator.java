package com.wingjay.jayandroid.rxjava;

import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Used to create observerable by 'create'/'from'/'just'.
 */
public class ObservableCreator {

  public static Observable<MockData> createNormalObservable() {
    Observable<MockData> observable = Observable.create(new Observable.OnSubscribe<MockData>() {
      @Override
      public void call(Subscriber<? super MockData> subscriber) {
        if (subscriber.isUnsubscribed()) {
          return;
        } else {
          subscriber.onNext(new MockData("test1", null));
        }

        if (!subscriber.isUnsubscribed()) {
          subscriber.onCompleted();
        }
      }
    });
    Log.i(RxJavaActivity.RXJAVA_DEBUG_JAY, "observable created");
    return observable;
  }

  public static Observable<MockData> createJustObservable() {
    return Observable.just(new MockData("1"), new MockData("2"));
  }

  public static Observable<List<MockData>> createListObservable() {
    Observable<MockData> observable = Observable.from(RxJavaUtils.getMockGankItemList());
    return observable.toList();
  }

  public static Observable<MockData> createFromListObservable() {
    Observable<MockData> fromListObserverable = Observable.from(RxJavaUtils.getMockGankItemList());
    return fromListObserverable;
  }





}
