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
package com.amaze.filemanager.utils;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.util.ArrayList;
import static com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.CAN_CREATE_FILES;
import static com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.COMPRESS;
import com.amaze.filemanager.asynchronous.asynctasks.DeleteTask;
import com.amaze.filemanager.database.CryptHandler;
import com.amaze.filemanager.asynchronous.services.ZipService;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Build;
import androidx.annotation.StringRes;
import static com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.EXTRACT;
import com.amaze.filemanager.filesystem.Operations;
import com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil;
import static com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.NEW_FILE;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import androidx.appcompat.widget.AppCompatTextView;
import android.content.BroadcastReceiver;
import androidx.documentfile.provider.DocumentFile;
import static com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD;
import androidx.appcompat.widget.AppCompatImageView;
import com.amaze.filemanager.filesystem.HybridFile;
import android.view.LayoutInflater;
import static com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.NEW_FOLDER;
import com.amaze.filemanager.filesystem.compressed.CompressedHelper;
import com.amaze.filemanager.ui.views.WarnableTextInputValidator;
import com.amaze.filemanager.ui.ExtensionsKt;
import com.amaze.filemanager.filesystem.SafRootHolder;
import java.io.File;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor;
import com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils;
import com.amaze.filemanager.ui.activities.MainActivity;
import org.slf4j.Logger;
import com.amaze.filemanager.ui.fragments.MainFragment;
import com.amaze.filemanager.R;
import static com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.DELETE;
import com.amaze.filemanager.filesystem.files.CryptUtil;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.ExternalSdCardOperation;
import com.amaze.filemanager.filesystem.FileProperties;
import static com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.RENAME;
import android.content.SharedPreferences;
import com.amaze.filemanager.application.AppConfig;
import com.amaze.filemanager.database.CloudHandler;
import com.amaze.filemanager.database.models.explorer.EncryptedEntry;
import android.content.Intent;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.view.View;
import static com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.DOESNT_EXIST;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.annotation.SuppressLint;
import com.amaze.filemanager.utils.smb.SmbUtil;
import com.leinardi.android.speeddial.SpeedDialView;
import com.amaze.filemanager.fileoperations.filesystem.FolderState;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MainActivityHelper {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.utils.MainActivityHelper.class);

    private com.amaze.filemanager.ui.activities.MainActivity mainActivity;

    private com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

    private int accentColor;

    private com.leinardi.android.speeddial.SpeedDialView.OnActionSelectedListener fabActionListener;

    public MainActivityHelper(com.amaze.filemanager.ui.activities.MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        accentColor = mainActivity.getAccent();
    }


    public void showFailedOperationDialog(java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> failedOps, android.content.Context context) {
        com.afollestad.materialdialogs.MaterialDialog.Builder mat;
        mat = new com.afollestad.materialdialogs.MaterialDialog.Builder(context);
        mat.title(context.getString(com.amaze.filemanager.R.string.operation_unsuccesful));
        mat.theme(mainActivity.getAppTheme().getMaterialDialogTheme());
        mat.positiveColor(accentColor);
        mat.positiveText(com.amaze.filemanager.R.string.cancel);
        java.lang.String content;
        content = context.getString(com.amaze.filemanager.R.string.operation_fail_following);
        int k;
        k = 1;
        for (com.amaze.filemanager.filesystem.HybridFileParcelable s : failedOps) {
            content = (((content + "\n") + k) + ". ") + s.getName(context);
            k++;
        }
        mat.content(content);
        mat.build().show();
    }


    public final android.content.BroadcastReceiver mNotificationReceiver = new android.content.BroadcastReceiver() {
        @java.lang.Override
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(android.content.Intent.ACTION_MEDIA_MOUNTED)) {
                    android.widget.Toast.makeText(mainActivity, "Media Mounted", android.widget.Toast.LENGTH_SHORT).show();
                    java.lang.String a;
                    a = intent.getData().getPath();
                    if ((((a != null) && (a.trim().length() != 0)) && new java.io.File(a).exists()) && new java.io.File(a).canExecute()) {
                        dataUtils.getStorages().add(a);
                        mainActivity.getDrawer().refreshDrawer();
                    } else {
                        mainActivity.getDrawer().refreshDrawer();
                    }
                } else if (intent.getAction().equals(android.content.Intent.ACTION_MEDIA_UNMOUNTED)) {
                    mainActivity.getDrawer().refreshDrawer();
                }
            }
        }

    };

    /**
     * Prompt a dialog to user to input directory name
     *
     * @param path
     * 		current path at which directory to create
     * @param ma
     * 		{@link MainFragment} current fragment
     */
    public void mkdir(final com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, final java.lang.String path, final com.amaze.filemanager.ui.fragments.MainFragment ma) {
        mk(com.amaze.filemanager.R.string.newfolder, "", (com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
            androidx.appcompat.widget.AppCompatEditText textfield;
            switch(MUID_STATIC) {
                // MainActivityHelper_0_InvalidViewFocusOperatorMutator
                case 30: {
                    /**
                    * Inserted by Kadabra
                    */
                    textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
                    textfield.requestFocus();
                    break;
                }
                // MainActivityHelper_1_ViewComponentNotVisibleOperatorMutator
                case 1030: {
                    /**
                    * Inserted by Kadabra
                    */
                    textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
                    textfield.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
                break;
            }
        }
        java.lang.String parentPath;
        parentPath = path;
        if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE.equals(openMode) && (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R)) {
            parentPath = com.amaze.filemanager.filesystem.FileProperties.remapPathForApi30OrAbove(path, false);
        }
        mkDir(new com.amaze.filemanager.filesystem.HybridFile(openMode, parentPath), new com.amaze.filemanager.filesystem.HybridFile(openMode, parentPath, textfield.getText().toString().trim(), true), ma);
        dialog.dismiss();
    }, (java.lang.String text) -> {
        boolean isValidFilename;
        isValidFilename = com.amaze.filemanager.filesystem.FileProperties.isValidFilename(text);
        if ((!isValidFilename) || text.startsWith(" ")) {
            return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.invalid_name);
        } else if (text.length() < 1) {
            return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.field_empty);
        }
        return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState();
    });
}


