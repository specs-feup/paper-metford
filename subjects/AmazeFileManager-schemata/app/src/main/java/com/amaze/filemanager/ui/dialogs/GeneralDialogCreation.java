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
package com.amaze.filemanager.ui.dialogs;
import java.util.Locale;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import com.amaze.filemanager.asynchronous.asynctasks.TaskKt;
import com.github.mikephil.charting.formatter.IValueFormatter;
import androidx.core.view.ViewCompat;
import androidx.annotation.NonNull;
import com.amaze.filemanager.filesystem.RootHelper;
import android.os.Build;
import com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.CalculateHashTask;
import com.amaze.filemanager.database.models.explorer.Sort;
import androidx.annotation.StringRes;
import java.util.List;
import android.widget.TextView;
import com.amaze.filemanager.ui.theme.AppTheme;
import java.util.HashSet;
import java.util.Collections;
import android.graphics.Color;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.graphics.Typeface;
import android.os.AsyncTask;
import androidx.appcompat.widget.AppCompatTextView;
import com.amaze.filemanager.asynchronous.asynctasks.CountItemsOrAndSizeTask;
import com.amaze.filemanager.filesystem.files.EncryptDecryptUtils;
import androidx.appcompat.widget.AppCompatButton;
import com.amaze.filemanager.database.SortHandler;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import android.text.SpannableString;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask;
import android.view.LayoutInflater;
import android.text.InputType;
import com.afollestad.materialdialogs.DialogAction;
import com.amaze.filemanager.filesystem.compressed.CompressedHelper;
import com.amaze.filemanager.ui.views.WarnableTextInputValidator;
import java.util.Objects;
import com.amaze.filemanager.ui.ExtensionsKt;
import java.io.File;
import com.afollestad.materialdialogs.MaterialDialog;
import androidx.annotation.Nullable;
import com.github.mikephil.charting.components.Legend;
import java.util.Set;
import com.github.mikephil.charting.data.PieEntry;
import com.amaze.filemanager.filesystem.files.sort.SortBy;
import androidx.core.text.TextUtilsCompat;
import android.text.format.Formatter;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.net.Uri;
import org.slf4j.Logger;
import com.github.mikephil.charting.utils.ViewPortHandler;
import static android.os.Build.VERSION.SDK_INT;
import com.amaze.filemanager.ui.fragments.MainFragment;
import com.amaze.filemanager.R;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.charts.PieChart;
import com.amaze.filemanager.ui.activities.superclasses.ThemedActivity;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.FileProperties;
import com.amaze.filemanager.filesystem.root.ChangeFilePermissionsCommand;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import com.amaze.filemanager.application.AppConfig;
import java.io.IOException;
import com.amaze.filemanager.filesystem.files.sort.SortOrder;
import android.text.TextUtils;
import android.content.Intent;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SORTBY_ONLY_THIS;
import android.view.View;
import androidx.appcompat.widget.AppCompatCheckBox;
import com.amaze.filemanager.ui.views.WarnableTextInputLayout;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException;
import com.amaze.filemanager.filesystem.files.sort.SortType;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.github.mikephil.charting.data.PieData;
import java.util.concurrent.ExecutorService;
import com.amaze.filemanager.databinding.DialogSigninWithGoogleBinding;
import com.afollestad.materialdialogs.Theme;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Here are a lot of function that create material dialogs
 *
 * @author Emmanuel on 17/5/2017, at 13:27.
 */
public class GeneralDialogCreation {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.class);

    public static com.afollestad.materialdialogs.MaterialDialog showBasicDialog(com.amaze.filemanager.ui.activities.superclasses.ThemedActivity themedActivity, @androidx.annotation.StringRes
    int content, @androidx.annotation.StringRes
    int title, @androidx.annotation.StringRes
    int postiveText, @androidx.annotation.StringRes
    int negativeText) {
        int accentColor;
        accentColor = themedActivity.getAccent();
        com.afollestad.materialdialogs.MaterialDialog.Builder a;
        a = new com.afollestad.materialdialogs.MaterialDialog.Builder(themedActivity).content(content).widgetColor(accentColor).theme(themedActivity.getAppTheme().getMaterialDialogTheme()).title(title).positiveText(postiveText).positiveColor(accentColor).negativeText(negativeText).negativeColor(accentColor);
        return a.build();
    }


    public static com.afollestad.materialdialogs.MaterialDialog showNameDialog(final com.amaze.filemanager.ui.activities.MainActivity m, java.lang.String hint, java.lang.String prefill, java.lang.String title, java.lang.String positiveButtonText, java.lang.String neutralButtonText, java.lang.String negativeButtonText, com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback positiveButtonAction, com.amaze.filemanager.ui.views.WarnableTextInputValidator.OnTextValidate validator) {
        int accentColor;
        accentColor = m.getAccent();
        com.afollestad.materialdialogs.MaterialDialog.Builder builder;
        builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(m);
        android.view.View dialogView;
        dialogView = m.getLayoutInflater().inflate(com.amaze.filemanager.R.layout.dialog_singleedittext, null);
        androidx.appcompat.widget.AppCompatEditText textfield;
        switch(MUID_STATIC) {
            // GeneralDialogCreation_0_InvalidViewFocusOperatorMutator
            case 97: {
                /**
                * Inserted by Kadabra
                */
                textfield = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
                textfield.requestFocus();
                break;
            }
            // GeneralDialogCreation_1_ViewComponentNotVisibleOperatorMutator
            case 1097: {
                /**
                * Inserted by Kadabra
                */
                textfield = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
                textfield.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            textfield = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
            break;
        }
    }
    textfield.setHint(hint);
    textfield.setText(prefill);
    com.amaze.filemanager.ui.views.WarnableTextInputLayout tilTextfield;
    switch(MUID_STATIC) {
        // GeneralDialogCreation_2_InvalidViewFocusOperatorMutator
        case 2097: {
            /**
            * Inserted by Kadabra
            */
            tilTextfield = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
            tilTextfield.requestFocus();
            break;
        }
        // GeneralDialogCreation_3_ViewComponentNotVisibleOperatorMutator
        case 3097: {
            /**
            * Inserted by Kadabra
            */
            tilTextfield = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
            tilTextfield.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        tilTextfield = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
        break;
    }
}
dialogView.post(() -> com.amaze.filemanager.ui.ExtensionsKt.openKeyboard(textfield, m.getApplicationContext()));
builder.customView(dialogView, false).widgetColor(accentColor).theme(m.getAppTheme().getMaterialDialogTheme()).title(title).positiveText(positiveButtonText).onPositive(positiveButtonAction);
if (neutralButtonText != null) {
    builder.neutralText(neutralButtonText);
}
if (negativeButtonText != null) {
    builder.negativeText(negativeButtonText);
    builder.negativeColor(accentColor);
}
com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = builder.show();
com.amaze.filemanager.ui.views.WarnableTextInputValidator textInputValidator;
textInputValidator = new com.amaze.filemanager.ui.views.WarnableTextInputValidator(builder.getContext(), textfield, tilTextfield, dialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE), validator);
if (!android.text.TextUtils.isEmpty(prefill))
    textInputValidator.afterTextChanged(textfield.getText());

return dialog;
}


