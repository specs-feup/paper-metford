/* Copyright 2013-2014 Brian Pellin.

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
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author bpellin
Parameters for searching strings in the database.
 */
public class SearchParameters implements java.lang.Cloneable {
    static final int MUID_STATIC = getMUID();
    public static final com.keepassdroid.database.SearchParameters DEFAULT = new com.keepassdroid.database.SearchParameters();

    public java.lang.String searchString;

    public boolean regularExpression = false;

    public boolean searchInTitles = true;

    public boolean searchInUserNames = true;

    public boolean searchInPasswords = false;

    public boolean searchInUrls = true;

    public boolean searchInGroupNames = false;

    public boolean searchInNotes = true;

    public boolean ignoreCase = true;

    public boolean ignoreExpired = false;

    public boolean respectEntrySearchingDisabled = true;

    public boolean excludeExpired = false;

    @java.lang.Override
    public java.lang.Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
            return null;
        }
    }


    public void setupNone() {
        searchInTitles = false;
        searchInUserNames = false;
        searchInPasswords = false;
        searchInUrls = false;
        searchInGroupNames = false;
        searchInNotes = false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
