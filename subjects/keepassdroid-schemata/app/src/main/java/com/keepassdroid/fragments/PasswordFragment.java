/* Copyright 2020-2022 Brian Pellin

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
import com.keepassdroid.AboutDialog;
import com.keepassdroid.app.App;
import com.keepassdroid.utils.UriUtil;
import com.keepassdroid.utils.Util;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.widget.TextView;
import com.keepassdroid.biometric.BiometricHelper;
import com.keepassdroid.utils.PermissionUtil;
import com.keepassdroid.Database;
import android.view.Menu;
import android.view.MenuInflater;
import com.keepassdroid.utils.EmptyUtils;
import com.keepassdroid.utils.Interaction;
import android.os.Handler;
import com.keepassdroid.database.edit.OnFinish;
import android.widget.EditText;
import android.widget.CheckBox;
import android.view.LayoutInflater;
import android.text.InputType;
import android.content.ActivityNotFoundException;
import com.keepassdroid.fileselect.BrowserDialog;
import java.io.File;
import androidx.annotation.Nullable;
import com.keepassdroid.dialog.PasswordEncodingDialogHelper;
import android.content.DialogInterface;
import androidx.biometric.BiometricPrompt;
import com.keepassdroid.settings.AppSettingsActivity;
import com.keepassdroid.compat.ClipDataCompat;
import android.net.Uri;
import com.keepassdroid.GroupActivity;
import android.widget.ImageButton;
import com.android.keepass.R;
import java.util.concurrent.Executor;
import com.keepassdroid.intents.Intents;
import androidx.biometric.BiometricManager;
import com.keepassdroid.compat.StorageAF;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.android.keepass.KeePass;
import android.content.SharedPreferences;
import com.keepassdroid.PasswordActivity;
import com.keepassdroid.ProgressTask;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import android.app.backup.BackupManager;
import android.content.Intent;
import android.view.MenuItem;
import android.preference.PreferenceManager;
import android.view.View;
import javax.crypto.Cipher;
import android.widget.CompoundButton;
import android.content.Context;
import com.keepassdroid.database.edit.LoadDB;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordFragment extends androidx.fragment.app.Fragment implements com.keepassdroid.biometric.BiometricHelper.BiometricCallback {
    static final int MUID_STATIC = getMUID();
    private static final int FILE_BROWSE = 256;

    public static final int GET_CONTENT = 257;

    private static final int OPEN_DOC = 258;

    private static final java.lang.String KEY_PASSWORD = "password";

    private static final java.lang.String KEY_LAUNCH_IMMEDIATELY = "launchImmediately";

    private static final java.lang.String VIEW_INTENT = "android.intent.action.VIEW";

    private static final int PERMISSION_REQUEST_ID = 1;

    private static final int BIOMETRIC_SAVE = 1;

    private static final int BIOMETRIC_LOAD = 2;

    private android.net.Uri mDbUri = null;

    private android.net.Uri mKeyUri = null;

    private boolean mRememberKeyfile;

    android.content.SharedPreferences prefs;

    android.content.SharedPreferences prefsNoBackup;

    private android.net.Uri storedKeyUri = null;

    private java.lang.String storedPassword = null;

    private int mode;

    private static final java.lang.String PREF_KEY_VALUE_PREFIX = "valueFor_";// key is a combination of db file name and this prefix


    private static final java.lang.String PREF_KEY_IV_PREFIX = "ivFor_";// key is a combination of db file name and this prefix


    private android.view.View mView;

    private android.widget.CheckBox biometricCheck;

    private android.widget.EditText passwordView;

    private android.widget.Button biometricOpen;

    private android.widget.Button biometricClear;

    private android.view.View divider3;

    private android.widget.Button confirmButton;

    private boolean biometricsAvailable = false;

    private androidx.biometric.BiometricPrompt biometricSavePrompt;

    private androidx.biometric.BiometricPrompt biometricOpenPrompt;

    private androidx.biometric.BiometricPrompt.PromptInfo savePrompt;

    private androidx.biometric.BiometricPrompt.PromptInfo loadPrompt;

    private com.keepassdroid.biometric.BiometricHelper biometricHelper;

    private int biometricMode = 0;

    private androidx.appcompat.app.AppCompatActivity mActivity;

    private boolean afterOnCreateBeforeEndOfOnResume = false;

    @java.lang.Override
    public void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PasswordFragment_0_LengthyGUICreationOperatorMutator
            case 57: {
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
    setHasOptionsMenu(true);
    setRetainInstance(true);
    afterOnCreateBeforeEndOfOnResume = true;
}


@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, @androidx.annotation.Nullable
android.view.ViewGroup container, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    mView = inflater.inflate(com.android.keepass.R.layout.password, container, false);
    return mView;
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    android.content.Context context;
    context = getContext();
    prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(mActivity);
    prefsNoBackup = mActivity.getSharedPreferences("nobackup", android.content.Context.MODE_PRIVATE);
    mRememberKeyfile = prefs.getBoolean(getString(com.android.keepass.R.string.keyfile_key), getResources().getBoolean(com.android.keepass.R.bool.keyfile_default));
    switch(MUID_STATIC) {
        // PasswordFragment_1_InvalidViewFocusOperatorMutator
        case 1057: {
            /**
            * Inserted by Kadabra
            */
            confirmButton = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.pass_ok)));
            confirmButton.requestFocus();
            break;
        }
        // PasswordFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2057: {
            /**
            * Inserted by Kadabra
            */
            confirmButton = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.pass_ok)));
            confirmButton.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        confirmButton = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.pass_ok)));
        break;
    }
}
switch(MUID_STATIC) {
    // PasswordFragment_3_InvalidViewFocusOperatorMutator
    case 3057: {
        /**
        * Inserted by Kadabra
        */
        passwordView = ((android.widget.EditText) (view.findViewById(com.android.keepass.R.id.password)));
        passwordView.requestFocus();
        break;
    }
    // PasswordFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4057: {
        /**
        * Inserted by Kadabra
        */
        passwordView = ((android.widget.EditText) (view.findViewById(com.android.keepass.R.id.password)));
        passwordView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    passwordView = ((android.widget.EditText) (view.findViewById(com.android.keepass.R.id.password)));
    break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_5_InvalidViewFocusOperatorMutator
case 5057: {
    /**
    * Inserted by Kadabra
    */
    biometricOpen = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.open_biometric)));
    biometricOpen.requestFocus();
    break;
}
// PasswordFragment_6_ViewComponentNotVisibleOperatorMutator
case 6057: {
    /**
    * Inserted by Kadabra
    */
    biometricOpen = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.open_biometric)));
    biometricOpen.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