@java.lang.SuppressWarnings("ConstantConditions")
public static void deleteFilesDialog(@androidx.annotation.NonNull
final android.content.Context context, @androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
final java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> positions, @androidx.annotation.NonNull
com.amaze.filemanager.ui.theme.AppTheme appTheme) {
final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> itemsToDelete;
itemsToDelete = new java.util.ArrayList<>();
int accentColor;
accentColor = mainActivity.getAccent();
android.content.SharedPreferences sharedPreferences;
sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
boolean needConfirmation;
needConfirmation = sharedPreferences.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_DELETE_CONFIRMATION, com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.DEFAULT_PREFERENCE_DELETE_CONFIRMATION);
android.view.View dialogView;
dialogView = android.view.LayoutInflater.from(context).inflate(com.amaze.filemanager.R.layout.dialog_delete, null);
android.widget.TextView deleteDisclaimerTextView;
switch(MUID_STATIC) {
    // GeneralDialogCreation_4_InvalidViewFocusOperatorMutator
    case 4097: {
        /**
        * Inserted by Kadabra
        */
        deleteDisclaimerTextView = dialogView.findViewById(com.amaze.filemanager.R.id.dialog_delete_disclaimer);
        deleteDisclaimerTextView.requestFocus();
        break;
    }
    // GeneralDialogCreation_5_ViewComponentNotVisibleOperatorMutator
    case 5097: {
        /**
        * Inserted by Kadabra
        */
        deleteDisclaimerTextView = dialogView.findViewById(com.amaze.filemanager.R.id.dialog_delete_disclaimer);
        deleteDisclaimerTextView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    deleteDisclaimerTextView = dialogView.findViewById(com.amaze.filemanager.R.id.dialog_delete_disclaimer);
    break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox deletePermanentlyCheckbox;
switch(MUID_STATIC) {
// GeneralDialogCreation_6_InvalidViewFocusOperatorMutator
case 6097: {
    /**
    * Inserted by Kadabra
    */
    deletePermanentlyCheckbox = dialogView.findViewById(com.amaze.filemanager.R.id.delete_permanently_checkbox);
    deletePermanentlyCheckbox.requestFocus();
    break;
}
// GeneralDialogCreation_7_ViewComponentNotVisibleOperatorMutator
case 7097: {
    /**
    * Inserted by Kadabra
    */
    deletePermanentlyCheckbox = dialogView.findViewById(com.amaze.filemanager.R.id.delete_permanently_checkbox);
    deletePermanentlyCheckbox.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
deletePermanentlyCheckbox = dialogView.findViewById(com.amaze.filemanager.R.id.delete_permanently_checkbox);
break;
}
}
if (positions.get(0).generateBaseFile().isLocal()) {
// FIXME: make sure dialog is not shown for zero items
// allow trash bin delete only for local files for now
deletePermanentlyCheckbox.setVisibility(android.view.View.VISIBLE);
} else {
deleteDisclaimerTextView.setText(context.getString(com.amaze.filemanager.R.string.dialog_delete_disclaimer));
}
// Build dialog with custom view layout and accent color.
com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(context).title(context.getString(com.amaze.filemanager.R.string.dialog_delete_title)).customView(dialogView, true).theme(appTheme.getMaterialDialogTheme()).negativeText(context.getString(com.amaze.filemanager.R.string.cancel).toUpperCase()).positiveText(context.getString(com.amaze.filemanager.R.string.delete).toUpperCase()).positiveColor(accentColor).negativeColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog1,com.afollestad.materialdialogs.DialogAction which) -> {
android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.deleting), android.widget.Toast.LENGTH_SHORT).show();
mainActivity.mainActivityHelper.deleteFiles(itemsToDelete, deletePermanentlyCheckbox.isChecked() || (deletePermanentlyCheckbox.getVisibility() == android.view.View.GONE));
}).build();
// Get views from custom layout to set text values.
final androidx.appcompat.widget.AppCompatTextView categoryDirectories;
switch(MUID_STATIC) {
// GeneralDialogCreation_8_InvalidViewFocusOperatorMutator
case 8097: {
/**
* Inserted by Kadabra
*/
categoryDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_directories);
categoryDirectories.requestFocus();
break;
}
// GeneralDialogCreation_9_ViewComponentNotVisibleOperatorMutator
case 9097: {
/**
* Inserted by Kadabra
*/
categoryDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_directories);
categoryDirectories.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
categoryDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_directories);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView categoryFiles;
switch(MUID_STATIC) {
// GeneralDialogCreation_10_InvalidViewFocusOperatorMutator
case 10097: {
/**
* Inserted by Kadabra
*/
categoryFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_files);
categoryFiles.requestFocus();
break;
}
// GeneralDialogCreation_11_ViewComponentNotVisibleOperatorMutator
case 11097: {
/**
* Inserted by Kadabra
*/
categoryFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_files);
categoryFiles.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
categoryFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_files);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView listDirectories;
switch(MUID_STATIC) {
// GeneralDialogCreation_12_InvalidViewFocusOperatorMutator
case 12097: {
/**
* Inserted by Kadabra
*/
listDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_directories);
listDirectories.requestFocus();
break;
}
// GeneralDialogCreation_13_ViewComponentNotVisibleOperatorMutator
case 13097: {
/**
* Inserted by Kadabra
*/
listDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_directories);
listDirectories.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
listDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_directories);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView listFiles;
switch(MUID_STATIC) {
// GeneralDialogCreation_14_InvalidViewFocusOperatorMutator
case 14097: {
/**
* Inserted by Kadabra
*/
listFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_files);
listFiles.requestFocus();
break;
}
// GeneralDialogCreation_15_ViewComponentNotVisibleOperatorMutator
case 15097: {
/**
* Inserted by Kadabra
*/
listFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_files);
listFiles.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
listFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_files);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView total;
switch(MUID_STATIC) {
// GeneralDialogCreation_16_InvalidViewFocusOperatorMutator
case 16097: {
/**
* Inserted by Kadabra
*/
total = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.total);
total.requestFocus();
break;
}
// GeneralDialogCreation_17_ViewComponentNotVisibleOperatorMutator
case 17097: {
/**
* Inserted by Kadabra
*/
total = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.total);
total.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
total = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.total);
break;
}
}
new android.os.AsyncTask<java.lang.Void, java.lang.Object, java.lang.Void>() {
long sizeTotal = 0;

java.lang.StringBuilder files = new java.lang.StringBuilder();

java.lang.StringBuilder directories = new java.lang.StringBuilder();

int counterDirectories = 0;

int counterFiles = 0;

@java.lang.Override
protected void onPreExecute() {
super.onPreExecute();
if (needConfirmation) {
listFiles.setText(context.getString(com.amaze.filemanager.R.string.loading));
listDirectories.setText(context.getString(com.amaze.filemanager.R.string.loading));
total.setText(context.getString(com.amaze.filemanager.R.string.loading));
}
}


@java.lang.Override
protected java.lang.Void doInBackground(java.lang.Void... params) {
for (int i = 0; i < positions.size(); i++) {
final com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElement;
layoutElement = positions.get(i);
itemsToDelete.add(layoutElement.generateBaseFile());
if (needConfirmation) {
// Build list of directories to delete.
if (layoutElement.isDirectory) {
// Don't add newline between category and list.
if (counterDirectories != 0) {
directories.append("\n");
}
long sizeDirectory;
sizeDirectory = layoutElement.generateBaseFile().folderSize(context);
directories.append(++counterDirectories).append(". ").append(layoutElement.title).append(" (").append(android.text.format.Formatter.formatFileSize(context, sizeDirectory)).append(")");
sizeTotal += sizeDirectory;
// Build list of files to delete.
} else {
// Don't add newline between category and list.
if (counterFiles != 0) {
files.append("\n");
}
files.append(++counterFiles).append(". ").append(layoutElement.title).append(" (").append(layoutElement.size).append(")");
sizeTotal += layoutElement.longSize;
}
publishProgress(sizeTotal, counterFiles, counterDirectories, files, directories);
}
}
return null;
}


@java.lang.Override
protected void onProgressUpdate(java.lang.Object... result) {
super.onProgressUpdate(result);
if (needConfirmation) {
int tempCounterFiles;
tempCounterFiles = ((int) (result[1]));
int tempCounterDirectories;
tempCounterDirectories = ((int) (result[2]));
long tempSizeTotal;
tempSizeTotal = ((long) (result[0]));
java.lang.StringBuilder tempFilesStringBuilder;
tempFilesStringBuilder = ((java.lang.StringBuilder) (result[3]));
java.lang.StringBuilder tempDirectoriesStringBuilder;
tempDirectoriesStringBuilder = ((java.lang.StringBuilder) (result[4]));
updateViews(tempSizeTotal, tempFilesStringBuilder, tempDirectoriesStringBuilder, tempCounterFiles, tempCounterDirectories);
}
}


@java.lang.Override
protected void onPostExecute(java.lang.Void aVoid) {
super.onPostExecute(aVoid);
if (needConfirmation) {
updateViews(sizeTotal, files, directories, counterFiles, counterDirectories);
} else {
android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.deleting), android.widget.Toast.LENGTH_SHORT).show();
mainActivity.mainActivityHelper.deleteFiles(itemsToDelete, deletePermanentlyCheckbox.isChecked() || (deletePermanentlyCheckbox.getVisibility() == android.view.View.GONE));
}
}


private void updateViews(long tempSizeTotal, java.lang.StringBuilder filesStringBuilder, java.lang.StringBuilder directoriesStringBuilder, int... values) {
int tempCounterFiles;
tempCounterFiles = values[0];
int tempCounterDirectories;
tempCounterDirectories = values[1];
// Hide category and list for directories when zero.
if (tempCounterDirectories == 0) {
if (tempCounterDirectories == 0) {
categoryDirectories.setVisibility(android.view.View.GONE);
listDirectories.setVisibility(android.view.View.GONE);
}
// Hide category and list for files when zero.
}
if (tempCounterFiles == 0) {
categoryFiles.setVisibility(android.view.View.GONE);
listFiles.setVisibility(android.view.View.GONE);
}
if ((tempCounterDirectories != 0) || (tempCounterFiles != 0)) {
listDirectories.setText(directoriesStringBuilder);
if ((listDirectories.getVisibility() != android.view.View.VISIBLE) && (tempCounterDirectories != 0))
listDirectories.setVisibility(android.view.View.VISIBLE);

listFiles.setText(filesStringBuilder);
if ((listFiles.getVisibility() != android.view.View.VISIBLE) && (tempCounterFiles != 0))
listFiles.setVisibility(android.view.View.VISIBLE);

if ((categoryDirectories.getVisibility() != android.view.View.VISIBLE) && (tempCounterDirectories != 0))
categoryDirectories.setVisibility(android.view.View.VISIBLE);

if ((categoryFiles.getVisibility() != android.view.View.VISIBLE) && (tempCounterFiles != 0))
categoryFiles.setVisibility(android.view.View.VISIBLE);

}
switch(MUID_STATIC) {
// GeneralDialogCreation_18_BinaryMutator
case 18097: {
// Show total size with at least one directory or file and size is not zero.
if (((tempCounterFiles - tempCounterDirectories) > 1) && (tempSizeTotal > 0)) {
java.lang.StringBuilder builderTotal;
builderTotal = new java.lang.StringBuilder().append(context.getString(com.amaze.filemanager.R.string.total)).append(" ").append(android.text.format.Formatter.formatFileSize(context, tempSizeTotal));
total.setText(builderTotal);
if (total.getVisibility() != android.view.View.VISIBLE) {
total.setVisibility(android.view.View.VISIBLE);
}
} else {
total.setVisibility(android.view.View.GONE);
}
break;
}
default: {
// Show total size with at least one directory or file and size is not zero.
if (((tempCounterFiles + tempCounterDirectories) > 1) && (tempSizeTotal > 0)) {
java.lang.StringBuilder builderTotal;
builderTotal = new java.lang.StringBuilder().append(context.getString(com.amaze.filemanager.R.string.total)).append(" ").append(android.text.format.Formatter.formatFileSize(context, tempSizeTotal));
total.setText(builderTotal);
if (total.getVisibility() != android.view.View.VISIBLE)
total.setVisibility(android.view.View.VISIBLE);

} else {
total.setVisibility(android.view.View.GONE);
}
break;
}
}
}

}.execute();
// Set category text color for Jelly Bean (API 16) and later.
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
categoryDirectories.setTextColor(accentColor);
categoryFiles.setTextColor(accentColor);
}
if (needConfirmation) {
// Show dialog on screen.
dialog.show();
}
}


/**
 * Displays a dialog prompting user to restore files in trash bin.
 *
 * @param context
 * @param mainActivity
 * @param positions
 * @param appTheme
 */
