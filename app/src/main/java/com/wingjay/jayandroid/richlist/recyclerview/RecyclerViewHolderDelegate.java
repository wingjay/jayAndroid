package com.wingjay.jayandroid.richlist.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.wingjay.jayandroid.richlist.listview.IRichListViewHolder;

/**
 * IRichRecyclerViewHolder
 *
 * @author wingjay
 * @date 2017/09/22
 */
public class RecyclerViewHolderDelegate extends ViewHolder {
    private IRichListViewHolder realViewHolder;

    public RecyclerViewHolderDelegate(@NonNull View itemView,
                                      @NonNull IRichListViewHolder realViewHolder) {
        super(itemView);
        this.realViewHolder = realViewHolder;
    }

    public void bindData(Object data) {
        if (data instanceof RichItem) {
            realViewHolder.bindData(((RichItem) data).getData());
        } else {
            realViewHolder.bindData(data);
        }
    }

    @Override
    public String toString() {
        return realViewHolder.getClass().getSimpleName();
    }
}
