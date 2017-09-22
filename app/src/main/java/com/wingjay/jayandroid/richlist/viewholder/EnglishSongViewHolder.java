package com.wingjay.jayandroid.richlist.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.RichViewHolder;
import com.wingjay.jayandroid.richlist.bean.Song;
import com.wingjay.jayandroid.richlist.listview.IRichListViewHolder;
import com.wingjay.jayandroid.richlist.recyclerview.VhIdMapper;

/**
 * EnglishSongViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
@RichViewHolder(key = "EnglishSongViewHolder")
public class EnglishSongViewHolder implements IRichListViewHolder {

    private Context context;
    private TextView title;

    public EnglishSongViewHolder(Context context) {
        this.context = context;
        VhIdMapper.register(this);
    }

    @Override
    public View initView(ViewGroup root) {
        View view = LayoutInflater.from(context).inflate(R.layout.vh_english_song, root, false);
        title = view.findViewById(R.id.title);
        return view;
    }

    @Override
    public void bindData(@NonNull Object data) {
        if (data instanceof Song) {
            Song song = (Song) data;
            title.setText(song.title);
        }
    }
}
