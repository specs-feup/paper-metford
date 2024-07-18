package de.danoeh.antennapod.fragment.preferences;
import de.danoeh.antennapod.core.export.ExportWriter;
import java.util.Locale;
import io.reactivex.Completable;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import de.danoeh.antennapod.core.export.html.HtmlWriter;
import androidx.core.app.ShareCompat;
import android.net.Uri;
import android.app.ProgressDialog;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.export.favorites.FavoritesWriter;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.preference.PreferenceFragmentCompat;
import de.danoeh.antennapod.activity.OpmlImportActivity;
import de.danoeh.antennapod.activity.PreferenceActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.util.Log;
import de.danoeh.antennapod.core.storage.DatabaseExporter;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import androidx.activity.result.contract.ActivityResultContracts;
import io.reactivex.Observable;
import android.content.Intent;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import androidx.activity.result.ActivityResult;
import java.util.Date;
import de.danoeh.antennapod.PodcastApp;
import de.danoeh.antennapod.asynctask.DocumentFileExportWorker;
import de.danoeh.antennapod.asynctask.ExportWorker;
import androidx.activity.result.contract.ActivityResultContracts.GetContent;
import java.text.SimpleDateFormat;
import androidx.core.content.FileProvider;
import android.content.ActivityNotFoundException;
import androidx.activity.result.ActivityResultLauncher;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import de.danoeh.antennapod.core.export.opml.OpmlWriter;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImportExportPreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "ImportExPrefFragment";

    private static final java.lang.String PREF_OPML_EXPORT = "prefOpmlExport";

    private static final java.lang.String PREF_OPML_IMPORT = "prefOpmlImport";

    private static final java.lang.String PREF_HTML_EXPORT = "prefHtmlExport";

    private static final java.lang.String PREF_DATABASE_IMPORT = "prefDatabaseImport";

    private static final java.lang.String PREF_DATABASE_EXPORT = "prefDatabaseExport";

    private static final java.lang.String PREF_FAVORITE_EXPORT = "prefFavoritesExport";

    private static final java.lang.String DEFAULT_OPML_OUTPUT_NAME = "antennapod-feeds-%s.opml";

    private static final java.lang.String CONTENT_TYPE_OPML = "text/x-opml";

    private static final java.lang.String DEFAULT_HTML_OUTPUT_NAME = "antennapod-feeds-%s.html";

    private static final java.lang.String CONTENT_TYPE_HTML = "text/html";

    private static final java.lang.String DEFAULT_FAVORITES_OUTPUT_NAME = "antennapod-favorites-%s.html";

    private static final java.lang.String DATABASE_EXPORT_FILENAME = "AntennaPodBackup-%s.db";

    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> chooseOpmlExportPathLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(), this::chooseOpmlExportPathResult);

    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> chooseHtmlExportPathLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(), this::chooseHtmlExportPathResult);

    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> chooseFavoritesExportPathLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(), this::chooseFavoritesExportPathResult);

    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> restoreDatabaseLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(), this::restoreDatabaseResult);

    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> backupDatabaseLauncher = registerForActivityResult(new de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.BackupDatabase(), this::backupDatabaseResult);

    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> chooseOpmlImportPathLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.GetContent(), this::chooseOpmlImportPathResult);

    private io.reactivex.disposables.Disposable disposable;

    private android.app.ProgressDialog progressDialog;

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_import_export);
        setupStorageScreen();
        progressDialog = new android.app.ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getContext().getString(de.danoeh.antennapod.R.string.please_wait));
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.import_export_pref);
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
        }
    }


    private java.lang.String dateStampFilename(java.lang.String fname) {
        return java.lang.String.format(fname, new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.US).format(new java.util.Date()));
    }


    private void setupStorageScreen() {
        findPreference(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.PREF_OPML_EXPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            openExportPathPicker(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export.OPML, chooseOpmlExportPathLauncher, new de.danoeh.antennapod.core.export.opml.OpmlWriter());
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.PREF_HTML_EXPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            openExportPathPicker(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export.HTML, chooseHtmlExportPathLauncher, new de.danoeh.antennapod.core.export.html.HtmlWriter());
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.PREF_OPML_IMPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            try {
                chooseOpmlImportPathLauncher.launch("*/*");
            } catch (android.content.ActivityNotFoundException e) {
                android.util.Log.e(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.TAG, "No activity found. Should never happen...");
            }
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.PREF_DATABASE_IMPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            importDatabase();
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.PREF_DATABASE_EXPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            exportDatabase();
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.PREF_FAVORITE_EXPORT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            openExportPathPicker(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export.FAVORITES, chooseFavoritesExportPathLauncher, new de.danoeh.antennapod.core.export.favorites.FavoritesWriter());
            return true;
        });
    }


    private void exportWithWriter(de.danoeh.antennapod.core.export.ExportWriter exportWriter, android.net.Uri uri, de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export exportType) {
        android.content.Context context;
        context = getActivity();
        progressDialog.show();
        if (uri == null) {
            io.reactivex.Observable<java.io.File> observable;
            observable = new de.danoeh.antennapod.asynctask.ExportWorker(exportWriter, getContext()).exportObservable();
            disposable = observable.subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.io.File output) -> {
                android.net.Uri fileUri;
                fileUri = androidx.core.content.FileProvider.getUriForFile(context.getApplicationContext(), context.getString(de.danoeh.antennapod.R.string.provider_authority), output);
                showExportSuccessSnackbar(fileUri, exportType.contentType);
            }, this::showExportErrorDialog, progressDialog::dismiss);
        } else {
            de.danoeh.antennapod.asynctask.DocumentFileExportWorker worker;
            worker = new de.danoeh.antennapod.asynctask.DocumentFileExportWorker(exportWriter, context, uri);
            disposable = worker.exportObservable().subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((androidx.documentfile.provider.DocumentFile output) -> showExportSuccessSnackbar(output.getUri(), exportType.contentType), this::showExportErrorDialog, progressDialog::dismiss);
        }
    }


    private void exportDatabase() {
        backupDatabaseLauncher.launch(dateStampFilename(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.DATABASE_EXPORT_FILENAME));
    }


    private void importDatabase() {
        // setup the alert builder
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getActivity());
        builder.setTitle(de.danoeh.antennapod.R.string.database_import_label);
        builder.setMessage(de.danoeh.antennapod.R.string.database_import_warning);
        // add a button
        builder.setNegativeButton(de.danoeh.antennapod.R.string.no, null);
        switch(MUID_STATIC) {
            // ImportExportPreferencesFragment_0_BuggyGUIListenerOperatorMutator
            case 100: {
                builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
                break;
            }
            default: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
                android.content.Intent intent;
                switch(MUID_STATIC) {
                    // ImportExportPreferencesFragment_1_InvalidKeyIntentOperatorMutator
                    case 1100: {
                        intent = new android.content.Intent((String) null);
                        break;
                    }
                    // ImportExportPreferencesFragment_2_RandomActionIntentDefinitionOperatorMutator
                    case 2100: {
                        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                        break;
                    }
                    default: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_OPEN_DOCUMENT);
                    break;
                }
            }
            switch(MUID_STATIC) {
                // ImportExportPreferencesFragment_3_RandomActionIntentDefinitionOperatorMutator
                case 3100: {
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
        restoreDatabaseLauncher.launch(intent);
    });
    break;
}
}
// create and show the alert dialog
builder.show();
}


