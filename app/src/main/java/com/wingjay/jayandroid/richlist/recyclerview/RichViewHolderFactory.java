package com.wingjay.jayandroid.richlist.recyclerview;

import com.wingjay.jayandroid.richlist.bean.Mv;
import com.wingjay.jayandroid.richlist.listview.IRichListViewHolder;
import com.wingjay.jayandroid.richlist.viewholder.MvViewHolder;

/**
 * RichViewHolderFactory
 *
 * @author wingjay
 * @date 2017/09/21
 */
public class RichViewHolderFactory {

    /**
     * Song -> ChineseSongViewHolder + EnglishSongViewHolder; -> RichItem.
     * Artist -> MaleArtistViewHolder + FemaleArtistViewHolder; -> RichItem
     *
     * Mv -> MvViewHolder, auto-generated.
     */
    public static Class<? extends IRichListViewHolder> match(Object data) {
        if (data instanceof RichItem) {
            return VhIdMapper.idToClass(((RichItem) data).getViewHolderId());
        }

        String className = data.getClass().getName();
        if (Mv.class.getName().equals(className)) {
            return MvViewHolder.class;
        }

        return null;
    }
}
