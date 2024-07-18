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
import java.util.Locale;
import java.io.OutputStream;
import android.util.Log;
import java.io.InputStream;
import android.content.ContentValues;
import java.io.IOException;
import android.net.Uri;
import android.database.Cursor;
import android.content.ContentResolver;
import com.amaze.filemanager.R;
import java.io.FileOutputStream;
import android.provider.MediaStore;
import android.os.ParcelFileDescriptor;
import java.io.File;
import androidx.annotation.Nullable;
import android.content.Context;
import android.provider.BaseColumns;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Wrapper for manipulating files via the Android Media Content Provider. As of Android 4.4 KitKat,
 * applications can no longer write to the "secondary storage" of a device. Write operations using
 * the java.io.File API will thus fail. This class restores access to those write operations by way
 * of the Media Content Provider. Note that this class relies on the internal operational
 * characteristics of the media content provider API, and as such is not guaranteed to be
 * future-proof. Then again, we did all think the java.io.File API was going to be future-proof for
 * media card access, so all bets are off. If you're forced to use this class, it's because
 * Google/AOSP made a very poor API decision in Android 4.4 KitKat. Read more at
 * https://plus.google.com/+TodLiebeck/posts/gjnmuaDM8sn Your application must declare the
 * permission "android.permission.WRITE_EXTERNAL_STORAGE". Adapted from:
 * http://forum.xda-developers.com/showpost.php?p=52151865&postcount=20
 *
 * @author Jared Rummler <jared.rummler@gmail.com>
 */
public class MediaStoreHack {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "MediaStoreHack";

    private static final java.lang.String ALBUM_ART_URI = "content://media/external/audio/albumart";

    private static final java.lang.String[] ALBUM_PROJECTION = new java.lang.String[]{ android.provider.BaseColumns._ID, android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ID, "media_type" };

