package com.wingjay.jayandroid.animation.searchbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jay on 4/13/16.
 */
public class TransitionAnimationFromActivity extends AppCompatActivity {

    @Bind(R.id.local_img)
    ImageView localImg1;

    @Bind(R.id.local_img_2)
    ImageView localImg2;

    @Bind(R.id.network_img)
    RatioImageView networkImg1;

    @Bind(R.id.network_img_2)
    RatioImageView networkImg2;

    String url1 = "https://cdn-images-1.medium.com/freeze/max/60/1*hBp9i_LZHGwKfq7plvjWxQ.jpeg?q=20";
    String url2 = "https://cdn-images-1.medium.com/max/2000/1*hBp9i_LZHGwKfq7plvjWxQ.jpeg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_from);
        ButterKnife.bind(this);

        Picasso.with(this)
                .load(url1)
                .fit()
                .centerCrop()
                .into(networkImg1);

        Picasso.with(this)
                .load(url2)
                .fit()
                .centerCrop()
                .into(networkImg2);
    }

    @OnClick(R.id.network_img)
    void click1() {
        Picasso.with(this)
                .load(url1)
                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        startTransition(networkImg1, url1);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @OnClick(R.id.network_img_2)
    void click2() {
        startTransition(networkImg2, url2);
    }

    private void startTransition(View v, String url) {
        Intent intent = TransitionAnimationToActivity.createIntent(TransitionAnimationFromActivity.this, url);
        ActivityOptionsCompat optionsCompat
                = ActivityOptionsCompat.makeSceneTransitionAnimation(
                TransitionAnimationFromActivity.this, v, TransitionAnimationToActivity.TRANSIT_PIC);
        try {
            ActivityCompat.startActivity(TransitionAnimationFromActivity.this, intent,
                    optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }
}
