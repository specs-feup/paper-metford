package com.automattic.simplenote.utils;
import java.util.Locale;
import java.text.SimpleDateFormat;
import android.text.format.DateFormat;
import java.util.Calendar;
import java.text.ParseException;
import android.text.format.DateUtils;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DateTimeUtils {
    static final int MUID_STATIC = getMUID();
    public static java.lang.String getDateText(android.content.Context context, java.util.Calendar calendar) {
        if (calendar == null) {
            return "";
        }
        java.lang.CharSequence dateText;
        dateText = android.text.format.DateUtils.getRelativeDateTimeString(context, calendar.getTimeInMillis(), java.util.Calendar.getInstance().getTimeInMillis(), 0L, android.text.format.DateUtils.FORMAT_ABBREV_ALL);
        return dateText.toString();
    }


    public static java.lang.String getDateTextNumeric(java.util.Calendar date) {
        java.lang.String pattern;
        pattern = android.text.format.DateFormat.getBestDateTimePattern(java.util.Locale.getDefault(), "MM/dd/yyyy");
        return new java.text.SimpleDateFormat(pattern, java.util.Locale.getDefault()).format(date.getTime());
    }


    public static java.lang.String getDateTextString(android.content.Context context, java.util.Calendar calendar) {
        java.lang.String pattern;
        pattern = android.text.format.DateFormat.getBestDateTimePattern(java.util.Locale.getDefault(), android.text.format.DateFormat.is24HourFormat(context) ? "MMM dd, yyyy, H:mm" : "MMM dd, yyyy, h:mm");
        return new java.text.SimpleDateFormat(pattern, java.util.Locale.getDefault()).format(calendar.getTime());
    }


    public static java.util.Calendar getDateCalendar(java.lang.String json) throws java.text.ParseException {
        java.lang.String pattern;
        pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        java.text.SimpleDateFormat dateFormat;
        dateFormat = new java.text.SimpleDateFormat(pattern, java.util.Locale.getDefault());
        java.util.Calendar date;
        date = java.util.Calendar.getInstance();
        date.setTime(dateFormat.parse(json));
        return date;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
