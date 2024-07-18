package com.automattic.simplenote.analytics;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.automattic.android.tracks.TracksClient;
import org.json.JSONObject;
import java.util.UUID;
import androidx.preference.PreferenceManager;
import java.util.Map;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AnalyticsTrackerNosara implements com.automattic.simplenote.analytics.AnalyticsTracker.Tracker {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TRACKS_ANON_ID = "nosara_tracks_anon_id";

    private static final java.lang.String EVENTS_PREFIX = "spandroid_";

    private java.lang.String mUserName = null;

    private java.lang.String mAnonID = null;// do not access this variable directly. Use methods.


    private com.automattic.android.tracks.TracksClient mNosaraClient;

    private android.content.Context mContext;

    public AnalyticsTrackerNosara(android.content.Context context) {
        if (null == context) {
            mNosaraClient = null;
            return;
        }
        mContext = context;
        mNosaraClient = com.automattic.android.tracks.TracksClient.getClient(context);
    }


    private void clearAnonID() {
        mAnonID = null;
        android.content.SharedPreferences preferences;
        preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(mContext);
        if (preferences.contains(com.automattic.simplenote.analytics.AnalyticsTrackerNosara.TRACKS_ANON_ID)) {
            final android.content.SharedPreferences.Editor editor;
            editor = preferences.edit();
            editor.remove(com.automattic.simplenote.analytics.AnalyticsTrackerNosara.TRACKS_ANON_ID);
            editor.apply();
        }
    }


    private java.lang.String getAnonID() {
        if (mAnonID == null) {
            android.content.SharedPreferences preferences;
            preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(mContext);
            mAnonID = preferences.getString(com.automattic.simplenote.analytics.AnalyticsTrackerNosara.TRACKS_ANON_ID, null);
        }
        return mAnonID;
    }


    private java.lang.String generateNewAnonID() {
        java.lang.String uuid;
        uuid = java.util.UUID.randomUUID().toString();
        android.content.SharedPreferences preferences;
        preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(mContext);
        final android.content.SharedPreferences.Editor editor;
        editor = preferences.edit();
        editor.putString(com.automattic.simplenote.analytics.AnalyticsTrackerNosara.TRACKS_ANON_ID, uuid);
        editor.apply();
        mAnonID = uuid;
        return uuid;
    }


    @java.lang.Override
    public void track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat stat, java.lang.String category, java.lang.String label) {
        track(stat, category, label, null);
    }


    @java.lang.Override
    public void track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat stat, java.lang.String category, java.lang.String label, java.util.Map<java.lang.String, ?> properties) {
        if (mNosaraClient == null) {
            return;
        }
        java.lang.String eventName;
        eventName = stat.name().toLowerCase();
        final java.lang.String user;
        final com.automattic.android.tracks.TracksClient.NosaraUserType userType;
        if (mUserName != null) {
            user = mUserName;
            userType = com.automattic.android.tracks.TracksClient.NosaraUserType.SIMPLENOTE;
        } else {
            // This is just a security checks since the anonID is already available here.
            // refresh metadata is called on login/logout/startup and it loads/generates the anonId when necessary.
            if (getAnonID() == null) {
                user = generateNewAnonID();
            } else {
                user = getAnonID();
            }
            userType = com.automattic.android.tracks.TracksClient.NosaraUserType.ANON;
        }
        if (properties != null) {
            org.json.JSONObject propertiesJson;
            propertiesJson = new org.json.JSONObject(properties);
            mNosaraClient.track(com.automattic.simplenote.analytics.AnalyticsTrackerNosara.EVENTS_PREFIX + eventName, propertiesJson, user, userType);
        } else {
            mNosaraClient.track(com.automattic.simplenote.analytics.AnalyticsTrackerNosara.EVENTS_PREFIX + eventName, user, userType);
        }
    }


    @java.lang.Override
    public void refreshMetadata(java.lang.String username) {
        if (mNosaraClient == null) {
            return;
        }
        if (!android.text.TextUtils.isEmpty(username)) {
            mUserName = username;
            if (getAnonID() != null) {
                mNosaraClient.trackAliasUser(mUserName, getAnonID(), com.automattic.android.tracks.TracksClient.NosaraUserType.SIMPLENOTE);
                clearAnonID();
            }
        } else {
            mUserName = null;
            if (getAnonID() == null) {
                generateNewAnonID();
            }
        }
    }


    @java.lang.Override
    public void flush() {
        if (mNosaraClient == null) {
            return;
        }
        mNosaraClient.flush();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
