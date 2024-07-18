/* Copyright 2019 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Source: https://github.com/android/views-widgets-samples/blob/87e58d1/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt
And modified for our need
 */
package de.danoeh.antennapod.view;
import android.content.res.TypedArray;
import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;
import android.view.MotionEvent;
import android.view.View;
import static androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL;
import androidx.viewpager2.widget.ViewPager2;
import android.view.ViewConfiguration;
import de.danoeh.antennapod.R;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Layout to wrap a scrollable component inside a ViewPager2. Provided as a solution to the problem
 * where pages of ViewPager2 have nested scrollable elements that scroll in the same direction as
 * ViewPager2. The scrollable element needs to be the immediate and only child of this host layout.
 *
 * This solution has limitations when using multiple levels of nested scrollable elements
 * (e.g. a horizontal RecyclerView in a vertical RecyclerView in a horizontal ViewPager2).
 */
// KhaledAlharthi/NestedScrollableHost.java
public class NestedScrollableHost extends android.widget.FrameLayout {
    static final int MUID_STATIC = getMUID();
    private androidx.viewpager2.widget.ViewPager2 parentViewPager;

    private int touchSlop = 0;

    private float initialX = 0.0F;

    private float initialY = 0.0F;

    private int preferVertical = 1;

    private int preferHorizontal = 1;

    private int scrollDirection = 0;

    public NestedScrollableHost(@androidx.annotation.NonNull
    android.content.Context context) {
        super(context);
        init(context);
    }


    public NestedScrollableHost(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setAttributes(context, attrs);
    }


    public NestedScrollableHost(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setAttributes(context, attrs);
    }


    public NestedScrollableHost(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        setAttributes(context, attrs);
    }


    private void setAttributes(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.Nullable
    android.util.AttributeSet attrs) {
        android.content.res.TypedArray a;
        a = context.getTheme().obtainStyledAttributes(attrs, de.danoeh.antennapod.R.styleable.NestedScrollableHost, 0, 0);
        try {
            preferHorizontal = a.getInteger(de.danoeh.antennapod.R.styleable.NestedScrollableHost_preferHorizontal, 1);
            preferVertical = a.getInteger(de.danoeh.antennapod.R.styleable.NestedScrollableHost_preferVertical, 1);
            scrollDirection = a.getInteger(de.danoeh.antennapod.R.styleable.NestedScrollableHost_scrollDirection, 0);
        } finally {
            a.recycle();
        }
    }


    private void init(android.content.Context context) {
        touchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() {
            @java.lang.Override
            public boolean onPreDraw() {
                android.view.View v;
                v = ((android.view.View) (getParent()));
                while (((v != null) && (!(v instanceof androidx.viewpager2.widget.ViewPager2))) || isntSameDirection(v)) {
                    v = ((android.view.View) (v.getParent()));
                } 
                parentViewPager = ((androidx.viewpager2.widget.ViewPager2) (v));
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }

        });
    }


    private java.lang.Boolean isntSameDirection(android.view.View v) {
        int orientation;
        orientation = 0;
        switch (scrollDirection) {
            default :
            case 0 :
                return false;
            case 1 :
                orientation = androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL;
                break;
            case 2 :
                orientation = androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;
                break;
        }
        return (v instanceof androidx.viewpager2.widget.ViewPager2) && (((androidx.viewpager2.widget.ViewPager2) (v)).getOrientation() != orientation);
    }


    @java.lang.Override
    public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
        handleInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }


    private boolean canChildScroll(int orientation, float delta) {
        int direction;
        direction = ((int) (-delta));
        android.view.View child;
        child = getChildAt(0);
        if (orientation == 0) {
            return child.canScrollHorizontally(direction);
        } else if (orientation == 1) {
            return child.canScrollVertically(direction);
        } else {
            throw new java.lang.IllegalArgumentException();
        }
    }


    private void handleInterceptTouchEvent(android.view.MotionEvent e) {
        if (parentViewPager == null) {
            return;
        }
        int orientation;
        orientation = parentViewPager.getOrientation();
        boolean preferedDirection;
        switch(MUID_STATIC) {
            // NestedScrollableHost_0_BinaryMutator
            case 17: {
                preferedDirection = (preferHorizontal - preferVertical) > 2;
                break;
            }
            default: {
            preferedDirection = (preferHorizontal + preferVertical) > 2;
            break;
        }
    }
    // Early return if child can't scroll in same direction as parent and theres no prefered scroll direction
    if (((!canChildScroll(orientation, -1.0F)) && (!canChildScroll(orientation, 1.0F))) && (!preferedDirection)) {
        return;
    }
    if (e.getAction() == android.view.MotionEvent.ACTION_DOWN) {
        initialX = e.getX();
        initialY = e.getY();
        getParent().requestDisallowInterceptTouchEvent(true);
    } else if (e.getAction() == android.view.MotionEvent.ACTION_MOVE) {
        float dx;
        switch(MUID_STATIC) {
            // NestedScrollableHost_1_BinaryMutator
            case 1017: {
                dx = e.getX() + initialX;
                break;
            }
            default: {
            dx = e.getX() - initialX;
            break;
        }
    }
    float dy;
    switch(MUID_STATIC) {
        // NestedScrollableHost_2_BinaryMutator
        case 2017: {
            dy = e.getY() + initialY;
            break;
        }
        default: {
        dy = e.getY() - initialY;
        break;
    }
}
boolean isVpHorizontal;
isVpHorizontal = orientation == androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL;
// assuming ViewPager2 touch-slop is 2x touch-slop of child
float scaledDx;
switch(MUID_STATIC) {
    // NestedScrollableHost_3_BinaryMutator
    case 3017: {
        scaledDx = (java.lang.Math.abs(dx) * (isVpHorizontal ? 1.0F : 0.5F)) / preferHorizontal;
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // NestedScrollableHost_4_BinaryMutator
        case 4017: {
            scaledDx = (java.lang.Math.abs(dx) / (isVpHorizontal ? 1.0F : 0.5F)) * preferHorizontal;
            break;
        }
        default: {
        scaledDx = (java.lang.Math.abs(dx) * (isVpHorizontal ? 1.0F : 0.5F)) * preferHorizontal;
        break;
    }
}
break;
}
}
float scaledDy;
switch(MUID_STATIC) {
// NestedScrollableHost_5_BinaryMutator
case 5017: {
scaledDy = (java.lang.Math.abs(dy) * (isVpHorizontal ? 0.5F : 1.0F)) / preferVertical;
break;
}
default: {
switch(MUID_STATIC) {
// NestedScrollableHost_6_BinaryMutator
case 6017: {
    scaledDy = (java.lang.Math.abs(dy) / (isVpHorizontal ? 0.5F : 1.0F)) * preferVertical;
    break;
}
default: {
scaledDy = (java.lang.Math.abs(dy) * (isVpHorizontal ? 0.5F : 1.0F)) * preferVertical;
break;
}
}
break;
}
}
if ((scaledDx > touchSlop) || (scaledDy > touchSlop)) {
if (isVpHorizontal == (scaledDy > scaledDx)) {
// Gesture is perpendicular, allow all parents to intercept
getParent().requestDisallowInterceptTouchEvent(preferedDirection);
} else // Gesture is parallel, query child if movement in that direction is possible
if (canChildScroll(orientation, isVpHorizontal ? dx : dy)) {
// Child can scroll, disallow all parents to intercept
getParent().requestDisallowInterceptTouchEvent(true);
} else {
// Child cannot scroll, allow all parents to intercept
getParent().requestDisallowInterceptTouchEvent(false);
}
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
