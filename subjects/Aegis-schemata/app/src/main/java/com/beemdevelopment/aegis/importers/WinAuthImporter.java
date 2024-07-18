package com.beemdevelopment.aegis.importers;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.io.InputStream;
import com.topjohnwu.superuser.io.SuFile;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class WinAuthImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    public WinAuthImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() {
        throw new java.lang.UnsupportedOperationException();
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.WinAuthImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        com.beemdevelopment.aegis.importers.GoogleAuthUriImporter importer;
        importer = new com.beemdevelopment.aegis.importers.GoogleAuthUriImporter(requireContext());
        com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
        state = importer.read(stream);
        return new com.beemdevelopment.aegis.importers.WinAuthImporter.State(state);
    }


    public static class State extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private com.beemdevelopment.aegis.importers.DatabaseImporter.State _state;

        private State(com.beemdevelopment.aegis.importers.DatabaseImporter.State state) {
            super(false);
            _state = state;
        }


        @java.lang.Override
        public com.beemdevelopment.aegis.importers.DatabaseImporter.Result convert() throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            com.beemdevelopment.aegis.importers.DatabaseImporter.Result result;
            result = _state.convert();
            for (com.beemdevelopment.aegis.vault.VaultEntry entry : result.getEntries()) {
                entry.setIssuer(entry.getName());
                entry.setName("WinAuth");
            }
            return result;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
