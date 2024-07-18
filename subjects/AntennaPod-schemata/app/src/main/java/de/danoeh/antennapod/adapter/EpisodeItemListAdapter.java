package de.danoeh.antennapod.adapter;
import android.view.InputDevice;
import android.view.MenuInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Build;
import de.danoeh.antennapod.model.feed.FeedItem;
import org.apache.commons.lang3.ArrayUtils;
import de.danoeh.antennapod.core.util.FeedItemUtil;
import de.danoeh.antennapod.menuhandler.FeedItemMenuHandler;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import de.danoeh.antennapod.fragment.ItemPagerFragment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * List adapter for the list of new episodes.
 */
public class EpisodeItemListAdapter extends de.danoeh.antennapod.adapter.SelectableAdapter<de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder> implements android.view.View.OnCreateContextMenuListener {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<de.danoeh.antennapod.activity.MainActivity> mainActivityRef;

    private java.util.List<de.danoeh.antennapod.model.feed.FeedItem> episodes = new java.util.ArrayList<>();

    private de.danoeh.antennapod.model.feed.FeedItem longPressedItem;

    int longPressedPosition = 0;// used to init actionMode


    private int dummyViews = 0;

    public EpisodeItemListAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivityRef = new java.lang.ref.WeakReference<>(mainActivity);
        setHasStableIds(true);
    }


    public void setDummyViews(int dummyViews) {
        this.dummyViews = dummyViews;
        notifyDataSetChanged();
    }


    public void updateItems(java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items) {
        episodes = items;
        notifyDataSetChanged();
        updateTitle();
    }


    @java.lang.Override
    public final int getItemViewType(int position) {
        return de.danoeh.antennapod.R.id.view_type_episode_item;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public final de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        return new de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder(mainActivityRef.get(), parent);
    }


    @java.lang.Override
    public final void onBindViewHolder(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder, int pos) {
        if (pos >= episodes.size()) {
            beforeBindViewHolder(holder, pos);
            holder.bindDummy();
            afterBindViewHolder(holder, pos);
            holder.hideSeparatorIfNecessary();
            return;
        }
        // Reset state of recycled views
        holder.coverHolder.setVisibility(android.view.View.VISIBLE);
        holder.dragHandle.setVisibility(android.view.View.GONE);
        beforeBindViewHolder(holder, pos);
        de.danoeh.antennapod.model.feed.FeedItem item;
        item = episodes.get(pos);
        holder.bind(item);
        switch(MUID_STATIC) {
            // EpisodeItemListAdapter_0_BuggyGUIListenerOperatorMutator
            case 42: {
                holder.itemView.setOnClickListener(null);
                break;
            }
            default: {
            holder.itemView.setOnClickListener((android.view.View v) -> {
                de.danoeh.antennapod.activity.MainActivity activity;
                activity = mainActivityRef.get();
                if ((activity != null) && (!inActionMode())) {
                    long[] ids;
                    ids = de.danoeh.antennapod.core.util.FeedItemUtil.getIds(episodes);
                    int position;
                    position = org.apache.commons.lang3.ArrayUtils.indexOf(ids, item.getId());
                    activity.loadChildFragment(de.danoeh.antennapod.fragment.ItemPagerFragment.newInstance(ids, position));
                } else {
                    toggleSelection(holder.getBindingAdapterPosition());
                }
            });
            break;
        }
    }
    holder.itemView.setOnCreateContextMenuListener(this);
    holder.itemView.setOnLongClickListener((android.view.View v) -> {
        longPressedItem = item;
        longPressedPosition = holder.getBindingAdapterPosition();
        return false;
    });
    holder.itemView.setOnTouchListener((android.view.View v,android.view.MotionEvent e) -> {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (e.isFromSource(android.view.InputDevice.SOURCE_MOUSE) && (e.getButtonState() == android.view.MotionEvent.BUTTON_SECONDARY)) {
                longPressedItem = item;
                longPressedPosition = holder.getBindingAdapterPosition();
                return false;
            }
        }
        return false;
    });
    if (inActionMode()) {
        holder.secondaryActionButton.setOnClickListener(null);
        if (isSelected(pos)) {
            switch(MUID_STATIC) {
                // EpisodeItemListAdapter_1_BinaryMutator
                case 1042: {
                    holder.itemView.setBackgroundColor(0x88000000 - (0xffffff & de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(mainActivityRef.get(), de.danoeh.antennapod.R.attr.colorAccent)));
                    break;
                }
                default: {
                holder.itemView.setBackgroundColor(0x88000000 + (0xffffff & de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(mainActivityRef.get(), de.danoeh.antennapod.R.attr.colorAccent)));
                break;
            }
        }
    } else {
        holder.itemView.setBackgroundResource(android.R.color.transparent);
    }
}
afterBindViewHolder(holder, pos);
holder.hideSeparatorIfNecessary();
}