/**
 * Prompt a dialog to user to input file name
 *
 * @param path
 * 		current path at which file to create
 * @param ma
 * 		{@link MainFragment} current fragment
 */
public void mkfile(final com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, final java.lang.String path, final com.amaze.filemanager.ui.fragments.MainFragment ma) {
    mk(com.amaze.filemanager.R.string.newfile, com.amaze.filemanager.utils.AppConstants.NEW_FILE_DELIMITER.concat(com.amaze.filemanager.utils.AppConstants.NEW_FILE_EXTENSION_TXT), (com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
        androidx.appcompat.widget.AppCompatEditText textfield;
        switch(MUID_STATIC) {
            // MainActivityHelper_2_InvalidViewFocusOperatorMutator
            case 2030: {
                /**
                * Inserted by Kadabra
                */
                textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
                textfield.requestFocus();
                break;
            }
            // MainActivityHelper_3_ViewComponentNotVisibleOperatorMutator
            case 3030: {
                /**
                * Inserted by Kadabra
                */
                textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
                textfield.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
            break;
        }
    }
    mkFile(new com.amaze.filemanager.filesystem.HybridFile(openMode, path), new com.amaze.filemanager.filesystem.HybridFile(openMode, path, textfield.getText().toString().trim(), false), ma);
    dialog.dismiss();
}, (java.lang.String text) -> {
    boolean isValidFilename;
    isValidFilename = com.amaze.filemanager.filesystem.FileProperties.isValidFilename(text);
    // The redundant equalsIgnoreCase() is needed since ".txt" itself does not end with .txt
    // (i.e. recommended as ".txt.txt"
    if (text.length() > 0) {
        if ((!isValidFilename) || text.startsWith(" ")) {
            return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.invalid_name);
        } else {
            android.content.SharedPreferences prefs;
            prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(mainActivity);
            if (text.startsWith(".") && (!prefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HIDDENFILES, false))) {
                return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_WARNING, com.amaze.filemanager.R.string.create_hidden_file_warn);
            } else if (!text.toLowerCase().endsWith(com.amaze.filemanager.utils.AppConstants.NEW_FILE_DELIMITER.concat(com.amaze.filemanager.utils.AppConstants.NEW_FILE_EXTENSION_TXT))) {
                return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_WARNING, com.amaze.filemanager.R.string.create_file_suggest_txt_extension);
            }
        }
    } else {
        return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.field_empty);
    }
    return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState();
});
}


