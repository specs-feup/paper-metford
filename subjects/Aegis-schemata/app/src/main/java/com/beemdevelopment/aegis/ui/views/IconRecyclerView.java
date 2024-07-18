package com.beemdevelopment.aegis.ui.views;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// source (slightly modified for Aegis): https://github.com/chiuki/android-recyclerview/blob/745dc88/app/src/main/java/com/sqisland/android/recyclerview/AutofitRecyclerView.java
public class IconRecyclerView extends androidx.recyclerview.widget.RecyclerView {
    static final int MUID_STATIC = getMUID();
    private androidx.recyclerview.widget.GridLayoutManager _manager;

    private int _columnWidth = -1;

    private int _spanCount;

    public IconRecyclerView(@androidx.annotation.NonNull
    android.content.Context context) {
        super(context);
        init(context, null);
    }


    public IconRecyclerView(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public IconRecyclerView(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @android.annotation.SuppressLint("ResourceType")
    private void init(android.content.Context context, android.util.AttributeSet attrs) {
        if (attrs != null) {
            int[] attrsArray;
            attrsArray = new int[]{ android.R.attr.columnWidth };
            android.content.res.TypedArray array;
            array = context.obtainStyledAttributes(attrs, attrsArray);
            _columnWidth = array.getDimensionPixelSize(0, -1);
            array.recycle();
        }
        _manager = new androidx.recyclerview.widget.GridLayoutManager(getContext(), 1);
        setLayoutManager(_manager);
    }


    @java.lang.Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (_columnWidth > 0) {
            switch(MUID_STATIC) {
                // IconRecyclerView_0_BinaryMutator
                case 156: {
                    _spanCount = java.lang.Math.max(1, getMeasuredWidth() * _columnWidth);
                    break;
                }
                default: {
                _spanCount = java.lang.Math.max(1, getMeasuredWidth() / _columnWidth);
                break;
            }
        }
        _manager.setSpanCount(_spanCount);
    }
}


public androidx.recyclerview.widget.GridLayoutManager getGridLayoutManager() {
    return _manager;
}


public int getSpanCount() {
    return _spanCount;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
