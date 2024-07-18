package com.beemdevelopment.aegis.ui.fragments.preferences;
import androidx.appcompat.app.AlertDialog;
import com.beemdevelopment.aegis.helpers.BiometricsHelper;
import androidx.biometric.BiometricPrompt;
import com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import android.view.WindowManager;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.vault.slots.SlotException;
import com.beemdevelopment.aegis.vault.slots.SlotList;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.crypto.KeyStoreHandle;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.ui.preferences.SwitchPreference;
import android.widget.Toast;
import com.beemdevelopment.aegis.crypto.KeyStoreHandleException;
import java.util.List;
import static android.text.TextUtils.isDigitsOnly;
import com.beemdevelopment.aegis.Preferences;
import com.beemdevelopment.aegis.vault.slots.BiometricSlot;
import android.view.Window;
import com.beemdevelopment.aegis.helpers.BiometricSlotInitializer;
import androidx.preference.SwitchPreferenceCompat;
import android.os.Bundle;
import androidx.preference.Preference;
import com.beemdevelopment.aegis.vault.slots.Slot;
import com.beemdevelopment.aegis.PassReminderFreq;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.vault.VaultFileCredentials;
import java.util.Arrays;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SecurityPreferencesFragment extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.preferences.SwitchPreference _encryptionPreference;

    private com.beemdevelopment.aegis.ui.preferences.SwitchPreference _biometricsPreference;

    private androidx.preference.Preference _autoLockPreference;

    private androidx.preference.Preference _setPasswordPreference;

    private androidx.preference.Preference _passwordReminderPreference;

    private androidx.preference.SwitchPreferenceCompat _pinKeyboardPreference;

    private com.beemdevelopment.aegis.ui.preferences.SwitchPreference _backupPasswordPreference;

    private androidx.preference.Preference _backupPasswordChangePreference;

    @java.lang.Override
    public void onResume() {
        super.onResume();
        updateEncryptionPreferences();
    }


    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        addPreferencesFromResource(com.beemdevelopment.aegis.R.xml.preferences_security);
        androidx.preference.Preference tapToRevealPreference;
        tapToRevealPreference = requirePreference("pref_tap_to_reveal");
        tapToRevealPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            switch(MUID_STATIC) {
                // SecurityPreferencesFragment_0_NullValueIntentPutExtraOperatorMutator
                case 138: {
                    getResult().putExtra("needsRefresh", new Parcelable[0]);
                    break;
                }
                // SecurityPreferencesFragment_1_IntentPayloadReplacementOperatorMutator
                case 1138: {
                    getResult().putExtra("needsRefresh", true);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // SecurityPreferencesFragment_2_RandomActionIntentDefinitionOperatorMutator
                    case 2138: {
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
                    getResult().putExtra("needsRefresh", true);
                    break;
                }
            }
            break;
        }
    }
    return true;
});
androidx.preference.Preference screenPreference;
screenPreference = requirePreference("pref_secure_screen");
screenPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
    switch(MUID_STATIC) {
        // SecurityPreferencesFragment_3_NullValueIntentPutExtraOperatorMutator
        case 3138: {
            getResult().putExtra("needsRecreate", new Parcelable[0]);
            break;
        }
        // SecurityPreferencesFragment_4_IntentPayloadReplacementOperatorMutator
        case 4138: {
            getResult().putExtra("needsRecreate", true);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // SecurityPreferencesFragment_5_RandomActionIntentDefinitionOperatorMutator
            case 5138: {
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
            getResult().putExtra("needsRecreate", true);
            break;
        }
    }
    break;
}
}
android.view.Window window;
window = requireActivity().getWindow();
if (((boolean) (newValue))) {
window.addFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);
} else {
window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);
}
return true;
});
androidx.preference.Preference tapToRevealTimePreference;
tapToRevealTimePreference = requirePreference("pref_tap_to_reveal_time");
tapToRevealTimePreference.setSummary(_prefs.getTapToRevealTime() + " seconds");
tapToRevealTimePreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTapToRevealTimeoutPickerDialog(requireContext(), _prefs.getTapToRevealTime(), (int number) -> {
_prefs.setTapToRevealTime(number);
tapToRevealTimePreference.setSummary(number + " seconds");
switch(MUID_STATIC) {
    // SecurityPreferencesFragment_6_NullValueIntentPutExtraOperatorMutator
    case 6138: {
        getResult().putExtra("needsRefresh", new Parcelable[0]);
        break;
    }
    // SecurityPreferencesFragment_7_IntentPayloadReplacementOperatorMutator
    case 7138: {
        getResult().putExtra("needsRefresh", true);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // SecurityPreferencesFragment_8_RandomActionIntentDefinitionOperatorMutator
        case 8138: {
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
        getResult().putExtra("needsRefresh", true);
        break;
    }
}
break;
}
}
});
return false;
});
_encryptionPreference = requirePreference("pref_encryption");
_encryptionPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
if (!_vaultManager.getVault().isEncryptionEnabled()) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSetPasswordDialog(requireActivity(), new com.beemdevelopment.aegis.ui.fragments.preferences.SecurityPreferencesFragment.EnableEncryptionListener());
} else {
switch(MUID_STATIC) {
// SecurityPreferencesFragment_9_BuggyGUIListenerOperatorMutator
case 9138: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.disable_encryption).setMessage(getText(com.beemdevelopment.aegis.R.string.disable_encryption_description)).setPositiveButton(android.R.string.yes, null).setNegativeButton(android.R.string.no, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.disable_encryption).setMessage(getText(com.beemdevelopment.aegis.R.string.disable_encryption_description)).setPositiveButton(android.R.string.yes, (android.content.DialogInterface dialog,int which) -> {
try {
    _vaultManager.disableEncryption();
} catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
    e.printStackTrace();
    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.disable_encryption_error, e);
    return;
}
_prefs.setIsBackupsEnabled(false);
_prefs.setIsAndroidBackupsEnabled(false);
updateEncryptionPreferences();
}).setNegativeButton(android.R.string.no, null).create());
break;
}
}
}
return false;
});
_biometricsPreference = requirePreference("pref_biometrics");
_biometricsPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = _vaultManager.getVault().getCredentials();
com.beemdevelopment.aegis.vault.slots.SlotList slots;
slots = creds.getSlots();
if (!slots.has(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class)) {
if (com.beemdevelopment.aegis.helpers.BiometricsHelper.isAvailable(requireContext())) {
com.beemdevelopment.aegis.helpers.BiometricSlotInitializer initializer;
initializer = new com.beemdevelopment.aegis.helpers.BiometricSlotInitializer(this, new com.beemdevelopment.aegis.ui.fragments.preferences.SecurityPreferencesFragment.RegisterBiometricsListener());
androidx.biometric.BiometricPrompt.PromptInfo info;
info = new androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle(getString(com.beemdevelopment.aegis.R.string.set_up_biometric)).setNegativeButtonText(getString(android.R.string.cancel)).build();
initializer.authenticate(info);
}
} else {
// remove the biometric slot
com.beemdevelopment.aegis.vault.slots.BiometricSlot slot;
slot = slots.find(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class);
slots.remove(slot);
_vaultManager.getVault().setCredentials(creds);
// remove the KeyStore key
try {
com.beemdevelopment.aegis.crypto.KeyStoreHandle handle;
handle = new com.beemdevelopment.aegis.crypto.KeyStoreHandle();
handle.deleteKey(slot.getUUID().toString());
} catch (com.beemdevelopment.aegis.crypto.KeyStoreHandleException e) {
e.printStackTrace();
}
saveAndBackupVault();
updateEncryptionPreferences();
}
return false;
});
_setPasswordPreference = requirePreference("pref_password");
_setPasswordPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSetPasswordDialog(requireActivity(), new com.beemdevelopment.aegis.ui.fragments.preferences.SecurityPreferencesFragment.SetPasswordListener());
return false;
});
_pinKeyboardPreference = requirePreference("pref_pin_keyboard");
_pinKeyboardPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
if (!((boolean) (newValue))) {
return true;
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(requireContext(), com.beemdevelopment.aegis.R.string.set_password_confirm, com.beemdevelopment.aegis.R.string.pin_keyboard_description, ( password) -> {
if (android.text.TextUtils.isDigitsOnly(new java.lang.String(password))) {
java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> slots;
slots = _vaultManager.getVault().getCredentials().getSlots().findRegularPasswordSlots();
com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params params;
params = new com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Params(slots, password);
com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask task;
task = new com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask(requireContext(), new com.beemdevelopment.aegis.ui.fragments.preferences.SecurityPreferencesFragment.PasswordConfirmationListener());
task.execute(getLifecycle(), params);
} else {
_pinKeyboardPreference.setChecked(false);
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.pin_keyboard_error).setMessage(com.beemdevelopment.aegis.R.string.pin_keyboard_error_description).setCancelable(false).setPositiveButton(android.R.string.ok, null).create());
}
}, (android.content.DialogInterface dialog) -> {
_pinKeyboardPreference.setChecked(false);
});
return false;
});
_autoLockPreference = requirePreference("pref_auto_lock");
_autoLockPreference.setSummary(getAutoLockSummary());
_autoLockPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
final int[] items;
items = com.beemdevelopment.aegis.Preferences.AUTO_LOCK_SETTINGS;
final java.lang.String[] textItems;
textItems = getResources().getStringArray(com.beemdevelopment.aegis.R.array.pref_auto_lock_types);
final boolean[] checkedItems;
checkedItems = new boolean[items.length];
for (int i = 0; i < items.length; i++) {
checkedItems[i] = _prefs.isAutoLockTypeEnabled(items[i]);
}
androidx.appcompat.app.AlertDialog.Builder builder;
switch(MUID_STATIC) {
// SecurityPreferencesFragment_10_BuggyGUIListenerOperatorMutator
case 10138: {
builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.pref_auto_lock_prompt).setMultiChoiceItems(textItems, checkedItems, (android.content.DialogInterface dialog,int index,boolean isChecked) -> checkedItems[index] = isChecked).setPositiveButton(android.R.string.ok, null).setNegativeButton(android.R.string.cancel, null);
break;
}
default: {
builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.pref_auto_lock_prompt).setMultiChoiceItems(textItems, checkedItems, (android.content.DialogInterface dialog,int index,boolean isChecked) -> checkedItems[index] = isChecked).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
int autoLock;
autoLock = com.beemdevelopment.aegis.Preferences.AUTO_LOCK_OFF;
for (int i = 0; i < checkedItems.length; i++) {
if (checkedItems[i]) {
autoLock |= items[i];
}
}
_prefs.setAutoLockMask(autoLock);
_autoLockPreference.setSummary(getAutoLockSummary());
}).setNegativeButton(android.R.string.cancel, null);
break;
}
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(builder.create());
return false;
});
_passwordReminderPreference = requirePreference("pref_password_reminder_freq");
_passwordReminderPreference.setSummary(getPasswordReminderSummary());
_passwordReminderPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
final com.beemdevelopment.aegis.PassReminderFreq currFreq;
currFreq = _prefs.getPasswordReminderFrequency();
final com.beemdevelopment.aegis.PassReminderFreq[] items;
items = com.beemdevelopment.aegis.PassReminderFreq.values();
final java.lang.String[] textItems;
textItems = java.util.Arrays.stream(items).map((com.beemdevelopment.aegis.PassReminderFreq f) -> getString(f.getStringRes())).toArray(java.lang.String[]::new);
androidx.appcompat.app.AlertDialog.Builder builder;
switch(MUID_STATIC) {
// SecurityPreferencesFragment_11_BuggyGUIListenerOperatorMutator
case 11138: {
builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.pref_password_reminder_title).setSingleChoiceItems(textItems, currFreq.ordinal(), null).setNegativeButton(android.R.string.cancel, null);
break;
}
default: {
builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.pref_password_reminder_title).setSingleChoiceItems(textItems, currFreq.ordinal(), (android.content.DialogInterface dialog,int which) -> {
int i;
i = ((androidx.appcompat.app.AlertDialog) (dialog)).getListView().getCheckedItemPosition();
com.beemdevelopment.aegis.PassReminderFreq freq;
freq = com.beemdevelopment.aegis.PassReminderFreq.fromInteger(i);
_prefs.setPasswordReminderFrequency(freq);
_passwordReminderPreference.setSummary(getPasswordReminderSummary());
dialog.dismiss();
}).setNegativeButton(android.R.string.cancel, null);
break;
}
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(builder.create());
return false;
});
_backupPasswordPreference = requirePreference("pref_backup_password");
_backupPasswordPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
if (!_vaultManager.getVault().isBackupPasswordSet()) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSetPasswordDialog(requireActivity(), new com.beemdevelopment.aegis.ui.fragments.preferences.SecurityPreferencesFragment.SetBackupPasswordListener());
} else {
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = _vaultManager.getVault().getCredentials();
com.beemdevelopment.aegis.vault.slots.SlotList slots;
slots = creds.getSlots();
for (com.beemdevelopment.aegis.vault.slots.Slot slot : slots.findBackupPasswordSlots()) {
slots.remove(slot);
}
_vaultManager.getVault().setCredentials(creds);
saveAndBackupVault();
updateEncryptionPreferences();
}
return false;
});
_backupPasswordChangePreference = requirePreference("pref_backup_password_change");
_backupPasswordChangePreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSetPasswordDialog(requireActivity(), new com.beemdevelopment.aegis.ui.fragments.preferences.SecurityPreferencesFragment.SetBackupPasswordListener());
return false;
});
}


