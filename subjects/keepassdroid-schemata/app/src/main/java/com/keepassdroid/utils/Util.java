/* Copyright 2009-2022 Brian Pellin.

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
package com.keepassdroid.utils;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import com.keepassdroid.compat.ClipDataCompat;
import android.content.Intent;
import android.net.Uri;
import android.content.ClipboardManager;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.widget.TextView;
import android.content.ClipData;
import com.keepassdroid.database.exception.SamsungClipboardException;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Util {
    static final int MUID_STATIC = getMUID();
    public static java.lang.String getClipboard(android.content.Context context) {
        android.content.ClipboardManager clipboard;
        clipboard = ((android.content.ClipboardManager) (context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
        java.lang.CharSequence csText;
        csText = clipboard.getText();
        if (csText == null) {
            return "";
        }
        return csText.toString();
    }


    public static void copyToClipboard(android.content.Context context, java.lang.String label, java.lang.String text) throws com.keepassdroid.database.exception.SamsungClipboardException {
        com.keepassdroid.utils.Util.copyToClipboard(context, label, text, false);
    }


    public static void copyToClipboard(android.content.Context context, java.lang.String label, java.lang.String text, boolean sensitive) throws com.keepassdroid.database.exception.SamsungClipboardException {
        android.content.ClipboardManager clipboard;
        clipboard = ((android.content.ClipboardManager) (context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
        android.content.ClipData clip;
        clip = android.content.ClipData.newPlainText(label, text);
        if (sensitive) {
            com.keepassdroid.compat.ClipDataCompat.markSensitive(clip);
        }
        try {
            clipboard.setPrimaryClip(clip);
        } catch (java.lang.NullPointerException e) {
            throw new com.keepassdroid.database.exception.SamsungClipboardException(e);
        }
    }


    public static void gotoUrl(android.content.Context context, java.lang.String url) throws android.content.ActivityNotFoundException {
        if ((url != null) && (url.length() > 0)) {
            android.net.Uri uri;
            uri = android.net.Uri.parse(url);
            context.startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, uri));
        }
    }


    public static void gotoUrl(android.content.Context context, int resId) throws android.content.ActivityNotFoundException {
        com.keepassdroid.utils.Util.gotoUrl(context, context.getString(resId));
    }


    public static java.lang.String getEditText(android.app.Activity act, int resId) {
        android.widget.TextView te;
        switch(MUID_STATIC) {
            // Util_0_InvalidViewFocusOperatorMutator
            case 97: {
                /**
                * Inserted by Kadabra
                */
                te = ((android.widget.TextView) (act.findViewById(resId)));
                te.requestFocus();
                break;
            }
            // Util_1_ViewComponentNotVisibleOperatorMutator
            case 1097: {
                /**
                * Inserted by Kadabra
                */
                te = ((android.widget.TextView) (act.findViewById(resId)));
                te.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            te = ((android.widget.TextView) (act.findViewById(resId)));
            break;
        }
    }
    if (te != null) {
        return te.getText().toString();
    } else {
        return "";
    }
}


public static void setEditText(android.app.Activity act, int resId, java.lang.String str) {
    android.widget.TextView te;
    switch(MUID_STATIC) {
        // Util_2_InvalidViewFocusOperatorMutator
        case 2097: {
            /**
            * Inserted by Kadabra
            */
            te = ((android.widget.TextView) (act.findViewById(resId)));
            te.requestFocus();
            break;
        }
        // Util_3_ViewComponentNotVisibleOperatorMutator
        case 3097: {
            /**
            * Inserted by Kadabra
            */
            te = ((android.widget.TextView) (act.findViewById(resId)));
            te.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        te = ((android.widget.TextView) (act.findViewById(resId)));
        break;
    }
}
if (te != null) {
    te.setText(str);
}
}


private static final int MAX_BUF_SIZE = 1024;

public static void copyStream(java.io.InputStream in, java.io.OutputStream out) throws java.io.IOException {
byte[] buf;
buf = new byte[com.keepassdroid.utils.Util.MAX_BUF_SIZE];
int read;
while ((read = in.read(buf)) != (-1)) {
    out.write(buf, 0, read);
} 
}


public static int copyStream(java.io.InputStream in, java.io.OutputStream out, int maxBytes) throws java.io.IOException {
if (maxBytes <= 0)
    return 0;

int bufSize;
bufSize = java.lang.Math.min(maxBytes, com.keepassdroid.utils.Util.MAX_BUF_SIZE);
byte[] buf;
buf = new byte[bufSize];
int origMax;
origMax = maxBytes;
int read;
do {
    assert maxBytes > 0;
    if (maxBytes >= buf.length) {
        read = in.read(buf);
    } else {
        read = in.read(buf, 0, maxBytes);
    }
    if (read == (-1)) {
        break;
    }
    out.write(buf, 0, read);
    maxBytes -= read;
} while (maxBytes > 0 );
switch(MUID_STATIC) {
    // Util_4_BinaryMutator
    case 4097: {
        // return total amonut read
        return origMax + maxBytes;
    }
    default: {
    // return total amonut read
    return origMax - maxBytes;
    }
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