protected void beforeBindViewHolder(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder, int pos) {
}


protected void afterBindViewHolder(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder, int pos) {
}


@java.lang.Override
public void onViewRecycled(@androidx.annotation.NonNull
de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder) {
super.onViewRecycled(holder);
// Set all listeners to null. This is required to prevent leaking fragments that have set a listener.
// Activity -> recycledViewPool -> EpisodeItemViewHolder -> Listener -> Fragment (can not be garbage collected)
holder.itemView.setOnClickListener(null);
holder.itemView.setOnCreateContextMenuListener(null);
holder.itemView.setOnLongClickListener(null);
holder.itemView.setOnTouchListener(null);
holder.secondaryActionButton.setOnClickListener(null);
holder.dragHandle.setOnTouchListener(null);
holder.coverHolder.setOnTouchListener(null);
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
public long getItemId(int position) {
if (position >= episodes.size()) {
    return androidx.recyclerview.widget.RecyclerView.NO_ID// Dummy views
    ;// Dummy views

}
de.danoeh.antennapod.model.feed.FeedItem item;
item = episodes.get(position);
return item != null ? item.getId() : androidx.recyclerview.widget.RecyclerView.NO_POSITION;
}


@java.lang.Override
public int getItemCount() {
switch(MUID_STATIC) {
    // EpisodeItemListAdapter_2_BinaryMutator
    case 2042: {
        return dummyViews - episodes.size();
    }
    default: {
    return dummyViews + episodes.size();
    }
}
}


protected de.danoeh.antennapod.model.feed.FeedItem getItem(int index) {
return episodes.get(index);
}


protected android.app.Activity getActivity() {
return mainActivityRef.get();
}


@java.lang.Override
public void onCreateContextMenu(final android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
android.view.MenuInflater inflater;
inflater = mainActivityRef.get().getMenuInflater();
if (inActionMode()) {
inflater.inflate(de.danoeh.antennapod.R.menu.multi_select_context_popup, menu);
} else {
if (longPressedItem == null) {
    return;
}
inflater.inflate(de.danoeh.antennapod.R.menu.feeditemlist_context, menu);
menu.setHeaderTitle(longPressedItem.getTitle());
de.danoeh.antennapod.menuhandler.FeedItemMenuHandler.onPrepareMenu(menu, longPressedItem, de.danoeh.antennapod.R.id.skip_episode_item);
}
}


public boolean onContextItemSelected(android.view.MenuItem item) {
if (item.getItemId() == de.danoeh.antennapod.R.id.multi_select) {
startSelectMode(longPressedPosition);
return true;
} else if (item.getItemId() == de.danoeh.antennapod.R.id.select_all_above) {
setSelected(0, longPressedPosition, true);
return true;
} else if (item.getItemId() == de.danoeh.antennapod.R.id.select_all_below) {
shouldSelectLazyLoadedItems = true;
switch(MUID_STATIC) {
    // EpisodeItemListAdapter_3_BinaryMutator
    case 3042: {
        setSelected(longPressedPosition - 1, getItemCount(), true);
        break;
    }
    default: {
    setSelected(longPressedPosition + 1, getItemCount(), true);
    break;
}
}
return true;
}
return false;
}


public java.util.List<de.danoeh.antennapod.model.feed.FeedItem> getSelectedItems() {
java.util.List<de.danoeh.antennapod.model.feed.FeedItem> items;
items = new java.util.ArrayList<>();
for (int i = 0; i < getItemCount(); i++) {
if (isSelected(i)) {
items.add(getItem(i));
}
}
return items;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
