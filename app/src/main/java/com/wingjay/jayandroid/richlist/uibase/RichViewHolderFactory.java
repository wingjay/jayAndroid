package com.wingjay.jayandroid.richlist.uibase;

import android.support.annotation.NonNull;

/**
 * RichViewHolderFactory
 *
 * @author wingjay
 * @date 2017/09/21
 */
public class RichViewHolderFactory {

    private static IViewHolderMapper viewHolderMapper;

    public static void init(@NonNull IRichProvider iRichProvider) {
        viewHolderMapper = iRichProvider.getIViewHolderMapper();
    }

    /**
     * Song -> ChineseSongViewHolder + EnglishSongViewHolder; -> RichItem.
     * Artist -> MaleArtistViewHolder + FemaleArtistViewHolder; -> RichItem
     *
     * Mv -> MvViewHolder, auto-generated.
     */
    public static String match(Object data) {
        if (data instanceof RichItem) {
            // 指定了 ViewHolderId
            return viewHolderMapper.match(((RichItem) data).getViewHolderId());
        } else {
            // 原始数据
            //String className = data.getClass().getName();
            //if (Mv.class.getName().equals(className)) {
            //    return MvViewHolder.class;
            //}
            return null;
        }
    }
}
