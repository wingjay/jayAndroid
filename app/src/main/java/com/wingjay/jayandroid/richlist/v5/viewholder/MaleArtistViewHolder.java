package com.wingjay.jayandroid.richlist.v5.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.lego_annotation.RichViewHolder;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.uibase.IRichViewHolder;
import com.wingjay.jayandroid.richlist.v5.bean.Artist;

/**
 * MaleArtistViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
@RichViewHolder(key = "MaleArtistViewHolder", bean = Artist.class)
public class MaleArtistViewHolder implements IRichViewHolder {
    private Context context;
    private TextView name;

    public MaleArtistViewHolder(Context context) {
        this.context = context;
    }

    @Override
    public View initView(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.vh_male_artist, parent, false);
        name = view.findViewById(R.id.name);
        return view;
    }

    @Override
    public void bindData(@NonNull Object data, int position) {
        if (data instanceof Artist) {
            Artist artist = (Artist) data;
            name.setText(artist.name);
        }
    }
}
