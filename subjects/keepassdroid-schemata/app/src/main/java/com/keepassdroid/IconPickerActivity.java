/* Copyright 2010 Brian Pellin.

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
package com.keepassdroid;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import android.view.ViewGroup;
import com.keepassdroid.icons.Icons;
import android.content.Intent;
import android.view.View;
import com.android.keepass.R;
import android.view.LayoutInflater;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconPickerActivity extends com.keepassdroid.LockCloseActivity {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_ICON_ID = "icon_id";

    public static void Launch(android.app.Activity act) {
        android.content.Intent i;
        switch(MUID_STATIC) {
            // IconPickerActivity_0_NullIntentOperatorMutator
            case 214: {
                i = null;
                break;
            }
            // IconPickerActivity_1_InvalidKeyIntentOperatorMutator
            case 1214: {
                i = new android.content.Intent((Activity) null, com.keepassdroid.IconPickerActivity.class);
                break;
            }
            // IconPickerActivity_2_RandomActionIntentDefinitionOperatorMutator
            case 2214: {
                i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i = new android.content.Intent(act, com.keepassdroid.IconPickerActivity.class);
            break;
        }
    }
    act.startActivityForResult(i, 0);
}


@java.lang.Override
public void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    switch(MUID_STATIC) {
        // IconPickerActivity_3_LengthyGUICreationOperatorMutator
        case 3214: {
            /**
            * Inserted by Kadabra
            */
            /**
            * Inserted by Kadabra
            */
            // AFTER SUPER
            try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
            break;
        }
        default: {
        // AFTER SUPER
        break;
    }
}
setContentView(com.android.keepass.R.layout.icon_picker);
android.widget.GridView currIconGridView;
switch(MUID_STATIC) {
    // IconPickerActivity_4_InvalidViewFocusOperatorMutator
    case 4214: {
        /**
        * Inserted by Kadabra
        */
        currIconGridView = ((android.widget.GridView) (findViewById(com.android.keepass.R.id.IconGridView)));
        currIconGridView.requestFocus();
        break;
    }
    // IconPickerActivity_5_ViewComponentNotVisibleOperatorMutator
    case 5214: {
        /**
        * Inserted by Kadabra
        */
        currIconGridView = ((android.widget.GridView) (findViewById(com.android.keepass.R.id.IconGridView)));
        currIconGridView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    currIconGridView = ((android.widget.GridView) (findViewById(com.android.keepass.R.id.IconGridView)));
    break;
}
}
currIconGridView.setAdapter(new com.keepassdroid.IconPickerActivity.ImageAdapter(this));
currIconGridView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
public void onItemClick(android.widget.AdapterView<?> parent, android.view.View v, int position, long id) {
    final android.content.Intent intent;
    switch(MUID_STATIC) {
        // IconPickerActivity_6_RandomActionIntentDefinitionOperatorMutator
        case 6214: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent();
        break;
    }
}
switch(MUID_STATIC) {
    // IconPickerActivity_7_NullValueIntentPutExtraOperatorMutator
    case 7214: {
        intent.putExtra(com.keepassdroid.IconPickerActivity.KEY_ICON_ID, new Parcelable[0]);
        break;
    }
    // IconPickerActivity_8_IntentPayloadReplacementOperatorMutator
    case 8214: {
        intent.putExtra(com.keepassdroid.IconPickerActivity.KEY_ICON_ID, 0);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // IconPickerActivity_9_RandomActionIntentDefinitionOperatorMutator
        case 9214: {
            /**
            * Inserted by Kadabra
            */
            /**
            * Inserted by Kadabra
            */
            new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent.putExtra(com.keepassdroid.IconPickerActivity.KEY_ICON_ID, position);
        break;
    }
}
break;
}
}
setResult(com.keepassdroid.EntryEditActivity.RESULT_OK_ICON_PICKER, intent);
finish();
}

});
}


public class ImageAdapter extends android.widget.BaseAdapter {
android.content.Context mContext;

public ImageAdapter(android.content.Context c) {
mContext = c;
}


public int getCount() {
/* Return number of KeePass icons */
return com.keepassdroid.icons.Icons.count();
}


public java.lang.Object getItem(int position) {
return null;
}


public long getItemId(int position) {
return 0;
}


public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
android.view.View currView;
if (convertView == null) {
android.view.LayoutInflater li;
li = ((android.view.LayoutInflater) (getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
currView = li.inflate(com.android.keepass.R.layout.icon, parent, false);
} else {
currView = convertView;
}
android.widget.TextView tv;
switch(MUID_STATIC) {
// IconPickerActivity_10_InvalidViewFocusOperatorMutator
case 10214: {
/**
* Inserted by Kadabra
*/
tv = ((android.widget.TextView) (currView.findViewById(com.android.keepass.R.id.icon_text)));
tv.requestFocus();
break;
}
// IconPickerActivity_11_ViewComponentNotVisibleOperatorMutator
case 11214: {
/**
* Inserted by Kadabra
*/
tv = ((android.widget.TextView) (currView.findViewById(com.android.keepass.R.id.icon_text)));
tv.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
tv = ((android.widget.TextView) (currView.findViewById(com.android.keepass.R.id.icon_text)));
break;
}
}
tv.setText("" + position);
android.widget.ImageView iv;
switch(MUID_STATIC) {
// IconPickerActivity_12_InvalidViewFocusOperatorMutator
case 12214: {
/**
* Inserted by Kadabra
*/
iv = ((android.widget.ImageView) (currView.findViewById(com.android.keepass.R.id.icon_image)));
iv.requestFocus();
break;
}
// IconPickerActivity_13_ViewComponentNotVisibleOperatorMutator
case 13214: {
/**
* Inserted by Kadabra
*/
iv = ((android.widget.ImageView) (currView.findViewById(com.android.keepass.R.id.icon_image)));
iv.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
iv = ((android.widget.ImageView) (currView.findViewById(com.android.keepass.R.id.icon_image)));
break;
}
}
iv.setImageResource(com.keepassdroid.icons.Icons.iconToResId(position));
return currView;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
