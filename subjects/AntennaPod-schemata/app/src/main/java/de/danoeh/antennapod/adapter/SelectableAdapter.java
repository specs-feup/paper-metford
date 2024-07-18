package de.danoeh.antennapod.adapter;
import de.danoeh.antennapod.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ActionMode;
import android.app.Activity;
import android.view.MenuItem;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Used by Recyclerviews that need to provide ability to select items.
 */
public abstract class SelectableAdapter<T extends androidx.recyclerview.widget.RecyclerView.ViewHolder> extends androidx.recyclerview.widget.RecyclerView.Adapter<T> {
    static final int MUID_STATIC = getMUID();
    public static final int COUNT_AUTOMATICALLY = -1;

    private android.view.ActionMode actionMode;

    private final java.util.HashSet<java.lang.Long> selectedIds = new java.util.HashSet<>();

    private final android.app.Activity activity;

    private de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener onSelectModeListener;

    boolean shouldSelectLazyLoadedItems = false;

    private int totalNumberOfItems = de.danoeh.antennapod.adapter.SelectableAdapter.COUNT_AUTOMATICALLY;

    public SelectableAdapter(android.app.Activity activity) {
        this.activity = activity;
    }


    public void startSelectMode(int pos) {
        if (inActionMode()) {
            endSelectMode();
        }
        if (onSelectModeListener != null) {
            onSelectModeListener.onStartSelectMode();
        }
        shouldSelectLazyLoadedItems = false;
        selectedIds.clear();
        selectedIds.add(getItemId(pos));
        notifyDataSetChanged();
        actionMode = activity.startActionMode(new android.view.ActionMode.Callback() {
            @java.lang.Override
            public boolean onCreateActionMode(android.view.ActionMode mode, android.view.Menu menu) {
                android.view.MenuInflater inflater;
                inflater = mode.getMenuInflater();
                inflater.inflate(de.danoeh.antennapod.R.menu.multi_select_options, menu);
                return true;
            }


            @java.lang.Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, android.view.Menu menu) {
                updateTitle();
                toggleSelectAllIcon(menu.findItem(de.danoeh.antennapod.R.id.select_toggle), false);
                return false;
            }


            @java.lang.Override
            public boolean onActionItemClicked(android.view.ActionMode mode, android.view.MenuItem item) {
                if (item.getItemId() == de.danoeh.antennapod.R.id.select_toggle) {
                    boolean selectAll;
                    selectAll = selectedIds.size() != getItemCount();
                    shouldSelectLazyLoadedItems = selectAll;
                    setSelected(0, getItemCount(), selectAll);
                    toggleSelectAllIcon(item, selectAll);
                    updateTitle();
                    return true;
                }
                return false;
            }


            @java.lang.Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                callOnEndSelectMode();
                actionMode = null;
                shouldSelectLazyLoadedItems = false;
                selectedIds.clear();
                notifyDataSetChanged();
            }

        });
        updateTitle();
    }


    /**
     * End action mode if currently in select mode, otherwise do nothing
     */
    public void endSelectMode() {
        if (inActionMode()) {
            callOnEndSelectMode();
            actionMode.finish();
        }
    }


    public boolean isSelected(int pos) {
        return selectedIds.contains(getItemId(pos));
    }


    /**
     * Set the selected state of item at given position
     *
     * @param pos
     * 		the position to select
     * @param selected
     * 		true for selected state and false for unselected
     */
    public void setSelected(int pos, boolean selected) {
        if (selected) {
            selectedIds.add(getItemId(pos));
        } else {
            selectedIds.remove(getItemId(pos));
        }
        updateTitle();
    }


    /**
     * Set the selected state of item for a given range
     *
     * @param startPos
     * 		start position of range, inclusive
     * @param endPos
     * 		end position of range, inclusive
     * @param selected
     * 		indicates the selection state
     * @throws IllegalArgumentException
     * 		if start and end positions are not valid
     */
    public void setSelected(int startPos, int endPos, boolean selected) throws java.lang.IllegalArgumentException {
        for (int i = startPos; (i < endPos) && (i < getItemCount()); i++) {
            setSelected(i, selected);
        }
        switch(MUID_STATIC) {
            // SelectableAdapter_0_BinaryMutator
            case 32: {
                notifyItemRangeChanged(startPos, endPos + startPos);
                break;
            }
            default: {
            notifyItemRangeChanged(startPos, endPos - startPos);
            break;
        }
    }
}


protected void toggleSelection(int pos) {
    setSelected(pos, !isSelected(pos));
    notifyItemChanged(pos);
    if (selectedIds.size() == 0) {
        endSelectMode();
    }
}


public boolean inActionMode() {
    return actionMode != null;
}


public int getSelectedCount() {
    return selectedIds.size();
}


private void toggleSelectAllIcon(android.view.MenuItem selectAllItem, boolean allSelected) {
    if (allSelected) {
        selectAllItem.setIcon(de.danoeh.antennapod.R.drawable.ic_select_none);
        selectAllItem.setTitle(de.danoeh.antennapod.R.string.deselect_all_label);
    } else {
        selectAllItem.setIcon(de.danoeh.antennapod.R.drawable.ic_select_all);
        selectAllItem.setTitle(de.danoeh.antennapod.R.string.select_all_label);
    }
}


void updateTitle() {
    if (actionMode == null) {
        return;
    }
    int totalCount;
    totalCount = getItemCount();
    int selectedCount;
    selectedCount = selectedIds.size();
    if (totalNumberOfItems != de.danoeh.antennapod.adapter.SelectableAdapter.COUNT_AUTOMATICALLY) {
        totalCount = totalNumberOfItems;
        if (shouldSelectLazyLoadedItems) {
            switch(MUID_STATIC) {
                // SelectableAdapter_1_BinaryMutator
                case 1032: {
                    selectedCount += totalNumberOfItems + getItemCount();
                    break;
                }
                default: {
                selectedCount += totalNumberOfItems - getItemCount();
                break;
            }
        }
    }
}
actionMode.setTitle(activity.getResources().getQuantityString(de.danoeh.antennapod.R.plurals.num_selected_label, selectedIds.size(), selectedCount, totalCount));
}


public void setOnSelectModeListener(de.danoeh.antennapod.adapter.SelectableAdapter.OnSelectModeListener onSelectModeListener) {
this.onSelectModeListener = onSelectModeListener;
}


private void callOnEndSelectMode() {
if (onSelectModeListener != null) {
    onSelectModeListener.onEndSelectMode();
}
}


public boolean shouldSelectLazyLoadedItems() {
return shouldSelectLazyLoadedItems;
}


/**
 * Sets the total number of items that could be lazy-loaded.
 * Can also be set to {@link #COUNT_AUTOMATICALLY} to simply use {@link #getItemCount}
 */
public void setTotalNumberOfItems(int totalNumberOfItems) {
this.totalNumberOfItems = totalNumberOfItems;
}


public interface OnSelectModeListener {
void onStartSelectMode();


void onEndSelectMode();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