biometricOpen = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.open_biometric)));
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_7_InvalidViewFocusOperatorMutator
case 7057: {
/**
* Inserted by Kadabra
*/
biometricClear = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.clear_biometric)));
biometricClear.requestFocus();
break;
}
// PasswordFragment_8_ViewComponentNotVisibleOperatorMutator
case 8057: {
/**
* Inserted by Kadabra
*/
biometricClear = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.clear_biometric)));
biometricClear.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
biometricClear = ((android.widget.Button) (view.findViewById(com.android.keepass.R.id.clear_biometric)));
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_9_InvalidViewFocusOperatorMutator
case 9057: {
/**
* Inserted by Kadabra
*/
divider3 = view.findViewById(com.android.keepass.R.id.divider3);
divider3.requestFocus();
break;
}
// PasswordFragment_10_ViewComponentNotVisibleOperatorMutator
case 10057: {
/**
* Inserted by Kadabra
*/
divider3 = view.findViewById(com.android.keepass.R.id.divider3);
divider3.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
divider3 = view.findViewById(com.android.keepass.R.id.divider3);
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_11_InvalidViewFocusOperatorMutator
case 11057: {
/**
* Inserted by Kadabra
*/
biometricCheck = ((android.widget.CheckBox) (view.findViewById(com.android.keepass.R.id.save_password)));
biometricCheck.requestFocus();
break;
}
// PasswordFragment_12_ViewComponentNotVisibleOperatorMutator
case 12057: {
/**
* Inserted by Kadabra
*/
biometricCheck = ((android.widget.CheckBox) (view.findViewById(com.android.keepass.R.id.save_password)));
biometricCheck.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
biometricCheck = ((android.widget.CheckBox) (view.findViewById(com.android.keepass.R.id.save_password)));
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_13_BuggyGUIListenerOperatorMutator
case 13057: {
biometricOpen.setOnClickListener(null);
break;
}
default: {
biometricOpen.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// PasswordFragment_14_LengthyGUIListenerOperatorMutator
case 14057: {
/**
* Inserted by Kadabra
*/
biometricLogin();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
biometricLogin();
break;
}
}
}

});
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_15_BuggyGUIListenerOperatorMutator
case 15057: {
biometricClear.setOnClickListener(null);
break;
}
default: {
biometricClear.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// PasswordFragment_16_LengthyGUIListenerOperatorMutator
case 16057: {
/**
* Inserted by Kadabra
*/
clearStoredCredentials();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
clearStoredCredentials();
break;
}
}
}

});
break;
}
}
}


private void biometricLogin() {
if (!initDecryptData()) {
return;
}
biometricCheck.setChecked(false);
javax.crypto.Cipher cipher;
cipher = biometricHelper.getCipher();
biometricMode = com.keepassdroid.fragments.PasswordFragment.BIOMETRIC_LOAD;
biometricOpenPrompt.authenticate(loadPrompt, new androidx.biometric.BiometricPrompt.CryptoObject(cipher));
}


@java.lang.Override
public void onCreateOptionsMenu(@androidx.annotation.NonNull
android.view.Menu menu, @androidx.annotation.NonNull
android.view.MenuInflater inflater) {
super.onCreateOptionsMenu(menu, inflater);
android.view.MenuInflater inflate;
inflate = mActivity.getMenuInflater();
inflate.inflate(com.android.keepass.R.menu.password, menu);
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
android.content.Context context;
context = getContext();
switch (item.getItemId()) {
case com.android.keepass.R.id.menu_about :
com.keepassdroid.AboutDialog dialog;
dialog = new com.keepassdroid.AboutDialog(context);
dialog.show();
return true;
case com.android.keepass.R.id.menu_app_settings :
com.keepassdroid.settings.AppSettingsActivity.Launch(context);
return true;
}
return super.onContextItemSelected(item);
}


@java.lang.Override
public void onAttach(@androidx.annotation.NonNull
android.content.Context context) {
super.onAttach(context);
if (context instanceof androidx.appcompat.app.AppCompatActivity) {
mActivity = ((androidx.appcompat.app.AppCompatActivity) (context));
}
}


@java.lang.Override
public void onDetach() {
super.onDetach();
mActivity = null;
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
mActivity = null;
}


private void setFingerPrintVisibilty() {
if (biometricsAvailable) {
biometricCheck.setVisibility(android.view.View.VISIBLE);
} else {
biometricCheck.setVisibility(android.view.View.GONE);
}
biometricOpenUpdateVisibility();
}


