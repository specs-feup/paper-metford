package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.fragment.AllEpisodesFragment;
import java.util.ArrayList;
import de.danoeh.antennapod.fragment.PlaybackHistoryFragment;
import android.view.ContextMenu;
import de.danoeh.antennapod.fragment.QueueFragment;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.ui.home.HomeFragment;
import de.danoeh.antennapod.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Build;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestOptions;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import java.util.List;
import com.bumptech.glide.Glide;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.model.feed.Feed;
import de.danoeh.antennapod.fragment.SubscriptionFragment;
import java.util.Collections;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import android.view.InputDevice;
import androidx.annotation.DrawableRes;
import de.danoeh.antennapod.core.storage.NavDrawerData;
import android.view.ViewGroup;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import android.content.Intent;
import androidx.preference.PreferenceManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.MotionEvent;
import android.view.View;
import de.danoeh.antennapod.fragment.InboxFragment;
import android.view.LayoutInflater;
import de.danoeh.antennapod.fragment.CompletedDownloadsFragment;
import de.danoeh.antennapod.fragment.AddFeedFragment;
import android.widget.RelativeLayout;
import org.apache.commons.lang3.ArrayUtils;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import de.danoeh.antennapod.fragment.NavDrawerFragment;
import java.text.NumberFormat;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * BaseAdapter for the navigation drawer
 */
public class NavListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<de.danoeh.antennapod.adapter.NavListAdapter.Holder> implements android.content.SharedPreferences.OnSharedPreferenceChangeListener {
    static final int MUID_STATIC = getMUID();
    public static final int VIEW_TYPE_NAV = 0;

    public static final int VIEW_TYPE_SECTION_DIVIDER = 1;

    private static final int VIEW_TYPE_SUBSCRIPTION = 2;

    /**
     * a tag used as a placeholder to indicate if the subscription list should be displayed or not
     * This tag doesn't correspond to any specific activity.
     */
    public static final java.lang.String SUBSCRIPTION_LIST_TAG = "SubscriptionList";

    private final java.util.List<java.lang.String> fragmentTags = new java.util.ArrayList<>();

    private final java.lang.String[] titles;

    private final de.danoeh.antennapod.adapter.NavListAdapter.ItemAccess itemAccess;

    private final java.lang.ref.WeakReference<android.app.Activity> activity;

    public boolean showSubscriptionList = true;

    public NavListAdapter(de.danoeh.antennapod.adapter.NavListAdapter.ItemAccess itemAccess, android.app.Activity context) {
        this.itemAccess = itemAccess;
        this.activity = new java.lang.ref.WeakReference<>(context);
        titles = context.getResources().getStringArray(de.danoeh.antennapod.R.array.nav_drawer_titles);
        loadItems();
        android.content.SharedPreferences prefs;
        prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }


    public void onSharedPreferenceChanged(android.content.SharedPreferences sharedPreferences, java.lang.String key) {
        if (de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_HIDDEN_DRAWER_ITEMS.equals(key)) {
            loadItems();
        }
    }


    private void loadItems() {
        java.util.List<java.lang.String> newTags;
        newTags = new java.util.ArrayList<>(java.util.Arrays.asList(de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS));
        java.util.List<java.lang.String> hiddenFragments;
        hiddenFragments = de.danoeh.antennapod.storage.preferences.UserPreferences.getHiddenDrawerItems();
        newTags.removeAll(hiddenFragments);
        if (newTags.contains(de.danoeh.antennapod.adapter.NavListAdapter.SUBSCRIPTION_LIST_TAG)) {
            // we never want SUBSCRIPTION_LIST_TAG to be in 'tags'
            // since it doesn't actually correspond to a position in the list, but is
            // a placeholder that indicates if we should show the subscription list in the
            // nav drawer at all.
            showSubscriptionList = true;
            newTags.remove(de.danoeh.antennapod.adapter.NavListAdapter.SUBSCRIPTION_LIST_TAG);
        } else {
            showSubscriptionList = false;
        }
        fragmentTags.clear();
        fragmentTags.addAll(newTags);
        notifyDataSetChanged();
    }


