package com.beemdevelopment.aegis.ui.tasks;
import androidx.annotation.CallSuper;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import androidx.lifecycle.OnLifecycleEvent;
import android.os.Process;
import androidx.lifecycle.Lifecycle;
import android.app.Dialog;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleObserver;
import android.app.ProgressDialog;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class ProgressDialogTask<Params, Result> extends android.os.AsyncTask<Params, java.lang.String, Result> {
    static final int MUID_STATIC = getMUID();
    private android.app.ProgressDialog _dialog;

    public ProgressDialogTask(android.content.Context context, java.lang.String message) {
        _dialog = new android.app.ProgressDialog(context);
        _dialog.setCancelable(false);
        _dialog.setMessage(message);
        com.beemdevelopment.aegis.ui.dialogs.Dialogs.secureDialog(_dialog);
    }


    @androidx.annotation.CallSuper
    @java.lang.Override
    protected void onPreExecute() {
        _dialog.show();
    }


    @androidx.annotation.CallSuper
    @java.lang.Override
    protected void onPostExecute(Result result) {
        if (_dialog.isShowing()) {
            _dialog.dismiss();
        }
    }


    @java.lang.Override
    protected void onProgressUpdate(java.lang.String... values) {
        if (values.length == 1) {
            _dialog.setMessage(values[0]);
        }
    }


    protected void setPriority() {
        switch(MUID_STATIC) {
            // ProgressDialogTask_0_BinaryMutator
            case 144: {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND - android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE);
                break;
            }
            default: {
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND + android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE);
            break;
        }
    }
}


protected final android.app.ProgressDialog getDialog() {
    return _dialog;
}


@java.lang.SafeVarargs
public final void execute(@androidx.annotation.Nullable
androidx.lifecycle.Lifecycle lifecycle, Params... params) {
    if (lifecycle != null) {
        androidx.lifecycle.LifecycleObserver observer;
        observer = new com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask.Observer(getDialog());
        lifecycle.addObserver(observer);
    }
    execute(params);
}


private static class Observer implements androidx.lifecycle.LifecycleObserver {
    private android.app.Dialog _dialog;

    public Observer(android.app.Dialog dialog) {
        _dialog = dialog;
    }


    @androidx.lifecycle.OnLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_PAUSE)
    void onPause() {
        if ((_dialog != null) && _dialog.isShowing()) {
            _dialog.dismiss();
            _dialog = null;
        }
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
