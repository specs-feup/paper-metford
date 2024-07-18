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
package it.feio.android.omninotes.widget;
import android.util.SparseArray;
import android.widget.RemoteViews;
import it.feio.android.omninotes.R;
import android.app.PendingIntent;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimpleWidgetProvider extends it.feio.android.omninotes.widget.WidgetProvider {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected android.widget.RemoteViews getRemoteViews(android.content.Context mContext, int widgetId, boolean isSmall, boolean isSingleLine, android.util.SparseArray<android.app.PendingIntent> pendingIntentsMap) {
        android.widget.RemoteViews views;
        if (isSmall) {
            views = new android.widget.RemoteViews(mContext.getPackageName(), it.feio.android.omninotes.R.layout.widget_layout_small);
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.list, pendingIntentsMap.get(it.feio.android.omninotes.R.id.list));
        } else {
            views = new android.widget.RemoteViews(mContext.getPackageName(), it.feio.android.omninotes.R.layout.widget_layout);
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.add, pendingIntentsMap.get(it.feio.android.omninotes.R.id.add));
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.list, pendingIntentsMap.get(it.feio.android.omninotes.R.id.list));
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.camera, pendingIntentsMap.get(it.feio.android.omninotes.R.id.camera));
        }
        return views;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