@java.lang.SuppressWarnings({ "ConstantConditions", "PMD.NPathComplexity" })
public static void restoreFilesDialog(@androidx.annotation.NonNull
final android.content.Context context, @androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.MainActivity mainActivity, @androidx.annotation.NonNull
final java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> positions, @androidx.annotation.NonNull
com.amaze.filemanager.ui.theme.AppTheme appTheme) {
final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> itemsToDelete;
itemsToDelete = new java.util.ArrayList<>();
int accentColor;
accentColor = mainActivity.getAccent();
android.view.View dialogView;
dialogView = android.view.LayoutInflater.from(context).inflate(com.amaze.filemanager.R.layout.dialog_delete, null);
android.widget.TextView deleteDisclaimerTextView;
switch(MUID_STATIC) {
// GeneralDialogCreation_19_InvalidViewFocusOperatorMutator
case 19097: {
/**
* Inserted by Kadabra
*/
deleteDisclaimerTextView = dialogView.findViewById(com.amaze.filemanager.R.id.dialog_delete_disclaimer);
deleteDisclaimerTextView.requestFocus();
break;
}
// GeneralDialogCreation_20_ViewComponentNotVisibleOperatorMutator
case 20097: {
/**
* Inserted by Kadabra
*/
deleteDisclaimerTextView = dialogView.findViewById(com.amaze.filemanager.R.id.dialog_delete_disclaimer);
deleteDisclaimerTextView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
deleteDisclaimerTextView = dialogView.findViewById(com.amaze.filemanager.R.id.dialog_delete_disclaimer);
break;
}
}
deleteDisclaimerTextView.setText(context.getString(com.amaze.filemanager.R.string.dialog_restore_disclaimer));
// Build dialog with custom view layout and accent color.
com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(context).title(context.getString(com.amaze.filemanager.R.string.restore_files)).customView(dialogView, true).theme(appTheme.getMaterialDialogTheme()).negativeText(context.getString(com.amaze.filemanager.R.string.cancel).toUpperCase()).positiveText(context.getString(com.amaze.filemanager.R.string.done).toUpperCase()).positiveColor(accentColor).negativeColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog1,com.afollestad.materialdialogs.DialogAction which) -> {
android.widget.Toast.makeText(context, context.getString(com.amaze.filemanager.R.string.processing), android.widget.Toast.LENGTH_SHORT).show();
mainActivity.getCurrentMainFragment().getMainActivityViewModel().restoreFromBin(positions);
}).build();
// Get views from custom layout to set text values.
final androidx.appcompat.widget.AppCompatTextView categoryDirectories;
switch(MUID_STATIC) {
// GeneralDialogCreation_21_InvalidViewFocusOperatorMutator
case 21097: {
/**
* Inserted by Kadabra
*/
categoryDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_directories);
categoryDirectories.requestFocus();
break;
}
// GeneralDialogCreation_22_ViewComponentNotVisibleOperatorMutator
case 22097: {
/**
* Inserted by Kadabra
*/
categoryDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_directories);
categoryDirectories.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
categoryDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_directories);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView categoryFiles;
switch(MUID_STATIC) {
// GeneralDialogCreation_23_InvalidViewFocusOperatorMutator
case 23097: {
/**
* Inserted by Kadabra
*/
categoryFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_files);
categoryFiles.requestFocus();
break;
}
// GeneralDialogCreation_24_ViewComponentNotVisibleOperatorMutator
case 24097: {
/**
* Inserted by Kadabra
*/
categoryFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_files);
categoryFiles.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
categoryFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.category_files);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView listDirectories;
switch(MUID_STATIC) {
// GeneralDialogCreation_25_InvalidViewFocusOperatorMutator
case 25097: {
/**
* Inserted by Kadabra
*/
listDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_directories);
listDirectories.requestFocus();
break;
}
// GeneralDialogCreation_26_ViewComponentNotVisibleOperatorMutator
case 26097: {
/**
* Inserted by Kadabra
*/
listDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_directories);
listDirectories.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
listDirectories = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_directories);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView listFiles;
switch(MUID_STATIC) {
// GeneralDialogCreation_27_InvalidViewFocusOperatorMutator
case 27097: {
/**
* Inserted by Kadabra
*/
listFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_files);
listFiles.requestFocus();
break;
}
// GeneralDialogCreation_28_ViewComponentNotVisibleOperatorMutator
case 28097: {
/**
* Inserted by Kadabra
*/
listFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_files);
listFiles.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
listFiles = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.list_files);
break;
}
}
final androidx.appcompat.widget.AppCompatTextView total;
switch(MUID_STATIC) {
// GeneralDialogCreation_29_InvalidViewFocusOperatorMutator
case 29097: {
/**
* Inserted by Kadabra
*/
total = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.total);
total.requestFocus();
break;
}
// GeneralDialogCreation_30_ViewComponentNotVisibleOperatorMutator
case 30097: {
/**
* Inserted by Kadabra
*/
total = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.total);
total.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
total = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.total);
break;
}
}
new android.os.AsyncTask<java.lang.Void, java.lang.Object, java.lang.Void>() {
long sizeTotal = 0;

java.lang.StringBuilder files = new java.lang.StringBuilder();

java.lang.StringBuilder directories = new java.lang.StringBuilder();

int counterDirectories = 0;

int counterFiles = 0;

@java.lang.Override
protected void onPreExecute() {
super.onPreExecute();
listFiles.setText(context.getString(com.amaze.filemanager.R.string.loading));
listDirectories.setText(context.getString(com.amaze.filemanager.R.string.loading));
total.setText(context.getString(com.amaze.filemanager.R.string.loading));
}


@java.lang.Override
protected java.lang.Void doInBackground(java.lang.Void... params) {
for (int i = 0; i < positions.size(); i++) {
final com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElement;
layoutElement = positions.get(i);
itemsToDelete.add(layoutElement.generateBaseFile());
// Build list of directories to delete.
if (layoutElement.isDirectory) {
// Don't add newline between category and list.
if (counterDirectories != 0) {
directories.append("\n");
}
long sizeDirectory;
sizeDirectory = layoutElement.generateBaseFile().folderSize(context);
directories.append(++counterDirectories).append(". ").append(layoutElement.title).append(" (").append(android.text.format.Formatter.formatFileSize(context, sizeDirectory)).append(")");
sizeTotal += sizeDirectory;
// Build list of files to delete.
} else {
// Don't add newline between category and list.
if (counterFiles != 0) {
files.append("\n");
}
files.append(++counterFiles).append(". ").append(layoutElement.title).append(" (").append(layoutElement.size).append(")");
sizeTotal += layoutElement.longSize;
}
publishProgress(sizeTotal, counterFiles, counterDirectories, files, directories);
}
return null;
}


@java.lang.Override
protected void onProgressUpdate(java.lang.Object... result) {
super.onProgressUpdate(result);
int tempCounterFiles;
tempCounterFiles = ((int) (result[1]));
int tempCounterDirectories;
tempCounterDirectories = ((int) (result[2]));
long tempSizeTotal;
tempSizeTotal = ((long) (result[0]));
java.lang.StringBuilder tempFilesStringBuilder;
tempFilesStringBuilder = ((java.lang.StringBuilder) (result[3]));
java.lang.StringBuilder tempDirectoriesStringBuilder;
tempDirectoriesStringBuilder = ((java.lang.StringBuilder) (result[4]));
updateViews(tempSizeTotal, tempFilesStringBuilder, tempDirectoriesStringBuilder, tempCounterFiles, tempCounterDirectories);
}


@java.lang.Override
protected void onPostExecute(java.lang.Void aVoid) {
super.onPostExecute(aVoid);
// do nothing
}


private void updateViews(long tempSizeTotal, java.lang.StringBuilder filesStringBuilder, java.lang.StringBuilder directoriesStringBuilder, int... values) {
int tempCounterFiles;
tempCounterFiles = values[0];
int tempCounterDirectories;
tempCounterDirectories = values[1];
// Hide category and list for directories when zero.
if (tempCounterDirectories == 0) {
if (tempCounterDirectories == 0) {
categoryDirectories.setVisibility(android.view.View.GONE);
listDirectories.setVisibility(android.view.View.GONE);
}
// Hide category and list for files when zero.
}
if (tempCounterFiles == 0) {
categoryFiles.setVisibility(android.view.View.GONE);
listFiles.setVisibility(android.view.View.GONE);
}
if ((tempCounterDirectories != 0) || (tempCounterFiles != 0)) {
listDirectories.setText(directoriesStringBuilder);
if ((listDirectories.getVisibility() != android.view.View.VISIBLE) && (tempCounterDirectories != 0))
listDirectories.setVisibility(android.view.View.VISIBLE);

listFiles.setText(filesStringBuilder);
if ((listFiles.getVisibility() != android.view.View.VISIBLE) && (tempCounterFiles != 0))
listFiles.setVisibility(android.view.View.VISIBLE);

if ((categoryDirectories.getVisibility() != android.view.View.VISIBLE) && (tempCounterDirectories != 0))
categoryDirectories.setVisibility(android.view.View.VISIBLE);

if ((categoryFiles.getVisibility() != android.view.View.VISIBLE) && (tempCounterFiles != 0))
categoryFiles.setVisibility(android.view.View.VISIBLE);

}
switch(MUID_STATIC) {
// GeneralDialogCreation_31_BinaryMutator
case 31097: {
// Show total size with at least one directory or file and size is not zero.
if (((tempCounterFiles - tempCounterDirectories) > 1) && (tempSizeTotal > 0)) {
java.lang.StringBuilder builderTotal;
builderTotal = new java.lang.StringBuilder().append(context.getString(com.amaze.filemanager.R.string.total)).append(" ").append(android.text.format.Formatter.formatFileSize(context, tempSizeTotal));
total.setText(builderTotal);
if (total.getVisibility() != android.view.View.VISIBLE) {
total.setVisibility(android.view.View.VISIBLE);
}
} else {
total.setVisibility(android.view.View.GONE);
}
break;
}
default: {
// Show total size with at least one directory or file and size is not zero.
if (((tempCounterFiles + tempCounterDirectories) > 1) && (tempSizeTotal > 0)) {
java.lang.StringBuilder builderTotal;
builderTotal = new java.lang.StringBuilder().append(context.getString(com.amaze.filemanager.R.string.total)).append(" ").append(android.text.format.Formatter.formatFileSize(context, tempSizeTotal));
total.setText(builderTotal);
if (total.getVisibility() != android.view.View.VISIBLE)
total.setVisibility(android.view.View.VISIBLE);

} else {
total.setVisibility(android.view.View.GONE);
}
break;
}
}
}

}.execute();
// Set category text color for Jelly Bean (API 16) and later.
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
categoryDirectories.setTextColor(accentColor);
categoryFiles.setTextColor(accentColor);
}
dialog.show();
}


