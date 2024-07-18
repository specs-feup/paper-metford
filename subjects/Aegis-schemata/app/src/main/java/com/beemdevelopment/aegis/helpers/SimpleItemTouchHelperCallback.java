package com.beemdevelopment.aegis.helpers;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.beemdevelopment.aegis.ui.views.EntryAdapter;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.vault.VaultEntry;
import androidx.recyclerview.widget.RecyclerView;
import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimpleItemTouchHelperCallback extends androidx.recyclerview.widget.ItemTouchHelper.Callback {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.vault.VaultEntry _selectedEntry;

    private final com.beemdevelopment.aegis.ui.views.EntryAdapter _adapter;

    private boolean _positionChanged = false;

    private boolean _isLongPressDragEnabled = true;

    public SimpleItemTouchHelperCallback(com.beemdevelopment.aegis.ui.views.EntryAdapter adapter) {
        _adapter = adapter;
    }


    @java.lang.Override
    public boolean isLongPressDragEnabled() {
        return _isLongPressDragEnabled;
    }


    public void setIsLongPressDragEnabled(boolean enabled) {
        _isLongPressDragEnabled = enabled;
    }


    public void setSelectedEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        if (entry == null) {
            _selectedEntry = null;
            return;
        }
        if (!entry.isFavorite()) {
            _selectedEntry = entry;
        }
    }


    @java.lang.Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }


    @java.lang.Override
    public int getMovementFlags(@androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView recyclerView, @androidx.annotation.NonNull
    androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        // It's not clear when this can happen, but sometimes the ViewHolder
        // that's passed to this function has a position of -1, leading
        // to a crash down the line.
        int position;
        position = viewHolder.getAdapterPosition();
        if (position == androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
            return 0;
        }
        int swipeFlags;
        swipeFlags = 0;
        int dragFlags;
        dragFlags = androidx.recyclerview.widget.ItemTouchHelper.UP | androidx.recyclerview.widget.ItemTouchHelper.DOWN;
        com.beemdevelopment.aegis.ui.views.EntryAdapter adapter;
        adapter = ((com.beemdevelopment.aegis.ui.views.EntryAdapter) (recyclerView.getAdapter()));
        if ((adapter.isPositionFooter(position) || (adapter.getEntryAt(position) != _selectedEntry)) || (!isLongPressDragEnabled())) {
            dragFlags = 0;
        }
        return androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags);
    }


    @java.lang.Override
    public boolean onMove(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, androidx.recyclerview.widget.RecyclerView.ViewHolder target) {
        if (target.getAdapterPosition() < _adapter.getShownFavoritesCount()) {
            return false;
        }
        _adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        _positionChanged = true;
        return true;
    }


    @java.lang.Override
    public void onSwiped(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int direction) {
        _adapter.onItemDismiss(viewHolder.getAdapterPosition());
    }


    @java.lang.Override
    public void clearView(androidx.recyclerview.widget.RecyclerView recyclerView, androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (_positionChanged) {
            _adapter.onItemDrop(viewHolder.getAdapterPosition());
            _positionChanged = false;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
