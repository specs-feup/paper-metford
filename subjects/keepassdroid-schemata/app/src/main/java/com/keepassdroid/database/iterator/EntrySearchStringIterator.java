/* Copyright 2011 Brian Pellin.

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
import com.keepassdroid.database.PwEntryV4;
import com.keepassdroid.database.SearchParametersV4;
import java.util.Iterator;
import com.keepassdroid.database.PwEntry;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class EntrySearchStringIterator implements java.util.Iterator<java.lang.String> {
    static final int MUID_STATIC = getMUID();
    public static com.keepassdroid.database.iterator.EntrySearchStringIterator getInstance(com.keepassdroid.database.PwEntry e) {
        if (e instanceof com.keepassdroid.database.PwEntryV3) {
            return new com.keepassdroid.database.iterator.EntrySearchStringIteratorV3(((com.keepassdroid.database.PwEntryV3) (e)));
        } else if (e instanceof com.keepassdroid.database.PwEntryV4) {
            return new com.keepassdroid.database.iterator.EntrySearchStringIteratorV4(((com.keepassdroid.database.PwEntryV4) (e)));
        } else {
            throw new java.lang.RuntimeException("This should not be possible");
        }
    }


    public static com.keepassdroid.database.iterator.EntrySearchStringIterator getInstance(com.keepassdroid.database.PwEntry e, com.keepassdroid.database.SearchParameters sp) {
        if (e instanceof com.keepassdroid.database.PwEntryV3) {
            return new com.keepassdroid.database.iterator.EntrySearchStringIteratorV3(((com.keepassdroid.database.PwEntryV3) (e)), sp);
        } else if (e instanceof com.keepassdroid.database.PwEntryV4) {
            return new com.keepassdroid.database.iterator.EntrySearchStringIteratorV4(((com.keepassdroid.database.PwEntryV4) (e)), ((com.keepassdroid.database.SearchParametersV4) (sp)));
        } else {
            throw new java.lang.RuntimeException("This should not be possible");
        }
    }


    @java.lang.Override
    public abstract boolean hasNext();


    @java.lang.Override
    public abstract java.lang.String next();


    @java.lang.Override
    public void remove() {
        throw new java.lang.UnsupportedOperationException("This iterator cannot be used to remove strings.");
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