private void mk(@androidx.annotation.StringRes
int newText, java.lang.String prefill, final com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback onPositiveAction, final com.amaze.filemanager.ui.views.WarnableTextInputValidator.OnTextValidate validator) {
com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showNameDialog(mainActivity, mainActivity.getResources().getString(com.amaze.filemanager.R.string.entername), prefill, mainActivity.getResources().getString(newText), mainActivity.getResources().getString(com.amaze.filemanager.R.string.create), mainActivity.getResources().getString(com.amaze.filemanager.R.string.cancel), null, onPositiveAction, validator);
dialog.show();
// place cursor at the beginning
androidx.appcompat.widget.AppCompatEditText textfield;
switch(MUID_STATIC) {
    // MainActivityHelper_4_InvalidViewFocusOperatorMutator
    case 4030: {
        /**
        * Inserted by Kadabra
        */
        textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
        textfield.requestFocus();
        break;
    }
    // MainActivityHelper_5_ViewComponentNotVisibleOperatorMutator
    case 5030: {
        /**
        * Inserted by Kadabra
        */
        textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
        textfield.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
    break;
}
}
textfield.post(() -> {
textfield.setSelection(0);
});
}


public java.lang.String getIntegralNames(java.lang.String path) {
java.lang.String newPath;
newPath = "";
switch (java.lang.Integer.parseInt(path)) {
case 0 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.images);
    break;
case 1 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.videos);
    break;
case 2 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.audio);
    break;
case 3 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.documents);
    break;
case 4 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.apks);
    break;
case 5 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.quick);
    break;
case 6 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.recent);
    break;
case 7 :
    newPath = mainActivity.getString(com.amaze.filemanager.R.string.trash_bin);
    break;
}
return newPath;
}


public void guideDialogForLEXA(java.lang.String path) {
guideDialogForLEXA(path, 3);
}


public void guideDialogForLEXA(java.lang.String path, int requestCode) {
final com.afollestad.materialdialogs.MaterialDialog.Builder x;
x = new com.afollestad.materialdialogs.MaterialDialog.Builder(mainActivity);
x.theme(mainActivity.getAppTheme().getMaterialDialogTheme());
x.title(com.amaze.filemanager.R.string.needs_access);
android.view.LayoutInflater layoutInflater;
layoutInflater = ((android.view.LayoutInflater) (mainActivity.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
android.view.View view;
view = layoutInflater.inflate(com.amaze.filemanager.R.layout.lexadrawer, null);
x.customView(view, true);
// textView
androidx.appcompat.widget.AppCompatTextView textView;
switch(MUID_STATIC) {
// MainActivityHelper_6_InvalidViewFocusOperatorMutator
case 6030: {
    /**
    * Inserted by Kadabra
    */
    textView = view.findViewById(com.amaze.filemanager.R.id.description);
    textView.requestFocus();
    break;
}
// MainActivityHelper_7_ViewComponentNotVisibleOperatorMutator
case 7030: {
    /**
    * Inserted by Kadabra
    */
    textView = view.findViewById(com.amaze.filemanager.R.id.description);
    textView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
textView = view.findViewById(com.amaze.filemanager.R.id.description);
break;
}
}
textView.setText((mainActivity.getString(com.amaze.filemanager.R.string.needs_access_summary) + path) + mainActivity.getString(com.amaze.filemanager.R.string.needs_access_summary1));
((androidx.appcompat.widget.AppCompatImageView) (view.findViewById(com.amaze.filemanager.R.id.icon))).setImageResource(com.amaze.filemanager.R.drawable.sd_operate_step);
x.positiveText(com.amaze.filemanager.R.string.open).negativeText(com.amaze.filemanager.R.string.cancel).positiveColor(accentColor).negativeColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> triggerStorageAccessFramework(requestCode)).onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> android.widget.Toast.makeText(mainActivity, com.amaze.filemanager.R.string.error, android.widget.Toast.LENGTH_SHORT).show());
final com.afollestad.materialdialogs.MaterialDialog y;
y = x.build();
y.show();
}