private void biometricOpenUpdateVisibility() {
int visibility;
boolean autoOpen;
autoOpen = false;
androidx.biometric.BiometricManager biometricManager;
biometricManager = androidx.biometric.BiometricManager.from(mActivity);
int auth;
auth = biometricManager.canAuthenticate();
if (biometricsAvailable && (auth != androidx.biometric.BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED)) {
java.lang.String encryptedValue;
encryptedValue = prefsNoBackup.getString(getPreferenceKeyValue(), null);
java.lang.String ivSpecValue;
ivSpecValue = prefsNoBackup.getString(getPreferenceKeyIvSpec(), null);
boolean hasStoredKey;
hasStoredKey = (encryptedValue != null) && (ivSpecValue != null);
if (hasStoredKey) {
// Check key value
visibility = android.view.View.VISIBLE;
autoOpen = prefs.getBoolean(getString(com.android.keepass.R.string.biometric_autoscan_key), getResources().getBoolean(com.android.keepass.R.bool.biometric_autoscan)) && afterOnCreateBeforeEndOfOnResume;
} else {
visibility = android.view.View.GONE;
}
} else {
visibility = android.view.View.GONE;
}
biometricOpen.setVisibility(visibility);
biometricClear.setVisibility(visibility);
divider3.setVisibility(visibility);
if (autoOpen) {
biometricLogin();
}
}


private void initBiometrics() {
final android.content.Context context;
context = getContext();
biometricsAvailable = true;
biometricHelper = new com.keepassdroid.biometric.BiometricHelper(context, this);
java.util.concurrent.Executor executor;
executor = androidx.core.content.ContextCompat.getMainExecutor(context);
androidx.biometric.BiometricPrompt.AuthenticationCallback biometricCallback;
biometricCallback = new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
@java.lang.Override
public void onAuthenticationFailed() {
super.onAuthenticationFailed();
if (biometricMode == com.keepassdroid.fragments.PasswordFragment.BIOMETRIC_SAVE) {
android.widget.Toast.makeText(context, com.android.keepass.R.string.biometric_auth_failed_store, android.widget.Toast.LENGTH_LONG).show();
com.keepassdroid.GroupActivity.Launch(mActivity);
} else if (biometricMode == com.keepassdroid.fragments.PasswordFragment.BIOMETRIC_LOAD) {
android.widget.Toast.makeText(context, com.android.keepass.R.string.biometric_auth_failed, android.widget.Toast.LENGTH_LONG).show();
}
}


@java.lang.Override
public void onAuthenticationSucceeded(@androidx.annotation.NonNull
androidx.biometric.BiometricPrompt.AuthenticationResult result) {
super.onAuthenticationSucceeded(result);
if (biometricMode == com.keepassdroid.fragments.PasswordFragment.BIOMETRIC_SAVE) {
// newly store the entered password in encrypted way
final java.lang.String password;
password = passwordView.getText().toString();
biometricHelper.encryptData(password);
com.keepassdroid.GroupActivity.Launch(mActivity);
passwordView.setText("");
} else if (biometricMode == com.keepassdroid.fragments.PasswordFragment.BIOMETRIC_LOAD) {
// retrieve the encrypted value from preferences
final java.lang.String encryptedValue;
encryptedValue = prefsNoBackup.getString(getPreferenceKeyValue(), null);
if (encryptedValue != null) {
biometricHelper.decryptData(encryptedValue);
}
}
}


@java.lang.Override
public void onAuthenticationError(int errorCode, @androidx.annotation.NonNull
java.lang.CharSequence errString) {
super.onAuthenticationError(errorCode, errString);
if (!canceledBiometricAuth(errorCode)) {
android.widget.Toast.makeText(context, com.android.keepass.R.string.biometric_auth_error, android.widget.Toast.LENGTH_LONG).show();
}
if (biometricMode == com.keepassdroid.fragments.PasswordFragment.BIOMETRIC_SAVE) {
com.keepassdroid.GroupActivity.Launch(mActivity);
}
}

};
biometricSavePrompt = new androidx.biometric.BiometricPrompt(this, executor, biometricCallback);
androidx.biometric.BiometricPrompt.PromptInfo.Builder saveBuilder;
saveBuilder = new androidx.biometric.BiometricPrompt.PromptInfo.Builder();
savePrompt = saveBuilder.setDescription(getString(com.android.keepass.R.string.biometric_auth_to_store)).setConfirmationRequired(false).setTitle(getString(com.android.keepass.R.string.biometric_save_password)).setNegativeButtonText(getString(android.R.string.cancel)).build();
biometricOpenPrompt = new androidx.biometric.BiometricPrompt(this, executor, biometricCallback);
androidx.biometric.BiometricPrompt.PromptInfo.Builder openBuilder;
openBuilder = new androidx.biometric.BiometricPrompt.PromptInfo.Builder();
loadPrompt = openBuilder.setDescription(getString(com.android.keepass.R.string.biometric_auth_to_open)).setConfirmationRequired(false).setTitle(getString(com.android.keepass.R.string.biometric_open_db)).setNegativeButtonText(getString(android.R.string.cancel)).build();
setFingerPrintVisibilty();
}


private boolean canceledBiometricAuth(int errorCode) {
switch (errorCode) {
case androidx.biometric.BiometricPrompt.ERROR_CANCELED :
case androidx.biometric.BiometricPrompt.ERROR_USER_CANCELED :
case androidx.biometric.BiometricPrompt.ERROR_NEGATIVE_BUTTON :
return true;
default :
return false;
}
}


private boolean initDecryptData() {
final java.lang.String ivSpecValue;
ivSpecValue = prefsNoBackup.getString(getPreferenceKeyIvSpec(), null);
return biometricHelper.initDecryptData(ivSpecValue);
}


