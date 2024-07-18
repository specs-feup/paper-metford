package com.beemdevelopment.aegis.ui;
import com.beemdevelopment.aegis.util.UUIDMap;
import androidx.appcompat.app.AlertDialog;
import com.beemdevelopment.aegis.importers.DatabaseImporterException;
import java.io.FileNotFoundException;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.importers.DatabaseImporter;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.content.pm.PackageManager;
import android.widget.Toast;
import java.util.List;
import androidx.appcompat.app.ActionBar;
import com.beemdevelopment.aegis.ui.tasks.RootShellTask;
import android.view.Menu;
import com.beemdevelopment.aegis.ui.views.ImportEntriesAdapter;
import android.os.Bundle;
import java.io.InputStream;
import java.io.IOException;
import com.beemdevelopment.aegis.ui.models.ImportEntry;
import android.view.MenuItem;
import java.io.FileInputStream;
import com.beemdevelopment.aegis.importers.DatabaseImporterEntryException;
import com.beemdevelopment.aegis.vault.VaultRepository;
import com.beemdevelopment.aegis.helpers.FabScrollHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImportEntriesActivity extends com.beemdevelopment.aegis.ui.AegisActivity {
    static final int MUID_STATIC = getMUID();
    private android.view.Menu _menu;

    private com.beemdevelopment.aegis.ui.views.ImportEntriesAdapter _adapter;

    private com.beemdevelopment.aegis.helpers.FabScrollHelper _fabScrollHelper;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // ImportEntriesActivity_0_LengthyGUICreationOperatorMutator
            case 165: {
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
    if (abortIfOrphan(savedInstanceState)) {
        return;
    }
    setContentView(com.beemdevelopment.aegis.R.layout.activity_import_entries);
    switch(MUID_STATIC) {
        // ImportEntriesActivity_1_InvalidIDFindViewOperatorMutator
        case 1165: {
            setSupportActionBar(findViewById(732221));
            break;
        }
        default: {
        setSupportActionBar(findViewById(com.beemdevelopment.aegis.R.id.toolbar));
        break;
    }
}
androidx.appcompat.app.ActionBar bar;
bar = getSupportActionBar();
bar.setHomeAsUpIndicator(com.beemdevelopment.aegis.R.drawable.ic_close);
bar.setDisplayHomeAsUpEnabled(true);
_adapter = new com.beemdevelopment.aegis.ui.views.ImportEntriesAdapter();
androidx.recyclerview.widget.RecyclerView entriesView;
switch(MUID_STATIC) {
    // ImportEntriesActivity_2_FindViewByIdReturnsNullOperatorMutator
    case 2165: {
        entriesView = null;
        break;
    }
    // ImportEntriesActivity_3_InvalidIDFindViewOperatorMutator
    case 3165: {
        entriesView = findViewById(732221);
        break;
    }
    // ImportEntriesActivity_4_InvalidViewFocusOperatorMutator
    case 4165: {
        /**
        * Inserted by Kadabra
        */
        entriesView = findViewById(com.beemdevelopment.aegis.R.id.list_entries);
        entriesView.requestFocus();
        break;
    }
    // ImportEntriesActivity_5_ViewComponentNotVisibleOperatorMutator
    case 5165: {
        /**
        * Inserted by Kadabra
        */
        entriesView = findViewById(com.beemdevelopment.aegis.R.id.list_entries);
        entriesView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    entriesView = findViewById(com.beemdevelopment.aegis.R.id.list_entries);
    break;
}
}
entriesView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
@java.lang.Override
public void onScrolled(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);
    _fabScrollHelper.onScroll(dx, dy);
}

});
androidx.recyclerview.widget.LinearLayoutManager layoutManager;
layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(this);
entriesView.setLayoutManager(layoutManager);
entriesView.setAdapter(_adapter);
entriesView.setNestedScrollingEnabled(false);
com.google.android.material.floatingactionbutton.FloatingActionButton fab;
switch(MUID_STATIC) {
// ImportEntriesActivity_6_FindViewByIdReturnsNullOperatorMutator
case 6165: {
    fab = null;
    break;
}
// ImportEntriesActivity_7_InvalidIDFindViewOperatorMutator
case 7165: {
    fab = findViewById(732221);
    break;
}
// ImportEntriesActivity_8_InvalidViewFocusOperatorMutator
case 8165: {
    /**
    * Inserted by Kadabra
    */
    fab = findViewById(com.beemdevelopment.aegis.R.id.fab);
    fab.requestFocus();
    break;
}
// ImportEntriesActivity_9_ViewComponentNotVisibleOperatorMutator
case 9165: {
    /**
    * Inserted by Kadabra
    */
    fab = findViewById(com.beemdevelopment.aegis.R.id.fab);
    fab.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
fab = findViewById(com.beemdevelopment.aegis.R.id.fab);
break;
}
}
switch(MUID_STATIC) {
// ImportEntriesActivity_10_BuggyGUIListenerOperatorMutator
case 10165: {
fab.setOnClickListener(null);
break;
}
default: {
fab.setOnClickListener((android.view.View v) -> {
if ((_vaultManager.getVault().getEntries().size() > 0) && _menu.findItem(com.beemdevelopment.aegis.R.id.toggle_wipe_vault).isChecked()) {
    showWipeEntriesDialog();
} else {
    saveAndFinish(false);
}
});
break;
}
}
_fabScrollHelper = new com.beemdevelopment.aegis.helpers.FabScrollHelper(fab);
com.beemdevelopment.aegis.importers.DatabaseImporter.Definition importerDef;
importerDef = ((com.beemdevelopment.aegis.importers.DatabaseImporter.Definition) (getIntent().getSerializableExtra("importerDef")));
startImport(importerDef, ((java.io.File) (getIntent().getSerializableExtra("file"))));
}


