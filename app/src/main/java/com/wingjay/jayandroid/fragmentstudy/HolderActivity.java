package com.wingjay.jayandroid.fragmentstudy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.OnClick;

/**
 * Created by Jay on 10/15/16.
 */
public class HolderActivity extends BaseActivity {

  ChildFragment childFragment = new ChildFragment();
  Child2Fragment child2Fragment = new Child2Fragment();

  boolean show = false;
  boolean first = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_fragment_holder);
    Log.d("jaydebug", "start add fragment ");
    getSupportFragmentManager().beginTransaction()
        .add(R.id.container, childFragment)
        .commitAllowingStateLoss();
    Log.d("jaydebug", "finish adding fragment ");
  }

  @Override
  public boolean onCreatePanelMenu(int featureId, Menu menu) {
    Log.d("jaydebug", " onCreatePanelMenu");
    return super.onCreatePanelMenu(featureId, menu);
  }

  @OnClick(R.id.replace)
  void replace() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    if (first) {
      Log.d("jaydebug", "add child2Fragment");
      fragmentManager.beginTransaction().add(R.id.container, child2Fragment).commitAllowingStateLoss();
      first = false;
    }

    for (Fragment fragment : fragmentManager.getFragments()) {
      Log.d("jaydebug", "hide " + fragment.getClass().toString());
      transaction.hide(fragment);
    }
    if (show) {
      transaction.show(child2Fragment);
    } else {
      transaction.show(childFragment);
    }
    transaction.commitAllowingStateLoss();
    show = !show;
  }

}