@java.lang.Override
public void onActivityCreated(@androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
super.onActivityCreated(savedInstanceState);
android.content.Intent i;
switch(MUID_STATIC) {
// PasswordFragment_17_RandomActionIntentDefinitionOperatorMutator
case 17057: {
i = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i = mActivity.getIntent();
break;
}
}
com.keepassdroid.fragments.PasswordFragment.InitTask task;
task = new com.keepassdroid.fragments.PasswordFragment.InitTask();
task.onPostExecute(task.doInBackground(i));
}


@java.lang.Override
public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable
android.content.Intent data) {
super.onActivityResult(requestCode, resultCode, data);
android.app.Activity activity;
activity = mActivity;
switch (requestCode) {
case com.android.keepass.KeePass.EXIT_NORMAL :
setEditText(com.android.keepass.R.id.password, "");
com.keepassdroid.app.App.getDB().clear(activity.getApplicationContext());
break;
case com.android.keepass.KeePass.EXIT_LOCK :
activity.setResult(com.android.keepass.KeePass.EXIT_LOCK);
setEditText(com.android.keepass.R.id.password, "");
activity.finish();
com.keepassdroid.app.App.getDB().clear(activity.getApplicationContext());
break;
case com.keepassdroid.fragments.PasswordFragment.FILE_BROWSE :
if (resultCode == android.app.Activity.RESULT_OK) {
java.lang.String filename;
filename = data.getDataString();
if (filename != null) {
android.widget.EditText fn;
switch(MUID_STATIC) {
// PasswordFragment_18_InvalidViewFocusOperatorMutator
case 18057: {
/**
* Inserted by Kadabra
*/
fn = ((android.widget.EditText) (mView.findViewById(com.android.keepass.R.id.pass_keyfile)));
fn.requestFocus();
break;
}
// PasswordFragment_19_ViewComponentNotVisibleOperatorMutator
case 19057: {
/**
* Inserted by Kadabra
*/
fn = ((android.widget.EditText) (mView.findViewById(com.android.keepass.R.id.pass_keyfile)));
fn.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
fn = ((android.widget.EditText) (mView.findViewById(com.android.keepass.R.id.pass_keyfile)));
break;
}
}
fn.setText(filename);
mKeyUri = com.keepassdroid.utils.UriUtil.parseDefaultFile(filename);
}
}
break;
case com.keepassdroid.fragments.PasswordFragment.GET_CONTENT :
case com.keepassdroid.fragments.PasswordFragment.OPEN_DOC :
if (resultCode == android.app.Activity.RESULT_OK) {
if (data != null) {
android.net.Uri uri;
uri = data.getData();
if (uri != null) {
if (requestCode == com.keepassdroid.fragments.PasswordFragment.GET_CONTENT) {
uri = com.keepassdroid.utils.UriUtil.translate(activity, uri);
}
java.lang.String path;
path = uri.toString();
if (path != null) {
android.widget.EditText fn;
switch(MUID_STATIC) {
// PasswordFragment_20_InvalidViewFocusOperatorMutator
case 20057: {
/**
* Inserted by Kadabra
*/
fn = ((android.widget.EditText) (mView.findViewById(com.android.keepass.R.id.pass_keyfile)));
fn.requestFocus();
break;
}
// PasswordFragment_21_ViewComponentNotVisibleOperatorMutator
case 21057: {
/**
* Inserted by Kadabra
*/
fn = ((android.widget.EditText) (mView.findViewById(com.android.keepass.R.id.pass_keyfile)));
fn.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
fn = ((android.widget.EditText) (mView.findViewById(com.android.keepass.R.id.pass_keyfile)));
break;
}
}
fn.setText(path);
}
mKeyUri = uri;
}
}
}
break;
}
}


@java.lang.Override
public void onResume() {
super.onResume();
// If the application was shutdown make sure to clear the password field, if it
// was saved in the instance state
if (com.keepassdroid.app.App.isShutdown()) {
android.widget.TextView password;
switch(MUID_STATIC) {
// PasswordFragment_22_InvalidViewFocusOperatorMutator
case 22057: {
/**
* Inserted by Kadabra
*/
password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
password.requestFocus();
break;
}
// PasswordFragment_23_ViewComponentNotVisibleOperatorMutator
case 23057: {
/**
* Inserted by Kadabra
*/
password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
password.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
break;
}
}
password.setText("");
}
// Clear the shutdown flag
com.keepassdroid.app.App.clearShutdown();
androidx.biometric.BiometricManager biometricManager;
biometricManager = androidx.biometric.BiometricManager.from(mActivity);
int auth;
auth = biometricManager.canAuthenticate();
if (auth == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) {
initBiometrics();
} else {
biometricsAvailable = false;
setFingerPrintVisibilty();
}
afterOnCreateBeforeEndOfOnResume = false;
}


@java.lang.Override
public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull
java.lang.String[] permissions, @androidx.annotation.NonNull
int[] grantResults) {
super.onRequestPermissionsResult(requestCode, permissions, grantResults);
if (((requestCode == com.keepassdroid.fragments.PasswordFragment.PERMISSION_REQUEST_ID) && (grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED)) && (grantResults[1] == android.content.pm.PackageManager.PERMISSION_GRANTED)) {
loadDatabaseWithPermission();
} else {
errorMessage(com.android.keepass.R.string.no_external_permissions);
}
}


private java.lang.String getPreferenceKeyValue() {
// makes it possible to store passwords uniqly per database
return com.keepassdroid.fragments.PasswordFragment.PREF_KEY_VALUE_PREFIX + (mDbUri != null ? mDbUri.getPath() : "");
}


private java.lang.String getPreferenceKeyIvSpec() {
return com.keepassdroid.fragments.PasswordFragment.PREF_KEY_IV_PREFIX + (mDbUri != null ? mDbUri.getPath() : "");
}


