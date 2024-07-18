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
package it.feio.android.omninotes.utils;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PRETTIFIED_DATES;
import java.util.regex.Pattern;
import java.util.Locale;
import static it.feio.android.checklistview.interfaces.Constants.UNCHECKED_ENTITY;
import android.text.SpannedString;
import java.util.regex.Matcher;
import android.text.TextUtils;
import android.text.Spanned;
import lombok.experimental.UtilityClass;
import com.pixplicity.easyprefs.library.Prefs;
import android.text.Html;
import static it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM;
import it.feio.android.omninotes.helpers.date.DateHelper;
import it.feio.android.omninotes.R;
import static it.feio.android.checklistview.interfaces.Constants.CHECKED_ENTITY;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_SORTING_COLUMN;
import static it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.db.DbHelper;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class TextHelper {
    static final int MUID_STATIC = getMUID();
    public static android.text.Spanned[] parseTitleAndContent(android.content.Context mContext, it.feio.android.omninotes.models.Note note) {
        final int CONTENT_SUBSTRING_LENGTH;
        CONTENT_SUBSTRING_LENGTH = 300;
        java.lang.String titleText;
        titleText = note.getTitle();
        java.lang.String contentText;
        contentText = it.feio.android.omninotes.utils.TextHelper.limit(note.getContent().trim(), CONTENT_SUBSTRING_LENGTH, false, true);
        // Masking title and content string if note is locked
        if (java.lang.Boolean.TRUE.equals(note.isLocked()) && (!com.pixplicity.easyprefs.library.Prefs.getBoolean("settings_password_access", false))) {
            // This checks if a part of content is used as title and should be partially masked
            if ((!note.getTitle().equals(titleText)) && (titleText.length() > 3)) {
                titleText = it.feio.android.omninotes.utils.TextHelper.limit(titleText, 4, false, false);
            }
            contentText = "";
        }
        // Replacing checkmarks symbols with html entities
        android.text.Spanned contentSpanned;
        if (java.lang.Boolean.TRUE.equals(java.lang.Boolean.TRUE.equals(note.isChecklist())) && (!android.text.TextUtils.isEmpty(contentText))) {
            contentSpanned = android.text.Html.fromHtml(contentText.replace(it.feio.android.checklistview.interfaces.Constants.CHECKED_SYM, it.feio.android.checklistview.interfaces.Constants.CHECKED_ENTITY).replace(it.feio.android.checklistview.interfaces.Constants.UNCHECKED_SYM, it.feio.android.checklistview.interfaces.Constants.UNCHECKED_ENTITY).replace(java.lang.System.getProperty("line.separator"), "<br/>"));
        } else {
            contentSpanned = new android.text.SpannedString(contentText);
        }
        return new android.text.Spanned[]{ new android.text.SpannedString(titleText), contentSpanned };
    }


    private static java.lang.String limit(java.lang.String value, int length, boolean singleLine, boolean elipsize) {
        java.lang.StringBuilder buf;
        buf = new java.lang.StringBuilder(value);
        int indexNewLine;
        indexNewLine = buf.indexOf(java.lang.System.getProperty("line.separator"));
        int endIndex;
        endIndex = (singleLine && (indexNewLine < length)) ? indexNewLine : length < buf.length() ? length : -1;
        if (endIndex != (-1)) {
            buf.setLength(endIndex);
            if (elipsize) {
                buf.append("...");
            }
        }
        return buf.toString();
    }


    public static java.lang.String capitalize(java.lang.String string) {
        return string.substring(0, 1).toUpperCase(java.util.Locale.getDefault()) + string.substring(1).toLowerCase(java.util.Locale.getDefault());
    }


    /**
     * Checks if a query conditions searches for category
     *
     * @param sqlCondition
     * 		query "where" condition
     * @return Category ID
     */
    public static java.lang.String checkIntentCategory(java.lang.String sqlCondition) {
        java.lang.String pattern;
        pattern = it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY + "\\s*=\\s*([\\d]+)";
        java.util.regex.Pattern p;
        p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher;
        matcher = p.matcher(sqlCondition);
        if (matcher.find() && (matcher.group(1) != null)) {
            return matcher.group(1).trim();
        }
        return null;
    }


    /**
     * Choosing which date must be shown depending on sorting criteria
     *
     * @return String ith formatted date
     */
    public static java.lang.String getDateText(android.content.Context mContext, it.feio.android.omninotes.models.Note note, int navigation) {
        java.lang.String dateText;
        java.lang.String sort_column;
        // Reminder screen forces sorting
        if (it.feio.android.omninotes.utils.Navigation.REMINDERS == navigation) {
            sort_column = it.feio.android.omninotes.db.DbHelper.KEY_REMINDER;
        } else {
            sort_column = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_SORTING_COLUMN, "");
        }
        switch (sort_column) {
            case it.feio.android.omninotes.db.DbHelper.KEY_CREATION :
                dateText = (mContext.getString(it.feio.android.omninotes.R.string.creation) + " ") + it.feio.android.omninotes.helpers.date.DateHelper.getFormattedDate(note.getCreation(), com.pixplicity.easyprefs.library.Prefs.getBoolean(it.feio.android.omninotes.utils.ConstantsBase.PREF_PRETTIFIED_DATES, true));
                break;
            case it.feio.android.omninotes.db.DbHelper.KEY_REMINDER :
                if (note.getAlarm() == null) {
                    dateText = mContext.getString(it.feio.android.omninotes.R.string.no_reminder_set);
                } else {
                    dateText = (mContext.getString(it.feio.android.omninotes.R.string.alarm_set_on) + " ") + it.feio.android.omninotes.helpers.date.DateHelper.getDateTimeShort(mContext, java.lang.Long.parseLong(note.getAlarm()));
                }
                break;
            default :
                dateText = (mContext.getString(it.feio.android.omninotes.R.string.last_update) + " ") + it.feio.android.omninotes.helpers.date.DateHelper.getFormattedDate(note.getLastModification(), com.pixplicity.easyprefs.library.Prefs.getBoolean(it.feio.android.omninotes.utils.ConstantsBase.PREF_PRETTIFIED_DATES, true));
                break;
        }
        return dateText;
    }


    /**
     * Gets an alternative title if empty
     */
    public static java.lang.String getAlternativeTitle(android.content.Context context, it.feio.android.omninotes.models.Note note, android.text.Spanned spanned) {
        if (spanned.length() > 0) {
            return spanned.toString();
        }
        return (((context.getString(it.feio.android.omninotes.R.string.note) + " ") + context.getString(it.feio.android.omninotes.R.string.creation)) + " ") + it.feio.android.omninotes.helpers.date.DateHelper.getDateTimeShort(context, note.getCreation());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
