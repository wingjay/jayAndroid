package com.wingjay.wingjay_skin_loader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

/**
 * SkinInflaterFactory
 * 0. Each ViewHolder has one SkinInflaterFactory;
 * 1. During inflating view, it will store all skin-enable views as SkinView in skinViewList;
 * 2. When skin updates, it will traverse all skinView, let them apply new resource from SkinManager.
 *
 * Warning: Remember when ViewHolder destroys, you should make sure this Factory MUST also be destroyed.
 *
 * android:background="@color/red" (id: @12312312)
 *      background:     attributeSet.getAttributeName(i);
 *      id @12312312:   attributeSet.getAttributeValue(i);
 *      resId 12312312: Integer.parseInt(attributeSet.getAttributeValue(i).substring(1));
 *      color:          resource.getResourceTypeName(resId)
 *      red:            resource.getResourceEntryName(resId)
 *
 * @author wingjay
 * @date 2017/08/03
 */
public class SkinInflaterFactory implements Factory {

    private static final String skinNameSpace = "http://schemas.android.com/android/skin";
    private List<SkinView> skinViewList = new ArrayList<>();
    private Resources systemResource;

    public SkinInflaterFactory(Context context) {
        systemResource = context.getResources();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {
        // check if enable is true
        boolean enable = attributeSet.getAttributeBooleanValue(skinNameSpace, "enable", false);
        if (!enable) {
            return null;
        }

        View view = createView(name, context, attributeSet);

        // fetch all attrs whose value is like @color/ @drawable
        parseAttrs(view, attributeSet);

        return view;
    }

    /**
     * let all saved view use new resource.
     */
    public void updateSkin() {
        for (SkinView skinView : skinViewList) {
            skinView.apply();
        }
    }

    /**
     * when view holder destroy, better to invoke {@link #clean()} to avoid memory-leak
     */
    public void clean() {
        skinViewList.clear();
    }

    private void parseAttrs(View targetView, AttributeSet attributeSet) {
        // store all to-be-skined attrs
        int attrsCount = attributeSet.getAttributeCount();
        for (int i = 0; i < attrsCount; i++) {
            // android:background="@color/red" as example
            String attrName = attributeSet.getAttributeName(i); // background

            // not support: android:textSize etc.
            if (!SupportedAttrName.isSupport(attrName)) {
                continue;
            }

            // not support: android:background="#FF00FF", must be @1239812398 style
            String attrValue = attributeSet.getAttributeValue(i);
            if (!attrValue.startsWith("@")) {
                continue;
            }

            // extract resId
            int resId = Integer.parseInt(attrValue.substring(1));

            // assemble SkinView
            // systemResource use resId to get resource type name
            SkinView skinView = new SkinView(targetView, attrName, resId);
            skinViewList.add(skinView);
        }
    }

    private View createView(String name, Context context, AttributeSet attributeSet) {
        View v = null;
        try {
            if (-1 == name.indexOf('.')) {
                // system view
                if ("View".equals(name)) {
                    v = LayoutInflater.from(context).createView(name, "android.view.", attributeSet);
                }
                if (v == null) {
                    v = LayoutInflater.from(context).createView(name, "android.widget.", attributeSet);
                }
                if (v == null) {
                    v = LayoutInflater.from(context).createView(name, "android.webkit.", attributeSet);
                }
            } else {
                // customized view
                v = LayoutInflater.from(context).createView(name, null, attributeSet);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return v;
    }

}
