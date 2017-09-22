package com.wingjay.jayandroid.richlist.listview;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * IRichListViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
public interface IRichListViewHolder {
    View initView(ViewGroup root);
    void bindData(@NonNull Object data);
}
