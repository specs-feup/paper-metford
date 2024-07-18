package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.ui.common.SquareImageView;
import android.view.MenuInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import de.danoeh.antennapod.activity.MainActivity;
import androidx.cardview.widget.CardView;
import android.view.ContextMenu;
import de.danoeh.antennapod.fragment.FeedItemlistFragment;
import android.view.View;
import java.lang.ref.WeakReference;
import de.danoeh.antennapod.R;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.bumptech.glide.request.RequestOptions;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.bumptech.glide.Glide;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.model.feed.Feed;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class HorizontalFeedListAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<de.danoeh.antennapod.adapter.HorizontalFeedListAdapter.Holder> implements android.view.View.OnCreateContextMenuListener {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<de.danoeh.antennapod.activity.MainActivity> mainActivityRef;

    private final java.util.List<de.danoeh.antennapod.model.feed.Feed> data = new java.util.ArrayList<>();

    private int dummyViews = 0;

    private de.danoeh.antennapod.model.feed.Feed longPressedItem;

    @androidx.annotation.StringRes
    private int endButtonText = 0;

    private java.lang.Runnable endButtonAction = null;

    public HorizontalFeedListAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity) {
        this.mainActivityRef = new java.lang.ref.WeakReference<>(mainActivity);
    }


    public void setDummyViews(int dummyViews) {
        this.dummyViews = dummyViews;
    }


    public void updateData(java.util.List<de.danoeh.antennapod.model.feed.Feed> newData) {
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public de.danoeh.antennapod.adapter.HorizontalFeedListAdapter.Holder onCreateViewHolder(@androidx.annotation.NonNull
    android.view.ViewGroup parent, int viewType) {
        android.view.View convertView;
        convertView = android.view.View.inflate(mainActivityRef.get(), de.danoeh.antennapod.R.layout.horizontal_feed_item, null);
        return new de.danoeh.antennapod.adapter.HorizontalFeedListAdapter.Holder(convertView);
    }


    @java.lang.Override
    public void onBindViewHolder(@androidx.annotation.NonNull
    de.danoeh.antennapod.adapter.HorizontalFeedListAdapter.Holder holder, int position) {
        switch(MUID_STATIC) {
            // HorizontalFeedListAdapter_0_BinaryMutator
            case 30: {
                if ((position == (getItemCount() + 1)) && (endButtonAction != null)) {
                    holder.cardView.setVisibility(android.view.View.GONE);
                    holder.actionButton.setVisibility(android.view.View.VISIBLE);
                    holder.actionButton.setText(endButtonText);
                    holder.actionButton.setOnClickListener((android.view.View v) -> endButtonAction.run());
                    return;
                }
                break;
            }
            default: {
            if ((position == (getItemCount() - 1)) && (endButtonAction != null)) {
                holder.cardView.setVisibility(android.view.View.GONE);
                holder.actionButton.setVisibility(android.view.View.VISIBLE);
                holder.actionButton.setText(endButtonText);
                switch(MUID_STATIC) {
                    // HorizontalFeedListAdapter_1_BuggyGUIListenerOperatorMutator
                    case 1030: {
                        holder.actionButton.setOnClickListener(null);
                        break;
                    }
                    default: {
                    holder.actionButton.setOnClickListener((android.view.View v) -> endButtonAction.run());
                    break;
                }
            }
            return;
        }
        break;
    }
}
holder.cardView.setVisibility(android.view.View.VISIBLE);
holder.actionButton.setVisibility(android.view.View.GONE);
if (position >= data.size()) {
    holder.itemView.setAlpha(0.1F);
    com.bumptech.glide.Glide.with(mainActivityRef.get()).clear(holder.imageView);
    holder.imageView.setImageResource(de.danoeh.antennapod.R.color.medium_gray);
    return;
}
holder.itemView.setAlpha(1.0F);
final de.danoeh.antennapod.model.feed.Feed podcast;
podcast = data.get(position);
holder.imageView.setContentDescription(podcast.getTitle());
switch(MUID_STATIC) {
    // HorizontalFeedListAdapter_2_BuggyGUIListenerOperatorMutator
    case 2030: {
        holder.imageView.setOnClickListener(null);
        break;
    }
    default: {
    holder.imageView.setOnClickListener((android.view.View v) -> mainActivityRef.get().loadChildFragment(de.danoeh.antennapod.fragment.FeedItemlistFragment.newInstance(podcast.getId())));
    break;
}
}
holder.imageView.setOnCreateContextMenuListener(this);
holder.imageView.setOnLongClickListener((android.view.View v) -> {
int currentItemPosition;
currentItemPosition = holder.getBindingAdapterPosition();
longPressedItem = data.get(currentItemPosition);
return false;
});
com.bumptech.glide.Glide.with(mainActivityRef.get()).load(podcast.getImageUrl()).apply(new com.bumptech.glide.request.RequestOptions().placeholder(de.danoeh.antennapod.R.color.light_gray).fitCenter().dontAnimate()).into(holder.imageView);
}


@androidx.annotation.Nullable
public de.danoeh.antennapod.model.feed.Feed getLongPressedItem() {
return longPressedItem;
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
// HorizontalFeedListAdapter_3_BinaryMutator
case 3030: {
    return (dummyViews + data.size()) - (endButtonAction == null ? 0 : 1);
}
default: {
switch(MUID_STATIC) {
    // HorizontalFeedListAdapter_4_BinaryMutator
    case 4030: {
        return (dummyViews - data.size()) + (endButtonAction == null ? 0 : 1);
    }
    default: {
    return (dummyViews + data.size()) + (endButtonAction == null ? 0 : 1);
    }
}
}
}
}


