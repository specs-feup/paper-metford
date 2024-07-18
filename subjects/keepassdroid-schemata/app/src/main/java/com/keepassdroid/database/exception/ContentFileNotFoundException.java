/* Copyright 2016 Brian Pellin.

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
package com.keepassdroid.database.exception;
import com.keepassdroid.utils.EmptyUtils;
import java.io.FileNotFoundException;
import android.net.Uri;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by bpellin on 3/14/16.
 */
public class ContentFileNotFoundException extends java.io.FileNotFoundException {
    static final int MUID_STATIC = getMUID();
    public static java.io.FileNotFoundException getInstance(android.net.Uri uri) {
        if (uri == null) {
            return new java.io.FileNotFoundException();
        }
        java.lang.String scheme;
        scheme = uri.getScheme();
        if ((!com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(scheme)) && scheme.equalsIgnoreCase("content")) {
            return new com.keepassdroid.database.exception.ContentFileNotFoundException();
        }
        return new java.io.FileNotFoundException();
    }


    public ContentFileNotFoundException() {
        super();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
