package com.beemdevelopment.aegis.ui.fragments.preferences;
import androidx.appcompat.app.AlertDialog;
import java.io.OutputStream;
import java.util.ArrayList;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Random;
import android.widget.Button;
import com.beemdevelopment.aegis.importers.DatabaseImporter;
import android.app.Activity;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.util.TreeSet;
import android.widget.TextView;
import com.beemdevelopment.aegis.BuildConfig;
import java.util.List;
import com.beemdevelopment.aegis.ui.ImportEntriesActivity;
import java.util.HashSet;
import com.beemdevelopment.aegis.ui.tasks.ImportFileTask;
import com.beemdevelopment.aegis.helpers.DropdownHelper;
import android.widget.CheckBox;
import com.beemdevelopment.aegis.vault.VaultRepository;
import androidx.preference.Preference;
import com.beemdevelopment.aegis.ui.tasks.ExportTask;
import android.view.LayoutInflater;
import java.io.FileOutputStream;
import java.util.Objects;
import com.beemdevelopment.aegis.vault.VaultBackupManager;
import java.io.File;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.vault.VaultFileCredentials;
import com.beemdevelopment.aegis.ui.TransferEntriesActivity;
import java.util.Set;
import android.net.Uri;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.vault.slots.SlotException;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.otp.HotpInfo;
import android.widget.Toast;
import androidx.annotation.ArrayRes;
import com.beemdevelopment.aegis.ui.components.DropdownCheckBoxes;
import android.os.Bundle;
import java.io.IOException;
import android.content.Intent;
import android.view.View;
import androidx.core.content.FileProvider;
import com.beemdevelopment.aegis.vault.Vault;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import javax.crypto.Cipher;
import android.widget.AutoCompleteTextView;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImportExportPreferencesFragment extends com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment {
    static final int MUID_STATIC = getMUID();
    // keep a reference to the type of database converter that was selected
    private com.beemdevelopment.aegis.importers.DatabaseImporter.Definition _importerDef;

    private com.beemdevelopment.aegis.vault.Vault.EntryFilter _exportFilter;

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        addPreferencesFromResource(com.beemdevelopment.aegis.R.xml.preferences_import_export);
        if (savedInstanceState != null) {
            _importerDef = ((com.beemdevelopment.aegis.importers.DatabaseImporter.Definition) (savedInstanceState.getSerializable("importerDef")));
        }
        androidx.preference.Preference importPreference;
        importPreference = requirePreference("pref_import");
        importPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showImportersDialog(requireContext(), false, (com.beemdevelopment.aegis.importers.DatabaseImporter.Definition definition) -> {
                _importerDef = definition;
                android.content.Intent intent;
                switch(MUID_STATIC) {
                    // ImportExportPreferencesFragment_0_InvalidKeyIntentOperatorMutator
                    case 137: {
                        intent = new android.content.Intent((String) null);
                        break;
                    }
                    // ImportExportPreferencesFragment_1_RandomActionIntentDefinitionOperatorMutator
                    case 1137: {
                        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                        break;
                    }
                    default: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
                    break;
                }
            }
            switch(MUID_STATIC) {
                // ImportExportPreferencesFragment_2_RandomActionIntentDefinitionOperatorMutator
                case 2137: {
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
                intent.setType("*/*");
                break;
            }
        }
        _vaultManager.startActivityForResult(this, intent, com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_IMPORT_SELECT);
    });
    return true;
});
androidx.preference.Preference importAppPreference;
importAppPreference = requirePreference("pref_import_app");
importAppPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showImportersDialog(requireContext(), true, (com.beemdevelopment.aegis.importers.DatabaseImporter.Definition definition) -> {
        startImportEntriesActivity(definition, null);
    });
    return true;
});
androidx.preference.Preference exportPreference;
exportPreference = requirePreference("pref_export");
exportPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
    startExport();
    return true;
});
androidx.preference.Preference googleAuthStyleExportPreference;
googleAuthStyleExportPreference = requirePreference("pref_google_auth_style_export");
googleAuthStyleExportPreference.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
    startGoogleAuthenticatorStyleExport();
    return true;
});
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
outState.putSerializable("importerDef", _importerDef);
}


