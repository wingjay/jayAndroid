package com.wingjay.jayandroid.qqitem;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wingjay on 10/26/15.
 */
public class DragableActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragable_item);
        ListView listView = (ListView) findViewById(R.id.dragable_listview);
        listView.setAdapter(new DragableAdapter(this));

    }

    private class DragableAdapter extends BaseAdapter {

        private class ViewHolder {
            TextView content, hidden;
        }

        private List<Integer> dataList;
        private Context context;

        public DragableAdapter(Context context) {
            this.context = context;
            dataList = Arrays.asList(new Integer[]{1 ,2, 3, 4});

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
                convertView = new DragableItemView(context);
                viewHolder = new ViewHolder();
                viewHolder.content = (TextView) convertView.findViewById(R.id.dragable_content);
                viewHolder.hidden = (TextView) convertView.findViewById(R.id.dragable_hidden);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.content.setText(String.valueOf(dataList.get(position)));
            viewHolder.hidden.setText("hide");

            return convertView;
        }
    }

}
