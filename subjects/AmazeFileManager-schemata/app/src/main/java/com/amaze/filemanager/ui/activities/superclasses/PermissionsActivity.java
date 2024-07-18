/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
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
package com.amaze.filemanager.ui.activities.superclasses;
import androidx.core.app.ActivityCompat;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import android.util.Log;
import com.amaze.filemanager.application.AppConfig;
import android.content.Intent;
import static android.os.Build.VERSION_CODES.TIRAMISU;
import android.net.Uri;
import static android.os.Build.VERSION.SDK_INT;
import com.amaze.filemanager.R;
import android.Manifest;
import com.amaze.filemanager.utils.Utils;
import android.content.ActivityNotFoundException;
import androidx.annotation.NonNull;
import com.afollestad.materialdialogs.DialogAction;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import android.os.Environment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PermissionsActivity extends com.amaze.filemanager.ui.activities.superclasses.ThemedActivity implements androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.class.getSimpleName();

    public static final int PERMISSION_LENGTH = 4;

    public static final int STORAGE_PERMISSION = 0;

    public static final int INSTALL_APK_PERMISSION = 1;

    public static final int ALL_FILES_PERMISSION = 2;

    public static final int NOTIFICATION_PERMISSION = 3;

    private final com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.OnPermissionGranted[] permissionCallbacks = new com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.OnPermissionGranted[com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.PERMISSION_LENGTH];

    @java.lang.Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull
    java.lang.String[] permissions, @androidx.annotation.NonNull
    int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.STORAGE_PERMISSION) {
            if (isGranted(grantResults)) {
                com.amaze.filemanager.utils.Utils.enableScreenRotation(this);
                permissionCallbacks[com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.STORAGE_PERMISSION].onPermissionGranted();
                permissionCallbacks[com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.STORAGE_PERMISSION] = null;
            } else {
                android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.grantfailed, android.widget.Toast.LENGTH_SHORT).show();
                requestStoragePermission(permissionCallbacks[com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.STORAGE_PERMISSION], false);
            }
        } else if ((requestCode == com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.NOTIFICATION_PERMISSION) && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)) {
            if (isGranted(grantResults)) {
                com.amaze.filemanager.utils.Utils.enableScreenRotation(this);
            } else {
                android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.grantfailed, android.widget.Toast.LENGTH_SHORT).show();
                requestNotificationPermission(false);
            }
        } else if (requestCode == com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.INSTALL_APK_PERMISSION) {
            if (isGranted(grantResults)) {
                permissionCallbacks[com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.INSTALL_APK_PERMISSION].onPermissionGranted();
                permissionCallbacks[com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.INSTALL_APK_PERMISSION] = null;
            }
        }
    }


    public boolean checkStoragePermission() {
        // Verify that all required contact permissions have been granted.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            return ((androidx.core.app.ActivityCompat.checkSelfPermission(this, android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION) == android.content.pm.PackageManager.PERMISSION_GRANTED) || (androidx.core.app.ActivityCompat.checkSelfPermission(this, android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION) == android.content.pm.PackageManager.PERMISSION_GRANTED)) || android.os.Environment.isExternalStorageManager();
        } else {
            return androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED;
        }
    }


    @androidx.annotation.RequiresApi(android.os.Build.VERSION_CODES.TIRAMISU)
    public boolean checkNotificationPermission() {
        return androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED;
    }


    @androidx.annotation.RequiresApi(android.os.Build.VERSION_CODES.TIRAMISU)
    public void requestNotificationPermission(boolean isInitialStart) {
        com.amaze.filemanager.utils.Utils.disableScreenRotation(this);
        final com.afollestad.materialdialogs.MaterialDialog materialDialog;
        materialDialog = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showBasicDialog(this, com.amaze.filemanager.R.string.grant_notification_permission, com.amaze.filemanager.R.string.grantper, com.amaze.filemanager.R.string.grant, com.amaze.filemanager.R.string.cancel);
        switch(MUID_STATIC) {
            // PermissionsActivity_0_BuggyGUIListenerOperatorMutator
            case 104: {
                materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener(null);
                break;
            }
            default: {
            materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener((android.view.View v) -> finish());
            break;
        }
    }
    materialDialog.setCancelable(false);
    requestPermission(android.Manifest.permission.POST_NOTIFICATIONS, com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.NOTIFICATION_PERMISSION, materialDialog, () -> {
        // do nothing
    }, isInitialStart);
}


