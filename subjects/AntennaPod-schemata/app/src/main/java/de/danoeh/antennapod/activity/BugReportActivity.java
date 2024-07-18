package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.core.preferences.ThemeSwitcher;
import androidx.core.app.ShareCompat;
import android.net.Uri;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.os.Build;
import android.widget.TextView;
import android.util.Log;
import android.view.Menu;
import android.os.Bundle;
import java.io.IOException;
import java.nio.charset.Charset;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.ClipboardManager;
import java.io.FileInputStream;
import de.danoeh.antennapod.error.CrashReportWriter;
import androidx.core.content.FileProvider;
import de.danoeh.antennapod.core.util.IntentUtils;
import org.apache.commons.io.IOUtils;
import android.content.ClipData;
import com.google.android.material.snackbar.Snackbar;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays the 'crash report' screen
 */
public class BugReportActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "BugReportActivity";

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        setTheme(de.danoeh.antennapod.core.preferences.ThemeSwitcher.getTheme(this));
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // BugReportActivity_0_LengthyGUICreationOperatorMutator
            case 147: {
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
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    setContentView(de.danoeh.antennapod.R.layout.bug_report);
    java.lang.String stacktrace;
    stacktrace = "No crash report recorded";
    try {
        java.io.File crashFile;
        crashFile = de.danoeh.antennapod.error.CrashReportWriter.getFile();
        if (crashFile.exists()) {
            stacktrace = org.apache.commons.io.IOUtils.toString(new java.io.FileInputStream(crashFile), java.nio.charset.Charset.forName("UTF-8"));
        } else {
            android.util.Log.d(de.danoeh.antennapod.activity.BugReportActivity.TAG, stacktrace);
        }
    } catch (java.io.IOException e) {
        e.printStackTrace();
    }
    android.widget.TextView crashDetailsTextView;
    switch(MUID_STATIC) {
        // BugReportActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1147: {
            crashDetailsTextView = null;
            break;
        }
        // BugReportActivity_2_InvalidIDFindViewOperatorMutator
        case 2147: {
            crashDetailsTextView = findViewById(732221);
            break;
        }
        // BugReportActivity_3_InvalidViewFocusOperatorMutator
        case 3147: {
            /**
            * Inserted by Kadabra
            */
            crashDetailsTextView = findViewById(de.danoeh.antennapod.R.id.crash_report_logs);
            crashDetailsTextView.requestFocus();
            break;
        }
        // BugReportActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4147: {
            /**
            * Inserted by Kadabra
            */
            crashDetailsTextView = findViewById(de.danoeh.antennapod.R.id.crash_report_logs);
            crashDetailsTextView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        crashDetailsTextView = findViewById(de.danoeh.antennapod.R.id.crash_report_logs);
        break;
    }
}
crashDetailsTextView.setText((de.danoeh.antennapod.error.CrashReportWriter.getSystemInfo() + "\n\n") + stacktrace);
switch(MUID_STATIC) {
    // BugReportActivity_5_InvalidIDFindViewOperatorMutator
    case 5147: {
        findViewById(732221).setOnClickListener((android.view.View v) -> de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(this, "https://github.com/AntennaPod/AntennaPod/issues"));
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // BugReportActivity_6_BuggyGUIListenerOperatorMutator
        case 6147: {
            findViewById(de.danoeh.antennapod.R.id.btn_open_bug_tracker).setOnClickListener(null);
            break;
        }
        default: {
        findViewById(de.danoeh.antennapod.R.id.btn_open_bug_tracker).setOnClickListener((android.view.View v) -> de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(this, "https://github.com/AntennaPod/AntennaPod/issues"));
        break;
    }
}
break;
}
}
switch(MUID_STATIC) {
// BugReportActivity_7_InvalidIDFindViewOperatorMutator
case 7147: {
findViewById(732221).setOnClickListener((android.view.View v) -> {
    android.content.ClipboardManager clipboard;
    clipboard = ((android.content.ClipboardManager) (getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
    android.content.ClipData clip;
    clip = android.content.ClipData.newPlainText(getString(de.danoeh.antennapod.R.string.bug_report_title), crashDetailsTextView.getText());
    clipboard.setPrimaryClip(clip);
    if (android.os.Build.VERSION.SDK_INT < 32) {
        com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), de.danoeh.antennapod.R.string.copied_to_clipboard, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
    }
});
break;
}
default: {
switch(MUID_STATIC) {
// BugReportActivity_8_BuggyGUIListenerOperatorMutator
case 8147: {
    findViewById(de.danoeh.antennapod.R.id.btn_copy_log).setOnClickListener(null);
    break;
}
default: {
findViewById(de.danoeh.antennapod.R.id.btn_copy_log).setOnClickListener((android.view.View v) -> {
    android.content.ClipboardManager clipboard;
    clipboard = ((android.content.ClipboardManager) (getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
    android.content.ClipData clip;
    clip = android.content.ClipData.newPlainText(getString(de.danoeh.antennapod.R.string.bug_report_title), crashDetailsTextView.getText());
    clipboard.setPrimaryClip(clip);
    if (android.os.Build.VERSION.SDK_INT < 32) {
        switch(MUID_STATIC) {
            // BugReportActivity_9_InvalidIDFindViewOperatorMutator
            case 9147: {
                com.google.android.material.snackbar.Snackbar.make(findViewById(732221), de.danoeh.antennapod.R.string.copied_to_clipboard, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
                break;
            }
            default: {
            com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), de.danoeh.antennapod.R.string.copied_to_clipboard, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
            break;
        }
    }
}
});
break;
}
}
break;
}
}
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
getMenuInflater().inflate(de.danoeh.antennapod.R.menu.bug_report_options, menu);
return super.onCreateOptionsMenu(menu);
}


@java.lang.Override
public boolean onOptionsItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
if (item.getItemId() == de.danoeh.antennapod.R.id.export_logcat) {
com.google.android.material.dialog.MaterialAlertDialogBuilder alertBuilder;
alertBuilder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(this);
alertBuilder.setMessage(de.danoeh.antennapod.R.string.confirm_export_log_dialog_message);
switch(MUID_STATIC) {
// BugReportActivity_10_BuggyGUIListenerOperatorMutator
case 10147: {
alertBuilder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
break;
}
default: {
alertBuilder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> {
exportLog();
dialog.dismiss();
});
break;
}
}
alertBuilder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
alertBuilder.show();
return true;
}
return super.onOptionsItemSelected(item);
}