@java.lang.Override
public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
if (requestCode == com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_IMPORT) {
    switch(MUID_STATIC) {
        // ImportExportPreferencesFragment_3_NullValueIntentPutExtraOperatorMutator
        case 3137: {
            getResult().putExtra("needsRecreate", new Parcelable[0]);
            break;
        }
        // ImportExportPreferencesFragment_4_IntentPayloadReplacementOperatorMutator
        case 4137: {
            getResult().putExtra("needsRecreate", true);
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // ImportExportPreferencesFragment_5_RandomActionIntentDefinitionOperatorMutator
            case 5137: {
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
} else if (data != null) {
switch (requestCode) {
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_IMPORT_SELECT :
    onImportSelectResult(resultCode, data);
    break;
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT :
    // intentional fallthrough
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_PLAIN :
    // intentional fallthrough
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_HTML :
    // intentional fallthrough
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_GOOGLE_URI :
    onExportResult(requestCode, resultCode, data);
    break;
}
}
}


private void onImportSelectResult(int resultCode, android.content.Intent data) {
android.net.Uri uri;
uri = data.getData();
if ((resultCode != android.app.Activity.RESULT_OK) || (uri == null)) {
return;
}
com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params params;
params = new com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params(uri, "import", null);
com.beemdevelopment.aegis.ui.tasks.ImportFileTask task;
task = new com.beemdevelopment.aegis.ui.tasks.ImportFileTask(requireContext(), (com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result result) -> {
if (result.getError() == null) {
startImportEntriesActivity(_importerDef, result.getFile());
} else {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.reading_file_error, result.getError());
}
});
task.execute(getLifecycle(), params);
}


private void startImportEntriesActivity(com.beemdevelopment.aegis.importers.DatabaseImporter.Definition importerDef, java.io.File file) {
android.content.Intent intent;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_6_NullIntentOperatorMutator
case 6137: {
intent = null;
break;
}
// ImportExportPreferencesFragment_7_InvalidKeyIntentOperatorMutator
case 7137: {
intent = new android.content.Intent((android.content.Context) null, com.beemdevelopment.aegis.ui.ImportEntriesActivity.class);
break;
}
// ImportExportPreferencesFragment_8_RandomActionIntentDefinitionOperatorMutator
case 8137: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(requireContext(), com.beemdevelopment.aegis.ui.ImportEntriesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_9_NullValueIntentPutExtraOperatorMutator
case 9137: {
intent.putExtra("importerDef", new Parcelable[0]);
break;
}
// ImportExportPreferencesFragment_10_IntentPayloadReplacementOperatorMutator
case 10137: {
intent.putExtra("importerDef", (com.beemdevelopment.aegis.importers.DatabaseImporter.Definition) null);
break;
}
default: {
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_11_RandomActionIntentDefinitionOperatorMutator
case 11137: {
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
intent.putExtra("importerDef", importerDef);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_12_NullValueIntentPutExtraOperatorMutator
case 12137: {
intent.putExtra("file", new Parcelable[0]);
break;
}
// ImportExportPreferencesFragment_13_IntentPayloadReplacementOperatorMutator
case 13137: {
intent.putExtra("file", (java.io.File) null);
break;
}
default: {
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_14_RandomActionIntentDefinitionOperatorMutator
case 14137: {
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
intent.putExtra("file", file);
break;
}
}
break;
}
}
startActivityForResult(intent, com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_IMPORT);
}


private void startExport() {
boolean isBackupPasswordSet;
isBackupPasswordSet = _vaultManager.getVault().isBackupPasswordSet();
android.view.View view;
view = android.view.LayoutInflater.from(requireContext()).inflate(com.beemdevelopment.aegis.R.layout.dialog_export, null);
android.widget.TextView warningText;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_15_InvalidViewFocusOperatorMutator
case 15137: {
/**
* Inserted by Kadabra
*/
warningText = view.findViewById(com.beemdevelopment.aegis.R.id.text_export_warning);
warningText.requestFocus();
break;
}
// ImportExportPreferencesFragment_16_ViewComponentNotVisibleOperatorMutator
case 16137: {
/**
* Inserted by Kadabra
*/
warningText = view.findViewById(com.beemdevelopment.aegis.R.id.text_export_warning);
warningText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
warningText = view.findViewById(com.beemdevelopment.aegis.R.id.text_export_warning);
break;
}
}
android.widget.CheckBox checkBoxEncrypt;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_17_InvalidViewFocusOperatorMutator
case 17137: {
/**
* Inserted by Kadabra
*/
checkBoxEncrypt = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_export_encrypt);
checkBoxEncrypt.requestFocus();
break;
}
// ImportExportPreferencesFragment_18_ViewComponentNotVisibleOperatorMutator
case 18137: {
/**
* Inserted by Kadabra
*/
checkBoxEncrypt = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_export_encrypt);
checkBoxEncrypt.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkBoxEncrypt = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_export_encrypt);
break;
}
}
android.widget.CheckBox checkBoxAccept;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_19_InvalidViewFocusOperatorMutator
case 19137: {
/**
* Inserted by Kadabra
*/
checkBoxAccept = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_accept);
checkBoxAccept.requestFocus();
break;
}
// ImportExportPreferencesFragment_20_ViewComponentNotVisibleOperatorMutator
case 20137: {
/**
* Inserted by Kadabra
*/
checkBoxAccept = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_accept);
checkBoxAccept.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkBoxAccept = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_accept);
break;
}
}
android.widget.CheckBox checkBoxExportAllGroups;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_21_InvalidViewFocusOperatorMutator
case 21137: {
/**
* Inserted by Kadabra
*/
checkBoxExportAllGroups = view.findViewById(com.beemdevelopment.aegis.R.id.export_selected_groups);
checkBoxExportAllGroups.requestFocus();
break;
}
// ImportExportPreferencesFragment_22_ViewComponentNotVisibleOperatorMutator
case 22137: {
/**
* Inserted by Kadabra
*/
checkBoxExportAllGroups = view.findViewById(com.beemdevelopment.aegis.R.id.export_selected_groups);
checkBoxExportAllGroups.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkBoxExportAllGroups = view.findViewById(com.beemdevelopment.aegis.R.id.export_selected_groups);
break;
}
}
com.google.android.material.textfield.TextInputLayout groupsSelectionLayout;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_23_InvalidViewFocusOperatorMutator
case 23137: {
/**
* Inserted by Kadabra
*/
groupsSelectionLayout = view.findViewById(com.beemdevelopment.aegis.R.id.group_selection_layout);
groupsSelectionLayout.requestFocus();
break;
}
// ImportExportPreferencesFragment_24_ViewComponentNotVisibleOperatorMutator
case 24137: {
/**
* Inserted by Kadabra
*/
groupsSelectionLayout = view.findViewById(com.beemdevelopment.aegis.R.id.group_selection_layout);
groupsSelectionLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
groupsSelectionLayout = view.findViewById(com.beemdevelopment.aegis.R.id.group_selection_layout);
break;
}
}
com.beemdevelopment.aegis.ui.components.DropdownCheckBoxes groupsSelection;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_25_InvalidViewFocusOperatorMutator
case 25137: {
/**
* Inserted by Kadabra
*/
groupsSelection = view.findViewById(com.beemdevelopment.aegis.R.id.group_selection_dropdown);
groupsSelection.requestFocus();
break;
}
// ImportExportPreferencesFragment_26_ViewComponentNotVisibleOperatorMutator
case 26137: {
/**
* Inserted by Kadabra
*/
groupsSelection = view.findViewById(com.beemdevelopment.aegis.R.id.group_selection_dropdown);
groupsSelection.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
groupsSelection = view.findViewById(com.beemdevelopment.aegis.R.id.group_selection_dropdown);
break;
}
}
android.widget.TextView passwordInfoText;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_27_InvalidViewFocusOperatorMutator
case 27137: {
/**
* Inserted by Kadabra
*/
passwordInfoText = view.findViewById(com.beemdevelopment.aegis.R.id.text_separate_password);
passwordInfoText.requestFocus();
break;
}
// ImportExportPreferencesFragment_28_ViewComponentNotVisibleOperatorMutator
case 28137: {
/**
* Inserted by Kadabra
*/
passwordInfoText = view.findViewById(com.beemdevelopment.aegis.R.id.text_separate_password);
passwordInfoText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
passwordInfoText = view.findViewById(com.beemdevelopment.aegis.R.id.text_separate_password);
break;
}
}
passwordInfoText.setVisibility(checkBoxEncrypt.isChecked() && isBackupPasswordSet ? android.view.View.VISIBLE : android.view.View.GONE);
android.widget.AutoCompleteTextView dropdown;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_29_InvalidViewFocusOperatorMutator
case 29137: {
/**
* Inserted by Kadabra
*/
dropdown = view.findViewById(com.beemdevelopment.aegis.R.id.dropdown_export_format);
dropdown.requestFocus();
break;
}
// ImportExportPreferencesFragment_30_ViewComponentNotVisibleOperatorMutator
case 30137: {
/**
* Inserted by Kadabra
*/
dropdown = view.findViewById(com.beemdevelopment.aegis.R.id.dropdown_export_format);
dropdown.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
dropdown = view.findViewById(com.beemdevelopment.aegis.R.id.dropdown_export_format);
break;
}
}
com.beemdevelopment.aegis.helpers.DropdownHelper.fillDropdown(requireContext(), dropdown, com.beemdevelopment.aegis.R.array.export_formats);
dropdown.setText(getString(com.beemdevelopment.aegis.R.string.export_format_aegis), false);
dropdown.setOnItemClickListener((android.widget.AdapterView<?> parent,android.view.View view1,int position,long id) -> {
checkBoxEncrypt.setChecked(position == 0);
checkBoxEncrypt.setEnabled(position == 0);
warningText.setVisibility(checkBoxEncrypt.isChecked() ? android.view.View.GONE : android.view.View.VISIBLE);
passwordInfoText.setVisibility(checkBoxEncrypt.isChecked() && isBackupPasswordSet ? android.view.View.VISIBLE : android.view.View.GONE);
});
java.util.TreeSet<java.lang.String> groups;
groups = _vaultManager.getVault().getGroups();
if (groups.size() > 0) {
checkBoxExportAllGroups.setVisibility(android.view.View.VISIBLE);
java.util.ArrayList<java.lang.String> groupsArray;
groupsArray = new java.util.ArrayList<>();
groupsArray.add(getString(com.beemdevelopment.aegis.R.string.no_group));
groupsArray.addAll(groups);
groupsSelection.setCheckedItemsCountTextRes(com.beemdevelopment.aegis.R.plurals.export_groups_selected_count);
groupsSelection.addItems(groupsArray, false);
}
androidx.appcompat.app.AlertDialog dialog;
dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle(com.beemdevelopment.aegis.R.string.pref_export_summary).setView(view).setNeutralButton(com.beemdevelopment.aegis.R.string.share, null).setPositiveButton(android.R.string.ok, null).setNegativeButton(android.R.string.cancel, null).create();
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button btnPos;
btnPos = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
android.widget.Button btnNeutral;
btnNeutral = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL);
com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.DialogStateValidator stateValidator;
stateValidator = () -> {
boolean noGroupsSelected;
noGroupsSelected = groupsSelection.getCheckedItems().isEmpty();
boolean validState;
validState = (checkBoxEncrypt.isChecked() || checkBoxAccept.isChecked()) && (checkBoxExportAllGroups.isChecked() || (!noGroupsSelected));
if (noGroupsSelected && (groupsSelectionLayout.getError() == null)) {
java.lang.CharSequence errorMsg;
errorMsg = getString(com.beemdevelopment.aegis.R.string.export_no_groups_selected);
groupsSelectionLayout.setError(errorMsg);
} else if ((!noGroupsSelected) && (groupsSelectionLayout.getError() != null)) {
groupsSelectionLayout.setError(null);
groupsSelectionLayout.setErrorEnabled(false);
}
btnPos.setEnabled(validState);
btnNeutral.setEnabled(validState);
};
checkBoxEncrypt.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
warningText.setVisibility(isChecked ? android.view.View.GONE : android.view.View.VISIBLE);
passwordInfoText.setVisibility(isChecked && isBackupPasswordSet ? android.view.View.VISIBLE : android.view.View.GONE);
checkBoxAccept.setVisibility(isChecked ? android.view.View.GONE : android.view.View.VISIBLE);
checkBoxAccept.setChecked(false);
stateValidator.enableIfValid();
});
checkBoxAccept.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
stateValidator.enableIfValid();
});
checkBoxExportAllGroups.setOnCheckedChangeListener((android.widget.CompoundButton button,boolean isChecked) -> {
int visibility;
visibility = (isChecked) ? android.view.View.GONE : android.view.View.VISIBLE;
groupsSelectionLayout.setVisibility(visibility);
stateValidator.enableIfValid();
});
groupsSelection.setOnDismissListener(stateValidator::enableIfValid);
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_31_BuggyGUIListenerOperatorMutator
case 31137: {
btnPos.setOnClickListener(null);
break;
}
default: {
btnPos.setOnClickListener((android.view.View v) -> {
dialog.dismiss();
if ((!checkBoxEncrypt.isChecked()) && (!checkBoxAccept.isChecked())) {
return;
}
if (!checkBoxExportAllGroups.isChecked()) {
_exportFilter = getVaultEntryFilter(groupsSelection);
if (_exportFilter == null) {
android.widget.Toast.makeText(requireContext(), com.beemdevelopment.aegis.R.string.export_no_groups_selected, android.widget.Toast.LENGTH_SHORT).show();
return;
}
}
int pos;
pos = getStringResourceIndex(com.beemdevelopment.aegis.R.array.export_formats, dropdown.getText().toString());
int requestCode;
requestCode = com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.getExportRequestCode(pos, checkBoxEncrypt.isChecked());
com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo fileInfo;
fileInfo = com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.getExportFileInfo(pos, checkBoxEncrypt.isChecked());
android.content.Intent intent;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_32_RandomActionIntentDefinitionOperatorMutator
case 32137: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT).addCategory(android.content.Intent.CATEGORY_OPENABLE).setType(com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.getExportMimeType(requestCode)).putExtra(android.content.Intent.EXTRA_TITLE, fileInfo.toString());
break;
}
}
_vaultManager.startActivityForResult(this, intent, requestCode);
});
break;
}
}
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_33_BuggyGUIListenerOperatorMutator
case 33137: {
btnNeutral.setOnClickListener(null);
break;
}
default: {
btnNeutral.setOnClickListener((android.view.View v) -> {
dialog.dismiss();
int pos;
pos = getStringResourceIndex(com.beemdevelopment.aegis.R.array.export_formats, dropdown.getText().toString());
if ((!checkBoxEncrypt.isChecked()) && (!checkBoxAccept.isChecked())) {
return;
}
if (!checkBoxExportAllGroups.isChecked()) {
_exportFilter = getVaultEntryFilter(groupsSelection);
if (_exportFilter == null) {
return;
}
}
java.io.File file;
try {
com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo fileInfo;
fileInfo = com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.getExportFileInfo(pos, checkBoxEncrypt.isChecked());
file = java.io.File.createTempFile(fileInfo.getFilename() + "-", "." + fileInfo.getExtension(), getExportCacheDir());
} catch (java.io.IOException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.exporting_vault_error, e);
return;
}
int requestCode;
requestCode = com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.getExportRequestCode(pos, checkBoxEncrypt.isChecked());
startExportVault(requestCode, (com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.FinishExportCallback cb) -> {
try (java.io.OutputStream stream = new java.io.FileOutputStream(file)) {
cb.exportVault(stream);
} catch (java.io.IOException | com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.exporting_vault_error, e);
return;
}
// if the user creates an export, hide the backup reminder
_prefs.setLatestExportTimeNow();
android.net.Uri uri;
uri = androidx.core.content.FileProvider.getUriForFile(requireContext(), com.beemdevelopment.aegis.BuildConfig.FILE_PROVIDER_AUTHORITY, file);
android.content.Intent intent;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_34_RandomActionIntentDefinitionOperatorMutator
case 34137: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND).setFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION).setType(com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.getExportMimeType(requestCode)).putExtra(android.content.Intent.EXTRA_STREAM, uri);
break;
}
}
android.content.Intent chooser;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_35_RandomActionIntentDefinitionOperatorMutator
case 35137: {
chooser = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
chooser = android.content.Intent.createChooser(intent, getString(com.beemdevelopment.aegis.R.string.pref_export_summary));
break;
}
}
_vaultManager.startActivity(this, chooser);
}, _exportFilter);
_exportFilter = null;
});
break;
}
}
});
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


