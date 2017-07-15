package com.wingjay.jayandroid.skin;

import java.lang.reflect.Field;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

/**
 * SkinActivity
 *
 * @author wingjay
 * @date 2017/07/15
 */
public class SkinActivity extends BaseActivity {

    private static final String TAG = SkinActivity.class.getSimpleName();

    private static final String VIEW_TO_BE_REPLACED = "Button";
    private static final String NEW_VIEW_TEXT = "You Find Me!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // dynamically change Button in layout with a TextView by Factory2
        LayoutInflater inflater = LayoutInflater.from(this);
        Class inflaterClass = LayoutInflater.class;
        try {
            Field field = inflaterClass.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(inflater, false);
            inflater.setFactory2(new Factory2() {
                @Override
                public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                    Log.d(TAG, String.format("Parent: %s, Name: %s",
                        parent != null ? parent.getClass().getSimpleName() : "none", name));
                    for (int i = 0; i < attrs.getAttributeCount(); i++) {
                        Log.d(TAG, String.format("Attr Name: %s, Value: %s",
                            attrs.getAttributeName(i), attrs.getAttributeValue(i)));
                    }

                    // replace Button with TextView, whoes parent should be LinearLayout
                    if (VIEW_TO_BE_REPLACED.equals(name) && (parent instanceof LinearLayout)) {
                        TextView textView = new TextView(SkinActivity.this, attrs); // I reuse button attrs.
                        textView.setText(NEW_VIEW_TEXT);
                        return textView;
                    }
                    // for other views, do nothing
                    return null;
                }

                @Override
                public View onCreateView(String name, Context context, AttributeSet attrs) {
                    // This won't be called
                    return null;
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_skin);
    }
}
