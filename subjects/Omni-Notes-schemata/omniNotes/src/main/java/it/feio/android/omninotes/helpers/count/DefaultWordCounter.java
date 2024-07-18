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
import it.feio.android.omninotes.models.Note;
import rx.Observable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DefaultWordCounter implements it.feio.android.omninotes.helpers.count.WordCounter {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public int countWords(it.feio.android.omninotes.models.Note note) {
        int count;
        count = 0;
        java.lang.String[] fields;
        fields = new java.lang.String[]{ note.getTitle(), note.getContent() };
        for (java.lang.String field : fields) {
            field = sanitizeTextForWordsAndCharsCount(note, field);
            boolean word;
            word = false;
            int endOfLine;
            switch(MUID_STATIC) {
                // DefaultWordCounter_0_BinaryMutator
                case 121: {
                    endOfLine = field.length() + 1;
                    break;
                }
                default: {
                endOfLine = field.length() - 1;
                break;
            }
        }
        for (int i = 0; i < field.length(); i++) {
            // if the char is a letter, word = true.
            if (java.lang.Character.isLetter(field.charAt(i)) && (i != endOfLine)) {
                word = true;
                // if char isn't a letter and there have been letters before, counter goes up.
            } else if ((!java.lang.Character.isLetter(field.charAt(i))) && word) {
                count++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it  wouldn't count without this.
            } else if (java.lang.Character.isLetter(field.charAt(i)) && (i == endOfLine)) {
                count++;
            }
        }
    }
    return count;
}


@java.lang.Override
public int countChars(it.feio.android.omninotes.models.Note note) {
    java.lang.String titleAndContent;
    titleAndContent = (note.getTitle() + "\n") + note.getContent();
    return rx.Observable.from(sanitizeTextForWordsAndCharsCount(note, titleAndContent).split("")).map(java.lang.String::trim).filter((java.lang.String s) -> !s.isEmpty()).count().toBlocking().single();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
