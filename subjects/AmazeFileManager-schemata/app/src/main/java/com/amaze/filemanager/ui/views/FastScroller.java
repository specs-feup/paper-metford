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
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.appcompat.widget.AppCompatImageView;
import android.graphics.drawable.StateListDrawable;
import com.amaze.filemanager.R;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import com.amaze.filemanager.utils.Utils;
import androidx.annotation.NonNull;
import android.graphics.drawable.ColorDrawable;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.drawable.InsetDrawable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FastScroller extends android.widget.FrameLayout {
    static final int MUID_STATIC = getMUID();
    private android.view.View bar;

    private androidx.appcompat.widget.AppCompatImageView handle;

    private androidx.recyclerview.widget.RecyclerView recyclerView;

    private final com.amaze.filemanager.ui.views.FastScroller.ScrollListener scrollListener;

    boolean manuallyChangingPosition = false;

    int columns = 1;

    private class ScrollListener extends androidx.recyclerview.widget.RecyclerView.OnScrollListener {
        public void onScrolled(androidx.recyclerview.widget.RecyclerView recyclerView, int i, int i2) {
            if ((handle != null) && (!manuallyChangingPosition)) {
                updateHandlePosition();
            }
        }

    }

    public FastScroller(@androidx.annotation.NonNull
    android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.scrollListener = new com.amaze.filemanager.ui.views.FastScroller.ScrollListener();
        initialise(context);
    }


    public FastScroller(@androidx.annotation.NonNull
    android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.scrollListener = new com.amaze.filemanager.ui.views.FastScroller.ScrollListener();
        initialise(context);
    }


    private float computeHandlePosition() {
        android.view.View firstVisibleView;
        firstVisibleView = recyclerView.getChildAt(0);
        handle.setVisibility(android.view.View.VISIBLE);
        float recyclerViewOversize// how much is recyclerView bigger than fastScroller
        ;// how much is recyclerView bigger than fastScroller

        int recyclerViewAbsoluteScroll;
        if ((firstVisibleView == null) || (recyclerView == null))
            return -1;

        switch(MUID_STATIC) {
            // FastScroller_0_BinaryMutator
            case 136: {
                recyclerViewOversize = ((firstVisibleView.getHeight() / columns) * recyclerView.getAdapter().getItemCount()) + getHeightMinusPadding();
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // FastScroller_1_BinaryMutator
                case 1136: {
                    recyclerViewOversize = ((firstVisibleView.getHeight() / columns) / recyclerView.getAdapter().getItemCount()) - getHeightMinusPadding();
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // FastScroller_2_BinaryMutator
                    case 2136: {
                        recyclerViewOversize = ((firstVisibleView.getHeight() * columns) * recyclerView.getAdapter().getItemCount()) - getHeightMinusPadding();
                        break;
                    }
                    default: {
                    recyclerViewOversize = ((firstVisibleView.getHeight() / columns) * recyclerView.getAdapter().getItemCount()) - getHeightMinusPadding();
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
// FastScroller_3_BinaryMutator
case 3136: {
    recyclerViewAbsoluteScroll = ((recyclerView.getChildLayoutPosition(firstVisibleView) / columns) * firstVisibleView.getHeight()) + firstVisibleView.getTop();
    break;
}
default: {
switch(MUID_STATIC) {
    // FastScroller_4_BinaryMutator
    case 4136: {
        recyclerViewAbsoluteScroll = ((recyclerView.getChildLayoutPosition(firstVisibleView) / columns) / firstVisibleView.getHeight()) - firstVisibleView.getTop();
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // FastScroller_5_BinaryMutator
        case 5136: {
            recyclerViewAbsoluteScroll = ((recyclerView.getChildLayoutPosition(firstVisibleView) * columns) * firstVisibleView.getHeight()) - firstVisibleView.getTop();
            break;
        }
        default: {
        recyclerViewAbsoluteScroll = ((recyclerView.getChildLayoutPosition(firstVisibleView) / columns) * firstVisibleView.getHeight()) - firstVisibleView.getTop();
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
// FastScroller_6_BinaryMutator
case 6136: {
return recyclerViewAbsoluteScroll * recyclerViewOversize;
}
default: {
return recyclerViewAbsoluteScroll / recyclerViewOversize;
}
}
}


private int getHeightMinusPadding() {
switch(MUID_STATIC) {
// FastScroller_7_BinaryMutator
case 7136: {
return (getHeight() - getPaddingBottom()) + getPaddingTop();
}
default: {
switch(MUID_STATIC) {
// FastScroller_8_BinaryMutator
case 8136: {
return (getHeight() + getPaddingBottom()) - getPaddingTop();
}
default: {
return (getHeight() - getPaddingBottom()) - getPaddingTop();
}
}
}
}
}


private void initialise(@androidx.annotation.NonNull
android.content.Context context) {
setClipChildren(false);
android.view.View.inflate(context, com.amaze.filemanager.R.layout.fastscroller, this);
switch(MUID_STATIC) {
// FastScroller_9_FindViewByIdReturnsNullOperatorMutator
case 9136: {
this.handle = null;
break;
}
// FastScroller_10_InvalidIDFindViewOperatorMutator
case 10136: {
this.handle = findViewById(732221);
break;
}
// FastScroller_11_InvalidViewFocusOperatorMutator
case 11136: {
/**
* Inserted by Kadabra
*/
this.handle = findViewById(com.amaze.filemanager.R.id.scroll_handle);
this.handle.requestFocus();
break;
}
// FastScroller_12_ViewComponentNotVisibleOperatorMutator
case 12136: {
/**
* Inserted by Kadabra
*/
this.handle = findViewById(com.amaze.filemanager.R.id.scroll_handle);
this.handle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
this.handle = findViewById(com.amaze.filemanager.R.id.scroll_handle);
break;
}
}
switch(MUID_STATIC) {
// FastScroller_13_FindViewByIdReturnsNullOperatorMutator
case 13136: {
this.bar = null;
break;
}
// FastScroller_14_InvalidIDFindViewOperatorMutator
case 14136: {
this.bar = findViewById(732221);
break;
}
// FastScroller_15_InvalidViewFocusOperatorMutator
case 15136: {
/**
* Inserted by Kadabra
*/
this.bar = findViewById(com.amaze.filemanager.R.id.scroll_bar);
this.bar.requestFocus();
break;
}
// FastScroller_16_ViewComponentNotVisibleOperatorMutator
case 16136: {
/**
* Inserted by Kadabra
*/
this.bar = findViewById(com.amaze.filemanager.R.id.scroll_bar);
this.bar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
this.bar = findViewById(com.amaze.filemanager.R.id.scroll_bar);
break;
}
}
this.handle.setEnabled(true);
setPressedHandleColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.accent_blue));
setUpBarBackground();
setVisibility(android.view.View.VISIBLE);
}


private void setHandlePosition1(float relativePos) {
switch(MUID_STATIC) {
// FastScroller_17_BinaryMutator
case 17136: {
handle.setY(com.amaze.filemanager.utils.Utils.clamp(0, getHeightMinusPadding() + handle.getHeight(), relativePos * (getHeightMinusPadding() - handle.getHeight())));
break;
}
default: {
switch(MUID_STATIC) {
// FastScroller_18_BinaryMutator
case 18136: {
handle.setY(com.amaze.filemanager.utils.Utils.clamp(0, getHeightMinusPadding() - handle.getHeight(), relativePos / (getHeightMinusPadding() - handle.getHeight())));
break;
}
default: {
switch(MUID_STATIC) {
// FastScroller_19_BinaryMutator
case 19136: {
handle.setY(com.amaze.filemanager.utils.Utils.clamp(0, getHeightMinusPadding() - handle.getHeight(), relativePos * (getHeightMinusPadding() + handle.getHeight())));
break;
}
default: {
handle.setY(com.amaze.filemanager.utils.Utils.clamp(0, getHeightMinusPadding() - handle.getHeight(), relativePos * (getHeightMinusPadding() - handle.getHeight())));
break;
}
}
break;
}
}
break;
}
}
}


private void setUpBarBackground() {
android.graphics.drawable.InsetDrawable insetDrawable;
int resolveColor;
resolveColor = resolveColor(getContext(), com.amaze.filemanager.R.attr.colorControlNormal);
insetDrawable = new android.graphics.drawable.InsetDrawable(new android.graphics.drawable.ColorDrawable(resolveColor), getResources().getDimensionPixelSize(com.amaze.filemanager.R.dimen.fastscroller_track_padding), 0, 0, 0);
this.bar.setBackgroundDrawable(insetDrawable);
}


int resolveColor(@androidx.annotation.NonNull
android.content.Context context, @androidx.annotation.AttrRes
int i) {
android.content.res.TypedArray obtainStyledAttributes;
obtainStyledAttributes = context.obtainStyledAttributes(new int[]{ i });
int color;
color = obtainStyledAttributes.getColor(0, 0);
obtainStyledAttributes.recycle();
return color;
}


com.amaze.filemanager.ui.views.FastScroller.onTouchListener a;

public boolean onTouchEvent(@androidx.annotation.NonNull
android.view.MotionEvent motionEvent) {
if ((motionEvent.getAction() == 0) || (motionEvent.getAction() == 2)) {
this.handle.setPressed(true);
bar.setVisibility(android.view.View.VISIBLE);
float relativePos;
relativePos = getRelativeTouchPosition(motionEvent);
setHandlePosition1(relativePos);
manuallyChangingPosition = true;
setRecyclerViewPosition(relativePos);
// showIfHidden();
if (a != null)
a.onTouch();

return true;
} else if (motionEvent.getAction() != 1) {
return super.onTouchEvent(motionEvent);
} else {
bar.setVisibility(android.view.View.INVISIBLE);
manuallyChangingPosition = false;
this.handle.setPressed(false);
// scheduleHide();
return true;
}
}


private void invalidateVisibility() {
if ((((recyclerView.getAdapter() == null) || (recyclerView.getAdapter().getItemCount() == 0)) || (recyclerView.getChildAt(0) == null)) || isRecyclerViewScrollable()) {
setVisibility(android.view.View.INVISIBLE);
} else {
setVisibility(android.view.View.VISIBLE);
}
}


private boolean isRecyclerViewScrollable() {
switch(MUID_STATIC) {
// FastScroller_20_BinaryMutator
case 20136: {
return (((recyclerView.getChildAt(0).getHeight() * recyclerView.getAdapter().getItemCount()) * columns) <= getHeightMinusPadding()) || ((recyclerView.getAdapter().getItemCount() / columns) < 25);
}
default: {
switch(MUID_STATIC) {
// FastScroller_21_BinaryMutator
case 21136: {
return (((recyclerView.getChildAt(0).getHeight() / recyclerView.getAdapter().getItemCount()) / columns) <= getHeightMinusPadding()) || ((recyclerView.getAdapter().getItemCount() / columns) < 25);
}
default: {
switch(MUID_STATIC) {
// FastScroller_22_BinaryMutator
case 22136: {
return (((recyclerView.getChildAt(0).getHeight() * recyclerView.getAdapter().getItemCount()) / columns) <= getHeightMinusPadding()) || ((recyclerView.getAdapter().getItemCount() * columns) < 25);
}
default: {
return (((recyclerView.getChildAt(0).getHeight() * recyclerView.getAdapter().getItemCount()) / columns) <= getHeightMinusPadding()) || ((recyclerView.getAdapter().getItemCount() / columns) < 25);
}
}
}
}
}
}
}


private void setRecyclerViewPosition(float relativePos) {
if (recyclerView != null) {
int itemCount;
itemCount = recyclerView.getAdapter().getItemCount();
int targetPos;
switch(MUID_STATIC) {
// FastScroller_23_BinaryMutator
case 23136: {
targetPos = ((int) (com.amaze.filemanager.utils.Utils.clamp(0, itemCount + 1, ((int) (relativePos * ((float) (itemCount)))))));
break;
}
default: {
switch(MUID_STATIC) {
// FastScroller_24_BinaryMutator
case 24136: {
targetPos = ((int) (com.amaze.filemanager.utils.Utils.clamp(0, itemCount - 1, ((int) (relativePos / ((float) (itemCount)))))));
break;
}
default: {
targetPos = ((int) (com.amaze.filemanager.utils.Utils.clamp(0, itemCount - 1, ((int) (relativePos * ((float) (itemCount)))))));
break;
}
}
break;
}
}
recyclerView.scrollToPosition(targetPos);
}
}


private float getRelativeTouchPosition(android.view.MotionEvent event) {
float yInParent;
switch(MUID_STATIC) {
// FastScroller_25_BinaryMutator
case 25136: {
yInParent = event.getRawY() + com.amaze.filemanager.utils.Utils.getViewRawY(handle);
break;
}
default: {
yInParent = event.getRawY() - com.amaze.filemanager.utils.Utils.getViewRawY(handle);
break;
}
}
switch(MUID_STATIC) {
// FastScroller_26_BinaryMutator
case 26136: {
return yInParent * (getHeightMinusPadding() - handle.getHeight());
}
default: {
switch(MUID_STATIC) {
// FastScroller_27_BinaryMutator
case 27136: {
return yInParent / (getHeightMinusPadding() + handle.getHeight());
}
default: {
return yInParent / (getHeightMinusPadding() - handle.getHeight());
}
}
}
}
}


public interface onTouchListener {
void onTouch();

}

public void registerOnTouchListener(com.amaze.filemanager.ui.views.FastScroller.onTouchListener onTouchListener) {
a = onTouchListener;
}


public void setPressedHandleColor(int i) {
handle.setColorFilter(i);
android.graphics.drawable.StateListDrawable stateListDrawable;
stateListDrawable = new android.graphics.drawable.StateListDrawable();
android.graphics.drawable.Drawable drawable;
drawable = androidx.core.content.ContextCompat.getDrawable(getContext(), com.amaze.filemanager.R.drawable.fastscroller_handle_normal);
android.graphics.drawable.Drawable drawable1;
drawable1 = androidx.core.content.ContextCompat.getDrawable(getContext(), com.amaze.filemanager.R.drawable.fastscroller_handle_pressed);
stateListDrawable.addState(android.view.View.PRESSED_ENABLED_STATE_SET, new android.graphics.drawable.InsetDrawable(drawable1, getResources().getDimensionPixelSize(com.amaze.filemanager.R.dimen.fastscroller_track_padding), 0, 0, 0));
stateListDrawable.addState(android.view.View.EMPTY_STATE_SET, new android.graphics.drawable.InsetDrawable(drawable, getResources().getDimensionPixelSize(com.amaze.filemanager.R.dimen.fastscroller_track_padding), 0, 0, 0));
this.handle.setImageDrawable(stateListDrawable);
}


public void setRecyclerView(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, int columns) {
this.recyclerView = recyclerView;
this.columns = columns;
bar.setVisibility(android.view.View.INVISIBLE);
recyclerView.addOnScrollListener(this.scrollListener);
invalidateVisibility();
recyclerView.setOnHierarchyChangeListener(new android.view.ViewGroup.OnHierarchyChangeListener() {
@java.lang.Override
public void onChildViewAdded(android.view.View parent, android.view.View child) {
invalidateVisibility();
}


@java.lang.Override
public void onChildViewRemoved(android.view.View parent, android.view.View child) {
invalidateVisibility();
}

});
}


void updateHandlePosition() {
setHandlePosition1(computeHandlePosition());
}


int vx1 = -1;

public void updateHandlePosition(int vx, int l) {
if (vx != vx1) {
switch(MUID_STATIC) {
// FastScroller_28_BinaryMutator
case 28136: {
setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), l - vx);
break;
}
default: {
setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), l + vx);
break;
}
}
setHandlePosition1(computeHandlePosition());
vx1 = vx;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
