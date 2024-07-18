package com.beemdevelopment.aegis.ui.tasks;
import com.beemdevelopment.aegis.R;
import java.io.OutputStream;
import com.beemdevelopment.aegis.util.IOUtils;
import java.io.InputStream;
import java.io.IOException;
import android.net.Uri;
import java.io.File;
import android.content.Context;
import java.io.FileInputStream;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ExportTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<com.beemdevelopment.aegis.ui.tasks.ExportTask.Params, java.lang.Exception> {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.ui.tasks.ExportTask.Callback _cb;

    public ExportTask(android.content.Context context, com.beemdevelopment.aegis.ui.tasks.ExportTask.Callback cb) {
        super(context, context.getString(com.beemdevelopment.aegis.R.string.exporting_vault));
        _cb = cb;
    }


    @java.lang.Override
    protected java.lang.Exception doInBackground(com.beemdevelopment.aegis.ui.tasks.ExportTask.Params... args) {
        setPriority();
        com.beemdevelopment.aegis.ui.tasks.ExportTask.Params params;
        params = args[0];
        try (java.io.InputStream inStream = new java.io.FileInputStream(params.getFile());java.io.OutputStream outStream = getDialog().getContext().getContentResolver().openOutputStream(params.getDestUri(), "w")) {
            if (outStream == null) {
                throw new java.io.IOException("openOutputStream returned null");
            }
            com.beemdevelopment.aegis.util.IOUtils.copy(inStream, outStream);
            return null;
        } catch (java.io.IOException e) {
            return e;
        }
    }


    @java.lang.Override
    protected void onPostExecute(java.lang.Exception e) {
        super.onPostExecute(e);
        _cb.onTaskFinished(e);
    }


    public static class Params {
        private final java.io.File _file;

        private final android.net.Uri _destUri;

        public Params(java.io.File file, android.net.Uri destUri) {
            _file = file;
            _destUri = destUri;
        }


        public java.io.File getFile() {
            return _file;
        }


        public android.net.Uri getDestUri() {
            return _destUri;
        }

    }

    public interface Callback {
        void onTaskFinished(java.lang.Exception e);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
