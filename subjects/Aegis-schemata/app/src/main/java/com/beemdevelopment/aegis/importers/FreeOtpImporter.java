package com.beemdevelopment.aegis.importers;
import java.util.Locale;
import org.json.JSONException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import com.beemdevelopment.aegis.otp.SteamInfo;
import com.topjohnwu.superuser.io.SuFile;
import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParser;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.util.PreferenceParser;
import com.beemdevelopment.aegis.otp.HotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.vault.VaultEntry;
import android.util.Xml;
import android.content.pm.PackageManager;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;
import java.util.List;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class FreeOtpImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _subPath = "shared_prefs/tokens.xml";

    private static final java.lang.String _pkgName = "org.fedorahosted.freeotp";

    public FreeOtpImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws android.content.pm.PackageManager.NameNotFoundException {
        return getAppPath(com.beemdevelopment.aegis.importers.FreeOtpImporter._pkgName, com.beemdevelopment.aegis.importers.FreeOtpImporter._subPath);
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.FreeOtpImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            org.xmlpull.v1.XmlPullParser parser;
            parser = android.util.Xml.newPullParser();
            parser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            java.util.List<org.json.JSONObject> entries;
            entries = new java.util.ArrayList<>();
            for (com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry entry : com.beemdevelopment.aegis.util.PreferenceParser.parse(parser)) {
                if (!entry.Name.equals("tokenOrder")) {
                    entries.add(new org.json.JSONObject(entry.Value));
                }
            }
            return new com.beemdevelopment.aegis.importers.FreeOtpImporter.State(entries);
        } catch (org.xmlpull.v1.XmlPullParserException | java.io.IOException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    public static class State extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private java.util.List<org.json.JSONObject> _entries;

        public State(java.util.List<org.json.JSONObject> entries) {
            super(false);
            _entries = entries;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            for (org.json.JSONObject obj : _entries) {
                try {
                    com.beemdevelopment.aegis.vault.VaultEntry entry;
                    entry = com.beemdevelopment.aegis.importers.FreeOtpImporter.State.convertEntry(obj);
                    result.addEntry(entry);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                    result.addError(e);
                }
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(org.json.JSONObject obj) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                java.lang.String type;
                type = obj.getString("type").toLowerCase(java.util.Locale.ROOT);
                java.lang.String algo;
                algo = obj.getString("algo");
                int digits;
                digits = obj.getInt("digits");
                byte[] secret;
                secret = com.beemdevelopment.aegis.importers.FreeOtpImporter.toBytes(obj.getJSONArray("secret"));
                java.lang.String issuer;
                issuer = obj.getString("issuerExt");
                java.lang.String name;
                name = obj.optString("label");
                com.beemdevelopment.aegis.otp.OtpInfo info;
                switch (type) {
                    case "totp" :
                        int period;
                        period = obj.getInt("period");
                        if (issuer.equals("Steam")) {
                            info = new com.beemdevelopment.aegis.otp.SteamInfo(secret, algo, digits, period);
                        } else {
                            info = new com.beemdevelopment.aegis.otp.TotpInfo(secret, algo, digits, period);
                        }
                        break;
                    case "hotp" :
                        info = new com.beemdevelopment.aegis.otp.HotpInfo(secret, algo, digits, obj.getLong("counter"));
                        break;
                    default :
                        throw new com.beemdevelopment.aegis.importers.DatabaseImporterException("unsupported otp type: " + type);
                }
                return new com.beemdevelopment.aegis.vault.VaultEntry(info, name, issuer);
            } catch (com.beemdevelopment.aegis.importers.DatabaseImporterException | com.beemdevelopment.aegis.otp.OtpInfoException | org.json.JSONException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, obj.toString());
            }
        }

    }

    private static byte[] toBytes(org.json.JSONArray array) throws org.json.JSONException {
        byte[] bytes;
        bytes = new byte[array.length()];
        for (int i = 0; i < array.length(); i++) {
            bytes[i] = ((byte) (array.getInt(i)));
        }
        return bytes;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
