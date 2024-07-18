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
package com.amaze.filemanager.filesystem.files;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.utils.PasswordUtil;
import com.amaze.filemanager.ui.activities.MainActivity;
import org.slf4j.Logger;
import com.amaze.filemanager.ui.fragments.MainFragment;
import com.amaze.filemanager.ui.dialogs.DecryptFingerprintDialog;
import com.amaze.filemanager.R;
import com.amaze.filemanager.database.CryptHandler;
import androidx.annotation.NonNull;
import android.os.Build;
import com.amaze.filemanager.asynchronous.services.DecryptService;
import android.widget.Toast;
import android.util.Base64;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil;
import android.content.SharedPreferences;
import static com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION;
import com.amaze.filemanager.database.models.explorer.EncryptedEntry;
import java.io.IOException;
import java.security.GeneralSecurityException;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.content.Intent;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import static com.amaze.filemanager.asynchronous.services.EncryptService.TAG_AESCRYPT;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.asynchronous.services.EncryptService;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Provides useful interfaces and methods for encryption/decryption
 *
 * @author Emmanuel on 25/5/2017, at 16:55.
 */
public class EncryptDecryptUtils {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String DECRYPT_BROADCAST = "decrypt_broadcast";

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.class);

    /**
     * Queries database to map path and password. Starts the encryption process after database query
     *
     * @param path
     * 		the path of file to encrypt
     * @param password
     * 		the password in plaintext
     * @throws GeneralSecurityException
     * 		Errors on encrypting file/folder
     * @throws IOException
     * 		I/O errors on encrypting file/folder
     */
    public static void startEncryption(android.content.Context c, final java.lang.String path, final java.lang.String password, android.content.Intent intent) throws java.security.GeneralSecurityException, java.io.IOException {
        java.lang.String destPath;
        switch(MUID_STATIC) {
            // EncryptDecryptUtils_0_BinaryMutator
            case 38: {
                destPath = path.substring(0, path.lastIndexOf('/') - 1).concat(intent.getStringExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_ENCRYPT_TARGET));
                break;
            }
            default: {
            destPath = path.substring(0, path.lastIndexOf('/') + 1).concat(intent.getStringExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_ENCRYPT_TARGET));
            break;
        }
    }
    // EncryptService.TAG_ENCRYPT_TARGET already has the .aze extension, no need to append again
    if (!intent.getBooleanExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_AESCRYPT, false)) {
        com.amaze.filemanager.database.models.explorer.EncryptedEntry encryptedEntry;
        encryptedEntry = new com.amaze.filemanager.database.models.explorer.EncryptedEntry(destPath, password);
        com.amaze.filemanager.database.CryptHandler.INSTANCE.addEntry(encryptedEntry);
    }
    // start the encryption process
    com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.runService(c, intent);
}