@android.annotation.SuppressLint("InlinedApi")
private void triggerStorageAccessFramework(int requestCode) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainActivityHelper_8_NullIntentOperatorMutator
case 8030: {
intent = null;
break;
}
// MainActivityHelper_9_InvalidKeyIntentOperatorMutator
case 9030: {
intent = new android.content.Intent((String) null);
break;
}
// MainActivityHelper_10_RandomActionIntentDefinitionOperatorMutator
case 10030: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_OPEN_DOCUMENT_TREE);
break;
}
}
com.amaze.filemanager.ui.ExtensionsKt.runIfDocumentsUIExists(intent, mainActivity, () -> mainActivity.startActivityForResult(intent, requestCode));
}


public void rename(com.amaze.filemanager.fileoperations.filesystem.OpenMode mode, final java.lang.String oldPath, final java.lang.String newPath, final java.lang.String newName, final boolean isDirectory, final android.app.Activity context, boolean rootmode) {
final android.widget.Toast toast;
toast = android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.renaming), android.widget.Toast.LENGTH_SHORT);
toast.show();
com.amaze.filemanager.filesystem.HybridFile oldFile;
oldFile = new com.amaze.filemanager.filesystem.HybridFile(mode, oldPath);
com.amaze.filemanager.filesystem.HybridFile newFile;
if (com.amaze.filemanager.utils.Utils.isNullOrEmpty(newName)) {
newFile = new com.amaze.filemanager.filesystem.HybridFile(mode, newPath);
} else {
newFile = new com.amaze.filemanager.filesystem.HybridFile(mode, newPath, newName, isDirectory);
}
com.amaze.filemanager.filesystem.Operations.rename(oldFile, newFile, rootmode, context, new com.amaze.filemanager.filesystem.Operations.ErrorCallBack() {
@java.lang.Override
public void exists(com.amaze.filemanager.filesystem.HybridFile file) {
context.runOnUiThread(() -> {
if (toast != null)
    toast.cancel();

android.widget.Toast.makeText(mainActivity, context.getString(com.amaze.filemanager.R.string.fileexist), android.widget.Toast.LENGTH_SHORT).show();
});
}


@java.lang.Override
public void launchSAF(com.amaze.filemanager.filesystem.HybridFile file) {
}


@java.lang.Override
public void launchSAF(final com.amaze.filemanager.filesystem.HybridFile file, final com.amaze.filemanager.filesystem.HybridFile file1) {
context.runOnUiThread(() -> {
if (toast != null)
    toast.cancel();

mainActivity.oppathe = file.getPath();
mainActivity.oppathe1 = file1.getPath();
mainActivity.operation = com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.RENAME;
guideDialogForLEXA(mainActivity.oppathe1);
});
}


@java.lang.Override
public void done(final com.amaze.filemanager.filesystem.HybridFile hFile, final boolean b) {
context.runOnUiThread(() -> {
/* DocumentFile.renameTo() may return false even when rename is successful. Hence we need an extra check
instead of merely looking at the return value
 */
if (b || newFile.exists(context)) {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // MainActivityHelper_11_InvalidKeyIntentOperatorMutator
        case 11030: {
            intent = new android.content.Intent((String) null);
            break;
        }
        // MainActivityHelper_12_RandomActionIntentDefinitionOperatorMutator
        case 12030: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST);
        break;
    }
}
switch(MUID_STATIC) {
    // MainActivityHelper_13_NullValueIntentPutExtraOperatorMutator
    case 13030: {
        intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, new Parcelable[0]);
        break;
    }
    // MainActivityHelper_14_IntentPayloadReplacementOperatorMutator
    case 14030: {
        intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, "");
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // MainActivityHelper_15_RandomActionIntentDefinitionOperatorMutator
        case 15030: {
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
        intent.putExtra(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST_FILE, hFile.getParent(context));
        break;
    }
}
break;
}
}
mainActivity.sendBroadcast(intent);
// update the database entry to reflect rename for encrypted file
if (oldPath.endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION)) {
try {
com.amaze.filemanager.database.CryptHandler cryptHandler;
cryptHandler = com.amaze.filemanager.database.CryptHandler.INSTANCE;
com.amaze.filemanager.database.models.explorer.EncryptedEntry oldEntry;
oldEntry = cryptHandler.findEntry(oldPath);
com.amaze.filemanager.database.models.explorer.EncryptedEntry newEntry;
newEntry = new com.amaze.filemanager.database.models.explorer.EncryptedEntry();
newEntry.setId(oldEntry.getId());
newEntry.setPassword(oldEntry.getPassword());
newEntry.setPath(newPath);
cryptHandler.updateEntry(oldEntry, newEntry);
} catch (java.lang.Exception e) {
com.amaze.filemanager.utils.MainActivityHelper.LOG.warn("failure after rename, couldn't change the encrypted entry", e);
// couldn't change the entry, leave it alone
}
}
} else
android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.operation_unsuccesful), android.widget.Toast.LENGTH_SHORT).show();

});
}


