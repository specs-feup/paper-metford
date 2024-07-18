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
package com.amaze.filemanager.ui;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.filesystem.PasteHelper;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.util.ArrayList;
import com.amaze.filemanager.ui.activities.MainActivity;
import com.amaze.filemanager.ui.dialogs.EncryptWithPresetPasswordSaveAsDialog;
import android.net.Uri;
import com.amaze.filemanager.ui.dialogs.EncryptAuthenticateDialog;
import com.amaze.filemanager.ui.fragments.MainFragment;
import com.amaze.filemanager.R;
import androidx.annotation.NonNull;
import android.widget.Toast;
import android.content.SharedPreferences;
import java.io.IOException;
import java.security.GeneralSecurityException;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.content.Intent;
import android.view.MenuItem;
import com.amaze.filemanager.filesystem.files.EncryptDecryptUtils;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import androidx.documentfile.provider.DocumentFile;
import android.view.View;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import com.amaze.filemanager.utils.DataUtils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.widget.PopupMenu;
import com.amaze.filemanager.asynchronous.services.EncryptService;
import java.io.File;
import androidx.annotation.Nullable;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This class contains the functionality of the PopupMenu for each file in the MainFragment
 *
 * @author Emmanuel on 25/5/2017, at 16:39. Edited by bowiechen on 2019-10-19.
 */
