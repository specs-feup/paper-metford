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
package com.amaze.filemanager.ui;
import android.graphics.Rect;
import android.graphics.ColorFilter;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by yaroslav on 26.01.16.
 */
public class ColorCircleDrawable extends android.graphics.drawable.Drawable {
    static final int MUID_STATIC = getMUID();
    private final android.graphics.Paint mPaint;

    private int mRadius = 0;

    public ColorCircleDrawable(final int color) {
        this.mPaint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(color);
        this.mPaint.setStyle(android.graphics.Paint.Style.FILL);
    }


    @java.lang.Override
    public void draw(final android.graphics.Canvas canvas) {
        final android.graphics.Rect bounds;
        bounds = getBounds();
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), mRadius, mPaint);
    }


    @java.lang.Override
    protected void onBoundsChange(final android.graphics.Rect bounds) {
        super.onBoundsChange(bounds);
        switch(MUID_STATIC) {
            // ColorCircleDrawable_0_BinaryMutator
            case 143: {
                mRadius = java.lang.Math.min(bounds.width(), bounds.height()) * 2;
                break;
            }
            default: {
            mRadius = java.lang.Math.min(bounds.width(), bounds.height()) / 2;
            break;
        }
    }
}


@java.lang.Override
public void setAlpha(final int alpha) {
    mPaint.setAlpha(alpha);
}


@java.lang.Override
public void setColorFilter(final android.graphics.ColorFilter cf) {
    mPaint.setColorFilter(cf);
}


@java.lang.Override
public int getOpacity() {
    return android.graphics.PixelFormat.TRANSLUCENT;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