private void exportLog() {
switch(MUID_STATIC) {
// BugReportActivity_12_InvalidIDFindViewOperatorMutator
case 12147: {
try {
java.io.File filename;
filename = new java.io.File(de.danoeh.antennapod.storage.preferences.UserPreferences.getDataFolder(null), "full-logs.txt");
java.lang.String cmd;
cmd = "logcat -d -f " + filename.getAbsolutePath();
java.lang.Runtime.getRuntime().exec(cmd);
/**
* Inserted by Kadabra
*/
switch(MUID_STATIC) {
// BugReportActivity_11_InvalidIDFindViewOperatorMutator
case 11147: {
// share file
try {
java.lang.String authority;
authority = getString(de.danoeh.antennapod.R.string.provider_authority);
android.net.Uri fileUri;
fileUri = androidx.core.content.FileProvider.getUriForFile(this, authority, filename);
new androidx.core.app.ShareCompat.IntentBuilder(this).setType("text/*").addStream(fileUri).setChooserTitle(de.danoeh.antennapod.R.string.share_file_label).startChooser();
} catch (java.lang.Exception e) {
e.printStackTrace();
int strResId;
strResId = de.danoeh.antennapod.R.string.log_file_share_exception;
com.google.android.material.snackbar.Snackbar.make(findViewById(732221), strResId, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
}
break;
}
default: {;
// share file
try {
java.lang.String authority;
authority = getString(de.danoeh.antennapod.R.string.provider_authority);
android.net.Uri fileUri;
fileUri = androidx.core.content.FileProvider.getUriForFile(this, authority, filename);
new androidx.core.app.ShareCompat.IntentBuilder(this).setType("text/*").addStream(fileUri).setChooserTitle(de.danoeh.antennapod.R.string.share_file_label).startChooser();
} catch (java.lang.Exception e) {
e.printStackTrace();
int strResId;
strResId = de.danoeh.antennapod.R.string.log_file_share_exception;
com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), strResId, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
}
/**
* Inserted by Kadabra
*/
break;
}
};
} catch (java.io.IOException e) {
e.printStackTrace();
com.google.android.material.snackbar.Snackbar.make(findViewById(732221), e.getMessage(), com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
}
break;
}
default: {
try {
java.io.File filename;
filename = new java.io.File(de.danoeh.antennapod.storage.preferences.UserPreferences.getDataFolder(null), "full-logs.txt");
java.lang.String cmd;
cmd = "logcat -d -f " + filename.getAbsolutePath();
java.lang.Runtime.getRuntime().exec(cmd);
switch(MUID_STATIC) {
// BugReportActivity_11_InvalidIDFindViewOperatorMutator
case 11147: {
// share file
try {
java.lang.String authority;
authority = getString(de.danoeh.antennapod.R.string.provider_authority);
android.net.Uri fileUri;
fileUri = androidx.core.content.FileProvider.getUriForFile(this, authority, filename);
new androidx.core.app.ShareCompat.IntentBuilder(this).setType("text/*").addStream(fileUri).setChooserTitle(de.danoeh.antennapod.R.string.share_file_label).startChooser();
} catch (java.lang.Exception e) {
e.printStackTrace();
int strResId;
strResId = de.danoeh.antennapod.R.string.log_file_share_exception;
com.google.android.material.snackbar.Snackbar.make(findViewById(732221), strResId, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
}
break;
}
default: {
// share file
try {
java.lang.String authority;
authority = getString(de.danoeh.antennapod.R.string.provider_authority);
android.net.Uri fileUri;
fileUri = androidx.core.content.FileProvider.getUriForFile(this, authority, filename);
new androidx.core.app.ShareCompat.IntentBuilder(this).setType("text/*").addStream(fileUri).setChooserTitle(de.danoeh.antennapod.R.string.share_file_label).startChooser();
} catch (java.lang.Exception e) {
e.printStackTrace();
int strResId;
strResId = de.danoeh.antennapod.R.string.log_file_share_exception;
com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), strResId, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
}
break;
}
}
} catch (java.io.IOException e) {
e.printStackTrace();
com.google.android.material.snackbar.Snackbar.make(findViewById(android.R.id.content), e.getMessage(), com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
}
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