    public java.lang.String getLabel(java.lang.String tag) {
        int index;
        index = org.apache.commons.lang3.ArrayUtils.indexOf(de.danoeh.antennapod.fragment.NavDrawerFragment.NAV_DRAWER_TAGS, tag);
        return titles[index];
    }


    @androidx.annotation.DrawableRes
    private int getDrawable(java.lang.String tag) {
        switch (tag) {
            case de.danoeh.antennapod.ui.home.HomeFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_home;
            case de.danoeh.antennapod.fragment.QueueFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_playlist_play;
            case de.danoeh.antennapod.fragment.InboxFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_inbox;
            case de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_feed;
            case de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_download;
            case de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_history;
            case de.danoeh.antennapod.fragment.SubscriptionFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_subscriptions;
            case de.danoeh.antennapod.fragment.AddFeedFragment.TAG :
                return de.danoeh.antennapod.R.drawable.ic_add;
            default :
                return 0;
        }
    }


    public java.util.List<java.lang.String> getFragmentTags() {
        return java.util.Collections.unmodifiableList(fragmentTags);
    }


    @java.lang.Override
    public int getItemCount() {
        int baseCount;
        baseCount = getSubscriptionOffset();
        if (showSubscriptionList) {
            baseCount += itemAccess.getCount();
        }
        return baseCount;
    }


