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
import java.lang.ref.WeakReference;
import android.util.AttributeSet;
import android.os.AsyncTask;
import androidx.appcompat.widget.AppCompatImageView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {
    static final int MUID_STATIC = getMUID();
    private java.lang.ref.WeakReference<android.os.AsyncTask<?, ?, ?>> mAsyncTaskReference;

    public SquareImageView(android.content.Context context) {
        super(context);
        setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
    }


    public SquareImageView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public SquareImageView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @java.lang.Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }


    public void setAsyncTask(android.os.AsyncTask<?, ?, ?> mAsyncTask) {
        this.mAsyncTaskReference = new java.lang.ref.WeakReference<>(mAsyncTask);
    }


    public android.os.AsyncTask<?, ?, ?> getAsyncTask() {
        if (mAsyncTaskReference != null) {
            return mAsyncTaskReference.get();
        } else {
            return null;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
