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
package it.feio.android.omninotes;
import static it.feio.android.omninotes.utils.ConstantsBase.ACTION_SHORTCUT_WIDGET;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ShortcutActivity extends android.app.Activity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // ShortcutActivity_0_LengthyGUICreationOperatorMutator
            case 137: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    android.content.Intent shortcutIntent;
    switch(MUID_STATIC) {
        // ShortcutActivity_1_NullIntentOperatorMutator
        case 1137: {
            shortcutIntent = null;
            break;
        }
        // ShortcutActivity_2_InvalidKeyIntentOperatorMutator
        case 2137: {
            shortcutIntent = new android.content.Intent((ShortcutActivity) null, it.feio.android.omninotes.MainActivity.class);
            break;
        }
        // ShortcutActivity_3_RandomActionIntentDefinitionOperatorMutator
        case 3137: {
            shortcutIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        shortcutIntent = new android.content.Intent(this, it.feio.android.omninotes.MainActivity.class);
        break;
    }
}
switch(MUID_STATIC) {
    // ShortcutActivity_4_RandomActionIntentDefinitionOperatorMutator
    case 4137: {
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
    shortcutIntent.setAction(it.feio.android.omninotes.utils.ConstantsBase.ACTION_SHORTCUT_WIDGET);
    break;
}
}
android.content.Intent.ShortcutIconResource iconResource;
iconResource = android.content.Intent.ShortcutIconResource.fromContext(this, it.feio.android.omninotes.R.drawable.shortcut_icon);
android.content.Intent intent;
switch(MUID_STATIC) {
// ShortcutActivity_5_NullIntentOperatorMutator
case 5137: {
    intent = null;
    break;
}
// ShortcutActivity_6_RandomActionIntentDefinitionOperatorMutator
case 6137: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// ShortcutActivity_7_NullValueIntentPutExtraOperatorMutator
case 7137: {
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_INTENT, new Parcelable[0]);
break;
}
// ShortcutActivity_8_IntentPayloadReplacementOperatorMutator
case 8137: {
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_INTENT, (android.content.Intent) null);
break;
}
default: {
switch(MUID_STATIC) {
// ShortcutActivity_9_RandomActionIntentDefinitionOperatorMutator
case 9137: {
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
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ShortcutActivity_10_NullValueIntentPutExtraOperatorMutator
case 10137: {
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_NAME, new Parcelable[0]);
break;
}
// ShortcutActivity_11_IntentPayloadReplacementOperatorMutator
case 11137: {
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_NAME, "");
break;
}
default: {
switch(MUID_STATIC) {
// ShortcutActivity_12_RandomActionIntentDefinitionOperatorMutator
case 12137: {
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
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_NAME, getString(it.feio.android.omninotes.R.string.add_note));
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// ShortcutActivity_13_NullValueIntentPutExtraOperatorMutator
case 13137: {
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_ICON_RESOURCE, new Parcelable[0]);
break;
}
// ShortcutActivity_14_IntentPayloadReplacementOperatorMutator
case 14137: {
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_ICON_RESOURCE, (android.content.Intent.ShortcutIconResource) null);
break;
}
default: {
switch(MUID_STATIC) {
// ShortcutActivity_15_RandomActionIntentDefinitionOperatorMutator
case 15137: {
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
intent.putExtra(android.content.Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);
break;
}
}
break;
}
}
setResult(android.app.Activity.RESULT_OK, intent);
finish();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