    @java.lang.Override
    public long getItemId(int position) {
        int viewType;
        viewType = getItemViewType(position);
        if (viewType == de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_SUBSCRIPTION) {
            switch(MUID_STATIC) {
                // NavListAdapter_0_BinaryMutator
                case 29: {
                    return itemAccess.getItem(position + getSubscriptionOffset()).id;
                }
                default: {
                return itemAccess.getItem(position - getSubscriptionOffset()).id;
                }
        }
    } else if (viewType == de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_NAV) {
        switch(MUID_STATIC) {
            // NavListAdapter_1_BinaryMutator
            case 1029: {
                return (-java.lang.Math.abs(((long) (fragmentTags.get(position).hashCode())))) + 1// Folder IDs are >0
                ;
            }
            default: {
            return (-java.lang.Math.abs(((long) (fragmentTags.get(position).hashCode())))) - 1// Folder IDs are >0
            ;// Folder IDs are >0

            }
    }
} else {
    return 0;
}
}


@java.lang.Override
public int getItemViewType(int position) {
if ((0 <= position) && (position < fragmentTags.size())) {
    return de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_NAV;
} else if (position < getSubscriptionOffset()) {
    return de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_SECTION_DIVIDER;
} else {
    return de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_SUBSCRIPTION;
}
}


public int getSubscriptionOffset() {
switch(MUID_STATIC) {
    // NavListAdapter_2_BinaryMutator
    case 2029: {
        return fragmentTags.size() > 0 ? fragmentTags.size() - 1 : 0;
    }
    default: {
    return fragmentTags.size() > 0 ? fragmentTags.size() + 1 : 0;
    }
}
}


@androidx.annotation.NonNull
@java.lang.Override
public de.danoeh.antennapod.adapter.NavListAdapter.Holder onCreateViewHolder(@androidx.annotation.NonNull
android.view.ViewGroup parent, int viewType) {
android.view.LayoutInflater inflater;
inflater = android.view.LayoutInflater.from(activity.get());
if (viewType == de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_NAV) {
return new de.danoeh.antennapod.adapter.NavListAdapter.NavHolder(inflater.inflate(de.danoeh.antennapod.R.layout.nav_listitem, parent, false));
} else if (viewType == de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_SECTION_DIVIDER) {
return new de.danoeh.antennapod.adapter.NavListAdapter.DividerHolder(inflater.inflate(de.danoeh.antennapod.R.layout.nav_section_item, parent, false));
} else {
return new de.danoeh.antennapod.adapter.NavListAdapter.FeedHolder(inflater.inflate(de.danoeh.antennapod.R.layout.nav_listitem, parent, false));
}
}


@java.lang.Override
public void onBindViewHolder(@androidx.annotation.NonNull
de.danoeh.antennapod.adapter.NavListAdapter.Holder holder, int position) {
int viewType;
viewType = getItemViewType(position);
holder.itemView.setOnCreateContextMenuListener(null);
if (viewType == de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_NAV) {
bindNavView(getLabel(fragmentTags.get(position)), position, ((de.danoeh.antennapod.adapter.NavListAdapter.NavHolder) (holder)));
} else if (viewType == de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_SECTION_DIVIDER) {
bindSectionDivider(((de.danoeh.antennapod.adapter.NavListAdapter.DividerHolder) (holder)));
} else {
int itemPos;
switch(MUID_STATIC) {
    // NavListAdapter_3_BinaryMutator
    case 3029: {
        itemPos = position + getSubscriptionOffset();
        break;
    }
    default: {
    itemPos = position - getSubscriptionOffset();
    break;
}
}
de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item;
item = itemAccess.getItem(itemPos);
bindListItem(item, ((de.danoeh.antennapod.adapter.NavListAdapter.FeedHolder) (holder)));
if (item.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
bindFeedView(((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (item)), ((de.danoeh.antennapod.adapter.NavListAdapter.FeedHolder) (holder)));
} else {
bindTagView(((de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem) (item)), ((de.danoeh.antennapod.adapter.NavListAdapter.FeedHolder) (holder)));
}
holder.itemView.setOnCreateContextMenuListener(itemAccess);
}
if (viewType != de.danoeh.antennapod.adapter.NavListAdapter.VIEW_TYPE_SECTION_DIVIDER) {
holder.itemView.setSelected(itemAccess.isSelected(position));
switch(MUID_STATIC) {
// NavListAdapter_4_BuggyGUIListenerOperatorMutator
case 4029: {
    holder.itemView.setOnClickListener(null);
    break;
}
default: {
holder.itemView.setOnClickListener((android.view.View v) -> itemAccess.onItemClick(position));
break;
}
}
holder.itemView.setOnLongClickListener((android.view.View v) -> itemAccess.onItemLongClick(position));
holder.itemView.setOnTouchListener((android.view.View v,android.view.MotionEvent e) -> {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
if (e.isFromSource(android.view.InputDevice.SOURCE_MOUSE) && (e.getButtonState() == android.view.MotionEvent.BUTTON_SECONDARY)) {
    itemAccess.onItemLongClick(position);
    return false;
}
}
return false;
});
}
}


private void bindNavView(java.lang.String title, int position, de.danoeh.antennapod.adapter.NavListAdapter.NavHolder holder) {
android.app.Activity context;
context = activity.get();
if (context == null) {
return;
}
holder.title.setText(title);
// reset for re-use
holder.count.setVisibility(android.view.View.GONE);
holder.count.setOnClickListener(null);
holder.count.setClickable(false);
java.lang.String tag;
tag = fragmentTags.get(position);
if (tag.equals(de.danoeh.antennapod.fragment.QueueFragment.TAG)) {
int queueSize;
queueSize = itemAccess.getQueueSize();
if (queueSize > 0) {
holder.count.setText(java.text.NumberFormat.getInstance().format(queueSize));
holder.count.setVisibility(android.view.View.VISIBLE);
}
} else if (tag.equals(de.danoeh.antennapod.fragment.InboxFragment.TAG)) {
int unreadItems;
unreadItems = itemAccess.getNumberOfNewItems();
if (unreadItems > 0) {
holder.count.setText(java.text.NumberFormat.getInstance().format(unreadItems));
holder.count.setVisibility(android.view.View.VISIBLE);
}
} else if (tag.equals(de.danoeh.antennapod.fragment.SubscriptionFragment.TAG)) {
int sum;
sum = itemAccess.getFeedCounterSum();
if (sum > 0) {
holder.count.setText(java.text.NumberFormat.getInstance().format(sum));
holder.count.setVisibility(android.view.View.VISIBLE);
}
} else if (tag.equals(de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG) && de.danoeh.antennapod.storage.preferences.UserPreferences.isEnableAutodownload()) {
int epCacheSize;
epCacheSize = de.danoeh.antennapod.storage.preferences.UserPreferences.getEpisodeCacheSize();
// don't count episodes that can be reclaimed
int spaceUsed;
switch(MUID_STATIC) {
// NavListAdapter_5_BinaryMutator
case 5029: {
spaceUsed = itemAccess.getNumberOfDownloadedItems() + itemAccess.getReclaimableItems();
break;
}
default: {
spaceUsed = itemAccess.getNumberOfDownloadedItems() - itemAccess.getReclaimableItems();
break;
}
}
if ((epCacheSize > 0) && (spaceUsed >= epCacheSize)) {
holder.count.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, de.danoeh.antennapod.R.drawable.ic_disc_full, 0);
holder.count.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// NavListAdapter_6_BuggyGUIListenerOperatorMutator
case 6029: {
holder.count.setOnClickListener(null);
break;
}
default: {
switch(MUID_STATIC) {
// NavListAdapter_7_BuggyGUIListenerOperatorMutator
case 7029: {
    holder.count.setOnClickListener((android.view.View v) -> new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.episode_cache_full_title).setMessage(de.danoeh.antennapod.R.string.episode_cache_full_message).setPositiveButton(android.R.string.ok, null).setNeutralButton(de.danoeh.antennapod.R.string.open_autodownload_settings, null).show());
    break;
}
default: {
holder.count.setOnClickListener((android.view.View v) -> new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.episode_cache_full_title).setMessage(de.danoeh.antennapod.R.string.episode_cache_full_message).setPositiveButton(android.R.string.ok, null).setNeutralButton(de.danoeh.antennapod.R.string.open_autodownload_settings, (android.content.DialogInterface dialog,int which) -> {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // NavListAdapter_8_InvalidKeyIntentOperatorMutator
        case 8029: {
            intent = new android.content.Intent((Activity) null, de.danoeh.antennapod.activity.PreferenceActivity.class);
            break;
        }
        // NavListAdapter_9_RandomActionIntentDefinitionOperatorMutator
        case 9029: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(context, de.danoeh.antennapod.activity.PreferenceActivity.class);
        break;
    }
}
switch(MUID_STATIC) {
    // NavListAdapter_10_NullValueIntentPutExtraOperatorMutator
    case 10029: {
        intent.putExtra(de.danoeh.antennapod.activity.PreferenceActivity.OPEN_AUTO_DOWNLOAD_SETTINGS, new Parcelable[0]);
        break;
    }
    // NavListAdapter_11_IntentPayloadReplacementOperatorMutator
    case 11029: {
        intent.putExtra(de.danoeh.antennapod.activity.PreferenceActivity.OPEN_AUTO_DOWNLOAD_SETTINGS, true);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // NavListAdapter_12_RandomActionIntentDefinitionOperatorMutator
        case 12029: {
            /**
            * Inserted by Kadabra
            */
            /**
            * Inserted by Kadabra
            */
            new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent.putExtra(de.danoeh.antennapod.activity.PreferenceActivity.OPEN_AUTO_DOWNLOAD_SETTINGS, true);
        break;
    }
}
break;
}
}
context.startActivity(intent);
}).show());
break;
}
}
break;
}
}
}
}
holder.image.setImageResource(getDrawable(fragmentTags.get(position)));
}


