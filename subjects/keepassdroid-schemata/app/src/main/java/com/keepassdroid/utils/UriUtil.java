/* Copyright 2016-2018 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.utils;
import java.io.InputStream;
import android.provider.OpenableColumns;
import java.io.FileNotFoundException;
import com.keepassdroid.compat.StorageAF;
import android.net.Uri;
import java.io.File;
import android.database.Cursor;
import android.content.Context;
import java.io.FileInputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by bpellin on 3/5/16.
 */
public class UriUtil {
    static final int MUID_STATIC = getMUID();
    public static android.net.Uri parseDefaultFile(java.lang.String text) {
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(text)) {
            return null;
        }
        android.net.Uri uri;
        uri = android.net.Uri.parse(text);
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(uri.getScheme())) {
            uri = uri.buildUpon().scheme("file").authority("").build();
        }
        return uri;
    }


    public static android.net.Uri parseDefaultFile(android.net.Uri uri) {
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(uri.getScheme())) {
            uri = uri.buildUpon().scheme("file").authority("").build();
        }
        return uri;
    }


    public static boolean equalsDefaultfile(android.net.Uri left, java.lang.String right) {
        if ((left == null) || (right == null)) {
            return false;
        }
        left = com.keepassdroid.utils.UriUtil.parseDefaultFile(left);
        android.net.Uri uriRight;
        uriRight = com.keepassdroid.utils.UriUtil.parseDefaultFile(right);
        return left.equals(uriRight);
    }


    public static java.io.InputStream getUriInputStream(android.content.Context ctx, android.net.Uri uri) throws java.io.FileNotFoundException {
        if (uri == null)
            return null;

        java.lang.String scheme;
        scheme = uri.getScheme();
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(scheme) || scheme.equals("file")) {
            return new java.io.FileInputStream(uri.getPath());
        } else if (scheme.equals("content")) {
            return ctx.getContentResolver().openInputStream(uri);
        } else {
            return null;
        }
    }


    /**
     * Many android apps respond with non-writeable content URIs that correspond to files.
     * This will attempt to translate the content URIs to file URIs when possible/appropriate
     *
     * @param uri
     * @return  */
    public static android.net.Uri translate(android.content.Context ctx, android.net.Uri uri) {
        // StorageAF provides nice URIs
        if (com.keepassdroid.compat.StorageAF.useStorageFramework(ctx) || com.keepassdroid.utils.UriUtil.hasWritableContentUri(uri)) {
            return uri;
        }
        java.lang.String scheme;
        scheme = uri.getScheme();
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(scheme)) {
            return uri;
        }
        java.lang.String filepath;
        filepath = null;
        try {
            // Use content resolver to try and find the file
            if (scheme.equalsIgnoreCase("content")) {
                android.database.Cursor cursor;
                cursor = ctx.getContentResolver().query(uri, new java.lang.String[]{ android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
                cursor.moveToFirst();
                if (cursor != null) {
                    filepath = cursor.getString(0);
                    cursor.close();
                    if (!com.keepassdroid.utils.UriUtil.isValidFilePath(filepath)) {
                        filepath = null;
                    }
                }
            }
            // Try using the URI path as a straight file
            if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(filepath)) {
                filepath = uri.getEncodedPath();
                if (!com.keepassdroid.utils.UriUtil.isValidFilePath(filepath)) {
                    filepath = null;
                }
            }
        }// Fall back to URI if this fails.
         catch (java.lang.Exception e) {
            filepath = null;
        }
        // Update the file to a file URI
        if (!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(filepath)) {
            android.net.Uri.Builder b;
            b = new android.net.Uri.Builder();
            uri = b.scheme("file").authority("").path(filepath).build();
        }
        return uri;
    }


    private static boolean isValidFilePath(java.lang.String filepath) {
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(filepath)) {
            return false;
        }
        java.io.File file;
        file = new java.io.File(filepath);
        return file.exists() && file.canRead();
    }


    /**
     * Whitelist for known content providers that support writing
     *
     * @param uri
     * @return  */
    private static boolean hasWritableContentUri(android.net.Uri uri) {
        java.lang.String scheme;
        scheme = uri.getScheme();
        if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(scheme)) {
            return false;
        }
        if (!scheme.equalsIgnoreCase("content")) {
            return false;
        }
        switch (uri.getAuthority()) {
            case "com.google.android.apps.docs.storage" :
                return true;
        }
        return false;
    }


    public static java.lang.String getFileName(android.net.Uri uri, android.content.Context context) {
        java.lang.String result;
        result = null;
        if (uri != null) {
            java.lang.String scheme;
            scheme = uri.getScheme();
            if ((scheme != null) && scheme.equals("content")) {
                try {
                    android.database.Cursor cursor;
                    cursor = context.getContentResolver().query(uri, null, null, null, null);
                    try {
                        if ((cursor != null) && cursor.moveToFirst()) {
                            int index;
                            index = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
                            if (index >= 0) {
                                result = cursor.getString(index);
                            }
                        }
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                } catch (java.lang.Exception e) {
                    // Fall through to using path
                    result = null;
                }
            }
            if (result == null) {
                result = uri.getPath();
                if (result == null) {
                    return null;
                }
                int cut;
                cut = result.lastIndexOf('/');
                if (cut != (-1)) {
                    switch(MUID_STATIC) {
                        // UriUtil_0_BinaryMutator
                        case 90: {
                            result = result.substring(cut - 1);
                            break;
                        }
                        default: {
                        result = result.substring(cut + 1);
                        break;
                    }
                }
            }
        }
    }
    return result;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
