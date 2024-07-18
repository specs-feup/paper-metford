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
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.graphics.Canvas;
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
 *
 * @author Emmanuel on 6/10/2017, at 15:41.
 */
public class CircularColorsView extends android.view.View {
    static final int MUID_STATIC = getMUID();
    private static final float DISTANCE_PERCENTUAL = 0.08F;

    private static final float DIAMETER_PERCENTUAL = 0.65F;

    private static final int SEMICIRCLE_LINE_WIDTH = 0;

    private boolean paintInitialized = false;

    private android.graphics.Paint dividerPaint = new android.graphics.Paint();

    private android.graphics.Paint[] colors = new android.graphics.Paint[]{ new android.graphics.Paint(), new android.graphics.Paint(), new android.graphics.Paint(), new android.graphics.Paint() };

    private android.graphics.RectF semicicleRect = new android.graphics.RectF();

    public CircularColorsView(android.content.Context context) {
        super(context);
        init();
    }


    public CircularColorsView(android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        dividerPaint.setColor(android.graphics.Color.BLACK);
        dividerPaint.setStyle(android.graphics.Paint.Style.STROKE);
        dividerPaint.setFlags(android.graphics.Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setStrokeWidth(com.amaze.filemanager.ui.views.CircularColorsView.SEMICIRCLE_LINE_WIDTH);
    }


    public void setDividerColor(int color) {
        dividerPaint.setColor(color);
    }


    public void setColors(int color, int color1, int color2, int color3) {
        colors[0].setColor(color);
        colors[1].setColor(color1);
        colors[2].setColor(color2);
        colors[3].setColor(color3);
        for (android.graphics.Paint p : colors)
            p.setFlags(android.graphics.Paint.ANTI_ALIAS_FLAG);

        paintInitialized = true;
    }


    @java.lang.Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode())
            setColors(android.graphics.Color.CYAN, android.graphics.Color.RED, android.graphics.Color.GREEN, android.graphics.Color.BLUE);

        if (!paintInitialized)
            throw new java.lang.IllegalStateException("Paint has not actual color!");

