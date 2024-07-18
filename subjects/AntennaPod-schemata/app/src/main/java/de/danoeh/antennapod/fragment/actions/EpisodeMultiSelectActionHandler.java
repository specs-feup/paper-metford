package de.danoeh.antennapod.fragment.actions;
import de.danoeh.antennapod.view.LocalDeleteModal;
import de.danoeh.antennapod.R;
import android.util.Log;
import de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.core.util.LongList;
import java.util.List;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.PluralsRes;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EpisodeMultiSelectActionHandler {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "EpisodeSelectHandler";

    private final de.danoeh.antennapod.activity.MainActivity activity;

    private final int actionId;

    private int totalNumItems = 0;

    private com.google.android.material.snackbar.Snackbar snackbar = null;

    public EpisodeMultiSelectActionHandler(de.danoeh.antennapod.activity.MainActivity activity, int actionId) {
        this.activity = activity;
        this.actionId = actionId;
    }


    public void handleAction(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        if (actionId == de.danoeh.antennapod.R.id.add_to_queue_batch) {
            queueChecked(items);
        } else if (actionId == de.danoeh.antennapod.R.id.remove_from_queue_batch) {
            removeFromQueueChecked(items);
        } else if (actionId == de.danoeh.antennapod.R.id.remove_from_inbox_batch) {
            removeFromInboxChecked(items);
        } else if (actionId == de.danoeh.antennapod.R.id.mark_read_batch) {
            markedCheckedPlayed(items);
        } else if (actionId == de.danoeh.antennapod.R.id.mark_unread_batch) {
            markedCheckedUnplayed(items);
        } else if (actionId == de.danoeh.antennapod.R.id.download_batch) {
            downloadChecked(items);
        } else if (actionId == de.danoeh.antennapod.R.id.delete_batch) {
            de.danoeh.antennapod.view.LocalDeleteModal.showLocalFeedDeleteWarningIfNecessary(activity, items, () -> deleteChecked(items));
        } else {
            android.util.Log.e(de.danoeh.antennapod.fragment.actions.EpisodeMultiSelectActionHandler.TAG, "Unrecognized speed dial action item. Do nothing. id=" + actionId);
        }
    }


    private void queueChecked(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        // Check if an episode actually contains any media files before adding it to queue
        de.danoeh.antennapod.core.util.LongList toQueue;
        toQueue = new de.danoeh.antennapod.core.util.LongList(items.size());
        for (de.danoeh.antennapod.model.feed.FeedItem episode : items) {
            if (episode.hasMedia()) {
                toQueue.add(episode.getId());
            }
        }
        de.danoeh.antennapod.core.storage.DBWriter.addQueueItem(activity, true, toQueue.toArray());
        showMessage(de.danoeh.antennapod.R.plurals.added_to_queue_batch_label, toQueue.size());
    }


    private void removeFromQueueChecked(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        long[] checkedIds;
        checkedIds = getSelectedIds(items);
        de.danoeh.antennapod.core.storage.DBWriter.removeQueueItem(activity, true, checkedIds);
        showMessage(de.danoeh.antennapod.R.plurals.removed_from_queue_batch_label, checkedIds.length);
    }


    private void removeFromInboxChecked(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        de.danoeh.antennapod.core.util.LongList markUnplayed;
        markUnplayed = new de.danoeh.antennapod.core.util.LongList();
        for (de.danoeh.antennapod.model.feed.FeedItem episode : items) {
            if (episode.isNew()) {
                markUnplayed.add(episode.getId());
            }
        }
        de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED, markUnplayed.toArray());
        showMessage(de.danoeh.antennapod.R.plurals.removed_from_inbox_batch_label, markUnplayed.size());
    }


    private void markedCheckedPlayed(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        long[] checkedIds;
        checkedIds = getSelectedIds(items);
        de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(de.danoeh.antennapod.model.feed.FeedItem.PLAYED, checkedIds);
        showMessage(de.danoeh.antennapod.R.plurals.marked_read_batch_label, checkedIds.length);
    }


    private void markedCheckedUnplayed(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        long[] checkedIds;
        checkedIds = getSelectedIds(items);
        de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED, checkedIds);
        showMessage(de.danoeh.antennapod.R.plurals.marked_unread_batch_label, checkedIds.length);
    }


    private void downloadChecked(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        // download the check episodes in the same order as they are currently displayed
        for (de.danoeh.antennapod.model.feed.FeedItem episode : items) {
            if (episode.hasMedia() && (!episode.getFeed().isLocalFeed())) {
                de.danoeh.antennapod.net.download.serviceinterface.DownloadServiceInterface.get().download(activity, episode);
            }
        }
        showMessage(de.danoeh.antennapod.R.plurals.downloading_batch_label, items.size());
    }


    private void deleteChecked(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        int countHasMedia;
        countHasMedia = 0;
        for (de.danoeh.antennapod.model.feed.FeedItem feedItem : items) {
            if (feedItem.hasMedia() && feedItem.getMedia().isDownloaded()) {
                countHasMedia++;
                de.danoeh.antennapod.core.storage.DBWriter.deleteFeedMediaOfItem(activity, feedItem.getMedia().getId());
            }
        }
        showMessage(de.danoeh.antennapod.R.plurals.deleted_multi_episode_batch_label, countHasMedia);
    }


    private void showMessage(@androidx.annotation.PluralsRes
    int msgId, int numItems) {
        totalNumItems += numItems;
        activity.runOnUiThread(() -> {
            java.lang.String text;
            text = activity.getResources().getQuantityString(msgId, totalNumItems, totalNumItems);
            if (snackbar != null) {
                snackbar.setText(text);
                snackbar.show()// Resets the timeout
                ;// Resets the timeout

            } else {
                snackbar = activity.showSnackbarAbovePlayer(text, com.google.android.material.snackbar.Snackbar.LENGTH_LONG);
            }
        });
    }


    private long[] getSelectedIds(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        long[] checkedIds;
        checkedIds = new long[items.size()];
        for (int i = 0; i < items.size(); ++i) {
            checkedIds[i] = items.get(i).getId();
        }
        return checkedIds;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
