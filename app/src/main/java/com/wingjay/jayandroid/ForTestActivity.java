package com.wingjay.jayandroid;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

/**
 * Created by Jay on 5/10/17.
 */

public class ForTestActivity extends BaseActivity {

  private View parent;
  private View floatingView, showHideBt, bt2, bt3, bt3Container;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    parent = findViewById(R.id.parent);
    floatingView = findViewById(R.id.floating_view);
    showHideBt = findViewById(R.id.bt1);
    bt2 = findViewById(R.id.bt2);
    bt3 = findViewById(R.id.bt3);
    bt3Container = findViewById(R.id.bt3_container);

    showHideBt.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        toggle();
      }
    });
    bt2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ForTestActivity.this, "bt2", Toast.LENGTH_SHORT).show();
      }
    });
    bt3.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(ForTestActivity.this, "bt3", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void toggle() {
    floatingView.setVisibility(floatingView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    if (floatingView.getVisibility() == View.VISIBLE) {
      bt3Container.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          floatingView.setVisibility(View.GONE);
        }
      });
      parent.setOnTouchListener(new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
          if (floatingView.getVisibility() == View.VISIBLE) {
            floatingView.setVisibility(View.GONE);
            return true;
          }
          return false;
        }
      });
    } else {
      bt3Container.setClickable(false);
      parent.setOnTouchListener(null);
    }
  }
}
