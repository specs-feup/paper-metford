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
package com.amaze.filemanager.ui.activities.texteditor;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import com.amaze.filemanager.filesystem.files.FileUtils;
import com.amaze.filemanager.asynchronous.asynctasks.TaskKt;
import androidx.annotation.NonNull;
import java.util.List;
import com.amaze.filemanager.asynchronous.asynctasks.texteditor.write.WriteTextFileTask;
import com.amaze.filemanager.ui.theme.AppTheme;
import com.amaze.filemanager.utils.OnProgressUpdate;
import androidx.appcompat.app.ActionBar;
import java.util.Collections;
import com.amaze.filemanager.utils.OnAsyncTaskFinished;
import android.view.Menu;
import android.graphics.Typeface;
import android.text.Spanned;
import com.amaze.filemanager.filesystem.EditableFileAbstraction;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK;
import java.io.File;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.snackbar.Snackbar;
import com.amaze.filemanager.asynchronous.asynctasks.texteditor.read.ReadTextFileTask;
import android.text.style.BackgroundColorSpan;
import android.text.TextWatcher;
import com.amaze.filemanager.asynchronous.asynctasks.SearchTextTask;
import android.view.inputmethod.InputMethodManager;
import android.net.Uri;
import java.lang.ref.WeakReference;
import com.amaze.filemanager.R;
import android.view.inputmethod.EditorInfo;
import java.util.TimerTask;
import android.view.animation.AnimationUtils;
import static com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.CONTENT;
import android.text.Editable;
import com.amaze.filemanager.ui.activities.superclasses.ThemedActivity;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import static com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.FILE;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatImageButton;
import android.view.MenuItem;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import android.view.View;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.annotation.ColorInt;
import android.widget.ScrollView;
import com.amaze.filemanager.utils.Utils;
import java.util.Timer;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.view.animation.Animation;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TextEditorActivity extends com.amaze.filemanager.ui.activities.superclasses.ThemedActivity implements android.text.TextWatcher , android.view.View.OnClickListener {
    static final int MUID_STATIC = getMUID();
    public androidx.appcompat.widget.AppCompatEditText mainTextView;

    public androidx.appcompat.widget.AppCompatEditText searchEditText;

    private android.graphics.Typeface inputTypefaceDefault;

    private android.graphics.Typeface inputTypefaceMono;

    private androidx.appcompat.widget.Toolbar toolbar;

    android.widget.ScrollView scrollView;

    private com.amaze.filemanager.asynchronous.asynctasks.SearchTextTask searchTextTask;

    private static final java.lang.String KEY_MODIFIED_TEXT = "modified";

    private static final java.lang.String KEY_INDEX = "index";

    private static final java.lang.String KEY_ORIGINAL_TEXT = "original";

    private static final java.lang.String KEY_MONOFONT = "monofont";

    private androidx.constraintlayout.widget.ConstraintLayout searchViewLayout;

    public androidx.appcompat.widget.AppCompatImageButton upButton;

    public androidx.appcompat.widget.AppCompatImageButton downButton;

    private com.google.android.material.snackbar.Snackbar loadingSnackbar;

    private com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // TextEditorActivity_0_LengthyGUICreationOperatorMutator
            case 103: {
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
    setContentView(com.amaze.filemanager.R.layout.search);
    switch(MUID_STATIC) {
        // TextEditorActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1103: {
            toolbar = null;
            break;
        }
        // TextEditorActivity_2_InvalidIDFindViewOperatorMutator
        case 2103: {
            toolbar = findViewById(732221);
            break;
        }
        // TextEditorActivity_3_InvalidViewFocusOperatorMutator
        case 3103: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.amaze.filemanager.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // TextEditorActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4103: {
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
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
switch(MUID_STATIC) {
    // TextEditorActivity_5_FindViewByIdReturnsNullOperatorMutator
    case 5103: {
        searchViewLayout = null;
        break;
    }
    // TextEditorActivity_6_InvalidIDFindViewOperatorMutator
    case 6103: {
        searchViewLayout = findViewById(732221);
        break;
    }
    // TextEditorActivity_7_InvalidViewFocusOperatorMutator
    case 7103: {
        /**
        * Inserted by Kadabra
        */
        searchViewLayout = findViewById(com.amaze.filemanager.R.id.textEditorSearchBar);
        searchViewLayout.requestFocus();
        break;
    }
    // TextEditorActivity_8_ViewComponentNotVisibleOperatorMutator
    case 8103: {
        /**
        * Inserted by Kadabra
        */
        searchViewLayout = findViewById(com.amaze.filemanager.R.id.textEditorSearchBar);
        searchViewLayout.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    searchViewLayout = findViewById(com.amaze.filemanager.R.id.textEditorSearchBar);
    break;
}
}
searchViewLayout.setBackgroundColor(getPrimary());
switch(MUID_STATIC) {
// TextEditorActivity_9_InvalidViewFocusOperatorMutator
case 9103: {
    /**
    * Inserted by Kadabra
    */
    searchEditText = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchBox);
    searchEditText.requestFocus();
    break;
}
// TextEditorActivity_10_ViewComponentNotVisibleOperatorMutator
case 10103: {
    /**
    * Inserted by Kadabra
    */
    searchEditText = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchBox);
    searchEditText.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
searchEditText = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchBox);
break;
}
}
switch(MUID_STATIC) {
// TextEditorActivity_11_InvalidViewFocusOperatorMutator
case 11103: {
/**
* Inserted by Kadabra
*/
upButton = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchPrevButton);
upButton.requestFocus();
break;
}
// TextEditorActivity_12_ViewComponentNotVisibleOperatorMutator
case 12103: {
/**
* Inserted by Kadabra
*/
upButton = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchPrevButton);
upButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
upButton = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchPrevButton);
break;
}
}
switch(MUID_STATIC) {
// TextEditorActivity_13_InvalidViewFocusOperatorMutator
case 13103: {
/**
* Inserted by Kadabra
*/
downButton = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchNextButton);
downButton.requestFocus();
break;
}
// TextEditorActivity_14_ViewComponentNotVisibleOperatorMutator
case 14103: {
/**
* Inserted by Kadabra
*/
downButton = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchNextButton);
downButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
downButton = searchViewLayout.findViewById(com.amaze.filemanager.R.id.textEditorSearchNextButton);
break;
}
}
searchEditText.addTextChangedListener(this);
upButton.setOnClickListener(this);
// upButton.setEnabled(false);
downButton.setOnClickListener(this);
// downButton.setEnabled(false);
if (getSupportActionBar() != null) {
boolean useNewStack;
useNewStack = getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK);
getSupportActionBar().setDisplayHomeAsUpEnabled(!useNewStack);
}
switch(MUID_STATIC) {
// TextEditorActivity_15_FindViewByIdReturnsNullOperatorMutator
case 15103: {
mainTextView = null;
break;
}
// TextEditorActivity_16_InvalidIDFindViewOperatorMutator
case 16103: {
mainTextView = findViewById(732221);
break;
}
// TextEditorActivity_17_InvalidViewFocusOperatorMutator
case 17103: {
/**
* Inserted by Kadabra
*/
mainTextView = findViewById(com.amaze.filemanager.R.id.textEditorMainEditText);
mainTextView.requestFocus();
break;
}
// TextEditorActivity_18_ViewComponentNotVisibleOperatorMutator
case 18103: {
/**
* Inserted by Kadabra
*/
mainTextView = findViewById(com.amaze.filemanager.R.id.textEditorMainEditText);
mainTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mainTextView = findViewById(com.amaze.filemanager.R.id.textEditorMainEditText);
break;
}
}
switch(MUID_STATIC) {
// TextEditorActivity_19_FindViewByIdReturnsNullOperatorMutator
case 19103: {
scrollView = null;
break;
}
// TextEditorActivity_20_InvalidIDFindViewOperatorMutator
case 20103: {
scrollView = findViewById(732221);
break;
}
// TextEditorActivity_21_InvalidViewFocusOperatorMutator
case 21103: {
/**
* Inserted by Kadabra
*/
scrollView = findViewById(com.amaze.filemanager.R.id.textEditorScrollView);
scrollView.requestFocus();
break;
}
// TextEditorActivity_22_ViewComponentNotVisibleOperatorMutator
case 22103: {
/**
* Inserted by Kadabra
*/
scrollView = findViewById(com.amaze.filemanager.R.id.textEditorScrollView);
scrollView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
scrollView = findViewById(com.amaze.filemanager.R.id.textEditorScrollView);
break;
}
}
final android.net.Uri uri;
uri = getIntent().getData();
if (uri != null) {
viewModel.setFile(new com.amaze.filemanager.filesystem.EditableFileAbstraction(this, uri));
} else {
android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.no_file_error, android.widget.Toast.LENGTH_LONG).show();
finish();
return;
}
androidx.appcompat.app.ActionBar actionBar;
actionBar = getSupportActionBar();
if (actionBar != null) {
actionBar.setDisplayHomeAsUpEnabled(!getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK));
actionBar.setTitle(viewModel.getFile().name);
}
mainTextView.addTextChangedListener(this);
if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK)) {
mainTextView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.holo_dark_action_mode));
mainTextView.setTextColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.primary_white));
} else if (getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
mainTextView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(this, android.R.color.black));
mainTextView.setTextColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.primary_white));
} else {
mainTextView.setTextColor(com.amaze.filemanager.utils.Utils.getColor(this, com.amaze.filemanager.R.color.primary_grey_900));
}
if (mainTextView.getTypeface() == null) {
mainTextView.setTypeface(android.graphics.Typeface.DEFAULT);
}
inputTypefaceDefault = mainTextView.getTypeface();
inputTypefaceMono = android.graphics.Typeface.MONOSPACE;
if (savedInstanceState != null) {
viewModel.setOriginal(savedInstanceState.getString(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_ORIGINAL_TEXT));
int index;
index = savedInstanceState.getInt(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_INDEX);
mainTextView.setText(savedInstanceState.getString(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_MODIFIED_TEXT));
mainTextView.setScrollY(index);
if (savedInstanceState.getBoolean(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_MONOFONT)) {
mainTextView.setTypeface(inputTypefaceMono);
}
} else {
com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.load(this);
}
switch(MUID_STATIC) {
// TextEditorActivity_23_InvalidIDFindViewOperatorMutator
case 23103: {
initStatusBarResources(findViewById(732221));
break;
}
default: {
initStatusBarResources(findViewById(com.amaze.filemanager.R.id.textEditorRootView));
break;
}
}
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
outState.putString(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_MODIFIED_TEXT, mainTextView.getText() != null ? mainTextView.getText().toString() : "");
outState.putInt(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_INDEX, mainTextView.getScrollY());
outState.putString(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_ORIGINAL_TEXT, viewModel.getOriginal());
outState.putBoolean(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.KEY_MONOFONT, inputTypefaceMono.equals(mainTextView.getTypeface()));
}


