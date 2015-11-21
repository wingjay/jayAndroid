package com.wingjay.jayandroid.eventdispatch;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wingjay on 10/26/15.
 */
public class EventDispatchActivity extends BaseActivity {

    @Bind(R.id.horizontal_scroll_view)
    MyHorizontalScrollView horizontalScrollView;

    ActionBar actionBar;
    int actionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_dispatch);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBarHeight = actionBar.getHeight();
        }

        // add three listView in horizontalScrollView
        int screenWidth = DisplayUtil.getScreenWidth(this);
        for (int i=0; i<3; i++) {
            MyListView listView = new MyListView(horizontalScrollView.getContext());

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            listView.setLayoutParams(layoutParams);

            listView.setAdapter(new MyAdapter(this));

            listView.setOnScrollUpDownListener(new MyListView.OnScrollUpDownListener() {
                @Override
                public void onScrollDown() {
                    showActionBarStatus(false);
                }

                @Override
                public void onScrollUp() {
                    showActionBarStatus(true);
                }
            });

            listView.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 2), 0));
            horizontalScrollView.addView(listView);
        }
    }

    private void showActionBarStatus(boolean show) {
        Log.i("MyHorizontalScrollView", "show " + show);

        if ((show && actionBar.isShowing()) ||
                (!show && !actionBar.isShowing())) {
            return;
        }

        if (show) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    private static List<Integer> dataList = new ArrayList<>();

    static {
        for (int i=0; i<20; i++) {
            dataList.add(i);
        }
    }

    private class MyAdapter extends BaseAdapter {

        class ViewHolder {
            TextView textView;
        }

        Context context;
        public MyAdapter(Context context) {
            this.context = context;
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
                convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.textView.setText(String.valueOf(dataList.get(position)));
            return convertView;
        }
    }

}
