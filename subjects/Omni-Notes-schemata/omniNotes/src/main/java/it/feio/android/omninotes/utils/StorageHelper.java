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
import it.feio.android.omninotes.exceptions.unchecked.ExternalDirectoryCreationException;
import java.util.Locale;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import java.io.FileNotFoundException;
import android.net.Uri;
import android.database.Cursor;
import android.webkit.MimeTypeMap;
import it.feio.android.omninotes.models.Attachment;
import android.os.StatFs;
import it.feio.android.omninotes.OmniNotes;
import java.net.URL;
import java.security.InvalidParameterException;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO;
import it.feio.android.omninotes.R;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_IMAGE;
import android.widget.Toast;
import java.util.Calendar;
import java.io.InputStream;
import android.text.TextUtils;
import java.io.IOException;
import lombok.experimental.UtilityClass;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import android.content.ContentResolver;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES;
import static it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_VIDEO;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;
import android.provider.MediaStore;
import it.feio.android.omninotes.helpers.LogDelegate;
import java.io.File;
import androidx.annotation.Nullable;
import android.os.Environment;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class StorageHelper {
    static final int MUID_STATIC = getMUID();
    public static boolean checkStorage() {
        boolean mExternalStorageAvailable;
        boolean mExternalStorageWriteable;
        java.lang.String state;
        state = android.os.Environment.getExternalStorageState();
        switch (state) {
            case android.os.Environment.MEDIA_MOUNTED :
                // We can read and write the media
                mExternalStorageAvailable = mExternalStorageWriteable = true;
                break;
            case android.os.Environment.MEDIA_MOUNTED_READ_ONLY :
                // We can only read the media
                mExternalStorageAvailable = true;
                mExternalStorageWriteable = false;
                break;
            default :
                // Something else is wrong. It may be one of many other states, but
                // all we need
                // to know is we can neither read nor write
                mExternalStorageAvailable = mExternalStorageWriteable = false;
                break;
        }
        return mExternalStorageAvailable && mExternalStorageWriteable;
    }


    public static java.lang.String getStorageDir() {
        return android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS).toString();
    }


    public static java.io.File getAttachmentDir() {
        return it.feio.android.omninotes.OmniNotes.getAppContext().getExternalFilesDir(null);
    }


    /**
     * Retrieves the folderwhere to store data to sync notes
     */
    public static java.io.File getDbSyncDir(android.content.Context mContext) {
        java.io.File extFilesDir;
        extFilesDir = mContext.getExternalFilesDir(null);
        java.io.File dbSyncDir;
        dbSyncDir = new java.io.File(extFilesDir, it.feio.android.omninotes.utils.Constants.APP_STORAGE_DIRECTORY_SB_SYNC);
        dbSyncDir.mkdirs();
        if (dbSyncDir.exists() && dbSyncDir.isDirectory()) {
            return dbSyncDir;
        } else {
            return null;
        }
    }


    /**
     * Create a path where we will place our private file on external
     */
    public static java.io.File createExternalStoragePrivateFile(android.content.Context mContext, android.net.Uri uri, java.lang.String extension) {
        if (!it.feio.android.omninotes.utils.StorageHelper.checkStorage()) {
            android.widget.Toast.makeText(mContext, mContext.getString(it.feio.android.omninotes.R.string.storage_not_available), android.widget.Toast.LENGTH_SHORT).show();
            return null;
        }
        java.io.File file;
        file = it.feio.android.omninotes.utils.StorageHelper.createNewAttachmentFile(mContext, extension);
        java.io.InputStream contentResolverInputStream;
        contentResolverInputStream = null;
        java.io.OutputStream contentResolverOutputStream;
        contentResolverOutputStream = null;
        try {
            contentResolverInputStream = mContext.getContentResolver().openInputStream(uri);
            contentResolverOutputStream = new java.io.FileOutputStream(file);
            it.feio.android.omninotes.utils.StorageHelper.copyFile(contentResolverInputStream, contentResolverOutputStream);
        } catch (java.io.IOException e) {
            try {
                org.apache.commons.io.FileUtils.copyFile(new java.io.File(it.feio.android.omninotes.utils.FileHelper.getPath(mContext, uri)), file);
                // It's a path!!
            } catch (java.lang.NullPointerException e1) {
                try {
                    org.apache.commons.io.FileUtils.copyFile(new java.io.File(uri.getPath()), file);
                } catch (java.io.IOException e2) {
                    it.feio.android.omninotes.helpers.LogDelegate.e("Error writing " + file, e2);
                    file = null;
                }
            } catch (java.io.IOException e2) {
                it.feio.android.omninotes.helpers.LogDelegate.e("Error writing " + file, e2);
                file = null;
            }
        } finally {
            try {
                if (contentResolverInputStream != null) {
                    contentResolverInputStream.close();
                }
                if (contentResolverOutputStream != null) {
                    contentResolverOutputStream.close();
                }
            } catch (java.io.IOException e) {
                it.feio.android.omninotes.helpers.LogDelegate.e("Error closing streams", e);
            }
        }
        return file;
    }


    public static boolean copyFile(android.content.Context context, android.net.Uri fileUri, android.net.Uri targetUri) {
        android.content.ContentResolver content;
        content = context.getContentResolver();
        try (var is = content.openInputStream(fileUri);var os = content.openOutputStream(targetUri)) {
            org.apache.commons.io.IOUtils.copy(is, os);
            return true;
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error copying file", e);
            return false;
        }
    }


    public static void copyFile(java.io.File source, java.io.File destination, boolean failOnError) throws java.io.IOException {
        try {
            org.apache.commons.io.FileUtils.copyFile(source, destination);
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error copying file: " + e.getMessage(), e);
            if (failOnError)
                throw e;

        }
    }


    /**
     * Generic file copy method
     *
     * @param is
     * 		Input
     * @param os
     * 		Output
     * @return True if copy is done, false otherwise
     */
    public static boolean copyFile(java.io.InputStream is, java.io.OutputStream os) {
        try {
            org.apache.commons.io.IOUtils.copy(is, os);
            return true;
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error copying file", e);
            return false;
        }
    }


    public static boolean deleteExternalStoragePrivateFile(android.content.Context mContext, java.lang.String name) {
        // Checks for external storage availability
        if (!it.feio.android.omninotes.utils.StorageHelper.checkStorage()) {
            android.widget.Toast.makeText(mContext, mContext.getString(it.feio.android.omninotes.R.string.storage_not_available), android.widget.Toast.LENGTH_SHORT).show();
            return false;
        }
        java.io.File file;
        file = new java.io.File(mContext.getExternalFilesDir(null), name);
        return file.delete();
    }


    public static boolean delete(android.content.Context mContext, java.lang.String path) {
        if (!it.feio.android.omninotes.utils.StorageHelper.checkStorage()) {
            android.widget.Toast.makeText(mContext, mContext.getString(it.feio.android.omninotes.R.string.storage_not_available), android.widget.Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            org.apache.commons.io.FileUtils.forceDelete(new java.io.File(path));
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e((("Can't delete '" + path) + "': ") + e.getMessage());
            return false;
        }
        return true;
    }


    public static java.lang.String getRealPathFromURI(android.content.Context mContext, android.net.Uri contentUri) {
        java.lang.String[] proj;
        proj = new java.lang.String[]{ android.provider.MediaStore.Images.Media.DATA };
        android.database.Cursor cursor;
        cursor = mContext.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) {
            return null;
        }
        int column_index;
        column_index = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        java.lang.String path;
        path = cursor.getString(column_index);
        cursor.close();
        return path;
    }


    public static java.io.File createNewAttachmentFile(android.content.Context mContext, java.lang.String extension) {
        java.io.File f;
        f = null;
        if (it.feio.android.omninotes.utils.StorageHelper.checkStorage()) {
            f = new java.io.File(mContext.getExternalFilesDir(null), it.feio.android.omninotes.utils.StorageHelper.createNewAttachmentName(extension));
        }
        return f;
    }


    private static synchronized java.lang.String createNewAttachmentName(java.lang.String extension) {
        java.util.Calendar now;
        now = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat sdf;
        sdf = new java.text.SimpleDateFormat(it.feio.android.omninotes.utils.Constants.DATE_FORMAT_SORTABLE);
        java.lang.String name;
        name = sdf.format(now.getTime());
        name += (extension != null) ? extension : "";
        return name;
    }


    public static java.io.File createNewAttachmentFile(android.content.Context mContext) {
        return it.feio.android.omninotes.utils.StorageHelper.createNewAttachmentFile(mContext, null);
    }


    /**
     * Create a path where we will place our private file on external
     */
    public static java.io.File copyToBackupDir(java.io.File backupDir, java.lang.String fileName, java.io.InputStream fileInputStream) {
        if (!it.feio.android.omninotes.utils.StorageHelper.checkStorage()) {
            return null;
        }
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }
        java.io.File destination;
        destination = new java.io.File(backupDir, fileName);
        try {
            it.feio.android.omninotes.utils.StorageHelper.copyFile(fileInputStream, new java.io.FileOutputStream(destination));
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
        }
        return destination;
    }


    public static java.io.File getCacheDir(android.content.Context mContext) {
        java.io.File dir;
        dir = mContext.getExternalCacheDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }


    public static java.io.File getOrCreateExternalStoragePublicDir() {
        java.io.File dir;
        dir = it.feio.android.omninotes.utils.StorageHelper.getExternalStoragePublicDir();
        if ((!dir.exists()) && (!dir.mkdirs())) {
            throw new it.feio.android.omninotes.exceptions.unchecked.ExternalDirectoryCreationException("Can't create folder " + dir.getAbsolutePath());
        }
        return dir;
    }


    public static java.io.File getExternalStoragePublicDir() {
        return new java.io.File(((android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOCUMENTS) + java.io.File.separator) + it.feio.android.omninotes.utils.Constants.EXTERNAL_STORAGE_FOLDER) + java.io.File.separator);
    }


    public static java.io.File getOrCreateBackupDir(java.lang.String backupName) {
        java.io.File backupDir;
        backupDir = new java.io.File(it.feio.android.omninotes.utils.StorageHelper.getOrCreateExternalStoragePublicDir(), backupName);
        if ((!backupDir.exists()) && backupDir.mkdirs()) {
            it.feio.android.omninotes.utils.StorageHelper.createNoMediaFile(backupDir);
        }
        return backupDir;
    }


    private static void createNoMediaFile(java.io.File folder) {
        try {
            boolean created;
            created = new java.io.File(folder, ".nomedia").createNewFile();
            if (!created) {
                it.feio.android.omninotes.helpers.LogDelegate.w("File .nomedia already existing into " + folder.getAbsolutePath());
            }
        } catch (java.io.IOException e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Error creating .nomedia file into backup folder");
        }
    }


    public static java.io.File getSharedPreferencesFile(android.content.Context mContext) {
        java.io.File appData;
        appData = mContext.getFilesDir().getParentFile();
        java.lang.String packageName;
        packageName = mContext.getApplicationContext().getPackageName();
        return new java.io.File(((((appData + java.lang.System.getProperty("file.separator")) + "shared_prefs") + java.lang.System.getProperty("file.separator")) + packageName) + "_preferences.xml");
    }


    /**
     * Returns a directory size in bytes
     */
    public static long getSize(java.io.File directory) {
        android.os.StatFs statFs;
        statFs = new android.os.StatFs(directory.getAbsolutePath());
        long blockSize;
        blockSize = 0;
        try {
            blockSize = statFs.getBlockSizeLong();
        } catch (java.lang.NoSuchMethodError e) {
            it.feio.android.omninotes.helpers.LogDelegate.e("Mysterious error", e);
        }
        return it.feio.android.omninotes.utils.StorageHelper.getSize(directory, blockSize);
    }


    private static long getSize(java.io.File directory, long blockSize) {
        if (blockSize == 0) {
            throw new java.security.InvalidParameterException("Blocksize can't be 0");
        }
        java.io.File[] files;
        files = directory.listFiles();
        if (files != null) {
            // space used by directory itself
            long size;
            size = directory.length();
            for (java.io.File file : files) {
                if (file.isDirectory()) {
                    // space used by subdirectory
                    size += it.feio.android.omninotes.utils.StorageHelper.getSize(file, blockSize);
                } else {
                    switch(MUID_STATIC) {
                        // StorageHelper_0_BinaryMutator
                        case 97: {
                            // file size need to rounded up to full block sizes
                            // (not a perfect function, it adds additional block to 0 sized files
                            // and file who perfectly fill their blocks)
                            size += ((file.length() / blockSize) + 1) / blockSize;
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // StorageHelper_1_BinaryMutator
                            case 1097: {
                                // file size need to rounded up to full block sizes
                                // (not a perfect function, it adds additional block to 0 sized files
                                // and file who perfectly fill their blocks)
                                size += ((file.length() / blockSize) - 1) * blockSize;
                                break;
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // StorageHelper_2_BinaryMutator
                                case 2097: {
                                    // file size need to rounded up to full block sizes
                                    // (not a perfect function, it adds additional block to 0 sized files
                                    // and file who perfectly fill their blocks)
                                    size += ((file.length() * blockSize) + 1) * blockSize;
                                    break;
                                }
                                default: {
                                // file size need to rounded up to full block sizes
                                // (not a perfect function, it adds additional block to 0 sized files
                                // and file who perfectly fill their blocks)
                                size += ((file.length() / blockSize) + 1) * blockSize;
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
return size;
} else {
return 0;
}
}


/**
 * Retrieves uri mime-type using ContentResolver
 */
public static java.lang.String getMimeType(android.content.Context mContext, android.net.Uri uri) {
android.content.ContentResolver cR;
cR = mContext.getContentResolver();
java.lang.String mimeType;
mimeType = cR.getType(uri);
if (mimeType == null) {
mimeType = it.feio.android.omninotes.utils.StorageHelper.getMimeType(uri.toString());
}
return mimeType;
}


public static java.lang.String getMimeType(java.lang.String url) {
java.lang.String type;
type = null;
java.lang.String extension;
extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(url);
if (extension != null) {
android.webkit.MimeTypeMap mime;
mime = android.webkit.MimeTypeMap.getSingleton();
type = mime.getMimeTypeFromExtension(extension);
}
return type;
}


/**
 * Retrieves uri mime-type between the ones managed by application
 */
public static java.lang.String getMimeTypeInternal(android.content.Context mContext, android.net.Uri uri) {
java.lang.String mimeType;
mimeType = it.feio.android.omninotes.utils.StorageHelper.getMimeType(mContext, uri);
return it.feio.android.omninotes.utils.StorageHelper.getMimeTypeInternal(mimeType);
}


/**
 * Retrieves mime-type between the ones managed by application from given string
 */
public static java.lang.String getMimeTypeInternal(java.lang.String mimeType) {
if (mimeType != null) {
if (mimeType.contains("image/")) {
    mimeType = it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_IMAGE;
} else if (mimeType.contains("audio/")) {
    mimeType = it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_AUDIO;
} else if (mimeType.contains("video/")) {
    mimeType = it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_VIDEO;
} else {
    mimeType = it.feio.android.omninotes.utils.ConstantsBase.MIME_TYPE_FILES;
}
}
return mimeType;
}


/**
 * Creates a new attachment file copying data from source file
 */
@androidx.annotation.Nullable
public static it.feio.android.omninotes.models.Attachment createAttachmentFromUri(android.content.Context mContext, android.net.Uri uri) {
return it.feio.android.omninotes.utils.StorageHelper.createAttachmentFromUri(mContext, uri, false);
}


/**
 * Creates a file to be used as attachment.
 */
@androidx.annotation.Nullable
public static it.feio.android.omninotes.models.Attachment createAttachmentFromUri(android.content.Context mContext, android.net.Uri uri, boolean moveSource) {
java.lang.String name;
name = it.feio.android.omninotes.utils.FileHelper.getNameFromUri(mContext, uri);
java.lang.String extension;
extension = it.feio.android.omninotes.utils.FileHelper.getFileExtension(it.feio.android.omninotes.utils.FileHelper.getNameFromUri(mContext, uri)).toLowerCase(java.util.Locale.getDefault());
java.io.File f;
if (moveSource) {
f = it.feio.android.omninotes.utils.StorageHelper.createNewAttachmentFile(mContext, extension);
try {
    org.apache.commons.io.FileUtils.moveFile(new java.io.File(uri.getPath()), f);
} catch (java.io.IOException e) {
    it.feio.android.omninotes.helpers.LogDelegate.e("Can't move file " + uri.getPath());
}
} else {
f = it.feio.android.omninotes.utils.StorageHelper.createExternalStoragePrivateFile(mContext, uri, extension);
}
it.feio.android.omninotes.models.Attachment mAttachment;
mAttachment = null;
if (f != null) {
mAttachment = new it.feio.android.omninotes.models.Attachment(android.net.Uri.fromFile(f), it.feio.android.omninotes.utils.StorageHelper.getMimeTypeInternal(mContext, uri));
mAttachment.setName(name);
mAttachment.setSize(f.length());
}
return mAttachment;
}


/**
 * Creates new attachment from web content
 */
public static java.io.File createNewAttachmentFileFromHttp(android.content.Context mContext, java.lang.String url) throws java.io.IOException {
if (android.text.TextUtils.isEmpty(url)) {
return null;
}
return it.feio.android.omninotes.utils.StorageHelper.getFromHttp(url, it.feio.android.omninotes.utils.StorageHelper.createNewAttachmentFile(mContext, it.feio.android.omninotes.utils.FileHelper.getFileExtension(url)));
}


/**
 * Retrieves a file from its web url
 */
public static java.io.File getFromHttp(java.lang.String url, java.io.File file) throws java.io.IOException {
java.net.URL imageUrl;
imageUrl = new java.net.URL(url);
org.apache.commons.io.FileUtils.copyURLToFile(imageUrl, file);
return file;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