private void updateEncryptionPreferences() {
boolean encrypted;
encrypted = _vaultManager.getVault().isEncryptionEnabled();
boolean backupPasswordSet;
backupPasswordSet = _vaultManager.getVault().isBackupPasswordSet();
_encryptionPreference.setChecked(encrypted, true);
_setPasswordPreference.setVisible(encrypted);
_biometricsPreference.setVisible(encrypted);
_autoLockPreference.setVisible(encrypted);
_pinKeyboardPreference.setVisible(encrypted);
_backupPasswordPreference.getParent().setVisible(encrypted);
_backupPasswordPreference.setChecked(backupPasswordSet, true);
_backupPasswordChangePreference.setVisible(backupPasswordSet);
if (encrypted) {
com.beemdevelopment.aegis.vault.slots.SlotList slots;
slots = _vaultManager.getVault().getCredentials().getSlots();
boolean multiBackupPassword;
multiBackupPassword = slots.findBackupPasswordSlots().size() > 1;
boolean multiPassword;
multiPassword = slots.findRegularPasswordSlots().size() > 1;
boolean multiBio;
multiBio = slots.findAll(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class).size() > 1;
boolean canUseBio;
canUseBio = com.beemdevelopment.aegis.helpers.BiometricsHelper.isAvailable(requireContext());
_setPasswordPreference.setEnabled(!multiPassword);
_biometricsPreference.setEnabled(canUseBio && (!multiBio));
_biometricsPreference.setChecked(slots.has(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class), true);
_passwordReminderPreference.setVisible(slots.has(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class));
_backupPasswordChangePreference.setEnabled(!multiBackupPassword);
} else {
_setPasswordPreference.setEnabled(false);
_biometricsPreference.setEnabled(false);
_biometricsPreference.setChecked(false, true);
_passwordReminderPreference.setVisible(false);
_backupPasswordChangePreference.setEnabled(false);
}
}