public static void showPropertiesDialogWithPermissions(@androidx.annotation.NonNull
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile, @androidx.annotation.Nullable
final java.lang.String permissions, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity themedActivity, @androidx.annotation.NonNull
com.amaze.filemanager.ui.fragments.MainFragment mainFragment, boolean isRoot, @androidx.annotation.NonNull
com.amaze.filemanager.ui.theme.AppTheme appTheme) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPropertiesDialog(baseFile, themedActivity, mainFragment, permissions, isRoot, appTheme, false);
}


public static void showPropertiesDialogWithoutPermissions(@androidx.annotation.NonNull
final com.amaze.filemanager.filesystem.HybridFileParcelable f, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.superclasses.ThemedActivity themedActivity, @androidx.annotation.NonNull
com.amaze.filemanager.ui.theme.AppTheme appTheme) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPropertiesDialog(f, themedActivity, null, null, false, appTheme, false);
}


public static void showPropertiesDialogForStorage(@androidx.annotation.NonNull
final com.amaze.filemanager.filesystem.HybridFileParcelable f, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity themedActivity, @androidx.annotation.NonNull
com.amaze.filemanager.ui.theme.AppTheme appTheme) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPropertiesDialog(f, themedActivity, null, null, false, appTheme, true);
}


private static void showPropertiesDialog(@androidx.annotation.NonNull
final com.amaze.filemanager.filesystem.HybridFileParcelable baseFile, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.superclasses.ThemedActivity themedActivity, @androidx.annotation.Nullable
com.amaze.filemanager.ui.fragments.MainFragment mainFragment, @androidx.annotation.Nullable
final java.lang.String permissions, boolean isRoot, @androidx.annotation.NonNull
com.amaze.filemanager.ui.theme.AppTheme appTheme, boolean forStorage) {
final java.util.concurrent.ExecutorService executor;
executor = java.util.concurrent.Executors.newFixedThreadPool(3);
final android.content.Context c;
c = themedActivity.getApplicationContext();
int accentColor;
accentColor = themedActivity.getAccent();
long last;
last = baseFile.getDate();
final java.lang.String date;
date = com.amaze.filemanager.utils.Utils.getDate(themedActivity, last);
final java.lang.String items;
items = c.getString(com.amaze.filemanager.R.string.calculating);
final java.lang.String name;
name = baseFile.getName(c);
final java.lang.String parent;
parent = baseFile.getReadablePath(baseFile.getParent(c));
java.io.File nomediaFile;
nomediaFile = (baseFile.isDirectory()) ? new java.io.File((baseFile.getPath() + "/") + com.amaze.filemanager.filesystem.files.FileUtils.NOMEDIA_FILE) : null;
com.afollestad.materialdialogs.MaterialDialog.Builder builder;
builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(themedActivity);
builder.title(c.getString(com.amaze.filemanager.R.string.properties));
builder.theme(appTheme.getMaterialDialogTheme());
android.view.View v;
v = themedActivity.getLayoutInflater().inflate(com.amaze.filemanager.R.layout.properties_dialog, null);
androidx.appcompat.widget.AppCompatTextView itemsText;
switch(MUID_STATIC) {
// GeneralDialogCreation_32_InvalidViewFocusOperatorMutator
case 32097: {
/**
* Inserted by Kadabra
*/
itemsText = v.findViewById(com.amaze.filemanager.R.id.t7);
itemsText.requestFocus();
break;
}
// GeneralDialogCreation_33_ViewComponentNotVisibleOperatorMutator
case 33097: {
/**
* Inserted by Kadabra
*/
itemsText = v.findViewById(com.amaze.filemanager.R.id.t7);
itemsText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
itemsText = v.findViewById(com.amaze.filemanager.R.id.t7);
break;
}
}
androidx.appcompat.widget.AppCompatCheckBox nomediaCheckBox;
switch(MUID_STATIC) {
// GeneralDialogCreation_34_InvalidViewFocusOperatorMutator
case 34097: {
/**
* Inserted by Kadabra
*/
nomediaCheckBox = v.findViewById(com.amaze.filemanager.R.id.nomediacheckbox);
nomediaCheckBox.requestFocus();
break;
}
// GeneralDialogCreation_35_ViewComponentNotVisibleOperatorMutator
case 35097: {
/**
* Inserted by Kadabra
*/
nomediaCheckBox = v.findViewById(com.amaze.filemanager.R.id.nomediacheckbox);
nomediaCheckBox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
nomediaCheckBox = v.findViewById(com.amaze.filemanager.R.id.nomediacheckbox);
break;
}
}
/* View setup */
{
androidx.appcompat.widget.AppCompatTextView mNameTitle;
switch(MUID_STATIC) {
// GeneralDialogCreation_36_InvalidViewFocusOperatorMutator
case 36097: {
/**
* Inserted by Kadabra
*/
mNameTitle = v.findViewById(com.amaze.filemanager.R.id.title_name);
mNameTitle.requestFocus();
break;
}
// GeneralDialogCreation_37_ViewComponentNotVisibleOperatorMutator
case 37097: {
/**
* Inserted by Kadabra
*/
mNameTitle = v.findViewById(com.amaze.filemanager.R.id.title_name);
mNameTitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mNameTitle = v.findViewById(com.amaze.filemanager.R.id.title_name);
break;
}
}
mNameTitle.setTextColor(accentColor);
androidx.appcompat.widget.AppCompatTextView mDateTitle;
switch(MUID_STATIC) {
// GeneralDialogCreation_38_InvalidViewFocusOperatorMutator
case 38097: {
/**
* Inserted by Kadabra
*/
mDateTitle = v.findViewById(com.amaze.filemanager.R.id.title_date);
mDateTitle.requestFocus();
break;
}
// GeneralDialogCreation_39_ViewComponentNotVisibleOperatorMutator
case 39097: {
/**
* Inserted by Kadabra
*/
mDateTitle = v.findViewById(com.amaze.filemanager.R.id.title_date);
mDateTitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDateTitle = v.findViewById(com.amaze.filemanager.R.id.title_date);
break;
}
}
mDateTitle.setTextColor(accentColor);
androidx.appcompat.widget.AppCompatTextView mSizeTitle;
switch(MUID_STATIC) {
// GeneralDialogCreation_40_InvalidViewFocusOperatorMutator
case 40097: {
/**
* Inserted by Kadabra
*/
mSizeTitle = v.findViewById(com.amaze.filemanager.R.id.title_size);
mSizeTitle.requestFocus();
break;
}
// GeneralDialogCreation_41_ViewComponentNotVisibleOperatorMutator
case 41097: {
/**
* Inserted by Kadabra
*/
mSizeTitle = v.findViewById(com.amaze.filemanager.R.id.title_size);
mSizeTitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSizeTitle = v.findViewById(com.amaze.filemanager.R.id.title_size);
break;
}
}
mSizeTitle.setTextColor(accentColor);
androidx.appcompat.widget.AppCompatTextView mLocationTitle;
switch(MUID_STATIC) {
// GeneralDialogCreation_42_InvalidViewFocusOperatorMutator
case 42097: {
/**
* Inserted by Kadabra
*/
mLocationTitle = v.findViewById(com.amaze.filemanager.R.id.title_location);
mLocationTitle.requestFocus();
break;
}
// GeneralDialogCreation_43_ViewComponentNotVisibleOperatorMutator
case 43097: {
/**
* Inserted by Kadabra
*/
mLocationTitle = v.findViewById(com.amaze.filemanager.R.id.title_location);
mLocationTitle.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mLocationTitle = v.findViewById(com.amaze.filemanager.R.id.title_location);
break;
}
}
mLocationTitle.setTextColor(accentColor);
androidx.appcompat.widget.AppCompatTextView md5Title;
switch(MUID_STATIC) {
// GeneralDialogCreation_44_InvalidViewFocusOperatorMutator
case 44097: {
/**
* Inserted by Kadabra
*/
md5Title = v.findViewById(com.amaze.filemanager.R.id.title_md5);
md5Title.requestFocus();
break;
}
// GeneralDialogCreation_45_ViewComponentNotVisibleOperatorMutator
case 45097: {
/**
* Inserted by Kadabra
*/
md5Title = v.findViewById(com.amaze.filemanager.R.id.title_md5);
md5Title.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
md5Title = v.findViewById(com.amaze.filemanager.R.id.title_md5);
break;
}
}
md5Title.setTextColor(accentColor);
androidx.appcompat.widget.AppCompatTextView sha256Title;
switch(MUID_STATIC) {
// GeneralDialogCreation_46_InvalidViewFocusOperatorMutator
case 46097: {
/**
* Inserted by Kadabra
*/
sha256Title = v.findViewById(com.amaze.filemanager.R.id.title_sha256);
sha256Title.requestFocus();
break;
}
// GeneralDialogCreation_47_ViewComponentNotVisibleOperatorMutator
case 47097: {
/**
* Inserted by Kadabra
*/
sha256Title = v.findViewById(com.amaze.filemanager.R.id.title_sha256);
sha256Title.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
sha256Title = v.findViewById(com.amaze.filemanager.R.id.title_sha256);
break;
}
}
sha256Title.setTextColor(accentColor);
((androidx.appcompat.widget.AppCompatTextView) (v.findViewById(com.amaze.filemanager.R.id.t5))).setText(name);
((androidx.appcompat.widget.AppCompatTextView) (v.findViewById(com.amaze.filemanager.R.id.t6))).setText(parent);
itemsText.setText(items);
((androidx.appcompat.widget.AppCompatTextView) (v.findViewById(com.amaze.filemanager.R.id.t8))).setText(date);
if (baseFile.isDirectory() && baseFile.isLocal()) {
nomediaCheckBox.setVisibility(android.view.View.VISIBLE);
if (nomediaFile != null) {
nomediaCheckBox.setChecked(nomediaFile.exists());
}
}
android.widget.LinearLayout mNameLinearLayout;
switch(MUID_STATIC) {
// GeneralDialogCreation_48_InvalidViewFocusOperatorMutator
case 48097: {
/**
* Inserted by Kadabra
*/
mNameLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_name);
mNameLinearLayout.requestFocus();
break;
}
// GeneralDialogCreation_49_ViewComponentNotVisibleOperatorMutator
case 49097: {
/**
* Inserted by Kadabra
*/
mNameLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_name);
mNameLinearLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mNameLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_name);
break;
}
}
android.widget.LinearLayout mLocationLinearLayout;
switch(MUID_STATIC) {
// GeneralDialogCreation_50_InvalidViewFocusOperatorMutator
case 50097: {
/**
* Inserted by Kadabra
*/
mLocationLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_location);
mLocationLinearLayout.requestFocus();
break;
}
// GeneralDialogCreation_51_ViewComponentNotVisibleOperatorMutator
case 51097: {
/**
* Inserted by Kadabra
*/
mLocationLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_location);
mLocationLinearLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mLocationLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_location);
break;
}
}
android.widget.LinearLayout mSizeLinearLayout;
switch(MUID_STATIC) {
// GeneralDialogCreation_52_InvalidViewFocusOperatorMutator
case 52097: {
/**
* Inserted by Kadabra
*/
mSizeLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_size);
mSizeLinearLayout.requestFocus();
break;
}
// GeneralDialogCreation_53_ViewComponentNotVisibleOperatorMutator
case 53097: {
/**
* Inserted by Kadabra
*/
mSizeLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_size);
mSizeLinearLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mSizeLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_size);
break;
}
}
android.widget.LinearLayout mDateLinearLayout;
switch(MUID_STATIC) {
// GeneralDialogCreation_54_InvalidViewFocusOperatorMutator
case 54097: {
/**
* Inserted by Kadabra
*/
mDateLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_date);
mDateLinearLayout.requestFocus();
break;
}
// GeneralDialogCreation_55_ViewComponentNotVisibleOperatorMutator
case 55097: {
/**
* Inserted by Kadabra
*/
mDateLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_date);
mDateLinearLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mDateLinearLayout = v.findViewById(com.amaze.filemanager.R.id.properties_dialog_date);
break;
}
}
// setting click listeners for long press
mNameLinearLayout.setOnLongClickListener((android.view.View v1) -> {
com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(c, name);
android.widget.Toast.makeText(c, (c.getString(com.amaze.filemanager.R.string.name) + " ") + c.getString(com.amaze.filemanager.R.string.properties_copied_clipboard), android.widget.Toast.LENGTH_SHORT).show();
return false;
});
mLocationLinearLayout.setOnLongClickListener((android.view.View v12) -> {
com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(c, parent);
android.widget.Toast.makeText(c, (c.getString(com.amaze.filemanager.R.string.location) + " ") + c.getString(com.amaze.filemanager.R.string.properties_copied_clipboard), android.widget.Toast.LENGTH_SHORT).show();
return false;
});
mSizeLinearLayout.setOnLongClickListener((android.view.View v13) -> {
com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(c, items);
android.widget.Toast.makeText(c, (c.getString(com.amaze.filemanager.R.string.size) + " ") + c.getString(com.amaze.filemanager.R.string.properties_copied_clipboard), android.widget.Toast.LENGTH_SHORT).show();
return false;
});
mDateLinearLayout.setOnLongClickListener((android.view.View v14) -> {
com.amaze.filemanager.filesystem.files.FileUtils.copyToClipboard(c, date);
android.widget.Toast.makeText(c, (c.getString(com.amaze.filemanager.R.string.date) + " ") + c.getString(com.amaze.filemanager.R.string.properties_copied_clipboard), android.widget.Toast.LENGTH_SHORT).show();
return false;
});
}
com.amaze.filemanager.asynchronous.asynctasks.CountItemsOrAndSizeTask countItemsOrAndSizeTask;
countItemsOrAndSizeTask = new com.amaze.filemanager.asynchronous.asynctasks.CountItemsOrAndSizeTask(c, itemsText, baseFile, forStorage);
countItemsOrAndSizeTask.executeOnExecutor(executor);
com.amaze.filemanager.asynchronous.asynctasks.TaskKt.fromTask(new com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.CalculateHashTask(baseFile, c, v));
/* Chart creation and data loading */
{
int layoutDirection;
layoutDirection = androidx.core.text.TextUtilsCompat.getLayoutDirectionFromLocale(java.util.Locale.getDefault());
boolean isRightToLeft;
isRightToLeft = layoutDirection == androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL;
boolean isDarkTheme;
isDarkTheme = appTheme.getMaterialDialogTheme() == com.afollestad.materialdialogs.Theme.DARK;
com.github.mikephil.charting.charts.PieChart chart;
switch(MUID_STATIC) {
// GeneralDialogCreation_56_InvalidViewFocusOperatorMutator
case 56097: {
/**
* Inserted by Kadabra
*/
chart = v.findViewById(com.amaze.filemanager.R.id.chart);
chart.requestFocus();
break;
}
// GeneralDialogCreation_57_ViewComponentNotVisibleOperatorMutator
case 57097: {
/**
* Inserted by Kadabra
*/
chart = v.findViewById(com.amaze.filemanager.R.id.chart);
chart.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
chart = v.findViewById(com.amaze.filemanager.R.id.chart);
break;
}
}
chart.setTouchEnabled(false);
chart.setDrawEntryLabels(false);
chart.setDescription(null);
chart.setNoDataText(c.getString(com.amaze.filemanager.R.string.loading));
chart.setRotationAngle(!isRightToLeft ? 0.0F : 180.0F);
chart.setHoleColor(android.graphics.Color.TRANSPARENT);
chart.setCenterTextColor(isDarkTheme ? android.graphics.Color.WHITE : android.graphics.Color.BLACK);
chart.getLegend().setEnabled(true);
chart.getLegend().setForm(com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE);
chart.getLegend().setHorizontalAlignment(com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER);
chart.getLegend().setTypeface(android.graphics.Typeface.create("sans-serif-medium", android.graphics.Typeface.NORMAL));
chart.getLegend().setTextColor(isDarkTheme ? android.graphics.Color.WHITE : android.graphics.Color.BLACK);
chart.animateY(1000);
if (forStorage) {
final java.lang.String[] LEGENDS;
LEGENDS = new java.lang.String[]{ c.getString(com.amaze.filemanager.R.string.used), c.getString(com.amaze.filemanager.R.string.free) };
final int[] COLORS;
COLORS = new int[]{ com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.R.color.piechart_red), com.amaze.filemanager.utils.Utils.getColor(c, com.amaze.filemanager.R.color.piechart_green) };
long totalSpace;
totalSpace = baseFile.getTotal(c);
long freeSpace;
freeSpace = baseFile.getUsableSpace();
long usedSpace;
switch(MUID_STATIC) {
// GeneralDialogCreation_58_BinaryMutator
case 58097: {
usedSpace = totalSpace + freeSpace;
break;
}
default: {
usedSpace = totalSpace - freeSpace;
break;
}
}
java.util.List<com.github.mikephil.charting.data.PieEntry> entries;
entries = new java.util.ArrayList<>();
entries.add(new com.github.mikephil.charting.data.PieEntry(usedSpace, LEGENDS[0]));
entries.add(new com.github.mikephil.charting.data.PieEntry(freeSpace, LEGENDS[1]));
com.github.mikephil.charting.data.PieDataSet set;
set = new com.github.mikephil.charting.data.PieDataSet(entries, null);
set.setColors(COLORS);
set.setXValuePosition(com.github.mikephil.charting.data.PieDataSet.ValuePosition.OUTSIDE_SLICE);
set.setYValuePosition(com.github.mikephil.charting.data.PieDataSet.ValuePosition.OUTSIDE_SLICE);
set.setSliceSpace(5.0F);
set.setAutomaticallyDisableSliceSpacing(true);
set.setValueLinePart2Length(1.05F);
set.setSelectionShift(0.0F);
com.github.mikephil.charting.data.PieData pieData;
pieData = new com.github.mikephil.charting.data.PieData(set);
pieData.setValueFormatter(new com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.SizeFormatter(c));
pieData.setValueTextColor(isDarkTheme ? android.graphics.Color.WHITE : android.graphics.Color.BLACK);
java.lang.String totalSpaceFormatted;
totalSpaceFormatted = android.text.format.Formatter.formatFileSize(c, totalSpace);
chart.setCenterText(new android.text.SpannableString((c.getString(com.amaze.filemanager.R.string.total) + "\n") + totalSpaceFormatted));
chart.setData(pieData);
} else {
com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask loadFolderSpaceDataTask;
loadFolderSpaceDataTask = new com.amaze.filemanager.asynchronous.asynctasks.LoadFolderSpaceDataTask(c, appTheme, chart, baseFile);
loadFolderSpaceDataTask.executeOnExecutor(executor);
}
chart.invalidate();
}
if (((!forStorage) && (permissions != null)) && (mainFragment != null)) {
androidx.appcompat.widget.AppCompatButton appCompatButton;
switch(MUID_STATIC) {
// GeneralDialogCreation_59_InvalidViewFocusOperatorMutator
case 59097: {
/**
* Inserted by Kadabra
*/
appCompatButton = v.findViewById(com.amaze.filemanager.R.id.permissionsButton);
appCompatButton.requestFocus();
break;
}
// GeneralDialogCreation_60_ViewComponentNotVisibleOperatorMutator
case 60097: {
/**
* Inserted by Kadabra
*/
appCompatButton = v.findViewById(com.amaze.filemanager.R.id.permissionsButton);
appCompatButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
appCompatButton = v.findViewById(com.amaze.filemanager.R.id.permissionsButton);
break;
}
}
appCompatButton.setAllCaps(true);
final android.view.View permissionsTable;
switch(MUID_STATIC) {
// GeneralDialogCreation_61_InvalidViewFocusOperatorMutator
case 61097: {
/**
* Inserted by Kadabra
*/
permissionsTable = v.findViewById(com.amaze.filemanager.R.id.permtable);
permissionsTable.requestFocus();
break;
}
// GeneralDialogCreation_62_ViewComponentNotVisibleOperatorMutator
case 62097: {
/**
* Inserted by Kadabra
*/
permissionsTable = v.findViewById(com.amaze.filemanager.R.id.permtable);
permissionsTable.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
permissionsTable = v.findViewById(com.amaze.filemanager.R.id.permtable);
break;
}
}
final android.view.View button;
switch(MUID_STATIC) {
// GeneralDialogCreation_63_InvalidViewFocusOperatorMutator
case 63097: {
/**
* Inserted by Kadabra
*/
button = v.findViewById(com.amaze.filemanager.R.id.set);
button.requestFocus();
break;
}
// GeneralDialogCreation_64_ViewComponentNotVisibleOperatorMutator
case 64097: {
/**
* Inserted by Kadabra
*/
button = v.findViewById(com.amaze.filemanager.R.id.set);
button.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
button = v.findViewById(com.amaze.filemanager.R.id.set);
break;
}
}
if (isRoot && (permissions.length() > 6)) {
appCompatButton.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// GeneralDialogCreation_65_BuggyGUIListenerOperatorMutator
case 65097: {
appCompatButton.setOnClickListener(null);
break;
}
default: {
appCompatButton.setOnClickListener((android.view.View v15) -> {
if (permissionsTable.getVisibility() == android.view.View.GONE) {
permissionsTable.setVisibility(android.view.View.VISIBLE);
button.setVisibility(android.view.View.VISIBLE);
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.setPermissionsDialog(permissionsTable, button, baseFile, permissions, c, mainFragment);
} else {
button.setVisibility(android.view.View.GONE);
permissionsTable.setVisibility(android.view.View.GONE);
}
});
break;
}
}
}
}
builder.customView(v, true);
builder.positiveText(themedActivity.getString(com.amaze.filemanager.R.string.ok));
builder.positiveColor(accentColor);
builder.dismissListener((android.content.DialogInterface dialog) -> executor.shutdown());
builder.onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
if (baseFile.isDirectory() && (nomediaFile != null)) {
if (nomediaCheckBox.isChecked()) {
// checkbox is checked, create .nomedia
try {
if (!nomediaFile.createNewFile()) {
// failed operation
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.LOG.warn(".nomedia file creation in {} failed", baseFile.getPath());
}
} catch (java.io.IOException e) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.LOG.warn("failed to create file at path {}", baseFile.getPath(), e);
}
} else // checkbox is unchecked, delete .nomedia
if (!nomediaFile.delete()) {
// failed operation
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.LOG.warn(".nomedia file deletion in {} failed", baseFile.getPath());
}
}
});
com.afollestad.materialdialogs.MaterialDialog materialDialog;
materialDialog = builder.build();
materialDialog.show();
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setEnabled(false);
/* View bottomSheet = c.findViewById(R.id.design_bottom_sheet);
BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_DRAGGING);
 */
}


