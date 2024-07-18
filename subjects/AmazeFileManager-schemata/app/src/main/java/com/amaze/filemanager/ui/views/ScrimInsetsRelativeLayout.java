/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.ui.views;
import com.amaze.filemanager.R;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import android.graphics.Canvas;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/* A layout that draws something in the insets passed to {@link #fitSystemWindows(Rect)}, i.e. the area above UI chrome
(status and navigation bars, overlay action bars).
 */
public class ScrimInsetsRelativeLayout extends android.widget.RelativeLayout {
    static final int MUID_STATIC = getMUID();
    private android.graphics.drawable.Drawable mInsetForeground;

    private android.graphics.Rect mInsets;

    private android.graphics.Rect mTempRect = new android.graphics.Rect();

    private com.amaze.filemanager.ui.views.ScrimInsetsRelativeLayout.OnInsetsCallback mOnInsetsCallback;

    public ScrimInsetsRelativeLayout(android.content.Context context) {
        super(context);
        init(context, null, 0);
    }


    public ScrimInsetsRelativeLayout(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }


    public ScrimInsetsRelativeLayout(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }


    private void init(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        final android.content.res.TypedArray a;
        a = context.obtainStyledAttributes(attrs, com.amaze.filemanager.R.styleable.ScrimInsetsFrameLayout, defStyle, 0);
        if (a == null) {
            return;
        }
        mInsetForeground = a.getDrawable(com.amaze.filemanager.R.styleable.ScrimInsetsFrameLayout_insetForeground);
        a.recycle();
        setWillNotDraw(true);
    }


    @java.lang.Override
    protected boolean fitSystemWindows(android.graphics.Rect insets) {
        mInsets = new android.graphics.Rect(insets);
        setWillNotDraw(mInsetForeground == null);
        androidx.core.view.ViewCompat.postInvalidateOnAnimation(this);
        if (mOnInsetsCallback != null) {
            mOnInsetsCallback.onInsetsChanged(insets);
        }
        return true// consume insets
        ;// consume insets

    }


    @java.lang.Override
    public void draw(android.graphics.Canvas canvas) {
        super.draw(canvas);
        int width;
        width = getWidth();
        int height;
        height = getHeight();
        if ((mInsets != null) && (mInsetForeground != null)) {
            int sc;
            sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());
            // Top
            mTempRect.set(0, 0, width, mInsets.top);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);
            switch(MUID_STATIC) {
                // ScrimInsetsRelativeLayout_0_BinaryMutator
                case 139: {
                    // Bottom
                    mTempRect.set(0, height + mInsets.bottom, width, height);
                    break;
                }
                default: {
                // Bottom
                mTempRect.set(0, height - mInsets.bottom, width, height);
                break;
            }
        }
        mInsetForeground.setBounds(mTempRect);
        mInsetForeground.draw(canvas);
        switch(MUID_STATIC) {
            // ScrimInsetsRelativeLayout_1_BinaryMutator
            case 1139: {
                // Left
                mTempRect.set(0, mInsets.top, mInsets.left, height + mInsets.bottom);
                break;
            }
            default: {
            // Left
            mTempRect.set(0, mInsets.top, mInsets.left, height - mInsets.bottom);
            break;
        }
    }
    mInsetForeground.setBounds(mTempRect);
    mInsetForeground.draw(canvas);
    switch(MUID_STATIC) {
        // ScrimInsetsRelativeLayout_2_BinaryMutator
        case 2139: {
            // Right
            mTempRect.set(width + mInsets.right, mInsets.top, width, height - mInsets.bottom);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // ScrimInsetsRelativeLayout_3_BinaryMutator
            case 3139: {
                // Right
                mTempRect.set(width - mInsets.right, mInsets.top, width, height + mInsets.bottom);
                break;
            }
            default: {
            // Right
            mTempRect.set(width - mInsets.right, mInsets.top, width, height - mInsets.bottom);
            break;
        }
    }
    break;
}
}
mInsetForeground.setBounds(mTempRect);
mInsetForeground.draw(canvas);
canvas.restoreToCount(sc);
}
}


@java.lang.Override
protected void onAttachedToWindow() {
super.onAttachedToWindow();
if (mInsetForeground != null) {
mInsetForeground.setCallback(this);
}
}


@java.lang.Override
protected void onDetachedFromWindow() {
super.onDetachedFromWindow();
if (mInsetForeground != null) {
mInsetForeground.setCallback(null);
}
}


/**
 * Allows the calling container to specify a callback for custom processing when insets change
 * (i.e. when {@link #fitSystemWindows(Rect)} is called. This is useful for setting padding on UI
 * elements based on UI chrome insets (e.g. a Google Map or a ListView). When using with ListView
 * or GridView, remember to set clipToPadding to false.
 */
public void setOnInsetsCallback(com.amaze.filemanager.ui.views.ScrimInsetsRelativeLayout.OnInsetsCallback onInsetsCallback) {
mOnInsetsCallback = onInsetsCallback;
}


public interface OnInsetsCallback {
void onInsetsChanged(android.graphics.Rect insets);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
