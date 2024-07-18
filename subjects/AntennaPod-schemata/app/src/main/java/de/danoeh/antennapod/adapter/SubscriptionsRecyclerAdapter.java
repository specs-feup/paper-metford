package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import java.util.ArrayList;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.ContextMenu;
import androidx.fragment.app.Fragment;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import de.danoeh.antennapod.fragment.SubscriptionFragment;
import de.danoeh.antennapod.model.feed.Feed;
import com.google.android.material.elevation.SurfaceColors;
import android.graphics.Rect;
import android.view.InputDevice;
import android.view.MenuInflater;
import android.graphics.Canvas;
import de.danoeh.antennapod.core.storage.NavDrawerData;
import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import androidx.cardview.widget.CardView;
import de.danoeh.antennapod.fragment.FeedItemlistFragment;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.content.res.AppCompatResources;
import android.widget.CheckBox;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Adapter for subscriptions
 */
public class SubscriptionsRecyclerAdapter extends de.danoeh.antennapod.adapter.SelectableAdapter<de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.SubscriptionViewHolder> implements android.view.View.OnCreateContextMenuListener {
    static final int MUID_STATIC = getMUID();
    private static final int COVER_WITH_TITLE = 1;

    private final java.lang.ref.WeakReference<de.danoeh.antennapod.activity.MainActivity> mainActivityRef;

    private java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> listItems;

    private de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem selectedItem = null;

    int longPressedPosition = 0;// used to init actionMode


    private int columnCount = 3;

    public SubscriptionsRecyclerAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity) {
        super(mainActivity);
        this.mainActivityRef = new java.lang.ref.WeakReference<>(mainActivity);
        this.listItems = new java.util.ArrayList<>();
        setHasStableIds(true);
    }


    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }


    public java.lang.Object getItem(int position) {
        return listItems.get(position);
    }


    public de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem getSelectedItem() {
        return selectedItem;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.SubscriptionViewHolder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        android.view.View itemView;
        itemView = android.view.LayoutInflater.from(mainActivityRef.get()).inflate(de.danoeh.antennapod.R.layout.subscription_item, parent, false);
        itemView.findViewById(de.danoeh.antennapod.R.id.titleLabel).setVisibility(viewType == de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.COVER_WITH_TITLE ? android.view.View.VISIBLE : android.view.View.GONE);
        return new de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.SubscriptionViewHolder(itemView);
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.SubscriptionViewHolder holder, int position) {
        de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem;
        drawerItem = listItems.get(position);
        boolean isFeed;
        isFeed = drawerItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED;
        holder.bind(drawerItem);
        holder.itemView.setOnCreateContextMenuListener(this);
        if (inActionMode()) {
            if (isFeed) {
                holder.selectCheckbox.setVisibility(android.view.View.VISIBLE);
                holder.selectView.setVisibility(android.view.View.VISIBLE);
            }
            holder.selectCheckbox.setChecked(isSelected(position));
            holder.selectCheckbox.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> setSelected(holder.getBindingAdapterPosition(), isChecked));
            holder.coverImage.setAlpha(0.6F);
            holder.count.setVisibility(android.view.View.GONE);
        } else {
            holder.selectView.setVisibility(android.view.View.GONE);
            holder.coverImage.setAlpha(1.0F);
        }
        holder.itemView.setOnLongClickListener((android.view.View v) -> {
            if (!inActionMode()) {
                if (isFeed) {
                    longPressedPosition = holder.getBindingAdapterPosition();
                }
                selectedItem = drawerItem;
            }
            return false;
        });
        holder.itemView.setOnTouchListener((android.view.View v,android.view.MotionEvent e) -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                if (e.isFromSource(android.view.InputDevice.SOURCE_MOUSE) && (e.getButtonState() == android.view.MotionEvent.BUTTON_SECONDARY)) {
                    if (!inActionMode()) {
                        if (isFeed) {
                            longPressedPosition = holder.getBindingAdapterPosition();
                        }
                        selectedItem = drawerItem;
                    }
                }
            }
            return false;
        });
        switch(MUID_STATIC) {
            // SubscriptionsRecyclerAdapter_0_BuggyGUIListenerOperatorMutator
            case 40: {
                holder.itemView.setOnClickListener(null);
                break;
            }
            default: {
            holder.itemView.setOnClickListener((android.view.View v) -> {
                if (isFeed) {
                    if (inActionMode()) {
                        holder.selectCheckbox.setChecked(!isSelected(holder.getBindingAdapterPosition()));
                    } else {
                        androidx.fragment.app.Fragment fragment;
                        fragment = de.danoeh.antennapod.fragment.FeedItemlistFragment.newInstance(((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (drawerItem)).feed.getId());
                        mainActivityRef.get().loadChildFragment(fragment);
                    }
                } else if (!inActionMode()) {
                    androidx.fragment.app.Fragment fragment;
                    fragment = de.danoeh.antennapod.fragment.SubscriptionFragment.newInstance(drawerItem.getTitle());
                    mainActivityRef.get().loadChildFragment(fragment);
                }
            });
            break;
        }
    }
}