public static class SizeFormatter implements com.github.mikephil.charting.formatter.IValueFormatter {
private android.content.Context context;

public SizeFormatter(android.content.Context c) {
context = c;
}


@java.lang.Override
public java.lang.String getFormattedValue(float value, com.github.mikephil.charting.data.Entry entry, int dataSetIndex, com.github.mikephil.charting.utils.ViewPortHandler viewPortHandler) {
java.lang.String prefix;
prefix = ((entry.getData() != null) && (entry.getData() instanceof java.lang.String)) ? ((java.lang.String) (entry.getData())) : "";
return prefix + android.text.format.Formatter.formatFileSize(context, ((long) (value)));
}

}

public static void showCloudDialog(final com.amaze.filemanager.ui.activities.MainActivity mainActivity, com.amaze.filemanager.ui.theme.AppTheme appTheme, final com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode) {
int accentColor;
accentColor = mainActivity.getAccent();
final com.afollestad.materialdialogs.MaterialDialog.Builder builder;
builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(mainActivity);
switch (openMode) {
case DROPBOX :
builder.title(mainActivity.getString(com.amaze.filemanager.R.string.cloud_dropbox));
break;
case BOX :
builder.title(mainActivity.getString(com.amaze.filemanager.R.string.cloud_box));
break;
case GDRIVE :
builder.title(mainActivity.getString(com.amaze.filemanager.R.string.cloud_drive));
break;
case ONEDRIVE :
builder.title(mainActivity.getString(com.amaze.filemanager.R.string.cloud_onedrive));
break;
}
builder.theme(appTheme.getMaterialDialogTheme());
builder.content(mainActivity.getString(com.amaze.filemanager.R.string.cloud_remove));
builder.positiveText(mainActivity.getString(com.amaze.filemanager.R.string.yes));
builder.positiveColor(accentColor);
builder.negativeText(mainActivity.getString(com.amaze.filemanager.R.string.no));
builder.negativeColor(accentColor);
builder.onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> mainActivity.deleteConnection(openMode));
builder.onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> dialog.cancel());
builder.show();
}


