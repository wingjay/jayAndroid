package com.wingjay.jayandroid.fulltextview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by wingjay on 10/29/15.
 */
public class FullTextViewBuilder {

    private final static int DRAWABLE_LOCATION_LEFT = 0;
    private final static int DRAWABLE_LOCATION_TOP = 1;
    private final static int DRAWABLE_LOCATION_RIGHT = 2;
    private final static int DRAWABLE_LOCATION_BOTTOM = 3;

    private Drawable mLeftDrawable;// icon to left of normalText
    private Drawable mTopDrawable;// icon to top of normalText
    private Drawable mRightDrawable;// icon to right of normalText
    private Drawable mBottomDrawable;// icon to bottom of normalText
    private int mDrawablePadding = 0;
    private boolean defaultBounds = true;
    private boolean[] defaultBoundsFor4Locations = {true, true, true, true};

    private Context mContext;
    private TextView mTextView;

    public FullTextViewBuilder(Context context, TextView textView) {
        mContext = context;
        mTextView = textView;
    }

//    public FullTextViewBuilder setContent(String content) {
//
//    }

    public FullTextViewBuilder setTopDrawable(int topDrawableRes) {
        return setDrawableByLocation(DRAWABLE_LOCATION_TOP, topDrawableRes);
    }

    public FullTextViewBuilder setTopDrawable(Drawable topDrawable) {
        return setDrawableByLocation(DRAWABLE_LOCATION_TOP, topDrawable);
    }

    public FullTextViewBuilder setTopDrawableWithSize(int topDrawableRes, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_TOP, topDrawableRes, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setTopDrawableWithSize(Drawable topDrawable, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_TOP, topDrawable, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setLeftDrawable(int leftDrawableRes) {
        return setDrawableByLocation(DRAWABLE_LOCATION_LEFT, leftDrawableRes);
    }

    public FullTextViewBuilder setLeftDrawable(Drawable leftDrawableRes) {
        return setDrawableByLocation(DRAWABLE_LOCATION_LEFT, leftDrawableRes);
    }

    public FullTextViewBuilder setLeftDrawableWithSize(int leftDrawableRes, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_LEFT, leftDrawableRes, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setLeftDrawableWithSize(Drawable leftDrawableRes, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_LEFT, leftDrawableRes, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setBottomDrawable(int bottomDrawableRes) {
        return setDrawableByLocation(DRAWABLE_LOCATION_BOTTOM, bottomDrawableRes);
    }

    public FullTextViewBuilder setBottomDrawable(Drawable bottomDrawableRes) {
        return setDrawableByLocation(DRAWABLE_LOCATION_BOTTOM, bottomDrawableRes);
    }

    public FullTextViewBuilder setBottomDrawableWithSize(int bottomDrawableRes, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_BOTTOM, bottomDrawableRes, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setBottomDrawableWithSize(Drawable bottomDrawableRes, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_BOTTOM, bottomDrawableRes, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setRightDrawable(int rightDrawableRes) {
        return setDrawableByLocation(DRAWABLE_LOCATION_RIGHT, rightDrawableRes);
    }

    public FullTextViewBuilder setRightDrawable(Drawable rightDrawableRes) {
        return setDrawableByLocation(DRAWABLE_LOCATION_RIGHT, rightDrawableRes);
    }

    public FullTextViewBuilder setRightDrawableWithSize(int rightDrawableRes, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_RIGHT, rightDrawableRes, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setRightDrawableWithSize(Drawable rightDrawableRes, int widthPixel, int heightPixel) {
        return setDrawableWithSizeByLocation(DRAWABLE_LOCATION_RIGHT, rightDrawableRes, widthPixel, heightPixel);
    }

    public FullTextViewBuilder setDrawablePadding(int drawablePadding) {
        if (drawablePadding <= 0) {
            throw new IllegalArgumentException("drawable padding must greater than 0");
        }
        mDrawablePadding = drawablePadding;
        return this;
    }

    private FullTextViewBuilder setDrawableByLocation(int location, int drawableRes) {
        return setDrawableByLocation(location, getDrawableByRes(drawableRes));
    }

    private FullTextViewBuilder setDrawableByLocation(int location, Drawable drawable) {
        if (drawable == null) {
            return this;
        }
        switch (location) {
            case DRAWABLE_LOCATION_LEFT:
                mLeftDrawable = drawable;
                break;
            case DRAWABLE_LOCATION_TOP:
                mTopDrawable = drawable;
                break;
            case DRAWABLE_LOCATION_RIGHT:
                mRightDrawable = drawable;
                break;
            case DRAWABLE_LOCATION_BOTTOM:
                mBottomDrawable = drawable;
                break;
        }
        return this;
    }

    private FullTextViewBuilder setDrawableWithSizeByLocation(int location, Drawable drawable, int width, int height) {
        if (drawable == null) {
            return this;
        }
        drawable.setBounds(0, 0, width, height);
        defaultBounds = false;
        defaultBoundsFor4Locations[location] = false;
        return setDrawableByLocation(location, drawable);
    }

    private FullTextViewBuilder setDrawableWithSizeByLocation(int location, int drawableRes, int width, int height) {
        return setDrawableWithSizeByLocation(location, getDrawableByRes(drawableRes), width, height);
    }

    private Drawable getDrawableByRes(int res) {
        Drawable drawable = mContext.getResources().getDrawable(res);
        if (drawable == null) {
            throw new Resources.NotFoundException("drawable with id : " + res + " not found");
        }
        return drawable;
    }

    public void apply() {
        if (mDrawablePadding > 0) {
            mTextView.setCompoundDrawablePadding(mDrawablePadding);
        }
        if (defaultBounds) {
            mTextView.setCompoundDrawablesWithIntrinsicBounds(mLeftDrawable,
                    mTopDrawable, mRightDrawable, mBottomDrawable);
        } else {
            setBoundsOrDefaultOfDrawable(mLeftDrawable, DRAWABLE_LOCATION_LEFT);
            setBoundsOrDefaultOfDrawable(mTopDrawable, DRAWABLE_LOCATION_TOP);
            setBoundsOrDefaultOfDrawable(mRightDrawable, DRAWABLE_LOCATION_RIGHT);
            setBoundsOrDefaultOfDrawable(mBottomDrawable, DRAWABLE_LOCATION_BOTTOM);
            mTextView.setCompoundDrawables(mLeftDrawable,
                    mTopDrawable, mRightDrawable, mBottomDrawable);
        }
    }

    private void setBoundsOrDefaultOfDrawable(Drawable drawable, int location) {
        if (drawable != null) {
            Rect rect;
            if (defaultBoundsFor4Locations[location]) {
                rect = new Rect(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            } else {
                rect = new Rect(0, 0, drawable.getBounds().width(), drawable.getBounds().height());
            }
            drawable.setBounds(rect);
        }
    }

}
