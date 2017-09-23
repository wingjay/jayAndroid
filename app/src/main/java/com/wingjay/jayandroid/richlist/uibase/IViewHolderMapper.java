package com.wingjay.jayandroid.richlist.uibase;

/**
 * IViewHolderMapper
 *
 * @author wingjay
 * @date 2017/09/23
 */
public interface IViewHolderMapper {
    /**
     * find ViewHolder class by key
     */
    Class<? extends IRichViewHolder> match(String key);
}
