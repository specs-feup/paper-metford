package com.beemdevelopment.aegis.ui.tasks;
import com.beemdevelopment.aegis.R;
import java.io.FileOutputStream;
import com.beemdevelopment.aegis.util.IOUtils;
import java.io.InputStream;
import java.io.IOException;
import android.net.Uri;
import java.io.File;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * ImportFileTask reads an SAF file from a background thread and
 * writes it to a temporary file in the cache directory.
 */
public class ImportFileTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params, com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result> {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Callback _cb;

    public ImportFileTask(android.content.Context context, com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Callback cb) {
        super(context, context.getString(com.beemdevelopment.aegis.R.string.reading_file));
        _cb = cb;
    }


    @java.lang.Override
    protected com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result doInBackground(com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params... params) {
        android.content.Context context;
        context = getDialog().getContext();
        com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Params p;
        p = params[0];
        android.net.Uri uri;
        uri = p.getUri();
        try (java.io.InputStream inStream = context.getContentResolver().openInputStream(uri)) {
            if (inStream == null) {
                throw new java.io.IOException("openInputStream returned null");
            }
            java.lang.String prefix;
            prefix = (p.getNamePrefix() != null) ? p.getNamePrefix() + "-" : "";
            java.lang.String suffix;
            suffix = (p.getNameSuffix() != null) ? "-" + p.getNameSuffix() : "";
            java.io.File tempFile;
            tempFile = java.io.File.createTempFile(prefix, suffix, context.getCacheDir());
            try (java.io.FileOutputStream outStream = new java.io.FileOutputStream(tempFile)) {
                com.beemdevelopment.aegis.util.IOUtils.copy(inStream, outStream);
            }
            return new com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result(uri, tempFile);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return new com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result(uri, e);
        }
    }


    @java.lang.Override
    protected void onPostExecute(com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result result) {
        super.onPostExecute(result);
        _cb.onTaskFinished(result);
    }


    public interface Callback {
        void onTaskFinished(com.beemdevelopment.aegis.ui.tasks.ImportFileTask.Result result);

    }

    public static class Params {
        private final android.net.Uri _uri;

        private final java.lang.String _namePrefix;

        private final java.lang.String _nameSuffix;

        public Params(android.net.Uri uri, java.lang.String namePrefix, java.lang.String nameSuffix) {
            _uri = uri;
            _namePrefix = namePrefix;
            _nameSuffix = nameSuffix;
        }


        public android.net.Uri getUri() {
            return _uri;
        }


        public java.lang.String getNamePrefix() {
            return _namePrefix;
        }


        public java.lang.String getNameSuffix() {
            return _nameSuffix;
        }

    }

    public static class Result {
        private final android.net.Uri _uri;

        private java.io.File _file;

        private java.lang.Exception _e;

        public Result(android.net.Uri uri, java.io.File file) {
            _uri = uri;
            _file = file;
        }


        public Result(android.net.Uri uri, java.lang.Exception e) {
            _uri = uri;
            _e = e;
        }


        public android.net.Uri getUri() {
            return _uri;
        }


        public java.io.File getFile() {
            return _file;
        }


        public java.lang.String getError() {
            if (_e == null) {
                return null;
            }
            return java.lang.String.format("ImportFileTask(uri=\"%s\"): %s", _uri, _e);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
