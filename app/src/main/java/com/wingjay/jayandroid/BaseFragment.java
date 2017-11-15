package com.wingjay.jayandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Jay on 1/2/16.
 */
public class BaseFragment extends Fragment {

  protected String TAG = getClass().getSimpleName();

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.d("jaydebug", TAG + "onAttach");
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(view);
    Log.d("7.0/r1 jaydebug", TAG + "onViewCreated");
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("jaydebug", TAG + "onCreate");
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d("jaydebug", TAG + "onCreateView");
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    Log.d("jaydebug", TAG + "onCreateOptionsMenu  getActivity() == null? " + (getActivity() == null));
  }

  @Override
  public void onDestroy() {
    Log.d("jaydebug", TAG + "onDestroy");
    super.onDestroy();
  }

  @Override
  public void onDetach() {
    Log.d("jaydebug", TAG + "onDetach");
    super.onDetach();
  }
}