private void checkUnsavedChanges() {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
if ((((viewModel.getOriginal() != null) && mainTextView.isShown()) && (mainTextView.getText() != null)) && (!viewModel.getOriginal().equals(mainTextView.getText().toString()))) {
new com.afollestad.materialdialogs.MaterialDialog.Builder(this).title(com.amaze.filemanager.R.string.unsaved_changes).content(com.amaze.filemanager.R.string.unsaved_changes_description).positiveText(com.amaze.filemanager.R.string.yes).negativeText(com.amaze.filemanager.R.string.no).positiveColor(getAccent()).negativeColor(getAccent()).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.saveFile(this, mainTextView.getText().toString());
finish();
}).onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> finish()).build().show();
} else {
finish();
}
}


/**
 * Method initiates a worker thread which writes the {@link #mainTextView} bytes to the defined
 * file/uri 's output stream
 *
 * @param activity
 * 		a reference to the current activity
 * @param editTextString
 * 		the edit text string
 */
private static void saveFile(final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity activity, final java.lang.String editTextString) {
final java.lang.ref.WeakReference<com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity> textEditorActivityWR;
textEditorActivityWR = new java.lang.ref.WeakReference<>(activity);
final java.lang.ref.WeakReference<android.content.Context> appContextWR;
appContextWR = new java.lang.ref.WeakReference<>(activity.getApplicationContext());
com.amaze.filemanager.asynchronous.asynctasks.TaskKt.fromTask(new com.amaze.filemanager.asynchronous.asynctasks.texteditor.write.WriteTextFileTask(activity, editTextString, textEditorActivityWR, appContextWR));
}


