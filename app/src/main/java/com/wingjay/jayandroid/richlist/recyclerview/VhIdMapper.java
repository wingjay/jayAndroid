package com.wingjay.jayandroid.richlist.recyclerview;

import java.util.HashMap;

import com.wingjay.jayandroid.richlist.listview.IRichListViewHolder;

/**
 * VhIdMapper
 *
 * @author wingjay
 * @date 2017/09/22
 */
public class VhIdMapper {

    private static HashMap<String, Class<? extends IRichListViewHolder>> viewHolderMap = new HashMap<>();

    public static void init() {
    }

    //public static void register(@NonNull IRichListViewHolder viewHolder) {
    //    Class<? extends IRichListViewHolder> clazz = viewHolder.getClass();
    //    if (clazz.isAnnotationPresent(RichViewHolder.class)) {
    //        RichViewHolder richViewHolder = clazz.getAnnotation(RichViewHolder.class);
    //        viewHolderMap.put(richViewHolder.key(), clazz);
    //    }
    //}

    public static Class<? extends IRichListViewHolder> idToClass(String id) {
        return viewHolderMap.get(id);
        //
        //switch (id) {
        //    case VhIdConstants.ChineseSongViewHolder_VH_ID: return ChineseSongViewHolder.class;
        //    case VhIdConstants.EnglishSongViewHolder_VH_ID : return EnglishSongViewHolder.class;
        //    case VhIdConstants.FemaleArtistViewHolder_VH_ID : return FemaleArtistViewHolder.class;
        //    case VhIdConstants.MaleArtistViewHolder_VH_ID : return MaleArtistViewHolder.class;
        //    case VhIdConstants.MvViewHolder_VH_ID : return MvViewHolder.class;
        //    default:
        //        return null;
        //}
    }
}