private com.beemdevelopment.aegis.vault.Vault.EntryFilter getVaultEntryFilter(com.beemdevelopment.aegis.ui.components.DropdownCheckBoxes dropdownCheckBoxes) {
java.util.Set<java.lang.String> groups;
groups = new java.util.HashSet<>();
for (java.lang.String group : dropdownCheckBoxes.getCheckedItems()) {
if (group.equals(getString(com.beemdevelopment.aegis.R.string.no_group))) {
groups.add(null);
} else {
groups.add(group);
}
}
return groups.isEmpty() ? null : (com.beemdevelopment.aegis.vault.VaultEntry entry) -> groups.contains(entry.getGroup());
}


private void startGoogleAuthenticatorStyleExport() {
java.util.ArrayList<com.beemdevelopment.aegis.otp.GoogleAuthInfo> toExport;
toExport = new java.util.ArrayList<>();
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _vaultManager.getVault().getEntries()) {
java.lang.String type;
type = entry.getInfo().getType().toLowerCase();
java.lang.String algo;
algo = entry.getInfo().getAlgorithm(false);
int digits;
digits = entry.getInfo().getDigits();
if (((java.util.Objects.equals(type, com.beemdevelopment.aegis.otp.TotpInfo.ID) || java.util.Objects.equals(type, com.beemdevelopment.aegis.otp.HotpInfo.ID)) && (digits == com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_DIGITS)) && java.util.Objects.equals(algo, com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM)) {
com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
info = new com.beemdevelopment.aegis.otp.GoogleAuthInfo(entry.getInfo(), entry.getName(), entry.getIssuer());
toExport.add(info);
}
}
int entriesSkipped;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_36_BinaryMutator
case 36137: {
entriesSkipped = _vaultManager.getVault().getEntries().size() + toExport.size();
break;
}
default: {
entriesSkipped = _vaultManager.getVault().getEntries().size() - toExport.size();
break;
}
}
if (entriesSkipped > 0) {
java.lang.String text;
text = requireContext().getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.pref_google_auth_export_incompatible_entries, entriesSkipped, entriesSkipped);
android.widget.Toast.makeText(requireContext(), text, android.widget.Toast.LENGTH_SHORT).show();
}
int qrSize;
qrSize = 10;
int batchId;
batchId = new java.util.Random().nextInt();
int batchSize;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_37_BinaryMutator
case 37137: {
batchSize = (toExport.size() / qrSize) - ((toExport.size() % qrSize) == 0 ? 0 : 1);
break;
}
default: {
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_38_BinaryMutator
case 38137: {
batchSize = (toExport.size() * qrSize) + ((toExport.size() % qrSize) == 0 ? 0 : 1);
break;
}
default: {
batchSize = (toExport.size() / qrSize) + ((toExport.size() % qrSize) == 0 ? 0 : 1);
break;
}
}
break;
}
}
java.util.List<com.beemdevelopment.aegis.otp.GoogleAuthInfo> infos;
infos = new java.util.ArrayList<>();
java.util.ArrayList<com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export> exports;
exports = new java.util.ArrayList<>();
for (int i = 0, batchIndex = 0; i < toExport.size(); i++) {
infos.add(toExport.get(i));
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_39_BinaryMutator
case 39137: {
if ((infos.size() == qrSize) || (toExport.size() == (i - 1))) {
exports.add(new com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export(infos, batchId, batchIndex++, batchSize));
infos = new java.util.ArrayList<>();
}
break;
}
default: {
if ((infos.size() == qrSize) || (toExport.size() == (i + 1))) {
exports.add(new com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export(infos, batchId, batchIndex++, batchSize));
infos = new java.util.ArrayList<>();
}
break;
}
}
}
if (exports.size() == 0) {
android.widget.Toast.makeText(requireContext(), com.beemdevelopment.aegis.R.string.pref_google_auth_export_no_data, android.widget.Toast.LENGTH_SHORT).show();
} else {
android.content.Intent intent;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_40_NullIntentOperatorMutator
case 40137: {
intent = null;
break;
}
// ImportExportPreferencesFragment_41_InvalidKeyIntentOperatorMutator
case 41137: {
intent = new android.content.Intent((android.content.Context) null, com.beemdevelopment.aegis.ui.TransferEntriesActivity.class);
break;
}
// ImportExportPreferencesFragment_42_RandomActionIntentDefinitionOperatorMutator
case 42137: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(requireContext(), com.beemdevelopment.aegis.ui.TransferEntriesActivity.class);
break;
}
}
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_43_NullValueIntentPutExtraOperatorMutator
case 43137: {
intent.putExtra("authInfos", new Parcelable[0]);
break;
}
// ImportExportPreferencesFragment_44_IntentPayloadReplacementOperatorMutator
case 44137: {
intent.putExtra("authInfos", (java.util.ArrayList<com.beemdevelopment.aegis.otp.GoogleAuthInfo.Export>) null);
break;
}
default: {
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_45_RandomActionIntentDefinitionOperatorMutator
case 45137: {
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
intent.putExtra("authInfos", exports);
break;
}
}
break;
}
}
startActivity(intent);
}
}