public static void showDecryptDialog(android.content.Context c, final com.amaze.filemanager.ui.activities.MainActivity main, final android.content.Intent intent, com.amaze.filemanager.ui.theme.AppTheme appTheme, final java.lang.String password, final com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.DecryptButtonCallbackInterface decryptButtonCallbackInterface) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showPasswordDialog(c, main, appTheme, com.amaze.filemanager.R.string.crypt_decrypt, com.amaze.filemanager.R.string.authenticate_password, (com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
androidx.appcompat.widget.AppCompatEditText editText;
switch(MUID_STATIC) {
// GeneralDialogCreation_66_InvalidViewFocusOperatorMutator
case 66097: {
/**
* Inserted by Kadabra
*/
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
editText.requestFocus();
break;
}
// GeneralDialogCreation_67_ViewComponentNotVisibleOperatorMutator
case 67097: {
/**
* Inserted by Kadabra
*/
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
editText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
editText = dialog.getView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
break;
}
}
if (editText.getText().toString().equals(password))
decryptButtonCallbackInterface.confirm(intent);
else
decryptButtonCallbackInterface.failed();

dialog.dismiss();
}, null);
}


public static void showPasswordDialog(@androidx.annotation.NonNull
android.content.Context c, @androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.MainActivity main, @androidx.annotation.NonNull
com.amaze.filemanager.ui.theme.AppTheme appTheme, @androidx.annotation.StringRes
int titleText, @androidx.annotation.StringRes
int promptText, @androidx.annotation.NonNull
com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback positiveCallback, @androidx.annotation.Nullable
com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback negativeCallback) {
int accentColor;
accentColor = main.getAccent();
com.afollestad.materialdialogs.MaterialDialog.Builder builder;
builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(c);
android.view.View dialogLayout;
dialogLayout = android.view.View.inflate(main, com.amaze.filemanager.R.layout.dialog_singleedittext, null);
com.amaze.filemanager.ui.views.WarnableTextInputLayout wilTextfield;
switch(MUID_STATIC) {
// GeneralDialogCreation_68_InvalidViewFocusOperatorMutator
case 68097: {
/**
* Inserted by Kadabra
*/
wilTextfield = dialogLayout.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
wilTextfield.requestFocus();
break;
}
// GeneralDialogCreation_69_ViewComponentNotVisibleOperatorMutator
case 69097: {
/**
* Inserted by Kadabra
*/
wilTextfield = dialogLayout.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
wilTextfield.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
wilTextfield = dialogLayout.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
break;
}
}
androidx.appcompat.widget.AppCompatEditText textfield;
switch(MUID_STATIC) {
// GeneralDialogCreation_70_InvalidViewFocusOperatorMutator
case 70097: {
/**
* Inserted by Kadabra
*/
textfield = dialogLayout.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
textfield.requestFocus();
break;
}
// GeneralDialogCreation_71_ViewComponentNotVisibleOperatorMutator
case 71097: {
/**
* Inserted by Kadabra
*/
textfield = dialogLayout.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
textfield.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textfield = dialogLayout.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
break;
}
}
textfield.setHint(promptText);
textfield.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
dialogLayout.post(() -> com.amaze.filemanager.ui.ExtensionsKt.openKeyboard(textfield, main.getApplicationContext()));
builder.customView(dialogLayout, false).theme(appTheme.getMaterialDialogTheme()).autoDismiss(false).canceledOnTouchOutside(false).title(titleText).positiveText(com.amaze.filemanager.R.string.ok).positiveColor(accentColor).onPositive(positiveCallback).negativeText(com.amaze.filemanager.R.string.cancel).negativeColor(accentColor);
if (negativeCallback != null)
builder.onNegative(negativeCallback);
else
builder.onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> dialog.cancel());

com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = builder.show();
new com.amaze.filemanager.ui.views.WarnableTextInputValidator(com.amaze.filemanager.application.AppConfig.getInstance().getMainActivityContext(), textfield, wilTextfield, dialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE), (java.lang.String text) -> {
if (text.length() < 1) {
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.field_empty);
}
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState();
});
}


public static void showSMBHelpDialog(android.content.Context m, int accentColor) {
com.afollestad.materialdialogs.MaterialDialog.Builder b;
b = new com.afollestad.materialdialogs.MaterialDialog.Builder(m);
b.content(m.getText(com.amaze.filemanager.R.string.smb_instructions));
b.positiveText(com.amaze.filemanager.R.string.doit);
b.positiveColor(accentColor);
b.build().show();
}


public static void showPackageDialog(final java.io.File f, final com.amaze.filemanager.ui.activities.MainActivity m) {
int accentColor;
accentColor = m.getAccent();
com.afollestad.materialdialogs.MaterialDialog.Builder mat;
mat = new com.afollestad.materialdialogs.MaterialDialog.Builder(m);
mat.title(com.amaze.filemanager.R.string.package_installer).content(com.amaze.filemanager.R.string.package_installer_text).positiveText(com.amaze.filemanager.R.string.install).negativeText(com.amaze.filemanager.R.string.view).neutralText(com.amaze.filemanager.R.string.cancel).positiveColor(accentColor).negativeColor(accentColor).neutralColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> com.amaze.filemanager.filesystem.files.FileUtils.installApk(f, m)).onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> m.openCompressed(f.getPath())).theme(m.getAppTheme().getMaterialDialogTheme()).build().show();
}


public static com.afollestad.materialdialogs.MaterialDialog showOpenFileDeeplinkDialog(final com.amaze.filemanager.filesystem.HybridFile file, final com.amaze.filemanager.ui.activities.MainActivity m, final java.lang.String content, java.lang.Runnable openCallback) {
int accentColor;
accentColor = m.getAccent();
return new com.afollestad.materialdialogs.MaterialDialog.Builder(m).title(com.amaze.filemanager.R.string.confirmation).content(content).positiveText(com.amaze.filemanager.R.string.open).negativeText(com.amaze.filemanager.R.string.cancel).positiveColor(accentColor).negativeColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> openCallback.run()).onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> dialog.dismiss()).theme(m.getAppTheme().getMaterialDialogTheme()).build();
}


