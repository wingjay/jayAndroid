package com.wingjay.jayandroid.fab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.fastblur.FastBlurUtil;
import com.wingjay.jayandroid.fastblur.ViewBlurHelper;

import butterknife.Bind;

/**
 * This Activity is used to show FabMenu.
 */
public class FabActivity extends BaseActivity {

  @Bind(R.id.fab_menu_toolbar)
  Toolbar toolbar;

  ViewGroup fabMenuRoot;

  @Bind(R.id.do_blur_btn)
  Button doBlurBtn;

  @Bind(R.id.item_fab_menu_image)
  ImageView imageView;

  String[] imageUrls = {
    "https://drscdn.500px.org/photo/3706985/m%3D2048/d9cba44c9d66f27d27e1628fa7f4606c",
    "https://drscdn.500px.org/photo/78381677/q%3D80_m%3D2000/fdcff9445034ced1fe48fd5f956a124e",
    "https://drscdn.500px.org/photo/40352952/q%3D80_m%3D2000/b8ea73af7f5aef17d99af0e0bf1ee1b0",
    "http://wingjay.com/img/%E4%BA%A4%E5%A4%A7%EF%BC%8D%E5%BD%B1/library.jpg",
    "http://wingjay.com/img/%E8%AF%B4%E4%B8%80%E8%AF%B4%E6%88%91%E5%92%8C%E5%8D%9A%E5%AE%A2.jpg"
  };

  private int imageUrlIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fab_menu);

    fabMenuRoot = (ViewGroup) findViewById(android.R.id.content).getRootView();

    final ViewGroup blurRootView = (ViewGroup) findViewById(R.id.fab_menu_root);

    toolbar.setTitle("Wingjay FloatingActionsMenus");
    toolbar.setBackgroundColor(Color.BLACK);
    toolbar.setTitleTextColor(Color.WHITE);
    setSupportActionBar(toolbar);

    LinearLayout header = new LinearLayout(this);
    header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    header.setOrientation(LinearLayout.VERTICAL);
    header.setPadding(20, 10, 20, 10);

    TextView tipTextView = new TextView(this);
    tipTextView.setText("Hi, Here is WJFloatingActionsMenu! " +
        "It works as Android FloatingActionsMenu, but also supports \n1. Auto-Hide based on the scroll of listview," +
        "\n2. Auto-Create a blur layer below this Menu button, \nwhich makes it easy for user to read the info on the Fabs." +
        "\nWhat's more, you can customize your animation for the auto-hide and blur layer style.");
    tipTextView.setTextColor(Color.DKGRAY);
    tipTextView.setTextSize(20);

    header.addView(tipTextView);

//    imageView.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Picasso.with(FabActivity.this).load(getImageUrl()).into(imageView);
//      }
//    });

    doBlurBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        blurView(blurRootView);
//        blurView(fabMenuRoot);
        blurImage(R.drawable.medium_nm_1);
      }
    });
  }

  private void blurImage(@DrawableRes int res) {
    final Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), res);

//    scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
//        originBitmap.getWidth() / scaleRatio,
//        originBitmap.getHeight() / scaleRatio,
//        false);
//    blurBitmap = FastBlurUtil.doBlur(scaledBitmap, 8, true);
    final Handler handler = new Handler(Looper.getMainLooper()) {
      @Override
      public void handleMessage(Message msg) {
        Bitmap blurBitmap = (Bitmap) msg.obj;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (blurBitmap != null) {
          imageView.setImageBitmap(blurBitmap);
        }
        super.handleMessage(msg);
      }
    };
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        long start = System.currentTimeMillis();
        Bitmap scaledBitmap, blurBitmap = originBitmap;
        int scaleRatio = 10;
        for (int i=0; i<300; i++) {
          scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
              originBitmap.getWidth() / scaleRatio,
              originBitmap.getHeight() / scaleRatio,
              false);
          blurBitmap = FastBlurUtil.doBlur(scaledBitmap, 8, true);
          Log.i("blurtime", "blur time: " + String.valueOf(System.currentTimeMillis() - start));
        }
        Message message = new Message();
        message.obj = blurBitmap;
        handler.sendMessage(message);
      }
    });
    thread.start();
  }

  private String getImageUrl() {
    if (imageUrlIndex == imageUrls.length) {
      imageUrlIndex = 0;
    }
    return imageUrls[imageUrlIndex++];
  }

  private void blurView(final ViewGroup root) {
    final ViewBlurHelper viewBlurHelper = new ViewBlurHelper(root);
//    imageView.setImageBitmap(viewBlurHelper.getSmallScreenshotBitmap());
//    imageView.setBackgroundColor(Color.RED);
    viewBlurHelper.blur();
    viewBlurHelper.getBlurImageView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        viewBlurHelper.removeBlurLayer();
      }
    });
  }
}
