package com.automattic.simplenote.utils;
import static com.automattic.simplenote.Simplenote.SYNC_TIME_PREFERENCES;
import android.content.SharedPreferences;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import com.automattic.simplenote.utils.AppLog.Type;
import static com.automattic.simplenote.Simplenote.SCROLL_POSITION_PREFERENCES;
import java.nio.charset.StandardCharsets;
import android.net.Uri;
import android.util.Base64;
import androidx.preference.PreferenceManager;
import com.automattic.simplenote.Simplenote;
import org.wordpress.passcodelock.AppLockManager;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AuthUtils {
    static final int MUID_STATIC = getMUID();
    public static void logOut(com.automattic.simplenote.Simplenote application) {
        application.getSimperium().deauthorizeUser();
        application.getAccountBucket().reset();
        application.getNotesBucket().reset();
        application.getTagsBucket().reset();
        application.getPreferencesBucket().reset();
        application.getAccountBucket().stop();
        com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped account bucket (AuthUtils)");
        application.getNotesBucket().stop();
        com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped note bucket (AuthUtils)");
        application.getTagsBucket().stop();
        com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped tag bucket (AuthUtils)");
        application.getPreferencesBucket().stop();
        com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped preference bucket (AuthUtils)");
        // Resets analytics user back to 'anon' type
        com.automattic.simplenote.analytics.AnalyticsTracker.refreshMetadata(null);
        // Remove wp.com token
        android.content.SharedPreferences.Editor editor;
        editor = androidx.preference.PreferenceManager.getDefaultSharedPreferences(application).edit();
        editor.remove(com.automattic.simplenote.utils.PrefUtils.PREF_WP_TOKEN);
        // Remove WordPress sites
        editor.remove(com.automattic.simplenote.utils.PrefUtils.PREF_WORDPRESS_SITES);
        editor.apply();
        // Remove note scroll positions
        application.getSharedPreferences(com.automattic.simplenote.Simplenote.SCROLL_POSITION_PREFERENCES, android.content.Context.MODE_PRIVATE).edit().clear().apply();
        // Remove note last sync times
        application.getSharedPreferences(com.automattic.simplenote.Simplenote.SYNC_TIME_PREFERENCES, android.content.Context.MODE_PRIVATE).edit().clear().apply();
        // Remove Passcode Lock password
        org.wordpress.passcodelock.AppLockManager.getInstance().getAppLock().setPassword("");
        com.automattic.simplenote.utils.WidgetUtils.updateNoteWidgets(application);
    }


    public static void magicLinkLogin(com.automattic.simplenote.Simplenote application, android.net.Uri uri) {
        java.lang.String userEmail;
        userEmail = com.automattic.simplenote.utils.AuthUtils.extractEmailFromMagicLink(uri);
        java.lang.String spToken;
        spToken = uri.getQueryParameter("token");
        application.loginWithToken(userEmail, spToken);
    }


    public static java.lang.String extractEmailFromMagicLink(android.net.Uri uri) {
        java.lang.String userEmailEncoded;
        userEmailEncoded = uri.getQueryParameter("email");
        return new java.lang.String(android.util.Base64.decode(userEmailEncoded, android.util.Base64.NO_WRAP), java.nio.charset.StandardCharsets.UTF_8);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
