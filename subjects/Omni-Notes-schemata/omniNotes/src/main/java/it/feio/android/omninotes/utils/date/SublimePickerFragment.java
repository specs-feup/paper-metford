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
import java.util.Locale;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.fragment.app.DialogFragment;
import android.view.View;
import com.appeaser.sublimepickerlibrary.SublimePicker;
import java.util.TimeZone;
import it.feio.android.omninotes.R;
import android.view.LayoutInflater;
import java.text.DateFormat;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import androidx.annotation.NonNull;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import androidx.annotation.Nullable;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SublimePickerFragment extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    java.text.DateFormat mDateFormatter;

    java.text.DateFormat mTimeFormatter;

    com.appeaser.sublimepickerlibrary.SublimePicker mSublimePicker;

    it.feio.android.omninotes.utils.date.SublimePickerFragment.Callback mCallback;

    com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter mListener = new com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter() {
        @java.lang.Override
        public void onCancelled() {
            if (mCallback != null) {
                mCallback.onCancelled();
            }
            dismiss();
        }


        @java.lang.Override
        public void onDateTimeRecurrenceSet(com.appeaser.sublimepickerlibrary.SublimePicker sublimeMaterialPicker, com.appeaser.sublimepickerlibrary.datepicker.SelectedDate selectedDate, int hourOfDay, int minute, com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption recurrenceOption, java.lang.String recurrenceRule) {
            if (mCallback != null) {
                mCallback.onDateTimeRecurrenceSet(selectedDate, hourOfDay, minute, recurrenceOption, recurrenceRule);
            }
            dismiss();
        }

    };

    public SublimePickerFragment() {
        mDateFormatter = java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM, java.util.Locale.getDefault());
        mTimeFormatter = java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT, java.util.Locale.getDefault());
        mTimeFormatter.setTimeZone(java.util.TimeZone.getTimeZone("GMT+0"));
    }


    public void setCallback(it.feio.android.omninotes.utils.date.SublimePickerFragment.Callback callback) {
        mCallback = callback;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        mSublimePicker = ((com.appeaser.sublimepickerlibrary.SublimePicker) (getActivity().getLayoutInflater().inflate(it.feio.android.omninotes.R.layout.sublime_picker, container)));
        android.os.Bundle arguments;
        arguments = getArguments();
        com.appeaser.sublimepickerlibrary.helpers.SublimeOptions options;
        options = null;
        if (arguments != null) {
            options = arguments.getParcelable("SUBLIME_OPTIONS");
        }
        mSublimePicker.initializePicker(options, mListener);
        return mSublimePicker;
    }


    public interface Callback {
        void onCancelled();


        void onDateTimeRecurrenceSet(com.appeaser.sublimepickerlibrary.datepicker.SelectedDate selectedDate, int hourOfDay, int minute, com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption recurrenceOption, java.lang.String recurrenceRule);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
