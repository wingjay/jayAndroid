package com.wingjay.wingjay_skin_loader;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

/**
 * SkinView, store skin-enable View and its skin-supportive-attrs.
 *
 * It can apply skin resource provided by SkinManager into its inner View.
 *
 * @author wingjay
 * @date 2017/08/04
 */
public class SkinView {
    private Context context;
    View originView;
    String attrName;
    int resId;
    String resTypeName;
    String resEntryName;

    public SkinView(@NonNull View originView, @NonNull String attrName, int resId) {
        this.context = originView.getContext();
        this.originView = originView;
        this.attrName = attrName;
        this.resId = resId;
        this.resTypeName = this.context.getResources().getResourceTypeName(resId); // color
        this.resEntryName = this.context.getResources().getResourceEntryName(resId); // red
    }

    public void apply() {
        switch (attrName) {
            case SupportedAttrName.BACKGROUND:
                handleBackground();
                break;
            case SupportedAttrName.TEXT_COLOR:
                handleTextColor();
            default: break;
        }
    }

    private void handleTextColor() {
        if (!(this.originView instanceof TextView)) {
            return;
        }
        if ("color".equals(this.resTypeName)) {
            ((TextView) this.originView).setTextColor(SkinResourceManager.getColor(this.resId));
        } else if ("drawable".equals(this.resTypeName)) {
            //
        }
    }

    private void handleBackground() {
        if ("color".equals(this.resTypeName)) {
            this.originView.setBackgroundColor(SkinResourceManager.getColor(this.resId));
        } else if ("drawable".equals(this.resTypeName)) {
            if (VERSION.SDK_INT < 16) {
                this.originView.setBackgroundDrawable(SkinResourceManager.getDrawable(this.resId));
            } else {
                this.originView.setBackground(SkinResourceManager.getDrawable(this.resId));
            }
        }
    }
}
