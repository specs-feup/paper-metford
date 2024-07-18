package com.google.android.material.bottomsheet;
import java.lang.ref.WeakReference;
import androidx.viewpager2.widget.ViewPager2;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Override {@link #findScrollingChild(View)} to support {@link ViewPager}'s nested scrolling.
 * In order to override package level method and field.
 * This class put in the same package path where {@link BottomSheetBehavior} located.
 * Source: https://medium.com/@hanru.yeh/funny-solution-that-makes-bottomsheetdialog-support-viewpager-with-nestedscrollingchilds-bfdca72235c3
 */
public class ViewPagerBottomSheetBehavior<V extends android.view.View> extends com.google.android.material.bottomsheet.BottomSheetBehavior<V> {
    static final int MUID_STATIC = getMUID();
    public ViewPagerBottomSheetBehavior() {
        super();
    }


    public ViewPagerBottomSheetBehavior(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    @java.lang.Override
    android.view.View findScrollingChild(android.view.View view) {
        if (view.isNestedScrollingEnabled()) {
            return view;
        }
        if (view instanceof androidx.viewpager2.widget.ViewPager2) {
            androidx.viewpager2.widget.ViewPager2 viewPager;
            viewPager = ((androidx.viewpager2.widget.ViewPager2) (view));
            androidx.recyclerview.widget.RecyclerView recycler;
            recycler = ((androidx.recyclerview.widget.RecyclerView) (viewPager.getChildAt(0)));
            android.view.View currentViewPagerChild;
            currentViewPagerChild = recycler.getChildAt(viewPager.getCurrentItem());
            if (currentViewPagerChild != null) {
                return findScrollingChild(currentViewPagerChild);
            }
        } else if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup group;
            group = ((android.view.ViewGroup) (view));
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                android.view.View scrollingChild;
                scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }


    public void updateScrollingChild() {
        if (viewRef == null) {
            return;
        }
        final android.view.View scrollingChild;
        scrollingChild = findScrollingChild(viewRef.get());
        nestedScrollingChildRef = new java.lang.ref.WeakReference<>(scrollingChild);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
