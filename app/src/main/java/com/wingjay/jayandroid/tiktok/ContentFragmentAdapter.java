package com.wingjay.jayandroid.tiktok;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * ContentFragmentAdapter
 *
 * @author wingjay
 * @date 2017/11/16
 */
public class ContentFragmentAdapter extends SmartFragmentStatePagerAdapter {

    public ContentFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % 3) {
            case 0: return ContentFragment.newInstance("First", Color.BLACK);
            case 1: return ContentFragment.newInstance("Second", Color.RED);
            case 2:
            default:
                return ContentFragment.newInstance("Third", Color.DKGRAY);
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }

}