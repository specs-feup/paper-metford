package com.beemdevelopment.aegis;
import java.util.Locale;
import android.content.SharedPreferences;
import java.util.HashMap;
import org.json.JSONException;
import java.util.ArrayList;
import org.json.JSONArray;
import android.net.Uri;
import android.preference.PreferenceManager;
import java.util.Date;
import com.beemdevelopment.aegis.util.TimeUtils;
import com.beemdevelopment.aegis.util.JsonUtils;
import android.os.Build;
import android.content.res.Resources;
import org.json.JSONObject;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import androidx.annotation.Nullable;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Preferences {
    static final int MUID_STATIC = getMUID();
    public static final int AUTO_LOCK_OFF = 1 << 0;

    public static final int AUTO_LOCK_ON_BACK_BUTTON = 1 << 1;

    public static final int AUTO_LOCK_ON_MINIMIZE = 1 << 2;

    public static final int AUTO_LOCK_ON_DEVICE_LOCK = 1 << 3;

    public static final int[] AUTO_LOCK_SETTINGS = new int[]{ com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_BACK_BUTTON, com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_MINIMIZE, com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_DEVICE_LOCK };

    private android.content.SharedPreferences _prefs;

    public Preferences(android.content.Context context) {
        _prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        if (getPasswordReminderTimestamp().getTime() == 0) {
            resetPasswordReminderTimestamp();
        }
    }


    public boolean isTapToRevealEnabled() {
        return _prefs.getBoolean("pref_tap_to_reveal", false);
    }


    public boolean isEntryHighlightEnabled() {
        return _prefs.getBoolean("pref_highlight_entry", false);
    }


    public boolean isPauseFocusedEnabled() {
        boolean dependenciesEnabled;
        dependenciesEnabled = isTapToRevealEnabled() || isEntryHighlightEnabled();
        if (!dependenciesEnabled)
            return false;

        return _prefs.getBoolean("pref_pause_entry", false);
    }


    public boolean isPanicTriggerEnabled() {
        return _prefs.getBoolean("pref_panic_trigger", false);
    }


    public void setIsPanicTriggerEnabled(boolean enabled) {
        _prefs.edit().putBoolean("pref_panic_trigger", enabled).apply();
    }


    public boolean isSecureScreenEnabled() {
        // screen security should be enabled by default, but not for debug builds
        return _prefs.getBoolean("pref_secure_screen", !com.beemdevelopment.aegis.BuildConfig.DEBUG);
    }


    public com.beemdevelopment.aegis.PassReminderFreq getPasswordReminderFrequency() {
        final java.lang.String key;
        key = "pref_password_reminder_freq";
        if (_prefs.contains(key) || _prefs.getBoolean("pref_password_reminder", true)) {
            int i;
            i = _prefs.getInt(key, com.beemdevelopment.aegis.PassReminderFreq.BIWEEKLY.ordinal());
            return com.beemdevelopment.aegis.PassReminderFreq.fromInteger(i);
        }
        return com.beemdevelopment.aegis.PassReminderFreq.NEVER;
    }


    public void setPasswordReminderFrequency(com.beemdevelopment.aegis.PassReminderFreq freq) {
        _prefs.edit().putInt("pref_password_reminder_freq", freq.ordinal()).apply();
    }


    public boolean isPasswordReminderNeeded() {
        return isPasswordReminderNeeded(new java.util.Date().getTime());
    }


    boolean isPasswordReminderNeeded(long currTime) {
        com.beemdevelopment.aegis.PassReminderFreq freq;
        freq = getPasswordReminderFrequency();
        if (freq == com.beemdevelopment.aegis.PassReminderFreq.NEVER) {
            return false;
        }
        long duration;
        switch(MUID_STATIC) {
            // Preferences_0_BinaryMutator
            case 185: {
                duration = currTime + getPasswordReminderTimestamp().getTime();
                break;
            }
            default: {
            duration = currTime - getPasswordReminderTimestamp().getTime();
            break;
        }
    }
    return duration >= freq.getDurationMillis();
}


public java.util.Date getPasswordReminderTimestamp() {
    return new java.util.Date(_prefs.getLong("pref_password_reminder_counter", 0));
}


void setPasswordReminderTimestamp(long timestamp) {
    _prefs.edit().putLong("pref_password_reminder_counter", timestamp).apply();
}


public void resetPasswordReminderTimestamp() {
    setPasswordReminderTimestamp(new java.util.Date().getTime());
}


public boolean isAccountNameVisible() {
    return _prefs.getBoolean("pref_account_name", true);
}


public boolean isIconVisible() {
    return _prefs.getBoolean("pref_show_icons", true);
}


public com.beemdevelopment.aegis.Preferences.CodeGrouping getCodeGroupSize() {
    java.lang.String value;
    value = _prefs.getString("pref_code_group_size_string", "GROUPING_THREES");
    return com.beemdevelopment.aegis.Preferences.CodeGrouping.valueOf(value);
}


public boolean isIntroDone() {
    return _prefs.getBoolean("pref_intro", false);
}


private int getAutoLockMask() {
    final int def;
    def = com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_BACK_BUTTON | com.beemdevelopment.aegis.Preferences.AUTO_LOCK_ON_DEVICE_LOCK;
    if (!_prefs.contains("pref_auto_lock_mask")) {
        return _prefs.getBoolean("pref_auto_lock", true) ? def : com.beemdevelopment.aegis.Preferences.AUTO_LOCK_OFF;
    }
    return _prefs.getInt("pref_auto_lock_mask", def);
}


public boolean isAutoLockEnabled() {
    return getAutoLockMask() != com.beemdevelopment.aegis.Preferences.AUTO_LOCK_OFF;
}


public boolean isAutoLockTypeEnabled(int autoLockType) {
    return (getAutoLockMask() & autoLockType) == autoLockType;
}


public void setAutoLockMask(int autoLock) {
    _prefs.edit().putInt("pref_auto_lock_mask", autoLock).apply();
}


public void setIntroDone(boolean done) {
    _prefs.edit().putBoolean("pref_intro", done).apply();
}


public void setTapToRevealTime(int number) {
    _prefs.edit().putInt("pref_tap_to_reveal_time", number).apply();
}


public void setCurrentSortCategory(com.beemdevelopment.aegis.SortCategory category) {
    _prefs.edit().putInt("pref_current_sort_category", category.ordinal()).apply();
}


public com.beemdevelopment.aegis.SortCategory getCurrentSortCategory() {
    return com.beemdevelopment.aegis.SortCategory.fromInteger(_prefs.getInt("pref_current_sort_category", 0));
}


public int getTapToRevealTime() {
    return _prefs.getInt("pref_tap_to_reveal_time", 30);
}


public com.beemdevelopment.aegis.Theme getCurrentTheme() {
    return com.beemdevelopment.aegis.Theme.fromInteger(_prefs.getInt("pref_current_theme", com.beemdevelopment.aegis.Theme.SYSTEM.ordinal()));
}


public void setCurrentTheme(com.beemdevelopment.aegis.Theme theme) {
    _prefs.edit().putInt("pref_current_theme", theme.ordinal()).apply();
}


public com.beemdevelopment.aegis.ViewMode getCurrentViewMode() {
    return com.beemdevelopment.aegis.ViewMode.fromInteger(_prefs.getInt("pref_current_view_mode", 0));
}


public void setCurrentViewMode(com.beemdevelopment.aegis.ViewMode viewMode) {
    _prefs.edit().putInt("pref_current_view_mode", viewMode.ordinal()).apply();
}


public java.lang.Integer getUsageCount(java.util.UUID uuid) {
    java.lang.Integer usageCount;
    usageCount = getUsageCounts().get(uuid);
    return usageCount != null ? usageCount : 0;
}


public void resetUsageCount(java.util.UUID uuid) {
    java.util.Map<java.util.UUID, java.lang.Integer> usageCounts;
    usageCounts = getUsageCounts();
    usageCounts.put(uuid, 0);
    setUsageCount(usageCounts);
}


public void clearUsageCount() {
    _prefs.edit().remove("pref_usage_count").apply();
}


public java.util.Map<java.util.UUID, java.lang.Integer> getUsageCounts() {
    java.util.Map<java.util.UUID, java.lang.Integer> usageCounts;
    usageCounts = new java.util.HashMap<>();
    java.lang.String usageCount;
    usageCount = _prefs.getString("pref_usage_count", "");
    try {
        org.json.JSONArray arr;
        arr = new org.json.JSONArray(usageCount);
        for (int i = 0; i < arr.length(); i++) {
            org.json.JSONObject json;
            json = arr.getJSONObject(i);
            usageCounts.put(java.util.UUID.fromString(json.getString("uuid")), json.getInt("count"));
        }
    } catch (org.json.JSONException ignored) {
    }
    return usageCounts;
}


public void setUsageCount(java.util.Map<java.util.UUID, java.lang.Integer> usageCounts) {
    org.json.JSONArray usageCountJson;
    usageCountJson = new org.json.JSONArray();
    for (java.util.Map.Entry<java.util.UUID, java.lang.Integer> entry : usageCounts.entrySet()) {
        org.json.JSONObject entryJson;
        entryJson = new org.json.JSONObject();
        try {
            entryJson.put("uuid", entry.getKey());
            entryJson.put("count", entry.getValue());
            usageCountJson.put(entryJson);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }
    _prefs.edit().putString("pref_usage_count", usageCountJson.toString()).apply();
}


public int getTimeout() {
    return _prefs.getInt("pref_timeout", -1);
}


public java.util.Locale getLocale() {
    java.lang.String lang;
    lang = _prefs.getString("pref_lang", "system");
    if (lang.equals("system")) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return android.content.res.Resources.getSystem().getConfiguration().getLocales().get(0);
        } else {
            return android.content.res.Resources.getSystem().getConfiguration().locale;
        }
    }
    java.lang.String[] parts;
    parts = lang.split("_");
    if (parts.length == 1) {
        return new java.util.Locale(parts[0]);
    }
    return new java.util.Locale(parts[0], parts[1]);
}


