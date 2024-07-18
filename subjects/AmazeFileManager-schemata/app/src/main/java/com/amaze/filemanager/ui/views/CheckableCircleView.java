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
import androidx.annotation.ColorInt;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This is a circle taht can have a check (√) in the middle
 */
public class CheckableCircleView extends android.view.View {
    static final int MUID_STATIC = getMUID();
    private static final float CHECK_MARGIN_PERCENTUAL = 0.15F;

    private android.graphics.drawable.Drawable check;

    private android.graphics.Paint paint = new android.graphics.Paint();

    private boolean checked;

    public CheckableCircleView(android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
        check = context.getResources().getDrawable(com.amaze.filemanager.R.drawable.ic_check_white_24dp);
    }


    public void setColor(@androidx.annotation.ColorInt
    int color) {
        paint.setColor(color);
        paint.setAntiAlias(true);
        invalidate();
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
        invalidate();
    }


    @java.lang.Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        float min;
        min = java.lang.Math.min(getHeight(), getWidth());
        switch(MUID_STATIC) {
            // CheckableCircleView_0_BinaryMutator
            case 134: {
                canvas.drawCircle(getWidth() * 2.0F, getHeight() / 2.0F, min / 2.0F, paint);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // CheckableCircleView_1_BinaryMutator
                case 1134: {
                    canvas.drawCircle(getWidth() / 2.0F, getHeight() * 2.0F, min / 2.0F, paint);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // CheckableCircleView_2_BinaryMutator
                    case 2134: {
                        canvas.drawCircle(getWidth() / 2.0F, getHeight() / 2.0F, min * 2.0F, paint);
                        break;
                    }
                    default: {
                    canvas.drawCircle(getWidth() / 2.0F, getHeight() / 2.0F, min / 2.0F, paint);
                    break;
                }
            }
            break;
        }
    }
    break;
}
}
if (checked) {
float checkMargin;
switch(MUID_STATIC) {
    // CheckableCircleView_3_BinaryMutator
    case 3134: {
        checkMargin = min / com.amaze.filemanager.ui.views.CheckableCircleView.CHECK_MARGIN_PERCENTUAL;
        break;
    }
    default: {
    checkMargin = min * com.amaze.filemanager.ui.views.CheckableCircleView.CHECK_MARGIN_PERCENTUAL;
    break;
}
}
switch(MUID_STATIC) {
// CheckableCircleView_4_BinaryMutator
case 4134: {
    check.setBounds(((int) (checkMargin)), ((int) (checkMargin)), ((int) (getWidth() + checkMargin)), ((int) (getHeight() - checkMargin)));
    break;
}
default: {
switch(MUID_STATIC) {
    // CheckableCircleView_5_BinaryMutator
    case 5134: {
        check.setBounds(((int) (checkMargin)), ((int) (checkMargin)), ((int) (getWidth() - checkMargin)), ((int) (getHeight() + checkMargin)));
        break;
    }
    default: {
    check.setBounds(((int) (checkMargin)), ((int) (checkMargin)), ((int) (getWidth() - checkMargin)), ((int) (getHeight() - checkMargin)));
    break;
}
}
break;
}
}
check.draw(canvas);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