private void showDatabaseImportSuccessDialog() {
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
builder.setTitle(de.danoeh.antennapod.R.string.successful_import_label);
builder.setMessage(de.danoeh.antennapod.R.string.import_ok);
builder.setCancelable(false);
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_4_BuggyGUIListenerOperatorMutator
case 4100: {
    builder.setPositiveButton(android.R.string.ok, null);
    break;
}
default: {
builder.setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialogInterface,int i) -> de.danoeh.antennapod.PodcastApp.forceRestart());
break;
}
}
builder.show();
}


void showExportSuccessSnackbar(android.net.Uri uri, java.lang.String mimeType) {
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_5_BuggyGUIListenerOperatorMutator
case 5100: {
com.google.android.material.snackbar.Snackbar.make(getView(), de.danoeh.antennapod.R.string.export_success_title, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(de.danoeh.antennapod.R.string.share_label, null).show();
break;
}
default: {
com.google.android.material.snackbar.Snackbar.make(getView(), de.danoeh.antennapod.R.string.export_success_title, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(de.danoeh.antennapod.R.string.share_label, (android.view.View v) -> new androidx.core.app.ShareCompat.IntentBuilder(getContext()).setType(mimeType).addStream(uri).setChooserTitle(de.danoeh.antennapod.R.string.share_label).startChooser()).show();
break;
}
}
}


private void showExportErrorDialog(final java.lang.Throwable error) {
progressDialog.dismiss();
final com.google.android.material.dialog.MaterialAlertDialogBuilder alert;
alert = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_6_BuggyGUIListenerOperatorMutator
case 6100: {
alert.setPositiveButton(android.R.string.ok, null);
break;
}
default: {
alert.setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> dialog.dismiss());
break;
}
}
alert.setTitle(de.danoeh.antennapod.R.string.export_error_label);
alert.setMessage(error.getMessage());
alert.show();
}


private void chooseOpmlExportPathResult(final androidx.activity.result.ActivityResult result) {
if ((result.getResultCode() != android.app.Activity.RESULT_OK) || (result.getData() == null)) {
return;
}
final android.net.Uri uri;
uri = result.getData().getData();
exportWithWriter(new de.danoeh.antennapod.core.export.opml.OpmlWriter(), uri, de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export.OPML);
}


private void chooseHtmlExportPathResult(final androidx.activity.result.ActivityResult result) {
if ((result.getResultCode() != android.app.Activity.RESULT_OK) || (result.getData() == null)) {
return;
}
final android.net.Uri uri;
uri = result.getData().getData();
exportWithWriter(new de.danoeh.antennapod.core.export.html.HtmlWriter(), uri, de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export.HTML);
}


