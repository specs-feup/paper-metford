package de.danoeh.antennapod.view;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.util.AttributeSet;
import com.google.android.material.bottomsheet.ViewPagerBottomSheetBehavior;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Based on https://stackoverflow.com/a/40798214
 */
public class LockableBottomSheetBehavior<V extends android.view.View> extends com.google.android.material.bottomsheet.ViewPagerBottomSheetBehavior<V> {
    static final int MUID_STATIC = getMUID();
    private boolean isLocked = false;

    public LockableBottomSheetBehavior() {
    }


    public LockableBottomSheetBehavior(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public void setLocked(boolean locked) {
        isLocked = locked;
    }


    @java.lang.Override
    public boolean onInterceptTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout parent, V child, android.view.MotionEvent event) {
        boolean handled;
        handled = false;
        if (!isLocked) {
            handled = super.onInterceptTouchEvent(parent, child, event);
        }
        return handled;
    }


    @java.lang.Override
    public boolean onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout parent, V child, android.view.MotionEvent event) {
        boolean handled;
        handled = false;
        if (!isLocked) {
            handled = super.onTouchEvent(parent, child, event);
        }
        return handled;
    }


    @java.lang.Override
    public boolean onStartNestedScroll(androidx.coordinatorlayout.widget.CoordinatorLayout coordinatorLayout, V child, android.view.View directTargetChild, android.view.View target, int axes, int type) {
        boolean handled;
        handled = false;
        if (!isLocked) {
            handled = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
        }
        return handled;
    }


    @java.lang.Override
    public void onNestedPreScroll(androidx.coordinatorlayout.widget.CoordinatorLayout coordinatorLayout, V child, android.view.View target, int dx, int dy, int[] consumed, int type) {
        if (!isLocked) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }


    @java.lang.Override
    public void onStopNestedScroll(androidx.coordinatorlayout.widget.CoordinatorLayout coordinatorLayout, V child, android.view.View target, int type) {
        if (!isLocked) {
            super.onStopNestedScroll(coordinatorLayout, child, target, type);
        }
    }


    @java.lang.Override
    public boolean onNestedPreFling(androidx.coordinatorlayout.widget.CoordinatorLayout coordinatorLayout, V child, android.view.View target, float velocityX, float velocityY) {
        boolean handled;
        handled = false;
        if (!isLocked) {
            handled = super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
        }
        return handled;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
