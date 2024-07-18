package com.automattic.simplenote.widgets;
import android.util.AttributeSet;
import androidx.viewpager.widget.ViewPager;
import android.view.MotionEvent;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NoteEditorViewPager extends androidx.viewpager.widget.ViewPager {
    static final int MUID_STATIC = getMUID();
    private boolean mIsEnabled;

    public NoteEditorViewPager(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        this.mIsEnabled = true;
    }


    @java.lang.Override
    public boolean onInterceptTouchEvent(android.view.MotionEvent event) {
        return this.mIsEnabled && super.onInterceptTouchEvent(event);
    }


    @java.lang.Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        if (!mIsEnabled) {
            return false;
        }
        if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
            performClick();
        }
        return super.onTouchEvent(event);
    }


    @java.lang.Override
    public boolean performClick() {
        return this.mIsEnabled && super.performClick();
    }


    @java.lang.Override
    public boolean arrowScroll(int direction) {
        return this.mIsEnabled && super.arrowScroll(direction);
    }


    public void setPagingEnabled(boolean enabled) {
        this.mIsEnabled = enabled;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