private java.lang.String getPasswordReminderSummary() {
com.beemdevelopment.aegis.PassReminderFreq freq;
freq = _prefs.getPasswordReminderFrequency();
if (freq == com.beemdevelopment.aegis.PassReminderFreq.NEVER) {
return getString(com.beemdevelopment.aegis.R.string.pref_password_reminder_summary_disabled);
}
java.lang.String freqString;
freqString = getString(freq.getStringRes()).toLowerCase();
return getString(com.beemdevelopment.aegis.R.string.pref_password_reminder_summary, freqString);
}


private java.lang.String getAutoLockSummary() {
final int[] settings;
settings = com.beemdevelopment.aegis.Preferences.AUTO_LOCK_SETTINGS;
final java.lang.String[] descriptions;
descriptions = getResources().getStringArray(com.beemdevelopment.aegis.R.array.pref_auto_lock_types);
java.lang.StringBuilder builder;
builder = new java.lang.StringBuilder();
for (int i = 0; i < settings.length; i++) {
if (_prefs.isAutoLockTypeEnabled(settings[i])) {
if (builder.length() != 0) {
builder.append(", ");
}
builder.append(descriptions[i].toLowerCase());
}
}
if (builder.length() == 0) {
return getString(com.beemdevelopment.aegis.R.string.pref_auto_lock_summary_disabled);
}
return getString(com.beemdevelopment.aegis.R.string.pref_auto_lock_summary, builder.toString());
}


