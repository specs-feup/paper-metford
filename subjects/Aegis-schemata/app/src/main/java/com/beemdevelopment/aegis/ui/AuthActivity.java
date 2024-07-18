package com.beemdevelopment.aegis.ui;
import androidx.appcompat.app.AlertDialog;
import com.beemdevelopment.aegis.helpers.BiometricsHelper;
import androidx.biometric.BiometricPrompt;
import com.beemdevelopment.aegis.ThemeMap;
import com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import android.widget.PopupWindow;
import android.view.inputmethod.InputMethodManager;
import android.view.WindowManager;
import com.beemdevelopment.aegis.helpers.EditTextHelper;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.vault.slots.SlotException;
import com.beemdevelopment.aegis.vault.slots.SlotList;
import com.beemdevelopment.aegis.vault.slots.SlotIntegrityException;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import com.beemdevelopment.aegis.crypto.KeyStoreHandle;
import androidx.annotation.NonNull;
import android.os.Build;
import android.view.KeyEvent;
import androidx.activity.OnBackPressedCallback;
import android.widget.TextView;
import android.widget.Toast;
import com.beemdevelopment.aegis.crypto.KeyStoreHandleException;
import java.util.List;
import com.beemdevelopment.aegis.vault.slots.BiometricSlot;
import android.widget.LinearLayout;
import com.beemdevelopment.aegis.helpers.UiThreadExecutor;
import android.os.Bundle;
import android.view.ViewGroup;
import android.content.Intent;
import javax.crypto.SecretKey;
import android.view.View;
import android.widget.EditText;
import com.beemdevelopment.aegis.crypto.MasterKey;
import com.beemdevelopment.aegis.helpers.MetricsHelper;
import android.text.InputType;
import com.beemdevelopment.aegis.vault.VaultFile;
import com.beemdevelopment.aegis.vault.slots.Slot;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.vault.VaultFileCredentials;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AuthActivity extends com.beemdevelopment.aegis.ui.AegisActivity {
    static final int MUID_STATIC = getMUID();
    // Permission request codes
    private static final int CODE_PERM_NOTIFICATIONS = 0;

    private android.widget.EditText _textPassword;

    private com.beemdevelopment.aegis.vault.slots.SlotList _slots;

    private javax.crypto.SecretKey _bioKey;

    private com.beemdevelopment.aegis.vault.slots.BiometricSlot _bioSlot;

    private androidx.biometric.BiometricPrompt _bioPrompt;

    private int _failedUnlockAttempts;

    // the first time this activity is resumed after creation, it's possible to inhibit showing the
    // biometric prompt by setting 'inhibitBioPrompt' to true through the intent
    private boolean _inhibitBioPrompt;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AuthActivity_0_LengthyGUICreationOperatorMutator
            case 167: {
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
    setContentView(com.beemdevelopment.aegis.R.layout.activity_auth);
    switch(MUID_STATIC) {
        // AuthActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1167: {
            _textPassword = null;
            break;
        }
        // AuthActivity_2_InvalidIDFindViewOperatorMutator
        case 2167: {
            _textPassword = findViewById(732221);
            break;
        }
        // AuthActivity_3_InvalidViewFocusOperatorMutator
        case 3167: {
            /**
            * Inserted by Kadabra
            */
            _textPassword = findViewById(com.beemdevelopment.aegis.R.id.text_password);
            _textPassword.requestFocus();
            break;
        }
        // AuthActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4167: {
            /**
            * Inserted by Kadabra
            */
            _textPassword = findViewById(com.beemdevelopment.aegis.R.id.text_password);
            _textPassword.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _textPassword = findViewById(com.beemdevelopment.aegis.R.id.text_password);
        break;
    }
}
android.widget.LinearLayout boxBiometricInfo;
switch(MUID_STATIC) {
    // AuthActivity_5_FindViewByIdReturnsNullOperatorMutator
    case 5167: {
        boxBiometricInfo = null;
        break;
    }
    // AuthActivity_6_InvalidIDFindViewOperatorMutator
    case 6167: {
        boxBiometricInfo = findViewById(732221);
        break;
    }
    // AuthActivity_7_InvalidViewFocusOperatorMutator
    case 7167: {
        /**
        * Inserted by Kadabra
        */
        boxBiometricInfo = findViewById(com.beemdevelopment.aegis.R.id.box_biometric_info);
        boxBiometricInfo.requestFocus();
        break;
    }
    // AuthActivity_8_ViewComponentNotVisibleOperatorMutator
    case 8167: {
        /**
        * Inserted by Kadabra
        */
        boxBiometricInfo = findViewById(com.beemdevelopment.aegis.R.id.box_biometric_info);
        boxBiometricInfo.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    boxBiometricInfo = findViewById(com.beemdevelopment.aegis.R.id.box_biometric_info);
    break;
}
}
android.widget.Button decryptButton;
switch(MUID_STATIC) {
// AuthActivity_9_FindViewByIdReturnsNullOperatorMutator
case 9167: {
    decryptButton = null;
    break;
}
// AuthActivity_10_InvalidIDFindViewOperatorMutator
case 10167: {
    decryptButton = findViewById(732221);
    break;
}
// AuthActivity_11_InvalidViewFocusOperatorMutator
case 11167: {
    /**
    * Inserted by Kadabra
    */
    decryptButton = findViewById(com.beemdevelopment.aegis.R.id.button_decrypt);
    decryptButton.requestFocus();
    break;
}
// AuthActivity_12_ViewComponentNotVisibleOperatorMutator
case 12167: {
    /**
    * Inserted by Kadabra
    */
    decryptButton = findViewById(com.beemdevelopment.aegis.R.id.button_decrypt);
    decryptButton.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
decryptButton = findViewById(com.beemdevelopment.aegis.R.id.button_decrypt);
break;
}
}
android.widget.TextView biometricsButton;
switch(MUID_STATIC) {
// AuthActivity_13_FindViewByIdReturnsNullOperatorMutator
case 13167: {
biometricsButton = null;
break;
}
// AuthActivity_14_InvalidIDFindViewOperatorMutator
case 14167: {
biometricsButton = findViewById(732221);
break;
}
// AuthActivity_15_InvalidViewFocusOperatorMutator
case 15167: {
/**
* Inserted by Kadabra
*/
biometricsButton = findViewById(com.beemdevelopment.aegis.R.id.button_biometrics);
biometricsButton.requestFocus();
break;
}
// AuthActivity_16_ViewComponentNotVisibleOperatorMutator
case 16167: {
/**
* Inserted by Kadabra
*/
biometricsButton = findViewById(com.beemdevelopment.aegis.R.id.button_biometrics);
biometricsButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
biometricsButton = findViewById(com.beemdevelopment.aegis.R.id.button_biometrics);
break;
}
}
getOnBackPressedDispatcher().addCallback(this, new com.beemdevelopment.aegis.ui.AuthActivity.BackPressHandler());
_textPassword.setOnEditorActionListener((android.widget.TextView v,int actionId,android.view.KeyEvent event) -> {
if (((event != null) && (event.getKeyCode() == android.view.KeyEvent.KEYCODE_ENTER)) || (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE)) {
decryptButton.performClick();
}
return false;
});
if (_prefs.isPinKeyboardEnabled()) {
_textPassword.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD);
}
android.content.Intent intent;
switch(MUID_STATIC) {
// AuthActivity_17_RandomActionIntentDefinitionOperatorMutator
case 17167: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
if (savedInstanceState == null) {
_inhibitBioPrompt = intent.getBooleanExtra("inhibitBioPrompt", false);
// A persistent notification is shown to let the user know that the vault is unlocked. Permission
// to do so is required since API 33, so for existing users, we have to request permission here
// in order to be able to show the notification after unlock.
// 
// NOTE: Disabled for now. See issue: #1047
/* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
PermissionHelper.request(this, CODE_PERM_NOTIFICATIONS, Manifest.permission.POST_NOTIFICATIONS);
}
 */
} else {
_inhibitBioPrompt = savedInstanceState.getBoolean("inhibitBioPrompt", false);
}
if (_vaultManager.getVaultFileError() != null) {
switch(MUID_STATIC) {
// AuthActivity_18_BuggyGUIListenerOperatorMutator
case 18167: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.vault_load_error, _vaultManager.getVaultFileError(), null);
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.vault_load_error, _vaultManager.getVaultFileError(), (android.content.DialogInterface dialog,int which) -> {
getOnBackPressedDispatcher().onBackPressed();
});
break;
}
}
return;
}
com.beemdevelopment.aegis.vault.VaultFile vaultFile;
vaultFile = _vaultManager.getVaultFile();
_slots = vaultFile.getHeader().getSlots();
// only show the biometric prompt if the api version is new enough, permission is granted, a scanner is found and a biometric slot is found
if (_slots.has(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class) && com.beemdevelopment.aegis.helpers.BiometricsHelper.isAvailable(this)) {
boolean invalidated;
invalidated = false;
try {
// find a biometric slot with an id that matches an alias in the keystore
for (com.beemdevelopment.aegis.vault.slots.BiometricSlot slot : _slots.findAll(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class)) {
java.lang.String id;
id = slot.getUUID().toString();
com.beemdevelopment.aegis.crypto.KeyStoreHandle handle;
handle = new com.beemdevelopment.aegis.crypto.KeyStoreHandle();
if (handle.containsKey(id)) {
javax.crypto.SecretKey key;
key = handle.getKey(id);
// if 'key' is null, it was permanently invalidated
if (key == null) {
invalidated = true;
continue;
}
_bioSlot = slot;
_bioKey = key;
biometricsButton.setVisibility(android.view.View.VISIBLE);
invalidated = false;
break;
}
}
} catch (com.beemdevelopment.aegis.crypto.KeyStoreHandleException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.biometric_init_error, e);
}
// display a help message if a matching invalidated keystore entry was found
if (invalidated) {
boxBiometricInfo.setVisibility(android.view.View.VISIBLE);
biometricsButton.setVisibility(android.view.View.GONE);
}
}
switch(MUID_STATIC) {
// AuthActivity_19_BuggyGUIListenerOperatorMutator
case 19167: {
decryptButton.setOnClickListener(null);
break;
}
default: {
decryptButton.setOnClickListener((android.view.View v) -> {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
char[] password;
password = com.beemdevelopment.aegis.helpers.EditTextHelper.getEditTextChars(_textPassword);
java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> slots;
slots = _slots.findAll(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class);
com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params params;
params = new com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params(slots, password);
com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask task;
task = new com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask(this, new com.beemdevelopment.aegis.ui.AuthActivity.PasswordDerivationListener());
task.execute(getLifecycle(), params);
});
break;
}
}
switch(MUID_STATIC) {
// AuthActivity_20_BuggyGUIListenerOperatorMutator
case 20167: {
biometricsButton.setOnClickListener(null);
break;
}
default: {
biometricsButton.setOnClickListener((android.view.View v) -> {
showBiometricPrompt();
});
break;
}
}
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putBoolean("inhibitBioPrompt", _inhibitBioPrompt);
}


