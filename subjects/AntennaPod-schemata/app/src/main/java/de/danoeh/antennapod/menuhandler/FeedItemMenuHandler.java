package de.danoeh.antennapod.menuhandler;
import de.danoeh.antennapod.core.util.ShareUtils;
import de.danoeh.antennapod.core.util.PlaybackStatus;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.core.storage.DBWriter;
import de.danoeh.antennapod.core.util.FeedUtil;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.receiver.MediaButtonReceiver;
import androidx.annotation.NonNull;
import android.view.KeyEvent;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.core.sync.SynchronizationSettings;
import de.danoeh.antennapod.view.LocalDeleteModal;
import de.danoeh.antennapod.core.service.playback.PlaybackServiceInterface;
import android.util.Log;
import android.view.Menu;
import android.os.Handler;
import android.view.MenuItem;
import de.danoeh.antennapod.dialog.ShareDialog;
import de.danoeh.antennapod.net.sync.model.EpisodeAction;
import de.danoeh.antennapod.core.preferences.PlaybackPreferences;
import de.danoeh.antennapod.core.util.IntentUtils;
import de.danoeh.antennapod.model.feed.FeedMedia;
import de.danoeh.antennapod.core.sync.queue.SynchronizationQueueSink;
import com.google.android.material.snackbar.Snackbar;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Handles interactions with the FeedItemMenu.
 */
public class FeedItemMenuHandler {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "FeedItemMenuHandler";

    private FeedItemMenuHandler() {
    }


