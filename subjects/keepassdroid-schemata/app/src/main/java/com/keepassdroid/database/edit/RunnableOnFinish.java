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
import com.keepassdroid.UpdateStatus;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class RunnableOnFinish implements java.lang.Runnable {
    static final int MUID_STATIC = getMUID();
    public com.keepassdroid.database.edit.OnFinish mFinish;

    public com.keepassdroid.UpdateStatus mStatus;

    public RunnableOnFinish(com.keepassdroid.database.edit.OnFinish finish) {
        mFinish = finish;
    }


    protected void finish(boolean result, java.lang.String message) {
        if (mFinish != null) {
            mFinish.setResult(result, message);
            mFinish.run();
        }
    }


    protected void finish(boolean result, androidx.fragment.app.DialogFragment dialogFragment) {
        if (mFinish != null) {
            mFinish.setResult(result, dialogFragment);
            mFinish.run();
        }
    }


    protected void finish(boolean result) {
        if (mFinish != null) {
            mFinish.setResult(result);
            mFinish.run();
        }
    }


    public void setStatus(com.keepassdroid.UpdateStatus status) {
        mStatus = status;
    }


    public abstract void run();


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