// Moved this to the foreground TOOD: Move this to a more typical pattern
private class InitTask {
java.lang.String password = "";

boolean launch_immediately = false;

public java.lang.Integer doInBackground(android.content.Intent... args) {
android.content.Intent i;
switch(MUID_STATIC) {
// PasswordFragment_24_RandomActionIntentDefinitionOperatorMutator
case 24057: {
i = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i = args[0];
break;
}
}
java.lang.String action;
action = i.getAction();
if ((action != null) && action.equals(com.keepassdroid.fragments.PasswordFragment.VIEW_INTENT)) {
android.net.Uri incoming;
incoming = i.getData();
mDbUri = incoming;
mKeyUri = com.keepassdroid.compat.ClipDataCompat.getUriFromIntent(i, com.keepassdroid.PasswordActivity.KEY_KEYFILE);
if (incoming == null) {
return com.android.keepass.R.string.error_can_not_handle_uri;
} else if (incoming.getScheme().equals("file")) {
java.lang.String fileName;
fileName = incoming.getPath();
if (fileName.length() == 0) {
// No file name
return com.android.keepass.R.string.FileNotFound;
}
java.io.File dbFile;
dbFile = new java.io.File(fileName);
if (!dbFile.exists()) {
// File does not exist
return com.android.keepass.R.string.FileNotFound;
}
if (mKeyUri == null) {
mKeyUri = getKeyFile(mDbUri);
}
} else if (incoming.getScheme().equals("content")) {
if (mKeyUri == null) {
mKeyUri = getKeyFile(mDbUri);
}
} else {
return com.android.keepass.R.string.error_can_not_handle_uri;
}
password = i.getStringExtra(com.keepassdroid.fragments.PasswordFragment.KEY_PASSWORD);
launch_immediately = i.getBooleanExtra(com.keepassdroid.fragments.PasswordFragment.KEY_LAUNCH_IMMEDIATELY, false);
} else {
mDbUri = com.keepassdroid.utils.UriUtil.parseDefaultFile(i.getStringExtra(com.keepassdroid.PasswordActivity.KEY_FILENAME));
mKeyUri = com.keepassdroid.utils.UriUtil.parseDefaultFile(i.getStringExtra(com.keepassdroid.PasswordActivity.KEY_KEYFILE));
password = i.getStringExtra(com.keepassdroid.fragments.PasswordFragment.KEY_PASSWORD);
launch_immediately = i.getBooleanExtra(com.keepassdroid.fragments.PasswordFragment.KEY_LAUNCH_IMMEDIATELY, false);
if ((mKeyUri == null) || (mKeyUri.toString().length() == 0)) {
mKeyUri = getKeyFile(mDbUri);
}
}
biometricOpenUpdateVisibility();
return null;
}


public void onPostExecute(java.lang.Integer result) {
if (result != null) {
android.widget.Toast.makeText(mActivity, result, android.widget.Toast.LENGTH_LONG).show();
mActivity.finish();
return;
}
populateView();
confirmButton.setOnClickListener(new com.keepassdroid.fragments.PasswordFragment.OkClickHandler());
android.widget.CheckBox checkBox;
switch(MUID_STATIC) {
// PasswordFragment_25_InvalidViewFocusOperatorMutator
case 25057: {
/**
* Inserted by Kadabra
*/
checkBox = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.show_password)));
checkBox.requestFocus();
break;
}
// PasswordFragment_26_ViewComponentNotVisibleOperatorMutator
case 26057: {
/**
* Inserted by Kadabra
*/
checkBox = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.show_password)));
checkBox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkBox = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.show_password)));
break;
}
}
// Show or hide password
checkBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
android.widget.TextView password;
switch(MUID_STATIC) {
// PasswordFragment_27_InvalidViewFocusOperatorMutator
case 27057: {
/**
* Inserted by Kadabra
*/
password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
password.requestFocus();
break;
}
// PasswordFragment_28_ViewComponentNotVisibleOperatorMutator
case 28057: {
/**
* Inserted by Kadabra
*/
password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
password.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
break;
}
}
if (isChecked) {
password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
} else {
password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
}
}

});
if (password != null) {
android.widget.TextView tv_password;
switch(MUID_STATIC) {
// PasswordFragment_29_InvalidViewFocusOperatorMutator
case 29057: {
/**
* Inserted by Kadabra
*/
tv_password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
tv_password.requestFocus();
break;
}
// PasswordFragment_30_ViewComponentNotVisibleOperatorMutator
case 30057: {
/**
* Inserted by Kadabra
*/
tv_password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
tv_password.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
tv_password = ((android.widget.TextView) (mView.findViewById(com.android.keepass.R.id.password)));
break;
}
}
tv_password.setText(password);
}
android.widget.CheckBox defaultCheck;
switch(MUID_STATIC) {
// PasswordFragment_31_InvalidViewFocusOperatorMutator
case 31057: {
/**
* Inserted by Kadabra
*/
defaultCheck = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.default_database)));
defaultCheck.requestFocus();
break;
}
// PasswordFragment_32_ViewComponentNotVisibleOperatorMutator
case 32057: {
/**
* Inserted by Kadabra
*/
defaultCheck = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.default_database)));
defaultCheck.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
defaultCheck = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.default_database)));
break;
}
}
defaultCheck.setOnCheckedChangeListener(new com.keepassdroid.fragments.PasswordFragment.DefaultCheckChange());
android.widget.ImageButton browse;
switch(MUID_STATIC) {
// PasswordFragment_33_InvalidViewFocusOperatorMutator
case 33057: {
/**
* Inserted by Kadabra
*/
browse = ((android.widget.ImageButton) (mView.findViewById(com.android.keepass.R.id.browse_button)));
browse.requestFocus();
break;
}
// PasswordFragment_34_ViewComponentNotVisibleOperatorMutator
case 34057: {
/**
* Inserted by Kadabra
*/
browse = ((android.widget.ImageButton) (mView.findViewById(com.android.keepass.R.id.browse_button)));
browse.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
browse = ((android.widget.ImageButton) (mView.findViewById(com.android.keepass.R.id.browse_button)));
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_35_BuggyGUIListenerOperatorMutator
case 35057: {
browse.setOnClickListener(null);
break;
}
default: {
browse.setOnClickListener(new android.view.View.OnClickListener() {
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// PasswordFragment_36_LengthyGUIListenerOperatorMutator
case 36057: {
/**
* Inserted by Kadabra
*/
if (com.keepassdroid.compat.StorageAF.useStorageFramework(mActivity)) {
android.content.Intent i;
i = new android.content.Intent(com.keepassdroid.compat.StorageAF.ACTION_OPEN_DOCUMENT);
i.addCategory(android.content.Intent.CATEGORY_OPENABLE);
i.setType("*/*");
startActivityForResult(i, com.keepassdroid.fragments.PasswordFragment.OPEN_DOC);
} else {
android.content.Intent i;
i = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
i.addCategory(android.content.Intent.CATEGORY_OPENABLE);
i.setType("*/*");
try {
startActivityForResult(i, com.keepassdroid.fragments.PasswordFragment.GET_CONTENT);
} catch (android.content.ActivityNotFoundException e) {
lookForOpenIntentsFilePicker();
}
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (com.keepassdroid.compat.StorageAF.useStorageFramework(mActivity)) {
android.content.Intent i;
switch(MUID_STATIC) {
// PasswordFragment_37_InvalidKeyIntentOperatorMutator
case 37057: {
i = new android.content.Intent((String) null);
break;
}
// PasswordFragment_38_RandomActionIntentDefinitionOperatorMutator
case 38057: {
i = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i = new android.content.Intent(com.keepassdroid.compat.StorageAF.ACTION_OPEN_DOCUMENT);
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_39_RandomActionIntentDefinitionOperatorMutator
case 39057: {
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
i.addCategory(android.content.Intent.CATEGORY_OPENABLE);
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_40_RandomActionIntentDefinitionOperatorMutator
case 40057: {
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
i.setType("*/*");
break;
}
}
startActivityForResult(i, com.keepassdroid.fragments.PasswordFragment.OPEN_DOC);
} else {
android.content.Intent i;
switch(MUID_STATIC) {
// PasswordFragment_41_InvalidKeyIntentOperatorMutator
case 41057: {
i = new android.content.Intent((String) null);
break;
}
// PasswordFragment_42_RandomActionIntentDefinitionOperatorMutator
case 42057: {
i = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_43_RandomActionIntentDefinitionOperatorMutator
case 43057: {
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
i.addCategory(android.content.Intent.CATEGORY_OPENABLE);
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_44_RandomActionIntentDefinitionOperatorMutator
case 44057: {
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
i.setType("*/*");
break;
}
}
try {
startActivityForResult(i, com.keepassdroid.fragments.PasswordFragment.GET_CONTENT);
} catch (android.content.ActivityNotFoundException e) {
lookForOpenIntentsFilePicker();
}
}
break;
}
}
}


private void lookForOpenIntentsFilePicker() {
switch(MUID_STATIC) {
// PasswordFragment_45_LengthyGUIListenerOperatorMutator
case 45057: {
/**
* Inserted by Kadabra
*/
if (com.keepassdroid.utils.Interaction.isIntentAvailable(mActivity, com.keepassdroid.intents.Intents.OPEN_INTENTS_FILE_BROWSE)) {
android.content.Intent i;
i = new android.content.Intent(com.keepassdroid.intents.Intents.OPEN_INTENTS_FILE_BROWSE);
// Get file path parent if possible
try {
if ((mDbUri != null) && (mDbUri.toString().length() > 0)) {
if (mDbUri.getScheme().equals("file")) {
java.io.File keyfile;
keyfile = new java.io.File(mDbUri.getPath());
java.io.File parent;
parent = keyfile.getParentFile();
if (parent != null) {
i.setData(android.net.Uri.parse("file://" + parent.getAbsolutePath()));
}
}
}
} catch (java.lang.Exception e) {
// Ignore
}
try {
startActivityForResult(i, com.keepassdroid.fragments.PasswordFragment.FILE_BROWSE);
} catch (android.content.ActivityNotFoundException e) {
showBrowserDialog();
}
} else {
showBrowserDialog();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (com.keepassdroid.utils.Interaction.isIntentAvailable(mActivity, com.keepassdroid.intents.Intents.OPEN_INTENTS_FILE_BROWSE)) {
android.content.Intent i;
switch(MUID_STATIC) {
// PasswordFragment_46_InvalidKeyIntentOperatorMutator
case 46057: {
i = new android.content.Intent((String) null);
break;
}
// PasswordFragment_47_RandomActionIntentDefinitionOperatorMutator
case 47057: {
i = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i = new android.content.Intent(com.keepassdroid.intents.Intents.OPEN_INTENTS_FILE_BROWSE);
break;
}
}
switch(MUID_STATIC) {
// PasswordFragment_48_RandomActionIntentDefinitionOperatorMutator
case 48057: {
// Get file path parent if possible
try {
if ((mDbUri != null) && (mDbUri.toString().length() > 0)) {
if (mDbUri.getScheme().equals("file")) {
java.io.File keyfile;
keyfile = new java.io.File(mDbUri.getPath());
java.io.File parent;
parent = keyfile.getParentFile();
if (parent != null) {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);;
}
}
}
} catch (java.lang.Exception e) {
// Ignore
}
break;
}
default: {
// Get file path parent if possible
try {
if ((mDbUri != null) && (mDbUri.toString().length() > 0)) {
if (mDbUri.getScheme().equals("file")) {
java.io.File keyfile;
keyfile = new java.io.File(mDbUri.getPath());
java.io.File parent;
parent = keyfile.getParentFile();
if (parent != null) {
i.setData(android.net.Uri.parse("file://" + parent.getAbsolutePath()));
}
}
}
} catch (java.lang.Exception e) {
// Ignore
}
break;
}
}
try {
startActivityForResult(i, com.keepassdroid.fragments.PasswordFragment.FILE_BROWSE);
} catch (android.content.ActivityNotFoundException e) {
showBrowserDialog();
}
} else {
showBrowserDialog();
}
break;
}
}
}


private void showBrowserDialog() {
com.keepassdroid.fileselect.BrowserDialog diag;
diag = new com.keepassdroid.fileselect.BrowserDialog(mActivity);
switch(MUID_STATIC) {
// PasswordFragment_49_LengthyGUIListenerOperatorMutator
case 49057: {
/**
* Inserted by Kadabra
*/
diag.show();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
diag.show();
break;
}
}
}

});
break;
}
}
retrieveSettings();
if (launch_immediately) {
loadDatabase(password, mKeyUri);
}
}

}

private class DefaultCheckChange implements android.widget.CompoundButton.OnCheckedChangeListener {
@java.lang.Override
public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
java.lang.String newDefaultFileName;
if (isChecked) {
newDefaultFileName = mDbUri.toString();
} else {
newDefaultFileName = "";
}
android.content.SharedPreferences.Editor editor;
editor = prefs.edit();
editor.putString(com.keepassdroid.PasswordActivity.KEY_DEFAULT_FILENAME, newDefaultFileName);
editor.apply();
android.app.backup.BackupManager backupManager;
backupManager = new android.app.backup.BackupManager(getContext());
backupManager.dataChanged();
}

}

private void retrieveSettings() {
java.lang.String defaultFilename;
defaultFilename = prefs.getString(com.keepassdroid.PasswordActivity.KEY_DEFAULT_FILENAME, "");
if ((!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(mDbUri.getPath())) && com.keepassdroid.utils.UriUtil.equalsDefaultfile(mDbUri, defaultFilename)) {
android.widget.CheckBox checkbox;
switch(MUID_STATIC) {
// PasswordFragment_50_InvalidViewFocusOperatorMutator
case 50057: {
/**
* Inserted by Kadabra
*/
checkbox = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.default_database)));
checkbox.requestFocus();
break;
}
// PasswordFragment_51_ViewComponentNotVisibleOperatorMutator
case 51057: {
/**
* Inserted by Kadabra
*/
checkbox = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.default_database)));
checkbox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkbox = ((android.widget.CheckBox) (mView.findViewById(com.android.keepass.R.id.default_database)));
break;
}
}
checkbox.setChecked(true);
}
}