private void startImport(com.beemdevelopment.aegis.importers.DatabaseImporter.Definition importerDef, @androidx.annotation.Nullable
java.io.File file) {
com.beemdevelopment.aegis.importers.DatabaseImporter importer;
importer = com.beemdevelopment.aegis.importers.DatabaseImporter.create(this, importerDef.getType());
if (file == null) {
if (importer.isInstalledAppVersionSupported()) {
startImportApp(importer);
} else {
switch(MUID_STATIC) {
// ImportEntriesActivity_11_BuggyGUIListenerOperatorMutator
case 11165: {
    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.warning).setMessage(getString(com.beemdevelopment.aegis.R.string.app_version_error, importerDef.getName())).setCancelable(false).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, null).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog1,int which) -> {
        finish();
    }).create());
    break;
}
default: {
switch(MUID_STATIC) {
    // ImportEntriesActivity_12_BuggyGUIListenerOperatorMutator
    case 12165: {
        com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.warning).setMessage(getString(com.beemdevelopment.aegis.R.string.app_version_error, importerDef.getName())).setCancelable(false).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog1,int which) -> {
            startImportApp(importer);
        }).setNegativeButton(com.beemdevelopment.aegis.R.string.no, null).create());
        break;
    }
    default: {
    com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(this).setTitle(com.beemdevelopment.aegis.R.string.warning).setMessage(getString(com.beemdevelopment.aegis.R.string.app_version_error, importerDef.getName())).setCancelable(false).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog1,int which) -> {
        startImportApp(importer);
    }).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog1,int which) -> {
        finish();
    }).create());
    break;
}
}
break;
}
}
}
} else {
startImportFile(importer, file);
}
}