    /**
     * This method should be called in the prepare-methods of menus. It changes
     * the visibility of the menu items depending on a FeedItem's attributes.
     *
     * @param menu
     * 		An instance of Menu
     * @param selectedItem
     * 		The FeedItem for which the menu is supposed to be prepared
     * @return Returns true if selectedItem is not null.
     */
    public static boolean onPrepareMenu(android.view.Menu menu, de.danoeh.antennapod.model.feed.FeedItem selectedItem) {
        if ((menu == null) || (selectedItem == null)) {
            return false;
        }
        final boolean hasMedia;
        hasMedia = selectedItem.getMedia() != null;
        final boolean isPlaying;
        isPlaying = hasMedia && de.danoeh.antennapod.core.util.PlaybackStatus.isPlaying(selectedItem.getMedia());
        final boolean isInQueue;
        isInQueue = selectedItem.isTagged(de.danoeh.antennapod.model.feed.FeedItem.TAG_QUEUE);
        final boolean fileDownloaded;
        fileDownloaded = hasMedia && selectedItem.getMedia().fileExists();
        final boolean isLocalFile;
        isLocalFile = hasMedia && selectedItem.getFeed().isLocalFeed();
        final boolean isFavorite;
        isFavorite = selectedItem.isTagged(de.danoeh.antennapod.model.feed.FeedItem.TAG_FAVORITE);
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.skip_episode_item, isPlaying);
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.remove_from_queue_item, isInQueue);
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.add_to_queue_item, (!isInQueue) && (selectedItem.getMedia() != null));
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.visit_website_item, (!selectedItem.getFeed().isLocalFeed()) && de.danoeh.antennapod.core.util.ShareUtils.hasLinkToShare(selectedItem));
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.share_item, !selectedItem.getFeed().isLocalFeed());
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.remove_inbox_item, selectedItem.isNew());
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.mark_read_item, !selectedItem.isPlayed());
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.mark_unread_item, selectedItem.isPlayed());
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.reset_position, hasMedia && (selectedItem.getMedia().getPosition() != 0));
        // Display proper strings when item has no media
        if (hasMedia) {
            de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemTitle(menu, de.danoeh.antennapod.R.id.mark_read_item, de.danoeh.antennapod.R.string.mark_read_label);
            de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemTitle(menu, de.danoeh.antennapod.R.id.mark_unread_item, de.danoeh.antennapod.R.string.mark_unread_label);
        } else {
            de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemTitle(menu, de.danoeh.antennapod.R.id.mark_read_item, de.danoeh.antennapod.R.string.mark_read_no_media_label);
            de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemTitle(menu, de.danoeh.antennapod.R.id.mark_unread_item, de.danoeh.antennapod.R.string.mark_unread_label_no_media);
        }
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.add_to_favorites_item, !isFavorite);
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.remove_from_favorites_item, isFavorite);
        de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, de.danoeh.antennapod.R.id.remove_item, fileDownloaded || isLocalFile);
        return true;
    }


    /**
     * Used to set the viability of a menu item.
     * This method also does some null-checking so that neither menu nor the menu item are null
     * in order to prevent nullpointer exceptions.
     *
     * @param menu
     * 		The menu that should be used
     * @param menuId
     * 		The id of the menu item that will be used
     * @param visibility
     * 		The new visibility status of given menu item
     */
    private static void setItemVisibility(android.view.Menu menu, int menuId, boolean visibility) {
        if (menu == null) {
            return;
        }
        android.view.MenuItem item;
        item = menu.findItem(menuId);
        if (item != null) {
            item.setVisible(visibility);
        }
    }


    /**
     * This method allows to replace to String of a menu item with a different one.
     *
     * @param menu
     * 		Menu item that should be used
     * @param id
     * 		The id of the string that is going to be replaced.
     * @param noMedia
     * 		The id of the new String that is going to be used.
     */
    public static void setItemTitle(android.view.Menu menu, int id, int noMedia) {
        android.view.MenuItem item;
        item = menu.findItem(id);
        if (item != null) {
            item.setTitle(noMedia);
        }
    }


    /**
     * The same method as {@link #onPrepareMenu(Menu, FeedItem)}, but lets the
     * caller also specify a list of menu items that should not be shown.
     *
     * @param excludeIds
     * 		Menu item that should be excluded
     * @return true if selectedItem is not null.
     */
    public static boolean onPrepareMenu(android.view.Menu menu, de.danoeh.antennapod.model.feed.FeedItem selectedItem, int... excludeIds) {
        if ((menu == null) || (selectedItem == null)) {
            return false;
        }
        boolean rc;
        rc = de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onPrepareMenu(menu, selectedItem);
        if (rc && (excludeIds != null)) {
            for (int id : excludeIds) {
                de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.setItemVisibility(menu, id, false);
            }
        }
        return rc;
    }


    /**
     * Default menu handling for the given FeedItem.
     *
     * A Fragment instance, (rather than the more generic Context), is needed as a parameter
     * to support some UI operations, e.g., creating a Snackbar.
     */
    public static boolean onMenuItemClicked(@androidx.annotation.NonNull
    androidx.fragment.app.Fragment fragment, int menuItemId, @androidx.annotation.NonNull
    de.danoeh.antennapod.model.feed.FeedItem selectedItem) {
        @androidx.annotation.NonNull
        android.content.Context context;
        context = fragment.requireContext();
        if (menuItemId == de.danoeh.antennapod.R.id.skip_episode_item) {
            context.sendBroadcast(de.danoeh.antennapod.core.receiver.MediaButtonReceiver.createIntent(context, android.view.KeyEvent.KEYCODE_MEDIA_NEXT));
        } else if (menuItemId == de.danoeh.antennapod.R.id.remove_item) {
            de.danoeh.antennapod.view.LocalDeleteModal.showLocalFeedDeleteWarningIfNecessary(context, java.util.Arrays.asList(selectedItem), () -> de.danoeh.antennapod.core.storage.DBWriter.deleteFeedMediaOfItem(context, selectedItem.getMedia().getId()));
        } else if (menuItemId == de.danoeh.antennapod.R.id.remove_inbox_item) {
            de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.removeNewFlagWithUndo(fragment, selectedItem);
        } else if (menuItemId == de.danoeh.antennapod.R.id.mark_read_item) {
            selectedItem.setPlayed(true);
            de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(selectedItem, de.danoeh.antennapod.model.feed.FeedItem.PLAYED, true);
            if ((!selectedItem.getFeed().isLocalFeed()) && de.danoeh.antennapod.core.sync.SynchronizationSettings.isProviderConnected()) {
                de.danoeh.antennapod.model.feed.FeedMedia media;
                media = selectedItem.getMedia();
                // not all items have media, Gpodder only cares about those that do
                if (media != null) {
                    de.danoeh.antennapod.net.sync.model.EpisodeAction actionPlay;
                    switch(MUID_STATIC) {
                        // FeedItemMenuHandler_0_BinaryMutator
                        case 152: {
                            actionPlay = new de.danoeh.antennapod.net.sync.model.EpisodeAction.Builder(selectedItem, de.danoeh.antennapod.net.sync.model.EpisodeAction.PLAY).currentTimestamp().started(media.getDuration() * 1000).position(media.getDuration() / 1000).total(media.getDuration() / 1000).build();
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // FeedItemMenuHandler_1_BinaryMutator
                            case 1152: {
                                actionPlay = new de.danoeh.antennapod.net.sync.model.EpisodeAction.Builder(selectedItem, de.danoeh.antennapod.net.sync.model.EpisodeAction.PLAY).currentTimestamp().started(media.getDuration() / 1000).position(media.getDuration() * 1000).total(media.getDuration() / 1000).build();
                                break;
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // FeedItemMenuHandler_2_BinaryMutator
                                case 2152: {
                                    actionPlay = new de.danoeh.antennapod.net.sync.model.EpisodeAction.Builder(selectedItem, de.danoeh.antennapod.net.sync.model.EpisodeAction.PLAY).currentTimestamp().started(media.getDuration() / 1000).position(media.getDuration() / 1000).total(media.getDuration() * 1000).build();
                                    break;
                                }
                                default: {
                                actionPlay = new de.danoeh.antennapod.net.sync.model.EpisodeAction.Builder(selectedItem, de.danoeh.antennapod.net.sync.model.EpisodeAction.PLAY).currentTimestamp().started(media.getDuration() / 1000).position(media.getDuration() / 1000).total(media.getDuration() / 1000).build();
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        de.danoeh.antennapod.core.sync.queue.SynchronizationQueueSink.enqueueEpisodeActionIfSynchronizationIsActive(context, actionPlay);
    }
}
} else if (menuItemId == de.danoeh.antennapod.R.id.mark_unread_item) {
selectedItem.setPlayed(false);
de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(selectedItem, de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED, false);
if ((!selectedItem.getFeed().isLocalFeed()) && (selectedItem.getMedia() != null)) {
    de.danoeh.antennapod.net.sync.model.EpisodeAction actionNew;
    actionNew = new de.danoeh.antennapod.net.sync.model.EpisodeAction.Builder(selectedItem, de.danoeh.antennapod.net.sync.model.EpisodeAction.NEW).currentTimestamp().build();
    de.danoeh.antennapod.core.sync.queue.SynchronizationQueueSink.enqueueEpisodeActionIfSynchronizationIsActive(context, actionNew);
}
} else if (menuItemId == de.danoeh.antennapod.R.id.add_to_queue_item) {
de.danoeh.antennapod.core.storage.DBWriter.addQueueItem(context, selectedItem);
} else if (menuItemId == de.danoeh.antennapod.R.id.remove_from_queue_item) {
de.danoeh.antennapod.core.storage.DBWriter.removeQueueItem(context, true, selectedItem);
} else if (menuItemId == de.danoeh.antennapod.R.id.add_to_favorites_item) {
de.danoeh.antennapod.core.storage.DBWriter.addFavoriteItem(selectedItem);
} else if (menuItemId == de.danoeh.antennapod.R.id.remove_from_favorites_item) {
de.danoeh.antennapod.core.storage.DBWriter.removeFavoriteItem(selectedItem);
} else if (menuItemId == de.danoeh.antennapod.R.id.reset_position) {
selectedItem.getMedia().setPosition(0);
if (de.danoeh.antennapod.core.preferences.PlaybackPreferences.getCurrentlyPlayingFeedMediaId() == selectedItem.getMedia().getId()) {
    de.danoeh.antennapod.core.preferences.PlaybackPreferences.writeNoMediaPlaying();
    de.danoeh.antennapod.core.util.IntentUtils.sendLocalBroadcast(context, de.danoeh.antennapod.core.service.playback.PlaybackServiceInterface.ACTION_SHUTDOWN_PLAYBACK_SERVICE);
}
de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(selectedItem, de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED, true);
} else if (menuItemId == de.danoeh.antennapod.R.id.visit_website_item) {
de.danoeh.antennapod.core.util.IntentUtils.openInBrowser(context, de.danoeh.antennapod.core.util.FeedItemUtil.getLinkWithFallback(selectedItem));
} else if (menuItemId == de.danoeh.antennapod.R.id.share_item) {
de.danoeh.antennapod.dialog.ShareDialog shareDialog;
shareDialog = de.danoeh.antennapod.dialog.ShareDialog.newInstance(selectedItem);
shareDialog.show(fragment.getActivity().getSupportFragmentManager(), "ShareEpisodeDialog");
} else {
android.util.Log.d(de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.TAG, "Unknown menuItemId: " + menuItemId);
return false;
}
// Refresh menu state
return true;
}


/**
 * Remove new flag with additional UI logic to allow undo with Snackbar.
 *
 * Undo is useful for Remove new flag, given there is no UI to undo it otherwise
 * ,i.e., there is (context) menu item for add new flag
 */
public static void markReadWithUndo(@androidx.annotation.NonNull
androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItem item, int playState, boolean showSnackbar) {
if (item == null) {
return;
}
android.util.Log.d(de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.TAG, ("markReadWithUndo(" + item.getId()) + ")");
// we're marking it as unplayed since the user didn't actually play it
// but they don't want it considered 'NEW' anymore
de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(playState, item.getId());
final android.os.Handler h;
h = new android.os.Handler(fragment.requireContext().getMainLooper());
final java.lang.Runnable r;
r = () -> {
de.danoeh.antennapod.model.feed.FeedMedia media;
media = item.getMedia();
boolean shouldAutoDelete;
shouldAutoDelete = de.danoeh.antennapod.core.util.FeedUtil.shouldAutoDeleteItemsOnThatFeed(item.getFeed());
if (((media != null) && de.danoeh.antennapod.core.util.FeedItemUtil.hasAlmostEnded(media)) && shouldAutoDelete) {
    de.danoeh.antennapod.core.storage.DBWriter.deleteFeedMediaOfItem(fragment.requireContext(), media.getId());
}
};
int playStateStringRes;
switch (playState) {
default :
case de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED :
    if (item.getPlayState() == de.danoeh.antennapod.model.feed.FeedItem.NEW) {
        // was new
        playStateStringRes = de.danoeh.antennapod.R.string.removed_inbox_label;
    } else {
        // was played
        playStateStringRes = de.danoeh.antennapod.R.string.marked_as_unplayed_label;
    }
    break;
case de.danoeh.antennapod.model.feed.FeedItem.PLAYED :
    playStateStringRes = de.danoeh.antennapod.R.string.marked_as_played_label;
    break;
}
int duration;
duration = com.google.android.material.snackbar.Snackbar.LENGTH_LONG;
if (showSnackbar) {
switch(MUID_STATIC) {
    // FeedItemMenuHandler_3_BuggyGUIListenerOperatorMutator
    case 3152: {
        ((de.danoeh.antennapod.activity.MainActivity) (fragment.getActivity())).showSnackbarAbovePlayer(playStateStringRes, duration).setAction(fragment.getString(de.danoeh.antennapod.R.string.undo), null);
        break;
    }
    default: {
    ((de.danoeh.antennapod.activity.MainActivity) (fragment.getActivity())).showSnackbarAbovePlayer(playStateStringRes, duration).setAction(fragment.getString(de.danoeh.antennapod.R.string.undo), (android.view.View v) -> {
        de.danoeh.antennapod.core.storage.DBWriter.markItemPlayed(item.getPlayState(), item.getId());
        // don't forget to cancel the thing that's going to remove the media
        h.removeCallbacks(r);
    });
    break;
}
}
}
switch(MUID_STATIC) {
// FeedItemMenuHandler_4_BinaryMutator
case 4152: {
h.postDelayed(r, ((int) (java.lang.Math.ceil(duration / 1.05F))));
break;
}
default: {
h.postDelayed(r, ((int) (java.lang.Math.ceil(duration * 1.05F))));
break;
}
}
}


public static void removeNewFlagWithUndo(@androidx.annotation.NonNull
androidx.fragment.app.Fragment fragment, de.danoeh.antennapod.model.feed.FeedItem item) {
de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.markReadWithUndo(fragment, item, de.danoeh.antennapod.model.feed.FeedItem.UNPLAYED, false);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
