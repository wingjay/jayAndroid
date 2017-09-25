package com.wingjay.jayandroid.richlist.test.listview;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.wingjay.jayandroid.richlist.uibase.IRichViewHolder;
import com.wingjay.jayandroid.richlist.uibase.RichViewHolderFactory;

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

    private List<String> viewHolderClassNameList;

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
            viewHolderClassNameList = new ArrayList<>(0);
            return;
        }

        viewHolderClassNameList = new ArrayList<>(dataList.size());
        for (Object d : dataList) {
            String vh = RichViewHolderFactory.match(d);
            if (!viewHolderClassNameList.contains(vh)) {
                viewHolderClassNameList.add(vh);
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
        IRichViewHolder viewHolder;
        if (view == null
            || !(view.getTag().getClass().getName().equals(viewHolderClassNameList.get(getItemViewType(position))))) {
            //Class<? extends IRichViewHolder> clazz = viewHolderClassNameList.get(getItemViewType(position));
            String className = viewHolderClassNameList.get(getItemViewType(position));
            try {
                Class<?> clazz = Class.forName(className);
                Constructor constructor = clazz.getConstructor(Context.class);
                constructor.setAccessible(true);
                viewHolder = (IRichViewHolder) constructor.newInstance(viewGroup.getContext());
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
        viewHolder = (IRichViewHolder) view.getTag();
        viewHolder.bindData(data, position);
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return viewHolderClassNameList == null ? 1 : viewHolderClassNameList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewHolderClassNameList == null ? 0 : viewHolderClassNameList.indexOf(RichViewHolderFactory.match(getItem(position)));
    }
}
