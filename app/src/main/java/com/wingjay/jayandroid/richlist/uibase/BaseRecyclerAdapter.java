package com.wingjay.jayandroid.richlist.uibase;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

/**
 * BaseRecyclerAdapter
 *
 * @author wingjay
 * @date 2017/09/22
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolderDelegate> {

    private static final String INFO_TAG = "Rich_Recycler_INFO";
    private static final String PERFORM_TAG = "Rich_Recycler_PERFORM";

    private List<Object> dataList;
    private List<String> viewHolderClassNameList; // IRichViewHolder class name

    public void setData(List<Object> dataList) {
        this.dataList = dataList;
        init();
        notifyItemRangeChanged(0, dataList.size());
    }
    
    private void init() {
        if (dataList == null || dataList.size() <= 0) {
            viewHolderClassNameList = null;
            return;
        }
        viewHolderClassNameList = new ArrayList<>();
        for (Object d : dataList) {
            String clazz = RichViewHolderFactory.match(d);
            if (!viewHolderClassNameList.contains(clazz)) {
                viewHolderClassNameList.add(clazz);
            }
        }
    }
    
    @Override
    public int getItemViewType(int position) {
        return viewHolderClassNameList != null
            ? viewHolderClassNameList.indexOf(RichViewHolderFactory.match(dataList.get(position)))
            : 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public RecyclerViewHolderDelegate onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            String className = viewHolderClassNameList.get(viewType);
            Class<?> viewHolderClass = Class.forName(className);
            Constructor constructor = viewHolderClass.getConstructor(Context.class);
            constructor.setAccessible(true);
            IRichViewHolder realViewHolder = (IRichViewHolder) constructor.newInstance(parent.getContext());
            Log.e(PERFORM_TAG, String.format("createViewHolder viewType: %s, viewHolder: %s",
                viewType, realViewHolder.getClass().getSimpleName()));
            return new RecyclerViewHolderDelegate(realViewHolder.initView(parent), realViewHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderDelegate holder, int position) {
        Log.e(PERFORM_TAG, String.format("bindViewHolder position: %s ,viewholder: %s", position, holder.toString()));
        holder.bindData(dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public void onViewRecycled(RecyclerViewHolderDelegate holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerViewHolderDelegate holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolderDelegate holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolderDelegate holder) {
        super.onViewDetachedFromWindow(holder);
    }
}
