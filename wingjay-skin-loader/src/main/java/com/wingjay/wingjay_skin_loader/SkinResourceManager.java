package com.wingjay.wingjay_skin_loader;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * SkinResourceManager. find Skin resource from given resId.
 *
 * Given a system resource ID, it will find the skin resource with same name in skin file and return it back.
 *
 * Example:
 * int resId @1232111 (@color/red)
 * resourceTypeName: color
 * resourceEntryName: red
 *
 * | systemResource | id: 1232111 -> red                            |
 * | skinResource   | color + red + skinPackageName -> id: 33312312 |
 * | skinResource   | use id 33312312 to get Color                  |
 *
 * @author wingjay
 * @date 2017/08/04
 */
public class SkinResourceManager {

    private static Resources skinResource;
    private static Resources systemResource;
    private static String skinPackageName;

    public static void setup(Resources mSystemResource, Resources mSkinResource, String mSkinPackageName) {
        systemResource = mSystemResource;
        skinResource = mSkinResource;
        skinPackageName = mSkinPackageName;
    }

    @ColorInt
    public static int getColor(int resId) {
        try {
            return skinResource.getColor(getSkinResId(resId));
        } catch (Exception e) {
            e.printStackTrace();
            return systemResource.getColor(resId);
        }
    }

    public static Drawable getDrawable(int resId) {
        try {
            return skinResource.getDrawable(getSkinResId(resId));
        } catch (Exception e) {
            e.printStackTrace();
            return systemResource.getDrawable(resId);
        }
    }

    private static int getSkinResId(int systemResId) {
        String resTypeName = systemResource.getResourceTypeName(systemResId);
        String resEntryName = systemResource.getResourceEntryName(systemResId);

        return skinResource.getIdentifier(resEntryName, resTypeName, skinPackageName);
    }
}
