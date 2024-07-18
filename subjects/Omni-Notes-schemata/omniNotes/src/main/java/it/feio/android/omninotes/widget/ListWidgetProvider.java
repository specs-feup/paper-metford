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
import static it.feio.android.omninotes.helpers.IntentHelper.mutablePendingIntentFlag;
import android.widget.RemoteViews;
import it.feio.android.omninotes.R;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import android.appwidget.AppWidgetManager;
import it.feio.android.omninotes.MainActivity;
import android.content.Intent;
import android.app.PendingIntent;
import android.net.Uri;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ListWidgetProvider extends it.feio.android.omninotes.widget.WidgetProvider {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected android.widget.RemoteViews getRemoteViews(android.content.Context mContext, int widgetId, boolean isSmall, boolean isSingleLine, android.util.SparseArray<android.app.PendingIntent> pendingIntentsMap) {
        android.widget.RemoteViews views;
        if (isSmall) {
            views = new android.widget.RemoteViews(mContext.getPackageName(), it.feio.android.omninotes.R.layout.widget_layout_small);
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.list, pendingIntentsMap.get(it.feio.android.omninotes.R.id.list));
        } else if (isSingleLine) {
            views = new android.widget.RemoteViews(mContext.getPackageName(), it.feio.android.omninotes.R.layout.widget_layout);
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.add, pendingIntentsMap.get(it.feio.android.omninotes.R.id.add));
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.list, pendingIntentsMap.get(it.feio.android.omninotes.R.id.list));
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.camera, pendingIntentsMap.get(it.feio.android.omninotes.R.id.camera));
        } else {
            views = new android.widget.RemoteViews(mContext.getPackageName(), it.feio.android.omninotes.R.layout.widget_layout_list);
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.add, pendingIntentsMap.get(it.feio.android.omninotes.R.id.add));
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.list, pendingIntentsMap.get(it.feio.android.omninotes.R.id.list));
            views.setOnClickPendingIntent(it.feio.android.omninotes.R.id.camera, pendingIntentsMap.get(it.feio.android.omninotes.R.id.camera));
            // Set up the intent that starts the ListViewService, which will
            // provide the views for this collection.
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // ListWidgetProvider_0_NullIntentOperatorMutator
                case 109: {
                    intent = null;
                    break;
                }
                // ListWidgetProvider_1_InvalidKeyIntentOperatorMutator
                case 1109: {
                    intent = new android.content.Intent((Context) null, it.feio.android.omninotes.widget.ListWidgetService.class);
                    break;
                }
                // ListWidgetProvider_2_RandomActionIntentDefinitionOperatorMutator
                case 2109: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(mContext, it.feio.android.omninotes.widget.ListWidgetService.class);
                break;
            }
        }
        switch(MUID_STATIC) {
            // ListWidgetProvider_3_NullValueIntentPutExtraOperatorMutator
            case 3109: {
                // Add the app widget ID to the intent extras.
                intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
                break;
            }
            // ListWidgetProvider_4_IntentPayloadReplacementOperatorMutator
            case 4109: {
                // Add the app widget ID to the intent extras.
                intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // ListWidgetProvider_5_RandomActionIntentDefinitionOperatorMutator
                case 5109: {
                    /**
                    * Inserted by Kadabra
                    */
                    /**
                    * Inserted by Kadabra
                    */
                    new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                // Add the app widget ID to the intent extras.
                intent.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                break;
            }
        }
        break;
    }
}
switch(MUID_STATIC) {
    // ListWidgetProvider_6_RandomActionIntentDefinitionOperatorMutator
    case 6109: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent.setData(android.net.Uri.parse(intent.toUri(android.content.Intent.URI_INTENT_SCHEME)));
    break;
}
}
views.setRemoteAdapter(it.feio.android.omninotes.R.id.widget_list, intent);
android.content.Intent clickIntent;
switch(MUID_STATIC) {
// ListWidgetProvider_7_NullIntentOperatorMutator
case 7109: {
    clickIntent = null;
    break;
}
// ListWidgetProvider_8_InvalidKeyIntentOperatorMutator
case 8109: {
    clickIntent = new android.content.Intent((Context) null, it.feio.android.omninotes.MainActivity.class);
    break;
}
// ListWidgetProvider_9_RandomActionIntentDefinitionOperatorMutator
case 9109: {
    clickIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
clickIntent = new android.content.Intent(mContext, it.feio.android.omninotes.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// ListWidgetProvider_10_RandomActionIntentDefinitionOperatorMutator
case 10109: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
clickIntent.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET);
break;
}
}
android.app.PendingIntent clickPI;
clickPI = android.app.PendingIntent.getActivity(mContext, 0, clickIntent, it.feio.android.omninotes.helpers.IntentHelper.mutablePendingIntentFlag(android.app.PendingIntent.FLAG_UPDATE_CURRENT));
views.setPendingIntentTemplate(it.feio.android.omninotes.R.id.widget_list, clickPI);
}
return views;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
