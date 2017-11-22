package com.wingjay.jayandroid.tiktok;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wingjay.jayandroid.R;

public class ContentFragment extends Fragment {

    public ContentFragment() {
    }

    public static Fragment newInstance(String title, @ColorInt int color) {
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("color", color);
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        TextView textView = view.findViewById(R.id.index);
        String index = getTitle() + String.valueOf(getColor());
        textView.setText(index);
        view.setBackgroundColor(getColor());
        return view;
    }

    public String getTitle() {
        return getArguments().getString("title");
    }

    public @ColorInt int getColor() {
        return getArguments().getInt("color");
    }
}