public class ItemPopupMenu extends android.widget.PopupMenu implements android.widget.PopupMenu.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    @androidx.annotation.NonNull
    private final android.content.Context context;

    @androidx.annotation.NonNull
    private final com.amaze.filemanager.ui.activities.MainActivity mainActivity;

    @androidx.annotation.NonNull
    private final com.amaze.filemanager.ui.provider.UtilitiesProvider utilitiesProvider;

    @androidx.annotation.NonNull
    private final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;

    @androidx.annotation.NonNull
    private final android.content.SharedPreferences sharedPrefs;

    @androidx.annotation.NonNull
    private final com.amaze.filemanager.adapters.data.LayoutElementParcelable rowItem;

    private final int accentColor;

    public ItemPopupMenu(@androidx.annotation.NonNull
    android.content.Context c, @androidx.annotation.NonNull
    com.amaze.filemanager.ui.activities.MainActivity ma, @androidx.annotation.NonNull
    com.amaze.filemanager.ui.provider.UtilitiesProvider up, @androidx.annotation.NonNull
    com.amaze.filemanager.ui.fragments.MainFragment mainFragment, @androidx.annotation.NonNull
    com.amaze.filemanager.adapters.data.LayoutElementParcelable ri, @androidx.annotation.NonNull
    android.view.View anchor, @androidx.annotation.NonNull
    android.content.SharedPreferences sharedPreferences) {
        super(c, anchor);
        context = c;
        mainActivity = ma;
        utilitiesProvider = up;
        this.mainFragment = mainFragment;
        sharedPrefs = sharedPreferences;
        rowItem = ri;
        accentColor = mainActivity.getAccent();
        setOnMenuItemClickListener(this);
    }


    @java.lang.Override
    public boolean onMenuItemClick(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case com.amaze.filemanager.R.id.about :
                com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPropertiesDialogWithPermissions(rowItem.generateBaseFile(), rowItem.permissions, mainActivity, mainFragment, mainActivity.isRootExplorer(), utilitiesProvider.getAppTheme());
                return true;
            case com.amaze.filemanager.R.id.share :
                switch (rowItem.getMode()) {
                    case DROPBOX :
                    case BOX :
                    case GDRIVE :
                    case ONEDRIVE :
                        com.amaze.filemanager.filesystem.files.FileUtils.shareCloudFile(rowItem.desc, rowItem.getMode(), context);
                        break;
                    default :
                        java.util.ArrayList<java.io.File> arrayList;
                        arrayList = new java.util.ArrayList<>();
                        arrayList.add(new java.io.File(rowItem.desc));
                        com.amaze.filemanager.filesystem.files.FileUtils.shareFiles(arrayList, mainActivity, utilitiesProvider.getAppTheme(), accentColor);
                        break;
                }
                return true;
            case com.amaze.filemanager.R.id.rename :
                mainFragment.rename(rowItem.generateBaseFile());
                return true;
            case com.amaze.filemanager.R.id.cpy :
            case com.amaze.filemanager.R.id.cut :
                {
                    int op;
                    op = (item.getItemId() == com.amaze.filemanager.R.id.cpy) ? com.amaze.filemanager.filesystem.PasteHelper.OPERATION_COPY : com.amaze.filemanager.filesystem.PasteHelper.OPERATION_CUT;
                    com.amaze.filemanager.filesystem.PasteHelper pasteHelper;
                    pasteHelper = new com.amaze.filemanager.filesystem.PasteHelper(mainActivity, op, new com.amaze.filemanager.filesystem.HybridFileParcelable[]{ rowItem.generateBaseFile() });
                    mainActivity.setPaste(pasteHelper);
                    return true;
                }
            case com.amaze.filemanager.R.id.ex :
                mainActivity.mainActivityHelper.extractFile(new java.io.File(rowItem.desc));
                return true;
            case com.amaze.filemanager.R.id.book :
                com.amaze.filemanager.utils.DataUtils dataUtils;
                dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
                if (dataUtils.addBook(new java.lang.String[]{ rowItem.title, rowItem.desc }, true)) {
                    mainActivity.getDrawer().refreshDrawer();
                    android.widget.Toast.makeText(mainFragment.getActivity(), mainFragment.getString(com.amaze.filemanager.R.string.bookmarks_added), android.widget.Toast.LENGTH_LONG).show();
                } else {
                    android.widget.Toast.makeText(mainFragment.getActivity(), mainFragment.getString(com.amaze.filemanager.R.string.bookmark_exists), android.widget.Toast.LENGTH_LONG).show();
                }
                return true;
            case com.amaze.filemanager.R.id.delete :
                java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> positions;
                positions = new java.util.ArrayList<>();
                positions.add(rowItem);
                com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.deleteFilesDialog(context, mainActivity, positions, utilitiesProvider.getAppTheme());
                return true;
            case com.amaze.filemanager.R.id.restore :
                java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> p2;
                p2 = new java.util.ArrayList<>();
                p2.add(rowItem);
                com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.restoreFilesDialog(context, mainActivity, p2, utilitiesProvider.getAppTheme());
                return true;
            case com.amaze.filemanager.R.id.open_with :
                boolean useNewStack;
                useNewStack = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK, false);
                if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE.equals(rowItem.getMode())) {
                    @androidx.annotation.Nullable
                    android.net.Uri fullUri;
                    fullUri = rowItem.generateBaseFile().getFullUri();
                    if (fullUri != null) {
                        androidx.documentfile.provider.DocumentFile documentFile;
                        documentFile = androidx.documentfile.provider.DocumentFile.fromSingleUri(context, fullUri);
                        if (documentFile != null) {
                            com.amaze.filemanager.filesystem.files.FileUtils.openWith(documentFile, mainActivity, useNewStack);
                            return true;
                        }
                    }
                }
                com.amaze.filemanager.filesystem.files.FileUtils.openWith(new java.io.File(rowItem.desc), mainActivity, useNewStack);
                return true;
            case com.amaze.filemanager.R.id.encrypt :
                final android.content.Intent encryptIntent;
                switch(MUID_STATIC) {
                    // ItemPopupMenu_0_NullIntentOperatorMutator
                    case 144: {
                        encryptIntent = null;
                        break;
                    }
                    // ItemPopupMenu_1_InvalidKeyIntentOperatorMutator
                    case 1144: {
                        encryptIntent = new android.content.Intent((android.content.Context) null, com.amaze.filemanager.asynchronous.services.EncryptService.class);
                        break;
                    }
                    // ItemPopupMenu_2_RandomActionIntentDefinitionOperatorMutator
                    case 2144: {
                        encryptIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                        break;
                    }
                    default: {
                    encryptIntent = new android.content.Intent(context, com.amaze.filemanager.asynchronous.services.EncryptService.class);
                    break;
                }
            }
            switch(MUID_STATIC) {
                // ItemPopupMenu_3_NullValueIntentPutExtraOperatorMutator
                case 3144: {
                    encryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_OPEN_MODE, new Parcelable[0]);
                    break;
                }
                // ItemPopupMenu_4_IntentPayloadReplacementOperatorMutator
                case 4144: {
                    encryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_OPEN_MODE, 0);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // ItemPopupMenu_5_RandomActionIntentDefinitionOperatorMutator
                    case 5144: {
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
                    encryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_OPEN_MODE, rowItem.getMode().ordinal());
                    break;
                }
            }
            break;
        }
    }
    switch(MUID_STATIC) {
        // ItemPopupMenu_6_NullValueIntentPutExtraOperatorMutator
        case 6144: {
            encryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_SOURCE, new Parcelable[0]);
            break;
        }
        // ItemPopupMenu_7_IntentPayloadReplacementOperatorMutator
        case 7144: {
            encryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_SOURCE, (HybridFileParcelable) null);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // ItemPopupMenu_8_RandomActionIntentDefinitionOperatorMutator
            case 8144: {
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
            encryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_SOURCE, rowItem.generateBaseFile());
            break;
        }
    }
    break;
}
}
final com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.EncryptButtonCallbackInterface encryptButtonCallbackInterfaceAuthenticate;
encryptButtonCallbackInterfaceAuthenticate = new com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.EncryptButtonCallbackInterface() {
@java.lang.Override
public void onButtonPressed(android.content.Intent intent, java.lang.String password) throws java.security.GeneralSecurityException, java.io.IOException {
    com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.startEncryption(context, rowItem.generateBaseFile().getPath(), password, intent);
}

};
final android.content.SharedPreferences preferences;
preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
if (!preferences.getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CRYPT_MASTER_PASSWORD, com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CRYPT_MASTER_PASSWORD_DEFAULT).equals("")) {
com.amaze.filemanager.ui.dialogs.EncryptWithPresetPasswordSaveAsDialog.show(context, encryptIntent, mainActivity, com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.ENCRYPT_PASSWORD_MASTER, encryptButtonCallbackInterfaceAuthenticate);
} else if (preferences.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CRYPT_FINGERPRINT, com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CRYPT_FINGERPRINT_DEFAULT)) {
com.amaze.filemanager.ui.dialogs.EncryptWithPresetPasswordSaveAsDialog.show(context, encryptIntent, mainActivity, com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.ENCRYPT_PASSWORD_FINGERPRINT, encryptButtonCallbackInterfaceAuthenticate);
} else {
com.amaze.filemanager.ui.dialogs.EncryptAuthenticateDialog.show(context, encryptIntent, mainActivity, utilitiesProvider.getAppTheme(), encryptButtonCallbackInterfaceAuthenticate);
}
return true;
case com.amaze.filemanager.R.id.decrypt :
com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.decryptFile(context, mainActivity, mainFragment, mainFragment.getMainFragmentViewModel().getOpenMode(), rowItem.generateBaseFile(), rowItem.generateBaseFile().getParent(context), utilitiesProvider, false);
return true;
case com.amaze.filemanager.R.id.compress :
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showCompressDialog(mainActivity, rowItem.generateBaseFile(), mainActivity.getCurrentMainFragment().getMainFragmentViewModel().getCurrentPath());
return true;
case com.amaze.filemanager.R.id.return_select :
mainFragment.returnIntentResults(new com.amaze.filemanager.filesystem.HybridFileParcelable[]{ rowItem.generateBaseFile() });
return true;
}
return false;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
