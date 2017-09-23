package com.wingjay.jayandroid.richlist.liveroom;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.liveroom.bean.Mv;
import com.wingjay.jayandroid.richlist.uibase.BaseRecyclerAdapter;
import com.wingjay.jayandroid.richlist.uibase.RichItem;
import com.wingjay.jayandroid.richlist.v5.bean.LocalSong;
import com.wingjay.jayandroid.richlist.v5.bean.Song;

/**
 * LiveRoomActivity
 *
 * @author wingjay
 * @date 2017/09/23
 */
public class LiveRoomActivity extends BaseActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().setTitle("LiveRoom");
        }
        setContentView(R.layout.activity_rich_recycler_view);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setData(loadRecyclerData());
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
}
