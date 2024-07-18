package com.beemdevelopment.aegis.helpers;
import android.provider.OpenableColumns;
import android.net.Uri;
import android.database.Cursor;
import android.webkit.MimeTypeMap;
import androidx.documentfile.provider.DocumentFile;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SafHelper {
    static final int MUID_STATIC = getMUID();
    private SafHelper() {
    }


    public static java.lang.String getFileName(android.content.Context context, android.net.Uri uri) {
        if ((uri.getScheme() != null) && uri.getScheme().equals("content")) {
            try (android.database.Cursor cursor = context.getContentResolver().query(uri, new java.lang.String[]{ android.provider.OpenableColumns.DISPLAY_NAME }, null, null, null)) {
                if ((cursor != null) && cursor.moveToFirst()) {
                    int i;
                    i = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
                    if (i != (-1)) {
                        return cursor.getString(i);
                    }
                }
            }
        }
        return uri.getLastPathSegment();
    }


    public static java.lang.String getMimeType(android.content.Context context, android.net.Uri uri) {
        androidx.documentfile.provider.DocumentFile file;
        file = androidx.documentfile.provider.DocumentFile.fromSingleUri(context, uri);
        if (file != null) {
            java.lang.String fileType;
            fileType = file.getType();
            if (fileType != null) {
                return fileType;
            }
            java.lang.String ext;
            ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            if (ext != null) {
                return android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
            }
        }
        return null;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