/**
 * Initiates loading of file/uri by getting an input stream associated with it on a worker thread
 */
private static void load(final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity activity) {
activity.dismissLoadingSnackbar();
activity.loadingSnackbar = com.google.android.material.snackbar.Snackbar.make(activity.scrollView, com.amaze.filemanager.R.string.loading, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
activity.loadingSnackbar.show();
final java.lang.ref.WeakReference<com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity> textEditorActivityWR;
textEditorActivityWR = new java.lang.ref.WeakReference<>(activity);
final java.lang.ref.WeakReference<android.content.Context> appContextWR;
appContextWR = new java.lang.ref.WeakReference<>(activity.getApplicationContext());
com.amaze.filemanager.asynchronous.asynctasks.TaskKt.fromTask(new com.amaze.filemanager.asynchronous.asynctasks.texteditor.read.ReadTextFileTask(activity, textEditorActivityWR, appContextWR));
}


public void setReadOnly() {
mainTextView.setInputType(android.view.inputmethod.EditorInfo.TYPE_NULL);
mainTextView.setSingleLine(false);
mainTextView.setImeOptions(android.view.inputmethod.EditorInfo.IME_FLAG_NO_ENTER_ACTION);
}


public void dismissLoadingSnackbar() {
if (loadingSnackbar != null) {
loadingSnackbar.dismiss();
loadingSnackbar = null;
}
}


@java.lang.Override
public void onBackPressed() {
checkUnsavedChanges();
}


@java.lang.Override
public boolean onCreateOptionsMenu(android.view.Menu menu) {
getMenuInflater().inflate(com.amaze.filemanager.R.menu.text, menu);
return super.onCreateOptionsMenu(menu);
}


@java.lang.Override
public boolean onPrepareOptionsMenu(android.view.Menu menu) {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
menu.findItem(com.amaze.filemanager.R.id.save).setVisible(viewModel.getModified());
menu.findItem(com.amaze.filemanager.R.id.monofont).setChecked(inputTypefaceMono.equals(mainTextView.getTypeface()));
return super.onPrepareOptionsMenu(menu);
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
final com.amaze.filemanager.filesystem.EditableFileAbstraction editableFileAbstraction;
editableFileAbstraction = viewModel.getFile();
switch (item.getItemId()) {
case android.R.id.home :
checkUnsavedChanges();
break;
case com.amaze.filemanager.R.id.save :
// Make sure EditText is visible before saving!
if (mainTextView.getText() != null) {
com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity.saveFile(this, mainTextView.getText().toString());
}
break;
case com.amaze.filemanager.R.id.details :
if ((editableFileAbstraction.scheme.equals(com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.FILE) && (editableFileAbstraction.hybridFileParcelable.getFile() != null)) && editableFileAbstraction.hybridFileParcelable.getFile().exists()) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPropertiesDialogWithoutPermissions(editableFileAbstraction.hybridFileParcelable, this, getAppTheme());
} else if (editableFileAbstraction.scheme.equals(com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.CONTENT)) {
if (getApplicationContext().getPackageName().equals(editableFileAbstraction.uri.getAuthority())) {
java.io.File file;
file = com.amaze.filemanager.filesystem.files.FileUtils.fromContentUri(editableFileAbstraction.uri);
com.amaze.filemanager.filesystem.HybridFileParcelable p;
p = new com.amaze.filemanager.filesystem.HybridFileParcelable(file.getAbsolutePath());
if (isRootExplorer())
p.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT);

com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPropertiesDialogWithoutPermissions(p, this, getAppTheme());
}
} else {
android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.no_obtainable_info, android.widget.Toast.LENGTH_SHORT).show();
}
break;
case com.amaze.filemanager.R.id.openwith :
if (editableFileAbstraction.scheme.equals(com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.FILE)) {
java.io.File currentFile;
currentFile = editableFileAbstraction.hybridFileParcelable.getFile();
if ((currentFile != null) && currentFile.exists()) {
boolean useNewStack;
useNewStack = getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_TEXTEDITOR_NEWSTACK);
com.amaze.filemanager.filesystem.files.FileUtils.openWith(currentFile, this, useNewStack);
} else {
android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.not_allowed, android.widget.Toast.LENGTH_SHORT).show();
}
} else {
android.widget.Toast.makeText(this, com.amaze.filemanager.R.string.reopen_from_source, android.widget.Toast.LENGTH_SHORT).show();
}
break;
case com.amaze.filemanager.R.id.find :
if (searchViewLayout.isShown())
hideSearchView();
else
revealSearchView();

