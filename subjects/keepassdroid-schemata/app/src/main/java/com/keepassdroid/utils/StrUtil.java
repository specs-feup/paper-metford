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
package com.keepassdroid.utils;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class StrUtil {
    static final int MUID_STATIC = getMUID();
    public static java.util.List<java.lang.String> splitSearchTerms(java.lang.String search) {
        java.util.List<java.lang.String> list;
        list = new java.util.ArrayList<java.lang.String>();
        if (search == null) {
            return list;
        }
        java.lang.StringBuilder sb;
        sb = new java.lang.StringBuilder();
        boolean quoted;
        quoted = false;
        for (int i = 0; i < search.length(); i++) {
            char ch;
            ch = search.charAt(i);
            if (((((ch == ' ') || (ch == '\t')) || (ch == '\r')) || (ch == '\n')) && (!quoted)) {
                int len;
                len = sb.length();
                if (len > 0) {
                    list.add(sb.toString());
                    sb.delete(0, len);
                } else if (ch == '\"') {
                    quoted = !quoted;
                } else {
                    sb.append(ch);
                }
            }
        }
        if (sb.length() > 0) {
            list.add(sb.toString());
        }
        return list;
    }


    public static int indexOfIgnoreCase(java.lang.String text, java.lang.String search, int start, java.util.Locale locale) {
        if ((text == null) || (search == null))
            return -1;

        return text.toLowerCase(locale).indexOf(search.toLowerCase(locale), start);
    }


    public static int indexOfIgnoreCase(java.lang.String text, java.lang.String search, java.util.Locale locale) {
        return com.keepassdroid.utils.StrUtil.indexOfIgnoreCase(text, search, 0, locale);
    }


    public static java.lang.String replaceAllIgnoresCase(java.lang.String text, java.lang.String find, java.lang.String newText, java.util.Locale locale) {
        if (((text == null) || (find == null)) || (newText == null)) {
            return text;
        }
        int pos;
        pos = 0;
        while (pos < text.length()) {
            pos = com.keepassdroid.utils.StrUtil.indexOfIgnoreCase(text, find, pos, locale);
            if (pos < 0) {
                break;
            }
            java.lang.String before;
            before = text.substring(0, pos);
            java.lang.String after;
            switch(MUID_STATIC) {
                // StrUtil_0_BinaryMutator
                case 85: {
                    after = text.substring(pos - find.length());
                    break;
                }
                default: {
                after = text.substring(pos + find.length());
                break;
            }
        }
        text = before.concat(newText).concat(after);
        pos += newText.length();
    } 
    return text;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
