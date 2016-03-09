package com.wingjay.jayandroid.fab;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * This detector can detect scroll up/down event in a given listview and call back OnListScrollListener.
 */
public class AbsListViewScrollUpDownDetector implements AbsListView.OnScrollListener {

  private OnListScrollListener onListScrollListener;
  private ListView listView;
  private int previousFirstVisibleItem;
  private int lastScrollY;
  private int scrollYDetectabltThreshold = 10;

  public AbsListViewScrollUpDownDetector(ListView listView, OnListScrollListener onListScrollListener) {
    this.listView = listView;
    this.onListScrollListener = onListScrollListener;
  }

  public boolean isEqualOnListScrollListener(OnListScrollListener onListScrollListener) {
    if (onListScrollListener == null) {
      return this.onListScrollListener == null;
    } else {
      return this.onListScrollListener.equals(onListScrollListener);
    }
  }

  @Override
  public void onScrollStateChanged(AbsListView view, int scrollState) {
  }

  @Override
  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    // detect scroll UP/DOWN
    if (totalItemCount == 0 || onListScrollListener == null) {
      return;
    }
    if (firstVisibleItem == previousFirstVisibleItem) {
      int currentScrollY = getTopItemScrollY();
      boolean detectable = Math.abs(currentScrollY - lastScrollY) > scrollYDetectabltThreshold;
      if (detectable) {
        if (currentScrollY < lastScrollY) {
          onListScrollListener.onListScrollUp();
        } else {
          onListScrollListener.onListScrollDown();
        }
      }
      lastScrollY = getTopItemScrollY();
    } else {
      if (firstVisibleItem > previousFirstVisibleItem) {
        onListScrollListener.onListScrollUp();
      } else {
        onListScrollListener.onListScrollDown();
      }

      previousFirstVisibleItem = firstVisibleItem;
      lastScrollY = getTopItemScrollY();
    }
  }

  private int getTopItemScrollY() {
    if (listView == null || listView.getChildAt(0) == null) {
      return 0;
    }
    View topChild = listView.getChildAt(0);
    return topChild.getTop();
  }

}