break;
case com.amaze.filemanager.R.id.monofont :
item.setChecked(!item.isChecked());
mainTextView.setTypeface(item.isChecked() ? inputTypefaceMono : inputTypefaceDefault);
break;
default :
return false;
}
return super.onOptionsItemSelected(item);
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
final java.io.File cacheFile;
cacheFile = viewModel.getCacheFile();
if ((cacheFile != null) && cacheFile.exists()) {
cacheFile.delete();
}
}


@java.lang.Override
public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
// condition to check if callback is called in search editText
if ((searchEditText.getText() != null) && (charSequence.hashCode() == searchEditText.getText().hashCode())) {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
// clearing before adding new values
if (searchTextTask != null) {
searchTextTask.cancel(true);
searchTextTask = null// dereference the task for GC
;// dereference the task for GC

}
cleanSpans(viewModel);
}
}


@java.lang.Override
public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
if ((mainTextView.getText() != null) && (charSequence.hashCode() == mainTextView.getText().hashCode())) {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
final java.util.Timer oldTimer;
oldTimer = viewModel.getTimer();
viewModel.setTimer(null);
if (oldTimer != null) {
oldTimer.cancel();
oldTimer.purge();
}
final java.lang.ref.WeakReference<com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity> textEditorActivityWR;
textEditorActivityWR = new java.lang.ref.WeakReference<>(this);
java.util.Timer newTimer;
newTimer = new java.util.Timer();
newTimer.schedule(new java.util.TimerTask() {
boolean modified;

@java.lang.Override
public void run() {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity textEditorActivity;
textEditorActivity = textEditorActivityWR.get();
if (textEditorActivity == null) {
return;
}
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(textEditorActivity).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
modified = (textEditorActivity.mainTextView.getText() != null) && (!textEditorActivity.mainTextView.getText().toString().equals(viewModel.getOriginal()));
if (viewModel.getModified() != modified) {
viewModel.setModified(modified);
invalidateOptionsMenu();
}
}

}, 250);
viewModel.setTimer(newTimer);
}
}


