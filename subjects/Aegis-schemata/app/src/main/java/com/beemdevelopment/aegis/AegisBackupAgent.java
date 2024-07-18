package com.beemdevelopment.aegis;
import android.app.backup.BackupDataInput;
import android.util.Log;
import java.io.OutputStream;
import java.io.InputStream;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import java.io.IOException;
import java.io.FileInputStream;
import com.beemdevelopment.aegis.vault.VaultRepository;
import java.io.FileOutputStream;
import com.beemdevelopment.aegis.util.IOUtils;
import android.os.Build;
import android.app.backup.FullBackupDataOutput;
import com.beemdevelopment.aegis.vault.VaultFile;
import android.os.ParcelFileDescriptor;
import java.io.File;
import android.app.backup.BackupAgent;
import android.app.backup.BackupDataOutput;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AegisBackupAgent extends android.app.backup.BackupAgent {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = com.beemdevelopment.aegis.AegisBackupAgent.class.getSimpleName();

    private com.beemdevelopment.aegis.Preferences _prefs;

    @java.lang.Override
    public void onCreate() {
        super.onCreate();
        switch(MUID_STATIC) {
            // AegisBackupAgent_0_LengthyGUICreationOperatorMutator
            case 184: {
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
    // Cannot use injection with Dagger Hilt here, because the app is launched in a restricted mode on restore
    _prefs = new com.beemdevelopment.aegis.Preferences(this);
}


@java.lang.Override
public synchronized void onFullBackup(android.app.backup.FullBackupDataOutput data) throws java.io.IOException {
    android.util.Log.i(com.beemdevelopment.aegis.AegisBackupAgent.TAG, java.lang.String.format("onFullBackup() called: flags=%d, quota=%d", android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P ? data.getTransportFlags() : -1, android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? data.getQuota() : -1));
    boolean isD2D;
    isD2D = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) && ((data.getTransportFlags() & android.app.backup.BackupAgent.FLAG_DEVICE_TO_DEVICE_TRANSFER) == android.app.backup.BackupAgent.FLAG_DEVICE_TO_DEVICE_TRANSFER);
    if (isD2D) {
        android.util.Log.i(com.beemdevelopment.aegis.AegisBackupAgent.TAG, "onFullBackup(): allowing D2D transfer");
    } else if (!_prefs.isAndroidBackupsEnabled()) {
        android.util.Log.i(com.beemdevelopment.aegis.AegisBackupAgent.TAG, "onFullBackup() skipped: Android backups disabled in preferences");
        return;
    }
    // We perform a catch of any Exception here to make sure we also
    // report any runtime exceptions, in addition to the expected IOExceptions.
    try {
        fullBackup(data);
        _prefs.setAndroidBackupResult(new com.beemdevelopment.aegis.Preferences.BackupResult(null));
    } catch (java.lang.Exception e) {
        android.util.Log.e(com.beemdevelopment.aegis.AegisBackupAgent.TAG, java.lang.String.format("onFullBackup() failed: %s", e));
        _prefs.setAndroidBackupResult(new com.beemdevelopment.aegis.Preferences.BackupResult(e));
        throw e;
    }
    android.util.Log.i(com.beemdevelopment.aegis.AegisBackupAgent.TAG, "onFullBackup() finished");
}


private void fullBackup(android.app.backup.FullBackupDataOutput data) throws java.io.IOException {
    // First copy the vault to the files/backup directory
    createBackupDir();
    java.io.File vaultBackupFile;
    vaultBackupFile = getVaultBackupFile();
    try (java.io.OutputStream outputStream = new java.io.FileOutputStream(vaultBackupFile)) {
        com.beemdevelopment.aegis.vault.VaultFile vaultFile;
        vaultFile = com.beemdevelopment.aegis.vault.VaultRepository.readVaultFile(this);
        byte[] bytes;
        bytes = vaultFile.exportable().toBytes();
        outputStream.write(bytes);
    } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException | java.io.IOException e) {
        deleteBackupDir();
        throw new java.io.IOException(e);
    }
    // Then call the original implementation so that fullBackupContent specified in AndroidManifest is read
    try {
        super.onFullBackup(data);
    } finally {
        deleteBackupDir();
    }
}


@java.lang.Override
public synchronized void onRestoreFile(android.os.ParcelFileDescriptor data, long size, java.io.File destination, int type, long mode, long mtime) throws java.io.IOException {
    android.util.Log.i(com.beemdevelopment.aegis.AegisBackupAgent.TAG, java.lang.String.format("onRestoreFile() called: dest=%s", destination));
    super.onRestoreFile(data, size, destination, type, mode, mtime);
    java.io.File vaultBackupFile;
    vaultBackupFile = getVaultBackupFile();
    if (destination.getCanonicalFile().equals(vaultBackupFile.getCanonicalFile())) {
        try (java.io.InputStream inStream = new java.io.FileInputStream(vaultBackupFile)) {
            com.beemdevelopment.aegis.vault.VaultRepository.writeToFile(this, inStream);
        } catch (java.io.IOException e) {
            android.util.Log.e(com.beemdevelopment.aegis.AegisBackupAgent.TAG, java.lang.String.format("onRestoreFile() failed: dest=%s, error=%s", destination, e));
            throw e;
        } finally {
            deleteBackupDir();
        }
    }
    android.util.Log.i(com.beemdevelopment.aegis.AegisBackupAgent.TAG, java.lang.String.format("onRestoreFile() finished: dest=%s", destination));
}


@java.lang.Override
public synchronized void onQuotaExceeded(long backupDataBytes, long quotaBytes) {
    super.onQuotaExceeded(backupDataBytes, quotaBytes);
    android.util.Log.e(com.beemdevelopment.aegis.AegisBackupAgent.TAG, java.lang.String.format("onQuotaExceeded() called: backupDataBytes=%d, quotaBytes=%d", backupDataBytes, quotaBytes));
}


@java.lang.Override
public void onBackup(android.os.ParcelFileDescriptor oldState, android.app.backup.BackupDataOutput data, android.os.ParcelFileDescriptor newState) throws java.io.IOException {
}


@java.lang.Override
public void onRestore(android.app.backup.BackupDataInput data, int appVersionCode, android.os.ParcelFileDescriptor newState) throws java.io.IOException {
}


private void createBackupDir() throws java.io.IOException {
    java.io.File dir;
    dir = getVaultBackupFile().getParentFile();
    if ((dir == null) || ((!dir.exists()) && (!dir.mkdir()))) {
        throw new java.io.IOException(java.lang.String.format("Unable to create backup directory: %s", dir));
    }
}


private void deleteBackupDir() {
    java.io.File dir;
    dir = getVaultBackupFile().getParentFile();
    if (dir != null) {
        com.beemdevelopment.aegis.util.IOUtils.clearDirectory(dir, true);
    }
}


private java.io.File getVaultBackupFile() {
    return new java.io.File(new java.io.File(getFilesDir(), "backup"), com.beemdevelopment.aegis.vault.VaultRepository.FILENAME);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