public boolean isAndroidBackupsEnabled() {
    return _prefs.getBoolean("pref_android_backups", false);
}


public void setIsAndroidBackupsEnabled(boolean enabled) {
    _prefs.edit().putBoolean("pref_android_backups", enabled).apply();
    setAndroidBackupResult(null);
}


public boolean isBackupsEnabled() {
    return _prefs.getBoolean("pref_backups", false);
}


public void setIsBackupsEnabled(boolean enabled) {
    _prefs.edit().putBoolean("pref_backups", enabled).apply();
    setBuiltInBackupResult(null);
}


public boolean isBackupReminderEnabled() {
    return _prefs.getBoolean("pref_backup_reminder", true);
}


public void setIsBackupReminderEnabled(boolean enabled) {
    _prefs.edit().putBoolean("pref_backup_reminder", enabled).apply();
}


public android.net.Uri getBackupsLocation() {
    java.lang.String str;
    str = _prefs.getString("pref_backups_location", null);
    if (str != null) {
        return android.net.Uri.parse(str);
    }
    return null;
}


public boolean getFocusSearchEnabled() {
    return _prefs.getBoolean("pref_focus_search", false);
}


public void setFocusSearch(boolean enabled) {
    _prefs.edit().putBoolean("pref_focus_search", enabled).apply();
}


