package com.automattic.simplenote.utils;
import com.automattic.simplenote.models.Account;
import android.util.Log;
import android.os.Handler;
import androidx.work.ListenableWorker;
import com.automattic.simplenote.Simplenote;
import android.os.Looper;
import com.automattic.simplenote.utils.AppLog.Type;
import com.simperium.client.Bucket;
import static com.automattic.simplenote.Simplenote.TEN_SECONDS_MILLIS;
import androidx.annotation.NonNull;
import com.automattic.simplenote.models.Note;
import com.google.common.util.concurrent.ListenableFuture;
import com.automattic.simplenote.models.Tag;
import androidx.annotation.Nullable;
import androidx.work.WorkerParameters;
import com.automattic.simplenote.models.Preferences;
import android.content.Context;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SyncWorker extends androidx.work.ListenableWorker {
    static final int MUID_STATIC = getMUID();
    private final com.simperium.client.Bucket<com.automattic.simplenote.models.Note> mBucketNote;

    private final com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> mBucketPreference;

    private final com.simperium.client.Bucket<com.automattic.simplenote.models.Account> mBucketAccount;

    private final com.simperium.client.Bucket<com.automattic.simplenote.models.Tag> mBucketTag;

    public SyncWorker(@androidx.annotation.NonNull
    android.content.Context context, @androidx.annotation.NonNull
    androidx.work.WorkerParameters params) {
        super(context, params);
        com.automattic.simplenote.Simplenote application;
        application = ((com.automattic.simplenote.Simplenote) (context.getApplicationContext()));
        mBucketNote = application.getNotesBucket();
        mBucketTag = application.getTagsBucket();
        mBucketPreference = application.getPreferencesBucket();
        mBucketAccount = application.getAccountBucket();
    }


    @java.lang.Override
    public void onStopped() {
        super.onStopped();
        stopBuckets("onStopped");
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public com.google.common.util.concurrent.ListenableFuture<androidx.work.ListenableWorker.Result> startWork() {
        return androidx.concurrent.futures.CallbackToFutureAdapter.getFuture(new androidx.concurrent.futures.CallbackToFutureAdapter.Resolver<androidx.work.ListenableWorker.Result>() {
            @androidx.annotation.Nullable
            @java.lang.Override
            public java.lang.Object attachCompleter(@androidx.annotation.NonNull
            final androidx.concurrent.futures.CallbackToFutureAdapter.Completer<androidx.work.ListenableWorker.Result> completer) {
                com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.NETWORK, com.automattic.simplenote.utils.NetworkUtils.getNetworkInfo(getApplicationContext()));
                if (mBucketAccount != null) {
                    mBucketAccount.start();
                    com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Started account bucket (SyncWorker)");
                }
                if (mBucketNote != null) {
                    mBucketNote.start();
                    com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Started note bucket (SyncWorker)");
                }
                if (mBucketTag != null) {
                    mBucketTag.start();
                    com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Started tag bucket (SyncWorker)");
                }
                if (mBucketPreference != null) {
                    mBucketPreference.start();
                    com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Started preference bucket (SyncWorker)");
                }
                android.util.Log.d("SyncWorker.startWork", "Started buckets");
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(new java.lang.Runnable() {
                    @java.lang.Override
                    public void run() {
                        stopBuckets("startWork");
                        completer.set(androidx.work.ListenableWorker.Result.success());
                    }

                }, com.automattic.simplenote.Simplenote.TEN_SECONDS_MILLIS);
                return null;
            }

        });
    }


    private void stopBuckets(java.lang.String method) {
        if (((com.automattic.simplenote.Simplenote) (getApplicationContext())).isInBackground()) {
            if (mBucketAccount != null) {
                mBucketAccount.stop();
                com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped account bucket (SyncWorker)");
            }
            if (mBucketNote != null) {
                mBucketNote.stop();
                com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped note bucket (SyncWorker)");
            }
            if (mBucketTag != null) {
                mBucketTag.stop();
                com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped tag bucket (SyncWorker)");
            }
            if (mBucketPreference != null) {
                mBucketPreference.stop();
                com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Stopped preference bucket (SyncWorker)");
            }
            android.util.Log.d("SyncWorker." + method, "Stopped buckets");
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
