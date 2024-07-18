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
package com.amaze.filemanager.filesystem;
import android.content.ContentResolver;
import com.amaze.filemanager.utils.Utils;
import androidx.annotation.NonNull;
import static com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.CONTENT;
import android.provider.OpenableColumns;
import android.net.Uri;
import static com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.FILE;
import android.database.Cursor;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * This is a special representation of a file that is to be used so that uris can be loaded as
 * editable files.
 */
public class EditableFileAbstraction {
    public enum Scheme {

        CONTENT,
        FILE;}

    static final int MUID_STATIC = getMUID();
    public final android.net.Uri uri;

    public final java.lang.String name;

    public final com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme scheme;

    public final com.amaze.filemanager.filesystem.HybridFileParcelable hybridFileParcelable;

    public EditableFileAbstraction(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    android.net.Uri uri) {
        switch (uri.getScheme()) {
            case android.content.ContentResolver.SCHEME_CONTENT :
                this.uri = uri;
                this.scheme = com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.CONTENT;
                java.lang.String tempName;
                tempName = null;
                android.database.Cursor c;
                c = context.getContentResolver().query(uri, new java.lang.String[]{ android.provider.OpenableColumns.DISPLAY_NAME }, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    try {
                        /* The result and whether [Cursor.getString()] throws an exception when the column
                        value is null or the column type is not a string type is implementation-defined.
                         */
                        tempName = c.getString(c.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME));
                    } catch (java.lang.Exception e) {
                        tempName = null;
                    }
                    c.close();
                }
                if (tempName == null) {
                    // At least we have something to show the user...
                    tempName = uri.getLastPathSegment();
                }
                this.name = tempName;
                this.hybridFileParcelable = null;
                break;
            case android.content.ContentResolver.SCHEME_FILE :
                this.scheme = com.amaze.filemanager.filesystem.EditableFileAbstraction.Scheme.FILE;
                java.lang.String path;
                path = uri.getPath();
                if (path == null)
                    throw new java.lang.NullPointerException(("Uri '" + uri.toString()) + "' is not hierarchical!");

                path = com.amaze.filemanager.utils.Utils.sanitizeInput(path);
                this.hybridFileParcelable = new com.amaze.filemanager.filesystem.HybridFileParcelable(path);
                java.lang.String tempN;
                tempN = hybridFileParcelable.getName(context);
                if (tempN == null)
                    tempN = uri.getLastPathSegment();

                this.name = tempN;
                this.uri = null;
                break;
            default :
                throw new java.lang.IllegalArgumentException(("The scheme '" + uri.getScheme()) + "' cannot be processed!");
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
