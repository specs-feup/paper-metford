package com.beemdevelopment.aegis.importers;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.topjohnwu.superuser.io.SuFile;
import net.lingala.zip4j.model.LocalFileHeader;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AuthenticatorPlusImporter extends com.beemdevelopment.aegis.importers.DatabaseImporter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String FILENAME = "Accounts.txt";

    public AuthenticatorPlusImporter(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.io.SuFile getAppPath() {
        throw new java.lang.UnsupportedOperationException();
    }


    @java.lang.Override
    public com.beemdevelopment.aegis.importers.DatabaseImporter.State read(java.io.InputStream stream, boolean isInternal) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
        try {
            return new com.beemdevelopment.aegis.importers.AuthenticatorPlusImporter.EncryptedState(com.beemdevelopment.aegis.util.IOUtils.readAll(stream));
        } catch (java.io.IOException e) {
            throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
        }
    }


    public static class EncryptedState extends com.beemdevelopment.aegis.importers.DatabaseImporter.State {
        private final byte[] _data;

        private EncryptedState(byte[] data) {
            super(true);
            _data = data;
        }


        protected com.beemdevelopment.aegis.importers.DatabaseImporter.State decrypt(char[] password) throws com.beemdevelopment.aegis.importers.DatabaseImporterException {
            try (java.io.ByteArrayInputStream inStream = new java.io.ByteArrayInputStream(_data);net.lingala.zip4j.io.inputstream.ZipInputStream zipStream = new net.lingala.zip4j.io.inputstream.ZipInputStream(inStream, password)) {
                net.lingala.zip4j.model.LocalFileHeader header;
                while ((header = zipStream.getNextEntry()) != null) {
                    java.io.File file;
                    file = new java.io.File(header.getFileName());
                    if (file.getName().equals(com.beemdevelopment.aegis.importers.AuthenticatorPlusImporter.FILENAME)) {
                        com.beemdevelopment.aegis.importers.GoogleAuthUriImporter importer;
                        importer = new com.beemdevelopment.aegis.importers.GoogleAuthUriImporter(null);
                        return importer.read(zipStream);
                    }
                } 
                throw new java.io.FileNotFoundException(com.beemdevelopment.aegis.importers.AuthenticatorPlusImporter.FILENAME);
            } catch (java.io.IOException e) {
                throw new com.beemdevelopment.aegis.importers.DatabaseImporterException(e);
            }
        }


        @java.lang.Override
        public void decrypt(android.content.Context context, com.beemdevelopment.aegis.importers.DatabaseImporter.DecryptListener listener) {
            com.beemdevelopment.aegis.ui.dialogs.Dialogs.showPasswordInputDialog(context, ( password) -> {
                try {
                    com.beemdevelopment.aegis.importers.DatabaseImporter.State state;
                    state = decrypt(password);
                    listener.onStateDecrypted(state);
                } catch (com.beemdevelopment.aegis.importers.DatabaseImporterException e) {
                    listener.onError(e);
                }
            }, (android.content.DialogInterface dialog1) -> listener.onCanceled());
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