@java.lang.Override
public void afterTextChanged(android.text.Editable editable) {
// searchBox callback block
if ((searchEditText.getText() != null) && (editable.hashCode() == searchEditText.getText().hashCode())) {
final java.lang.ref.WeakReference<com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity> textEditorActivityWR;
textEditorActivityWR = new java.lang.ref.WeakReference<>(this);
final com.amaze.filemanager.utils.OnProgressUpdate<com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex> onProgressUpdate;
onProgressUpdate = (com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex index) -> {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity textEditorActivity;
textEditorActivity = textEditorActivityWR.get();
if (textEditorActivity == null) {
return;
}
textEditorActivity.colorSearchResult(index, getPrimary());
};
final com.amaze.filemanager.utils.OnAsyncTaskFinished<java.util.List<com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex>> onAsyncTaskFinished;
onAsyncTaskFinished = (java.util.List<com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex> data) -> {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivity textEditorActivity;
textEditorActivity = textEditorActivityWR.get();
if (textEditorActivity == null) {
return;
}
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(textEditorActivity).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
viewModel.setSearchResultIndices(data);
for (com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex searchResultIndex : data) {
textEditorActivity.colorSearchResult(searchResultIndex, getPrimary());
}
if (data.size() != 0) {
textEditorActivity.upButton.setEnabled(true);
textEditorActivity.downButton.setEnabled(true);
// downButton
textEditorActivity.onClick(textEditorActivity.downButton);
} else {
textEditorActivity.upButton.setEnabled(false);
textEditorActivity.downButton.setEnabled(false);
}
};
if (mainTextView.getText() != null) {
searchTextTask = new com.amaze.filemanager.asynchronous.asynctasks.SearchTextTask(mainTextView.getText().toString(), editable.toString(), onProgressUpdate, onAsyncTaskFinished);
searchTextTask.execute();
}
}
}