private void bindSectionDivider(de.danoeh.antennapod.adapter.NavListAdapter.DividerHolder holder) {
android.app.Activity context;
context = activity.get();
if (context == null) {
return;
}
if (de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter().isEnabled() && showSubscriptionList) {
holder.itemView.setEnabled(true);
holder.feedsFilteredMsg.setVisibility(android.view.View.VISIBLE);
} else {
holder.itemView.setEnabled(false);
holder.feedsFilteredMsg.setVisibility(android.view.View.GONE);
}
}


private void bindListItem(de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem item, de.danoeh.antennapod.adapter.NavListAdapter.FeedHolder holder) {
if (item.getCounter() > 0) {
holder.count.setVisibility(android.view.View.VISIBLE);
holder.count.setText(java.text.NumberFormat.getInstance().format(item.getCounter()));
} else {
holder.count.setVisibility(android.view.View.GONE);
}
holder.title.setText(item.getTitle());
int padding;
switch(MUID_STATIC) {
// NavListAdapter_13_BinaryMutator
case 13029: {
padding = ((int) (activity.get().getResources().getDimension(de.danoeh.antennapod.R.dimen.thumbnail_length_navlist) * 2));
break;
}
default: {
padding = ((int) (activity.get().getResources().getDimension(de.danoeh.antennapod.R.dimen.thumbnail_length_navlist) / 2));
break;
}
}
switch(MUID_STATIC) {
// NavListAdapter_14_BinaryMutator
case 14029: {
holder.itemView.setPadding(item.getLayer() / padding, 0, 0, 0);
break;
}
default: {
holder.itemView.setPadding(item.getLayer() * padding, 0, 0, 0);
break;
}
}
}


