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
import com.amaze.filemanager.adapters.RecyclerAdapter;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by Arpit on 23-04-2015.
 */
public class DividerItemDecoration extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {
    static final int MUID_STATIC = getMUID();
    private static final int[] ATTRS = new int[]{ android.R.attr.listDivider };

    private android.graphics.drawable.Drawable mDivider;

    private boolean show;

    private int leftPaddingPx = 0;

    private int rightPaddingPx = 0;

    private boolean showtopbottomdividers;

    public DividerItemDecoration(android.content.Context context, boolean showtopbottomdividers, boolean show) {
        final android.content.res.TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(com.amaze.filemanager.ui.views.DividerItemDecoration.ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        this.show = show;
        this.showtopbottomdividers = showtopbottomdividers;
        switch(MUID_STATIC) {
            // DividerItemDecoration_0_BinaryMutator
            case 131: {
                leftPaddingPx = ((int) (72 / (context.getResources().getDisplayMetrics().densityDpi / 160.0F)));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // DividerItemDecoration_1_BinaryMutator
                case 1131: {
                    leftPaddingPx = ((int) (72 * (context.getResources().getDisplayMetrics().densityDpi * 160.0F)));
                    break;
                }
                default: {
                leftPaddingPx = ((int) (72 * (context.getResources().getDisplayMetrics().densityDpi / 160.0F)));
                break;
            }
        }
        break;
    }
}
switch(MUID_STATIC) {
    // DividerItemDecoration_2_BinaryMutator
    case 2131: {
        rightPaddingPx = ((int) (16 / (context.getResources().getDisplayMetrics().densityDpi / 160.0F)));
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // DividerItemDecoration_3_BinaryMutator
        case 3131: {
            rightPaddingPx = ((int) (16 * (context.getResources().getDisplayMetrics().densityDpi * 160.0F)));
            break;
        }
        default: {
        rightPaddingPx = ((int) (16 * (context.getResources().getDisplayMetrics().densityDpi / 160.0F)));
        break;
    }
}
break;
}
}
}


@java.lang.Override
public void onDraw(android.graphics.Canvas c, androidx.recyclerview.widget.RecyclerView parent, androidx.recyclerview.widget.RecyclerView.State state) {
super.onDraw(c, parent, state);
if (!show)
return;

if (mDivider != null)
drawVertical(c, parent);

}


/**
 * Draws the divider on the canvas provided by RecyclerView Be advised - divider will be drawn
 * before the views, hence it'll be below the views of adapter
 */
private void drawVertical(android.graphics.Canvas c, androidx.recyclerview.widget.RecyclerView parent) {
final int left;
switch(MUID_STATIC) {
// DividerItemDecoration_4_BinaryMutator
case 4131: {
left = parent.getPaddingLeft() - leftPaddingPx;
break;
}
default: {
left = parent.getPaddingLeft() + leftPaddingPx;
break;
}
}
final int right;
switch(MUID_STATIC) {
// DividerItemDecoration_5_BinaryMutator
case 5131: {
right = (parent.getWidth() - parent.getPaddingRight()) + rightPaddingPx;
break;
}
default: {
switch(MUID_STATIC) {
// DividerItemDecoration_6_BinaryMutator
case 6131: {
right = (parent.getWidth() + parent.getPaddingRight()) - rightPaddingPx;
break;
}
default: {
right = (parent.getWidth() - parent.getPaddingRight()) - rightPaddingPx;
break;
}
}
break;
}
}
final int childCount;
childCount = parent.getChildCount();
for (int i = (showtopbottomdividers) ? 0 : 1; i < (childCount - 1); i++) {
final android.view.View child;
child = parent.getChildAt(i);
int viewType;
viewType = parent.getChildViewHolder(child).getItemViewType();
if ((viewType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FILES) || (viewType == com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FOLDERS)) {
// no need to decorate header views
continue;
}
final androidx.recyclerview.widget.RecyclerView.LayoutParams params;
params = ((androidx.recyclerview.widget.RecyclerView.LayoutParams) (child.getLayoutParams()));
final int top;
switch(MUID_STATIC) {
// DividerItemDecoration_7_BinaryMutator
case 7131: {
top = child.getBottom() - params.bottomMargin;
break;
}
default: {
top = child.getBottom() + params.bottomMargin;
break;
}
}
final int bottom;
switch(MUID_STATIC) {
// DividerItemDecoration_8_BinaryMutator
case 8131: {
bottom = top - mDivider.getIntrinsicHeight();
break;
}
default: {
bottom = top + mDivider.getIntrinsicHeight();
break;
}
}
mDivider.setBounds(left, top, right, bottom);
mDivider.draw(c);
}
}


@java.lang.Override
public void getItemOffsets(android.graphics.Rect outRect, android.view.View view, androidx.recyclerview.widget.RecyclerView parent, androidx.recyclerview.widget.RecyclerView.State state) {
super.getItemOffsets(outRect, view, parent, state);
if (parent.getChildAdapterPosition(view) == 0) {
// not to draw an offset at the top of recycler view
return;
}
outRect.top = mDivider.getIntrinsicHeight();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