@java.lang.Override
protected void onSetTheme() {
setTheme(com.beemdevelopment.aegis.ThemeMap.NO_ACTION_BAR);
}


private void selectPassword() {
_textPassword.selectAll();
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
imm.toggleSoftInput(android.view.inputmethod.InputMethodManager.SHOW_FORCED, 0);
}


@java.lang.Override
public void onResume() {
super.onResume();
boolean remindPassword;
remindPassword = _prefs.isPasswordReminderNeeded();
if ((_bioKey == null) || remindPassword) {
focusPasswordField();
}
if ((((_bioKey != null) && (_bioPrompt == null)) && (!_inhibitBioPrompt)) && (!remindPassword)) {
_bioPrompt = showBiometricPrompt();
}
_inhibitBioPrompt = false;
}


@java.lang.Override
public void onPause() {
if ((!isChangingConfigurations()) && (_bioPrompt != null)) {
_bioPrompt.cancelAuthentication();
_bioPrompt = null;
}
super.onPause();
}


@java.lang.Override
public void onAttachedToWindow() {
if ((_bioKey != null) && _prefs.isPasswordReminderNeeded()) {
showPasswordReminder();
}
}


private void focusPasswordField() {
_textPassword.requestFocus();
getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
}


private void showPasswordReminder() {
android.view.View popupLayout;
popupLayout = getLayoutInflater().inflate(com.beemdevelopment.aegis.R.layout.popup_password, null);
popupLayout.measure(android.view.View.MeasureSpec.UNSPECIFIED, android.view.View.MeasureSpec.UNSPECIFIED);
android.widget.PopupWindow popup;
popup = new android.widget.PopupWindow(popupLayout, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
popup.setFocusable(false);
popup.setOutsideTouchable(true);
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
popup.setElevation(5.0F);
}
_textPassword.post(() -> {
if (isFinishing()) {
return;
}
// calculating the actual height of the popup window does not seem possible
// adding 25dp seems to look good enough
int yoff;
switch(MUID_STATIC) {
// AuthActivity_21_BinaryMutator
case 21167: {
yoff = (_textPassword.getHeight() + popupLayout.getMeasuredHeight()) - com.beemdevelopment.aegis.helpers.MetricsHelper.convertDpToPixels(this, 25);
break;
}
default: {
switch(MUID_STATIC) {
// AuthActivity_22_BinaryMutator
case 22167: {
yoff = (_textPassword.getHeight() - popupLayout.getMeasuredHeight()) + com.beemdevelopment.aegis.helpers.MetricsHelper.convertDpToPixels(this, 25);
break;
}
default: {
yoff = (_textPassword.getHeight() + popupLayout.getMeasuredHeight()) + com.beemdevelopment.aegis.helpers.MetricsHelper.convertDpToPixels(this, 25);
break;
}
}
break;
}
}
popup.showAsDropDown(_textPassword, 0, -yoff);
});
_textPassword.postDelayed(popup::dismiss, 5000);
}


