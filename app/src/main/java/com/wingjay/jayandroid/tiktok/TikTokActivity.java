package com.wingjay.jayandroid.tiktok;

import android.os.Bundle;
import android.view.View;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

/**
 * TikTokActivity 抖音
 *
 * @author wingjay
 * @date 2017/11/15
 */
public class TikTokActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiktok);
        setTitle("last minute fix: git flow");
        initViewPager();
    }

    private void initViewPager() {
        VerticalViewPager viewPager = findViewById(R.id.vertical_viewpager);
        //viewPager.setPageTransformer(false, new ZoomOutTransformer());
        //viewPager.setPageTransformer(true, new StackTransformer());
        viewPager.setAdapter(new ContentFragmentAdapter(getSupportFragmentManager()));
        //If you setting other scroll mode, the scrolled fade is shown from either side of display.
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }
}
