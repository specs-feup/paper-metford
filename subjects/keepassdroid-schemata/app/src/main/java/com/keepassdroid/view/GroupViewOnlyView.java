/* Copyright 2009-2011 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.view;
import com.android.keepass.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GroupViewOnlyView extends android.widget.RelativeLayout {
    static final int MUID_STATIC = getMUID();
    public GroupViewOnlyView(android.content.Context context) {
        this(context, null);
    }


    public GroupViewOnlyView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        inflate(context);
    }


    private void inflate(android.content.Context context) {
        android.view.LayoutInflater inflater;
        inflater = ((android.view.LayoutInflater) (context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
        inflater.inflate(com.android.keepass.R.layout.group_add_entry, this);
        // Hide the buttons
        android.view.View addGroup;
        switch(MUID_STATIC) {
            // GroupViewOnlyView_0_FindViewByIdReturnsNullOperatorMutator
            case 15: {
                addGroup = null;
                break;
            }
            // GroupViewOnlyView_1_InvalidIDFindViewOperatorMutator
            case 1015: {
                addGroup = findViewById(732221);
                break;
            }
            // GroupViewOnlyView_2_InvalidViewFocusOperatorMutator
            case 2015: {
                /**
                * Inserted by Kadabra
                */
                addGroup = findViewById(com.android.keepass.R.id.add_group);
                addGroup.requestFocus();
                break;
            }
            // GroupViewOnlyView_3_ViewComponentNotVisibleOperatorMutator
            case 3015: {
                /**
                * Inserted by Kadabra
                */
                addGroup = findViewById(com.android.keepass.R.id.add_group);
                addGroup.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            addGroup = findViewById(com.android.keepass.R.id.add_group);
            break;
        }
    }
    addGroup.setVisibility(android.view.View.INVISIBLE);
    android.view.View addEntry;
    switch(MUID_STATIC) {
        // GroupViewOnlyView_4_FindViewByIdReturnsNullOperatorMutator
        case 4015: {
            addEntry = null;
            break;
        }
        // GroupViewOnlyView_5_InvalidIDFindViewOperatorMutator
        case 5015: {
            addEntry = findViewById(732221);
            break;
        }
        // GroupViewOnlyView_6_InvalidViewFocusOperatorMutator
        case 6015: {
            /**
            * Inserted by Kadabra
            */
            addEntry = findViewById(com.android.keepass.R.id.add_entry);
            addEntry.requestFocus();
            break;
        }
        // GroupViewOnlyView_7_ViewComponentNotVisibleOperatorMutator
        case 7015: {
            /**
            * Inserted by Kadabra
            */
            addEntry = findViewById(com.android.keepass.R.id.add_entry);
            addEntry.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        addEntry = findViewById(com.android.keepass.R.id.add_entry);
        break;
    }
}
addEntry.setVisibility(android.view.View.INVISIBLE);
android.view.View divider2;
switch(MUID_STATIC) {
    // GroupViewOnlyView_8_FindViewByIdReturnsNullOperatorMutator
    case 8015: {
        divider2 = null;
        break;
    }
    // GroupViewOnlyView_9_InvalidIDFindViewOperatorMutator
    case 9015: {
        divider2 = findViewById(732221);
        break;
    }
    // GroupViewOnlyView_10_InvalidViewFocusOperatorMutator
    case 10015: {
        /**
        * Inserted by Kadabra
        */
        divider2 = findViewById(com.android.keepass.R.id.divider2);
        divider2.requestFocus();
        break;
    }
    // GroupViewOnlyView_11_ViewComponentNotVisibleOperatorMutator
    case 11015: {
        /**
        * Inserted by Kadabra
        */
        divider2 = findViewById(com.android.keepass.R.id.divider2);
        divider2.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    divider2 = findViewById(com.android.keepass.R.id.divider2);
    break;
}
}
divider2.setVisibility(android.view.View.INVISIBLE);
android.view.View list;
switch(MUID_STATIC) {
// GroupViewOnlyView_12_FindViewByIdReturnsNullOperatorMutator
case 12015: {
    list = null;
    break;
}
// GroupViewOnlyView_13_InvalidIDFindViewOperatorMutator
case 13015: {
    list = findViewById(732221);
    break;
}
// GroupViewOnlyView_14_InvalidViewFocusOperatorMutator
case 14015: {
    /**
    * Inserted by Kadabra
    */
    list = findViewById(com.android.keepass.R.id.group_list);
    list.requestFocus();
    break;
}
// GroupViewOnlyView_15_ViewComponentNotVisibleOperatorMutator
case 15015: {
    /**
    * Inserted by Kadabra
    */
    list = findViewById(com.android.keepass.R.id.group_list);
    list.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
list = findViewById(com.android.keepass.R.id.group_list);
break;
}
}
android.widget.RelativeLayout.LayoutParams lp;
lp = ((android.widget.RelativeLayout.LayoutParams) (list.getLayoutParams()));
lp.addRule(android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM, android.widget.RelativeLayout.TRUE);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
