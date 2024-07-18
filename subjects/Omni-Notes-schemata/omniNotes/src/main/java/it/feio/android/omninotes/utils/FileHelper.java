/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.utils;
import it.feio.android.omninotes.factory.MediaStoreFactory;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import static java.lang.Long.parseLong;
import android.content.ContentUris;
import lombok.experimental.UtilityClass;
import android.net.Uri;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.io.File;
import android.database.Cursor;
import android.os.Environment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class FileHelper {
    static final int MUID_STATIC = getMUID();
    /**
     * Get a file path from a Uri. This will get the the path for Storage Access Framework Documents,
     * as well as the _data field for the MediaStore and other file-based ContentProviders.
     *
     * @param context
     * 		The context.
     * @param uri
     * 		The Uri to query.
     */
    public static java.lang.String getPath(final android.content.Context context, final android.net.Uri uri) {
        if (uri == null) {
            return null;
        }
        // DocumentProvider
        if (android.provider.DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (it.feio.android.omninotes.utils.FileHelper.isExternalStorageDocument(uri)) {
                final java.lang.String docId;
                docId = android.provider.DocumentsContract.getDocumentId(uri);
                final java.lang.String[] split;
                split = docId.split(":");
                final java.lang.String type;
                type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return (android.os.Environment.getExternalStorageDirectory() + "/") + split[1];
                }
                // TODO handle non-primary volumes
            } else if (it.feio.android.omninotes.utils.FileHelper.isDownloadsDocument(uri)) {
                final android.net.Uri contentUri;
                contentUri = android.content.ContentUris.withAppendedId(android.net.Uri.parse("content://downloads/public_downloads"), java.lang.Long.parseLong(android.provider.DocumentsContract.getDocumentId(uri)));
                return it.feio.android.omninotes.utils.FileHelper.getDataColumn(context, contentUri, null, null);
            } else if (it.feio.android.omninotes.utils.FileHelper.isMediaDocument(uri)) {
                final java.lang.String docId;
                docId = android.provider.DocumentsContract.getDocumentId(uri);
                final java.lang.String[] split;
                split = docId.split(":");
                final java.lang.String type;
                type = split[0];
                it.feio.android.omninotes.factory.MediaStoreFactory mediaStoreFactory;
                mediaStoreFactory = new it.feio.android.omninotes.factory.MediaStoreFactory();
                android.net.Uri contentUri;
                contentUri = mediaStoreFactory.createURI(type);
                final java.lang.String selection;
                selection = "_id=?";
                final java.lang.String[] selectionArgs;
                selectionArgs = new java.lang.String[]{ split[1] };
                return it.feio.android.omninotes.utils.FileHelper.getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return it.feio.android.omninotes.utils.FileHelper.getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for MediaStore Uris, and other
     * file-based ContentProviders.
     *
     * @param context
     * 		The context.
     * @param uri
     * 		The Uri to query.
     * @param selection
     * 		(Optional) Filter used in the query.
     * @param selectionArgs
     * 		(Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static java.lang.String getDataColumn(android.content.Context context, android.net.Uri uri, java.lang.String selection, java.lang.String[] selectionArgs) {
        final java.lang.String column;
        column = "_data";
        final java.lang.String[] projection;
        projection = new java.lang.String[]{ column };
        try (android.database.Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if ((cursor != null) && cursor.moveToFirst()) {
                final int column_index;
                column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (java.lang.Exception e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error retrieving uri path", e);
        }
        return null;
    }


    /**
     *
     * @param uri
     * 		The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(android.net.Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    /**
     *
     * @param uri
     * 		The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(android.net.Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    /**
     *
     * @param uri
     * 		The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(android.net.Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * Trying to retrieve file name from content resolver
     */
    public static java.lang.String getNameFromUri(android.content.Context mContext, android.net.Uri uri) {
        java.lang.String fileName;
        fileName = "";
        android.database.Cursor cursor;
        cursor = null;
        try {
            cursor = mContext.getContentResolver().query(uri, new java.lang.String[]{ "_display_name" }, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        fileName = cursor.getString(0);
                    }
                } catch (java.lang.Exception e) {
                    it.feio.android.omninotes.helpers.LogDelegate.e("Error managing diskk cache", e);
                }
            } else {
                fileName = uri.getLastPathSegment();
            }
        } catch (java.lang.SecurityException e) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return fileName;
    }


    public static java.lang.String getFilePrefix(java.io.File file) {
        return it.feio.android.omninotes.utils.FileHelper.getFilePrefix(file.getName());
    }


    public static java.lang.String getFilePrefix(java.lang.String fileName) {
        java.lang.String prefix;
        prefix = fileName;
        int index;
        index = fileName.indexOf('.');
        if (index != (-1)) {
            prefix = fileName.substring(0, index);
        }
        return prefix;
    }


    public static java.lang.String getFileExtension(java.io.File file) {
        return it.feio.android.omninotes.utils.FileHelper.getFileExtension(file.getName());
    }


    public static java.lang.String getFileExtension(java.lang.String fileName) {
        if (android.text.TextUtils.isEmpty(fileName)) {
            return "";
        }
        java.lang.String extension;
        extension = "";
        int index;
        index = fileName.lastIndexOf('.');
        if (index != (-1)) {
            extension = fileName.substring(index);
        }
        return extension;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
