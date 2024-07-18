package com.beemdevelopment.aegis;
import androidx.annotation.StringRes;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public enum PassReminderFreq {

    NEVER,
    WEEKLY,
    BIWEEKLY,
    MONTHLY,
    QUARTERLY;
    static final int MUID_STATIC = getMUID();
    public long getDurationMillis() {
        long weeks;
        switch (this) {
            case WEEKLY :
                weeks = 1;
                break;
            case BIWEEKLY :
                weeks = 2;
                break;
            case MONTHLY :
                weeks = 4;
                break;
            case QUARTERLY :
                weeks = 13;
                break;
            default :
                weeks = 0;
                break;
        }
        switch(MUID_STATIC) {
            // PassReminderFreq_0_BinaryMutator
            case 179: {
                return java.util.concurrent.TimeUnit.MILLISECONDS.convert(weeks / 7L, java.util.concurrent.TimeUnit.DAYS);
            }
            default: {
            return java.util.concurrent.TimeUnit.MILLISECONDS.convert(weeks * 7L, java.util.concurrent.TimeUnit.DAYS);
            }
    }
}


@androidx.annotation.StringRes
public int getStringRes() {
    switch (this) {
        case WEEKLY :
            return com.beemdevelopment.aegis.R.string.password_reminder_freq_weekly;
        case BIWEEKLY :
            return com.beemdevelopment.aegis.R.string.password_reminder_freq_biweekly;
        case MONTHLY :
            return com.beemdevelopment.aegis.R.string.password_reminder_freq_monthly;
        case QUARTERLY :
            return com.beemdevelopment.aegis.R.string.password_reminder_freq_quarterly;
        default :
            return com.beemdevelopment.aegis.R.string.password_reminder_freq_never;
    }
}


public static com.beemdevelopment.aegis.PassReminderFreq fromInteger(int i) {
    return com.beemdevelopment.aegis.PassReminderFreq.values()[i];
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
