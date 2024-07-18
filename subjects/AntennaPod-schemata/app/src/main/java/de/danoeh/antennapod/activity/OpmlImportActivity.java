package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.core.export.opml.OpmlReader;
import io.reactivex.Completable;
import org.apache.commons.io.ByteOrderMark;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import java.util.ArrayList;
import android.net.Uri;
import java.io.Reader;
import de.danoeh.antennapod.core.export.opml.OpmlElement;
import de.danoeh.antennapod.R;
import org.apache.commons.io.input.BOMInputStream;
import android.widget.ListView;
import android.os.Build;
import android.content.pm.PackageManager;
import android.util.SparseBooleanArray;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import java.util.List;
import de.danoeh.antennapod.databinding.OpmlSelectionBinding;
import de.danoeh.antennapod.model.feed.Feed;
import java.util.Collections;
import androidx.core.app.ActivityCompat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import de.danoeh.antennapod.core.storage.DBTasks;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import java.io.InputStream;
import java.io.InputStreamReader;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import android.content.Intent;
import android.view.MenuItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.Manifest;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import android.os.Environment;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Activity for Opml Import.
 */
public class OpmlImportActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "OpmlImportBaseActivity";

    @androidx.annotation.Nullable
    private android.net.Uri uri;

    private de.danoeh.antennapod.databinding.OpmlSelectionBinding viewBinding;

    private android.widget.ArrayAdapter<java.lang.String> listAdapter;

    private android.view.MenuItem selectAll;

    private android.view.MenuItem deselectAll;

    private java.util.ArrayList<de.danoeh.antennapod.core.export.opml.OpmlElement> readElements;

    @java.lang.Override
    protected void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        setTheme(de.danoeh.antennapod.core.preferences.ThemeSwitcher.getTheme(this));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // OpmlImportActivity_0_LengthyGUICreationOperatorMutator
            case 145: {
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
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    viewBinding = de.danoeh.antennapod.databinding.OpmlSelectionBinding.inflate(getLayoutInflater());
    setContentView(viewBinding.getRoot());
    viewBinding.feedlist.setChoiceMode(android.widget.ListView.CHOICE_MODE_MULTIPLE);
    viewBinding.feedlist.setOnItemClickListener((android.widget.AdapterView<?> parent,android.view.View view,int position,long id) -> {
        android.util.SparseBooleanArray checked;
        checked = viewBinding.feedlist.getCheckedItemPositions();
        int checkedCount;
        checkedCount = 0;
        for (int i = 0; i < checked.size(); i++) {
            if (checked.valueAt(i)) {
                checkedCount++;
            }
        }
        if (checkedCount == listAdapter.getCount()) {
            selectAll.setVisible(false);
            deselectAll.setVisible(true);
        } else {
            deselectAll.setVisible(false);
            selectAll.setVisible(true);
        }
    });
    switch(MUID_STATIC) {
        // OpmlImportActivity_1_BuggyGUIListenerOperatorMutator
        case 1145: {
            viewBinding.butCancel.setOnClickListener(null);
            break;
        }
        default: {
        viewBinding.butCancel.setOnClickListener((android.view.View v) -> {
            setResult(android.app.Activity.RESULT_CANCELED);
            finish();
        });
        break;
    }
}
switch(MUID_STATIC) {
    // OpmlImportActivity_2_BuggyGUIListenerOperatorMutator
    case 2145: {
        viewBinding.butConfirm.setOnClickListener(null);
        break;
    }
    default: {
    viewBinding.butConfirm.setOnClickListener((android.view.View v) -> {
        viewBinding.progressBar.setVisibility(android.view.View.VISIBLE);
        io.reactivex.Completable.fromAction(() -> {
            android.util.SparseBooleanArray checked;
            checked = viewBinding.feedlist.getCheckedItemPositions();
            for (int i = 0; i < checked.size(); i++) {
                if (!checked.valueAt(i)) {
                    continue;
                }
                de.danoeh.antennapod.core.export.opml.OpmlElement element;
                element = readElements.get(checked.keyAt(i));
                de.danoeh.antennapod.model.feed.Feed feed;
                feed = new de.danoeh.antennapod.model.feed.Feed(element.getXmlUrl(), null, element.getText() != null ? element.getText() : "Unknown podcast");
                feed.setItems(java.util.Collections.emptyList());
                de.danoeh.antennapod.core.storage.DBTasks.updateFeed(this, feed, false);
            }
            de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(this);
        }).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> {
            viewBinding.progressBar.setVisibility(android.view.View.GONE);
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // OpmlImportActivity_3_InvalidKeyIntentOperatorMutator
                case 3145: {
                    intent = new android.content.Intent((OpmlImportActivity) null, de.danoeh.antennapod.activity.MainActivity.class);
                    break;
                }
                // OpmlImportActivity_4_RandomActionIntentDefinitionOperatorMutator
                case 4145: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(this, de.danoeh.antennapod.activity.MainActivity.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // OpmlImportActivity_5_RandomActionIntentDefinitionOperatorMutator
            case 5145: {
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
            intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
            break;
        }
    }
    startActivity(intent);
    finish();
}, (java.lang.Throwable e) -> {
    e.printStackTrace();
    viewBinding.progressBar.setVisibility(android.view.View.GONE);
    android.widget.Toast.makeText(this, e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
});
});
break;
}
}
android.net.Uri uri;
uri = getIntent().getData();
if ((uri != null) && uri.toString().startsWith("/")) {
uri = android.net.Uri.parse("file://" + uri.toString());
} else {
java.lang.String extraText;
extraText = getIntent().getStringExtra(android.content.Intent.EXTRA_TEXT);
if (extraText != null) {
uri = android.net.Uri.parse(extraText);
}
}
importUri(uri);
}


