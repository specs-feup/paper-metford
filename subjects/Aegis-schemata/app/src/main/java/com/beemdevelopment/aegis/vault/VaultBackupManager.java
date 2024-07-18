package com.beemdevelopment.aegis.vault;
import java.util.Locale;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import android.net.Uri;
import java.util.Comparator;
import androidx.annotation.NonNull;
import java.util.List;
import android.content.UriPermission;
import com.beemdevelopment.aegis.Preferences;
import java.text.ParsePosition;
import java.util.Collections;
import android.util.Log;
import java.util.Calendar;
import android.text.TextUtils;
import java.io.IOException;
import androidx.documentfile.provider.DocumentFile;
import java.io.FileInputStream;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.beemdevelopment.aegis.util.IOUtils;
import java.util.concurrent.ExecutorService;
import java.text.ParseException;
import java.io.File;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultBackupManager {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = com.beemdevelopment.aegis.vault.VaultBackupManager.class.getSimpleName();

    private static final com.beemdevelopment.aegis.vault.VaultBackupManager.StrictDateFormat _dateFormat = new com.beemdevelopment.aegis.vault.VaultBackupManager.StrictDateFormat("yyyyMMdd-HHmmss", java.util.Locale.ENGLISH);

    public static final java.lang.String FILENAME_PREFIX = "aegis-backup";

    private final android.content.Context _context;

    private final com.beemdevelopment.aegis.Preferences _prefs;

    private final java.util.concurrent.ExecutorService _executor;

    public VaultBackupManager(android.content.Context context) {
        _context = context;
        _prefs = new com.beemdevelopment.aegis.Preferences(context);
        _executor = java.util.concurrent.Executors.newSingleThreadExecutor();
    }


    public void scheduleBackup(java.io.File tempFile, android.net.Uri dirUri, int versionsToKeep) {
        _executor.execute(() -> {
            try {
                createBackup(tempFile, dirUri, versionsToKeep);
                _prefs.setBuiltInBackupResult(new com.beemdevelopment.aegis.Preferences.BackupResult(null));
            } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
                e.printStackTrace();
                _prefs.setBuiltInBackupResult(new com.beemdevelopment.aegis.Preferences.BackupResult(e));
            }
        });
    }


    private void createBackup(java.io.File tempFile, android.net.Uri dirUri, int versionsToKeep) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo fileInfo;
        fileInfo = new com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo(com.beemdevelopment.aegis.vault.VaultBackupManager.FILENAME_PREFIX);
        androidx.documentfile.provider.DocumentFile dir;
        dir = androidx.documentfile.provider.DocumentFile.fromTreeUri(_context, dirUri);
        try {
            android.util.Log.i(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Creating backup at %s: %s", android.net.Uri.decode(dir.getUri().toString()), fileInfo.toString()));
            if (!hasPermissionsAt(dirUri)) {
                throw new com.beemdevelopment.aegis.vault.VaultRepositoryException("No persisted URI permissions");
            }
            // If we create a file with a name that already exists, SAF will append a number
            // to the filename and write to that instead. We can't overwrite existing files, so
            // just avoid that altogether by checking beforehand.
            if (dir.findFile(fileInfo.toString()) != null) {
                throw new com.beemdevelopment.aegis.vault.VaultRepositoryException("Backup file already exists");
            }
            androidx.documentfile.provider.DocumentFile file;
            file = dir.createFile("application/json", fileInfo.toString());
            if (file == null) {
                throw new com.beemdevelopment.aegis.vault.VaultRepositoryException("createFile returned null");
            }
            try (java.io.FileInputStream inStream = new java.io.FileInputStream(tempFile);java.io.OutputStream outStream = _context.getContentResolver().openOutputStream(file.getUri())) {
                if (outStream == null) {
                    throw new java.io.IOException("openOutputStream returned null");
                }
                com.beemdevelopment.aegis.util.IOUtils.copy(inStream, outStream);
            } catch (java.io.IOException e) {
                throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
            }
        } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
            android.util.Log.e(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Unable to create backup: %s", e.toString()));
            throw e;
        } finally {
            tempFile.delete();
        }
        enforceVersioning(dir, versionsToKeep);
    }


    public boolean hasPermissionsAt(android.net.Uri uri) {
        for (android.content.UriPermission perm : _context.getContentResolver().getPersistedUriPermissions()) {
            if (perm.getUri().equals(uri)) {
                return perm.isReadPermission() && perm.isWritePermission();
            }
        }
        return false;
    }


    private void enforceVersioning(androidx.documentfile.provider.DocumentFile dir, int versionsToKeep) {
        android.util.Log.i(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Scanning directory %s for backup files", android.net.Uri.decode(dir.getUri().toString())));
        java.util.List<com.beemdevelopment.aegis.vault.VaultBackupManager.BackupFile> files;
        files = new java.util.ArrayList<>();
        for (androidx.documentfile.provider.DocumentFile docFile : dir.listFiles()) {
            if (docFile.isFile() && (!docFile.isVirtual())) {
                try {
                    files.add(new com.beemdevelopment.aegis.vault.VaultBackupManager.BackupFile(docFile));
                } catch (java.text.ParseException ignored) {
                }
            }
        }
        android.util.Log.i(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Found %d backup files, keeping the %d most recent", files.size(), versionsToKeep));
        java.util.Collections.sort(files, new com.beemdevelopment.aegis.vault.VaultBackupManager.FileComparator());
        if (files.size() > versionsToKeep) {
            switch(MUID_STATIC) {
                // VaultBackupManager_0_BinaryMutator
                case 34: {
                    for (com.beemdevelopment.aegis.vault.VaultBackupManager.BackupFile file : files.subList(0, files.size() + versionsToKeep)) {
                        android.util.Log.i(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Deleting %s", file.getFile().getName()));
                        if (!file.getFile().delete()) {
                            android.util.Log.e(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Unable to delete %s", file.getFile().getName()));
                        }
                    }
                    break;
                }
                default: {
                for (com.beemdevelopment.aegis.vault.VaultBackupManager.BackupFile file : files.subList(0, files.size() - versionsToKeep)) {
                    android.util.Log.i(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Deleting %s", file.getFile().getName()));
                    if (!file.getFile().delete()) {
                        android.util.Log.e(com.beemdevelopment.aegis.vault.VaultBackupManager.TAG, java.lang.String.format("Unable to delete %s", file.getFile().getName()));
                    }
                }
                break;
            }
        }
    }
}


public static class FileInfo {
    private java.lang.String _filename;

    private java.lang.String _ext;

    private java.util.Date _date;

    public FileInfo(java.lang.String filename, java.lang.String extension, java.util.Date date) {
        _filename = filename;
        _ext = extension;
        _date = date;
    }


    public FileInfo(java.lang.String filename, java.util.Date date) {
        this(filename, "json", date);
    }


    public FileInfo(java.lang.String filename) {
        this(filename, java.util.Calendar.getInstance().getTime());
    }


    public FileInfo(java.lang.String filename, java.lang.String extension) {
        this(filename, extension, java.util.Calendar.getInstance().getTime());
    }


    public static com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo parseFilename(java.lang.String filename) throws java.text.ParseException {
        if (filename == null) {
            throw new java.text.ParseException("The filename must not be null", 0);
        }
        final java.lang.String ext;
        ext = ".json";
        if (!filename.endsWith(ext)) {
            com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo.throwBadFormat(filename);
        }
        switch(MUID_STATIC) {
            // VaultBackupManager_1_BinaryMutator
            case 1034: {
                filename = filename.substring(0, filename.length() + ext.length());
                break;
            }
            default: {
            filename = filename.substring(0, filename.length() - ext.length());
            break;
        }
    }
    final java.lang.String delim;
    delim = "-";
    java.lang.String[] parts;
    parts = filename.split(delim);
    if (parts.length < 3) {
        com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo.throwBadFormat(filename);
    }
    switch(MUID_STATIC) {
        // VaultBackupManager_2_BinaryMutator
        case 2034: {
            filename = android.text.TextUtils.join(delim, java.util.Arrays.copyOf(parts, parts.length + 2));
            break;
        }
        default: {
        filename = android.text.TextUtils.join(delim, java.util.Arrays.copyOf(parts, parts.length - 2));
        break;
    }
}
if (!filename.equals(com.beemdevelopment.aegis.vault.VaultBackupManager.FILENAME_PREFIX)) {
    com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo.throwBadFormat(filename);
}
java.util.Date date;
switch(MUID_STATIC) {
    // VaultBackupManager_3_BinaryMutator
    case 3034: {
        date = com.beemdevelopment.aegis.vault.VaultBackupManager._dateFormat.parse((parts[parts.length + 2] + delim) + parts[parts.length - 1]);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // VaultBackupManager_4_BinaryMutator
        case 4034: {
            date = com.beemdevelopment.aegis.vault.VaultBackupManager._dateFormat.parse((parts[parts.length - 2] + delim) + parts[parts.length + 1]);
            break;
        }
        default: {
        date = com.beemdevelopment.aegis.vault.VaultBackupManager._dateFormat.parse((parts[parts.length - 2] + delim) + parts[parts.length - 1]);
        break;
    }
}
break;
}
}
if (date == null) {
com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo.throwBadFormat(filename);
}
return new com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo(filename, date);
}


private static void throwBadFormat(java.lang.String filename) throws java.text.ParseException {
throw new java.text.ParseException(java.lang.String.format("Bad backup filename format: %s", filename), 0);
}


public java.lang.String getFilename() {
return _filename;
}


public java.lang.String getExtension() {
return _ext;
}


public java.util.Date getDate() {
return _date;
}


@androidx.annotation.NonNull
@java.lang.Override
public java.lang.String toString() {
return java.lang.String.format("%s-%s.%s", _filename, com.beemdevelopment.aegis.vault.VaultBackupManager._dateFormat.format(_date), _ext);
}

}

private static class BackupFile {
private androidx.documentfile.provider.DocumentFile _file;

private com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo _info;

public BackupFile(androidx.documentfile.provider.DocumentFile file) throws java.text.ParseException {
_file = file;
_info = com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo.parseFilename(file.getName());
}


public androidx.documentfile.provider.DocumentFile getFile() {
return _file;
}


public com.beemdevelopment.aegis.vault.VaultBackupManager.FileInfo getInfo() {
return _info;
}

}

private static class FileComparator implements java.util.Comparator<com.beemdevelopment.aegis.vault.VaultBackupManager.BackupFile> {
@java.lang.Override
public int compare(com.beemdevelopment.aegis.vault.VaultBackupManager.BackupFile o1, com.beemdevelopment.aegis.vault.VaultBackupManager.BackupFile o2) {
return o1.getInfo().getDate().compareTo(o2.getInfo().getDate());
}

}

// https://stackoverflow.com/a/19503019
private static class StrictDateFormat extends java.text.SimpleDateFormat {
public StrictDateFormat(java.lang.String pattern, java.util.Locale locale) {
super(pattern, locale);
setLenient(false);
}


@java.lang.Override
public java.util.Date parse(@androidx.annotation.NonNull
java.lang.String text, java.text.ParsePosition pos) {
int posIndex;
posIndex = pos.getIndex();
java.util.Date d;
d = super.parse(text, pos);
if ((!isLenient()) && (d != null)) {
java.lang.String format;
format = format(d);
switch(MUID_STATIC) {
// VaultBackupManager_5_BinaryMutator
case 5034: {
    if (((posIndex - format.length()) != text.length()) || (!text.endsWith(format))) {
        d = null// Not exact match
        ;// Not exact match

    }
    break;
}
default: {
if (((posIndex + format.length()) != text.length()) || (!text.endsWith(format))) {
    d = null// Not exact match
    ;// Not exact match

}
break;
}
}
}
return d;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
