package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import de.danoeh.antennapod.fragment.swipeactions.SwipeActions;
import android.util.Log;
import android.view.MenuInflater;
import android.annotation.SuppressLint;
import de.danoeh.antennapod.activity.MainActivity;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * List adapter for the queue.
 */
public class QueueRecyclerAdapter extends de.danoeh.antennapod.adapter.EpisodeItemListAdapter {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "QueueRecyclerAdapter";

    private final de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions;

    private boolean dragDropEnabled;

    public QueueRecyclerAdapter(de.danoeh.antennapod.activity.MainActivity mainActivity, de.danoeh.antennapod.fragment.swipeactions.SwipeActions swipeActions) {
        super(mainActivity);
        this.swipeActions = swipeActions;
        dragDropEnabled = !(de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueKeepSorted() || de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueLocked());
    }


    public void updateDragDropEnabled() {
        dragDropEnabled = !(de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueKeepSorted() || de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueLocked());
        notifyDataSetChanged();
    }


    @java.lang.Override
    @android.annotation.SuppressLint("ClickableViewAccessibility")
    protected void afterBindViewHolder(de.danoeh.antennapod.view.viewholder.EpisodeItemViewHolder holder, int pos) {
        if (!dragDropEnabled) {
            holder.dragHandle.setVisibility(android.view.View.GONE);
            holder.dragHandle.setOnTouchListener(null);
            holder.coverHolder.setOnTouchListener(null);
        } else {
            holder.dragHandle.setVisibility(android.view.View.VISIBLE);
            holder.dragHandle.setOnTouchListener((android.view.View v1,android.view.MotionEvent event) -> {
                if (event.getActionMasked() == android.view.MotionEvent.ACTION_DOWN) {
                    android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "startDrag()");
                    swipeActions.startDrag(holder);
                }
                return false;
            });
            holder.coverHolder.setOnTouchListener((android.view.View v1,android.view.MotionEvent event) -> {
                if (event.getActionMasked() == android.view.MotionEvent.ACTION_DOWN) {
                    boolean isLtr;
                    isLtr = holder.itemView.getLayoutDirection() == android.view.View.LAYOUT_DIRECTION_LTR;
                    float factor;
                    factor = (isLtr) ? 1 : -1;
                    switch(MUID_STATIC) {
                        // QueueRecyclerAdapter_0_BinaryMutator
                        case 37: {
                            if ((factor / event.getX()) < ((factor * 0.5) * v1.getWidth())) {
                                android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "startDrag()");
                                swipeActions.startDrag(holder);
                            } else {
                                android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "Ignoring drag in right half of the image");
                            }
                            break;
                        }
                        default: {
                        switch(MUID_STATIC) {
                            // QueueRecyclerAdapter_1_BinaryMutator
                            case 1037: {
                                if ((factor * event.getX()) < ((factor * 0.5) / v1.getWidth())) {
                                    android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "startDrag()");
                                    swipeActions.startDrag(holder);
                                } else {
                                    android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "Ignoring drag in right half of the image");
                                }
                                break;
                            }
                            default: {
                            switch(MUID_STATIC) {
                                // QueueRecyclerAdapter_2_BinaryMutator
                                case 2037: {
                                    if ((factor * event.getX()) < ((factor / 0.5) * v1.getWidth())) {
                                        android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "startDrag()");
                                        swipeActions.startDrag(holder);
                                    } else {
                                        android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "Ignoring drag in right half of the image");
                                    }
                                    break;
                                }
                                default: {
                                if ((factor * event.getX()) < ((factor * 0.5) * v1.getWidth())) {
                                    android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "startDrag()");
                                    swipeActions.startDrag(holder);
                                } else {
                                    android.util.Log.d(de.danoeh.antennapod.adapter.QueueRecyclerAdapter.TAG, "Ignoring drag in right half of the image");
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
    }
    return false;
});
}
if (inActionMode()) {
holder.dragHandle.setOnTouchListener(null);
holder.coverHolder.setOnTouchListener(null);
}
holder.isInQueue.setVisibility(android.view.View.GONE);
}


@java.lang.Override
public void onCreateContextMenu(final android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
android.view.MenuInflater inflater;
inflater = getActivity().getMenuInflater();
inflater.inflate(de.danoeh.antennapod.R.menu.queue_context, menu);
super.onCreateContextMenu(menu, v, menuInfo);
if (!inActionMode()) {
menu.findItem(de.danoeh.antennapod.R.id.multi_select).setVisible(true);
final boolean keepSorted;
keepSorted = de.danoeh.antennapod.storage.preferences.UserPreferences.isQueueKeepSorted();
if ((getItem(0).getId() == getLongPressedItem().getId()) || keepSorted) {
    menu.findItem(de.danoeh.antennapod.R.id.move_to_top_item).setVisible(false);
}
switch(MUID_STATIC) {
    // QueueRecyclerAdapter_3_BinaryMutator
    case 3037: {
        if ((getItem(getItemCount() + 1).getId() == getLongPressedItem().getId()) || keepSorted) {
            menu.findItem(de.danoeh.antennapod.R.id.move_to_bottom_item).setVisible(false);
        }
        break;
    }
    default: {
    if ((getItem(getItemCount() - 1).getId() == getLongPressedItem().getId()) || keepSorted) {
        menu.findItem(de.danoeh.antennapod.R.id.move_to_bottom_item).setVisible(false);
    }
    break;
}
}
} else {
menu.findItem(de.danoeh.antennapod.R.id.move_to_top_item).setVisible(false);
menu.findItem(de.danoeh.antennapod.R.id.move_to_bottom_item).setVisible(false);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
