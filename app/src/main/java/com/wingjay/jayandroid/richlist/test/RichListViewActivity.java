package com.wingjay.jayandroid.richlist.test;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.bean.Artist;
import com.wingjay.jayandroid.richlist.bean.LocalSong;
import com.wingjay.jayandroid.richlist.bean.Mv;
import com.wingjay.jayandroid.richlist.bean.Song;
import com.wingjay.jayandroid.richlist.listview.BaseRichListAdapter;
import com.wingjay.jayandroid.richlist.recyclerview.BaseRecyclerAdapter;
import com.wingjay.jayandroid.richlist.recyclerview.RichItem;

/**
 * RichListViewActivity
 *
 * @author wingjay
 * @date 2017/09/21
 */
public class RichListViewActivity extends BaseActivity {

    Button switcher;
    ListView listView;
    RecyclerView recyclerView;

    boolean showListView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_list_view);

        switcher = findViewById(R.id.switcher);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listView = findViewById(R.id.listview);

        switcher.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showListView = !showListView;
                switchVisibility();
            }
        });

        setupListView();
        setupRecyclerView();
    }

    private void setupListView() {
        BaseRichListAdapter adapter = new BaseRichListAdapter(this);
        listView.setAdapter(adapter);
        adapter.setData(loadListData());
    }

    private void setupRecyclerView() {
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setData(loadRecyclerData());
    }

    private void switchVisibility() {
        if (showListView) {
            listView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    private List<Object> loadRecyclerData() {
        List<Object> dataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            dataList.add(RichItem.create(new Song("中文歌曲: "+i), "ChineseSongViewHolder"));
            dataList.add(RichItem.create(new Song("英文歌曲: "+i), "EnglishSongViewHolder"));
            dataList.add(new LocalSong("localSong: " + i));
            dataList.add(new Mv());
        }
        return dataList;
    }

    private List<Object> loadListData() {
        List<Object> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add(new Song("歌曲: "+i));
            dataList.add(new Artist("艺人: " + i));
            dataList.add(new Mv());
        }

        return dataList;
    }
}
