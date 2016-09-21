package com.wingjay.jayandroid.animation.searchbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jay on 4/13/16.
 */
public class TransitionAnimationToActivity extends AppCompatActivity {
    public static final String PIC_URL = "pic_url";
    public static final String TRANSIT_PIC = "picture";

    @Bind(R.id.transition_img)
    ImageView transitionImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_to);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(PIC_URL);

        ViewCompat.setTransitionName(transitionImg, TRANSIT_PIC);
        Picasso.with(this).load(url).fit().centerCrop().into(transitionImg);

    }

    public static Intent createIntent(Activity activity, String url) {
        Intent intent = new Intent(activity, TransitionAnimationToActivity.class);
        intent.putExtra(PIC_URL, url);
        return intent;
    }
}
