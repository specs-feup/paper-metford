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
import it.feio.android.omninotes.async.bus.NotesUpdatedEvent;
import it.feio.android.omninotes.BaseActivity;
import de.greenrobot.event.EventBus;
import android.os.AsyncTask;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UpdateWidgetsTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
    static final int MUID_STATIC = getMUID();
    private java.lang.ref.WeakReference<android.content.Context> context;

    public UpdateWidgetsTask(android.content.Context context) {
        this.context = new java.lang.ref.WeakReference<>(context);
    }


    @java.lang.Override
    protected java.lang.Void doInBackground(java.lang.Void... params) {
        it.feio.android.omninotes.async.UpdateWidgetsTask.WidgetUpdateSubscriber widgetUpdateSubscriber;
        widgetUpdateSubscriber = new it.feio.android.omninotes.async.UpdateWidgetsTask.WidgetUpdateSubscriber();
        return null;
    }


    class WidgetUpdateSubscriber {
        WidgetUpdateSubscriber() {
            de.greenrobot.event.EventBus.getDefault().register(this);
        }


        public void onEvent(it.feio.android.omninotes.async.bus.NotesUpdatedEvent event) {
            it.feio.android.omninotes.BaseActivity.notifyAppWidgets(context.get());
            de.greenrobot.event.EventBus.getDefault().unregister(this);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
