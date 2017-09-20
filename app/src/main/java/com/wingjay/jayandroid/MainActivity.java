package com.wingjay.jayandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wingjay.jayandroid.abot.ABotActivity;
import com.wingjay.jayandroid.animation.searchbar.LottieActivity;
import com.wingjay.jayandroid.animation.searchbar.TransitionAnimationFromActivity;
import com.wingjay.jayandroid.bitmap.BitmapTestingActivity;
import com.wingjay.jayandroid.contentprovider.ContentResolverActivity;
import com.wingjay.jayandroid.curve.CurveActivity;
import com.wingjay.jayandroid.customizeview.CustomizeViewActivity;
import com.wingjay.jayandroid.daggerForGlow.fakeApp.DaggerForGlowAppActivity;
import com.wingjay.jayandroid.drag.DragChooseActivity;
import com.wingjay.jayandroid.drawable.DrawableActivity;
import com.wingjay.jayandroid.eveanimation.EveCycleViewActivity;
import com.wingjay.jayandroid.eventdispatch.EventDispatchActivity;
import com.wingjay.jayandroid.fab.FabActivity;
import com.wingjay.jayandroid.fastblur.FastBlurActivity;
import com.wingjay.jayandroid.fragmentstudy.HolderActivity;
import com.wingjay.jayandroid.fulltextview.FullTextViewActivity;
import com.wingjay.jayandroid.gesture.GestureActivity;
import com.wingjay.jayandroid.lowpoly.LowPolyActivity;
import com.wingjay.jayandroid.ndkdev.NdkActivity;
import com.wingjay.jayandroid.perisope.PerisopeActivity;
import com.wingjay.jayandroid.qqitem.DragableActivity;
import com.wingjay.jayandroid.realm.RealmIntroActivity;
import com.wingjay.jayandroid.retrofitOkhttpUpgrade.RetrofitOkhttpUpgradeActivity;
import com.wingjay.jayandroid.rxjava.RxJavaActivity;
import com.wingjay.jayandroid.skin.AndroidSkinLoaderDemoActivity;
import com.wingjay.jayandroid.skin.JaySkinLoaderDemoActivity;
import com.wingjay.jayandroid.skin.SkinActivity;
import com.wingjay.jayandroid.statusbar.StatusBarActivity;
import com.wingjay.jayandroid.webview.WebViewActivity;
import com.wingjay.jayandroid.weex.HelloWeexActivity;

import butterknife.OnClick;
import com.wingjay.jayandroid.xiami.XiamiActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.contentProvider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContentResolverActivity.class));
            }
        });

        findViewById(R.id.gesture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GestureActivity.class));
            }
        });
//        Log.i(TAG, FastBlurJni.test());
    }

    @OnClick(R.id.xiami)
    void xiami() {
        startMyActivity(XiamiActivity.class);
    }

    @OnClick(R.id.jay_android_skin_loader)
    void jaySkinLoaderDemo() {
        startMyActivity(JaySkinLoaderDemoActivity.class);
    }

    @OnClick(R.id.android_skin_loader)
    void skinLoaderDemo() {
        startMyActivity(AndroidSkinLoaderDemoActivity.class);
    }

  @OnClick(R.id.skin)
  void skin() {
      startMyActivity(SkinActivity.class);
  }

  @OnClick(R.id.for_test)
  void test() {
    startMyActivity(ForTestActivity.class);
  }

  @OnClick(R.id.weex)
  void weex() {
    startMyActivity(HelloWeexActivity.class);
  }

  @OnClick(R.id.lottie)
  void lottie() {
    startMyActivity(LottieActivity.class);
  }

  @OnClick(R.id.eve_home_animation)
  void eveHomeAnimation() {
    startMyActivity(EveCycleViewActivity.class);
//    TaskStackBuilder.create(getApplicationContext())
//        .addNextIntent(new Intent(this, EveCycleViewActivity.class))
//        .addNextIntentWithParentStack(new Intent(this, EveHomeActivity.class))
//        .startActivities();
  }

  @OnClick(R.id.webview)
  void webview() {
    startMyActivity(WebViewActivity.class);
  }

  @OnClick(R.id.a_bot)
  void abot() {
    startMyActivity(ABotActivity.class);
  }

  @OnClick(R.id.realm)
  void realm() {
    startMyActivity(RealmIntroActivity.class);
  }

  @OnClick(R.id.fragment_holder)
  void holder() {
    startMyActivity(HolderActivity.class);
  }

    @OnClick(R.id.ndk_dev)
    void ndkDev() {
        startActivity(new Intent(MainActivity.this, NdkActivity.class));
    }

    @OnClick(R.id.low_poly)
    void lowPoly() {
        startActivity(new Intent(MainActivity.this, LowPolyActivity.class));
    }

    @OnClick(R.id.retrofit_upgrade)
    void retrofitUpgrade() {
        startActivity(new Intent(MainActivity.this, RetrofitOkhttpUpgradeActivity.class));
    }

    @OnClick(R.id.bitmap_test)
    void bitmapTest() {
        startActivity(new Intent(MainActivity.this, BitmapTestingActivity.class));
    }

    @OnClick(R.id.dagger)
    void dagger() {
        startActivity(new Intent(MainActivity.this, DaggerForGlowAppActivity.class));
    }

    @OnClick(R.id.activity_transition)
    void activityTransition() {
        startActivity(new Intent(MainActivity.this, TransitionAnimationFromActivity.class));
    }

    @OnClick(R.id.rxJava)
    void rxJava() {
        startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
    }

    @OnClick(R.id.drag_helper)
    void drag(View view) {
        startActivity(new Intent(MainActivity.this, DragChooseActivity.class));
    }

    @OnClick(R.id.drag_able)
    void dragAble() {
        startActivity(new Intent(MainActivity.this, DragableActivity.class));
    }

    @OnClick(R.id.event_dispatch)
    void eventDispatch() {
        startActivity(new Intent(MainActivity.this, EventDispatchActivity.class));
    }

    @OnClick(R.id.full_text)
    void fullText() {
        startActivity(new Intent(MainActivity.this, FullTextViewActivity.class));
    }

    @OnClick(R.id.drawable)
    void drawable() {
        startActivity(new Intent(MainActivity.this, DrawableActivity.class));
    }

    @OnClick(R.id.customize_view)
    void customizeView() {
        startActivity(new Intent(MainActivity.this, CustomizeViewActivity.class));
    }

    @OnClick(R.id.perisope_view)
    void perisipeView() {
        startActivity(new Intent(MainActivity.this, PerisopeActivity.class));
    }

    @OnClick(R.id.curve_view)
    void curveView() {
        startActivity(new Intent(MainActivity.this, CurveActivity.class));
    }

    @OnClick(R.id.fast_blur)
    void fastBlur() {
        startActivity(new Intent(MainActivity.this, FastBlurActivity.class));
    }

    @OnClick(R.id.status_bar)
    void statusBar() {
        startMyActivity(StatusBarActivity.class);
    }

    @OnClick(R.id.fabMenu)
    void fabMenu() {
        startMyActivity(FabActivity.class);
    }

    private void startMyActivity(Class clz) {
        startActivity(new Intent(MainActivity.this, clz));
    }

}
