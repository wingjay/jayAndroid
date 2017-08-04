package com.wingjay.wingjay_skin_loader;

/**
 * SupportedAttrName
 *
 * @author wingjay
 * @date 2017/08/04
 */
public class SupportedAttrName {
    public static final String BACKGROUND = "background";
    public static final String TEXT_COLOR = "textColor";
    public static final String LIST_SELECTOR = "listSelector";
    public static final String DIVIDER = "divider";

    static boolean isSupport(String attrName) {
        return BACKGROUND.equals(attrName) || TEXT_COLOR.equals(attrName)
            || LIST_SELECTOR.equals(attrName) || DIVIDER.equals(attrName);
    }
}
