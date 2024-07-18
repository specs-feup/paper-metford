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
package com.amaze.filemanager.ui.fragments;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.database.CloudContract;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.net.Uri;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.amaze.filemanager.R;
import com.amaze.filemanager.ui.dialogs.SftpConnectDialog;
import androidx.annotation.NonNull;
import android.app.Dialog;
import com.amaze.filemanager.BuildConfig;
import android.content.pm.PackageManager;
import com.amaze.filemanager.ui.theme.AppTheme;
import android.widget.LinearLayout;
import com.amaze.filemanager.databinding.FragmentSheetCloudBinding;
import android.os.Bundle;
import android.content.Intent;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.view.View;
import com.amaze.filemanager.ui.dialogs.SmbSearchDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import android.content.ActivityNotFoundException;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 18/2/17.
 *
 * <p>Class represents implementation of a new cloud connection sheet dialog
 */
public class CloudSheetFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment implements android.view.View.OnClickListener {
    static final int MUID_STATIC = getMUID();
    private android.view.View rootView;

    private android.widget.LinearLayout mSmbLayout;

    private android.widget.LinearLayout mScpLayout;

    private android.widget.LinearLayout mDropboxLayout;

    private android.widget.LinearLayout mBoxLayout;

    private android.widget.LinearLayout mGoogleDriveLayout;

    private android.widget.LinearLayout mOnedriveLayout;

    private android.widget.LinearLayout mGetCloudLayout;

    public static final java.lang.String TAG_FRAGMENT = "cloud_fragment";

    @java.lang.Override
    public void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // CloudSheetFragment_0_LengthyGUICreationOperatorMutator
            case 112: {
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
}


@androidx.annotation.NonNull
@java.lang.Override
public android.app.Dialog onCreateDialog(@androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    com.google.android.material.bottomsheet.BottomSheetDialog dialog;
    dialog = ((com.google.android.material.bottomsheet.BottomSheetDialog) (super.onCreateDialog(savedInstanceState)));
    dialog.setOnShowListener((android.content.DialogInterface dialog1) -> {
        com.google.android.material.bottomsheet.BottomSheetDialog d;
        d = ((com.google.android.material.bottomsheet.BottomSheetDialog) (dialog1));
        android.widget.FrameLayout bottomSheet;
        switch(MUID_STATIC) {
            // CloudSheetFragment_1_InvalidViewFocusOperatorMutator
            case 1112: {
                /**
                * Inserted by Kadabra
                */
                bottomSheet = ((android.widget.FrameLayout) (d.findViewById(com.google.android.material.R.id.design_bottom_sheet)));
                bottomSheet.requestFocus();
                break;
            }
            // CloudSheetFragment_2_ViewComponentNotVisibleOperatorMutator
            case 2112: {
                /**
                * Inserted by Kadabra
                */
                bottomSheet = ((android.widget.FrameLayout) (d.findViewById(com.google.android.material.R.id.design_bottom_sheet)));
                bottomSheet.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            bottomSheet = ((android.widget.FrameLayout) (d.findViewById(com.google.android.material.R.id.design_bottom_sheet)));
            break;
        }
    }
    com.google.android.material.bottomsheet.BottomSheetBehavior.from(bottomSheet).setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
});
return dialog;
}


@java.lang.Override
public void setupDialog(android.app.Dialog dialog, int style) {
super.setupDialog(dialog, style);
rootView = com.amaze.filemanager.databinding.FragmentSheetCloudBinding.inflate(android.view.LayoutInflater.from(requireActivity())).getRoot();
com.amaze.filemanager.ui.activities.MainActivity activity;
activity = ((com.amaze.filemanager.ui.activities.MainActivity) (getActivity()));
if (activity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK)) {
    rootView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.holo_dark_background));
} else if (activity.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
    rootView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), android.R.color.black));
} else {
    rootView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), android.R.color.white));
}
switch(MUID_STATIC) {
    // CloudSheetFragment_3_InvalidViewFocusOperatorMutator
    case 3112: {
        /**
        * Inserted by Kadabra
        */
        mSmbLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_smb);
        mSmbLayout.requestFocus();
        break;
    }
    // CloudSheetFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4112: {
        /**
        * Inserted by Kadabra
        */
        mSmbLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_smb);
        mSmbLayout.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mSmbLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_smb);
    break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_5_InvalidViewFocusOperatorMutator
