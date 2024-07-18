package de.danoeh.antennapod.adapter;
import android.view.MenuInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.ContextMenu;
import android.view.View;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import de.danoeh.antennapod.model.feed.FeedItem;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import org.apache.commons.lang3.ArrayUtils;
import androidx.recyclerview.widget.RecyclerView;
import de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder;
import java.util.List;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.fragment.ItemPagerFragment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HorizontalItemListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder> implements android.view.View.OnCreateContextMenuListener {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<de.danoeh.antennapod.activity.MainActivity> mainActivityRef;

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> data = new java.util.ArrayList<>();

    private de.danoeh.antennapod.model.feed.FeedItem longPressedItem;

    private int dummyViews = 0;

    public HorizontalItemListAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity) {
        this.mainActivityRef = new java.lang.ref.WeakReference<>(mainActivity);
        setHasStableIds(true);
    }


    public void setDummyViews(int dummyViews) {
        this.dummyViews = dummyViews;
    }


    public void updateData(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> newData) {
        data = newData;
        notifyDataSetChanged();
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        return new de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder(mainActivityRef.get(), parent);
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder holder, int position) {
        if (position >= data.size()) {
            holder.bindDummy();
            return;
        }
        final de.danoeh.antennapod.model.feed.FeedItem item;
        item = data.get(position);
        holder.bind(item);
        holder.card.setOnCreateContextMenuListener(this);
        holder.card.setOnLongClickListener((android.view.View v) -> {
            longPressedItem = item;
            return false;
        });
        holder.secondaryActionIcon.setOnCreateContextMenuListener(this);
        holder.secondaryActionIcon.setOnLongClickListener((android.view.View v) -> {
            longPressedItem = item;
            return false;
        });
        switch(MUID_STATIC) {
            // HorizontalItemListAdapter_0_BuggyGUIListenerOperatorMutator
            case 36: {
                holder.card.setOnClickListener(null);
                break;
            }
            default: {
            holder.card.setOnClickListener((android.view.View v) -> {
                de.danoeh.antennapod.activity.MainActivity activity;
                activity = mainActivityRef.get();
                if (activity != null) {
                    long[] ids;
                    ids = de.danoeh.antennapod.core.util.FeedItemUtil.getIds(data);
                    int clickPosition;
                    clickPosition = org.apache.commons.lang3.ArrayUtils.indexOf(ids, item.getId());
                    activity.loadChildFragment(de.danoeh.antennapod.fragment.ItemPagerFragment.newInstance(ids, clickPosition));
                }
            });
            break;
        }
    }
}


@java.lang.Override
public long getItemId(int position) {
    if (position >= data.size()) {
        return androidx.recyclerview.widget.RecyclerView.NO_ID// Dummy views
        ;// Dummy views

    }
    return data.get(position).getId();
}


@java.lang.Override
public int getItemCount() {
    switch(MUID_STATIC) {
        // HorizontalItemListAdapter_1_BinaryMutator
        case 1036: {
            return dummyViews - data.size();
        }
        default: {
        return dummyViews + data.size();
        }
}
}


@java.lang.Override
public void onViewRecycled(@androidx.annotation.NonNull
de.danoeh.antennapod.view.viewholder.HorizontalItemViewHolder holder) {
super.onViewRecycled(holder);
// Set all listeners to null. This is required to prevent leaking fragments that have set a listener.
// Activity -> recycledViewPool -> ViewHolder -> Listener -> Fragment (can not be garbage collected)
holder.card.setOnClickListener(null);
holder.card.setOnCreateContextMenuListener(null);
holder.card.setOnLongClickListener(null);
holder.secondaryActionIcon.setOnClickListener(null);
holder.secondaryActionIcon.setOnCreateContextMenuListener(null);
holder.secondaryActionIcon.setOnLongClickListener(null);
}


/**
 * {@link #notifyItemChanged(int)} is final, so we can not override.
 * Calling {@link #notifyItemChanged(int)} may bind the item to a new ViewHolder and execute a transition.
 * This causes flickering and breaks the download animation that stores the old progress in the View.
 * Instead, we tell the adapter to use partial binding by calling {@link #notifyItemChanged(int, Object)}.
 * We actually ignore the payload and always do a full bind but calling the partial bind method ensures
 * that ViewHolders are always re-used.
 *
 * @param position
 * 		Position of the item that has changed
 */
public void notifyItemChangedCompat(int position) {
notifyItemChanged(position, "foo");
}


@androidx.annotation.Nullable
public de.danoeh.antennapod.model.feed.FeedItem getLongPressedItem() {
return longPressedItem;
}


@java.lang.Override
public void onCreateContextMenu(final android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
android.view.MenuInflater inflater;
inflater = mainActivityRef.get().getMenuInflater();
if (longPressedItem == null) {
    return;
}
menu.clear();
inflater.inflate(de.danoeh.antennapod.R.menu.feeditemlist_context, menu);
menu.setHeaderTitle(longPressedItem.getTitle());
de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onPrepareMenu(menu, longPressedItem, de.danoeh.antennapod.R.id.skip_episode_item);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
