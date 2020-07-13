package com.myCPE.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.myCPE.R;

@SuppressLint("AppCompatCustomView")
public class UnderlineTextView extends TextView {

    private Paint mLinePaint;

    private int mSpaceHeight;

    public UnderlineTextView(Context context) {
        super(context);
    }

    public UnderlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public UnderlineTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextViewCustom);
            String fontName = a.getString(R.styleable.EdittextViewCustom_edit_font);
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
        mLinePaint = new Paint();
        mLinePaint.setStrokeWidth(getTextSize() / 16);
        mLinePaint.setColor(getTextColors().getDefaultColor());

        Paint.FontMetricsInt fontMetricsInt = getPaint().getFontMetricsInt();

        mSpaceHeight = Math.abs(fontMetricsInt.bottom - fontMetricsInt.ascent - getLineHeight()) - (
                fontMetricsInt.bottom
                        - fontMetricsInt.descent);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int lineCount = getLineCount();
        Layout layout = getLayout();

        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();

        for (int i = 0; i < lineCount; i++) {
            float currentXStart = layout.getLineLeft(i) + paddingLeft;
            float currentXEnd = layout.getLineRight(i) + paddingLeft;
            int currentY = layout.getLineBottom(i) + paddingTop - mSpaceHeight + 8;
            canvas.drawLine(currentXStart, currentY, currentXEnd, currentY, mLinePaint);
        }
    }

    public void setLineWeight(float value) {
        mLinePaint.setStrokeWidth(value);
    }

    public void setLineColor(int color) {
        mLinePaint.setColor(color);
    }
}