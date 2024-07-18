package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.util.UUIDMap;
import java.util.stream.Collectors;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import com.topjohnwu.superuser.io.SuFile;
import java.lang.reflect.InvocationTargetException;
import com.topjohnwu.superuser.io.SuFileInputStream;
import com.beemdevelopment.aegis.R;
import com.topjohnwu.superuser.Shell;
import com.beemdevelopment.aegis.vault.VaultEntry;
import androidx.annotation.StringRes;
import android.content.pm.PackageManager;
import java.util.List;
import java.io.Serializable;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private android.content.Context _context;

    private static java.util.List<com.beemdevelopment.aegis.importers.DatabaseImporter.Definition> _importers;

    static {
        // note: keep these lists sorted alphabetically
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers = new java.util.ArrayList<>();
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("2FAS Authenticator", com.beemdevelopment.aegis.importers.TwoFASImporter.class, com.beemdevelopment.aegis.R.string.importer_help_2fas, false));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Aegis", com.beemdevelopment.aegis.importers.AegisImporter.class, com.beemdevelopment.aegis.R.string.importer_help_aegis, false));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("andOTP", com.beemdevelopment.aegis.importers.AndOtpImporter.class, com.beemdevelopment.aegis.R.string.importer_help_andotp, false));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Authenticator Plus", com.beemdevelopment.aegis.importers.AuthenticatorPlusImporter.class, com.beemdevelopment.aegis.R.string.importer_help_authenticator_plus, false));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Authy", com.beemdevelopment.aegis.importers.AuthyImporter.class, com.beemdevelopment.aegis.R.string.importer_help_authy, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Battle.net Authenticator", com.beemdevelopment.aegis.importers.BattleNetImporter.class, com.beemdevelopment.aegis.R.string.importer_help_battle_net_authenticator, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Bitwarden", com.beemdevelopment.aegis.importers.BitwardenImporter.class, com.beemdevelopment.aegis.R.string.importer_help_bitwarden, false));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Duo", com.beemdevelopment.aegis.importers.DuoImporter.class, com.beemdevelopment.aegis.R.string.importer_help_duo, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("FreeOTP", com.beemdevelopment.aegis.importers.FreeOtpImporter.class, com.beemdevelopment.aegis.R.string.importer_help_freeotp, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("FreeOTP+", com.beemdevelopment.aegis.importers.FreeOtpPlusImporter.class, com.beemdevelopment.aegis.R.string.importer_help_freeotp_plus, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Google Authenticator", com.beemdevelopment.aegis.importers.GoogleAuthImporter.class, com.beemdevelopment.aegis.R.string.importer_help_google_authenticator, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Microsoft Authenticator", com.beemdevelopment.aegis.importers.MicrosoftAuthImporter.class, com.beemdevelopment.aegis.R.string.importer_help_microsoft_authenticator, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Plain text", com.beemdevelopment.aegis.importers.GoogleAuthUriImporter.class, com.beemdevelopment.aegis.R.string.importer_help_plain_text, false));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("Steam", com.beemdevelopment.aegis.importers.SteamImporter.class, com.beemdevelopment.aegis.R.string.importer_help_steam, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("TOTP Authenticator", com.beemdevelopment.aegis.importers.TotpAuthenticatorImporter.class, com.beemdevelopment.aegis.R.string.importer_help_totp_authenticator, true));
        com.beemdevelopment.aegis.importers.DatabaseImporter._importers.add(new com.beemdevelopment.aegis.importers.DatabaseImporter.Definition("WinAuth", com.beemdevelopment.aegis.importers.WinAuthImporter.class, com.beemdevelopment.aegis.R.string.importer_help_winauth, false));
    }

    public DatabaseImporter(android.content.Context context) {
        _context = context;
    }


    protected android.content.Context requireContext() {
        return _context;
    }


    protected abstract com.topjohnwu.superuser.io.SuFile getAppPath() throws com.beemdevelopment.aegis.importers.DatabaseImporterException, android.content.pm.PackageManager.NameNotFoundException;


    protected com.topjohnwu.superuser.io.SuFile getAppPath(java.lang.String pkgName, java.lang.String subPath) throws android.content.pm.PackageManager.NameNotFoundException {
        android.content.pm.PackageManager man;
        man = requireContext().getPackageManager();
        return new com.topjohnwu.superuser.io.SuFile(man.getApplicationInfo(pkgName, 0).dataDir, subPath);
    }


    public boolean isInstalledAppVersionSupported() {
        return true;
    }


    protected abstract com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException;


    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        return read(stream, false);
    }


    public com.beemdevelopment.aegis.importers.DatabaseImporter.State readFromApp(com.topjohnwu.superuser.Shell shell) throws android.content.pm.PackageManager.NameNotFoundException, com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.topjohnwu.superuser.io.SuFile file;
        file = getAppPath();
        file.setShell(shell);
        try (java.io.InputStream stream = com.topjohnwu.superuser.io.SuFileInputStream.open(file)) {
            return read(stream, true);
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    public static com.beemdevelopment.aegis.importers.DatabaseImporter create(android.content.Context context, java.lang.Class<? extends com.beemdevelopment.aegis.importers.DatabaseImporter> type) {
        try {
            return type.getConstructor(android.content.Context.class).newInstance(context);
        } catch (java.lang.IllegalAccessException | java.lang.InstantiationException | java.lang.NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public static java.util.List<com.beemdevelopment.aegis.importers.DatabaseImporter.Definition> getImporters(boolean isDirect) {
        if (isDirect) {
            return java.util.Collections.unmodifiableList(com.beemdevelopment.aegis.importers.DatabaseImporter._importers.stream().filter(com.beemdevelopment.aegis.importers.DatabaseImporter.Definition::supportsDirect).collect(java.util.stream.Collectors.toList()));
        }
        return java.util.Collections.unmodifiableList(com.beemdevelopment.aegis.importers.DatabaseImporter._importers);
    }


    public static class Definition implements java.io.Serializable {
        private final java.lang.String _name;

        private final java.lang.Class<? extends com.beemdevelopment.aegis.importers.DatabaseImporter> _type;

        @androidx.annotation.StringRes
        private final int _help;

        private final boolean _supportsDirect;

        /**
         *
         * @param name
         * 		The name of the Authenticator the importer handles.
         * @param type
         * 		The class which does the importing.
         * @param help
         * 		The string that explains the type of file needed (and optionally where it can be obtained).
         * @param supportsDirect
         * 		Whether the importer can directly import the entries from the app's internal storage using root access.
         */
        public Definition(java.lang.String name, java.lang.Class<? extends com.beemdevelopment.aegis.importers.DatabaseImporter> type, @androidx.annotation.StringRes
        int help, boolean supportsDirect) {
            _name = name;
            _type = type;
            _help = help;
            _supportsDirect = supportsDirect;
        }


        public java.lang.String getName() {
            return _name;
        }


        public java.lang.Class<? extends com.beemdevelopment.aegis.importers.DatabaseImporter> getType() {
            return _type;
        }


        @androidx.annotation.StringRes
        public int getHelp() {
            return _help;
        }


        public boolean supportsDirect() {
            return _supportsDirect;
        }

    }

    public static abstract class State {
        private boolean _encrypted;

        public State(boolean encrypted) {
            _encrypted = encrypted;
        }


        public boolean isEncrypted() {
            return _encrypted;
        }


        public void decrypt(android.content.Context context, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            if (!_encrypted) {
                throw new java.lang.RuntimeException("Attempted to decrypt a plain text database");
            }
            throw new java.lang.UnsupportedOperationException();
        }


        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            if (_encrypted) {
                throw new java.lang.RuntimeException("Attempted to convert database before decrypting it");
            }
            throw new java.lang.UnsupportedOperationException();
        }

    }

    public static class Result {
        private com.beemdevelopment.aegis.util.UUIDMap<com.beemdevelopment.aegis.vault.VaultEntry> _entries = new com.beemdevelopment.aegis.util.UUIDMap<>();

        private java.util.List<com.beemdevelopment.aegis.importers.DatabaseImporterEntryException> _errors = new java.util.ArrayList<>();

        public void addEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
            _entries.add(entry);
        }


        public void addError(com.beemdevelopment.aegis.importers.DatabaseImporterEntryException error) {
            _errors.add(error);
        }


        public com.beemdevelopment.aegis.util.UUIDMap<com.beemdevelopment.aegis.vault.VaultEntry> getEntries() {
            return _entries;
        }


        public java.util.List<com.beemdevelopment.aegis.importers.DatabaseImporterEntryException> getErrors() {
            return _errors;
        }

    }

    public static abstract class DecryptListener {
        protected abstract void onStateDecrypted(com.beemdevelopment.aegis.importers.DatabaseImporter.State state);


        protected abstract void onError(java.lang.Exception e);


        protected abstract void onCanceled();

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
