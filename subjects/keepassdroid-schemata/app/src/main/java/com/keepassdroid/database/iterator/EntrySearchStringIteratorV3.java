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
import com.keepassdroid.database.PwEntryV3;
import com.keepassdroid.database.SearchParameters;
import java.util.NoSuchElementException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntrySearchStringIteratorV3 extends com.keepassdroid.database.iterator.EntrySearchStringIterator {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.database.PwEntryV3 entry;

    private com.keepassdroid.database.SearchParameters sp;

    public EntrySearchStringIteratorV3(com.keepassdroid.database.PwEntryV3 entry) {
        this.entry = entry;
        this.sp = com.keepassdroid.database.SearchParameters.DEFAULT;
    }


    public EntrySearchStringIteratorV3(com.keepassdroid.database.PwEntryV3 entry, com.keepassdroid.database.SearchParameters sp) {
        this.entry = entry;
        this.sp = sp;
    }


    private static final int title = 0;

    private static final int url = 1;

    private static final int username = 2;

    private static final int comment = 3;

    private static final int maxEntries = 4;

    private int current = 0;

    @java.lang.Override
    public boolean hasNext() {
        return current < com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.maxEntries;
    }


    @java.lang.Override
    public java.lang.String next() {
        // Past the end of the list
        if (current == com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.maxEntries) {
            throw new java.util.NoSuchElementException("Past final string");
        }
        useSearchParameters();
        java.lang.String str;
        str = getCurrentString();
        current++;
        return str;
    }


    private void useSearchParameters() {
        if (sp == null) {
            return;
        }
        boolean found;
        found = false;
        while (!found) {
            switch (current) {
                case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.title :
                    found = sp.searchInTitles;
                    break;
                case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.url :
                    found = sp.searchInUrls;
                    break;
                case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.username :
                    found = sp.searchInUserNames;
                    break;
                case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.comment :
                    found = sp.searchInNotes;
                    break;
                default :
                    found = true;
            }
            if (!found) {
                current++;
            }
        } 
    }


    private java.lang.String getCurrentString() {
        switch (current) {
            case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.title :
                return entry.getTitle();
            case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.url :
                return entry.getUrl();
            case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.username :
                return entry.getUsername();
            case com.keepassdroid.database.iterator.EntrySearchStringIteratorV3.comment :
                return entry.getNotes();
            default :
                return "";
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
