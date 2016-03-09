package com.wingjay.jayandroid.fab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;
import com.squareup.picasso.Picasso;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.fastblur.FastBlurUtil;
import com.wingjay.jayandroid.fastblur.ViewBlurHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    doBlurBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        blurView(fabMenuRoot);
      }
    });

    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Picasso.with(FabActivity.this).load(getImageUrl()).into(imageView);
      }
    });
  }

  private String getImageUrl() {
    if (imageUrlIndex == imageUrls.length) {
      imageUrlIndex = 0;
    }
    return imageUrls[imageUrlIndex++];
  }

  private void blurView(final ViewGroup root) {
    final ViewBlurHelper viewBlurHelper = new ViewBlurHelper(root);
    viewBlurHelper.blur();
    viewBlurHelper.getBlurImageView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        viewBlurHelper.removeBlurLayer();
      }
    });

  }

  private class MyAdapter extends BaseAdapter {

    class ViewHolder {
      TextView textView;
      ImageView imageView;
    }

    class MyData {
      String title;
      String imageUrl;
      public MyData(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
      }
    }

    String imageUrls = "https://avatars0.githubusercontent.com/u/9619875?v=3&s=460";
    List<MyData> dataList = new ArrayList<>();

    Context context;

    public MyAdapter(Context context) {
      this.context = context;
      initData();
    }

    private void initData() {
      for (int i=0; i<20; i++) {
        MyData myData = new MyData("Hi, I'm wingjay and here is item " + i, imageUrls);
        dataList.add(myData);
      }
    }

    @Override
    public int getCount() {
      return dataList.size();
    }

    @Override
    public Object getItem(int position) {
      return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder;
      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_fab_menu_listview, null);
        viewHolder = new ViewHolder();
        viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_fab_menu_image);
        viewHolder.textView = (TextView) convertView.findViewById(R.id.item_fab_menu_title);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (ViewHolder) convertView.getTag();
      }

      MyData myData = (MyData) getItem(position);
      Picasso.with(context).load(myData.imageUrl).into(viewHolder.imageView);
      viewHolder.textView.setText(myData.title);

      return convertView;
    }
  }


}
