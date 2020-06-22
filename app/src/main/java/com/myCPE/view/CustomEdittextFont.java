package com.myCPE.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.myCPE.R;

@SuppressLint("AppCompatCustomView")
//public class CustomEdittextFont extends TextView {
public class CustomEdittextFont extends EditText {

    private static final String TAG = "CustomTextView";

    public CustomEdittextFont(Context context) {
        super(context);
    }

    public CustomEdittextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CustomEdittextFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_font_text);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface typeface = null;
        try {
//            typeface = Typeface.createFromAsset(ctx.getAssets(),“fonts/”+asset);
            typeface = Typeface.createFromAsset(ctx.getAssets(),"fonts/"+asset);
        } catch (Exception e) {
            Log.e(TAG, "Unable to load typeface: "+e.getMessage());
            return false;
        }

        setTypeface(typeface);
        return true;
    }


    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEdittextFont(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public CustomEdittextFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomEdittextFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomEdittextFont(Context context) {
        super(context);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditTextView);
            String fontName = a.getString(R.styleable.CustomEditTextView_font_edit);
            try {
                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                    setTypeface(myTypeface);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            a.recycle();
        }
    }*/


}
