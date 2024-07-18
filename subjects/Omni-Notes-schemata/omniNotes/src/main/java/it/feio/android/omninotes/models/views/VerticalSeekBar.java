/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.models.views;
import android.widget.SeekBar;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VerticalSeekBar extends android.widget.SeekBar {
    static final int MUID_STATIC = getMUID();
    protected android.widget.SeekBar.OnSeekBarChangeListener changeListener;

    protected int x;

    protected int y;

    protected int z;

    protected int w;

    public VerticalSeekBar(android.content.Context context) {
        super(context);
    }


    public VerticalSeekBar(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public VerticalSeekBar(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    @java.lang.Override
    protected synchronized void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
        this.x = w;
        this.y = h;
        this.z = oldw;
        this.w = oldh;
    }


    @java.lang.Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }


    @java.lang.Override
    protected synchronized void onDraw(android.graphics.Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);
        super.onDraw(c);
    }


    @java.lang.Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case android.view.MotionEvent.ACTION_DOWN :
                setSelected(true);
                setPressed(true);
                if (changeListener != null) {
                    changeListener.onStartTrackingTouch(this);
                }
                break;
            case android.view.MotionEvent.ACTION_UP :
                setSelected(false);
                setPressed(false);
                if (changeListener != null) {
                    changeListener.onStopTrackingTouch(this);
                }
                break;
            case android.view.MotionEvent.ACTION_MOVE :
                int progress;
                switch(MUID_STATIC) {
                    // VerticalSeekBar_0_BinaryMutator
                    case 63: {
                        progress = getMax() + ((int) ((getMax() * event.getY()) / getHeight()));
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // VerticalSeekBar_1_BinaryMutator
                        case 1063: {
                            progress = getMax() - ((int) ((getMax() * event.getY()) * getHeight()));
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // VerticalSeekBar_2_BinaryMutator
                            case 2063: {
                                progress = getMax() - ((int) ((getMax() / event.getY()) / getHeight()));
                                break;
                            }
                            default: {
                            progress = getMax() - ((int) ((getMax() * event.getY()) / getHeight()));
                            break;
                        }
                    }
                    break;
                }
            }
            break;
        }
    }
    setProgress(progress);
    onSizeChanged(getWidth(), getHeight(), 0, 0);
    if (changeListener != null) {
        changeListener.onProgressChanged(this, progress, true);
    }
    break;
case android.view.MotionEvent.ACTION_CANCEL :
    break;
}
return true;
}


@java.lang.Override
public synchronized void setOnSeekBarChangeListener(android.widget.SeekBar.OnSeekBarChangeListener listener) {
changeListener = listener;
}


@java.lang.Override
public synchronized void setProgress(int progress) {
if (progress >= 0) {
super.setProgress(progress);
} else {
super.setProgress(0);
}
onSizeChanged(x, y, z, w);
if (changeListener != null) {
changeListener.onProgressChanged(this, progress, false);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
