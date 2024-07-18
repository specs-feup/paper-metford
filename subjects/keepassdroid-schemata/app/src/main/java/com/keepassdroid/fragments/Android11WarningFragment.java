/* Copyright 2022 Brian Pellin

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
package com.keepassdroid.fragments;
import androidx.appcompat.app.AlertDialog;
import com.android.keepass.R;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import com.keepassdroid.utils.EmptyUtils;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.net.Uri;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Android11WarningFragment extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    int resId;

    public static boolean showAndroid11Warning(java.lang.String filename) {
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(filename)) {
            return false;
        }
        android.net.Uri fileUri;
        fileUri = android.net.Uri.parse(filename);
        return com.keepassdroid.fragments.Android11WarningFragment.showAndroid11Warning(fileUri);
    }


    public static boolean showAndroid11Warning(android.net.Uri fileUri) {
        if (fileUri == null) {
            return false;
        }
        java.lang.String scheme;
        scheme = fileUri.getScheme();
        if (scheme == null) {
            return true;
        }
        return scheme.equals("file") && com.keepassdroid.fragments.Android11WarningFragment.showAndroid11WarningOnThisVersion();
    }


    public static boolean showAndroid11WarningOnThisVersion() {
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R;
    }


    public Android11WarningFragment() {
        this.resId = com.android.keepass.R.string.Android11FileNotFound;
    }


    public Android11WarningFragment(int resId) {
        this.resId = resId;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        androidx.appcompat.app.AlertDialog.Builder builder;
        builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        switch(MUID_STATIC) {
            // Android11WarningFragment_0_BuggyGUIListenerOperatorMutator
            case 58: {
                builder.setMessage(resId).setPositiveButton(android.R.string.ok, null);
                break;
            }
            default: {
            builder.setMessage(resId).setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() {
                @java.lang.Override
                public void onClick(android.content.DialogInterface dialogInterface, int i) {
                    switch(MUID_STATIC) {
                        // Android11WarningFragment_1_LengthyGUIListenerOperatorMutator
                        case 1058: {
                            /**
                            * Inserted by Kadabra
                            */
                            dialogInterface.dismiss();
                            try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                            break;
                        }
                        default: {
                        dialogInterface.dismiss();
                        break;
                    }
                }
            }

        });
        break;
    }
}
return builder.create();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