private class SetPasswordListener implements com.beemdevelopment.aegis.ui.dialogs.Dialogs.PasswordSlotListener {
@java.lang.Override
public void onSlotResult(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.Cipher cipher) {
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = _vaultManager.getVault().getCredentials();
com.beemdevelopment.aegis.vault.slots.SlotList slots;
slots = creds.getSlots();
try {
// encrypt the master key for this slot
slot.setKey(creds.getKey(), cipher);
// remove the old master password slot
java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> passSlots;
passSlots = creds.getSlots().findRegularPasswordSlots();
if (passSlots.size() != 0) {
slots.remove(passSlots.get(0));
}
// add the new master password slot
slots.add(slot);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
onException(e);
return;
}
_vaultManager.getVault().setCredentials(creds);
saveAndBackupVault();
if (_prefs.isPinKeyboardEnabled()) {
_pinKeyboardPreference.setChecked(false);
android.widget.Toast.makeText(requireContext(), com.beemdevelopment.aegis.R.string.pin_keyboard_disabled, android.widget.Toast.LENGTH_SHORT).show();
}
}


@java.lang.Override
public void onException(java.lang.Exception e) {
e.printStackTrace();
updateEncryptionPreferences();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.encryption_set_password_error, e);
}

}

private class SetBackupPasswordListener implements com.beemdevelopment.aegis.ui.dialogs.Dialogs.PasswordSlotListener {
@java.lang.Override
public void onSlotResult(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.Cipher cipher) {
slot.setIsBackup(true);
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = _vaultManager.getVault().getCredentials();
com.beemdevelopment.aegis.vault.slots.SlotList slots;
slots = creds.getSlots();
try {
// encrypt the master key for this slot
slot.setKey(creds.getKey(), cipher);
// remove the old backup password slot
for (com.beemdevelopment.aegis.vault.slots.Slot oldSlot : slots.findBackupPasswordSlots()) {
slots.remove(oldSlot);
}
// add the new backup password slot
slots.add(slot);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
onException(e);
return;
}
_vaultManager.getVault().setCredentials(creds);
saveAndBackupVault();
updateEncryptionPreferences();
}


@java.lang.Override
public void onException(java.lang.Exception e) {
e.printStackTrace();
updateEncryptionPreferences();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.encryption_set_password_error, e);
}

}

