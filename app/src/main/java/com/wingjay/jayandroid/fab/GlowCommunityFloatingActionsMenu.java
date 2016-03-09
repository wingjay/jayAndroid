package com.wingjay.jayandroid.fab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.wingjay.jayandroid.R;

/**
 * Created by Jay on 1/1/16.
 */
public class GlowCommunityFloatingActionsMenu extends AbsFloatingActionsMenu {

  private Context context;

  public static final FloatingActionButton[] FABS = new FloatingActionButton[3];

  public static final String [] FABS_TITLES = {
      "first fab",
      "second fab",
      "third fab"
  };

  public static final int[] FABS_ICONS = {
      R.drawable.blue_heart,
      R.drawable.ic_fab_star,
      R.drawable.red_heart
  };

  public GlowCommunityFloatingActionsMenu(Context context) {
    this(context, null);
  }

  public GlowCommunityFloatingActionsMenu(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public GlowCommunityFloatingActionsMenu(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
  }

  @Override
  public void assembleAllFabs() {
    for (int i=0; i<FABS.length; i++) {
      FABS[i] = assembleFabByDefault(FABS_TITLES[i], context.getResources().getDrawable(FABS_ICONS[i]));
      final int j = i;
      FABS[i].setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          Toast.makeText(context, "Click " + FABS_TITLES[j], Toast.LENGTH_SHORT).show();
        }
      });
    }
  }

}
