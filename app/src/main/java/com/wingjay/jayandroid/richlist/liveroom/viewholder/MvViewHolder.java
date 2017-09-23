package com.wingjay.jayandroid.richlist.liveroom.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lego_annotation.RichViewHolder;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.richlist.liveroom.bean.Mv;
import com.wingjay.jayandroid.richlist.uibase.IRichViewHolder;

/**
 * MvViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
@RichViewHolder(key = "MvViewHolder", bean = Mv.class)
public class MvViewHolder implements IRichViewHolder {

    private Context context;
    public MvViewHolder(Context context) {
        this.context = context;
    }

    @Override
    public View initView(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.vh_mv, parent, false);
        return view;
    }

    @Override
    public void bindData(@NonNull Object data, int position) {
        if (data instanceof Mv) {
            Mv mv = (Mv) data;
        }
    }
}