public static void showArchiveDialog(final java.io.File f, final com.amaze.filemanager.ui.activities.MainActivity m) {
int accentColor;
accentColor = m.getAccent();
com.afollestad.materialdialogs.MaterialDialog.Builder mat;
mat = new com.afollestad.materialdialogs.MaterialDialog.Builder(m);
mat.title(com.amaze.filemanager.R.string.archive).content(com.amaze.filemanager.R.string.archive_text).positiveText(com.amaze.filemanager.R.string.extract).negativeText(com.amaze.filemanager.R.string.view).neutralText(com.amaze.filemanager.R.string.cancel).positiveColor(accentColor).negativeColor(accentColor).neutralColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> m.mainActivityHelper.extractFile(f)).onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> m.openCompressed(android.net.Uri.fromFile(f).toString()));
if (m.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK) || m.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK))
mat.theme(com.afollestad.materialdialogs.Theme.DARK);

com.afollestad.materialdialogs.MaterialDialog b;
b = mat.build();
if (!com.amaze.filemanager.filesystem.compressed.CompressedHelper.isFileExtractable(f.getPath())) {
b.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setEnabled(false);
}
b.show();
}


public static void showCompressDialog(@androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.MainActivity mainActivity, final com.amaze.filemanager.filesystem.HybridFileParcelable baseFile, final java.lang.String current) {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> baseFiles;
baseFiles = new java.util.ArrayList<>();
baseFiles.add(baseFile);
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showCompressDialog(mainActivity, baseFiles, current);
}


public static void showCompressDialog(@androidx.annotation.NonNull
final com.amaze.filemanager.ui.activities.MainActivity mainActivity, final java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> baseFiles, final java.lang.String current) {
int accentColor;
accentColor = mainActivity.getAccent();
com.afollestad.materialdialogs.MaterialDialog.Builder a;
a = new com.afollestad.materialdialogs.MaterialDialog.Builder(mainActivity);
android.view.View dialogView;
dialogView = mainActivity.getLayoutInflater().inflate(com.amaze.filemanager.R.layout.dialog_singleedittext, null);
androidx.appcompat.widget.AppCompatEditText etFilename;
switch(MUID_STATIC) {
// GeneralDialogCreation_72_InvalidViewFocusOperatorMutator
case 72097: {
/**
* Inserted by Kadabra
*/
etFilename = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
etFilename.requestFocus();
break;
}
// GeneralDialogCreation_73_ViewComponentNotVisibleOperatorMutator
case 73097: {
/**
* Inserted by Kadabra
*/
etFilename = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
etFilename.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
etFilename = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_input);
break;
}
}
etFilename.setHint(com.amaze.filemanager.R.string.enterzipname);
etFilename.setText(".zip")// TODO: Put the file/folder name here
;// TODO: Put the file/folder name here

etFilename.setInputType(android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
etFilename.setSingleLine();
com.amaze.filemanager.ui.views.WarnableTextInputLayout tilFilename;
switch(MUID_STATIC) {
// GeneralDialogCreation_74_InvalidViewFocusOperatorMutator
case 74097: {
/**
* Inserted by Kadabra
*/
tilFilename = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
tilFilename.requestFocus();
break;
}
// GeneralDialogCreation_75_ViewComponentNotVisibleOperatorMutator
case 75097: {
/**
* Inserted by Kadabra
*/
tilFilename = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
tilFilename.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
tilFilename = dialogView.findViewById(com.amaze.filemanager.R.id.singleedittext_warnabletextinputlayout);
break;
}
}
dialogView.post(() -> com.amaze.filemanager.ui.ExtensionsKt.openKeyboard(etFilename, mainActivity.getApplicationContext()));
a.customView(dialogView, false).widgetColor(accentColor).theme(mainActivity.getAppTheme().getMaterialDialogTheme()).title(mainActivity.getResources().getString(com.amaze.filemanager.R.string.enterzipname)).positiveText(com.amaze.filemanager.R.string.create).positiveColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog materialDialog,com.afollestad.materialdialogs.DialogAction dialogAction) -> {
java.lang.String name;
name = (current + "/") + etFilename.getText().toString();
mainActivity.mainActivityHelper.compressFiles(new java.io.File(name), baseFiles);
}).negativeText(mainActivity.getResources().getString(com.amaze.filemanager.R.string.cancel)).negativeColor(accentColor);
final com.afollestad.materialdialogs.MaterialDialog materialDialog;
materialDialog = a.build();
new com.amaze.filemanager.ui.views.WarnableTextInputValidator(a.getContext(), etFilename, tilFilename, materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE), (java.lang.String text) -> {
boolean isValidFilename;
isValidFilename = com.amaze.filemanager.filesystem.FileProperties.isValidFilename(text);
if ((isValidFilename && (text.length() > 0)) && (!text.toLowerCase().endsWith(".zip"))) {
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_WARNING, com.amaze.filemanager.R.string.compress_file_suggest_zip_extension);
} else if (!isValidFilename) {
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.invalid_name);
} else if (text.length() < 1) {
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.field_empty);
}
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState();
});
materialDialog.show();
// place cursor at the starting of edit text by posting a runnable to edit text
// this is done because in case android has not populated the edit text layouts yet, it'll
// reset calls to selection if not posted in message queue
etFilename.post(() -> etFilename.setSelection(0));
}


public static void showSortDialog(final com.amaze.filemanager.ui.fragments.MainFragment m, com.amaze.filemanager.ui.theme.AppTheme appTheme, final android.content.SharedPreferences sharedPref) {
final java.lang.String path;
path = m.getCurrentPath();
int accentColor;
accentColor = m.getMainActivity().getAccent();
java.lang.String[] sort;
sort = m.getResources().getStringArray(com.amaze.filemanager.R.array.sortby);
com.amaze.filemanager.filesystem.files.sort.SortType current;
current = com.amaze.filemanager.database.SortHandler.getSortType(m.getContext(), path);
com.afollestad.materialdialogs.MaterialDialog.Builder a;
a = new com.afollestad.materialdialogs.MaterialDialog.Builder(m.getActivity());
a.theme(appTheme.getMaterialDialogTheme());
a.items(sort).itemsCallbackSingleChoice(current.getSortBy().getIndex(), (com.afollestad.materialdialogs.MaterialDialog dialog,android.view.View view,int which,java.lang.CharSequence text) -> true);
final java.util.Set<java.lang.String> sortbyOnlyThis;
sortbyOnlyThis = sharedPref.getStringSet(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SORTBY_ONLY_THIS, java.util.Collections.emptySet());
final java.util.Set<java.lang.String> onlyThisFloders;
onlyThisFloders = new java.util.HashSet<>(sortbyOnlyThis);
boolean onlyThis;
onlyThis = onlyThisFloders.contains(path);
a.checkBoxPrompt(m.getResources().getString(com.amaze.filemanager.R.string.sort_only_this), onlyThis, (android.widget.CompoundButton buttonView,boolean isChecked) -> {
if (isChecked) {
if (!onlyThisFloders.contains(path)) {
onlyThisFloders.add(path);
}
} else if (onlyThisFloders.contains(path)) {
onlyThisFloders.remove(path);
}
});
a.negativeText(com.amaze.filemanager.R.string.ascending).positiveColor(accentColor);
a.positiveText(com.amaze.filemanager.R.string.descending).negativeColor(accentColor);
a.onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.onSortTypeSelected(m, sharedPref, onlyThisFloders, dialog, com.amaze.filemanager.filesystem.files.sort.SortOrder.ASC);
});
a.onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.onSortTypeSelected(m, sharedPref, onlyThisFloders, dialog, com.amaze.filemanager.filesystem.files.sort.SortOrder.DESC);
});
a.title(com.amaze.filemanager.R.string.sort_by);
a.build().show();
}


private static void onSortTypeSelected(com.amaze.filemanager.ui.fragments.MainFragment m, android.content.SharedPreferences sharedPref, java.util.Set<java.lang.String> onlyThisFloders, com.afollestad.materialdialogs.MaterialDialog dialog, com.amaze.filemanager.filesystem.files.sort.SortOrder sortOrder) {
final com.amaze.filemanager.filesystem.files.sort.SortType sortType;
sortType = new com.amaze.filemanager.filesystem.files.sort.SortType(com.amaze.filemanager.filesystem.files.sort.SortBy.getDirectorySortBy(dialog.getSelectedIndex()), sortOrder);
com.amaze.filemanager.database.SortHandler sortHandler;
sortHandler = com.amaze.filemanager.database.SortHandler.getInstance();
if (onlyThisFloders.contains(m.getCurrentPath())) {
com.amaze.filemanager.database.models.explorer.Sort oldSort;
oldSort = sortHandler.findEntry(m.getCurrentPath());
if (oldSort == null) {
sortHandler.addEntry(m.getCurrentPath(), sortType);
} else {
sortHandler.updateEntry(oldSort, m.getCurrentPath(), sortType);
}
} else {
sortHandler.clear(m.getCurrentPath());
sharedPref.edit().putString("sortby", java.lang.String.valueOf(sortType.toDirectorySortInt())).apply();
}
sharedPref.edit().putStringSet(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SORTBY_ONLY_THIS, onlyThisFloders).apply();
m.updateList(false);
dialog.dismiss();
}


