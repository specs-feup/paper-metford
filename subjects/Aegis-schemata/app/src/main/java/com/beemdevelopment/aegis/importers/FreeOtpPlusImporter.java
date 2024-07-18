package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.util.IOUtils;
import org.json.JSONException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.IOException;
import android.content.pm.PackageManager;
import com.topjohnwu.superuser.io.SuFile;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FreeOtpPlusImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _subPath = "shared_prefs/tokens.xml";

    private static final java.lang.String _pkgName = "org.liberty.android.freeotpplus";

    public FreeOtpPlusImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws android.content.pm.PackageManager.NameNotFoundException {
        return getAppPath(com.beemdevelopment.aegis.importers.FreeOtpPlusImporter._pkgName, com.beemdevelopment.aegis.importers.FreeOtpPlusImporter._subPath);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
        if (isInternal) {
            state = new com.beemdevelopment.aegis.importers.FreeOtpImporter(requireContext()).read(stream);
        } else {
            try {
                java.lang.String json;
                json = new java.lang.String(com.beemdevelopment.aegis.util.IOUtils.readAll(stream), java.nio.charset.StandardCharsets.UTF_8);
                org.json.JSONObject obj;
                obj = new org.json.JSONObject(json);
                org.json.JSONArray array;
                array = obj.getJSONArray("tokens");
                java.util.List<org.json.JSONObject> entries;
                entries = new java.util.ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    entries.add(array.getJSONObject(i));
                }
                state = new com.beemdevelopment.aegis.importers.FreeOtpImporter.State(entries);
            } catch (java.io.IOException | org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
        }
        return state;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
