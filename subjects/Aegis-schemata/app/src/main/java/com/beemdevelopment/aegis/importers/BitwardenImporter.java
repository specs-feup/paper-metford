package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import org.json.JSONException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.IOException;
import com.beemdevelopment.aegis.otp.SteamInfo;
import java.net.URISyntaxException;
import com.topjohnwu.superuser.io.SuFile;
import org.json.JSONArray;
import android.net.Uri;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.util.IOUtils;
import com.beemdevelopment.aegis.vault.VaultEntry;
import com.beemdevelopment.aegis.otp.GoogleAuthInfoException;
import org.simpleflatmapper.csv.CsvParser;
import java.util.Iterator;
import java.util.Objects;
import org.simpleflatmapper.lightningcsv.Row;
import org.json.JSONObject;
import java.util.List;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import com.beemdevelopment.aegis.encoding.Base32;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BitwardenImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    public BitwardenImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() {
        throw new java.lang.UnsupportedOperationException();
    }


    @java.lang.Override
    protected com.beemdevelopment.aegis.importers.BitwardenImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        java.lang.String fileString;
        try {
            fileString = new java.lang.String(com.beemdevelopment.aegis.util.IOUtils.readAll(stream), java.nio.charset.StandardCharsets.UTF_8);
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
        try {
            org.json.JSONObject obj;
            obj = new org.json.JSONObject(fileString);
            org.json.JSONArray array;
            array = obj.getJSONArray("items");
            java.util.List<java.lang.String> entries;
            entries = new java.util.ArrayList<>();
            java.lang.String entry;
            for (int i = 0; i < array.length(); i++) {
                entry = array.getJSONObject(i).getJSONObject("login").getString("totp");
                if (!entry.isEmpty()) {
                    entries.add(entry);
                }
            }
            return new com.beemdevelopment.aegis.importers.BitwardenImporter.State(entries);
        } catch (org.json.JSONException e) {
            try {
                java.util.Iterator<org.simpleflatmapper.lightningcsv.Row> rowIterator;
                rowIterator = org.simpleflatmapper.csv.CsvParser.separator(',').rowIterator(fileString);
                java.util.List<java.lang.String> entries;
                entries = new java.util.ArrayList<>();
                rowIterator.forEachRemaining((org.simpleflatmapper.lightningcsv.Row row) -> {
                    java.lang.String entry;
                    entry = row.get("login_totp");
                    if ((entry != null) && (!entry.isEmpty())) {
                        entries.add(entry);
                    }
                });
                return new com.beemdevelopment.aegis.importers.BitwardenImporter.State(entries);
            } catch (java.io.IOException e2) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e2);
            }
        }
    }


    public static class State extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private final java.util.List<java.lang.String> _entries;

        public State(java.util.List<java.lang.String> entries) {
            super(false);
            _entries = entries;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            for (java.lang.String obj : _entries) {
                try {
                    com.beemdevelopment.aegis.vault.VaultEntry entry;
                    entry = com.beemdevelopment.aegis.importers.BitwardenImporter.State.convertEntry(obj);
                    result.addEntry(entry);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                    result.addError(e);
                }
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(java.lang.String obj) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
                info = com.beemdevelopment.aegis.importers.BitwardenImporter.parseUri(obj);
                return new com.beemdevelopment.aegis.vault.VaultEntry(info);
            } catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException | com.beemdevelopment.aegis.encoding.EncodingException | com.beemdevelopment.aegis.otp.OtpInfoException | java.net.URISyntaxException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, obj);
            }
        }

    }

    private static com.beemdevelopment.aegis.otp.GoogleAuthInfo parseUri(java.lang.String s) throws com.beemdevelopment.aegis.encoding.EncodingException, com.beemdevelopment.aegis.otp.OtpInfoException, java.net.URISyntaxException, com.beemdevelopment.aegis.otp.GoogleAuthInfoException {
        android.net.Uri uri;
        uri = android.net.Uri.parse(s);
        if (java.util.Objects.equals(uri.getScheme(), "steam")) {
            java.lang.String secretString;
            secretString = uri.getAuthority();
            if (secretString == null) {
                throw new com.beemdevelopment.aegis.otp.GoogleAuthInfoException(uri, "Empty secret (empty authority)");
            }
            byte[] secret;
            secret = com.beemdevelopment.aegis.encoding.Base32.decode(secretString);
            return new com.beemdevelopment.aegis.otp.GoogleAuthInfo(new com.beemdevelopment.aegis.otp.SteamInfo(secret), "Steam account", "Steam");
        }
        return com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseUri(uri);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
