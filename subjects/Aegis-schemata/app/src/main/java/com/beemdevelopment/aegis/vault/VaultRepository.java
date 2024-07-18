package com.beemdevelopment.aegis.vault;
import java.util.stream.Collectors;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.text.Collator;
import java.io.FileOutputStream;
import com.beemdevelopment.aegis.util.IOUtils;
import androidx.annotation.NonNull;
import java.io.ByteArrayInputStream;
import java.util.TreeSet;
import java.util.Collection;
import androidx.core.util.AtomicFile;
import org.json.JSONObject;
import java.util.UUID;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import java.io.File;
import androidx.annotation.Nullable;
import android.content.Context;
import com.google.zxing.WriterException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class VaultRepository {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String FILENAME = "aegis.json";

    public static final java.lang.String FILENAME_PREFIX_EXPORT = "aegis-export";

    public static final java.lang.String FILENAME_PREFIX_EXPORT_PLAIN = "aegis-export-plain";

    public static final java.lang.String FILENAME_PREFIX_EXPORT_URI = "aegis-export-uri";

    public static final java.lang.String FILENAME_PREFIX_EXPORT_HTML = "aegis-export-html";

    @androidx.annotation.NonNull
    private final com.beemdevelopment.aegis.vault.Vault _vault;

    @androidx.annotation.Nullable
    private com.beemdevelopment.aegis.vault.VaultFileCredentials _creds;

    @androidx.annotation.NonNull
    private final android.content.Context _context;

    public VaultRepository(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    com.beemdevelopment.aegis.vault.Vault vault, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.VaultFileCredentials creds) {
        _context = context;
        _vault = vault;
        _creds = creds;
    }


    private static androidx.core.util.AtomicFile getAtomicFile(android.content.Context context) {
        return new androidx.core.util.AtomicFile(new java.io.File(context.getFilesDir(), com.beemdevelopment.aegis.vault.VaultRepository.FILENAME));
    }


    public static boolean fileExists(android.content.Context context) {
        java.io.File file;
        file = com.beemdevelopment.aegis.vault.VaultRepository.getAtomicFile(context).getBaseFile();
        return file.exists() && file.isFile();
    }


    public static void deleteFile(android.content.Context context) {
        com.beemdevelopment.aegis.vault.VaultRepository.getAtomicFile(context).delete();
    }


    public static com.beemdevelopment.aegis.vault.VaultFile readVaultFile(android.content.Context context) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        androidx.core.util.AtomicFile file;
        file = com.beemdevelopment.aegis.vault.VaultRepository.getAtomicFile(context);
        try {
            byte[] fileBytes;
            fileBytes = file.readFully();
            return com.beemdevelopment.aegis.vault.VaultFile.fromBytes(fileBytes);
        } catch (java.io.IOException | com.beemdevelopment.aegis.vault.VaultFileException e) {
            throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
        }
    }


    public static void writeToFile(android.content.Context context, java.io.InputStream inStream) throws java.io.IOException {
        androidx.core.util.AtomicFile file;
        file = com.beemdevelopment.aegis.vault.VaultRepository.getAtomicFile(context);
        java.io.FileOutputStream outStream;
        outStream = null;
        try {
            outStream = file.startWrite();
            com.beemdevelopment.aegis.util.IOUtils.copy(inStream, outStream);
            file.finishWrite(outStream);
        } catch (java.io.IOException e) {
            if (outStream != null) {
                file.failWrite(outStream);
            }
            throw e;
        }
    }


    public static com.beemdevelopment.aegis.vault.VaultRepository fromFile(android.content.Context context, com.beemdevelopment.aegis.vault.VaultFile file, com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        if (file.isEncrypted() && (creds == null)) {
            throw new java.lang.IllegalArgumentException("The VaultFile is encrypted but the given VaultFileCredentials is null");
        }
        com.beemdevelopment.aegis.vault.Vault vault;
        try {
            org.json.JSONObject obj;
            if (!file.isEncrypted()) {
                obj = file.getContent();
            } else {
                obj = file.getContent(creds);
            }
            vault = com.beemdevelopment.aegis.vault.Vault.fromJson(obj);
        } catch (com.beemdevelopment.aegis.vault.VaultException | com.beemdevelopment.aegis.vault.VaultFileException e) {
            throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
        }
        return new com.beemdevelopment.aegis.vault.VaultRepository(context, vault, creds);
    }


    void save() throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        try {
            org.json.JSONObject obj;
            obj = _vault.toJson();
            com.beemdevelopment.aegis.vault.VaultFile file;
            file = new com.beemdevelopment.aegis.vault.VaultFile();
            if (isEncryptionEnabled()) {
                file.setContent(obj, _creds);
            } else {
                file.setContent(obj);
            }
            try {
                byte[] bytes;
                bytes = file.toBytes();
                com.beemdevelopment.aegis.vault.VaultRepository.writeToFile(_context, new java.io.ByteArrayInputStream(bytes));
            } catch (java.io.IOException e) {
                throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
            }
        } catch (com.beemdevelopment.aegis.vault.VaultFileException e) {
            throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
        }
    }


    /**
     * Exports the vault by serializing it and writing it to the given OutputStream. If encryption
     * is enabled, the vault will be encrypted automatically.
     */
    public void export(java.io.OutputStream stream) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        export(stream, getCredentials());
    }


    /**
     * Exports the vault by serializing it and writing it to the given OutputStream. If creds is
     * not null, it will be used to encrypt the vault first.
     */
    public void export(java.io.OutputStream stream, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.VaultFileCredentials creds) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        exportFiltered(stream, creds, null);
    }


    /**
     * Exports the vault by serializing it and writing it to the given OutputStream. If encryption
     * is enabled, the vault will be encrypted automatically. If filter is not null only specified
     * entries will be exported
     */
    public void exportFiltered(java.io.OutputStream stream, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.Vault.EntryFilter filter) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        exportFiltered(stream, getCredentials(), filter);
    }


    /**
     * Exports the vault by serializing it and writing it to the given OutputStream. If creds is
     * not null, it will be used to encrypt the vault first. If filter is not null only specified
     * entries will be exported
     */
    public void exportFiltered(java.io.OutputStream stream, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.VaultFileCredentials creds, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.Vault.EntryFilter filter) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        if (creds != null) {
            creds = creds.exportable();
        }
        try {
            com.beemdevelopment.aegis.vault.VaultFile vaultFile;
            vaultFile = new com.beemdevelopment.aegis.vault.VaultFile();
            if (creds != null) {
                vaultFile.setContent(_vault.toJson(filter), creds);
            } else {
                vaultFile.setContent(_vault.toJson(filter));
            }
            byte[] bytes;
            bytes = vaultFile.toBytes();
            stream.write(bytes);
        } catch (java.io.IOException | com.beemdevelopment.aegis.vault.VaultFileException e) {
            throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
        }
    }


    /**
     * Exports the vault by serializing the list of entries to a newline-separated list of
     * Google Authenticator URI's and writing it to the given OutputStream.
     */
    public void exportGoogleUris(java.io.OutputStream outStream, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.Vault.EntryFilter filter) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        try (java.io.PrintStream stream = new java.io.PrintStream(outStream, false, java.nio.charset.StandardCharsets.UTF_8.name())) {
            for (com.beemdevelopment.aegis.vault.VaultEntry entry : getEntries()) {
                if ((filter == null) || filter.includeEntry(entry)) {
                    com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
                    info = new com.beemdevelopment.aegis.otp.GoogleAuthInfo(entry.getInfo(), entry.getName(), entry.getIssuer());
                    stream.println(info.getUri().toString());
                }
            }
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
        }
    }


    /**
     * Exports the vault by serializing the list of entries to an HTML file containing the Issuer,
     * Username and QR Code and writing it to the given OutputStream.
     */
    public void exportHtml(java.io.OutputStream outStream, @androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.Vault.EntryFilter filter) throws com.beemdevelopment.aegis.vault.VaultRepositoryException {
        java.util.Collection<com.beemdevelopment.aegis.vault.VaultEntry> entries;
        entries = getEntries();
        if (filter != null) {
            entries = entries.stream().filter(filter::includeEntry).collect(java.util.stream.Collectors.toList());
        }
        try (java.io.PrintStream ps = new java.io.PrintStream(outStream, false, java.nio.charset.StandardCharsets.UTF_8.name())) {
            com.beemdevelopment.aegis.vault.VaultHtmlExporter.export(_context, ps, entries);
        } catch (com.google.zxing.WriterException | java.io.IOException e) {
            throw new com.beemdevelopment.aegis.vault.VaultRepositoryException(e);
        }
    }


    public void addEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        _vault.getEntries().add(entry);
    }


    public com.beemdevelopment.aegis.vault.VaultEntry getEntryByUUID(java.util.UUID uuid) {
        return _vault.getEntries().getByUUID(uuid);
    }


    public com.beemdevelopment.aegis.vault.VaultEntry removeEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        return _vault.getEntries().remove(entry);
    }


    public void wipeEntries() {
        _vault.getEntries().wipe();
    }


    public com.beemdevelopment.aegis.vault.VaultEntry replaceEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        return _vault.getEntries().replace(entry);
    }


    public void swapEntries(com.beemdevelopment.aegis.vault.VaultEntry entry1, com.beemdevelopment.aegis.vault.VaultEntry entry2) {
        _vault.getEntries().swap(entry1, entry2);
    }


    public boolean isEntryDuplicate(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        return _vault.getEntries().has(entry);
    }


    public java.util.Collection<com.beemdevelopment.aegis.vault.VaultEntry> getEntries() {
        return _vault.getEntries().getValues();
    }


    public java.util.TreeSet<java.lang.String> getGroups() {
        java.util.TreeSet<java.lang.String> groups;
        groups = new java.util.TreeSet<>(java.text.Collator.getInstance());
        for (com.beemdevelopment.aegis.vault.VaultEntry entry : getEntries()) {
            java.lang.String group;
            group = entry.getGroup();
            if (group != null) {
                groups.add(group);
            }
        }
        return groups;
    }


    public com.beemdevelopment.aegis.vault.VaultFileCredentials getCredentials() {
        return _creds == null ? null : _creds.clone();
    }


    public void setCredentials(com.beemdevelopment.aegis.vault.VaultFileCredentials creds) {
        _creds = (creds == null) ? null : creds.clone();
    }


    public boolean isEncryptionEnabled() {
        return _creds != null;
    }


    public boolean isBackupPasswordSet() {
        if (!isEncryptionEnabled()) {
            return false;
        }
        return getCredentials().getSlots().findBackupPasswordSlots().size() > 0;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
