package com.wingjay.jayandroid.richlist.recyclerview;

/**
 * VhIdConstants
 *
 * @author wingjay
 * @date 2017/09/22
 */
public enum VhIdConstants {
    ChineseSongViewHolder_VH_ID(1),
    EnglishSongViewHolder_VH_ID(2),
    FemaleArtistViewHolder_VH_ID(3),
    MaleArtistViewHolder_VH_ID(4),
    MvViewHolder_VH_ID(5);

    long id;
    VhIdConstants(long id) {
        this.id = id;
    }
}
