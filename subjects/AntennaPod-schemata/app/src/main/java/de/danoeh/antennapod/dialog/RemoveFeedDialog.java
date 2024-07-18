package de.danoeh.antennapod.dialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Completable;
import android.util.Log;
import android.content.DialogInterface;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.core.dialog.ConfirmationDialog;
import android.app.ProgressDialog;
import de.danoeh.antennapod.R;
import java.util.List;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.model.feed.Feed;
import android.content.Context;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class RemoveFeedDialog {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "RemoveFeedDialog";

    public static void show(android.content.Context context, de.danoeh.antennapod.model.feed.Feed feed, @androidx.annotation.Nullable
    java.lang.Runnable callback) {
        java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds;
        feeds = java.util.Collections.singletonList(feed);
        java.lang.String message;
        message = de.danoeh.antennapod.dialog.RemoveFeedDialog.getMessageId(context, feeds);
        de.danoeh.antennapod.dialog.RemoveFeedDialog.showDialog(context, feeds, message, callback);
    }


    public static void show(android.content.Context context, java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds) {
        java.lang.String message;
        message = de.danoeh.antennapod.dialog.RemoveFeedDialog.getMessageId(context, feeds);
        de.danoeh.antennapod.dialog.RemoveFeedDialog.showDialog(context, feeds, message, null);
    }


    private static void showDialog(android.content.Context context, java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds, java.lang.String message, @androidx.annotation.Nullable
    java.lang.Runnable callback) {
        de.danoeh.antennapod.core.dialog.ConfirmationDialog dialog;
        dialog = new de.danoeh.antennapod.core.dialog.ConfirmationDialog(context, de.danoeh.antennapod.R.string.remove_feed_label, message) {
            @java.lang.Override
            public void onConfirmButtonPressed(android.content.DialogInterface clickedDialog) {
                if (callback != null) {
                    callback.run();
                }
                clickedDialog.dismiss();
                android.app.ProgressDialog progressDialog;
                progressDialog = new android.app.ProgressDialog(context);
                progressDialog.setMessage(context.getString(de.danoeh.antennapod.R.string.feed_remover_msg));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
                io.reactivex.Completable.fromAction(() -> {
                    for (de.danoeh.antennapod.model.feed.Feed feed : feeds) {
                        de.danoeh.antennapod.core.storage.DBWriter.deleteFeed(context, feed.getId()).get();
                    }
                }).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> {
                    android.util.Log.d(de.danoeh.antennapod.dialog.RemoveFeedDialog.TAG, "Feed(s) deleted");
                    progressDialog.dismiss();
                }, (java.lang.Throwable error) -> {
                    android.util.Log.e(de.danoeh.antennapod.dialog.RemoveFeedDialog.TAG, android.util.Log.getStackTraceString(error));
                    progressDialog.dismiss();
                });
            }

        };
        dialog.createNewDialog().show();
    }


    private static java.lang.String getMessageId(android.content.Context context, java.util.List<de.danoeh.antennapod.model.feed.Feed> feeds) {
        if (feeds.size() == 1) {
            if (feeds.get(0).isLocalFeed()) {
                return context.getString(de.danoeh.antennapod.R.string.feed_delete_confirmation_local_msg, feeds.get(0).getTitle());
            } else {
                return context.getString(de.danoeh.antennapod.R.string.feed_delete_confirmation_msg, feeds.get(0).getTitle());
            }
        } else {
            return context.getString(de.danoeh.antennapod.R.string.feed_delete_confirmation_msg_batch);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
