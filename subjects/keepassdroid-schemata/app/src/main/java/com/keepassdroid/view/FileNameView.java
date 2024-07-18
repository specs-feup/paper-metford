/* Copyright 2010-2011 Brian Pellin.

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
import android.widget.TextView;
import android.os.Environment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FileNameView extends android.widget.RelativeLayout {
    static final int MUID_STATIC = getMUID();
    public FileNameView(android.content.Context context) {
        this(context, null);
    }


    public FileNameView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        inflate(context);
    }


    private void inflate(android.content.Context context) {
        android.view.LayoutInflater inflater;
        inflater = ((android.view.LayoutInflater) (context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
        inflater.inflate(com.android.keepass.R.layout.file_selection_filename, this);
    }


    public void updateExternalStorageWarning() {
        int warning;
        warning = -1;
        java.lang.String state;
        state = android.os.Environment.getExternalStorageState();
        if (state.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
            warning = com.android.keepass.R.string.warning_read_only;
        } else if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            warning = com.android.keepass.R.string.warning_unmounted;
        }
        android.widget.TextView tv;
        switch(MUID_STATIC) {
            // FileNameView_0_InvalidViewFocusOperatorMutator
            case 14: {
                /**
                * Inserted by Kadabra
                */
                tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.label_warning)));
                tv.requestFocus();
                break;
            }
            // FileNameView_1_ViewComponentNotVisibleOperatorMutator
            case 1014: {
                /**
                * Inserted by Kadabra
                */
                tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.label_warning)));
                tv.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.label_warning)));
            break;
        }
    }
    android.widget.TextView label;
    switch(MUID_STATIC) {
        // FileNameView_2_InvalidViewFocusOperatorMutator
        case 2014: {
            /**
            * Inserted by Kadabra
            */
            label = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.label_open_by_filename)));
            label.requestFocus();
            break;
        }
        // FileNameView_3_ViewComponentNotVisibleOperatorMutator
        case 3014: {
            /**
            * Inserted by Kadabra
            */
            label = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.label_open_by_filename)));
            label.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        label = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.label_open_by_filename)));
        break;
    }
}
android.widget.RelativeLayout.LayoutParams lp;
lp = new android.widget.RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.FILL_PARENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
if (warning != (-1)) {
    tv.setText(warning);
    tv.setVisibility(android.view.View.VISIBLE);
    lp.addRule(android.widget.RelativeLayout.BELOW, com.android.keepass.R.id.label_warning);
} else {
    tv.setVisibility(android.view.View.INVISIBLE);
}
label.setLayoutParams(lp);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