public static void decryptFile(android.content.Context c, final com.amaze.filemanager.ui.activities.MainActivity mainActivity, final com.amaze.filemanager.ui.fragments.MainFragment main, com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, com.amaze.filemanager.filesystem.HybridFileParcelable sourceFile, java.lang.String decryptPath, com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider, boolean broadcastResult) {
    android.content.Intent decryptIntent;
    switch(MUID_STATIC) {
        // EncryptDecryptUtils_1_NullIntentOperatorMutator
        case 1038: {
            decryptIntent = null;
            break;
        }
        // EncryptDecryptUtils_2_InvalidKeyIntentOperatorMutator
        case 2038: {
            decryptIntent = new android.content.Intent((Context) null, com.amaze.filemanager.asynchronous.services.DecryptService.class);
            break;
        }
        // EncryptDecryptUtils_3_RandomActionIntentDefinitionOperatorMutator
        case 3038: {
            decryptIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        decryptIntent = new android.content.Intent(main.getContext(), com.amaze.filemanager.asynchronous.services.DecryptService.class);
        break;
    }
}
switch(MUID_STATIC) {
    // EncryptDecryptUtils_4_NullValueIntentPutExtraOperatorMutator
    case 4038: {
        decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_OPEN_MODE, new Parcelable[0]);
        break;
    }
    // EncryptDecryptUtils_5_IntentPayloadReplacementOperatorMutator
    case 5038: {
        decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_OPEN_MODE, 0);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // EncryptDecryptUtils_6_RandomActionIntentDefinitionOperatorMutator
        case 6038: {
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
        decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_OPEN_MODE, openMode.ordinal());
        break;
    }
}
break;
}
}
switch(MUID_STATIC) {
// EncryptDecryptUtils_7_NullValueIntentPutExtraOperatorMutator
case 7038: {
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_SOURCE, new Parcelable[0]);
break;
}
// EncryptDecryptUtils_8_IntentPayloadReplacementOperatorMutator
case 8038: {
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_SOURCE, (com.amaze.filemanager.filesystem.HybridFileParcelable) null);
break;
}
default: {
switch(MUID_STATIC) {
// EncryptDecryptUtils_9_RandomActionIntentDefinitionOperatorMutator
case 9038: {
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
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_SOURCE, sourceFile);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// EncryptDecryptUtils_10_NullValueIntentPutExtraOperatorMutator
case 10038: {
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_DECRYPT_PATH, new Parcelable[0]);
break;
}
// EncryptDecryptUtils_11_IntentPayloadReplacementOperatorMutator
case 11038: {
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_DECRYPT_PATH, "");
break;
}
default: {
switch(MUID_STATIC) {
// EncryptDecryptUtils_12_RandomActionIntentDefinitionOperatorMutator
case 12038: {
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
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_DECRYPT_PATH, decryptPath);
break;
}
}
break;
}
}
android.content.SharedPreferences preferences1;
preferences1 = androidx.preference.PreferenceManager.getDefaultSharedPreferences(main.getContext());
if (sourceFile.getPath().endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION)) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPasswordDialog(c, mainActivity, utilsProvider.getAppTheme(), com.amaze.filemanager.R.string.crypt_decrypt, com.amaze.filemanager.R.string.authenticate_password, (com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
androidx.appcompat.widget.AppCompatEditText editText;
switch(MUID_STATIC) {
// EncryptDecryptUtils_13_InvalidViewFocusOperatorMutator
case 13038: {
/**
* Inserted by Kadabra
*/
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
editText.requestFocus();
break;
}
// EncryptDecryptUtils_14_ViewComponentNotVisibleOperatorMutator
case 14038: {
/**
* Inserted by Kadabra
*/
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
editText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
break;
}
}
switch(MUID_STATIC) {
// EncryptDecryptUtils_15_NullValueIntentPutExtraOperatorMutator
case 15038: {
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_PASSWORD, new Parcelable[0]);
break;
}
// EncryptDecryptUtils_16_IntentPayloadReplacementOperatorMutator
case 16038: {
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_PASSWORD, "");
break;
}
default: {
switch(MUID_STATIC) {
// EncryptDecryptUtils_17_RandomActionIntentDefinitionOperatorMutator
case 17038: {
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
decryptIntent.putExtra(com.amaze.filemanager.asynchronous.services.EncryptService.TAG_PASSWORD, editText.getText().toString());
break;
}
}
break;
}
}
com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.runService(main.getContext(), decryptIntent);
dialog.dismiss();
}, null);
} else {
com.amaze.filemanager.database.models.explorer.EncryptedEntry encryptedEntry;
try {
encryptedEntry = com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.findEncryptedEntry(sourceFile.getPath());
} catch (java.security.GeneralSecurityException | java.io.IOException e) {
com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.LOG.warn("failed to find encrypted entry while decrypting", e);
// we couldn't find any entry in database or lost the key to decipher
android.widget.Toast.makeText(main.getContext(), main.getActivity().getString(com.amaze.filemanager.R.string.crypt_decryption_fail), android.widget.Toast.LENGTH_LONG).show();
return;
}
com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.DecryptButtonCallbackInterface decryptButtonCallbackInterface;
decryptButtonCallbackInterface = new com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.DecryptButtonCallbackInterface() {
@java.lang.Override
public void confirm(android.content.Intent intent) {
com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.runService(main.getContext(), intent);
}


@java.lang.Override
public void failed() {
android.widget.Toast.makeText(main.getContext(), main.getActivity().getString(com.amaze.filemanager.R.string.crypt_decryption_fail_password), android.widget.Toast.LENGTH_LONG).show();
}

};
if ((encryptedEntry == null) && (!sourceFile.getPath().endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION))) {
// couldn't find the matching path in database, we lost the password
android.widget.Toast.makeText(main.getContext(), main.getActivity().getString(com.amaze.filemanager.R.string.crypt_decryption_fail), android.widget.Toast.LENGTH_LONG).show();
return;
}
switch (encryptedEntry.getPassword().value) {
case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.ENCRYPT_PASSWORD_FINGERPRINT :
try {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
com.amaze.filemanager.ui.dialogs.DecryptFingerprintDialog.show(c, mainActivity, decryptIntent, utilsProvider.getAppTheme(), decryptButtonCallbackInterface);
} else
throw new java.lang.IllegalStateException("API < M!");

} catch (java.security.GeneralSecurityException | java.io.IOException | java.lang.IllegalStateException e) {
com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.LOG.warn("failed to form fingerprint dialog", e);
android.widget.Toast.makeText(main.getContext(), main.getString(com.amaze.filemanager.R.string.crypt_decryption_fail), android.widget.Toast.LENGTH_LONG).show();
}
break;
case com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.ENCRYPT_PASSWORD_MASTER :
try {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showDecryptDialog(c, mainActivity, decryptIntent, utilsProvider.getAppTheme(), com.amaze.filemanager.utils.PasswordUtil.INSTANCE.decryptPassword(c, preferences1.getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CRYPT_MASTER_PASSWORD, com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CRYPT_MASTER_PASSWORD_DEFAULT), android.util.Base64.DEFAULT), decryptButtonCallbackInterface);
} catch (java.security.GeneralSecurityException | java.io.IOException e) {
com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.LOG.warn("failed to show decrypt dialog, e");
android.widget.Toast.makeText(main.getContext(), main.getString(com.amaze.filemanager.R.string.crypt_decryption_fail), android.widget.Toast.LENGTH_LONG).show();
}
break;
default :
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showDecryptDialog(c, mainActivity, decryptIntent, utilsProvider.getAppTheme(), encryptedEntry.getPassword().value, decryptButtonCallbackInterface);
break;
}
}
}