public androidx.biometric.BiometricPrompt showBiometricPrompt() {
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
imm.hideSoftInputFromWindow(_textPassword.getWindowToken(), 0);
javax.crypto.Cipher cipher;
try {
cipher = _bioSlot.createDecryptCipher(_bioKey);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.biometric_init_error, e);
return null;
}
androidx.biometric.BiometricPrompt.CryptoObject cryptoObj;
cryptoObj = new androidx.biometric.BiometricPrompt.CryptoObject(cipher);
androidx.biometric.BiometricPrompt prompt;
prompt = new androidx.biometric.BiometricPrompt(this, new com.beemdevelopment.aegis.helpers.UiThreadExecutor(), new com.beemdevelopment.aegis.ui.AuthActivity.BiometricPromptListener());
androidx.biometric.BiometricPrompt.PromptInfo info;
info = new androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle(getString(com.beemdevelopment.aegis.R.string.authentication)).setNegativeButtonText(getString(android.R.string.cancel)).setConfirmationRequired(false).build();
prompt.authenticate(info, cryptoObj);
return prompt;
}


private void finish(com.beemdevelopment.aegis.crypto.MasterKey key, boolean isSlotRepaired) {
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = new com.beemdevelopment.aegis.vault.VaultFileCredentials(key, _slots);
try {
_vaultManager.unlock(creds);
if (isSlotRepaired) {
saveAndBackupVault();
}
} catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.decryption_corrupt_error, e);
return;
}
setResult(android.app.Activity.RESULT_OK);
finish();
}