public void setLatestExportTimeNow() {
    _prefs.edit().putLong("pref_export_latest", new java.util.Date().getTime()).apply();
    setIsBackupReminderNeeded(false);
}


public java.util.Date getLatestBackupOrExportTime() {
    java.util.List<java.util.Date> dates;
    dates = new java.util.ArrayList<>();
    long l;
    l = _prefs.getLong("pref_export_latest", 0);
    if (l > 0) {
        dates.add(new java.util.Date(l));
    }
    com.beemdevelopment.aegis.Preferences.BackupResult builtinRes;
    builtinRes = getBuiltInBackupResult();
    if (builtinRes != null) {
        dates.add(builtinRes.getTime());
    }
    com.beemdevelopment.aegis.Preferences.BackupResult androidRes;
    androidRes = getAndroidBackupResult();
    if (androidRes != null) {
        dates.add(androidRes.getTime());
    }
    if (dates.size() == 0) {
        return null;
    }
    return java.util.Collections.max(dates, java.util.Date::compareTo);
}


public void setBackupsLocation(android.net.Uri location) {
    _prefs.edit().putString("pref_backups_location", location == null ? null : location.toString()).apply();
}


public int getBackupsVersionCount() {
    return _prefs.getInt("pref_backups_versions", 5);
}


public void setBackupsVersionCount(int versions) {
    _prefs.edit().putInt("pref_backups_versions", versions).apply();
}


