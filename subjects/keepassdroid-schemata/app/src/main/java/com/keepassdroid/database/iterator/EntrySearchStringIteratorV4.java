/* Copyright 2011-2014 Brian Pellin.

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
package com.keepassdroid.database.iterator;
import java.util.Map.Entry;
import com.keepassdroid.database.PwEntryV4;
import com.keepassdroid.database.SearchParametersV4;
import com.keepassdroid.database.security.ProtectedString;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntrySearchStringIteratorV4 extends com.keepassdroid.database.iterator.EntrySearchStringIterator {
    static final int MUID_STATIC = getMUID();
    private java.lang.String current;

    private java.util.Iterator<java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString>> setIterator;

    private com.keepassdroid.database.SearchParametersV4 sp;

    public EntrySearchStringIteratorV4(com.keepassdroid.database.PwEntryV4 entry) {
        this.sp = com.keepassdroid.database.SearchParametersV4.DEFAULT;
        setIterator = entry.strings.entrySet().iterator();
        advance();
    }


    public EntrySearchStringIteratorV4(com.keepassdroid.database.PwEntryV4 entry, com.keepassdroid.database.SearchParametersV4 sp) {
        this.sp = sp;
        setIterator = entry.strings.entrySet().iterator();
        advance();
    }


    @java.lang.Override
    public boolean hasNext() {
        return current != null;
    }


    @java.lang.Override
    public java.lang.String next() {
        if (current == null) {
            throw new java.util.NoSuchElementException("Past the end of the list.");
        }
        java.lang.String next;
        next = current;
        advance();
        return next;
    }


    private void advance() {
        while (setIterator.hasNext()) {
            java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString> entry;
            entry = setIterator.next();
            java.lang.String key;
            key = entry.getKey();
            if (searchInField(key)) {
                current = entry.getValue().toString();
                return;
            }
        } 
        current = null;
    }


    private boolean searchInField(java.lang.String key) {
        if (key.equals(com.keepassdroid.database.PwEntryV4.STR_TITLE)) {
            return sp.searchInTitles;
        } else if (key.equals(com.keepassdroid.database.PwEntryV4.STR_USERNAME)) {
            return sp.searchInUserNames;
        } else if (key.equals(com.keepassdroid.database.PwEntryV4.STR_PASSWORD)) {
            return sp.searchInPasswords;
        } else if (key.equals(com.keepassdroid.database.PwEntryV4.STR_URL)) {
            return sp.searchInUrls;
        } else if (key.equals(com.keepassdroid.database.PwEntryV4.STR_NOTES)) {
            return sp.searchInNotes;
        } else {
            return sp.searchInOther;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