public void requestStoragePermission(@androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.OnPermissionGranted onPermissionGranted, boolean isInitialStart) {
    com.amaze.filemanager.utils.Utils.disableScreenRotation(this);
    final com.afollestad.materialdialogs.MaterialDialog materialDialog;
    materialDialog = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showBasicDialog(this, com.amaze.filemanager.R.string.grant_storage_permission, com.amaze.filemanager.R.string.grantper, com.amaze.filemanager.R.string.grant, com.amaze.filemanager.R.string.cancel);
    switch(MUID_STATIC) {
        // PermissionsActivity_1_BuggyGUIListenerOperatorMutator
        case 1104: {
            materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener(null);
            break;
        }
        default: {
        materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener((android.view.View v) -> finish());
        break;
    }
}
materialDialog.setCancelable(false);
requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.STORAGE_PERMISSION, materialDialog, onPermissionGranted, isInitialStart);
}


@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
public void requestInstallApkPermission(@androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.OnPermissionGranted onPermissionGranted, boolean isInitialStart) {
final com.afollestad.materialdialogs.MaterialDialog materialDialog;
materialDialog = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showBasicDialog(this, com.amaze.filemanager.R.string.grant_apkinstall_permission, com.amaze.filemanager.R.string.grantper, com.amaze.filemanager.R.string.grant, com.amaze.filemanager.R.string.cancel);
switch(MUID_STATIC) {
    // PermissionsActivity_2_BuggyGUIListenerOperatorMutator
    case 2104: {
        materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener(null);
        break;
    }
    default: {
    materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener((android.view.View v) -> materialDialog.dismiss());
    break;
}
}
materialDialog.setCancelable(false);
requestPermission(android.Manifest.permission.REQUEST_INSTALL_PACKAGES, com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.INSTALL_APK_PERMISSION, materialDialog, onPermissionGranted, isInitialStart);
}


/**
 * Requests permission, overrides {@param rationale}'s POSITIVE button dialog action.
 *
 * @param permission
 * 		The permission to ask for
 * @param code
 * 		{@link #STORAGE_PERMISSION} or {@link #INSTALL_APK_PERMISSION}
 * @param rationale
 * 		MaterialLayout to provide an additional rationale to the user if the
 * 		permission was not granted and the user would benefit from additional context for the use
 * 		of the permission. For example, if the request has been denied previously.
 * @param isInitialStart
 * 		is the permission being requested for the first time in the application
 * 		lifecycle
 */
private void requestPermission(final java.lang.String permission, final int code, @androidx.annotation.NonNull
final com.afollestad.materialdialogs.MaterialDialog rationale, @androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.OnPermissionGranted onPermissionGranted, boolean isInitialStart) {
permissionCallbacks[code] = onPermissionGranted;
if (androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
switch(MUID_STATIC) {
    // PermissionsActivity_3_BuggyGUIListenerOperatorMutator
    case 3104: {
        rationale.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener(null);
        break;
    }
    default: {
    rationale.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener((android.view.View v) -> {
        androidx.core.app.ActivityCompat.requestPermissions(this, new java.lang.String[]{ permission }, code);
        rationale.dismiss();
    });
    break;
}
}
rationale.show();
} else if (isInitialStart) {
androidx.core.app.ActivityCompat.requestPermissions(this, new java.lang.String[]{ permission }, code);
} else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
switch(MUID_STATIC) {
// PermissionsActivity_4_InvalidIDFindViewOperatorMutator
case 4104: {
    com.google.android.material.snackbar.Snackbar.make(findViewById(732221), com.amaze.filemanager.R.string.grantfailed, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(com.amaze.filemanager.R.string.grant, (android.view.View v) -> requestAllFilesAccessPermission(onPermissionGranted)).show();
    break;
}
default: {
switch(MUID_STATIC) {
    // PermissionsActivity_5_BuggyGUIListenerOperatorMutator
    case 5104: {
        com.google.android.material.snackbar.Snackbar.make(findViewById(com.amaze.filemanager.R.id.content_frame), com.amaze.filemanager.R.string.grantfailed, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(com.amaze.filemanager.R.string.grant, null).show();
        break;
    }
    default: {
    com.google.android.material.snackbar.Snackbar.make(findViewById(com.amaze.filemanager.R.id.content_frame), com.amaze.filemanager.R.string.grantfailed, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(com.amaze.filemanager.R.string.grant, (android.view.View v) -> requestAllFilesAccessPermission(onPermissionGranted)).show();
    break;
}
}
break;
}
}
} else {
switch(MUID_STATIC) {
// PermissionsActivity_6_InvalidIDFindViewOperatorMutator
case 6104: {
com.google.android.material.snackbar.Snackbar.make(findViewById(732221), com.amaze.filemanager.R.string.grantfailed, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(com.amaze.filemanager.R.string.grant, (android.view.View v) -> startActivity(new android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, android.net.Uri.parse(java.lang.String.format("package:%s", getPackageName()))))).show();
break;
}
default: {
switch(MUID_STATIC) {
// PermissionsActivity_7_BuggyGUIListenerOperatorMutator
case 7104: {
com.google.android.material.snackbar.Snackbar.make(findViewById(com.amaze.filemanager.R.id.content_frame), com.amaze.filemanager.R.string.grantfailed, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(com.amaze.filemanager.R.string.grant, null).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(findViewById(com.amaze.filemanager.R.id.content_frame), com.amaze.filemanager.R.string.grantfailed, com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE).setAction(com.amaze.filemanager.R.string.grant, (android.view.View v) -> startActivity(new android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, android.net.Uri.parse(java.lang.String.format("package:%s", getPackageName()))))).show();
break;
}
}
break;
}
}
}
}


/**
 * Request all files access on android 11+
 *
 * @param onPermissionGranted
 * 		permission granted callback
 */
public void requestAllFilesAccess(@androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.OnPermissionGranted onPermissionGranted) {
if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) && (!android.os.Environment.isExternalStorageManager())) {
final com.afollestad.materialdialogs.MaterialDialog materialDialog;
materialDialog = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showBasicDialog(this, com.amaze.filemanager.R.string.grant_all_files_permission, com.amaze.filemanager.R.string.grantper, com.amaze.filemanager.R.string.grant, com.amaze.filemanager.R.string.cancel);
switch(MUID_STATIC) {
// PermissionsActivity_8_BuggyGUIListenerOperatorMutator
case 8104: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener(null);
break;
}
default: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener((android.view.View v) -> finish());
break;
}
}
switch(MUID_STATIC) {
// PermissionsActivity_9_BuggyGUIListenerOperatorMutator
case 9104: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener(null);
break;
}
default: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener((android.view.View v) -> {
requestAllFilesAccessPermission(onPermissionGranted);
materialDialog.dismiss();
});
break;
}
}
materialDialog.setCancelable(false);
materialDialog.show();
}
}


