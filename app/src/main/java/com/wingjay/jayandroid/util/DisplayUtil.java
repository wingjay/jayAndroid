package com.wingjay.jayandroid.util;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;

import com.wingjay.jayandroid.BaseActivity;

/**
 * Created by jay on 11/21/15.
 */
public class DisplayUtil {

    public static int getScreenWidth(Context context) {
        return getScreenSize(context).x;
    }

    public static int getScreenHeight(Context context) {
        return getScreenSize(context).y;
    }

    public static Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

}