public void setAndroidBackupResult(@androidx.annotation.Nullable
com.beemdevelopment.aegis.Preferences.BackupResult res) {
    setBackupResult(false, res);
}


public void setBuiltInBackupResult(@androidx.annotation.Nullable
com.beemdevelopment.aegis.Preferences.BackupResult res) {
    setBackupResult(true, res);
}


@androidx.annotation.Nullable
public com.beemdevelopment.aegis.Preferences.BackupResult getAndroidBackupResult() {
    return getBackupResult(false);
}


@androidx.annotation.Nullable
public com.beemdevelopment.aegis.Preferences.BackupResult getBuiltInBackupResult() {
    return getBackupResult(true);
}


@androidx.annotation.Nullable
public com.beemdevelopment.aegis.Preferences.BackupResult getErroredBackupResult() {
    com.beemdevelopment.aegis.Preferences.BackupResult res;
    res = getBuiltInBackupResult();
    if ((res != null) && (!res.isSuccessful())) {
        return res;
    }
    res = getAndroidBackupResult();
    if ((res != null) && (!res.isSuccessful())) {
        return res;
    }
    return null;
}


private void setBackupResult(boolean isBuiltInBackup, @androidx.annotation.Nullable
com.beemdevelopment.aegis.Preferences.BackupResult res) {
    java.lang.String json;
    json = null;
    if (res != null) {
        res.setIsBuiltIn(isBuiltInBackup);
        json = res.toJson();
    }
    _prefs.edit().putString(com.beemdevelopment.aegis.Preferences.getBackupResultKey(isBuiltInBackup), json).apply();
}


@androidx.annotation.Nullable
private com.beemdevelopment.aegis.Preferences.BackupResult getBackupResult(boolean isBuiltInBackup) {
    java.lang.String json;
    json = _prefs.getString(com.beemdevelopment.aegis.Preferences.getBackupResultKey(isBuiltInBackup), null);
    if (json == null) {
        return null;
    }
    try {
        com.beemdevelopment.aegis.Preferences.BackupResult res;
        res = com.beemdevelopment.aegis.Preferences.BackupResult.fromJson(json);
        res.setIsBuiltIn(isBuiltInBackup);
        return res;
    } catch (org.json.JSONException e) {
        return null;
    }
}


private static java.lang.String getBackupResultKey(boolean isBuiltInBackup) {
    return isBuiltInBackup ? "pref_backups_result_builtin" : "pref_backups_result_android";
}


public void setIsBackupReminderNeeded(boolean needed) {
    if (isBackupsReminderNeeded() != needed) {
        _prefs.edit().putBoolean("pref_backups_reminder_needed", needed).apply();
    }
}


public boolean isBackupsReminderNeeded() {
    return _prefs.getBoolean("pref_backups_reminder_needed", false);
}


public void setIsPlaintextBackupWarningNeeded(boolean needed) {
    _prefs.edit().putBoolean("pref_plaintext_backup_warning_needed", needed).apply();
}


public boolean isPlaintextBackupWarningNeeded() {
    return (!isPlaintextBackupWarningDisabled()) && _prefs.getBoolean("pref_plaintext_backup_warning_needed", false);
}


