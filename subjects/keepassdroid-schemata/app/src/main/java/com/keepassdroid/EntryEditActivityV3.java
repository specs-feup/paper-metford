/* Copyright 2013 Brian Pellin.

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
package com.keepassdroid;
import com.keepassdroid.database.PwGroupId;
import com.keepassdroid.database.PwGroupIdV3;
import com.keepassdroid.database.PwGroupV3;
import com.keepassdroid.app.App;
import android.content.Intent;
import com.keepassdroid.database.PwEntry;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntryEditActivityV3 extends com.keepassdroid.EntryEditActivity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected com.keepassdroid.database.PwEntry populateNewEntry(com.keepassdroid.database.PwEntry entry) {
        com.keepassdroid.database.PwEntry newEntry;
        newEntry = super.populateNewEntry(entry);
        if (mSelectedIconID == (-1)) {
            if (mIsNew) {
                newEntry.icon = com.keepassdroid.app.App.getDB().pm.iconFactory.getIcon(0);
            } else {
                //  Keep previous icon, if no new one was selected
                newEntry.icon = mEntry.icon;
            }
        } else {
            newEntry.icon = com.keepassdroid.app.App.getDB().pm.iconFactory.getIcon(mSelectedIconID);
        }
        return newEntry;
    }


    protected static void putParentId(android.content.Intent i, java.lang.String parentKey, com.keepassdroid.database.PwGroupV3 parent) {
        switch(MUID_STATIC) {
            // EntryEditActivityV3_0_NullValueIntentPutExtraOperatorMutator
            case 216: {
                i.putExtra(parentKey, new Parcelable[0]);
                break;
            }
            // EntryEditActivityV3_1_IntentPayloadReplacementOperatorMutator
            case 1216: {
                i.putExtra(parentKey, 0);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // EntryEditActivityV3_2_RandomActionIntentDefinitionOperatorMutator
                case 2216: {
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
                i.putExtra(parentKey, parent.groupId);
                break;
            }
        }
        break;
    }
}
}


@java.lang.Override
protected com.keepassdroid.database.PwGroupId getParentGroupId(android.content.Intent i, java.lang.String key) {
int groupId;
groupId = i.getIntExtra(key, -1);
return new com.keepassdroid.database.PwGroupIdV3(groupId);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
