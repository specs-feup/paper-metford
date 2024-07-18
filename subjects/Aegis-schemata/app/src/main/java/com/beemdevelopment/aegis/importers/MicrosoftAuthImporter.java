package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import java.io.InputStream;
import com.topjohnwu.superuser.io.SuFile;
import android.database.Cursor;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.topjohnwu.superuser.Shell;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.vault.VaultEntry;
import android.content.pm.PackageManager;
import java.util.List;
import com.beemdevelopment.aegis.encoding.Base64;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MicrosoftAuthImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _subPath = "databases/PhoneFactor";

    private static final java.lang.String _pkgName = "com.azure.authenticator";

    private static final int TYPE_TOTP = 0;

    private static final int TYPE_MICROSOFT = 1;

    public MicrosoftAuthImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws android.content.pm.PackageManager.NameNotFoundException {
        return getAppPath(com.beemdevelopment.aegis.importers.MicrosoftAuthImporter._pkgName, com.beemdevelopment.aegis.importers.MicrosoftAuthImporter._subPath);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.beemdevelopment.aegis.importers.SqlImporterHelper helper;
        helper = new com.beemdevelopment.aegis.importers.SqlImporterHelper(requireContext());
        java.util.List<com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry> entries;
        entries = helper.read(com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry.class, stream, "accounts");
        return new com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.State(entries);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State readFromApp(com.topjohnwu.superuser.Shell shell) throws android.content.pm.PackageManager.NameNotFoundException, com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.topjohnwu.superuser.io.SuFile path;
        path = getAppPath();
        path.setShell(shell);
        com.beemdevelopment.aegis.importers.SqlImporterHelper helper;
        helper = new com.beemdevelopment.aegis.importers.SqlImporterHelper(requireContext());
        java.util.List<com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry> entries;
        entries = helper.read(com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry.class, path, "accounts");
        return new com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.State(entries);
    }


    public static class State extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private java.util.List<com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry> _entries;

        private State(java.util.List<com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry> entries) {
            super(false);
            _entries = entries;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            for (com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry sqlEntry : _entries) {
                try {
                    int type;
                    type = sqlEntry.getType();
                    if ((type == com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.TYPE_TOTP) || (type == com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.TYPE_MICROSOFT)) {
                        com.beemdevelopment.aegis.vault.VaultEntry entry;
                        entry = com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.State.convertEntry(sqlEntry);
                        result.addEntry(entry);
                    }
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                    result.addError(e);
                }
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.Entry entry) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                byte[] secret;
                int digits;
                digits = 6;
                switch (entry.getType()) {
                    case com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.TYPE_TOTP :
                        secret = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseSecret(entry.getSecret());
                        break;
                    case com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.TYPE_MICROSOFT :
                        digits = 8;
                        secret = com.beemdevelopment.aegis.encoding.Base64.decode(entry.getSecret());
                        break;
                    default :
                        throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(java.lang.String.format("Unsupported OTP type: %d", entry.getType()), entry.toString());
                }
                com.beemdevelopment.aegis.otp.OtpInfo info;
                info = new com.beemdevelopment.aegis.otp.TotpInfo(secret, com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, digits, com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
                return new com.beemdevelopment.aegis.vault.VaultEntry(info, entry.getUserName(), entry.getIssuer());
            } catch (com.beemdevelopment.aegis.encoding.EncodingException | com.beemdevelopment.aegis.otp.OtpInfoException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, entry.toString());
            }
        }

    }

    private static class Entry extends com.beemdevelopment.aegis.importers.SqlImporterHelper.Entry {
        private int _type;

        private java.lang.String _secret;

        private java.lang.String _issuer;

        private java.lang.String _userName;

        public Entry(android.database.Cursor cursor) {
            super(cursor);
            _type = com.beemdevelopment.aegis.importers.SqlImporterHelper.getInt(cursor, "account_type");
            _secret = com.beemdevelopment.aegis.importers.SqlImporterHelper.getString(cursor, "oath_secret_key");
            _issuer = com.beemdevelopment.aegis.importers.SqlImporterHelper.getString(cursor, "name");
            _userName = com.beemdevelopment.aegis.importers.SqlImporterHelper.getString(cursor, "username");
        }


        public int getType() {
            return _type;
        }


        public java.lang.String getSecret() {
            return _secret;
        }


        public java.lang.String getIssuer() {
            return _issuer;
        }


        public java.lang.String getUserName() {
            return _userName;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