private void chooseFavoritesExportPathResult(final androidx.activity.result.ActivityResult result) {
if ((result.getResultCode() != android.app.Activity.RESULT_OK) || (result.getData() == null)) {
return;
}
final android.net.Uri uri;
uri = result.getData().getData();
exportWithWriter(new de.danoeh.antennapod.core.export.favorites.FavoritesWriter(), uri, de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export.FAVORITES);
}


private void restoreDatabaseResult(final androidx.activity.result.ActivityResult result) {
if ((result.getResultCode() != android.app.Activity.RESULT_OK) || (result.getData() == null)) {
return;
}
final android.net.Uri uri;
uri = result.getData().getData();
progressDialog.show();
disposable = io.reactivex.Completable.fromAction(() -> de.danoeh.antennapod.core.storage.DatabaseExporter.importBackup(uri, getContext())).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> {
showDatabaseImportSuccessDialog();
progressDialog.dismiss();
}, this::showExportErrorDialog);
}


private void backupDatabaseResult(final android.net.Uri uri) {
if (uri == null) {
return;
}
progressDialog.show();
disposable = io.reactivex.Completable.fromAction(() -> de.danoeh.antennapod.core.storage.DatabaseExporter.exportToDocument(uri, getContext())).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> {
showExportSuccessSnackbar(uri, "application/x-sqlite3");
progressDialog.dismiss();
}, this::showExportErrorDialog);
}


private void chooseOpmlImportPathResult(final android.net.Uri uri) {
if (uri == null) {
return;
}
final android.content.Intent intent;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_7_NullIntentOperatorMutator
case 7100: {
intent = null;
break;
}
// ImportExportPreferencesFragment_8_InvalidKeyIntentOperatorMutator
case 8100: {
intent = new android.content.Intent((android.content.Context) null, de.danoeh.antennapod.activity.OpmlImportActivity.class);
break;
}
// ImportExportPreferencesFragment_9_RandomActionIntentDefinitionOperatorMutator
case 9100: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(getContext(), de.danoeh.antennapod.activity.OpmlImportActivity.class);
break;
}
}
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_10_RandomActionIntentDefinitionOperatorMutator
case 10100: {
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
intent.setData(uri);
break;
}
}
startActivity(intent);
}


private void openExportPathPicker(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.Export exportType, androidx.activity.result.ActivityResultLauncher<android.content.Intent> result, de.danoeh.antennapod.core.export.ExportWriter writer) {
java.lang.String title;
title = dateStampFilename(exportType.outputNameTemplate);
android.content.Intent intentPickAction;
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_11_RandomActionIntentDefinitionOperatorMutator
case 11100: {
intentPickAction = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentPickAction = new android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT).addCategory(android.content.Intent.CATEGORY_OPENABLE).setType(exportType.contentType).putExtra(android.content.Intent.EXTRA_TITLE, title);
break;
}
}
// Creates an implicit intent to launch a file manager which lets
// the user choose a specific directory to export to.
try {
result.launch(intentPickAction);
return;
} catch (android.content.ActivityNotFoundException e) {
android.util.Log.e(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.TAG, "No activity found. Should never happen...");
}
// If we are using a SDK lower than API 21 or the implicit intent failed
// fallback to the legacy export process
exportWithWriter(writer, null, exportType);
}


private static class BackupDatabase extends androidx.activity.result.contract.ActivityResultContracts.CreateDocument {
@androidx.annotation.NonNull
@java.lang.Override
public android.content.Intent createIntent(@androidx.annotation.NonNull
final android.content.Context context, @androidx.annotation.NonNull
final java.lang.String input) {
switch(MUID_STATIC) {
// ImportExportPreferencesFragment_12_RandomActionIntentDefinitionOperatorMutator
case 12100: {
return new android.content.Intent(android.content.Intent.ACTION_SEND);
}
default: {
return super.createIntent(context, input).addCategory(android.content.Intent.CATEGORY_OPENABLE).setType("application/x-sqlite3");
}
}
}

}

private enum Export {

OPML(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.CONTENT_TYPE_OPML, de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.DEFAULT_OPML_OUTPUT_NAME, de.danoeh.antennapod.R.string.opml_export_label),
HTML(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.CONTENT_TYPE_HTML, de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.DEFAULT_HTML_OUTPUT_NAME, de.danoeh.antennapod.R.string.html_export_label),
FAVORITES(de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.CONTENT_TYPE_HTML, de.danoeh.antennapod.fragment.preferences.ImportExportPreferencesFragment.DEFAULT_FAVORITES_OUTPUT_NAME, de.danoeh.antennapod.R.string.favorites_export_label);
final java.lang.String contentType;

final java.lang.String outputNameTemplate;

@androidx.annotation.StringRes
final int labelResId;

Export(java.lang.String contentType, java.lang.String outputNameTemplate, int labelResId) {
this.contentType = contentType;
this.outputNameTemplate = outputNameTemplate;
this.labelResId = labelResId;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
