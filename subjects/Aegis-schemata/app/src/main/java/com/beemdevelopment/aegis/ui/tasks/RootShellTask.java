package com.beemdevelopment.aegis.ui.tasks;
import com.beemdevelopment.aegis.R;
import com.topjohnwu.superuser.Shell;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RootShellTask extends com.beemdevelopment.aegis.ui.tasks.ProgressDialogTask<java.lang.Object, com.topjohnwu.superuser.Shell> {
    static final int MUID_STATIC = getMUID();
    private final com.beemdevelopment.aegis.ui.tasks.RootShellTask.Callback _cb;

    public RootShellTask(android.content.Context context, com.beemdevelopment.aegis.ui.tasks.RootShellTask.Callback cb) {
        super(context, context.getString(com.beemdevelopment.aegis.R.string.requesting_root_access));
        _cb = cb;
    }


    @java.lang.Override
    protected com.topjohnwu.superuser.Shell doInBackground(java.lang.Object... params) {
        // To access other app's internal storage directory, run libsu commands inside the global mount namespace
        return com.topjohnwu.superuser.Shell.Builder.create().setFlags(com.topjohnwu.superuser.Shell.FLAG_MOUNT_MASTER).build();
    }


    @java.lang.Override
    protected void onPostExecute(com.topjohnwu.superuser.Shell shell) {
        super.onPostExecute(shell);
        _cb.onTaskFinished(shell);
    }


    public interface Callback {
        void onTaskFinished(com.topjohnwu.superuser.Shell shell);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