void importUri(@androidx.annotation.Nullable
android.net.Uri uri) {
if (uri == null) {
new com.google.android.material.dialog.MaterialAlertDialogBuilder(this).setMessage(de.danoeh.antennapod.R.string.opml_import_error_no_file).setPositiveButton(android.R.string.ok, null).show();
return;
}
this.uri = uri;
if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) && uri.toString().contains(android.os.Environment.getExternalStorageDirectory().toString())) {
int permission;
permission = androidx.core.app.ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
if (permission != android.content.pm.PackageManager.PERMISSION_GRANTED) {
requestPermission();
return;
}
}
startImport();
}


private java.util.List<java.lang.String> getTitleList() {
java.util.List<java.lang.String> result;
result = new java.util.ArrayList<>();
if (readElements != null) {
for (de.danoeh.antennapod.core.export.opml.OpmlElement element : readElements) {
result.add(element.getText());
}
}
return result;
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
super.onCreateOptionsMenu(menu);
android.view.MenuInflater inflater;
inflater = getMenuInflater();
inflater.inflate(de.danoeh.antennapod.R.menu.opml_selection_options, menu);
selectAll = menu.findItem(de.danoeh.antennapod.R.id.select_all_item);
deselectAll = menu.findItem(de.danoeh.antennapod.R.id.deselect_all_item);
deselectAll.setVisible(false);
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
final int itemId;
itemId = item.getItemId();
if (itemId == de.danoeh.antennapod.R.id.select_all_item) {
selectAll.setVisible(false);
selectAllItems(true);
deselectAll.setVisible(true);
return true;
} else if (itemId == de.danoeh.antennapod.R.id.deselect_all_item) {
deselectAll.setVisible(false);
selectAllItems(false);
selectAll.setVisible(true);
return true;
} else if (itemId == android.R.id.home) {
finish();
}
return false;
}


private void selectAllItems(boolean b) {
for (int i = 0; i < viewBinding.feedlist.getCount(); i++) {
viewBinding.feedlist.setItemChecked(i, b);
}
}


private void requestPermission() {
requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE);
}


private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestPermissionLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.RequestPermission(), (java.lang.Boolean isGranted) -> {
if (isGranted) {
startImport();
} else {
switch(MUID_STATIC) {
// OpmlImportActivity_6_BuggyGUIListenerOperatorMutator
case 6145: {
new com.google.android.material.dialog.MaterialAlertDialogBuilder(this).setMessage(de.danoeh.antennapod.R.string.opml_import_ask_read_permission).setPositiveButton(android.R.string.ok, null).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, (android.content.DialogInterface dialog,int which) -> finish()).show();
break;
}
default: {
switch(MUID_STATIC) {
// OpmlImportActivity_7_BuggyGUIListenerOperatorMutator
case 7145: {
    new com.google.android.material.dialog.MaterialAlertDialogBuilder(this).setMessage(de.danoeh.antennapod.R.string.opml_import_ask_read_permission).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> requestPermission()).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).show();
    break;
}
default: {
new com.google.android.material.dialog.MaterialAlertDialogBuilder(this).setMessage(de.danoeh.antennapod.R.string.opml_import_ask_read_permission).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> requestPermission()).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, (android.content.DialogInterface dialog,int which) -> finish()).show();
break;
}
}
break;
}
}
}
});

/**
 * Starts the import process.
 */
private void startImport() {
viewBinding.progressBar.setVisibility(android.view.View.VISIBLE);
io.reactivex.Observable.fromCallable(() -> {
java.io.InputStream opmlFileStream;
opmlFileStream = getContentResolver().openInputStream(uri);
org.apache.commons.io.input.BOMInputStream bomInputStream;
bomInputStream = new org.apache.commons.io.input.BOMInputStream(opmlFileStream);
org.apache.commons.io.ByteOrderMark bom;
bom = bomInputStream.getBOM();
java.lang.String charsetName;
charsetName = (bom == null) ? "UTF-8" : bom.getCharsetName();
java.io.Reader reader;
reader = new java.io.InputStreamReader(bomInputStream, charsetName);
de.danoeh.antennapod.core.export.opml.OpmlReader opmlReader;
opmlReader = new de.danoeh.antennapod.core.export.opml.OpmlReader();
java.util.ArrayList<de.danoeh.antennapod.core.export.opml.OpmlElement> result;
result = opmlReader.readDocument(reader);
reader.close();
return result;
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.ArrayList<de.danoeh.antennapod.core.export.opml.OpmlElement> result) -> {
viewBinding.progressBar.setVisibility(android.view.View.GONE);
android.util.Log.d(de.danoeh.antennapod.activity.OpmlImportActivity.TAG, "Parsing was successful");
readElements = result;
listAdapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, getTitleList());
viewBinding.feedlist.setAdapter(listAdapter);
}, (java.lang.Throwable e) -> {
viewBinding.progressBar.setVisibility(android.view.View.GONE);
com.google.android.material.dialog.MaterialAlertDialogBuilder alert;
alert = new com.google.android.material.dialog.MaterialAlertDialogBuilder(this);
alert.setTitle(de.danoeh.antennapod.R.string.error_label);
alert.setMessage(getString(de.danoeh.antennapod.R.string.opml_reader_error) + e.getMessage());
switch(MUID_STATIC) {
// OpmlImportActivity_8_BuggyGUIListenerOperatorMutator
case 8145: {
alert.setNeutralButton(android.R.string.ok, null);
break;
}
default: {
alert.setNeutralButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> dialog.dismiss());
break;
}
}
alert.create().show();
});
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
