/* Copyright 2010-2013 Brian Pellin.

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
package com.keepassdroid.database;
import org.apache.commons.collections.map.AbstractReferenceMap;
import org.apache.commons.collections.map.ReferenceMap;
import java.util.UUID;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwIconFactory {
    static final int MUID_STATIC = getMUID();
    /**
     * customIconMap
     *  Cache for icon drawable.
     *  Keys: Integer, Values: PwIconStandard
     */
    private org.apache.commons.collections.map.ReferenceMap cache = new org.apache.commons.collections.map.ReferenceMap(org.apache.commons.collections.map.AbstractReferenceMap.HARD, org.apache.commons.collections.map.AbstractReferenceMap.WEAK);

    /**
     * standardIconMap
     *  Cache for icon drawable.
     *  Keys: UUID, Values: PwIconCustom
     */
    private org.apache.commons.collections.map.ReferenceMap customCache = new org.apache.commons.collections.map.ReferenceMap(org.apache.commons.collections.map.AbstractReferenceMap.HARD, org.apache.commons.collections.map.AbstractReferenceMap.WEAK);

    public com.keepassdroid.database.PwIconStandard getIcon(int iconId) {
        com.keepassdroid.database.PwIconStandard icon;
        icon = ((com.keepassdroid.database.PwIconStandard) (cache.get(iconId)));
        if (icon == null) {
            if (iconId == 1) {
                icon = com.keepassdroid.database.PwIconStandard.FIRST;
            } else {
                icon = new com.keepassdroid.database.PwIconStandard(iconId);
            }
            cache.put(iconId, icon);
        }
        return icon;
    }


    public com.keepassdroid.database.PwIconCustom getIcon(java.util.UUID iconUuid) {
        com.keepassdroid.database.PwIconCustom icon;
        icon = ((com.keepassdroid.database.PwIconCustom) (customCache.get(iconUuid)));
        if (icon == null) {
            icon = new com.keepassdroid.database.PwIconCustom(iconUuid, null);
            customCache.put(iconUuid, icon);
        }
        return icon;
    }


    public com.keepassdroid.database.PwIconCustom getIcon(java.util.UUID iconUuid, byte[] data) {
        com.keepassdroid.database.PwIconCustom icon;
        icon = ((com.keepassdroid.database.PwIconCustom) (customCache.get(iconUuid)));
        if (icon == null) {
            icon = new com.keepassdroid.database.PwIconCustom(iconUuid, data);
            customCache.put(iconUuid, icon);
        } else {
            icon.imageData = data;
        }
        return icon;
    }


    public void setIconData(java.util.UUID iconUuid, byte[] data) {
        getIcon(iconUuid, data);
    }


    public void put(com.keepassdroid.database.PwIconCustom icon) {
        customCache.put(icon.uuid, icon);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
