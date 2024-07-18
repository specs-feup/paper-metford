/* Copyright 2014 Brian Pellin.

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
import java.util.Locale;
import com.keepassdroid.utils.StrUtil;
import java.util.List;
import com.keepassdroid.utils.UuidUtil;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntrySearchHandlerV4 extends com.keepassdroid.database.EntrySearchHandler {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.database.SearchParametersV4 sp;

    protected EntrySearchHandlerV4(com.keepassdroid.database.SearchParameters sp, java.util.List<com.keepassdroid.database.PwEntry> listStorage) {
        super(sp, listStorage);
        this.sp = ((com.keepassdroid.database.SearchParametersV4) (sp));
    }


    @java.lang.Override
    protected boolean searchID(com.keepassdroid.database.PwEntry e) {
        com.keepassdroid.database.PwEntryV4 entry;
        entry = ((com.keepassdroid.database.PwEntryV4) (e));
        if (sp.searchInUUIDs) {
            java.lang.String hex;
            hex = com.keepassdroid.utils.UuidUtil.toHexString(entry.uuid);
            return com.keepassdroid.utils.StrUtil.indexOfIgnoreCase(hex, sp.searchString, java.util.Locale.ENGLISH) >= 0;
        }
        return false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
