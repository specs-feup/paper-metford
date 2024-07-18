/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.feio.android.omninotes.async;
import java.lang.ref.WeakReference;
import android.app.Activity;
import it.feio.android.omninotes.models.Note;
import android.text.Spanned;
import android.os.AsyncTask;
import android.widget.TextView;
import it.feio.android.omninotes.utils.TextHelper;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TextWorkerTask extends android.os.AsyncTask<it.feio.android.omninotes.models.Note, java.lang.Void, android.text.Spanned[]> {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<android.app.Activity> mActivityWeakReference;

    private android.app.Activity mActivity;

    private android.widget.TextView titleTextView;

    private android.widget.TextView contentTextView;

    private boolean expandedView;

    public TextWorkerTask(android.app.Activity activity, android.widget.TextView titleTextView, android.widget.TextView contentTextView, boolean expandedView) {
        mActivityWeakReference = new java.lang.ref.WeakReference<>(activity);
        mActivity = activity;
        this.titleTextView = titleTextView;
        this.contentTextView = contentTextView;
        this.expandedView = expandedView;
    }


    @java.lang.Override
    protected android.text.Spanned[] doInBackground(it.feio.android.omninotes.models.Note... params) {
        return it.feio.android.omninotes.utils.TextHelper.parseTitleAndContent(mActivity, params[0]);
    }


    @java.lang.Override
    protected void onPostExecute(android.text.Spanned[] titleAndContent) {
        if (isAlive()) {
            titleTextView.setText(titleAndContent[0]);
            if (titleAndContent[1].length() > 0) {
                contentTextView.setText(titleAndContent[1]);
                contentTextView.setVisibility(android.view.View.VISIBLE);
            } else if (expandedView) {
                contentTextView.setVisibility(android.view.View.INVISIBLE);
            } else {
                contentTextView.setVisibility(android.view.View.GONE);
            }
        }
    }


    /**
     * Cheks if activity is still alive and not finishing
     *
     * @return True or false
     */
    private boolean isAlive() {
        return (mActivityWeakReference != null) && (mActivityWeakReference.get() != null);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
