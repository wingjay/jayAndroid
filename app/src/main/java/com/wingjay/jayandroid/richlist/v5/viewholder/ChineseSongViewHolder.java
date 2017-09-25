package com.wingjay.jayandroid.richlist.v5.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.lego_annotation.RichViewHolder;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.constants.RichViewHolderConstant;
import com.wingjay.jayandroid.richlist.uibase.IRichViewHolder;
import com.wingjay.jayandroid.richlist.v5.bean.Song;

/**
 * ChineseSongViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
@RichViewHolder(key = RichViewHolderConstant.ChineseSongViewHolder, bean = Song.class)
public class ChineseSongViewHolder implements IRichViewHolder {

    private Context context;
    private TextView title;

    public ChineseSongViewHolder(Context context) {
        this.context = context;
    }

    @Override
    public View initView(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.vh_chinese_song, parent, false);
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