private void bindFeedView(de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem drawerItem, de.danoeh.antennapod.adapter.NavListAdapter.FeedHolder holder) {
de.danoeh.antennapod.model.feed.Feed feed;
feed = drawerItem.feed;
android.app.Activity context;
context = activity.get();
if (context == null) {
return;
}
switch(MUID_STATIC) {
// NavListAdapter_15_BinaryMutator
case 15029: {
com.bumptech.glide.Glide.with(context).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).error(de.danoeh.antennapod.R.color.light_gray).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (4 / context.getResources().getDisplayMetrics().density)))).dontAnimate()).into(holder.image);
break;
}
default: {
com.bumptech.glide.Glide.with(context).load(feed.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).error(de.danoeh.antennapod.R.color.light_gray).transform(new com.bumptech.glide.load.resource.bitmap.FitCenter(), new com.bumptech.glide.load.resource.bitmap.RoundedCorners(((int) (4 * context.getResources().getDisplayMetrics().density)))).dontAnimate()).into(holder.image);
break;
}
}
if (feed.hasLastUpdateFailed()) {
android.widget.RelativeLayout.LayoutParams p;
p = ((android.widget.RelativeLayout.LayoutParams) (holder.title.getLayoutParams()));
p.addRule(android.widget.RelativeLayout.LEFT_OF, de.danoeh.antennapod.R.id.itxtvFailure);
holder.failure.setVisibility(android.view.View.VISIBLE);
} else {
android.widget.RelativeLayout.LayoutParams p;
p = ((android.widget.RelativeLayout.LayoutParams) (holder.title.getLayoutParams()));
p.addRule(android.widget.RelativeLayout.LEFT_OF, de.danoeh.antennapod.R.id.txtvCount);
holder.failure.setVisibility(android.view.View.GONE);
}
}


private void bindTagView(de.danoeh.antennapod.core.storage.NavDrawerData.TagDrawerItem tag, de.danoeh.antennapod.adapter.NavListAdapter.FeedHolder holder) {
android.app.Activity context;
context = activity.get();
if (context == null) {
return;
}
if (tag.isOpen) {
holder.count.setVisibility(android.view.View.GONE);
}
com.bumptech.glide.Glide.with(context).clear(holder.image);
holder.image.setImageResource(de.danoeh.antennapod.R.drawable.ic_tag);
holder.failure.setVisibility(android.view.View.GONE);
}


static class Holder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
public Holder(@androidx.annotation.NonNull
android.view.View itemView) {
super(itemView);
}

}

static class DividerHolder extends de.danoeh.antennapod.adapter.NavListAdapter.Holder {
final android.widget.LinearLayout feedsFilteredMsg;

public DividerHolder(@androidx.annotation.NonNull
android.view.View itemView) {
super(itemView);
switch(MUID_STATIC) {
// NavListAdapter_16_InvalidViewFocusOperatorMutator
case 16029: {
/**
* Inserted by Kadabra
*/
feedsFilteredMsg = itemView.findViewById(de.danoeh.antennapod.R.id.nav_feeds_filtered_message);
feedsFilteredMsg.requestFocus();
break;
}
// NavListAdapter_17_ViewComponentNotVisibleOperatorMutator
case 17029: {
/**
* Inserted by Kadabra
*/
feedsFilteredMsg = itemView.findViewById(de.danoeh.antennapod.R.id.nav_feeds_filtered_message);
feedsFilteredMsg.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
feedsFilteredMsg = itemView.findViewById(de.danoeh.antennapod.R.id.nav_feeds_filtered_message);
break;
}
}
}

}

