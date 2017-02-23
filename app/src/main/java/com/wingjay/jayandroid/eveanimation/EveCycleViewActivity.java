package com.wingjay.jayandroid.eveanimation;

import android.content.Intent;
import android.os.Bundle;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

/**
 * Created by Jay on 11/18/16.
 */

public class EveCycleViewActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_eve_cycle_view);

    startActivity(new Intent(this, EveHomeActivity.class));
  }
}