    /**
     * Deletes the file. Returns true if the file has been successfully deleted or otherwise does not
     * exist. This operation is not recursive.
     */
    public static boolean delete(final android.content.Context context, final java.io.File file) {
        final java.lang.String where;
        where = android.provider.MediaStore.MediaColumns.DATA + "=?";
        final java.lang.String[] selectionArgs;
        selectionArgs = new java.lang.String[]{ file.getAbsolutePath() };
        final android.content.ContentResolver contentResolver;
        contentResolver = context.getContentResolver();
        final android.net.Uri filesUri;
        filesUri = android.provider.MediaStore.Files.getContentUri("external");
        // Delete the entry from the media database. This will actually delete media files.
        contentResolver.delete(filesUri, where, selectionArgs);
        // If the file is not a media file, create a new entry.
        if (file.exists()) {
            final android.content.ContentValues values;
            values = new android.content.ContentValues();
            values.put(android.provider.MediaStore.MediaColumns.DATA, file.getAbsolutePath());
            contentResolver.insert(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            // Delete the created entry, such that content provider will delete the file.
            contentResolver.delete(filesUri, where, selectionArgs);
        }
        return !file.exists();
    }


    private static java.io.File getExternalFilesDir(final android.content.Context context) {
        return context.getExternalFilesDir(null);
    }


    public static java.io.InputStream getInputStream(final android.content.Context context, final java.io.File file, final long size) {
        try {
            final java.lang.String where;
            where = android.provider.MediaStore.MediaColumns.DATA + "=?";
            final java.lang.String[] selectionArgs;
            selectionArgs = new java.lang.String[]{ file.getAbsolutePath() };
            final android.content.ContentResolver contentResolver;
            contentResolver = context.getContentResolver();
            final android.net.Uri filesUri;
            filesUri = android.provider.MediaStore.Files.getContentUri("external");
            contentResolver.delete(filesUri, where, selectionArgs);
            final android.content.ContentValues values;
            values = new android.content.ContentValues();
            values.put(android.provider.MediaStore.MediaColumns.DATA, file.getAbsolutePath());
            values.put(android.provider.MediaStore.MediaColumns.SIZE, size);
            final android.net.Uri uri;
            uri = contentResolver.insert(filesUri, values);
            return contentResolver.openInputStream(uri);
        } catch (final java.lang.Throwable t) {
            return null;
        }
    }


    public static java.io.OutputStream getOutputStream(android.content.Context context, java.lang.String str) {
        java.io.OutputStream outputStream;
        outputStream = null;
        android.net.Uri fileUri;
        fileUri = com.amaze.filemanager.filesystem.MediaStoreHack.getUriFromFile(str, context);
        if (fileUri != null) {
            try {
                outputStream = context.getContentResolver().openOutputStream(fileUri);
            } catch (java.lang.Throwable th) {
            }
        }
        return outputStream;
    }


    /**
     * Fallback to get uri from a path. Used only as a workaround for Kitkat ext SD card
     *
     * @param path
     * 		file path
     * @param context
     * 		context
     * @return uri of file or null if resolver.query fails
     */
    @androidx.annotation.Nullable
    public static android.net.Uri getUriFromFile(final java.lang.String path, android.content.Context context) {
        android.content.ContentResolver resolver;
        resolver = context.getContentResolver();
        android.database.Cursor filecursor;
        filecursor = resolver.query(android.provider.MediaStore.Files.getContentUri("external"), new java.lang.String[]{ android.provider.BaseColumns._ID }, android.provider.MediaStore.MediaColumns.DATA + " = ?", new java.lang.String[]{ path }, android.provider.MediaStore.MediaColumns.DATE_ADDED + " desc");
        if (filecursor == null) {
            android.util.Log.e(com.amaze.filemanager.filesystem.MediaStoreHack.TAG, "Error when deleting file " + path);
            return null;
        }
        filecursor.moveToFirst();
        if (filecursor.isAfterLast()) {
            filecursor.close();
            android.content.ContentValues values;
            values = new android.content.ContentValues();
            values.put(android.provider.MediaStore.MediaColumns.DATA, path);
            return resolver.insert(android.provider.MediaStore.Files.getContentUri("external"), values);
        } else {
            int imageId;
            imageId = filecursor.getInt(filecursor.getColumnIndex(android.provider.BaseColumns._ID));
            android.net.Uri uri;
            uri = android.provider.MediaStore.Files.getContentUri("external").buildUpon().appendEncodedPath(java.lang.Integer.toString(imageId)).build();
            filecursor.close();
            return uri;
        }
    }


    /**
     * Returns an OutputStream to write to the file. The file will be truncated immediately.
     */
    private static int getTemporaryAlbumId(final android.content.Context context) {
        final java.io.File temporaryTrack;
        try {
            temporaryTrack = com.amaze.filemanager.filesystem.MediaStoreHack.installTemporaryTrack(context);
        } catch (final java.io.IOException ex) {
            android.util.Log.w(com.amaze.filemanager.filesystem.MediaStoreHack.TAG, "Error installing temporary track.", ex);
            return 0;
        }
        final android.net.Uri filesUri;
        filesUri = android.provider.MediaStore.Files.getContentUri("external");
        final java.lang.String[] selectionArgs;
        selectionArgs = new java.lang.String[]{ temporaryTrack.getAbsolutePath() };
        final android.content.ContentResolver contentResolver;
        contentResolver = context.getContentResolver();
        android.database.Cursor cursor;
        cursor = contentResolver.query(filesUri, com.amaze.filemanager.filesystem.MediaStoreHack.ALBUM_PROJECTION, android.provider.MediaStore.MediaColumns.DATA + "=?", selectionArgs, null);
        if ((cursor == null) || (!cursor.moveToFirst())) {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            final android.content.ContentValues values;
            values = new android.content.ContentValues();
            values.put(android.provider.MediaStore.MediaColumns.DATA, temporaryTrack.getAbsolutePath());
            values.put(android.provider.MediaStore.MediaColumns.TITLE, "{MediaWrite Workaround}");
            values.put(android.provider.MediaStore.MediaColumns.SIZE, temporaryTrack.length());
            values.put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "audio/mpeg");
            values.put(android.provider.MediaStore.Audio.AudioColumns.IS_MUSIC, true);
            contentResolver.insert(filesUri, values);
        }
        cursor = contentResolver.query(filesUri, com.amaze.filemanager.filesystem.MediaStoreHack.ALBUM_PROJECTION, android.provider.MediaStore.MediaColumns.DATA + "=?", selectionArgs, null);
        if (cursor == null) {
            return 0;
        }
        if (!cursor.moveToFirst()) {
            cursor.close();
            return 0;
        }
        final int id;
        id = cursor.getInt(0);
        final int albumId;
        albumId = cursor.getInt(1);
        final int mediaType;
        mediaType = cursor.getInt(2);
        cursor.close();
        final android.content.ContentValues values;
        values = new android.content.ContentValues();
        boolean updateRequired;
        updateRequired = false;
        if (albumId == 0) {
            values.put(android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ID, 13371337);
            updateRequired = true;
        }
        if (mediaType != 2) {
            values.put("media_type", 2);
            updateRequired = true;
        }
        if (updateRequired) {
            contentResolver.update(filesUri, values, (android.provider.BaseColumns._ID + "=") + id, null);
        }
        cursor = contentResolver.query(filesUri, com.amaze.filemanager.filesystem.MediaStoreHack.ALBUM_PROJECTION, android.provider.MediaStore.MediaColumns.DATA + "=?", selectionArgs, null);
        if (cursor == null) {
            return 0;
        }
        try {
            if (!cursor.moveToFirst()) {
                return 0;
            }
            return cursor.getInt(1);
        } finally {
            cursor.close();
        }
    }