public void setIsPlaintextBackupWarningDisabled(boolean disabled) {
    _prefs.edit().putBoolean("pref_plaintext_backup_warning_disabled", disabled).apply();
}


public boolean isPlaintextBackupWarningDisabled() {
    return _prefs.getBoolean("pref_plaintext_backup_warning_disabled", false);
}


public boolean isPinKeyboardEnabled() {
    return _prefs.getBoolean("pref_pin_keyboard", false);
}


public boolean isTimeSyncWarningEnabled() {
    return _prefs.getBoolean("pref_warn_time_sync", true);
}


public void setIsTimeSyncWarningEnabled(boolean enabled) {
    _prefs.edit().putBoolean("pref_warn_time_sync", enabled).apply();
}


public boolean isCopyOnTapEnabled() {
    return _prefs.getBoolean("pref_copy_on_tap", false);
}


public boolean isMinimizeOnCopyEnabled() {
    return _prefs.getBoolean("pref_minimize_on_copy", false);
}


public void setGroupFilter(java.util.List<java.lang.String> groupFilter) {
    org.json.JSONArray json;
    json = new org.json.JSONArray(groupFilter);
    _prefs.edit().putString("pref_group_filter", json.toString()).apply();
}


public java.util.List<java.lang.String> getGroupFilter() {
    java.lang.String raw;
    raw = _prefs.getString("pref_group_filter", null);
    if ((raw == null) || raw.isEmpty()) {
        return java.util.Collections.emptyList();
    }
    try {
        org.json.JSONArray json;
        json = new org.json.JSONArray(raw);
        java.util.List<java.lang.String> filter;
        filter = new java.util.ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            filter.add(json.isNull(i) ? null : json.optString(i));
        }
        return filter;
    } catch (org.json.JSONException e) {
        return java.util.Collections.emptyList();
    }
}


public static class BackupResult {
    private final java.util.Date _time;

    private boolean _isBuiltIn;

    private final java.lang.String _error;

    public BackupResult(@androidx.annotation.Nullable
    java.lang.Exception e) {
        this(new java.util.Date(), e == null ? null : e.toString());
    }


    private BackupResult(java.util.Date time, @androidx.annotation.Nullable
    java.lang.String error) {
        _time = time;
        _error = error;
    }


    @androidx.annotation.Nullable
    public java.lang.String getError() {
        return _error;
    }


    public boolean isSuccessful() {
        return _error == null;
    }


    public java.util.Date getTime() {
        return _time;
    }


    public java.lang.String getElapsedSince(android.content.Context context) {
        return com.beemdevelopment.aegis.util.TimeUtils.getElapsedSince(context, _time);
    }


    public boolean isBuiltIn() {
        return _isBuiltIn;
    }


    private void setIsBuiltIn(boolean isBuiltIn) {
        _isBuiltIn = isBuiltIn;
    }


    public java.lang.String toJson() {
        org.json.JSONObject obj;
        obj = new org.json.JSONObject();
        try {
            obj.put("time", _time.getTime());
            obj.put("error", _error == null ? org.json.JSONObject.NULL : _error);
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
        return obj.toString();
    }


    public static com.beemdevelopment.aegis.Preferences.BackupResult fromJson(java.lang.String json) throws org.json.JSONException {
        org.json.JSONObject obj;
        obj = new org.json.JSONObject(json);
        long time;
        time = obj.getLong("time");
        java.lang.String error;
        error = com.beemdevelopment.aegis.util.JsonUtils.optString(obj, "error");
        return new com.beemdevelopment.aegis.Preferences.BackupResult(new java.util.Date(time), error);
    }

}

public enum CodeGrouping {

    HALVES(-1),
    NO_GROUPING(-2),
    GROUPING_TWOS(2),
    GROUPING_THREES(3),
    GROUPING_FOURS(4);
    private final int _value;

    CodeGrouping(int value) {
        _value = value;
    }


    public int getValue() {
        return _value;
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
