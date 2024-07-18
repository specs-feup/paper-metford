package com.automattic.simplenote.models;
import org.json.JSONException;
import java.util.ArrayList;
import com.simperium.client.BucketObject;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import com.simperium.client.BucketSchema;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Preferences extends com.simperium.client.BucketObject {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String BUCKET_NAME = "preferences";

    public static final java.lang.String PREFERENCES_OBJECT_KEY = "preferences-key";

    public static final int MAX_RECENT_SEARCHES = 5;

    private static final java.lang.String ANALYTICS_ENABLED_KEY = "analytics_enabled";

    private static final java.lang.String RECENT_SEARCHES_KEY = "recent_searches";

    private static final java.lang.String SUBSCRIPTION_LEVEL_KEY = "subscription_level";

    private static final java.lang.String SUBSCRIPTION_PLATFORM_KEY = "subscription_platform";

    private static final java.lang.String SUBSCRIPTION_DATE_KEY = "subscription_date";

    private Preferences(java.lang.String key, org.json.JSONObject properties) {
        super(key, properties);
    }


    public boolean getAnalyticsEnabled() {
        java.lang.Object isEnabled;
        isEnabled = getProperty(com.automattic.simplenote.models.Preferences.ANALYTICS_ENABLED_KEY);
        if (isEnabled == null) {
            return true;
        }
        if (isEnabled instanceof java.lang.Boolean) {
            return ((java.lang.Boolean) (isEnabled));
        } else {
            // Simperium-iOS sets booleans as integer values (0 or 1)
            return (isEnabled instanceof java.lang.Integer) && (((java.lang.Integer) (isEnabled)) > 0);
        }
    }


    public java.util.ArrayList<java.lang.String> getRecentSearches() {
        java.lang.Object object;
        object = getProperty(com.automattic.simplenote.models.Preferences.RECENT_SEARCHES_KEY);
        if (object instanceof org.json.JSONArray) {
            org.json.JSONArray recents;
            recents = ((org.json.JSONArray) (object));
            java.util.ArrayList<java.lang.String> recentsList;
            recentsList = new java.util.ArrayList<>(recents.length());
            for (int i = 0; i < recents.length(); i++) {
                java.lang.String recent;
                recent = recents.optString(i);
                if (!recent.isEmpty()) {
                    recentsList.add(recent);
                }
            }
            return recentsList;
        } else {
            return new java.util.ArrayList<>();
        }
    }


    public void setAnalyticsEnabled(boolean enabled) {
        try {
            getProperties().put(com.automattic.simplenote.models.Preferences.ANALYTICS_ENABLED_KEY, enabled);
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }


    public void setRecentSearches(java.util.List<java.lang.String> recents) {
        if (recents == null) {
            recents = new java.util.ArrayList<>();
        }
        setProperty(com.automattic.simplenote.models.Preferences.RECENT_SEARCHES_KEY, new org.json.JSONArray(recents));
    }


    public void setActiveSubscription(long purchaseTime) {
        setSubscriptionPlatform(com.automattic.simplenote.models.Preferences.SubscriptionPlatform.ANDROID);
        setSubscriptionLevel(com.automattic.simplenote.models.Preferences.SubscriptionLevel.SUSTAINER);
        setSubscriptionDate(purchaseTime);
        save();
    }


    public void removeActiveSubscription() {
        setSubscriptionPlatform(com.automattic.simplenote.models.Preferences.SubscriptionPlatform.NONE);
        setSubscriptionLevel(com.automattic.simplenote.models.Preferences.SubscriptionLevel.NONE);
        setSubscriptionDate(null);
        save();
    }


    public com.automattic.simplenote.models.Preferences.SubscriptionPlatform getCurrentSubscriptionPlatform() {
        java.lang.Object subscriptionPlatform;
        subscriptionPlatform = getProperty(com.automattic.simplenote.models.Preferences.SUBSCRIPTION_PLATFORM_KEY);
        if (subscriptionPlatform == null) {
            return null;
        }
        if (subscriptionPlatform instanceof java.lang.String) {
            return com.automattic.simplenote.models.Preferences.SubscriptionPlatform.fromString(((java.lang.String) (subscriptionPlatform)));
        } else {
            return null;
        }
    }


    public void setSubscriptionDate(java.lang.Long subscriptionDate) {
        setProperty(com.automattic.simplenote.models.Preferences.SUBSCRIPTION_DATE_KEY, subscriptionDate);
    }


    public void setSubscriptionPlatform(com.automattic.simplenote.models.Preferences.SubscriptionPlatform subscriptionPlatform) {
        setProperty(com.automattic.simplenote.models.Preferences.SUBSCRIPTION_PLATFORM_KEY, subscriptionPlatform.platformName);
    }


    public void setSubscriptionLevel(com.automattic.simplenote.models.Preferences.SubscriptionLevel subscriptionLevel) {
        setProperty(com.automattic.simplenote.models.Preferences.SUBSCRIPTION_LEVEL_KEY, subscriptionLevel.getName());
    }


    public static class Schema extends com.simperium.client.BucketSchema<com.automattic.simplenote.models.Preferences> {
        public Schema() {
            autoIndex();
        }


        public java.lang.String getRemoteName() {
            return com.automattic.simplenote.models.Preferences.BUCKET_NAME;
        }


        public com.automattic.simplenote.models.Preferences build(java.lang.String key, org.json.JSONObject properties) {
            return new com.automattic.simplenote.models.Preferences(key, properties);
        }


        public void update(com.automattic.simplenote.models.Preferences prefs, org.json.JSONObject properties) {
            prefs.setProperties(properties);
        }

    }

    public enum SubscriptionPlatform {

        ANDROID("android"),
        IOS("iOS"),
        WEB("WEB"),
        NONE(null);
        private final java.lang.String platformName;

        SubscriptionPlatform(final java.lang.String platform) {
            this.platformName = platform;
        }


        public java.lang.String getName() {
            return platformName;
        }


        public static com.automattic.simplenote.models.Preferences.SubscriptionPlatform fromString(java.lang.String platformName) {
            if (platformName != null) {
                for (com.automattic.simplenote.models.Preferences.SubscriptionPlatform platform : com.automattic.simplenote.models.Preferences.SubscriptionPlatform.values()) {
                    if (platformName.equalsIgnoreCase(platform.getName())) {
                        return platform;
                    }
                }
            }
            return null;
        }

    }

    public enum SubscriptionLevel {

        SUSTAINER("sustainer"),
        NONE(null);
        private final java.lang.String subscriptionLevel;

        SubscriptionLevel(final java.lang.String level) {
            this.subscriptionLevel = level;
        }


        public java.lang.String getName() {
            return subscriptionLevel;
        }


        public static com.automattic.simplenote.models.Preferences.SubscriptionLevel fromString(java.lang.String level) {
            if (level != null) {
                for (com.automattic.simplenote.models.Preferences.SubscriptionLevel subscriptionLevel : com.automattic.simplenote.models.Preferences.SubscriptionLevel.values()) {
                    if (level.equalsIgnoreCase(subscriptionLevel.getName())) {
                        return subscriptionLevel;
                    }
                }
            }
            return null;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
