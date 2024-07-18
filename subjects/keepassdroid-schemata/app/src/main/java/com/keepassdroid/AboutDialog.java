/* Copyright 2009-2020 Brian Pellin.

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
import com.android.keepass.R;
import android.widget.Button;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import androidx.appcompat.app.AppCompatDialog;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AboutDialog extends androidx.appcompat.app.AppCompatDialog {
    static final int MUID_STATIC = getMUID();
    public AboutDialog(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AboutDialog_0_LengthyGUICreationOperatorMutator
            case 204: {
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
    setContentView(com.android.keepass.R.layout.about);
    setTitle(com.android.keepass.R.string.app_name);
    setVersion();
    android.widget.Button okButton;
    switch(MUID_STATIC) {
        // AboutDialog_1_InvalidViewFocusOperatorMutator
        case 1204: {
            /**
            * Inserted by Kadabra
            */
            okButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.about_button)));
            okButton.requestFocus();
            break;
        }
        // AboutDialog_2_ViewComponentNotVisibleOperatorMutator
        case 2204: {
            /**
            * Inserted by Kadabra
            */
            okButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.about_button)));
            okButton.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        okButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.about_button)));
        break;
    }
}
switch(MUID_STATIC) {
    // AboutDialog_3_BuggyGUIListenerOperatorMutator
    case 3204: {
        okButton.setOnClickListener(null);
        break;
    }
    default: {
    okButton.setOnClickListener(new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {
            switch(MUID_STATIC) {
                // AboutDialog_4_LengthyGUIListenerOperatorMutator
                case 4204: {
                    /**
                    * Inserted by Kadabra
                    */
                    dismiss();
                    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                    break;
                }
                default: {
                dismiss();
                break;
            }
        }
    }

});
break;
}
}
}


private void setVersion() {
android.content.Context ctx;
ctx = getContext();
java.lang.String version;
try {
android.content.pm.PackageInfo packageInfo;
packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
version = packageInfo.versionName;
} catch (android.content.pm.PackageManager.NameNotFoundException e) {
e.printStackTrace();
version = "";
}
android.widget.TextView tv;
switch(MUID_STATIC) {
// AboutDialog_5_InvalidViewFocusOperatorMutator
case 5204: {
/**
* Inserted by Kadabra
*/
tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.version)));
tv.requestFocus();
break;
}
// AboutDialog_6_ViewComponentNotVisibleOperatorMutator
case 6204: {
/**
* Inserted by Kadabra
*/
tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.version)));
tv.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
tv = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.version)));
break;
}
}
tv.setText(version);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
