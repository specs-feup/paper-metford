package com.beemdevelopment.aegis.ui.fragments.preferences;
import android.text.style.ForegroundColorSpan;
import androidx.preference.SwitchPreferenceCompat;
import android.os.Bundle;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import android.graphics.Typeface;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import androidx.preference.Preference;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import android.app.Activity;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.Preferences;
import android.text.Spannable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BackupsPreferencesFragment extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment {
    static final int MUID_STATIC = getMUID();
    private androidx.preference.SwitchPreferenceCompat _androidBackupsPreference;

    private androidx.preference.SwitchPreferenceCompat _backupsPreference;

    private androidx.preference.SwitchPreferenceCompat _backupReminderPreference;

    private androidx.preference.Preference _backupsLocationPreference;

    private androidx.preference.Preference _backupsTriggerPreference;

    private androidx.preference.Preference _backupsVersionsPreference;

    private androidx.preference.Preference _backupsPasswordWarningPreference;

    private androidx.preference.Preference _builtinBackupStatusPreference;

    private androidx.preference.Preference _androidBackupStatusPreference;

    @java.lang.Override
    public void onResume() {
        super.onResume();
        updateBackupPreference();
    }


    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        addPreferencesFromResource(com.beemdevelopment.aegis.R.xml.preferences_backups);
        _backupsPasswordWarningPreference = requirePreference("pref_backups_warning_password");
        _builtinBackupStatusPreference = requirePreference("pref_status_backup_builtin");
        _builtinBackupStatusPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            com.beemdevelopment.aegis.Preferences.BackupResult backupRes;
            backupRes = _prefs.getBuiltInBackupResult();
            if ((backupRes != null) && (!backupRes.isSuccessful())) {
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showBackupErrorDialog(requireContext(), backupRes, null);
            }
            return true;
        });
        _androidBackupStatusPreference = requirePreference("pref_status_backup_android");
        _androidBackupStatusPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            com.beemdevelopment.aegis.Preferences.BackupResult backupRes;
            backupRes = _prefs.getAndroidBackupResult();
            if ((backupRes != null) && (!backupRes.isSuccessful())) {
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showBackupErrorDialog(requireContext(), backupRes, null);
            }
            return true;
        });
        _backupsPreference = requirePreference("pref_backups");
        _backupsPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            if (((boolean) (newValue))) {
                selectBackupsLocation();
            } else {
                _prefs.setIsBackupsEnabled(false);
                updateBackupPreference();
            }
            return false;
        });
        _backupReminderPreference = requirePreference("pref_backup_reminder");
        _backupReminderPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            if (!((boolean) (newValue))) {
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showCheckboxDialog(getContext(), com.beemdevelopment.aegis.R.string.pref_backups_reminder_dialog_title, com.beemdevelopment.aegis.R.string.pref_backups_reminder_dialog_summary, com.beemdevelopment.aegis.R.string.understand_risk_accept, this::saveAndDisableBackupReminder);
            } else {
                _prefs.setIsBackupReminderEnabled(true);
                return true;
            }
            return false;
        });
        _androidBackupsPreference = requirePreference("pref_android_backups");
        _androidBackupsPreference.setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            _prefs.setIsAndroidBackupsEnabled(((boolean) (newValue)));
            updateBackupPreference();
            if (((boolean) (newValue))) {
                _vaultManager.scheduleAndroidBackup();
            }
            return false;
        });
        android.net.Uri backupLocation;
        backupLocation = _prefs.getBackupsLocation();
        _backupsLocationPreference = requirePreference("pref_backups_location");
        if (backupLocation != null) {
            _backupsLocationPreference.setSummary(java.lang.String.format("%s: %s", getString(com.beemdevelopment.aegis.R.string.pref_backups_location_summary), android.net.Uri.decode(backupLocation.toString())));
        }
        _backupsLocationPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            selectBackupsLocation();
            return false;
        });
        _backupsTriggerPreference = requirePreference("pref_backups_trigger");
        _backupsTriggerPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            if (_prefs.isBackupsEnabled()) {
                scheduleBackup();
                _builtinBackupStatusPreference.setVisible(false);
            }
            return true;
        });
        _backupsVersionsPreference = requirePreference("pref_backups_versions");
        _backupsVersionsPreference.setSummary(getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.pref_backups_versions_summary, _prefs.getBackupsVersionCount(), _prefs.getBackupsVersionCount()));
        _backupsVersionsPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showBackupVersionsPickerDialog(requireContext(), _prefs.getBackupsVersionCount(), (int number) -> {
                switch(MUID_STATIC) {
                    // BackupsPreferencesFragment_0_BinaryMutator
                    case 133: {
                        number = (number * 5) - 5;
                        break;
                    }
                    default: {
                    switch(MUID_STATIC) {
                        // BackupsPreferencesFragment_1_BinaryMutator
                        case 1133: {
                            number = (number / 5) + 5;
                            break;
                        }
                        default: {
                        number = (number * 5) + 5;
                        break;
                    }
                }
                break;
            }
        }
        _prefs.setBackupsVersionCount(number);
        _backupsVersionsPreference.setSummary(getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.pref_backups_versions_summary, _prefs.getBackupsVersionCount(), _prefs.getBackupsVersionCount()));
    });
    return false;
});
}


private void saveAndDisableBackupReminder(boolean understand) {
if (understand) {
    _prefs.setIsBackupReminderEnabled(false);
    updateBackupPreference();
}
}