public static void setPermissionsDialog(final android.view.View v, android.view.View but, final com.amaze.filemanager.filesystem.HybridFile file, final java.lang.String f, final android.content.Context context, final com.amaze.filemanager.ui.fragments.MainFragment mainFrag) {
final androidx.appcompat.widget.AppCompatCheckBox readown;
switch(MUID_STATIC) {
// GeneralDialogCreation_76_InvalidViewFocusOperatorMutator
case 76097: {
/**
* Inserted by Kadabra
*/
readown = v.findViewById(com.amaze.filemanager.R.id.creadown);
readown.requestFocus();
break;
}
// GeneralDialogCreation_77_ViewComponentNotVisibleOperatorMutator
case 77097: {
/**
* Inserted by Kadabra
*/
readown = v.findViewById(com.amaze.filemanager.R.id.creadown);
readown.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
readown = v.findViewById(com.amaze.filemanager.R.id.creadown);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox readgroup;
switch(MUID_STATIC) {
// GeneralDialogCreation_78_InvalidViewFocusOperatorMutator
case 78097: {
/**
* Inserted by Kadabra
*/
readgroup = v.findViewById(com.amaze.filemanager.R.id.creadgroup);
readgroup.requestFocus();
break;
}
// GeneralDialogCreation_79_ViewComponentNotVisibleOperatorMutator
case 79097: {
/**
* Inserted by Kadabra
*/
readgroup = v.findViewById(com.amaze.filemanager.R.id.creadgroup);
readgroup.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
readgroup = v.findViewById(com.amaze.filemanager.R.id.creadgroup);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox readother;
switch(MUID_STATIC) {
// GeneralDialogCreation_80_InvalidViewFocusOperatorMutator
case 80097: {
/**
* Inserted by Kadabra
*/
readother = v.findViewById(com.amaze.filemanager.R.id.creadother);
readother.requestFocus();
break;
}
// GeneralDialogCreation_81_ViewComponentNotVisibleOperatorMutator
case 81097: {
/**
* Inserted by Kadabra
*/
readother = v.findViewById(com.amaze.filemanager.R.id.creadother);
readother.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
readother = v.findViewById(com.amaze.filemanager.R.id.creadother);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox writeown;
switch(MUID_STATIC) {
// GeneralDialogCreation_82_InvalidViewFocusOperatorMutator
case 82097: {
/**
* Inserted by Kadabra
*/
writeown = v.findViewById(com.amaze.filemanager.R.id.cwriteown);
writeown.requestFocus();
break;
}
// GeneralDialogCreation_83_ViewComponentNotVisibleOperatorMutator
case 83097: {
/**
* Inserted by Kadabra
*/
writeown = v.findViewById(com.amaze.filemanager.R.id.cwriteown);
writeown.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
writeown = v.findViewById(com.amaze.filemanager.R.id.cwriteown);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox writegroup;
switch(MUID_STATIC) {
// GeneralDialogCreation_84_InvalidViewFocusOperatorMutator
case 84097: {
/**
* Inserted by Kadabra
*/
writegroup = v.findViewById(com.amaze.filemanager.R.id.cwritegroup);
writegroup.requestFocus();
break;
}
// GeneralDialogCreation_85_ViewComponentNotVisibleOperatorMutator
case 85097: {
/**
* Inserted by Kadabra
*/
writegroup = v.findViewById(com.amaze.filemanager.R.id.cwritegroup);
writegroup.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
writegroup = v.findViewById(com.amaze.filemanager.R.id.cwritegroup);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox writeother;
switch(MUID_STATIC) {
// GeneralDialogCreation_86_InvalidViewFocusOperatorMutator
case 86097: {
/**
* Inserted by Kadabra
*/
writeother = v.findViewById(com.amaze.filemanager.R.id.cwriteother);
writeother.requestFocus();
break;
}
// GeneralDialogCreation_87_ViewComponentNotVisibleOperatorMutator
case 87097: {
/**
* Inserted by Kadabra
*/
writeother = v.findViewById(com.amaze.filemanager.R.id.cwriteother);
writeother.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
writeother = v.findViewById(com.amaze.filemanager.R.id.cwriteother);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox exeown;
switch(MUID_STATIC) {
// GeneralDialogCreation_88_InvalidViewFocusOperatorMutator
case 88097: {
/**
* Inserted by Kadabra
*/
exeown = v.findViewById(com.amaze.filemanager.R.id.cexeown);
exeown.requestFocus();
break;
}
// GeneralDialogCreation_89_ViewComponentNotVisibleOperatorMutator
case 89097: {
/**
* Inserted by Kadabra
*/
exeown = v.findViewById(com.amaze.filemanager.R.id.cexeown);
exeown.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
exeown = v.findViewById(com.amaze.filemanager.R.id.cexeown);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox exegroup;
switch(MUID_STATIC) {
// GeneralDialogCreation_90_InvalidViewFocusOperatorMutator
case 90097: {
/**
* Inserted by Kadabra
*/
exegroup = v.findViewById(com.amaze.filemanager.R.id.cexegroup);
exegroup.requestFocus();
break;
}
// GeneralDialogCreation_91_ViewComponentNotVisibleOperatorMutator
case 91097: {
/**
* Inserted by Kadabra
*/
exegroup = v.findViewById(com.amaze.filemanager.R.id.cexegroup);
exegroup.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
exegroup = v.findViewById(com.amaze.filemanager.R.id.cexegroup);
break;
}
}
final androidx.appcompat.widget.AppCompatCheckBox exeother;
switch(MUID_STATIC) {
// GeneralDialogCreation_92_InvalidViewFocusOperatorMutator
case 92097: {
/**
* Inserted by Kadabra
*/
exeother = v.findViewById(com.amaze.filemanager.R.id.cexeother);
exeother.requestFocus();
break;
}
// GeneralDialogCreation_93_ViewComponentNotVisibleOperatorMutator
case 93097: {
/**
* Inserted by Kadabra
*/
exeother = v.findViewById(com.amaze.filemanager.R.id.cexeother);
exeother.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
exeother = v.findViewById(com.amaze.filemanager.R.id.cexeother);
break;
}
}
java.lang.String perm;
perm = f;
if (perm.length() < 6) {
v.setVisibility(android.view.View.GONE);
but.setVisibility(android.view.View.GONE);
android.widget.Toast.makeText(context, com.amaze.filemanager.R.string.not_allowed, android.widget.Toast.LENGTH_SHORT).show();
return;
}
java.util.ArrayList<java.lang.Boolean[]> arrayList;
arrayList = com.amaze.filemanager.filesystem.files.FileUtils.parse(perm);
java.lang.Boolean[] read;
read = arrayList.get(0);
java.lang.Boolean[] write;
write = arrayList.get(1);
final java.lang.Boolean[] exe;
exe = arrayList.get(2);
readown.setChecked(read[0]);
readgroup.setChecked(read[1]);
readother.setChecked(read[2]);
writeown.setChecked(write[0]);
writegroup.setChecked(write[1]);
writeother.setChecked(write[2]);
exeown.setChecked(exe[0]);
exegroup.setChecked(exe[1]);
exeother.setChecked(exe[2]);
switch(MUID_STATIC) {
// GeneralDialogCreation_94_BuggyGUIListenerOperatorMutator
case 94097: {
but.setOnClickListener(null);
break;
}
default: {
but.setOnClickListener((android.view.View v1) -> {
int perms;
perms = com.amaze.filemanager.filesystem.RootHelper.permissionsToOctalString(readown.isChecked(), writeown.isChecked(), exeown.isChecked(), readgroup.isChecked(), writegroup.isChecked(), exegroup.isChecked(), readother.isChecked(), writeother.isChecked(), exeother.isChecked());
try {
com.amaze.filemanager.filesystem.root.ChangeFilePermissionsCommand.INSTANCE.changeFilePermissions(file.getPath(), perms, file.isDirectory(context), (java.lang.Boolean isSuccess) -> {
if (isSuccess) {
android.widget.Toast.makeText(context, mainFrag.getString(com.amaze.filemanager.R.string.done), android.widget.Toast.LENGTH_LONG).show();
} else {
android.widget.Toast.makeText(context, mainFrag.getString(com.amaze.filemanager.R.string.operation_unsuccesful), android.widget.Toast.LENGTH_LONG).show();
}
return null;
});
} catch (com.amaze.filemanager.fileoperations.exceptions.ShellNotRunningException e) {
android.widget.Toast.makeText(context, mainFrag.getString(com.amaze.filemanager.R.string.root_failure), android.widget.Toast.LENGTH_LONG).show();
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.LOG.warn("failed to set permission dialog", e);
}
});
break;
}
}
}


public static void showChangePathsDialog(final com.amaze.filemanager.ui.activities.MainActivity mainActivity, final android.content.SharedPreferences prefs) {
final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
mainFragment = mainActivity.getCurrentMainFragment();
java.util.Objects.requireNonNull(mainActivity);
final com.afollestad.materialdialogs.MaterialDialog.Builder a;
a = new com.afollestad.materialdialogs.MaterialDialog.Builder(mainActivity);
a.input(null, mainFragment.getCurrentPath(), false, (com.afollestad.materialdialogs.MaterialDialog dialog,java.lang.CharSequence charSequence) -> {
boolean isAccessible;
isAccessible = com.amaze.filemanager.filesystem.files.FileUtils.isPathAccessible(charSequence.toString(), prefs);
dialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setEnabled(isAccessible);
});
a.alwaysCallInputCallback();
int accentColor;
accentColor = mainActivity.getAccent();
a.widgetColor(accentColor);
a.theme(mainActivity.getAppTheme().getMaterialDialogTheme());
a.title(com.amaze.filemanager.R.string.enterpath);
a.positiveText(com.amaze.filemanager.R.string.go);
a.positiveColor(accentColor);
a.negativeText(com.amaze.filemanager.R.string.cancel);
a.negativeColor(accentColor);
a.onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
mainFragment.loadlist(dialog.getInputEditText().getText().toString(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, false);
});
a.show();
}


public static com.afollestad.materialdialogs.MaterialDialog showOtgSafExplanationDialog(com.amaze.filemanager.ui.activities.superclasses.ThemedActivity themedActivity) {
return com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showBasicDialog(themedActivity, com.amaze.filemanager.R.string.saf_otg_explanation, com.amaze.filemanager.R.string.otg_access, com.amaze.filemanager.R.string.ok, com.amaze.filemanager.R.string.cancel);
}


public static void showSignInWithGoogleDialog(@androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity mainActivity) {
android.view.View customView;
customView = com.amaze.filemanager.databinding.DialogSigninWithGoogleBinding.inflate(android.view.LayoutInflater.from(mainActivity)).getRoot();
int accentColor;
accentColor = mainActivity.getAccent();
com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(mainActivity).customView(customView, false).title(com.amaze.filemanager.R.string.signin_with_google_title).negativeText(android.R.string.cancel).negativeColor(accentColor).onNegative((com.afollestad.materialdialogs.MaterialDialog dlg,com.afollestad.materialdialogs.DialogAction which) -> dlg.dismiss()).build();
switch(MUID_STATIC) {
// GeneralDialogCreation_95_BuggyGUIListenerOperatorMutator
case 95097: {
customView.findViewById(com.amaze.filemanager.R.id.signin_with_google).setOnClickListener(null);
break;
}
default: {
customView.findViewById(com.amaze.filemanager.R.id.signin_with_google).setOnClickListener((android.view.View v) -> {
mainActivity.addConnection(com.amaze.filemanager.fileoperations.filesystem.OpenMode.GDRIVE);
dialog.dismiss();
});
break;
}
}
dialog.show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