@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.R)
private void requestAllFilesAccessPermission(@androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.OnPermissionGranted onPermissionGranted) {
com.amaze.filemanager.utils.Utils.disableScreenRotation(this);
permissionCallbacks[com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.ALL_FILES_PERMISSION] = onPermissionGranted;
switch(MUID_STATIC) {
// PermissionsActivity_10_RandomActionIntentDefinitionOperatorMutator
case 10104: {
try {
android.content.Intent intent;
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
startActivity(intent);
} catch (android.content.ActivityNotFoundException anf) {
// fallback
try {
android.content.Intent intent;
intent = new android.content.Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION).setData(android.net.Uri.parse("package:$packageName"));
startActivity(intent);
} catch (java.lang.Exception e) {
com.amaze.filemanager.application.AppConfig.toast(this, getString(com.amaze.filemanager.R.string.grantfailed));
}
} catch (java.lang.Exception e) {
android.util.Log.e(com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.TAG, "Failed to initial activity to grant all files access", e);
com.amaze.filemanager.application.AppConfig.toast(this, getString(com.amaze.filemanager.R.string.grantfailed));
}
break;
}
default: {
try {
android.content.Intent intent;
intent = new android.content.Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).setData(android.net.Uri.parse("package:" + getPackageName()));
startActivity(intent);
} catch (android.content.ActivityNotFoundException anf) {
switch(MUID_STATIC) {
// PermissionsActivity_11_RandomActionIntentDefinitionOperatorMutator
case 11104: {
// fallback
try {
android.content.Intent intent;
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
startActivity(intent);
} catch (java.lang.Exception e) {
com.amaze.filemanager.application.AppConfig.toast(this, getString(com.amaze.filemanager.R.string.grantfailed));
}
break;
}
default: {
// fallback
try {
android.content.Intent intent;
intent = new android.content.Intent(android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION).setData(android.net.Uri.parse("package:$packageName"));
startActivity(intent);
} catch (java.lang.Exception e) {
com.amaze.filemanager.application.AppConfig.toast(this, getString(com.amaze.filemanager.R.string.grantfailed));
}
break;
}
}
} catch (java.lang.Exception e) {
android.util.Log.e(com.amaze.filemanager.ui.activities.superclasses.PermissionsActivity.TAG, "Failed to initial activity to grant all files access", e);
com.amaze.filemanager.application.AppConfig.toast(this, getString(com.amaze.filemanager.R.string.grantfailed));
}
break;
}
}
}


private boolean isGranted(int[] grantResults) {
return (grantResults.length == 1) && (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED);
}


public interface OnPermissionGranted {
void onPermissionGranted();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
