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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.DialogFragment;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.ui.activities.superclasses.BasicActivity;
import android.view.View;
import com.amaze.filemanager.utils.SimpleTextWatcher;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.R;
import com.google.android.material.textfield.TextInputLayout;
import com.amaze.filemanager.utils.DataUtils;
import com.afollestad.materialdialogs.DialogAction;
import android.app.Dialog;
import android.text.Editable;
import com.afollestad.materialdialogs.MaterialDialog;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RenameBookmark extends android.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    private java.lang.String title;

    private java.lang.String path;

    private com.amaze.filemanager.ui.dialogs.RenameBookmark.BookmarkCallback bookmarkCallback;

    private final com.amaze.filemanager.utils.DataUtils dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();

    public static com.amaze.filemanager.ui.dialogs.RenameBookmark getInstance(java.lang.String name, java.lang.String path, int accentColor) {
        com.amaze.filemanager.ui.dialogs.RenameBookmark renameBookmark;
        renameBookmark = new com.amaze.filemanager.ui.dialogs.RenameBookmark();
        android.os.Bundle bundle;
        bundle = new android.os.Bundle();
        bundle.putString("title", name);
        bundle.putString("path", path);
        bundle.putInt("accentColor", accentColor);
        renameBookmark.setArguments(bundle);
        return renameBookmark;
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // RenameBookmark_0_LengthyGUICreationOperatorMutator
            case 96: {
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
}


@java.lang.Override
public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
    android.content.Context c;
    c = getActivity();
    if (getActivity() instanceof com.amaze.filemanager.ui.dialogs.RenameBookmark.BookmarkCallback)
        bookmarkCallback = ((com.amaze.filemanager.ui.dialogs.RenameBookmark.BookmarkCallback) (getActivity()));

    title = getArguments().getString("title");
    path = getArguments().getString("path");
    int accentColor;
    accentColor = getArguments().getInt("accentColor");
    android.content.SharedPreferences sp;
    sp = androidx.preference.PreferenceManager.getDefaultSharedPreferences(c);
    if (dataUtils.containsBooks(new java.lang.String[]{ title, path }) != (-1)) {
        final com.afollestad.materialdialogs.MaterialDialog materialDialog;
        java.lang.String pa;
        pa = path;
        com.afollestad.materialdialogs.MaterialDialog.Builder builder;
        builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(c);
        builder.title(com.amaze.filemanager.R.string.rename_bookmark);
        builder.positiveColor(accentColor);
        builder.negativeColor(accentColor);
        builder.neutralColor(accentColor);
        builder.positiveText(com.amaze.filemanager.R.string.save);
        builder.neutralText(com.amaze.filemanager.R.string.cancel);
        builder.negativeText(com.amaze.filemanager.R.string.delete);
        builder.theme(((com.amaze.filemanager.ui.activities.superclasses.BasicActivity) (getActivity())).getAppTheme().getMaterialDialogTheme());
        builder.autoDismiss(false);
        android.view.View v2;
        v2 = getActivity().getLayoutInflater().inflate(com.amaze.filemanager.R.layout.rename, null);
        builder.customView(v2, true);
        final com.google.android.material.textfield.TextInputLayout t1;
        switch(MUID_STATIC) {
            // RenameBookmark_1_InvalidViewFocusOperatorMutator
            case 1096: {
                /**
                * Inserted by Kadabra
                */
                t1 = v2.findViewById(com.amaze.filemanager.R.id.t1);
                t1.requestFocus();
                break;
            }
            // RenameBookmark_2_ViewComponentNotVisibleOperatorMutator
            case 2096: {
                /**
                * Inserted by Kadabra
                */
                t1 = v2.findViewById(com.amaze.filemanager.R.id.t1);
                t1.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            t1 = v2.findViewById(com.amaze.filemanager.R.id.t1);
            break;
        }
    }
    final com.google.android.material.textfield.TextInputLayout t2;
    switch(MUID_STATIC) {
        // RenameBookmark_3_InvalidViewFocusOperatorMutator
        case 3096: {
            /**
            * Inserted by Kadabra
            */
            t2 = v2.findViewById(com.amaze.filemanager.R.id.t2);
            t2.requestFocus();
            break;
        }
        // RenameBookmark_4_ViewComponentNotVisibleOperatorMutator
        case 4096: {
            /**
            * Inserted by Kadabra
            */
            t2 = v2.findViewById(com.amaze.filemanager.R.id.t2);
            t2.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        t2 = v2.findViewById(com.amaze.filemanager.R.id.t2);
        break;
    }
}
final androidx.appcompat.widget.AppCompatEditText conName;
switch(MUID_STATIC) {
    // RenameBookmark_5_InvalidViewFocusOperatorMutator
    case 5096: {
        /**
        * Inserted by Kadabra
        */
        conName = v2.findViewById(com.amaze.filemanager.R.id.editText4);
        conName.requestFocus();
        break;
    }
    // RenameBookmark_6_ViewComponentNotVisibleOperatorMutator
    case 6096: {
        /**
        * Inserted by Kadabra
        */
        conName = v2.findViewById(com.amaze.filemanager.R.id.editText4);
        conName.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    conName = v2.findViewById(com.amaze.filemanager.R.id.editText4);
    break;
}
}
conName.setText(title);
final java.lang.String s1;
s1 = getString(com.amaze.filemanager.R.string.cant_be_empty, c.getString(com.amaze.filemanager.R.string.name));
final java.lang.String s2;
s2 = getString(com.amaze.filemanager.R.string.cant_be_empty, c.getString(com.amaze.filemanager.R.string.path));
conName.addTextChangedListener(new com.amaze.filemanager.utils.SimpleTextWatcher() {
@java.lang.Override
public void afterTextChanged(android.text.Editable s) {
    if (conName.getText().toString().length() == 0)
        t1.setError(s2);
    else
        t1.setError("");

}

});
final androidx.appcompat.widget.AppCompatEditText ip;
switch(MUID_STATIC) {
// RenameBookmark_7_InvalidViewFocusOperatorMutator
case 7096: {
    /**
    * Inserted by Kadabra
    */
    ip = v2.findViewById(com.amaze.filemanager.R.id.editText);
    ip.requestFocus();
    break;
}
// RenameBookmark_8_ViewComponentNotVisibleOperatorMutator
case 8096: {
    /**
    * Inserted by Kadabra
    */
    ip = v2.findViewById(com.amaze.filemanager.R.id.editText);
    ip.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
ip = v2.findViewById(com.amaze.filemanager.R.id.editText);
break;
}
}
t2.setVisibility(android.view.View.GONE);
ip.setText(pa);
builder.onNeutral((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> dialog.dismiss());
materialDialog = builder.build();
switch(MUID_STATIC) {
// RenameBookmark_9_BuggyGUIListenerOperatorMutator
case 9096: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener(null);
break;
}
default: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener((android.view.View v) -> {
java.lang.String t;
t = ip.getText().toString();
java.lang.String name;
name = conName.getText().toString();
int i;
i = -1;
if ((i = dataUtils.containsBooks(new java.lang.String[]{ title, path })) != (-1)) {
    if ((!t.equals(title)) && (t.length() >= 1)) {
        dataUtils.removeBook(i);
        dataUtils.addBook(new java.lang.String[]{ name, t });
        dataUtils.sortBook();
        if (bookmarkCallback != null) {
            bookmarkCallback.modify(path, title, t, name);
        }
    }
}
materialDialog.dismiss();
});
break;
}
}
switch(MUID_STATIC) {
// RenameBookmark_10_BuggyGUIListenerOperatorMutator
case 10096: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener(null);
break;
}
default: {
materialDialog.getActionButton(com.afollestad.materialdialogs.DialogAction.NEGATIVE).setOnClickListener((android.view.View v) -> {
int i;
if ((i = dataUtils.containsBooks(new java.lang.String[]{ title, path })) != (-1)) {
dataUtils.removeBook(i);
if (bookmarkCallback != null) {
    bookmarkCallback.delete(title, path);
}
}
materialDialog.dismiss();
});
break;
}
}
return materialDialog;
}
return null;
}


public interface BookmarkCallback {
void delete(java.lang.String title, java.lang.String path);


void modify(java.lang.String oldpath, java.lang.String oldname, java.lang.String newpath, java.lang.String newname);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
