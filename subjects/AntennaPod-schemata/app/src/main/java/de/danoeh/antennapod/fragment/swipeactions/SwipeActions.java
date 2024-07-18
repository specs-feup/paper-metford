package de.danoeh.antennapod.fragment.swipeactions;
import androidx.lifecycle.OnLifecycleEvent;
import de.danoeh.antennapod.fragment.AllEpisodesFragment;
import de.danoeh.antennapod.fragment.PlaybackHistoryFragment;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.dialog.SwipeActionsDialog;
import de.danoeh.antennapod.model.feed.FeedItemFilter;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.fragment.QueueFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import com.annimon.stream.Stream;
import java.util.List;
import androidx.lifecycle.LifecycleObserver;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import java.util.Collections;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import androidx.lifecycle.Lifecycle;
import de.danoeh.antennapod.fragment.InboxFragment;
import de.danoeh.antennapod.fragment.CompletedDownloadsFragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SwipeActions extends androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback implements androidx.lifecycle.LifecycleObserver {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String PREF_NAME = "SwipeActionsPrefs";

    public static final java.lang.String KEY_PREFIX_SWIPEACTIONS = "PrefSwipeActions";

    public static final java.lang.String KEY_PREFIX_NO_ACTION = "PrefNoSwipeAction";

    public static final java.util.List<de.danoeh.antennapod.fragment.swipeactions.SwipeAction> swipeActions = java.util.Collections.unmodifiableList(java.util.Arrays.asList(new de.danoeh.antennapod.fragment.swipeactions.AddToQueueSwipeAction(), new de.danoeh.antennapod.fragment.swipeactions.RemoveFromInboxSwipeAction(), new de.danoeh.antennapod.fragment.swipeactions.StartDownloadSwipeAction(), new de.danoeh.antennapod.fragment.swipeactions.MarkFavoriteSwipeAction(), new de.danoeh.antennapod.fragment.swipeactions.TogglePlaybackStateSwipeAction(), new de.danoeh.antennapod.fragment.swipeactions.RemoveFromQueueSwipeAction(), new de.danoeh.antennapod.fragment.swipeactions.DeleteSwipeAction(), new de.danoeh.antennapod.fragment.swipeactions.RemoveFromHistorySwipeAction()));

    private final androidx.fragment.app.Fragment fragment;

    private final java.lang.String tag;

    private de.danoeh.antennapod.model.feed.FeedItemFilter filter = null;

    de.danoeh.antennapod.fragment.swipeactions.SwipeActions.Actions actions;

    boolean swipeOutEnabled = true;

    int swipedOutTo = 0;

    private final androidx.recyclerview.widget.ItemTouchHelper itemTouchHelper = new androidx.recyclerview.widget.ItemTouchHelper(this);

    public SwipeActions(int dragDirs, androidx.fragment.app.Fragment fragment, java.lang.String tag) {
        super(dragDirs, androidx.recyclerview.widget.ItemTouchHelper.RIGHT | androidx.recyclerview.widget.ItemTouchHelper.LEFT);
        this.fragment = fragment;
        this.tag = tag;
        reloadPreference();
        fragment.getLifecycle().addObserver(this);
    }


    public SwipeActions(androidx.fragment.app.Fragment fragment, java.lang.String tag) {
        this(0, fragment, tag);
    }


    @androidx.lifecycle.OnLifecycleEvent(androidx.lifecycle.Lifecycle.Event.ON_START)
    public void reloadPreference() {
        actions = de.danoeh.antennapod.fragment.swipeactions.SwipeActions.getPrefs(fragment.requireContext(), tag);
    }


    public void setFilter(de.danoeh.antennapod.model.feed.FeedItemFilter filter) {
        this.filter = filter;
    }


    public de.danoeh.antennapod.fragment.swipeactions.SwipeActions attachTo(androidx.recyclerview.widget.RecyclerView recyclerView) {
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return this;
    }


    public void detach() {
        itemTouchHelper.attachToRecyclerView(null);
    }


    private static de.danoeh.antennapod.fragment.swipeactions.SwipeActions.Actions getPrefs(android.content.Context context, java.lang.String tag, java.lang.String defaultActions) {
        android.content.SharedPreferences prefs;
        prefs = context.getSharedPreferences(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.PREF_NAME, android.content.Context.MODE_PRIVATE);
        java.lang.String prefsString;
        prefsString = prefs.getString(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.KEY_PREFIX_SWIPEACTIONS + tag, defaultActions);
        return new de.danoeh.antennapod.fragment.swipeactions.SwipeActions.Actions(prefsString);
    }


    private static de.danoeh.antennapod.fragment.swipeactions.SwipeActions.Actions getPrefs(android.content.Context context, java.lang.String tag) {
        return de.danoeh.antennapod.fragment.swipeactions.SwipeActions.getPrefs(context, tag, "");
    }


    public static de.danoeh.antennapod.fragment.swipeactions.SwipeActions.Actions getPrefsWithDefaults(android.content.Context context, java.lang.String tag) {
        java.lang.String defaultActions;
        switch (tag) {
            case de.danoeh.antennapod.fragment.InboxFragment.TAG :
                defaultActions = (de.danoeh.antennapod.fragment.swipeactions.SwipeAction.ADD_TO_QUEUE + ",") + de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_INBOX;
                break;
            case de.danoeh.antennapod.fragment.QueueFragment.TAG :
                defaultActions = (de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_QUEUE + ",") + de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_QUEUE;
                break;
            case de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG :
                defaultActions = (de.danoeh.antennapod.fragment.swipeactions.SwipeAction.DELETE + ",") + de.danoeh.antennapod.fragment.swipeactions.SwipeAction.DELETE;
                break;
            case de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG :
                defaultActions = (de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY + ",") + de.danoeh.antennapod.fragment.swipeactions.SwipeAction.REMOVE_FROM_HISTORY;
                break;
            default :
            case de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG :
                defaultActions = (de.danoeh.antennapod.fragment.swipeactions.SwipeAction.MARK_FAV + ",") + de.danoeh.antennapod.fragment.swipeactions.SwipeAction.START_DOWNLOAD;
                break;
        }
        return de.danoeh.antennapod.fragment.swipeactions.SwipeActions.getPrefs(context, tag, defaultActions);
    }


    public static boolean isSwipeActionEnabled(android.content.Context context, java.lang.String tag) {
        android.content.SharedPreferences prefs;
        prefs = context.getSharedPreferences(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.PREF_NAME, android.content.Context.MODE_PRIVATE);
        return prefs.getBoolean(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.KEY_PREFIX_NO_ACTION + tag, true);
    }


    private boolean isSwipeActionEnabled() {
        return de.danoeh.antennapod.fragment.swipeactions.SwipeActions.isSwipeActionEnabled(fragment.requireContext(), tag);
    }


    @java.lang.Override
    public boolean onMove(@androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, @androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView.ViewHolder target) {
        return false;
    }


    @java.lang.Override
    public void onSwiped(@androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int swipeDir) {
        if (!actions.hasActions()) {
            // open settings dialog if no prefs are set
            new de.danoeh.antennapod.dialog.SwipeActionsDialog(fragment.requireContext(), tag).show(this::reloadPreference);
            return;
        }
        de.danoeh.antennapod.model.feed.FeedItem item;
        item = ((de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder) (viewHolder)).getFeedItem();
        (swipeDir == androidx.recyclerview.widget.ItemTouchHelper.RIGHT ? actions.right : actions.left).performAction(item, fragment, filter);
    }


    @java.lang.Override
    public void onChildDraw(@androidx.annotation.NonNull
    android.graphics.Canvas c, @androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive) {
        de.danoeh.antennapod.fragment.swipeactions.SwipeAction right;
        de.danoeh.antennapod.fragment.swipeactions.SwipeAction left;
        if (actions.hasActions()) {
            right = actions.right;
            left = actions.left;
        } else {
            right = left = new de.danoeh.antennapod.fragment.swipeactions.ShowFirstSwipeDialogAction();
        }
        // check if it will be removed
        de.danoeh.antennapod.model.feed.FeedItem item;
        item = ((de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder) (viewHolder)).getFeedItem();
        boolean rightWillRemove;
        rightWillRemove = right.willRemove(filter, item);
        boolean leftWillRemove;
        leftWillRemove = left.willRemove(filter, item);
        boolean wontLeave;
        wontLeave = ((dx > 0) && (!rightWillRemove)) || ((dx < 0) && (!leftWillRemove));
        // Limit swipe if it's not removed
        int maxMovement;
        switch(MUID_STATIC) {
            // SwipeActions_0_BinaryMutator
            case 113: {
                maxMovement = (recyclerView.getWidth() * 2) * 5;
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // SwipeActions_1_BinaryMutator
                case 1113: {
                    maxMovement = (recyclerView.getWidth() / 2) / 5;
                    break;
                }
                default: {
                maxMovement = (recyclerView.getWidth() * 2) / 5;
                break;
            }
        }
        break;
    }
}
float sign;
sign = (dx > 0) ? 1 : -1;
float limitMovement;
switch(MUID_STATIC) {
    // SwipeActions_2_BinaryMutator
    case 2113: {
        limitMovement = java.lang.Math.min(maxMovement, sign / dx);
        break;
    }
    default: {
    limitMovement = java.lang.Math.min(maxMovement, sign * dx);
    break;
}
}
float displacementPercentage;
switch(MUID_STATIC) {
// SwipeActions_3_BinaryMutator
case 3113: {
    displacementPercentage = limitMovement * maxMovement;
    break;
}
default: {
displacementPercentage = limitMovement / maxMovement;
break;
}
}
if ((actionState == androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE) && wontLeave) {
swipeOutEnabled = false;
boolean swipeThresholdReached;
swipeThresholdReached = displacementPercentage == 1;
switch(MUID_STATIC) {
// SwipeActions_4_BinaryMutator
case 4113: {
    // Move slower when getting near the maxMovement
    dx = (sign * maxMovement) / ((float) (java.lang.Math.sin((java.lang.Math.PI / 2) * displacementPercentage)));
    break;
}
default: {
switch(MUID_STATIC) {
    // SwipeActions_5_BinaryMutator
    case 5113: {
        // Move slower when getting near the maxMovement
        dx = (sign / maxMovement) * ((float) (java.lang.Math.sin((java.lang.Math.PI / 2) * displacementPercentage)));
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // SwipeActions_6_BinaryMutator
        case 6113: {
            // Move slower when getting near the maxMovement
            dx = (sign * maxMovement) * ((float) (java.lang.Math.sin((java.lang.Math.PI / 2) / displacementPercentage)));
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // SwipeActions_7_BinaryMutator
            case 7113: {
                // Move slower when getting near the maxMovement
                dx = (sign * maxMovement) * ((float) (java.lang.Math.sin((java.lang.Math.PI * 2) * displacementPercentage)));
                break;
            }
            default: {
            // Move slower when getting near the maxMovement
            dx = (sign * maxMovement) * ((float) (java.lang.Math.sin((java.lang.Math.PI / 2) * displacementPercentage)));
            break;
        }
    }
    break;
}
}
break;
}
}
break;
}
}
if (isCurrentlyActive) {
int dir;
dir = (dx > 0) ? androidx.recyclerview.widget.ItemTouchHelper.RIGHT : androidx.recyclerview.widget.ItemTouchHelper.LEFT;
swipedOutTo = (swipeThresholdReached) ? dir : 0;
}
} else {
swipeOutEnabled = true;
}
// add color and icon
android.content.Context context;
context = fragment.requireContext();
int themeColor;
themeColor = de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, android.R.attr.colorBackground);
int actionColor;
actionColor = de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, dx > 0 ? right.getActionColor() : left.getActionColor());
it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator.Builder builder;
builder = new it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive).addSwipeRightActionIcon(right.getActionIcon()).addSwipeLeftActionIcon(left.getActionIcon()).addSwipeRightBackgroundColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.background_elevated)).addSwipeLeftBackgroundColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.background_elevated)).setActionIconTint(androidx.core.graphics.ColorUtils.blendARGB(themeColor, actionColor, java.lang.Math.max(0.5F, displacementPercentage)));
builder.create().decorate();
super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);
}


