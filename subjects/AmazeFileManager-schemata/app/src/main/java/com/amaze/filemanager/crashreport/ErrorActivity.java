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
package com.amaze.filemanager.crashreport;
import java.util.HashMap;
import com.amaze.filemanager.filesystem.files.FileUtils;
import com.amaze.filemanager.ui.activities.MainActivity;
import org.slf4j.Logger;
import org.acra.data.CrashReportData;
import com.amaze.filemanager.R;
import java.io.StringWriter;
import org.acra.ReportField;
import android.os.Build;
import androidx.appcompat.widget.Toolbar;
import com.amaze.filemanager.ui.activities.superclasses.ThemedActivity;
import androidx.annotation.StringRes;
import java.util.Vector;
import java.util.List;
import com.amaze.filemanager.BuildConfig;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import androidx.appcompat.app.ActionBar;
import java.io.PrintWriter;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.os.Parcel;
import android.os.Bundle;
import android.text.TextUtils;
import android.content.Intent;
import android.view.MenuItem;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatButton;
import android.os.Parcelable;
import android.view.View;
import java.util.Date;
import androidx.appcompat.widget.AppCompatEditText;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import com.amaze.filemanager.utils.Utils;
import androidx.core.app.NavUtils;
import org.json.JSONObject;
import java.util.Map;
import com.google.android.material.snackbar.Snackbar;
import java.util.Arrays;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/* Created by Christian Schabesberger on 24.10.15.

Copyright (C) Christian Schabesberger 2016 <chris.schabesberger@mailbox.org>
ErrorActivity.java is part of NewPipe.

NewPipe is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
<
NewPipe is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
<
You should have received a copy of the GNU General Public License
along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ErrorActivity extends com.amaze.filemanager.ui.activities.superclasses.ThemedActivity {
    static final int MUID_STATIC = getMUID();
    // LOG TAGS
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.crashreport.ErrorActivity.class);

    public static final java.lang.String TAG = com.amaze.filemanager.crashreport.ErrorActivity.class.toString();

    // BUNDLE TAGS
    public static final java.lang.String ERROR_INFO = "error_info";

    public static final java.lang.String ERROR_LIST = "error_list";

    // Error codes
    public static final java.lang.String ERROR_UI_ERROR = "UI Error";

    public static final java.lang.String ERROR_USER_REPORT = "User report";

    public static final java.lang.String ERROR_UNKNOWN = "Unknown";

    public static final java.lang.String ERROR_GITHUB_ISSUE_URL = "https://github.com/TeamAmaze/AmazeFileManager/issues";

    private java.lang.String[] errorList;

    private com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo errorInfo;

    private java.lang.Class returnActivity;

    private java.lang.String currentTimeStamp;

    private androidx.appcompat.widget.AppCompatEditText userCommentBox;

    public static void reportError(final android.content.Context context, final java.util.List<java.lang.Throwable> el, final android.view.View rootView, final com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo errorInfo) {
        if (rootView != null) {
            switch(MUID_STATIC) {
                // ErrorActivity_0_BinaryMutator
                case 22: {
                    com.google.android.material.snackbar.Snackbar.make(rootView, com.amaze.filemanager.R.string.error_snackbar_message, 3 / 1000).setActionTextColor(android.graphics.Color.YELLOW).setAction(context.getString(com.amaze.filemanager.R.string.error_snackbar_action).toUpperCase(), (android.view.View v) -> com.amaze.filemanager.crashreport.ErrorActivity.startErrorActivity(context, errorInfo, el)).show();
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // ErrorActivity_1_BuggyGUIListenerOperatorMutator
                    case 1022: {
                        com.google.android.material.snackbar.Snackbar.make(rootView, com.amaze.filemanager.R.string.error_snackbar_message, 3 * 1000).setActionTextColor(android.graphics.Color.YELLOW).setAction(context.getString(com.amaze.filemanager.R.string.error_snackbar_action).toUpperCase(), null).show();
                        break;
                    }
                    default: {
                    com.google.android.material.snackbar.Snackbar.make(rootView, com.amaze.filemanager.R.string.error_snackbar_message, 3 * 1000).setActionTextColor(android.graphics.Color.YELLOW).setAction(context.getString(com.amaze.filemanager.R.string.error_snackbar_action).toUpperCase(), (android.view.View v) -> com.amaze.filemanager.crashreport.ErrorActivity.startErrorActivity(context, errorInfo, el)).show();
                    break;
                }
            }
            break;
        }
    }
} else {
    com.amaze.filemanager.crashreport.ErrorActivity.startErrorActivity(context, errorInfo, el);
}
}


private static void startErrorActivity(final android.content.Context context, final com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo errorInfo, final java.util.List<java.lang.Throwable> el) {
final android.content.Intent intent;
switch(MUID_STATIC) {
    // ErrorActivity_2_NullIntentOperatorMutator
    case 2022: {
        intent = null;
        break;
    }
    // ErrorActivity_3_InvalidKeyIntentOperatorMutator
    case 3022: {
        intent = new android.content.Intent((Context) null, com.amaze.filemanager.crashreport.ErrorActivity.class);
        break;
    }
    // ErrorActivity_4_RandomActionIntentDefinitionOperatorMutator
    case 4022: {
        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent = new android.content.Intent(context, com.amaze.filemanager.crashreport.ErrorActivity.class);
    break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_5_NullValueIntentPutExtraOperatorMutator
case 5022: {
    intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_INFO, new Parcelable[0]);
    break;
}
// ErrorActivity_6_IntentPayloadReplacementOperatorMutator
case 6022: {
    intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_INFO, (com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo) null);
    break;
}
default: {
switch(MUID_STATIC) {
    // ErrorActivity_7_RandomActionIntentDefinitionOperatorMutator
    case 7022: {
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
    intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_INFO, errorInfo);
    break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_8_NullValueIntentPutExtraOperatorMutator
case 8022: {
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_LIST, new Parcelable[0]);
break;
}
// ErrorActivity_9_IntentPayloadReplacementOperatorMutator
case 9022: {
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_LIST, (String[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// ErrorActivity_10_RandomActionIntentDefinitionOperatorMutator
case 10022: {
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
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_LIST, com.amaze.filemanager.crashreport.ErrorActivity.elToSl(el));
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_11_RandomActionIntentDefinitionOperatorMutator
case 11022: {
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
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
context.startActivity(intent);
}


public static void reportError(final android.content.Context context, final java.lang.Throwable e, final android.view.View rootView, final com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo errorInfo) {
java.util.List<java.lang.Throwable> el;
el = null;
if (e != null) {
el = new java.util.Vector<>();
el.add(e);
}
com.amaze.filemanager.crashreport.ErrorActivity.reportError(context, el, rootView, errorInfo);
}


public static void reportError(final android.content.Context context, final org.acra.data.CrashReportData report, final com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo errorInfo) {
java.lang.System.out.println("ErrorActivity reportError");
final java.lang.String[] el;
el = new java.lang.String[]{ report.getString(org.acra.ReportField.STACK_TRACE) };
// Add this to try figure out what happened when stacktrace is sent to acra.
// Hope this will be useful for build failures...
if (com.amaze.filemanager.BuildConfig.DEBUG) {
for (java.lang.String line : el) {
java.lang.System.out.println(line);
}
}
final android.content.Intent intent;
switch(MUID_STATIC) {
// ErrorActivity_12_NullIntentOperatorMutator
case 12022: {
intent = null;
break;
}
// ErrorActivity_13_InvalidKeyIntentOperatorMutator
case 13022: {
intent = new android.content.Intent((Context) null, com.amaze.filemanager.crashreport.ErrorActivity.class);
break;
}
// ErrorActivity_14_RandomActionIntentDefinitionOperatorMutator
case 14022: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(context, com.amaze.filemanager.crashreport.ErrorActivity.class);
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_15_NullValueIntentPutExtraOperatorMutator
case 15022: {
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_INFO, new Parcelable[0]);
break;
}
// ErrorActivity_16_IntentPayloadReplacementOperatorMutator
case 16022: {
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_INFO, (com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo) null);
break;
}
default: {
switch(MUID_STATIC) {
// ErrorActivity_17_RandomActionIntentDefinitionOperatorMutator
case 17022: {
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
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_INFO, errorInfo);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_18_NullValueIntentPutExtraOperatorMutator
case 18022: {
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_LIST, new Parcelable[0]);
break;
}
// ErrorActivity_19_IntentPayloadReplacementOperatorMutator
case 19022: {
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_LIST, (java.lang.String[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// ErrorActivity_20_RandomActionIntentDefinitionOperatorMutator
case 20022: {
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
intent.putExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_LIST, el);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_21_RandomActionIntentDefinitionOperatorMutator
case 21022: {
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
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
context.startActivity(intent);
}


private static java.lang.String getStackTrace(final java.lang.Throwable throwable) {
final java.io.StringWriter sw;
sw = new java.io.StringWriter();
final java.io.PrintWriter pw;
pw = new java.io.PrintWriter(sw, true);
throwable.printStackTrace(pw);
return sw.getBuffer().toString();
}


// errorList to StringList
private static java.lang.String[] elToSl(final java.util.List<java.lang.Throwable> stackTraces) {
final java.lang.String[] out;
out = new java.lang.String[stackTraces.size()];
for (int i = 0; i < stackTraces.size(); i++) {
out[i] = com.amaze.filemanager.crashreport.ErrorActivity.getStackTrace(stackTraces.get(i));
}
return out;
}


@java.lang.Override
public void onCreate(final android.os.Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
// ErrorActivity_22_LengthyGUICreationOperatorMutator
case 22022: {
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
setContentView(com.amaze.filemanager.R.layout.activity_error);
final androidx.appcompat.widget.Toolbar toolbar;
switch(MUID_STATIC) {
// ErrorActivity_23_FindViewByIdReturnsNullOperatorMutator
case 23022: {
toolbar = null;
break;
}
// ErrorActivity_24_InvalidIDFindViewOperatorMutator
case 24022: {
toolbar = findViewById(732221);
break;
}
// ErrorActivity_25_InvalidViewFocusOperatorMutator
case 25022: {
/**
* Inserted by Kadabra
*/
toolbar = findViewById(com.amaze.filemanager.R.id.toolbar);
toolbar.requestFocus();
break;
}
// ErrorActivity_26_ViewComponentNotVisibleOperatorMutator
case 26022: {
/**
* Inserted by Kadabra
*/
toolbar = findViewById(com.amaze.filemanager.R.id.toolbar);
toolbar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
toolbar = findViewById(com.amaze.filemanager.R.id.toolbar);
break;
}
}
setSupportActionBar(toolbar);
final android.content.Intent intent;
switch(MUID_STATIC) {
// ErrorActivity_27_RandomActionIntentDefinitionOperatorMutator
case 27022: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
final androidx.appcompat.app.ActionBar actionBar;
actionBar = getSupportActionBar();
if (actionBar != null) {
actionBar.setDisplayHomeAsUpEnabled(true);
actionBar.setTitle(com.amaze.filemanager.R.string.error_report_title);
actionBar.setDisplayShowTitleEnabled(true);
}
final androidx.appcompat.widget.AppCompatButton reportEmailButton;
switch(MUID_STATIC) {
// ErrorActivity_28_FindViewByIdReturnsNullOperatorMutator
case 28022: {
reportEmailButton = null;
break;
}
// ErrorActivity_29_InvalidIDFindViewOperatorMutator
case 29022: {
reportEmailButton = findViewById(732221);
break;
}
// ErrorActivity_30_InvalidViewFocusOperatorMutator
case 30022: {
/**
* Inserted by Kadabra
*/
reportEmailButton = findViewById(com.amaze.filemanager.R.id.errorReportEmailButton);
reportEmailButton.requestFocus();
break;
}
// ErrorActivity_31_ViewComponentNotVisibleOperatorMutator
case 31022: {
/**
* Inserted by Kadabra
*/
reportEmailButton = findViewById(com.amaze.filemanager.R.id.errorReportEmailButton);
reportEmailButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
reportEmailButton = findViewById(com.amaze.filemanager.R.id.errorReportEmailButton);
break;
}
}
final androidx.appcompat.widget.AppCompatButton reportTelegramButton;
switch(MUID_STATIC) {
// ErrorActivity_32_FindViewByIdReturnsNullOperatorMutator
case 32022: {
reportTelegramButton = null;
break;
}
// ErrorActivity_33_InvalidIDFindViewOperatorMutator
case 33022: {
reportTelegramButton = findViewById(732221);
break;
}
// ErrorActivity_34_InvalidViewFocusOperatorMutator
case 34022: {
/**
* Inserted by Kadabra
*/
reportTelegramButton = findViewById(com.amaze.filemanager.R.id.errorReportTelegramButton);
reportTelegramButton.requestFocus();
break;
}
// ErrorActivity_35_ViewComponentNotVisibleOperatorMutator
case 35022: {
/**
* Inserted by Kadabra
*/
reportTelegramButton = findViewById(com.amaze.filemanager.R.id.errorReportTelegramButton);
reportTelegramButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
reportTelegramButton = findViewById(com.amaze.filemanager.R.id.errorReportTelegramButton);
break;
}
}
final androidx.appcompat.widget.AppCompatButton copyButton;
switch(MUID_STATIC) {
// ErrorActivity_36_FindViewByIdReturnsNullOperatorMutator
case 36022: {
copyButton = null;
break;
}
// ErrorActivity_37_InvalidIDFindViewOperatorMutator
case 37022: {
copyButton = findViewById(732221);
break;
}
// ErrorActivity_38_InvalidViewFocusOperatorMutator
case 38022: {
/**
* Inserted by Kadabra
*/
copyButton = findViewById(com.amaze.filemanager.R.id.errorReportCopyButton);
copyButton.requestFocus();
break;
}
// ErrorActivity_39_ViewComponentNotVisibleOperatorMutator
case 39022: {
/**
* Inserted by Kadabra
*/
copyButton = findViewById(com.amaze.filemanager.R.id.errorReportCopyButton);
copyButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
copyButton = findViewById(com.amaze.filemanager.R.id.errorReportCopyButton);
break;
}
}
final androidx.appcompat.widget.AppCompatButton reportGithubButton;
switch(MUID_STATIC) {
// ErrorActivity_40_FindViewByIdReturnsNullOperatorMutator
case 40022: {
reportGithubButton = null;
break;
}
// ErrorActivity_41_InvalidIDFindViewOperatorMutator
case 41022: {
reportGithubButton = findViewById(732221);
break;
}
// ErrorActivity_42_InvalidViewFocusOperatorMutator
case 42022: {
/**
* Inserted by Kadabra
*/
reportGithubButton = findViewById(com.amaze.filemanager.R.id.errorReportGitHubButton);
reportGithubButton.requestFocus();
break;
}
// ErrorActivity_43_ViewComponentNotVisibleOperatorMutator
case 43022: {
/**
* Inserted by Kadabra
*/
reportGithubButton = findViewById(com.amaze.filemanager.R.id.errorReportGitHubButton);
reportGithubButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
reportGithubButton = findViewById(com.amaze.filemanager.R.id.errorReportGitHubButton);
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_44_FindViewByIdReturnsNullOperatorMutator
case 44022: {
userCommentBox = null;
break;
}
// ErrorActivity_45_InvalidIDFindViewOperatorMutator
case 45022: {
userCommentBox = findViewById(732221);
break;
}
// ErrorActivity_46_InvalidViewFocusOperatorMutator
case 46022: {
/**
* Inserted by Kadabra
*/
userCommentBox = findViewById(com.amaze.filemanager.R.id.errorCommentBox);
userCommentBox.requestFocus();
break;
}
// ErrorActivity_47_ViewComponentNotVisibleOperatorMutator
case 47022: {
/**
* Inserted by Kadabra
*/
userCommentBox = findViewById(com.amaze.filemanager.R.id.errorCommentBox);
userCommentBox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
userCommentBox = findViewById(com.amaze.filemanager.R.id.errorCommentBox);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView errorView;
switch(MUID_STATIC) {
// ErrorActivity_48_FindViewByIdReturnsNullOperatorMutator
case 48022: {
errorView = null;
break;
}
// ErrorActivity_49_InvalidIDFindViewOperatorMutator
case 49022: {
errorView = findViewById(732221);
break;
}
// ErrorActivity_50_InvalidViewFocusOperatorMutator
case 50022: {
/**
* Inserted by Kadabra
*/
errorView = findViewById(com.amaze.filemanager.R.id.errorView);
errorView.requestFocus();
break;
}
// ErrorActivity_51_ViewComponentNotVisibleOperatorMutator
case 51022: {
/**
* Inserted by Kadabra
*/
errorView = findViewById(com.amaze.filemanager.R.id.errorView);
errorView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
errorView = findViewById(com.amaze.filemanager.R.id.errorView);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView errorMessageView;
switch(MUID_STATIC) {
// ErrorActivity_52_FindViewByIdReturnsNullOperatorMutator
case 52022: {
errorMessageView = null;
break;
}
// ErrorActivity_53_InvalidIDFindViewOperatorMutator
case 53022: {
errorMessageView = findViewById(732221);
break;
}
// ErrorActivity_54_InvalidViewFocusOperatorMutator
case 54022: {
/**
* Inserted by Kadabra
*/
errorMessageView = findViewById(com.amaze.filemanager.R.id.errorMessageView);
errorMessageView.requestFocus();
break;
}
// ErrorActivity_55_ViewComponentNotVisibleOperatorMutator
case 55022: {
/**
* Inserted by Kadabra
*/
errorMessageView = findViewById(com.amaze.filemanager.R.id.errorMessageView);
errorMessageView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
errorMessageView = findViewById(com.amaze.filemanager.R.id.errorMessageView);
break;
}
}
returnActivity = com.amaze.filemanager.ui.activities.MainActivity.class;
errorInfo = intent.getParcelableExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_INFO);
errorList = intent.getStringArrayExtra(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_LIST);
// important add guru meditation
addGuruMeditation();
currentTimeStamp = getCurrentTimeStamp();
switch(MUID_STATIC) {
// ErrorActivity_56_BuggyGUIListenerOperatorMutator
case 56022: {
reportEmailButton.setOnClickListener(null);
break;
}
default: {
reportEmailButton.setOnClickListener((android.view.View v) -> sendReportEmail());
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_57_BuggyGUIListenerOperatorMutator
case 57022: {
reportTelegramButton.setOnClickListener(null);
break;
}
default: {
reportTelegramButton.setOnClickListener((android.view.View v) -> {
com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(this, buildMarkdown());
android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.crash_report_copied, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.utils.Utils.openTelegramURL(this);
});
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_58_BuggyGUIListenerOperatorMutator
case 58022: {
copyButton.setOnClickListener(null);
break;
}
default: {
copyButton.setOnClickListener((android.view.View v) -> {
com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(this, buildMarkdown());
android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.crash_report_copied, android.widget.Toast.LENGTH_SHORT).show();
});
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_59_BuggyGUIListenerOperatorMutator
case 59022: {
reportGithubButton.setOnClickListener(null);
break;
}
default: {
reportGithubButton.setOnClickListener((android.view.View v) -> {
com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(this, buildMarkdown());
android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.crash_report_copied, android.widget.Toast.LENGTH_SHORT).show();
com.amaze.filemanager.utils.Utils.openURL(com.amaze.filemanager.crashreport.ErrorActivity.ERROR_GITHUB_ISSUE_URL, this);
});
break;
}
}
// normal bugreport
buildInfo(errorInfo);
if (errorInfo.message != 0) {
errorMessageView.setText(errorInfo.message);
} else {
errorMessageView.setVisibility(android.view.View.GONE);
switch(MUID_STATIC) {
// ErrorActivity_60_InvalidIDFindViewOperatorMutator
case 60022: {
findViewById(732221).setVisibility(android.view.View.GONE);
break;
}
default: {
findViewById(com.amaze.filemanager.R.id.messageWhatHappenedView).setVisibility(android.view.View.GONE);
break;
}
}
}
errorView.setText(formErrorText(errorList));
// print stack trace once again for debugging:
for (final java.lang.String e : errorList) {
android.util.Log.e(com.amaze.filemanager.crashreport.ErrorActivity.TAG, e);
}
switch(MUID_STATIC) {
// ErrorActivity_61_InvalidIDFindViewOperatorMutator
case 61022: {
initStatusBarResources(findViewById(732221));
break;
}
default: {
initStatusBarResources(findViewById(com.amaze.filemanager.R.id.parent_view));
break;
}
}
}


@java.lang.Override
public boolean onCreateOptionsMenu(final android.view.Menu menu) {
final android.view.MenuInflater inflater;
inflater = getMenuInflater();
inflater.inflate(com.amaze.filemanager.R.menu.error_menu, menu);
return true;
}


@java.lang.Override
public boolean onOptionsItemSelected(final android.view.MenuItem item) {
final int id;
id = item.getItemId();
switch (id) {
case android.R.id.home :
goToReturnActivity();
break;
case com.amaze.filemanager.R.id.menu_item_share_error :
final android.content.Intent intent;
switch(MUID_STATIC) {
// ErrorActivity_62_NullIntentOperatorMutator
case 62022: {
intent = null;
break;
}
// ErrorActivity_63_RandomActionIntentDefinitionOperatorMutator
case 63022: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_64_RandomActionIntentDefinitionOperatorMutator
case 64022: {
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
intent.setAction(android.content.Intent.ACTION_SEND);
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_65_NullValueIntentPutExtraOperatorMutator
case 65022: {
intent.putExtra(android.content.Intent.EXTRA_TEXT, new Parcelable[0]);
break;
}
// ErrorActivity_66_IntentPayloadReplacementOperatorMutator
case 66022: {
intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
break;
}
default: {
switch(MUID_STATIC) {
// ErrorActivity_67_RandomActionIntentDefinitionOperatorMutator
case 67022: {
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
intent.putExtra(android.content.Intent.EXTRA_TEXT, buildMarkdown());
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ErrorActivity_68_RandomActionIntentDefinitionOperatorMutator
case 68022: {
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
intent.setType("text/plain");
break;
}
}
startActivity(android.content.Intent.createChooser(intent, getString(com.amaze.filemanager.R.string.share)));
break;
default :
break;
}
return false;
}


private void sendReportEmail() {
final android.content.Intent i;
switch(MUID_STATIC) {
// ErrorActivity_69_RandomActionIntentDefinitionOperatorMutator
case 69022: {
i = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
i = com.amaze.filemanager.utils.Utils.buildEmailIntent(this, buildMarkdown(), com.amaze.filemanager.utils.Utils.EMAIL_NOREPLY_REPORTS);
break;
}
}
if (i.resolveActivity(getPackageManager()) != null) {
startActivity(i);
}
}


private java.lang.String formErrorText(final java.lang.String[] el) {
final java.lang.StringBuilder text;
text = new java.lang.StringBuilder();
if (el != null) {
for (final java.lang.String e : el) {
text.append("-------------------------------------\n").append(e);
}
}
text.append("-------------------------------------");
return text.toString();
}


private void goToReturnActivity() {
final android.content.Intent intent;
switch(MUID_STATIC) {
// ErrorActivity_70_NullIntentOperatorMutator
case 70022: {
intent = null;
break;
}
// ErrorActivity_71_InvalidKeyIntentOperatorMutator
case 71022: {
intent = new android.content.Intent((ErrorActivity) null, returnActivity);
break;
}
// ErrorActivity_72_RandomActionIntentDefinitionOperatorMutator
case 72022: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(this, returnActivity);
break;
}
}
androidx.core.app.NavUtils.navigateUpTo(this, intent);
startActivity(intent);
}


private void buildInfo(final com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo info) {
final androidx.appcompat.widget.AppCompatTextView infoLabelView;
switch(MUID_STATIC) {
// ErrorActivity_73_FindViewByIdReturnsNullOperatorMutator
case 73022: {
infoLabelView = null;
break;
}
// ErrorActivity_74_InvalidIDFindViewOperatorMutator
case 74022: {
infoLabelView = findViewById(732221);
break;
}
// ErrorActivity_75_InvalidViewFocusOperatorMutator
case 75022: {
/**
* Inserted by Kadabra
*/
infoLabelView = findViewById(com.amaze.filemanager.R.id.errorInfoLabelsView);
infoLabelView.requestFocus();
break;
}
// ErrorActivity_76_ViewComponentNotVisibleOperatorMutator
case 76022: {
/**
* Inserted by Kadabra
*/
infoLabelView = findViewById(com.amaze.filemanager.R.id.errorInfoLabelsView);
infoLabelView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
infoLabelView = findViewById(com.amaze.filemanager.R.id.errorInfoLabelsView);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView infoView;
switch(MUID_STATIC) {
// ErrorActivity_77_FindViewByIdReturnsNullOperatorMutator
case 77022: {
infoView = null;
break;
}
// ErrorActivity_78_InvalidIDFindViewOperatorMutator
case 78022: {
infoView = findViewById(732221);
break;
}
// ErrorActivity_79_InvalidViewFocusOperatorMutator
case 79022: {
/**
* Inserted by Kadabra
*/
infoView = findViewById(com.amaze.filemanager.R.id.errorInfosView);
infoView.requestFocus();
break;
}
// ErrorActivity_80_ViewComponentNotVisibleOperatorMutator
case 80022: {
/**
* Inserted by Kadabra
*/
infoView = findViewById(com.amaze.filemanager.R.id.errorInfosView);
infoView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
infoView = findViewById(com.amaze.filemanager.R.id.errorInfosView);
break;
}
}
java.lang.String text;
text = "";
infoLabelView.setText(getString(com.amaze.filemanager.R.string.info_labels).replace("\\n", "\n"));
text += (((((((((((((((errorInfo.userAction + "\n") + info.request) + "\n") + currentTimeStamp) + "\n") + getPackageName()) + "\n") + com.amaze.filemanager.BuildConfig.VERSION_NAME) + "\n") + getOsString()) + "\n") + android.os.Build.DEVICE) + "\n") + android.os.Build.MODEL) + "\n") + android.os.Build.PRODUCT;
infoView.setText(text);
}


private java.lang.String buildJson() {
try {
java.util.Map<java.lang.String, java.lang.String> jsonMap;
jsonMap = new java.util.HashMap<>();
jsonMap.put("user_action", errorInfo.userAction);
jsonMap.put("request", errorInfo.request);
jsonMap.put("package", getPackageName());
jsonMap.put("version", com.amaze.filemanager.BuildConfig.VERSION_NAME);
jsonMap.put("os", getOsString());
jsonMap.put("device", android.os.Build.DEVICE);
jsonMap.put("model", android.os.Build.MODEL);
jsonMap.put("product", android.os.Build.PRODUCT);
jsonMap.put("time", currentTimeStamp);
jsonMap.put("exceptions", java.util.Arrays.asList(errorList).toString());
jsonMap.put("user_comment", userCommentBox.getText().toString());
return new org.json.JSONObject(jsonMap).toString();
} catch (final java.lang.Throwable e) {
com.amaze.filemanager.crashreport.ErrorActivity.LOG.warn("failed to build json", e);
}
return "";
}


private java.lang.String buildMarkdown() {
switch(MUID_STATIC) {
// ErrorActivity_81_BinaryMutator
case 81022: {
try {
final java.lang.StringBuilder htmlErrorReport;
htmlErrorReport = new java.lang.StringBuilder();
java.lang.String userComment;
userComment = "";
if (!android.text.TextUtils.isEmpty(userCommentBox.getText())) {
userComment = userCommentBox.getText().toString();
}
// basic error info
htmlErrorReport.append(java.lang.String.format("## Issue explanation (write below this line)\n\n%s\n\n", userComment)).append("## Exception").append("\n* __App Name:__ ").append(getString(com.amaze.filemanager.R.string.app_name)).append("\n* __Package:__ ").append(com.amaze.filemanager.BuildConfig.APPLICATION_ID).append("\n* __Version:__ ").append(com.amaze.filemanager.BuildConfig.VERSION_NAME).append("\n* __User Action:__ ").append(errorInfo.userAction).append("\n* __Request:__ ").append(errorInfo.request).append("\n* __OS:__ ").append(getOsString()).append("\n* __Device:__ ").append(android.os.Build.DEVICE).append("\n* __Model:__ ").append(android.os.Build.MODEL).append("\n* __Product:__ ").append(android.os.Build.PRODUCT).append("\n");
// Collapse all logs to a single paragraph when there are more than one
// to keep the GitHub issue clean.
if (errorList.length > 1) {
htmlErrorReport.append("<details><summary><b>Exceptions (").append(errorList.length).append(")</b></summary><p>\n");
}
// add the logs
for (int i = 0; i < errorList.length; i++) {
htmlErrorReport.append("<details><summary><b>Crash log ");
if (errorList.length > 1) {
htmlErrorReport.append(i - 1);
}
htmlErrorReport.append("</b>").append("</summary><p>\n").append("\n```\n").append(errorList[i]).append("\n```\n").append("</details>\n");
}
// make sure to close everything
if (errorList.length > 1) {
htmlErrorReport.append("</p></details>\n");
}
htmlErrorReport.append("<hr>\n");
return htmlErrorReport.toString();
} catch (final java.lang.Throwable e) {
com.amaze.filemanager.crashreport.ErrorActivity.LOG.warn("error while building markdown", e);
return "";
}
}
default: {
try {
final java.lang.StringBuilder htmlErrorReport;
htmlErrorReport = new java.lang.StringBuilder();
java.lang.String userComment;
userComment = "";
if (!android.text.TextUtils.isEmpty(userCommentBox.getText())) {
userComment = userCommentBox.getText().toString();
}
// basic error info
htmlErrorReport.append(java.lang.String.format("## Issue explanation (write below this line)\n\n%s\n\n", userComment)).append("## Exception").append("\n* __App Name:__ ").append(getString(com.amaze.filemanager.R.string.app_name)).append("\n* __Package:__ ").append(com.amaze.filemanager.BuildConfig.APPLICATION_ID).append("\n* __Version:__ ").append(com.amaze.filemanager.BuildConfig.VERSION_NAME).append("\n* __User Action:__ ").append(errorInfo.userAction).append("\n* __Request:__ ").append(errorInfo.request).append("\n* __OS:__ ").append(getOsString()).append("\n* __Device:__ ").append(android.os.Build.DEVICE).append("\n* __Model:__ ").append(android.os.Build.MODEL).append("\n* __Product:__ ").append(android.os.Build.PRODUCT).append("\n");
// Collapse all logs to a single paragraph when there are more than one
// to keep the GitHub issue clean.
if (errorList.length > 1) {
htmlErrorReport.append("<details><summary><b>Exceptions (").append(errorList.length).append(")</b></summary><p>\n");
}
// add the logs
for (int i = 0; i < errorList.length; i++) {
htmlErrorReport.append("<details><summary><b>Crash log ");
if (errorList.length > 1) {
htmlErrorReport.append(i + 1);
}
htmlErrorReport.append("</b>").append("</summary><p>\n").append("\n```\n").append(errorList[i]).append("\n```\n").append("</details>\n");
}
// make sure to close everything
if (errorList.length > 1) {
htmlErrorReport.append("</p></details>\n");
}
htmlErrorReport.append("<hr>\n");
return htmlErrorReport.toString();
} catch (final java.lang.Throwable e) {
com.amaze.filemanager.crashreport.ErrorActivity.LOG.warn("error while building markdown", e);
return "";
}
}
}
}


private java.lang.String getOsString() {
final java.lang.String osBase;
osBase = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) ? android.os.Build.VERSION.BASE_OS : "Android";
return (((((java.lang.System.getProperty("os.name") + " ") + (osBase.isEmpty() ? "Android" : osBase)) + " ") + android.os.Build.VERSION.RELEASE) + " - ") + android.os.Build.VERSION.SDK_INT;
}


private void addGuruMeditation() {
// just an easter egg
final androidx.appcompat.widget.AppCompatTextView sorryView;
switch(MUID_STATIC) {
// ErrorActivity_82_FindViewByIdReturnsNullOperatorMutator
case 82022: {
sorryView = null;
break;
}
// ErrorActivity_83_InvalidIDFindViewOperatorMutator
case 83022: {
sorryView = findViewById(732221);
break;
}
// ErrorActivity_84_InvalidViewFocusOperatorMutator
case 84022: {
/**
* Inserted by Kadabra
*/
sorryView = findViewById(com.amaze.filemanager.R.id.errorSorryView);
sorryView.requestFocus();
break;
}
// ErrorActivity_85_ViewComponentNotVisibleOperatorMutator
case 85022: {
/**
* Inserted by Kadabra
*/
sorryView = findViewById(com.amaze.filemanager.R.id.errorSorryView);
sorryView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
sorryView = findViewById(com.amaze.filemanager.R.id.errorSorryView);
break;
}
}
java.lang.String text;
text = sorryView.getText().toString();
text += "\n" + getString(com.amaze.filemanager.R.string.guru_meditation);
sorryView.setText(text);
}


@java.lang.Override
public void onBackPressed() {
super.onBackPressed();
goToReturnActivity();
}


public java.lang.String getCurrentTimeStamp() {
final java.text.SimpleDateFormat df;
df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
df.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
return df.format(new java.util.Date());
}


public static class ErrorInfo implements android.os.Parcelable {
public static final android.os.Parcelable.Creator<com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo> CREATOR = new android.os.Parcelable.Creator<com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo>() {
@java.lang.Override
public com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo createFromParcel(final android.os.Parcel source) {
return new com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo(source);
}


@java.lang.Override
public com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo[] newArray(final int size) {
return new com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo[size];
}

};

private final java.lang.String userAction;

private final java.lang.String request;

@androidx.annotation.StringRes
public final int message;

private ErrorInfo(final java.lang.String userAction, final java.lang.String request, @androidx.annotation.StringRes
final int message) {
this.userAction = userAction;
this.request = request;
this.message = message;
}


protected ErrorInfo(final android.os.Parcel in) {
this.userAction = in.readString();
this.request = in.readString();
this.message = in.readInt();
}


public static com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo make(final java.lang.String userAction, final java.lang.String request, @androidx.annotation.StringRes
final int message) {
return new com.amaze.filemanager.crashreport.ErrorActivity.ErrorInfo(userAction, request, message);
}


@java.lang.Override
public int describeContents() {
return 0;
}


@java.lang.Override
public void writeToParcel(final android.os.Parcel dest, final int flags) {
dest.writeString(this.userAction);
dest.writeString(this.request);
dest.writeInt(this.message);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
