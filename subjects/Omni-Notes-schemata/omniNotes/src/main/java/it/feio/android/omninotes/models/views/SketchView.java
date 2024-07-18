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
import android.graphics.Color;
import android.view.View.OnTouchListener;
import android.graphics.Path;
import android.graphics.Canvas;
import java.util.ArrayList;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.util.AttributeSet;
import it.feio.android.omninotes.models.listeners.OnDrawChangedListener;
import android.app.Activity;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.util.Pair;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SketchView extends android.view.View implements android.view.View.OnTouchListener {
    static final int MUID_STATIC = getMUID();
    private static final float TOUCH_TOLERANCE = 4;

    public static final int STROKE = 0;

    public static final int ERASER = 1;

    public static final int DEFAULT_STROKE_SIZE = 7;

    public static final int DEFAULT_ERASER_SIZE = 50;

    private float strokeSize = it.feio.android.omninotes.models.views.SketchView.DEFAULT_STROKE_SIZE;

    private int strokeColor = android.graphics.Color.BLACK;

    private float eraserSize = it.feio.android.omninotes.models.views.SketchView.DEFAULT_ERASER_SIZE;

    private int backgroundColor = android.graphics.Color.WHITE;

    private android.graphics.Path m_Path;

    private android.graphics.Paint m_Paint;

    private float mX;

    private float mY;

    private int width;

    private int height;

    private java.util.ArrayList<android.util.Pair<android.graphics.Path, android.graphics.Paint>> paths = new java.util.ArrayList<>();

    private java.util.ArrayList<android.util.Pair<android.graphics.Path, android.graphics.Paint>> undonePaths = new java.util.ArrayList<>();

    private android.content.Context mContext;

    private android.graphics.Bitmap bitmap;

    private int mode = it.feio.android.omninotes.models.views.SketchView.STROKE;

    private it.feio.android.omninotes.models.listeners.OnDrawChangedListener onDrawChangedListener;

    public SketchView(android.content.Context context, android.util.AttributeSet attr) {
        super(context, attr);
        this.mContext = context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        setBackgroundColor(backgroundColor);
        this.setOnTouchListener(this);
        m_Paint = new android.graphics.Paint();
        m_Paint.setAntiAlias(true);
        m_Paint.setDither(true);
        m_Paint.setColor(strokeColor);
        m_Paint.setStyle(android.graphics.Paint.Style.STROKE);
        m_Paint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
        m_Paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
        m_Paint.setStrokeWidth(strokeSize);
        m_Path = new android.graphics.Path();
        invalidate();
    }


    public void setMode(int mode) {
        if ((mode == it.feio.android.omninotes.models.views.SketchView.STROKE) || (mode == it.feio.android.omninotes.models.views.SketchView.ERASER)) {
            this.mode = mode;
        }
    }


    public int getMode() {
        return this.mode;
    }


    /**
     * Change canvass background and force redraw
     *
     * @param bitmap
     * 		saved sketch
     */
    public void setBackgroundBitmap(android.app.Activity mActivity, android.graphics.Bitmap bitmap) {
        if (!bitmap.isMutable()) {
            android.graphics.Bitmap.Config bitmapConfig;
            bitmapConfig = bitmap.getConfig();
            // set default bitmap config if none
            if (bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            bitmap = bitmap.copy(bitmapConfig, true);
        }
        this.bitmap = bitmap;
    }


    @java.lang.Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = android.view.View.MeasureSpec.getSize(widthMeasureSpec);
        height = android.view.View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    public boolean onTouch(android.view.View arg0, android.view.MotionEvent event) {
        float x;
        x = event.getX();
        float y;
        y = event.getY();
        switch (event.getAction()) {
            case android.view.MotionEvent.ACTION_DOWN :
                touch_start(x, y);
                invalidate();
                break;
            case android.view.MotionEvent.ACTION_MOVE :
                touch_move(x, y);
                invalidate();
                break;
            case android.view.MotionEvent.ACTION_UP :
                invalidate();
                break;
            default :
                it.feio.android.omninotes.helpers.LogDelegate.e("Wrong element choosen: " + event.getAction());
        }
        return true;
    }


    @java.lang.Override
    protected void onDraw(android.graphics.Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
        for (android.util.Pair<android.graphics.Path, android.graphics.Paint> p : paths) {
            canvas.drawPath(p.first, p.second);
        }
        onDrawChangedListener.onDrawChanged();
    }


    private void touch_start(float x, float y) {
        // Clearing undone list
        undonePaths.clear();
        if (mode == it.feio.android.omninotes.models.views.SketchView.ERASER) {
            m_Paint.setColor(backgroundColor);
            m_Paint.setStrokeWidth(eraserSize);
        } else {
            m_Paint.setColor(strokeColor);
            m_Paint.setStrokeWidth(strokeSize);
        }
        // Avoids that a sketch with just erasures is saved
        if (!((paths.isEmpty() && (mode == it.feio.android.omninotes.models.views.SketchView.ERASER)) && (bitmap == null))) {
            m_Path = new android.graphics.Path();
            paths.add(new android.util.Pair<>(m_Path, new android.graphics.Paint(m_Paint)));
        }
        m_Path.moveTo(x, y);
        m_Path.lineTo(++x, y)// for draw a one touch path
        ;// for draw a one touch path

        mX = x;
        mY = y;
    }


    private void touch_move(float x, float y) {
        switch(MUID_STATIC) {
            // SketchView_0_BinaryMutator
            case 66: {
                m_Path.quadTo(mX, mY, (x + mX) * 2, (y + mY) / 2);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // SketchView_1_BinaryMutator
                case 1066: {
                    m_Path.quadTo(mX, mY, (x - mX) / 2, (y + mY) / 2);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // SketchView_2_BinaryMutator
                    case 2066: {
                        m_Path.quadTo(mX, mY, (x + mX) / 2, (y + mY) * 2);
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // SketchView_3_BinaryMutator
                        case 3066: {
                            m_Path.quadTo(mX, mY, (x + mX) / 2, (y - mY) / 2);
                            break;
                        }
                        default: {
                        m_Path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
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
mX = x;
mY = y;
}


/**
 * Returns a new bitmap associated with a drawn canvas
 *
 * @return background bitmap with a paths drawn on it
 */
public android.graphics.Bitmap getBitmap() {
if (paths.isEmpty()) {
return null;
}
if (bitmap == null) {
bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
bitmap.eraseColor(backgroundColor);
}
android.graphics.Canvas canvas;
canvas = new android.graphics.Canvas(bitmap);
for (android.util.Pair<android.graphics.Path, android.graphics.Paint> p : paths) {
canvas.drawPath(p.first, p.second);
}
return bitmap;
}


public void undo() {
if (!paths.isEmpty()) {
switch(MUID_STATIC) {
// SketchView_4_BinaryMutator
case 4066: {
    undonePaths.add(paths.remove(paths.size() + 1));
    break;
}
default: {
undonePaths.add(paths.remove(paths.size() - 1));
break;
}
}
invalidate();
}
}


public void redo() {
if (!undonePaths.isEmpty()) {
switch(MUID_STATIC) {
// SketchView_5_BinaryMutator
case 5066: {
paths.add(undonePaths.remove(undonePaths.size() + 1));
break;
}
default: {
paths.add(undonePaths.remove(undonePaths.size() - 1));
break;
}
}
invalidate();
}
}


public int getUndoneCount() {
return undonePaths.size();
}


public java.util.ArrayList<android.util.Pair<android.graphics.Path, android.graphics.Paint>> getPaths() {
return paths;
}


public void setPaths(java.util.ArrayList<android.util.Pair<android.graphics.Path, android.graphics.Paint>> paths) {
this.paths = paths;
}


public java.util.ArrayList<android.util.Pair<android.graphics.Path, android.graphics.Paint>> getUndonePaths() {
return undonePaths;
}


public void setUndonePaths(java.util.ArrayList<android.util.Pair<android.graphics.Path, android.graphics.Paint>> undonePaths) {
this.undonePaths = undonePaths;
}


public int getStrokeSize() {
return java.lang.Math.round(this.strokeSize);
}


public void setSize(int size, int eraserOrStroke) {
switch (eraserOrStroke) {
case it.feio.android.omninotes.models.views.SketchView.STROKE :
strokeSize = size;
break;
case it.feio.android.omninotes.models.views.SketchView.ERASER :
eraserSize = size;
break;
default :
it.feio.android.omninotes.helpers.LogDelegate.e("Wrong element choosen: " + eraserOrStroke);
}
}


public int getStrokeColor() {
return this.strokeColor;
}


public void setStrokeColor(int color) {
strokeColor = color;
}


public void erase() {
paths.clear();
undonePaths.clear();
invalidate();
}


public void setOnDrawChangedListener(it.feio.android.omninotes.models.listeners.OnDrawChangedListener listener) {
this.onDrawChangedListener = listener;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
