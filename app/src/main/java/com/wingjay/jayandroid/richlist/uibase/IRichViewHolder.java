package com.wingjay.jayandroid.richlist.uibase;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * IRichViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
public interface IRichViewHolder {
    View initView(ViewGroup parent);
    void bindData(@NonNull Object data, int position);
}