@java.lang.Override
public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
if ((data != null) && (requestCode == com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_BACKUPS)) {
    onSelectBackupsLocationResult(resultCode, data);
}
}


private void onSelectBackupsLocationResult(int resultCode, android.content.Intent data) {
android.net.Uri uri;
uri = data.getData();
if ((resultCode != android.app.Activity.RESULT_OK) || (uri == null)) {
    return;
}
int flags;
flags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION | android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
requireContext().getContentResolver().takePersistableUriPermission(data.getData(), flags);
_prefs.setBackupsLocation(uri);
_prefs.setIsBackupsEnabled(true);
_backupsLocationPreference.setSummary(java.lang.String.format("%s: %s", getString(com.beemdevelopment.aegis.R.string.pref_backups_location_summary), android.net.Uri.decode(uri.toString())));
updateBackupPreference();
scheduleBackup();
}


private void updateBackupPreference() {
boolean encrypted;
encrypted = _vaultManager.getVault().isEncryptionEnabled();
boolean androidBackupEnabled;
androidBackupEnabled = _prefs.isAndroidBackupsEnabled() && encrypted;
boolean backupEnabled;
backupEnabled = _prefs.isBackupsEnabled() && encrypted;
boolean backupReminderEnabled;
backupReminderEnabled = _prefs.isBackupReminderEnabled();
_backupsPasswordWarningPreference.setVisible(_vaultManager.getVault().isBackupPasswordSet());
_androidBackupsPreference.setChecked(androidBackupEnabled);
_androidBackupsPreference.setEnabled(encrypted);
_backupsPreference.setChecked(backupEnabled);
_backupsPreference.setEnabled(encrypted);
_backupReminderPreference.setChecked(backupReminderEnabled);
_backupsLocationPreference.setVisible(backupEnabled);
_backupsTriggerPreference.setVisible(backupEnabled);
_backupsVersionsPreference.setVisible(backupEnabled);
if (backupEnabled) {
    updateBackupStatus(_builtinBackupStatusPreference, _prefs.getBuiltInBackupResult());
}
if (androidBackupEnabled) {
    updateBackupStatus(_androidBackupStatusPreference, _prefs.getAndroidBackupResult());
}
_builtinBackupStatusPreference.setVisible(backupEnabled);
_androidBackupStatusPreference.setVisible(androidBackupEnabled);
}


private void updateBackupStatus(androidx.preference.Preference pref, com.beemdevelopment.aegis.Preferences.BackupResult res) {
boolean backupFailed;
backupFailed = (res != null) && (!res.isSuccessful());
pref.setSummary(getBackupStatusMessage(res));
pref.setSelectable(backupFailed);
// TODO: Find out why setting the tint of the icon doesn't work
if (backupFailed) {
    pref.setIcon(com.beemdevelopment.aegis.R.drawable.ic_info_outline_black_24dp);
} else if (res != null) {
    pref.setIcon(com.beemdevelopment.aegis.R.drawable.ic_check_black_24dp);
} else {
    pref.setIcon(null);
}
}


private java.lang.CharSequence getBackupStatusMessage(@androidx.annotation.Nullable
com.beemdevelopment.aegis.Preferences.BackupResult res) {
java.lang.String message;
int color;
color = com.beemdevelopment.aegis.R.color.warning_color;
if (res == null) {
    message = getString(com.beemdevelopment.aegis.R.string.backup_status_none);
} else if (res.isSuccessful()) {
    color = com.beemdevelopment.aegis.R.color.success_color;
    message = getString(com.beemdevelopment.aegis.R.string.backup_status_success, res.getElapsedSince(requireContext()));
} else {
    message = getString(com.beemdevelopment.aegis.R.string.backup_status_failed, res.getElapsedSince(requireContext()));
}
android.text.Spannable spannable;
spannable = new android.text.SpannableString(message);
spannable.setSpan(new android.text.style.ForegroundColorSpan(getResources().getColor(color)), 0, message.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
if (color == com.beemdevelopment.aegis.R.color.warning_color) {
    spannable.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, message.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
}
return spannable;
}


private void selectBackupsLocation() {
android.content.Intent intent;
switch(MUID_STATIC) {
    // BackupsPreferencesFragment_2_NullIntentOperatorMutator
    case 2133: {
        intent = null;
        break;
    }
    // BackupsPreferencesFragment_3_InvalidKeyIntentOperatorMutator
    case 3133: {
        intent = new android.content.Intent((String) null);
        break;
    }
    // BackupsPreferencesFragment_4_RandomActionIntentDefinitionOperatorMutator
    case 4133: {
        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent = new android.content.Intent(android.content.Intent.ACTION_OPEN_DOCUMENT_TREE);
    break;
}
}
switch(MUID_STATIC) {
// BackupsPreferencesFragment_5_RandomActionIntentDefinitionOperatorMutator
case 5133: {
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
intent.addFlags(((android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION | android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION) | android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION) | android.content.Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
break;
}
}
_vaultManager.startActivityForResult(this, intent, com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_BACKUPS);
}


private void scheduleBackup() {
try {
_vaultManager.scheduleBackup();
android.widget.Toast.makeText(requireContext(), com.beemdevelopment.aegis.R.string.backup_successful, android.widget.Toast.LENGTH_LONG).show();
} catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.backup_error, e);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
