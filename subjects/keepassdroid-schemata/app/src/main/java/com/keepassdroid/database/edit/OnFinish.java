/* Copyright 2009-2022 Brian Pellin.

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
package com.keepassdroid.database.edit;
import androidx.fragment.app.FragmentManager;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.widget.Toast;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Callback after a task is completed.
 *
 * @author bpellin
 */
public class OnFinish implements java.lang.Runnable {
    static final int MUID_STATIC = getMUID();
    protected boolean mSuccess;

    protected java.lang.String mMessage;

    protected androidx.fragment.app.DialogFragment mDialog = null;

    protected com.keepassdroid.database.edit.OnFinish mOnFinish;

    protected android.os.Handler mHandler;

    public OnFinish() {
    }


    public OnFinish(android.os.Handler handler) {
        mOnFinish = null;
        mHandler = handler;
    }


    public OnFinish(com.keepassdroid.database.edit.OnFinish finish, android.os.Handler handler) {
        mOnFinish = finish;
        mHandler = handler;
    }


    public OnFinish(com.keepassdroid.database.edit.OnFinish finish) {
        mOnFinish = finish;
        mHandler = null;
    }


    public void setResult(boolean success, java.lang.String message) {
        mSuccess = success;
        mMessage = message;
    }


    public void setResult(boolean success, androidx.fragment.app.DialogFragment dialogFragment) {
        mSuccess = success;
        mDialog = dialogFragment;
    }


    public void setResult(boolean success) {
        mSuccess = success;
    }


    public void run() {
        if (mOnFinish != null) {
            // Pass on result on call finish
            if (mDialog != null) {
                mOnFinish.setResult(mSuccess, mDialog);
            } else {
                mOnFinish.setResult(mSuccess, mMessage);
            }
            if (mHandler != null) {
                mHandler.post(mOnFinish);
            } else {
                mOnFinish.run();
            }
        }
    }


    protected void displayMessage(androidx.appcompat.app.AppCompatActivity ctx) {
        if (ctx == null) {
            return;
        }
        displayMessage(ctx, ctx.getSupportFragmentManager());
    }


    protected void displayMessage(android.content.Context ctx, androidx.fragment.app.FragmentManager fm) {
        if (((ctx != null) && (mMessage != null)) && (mMessage.length() > 0)) {
            android.widget.Toast.makeText(ctx, mMessage, android.widget.Toast.LENGTH_LONG).show();
        } else if ((fm != null) && (mDialog != null)) {
            mDialog.show(fm, "message");
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