@java.lang.Override
public float getSwipeEscapeVelocity(float defaultValue) {
switch(MUID_STATIC) {
// SwipeActions_8_BinaryMutator
case 8113: {
return swipeOutEnabled ? defaultValue / 1.5F : java.lang.Float.MAX_VALUE;
}
default: {
return swipeOutEnabled ? defaultValue * 1.5F : java.lang.Float.MAX_VALUE;
}
}
}


@java.lang.Override
public float getSwipeVelocityThreshold(float defaultValue) {
switch(MUID_STATIC) {
// SwipeActions_9_BinaryMutator
case 9113: {
return swipeOutEnabled ? defaultValue / 0.6F : 0;
}
default: {
return swipeOutEnabled ? defaultValue * 0.6F : 0;
}
}
}


@java.lang.Override
public float getSwipeThreshold(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
return swipeOutEnabled ? 0.6F : 1.0F;
}


@java.lang.Override
public void clearView(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
super.clearView(recyclerView, viewHolder);
if (swipedOutTo != 0) {
onSwiped(viewHolder, swipedOutTo);
swipedOutTo = 0;
}
}


@java.lang.Override
public int getMovementFlags(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
if (!isSwipeActionEnabled()) {
return androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags(getDragDirs(recyclerView, viewHolder), 0);
} else {
return super.getMovementFlags(recyclerView, viewHolder);
}
}


public void startDrag(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder) {
itemTouchHelper.startDrag(holder);
}


public static class Actions {
public de.danoeh.antennapod.fragment.swipeactions.SwipeAction right = null;

public de.danoeh.antennapod.fragment.swipeactions.SwipeAction left = null;

public Actions(java.lang.String prefs) {
java.lang.String[] actions;
actions = prefs.split(",");
if (actions.length == 2) {
this.right = com.annimon.stream.Stream.of(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.swipeActions).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> a.getId().equals(actions[0])).single();
this.left = com.annimon.stream.Stream.of(de.danoeh.antennapod.fragment.swipeactions.SwipeActions.swipeActions).filter((de.danoeh.antennapod.fragment.swipeactions.SwipeAction a) -> a.getId().equals(actions[1])).single();
}
}


public boolean hasActions() {
return (right != null) && (left != null);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
