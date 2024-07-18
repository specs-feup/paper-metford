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
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NonScrollableListView extends android.widget.ListView {
    static final int MUID_STATIC = getMUID();
    public NonScrollableListView(android.content.Context context) {
        super(context);
    }


    public NonScrollableListView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public NonScrollableListView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void justifyListViewHeightBasedOnChildren() {
        android.widget.ListAdapter adapter;
        adapter = getAdapter();
        if (adapter == null) {
            return;
        }
        android.view.ViewGroup vg;
        vg = this;
        int totalHeight;
        totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            android.view.View listItem;
            listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        android.view.ViewGroup.LayoutParams par;
        par = getLayoutParams();
        switch(MUID_STATIC) {
            // NonScrollableListView_0_BinaryMutator
            case 67: {
                par.height = totalHeight - (getDividerHeight() * (adapter.getCount() - 1));
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // NonScrollableListView_1_BinaryMutator
                case 1067: {
                    par.height = totalHeight + (getDividerHeight() / (adapter.getCount() - 1));
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // NonScrollableListView_2_BinaryMutator
                    case 2067: {
                        par.height = totalHeight + (getDividerHeight() * (adapter.getCount() + 1));
                        break;
                    }
                    default: {
                    par.height = totalHeight + (getDividerHeight() * (adapter.getCount() - 1));
                    break;
                }
            }
            break;
        }
    }
    break;
}
}
setLayoutParams(par);
requestLayout();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