private android.net.Uri getKeyFile(android.net.Uri dbUri) {
if (mRememberKeyfile) {
return com.keepassdroid.app.App.getFileHistory().getFileByName(dbUri);
} else {
return null;
}
}


private void populateView() {
java.lang.String db;
db = (mDbUri == null) ? "" : mDbUri.toString();
setEditText(com.android.keepass.R.id.filename, db);
java.lang.String displayName;
displayName = com.keepassdroid.utils.UriUtil.getFileName(mDbUri, getContext());
android.widget.TextView displayNameView;
switch(MUID_STATIC) {
// PasswordFragment_52_InvalidViewFocusOperatorMutator
case 52057: {
/**
* Inserted by Kadabra
*/
displayNameView = mView.findViewById(com.android.keepass.R.id.filename_display);
displayNameView.requestFocus();
break;
}
// PasswordFragment_53_ViewComponentNotVisibleOperatorMutator
case 53057: {
/**
* Inserted by Kadabra
*/
displayNameView = mView.findViewById(com.android.keepass.R.id.filename_display);
displayNameView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
displayNameView = mView.findViewById(com.android.keepass.R.id.filename_display);
break;
}
}
if (displayNameView != null) {
if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(displayName)) {
displayNameView.setVisibility(android.view.View.GONE);
} else {
displayNameView.setText(com.keepassdroid.utils.UriUtil.getFileName(mDbUri, getContext()));
}
}
java.lang.String key;
key = (mKeyUri == null) ? "" : mKeyUri.toString();
setEditText(com.android.keepass.R.id.pass_keyfile, key);
}


