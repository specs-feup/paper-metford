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
package it.feio.android.omninotes.helpers;
import static android.app.PendingIntent.FLAG_MUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import androidx.annotation.NonNull;
import it.feio.android.omninotes.models.Note;
import android.os.Bundle;
import android.content.Intent;
import lombok.experimental.UtilityClass;
import static android.app.PendingIntent.FLAG_IMMUTABLE;
import android.app.PendingIntent;
import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class IntentHelper {
    static final int MUID_STATIC = getMUID();
    public static android.content.Intent getNoteIntent(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    java.lang.Class target, java.lang.String action, it.feio.android.omninotes.models.Note note) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // IntentHelper_0_NullIntentOperatorMutator
            case 128: {
                intent = null;
                break;
            }
            // IntentHelper_1_InvalidKeyIntentOperatorMutator
            case 1128: {
                intent = new android.content.Intent((Context) null, target);
                break;
            }
            // IntentHelper_2_RandomActionIntentDefinitionOperatorMutator
            case 2128: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(context, target);
            break;
        }
    }
    switch(MUID_STATIC) {
        // IntentHelper_3_RandomActionIntentDefinitionOperatorMutator
        case 3128: {
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
        intent.setAction(action);
        break;
    }
}
android.os.Bundle bundle;
bundle = new android.os.Bundle();
bundle.putParcelable(it.feio.android.omninotes.utils.ConstantsBase.INTENT_NOTE, note);
switch(MUID_STATIC) {
    // IntentHelper_4_RandomActionIntentDefinitionOperatorMutator
    case 4128: {
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
    intent.putExtras(bundle);
    break;
}
}
// // Sets the Activity to start in a new, empty task
// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// // Workaround to fix problems with multiple notifications
// intent.setAction(ACTION_NOTIFICATION_CLICK + System.currentTimeMillis());
return intent;
}


public static android.app.PendingIntent getNotePendingIntent(@androidx.annotation.NonNull
android.content.Context context, @androidx.annotation.NonNull
java.lang.Class target, java.lang.String action, it.feio.android.omninotes.models.Note note) {
android.content.Intent intent;
switch(MUID_STATIC) {
// IntentHelper_5_RandomActionIntentDefinitionOperatorMutator
case 5128: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = it.feio.android.omninotes.helpers.IntentHelper.getNoteIntent(context, target, action, note);
break;
}
}
return android.app.PendingIntent.getActivity(context, it.feio.android.omninotes.helpers.IntentHelper.getUniqueRequestCode(note), intent, it.feio.android.omninotes.helpers.IntentHelper.immutablePendingIntentFlag(android.app.PendingIntent.FLAG_UPDATE_CURRENT));
}


public static int immutablePendingIntentFlag(final int flag) {
int pIntentFlags;
pIntentFlags = flag;
if (android.os.Build.VERSION.SDK_INT >= 23) {
pIntentFlags = pIntentFlags | android.app.PendingIntent.FLAG_IMMUTABLE;
}
return pIntentFlags;
}


public static int mutablePendingIntentFlag(final int flag) {
int pIntentFlags;
pIntentFlags = flag;
if (android.os.Build.VERSION.SDK_INT >= 31) {
pIntentFlags = pIntentFlags | android.app.PendingIntent.FLAG_MUTABLE;
}
return pIntentFlags;
}


static int getUniqueRequestCode(it.feio.android.omninotes.models.Note note) {
return note.get_id().intValue();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