case 5112: {
    /**
    * Inserted by Kadabra
    */
    mScpLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_scp);
    mScpLayout.requestFocus();
    break;
}
// CloudSheetFragment_6_ViewComponentNotVisibleOperatorMutator
case 6112: {
    /**
    * Inserted by Kadabra
    */
    mScpLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_scp);
    mScpLayout.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mScpLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_scp);
break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_7_InvalidViewFocusOperatorMutator
case 7112: {
/**
* Inserted by Kadabra
*/
mBoxLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_box);
mBoxLayout.requestFocus();
break;
}
// CloudSheetFragment_8_ViewComponentNotVisibleOperatorMutator
case 8112: {
/**
* Inserted by Kadabra
*/
mBoxLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_box);
mBoxLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mBoxLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_box);
break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_9_InvalidViewFocusOperatorMutator
case 9112: {
/**
* Inserted by Kadabra
*/
mDropboxLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_dropbox);
mDropboxLayout.requestFocus();
break;
}
// CloudSheetFragment_10_ViewComponentNotVisibleOperatorMutator
case 10112: {
/**
* Inserted by Kadabra
*/
mDropboxLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_dropbox);
mDropboxLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDropboxLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_dropbox);
break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_11_InvalidViewFocusOperatorMutator
case 11112: {
/**
* Inserted by Kadabra
*/
mGoogleDriveLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_google_drive);
mGoogleDriveLayout.requestFocus();
break;
}
// CloudSheetFragment_12_ViewComponentNotVisibleOperatorMutator
case 12112: {
/**
* Inserted by Kadabra
*/
mGoogleDriveLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_google_drive);
mGoogleDriveLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mGoogleDriveLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_google_drive);
break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_13_InvalidViewFocusOperatorMutator
case 13112: {
/**
* Inserted by Kadabra
*/
mOnedriveLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_onedrive);
mOnedriveLayout.requestFocus();
break;
}
// CloudSheetFragment_14_ViewComponentNotVisibleOperatorMutator
case 14112: {
/**
* Inserted by Kadabra
*/
mOnedriveLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_onedrive);
mOnedriveLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mOnedriveLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_onedrive);
break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_15_InvalidViewFocusOperatorMutator
case 15112: {
/**
* Inserted by Kadabra
*/
mGetCloudLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_get_cloud);
mGetCloudLayout.requestFocus();
break;
}
// CloudSheetFragment_16_ViewComponentNotVisibleOperatorMutator
case 16112: {
/**
* Inserted by Kadabra
*/
mGetCloudLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_get_cloud);
mGetCloudLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mGetCloudLayout = rootView.findViewById(com.amaze.filemanager.R.id.linear_layout_get_cloud);
break;
}
}
if (com.amaze.filemanager.ui.fragments.CloudSheetFragment.isCloudProviderAvailable(getContext())) {
mBoxLayout.setVisibility(android.view.View.VISIBLE);
mDropboxLayout.setVisibility(android.view.View.VISIBLE);
mGoogleDriveLayout.setVisibility(android.view.View.VISIBLE);
mOnedriveLayout.setVisibility(android.view.View.VISIBLE);
mGetCloudLayout.setVisibility(android.view.View.GONE);
}
if (com.amaze.filemanager.BuildConfig.IS_VERSION_FDROID) {
mBoxLayout.setVisibility(android.view.View.GONE);
mDropboxLayout.setVisibility(android.view.View.GONE);
mGoogleDriveLayout.setVisibility(android.view.View.GONE);
mOnedriveLayout.setVisibility(android.view.View.GONE);
mGetCloudLayout.setVisibility(android.view.View.GONE);
}
mSmbLayout.setOnClickListener(this);
mScpLayout.setOnClickListener(this);
mBoxLayout.setOnClickListener(this);
mDropboxLayout.setOnClickListener(this);
mGoogleDriveLayout.setOnClickListener(this);
mOnedriveLayout.setOnClickListener(this);
mGetCloudLayout.setOnClickListener(this);
dialog.setContentView(rootView);
}


