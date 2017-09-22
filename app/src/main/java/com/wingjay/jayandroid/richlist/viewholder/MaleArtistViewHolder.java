package com.wingjay.jayandroid.richlist.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.RichViewHolder;
import com.wingjay.jayandroid.richlist.bean.Artist;
import com.wingjay.jayandroid.richlist.bean.LocalSong;
import com.wingjay.jayandroid.richlist.listview.IRichListViewHolder;

/**
 * MaleArtistViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
@RichViewHolder(key = "MaleArtistViewHolder")
public class MaleArtistViewHolder implements IRichListViewHolder {
    private Context context;
    private TextView name;

    public MaleArtistViewHolder(Context context) {
        this.context = context;
    }

    @Override
    public View initView(ViewGroup root) {
        View view = LayoutInflater.from(context).inflate(R.layout.vh_male_artist, root, false);
        name = view.findViewById(R.id.name);
        return view;
    }

    @Override
    public void bindData(@NonNull Object data) {
        if (data instanceof Artist) {
            Artist artist = (Artist) data;
            name.setText(artist.name);
        } else if (data instanceof LocalSong) {
            LocalSong localSong = (LocalSong) data;
            name.setText(localSong.title);
        }
    }
}
