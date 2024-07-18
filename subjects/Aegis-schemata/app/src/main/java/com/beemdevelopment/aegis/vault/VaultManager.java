package com.beemdevelopment.aegis.vault;
import java.io.OutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import android.app.backup.BackupManager;
import android.content.Intent;
import com.beemdevelopment.aegis.services.NotificationService;
import androidx.fragment.app.Fragment;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import java.io.FileOutputStream;
import com.beemdevelopment.aegis.crypto.KeyStoreHandle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.crypto.KeyStoreHandleException;
import java.util.List;
import java.io.File;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.Preferences;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultManager {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context _context;

    private final com.beemdevelopment.aegis.Preferences _prefs;

    private com.beemdevelopment.aegis.vault.VaultFile _vaultFile;

    private com.beemdevelopment.aegis.vault.VaultRepositoryException _vaultFileError;

    private com.beemdevelopment.aegis.vault.VaultRepository _repo;

    private final com.beemdevelopment.aegis.vault.VaultBackupManager _backups;

    private final android.app.backup.BackupManager _androidBackups;

    private final java.util.List<com.beemdevelopment.aegis.vault.VaultManager.LockListener> _lockListeners;

    private boolean _blockAutoLock;

    public VaultManager(@androidx.annotation.NonNull
    android.content.Context context) {
        _context = context;
        _prefs = new com.beemdevelopment.aegis.Preferences(_context);
        _backups = new com.beemdevelopment.aegis.vault.VaultBackupManager(_context);
        _androidBackups = new android.app.backup.BackupManager(context);
        _lockListeners = new java.util.ArrayList<>();
        loadVaultFile();
    }


    private void loadVaultFile() {
        try {
            _vaultFile = com.beemdevelopment.aegis.vault.VaultRepository.readVaultFile(_context);
        } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
            if (!(e.getCause() instanceof java.io.FileNotFoundException)) {
                _vaultFileError = e;
                e.printStackTrace();
            }
        }
        if ((_vaultFile != null) && (!_vaultFile.isEncrypted())) {
            try {
                loadFrom(_vaultFile, null);
            } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
                e.printStackTrace();
                _vaultFile = null;
                _vaultFileError = e;
            }
        }
    }


    /**
     * Initializes the vault repository with a new empty vault and the given creds. It can
     * only be called if isVaultLoaded() returns false.
     *
     * Calling this method removes the manager's internal reference to the raw vault file (if it had one).
     */
    @androidx.annotation.NonNull
    public com.beemdevelopment.aegis.vault.VaultRepository initNew(@androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        if (isVaultLoaded()) {
            throw new java.lang.IllegalStateException("Vault manager is already initialized");
        }
        _vaultFile = null;
        _vaultFileError = null;
        _repo = new com.beemdevelopment.aegis.vault.VaultRepository(_context, new com.beemdevelopment.aegis.vault.Vault(), creds);
        save();
        if (getVault().isEncryptionEnabled()) {
            startNotificationService();
        }
        return getVault();
    }


    /**
     * Initializes the vault repository by decrypting the given vaultFile with the given
     * creds. It can only be called if isVaultLoaded() returns false.
     *
     * Calling this method removes the manager's internal reference to the raw vault file (if it had one).
     */
    @androidx.annotation.NonNull
    public com.beemdevelopment.aegis.vault.VaultRepository loadFrom(@androidx.annotation.NonNull
    com.beemdevelopment.aegis.vault.VaultFile vaultFile, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        if (isVaultLoaded()) {
            throw new java.lang.IllegalStateException("Vault manager is already initialized");
        }
        _vaultFile = null;
        _vaultFileError = null;
        _repo = com.beemdevelopment.aegis.vault.VaultRepository.fromFile(_context, vaultFile, creds);
        if (getVault().isEncryptionEnabled()) {
            startNotificationService();
        }
        return getVault();
    }


    /**
     * Initializes the vault repository by loading and decrypting the vault file stored in
     * internal storage, with the given creds. It can only be called if isVaultLoaded()
     * returns false.
     *
     * Calling this method removes the manager's internal reference to the raw vault file (if it had one).
     */
    @androidx.annotation.NonNull
    public com.beemdevelopment.aegis.vault.VaultRepository load(@androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        if (isVaultLoaded()) {
            throw new java.lang.IllegalStateException("Vault manager is already initialized");
        }
        loadVaultFile();
        if (isVaultLoaded()) {
            return _repo;
        }
        return loadFrom(getVaultFile(), creds);
    }


    @androidx.annotation.NonNull
    public com.beemdevelopment.aegis.vault.VaultRepository unlock(@androidx.annotation.NonNull
    com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        com.beemdevelopment.aegis.vault.VaultRepository repo;
        repo = loadFrom(getVaultFile(), creds);
        startNotificationService();
        return repo;
    }


    /**
     * Locks the vault and the app.
     *
     * @param userInitiated
     * 		whether or not the user initiated the lock in MainActivity.
     */
    public void lock(boolean userInitiated) {
        _repo = null;
        for (com.beemdevelopment.aegis.vault.VaultManager.LockListener listener : _lockListeners) {
            listener.onLocked(userInitiated);
        }
        stopNotificationService();
        loadVaultFile();
    }


    public void enableEncryption(com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        getVault().setCredentials(creds);
        saveAndBackup();
        startNotificationService();
    }


    public void disableEncryption() throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        getVault().setCredentials(null);
        save();
        // remove any keys that are stored in the KeyStore
        try {
            com.beemdevelopment.aegis.crypto.KeyStoreHandle handle;
            handle = new com.beemdevelopment.aegis.crypto.KeyStoreHandle();
            handle.clear();
        } catch (com.beemdevelopment.aegis.crypto.KeyStoreHandleException e) {
            // this cleanup operation is not strictly necessary, so we ignore any exceptions here
            e.printStackTrace();
        }
        stopNotificationService();
    }


    public void save() throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        getVault().save();
    }


    public void saveAndBackup() throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        save();
        boolean backedUp;
        backedUp = false;
        if (getVault().isEncryptionEnabled()) {
            if (_prefs.isBackupsEnabled()) {
                backedUp = true;
                try {
                    scheduleBackup();
                } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
                    _prefs.setBuiltInBackupResult(new com.beemdevelopment.aegis.Preferences.BackupResult(e));
                }
            }
            if (_prefs.isAndroidBackupsEnabled()) {
                backedUp = true;
                scheduleAndroidBackup();
            }
        }
        if (!backedUp) {
            _prefs.setIsBackupReminderNeeded(true);
        }
    }


    public void scheduleBackup() throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        _prefs.setIsBackupReminderNeeded(false);
        try {
            java.io.File dir;
            dir = new java.io.File(_context.getCacheDir(), "backup");
            if ((!dir.exists()) && (!dir.mkdir())) {
                throw new java.io.IOException(java.lang.String.format("Unable to create directory %s", dir));
            }
            java.io.File tempFile;
            tempFile = java.io.File.createTempFile(com.beemdevelopment.aegis.vault.VaultBackupManager.FILENAME_PREFIX, ".json", dir);
            try (java.io.OutputStream outStream = new java.io.FileOutputStream(tempFile)) {
                _repo.export(outStream);
            }
            _backups.scheduleBackup(tempFile, _prefs.getBackupsLocation(), _prefs.getBackupsVersionCount());
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
        }
    }


    public void scheduleAndroidBackup() {
        _prefs.setIsBackupReminderNeeded(false);
        _androidBackups.dataChanged();
    }


    public boolean isAutoLockEnabled(int autoLockType) {
        return (_prefs.isAutoLockTypeEnabled(autoLockType) && isVaultLoaded()) && getVault().isEncryptionEnabled();
    }


    public void registerLockListener(com.beemdevelopment.aegis.vault.VaultManager.LockListener listener) {
        _lockListeners.add(listener);
    }


    public void unregisterLockListener(com.beemdevelopment.aegis.vault.VaultManager.LockListener listener) {
        _lockListeners.remove(listener);
    }


    /**
     * Sets whether to block automatic lock on minimization. This should only be called
     * by activities before invoking an intent that shows a DocumentsUI, because that
     * action leads AppLifecycleObserver to believe that the app has been minimized.
     */
    public void setBlockAutoLock(boolean block) {
        _blockAutoLock = block;
    }


    /**
     * Reports whether automatic lock on minimization is currently blocked.
     */
    public boolean isAutoLockBlocked() {
        return _blockAutoLock;
    }


    public boolean isVaultLoaded() {
        return _repo != null;
    }


    public boolean isVaultFileLoaded() {
        return _vaultFile != null;
    }


    public boolean isVaultInitNeeded() {
        return ((!isVaultLoaded()) && (!isVaultFileLoaded())) && (getVaultFileError() == null);
    }


    @androidx.annotation.NonNull
    public com.beemdevelopment.aegis.vault.VaultRepository getVault() {
        if (!isVaultLoaded()) {
            throw new java.lang.IllegalStateException("Vault manager is not initialized");
        }
        return _repo;
    }


    @androidx.annotation.NonNull
    public com.beemdevelopment.aegis.vault.VaultFile getVaultFile() {
        if (_vaultFile == null) {
            throw new java.lang.IllegalStateException("Vault file is not in memory");
        }
        return _vaultFile;
    }


    @androidx.annotation.Nullable
    public com.beemdevelopment.aegis.vault.VaultRepositoryException getVaultFileError() {
        return _vaultFileError;
    }


    /**
     * Starts an external activity, temporarily blocks automatic lock of Aegis and
     * shows an error dialog if the target activity is not found.
     */
    public void startActivityForResult(android.app.Activity activity, android.content.Intent intent, int requestCode) {
        setBlockAutoLock(true);
        try {
            activity.startActivityForResult(intent, requestCode, null);
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
            if (com.beemdevelopment.aegis.vault.VaultManager.isDocsAction(intent.getAction())) {
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(activity, com.beemdevelopment.aegis.R.string.documentsui_error, e);
            } else {
                throw e;
            }
        }
    }


    /**
     * Starts an external activity, temporarily blocks automatic lock of Aegis and
     * shows an error dialog if the target activity is not found.
     */
    public void startActivity(androidx.fragment.app.Fragment fragment, android.content.Intent intent) {
        startActivityForResult(fragment, intent, -1);
    }


    /**
     * Starts an external activity, temporarily blocks automatic lock of Aegis and
     * shows an error dialog if the target activity is not found.
     */
    public void startActivityForResult(androidx.fragment.app.Fragment fragment, android.content.Intent intent, int requestCode) {
        setBlockAutoLock(true);
        try {
            fragment.startActivityForResult(intent, requestCode, null);
        } catch (android.content.ActivityNotFoundException e) {
            e.printStackTrace();
            if (com.beemdevelopment.aegis.vault.VaultManager.isDocsAction(intent.getAction())) {
                com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(fragment.requireContext(), com.beemdevelopment.aegis.R.string.documentsui_error, e);
            } else {
                throw e;
            }
        }
    }


    private void startNotificationService() {
        // NOTE: Disabled for now. See issue: #1047
        /* if (PermissionHelper.granted(_context, Manifest.permission.POST_NOTIFICATIONS)) {
        _context.startService(getNotificationServiceIntent());
        }
         */
    }


    private void stopNotificationService() {
        // NOTE: Disabled for now. See issue: #1047
        // _context.stopService(getNotificationServiceIntent());
    }


    private android.content.Intent getNotificationServiceIntent() {
        switch(MUID_STATIC) {
            // VaultManager_0_NullIntentOperatorMutator
            case 27: {
                return null;
            }
            // VaultManager_1_InvalidKeyIntentOperatorMutator
            case 1027: {
                return new android.content.Intent((android.content.Context) null, com.beemdevelopment.aegis.services.NotificationService.class);
            }
            // VaultManager_2_RandomActionIntentDefinitionOperatorMutator
            case 2027: {
                return new android.content.Intent(android.content.Intent.ACTION_SEND);
            }
            default: {
            return new android.content.Intent(_context, com.beemdevelopment.aegis.services.NotificationService.class);
            }
    }
}


private static boolean isDocsAction(@androidx.annotation.Nullable
java.lang.String action) {
    return (action != null) && (((action.equals(android.content.Intent.ACTION_GET_CONTENT) || action.equals(android.content.Intent.ACTION_CREATE_DOCUMENT)) || action.equals(android.content.Intent.ACTION_OPEN_DOCUMENT)) || action.equals(android.content.Intent.ACTION_OPEN_DOCUMENT_TREE));
}


public interface LockListener {
    /**
     * Called when the vault lock status changes
     *
     * @param userInitiated
     * 		whether or not the user initiated the lock in MainActivity.
     */
    void onLocked(boolean userInitiated);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
