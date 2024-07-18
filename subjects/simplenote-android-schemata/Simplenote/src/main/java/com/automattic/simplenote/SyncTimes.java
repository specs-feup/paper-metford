package com.automattic.simplenote;
import java.util.Set;
import android.util.Log;
import com.simperium.client.Bucket;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Map;
import java.util.HashSet;
import com.simperium.client.Syncable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SyncTimes<T extends com.simperium.client.Syncable> {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = com.automattic.simplenote.SyncTimes.class.getSimpleName();

    private final java.util.HashMap<java.lang.String, java.util.Calendar> mSyncTimes = new java.util.HashMap<>();

    private final java.util.HashSet<java.lang.String> mUnsyncedKeys = new java.util.HashSet<>();

    private final java.util.Set<com.automattic.simplenote.SyncTimes.SyncTimeListener> mListeners = new java.util.HashSet<>();

    public SyncTimes(java.util.Map<java.lang.String, java.util.Calendar> syncTimes) {
        mSyncTimes.putAll(syncTimes);
    }


    public java.util.Calendar getLastSyncTime(java.lang.String key) {
        return mSyncTimes.get(key);
    }


    public boolean isSynced(java.lang.String key) {
        return !mUnsyncedKeys.contains(key);
    }


    public void addListener(com.automattic.simplenote.SyncTimes.SyncTimeListener listener) {
        mListeners.add(listener);
    }


    public void removeListener(com.automattic.simplenote.SyncTimes.SyncTimeListener listener) {
        mListeners.remove(listener);
    }


    private void notifyUpdate(java.lang.String entityId) {
        for (com.automattic.simplenote.SyncTimes.SyncTimeListener listener : mListeners) {
            listener.onUpdate(entityId, getLastSyncTime(entityId), isSynced(entityId));
        }
    }


    private void notifyRemove(java.lang.String entityId) {
        for (com.automattic.simplenote.SyncTimes.SyncTimeListener listener : mListeners) {
            listener.onRemove(entityId);
        }
    }


    private void updateSyncTime(java.lang.String entityId) {
        java.util.Calendar now;
        now = java.util.Calendar.getInstance();
        mSyncTimes.put(entityId, now);
        android.util.Log.d(com.automattic.simplenote.SyncTimes.TAG, ((("updateSyncTime: " + entityId) + " (") + now.getTime()) + ")");
    }


    public com.simperium.client.Bucket.Listener<T> bucketListener = new com.simperium.client.Bucket.Listener<T>() {
        @java.lang.Override
        public void onBeforeUpdateObject(com.simperium.client.Bucket<T> bucket, T object) {
        }


        @java.lang.Override
        public void onDeleteObject(com.simperium.client.Bucket<T> bucket, T object) {
            mSyncTimes.remove(object.getSimperiumKey());
            notifyRemove(object.getSimperiumKey());
        }


        @java.lang.Override
        public void onLocalQueueChange(com.simperium.client.Bucket<T> bucket, java.util.Set<java.lang.String> entityIds) {
            java.util.Set<java.lang.String> changed;
            changed = new java.util.HashSet<>();
            for (java.lang.String entityId : mUnsyncedKeys) {
                if (!entityIds.contains(entityId)) {
                    changed.add(entityId);
                }
            }
            for (java.lang.String entityId : entityIds) {
                if (!mUnsyncedKeys.contains(entityId)) {
                    changed.add(entityId);
                }
            }
            mUnsyncedKeys.clear();
            mUnsyncedKeys.addAll(entityIds);
            for (java.lang.String entityId : changed) {
                android.util.Log.d(com.automattic.simplenote.SyncTimes.TAG, ((("updateIsSynced: " + entityId) + " (") + isSynced(entityId)) + ")");
                notifyUpdate(entityId);
            }
        }


        @java.lang.Override
        public void onNetworkChange(com.simperium.client.Bucket<T> bucket, com.simperium.client.Bucket.ChangeType type, java.lang.String entityId) {
            if (entityId == null) {
                return;
            }
            if (type == com.simperium.client.Bucket.ChangeType.REMOVE) {
                mSyncTimes.remove(entityId);
                notifyRemove(entityId);
            } else {
                updateSyncTime(entityId);
                notifyUpdate(entityId);
            }
        }


        @java.lang.Override
        public void onSaveObject(com.simperium.client.Bucket<T> bucket, T object) {
        }


        @java.lang.Override
        public void onSyncObject(com.simperium.client.Bucket<T> bucket, java.lang.String noteId) {
            updateSyncTime(noteId);
            notifyUpdate(noteId);
        }

    };

    interface SyncTimeListener {
        void onRemove(java.lang.String entityId);


        void onUpdate(java.lang.String entityId, java.util.Calendar lastSyncTime, boolean isSynced);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