@java.lang.Override
public void invalidName(final com.amaze.filemanager.filesystem.HybridFile file) {
context.runOnUiThread(() -> {
if (toast != null)
toast.cancel();

android.widget.Toast.makeText(context, (context.getString(com.amaze.filemanager.R.string.invalid_name) + ": ") + file.getName(context), android.widget.Toast.LENGTH_LONG).show();
});
}

});
}


@com.amaze.filemanager.fileoperations.filesystem.FolderState
public int checkFolder(@androidx.annotation.NonNull
final java.io.File folder, android.content.Context context) {
return checkFolder(folder.getAbsolutePath(), com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, context);
}


@com.amaze.filemanager.fileoperations.filesystem.FolderState
public int checkFolder(final java.lang.String path, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, android.content.Context context) {
if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB.equals(openMode)) {
return com.amaze.filemanager.utils.smb.SmbUtil.checkFolder(path);
} else if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.SFTP.equals(openMode) || com.amaze.filemanager.fileoperations.filesystem.OpenMode.FTP.equals(openMode)) {
int result;
result = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.checkFolder(path);
return result;
} else if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE.equals(openMode)) {
androidx.documentfile.provider.DocumentFile d;
d = androidx.documentfile.provider.DocumentFile.fromTreeUri(com.amaze.filemanager.application.AppConfig.getInstance(), com.amaze.filemanager.filesystem.SafRootHolder.getUriRoot());
if (d == null)
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.DOESNT_EXIST;
else {
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD;
}
} else {
java.io.File folder;
folder = new java.io.File(path);
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
if (com.amaze.filemanager.filesystem.ExternalSdCardOperation.isOnExtSdCard(folder, context)) {
if ((!folder.exists()) || (!folder.isDirectory())) {
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.DOESNT_EXIST;
}
// On Android 5, trigger storage access framework.
if (!com.amaze.filemanager.filesystem.FileProperties.isWritableNormalOrSaf(folder, context)) {
guideDialogForLEXA(folder.getPath());
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.CAN_CREATE_FILES;
}
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD;
} else if (com.amaze.filemanager.filesystem.FileProperties.isWritable(new java.io.File(folder, com.amaze.filemanager.filesystem.files.FileUtils.DUMMY_FILE))) {
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD;
} else
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.DOESNT_EXIST;

} else if (android.os.Build.VERSION.SDK_INT == 19) {
if (com.amaze.filemanager.filesystem.ExternalSdCardOperation.isOnExtSdCard(folder, context)) {
// Assume that Kitkat workaround works
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD;
} else if (com.amaze.filemanager.filesystem.FileProperties.isWritable(new java.io.File(folder, com.amaze.filemanager.filesystem.files.FileUtils.DUMMY_FILE))) {
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD;
} else
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.DOESNT_EXIST;

} else if (com.amaze.filemanager.filesystem.FileProperties.isWritable(new java.io.File(folder, com.amaze.filemanager.filesystem.files.FileUtils.DUMMY_FILE))) {
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD;
} else {
return com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.DOESNT_EXIST;
}
}
}


/**
 * Helper method to start Compress service
 *
 * @param file
 * 		the new compressed file
 * @param baseFiles
 * 		list of {@link HybridFileParcelable} to be compressed
 */
