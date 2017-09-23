package com.wingjay.jayandroid.richlist.v5;

import com.wingjay.jayandroid.richlist.liveroom.viewholder.MvViewHolder;
import com.wingjay.jayandroid.richlist.uibase.IRichViewHolder;
import com.wingjay.jayandroid.richlist.uibase.IViewHolderMapper;
import com.wingjay.jayandroid.richlist.v5.viewholder.ChineseSongViewHolder;
import com.wingjay.jayandroid.richlist.v5.viewholder.EnglishSongViewHolder;

/**
 * ViewHolderMapperImpl, should be generated.
 *
 * @author wingjay
 * @date 2017/09/23
 */
public class ViewHolderMapperImpl implements IViewHolderMapper {
    @Override
    public Class<? extends IRichViewHolder> match(String key) {
        switch (key) {
            // viewHolder from v5
            case "ChineseSongViewHolder": return ChineseSongViewHolder.class;
            case "EnglishSongViewHolder": return EnglishSongViewHolder.class;

            // viewHolder from liveroom
            case "MvViewHolder": return MvViewHolder.class;
            default: return null;
        }
    }
}
