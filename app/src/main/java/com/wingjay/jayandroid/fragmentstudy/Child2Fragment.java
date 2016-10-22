package com.wingjay.jayandroid.fragmentstudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wingjay.jayandroid.BaseFragment;
import com.wingjay.jayandroid.R;

/**
 * Created by Jay on 10/20/16.
 */

public class Child2Fragment extends BaseFragment {
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    FrameLayout frameLayout = new FrameLayout(getContext());

    setHasOptionsMenu(true);

    View childView = new View(getContext());
    childView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
    childView.setBackgroundResource(R.color.black);
    frameLayout.addView(childView);
    return frameLayout;
  }
}
