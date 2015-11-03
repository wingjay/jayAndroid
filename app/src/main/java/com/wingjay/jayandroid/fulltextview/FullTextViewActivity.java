package com.wingjay.jayandroid.fulltextview;

import android.os.Bundle;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;

/**
 * Created by wingjay on 10/27/15.
 */
public class FullTextViewActivity extends BaseActivity {

    @Bind(R.id.full_text_view)
    FullTextView fullTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_text_view);

        fullTextView.build()
                .setDrawablePadding(20)
                .setTopDrawable(R.drawable.ic_launcher)
                .setBottomDrawableWithSize(R.drawable.ic_launcher, 100, 100)
                .setRightDrawable(R.drawable.ic_launcher)
                .setLeftDrawable(R.drawable.ic_launcher)
                .apply();

    }
}