static class NavHolder extends de.danoeh.antennapod.adapter.NavListAdapter.Holder {
final android.widget.ImageView image;

final android.widget.TextView title;

final android.widget.TextView count;

public NavHolder(@androidx.annotation.NonNull
android.view.View itemView) {
super(itemView);
switch(MUID_STATIC) {
// NavListAdapter_18_InvalidViewFocusOperatorMutator
case 18029: {
/**
* Inserted by Kadabra
*/
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
image.requestFocus();
break;
}
// NavListAdapter_19_ViewComponentNotVisibleOperatorMutator
case 19029: {
/**
* Inserted by Kadabra
*/
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
image.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
break;
}
}
switch(MUID_STATIC) {
// NavListAdapter_20_InvalidViewFocusOperatorMutator
case 20029: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.requestFocus();
break;
}
// NavListAdapter_21_ViewComponentNotVisibleOperatorMutator
case 21029: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
break;
}
}
switch(MUID_STATIC) {
// NavListAdapter_22_InvalidViewFocusOperatorMutator
case 22029: {
/**
* Inserted by Kadabra
*/
count = itemView.findViewById(de.danoeh.antennapod.R.id.txtvCount);
count.requestFocus();
break;
}
// NavListAdapter_23_ViewComponentNotVisibleOperatorMutator
case 23029: {
/**
* Inserted by Kadabra
*/
count = itemView.findViewById(de.danoeh.antennapod.R.id.txtvCount);
count.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
count = itemView.findViewById(de.danoeh.antennapod.R.id.txtvCount);
break;
}
}
}

}

static class FeedHolder extends de.danoeh.antennapod.adapter.NavListAdapter.Holder {
final android.widget.ImageView image;

final android.widget.TextView title;

final android.widget.ImageView failure;

final android.widget.TextView count;

public FeedHolder(@androidx.annotation.NonNull
android.view.View itemView) {
super(itemView);
switch(MUID_STATIC) {
// NavListAdapter_24_InvalidViewFocusOperatorMutator
case 24029: {
/**
* Inserted by Kadabra
*/
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
image.requestFocus();
break;
}
// NavListAdapter_25_ViewComponentNotVisibleOperatorMutator
case 25029: {
/**
* Inserted by Kadabra
*/
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
image.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
image = itemView.findViewById(de.danoeh.antennapod.R.id.imgvCover);
break;
}
}
switch(MUID_STATIC) {
// NavListAdapter_26_InvalidViewFocusOperatorMutator
case 26029: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.requestFocus();
break;
}
// NavListAdapter_27_ViewComponentNotVisibleOperatorMutator
case 27029: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
break;
}
}
switch(MUID_STATIC) {
// NavListAdapter_28_InvalidViewFocusOperatorMutator
case 28029: {
/**
* Inserted by Kadabra
*/
failure = itemView.findViewById(de.danoeh.antennapod.R.id.itxtvFailure);
failure.requestFocus();
break;
}
// NavListAdapter_29_ViewComponentNotVisibleOperatorMutator
case 29029: {
/**
* Inserted by Kadabra
*/
failure = itemView.findViewById(de.danoeh.antennapod.R.id.itxtvFailure);
failure.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
failure = itemView.findViewById(de.danoeh.antennapod.R.id.itxtvFailure);
break;
}
}
switch(MUID_STATIC) {
// NavListAdapter_30_InvalidViewFocusOperatorMutator
case 30029: {
/**
* Inserted by Kadabra
*/
count = itemView.findViewById(de.danoeh.antennapod.R.id.txtvCount);
count.requestFocus();
break;
}
// NavListAdapter_31_ViewComponentNotVisibleOperatorMutator
case 31029: {
/**
* Inserted by Kadabra
*/
count = itemView.findViewById(de.danoeh.antennapod.R.id.txtvCount);
count.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
count = itemView.findViewById(de.danoeh.antennapod.R.id.txtvCount);
break;
}
}
}

}

public interface ItemAccess extends android.view.View.OnCreateContextMenuListener {
int getCount();


de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem getItem(int position);


boolean isSelected(int position);


int getQueueSize();


int getNumberOfNewItems();


int getNumberOfDownloadedItems();


int getReclaimableItems();


int getFeedCounterSum();


void onItemClick(int position);


boolean onItemLongClick(int position);


@java.lang.Override
void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
