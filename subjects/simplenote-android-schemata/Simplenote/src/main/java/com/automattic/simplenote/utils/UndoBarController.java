/* Copyright 2012 Roman Nurik

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.automattic.simplenote.utils;
import com.automattic.simplenote.R;
import java.util.List;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UndoBarController {
    static final int MUID_STATIC = getMUID();
    private com.automattic.simplenote.utils.UndoBarController.UndoListener mUndoListener;

    private java.util.List<java.lang.String> mDeletedNoteIds;

    private android.view.View.OnClickListener mOnUndoClickListener = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            switch(MUID_STATIC) {
                // UndoBarController_0_LengthyGUIListenerOperatorMutator
                case 34: {
                    /**
                    * Inserted by Kadabra
                    */
                    if (mUndoListener != null) {
                        mUndoListener.onUndo();
                    }
                    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                    break;
                }
                default: {
                if (mUndoListener != null) {
                    mUndoListener.onUndo();
                }
                break;
            }
        }
    }

};

public UndoBarController(com.automattic.simplenote.utils.UndoBarController.UndoListener undoListener) {
    mUndoListener = undoListener;
}


public void showUndoBar(android.view.View view, java.lang.CharSequence message) {
    if (view == null)
        return;

    com.google.android.material.snackbar.Snackbar.make(view, message, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).setAction(com.automattic.simplenote.R.string.undo, mOnUndoClickListener).show();
}


public java.util.List<java.lang.String> getDeletedNoteIds() {
    return mDeletedNoteIds;
}


public void setDeletedNoteIds(java.util.List<java.lang.String> noteIds) {
    mDeletedNoteIds = noteIds;
}


public interface UndoListener {
    void onUndo();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
