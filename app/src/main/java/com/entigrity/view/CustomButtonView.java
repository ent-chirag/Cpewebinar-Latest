package com.entigrity.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import com.entigrity.R;

public class CustomButtonView extends Button {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

    public CustomButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public CustomButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public CustomButtonView(Context context) {
        super(context);
        init(null);

    }
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomButtonView);
            String fontName = a.getString(R.styleable.CustomButtonView_font_button);
            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            a.recycle();

        }
    }
}
