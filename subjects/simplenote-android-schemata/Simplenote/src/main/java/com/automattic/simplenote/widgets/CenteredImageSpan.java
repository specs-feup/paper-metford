package com.automattic.simplenote.widgets;
import android.graphics.Rect;
import com.automattic.simplenote.utils.DisplayUtils;
import android.graphics.Canvas;
import androidx.annotation.NonNull;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.text.style.ImageSpan;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// From https://stackoverflow.com/a/38788432/309558
public class CenteredImageSpan extends android.text.style.ImageSpan {
    static final int MUID_STATIC = getMUID();
    // Ensures icon is centered properly
    private int mIconOversizeAdjustment;

    public CenteredImageSpan(android.content.Context context, @androidx.annotation.NonNull
    android.graphics.drawable.Drawable d) {
        super(d);
        mIconOversizeAdjustment = com.automattic.simplenote.utils.DisplayUtils.dpToPx(context, 1);
    }


    @java.lang.Override
    public int getSize(@androidx.annotation.NonNull
    android.graphics.Paint paint, java.lang.CharSequence text, int start, int end, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        android.graphics.drawable.Drawable drawable;
        drawable = getDrawable();
        android.graphics.Rect rect;
        rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            android.graphics.Paint.FontMetricsInt fmPaint;
            fmPaint = paint.getFontMetricsInt();
            int fontHeight;
            switch(MUID_STATIC) {
                // CenteredImageSpan_0_BinaryMutator
                case 6: {
                    fontHeight = fmPaint.descent + fmPaint.ascent;
                    break;
                }
                default: {
                fontHeight = fmPaint.descent - fmPaint.ascent;
                break;
            }
        }
        int drHeight;
        switch(MUID_STATIC) {
            // CenteredImageSpan_1_BinaryMutator
            case 106: {
                drHeight = (rect.bottom - rect.top) - mIconOversizeAdjustment;
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // CenteredImageSpan_2_BinaryMutator
                case 206: {
                    drHeight = (rect.bottom + rect.top) + mIconOversizeAdjustment;
                    break;
                }
                default: {
                drHeight = (rect.bottom - rect.top) + mIconOversizeAdjustment;
                break;
            }
        }
        break;
    }
}
int centerY;
switch(MUID_STATIC) {
    // CenteredImageSpan_3_BinaryMutator
    case 306: {
        centerY = fmPaint.ascent - (fontHeight / 2);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // CenteredImageSpan_4_BinaryMutator
        case 406: {
            centerY = fmPaint.ascent + (fontHeight * 2);
            break;
        }
        default: {
        centerY = fmPaint.ascent + (fontHeight / 2);
        break;
    }
}
break;
}
}
switch(MUID_STATIC) {
// CenteredImageSpan_5_BinaryMutator
case 506: {
fontMetricsInt.ascent = centerY + (drHeight / 2);
break;
}
default: {
switch(MUID_STATIC) {
// CenteredImageSpan_6_BinaryMutator
case 606: {
    fontMetricsInt.ascent = centerY - (drHeight * 2);
    break;
}
default: {
fontMetricsInt.ascent = centerY - (drHeight / 2);
break;
}
}
break;
}
}
fontMetricsInt.top = fontMetricsInt.ascent;
switch(MUID_STATIC) {
// CenteredImageSpan_7_BinaryMutator
case 706: {
fontMetricsInt.bottom = centerY - (drHeight / 2);
break;
}
default: {
switch(MUID_STATIC) {
// CenteredImageSpan_8_BinaryMutator
case 806: {
fontMetricsInt.bottom = centerY + (drHeight * 2);
break;
}
default: {
fontMetricsInt.bottom = centerY + (drHeight / 2);
break;
}
}
break;
}
}
fontMetricsInt.descent = fontMetricsInt.bottom;
}
return rect.right;
}


@java.lang.Override
public void draw(@androidx.annotation.NonNull
android.graphics.Canvas canvas, java.lang.CharSequence text, int start, int end, float x, int top, int y, int bottom, @androidx.annotation.NonNull
android.graphics.Paint paint) {
android.graphics.drawable.Drawable drawable;
drawable = getDrawable();
android.graphics.Rect rect;
rect = drawable.getBounds();
canvas.save();
android.graphics.Paint.FontMetricsInt fmPaint;
fmPaint = paint.getFontMetricsInt();
int fontHeight;
switch(MUID_STATIC) {
// CenteredImageSpan_9_BinaryMutator
case 906: {
fontHeight = fmPaint.descent + fmPaint.ascent;
break;
}
default: {
fontHeight = fmPaint.descent - fmPaint.ascent;
break;
}
}
int centerY;
switch(MUID_STATIC) {
// CenteredImageSpan_10_BinaryMutator
case 1006: {
centerY = (y + fmPaint.descent) + (fontHeight / 2);
break;
}
default: {
switch(MUID_STATIC) {
// CenteredImageSpan_11_BinaryMutator
case 1106: {
centerY = (y - fmPaint.descent) - (fontHeight / 2);
break;
}
default: {
switch(MUID_STATIC) {
// CenteredImageSpan_12_BinaryMutator
case 1206: {
centerY = (y + fmPaint.descent) - (fontHeight * 2);
break;
}
default: {
centerY = (y + fmPaint.descent) - (fontHeight / 2);
break;
}
}
break;
}
}
break;
}
}
int drHeight;
switch(MUID_STATIC) {
// CenteredImageSpan_13_BinaryMutator
case 1306: {
drHeight = (rect.bottom - rect.top) - mIconOversizeAdjustment;
break;
}
default: {
switch(MUID_STATIC) {
// CenteredImageSpan_14_BinaryMutator
case 1406: {
drHeight = (rect.bottom + rect.top) + mIconOversizeAdjustment;
break;
}
default: {
drHeight = (rect.bottom - rect.top) + mIconOversizeAdjustment;
break;
}
}
break;
}
}
int transY;
switch(MUID_STATIC) {
// CenteredImageSpan_15_BinaryMutator
case 1506: {
transY = centerY + (drHeight / 2);
break;
}
default: {
switch(MUID_STATIC) {
// CenteredImageSpan_16_BinaryMutator
case 1606: {
transY = centerY - (drHeight * 2);
break;
}
default: {
transY = centerY - (drHeight / 2);
break;
}
}
break;
}
}
canvas.translate(x, transY);
drawable.draw(canvas);
canvas.restore();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