private void revealSearchView() {
searchViewLayout.setVisibility(android.view.View.VISIBLE);
android.view.animation.Animation animation;
animation = android.view.animation.AnimationUtils.loadAnimation(this, com.amaze.filemanager.R.anim.fade_in_top);
animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
@java.lang.Override
public void onAnimationStart(android.view.animation.Animation animation) {
}


@java.lang.Override
public void onAnimationEnd(android.view.animation.Animation animation) {
searchEditText.requestFocus();
((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE))).showSoftInput(searchEditText, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
}


@java.lang.Override
public void onAnimationRepeat(android.view.animation.Animation animation) {
}

});
searchViewLayout.startAnimation(animation);
}


private void hideSearchView() {
android.view.animation.Animation animation;
animation = android.view.animation.AnimationUtils.loadAnimation(this, com.amaze.filemanager.R.anim.fade_out_top);
animation.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
@java.lang.Override
public void onAnimationStart(android.view.animation.Animation animation) {
}


@java.lang.Override
public void onAnimationEnd(android.view.animation.Animation animation) {
searchViewLayout.setVisibility(android.view.View.GONE);
cleanSpans(viewModel);
searchEditText.setText("");
((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE))).hideSoftInputFromWindow(searchEditText.getWindowToken(), android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY);
}


@java.lang.Override
public void onAnimationRepeat(android.view.animation.Animation animation) {
}

});
searchViewLayout.startAnimation(animation);
}


@java.lang.Override
public void onClick(android.view.View v) {
final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel;
viewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel.class);
switch (v.getId()) {
case com.amaze.filemanager.R.id.textEditorSearchPrevButton :
// upButton
if (viewModel.getCurrent() > 0) {
unhighlightCurrentSearchResult(viewModel);
switch(MUID_STATIC) {
// TextEditorActivity_24_BinaryMutator
case 24103: {
// highlighting previous element in list
viewModel.setCurrent(viewModel.getCurrent() + 1);
break;
}
default: {
// highlighting previous element in list
viewModel.setCurrent(viewModel.getCurrent() - 1);
break;
}
}
highlightCurrentSearchResult(viewModel);
}
break;
case com.amaze.filemanager.R.id.textEditorSearchNextButton :
switch(MUID_STATIC) {
// TextEditorActivity_25_BinaryMutator
case 25103: {
// downButton
if (viewModel.getCurrent() < (viewModel.getSearchResultIndices().size() + 1)) {
unhighlightCurrentSearchResult(viewModel);
viewModel.setCurrent(viewModel.getCurrent() + 1);
highlightCurrentSearchResult(viewModel);
}
break;
}
default: {
// downButton
if (viewModel.getCurrent() < (viewModel.getSearchResultIndices().size() - 1)) {
unhighlightCurrentSearchResult(viewModel);
switch(MUID_STATIC) {
// TextEditorActivity_26_BinaryMutator
case 26103: {
viewModel.setCurrent(viewModel.getCurrent() - 1);
break;
}
default: {
viewModel.setCurrent(viewModel.getCurrent() + 1);
break;
}
}
highlightCurrentSearchResult(viewModel);
}
break;
}
}
break;
default :
throw new java.lang.IllegalStateException();
}
}