private void startImportFile(@androidx.annotation.NonNull
com.beemdevelopment.aegis.importers.DatabaseImporter importer, @androidx.annotation.NonNull
java.io.File file) {
switch(MUID_STATIC) {
// ImportEntriesActivity_13_BuggyGUIListenerOperatorMutator
case 13165: {
try (java.io.InputStream stream = new java.io.FileInputStream(file)) {
com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
state = importer.read(stream);
processImporterState(state);
} catch (java.io.FileNotFoundException e) {
android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.file_not_found, android.widget.Toast.LENGTH_SHORT).show();
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException | java.io.IOException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.reading_file_error, e, null);
}
break;
}
default: {
try (java.io.InputStream stream = new java.io.FileInputStream(file)) {
com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
state = importer.read(stream);
processImporterState(state);
} catch (java.io.FileNotFoundException e) {
android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.file_not_found, android.widget.Toast.LENGTH_SHORT).show();
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException | java.io.IOException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.reading_file_error, e, (android.content.DialogInterface dialog,int which) -> finish());
}
break;
}
}
}


private void startImportApp(@androidx.annotation.NonNull
com.beemdevelopment.aegis.importers.DatabaseImporter importer) {
com.beemdevelopment.aegis.ui.tasks.RootShellTask task;
task = new com.beemdevelopment.aegis.ui.tasks.RootShellTask(this, (com.topjohnwu.superuser.Shell shell) -> {
if (isFinishing()) {
return;
}
if ((shell == null) || (!shell.isRoot())) {
android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.root_error, android.widget.Toast.LENGTH_SHORT).show();
finish();
return;
}
switch(MUID_STATIC) {
// ImportEntriesActivity_14_BuggyGUIListenerOperatorMutator
case 14165: {
try {
com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
state = importer.readFromApp(shell);
processImporterState(state);
} catch (android.content.pm.PackageManager.NameNotFoundException e) {
e.printStackTrace();
android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.app_lookup_error, android.widget.Toast.LENGTH_SHORT).show();
finish();
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.reading_file_error, e, null);
} finally {
try {
shell.close();
} catch (java.io.IOException e) {
e.printStackTrace();
}
}
break;
}
default: {
try {
com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
state = importer.readFromApp(shell);
processImporterState(state);
} catch (android.content.pm.PackageManager.NameNotFoundException e) {
e.printStackTrace();
android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.app_lookup_error, android.widget.Toast.LENGTH_SHORT).show();
finish();
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.reading_file_error, e, (android.content.DialogInterface dialog,int which) -> finish());
} finally {
try {
shell.close();
} catch (java.io.IOException e) {
e.printStackTrace();
}
}
break;
}
}
});
task.execute(this);
}


private void processImporterState(com.beemdevelopment.aegis.importers.DatabaseImporter.State state) {
switch(MUID_STATIC) {
// ImportEntriesActivity_15_BuggyGUIListenerOperatorMutator
case 15165: {
try {
if (state.isEncrypted()) {
state.decrypt(this, new com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener() {
@java.lang.Override
public void onStateDecrypted(com.beemdevelopment.aegis.importers.DatabaseImporter.State state) {
importDatabase(state);
}


@java.lang.Override
public void onError(java.lang.Exception e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(com.beemdevelopment.aegis.ui.ImportEntriesActivity.this, com.beemdevelopment.aegis.R.string.decryption_error, e, null);
}


@java.lang.Override
public void onCanceled() {
finish();
}

});
} else {
importDatabase(state);
}
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.parsing_file_error, e, (android.content.DialogInterface dialog,int which) -> finish());
}
break;
}
default: {
switch(MUID_STATIC) {
// ImportEntriesActivity_16_BuggyGUIListenerOperatorMutator
case 16165: {
try {
if (state.isEncrypted()) {
state.decrypt(this, new com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener() {
@java.lang.Override
public void onStateDecrypted(com.beemdevelopment.aegis.importers.DatabaseImporter.State state) {
importDatabase(state);
}


@java.lang.Override
public void onError(java.lang.Exception e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(com.beemdevelopment.aegis.ui.ImportEntriesActivity.this, com.beemdevelopment.aegis.R.string.decryption_error, e, (android.content.DialogInterface dialog,int which) -> finish());
}


@java.lang.Override
public void onCanceled() {
finish();
}

});
} else {
importDatabase(state);
}
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.parsing_file_error, e, null);
}
break;
}
default: {
try {
if (state.isEncrypted()) {
state.decrypt(this, new com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener() {
@java.lang.Override
public void onStateDecrypted(com.beemdevelopment.aegis.importers.DatabaseImporter.State state) {
importDatabase(state);
}


@java.lang.Override
public void onError(java.lang.Exception e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(com.beemdevelopment.aegis.ui.ImportEntriesActivity.this, com.beemdevelopment.aegis.R.string.decryption_error, e, (android.content.DialogInterface dialog,int which) -> finish());
}


@java.lang.Override
public void onCanceled() {
finish();
}

});
} else {
importDatabase(state);
}
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.parsing_file_error, e, (android.content.DialogInterface dialog,int which) -> finish());
}
break;
}
}
break;
}
}
}