    private static java.io.File installTemporaryTrack(final android.content.Context context) throws java.io.IOException {
        final java.io.File externalFilesDir;
        externalFilesDir = com.amaze.filemanager.filesystem.MediaStoreHack.getExternalFilesDir(context);
        if (externalFilesDir == null) {
            return null;
        }
        final java.io.File temporaryTrack;
        temporaryTrack = new java.io.File(externalFilesDir, "temptrack.mp3");
        if (!temporaryTrack.exists()) {
            java.io.InputStream in;
            in = null;
            java.io.OutputStream out;
            out = null;
            try {
                in = context.getResources().openRawResource(com.amaze.filemanager.R.raw.temptrack);
                out = new java.io.FileOutputStream(temporaryTrack);
                final byte[] buffer;
                buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != (-1)) {
                    out.write(buffer, 0, bytesRead);
                } 
            } finally {
                out.close();
                in.close();
            }
        }
        return temporaryTrack;
    }


    public static boolean mkdir(final android.content.Context context, final java.io.File file) throws java.io.IOException {
        if (file.exists()) {
            return file.isDirectory();
        }
        final java.io.File tmpFile;
        tmpFile = new java.io.File(file, ".MediaWriteTemp");
        final int albumId;
        albumId = com.amaze.filemanager.filesystem.MediaStoreHack.getTemporaryAlbumId(context);
        if (albumId == 0) {
            throw new java.io.IOException("Failed to create temporary album id.");
        }
        final android.net.Uri albumUri;
        albumUri = android.net.Uri.parse(java.lang.String.format(java.util.Locale.US, com.amaze.filemanager.filesystem.MediaStoreHack.ALBUM_ART_URI + "/%d", albumId));
        final android.content.ContentValues values;
        values = new android.content.ContentValues();
        values.put(android.provider.MediaStore.MediaColumns.DATA, tmpFile.getAbsolutePath());
        final android.content.ContentResolver contentResolver;
        contentResolver = context.getContentResolver();
        if (contentResolver.update(albumUri, values, null, null) == 0) {
            values.put(android.provider.MediaStore.Audio.AlbumColumns.ALBUM_ID, albumId);
            contentResolver.insert(android.net.Uri.parse(com.amaze.filemanager.filesystem.MediaStoreHack.ALBUM_ART_URI), values);
        }
        try {
            final android.os.ParcelFileDescriptor fd;
            fd = contentResolver.openFileDescriptor(albumUri, "r");
            fd.close();
        } finally {
            com.amaze.filemanager.filesystem.MediaStoreHack.delete(context, tmpFile);
        }
        return file.exists();
    }


    public static boolean mkfile(final android.content.Context context, final java.io.File file) {
        final java.io.OutputStream outputStream;
        outputStream = com.amaze.filemanager.filesystem.MediaStoreHack.getOutputStream(context, file.getPath());
        if (outputStream == null) {
            return false;
        }
        try {
            outputStream.close();
            return true;
        } catch (final java.io.IOException e) {
        }
        return false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
