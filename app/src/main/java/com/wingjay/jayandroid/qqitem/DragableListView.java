package com.wingjay.jayandroid.qqitem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wingjay on 10/26/15.
 */
public class DragableListView extends ListView {

    public DragableListView(Context context) {
        this(context, null);
    }

    public DragableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
