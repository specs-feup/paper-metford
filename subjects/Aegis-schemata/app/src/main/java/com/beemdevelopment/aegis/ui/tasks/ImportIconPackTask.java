package com.beemdevelopment.aegis.ui.tasks;
import com.beemdevelopment.aegis.icons.IconPackException;
import com.beemdevelopment.aegis.icons.IconPackManager;
import com.beemdevelopment.aegis.R;
import java.io.FileOutputStream;
import com.beemdevelopment.aegis.util.IOUtils;
import java.io.InputStream;
import java.io.IOException;
import android.net.Uri;
import java.io.File;
import android.content.Context;
import com.beemdevelopment.aegis.icons.IconPack;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImportIconPackTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Params, com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Result> {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Callback _cb;

    public ImportIconPackTask(android.content.Context context, com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Callback cb) {
        super(context, context.getString(com.beemdevelopment.aegis.R.string.importing_icon_pack));
        _cb = cb;
    }


    @java.lang.Override
    protected com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Result doInBackground(com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Params... params) {
        android.content.Context context;
        context = getDialog().getContext();
        com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Params param;
        param = params[0];
        java.io.File tempFile;
        tempFile = null;
        try {
            tempFile = java.io.File.createTempFile("icon-pack-", "", context.getCacheDir());
            try (java.io.InputStream inStream = context.getContentResolver().openInputStream(param.getUri());java.io.FileOutputStream outStream = new java.io.FileOutputStream(tempFile)) {
                if (inStream == null) {
                    throw new java.io.IOException("openInputStream returned null");
                }
                com.beemdevelopment.aegis.util.IOUtils.copy(inStream, outStream);
            }
            com.beemdevelopment.aegis.icons.IconPack pack;
            pack = param.getManager().importPack(tempFile);
            return new com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Result(pack, null);
        } catch (java.io.IOException | com.beemdevelopment.aegis.icons.IconPackException e) {
            e.printStackTrace();
            return new com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Result(null, e);
        } finally {
            if (tempFile != null) {
                tempFile.delete();
            }
        }
    }


    @java.lang.Override
    protected void onPostExecute(com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Result result) {
        super.onPostExecute(result);
        _cb.onTaskFinished(result);
    }


    public interface Callback {
        void onTaskFinished(com.beemdevelopment.aegis.ui.tasks.ImportIconPackTask.Result result);

    }

    public static class Params {
        private final com.beemdevelopment.aegis.icons.IconPackManager _manager;

        private final android.net.Uri _uri;

        public Params(com.beemdevelopment.aegis.icons.IconPackManager manager, android.net.Uri uri) {
            _manager = manager;
            _uri = uri;
        }


        public com.beemdevelopment.aegis.icons.IconPackManager getManager() {
            return _manager;
        }


        public android.net.Uri getUri() {
            return _uri;
        }

    }

    public static class Result {
        private final com.beemdevelopment.aegis.icons.IconPack _pack;

        private final java.lang.Exception _e;

        public Result(com.beemdevelopment.aegis.icons.IconPack pack, java.lang.Exception e) {
            _pack = pack;
            _e = e;
        }


        public com.beemdevelopment.aegis.icons.IconPack getIconPack() {
            return _pack;
        }


        public java.lang.Exception getException() {
            return _e;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