private static int getExportRequestCode(int spinnerPos, boolean encrypt) {
if (spinnerPos == 0) {
return encrypt ? com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT : com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_PLAIN;
} else if (spinnerPos == 1) {
return com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_HTML;
}
return com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_GOOGLE_URI;
}


private static com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo getExportFileInfo(int spinnerPos, boolean encrypt) {
if (spinnerPos == 0) {
java.lang.String filename;
filename = (encrypt) ? com.beemdevelopment.aegis.vault.VaultRepository.FILENAME_PREFIX_EXPORT : com.beemdevelopment.aegis.vault.VaultRepository.FILENAME_PREFIX_EXPORT_PLAIN;
return new com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo(filename);
} else if (spinnerPos == 1) {
return new com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo(com.beemdevelopment.aegis.vault.VaultRepository.FILENAME_PREFIX_EXPORT_HTML, "html");
}
return new com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo(com.beemdevelopment.aegis.vault.VaultRepository.FILENAME_PREFIX_EXPORT_URI, "txt");
}


private static java.lang.String getExportMimeType(int requestCode) {
if (requestCode == com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_GOOGLE_URI) {
return "text/plain";
} else if (requestCode == com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_HTML) {
return "text/html";
}
return "application/json";
}


private java.io.File getExportCacheDir() throws java.io.IOException {
java.io.File dir;
dir = new java.io.File(requireContext().getCacheDir(), "export");
if ((!dir.exists()) && (!dir.mkdir())) {
throw new java.io.IOException(java.lang.String.format("Unable to create directory %s", dir));
}
return dir;
}


