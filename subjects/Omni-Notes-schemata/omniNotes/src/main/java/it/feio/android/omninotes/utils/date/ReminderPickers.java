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
package it.feio.android.omninotes.utils.date;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import it.feio.android.omninotes.helpers.date.RecurrenceHelper;
import it.feio.android.omninotes.models.listeners.OnReminderPickedListener;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import java.util.Calendar;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import androidx.fragment.app.FragmentActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ReminderPickers {
    static final int MUID_STATIC = getMUID();
    private androidx.fragment.app.FragmentActivity mActivity;

    private it.feio.android.omninotes.models.listeners.OnReminderPickedListener mOnReminderPickedListener;

    public ReminderPickers(androidx.fragment.app.FragmentActivity mActivity, it.feio.android.omninotes.models.listeners.OnReminderPickedListener mOnReminderPickedListener) {
        this.mActivity = mActivity;
        this.mOnReminderPickedListener = mOnReminderPickedListener;
    }


    public void pick(java.lang.Long presetDateTime, java.lang.String recurrenceRule) {
        showDateTimeSelectors(it.feio.android.omninotes.utils.date.DateUtils.getCalendar(presetDateTime), recurrenceRule);
    }


    /**
     * Show date and time pickers
     */
    private void showDateTimeSelectors(java.util.Calendar reminder, java.lang.String recurrenceRule) {
        it.feio.android.omninotes.utils.date.SublimePickerFragment pickerFrag;
        pickerFrag = new it.feio.android.omninotes.utils.date.SublimePickerFragment();
        pickerFrag.setCallback(new it.feio.android.omninotes.utils.date.SublimePickerFragment.Callback() {
            @java.lang.Override
            public void onCancelled() {
                // Nothing to do
            }


            @java.lang.Override
            public void onDateTimeRecurrenceSet(com.appeaser.sublimepickerlibrary.datepicker.SelectedDate selectedDate, int hourOfDay, int minute, com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption recurrenceOption, java.lang.String recurrenceRule) {
                java.util.Calendar reminder;
                reminder = selectedDate.getFirstDate();
                reminder.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
                reminder.set(java.util.Calendar.MINUTE, minute);
                mOnReminderPickedListener.onReminderPicked(reminder.getTimeInMillis());
                mOnReminderPickedListener.onRecurrenceReminderPicked(it.feio.android.omninotes.helpers.date.RecurrenceHelper.buildRecurrenceRuleByRecurrenceOptionAndRule(recurrenceOption, recurrenceRule));
            }

        });
        int displayOptions;
        displayOptions = 0;
        displayOptions |= com.appeaser.sublimepickerlibrary.helpers.SublimeOptions.ACTIVATE_DATE_PICKER;
        displayOptions |= com.appeaser.sublimepickerlibrary.helpers.SublimeOptions.ACTIVATE_TIME_PICKER;
        displayOptions |= com.appeaser.sublimepickerlibrary.helpers.SublimeOptions.ACTIVATE_RECURRENCE_PICKER;
        com.appeaser.sublimepickerlibrary.helpers.SublimeOptions sublimeOptions;
        sublimeOptions = new com.appeaser.sublimepickerlibrary.helpers.SublimeOptions();
        sublimeOptions.setPickerToShow(com.appeaser.sublimepickerlibrary.helpers.SublimeOptions.Picker.TIME_PICKER);
        sublimeOptions.setDisplayOptions(displayOptions);
        sublimeOptions.setDateParams(reminder);
        sublimeOptions.setRecurrenceParams(com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption.CUSTOM, recurrenceRule);
        sublimeOptions.setTimeParams(reminder.get(java.util.Calendar.HOUR_OF_DAY), reminder.get(java.util.Calendar.MINUTE), it.feio.android.omninotes.utils.date.DateUtils.is24HourMode(mActivity));
        android.os.Bundle bundle;
        bundle = new android.os.Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", sublimeOptions);
        pickerFrag.setArguments(bundle);
        pickerFrag.setStyle(androidx.fragment.app.DialogFragment.STYLE_NO_TITLE, 0);
        pickerFrag.show(mActivity.getSupportFragmentManager(), "SUBLIME_PICKER");
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