private class RegisterBiometricsListener implements com.beemdevelopment.aegis.helpers.BiometricSlotInitializer.Listener {
@java.lang.Override
public void onInitializeSlot(com.beemdevelopment.aegis.vault.slots.BiometricSlot slot, javax.crypto.Cipher cipher) {
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = _vaultManager.getVault().getCredentials();
try {
slot.setKey(creds.getKey(), cipher);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
e.printStackTrace();
onSlotInitializationFailed(0, e.toString());
return;
}
creds.getSlots().add(slot);
_vaultManager.getVault().setCredentials(creds);
saveAndBackupVault();
updateEncryptionPreferences();
}


@java.lang.Override
public void onSlotInitializationFailed(int errorCode, @androidx.annotation.NonNull
java.lang.CharSequence errString) {
if (!com.beemdevelopment.aegis.helpers.BiometricsHelper.isCanceled(errorCode)) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.encryption_enable_biometrics_error, errString);
}
}

}

private class EnableEncryptionListener implements com.beemdevelopment.aegis.ui.dialogs.Dialogs.PasswordSlotListener {
@java.lang.Override
public void onSlotResult(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.Cipher cipher) {
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = new com.beemdevelopment.aegis.vault.VaultFileCredentials();
try {
slot.setKey(creds.getKey(), cipher);
creds.getSlots().add(slot);
_vaultManager.enableEncryption(creds);
} catch (com.beemdevelopment.aegis.vault.VaultRepositoryException | com.beemdevelopment.aegis.vault.slots.SlotException e) {
onException(e);
return;
}
_pinKeyboardPreference.setChecked(false);
updateEncryptionPreferences();
}


@java.lang.Override
public void onException(java.lang.Exception e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.encryption_set_password_error, e);
}

}

private class PasswordConfirmationListener implements com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Callback {
@java.lang.Override
public void onTaskFinished(com.beemdevelopment.aegis.ui.tasks.PasswordSlotDecryptTask.Result result) {
if (result != null) {
_pinKeyboardPreference.setChecked(true);
} else {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.pin_keyboard_error).setMessage(com.beemdevelopment.aegis.R.string.invalid_password).setCancelable(false).setPositiveButton(android.R.string.ok, null).create());
_pinKeyboardPreference.setChecked(false);
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