/**
 * Determines whether cloud provider is installed or not
 */
public static final boolean isCloudProviderAvailable(android.content.Context context) {
android.content.pm.PackageManager pm;
pm = context.getPackageManager();
try {
pm.getPackageInfo(com.amaze.filemanager.database.CloudContract.APP_PACKAGE_NAME, android.content.pm.PackageManager.GET_ACTIVITIES);
return true;
} catch (android.content.pm.PackageManager.NameNotFoundException e) {
return false;
}
}


@java.lang.Override
public void onClick(android.view.View v) {
switch (v.getId()) {
case com.amaze.filemanager.R.id.linear_layout_smb :
dismiss();
com.amaze.filemanager.ui.dialogs.SmbSearchDialog smbDialog;
smbDialog = new com.amaze.filemanager.ui.dialogs.SmbSearchDialog();
smbDialog.show(getActivity().getSupportFragmentManager(), "tab");
return;
case com.amaze.filemanager.R.id.linear_layout_scp :
dismiss();
com.amaze.filemanager.ui.dialogs.SftpConnectDialog sftpConnectDialog;
sftpConnectDialog = new com.amaze.filemanager.ui.dialogs.SftpConnectDialog();
android.os.Bundle args;
args = new android.os.Bundle();
args.putBoolean("edit", false);
sftpConnectDialog.setArguments(args);
sftpConnectDialog.show(getFragmentManager(), "tab");
return;
case com.amaze.filemanager.R.id.linear_layout_box :
((com.amaze.filemanager.ui.activities.MainActivity) (getActivity())).addConnection(com.amaze.filemanager.fileoperations.filesystem.OpenMode.BOX);
break;
case com.amaze.filemanager.R.id.linear_layout_dropbox :
((com.amaze.filemanager.ui.activities.MainActivity) (getActivity())).addConnection(com.amaze.filemanager.fileoperations.filesystem.OpenMode.DROPBOX);
break;
case com.amaze.filemanager.R.id.linear_layout_google_drive :
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showSignInWithGoogleDialog(((com.amaze.filemanager.ui.activities.MainActivity) (getActivity())));
break;
case com.amaze.filemanager.R.id.linear_layout_onedrive :
((com.amaze.filemanager.ui.activities.MainActivity) (getActivity())).addConnection(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ONEDRIVE);
break;
case com.amaze.filemanager.R.id.linear_layout_get_cloud :
android.content.Intent cloudPluginIntent;
switch(MUID_STATIC) {
// CloudSheetFragment_17_NullIntentOperatorMutator
case 17112: {
cloudPluginIntent = null;
break;
}
// CloudSheetFragment_18_InvalidKeyIntentOperatorMutator
case 18112: {
cloudPluginIntent = new android.content.Intent((String) null);
break;
}
// CloudSheetFragment_19_RandomActionIntentDefinitionOperatorMutator
case 19112: {
cloudPluginIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
cloudPluginIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_20_RandomActionIntentDefinitionOperatorMutator
case 20112: {
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
cloudPluginIntent.setData(android.net.Uri.parse(getString(com.amaze.filemanager.R.string.cloud_plugin_google_play_uri)));
break;
}
}
switch(MUID_STATIC) {
// CloudSheetFragment_21_RandomActionIntentDefinitionOperatorMutator
case 21112: {
try {
startActivity(cloudPluginIntent);
} catch (android.content.ActivityNotFoundException ifGooglePlayIsNotInstalled) {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);;
startActivity(cloudPluginIntent);
}
break;
}
default: {
try {
startActivity(cloudPluginIntent);
} catch (android.content.ActivityNotFoundException ifGooglePlayIsNotInstalled) {
cloudPluginIntent.setData(android.net.Uri.parse(getString(com.amaze.filemanager.R.string.cloud_plugin_google_play_web_uri)));
startActivity(cloudPluginIntent);
}
break;
}
}
break;
}
// dismiss this sheet dialog
dismiss();
}


public interface CloudConnectionCallbacks {
void addConnection(com.amaze.filemanager.fileoperations.filesystem.OpenMode service);


void deleteConnection(com.amaze.filemanager.fileoperations.filesystem.OpenMode service);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