private void startExportVault(int requestCode, com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.StartExportCallback cb, @androidx.annotation.Nullable
com.beemdevelopment.aegis.vault.Vault.EntryFilter filter) {
switch (requestCode) {
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT :
if (_vaultManager.getVault().isEncryptionEnabled()) {
cb.exportVault((java.io.OutputStream stream) -> {
if (filter != null) {
_vaultManager.getVault().exportFiltered(stream, filter);
} else {
_vaultManager.getVault().export(stream);
}
});
} else {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSetPasswordDialog(requireActivity(), new com.beemdevelopment.aegis.ui.dialogs.Dialogs.PasswordSlotListener() {
@java.lang.Override
public void onSlotResult(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.Cipher cipher) {
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = new com.beemdevelopment.aegis.vault.VaultFileCredentials();
try {
slot.setKey(creds.getKey(), cipher);
creds.getSlots().add(slot);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
onException(e);
return;
}
cb.exportVault((java.io.OutputStream stream) -> {
if (filter != null) {
_vaultManager.getVault().exportFiltered(stream, creds, filter);
} else {
_vaultManager.getVault().export(stream, creds);
}
});
}


@java.lang.Override
public void onException(java.lang.Exception e) {
}

});
}
break;
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_PLAIN :
cb.exportVault((java.io.OutputStream stream) -> {
if (filter != null) {
_vaultManager.getVault().exportFiltered(stream, null, filter);
} else {
_vaultManager.getVault().export(stream, null);
}
});
_prefs.setIsPlaintextBackupWarningNeeded(true);
break;
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_GOOGLE_URI :
cb.exportVault((java.io.OutputStream stream) -> _vaultManager.getVault().exportGoogleUris(stream, filter));
_prefs.setIsPlaintextBackupWarningNeeded(true);
break;
case com.beemdevelopment.aegis.ui.fragments.preferences.PreferencesFragment.CODE_EXPORT_HTML :
cb.exportVault((java.io.OutputStream stream) -> _vaultManager.getVault().exportHtml(stream, filter));
_prefs.setIsPlaintextBackupWarningNeeded(true);
break;
}
}


