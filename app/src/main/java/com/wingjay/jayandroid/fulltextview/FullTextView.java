package com.wingjay.jayandroid.fulltextview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wingjay.jayandroid.R;

/**
 * Created by wingjay on 10/26/15.
 * One textView, support link, drawable, bold, color, title:, different size/style, together.
 *
 */
public class FullTextView extends FrameLayout {

    private Context mContext;

    private TextView mTextView;

    private String normalText;

    private String linkText;// click to webview
    private int linkColor;

    private String jumpText;// click to other activity

    public FullTextViewBuilder build() {
        return new FullTextViewBuilder(mContext, mTextView);
    }

    public FullTextView(Context context) {
        this(context, null);
    }

    public FullTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FullTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        LayoutInflater.from(context).inflate(R.layout.view_full_textview, this);
        mTextView = (TextView) findViewById(R.id.content);
        init();
    }

    private void init() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();


        SpannableString s1 = new SpannableString("normalText");

        SpannableString s2 = new SpannableString("linkText");
        final String url_s2 = "http://baidu.com";
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_s2));
                mContext.startActivity(intent);
            }
        };
        s2.setSpan(clickableSpan2, 0, s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        spannableStringBuilder.append(s1).append(s2);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        mTextView.setCompoundDrawablePadding(20);
        mTextView.setText(spannableStringBuilder);
    }


}
