package com.wingjay.jayandroid.richlist.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.RichViewHolder;
import com.wingjay.jayandroid.richlist.bean.Mv;
import com.wingjay.jayandroid.richlist.listview.IRichListViewHolder;

/**
 * MvViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
@RichViewHolder(key = "MvViewHolder")
public class MvViewHolder implements IRichListViewHolder {

    private Context context;
    public MvViewHolder(Context context) {
        this.context = context;
    }

    @Override
    public View initView(ViewGroup root) {
        View view = LayoutInflater.from(context).inflate(R.layout.vh_mv, root, false);
        return view;
    }

    @Override
    public void bindData(@NonNull Object data) {
        if (data instanceof Mv) {
            Mv mv = (Mv) data;
        }
    }
}
