package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.encoding.EncodingException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import com.topjohnwu.superuser.io.SuFile;
import org.xmlpull.v1.XmlPullParser;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.util.PreferenceParser;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import com.beemdevelopment.aegis.vault.VaultEntry;
import android.util.Xml;
import android.content.pm.PackageManager;
import org.xmlpull.v1.XmlPullParserException;
import java.util.List;
import com.beemdevelopment.aegis.encoding.Hex;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BattleNetImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String _pkgName = "com.blizzard.bma";

    private static final java.lang.String _subPath = "shared_prefs/com.blizzard.bma.AUTH_STORE.xml";

    private static byte[] _key;

    public BattleNetImporter(android.content.Context context) {
        super(context);
    }


    static {
        try {
            com.beemdevelopment.aegis.importers.BattleNetImporter._key = com.beemdevelopment.aegis.encoding.Hex.decode("398e27fc50276a656065b0e525f4c06c04c61075286b8e7aeda59da9813b5dd6c80d2fb38068773fa59ba47c17ca6c6479015c1d5b8b8f6b9a");
        } catch (com.beemdevelopment.aegis.encoding.EncodingException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() throws com.beemdevelopment.aegis.importers.DatabaseImporterException, android.content.pm.PackageManager.NameNotFoundException {
        return getAppPath(com.beemdevelopment.aegis.importers.BattleNetImporter._pkgName, com.beemdevelopment.aegis.importers.BattleNetImporter._subPath);
    }


    @java.lang.Override
    protected com.beemdevelopment.aegis.importers.BattleNetImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            org.xmlpull.v1.XmlPullParser parser;
            parser = android.util.Xml.newPullParser();
            parser.setFeature(org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            parser.nextTag();
            java.util.List<java.lang.String> entries;
            entries = new java.util.ArrayList<>();
            for (com.beemdevelopment.aegis.util.PreferenceParser.XmlEntry entry : com.beemdevelopment.aegis.util.PreferenceParser.parse(parser)) {
                if (entry.Name.equals("com.blizzard.bma.AUTH_STORE.HASH")) {
                    entries.add(entry.Value);
                    break;
                }
            }
            return new com.beemdevelopment.aegis.importers.BattleNetImporter.State(entries);
        } catch (org.xmlpull.v1.XmlPullParserException | java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
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
            for (java.lang.String str : _entries) {
                try {
                    com.beemdevelopment.aegis.vault.VaultEntry entry;
                    entry = com.beemdevelopment.aegis.importers.BattleNetImporter.State.convertEntry(str);
                    result.addEntry(entry);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                    result.addError(e);
                }
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(java.lang.String hashString) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                byte[] hash;
                hash = com.beemdevelopment.aegis.encoding.Hex.decode(hashString);
                if (hash.length != com.beemdevelopment.aegis.importers.BattleNetImporter._key.length) {
                    throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(java.lang.String.format("Unexpected hash length: %d", hash.length), hashString);
                }
                java.lang.StringBuilder sb;
                sb = new java.lang.StringBuilder();
                for (int i = 0; i < hash.length; i++) {
                    char c;
                    c = ((char) (hash[i] ^ com.beemdevelopment.aegis.importers.BattleNetImporter._key[i]));
                    sb.append(c);
                }
                final int secretLen;
                secretLen = 40;
                byte[] secret;
                secret = com.beemdevelopment.aegis.encoding.Hex.decode(sb.substring(0, secretLen));
                java.lang.String serial;
                serial = sb.substring(secretLen);
                com.beemdevelopment.aegis.otp.OtpInfo info;
                info = new com.beemdevelopment.aegis.otp.TotpInfo(secret, com.beemdevelopment.aegis.otp.OtpInfo.DEFAULT_ALGORITHM, 8, com.beemdevelopment.aegis.otp.TotpInfo.DEFAULT_PERIOD);
                return new com.beemdevelopment.aegis.vault.VaultEntry(info, serial, "Battle.net");
            } catch (com.beemdevelopment.aegis.otp.OtpInfoException | com.beemdevelopment.aegis.encoding.EncodingException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, hashString);
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
