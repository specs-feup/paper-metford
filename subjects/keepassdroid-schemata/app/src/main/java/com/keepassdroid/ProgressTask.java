/* Copyright 2009-2013 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid;
import com.android.keepass.R;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import com.keepassdroid.database.edit.RunnableOnFinish;
import com.keepassdroid.database.edit.OnFinish;
import android.app.ProgressDialog;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Designed to Pop up a progress dialog, run a thread in the background,
 *  run cleanup in the current thread, close the dialog.  Without blocking
 *  the current thread.
 *
 * @author bpellin
 */
public class ProgressTask implements java.lang.Runnable {
    static final int MUID_STATIC = getMUID();
    private android.app.Activity mAct;

    private android.os.Handler mHandler;

    private com.keepassdroid.database.edit.RunnableOnFinish mTask;

    private android.app.ProgressDialog mPd;

    public ProgressTask(android.app.Activity act, com.keepassdroid.database.edit.RunnableOnFinish task, int messageId) {
        mAct = act;
        mTask = task;
        mHandler = new android.os.Handler();
        // Show process dialog
        mPd = new android.app.ProgressDialog(mAct);
        mPd.setCanceledOnTouchOutside(false);
        mPd.setTitle(act.getText(com.android.keepass.R.string.progress_title));
        mPd.setMessage(act.getText(messageId));
        // Set code to run when this is finished
        mTask.setStatus(new com.keepassdroid.UpdateStatus(act, mHandler, mPd));
        mTask.mFinish = new com.keepassdroid.ProgressTask.AfterTask(task.mFinish, mHandler);
    }


    public void run() {
        // Show process dialog
        mPd.show();
        // Start Thread to Run task
        java.lang.Thread t;
        t = new java.lang.Thread(mTask);
        t.start();
    }


    private class AfterTask extends com.keepassdroid.database.edit.OnFinish {
        public AfterTask(com.keepassdroid.database.edit.OnFinish finish, android.os.Handler handler) {
            super(finish, handler);
        }


        @java.lang.Override
        public void run() {
            super.run();
            // Remove the progress dialog
            mHandler.post(new com.keepassdroid.ProgressTask.CloseProcessDialog());
        }

    }

    private class CloseProcessDialog implements java.lang.Runnable {
        public void run() {
            android.app.Activity act;
            act = mPd.getOwnerActivity();
            if ((act != null) && act.isFinishing()) {
                return;
            }
            boolean isDestroyed;
            isDestroyed = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                isDestroyed = mAct.isDestroyed();
            }
            if (((mPd != null) && mPd.isShowing()) && (!isDestroyed)) {
                mPd.dismiss();
            }
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
