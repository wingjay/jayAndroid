package com.wingjay.jayandroid.rxjava;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * We can view an Observable as a water flow, and data as floating leaves on the water. The observable will always transfer data to you,
 * if you have interest in these data, just subscribe it. Then when new leave is coming, you will be notified.
 */
public class RxJavaActivity extends BaseActivity {

  public final static String RXJAVA_DEBUG_JAY = "RxjavaDebugJay";

  @Bind(R.id.listview)
  ListView listView;

  private GankAdapter gankAdapter;
  private List<MockData> items = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listview);

    Log.d("jaydebug", " 1 current thread " + Thread.currentThread().getName());
    Observable.just("haha")
        .map(new Func1<String, String>() {
          @Override
          public String call(String s) {
            Log.d("jaydebug", " 2 current thread " + Thread.currentThread().getName());
            return s;
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .map(new Func1<String, String>() {
          @Override
          public String call(String s) {
            Log.d("jaydebug", " 3 current thread " + Thread.currentThread().getName());
            return null;
          }
        })
        .subscribeOn(Schedulers.io())
        .subscribe(new Action1<String>() {
          @Override
          public void call(String s) {
            Log.d("jaydebug", " 4 current thread " + Thread.currentThread().getName());
          }
        });


    gankAdapter = new GankAdapter(RxJavaActivity.this, items);
    listView.setAdapter(gankAdapter);
    // create observable
//    tryNormalObservable();
//    tryListObservable();
//    tryFromListObservable();
//    tryJustObservable();

    // filter observable
//    tryFilterObservable();
//    tryDistinctObservable();

    // transform observable to better fit ones
//    tryMapObservable();
//    tryFlatMapObservable();

    // combine observable
//    tryMergeObservables();
//    tryZipObservables();

    // cache
//    tryCacheObservables();

    // time related
//    tryTimerObservables();
//    tryIntervalObservables();
//    tryDelayObservables();

    // retry
//    tryRetryObservables();

    // onerror
//    tryOnError();
  }

  private void tryOnError() {
//    ObservableCreator.createFromListObservable()
//        .subscribe()
  }

  private void tryRetryObservables() {
    Observable<MockData> observable = ObservableCreator.createFromListObservable();
//    observable.retry()
//        .subscribe()
  }

  private void tryDelayObservables() {
    Observable<MockData> observable = ObservableCreator.createFromListObservable();
    observable.delay(500, TimeUnit.MILLISECONDS)
        .subscribe(new LogSubscriber<MockData>());
  }

  // an Observable that emits a 0L after the initialDelay and ever increasing numbers
  // after each period of time thereafter, while running on the given Scheduler.
  private void tryIntervalObservables() {
    // This won't stop until app be killed
    Observable.interval(500, 100, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Long>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(Long aLong) {
            // aLong = 0, 1, 2, 3...
            Log.d("jaydebug", "interval emit increasing value: " + aLong);
          }
        });
  }

  //an Observable that emits one item after a specified delay, and then completes
  private void tryTimerObservables() {
    Observable.timer(500, TimeUnit.MILLISECONDS)
        .subscribe(new LogSubscriber<Long>());
  }



  private void tryCacheObservables() {
    items.clear();
    Observable<MockData> observable = ObservableCreator.createFromListObservable();
    Observable<MockData> cached = observable.cache();
    // cachedObservable will emit exactly same items as originalObservable
    observable.subscribe(new Subscriber<MockData>() {
      @Override
      public void onCompleted() {
        Log.d("jaydebug", "original completed");
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(MockData mockData) {
        Log.d("jaydebug", "original observable: mockData is " + mockData);
      }
    });
    cached.subscribe(new Subscriber<MockData>() {
      @Override
      public void onCompleted() {
        Log.d("jaydebug", "cached ends");
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(MockData mockData) {
        Log.d("jaydebug", "cached observable: mockData is " + mockData);
      }
    });
  }

  private void tryZipObservables() {
    items.clear();

    Observable<MockData> observable1 = ObservableCreator.createFromListObservable();
    Observable<MockData> observable2 = Observable.just(new MockData("a"), new MockData("b"), new MockData("c"));
    Observable<MockData> observable3 = Observable.just(new MockData(","), new MockData("."), new MockData("?"), new MockData(":"), new MockData("+"));

    Observable.zip(observable1, observable2, observable3, new Func3<MockData, MockData, MockData, String>() {
      @Override
      public String call(MockData mockData, MockData mockData2, MockData mockData3) {
        return mockData.getName() + mockData2.getName() + mockData3.getName();
      }
    }).subscribe(new Subscriber<String>() {
      @Override
      public void onCompleted() {
        gankAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(String s) {
        items.add(new MockData(s));
      }
    });
    // result will show: "1a,", "2b.", "3c?", Only three values because least observable only has 3 values.
  }

  private void tryMergeObservables() {
    // simply merge data from different observable into one common observable, but not processing these data.
    items.clear();
    Observable<MockData> observable1 = ObservableCreator.createFromListObservable();
    Observable<MockData> observable2 = Observable.just(new MockData("a"), new MockData("b"), new MockData("c"));

    Observable.merge(observable1, observable2)
        .subscribe(new Subscriber<MockData>() {
          @Override
          public void onCompleted() {
            gankAdapter.notifyDataSetChanged();
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onNext(MockData mockData) {
            items.add(mockData);
          }
        });
  }

  private void tryFlatMapObservable() {
    items.clear();
    Observable<MockData> observable = ObservableCreator.createFromListObservable();

    // value in Observable will get another observable.
    observable.flatMap(new Func1<MockData, Observable<String>>() {
      @Override
      public Observable<String> call(MockData mockData) {
        // use this value to fetch another observable,but flatmap() won't maintain the exact order of fetched observables.
        // if you want to keep the exact order, plz use concatmap()
        return Observable.just(mockData.getName() + System.currentTimeMillis());
      }
    }).subscribe(new Subscriber<String>() {
      @Override
      public void onCompleted() {
        gankAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(String s) {
        items.add(new MockData(s));
      }
    });

  }

  private void tryMapObservable() {
    items.clear();
    Observable<MockData> observable = ObservableCreator.createFromListObservable();
    
    observable.filter(new Func1<MockData, Boolean>() {
      @Override
      public Boolean call(MockData mockData) {
        return mockData != null;
      }
    }).map(new Func1<MockData, TransformedMockData>() {
      @Override
      public TransformedMockData call(MockData mockData) {
        return new TransformedMockData(mockData.getName(), mockData.getName().length());
      }
    }).subscribe(new Subscriber<TransformedMockData>() {
      @Override
      public void onCompleted() {
        gankAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(TransformedMockData transformedGankItem) {
        items.add(transformedGankItem);
      }
    });
    
  }

  private void tryFilterObservable() {
    items.clear();
    Observable<MockData> observable = ObservableCreator.createFromListObservable();

    observable.filter(new Func1<MockData, Boolean>() {
      @Override
      public Boolean call(MockData mockData) {
        return mockData.getName().length() == 2;
      }
    }).subscribe(new Subscriber<MockData>() {
      @Override
      public void onCompleted() {
        gankAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(MockData mockData) {
        items.add(mockData);
      }
    });
  }

  private void tryDistinctObservable() {
    items.clear();
    Observable<MockData> listObservable = ObservableCreator.createFromListObservable();

    // if we want to use distinct() to delete repeated Object, we must make sure our Object implements Comparable
    listObservable.distinct().subscribe(new Subscriber<MockData>() {
      @Override
      public void onCompleted() {
        gankAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(MockData mockData) {
        items.add(mockData);
      }
    });
  }

  private void tryNormalObservable() {
    items.clear();
    Observable<MockData> normalObservable = ObservableCreator.createNormalObservable();

    Log.i(RXJAVA_DEBUG_JAY, "ready to subscribe normalObservable ");
    normalObservable.subscribe(new Subscriber<MockData>() {
      @Override
      public void onCompleted() {
        Log.i(RXJAVA_DEBUG_JAY, "onCompleted");
      }

      @Override
      public void onError(Throwable e) {
        Log.i(RXJAVA_DEBUG_JAY, "onError error " + e.toString());
      }

      @Override
      public void onNext(MockData mockData) {
        Log.i(RXJAVA_DEBUG_JAY, "onNext mockData " + mockData.getName() + ", " + mockData.getImageUrl());
      }
    });
  }


  private void tryJustObservable() {
    items.clear();
    Observable<MockData> justObservable = ObservableCreator.createJustObservable();

    justObservable.repeat(3).toList().subscribe(new Subscriber<List<MockData>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(List<MockData> mockDatas) {
        items = mockDatas;
        gankAdapter.notifyDataSetChanged();
      }
    });
  }

  private void tryListObservable() {
    items.clear();
    Observable<List<MockData>> listObservable = ObservableCreator.createListObservable();

    Subscriber<List<MockData>> listSubscriber = new Subscriber<List<MockData>>() {
      @Override
      public void onCompleted() {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(List<MockData> gankItems) {
        items = gankItems;
        gankAdapter.notifyDataSetChanged();
      }
    };

    listObservable.subscribe(listSubscriber);
  }

  private void tryFromListObservable() {
    items.clear();
    Observable<MockData> fromListObservable = ObservableCreator.createFromListObservable();
    final List<MockData> items = new ArrayList<>();

    fromListObservable.subscribe(new Subscriber<MockData>() {
      @Override
      public void onCompleted() {
        gankAdapter.notifyDataSetChanged();
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onNext(MockData mockData) {
        items.add(mockData);
      }
    });
  }

  private class GankAdapter extends BaseAdapter {

    class ViewHolder {
      private TextView nameTextView;
    }

    private Context context;
    private List<MockData> items;

    public GankAdapter(Context context, List<MockData> items) {
      this.context = context;
      this.items = items;
    }

    @Override
    public int getCount() {
      return items.size();
    }

    @Override
    public Object getItem(int position) {
      return items.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_gank, null, false);
        viewHolder = new ViewHolder();
        viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.gank_name);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }

      viewHolder.nameTextView.setText(items.get(position).getName());

      return convertView;
    }
  }

}