private void importDatabase(com.beemdevelopment.aegis.importers.DatabaseImporter.State state) {
com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
switch(MUID_STATIC) {
// ImportEntriesActivity_17_BuggyGUIListenerOperatorMutator
case 17165: {
try {
result = state.convert();
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.parsing_file_error, e, null);
return;
}
break;
}
default: {
try {
result = state.convert();
} catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.parsing_file_error, e, (android.content.DialogInterface dialog,int which) -> finish());
return;
}
break;
}
}
com.beemdevelopment.aegis.util.UUIDMap<com.beemdevelopment.aegis.vault.VaultEntry> entries;
entries = result.getEntries();
for (com.beemdevelopment.aegis.vault.VaultEntry entry : entries.getValues()) {
_adapter.addEntry(new com.beemdevelopment.aegis.ui.models.ImportEntry(entry));
}
java.util.List<com.beemdevelopment.aegis.importers.DatabaseImporterEntryException> errors;
errors = result.getErrors();
if (errors.size() > 0) {
java.lang.String message;
message = getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.import_error_dialog, errors.size(), errors.size());
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showMultiErrorDialog(this, com.beemdevelopment.aegis.R.string.import_error_title, message, errors, null);
}
}


private void showWipeEntriesDialog() {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showCheckboxDialog(this, com.beemdevelopment.aegis.R.string.dialog_wipe_entries_title, com.beemdevelopment.aegis.R.string.dialog_wipe_entries_message, com.beemdevelopment.aegis.R.string.dialog_wipe_entries_checkbox, this::saveAndFinish);
}


private void saveAndFinish(boolean wipeEntries) {
com.beemdevelopment.aegis.vault.VaultRepository vault;
vault = _vaultManager.getVault();
if (wipeEntries) {
vault.wipeEntries();
}
java.util.List<com.beemdevelopment.aegis.ui.models.ImportEntry> selectedEntries;
selectedEntries = _adapter.getCheckedEntries();
for (com.beemdevelopment.aegis.ui.models.ImportEntry selectedEntry : selectedEntries) {
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = selectedEntry.getEntry();
// temporary: randomize the UUID of duplicate entries and add them anyway
if (vault.isEntryDuplicate(entry)) {
entry.resetUUID();
}
vault.addEntry(entry);
}
if (saveAndBackupVault()) {
java.lang.String toastMessage;
toastMessage = getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.imported_entries_count, selectedEntries.size(), selectedEntries.size());
android.widget.Toast.makeText(this, toastMessage, android.widget.Toast.LENGTH_SHORT).show();
setResult(android.app.Activity.RESULT_OK, null);
finish();
}
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
_menu = menu;
getMenuInflater().inflate(com.beemdevelopment.aegis.R.menu.menu_import_entries, _menu);
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case android.R.id.home :
finish();
break;
case com.beemdevelopment.aegis.R.id.toggle_checkboxes :
_adapter.toggleCheckboxes();
break;
case com.beemdevelopment.aegis.R.id.toggle_wipe_vault :
item.setChecked(!item.isChecked());
break;
default :
return super.onOptionsItemSelected(item);
}
return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
