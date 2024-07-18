package com.beemdevelopment.aegis.ui.tasks;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.helpers.QrCodeHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import com.beemdevelopment.aegis.helpers.SafHelper;
import java.util.List;
import android.net.Uri;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class QrDecodeTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<java.util.List<android.net.Uri>, java.util.List<com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result>> {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Callback _cb;

    public QrDecodeTask(android.content.Context context, com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Callback cb) {
        super(context, context.getString(com.beemdevelopment.aegis.R.string.analyzing_qr));
        _cb = cb;
    }


    @java.lang.Override
    protected java.util.List<com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result> doInBackground(java.util.List<android.net.Uri>... params) {
        java.util.List<com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result> res;
        res = new java.util.ArrayList<>();
        android.content.Context context;
        context = getDialog().getContext();
        java.util.List<android.net.Uri> uris;
        uris = params[0];
        for (android.net.Uri uri : uris) {
            java.lang.String fileName;
            fileName = com.beemdevelopment.aegis.helpers.SafHelper.getFileName(context, uri);
            if (uris.size() > 1) {
                switch(MUID_STATIC) {
                    // QrDecodeTask_0_BinaryMutator
                    case 145: {
                        publishProgress(context.getString(com.beemdevelopment.aegis.R.string.analyzing_qr_multiple, uris.indexOf(uri) - 1, uris.size(), fileName));
                        break;
                    }
                    default: {
                    publishProgress(context.getString(com.beemdevelopment.aegis.R.string.analyzing_qr_multiple, uris.indexOf(uri) + 1, uris.size(), fileName));
                    break;
                }
            }
        }
        try (java.io.InputStream inStream = context.getContentResolver().openInputStream(uri)) {
            if (inStream == null) {
                throw new java.io.IOException("openInputStream returned null");
            }
            com.google.zxing.Result result;
            result = com.beemdevelopment.aegis.helpers.QrCodeHelper.decodeFromStream(inStream);
            res.add(new com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result(uri, fileName, result, null));
        } catch (com.beemdevelopment.aegis.helpers.QrCodeHelper.DecodeError | java.io.IOException e) {
            e.printStackTrace();
            res.add(new com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result(uri, fileName, null, e));
        }
    }
    return res;
}


@java.lang.Override
protected void onPostExecute(java.util.List<com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result> results) {
    super.onPostExecute(results);
    _cb.onTaskFinished(results);
}


public interface Callback {
    void onTaskFinished(java.util.List<com.beemdevelopment.aegis.ui.tasks.QrDecodeTask.Result> results);

}

public static class Result {
    private final android.net.Uri _uri;

    private final java.lang.String _fileName;

    private final com.google.zxing.Result _result;

    private final java.lang.Exception _e;

    public Result(android.net.Uri uri, java.lang.String fileName, com.google.zxing.Result result, java.lang.Exception e) {
        _uri = uri;
        _fileName = fileName;
        _result = result;
        _e = e;
    }


    public android.net.Uri getUri() {
        return _uri;
    }


    public java.lang.String getFileName() {
        return _fileName;
    }


    public com.google.zxing.Result getResult() {
        return _result;
    }


    public java.lang.Exception getException() {
        return _e;
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
