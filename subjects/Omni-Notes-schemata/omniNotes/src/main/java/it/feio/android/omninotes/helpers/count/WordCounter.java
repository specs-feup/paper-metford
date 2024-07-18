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
package it.feio.android.omninotes.helpers.count;
import java.util.regex.Pattern;
import it.feio.android.omninotes.models.Note;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public interface WordCounter {
    static final int MUID_STATIC = getMUID();
    int countWords(it.feio.android.omninotes.models.Note note);


    int countChars(it.feio.android.omninotes.models.Note note);


    default java.lang.String sanitizeTextForWordsAndCharsCount(it.feio.android.omninotes.models.Note note, java.lang.String field) {
        if (java.lang.Boolean.TRUE.equals(note.isChecklist())) {
            java.lang.String regex;
            regex = ((("(" + java.util.regex.Pattern.quote(it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM)) + "|") + java.util.regex.Pattern.quote(it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM)) + ")";
            field = field.replaceAll(regex, "");
        }
        return field;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