private void unhighlightCurrentSearchResult(final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel) {
if (viewModel.getCurrent() == (-1)) {
return;
}
com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex resultIndex;
resultIndex = viewModel.getSearchResultIndices().get(viewModel.getCurrent());
colorSearchResult(resultIndex, getPrimary());
}


private void highlightCurrentSearchResult(final com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel) {
com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex keyValueNew;
keyValueNew = viewModel.getSearchResultIndices().get(viewModel.getCurrent());
colorSearchResult(keyValueNew, getAccent());
// scrolling to the highlighted element
if (getSupportActionBar() != null) {
switch(MUID_STATIC) {
// TextEditorActivity_27_BinaryMutator
case 27103: {
scrollView.scrollTo(0, ((((java.lang.Integer) (keyValueNew.getLineNumber())) + mainTextView.getLineHeight()) + java.lang.Math.round(mainTextView.getLineSpacingExtra())) + getSupportActionBar().getHeight());
break;
}
default: {
switch(MUID_STATIC) {
// TextEditorActivity_28_BinaryMutator
case 28103: {
scrollView.scrollTo(0, ((((java.lang.Integer) (keyValueNew.getLineNumber())) + mainTextView.getLineHeight()) - java.lang.Math.round(mainTextView.getLineSpacingExtra())) - getSupportActionBar().getHeight());
break;
}
default: {
switch(MUID_STATIC) {
// TextEditorActivity_29_BinaryMutator
case 29103: {
scrollView.scrollTo(0, ((((java.lang.Integer) (keyValueNew.getLineNumber())) - mainTextView.getLineHeight()) + java.lang.Math.round(mainTextView.getLineSpacingExtra())) - getSupportActionBar().getHeight());
break;
}
default: {
scrollView.scrollTo(0, ((((java.lang.Integer) (keyValueNew.getLineNumber())) + mainTextView.getLineHeight()) + java.lang.Math.round(mainTextView.getLineSpacingExtra())) - getSupportActionBar().getHeight());
break;
}
}
break;
}
}
break;
}
}
}
}


private void colorSearchResult(com.amaze.filemanager.ui.activities.texteditor.SearchResultIndex resultIndex, @androidx.annotation.ColorInt
int color) {
if (mainTextView.getText() != null) {
mainTextView.getText().setSpan(new android.text.style.BackgroundColorSpan(color), ((java.lang.Integer) (resultIndex.getStartCharNumber())), ((java.lang.Integer) (resultIndex.getEndCharNumber())), android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE);
}
}


private void cleanSpans(com.amaze.filemanager.ui.activities.texteditor.TextEditorActivityViewModel viewModel) {
// resetting current highlight and line number
viewModel.setSearchResultIndices(java.util.Collections.emptyList());
viewModel.setCurrent(-1);
viewModel.setLine(0);
// clearing textView spans
if (mainTextView.getText() != null) {
android.text.style.BackgroundColorSpan[] colorSpans;
colorSpans = mainTextView.getText().getSpans(0, mainTextView.length(), android.text.style.BackgroundColorSpan.class);
for (android.text.style.BackgroundColorSpan colorSpan : colorSpans) {
mainTextView.getText().removeSpan(colorSpan);
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