@java.lang.Override
public void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
android.view.MenuInflater inflater;
inflater = mainActivityRef.get().getMenuInflater();
if (longPressedItem == null) {
return;
}
inflater.inflate(de.danoeh.antennapod.R.menu.nav_feed_context, contextMenu);
contextMenu.setHeaderTitle(longPressedItem.getTitle());
}


public void setEndButton(@androidx.annotation.StringRes
int text, java.lang.Runnable action) {
endButtonAction = action;
endButtonText = text;
notifyDataSetChanged();
}


static class Holder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
de.danoeh.antennapod.ui.common.SquareImageView imageView;

androidx.cardview.widget.CardView cardView;

android.widget.Button actionButton;

public Holder(@androidx.annotation.NonNull
android.view.View itemView) {
super(itemView);
switch(MUID_STATIC) {
// HorizontalFeedListAdapter_5_InvalidViewFocusOperatorMutator
case 5030: {
/**
* Inserted by Kadabra
*/
imageView = itemView.findViewById(de.danoeh.antennapod.R.id.discovery_cover);
imageView.requestFocus();
break;
}
// HorizontalFeedListAdapter_6_ViewComponentNotVisibleOperatorMutator
case 6030: {
/**
* Inserted by Kadabra
*/
imageView = itemView.findViewById(de.danoeh.antennapod.R.id.discovery_cover);
imageView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
imageView = itemView.findViewById(de.danoeh.antennapod.R.id.discovery_cover);
break;
}
}
imageView.setDirection(de.danoeh.antennapod.ui.common.SquareImageView.DIRECTION_HEIGHT);
switch(MUID_STATIC) {
// HorizontalFeedListAdapter_7_InvalidViewFocusOperatorMutator
case 7030: {
/**
* Inserted by Kadabra
*/
actionButton = itemView.findViewById(de.danoeh.antennapod.R.id.actionButton);
actionButton.requestFocus();
break;
}
// HorizontalFeedListAdapter_8_ViewComponentNotVisibleOperatorMutator
case 8030: {
/**
* Inserted by Kadabra
*/
actionButton = itemView.findViewById(de.danoeh.antennapod.R.id.actionButton);
actionButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
actionButton = itemView.findViewById(de.danoeh.antennapod.R.id.actionButton);
break;
}
}
switch(MUID_STATIC) {
// HorizontalFeedListAdapter_9_InvalidViewFocusOperatorMutator
case 9030: {
/**
* Inserted by Kadabra
*/
cardView = itemView.findViewById(de.danoeh.antennapod.R.id.cardView);
cardView.requestFocus();
break;
}
// HorizontalFeedListAdapter_10_ViewComponentNotVisibleOperatorMutator
case 10030: {
/**
* Inserted by Kadabra
*/
cardView = itemView.findViewById(de.danoeh.antennapod.R.id.cardView);
cardView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cardView = itemView.findViewById(de.danoeh.antennapod.R.id.cardView);
break;
}
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