private void errorMessage(int resId) {
android.widget.Toast.makeText(mActivity, resId, android.widget.Toast.LENGTH_LONG).show();
}


private void setEditText(int resId, java.lang.String str) {
android.widget.TextView te;
switch(MUID_STATIC) {
// PasswordFragment_54_InvalidViewFocusOperatorMutator
case 54057: {
/**
* Inserted by Kadabra
*/
te = ((android.widget.TextView) (mView.findViewById(resId)));
te.requestFocus();
break;
}
// PasswordFragment_55_ViewComponentNotVisibleOperatorMutator
case 55057: {
/**
* Inserted by Kadabra
*/
te = ((android.widget.TextView) (mView.findViewById(resId)));
te.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
te = ((android.widget.TextView) (mView.findViewById(resId)));
break;
}
}
assert te != null;
if (te != null) {
te.setText(str);
}
}


private void loadDatabase(java.lang.String pass, android.net.Uri keyfile) {
if ((pass.length() == 0) && ((keyfile == null) || (keyfile.toString().length() == 0))) {
errorMessage(com.android.keepass.R.string.error_nopass);
return;
}
storedPassword = pass;
storedKeyUri = keyfile;
if (checkFilePermissions(mDbUri, keyfile)) {
loadDatabaseWithPermission();
}
}


