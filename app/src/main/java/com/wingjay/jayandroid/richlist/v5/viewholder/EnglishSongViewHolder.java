package com.wingjay.jayandroid.richlist.v5.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.uibase.RichViewHolder;
import com.wingjay.jayandroid.richlist.v5.bean.Song;
import com.wingjay.jayandroid.richlist.uibase.IRichViewHolder;

/**
 * EnglishSongViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
@RichViewHolder(key = "EnglishSongViewHolder", bean = Song.class)
public class EnglishSongViewHolder implements IRichViewHolder {

    private Context context;
    private TextView title;

    public EnglishSongViewHolder(Context context) {
        this.context = context;
    }

    @Override
    public View initView(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.vh_english_song, parent, false);
        title = view.findViewById(R.id.title);
        return view;
    }

    @Override
    public void bindData(@NonNull Object data, int position) {
        if (data instanceof Song) {
            Song song = (Song) data;
            title.setText(song.title);
        }
    }
}
