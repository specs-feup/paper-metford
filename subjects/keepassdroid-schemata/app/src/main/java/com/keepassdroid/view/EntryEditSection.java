/* Copyright 2013 Brian Pellin.

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
import android.widget.CheckBox;
import com.android.keepass.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.keepassdroid.database.security.ProtectedString;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntryEditSection extends android.widget.RelativeLayout {
    static final int MUID_STATIC = getMUID();
    public EntryEditSection(android.content.Context context) {
        super(context);
    }


    public EntryEditSection(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public EntryEditSection(android.content.Context context, android.util.AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /* public EntryEditSection(Context context, AttributeSet attrs, String title, ProtectedString value) {
    super(context, attrs);

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflate(inflater, context, title, value);

    fillData(title, value);
    }
     */
    /* private int getLayout() {
    return R.layout.entry_edit_section;
    }

    protected void inflate(LayoutInflater inflater, Context context,
    String title, ProtectedString value) {

    inflater.inflate(getLayout(), this);
     */
    public void setData(java.lang.String title, com.keepassdroid.database.security.ProtectedString value) {
        setText(com.android.keepass.R.id.title, title);
        setText(com.android.keepass.R.id.value, value.toString());
        android.widget.CheckBox cb;
        switch(MUID_STATIC) {
            // EntryEditSection_0_InvalidViewFocusOperatorMutator
            case 22: {
                /**
                * Inserted by Kadabra
                */
                cb = ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.protection)));
                cb.requestFocus();
                break;
            }
            // EntryEditSection_1_ViewComponentNotVisibleOperatorMutator
            case 1022: {
                /**
                * Inserted by Kadabra
                */
                cb = ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.protection)));
                cb.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            cb = ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.protection)));
            break;
        }
    }
    cb.setChecked(value.isProtected());
}


private void setText(int resId, java.lang.String str) {
    if (str != null) {
        android.widget.TextView tvTitle;
        switch(MUID_STATIC) {
            // EntryEditSection_2_InvalidViewFocusOperatorMutator
            case 2022: {
                /**
                * Inserted by Kadabra
                */
                tvTitle = ((android.widget.TextView) (findViewById(resId)));
                tvTitle.requestFocus();
                break;
            }
            // EntryEditSection_3_ViewComponentNotVisibleOperatorMutator
            case 3022: {
                /**
                * Inserted by Kadabra
                */
                tvTitle = ((android.widget.TextView) (findViewById(resId)));
                tvTitle.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            tvTitle = ((android.widget.TextView) (findViewById(resId)));
            break;
        }
    }
    tvTitle.setText(str);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