public void compressFiles(java.io.File file, java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> baseFiles) {
int mode;
mode = checkFolder(file.getParentFile(), mainActivity);
if (mode == 2) {
mainActivity.oppathe = file.getPath();
mainActivity.operation = com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.COMPRESS;
mainActivity.oparrayList = baseFiles;
} else if (mode == 1) {
android.content.Intent intent2;
switch(MUID_STATIC) {
// MainActivityHelper_16_NullIntentOperatorMutator
case 16030: {
intent2 = null;
break;
}
// MainActivityHelper_17_InvalidKeyIntentOperatorMutator
case 17030: {
intent2 = new android.content.Intent((com.amaze.filemanager.ui.activities.MainActivity) null, com.amaze.filemanager.asynchronous.services.ZipService.class);
break;
}
// MainActivityHelper_18_RandomActionIntentDefinitionOperatorMutator
case 18030: {
intent2 = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent2 = new android.content.Intent(mainActivity, com.amaze.filemanager.asynchronous.services.ZipService.class);
break;
}
}
switch(MUID_STATIC) {
// MainActivityHelper_19_NullValueIntentPutExtraOperatorMutator
case 19030: {
intent2.putExtra(com.amaze.filemanager.asynchronous.services.ZipService.KEY_COMPRESS_PATH, new Parcelable[0]);
break;
}
// MainActivityHelper_20_IntentPayloadReplacementOperatorMutator
case 20030: {
intent2.putExtra(com.amaze.filemanager.asynchronous.services.ZipService.KEY_COMPRESS_PATH, "");
break;
}
default: {
switch(MUID_STATIC) {
// MainActivityHelper_21_RandomActionIntentDefinitionOperatorMutator
case 21030: {
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
intent2.putExtra(com.amaze.filemanager.asynchronous.services.ZipService.KEY_COMPRESS_PATH, file.getPath());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainActivityHelper_22_NullValueIntentPutExtraOperatorMutator
case 22030: {
intent2.putExtra(com.amaze.filemanager.asynchronous.services.ZipService.KEY_COMPRESS_FILES, new Parcelable[0]);
break;
}
// MainActivityHelper_23_IntentPayloadReplacementOperatorMutator
case 23030: {
intent2.putExtra(com.amaze.filemanager.asynchronous.services.ZipService.KEY_COMPRESS_FILES, (java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable>) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainActivityHelper_24_RandomActionIntentDefinitionOperatorMutator
case 24030: {
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
intent2.putExtra(com.amaze.filemanager.asynchronous.services.ZipService.KEY_COMPRESS_FILES, baseFiles);
break;
}
}
break;
}
}
com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.runService(mainActivity, intent2);
} else
android.widget.Toast.makeText(mainActivity, com.amaze.filemanager.R.string.not_allowed, android.widget.Toast.LENGTH_SHORT).show();

}


public void mkFile(final com.amaze.filemanager.filesystem.HybridFile parentFile, final com.amaze.filemanager.filesystem.HybridFile path, final com.amaze.filemanager.ui.fragments.MainFragment ma) {
final android.widget.Toast toast;
toast = android.widget.Toast.makeText(ma.getActivity(), ma.getString(com.amaze.filemanager.R.string.creatingfile), android.widget.Toast.LENGTH_SHORT);
toast.show();
com.amaze.filemanager.filesystem.Operations.mkfile(parentFile, path, ma.getActivity(), mainActivity.isRootExplorer(), new com.amaze.filemanager.filesystem.Operations.ErrorCallBack() {
@java.lang.Override
public void exists(final com.amaze.filemanager.filesystem.HybridFile file) {
ma.getActivity().runOnUiThread(() -> {
if (toast != null)
toast.cancel();

android.widget.Toast.makeText(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.fileexist), android.widget.Toast.LENGTH_SHORT).show();
if ((ma != null) && (ma.getActivity() != null)) {
// retry with dialog prompted again
mkfile(file.getMode(), file.getParent(mainActivity.getApplicationContext()), ma);
}
});
}


@java.lang.Override
public void launchSAF(com.amaze.filemanager.filesystem.HybridFile file) {
ma.getActivity().runOnUiThread(() -> {
if (toast != null)
toast.cancel();

mainActivity.oppathe = path.getPath();
mainActivity.operation = com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.NEW_FILE;
guideDialogForLEXA(mainActivity.oppathe);
});
}


@java.lang.Override
public void launchSAF(com.amaze.filemanager.filesystem.HybridFile file, com.amaze.filemanager.filesystem.HybridFile file1) {
}


@java.lang.Override
public void done(com.amaze.filemanager.filesystem.HybridFile hFile, final boolean b) {
ma.getActivity().runOnUiThread(() -> {
if (b) {
ma.updateList(false);
} else {
android.widget.Toast.makeText(ma.getActivity(), ma.getString(com.amaze.filemanager.R.string.operation_unsuccesful), android.widget.Toast.LENGTH_SHORT).show();
}
});
}


@java.lang.Override
public void invalidName(final com.amaze.filemanager.filesystem.HybridFile file) {
ma.getActivity().runOnUiThread(() -> {
if (toast != null)
toast.cancel();

android.widget.Toast.makeText(ma.getActivity(), (ma.getString(com.amaze.filemanager.R.string.invalid_name) + ": ") + file.getName(ma.getMainActivity()), android.widget.Toast.LENGTH_LONG).show();
});
}

});
}


public void mkDir(final com.amaze.filemanager.filesystem.HybridFile parentPath, final com.amaze.filemanager.filesystem.HybridFile path, final com.amaze.filemanager.ui.fragments.MainFragment ma) {
final android.widget.Toast toast;
toast = android.widget.Toast.makeText(ma.getActivity(), ma.getString(com.amaze.filemanager.R.string.creatingfolder), android.widget.Toast.LENGTH_SHORT);
toast.show();
com.amaze.filemanager.filesystem.Operations.mkdir(parentPath, path, ma.getActivity(), mainActivity.isRootExplorer(), new com.amaze.filemanager.filesystem.Operations.ErrorCallBack() {
@java.lang.Override
public void exists(final com.amaze.filemanager.filesystem.HybridFile file) {
ma.getActivity().runOnUiThread(() -> {
if (toast != null)
toast.cancel();

android.widget.Toast.makeText(mainActivity, mainActivity.getString(com.amaze.filemanager.R.string.fileexist), android.widget.Toast.LENGTH_SHORT).show();
if ((ma != null) && (ma.getActivity() != null)) {
// retry with dialog prompted again
mkdir(file.getMode(), file.getParent(mainActivity.getApplicationContext()), ma);
}
});
}


@java.lang.Override
public void launchSAF(com.amaze.filemanager.filesystem.HybridFile file) {
if (toast != null)
toast.cancel();

ma.getActivity().runOnUiThread(() -> {
mainActivity.oppathe = path.getPath();
mainActivity.operation = com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.NEW_FOLDER;
guideDialogForLEXA(mainActivity.oppathe);
});
}


@java.lang.Override
public void launchSAF(com.amaze.filemanager.filesystem.HybridFile file, com.amaze.filemanager.filesystem.HybridFile file1) {
}


@java.lang.Override
public void done(com.amaze.filemanager.filesystem.HybridFile hFile, final boolean b) {
ma.getActivity().runOnUiThread(() -> {
if (b) {
ma.updateList(false);
} else {
android.widget.Toast.makeText(ma.getActivity(), ma.getString(com.amaze.filemanager.R.string.operation_unsuccesful), android.widget.Toast.LENGTH_SHORT).show();
}
});
}


@java.lang.Override
public void invalidName(final com.amaze.filemanager.filesystem.HybridFile file) {
ma.getActivity().runOnUiThread(() -> {
if (toast != null)
toast.cancel();

android.widget.Toast.makeText(ma.getActivity(), (ma.getString(com.amaze.filemanager.R.string.invalid_name) + ": ") + file.getName(ma.getMainActivity()), android.widget.Toast.LENGTH_LONG).show();
});
}

});
}


public void deleteFiles(java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> files, boolean doDeletePermanently) {
if ((files == null) || (files.size() == 0))
return;

if (files.get(0).isSmb() || files.get(0).isFtp()) {
new com.amaze.filemanager.asynchronous.asynctasks.DeleteTask(mainActivity, doDeletePermanently).execute(files);
return;
}
@com.amaze.filemanager.fileoperations.filesystem.FolderState
int mode;
mode = checkFolder(files.get(0).getParent(mainActivity), files.get(0).getMode(), mainActivity);
if (mode == com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.CAN_CREATE_FILES) {
mainActivity.oparrayList = files;
mainActivity.operation = com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.DELETE;
} else if ((mode == com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD) || (mode == com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.DOESNT_EXIST))
new com.amaze.filemanager.asynchronous.asynctasks.DeleteTask(mainActivity, doDeletePermanently).execute(files);
else
android.widget.Toast.makeText(mainActivity, com.amaze.filemanager.R.string.not_allowed, android.widget.Toast.LENGTH_SHORT).show();

}


public void extractFile(@androidx.annotation.NonNull
java.io.File file) {
final java.io.File parent;
parent = file.getParentFile();
if (parent == null) {
android.widget.Toast.makeText(mainActivity, com.amaze.filemanager.R.string.error, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.utils.MainActivityHelper.LOG.warn("File's parent is null " + file.getPath());
return;
}
@com.amaze.filemanager.fileoperations.filesystem.FolderState
int mode;
mode = checkFolder(parent, mainActivity);
switch (mode) {
case com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.WRITABLE_OR_ON_SDCARD :
com.amaze.filemanager.filesystem.compressed.showcontents.Decompressor decompressor;
decompressor = com.amaze.filemanager.filesystem.compressed.CompressedHelper.getCompressorInstance(mainActivity, file);
if (decompressor == null) {
android.widget.Toast.makeText(mainActivity, com.amaze.filemanager.R.string.error_cant_decompress_that_file, android.widget.Toast.LENGTH_LONG).show();
return;
}
decompressor.decompress(file.getPath());
break;
case com.amaze.filemanager.fileoperations.filesystem.FolderStateKt.CAN_CREATE_FILES :
mainActivity.oppathe = file.getPath();
mainActivity.operation = com.amaze.filemanager.fileoperations.filesystem.OperationTypeKt.EXTRACT;
break;
default :
android.widget.Toast.makeText(mainActivity, com.amaze.filemanager.R.string.not_allowed, android.widget.Toast.LENGTH_SHORT).show();
break;
}
}


/**
 * Retrieve a path with {@link OTGUtil#PREFIX_OTG} as prefix
 */
public java.lang.String parseOTGPath(java.lang.String path) {
if (path.contains(com.amaze.filemanager.utils.OTGUtil.PREFIX_OTG))
return path;
else {
switch(MUID_STATIC) {
// MainActivityHelper_25_BinaryMutator
case 25030: {
return com.amaze.filemanager.utils.OTGUtil.PREFIX_OTG + path.substring(path.indexOf(":") - 1);
}
default: {
return com.amaze.filemanager.utils.OTGUtil.PREFIX_OTG + path.substring(path.indexOf(":") + 1);
}
}
}
}


public java.lang.String parseCloudPath(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType, java.lang.String path) {
switch (serviceType) {
case DROPBOX :
if (path.contains(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_DROPBOX))
return path;
else {
switch(MUID_STATIC) {
// MainActivityHelper_26_BinaryMutator
case 26030: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_DROPBOX + path.substring(path.indexOf(":") - 1);
}
default: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_DROPBOX + path.substring(path.indexOf(":") + 1);
}
}
}
case BOX :
if (path.contains(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_BOX))
return path;
else {
switch(MUID_STATIC) {
// MainActivityHelper_27_BinaryMutator
case 27030: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_BOX + path.substring(path.indexOf(":") - 1);
}
default: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_BOX + path.substring(path.indexOf(":") + 1);
}
}
}
case GDRIVE :
if (path.contains(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_GOOGLE_DRIVE))
return path;
else {
switch(MUID_STATIC) {
// MainActivityHelper_28_BinaryMutator
case 28030: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_GOOGLE_DRIVE + path.substring(path.indexOf(":") - 1);
}
default: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_GOOGLE_DRIVE + path.substring(path.indexOf(":") + 1);
}
}
}
case ONEDRIVE :
if (path.contains(com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_ONE_DRIVE))
return path;
else {
switch(MUID_STATIC) {
// MainActivityHelper_29_BinaryMutator
case 29030: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_ONE_DRIVE + path.substring(path.indexOf(":") - 1);
}
default: {
return com.amaze.filemanager.database.CloudHandler.CLOUD_PREFIX_ONE_DRIVE + path.substring(path.indexOf(":") + 1);
}
}
}
default :
return path;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
