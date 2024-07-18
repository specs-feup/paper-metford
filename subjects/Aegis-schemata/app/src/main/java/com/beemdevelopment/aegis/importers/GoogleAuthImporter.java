package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import android.content.pm.PackageInfo;
import java.io.InputStream;
import com.topjohnwu.superuser.io.SuFile;
import android.database.Cursor;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.topjohnwu.superuser.Shell;
import com.beemdevelopment.aegis.otp.HotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.vault.VaultEntry;
import android.content.pm.PackageManager;
import java.util.List;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GoogleAuthImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final int TYPE_TOTP = 0;

    private static final int TYPE_HOTP = 1;

    private static final java.lang.String _subPath = "databases/databases";

    private static final java.lang.String _pkgName = "com.google.android.apps.authenticator2";

    public GoogleAuthImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws android.content.pm.PackageManager.NameNotFoundException {
        com.topjohnwu.superuser.io.SuFile file;
        file = getAppPath(com.beemdevelopment.aegis.importers.GoogleAuthImporter._pkgName, com.beemdevelopment.aegis.importers.GoogleAuthImporter._subPath);
        return file;
    }


    @java.lang.Override
    public boolean isInstalledAppVersionSupported() {
        android.content.pm.PackageInfo info;
        try {
            info = requireContext().getPackageManager().getPackageInfo(com.beemdevelopment.aegis.importers.GoogleAuthImporter._pkgName, 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
        return info.versionCode <= 5000100;
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.GoogleAuthImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        final android.content.Context context;
        context = requireContext();
        com.beemdevelopment.aegis.importers.SqlImporterHelper helper;
        helper = new com.beemdevelopment.aegis.importers.SqlImporterHelper(context);
        java.util.List<com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry> entries;
        entries = helper.read(com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry.class, stream, "accounts");
        return new com.beemdevelopment.aegis.importers.GoogleAuthImporter.State(entries, context);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State readFromApp(com.topjohnwu.superuser.Shell shell) throws android.content.pm.PackageManager.NameNotFoundException, com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.topjohnwu.superuser.io.SuFile path;
        path = getAppPath();
        path.setShell(shell);
        final android.content.Context context;
        context = requireContext();
        com.beemdevelopment.aegis.importers.SqlImporterHelper helper;
        helper = new com.beemdevelopment.aegis.importers.SqlImporterHelper(context);
        java.util.List<com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry> entries;
        entries = helper.read(com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry.class, path, "accounts");
        return new com.beemdevelopment.aegis.importers.GoogleAuthImporter.State(entries, context);
    }


    public static class State extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private java.util.List<com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry> _entries;

        private android.content.Context _context;

        private State(java.util.List<com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry> entries, android.content.Context context) {
            super(false);
            _entries = entries;
            _context = context;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            for (com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry sqlEntry : _entries) {
                try {
                    com.beemdevelopment.aegis.vault.VaultEntry entry;
                    entry = com.beemdevelopment.aegis.importers.GoogleAuthImporter.State.convertEntry(sqlEntry, _context);
                    result.addEntry(entry);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                    result.addError(e);
                }
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(com.beemdevelopment.aegis.importers.GoogleAuthImporter.Entry entry, android.content.Context context) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                if (entry.isEncrypted()) {
                    throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(context.getString(com.beemdevelopment.aegis.R.string.importer_encrypted_exception_google_authenticator, entry.getEmail()));
                }
                byte[] secret;
                secret = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseSecret(entry.getSecret());
                com.beemdevelopment.aegis.otp.OtpInfo info;
                switch (entry.getType()) {
                    case com.beemdevelopment.aegis.importers.GoogleAuthImporter.TYPE_TOTP :
                        info = new com.beemdevelopment.aegis.otp.TotpInfo(secret);
                        break;
                    case com.beemdevelopment.aegis.importers.GoogleAuthImporter.TYPE_HOTP :
                        info = new com.beemdevelopment.aegis.otp.HotpInfo(secret, entry.getCounter());
                        break;
                    default :
                        throw new com.beemdevelopment.aegis.importers.DatabaseImporterException("unsupported otp type: " + entry.getType());
                }
                java.lang.String name;
                name = entry.getEmail();
                java.lang.String[] parts;
                parts = name.split(":");
                if (parts.length == 2) {
                    name = parts[1];
                }
                return new com.beemdevelopment.aegis.vault.VaultEntry(info, name, entry.getIssuer());
            } catch (com.beemdevelopment.aegis.encoding.EncodingException | com.beemdevelopment.aegis.otp.OtpInfoException | com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, entry.toString());
            }
        }

    }

    private static class Entry extends com.beemdevelopment.aegis.importers.SqlImporterHelper.Entry {
        private int _type;

        private boolean _isEncrypted;

        private java.lang.String _secret;

        private java.lang.String _email;

        private java.lang.String _issuer;

        private long _counter;

        public Entry(android.database.Cursor cursor) {
            super(cursor);
            _type = com.beemdevelopment.aegis.importers.SqlImporterHelper.getInt(cursor, "type");
            _secret = com.beemdevelopment.aegis.importers.SqlImporterHelper.getString(cursor, "secret");
            _email = com.beemdevelopment.aegis.importers.SqlImporterHelper.getString(cursor, "email", "");
            _issuer = com.beemdevelopment.aegis.importers.SqlImporterHelper.getString(cursor, "issuer", "");
            _counter = com.beemdevelopment.aegis.importers.SqlImporterHelper.getLong(cursor, "counter");
            _isEncrypted = (cursor.getColumnIndex("isencrypted") != (-1)) && (com.beemdevelopment.aegis.importers.SqlImporterHelper.getInt(cursor, "isencrypted") > 0);
        }


        public int getType() {
            return _type;
        }


        public boolean isEncrypted() {
            return _isEncrypted;
        }


        public java.lang.String getSecret() {
            return _secret;
        }


        public java.lang.String getEmail() {
            return _email;
        }


        public java.lang.String getIssuer() {
            return _issuer;
        }


        public long getCounter() {
            return _counter;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
