package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.beemdevelopment.aegis.otp.GoogleAuthInfoException;
import java.io.IOException;
import java.io.BufferedReader;
import com.topjohnwu.superuser.io.SuFile;
import com.beemdevelopment.aegis.otp.GoogleAuthInfo;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GoogleAuthUriImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    public GoogleAuthUriImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() {
        throw new java.lang.UnsupportedOperationException();
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.GoogleAuthUriImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        java.util.ArrayList<java.lang.String> lines;
        lines = new java.util.ArrayList<>();
        try (java.io.InputStreamReader streamReader = new java.io.InputStreamReader(stream);java.io.BufferedReader bufferedReader = new java.io.BufferedReader(streamReader)) {
            java.lang.String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            } 
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
        return new com.beemdevelopment.aegis.importers.GoogleAuthUriImporter.State(lines);
    }


    public static class State extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private java.util.ArrayList<java.lang.String> _lines;

        private State(java.util.ArrayList<java.lang.String> lines) {
            super(false);
            _lines = lines;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = new com.beemdevelopment.aegis.importers.DatabaseImporter.Result();
            for (java.lang.String line : _lines) {
                try {
                    com.beemdevelopment.aegis.vault.VaultEntry entry;
                    entry = com.beemdevelopment.aegis.importers.GoogleAuthUriImporter.State.convertEntry(line);
                    result.addEntry(entry);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterEntryException e) {
                    result.addError(e);
                }
            }
            return result;
        }


        private static com.beemdevelopment.aegis.vault.VaultEntry convertEntry(java.lang.String line) throws com.beemdevelopment.aegis.importers.DatabaseImporterEntryException {
            try {
                com.beemdevelopment.aegis.otp.GoogleAuthInfo info;
                info = com.beemdevelopment.aegis.otp.GoogleAuthInfo.parseUri(line);
                return new com.beemdevelopment.aegis.vault.VaultEntry(info);
            } catch (com.beemdevelopment.aegis.otp.GoogleAuthInfoException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterEntryException(e, line);
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
