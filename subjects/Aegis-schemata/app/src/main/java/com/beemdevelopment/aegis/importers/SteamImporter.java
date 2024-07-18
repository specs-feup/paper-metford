package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import org.json.JSONException;
import android.content.pm.PackageInfo;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import com.beemdevelopment.aegis.otp.SteamInfo;
import com.topjohnwu.superuser.io.SuFile;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.util.IOUtils;
import com.beemdevelopment.aegis.vault.VaultEntry;
import android.content.pm.PackageManager;
import org.json.JSONObject;
import com.beemdevelopment.aegis.encoding.Base64;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SteamImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _subDir = "files";

    private static final java.lang.String _pkgName = "com.valvesoftware.android.steam.community";

    public SteamImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws com.beemdevelopment.aegis.importers.DatabaseImporterException, android.content.pm.PackageManager.NameNotFoundException {
        // NOTE: this assumes that a global root shell has already been obtained by the caller
        com.topjohnwu.superuser.io.SuFile path;
        path = getAppPath(com.beemdevelopment.aegis.importers.SteamImporter._pkgName, com.beemdevelopment.aegis.importers.SteamImporter._subDir);
        com.topjohnwu.superuser.io.SuFile[] files;
        files = path.listFiles((java.io.File d,java.lang.String name) -> name.startsWith("Steamguard-"));
        if ((files == null) || (files.length == 0)) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(java.lang.String.format("Empty directory: %s", path.getAbsolutePath()));
        }
        // TODO: handle multiple files (can this even occur?)
        return files[0];
    }


    @java.lang.Override
    public boolean isInstalledAppVersionSupported() {
        android.content.pm.PackageInfo info;
        try {
            info = requireContext().getPackageManager().getPackageInfo(com.beemdevelopment.aegis.importers.SteamImporter._pkgName, 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
        return info.versionCode < 7460894;
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.SteamImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            byte[] bytes;
            bytes = com.beemdevelopment.aegis.util.IOUtils.readAll(stream);
            org.json.JSONObject obj;
            obj = new org.json.JSONObject(new java.lang.String(bytes, java.nio.charset.StandardCharsets.UTF_8));
            return new com.beemdevelopment.aegis.importers.SteamImporter.State(obj);
        } catch (java.io.IOException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    public static class State extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private org.json.JSONObject _obj;

        private State(org.json.JSONObject obj) {
            super(false);
            _obj = obj;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            try {
                com.beemdevelopment.aegis.vault.VaultEntry entry;
                entry = com.beemdevelopment.aegis.importers.SteamImporter.State.convertEntry(_obj);
                result.addEntry(entry);
            } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                result.addError(e);
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(org.json.JSONObject obj) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                byte[] secret;
                secret = com.beemdevelopment.aegis.encoding.Base64.decode(obj.getString("shared_secret"));
                com.beemdevelopment.aegis.otp.SteamInfo info;
                info = new com.beemdevelopment.aegis.otp.SteamInfo(secret);
                java.lang.String account;
                account = obj.getString("account_name");
                return new com.beemdevelopment.aegis.vault.VaultEntry(info, account, "Steam");
            } catch (org.json.JSONException | com.beemdevelopment.aegis.encoding.EncodingException | com.beemdevelopment.aegis.otp.OtpInfoException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, obj.toString());
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
