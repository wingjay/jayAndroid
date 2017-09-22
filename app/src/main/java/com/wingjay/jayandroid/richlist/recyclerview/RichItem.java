package com.wingjay.jayandroid.richlist.recyclerview;

/**
 * RichItem
 *
 * @author wingjay
 * @date 2017/09/22
 */
public class RichItem {
    private String viewHolderId;
    private Object data;
    private RichItem(Object data, String viewHolderId) {
        this.data = data;
        this.viewHolderId = viewHolderId;
    }

    public static RichItem create(Object data, String viewHolderId) {
        return new RichItem(data, viewHolderId);
    }

    public String getViewHolderId() {
        return viewHolderId;
    }

    public Object getData() {
        return data;
    }
}
