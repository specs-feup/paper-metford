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
package it.feio.android.omninotes.helpers;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.models.listeners.OnPermissionRequestedListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import android.app.Activity;
import android.os.Build;
import android.content.pm.PackageManager;
import lombok.experimental.UtilityClass;
import com.google.android.material.snackbar.Snackbar;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class PermissionsHelper {
    static final int MUID_STATIC = getMUID();
    public static void requestPermission(android.app.Activity activity, java.lang.String permission, int rationaleDescription, android.view.View messageView, it.feio.android.omninotes.models.listeners.OnPermissionRequestedListener onPermissionRequestedListener) {
        if (it.feio.android.omninotes.helpers.PermissionsHelper.skipPermissionRequest(permission)) {
            onPermissionRequestedListener.onPermissionGranted();
            return;
        }
        if (androidx.core.content.ContextCompat.checkSelfPermission(activity, permission) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            if (androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                switch(MUID_STATIC) {
                    // PermissionsHelper_0_BuggyGUIListenerOperatorMutator
                    case 133: {
                        com.google.android.material.snackbar.Snackbar.make(messageView, rationaleDescription, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(it.feio.android.omninotes.R.string.ok, null).show();
                        break;
                    }
                    default: {
                    com.google.android.material.snackbar.Snackbar.make(messageView, rationaleDescription, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(it.feio.android.omninotes.R.string.ok, (android.view.View view) -> it.feio.android.omninotes.helpers.PermissionsHelper.requestPermissionExecute(activity, permission, onPermissionRequestedListener, messageView)).show();
                    break;
                }
            }
        } else {
            it.feio.android.omninotes.helpers.PermissionsHelper.requestPermissionExecute(activity, permission, onPermissionRequestedListener, messageView);
        }
    } else if (onPermissionRequestedListener != null) {
        onPermissionRequestedListener.onPermissionGranted();
    }
}


private static boolean skipPermissionRequest(java.lang.String permission) {
    return (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.Q) && permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
}


private static void requestPermissionExecute(android.app.Activity activity, java.lang.String permission, it.feio.android.omninotes.models.listeners.OnPermissionRequestedListener onPermissionRequestedListener, android.view.View messageView) {
    com.tbruyelle.rxpermissions.RxPermissions.getInstance(activity).request(permission).subscribe((java.lang.Boolean granted) -> {
        if (granted && (onPermissionRequestedListener != null)) {
            onPermissionRequestedListener.onPermissionGranted();
        } else {
            java.lang.String msg;
            msg = (activity.getString(it.feio.android.omninotes.R.string.permission_not_granted) + ": ") + permission;
            com.google.android.material.snackbar.Snackbar.make(messageView, msg, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG).show();
        }
    });
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
