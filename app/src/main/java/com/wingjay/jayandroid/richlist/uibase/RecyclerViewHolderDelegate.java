package com.wingjay.jayandroid.richlist.uibase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

/**
 * IRichRecyclerViewHolder
 *
 * @author wingjay
 * @date 2017/09/22
 */
public class RecyclerViewHolderDelegate extends ViewHolder {
    private IRichViewHolder realViewHolder;

    public RecyclerViewHolderDelegate(@NonNull View itemView,
                                      @NonNull IRichViewHolder realViewHolder) {
        super(itemView);
        this.realViewHolder = realViewHolder;
    }

    public void bindData(Object data, int position) {
        if (data instanceof RichItem) {
            realViewHolder.bindData(((RichItem) data).getData(), position);
        } else {
            realViewHolder.bindData(data, position);
        }
    }

    @Override
    public String toString() {
        return realViewHolder.getClass().getSimpleName();
    }
}
