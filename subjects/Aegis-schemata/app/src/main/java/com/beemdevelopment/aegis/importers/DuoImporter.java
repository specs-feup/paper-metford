package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import android.content.pm.PackageManager.NameNotFoundException;
import org.json.JSONException;
import java.io.InputStream;
import java.io.IOException;
import com.topjohnwu.superuser.io.SuFile;
import org.json.JSONArray;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.otp.TotpInfo;
import static java.nio.charset.StandardCharsets.UTF_8;
import com.beemdevelopment.aegis.otp.HotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.util.IOUtils;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import org.json.JSONObject;
import com.beemdevelopment.aegis.encoding.Base32;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DuoImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _pkgName = "com.duosecurity.duomobile";

    private static final java.lang.String _subPath = "files/duokit/accounts.json";

    public DuoImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    @androidx.annotation.NonNull
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws com.beemdevelopment.aegis.importers.DatabaseImporterException, android.content.pm.PackageManager.NameNotFoundException {
        return getAppPath(com.beemdevelopment.aegis.importers.DuoImporter._pkgName, com.beemdevelopment.aegis.importers.DuoImporter._subPath);
    }


    @java.lang.Override
    @androidx.annotation.NonNull
    protected com.beemdevelopment.aegis.importers.DatabaseImporter.State read(@androidx.annotation.NonNull
    java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            java.lang.String contents;
            contents = new java.lang.String(com.beemdevelopment.aegis.util.IOUtils.readAll(stream), java.nio.charset.StandardCharsets.UTF_8);
            return new com.beemdevelopment.aegis.importers.DuoImporter.DecryptedState(new org.json.JSONArray(contents));
        } catch (org.json.JSONException | java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    public static class DecryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private final org.json.JSONArray _array;

        public DecryptedState(@androidx.annotation.NonNull
        org.json.JSONArray array) {
            super(false);
            _array = array;
        }


        @java.lang.Override
        @androidx.annotation.NonNull
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            try {
                for (int i = 0; i < _array.length(); i++) {
                    org.json.JSONObject entry;
                    entry = _array.getJSONObject(i);
                    try {
                        result.addEntry(com.beemdevelopment.aegis.importers.DuoImporter.DecryptedState.convertEntry(entry));
                    } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                        result.addError(e);
                    }
                }
            } catch (org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
            return result;
        }


        @androidx.annotation.NonNull
        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(@androidx.annotation.NonNull
        org.json.JSONObject entry) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                java.lang.String label;
                label = entry.optString("name");
                org.json.JSONObject otpData;
                otpData = entry.getJSONObject("otpGenerator");
                byte[] secret;
                secret = com.beemdevelopment.aegis.encoding.Base32.decode(otpData.getString("otpSecret"));
                java.lang.Long counter;
                counter = (otpData.has("counter")) ? otpData.getLong("counter") : null;
                com.beemdevelopment.aegis.otp.OtpInfo otp;
                otp = (counter == null) ? new com.beemdevelopment.aegis.otp.TotpInfo(secret) : new com.beemdevelopment.aegis.otp.HotpInfo(secret, counter);
                return new com.beemdevelopment.aegis.vault.VaultEntry(otp, label, "");
            } catch (org.json.JSONException | com.beemdevelopment.aegis.otp.OtpInfoException | com.beemdevelopment.aegis.encoding.EncodingException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, entry.toString());
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