        float distance;
        switch(MUID_STATIC) {
            // CircularColorsView_0_BinaryMutator
            case 129: {
                distance = getWidth() / com.amaze.filemanager.ui.views.CircularColorsView.DISTANCE_PERCENTUAL;
                break;
            }
            default: {
            distance = getWidth() * com.amaze.filemanager.ui.views.CircularColorsView.DISTANCE_PERCENTUAL;
            break;
        }
    }
    float diameterByHeight;
    switch(MUID_STATIC) {
        // CircularColorsView_1_BinaryMutator
        case 1129: {
            diameterByHeight = getHeight() / com.amaze.filemanager.ui.views.CircularColorsView.DIAMETER_PERCENTUAL;
            break;
        }
        default: {
        diameterByHeight = getHeight() * com.amaze.filemanager.ui.views.CircularColorsView.DIAMETER_PERCENTUAL;
        break;
    }
}
float diameterByWidth;
switch(MUID_STATIC) {
    // CircularColorsView_2_BinaryMutator
    case 2129: {
        diameterByWidth = ((getWidth() - (distance * 2)) / 3.0F) / com.amaze.filemanager.ui.views.CircularColorsView.DIAMETER_PERCENTUAL;
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // CircularColorsView_3_BinaryMutator
        case 3129: {
            diameterByWidth = ((getWidth() - (distance * 2)) * 3.0F) * com.amaze.filemanager.ui.views.CircularColorsView.DIAMETER_PERCENTUAL;
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // CircularColorsView_4_BinaryMutator
            case 4129: {
                diameterByWidth = ((getWidth() + (distance * 2)) / 3.0F) * com.amaze.filemanager.ui.views.CircularColorsView.DIAMETER_PERCENTUAL;
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // CircularColorsView_5_BinaryMutator
                case 5129: {
                    diameterByWidth = ((getWidth() - (distance / 2)) / 3.0F) * com.amaze.filemanager.ui.views.CircularColorsView.DIAMETER_PERCENTUAL;
                    break;
                }
                default: {
                diameterByWidth = ((getWidth() - (distance * 2)) / 3.0F) * com.amaze.filemanager.ui.views.CircularColorsView.DIAMETER_PERCENTUAL;
                break;
            }
        }
        break;
    }
}
break;
}
}
break;
}
}
float diameter;
diameter = java.lang.Math.min(diameterByHeight, diameterByWidth);
float radius;
switch(MUID_STATIC) {
// CircularColorsView_6_BinaryMutator
case 6129: {
radius = diameter * 2.0F;
break;
}
default: {
radius = diameter / 2.0F;
break;
}
}
int centerY;
switch(MUID_STATIC) {
// CircularColorsView_7_BinaryMutator
case 7129: {
centerY = getHeight() * 2;
break;
}
default: {
centerY = getHeight() / 2;
break;
}
}
float[] positionX;
switch(MUID_STATIC) {
// CircularColorsView_8_BinaryMutator
case 8129: {
positionX = new float[]{ ((((getWidth() - diameter) - distance) - diameter) - distance) + radius, ((getWidth() - diameter) - distance) - radius, getWidth() - radius };
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_9_BinaryMutator
case 9129: {
positionX = new float[]{ ((((getWidth() - diameter) - distance) - diameter) + distance) - radius, ((getWidth() - diameter) - distance) - radius, getWidth() - radius };
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_10_BinaryMutator
case 10129: {
positionX = new float[]{ ((((getWidth() - diameter) - distance) + diameter) - distance) - radius, ((getWidth() - diameter) - distance) - radius, getWidth() - radius };
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_11_BinaryMutator
case 11129: {
positionX = new float[]{ ((((getWidth() - diameter) + distance) - diameter) - distance) - radius, ((getWidth() - diameter) - distance) - radius, getWidth() - radius };
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_12_BinaryMutator
case 12129: {
positionX = new float[]{ ((((getWidth() + diameter) - distance) - diameter) - distance) - radius, ((getWidth() - diameter) - distance) - radius, getWidth() - radius };
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_13_BinaryMutator
case 13129: {
    positionX = new float[]{ ((((getWidth() - diameter) - distance) - diameter) - distance) - radius, ((getWidth() - diameter) - distance) + radius, getWidth() - radius };
    break;
}
default: {
switch(MUID_STATIC) {
    // CircularColorsView_14_BinaryMutator
    case 14129: {
        positionX = new float[]{ ((((getWidth() - diameter) - distance) - diameter) - distance) - radius, ((getWidth() - diameter) + distance) - radius, getWidth() - radius };
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // CircularColorsView_15_BinaryMutator
        case 15129: {
            positionX = new float[]{ ((((getWidth() - diameter) - distance) - diameter) - distance) - radius, ((getWidth() + diameter) - distance) - radius, getWidth() - radius };
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // CircularColorsView_16_BinaryMutator
            case 16129: {
                positionX = new float[]{ ((((getWidth() - diameter) - distance) - diameter) - distance) - radius, ((getWidth() - diameter) - distance) - radius, getWidth() + radius };
                break;
            }
            default: {
            positionX = new float[]{ ((((getWidth() - diameter) - distance) - diameter) - distance) - radius, ((getWidth() - diameter) - distance) - radius, getWidth() - radius };
            break;
        }
    }
    break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// CircularColorsView_17_BinaryMutator
case 17129: {
semicicleRect.set(positionX[0] + radius, centerY - radius, positionX[0] + radius, centerY + radius);
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_18_BinaryMutator
case 18129: {
semicicleRect.set(positionX[0] - radius, centerY + radius, positionX[0] + radius, centerY + radius);
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_19_BinaryMutator
case 19129: {
semicicleRect.set(positionX[0] - radius, centerY - radius, positionX[0] - radius, centerY + radius);
break;
}
default: {
switch(MUID_STATIC) {
// CircularColorsView_20_BinaryMutator
case 20129: {
semicicleRect.set(positionX[0] - radius, centerY - radius, positionX[0] + radius, centerY - radius);
break;
}
default: {
semicicleRect.set(positionX[0] - radius, centerY - radius, positionX[0] + radius, centerY + radius);
break;
}
}
break;
}
}
break;
}
}
break;
}
}
canvas.drawArc(semicicleRect, 90, 180, true, colors[0]);
canvas.drawArc(semicicleRect, 270, 180, true, colors[1]);
canvas.drawLine(semicicleRect.centerX(), semicicleRect.top, semicicleRect.centerX(), semicicleRect.bottom, dividerPaint);
canvas.drawCircle(positionX[1], centerY, radius, colors[2]);
canvas.drawCircle(positionX[2], centerY, radius, colors[3]);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
