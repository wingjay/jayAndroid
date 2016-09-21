package com.wingjay.jayandroid.ndkdev;

import android.os.Bundle;
import android.widget.TextView;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;

/**
 * Created by Jay on 9/21/16.
 */
public class NdkActivity extends BaseActivity {

  @Bind(R.id.text)
  TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ndk);

    NdkUtil ndkUtil = new NdkUtil();
    textView.setText(ndkUtil.stringFromJayJni());
  }
}