private void onExportResult(int requestCode, int resultCode, android.content.Intent data) {
android.net.Uri uri;
uri = data.getData();
if ((resultCode != android.app.Activity.RESULT_OK) || (uri == null)) {
return;
}
startExportVault(requestCode, (com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.FinishExportCallback cb) -> {
java.io.File file;
java.io.OutputStream outStream;
outStream = null;
try {
file = java.io.File.createTempFile(com.beemdevelopment.aegis.vault.VaultRepository.FILENAME_PREFIX_EXPORT + "-", ".json", getExportCacheDir());
outStream = new java.io.FileOutputStream(file);
cb.exportVault(outStream);
new com.beemdevelopment.aegis.ui.tasks.ExportTask(requireContext(), new com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.ExportResultListener()).execute(getLifecycle(), new com.beemdevelopment.aegis.ui.tasks.ExportTask.Params(file, uri));
} catch (com.beemdevelopment.aegis.vault.VaultRepositoryException | java.io.IOException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.exporting_vault_error, e);
} finally {
try {
if (outStream != null) {
outStream.close();
}
} catch (java.io.IOException e) {
e.printStackTrace();
}
}
}, _exportFilter);
_exportFilter = null;
}


private int getStringResourceIndex(@androidx.annotation.ArrayRes
int id, java.lang.String string) {
java.lang.String[] res;
res = getResources().getStringArray(id);
for (int i = 0; i < res.length; i++) {
if (res[i].equalsIgnoreCase(string)) {
return i;
}
}
return -1;
}


private class ExportResultListener implements com.beemdevelopment.aegis.ui.tasks.ExportTask.Callback {
@java.lang.Override
public void onTaskFinished(java.lang.Exception e) {
if (e != null) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.exporting_vault_error, e);
} else {
// if the user creates an export, hide the backup reminder
_prefs.setLatestExportTimeNow();
android.widget.Toast.makeText(requireContext(), getString(com.beemdevelopment.aegis.R.string.exported_vault), android.widget.Toast.LENGTH_SHORT).show();
}
}

}

private interface FinishExportCallback {
void exportVault(java.io.OutputStream stream) throws java.io.IOException, com.beemdevelopment.aegis.vault.VaultRepositoryException;

}

private interface StartExportCallback {
void exportVault(com.beemdevelopment.aegis.ui.fragments.preferences.ImportExportPreferencesFragment.FinishExportCallback exportCb);

}

private interface DialogStateValidator {
void enableIfValid();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
