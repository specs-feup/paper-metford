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
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import android.appwidget.AppWidgetProvider;
import android.os.Bundle;
import android.appwidget.AppWidgetManager;
import it.feio.android.omninotes.MainActivity;
import android.content.Intent;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET;
import android.util.SparseArray;
import static it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET;
import android.widget.RemoteViews;
import it.feio.android.omninotes.R;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET_TAKE_PHOTO;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET_SHOW_LIST;
import android.content.ComponentName;
import android.app.PendingIntent;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class WidgetProvider extends android.appwidget.AppWidgetProvider {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String EXTRA_WORD = "it.feio.android.omninotes.widget.WORD";

    public static final java.lang.String TOAST_ACTION = "it.feio.android.omninotes.widget.NOTE";

    public static final java.lang.String EXTRA_ITEM = "it.feio.android.omninotes.widget.EXTRA_FIELD";

    @java.lang.Override
    public void onUpdate(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Get all ids
        android.content.ComponentName thisWidget;
        thisWidget = new android.content.ComponentName(context, getClass());
        int[] allWidgetIds;
        allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int appWidgetId : allWidgetIds) {
            it.feio.android.omninotes.helpers.LogDelegate.d("WidgetProvider onUpdate() widget " + appWidgetId);
            // Get the layout for and attach an on-click listener to views
            setLayout(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @java.lang.Override
    public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int appWidgetId, android.os.Bundle newOptions) {
        it.feio.android.omninotes.helpers.LogDelegate.d("Widget size changed");
        setLayout(context, appWidgetManager, appWidgetId);
    }


    private void setLayout(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int widgetId) {
        // Create an Intent to launch DetailActivity
        android.content.Intent intentDetail;
        switch(MUID_STATIC) {
            // WidgetProvider_0_NullIntentOperatorMutator
            case 111: {
                intentDetail = null;
                break;
            }
            // WidgetProvider_1_InvalidKeyIntentOperatorMutator
            case 1111: {
                intentDetail = new android.content.Intent((Context) null, it.feio.android.omninotes.MainActivity.class);
                break;
            }
            // WidgetProvider_2_RandomActionIntentDefinitionOperatorMutator
            case 2111: {
                intentDetail = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intentDetail = new android.content.Intent(context, it.feio.android.omninotes.MainActivity.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // WidgetProvider_3_RandomActionIntentDefinitionOperatorMutator
        case 3111: {
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
        intentDetail.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET);
        break;
    }
}
switch(MUID_STATIC) {
    // WidgetProvider_4_NullValueIntentPutExtraOperatorMutator
    case 4111: {
        intentDetail.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, new Parcelable[0]);
        break;
    }
    // WidgetProvider_5_IntentPayloadReplacementOperatorMutator
    case 5111: {
        intentDetail.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, 0);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // WidgetProvider_6_RandomActionIntentDefinitionOperatorMutator
        case 6111: {
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
        intentDetail.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, widgetId);
        break;
    }
}
break;
}
}
android.app.PendingIntent pendingIntentDetail;
pendingIntentDetail = android.app.PendingIntent.getActivity(context, widgetId, intentDetail, it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(android.content.Intent.FLAG_ACTIVITY_NEW_TASK));
// Create an Intent to launch ListActivity
android.content.Intent intentList;
switch(MUID_STATIC) {
// WidgetProvider_7_NullIntentOperatorMutator
case 7111: {
intentList = null;
break;
}
// WidgetProvider_8_InvalidKeyIntentOperatorMutator
case 8111: {
intentList = new android.content.Intent((Context) null, it.feio.android.omninotes.MainActivity.class);
break;
}
// WidgetProvider_9_RandomActionIntentDefinitionOperatorMutator
case 9111: {
intentList = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentList = new android.content.Intent(context, it.feio.android.omninotes.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// WidgetProvider_10_RandomActionIntentDefinitionOperatorMutator
case 10111: {
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
intentList.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET_SHOW_LIST);
break;
}
}
switch(MUID_STATIC) {
// WidgetProvider_11_NullValueIntentPutExtraOperatorMutator
case 11111: {
intentList.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, new Parcelable[0]);
break;
}
// WidgetProvider_12_IntentPayloadReplacementOperatorMutator
case 12111: {
intentList.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, 0);
break;
}
default: {
switch(MUID_STATIC) {
// WidgetProvider_13_RandomActionIntentDefinitionOperatorMutator
case 13111: {
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
intentList.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, widgetId);
break;
}
}
break;
}
}
android.app.PendingIntent pendingIntentList;
pendingIntentList = android.app.PendingIntent.getActivity(context, widgetId, intentList, it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(android.content.Intent.FLAG_ACTIVITY_NEW_TASK));
// Create an Intent to launch DetailActivity to take a photo
android.content.Intent intentDetailPhoto;
switch(MUID_STATIC) {
// WidgetProvider_14_NullIntentOperatorMutator
case 14111: {
intentDetailPhoto = null;
break;
}
// WidgetProvider_15_InvalidKeyIntentOperatorMutator
case 15111: {
intentDetailPhoto = new android.content.Intent((Context) null, it.feio.android.omninotes.MainActivity.class);
break;
}
// WidgetProvider_16_RandomActionIntentDefinitionOperatorMutator
case 16111: {
intentDetailPhoto = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intentDetailPhoto = new android.content.Intent(context, it.feio.android.omninotes.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// WidgetProvider_17_RandomActionIntentDefinitionOperatorMutator
case 17111: {
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
intentDetailPhoto.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_WIDGET_TAKE_PHOTO);
break;
}
}
switch(MUID_STATIC) {
// WidgetProvider_18_NullValueIntentPutExtraOperatorMutator
case 18111: {
intentDetailPhoto.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, new Parcelable[0]);
break;
}
// WidgetProvider_19_IntentPayloadReplacementOperatorMutator
case 19111: {
intentDetailPhoto.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, 0);
break;
}
default: {
switch(MUID_STATIC) {
// WidgetProvider_20_RandomActionIntentDefinitionOperatorMutator
case 20111: {
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
intentDetailPhoto.putExtra(it.feio.android.omninotes.utils.ConstantsBase.INTENT_WIDGET, widgetId);
break;
}
}
break;
}
}
android.app.PendingIntent pendingIntentDetailPhoto;
pendingIntentDetailPhoto = android.app.PendingIntent.getActivity(context, widgetId, intentDetailPhoto, it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(android.content.Intent.FLAG_ACTIVITY_NEW_TASK));
// Check various dimensions aspect of widget to choose between layouts
boolean isSmall;
isSmall = false;
boolean isSingleLine;
isSingleLine = true;
android.os.Bundle options;
options = appWidgetManager.getAppWidgetOptions(widgetId);
// Width check
isSmall = options.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH) < 110;
// Height check
isSingleLine = options.getInt(android.appwidget.AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT) < 110;
// Creation of a map to associate PendingIntent(s) to views
android.util.SparseArray<android.app.PendingIntent> map;
map = new android.util.SparseArray<>();
map.put(it.feio.android.omninotes.R.id.list, pendingIntentList);
map.put(it.feio.android.omninotes.R.id.add, pendingIntentDetail);
map.put(it.feio.android.omninotes.R.id.camera, pendingIntentDetailPhoto);
android.widget.RemoteViews views;
views = getRemoteViews(context, widgetId, isSmall, isSingleLine, map);
// Tell the AppWidgetManager to perform an update on the current app widget
appWidgetManager.updateAppWidget(widgetId, views);
}


protected abstract android.widget.RemoteViews getRemoteViews(android.content.Context context, int widgetId, boolean isSmall, boolean isSingleLine, android.util.SparseArray<android.app.PendingIntent> pendingIntentsMap);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