private void loadDatabaseWithPermission() {
java.lang.String pass;
pass = storedPassword;
storedPassword = null;
android.net.Uri keyfile;
keyfile = storedKeyUri;
storedKeyUri = null;
android.app.Activity activity;
activity = mActivity;
// Clear before we load
com.keepassdroid.Database db;
db = com.keepassdroid.app.App.getDB();
db.clear(activity.getApplicationContext());
// Clear the shutdown flag
com.keepassdroid.app.App.clearShutdown();
android.os.Handler handler;
handler = new android.os.Handler();
com.keepassdroid.database.edit.LoadDB task;
task = new com.keepassdroid.database.edit.LoadDB(db, activity, mDbUri, pass, keyfile, new com.keepassdroid.fragments.PasswordFragment.AfterLoad(handler, db));
com.keepassdroid.ProgressTask pt;
pt = new com.keepassdroid.ProgressTask(activity, task, com.android.keepass.R.string.loading_database);
pt.run();
}


private java.lang.String getEditText(int resId) {
return com.keepassdroid.utils.Util.getEditText(mActivity, resId);
}


private final class AfterLoad extends com.keepassdroid.database.edit.OnFinish {
private com.keepassdroid.Database db;

public AfterLoad(android.os.Handler handler, com.keepassdroid.Database db) {
super(handler);
this.db = db;
}


@java.lang.Override
public void run() {
if (db.passwordEncodingError) {
com.keepassdroid.dialog.PasswordEncodingDialogHelper dialog;
dialog = new com.keepassdroid.dialog.PasswordEncodingDialogHelper();
switch(MUID_STATIC) {
// PasswordFragment_56_BuggyGUIListenerOperatorMutator
case 56057: {
dialog.show(mActivity, null);
break;
}
default: {
dialog.show(mActivity, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialog, int which) {
switch(MUID_STATIC) {
// PasswordFragment_57_LengthyGUIListenerOperatorMutator
case 57057: {
/**
* Inserted by Kadabra
*/
com.keepassdroid.GroupActivity.Launch(mActivity);
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
com.keepassdroid.GroupActivity.Launch(mActivity);
break;
}
}
}

});
break;
}
}
} else if (mSuccess) {
if (biometricCheck.isChecked()) {
if (!biometricHelper.initEncryptData()) {
return;
}
javax.crypto.Cipher cipher;
cipher = biometricHelper.getCipher();
biometricMode = com.keepassdroid.fragments.PasswordFragment.BIOMETRIC_SAVE;
biometricSavePrompt.authenticate(savePrompt, new androidx.biometric.BiometricPrompt.CryptoObject(cipher));
} else {
passwordView.setText("");
// Check to see if the fragement detached before this finished
if (mActivity != null) {
com.keepassdroid.GroupActivity.Launch(mActivity);
}
}
} else {
displayMessage(mActivity);
}
}

}

private class OkClickHandler implements android.view.View.OnClickListener {
public void onClick(android.view.View view) {
java.lang.String pass;
pass = getEditText(com.android.keepass.R.id.password);
java.lang.String key;
key = getEditText(com.android.keepass.R.id.pass_keyfile);
loadDatabase(pass, key);
}

}

private void loadDatabase(java.lang.String pass, java.lang.String keyfile) {
loadDatabase(pass, com.keepassdroid.utils.UriUtil.parseDefaultFile(keyfile));
}


private boolean hasFileUri(android.net.Uri uri) {
try {
if (uri == null) {
return false;
}
return uri.getScheme().equalsIgnoreCase("file");
} catch (java.lang.Exception e) {
return false;
}
}


private void clearStoredCredentials() {
prefsNoBackup.edit().remove(getPreferenceKeyValue()).remove(getPreferenceKeyIvSpec()).commit();
setFingerPrintVisibilty();
}


@java.lang.Override
public void handleEncryptedResult(java.lang.String value, java.lang.String ivSpec) {
prefsNoBackup.edit().putString(getPreferenceKeyValue(), value).putString(getPreferenceKeyIvSpec(), ivSpec).commit();
// and remove visual input to reset UI
android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.encrypted_value_stored, android.widget.Toast.LENGTH_SHORT).show();
}


@java.lang.Override
public void handleDecryptedResult(java.lang.String value) {
// on decrypt enter it for the purchase/login action
passwordView.setText(value);
confirmButton.performClick();
}


@java.lang.Override
public void onInvalidKeyException() {
android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.fingerprint_invalid_key, android.widget.Toast.LENGTH_SHORT).show();
}


@java.lang.Override
public void onException() {
onException(true);
}


@java.lang.Override
public void onException(boolean showWarningMessage) {
if (showWarningMessage) {
onException(com.android.keepass.R.string.biometric_error);
}
}


@java.lang.Override
public void onException(java.lang.CharSequence message) {
android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
}


@java.lang.Override
public void onException(int resId) {
android.widget.Toast.makeText(getContext(), resId, android.widget.Toast.LENGTH_SHORT).show();
}


@java.lang.Override
public void onKeyInvalidated() {
clearStoredCredentials();
android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.biometric_invalidated, android.widget.Toast.LENGTH_LONG).show();
}


private boolean checkFilePermissions(android.net.Uri db, android.net.Uri keyfile) {
boolean hasFileUri;
hasFileUri = hasFileUri(db) || hasFileUri(keyfile);
if (!hasFileUri)
return true;

return com.keepassdroid.utils.PermissionUtil.checkAndRequest(this.mActivity, com.keepassdroid.fragments.PasswordFragment.PERMISSION_REQUEST_ID);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
