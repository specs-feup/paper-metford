package com.automattic.simplenote.utils;
import android.appwidget.AppWidgetManager;
import com.automattic.simplenote.NoteListWidgetDark;
import com.automattic.simplenote.NoteListWidgetLight;
import com.automattic.simplenote.NoteWidgetDark;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import com.automattic.simplenote.NoteWidgetLight;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class WidgetUtils {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_LIST_WIDGET_CLICK = "key_list_widget_click";

    public static final java.lang.String KEY_WIDGET_CLICK = "key_widget_click";

    public static final int MINIMUM_HEIGHT_FOR_BUTTON = 150;

    public static final int MINIMUM_WIDTH_FOR_BUTTON = 300;

    public static void updateNoteWidgets(android.content.Context context) {
        android.appwidget.AppWidgetManager appWidgetManager;
        appWidgetManager = android.appwidget.AppWidgetManager.getInstance(context);
        int[] idsDark;
        idsDark = appWidgetManager.getAppWidgetIds(new android.content.ComponentName(context, com.automattic.simplenote.NoteWidgetDark.class));
        android.content.Intent updateIntentDark;
        switch(MUID_STATIC) {
            // WidgetUtils_0_NullIntentOperatorMutator
            case 46: {
                updateIntentDark = null;
                break;
            }
            // WidgetUtils_1_RandomActionIntentDefinitionOperatorMutator
            case 146: {
                updateIntentDark = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            updateIntentDark = new android.content.Intent();
            break;
        }
    }
    switch(MUID_STATIC) {
        // WidgetUtils_2_RandomActionIntentDefinitionOperatorMutator
        case 246: {
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
        updateIntentDark.setAction(android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        break;
    }
}
switch(MUID_STATIC) {
    // WidgetUtils_3_NullValueIntentPutExtraOperatorMutator
    case 346: {
        updateIntentDark.putExtra(com.automattic.simplenote.NoteWidgetDark.KEY_WIDGET_IDS_DARK, new Parcelable[0]);
        break;
    }
    // WidgetUtils_4_IntentPayloadReplacementOperatorMutator
    case 446: {
        updateIntentDark.putExtra(com.automattic.simplenote.NoteWidgetDark.KEY_WIDGET_IDS_DARK, (int[]) null);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // WidgetUtils_5_RandomActionIntentDefinitionOperatorMutator
        case 546: {
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
        updateIntentDark.putExtra(com.automattic.simplenote.NoteWidgetDark.KEY_WIDGET_IDS_DARK, idsDark);
        break;
    }
}
break;
}
}
context.sendBroadcast(updateIntentDark);
int[] idsLight;
idsLight = appWidgetManager.getAppWidgetIds(new android.content.ComponentName(context, com.automattic.simplenote.NoteWidgetLight.class));
android.content.Intent updateIntentLight;
switch(MUID_STATIC) {
// WidgetUtils_6_NullIntentOperatorMutator
case 646: {
updateIntentLight = null;
break;
}
// WidgetUtils_7_RandomActionIntentDefinitionOperatorMutator
case 746: {
updateIntentLight = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
updateIntentLight = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// WidgetUtils_8_RandomActionIntentDefinitionOperatorMutator
case 846: {
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
updateIntentLight.setAction(android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE);
break;
}
}
switch(MUID_STATIC) {
// WidgetUtils_9_NullValueIntentPutExtraOperatorMutator
case 946: {
updateIntentLight.putExtra(com.automattic.simplenote.NoteWidgetLight.KEY_WIDGET_IDS_LIGHT, new Parcelable[0]);
break;
}
// WidgetUtils_10_IntentPayloadReplacementOperatorMutator
case 1046: {
updateIntentLight.putExtra(com.automattic.simplenote.NoteWidgetLight.KEY_WIDGET_IDS_LIGHT, (int[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// WidgetUtils_11_RandomActionIntentDefinitionOperatorMutator
case 1146: {
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
updateIntentLight.putExtra(com.automattic.simplenote.NoteWidgetLight.KEY_WIDGET_IDS_LIGHT, idsLight);
break;
}
}
break;
}
}
context.sendBroadcast(updateIntentLight);
int[] idsListDark;
idsListDark = appWidgetManager.getAppWidgetIds(new android.content.ComponentName(context, com.automattic.simplenote.NoteListWidgetDark.class));
android.content.Intent updateIntentListDark;
switch(MUID_STATIC) {
// WidgetUtils_12_NullIntentOperatorMutator
case 1246: {
updateIntentListDark = null;
break;
}
// WidgetUtils_13_RandomActionIntentDefinitionOperatorMutator
case 1346: {
updateIntentListDark = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
updateIntentListDark = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// WidgetUtils_14_RandomActionIntentDefinitionOperatorMutator
case 1446: {
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
updateIntentListDark.setAction(android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE);
break;
}
}
switch(MUID_STATIC) {
// WidgetUtils_15_NullValueIntentPutExtraOperatorMutator
case 1546: {
updateIntentListDark.putExtra(com.automattic.simplenote.NoteListWidgetDark.KEY_LIST_WIDGET_IDS_DARK, new Parcelable[0]);
break;
}
// WidgetUtils_16_IntentPayloadReplacementOperatorMutator
case 1646: {
updateIntentListDark.putExtra(com.automattic.simplenote.NoteListWidgetDark.KEY_LIST_WIDGET_IDS_DARK, (int[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// WidgetUtils_17_RandomActionIntentDefinitionOperatorMutator
case 1746: {
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
updateIntentListDark.putExtra(com.automattic.simplenote.NoteListWidgetDark.KEY_LIST_WIDGET_IDS_DARK, idsListDark);
break;
}
}
break;
}
}
context.sendBroadcast(updateIntentListDark);
int[] idsListLight;
idsListLight = appWidgetManager.getAppWidgetIds(new android.content.ComponentName(context, com.automattic.simplenote.NoteListWidgetLight.class));
android.content.Intent updateIntentListLight;
switch(MUID_STATIC) {
// WidgetUtils_18_NullIntentOperatorMutator
case 1846: {
updateIntentListLight = null;
break;
}
// WidgetUtils_19_RandomActionIntentDefinitionOperatorMutator
case 1946: {
updateIntentListLight = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
updateIntentListLight = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// WidgetUtils_20_RandomActionIntentDefinitionOperatorMutator
case 2046: {
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
updateIntentListLight.setAction(android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE);
break;
}
}
switch(MUID_STATIC) {
// WidgetUtils_21_NullValueIntentPutExtraOperatorMutator
case 2146: {
updateIntentListLight.putExtra(com.automattic.simplenote.NoteListWidgetLight.KEY_LIST_WIDGET_IDS_LIGHT, new Parcelable[0]);
break;
}
// WidgetUtils_22_IntentPayloadReplacementOperatorMutator
case 2246: {
updateIntentListLight.putExtra(com.automattic.simplenote.NoteListWidgetLight.KEY_LIST_WIDGET_IDS_LIGHT, (int[]) null);
break;
}
default: {
switch(MUID_STATIC) {
// WidgetUtils_23_RandomActionIntentDefinitionOperatorMutator
case 2346: {
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
updateIntentListLight.putExtra(com.automattic.simplenote.NoteListWidgetLight.KEY_LIST_WIDGET_IDS_LIGHT, idsListLight);
break;
}
}
break;
}
}
context.sendBroadcast(updateIntentListLight);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
