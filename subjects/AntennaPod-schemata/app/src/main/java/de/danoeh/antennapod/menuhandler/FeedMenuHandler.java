package de.danoeh.antennapod.menuhandler;
import de.danoeh.antennapod.core.util.ShareUtils;
import android.content.DialogInterface;
import de.danoeh.antennapod.core.storage.DBWriter;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.core.dialog.ConfirmationDialog;
import de.danoeh.antennapod.dialog.IntraFeedSortDialog;
import java.util.concurrent.Callable;
import de.danoeh.antennapod.dialog.RenameItemDialog;
import de.danoeh.antennapod.R;
import java.util.concurrent.ExecutionException;
import androidx.annotation.NonNull;
import org.apache.commons.lang3.StringUtils;
import de.danoeh.antennapod.model.feed.Feed;
import java.util.Collections;
import io.reactivex.android.schedulers.AndroidSchedulers;
import android.util.Log;
import android.view.Menu;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.model.feed.SortOrder;
import de.danoeh.antennapod.dialog.TagSettingsDialog;
import io.reactivex.Observable;
import android.view.MenuItem;
import de.danoeh.antennapod.core.util.IntentUtils;
import de.danoeh.antennapod.dialog.RemoveFeedDialog;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import android.annotation.SuppressLint;
import java.util.concurrent.Future;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Handles interactions with the FeedItemMenu.
 */
public class FeedMenuHandler {
    static final int MUID_STATIC = getMUID();
    private FeedMenuHandler() {
    }


    private static final java.lang.String TAG = "FeedMenuHandler";

    public static boolean onPrepareOptionsMenu(android.view.Menu menu, de.danoeh.antennapod.model.feed.Feed selectedFeed) {
        if (selectedFeed == null) {
            return true;
        }
        android.util.Log.d(de.danoeh.antennapod.menuhandler.FeedMenuHandler.TAG, "Preparing options menu");
        menu.findItem(de.danoeh.antennapod.R.id.refresh_complete_item).setVisible(selectedFeed.isPaged());
        if (org.apache.commons.lang3.StringUtils.isBlank(selectedFeed.getLink())) {
            menu.findItem(de.danoeh.antennapod.R.id.visit_website_item).setVisible(false);
        }
        if (selectedFeed.isLocalFeed()) {
            // hide complete submenu "Share..." as both sub menu items are not visible
            menu.findItem(de.danoeh.antennapod.R.id.share_item).setVisible(false);
        }
        return true;
    }


    /**
     * NOTE: This method does not handle clicks on the 'remove feed' - item.
     */
    public static boolean onOptionsItemClicked(final android.content.Context context, final android.view.MenuItem item, final de.danoeh.antennapod.model.feed.Feed selectedFeed) {
        final int itemId;
        itemId = item.getItemId();
        if (itemId == de.danoeh.antennapod.R.id.refresh_item) {
            de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(context, selectedFeed);
        } else if (itemId == de.danoeh.antennapod.R.id.refresh_complete_item) {
            new java.lang.Thread(() -> {
                selectedFeed.setNextPageLink(selectedFeed.getDownload_url());
                selectedFeed.setPageNr(0);
                try {
                    de.danoeh.antennapod.core.storage.DBWriter.resetPagedFeedPage(selectedFeed).get();
                    de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnce(context, selectedFeed);
                } catch (java.util.concurrent.ExecutionException | java.lang.InterruptedException e) {
                    throw new java.lang.RuntimeException(e);
                }
            }).start();
        } else if (itemId == de.danoeh.antennapod.R.id.sort_items) {
            de.danoeh.antennapod.menuhandler.FeedMenuHandler.showSortDialog(context, selectedFeed);
        } else if (itemId == de.danoeh.antennapod.R.id.visit_website_item) {
            de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(context, selectedFeed.getLink());
        } else if (itemId == de.danoeh.antennapod.R.id.share_item) {
            de.danoeh.antennapod.core.util.ShareUtils.shareFeedLink(context, selectedFeed);
        } else {
            return false;
        }
        return true;
    }


    private static void showSortDialog(android.content.Context context, de.danoeh.antennapod.model.feed.Feed selectedFeed) {
        de.danoeh.antennapod.dialog.IntraFeedSortDialog sortDialog;
        sortDialog = new de.danoeh.antennapod.dialog.IntraFeedSortDialog(context, selectedFeed.getSortOrder(), selectedFeed.isLocalFeed()) {
            @java.lang.Override
            protected void updateSort(@androidx.annotation.NonNull
            de.danoeh.antennapod.model.feed.SortOrder sortOrder) {
                selectedFeed.setSortOrder(sortOrder);
                de.danoeh.antennapod.core.storage.DBWriter.setFeedItemSortOrder(selectedFeed.getId(), sortOrder);
            }

        };
        sortDialog.openDialog();
    }


    public static boolean onMenuItemClicked(@androidx.annotation.NonNull
    androidx.fragment.app.Fragment fragment, int menuItemId, @androidx.annotation.NonNull
    de.danoeh.antennapod.model.feed.Feed selectedFeed, java.lang.Runnable callback) {
        @androidx.annotation.NonNull
        android.content.Context context;
        context = fragment.requireContext();
        if (menuItemId == de.danoeh.antennapod.R.id.rename_folder_item) {
            new de.danoeh.antennapod.dialog.RenameItemDialog(fragment.getActivity(), selectedFeed).show();
        } else if (menuItemId == de.danoeh.antennapod.R.id.remove_all_inbox_item) {
            de.danoeh.antennapod.core.dialog.ConfirmationDialog dialog;
            dialog = new de.danoeh.antennapod.core.dialog.ConfirmationDialog(fragment.getActivity(), de.danoeh.antennapod.R.string.remove_all_inbox_label, de.danoeh.antennapod.R.string.remove_all_inbox_confirmation_msg) {
                @java.lang.Override
                @android.annotation.SuppressLint("CheckResult")
                public void onConfirmButtonPressed(android.content.DialogInterface clickedDialog) {
                    clickedDialog.dismiss();
                    io.reactivex.Observable.fromCallable(((java.util.concurrent.Callable<java.util.concurrent.Future>) (() -> de.danoeh.antennapod.core.storage.DBWriter.removeFeedNewFlag(selectedFeed.getId())))).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.util.concurrent.Future result) -> callback.run(), (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.menuhandler.FeedMenuHandler.TAG, android.util.Log.getStackTraceString(error)));
                }

            };
            dialog.createNewDialog().show();
        } else if (menuItemId == de.danoeh.antennapod.R.id.edit_tags) {
            de.danoeh.antennapod.dialog.TagSettingsDialog.newInstance(java.util.Collections.singletonList(selectedFeed.getPreferences())).show(fragment.getChildFragmentManager(), de.danoeh.antennapod.dialog.TagSettingsDialog.TAG);
        } else if (menuItemId == de.danoeh.antennapod.R.id.rename_item) {
            new de.danoeh.antennapod.dialog.RenameItemDialog(fragment.getActivity(), selectedFeed).show();
        } else if (menuItemId == de.danoeh.antennapod.R.id.remove_feed) {
            de.danoeh.antennapod.dialog.RemoveFeedDialog.show(context, selectedFeed, null);
        } else {
            return false;
        }
        return true;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
