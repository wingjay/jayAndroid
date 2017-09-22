package com.wingjay.jayandroid.richlist.listview;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.wingjay.jayandroid.richlist.recyclerview.RichViewHolderFactory;

/**
 * BaseRichListAdapter:
 *
 * dataList <--------- Adapter --------> ViewHolder
 *
 * @author wingjay
 * @date 2017/09/21
 */
public class BaseRichListAdapter extends BaseAdapter {

    private static final String TAG = "listview_metric";

    private final Context context;
    private List<Object> dataList = new ArrayList<>(0);

    private List<Class<? extends IRichListViewHolder>> viewTypeList;

    public BaseRichListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Object> dataList) {
        this.dataList = dataList;
        initViewType();
        notifyDataSetChanged();
    }

    private void initViewType() {
        if (dataList == null || dataList.size() <= 0) {
            viewTypeList = new ArrayList<>(0);
            return;
        }

        viewTypeList = new ArrayList<>(dataList.size());
        for (Object d : dataList) {
            Class<? extends IRichListViewHolder> vh = RichViewHolderFactory.match(d);
            if (!viewTypeList.contains(vh)) {
                viewTypeList.add(vh);
            }
        }
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Object data = getItem(position);
        IRichListViewHolder viewHolder;
        if (view == null || !(view.getTag().getClass().equals(viewTypeList.get(getItemViewType(position))))) {
            Class<? extends IRichListViewHolder> clazz = viewTypeList.get(getItemViewType(position));
            try {
                Constructor constructor = clazz.getConstructor(Context.class);
                constructor.setAccessible(true);
                viewHolder = (IRichListViewHolder) constructor.newInstance(viewGroup.getContext());
                // lots of item keeps recreating, bad performance.
                Log.e(TAG, String.format("initView for position: %s, viewHolder: %s",
                    position, viewHolder.getClass().getSimpleName()));
                view = viewHolder.initView(viewGroup);
                view.setTag(viewHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (view == null) {
            return null;
        }
        viewHolder = (IRichListViewHolder) view.getTag();
        viewHolder.bindData(data);
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return viewTypeList == null ? 1 : viewTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypeList == null ? 0 : viewTypeList.indexOf(RichViewHolderFactory.match(getItem(position)));
    }
}
