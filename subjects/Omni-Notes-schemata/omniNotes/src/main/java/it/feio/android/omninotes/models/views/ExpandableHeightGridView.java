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
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ExpandableHeightGridView extends android.widget.GridView {
    static final int MUID_STATIC = getMUID();
    // private boolean expanded = false;
    private int itemHeight;

    public ExpandableHeightGridView(android.content.Context context) {
        super(context);
    }


    public ExpandableHeightGridView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public ExpandableHeightGridView(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    // 
    // public boolean isExpanded() {
    // return expanded;
    // }
    @java.lang.Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // if (isExpanded()) {
        // Calculate entire height by providing a very large height hint.
        // View.MEASURED_SIZE_MASK represents the largest height possible.
        int expandSpec;
        expandSpec = android.view.View.MeasureSpec.makeMeasureSpec(android.view.View.MEASURED_SIZE_MASK, android.view.View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        android.view.ViewGroup.LayoutParams params;
        params = getLayoutParams();
        params.height = getMeasuredHeight();
        // } else {
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // }
    }


    // public void setExpanded(boolean expanded) {
    // this.expanded = expanded;
    // }
    public void autoresize() {
        // Set gridview height
        // ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int items;
        items = getAdapter().getCount();
        int columns;
        columns = (items == 1) ? 1 : 2;
        setNumColumns(columns);
        // itemHeight = Constants.THUMBNAIL_SIZE * 2 / columns;
        // layoutParams.height = ( (items / columns) + (items % columns) ) * itemHeight; //this is in pixels
        // 
        // setLayoutParams(layoutParams);
    }


    public int getItemHeight() {
        return itemHeight;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
