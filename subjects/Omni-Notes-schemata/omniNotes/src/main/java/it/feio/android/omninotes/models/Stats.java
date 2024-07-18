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
package it.feio.android.omninotes.models;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Stats extends it.feio.android.omninotes.models.StatsSingleNote {
    static final int MUID_STATIC = getMUID();
    private int notesActive;

    private int notesArchived;

    private int notesTrashed;

    private int reminders;

    private int remindersFutures;

    private int notesChecklist;

    private int notesMasked;

    private int categories;

    private int location;

    private int wordsMax;

    private int wordsAvg;

    private int charsMax;

    private int charsAvg;

    private long usageTime;

    public int getNotesTotalNumber() {
        switch(MUID_STATIC) {
            // Stats_0_BinaryMutator
            case 75: {
                return (notesActive + notesArchived) - notesTrashed;
            }
            default: {
            switch(MUID_STATIC) {
                // Stats_1_BinaryMutator
                case 1075: {
                    return (notesActive - notesArchived) + notesTrashed;
                }
                default: {
                return (notesActive + notesArchived) + notesTrashed;
                }
        }
        }
}
}


public int getNotesActive() {
return notesActive;
}


public void setNotesActive(int notesActive) {
this.notesActive = notesActive;
}


public int getNotesArchived() {
return notesArchived;
}


public void setNotesArchived(int notesArchived) {
this.notesArchived = notesArchived;
}


public int getNotesTrashed() {
return notesTrashed;
}


public void setNotesTrashed(int notesTrashed) {
this.notesTrashed = notesTrashed;
}


public int getReminders() {
return reminders;
}


public void setReminders(int reminders) {
this.reminders = reminders;
}


public int getRemindersFutures() {
return remindersFutures;
}


public void setRemindersFutures(int remindersFutures) {
this.remindersFutures = remindersFutures;
}


public int getNotesChecklist() {
return notesChecklist;
}


public void setNotesChecklist(int notesChecklist) {
this.notesChecklist = notesChecklist;
}


public int getNotesMasked() {
return notesMasked;
}


public void setNotesMasked(int notesMasked) {
this.notesMasked = notesMasked;
}


public int getCategories() {
return categories;
}


public void setCategories(int categories) {
this.categories = categories;
}


@java.lang.Override
public int getTags() {
return tags;
}


@java.lang.Override
public void setTags(int tags) {
this.tags = tags;
}


@java.lang.Override
public int getWords() {
return words;
}


@java.lang.Override
public void setWords(int words) {
this.words = words;
}


@java.lang.Override
public int getChars() {
return chars;
}


@java.lang.Override
public void setChars(int chars) {
this.chars = chars;
}


public int getWordsMax() {
return wordsMax;
}


public void setWordsMax(int wordsMax) {
this.wordsMax = wordsMax;
}


public int getWordsAvg() {
return wordsAvg;
}


public void setWordsAvg(int wordsAvg) {
this.wordsAvg = wordsAvg;
}


public int getCharsMax() {
return charsMax;
}


public void setCharsMax(int charsMax) {
this.charsMax = charsMax;
}


public int getCharsAvg() {
return charsAvg;
}


public void setCharsAvg(int charsAvg) {
this.charsAvg = charsAvg;
}


public int getLocation() {
return location;
}


public void setLocation(int location) {
this.location = location;
}


public long getUsageTime() {
return usageTime;
}


public void setUsageTime(long usageTime) {
this.usageTime = usageTime;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