/**
 * Queries database to find entry for the specific path
 *
 * @param path
 * 		the path to match with
 * @return the entry
 */
private static com.amaze.filemanager.database.models.explorer.EncryptedEntry findEncryptedEntry(java.lang.String path) throws java.security.GeneralSecurityException, java.io.IOException {
com.amaze.filemanager.database.CryptHandler handler;
handler = com.amaze.filemanager.database.CryptHandler.INSTANCE;
com.amaze.filemanager.database.models.explorer.EncryptedEntry matchedEntry;
matchedEntry = null;
// find closest path which matches with database entry
for (com.amaze.filemanager.database.models.explorer.EncryptedEntry encryptedEntry : handler.getAllEntries()) {
if (path.contains(encryptedEntry.getPath())) {
if ((matchedEntry == null) || (matchedEntry.getPath().length() < encryptedEntry.getPath().length())) {
matchedEntry = encryptedEntry;
}
}
}
return matchedEntry;
}


public interface EncryptButtonCallbackInterface {
/**
 * Callback fired when user has entered a password for encryption Not called when we've a master
 * password set or enable fingerprint authentication
 *
 * @param password
 * 		the password entered by user
 */
default void onButtonPressed(@androidx.annotation.NonNull
android.content.Intent intent, @androidx.annotation.NonNull
java.lang.String password) throws java.security.GeneralSecurityException, java.io.IOException {
}

}

public interface DecryptButtonCallbackInterface {
/**
 * Callback fired when we've confirmed the password matches the database
 */
default void confirm(@androidx.annotation.NonNull
android.content.Intent intent) {
}


/**
 * Callback fired when password doesn't match the value entered by user
 */
default void failed() {
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