@java.lang.Override
public int getItemCount() {
    return listItems.size();
}


@java.lang.Override
public long getItemId(int position) {
    if (position >= listItems.size()) {
        return androidx.recyclerview.widget.RecyclerView.NO_ID// Dummy views
        ;// Dummy views

    }
    return listItems.get(position).id;
}


@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
    if (inActionMode() || (selectedItem == null)) {
        return;
    }
    android.view.MenuInflater inflater;
    inflater = mainActivityRef.get().getMenuInflater();
    if (selectedItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
        inflater.inflate(de.danoeh.antennapod.R.menu.nav_feed_context, menu);
        menu.findItem(de.danoeh.antennapod.R.id.multi_select).setVisible(true);
    } else {
        inflater.inflate(de.danoeh.antennapod.R.menu.nav_folder_context, menu);
    }
    menu.setHeaderTitle(selectedItem.getTitle());
}


public boolean onContextItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == de.danoeh.antennapod.R.id.multi_select) {
        startSelectMode(longPressedPosition);
        return true;
    }
    return false;
}


public java.util.List<de.danoeh.antennapod.model.feed.Feed> getSelectedItems() {
    java.util.List<de.danoeh.antennapod.model.feed.Feed> items;
    items = new java.util.ArrayList<>();
    for (int i = 0; i < getItemCount(); i++) {
        if (isSelected(i)) {
            de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem;
            drawerItem = listItems.get(i);
            if (drawerItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
                de.danoeh.antennapod.model.feed.Feed feed;
                feed = ((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (drawerItem)).feed;
                items.add(feed);
            }
        }
    }
    return items;
}


public void setItems(java.util.List<de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem> listItems) {
    this.listItems = listItems;
    notifyDataSetChanged();
}


@java.lang.Override
public void setSelected(int pos, boolean selected) {
    de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem;
    drawerItem = listItems.get(pos);
    if (drawerItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
        super.setSelected(pos, selected);
    }
}


@java.lang.Override
public int getItemViewType(int position) {
    return de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowSubscriptionTitle() ? de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.COVER_WITH_TITLE : 0;
}


public class SubscriptionViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    private final android.widget.TextView title;

    private final android.widget.ImageView coverImage;

    private final android.widget.TextView count;

    private final android.widget.TextView fallbackTitle;

    private final android.widget.FrameLayout selectView;

    private final android.widget.CheckBox selectCheckbox;

    private final androidx.cardview.widget.CardView card;

    private final android.view.View errorIcon;

    public SubscriptionViewHolder(@androidx.annotation.NonNull
    android.view.View itemView) {
        super(itemView);
        switch(MUID_STATIC) {
            // SubscriptionsRecyclerAdapter_1_InvalidViewFocusOperatorMutator
            case 1040: {
                /**
                * Inserted by Kadabra
                */
                title = itemView.findViewById(de.danoeh.antennapod.R.id.titleLabel);
                title.requestFocus();
                break;
            }
            // SubscriptionsRecyclerAdapter_2_ViewComponentNotVisibleOperatorMutator
            case 2040: {
                /**
                * Inserted by Kadabra
                */
                title = itemView.findViewById(de.danoeh.antennapod.R.id.titleLabel);
                title.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            title = itemView.findViewById(de.danoeh.antennapod.R.id.titleLabel);
            break;
        }
    }
    switch(MUID_STATIC) {
        // SubscriptionsRecyclerAdapter_3_InvalidViewFocusOperatorMutator
        case 3040: {
            /**
            * Inserted by Kadabra
            */
            coverImage = itemView.findViewById(de.danoeh.antennapod.R.id.coverImage);
            coverImage.requestFocus();
            break;
        }
        // SubscriptionsRecyclerAdapter_4_ViewComponentNotVisibleOperatorMutator
        case 4040: {
            /**
            * Inserted by Kadabra
            */
            coverImage = itemView.findViewById(de.danoeh.antennapod.R.id.coverImage);
            coverImage.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        coverImage = itemView.findViewById(de.danoeh.antennapod.R.id.coverImage);
        break;
    }
}
switch(MUID_STATIC) {
    // SubscriptionsRecyclerAdapter_5_InvalidViewFocusOperatorMutator
    case 5040: {
        /**
        * Inserted by Kadabra
        */
        count = itemView.findViewById(de.danoeh.antennapod.R.id.countViewPill);
        count.requestFocus();
        break;
    }
    // SubscriptionsRecyclerAdapter_6_ViewComponentNotVisibleOperatorMutator
    case 6040: {
        /**
        * Inserted by Kadabra
        */
        count = itemView.findViewById(de.danoeh.antennapod.R.id.countViewPill);
        count.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    count = itemView.findViewById(de.danoeh.antennapod.R.id.countViewPill);
    break;
}
}
switch(MUID_STATIC) {
// SubscriptionsRecyclerAdapter_7_InvalidViewFocusOperatorMutator
case 7040: {
    /**
    * Inserted by Kadabra
    */
    fallbackTitle = itemView.findViewById(de.danoeh.antennapod.R.id.fallbackTitleLabel);
    fallbackTitle.requestFocus();
    break;
}
// SubscriptionsRecyclerAdapter_8_ViewComponentNotVisibleOperatorMutator
case 8040: {
    /**
    * Inserted by Kadabra
    */
    fallbackTitle = itemView.findViewById(de.danoeh.antennapod.R.id.fallbackTitleLabel);
    fallbackTitle.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
fallbackTitle = itemView.findViewById(de.danoeh.antennapod.R.id.fallbackTitleLabel);
break;
}
}
switch(MUID_STATIC) {
// SubscriptionsRecyclerAdapter_9_InvalidViewFocusOperatorMutator
case 9040: {
/**
* Inserted by Kadabra
*/
selectView = itemView.findViewById(de.danoeh.antennapod.R.id.selectContainer);
selectView.requestFocus();
break;
}
// SubscriptionsRecyclerAdapter_10_ViewComponentNotVisibleOperatorMutator
case 10040: {
/**
* Inserted by Kadabra
*/
selectView = itemView.findViewById(de.danoeh.antennapod.R.id.selectContainer);
selectView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
selectView = itemView.findViewById(de.danoeh.antennapod.R.id.selectContainer);
break;
}
}
switch(MUID_STATIC) {
// SubscriptionsRecyclerAdapter_11_InvalidViewFocusOperatorMutator
case 11040: {
/**
* Inserted by Kadabra
*/
selectCheckbox = itemView.findViewById(de.danoeh.antennapod.R.id.selectCheckBox);
selectCheckbox.requestFocus();
break;
}
// SubscriptionsRecyclerAdapter_12_ViewComponentNotVisibleOperatorMutator
case 12040: {
/**
* Inserted by Kadabra
*/
selectCheckbox = itemView.findViewById(de.danoeh.antennapod.R.id.selectCheckBox);
selectCheckbox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
selectCheckbox = itemView.findViewById(de.danoeh.antennapod.R.id.selectCheckBox);
break;
}
}
switch(MUID_STATIC) {
// SubscriptionsRecyclerAdapter_13_InvalidViewFocusOperatorMutator
case 13040: {
/**
* Inserted by Kadabra
*/
card = itemView.findViewById(de.danoeh.antennapod.R.id.outerContainer);
card.requestFocus();
break;
}
// SubscriptionsRecyclerAdapter_14_ViewComponentNotVisibleOperatorMutator
case 14040: {
/**
* Inserted by Kadabra
*/
card = itemView.findViewById(de.danoeh.antennapod.R.id.outerContainer);
card.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
card = itemView.findViewById(de.danoeh.antennapod.R.id.outerContainer);
break;
}
}
switch(MUID_STATIC) {
// SubscriptionsRecyclerAdapter_15_InvalidViewFocusOperatorMutator
case 15040: {
/**
* Inserted by Kadabra
*/
errorIcon = itemView.findViewById(de.danoeh.antennapod.R.id.errorIcon);
errorIcon.requestFocus();
break;
}
// SubscriptionsRecyclerAdapter_16_ViewComponentNotVisibleOperatorMutator
case 16040: {
/**
* Inserted by Kadabra
*/
errorIcon = itemView.findViewById(de.danoeh.antennapod.R.id.errorIcon);
errorIcon.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
errorIcon = itemView.findViewById(de.danoeh.antennapod.R.id.errorIcon);
break;
}
}
}


public void bind(de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem drawerItem) {
android.graphics.drawable.Drawable drawable;
drawable = androidx.appcompat.content.res.AppCompatResources.getDrawable(selectView.getContext(), de.danoeh.antennapod.R.drawable.ic_checkbox_background);
selectView.setBackground(drawable)// Setting this in XML crashes API <= 21
;// Setting this in XML crashes API <= 21

title.setText(drawerItem.getTitle());
fallbackTitle.setText(drawerItem.getTitle());
coverImage.setContentDescription(drawerItem.getTitle());
if (drawerItem.getCounter() > 0) {
count.setText(java.text.NumberFormat.getInstance().format(drawerItem.getCounter()));
count.setVisibility(android.view.View.VISIBLE);
} else {
count.setVisibility(android.view.View.GONE);
}
de.danoeh.antennapod.adapter.CoverLoader coverLoader;
coverLoader = new de.danoeh.antennapod.adapter.CoverLoader(mainActivityRef.get());
boolean textAndImageCombined;
if (drawerItem.type == de.danoeh.antennapod.core.storage.NavDrawerData.DrawerItem.Type.FEED) {
de.danoeh.antennapod.model.feed.Feed feed;
feed = ((de.danoeh.antennapod.core.storage.NavDrawerData.FeedDrawerItem) (drawerItem)).feed;
textAndImageCombined = (feed.isLocalFeed() && (feed.getImageUrl() != null)) && feed.getImageUrl().startsWith(de.danoeh.antennapod.model.feed.Feed.PREFIX_GENERATIVE_COVER);
coverLoader.withUri(feed.getImageUrl());
errorIcon.setVisibility(feed.hasLastUpdateFailed() ? android.view.View.VISIBLE : android.view.View.GONE);
} else {
textAndImageCombined = true;
coverLoader.withResource(de.danoeh.antennapod.R.drawable.ic_tag);
errorIcon.setVisibility(android.view.View.GONE);
}
if (de.danoeh.antennapod.storage.preferences.UserPreferences.shouldShowSubscriptionTitle()) {
// No need for fallback title when already showing title
fallbackTitle.setVisibility(android.view.View.GONE);
} else {
coverLoader.withPlaceholderView(fallbackTitle, textAndImageCombined);
}
coverLoader.withCoverView(coverImage);
coverLoader.load();
float density;
density = mainActivityRef.get().getResources().getDisplayMetrics().density;
switch(MUID_STATIC) {
// SubscriptionsRecyclerAdapter_17_BinaryMutator
case 17040: {
card.setCardBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(mainActivityRef.get(), 1 / density));
break;
}
default: {
card.setCardBackgroundColor(com.google.android.material.elevation.SurfaceColors.getColorForElevation(mainActivityRef.get(), 1 * density));
break;
}
}
int textPadding;
textPadding = (columnCount <= 3) ? 16 : 8;
title.setPadding(textPadding, textPadding, textPadding, textPadding);
fallbackTitle.setPadding(textPadding, textPadding, textPadding, textPadding);
int textSize;
textSize = 14;
if (columnCount == 3) {
textSize = 15;
} else if (columnCount == 2) {
textSize = 16;
}
title.setTextSize(textSize);
fallbackTitle.setTextSize(textSize);
}

}

public static float convertDpToPixel(android.content.Context context, float dp) {
switch(MUID_STATIC) {
// SubscriptionsRecyclerAdapter_18_BinaryMutator
case 18040: {
return dp / context.getResources().getDisplayMetrics().density;
}
default: {
return dp * context.getResources().getDisplayMetrics().density;
}
}
}


public static class GridDividerItemDecorator extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {
@java.lang.Override
public void onDraw(@androidx.annotation.NonNull
android.graphics.Canvas c, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView parent, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.State state) {
super.onDraw(c, parent, state);
}


@java.lang.Override
public void getItemOffsets(@androidx.annotation.NonNull
android.graphics.Rect outRect, @androidx.annotation.NonNull
android.view.View view, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView parent, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.State state) {
super.getItemOffsets(outRect, view, parent, state);
android.content.Context context;
context = parent.getContext();
int insetOffset;
insetOffset = ((int) (de.danoeh.antennapod.adapter.SubscriptionsRecyclerAdapter.convertDpToPixel(context, 1.0F)));
outRect.set(insetOffset, insetOffset, insetOffset, insetOffset);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
