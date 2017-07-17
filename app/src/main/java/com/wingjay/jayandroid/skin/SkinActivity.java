package com.wingjay.jayandroid.skin;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import android.widget.Button;
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
        replaceButtonWithTextView();
        whatsResource();
    }

    private void whatsAttrs(String name, Context context, AttributeSet attrs) {
        int styleAttrs = attrs.getStyleAttribute(); // 0
        Resources resources = getResources();
        for (int i=0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            // if attrValue starts with @, means it's a resourceId
            if (attrValue.startsWith("@")) {
                int resourceId = Integer.parseInt(attrValue.substring(1));
                String resName = resources.getResourceName(resourceId);
                String resTypeName = resources.getResourceTypeName(resourceId);
                String resEntry = resources.getResourceEntryName(resourceId);
                String resPackage = resources.getResourcePackageName(resourceId);
            }
        }
    }
    // outside. LinearLayout: orientation: 1; fitsSystemWindow: true; layout_width: -1; layout_height: -1
    // real LinearLayout:  orientation: 1; layout_width: -1; layout_height: -1
    // Button: textColor: @2131361860;  gravity: 0x11; layout_gravity: 0x11; id: @2131427476; background: @2131427477;
    //          layout_width: 300.0dip; layout_height: -2; text: "I'll be";
    // Attribute: `textColor: @2131361860` =>
    private void whatsResource() {
        Resources resources = getResources();
        @ColorInt int color = resources.getColor(R.color.ColorPrimary);

        StateListDrawable stateListDrawable =
            (StateListDrawable) resources.getDrawable(R.drawable.selector_orange_background);
        processStateListDrawable(stateListDrawable);

        LayerDrawable layerDrawable =
            (LayerDrawable) resources.getDrawable(R.drawable.layer_list_drawable);
        processLayerDrawable(layerDrawable);
    }

    private void processDrawable(Drawable drawable) {
        if (drawable instanceof LayerDrawable) {
            processLayerDrawable((LayerDrawable) drawable);
        } else if (drawable instanceof GradientDrawable) {
            processGradientDrawable((GradientDrawable) drawable);
        } else if (drawable instanceof StateListDrawable) {
            processStateListDrawable((StateListDrawable) drawable);
        } else if (drawable instanceof ColorDrawable) {
            processColorDrawable((ColorDrawable) drawable);
        }
    }

    private void processColorDrawable(ColorDrawable colorDrawable) {
        // <color />
        Log.d(TAG, "Color: " + colorDrawable.getColor());
    }

    private void processStateListDrawable(StateListDrawable stateListDrawable) {
        // <selector> <item /> <item /> </selector>
    }

    private void processLayerDrawable(LayerDrawable layerDrawable) {
        // <layer-list> <item /> <item /> </layer-list>
        for (int i=0; i < layerDrawable.getNumberOfLayers(); i++) {
            Drawable drawable = layerDrawable.getDrawable(i); // Item: GradientDrawable, ColorDrawable
            processDrawable(drawable);
        }
    }

    private void processGradientDrawable(GradientDrawable gradientDrawable) {
        // <shape> <solid /> <stroke /> </shape>
        // fill color + stroke color + stroke width
        try {
            Field fillPaintField = GradientDrawable.class.getDeclaredField("mFillPaint");
            fillPaintField.setAccessible(true);
            Paint fillPaint = (Paint) fillPaintField.get(gradientDrawable);
            Log.d(TAG, "Fill Color: " + fillPaint.getColor());

            Field strokePaintField = GradientDrawable.class.getDeclaredField("mStrokePaint");
            strokePaintField.setAccessible(true);
            Paint strokePaint = (Paint) strokePaintField.get(gradientDrawable);
            Log.d(TAG, String.format("Stroke color: %s, width: %s",
                strokePaint.getColor(), strokePaint.getStrokeWidth()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void replaceButtonWithTextView() {
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

                    // for testing
                    whatsAttrs(name, context, attrs);

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
