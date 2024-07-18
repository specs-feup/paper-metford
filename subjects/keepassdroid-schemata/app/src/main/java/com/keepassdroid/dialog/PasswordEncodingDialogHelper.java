/* Copyright 2015 Brian Pellin.

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
package com.keepassdroid.dialog;
import android.app.AlertDialog;
import com.android.keepass.R;
import android.content.DialogInterface;
import android.app.Dialog;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordEncodingDialogHelper {
    static final int MUID_STATIC = getMUID();
    private android.app.AlertDialog dialog;

    public void show(android.content.Context ctx, android.content.DialogInterface.OnClickListener onclick) {
        show(ctx, onclick, false);
    }


    public void show(android.content.Context ctx, android.content.DialogInterface.OnClickListener onclick, boolean showCancel) {
        android.app.AlertDialog.Builder builder;
        builder = new android.app.AlertDialog.Builder(ctx);
        builder.setMessage(com.android.keepass.R.string.warning_password_encoding).setTitle(com.android.keepass.R.string.warning);
        builder.setPositiveButton(android.R.string.ok, onclick);
        if (showCancel) {
            switch(MUID_STATIC) {
                // PasswordEncodingDialogHelper_0_BuggyGUIListenerOperatorMutator
                case 55: {
                    builder.setNegativeButton(android.R.string.cancel, null);
                    break;
                }
                default: {
                builder.setNegativeButton(android.R.string.cancel, new android.content.DialogInterface.OnClickListener() {
                    @java.lang.Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        switch(MUID_STATIC) {
                            // PasswordEncodingDialogHelper_1_LengthyGUIListenerOperatorMutator
                            case 1055: {
                                /**
                                * Inserted by Kadabra
                                */
                                dialog.cancel();
                                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                                break;
                            }
                            default: {
                            dialog.cancel();
                            break;
                        }
                    }
                }

            });
            break;
        }
    }
}
dialog = builder.create();
dialog.show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