private void onInvalidPassword() {
switch(MUID_STATIC) {
// AuthActivity_23_BuggyGUIListenerOperatorMutator
case 23167: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(getString(com.beemdevelopment.aegis.R.string.unlock_vault_error)).setMessage(getString(com.beemdevelopment.aegis.R.string.unlock_vault_error_description)).setCancelable(false).setPositiveButton(android.R.string.ok, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(getString(com.beemdevelopment.aegis.R.string.unlock_vault_error)).setMessage(getString(com.beemdevelopment.aegis.R.string.unlock_vault_error_description)).setCancelable(false).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> selectPassword()).create());
break;
}
}
_failedUnlockAttempts++;
if (_failedUnlockAttempts >= 3) {
_textPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
}
}


private class BackPressHandler extends androidx.activity.OnBackPressedCallback {
public BackPressHandler() {
super(true);
}


@java.lang.Override
public void handleOnBackPressed() {
// This breaks predictive back gestures, but it doesn't make sense
// to go back to MainActivity when cancelling auth
setResult(android.app.Activity.RESULT_CANCELED);
finishAffinity();
}

}

private class PasswordDerivationListener implements com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Callback {
@java.lang.Override
public void onTaskFinished(com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result result) {
if (result != null) {
// replace the old slot with the repaired one
if (result.isSlotRepaired()) {
_slots.replace(result.getSlot());
}
if (result.getSlot().getType() == com.beemdevelopment.aegis.vault.slots.Slot.TYPE_PASSWORD) {
_prefs.resetPasswordReminderTimestamp();
}
finish(result.getKey(), result.isSlotRepaired());
} else {
onInvalidPassword();
}
}

}

private class BiometricPromptListener extends androidx.biometric.BiometricPrompt.AuthenticationCallback {
@java.lang.Override
public void onAuthenticationError(int errorCode, @androidx.annotation.NonNull
java.lang.CharSequence errString) {
super.onAuthenticationError(errorCode, errString);
_bioPrompt = null;
if (!com.beemdevelopment.aegis.helpers.BiometricsHelper.isCanceled(errorCode)) {
android.widget.Toast.makeText(com.beemdevelopment.aegis.ui.AuthActivity.this, errString, android.widget.Toast.LENGTH_LONG).show();
}
}


@java.lang.Override
public void onAuthenticationSucceeded(@androidx.annotation.NonNull
androidx.biometric.BiometricPrompt.AuthenticationResult result) {
super.onAuthenticationSucceeded(result);
_bioPrompt = null;
com.beemdevelopment.aegis.crypto.MasterKey key;
com.beemdevelopment.aegis.vault.slots.BiometricSlot slot;
slot = _slots.find(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class);
try {
key = slot.getKey(result.getCryptoObject().getCipher());
} catch (com.beemdevelopment.aegis.vault.slots.SlotException | com.beemdevelopment.aegis.vault.slots.SlotIntegrityException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(com.beemdevelopment.aegis.ui.AuthActivity.this, com.beemdevelopment.aegis.R.string.biometric_decrypt_error, e);
return;
}
finish(key, false);
}


@java.lang.Override
public void onAuthenticationFailed() {
super.onAuthenticationFailed();
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
