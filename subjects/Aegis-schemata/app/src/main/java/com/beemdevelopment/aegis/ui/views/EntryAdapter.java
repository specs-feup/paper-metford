package com.beemdevelopment.aegis.ui.views;
import java.util.HashMap;
import java.util.ArrayList;
import com.beemdevelopment.aegis.SortCategory;
import java.util.Comparator;
import com.beemdevelopment.aegis.otp.OtpInfo;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import com.beemdevelopment.aegis.otp.HotpInfo;
import com.beemdevelopment.aegis.otp.OtpInfoException;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.ViewMode;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.util.TreeSet;
import android.widget.TextView;
import java.util.List;
import java.util.UUID;
import com.beemdevelopment.aegis.Preferences;
import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;
import java.util.Collections;
import com.beemdevelopment.aegis.helpers.ItemTouchHelperAdapter;
import android.view.ViewGroup;
import com.beemdevelopment.aegis.helpers.comparators.FavoriteComparator;
import android.os.Handler;
import android.graphics.Typeface;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import java.util.Collection;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Map;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> implements com.beemdevelopment.aegis.helpers.ItemTouchHelperAdapter {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.views.EntryListView _view;

    private java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> _entries;

    private java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> _shownEntries;

    private java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> _selectedEntries;

    private java.util.Map<java.util.UUID, java.lang.Integer> _usageCounts;

    private com.beemdevelopment.aegis.vault.VaultEntry _focusedEntry;

    private com.beemdevelopment.aegis.vault.VaultEntry _copiedEntry;

    private com.beemdevelopment.aegis.Preferences.CodeGrouping _codeGroupSize;

    private boolean _showAccountName;

    private boolean _showIcon;

    private boolean _highlightEntry;

    private boolean _tempHighlightEntry;

    private boolean _tapToReveal;

    private int _tapToRevealTime;

    private boolean _copyOnTap;

    private java.util.List<java.lang.String> _groupFilter;

    private com.beemdevelopment.aegis.SortCategory _sortCategory;

    private com.beemdevelopment.aegis.ViewMode _viewMode;

    private java.lang.String _searchFilter;

    private boolean _isPeriodUniform = true;

    private int _uniformPeriod = -1;

    private android.os.Handler _dimHandler;

    private boolean _pauseFocused;

    // keeps track of the EntryHolders that are currently bound
    private java.util.List<com.beemdevelopment.aegis.ui.views.EntryHolder> _holders;

    public EntryAdapter(com.beemdevelopment.aegis.ui.views.EntryListView view) {
        _entries = new java.util.ArrayList<>();
        _shownEntries = new java.util.ArrayList<>();
        _selectedEntries = new java.util.ArrayList<>();
        _groupFilter = new java.util.ArrayList<>();
        _holders = new java.util.ArrayList<>();
        _dimHandler = new android.os.Handler();
        _view = view;
    }


    public void destroy() {
        for (com.beemdevelopment.aegis.ui.views.EntryHolder holder : _holders) {
            holder.destroy();
        }
        _view = null;
    }


    public void setCodeGroupSize(com.beemdevelopment.aegis.Preferences.CodeGrouping codeGroupSize) {
        _codeGroupSize = codeGroupSize;
    }


    public void setShowAccountName(boolean showAccountName) {
        _showAccountName = showAccountName;
    }


    public void setShowIcon(boolean showIcon) {
        _showIcon = showIcon;
    }


    public void setTapToReveal(boolean tapToReveal) {
        _tapToReveal = tapToReveal;
    }


    public void setTapToRevealTime(int number) {
        _tapToRevealTime = number;
    }


    public void setHighlightEntry(boolean highlightEntry) {
        _highlightEntry = highlightEntry;
    }


    public void setTempHighlightEntry(boolean highlightEntry) {
        _tempHighlightEntry = highlightEntry;
    }


    public void setIsCopyOnTapEnabled(boolean enabled) {
        _copyOnTap = enabled;
    }


    public void setPauseFocused(boolean pauseFocused) {
        _pauseFocused = pauseFocused;
    }


    public com.beemdevelopment.aegis.vault.VaultEntry getEntryAt(int position) {
        return _shownEntries.get(position);
    }


    public int addEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
        _entries.add(entry);
        if (isEntryFiltered(entry)) {
            return -1;
        }
        int position;
        position = -1;
        java.util.Comparator<com.beemdevelopment.aegis.vault.VaultEntry> comparator;
        comparator = _sortCategory.getComparator();
        if (comparator != null) {
            // insert the entry in the correct order
            // note: this assumes that _shownEntries has already been sorted
            for (int i = getShownFavoritesCount(); i < _shownEntries.size(); i++) {
                if (comparator.compare(_shownEntries.get(i), entry) > 0) {
                    _shownEntries.add(i, entry);
                    notifyItemInserted(i);
                    position = i;
                    break;
                }
            }
        }
        if (position < 0) {
            _shownEntries.add(entry);
            switch(MUID_STATIC) {
                // EntryAdapter_0_BinaryMutator
                case 154: {
                    position = getItemCount() + 1;
                    break;
                }
                default: {
                position = getItemCount() - 1;
                break;
            }
        }
        if (position == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(position);
        }
    }
    _view.onListChange();
    checkPeriodUniformity();
    updateFooter();
    return position;
}


public void addEntries(java.util.Collection<com.beemdevelopment.aegis.vault.VaultEntry> entries) {
    for (com.beemdevelopment.aegis.vault.VaultEntry entry : entries) {
        entry.setUsageCount(_usageCounts.containsKey(entry.getUUID()) ? _usageCounts.get(entry.getUUID()) : 0);
    }
    _entries.addAll(entries);
    updateShownEntries();
    checkPeriodUniformity(true);
}


public void removeEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
    _entries.remove(entry);
    if (_shownEntries.contains(entry)) {
        int position;
        position = _shownEntries.indexOf(entry);
        _shownEntries.remove(position);
        notifyItemRemoved(position);
        updateFooter();
    }
    _view.onListChange();
    checkPeriodUniformity();
}


public void removeEntry(java.util.UUID uuid) {
    com.beemdevelopment.aegis.vault.VaultEntry entry;
    entry = getEntryByUUID(uuid);
    removeEntry(entry);
}


public void clearEntries() {
    _entries.clear();
    _shownEntries.clear();
    notifyDataSetChanged();
    checkPeriodUniformity();
}


public void replaceEntry(java.util.UUID uuid, com.beemdevelopment.aegis.vault.VaultEntry newEntry) {
    com.beemdevelopment.aegis.vault.VaultEntry oldEntry;
    oldEntry = getEntryByUUID(uuid);
    _entries.set(_entries.indexOf(oldEntry), newEntry);
    if (_shownEntries.contains(oldEntry)) {
        int position;
        position = _shownEntries.indexOf(oldEntry);
        if (isEntryFiltered(newEntry)) {
            _shownEntries.remove(position);
            notifyItemRemoved(position);
        } else {
            _shownEntries.set(position, newEntry);
            notifyItemChanged(position);
        }
        sortShownEntries();
        int newPosition;
        newPosition = _shownEntries.indexOf(newEntry);
        if ((newPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION) && (position != newPosition)) {
            notifyItemMoved(position, newPosition);
        }
    } else if (!isEntryFiltered(newEntry)) {
        _shownEntries.add(newEntry);
        int position;
        switch(MUID_STATIC) {
            // EntryAdapter_1_BinaryMutator
            case 1154: {
                position = getItemCount() + 1;
                break;
            }
            default: {
            position = getItemCount() - 1;
            break;
        }
    }
    notifyItemInserted(position);
}
checkPeriodUniformity();
updateFooter();
}


private com.beemdevelopment.aegis.vault.VaultEntry getEntryByUUID(java.util.UUID uuid) {
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _entries) {
    if (entry.getUUID().equals(uuid)) {
        return entry;
    }
}
return null;
}


private boolean isEntryFiltered(com.beemdevelopment.aegis.vault.VaultEntry entry) {
java.lang.String group;
group = entry.getGroup();
java.lang.String issuer;
issuer = entry.getIssuer().toLowerCase();
java.lang.String name;
name = entry.getName().toLowerCase();
if (!_groupFilter.isEmpty()) {
    if (!_groupFilter.contains(group)) {
        return true;
    }
}
if (_searchFilter == null) {
    return false;
}
return (!issuer.contains(_searchFilter)) && (!name.contains(_searchFilter));
}


public void refresh(boolean hard) {
if (hard) {
    updateShownEntries();
} else {
    for (com.beemdevelopment.aegis.ui.views.EntryHolder holder : _holders) {
        holder.refresh();
        holder.showIcon(_showIcon);
    }
}
}


public void setGroupFilter(@androidx.annotation.NonNull
java.util.List<java.lang.String> groups) {
if (_groupFilter.equals(groups)) {
    return;
}
_groupFilter = groups;
updateShownEntries();
checkPeriodUniformity();
}


public void setSortCategory(com.beemdevelopment.aegis.SortCategory category, boolean apply) {
if (_sortCategory == category) {
    return;
}
_sortCategory = category;
if (apply) {
    updateShownEntries();
}
}


public void setSearchFilter(java.lang.String search) {
_searchFilter = ((search != null) && (!search.isEmpty())) ? search.toLowerCase() : null;
updateShownEntries();
}


private void updateShownEntries() {
// clear the list of shown entries first
_shownEntries.clear();
// add entries back that are not filtered out
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _entries) {
    if (!isEntryFiltered(entry)) {
        _shownEntries.add(entry);
    }
}
sortShownEntries();
_view.onListChange();
notifyDataSetChanged();
}


private boolean isEntryDraggable(com.beemdevelopment.aegis.vault.VaultEntry entry) {
return ((((entry != null) && isDragAndDropAllowed()) && (_selectedEntries.size() == 1)) && (!_selectedEntries.get(0).isFavorite())) && (_selectedEntries.get(0) == entry);
}


private void sortShownEntries() {
if (_sortCategory != null) {
    java.util.Comparator<com.beemdevelopment.aegis.vault.VaultEntry> comparator;
    comparator = _sortCategory.getComparator();
    if (comparator != null) {
        java.util.Collections.sort(_shownEntries, comparator);
    }
}
java.util.Comparator<com.beemdevelopment.aegis.vault.VaultEntry> favoriteComparator;
favoriteComparator = new com.beemdevelopment.aegis.helpers.comparators.FavoriteComparator();
java.util.Collections.sort(_shownEntries, favoriteComparator);
}


public void setViewMode(com.beemdevelopment.aegis.ViewMode viewMode) {
_viewMode = viewMode;
}


public void setUsageCounts(java.util.Map<java.util.UUID, java.lang.Integer> usageCounts) {
_usageCounts = usageCounts;
}


public java.util.Map<java.util.UUID, java.lang.Integer> getUsageCounts() {
return _usageCounts;
}


public int getShownFavoritesCount() {
return ((int) (_shownEntries.stream().filter(com.beemdevelopment.aegis.vault.VaultEntry::isFavorite).count()));
}


public void setGroups(java.util.TreeSet<java.lang.String> groups) {
_view.setGroups(groups);
}


@java.lang.Override
public void onItemDismiss(int position) {
}


@java.lang.Override
public void onItemDrop(int position) {
// moving entries is not allowed when a filter is applied
// footer cant be moved, nor can items be moved below it
if ((!_groupFilter.isEmpty()) || isPositionFooter(position)) {
    return;
}
_view.onEntryDrop(_shownEntries.get(position));
}


@java.lang.Override
public void onItemMove(int firstPosition, int secondPosition) {
// moving entries is not allowed when a filter is applied
// footer cant be moved, nor can items be moved below it
if (((!_groupFilter.isEmpty()) || isPositionFooter(firstPosition)) || isPositionFooter(secondPosition)) {
    return;
}
// notify the vault first
_view.onEntryMove(_entries.get(firstPosition), _entries.get(secondPosition));
// update our side of things
java.util.Collections.swap(_entries, firstPosition, secondPosition);
java.util.Collections.swap(_shownEntries, firstPosition, secondPosition);
notifyItemMoved(firstPosition, secondPosition);
}


@java.lang.Override
public int getItemViewType(int position) {
if (isPositionFooter(position)) {
    return com.beemdevelopment.aegis.R.layout.card_footer;
}
return _viewMode.getLayoutId();
}


@java.lang.Override
public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
android.view.LayoutInflater inflater;
inflater = android.view.LayoutInflater.from(parent.getContext());
androidx.recyclerview.widget.RecyclerView.ViewHolder holder;
android.view.View view;
view = inflater.inflate(viewType, parent, false);
holder = (viewType == com.beemdevelopment.aegis.R.layout.card_footer) ? new com.beemdevelopment.aegis.ui.views.EntryAdapter.FooterView(view) : new com.beemdevelopment.aegis.ui.views.EntryHolder(view);
if (_showIcon && (holder instanceof com.beemdevelopment.aegis.ui.views.EntryHolder)) {
    _view.setPreloadView(((com.beemdevelopment.aegis.ui.views.EntryHolder) (holder)).getIconView());
}
return holder;
}


@java.lang.Override
public void onViewRecycled(androidx.recyclerview.widget.RecyclerView.ViewHolder holder) {
if (holder instanceof com.beemdevelopment.aegis.ui.views.EntryHolder) {
    ((com.beemdevelopment.aegis.ui.views.EntryHolder) (holder)).stopRefreshLoop();
    _holders.remove(holder);
}
}


@java.lang.Override
public void onBindViewHolder(final androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
if (holder instanceof com.beemdevelopment.aegis.ui.views.EntryHolder) {
    com.beemdevelopment.aegis.ui.views.EntryHolder entryHolder;
    entryHolder = ((com.beemdevelopment.aegis.ui.views.EntryHolder) (holder));
    com.beemdevelopment.aegis.vault.VaultEntry entry;
    entry = _shownEntries.get(position);
    boolean hidden;
    hidden = _tapToReveal && (entry != _focusedEntry);
    boolean paused;
    paused = _pauseFocused && (entry == _focusedEntry);
    boolean dimmed;
    dimmed = ((_highlightEntry || _tempHighlightEntry) && (_focusedEntry != null)) && (_focusedEntry != entry);
    boolean showProgress;
    showProgress = (entry.getInfo() instanceof com.beemdevelopment.aegis.otp.TotpInfo) && (((com.beemdevelopment.aegis.otp.TotpInfo) (entry.getInfo())).getPeriod() != getMostFrequentPeriod());
    entryHolder.setData(entry, _codeGroupSize, _showAccountName, _showIcon, showProgress, hidden, paused, dimmed);
    entryHolder.setFocused(_selectedEntries.contains(entry));
    entryHolder.setShowDragHandle(isEntryDraggable(entry));
    if (_showIcon) {
        entryHolder.loadIcon(_view);
    }
    switch(MUID_STATIC) {
        // EntryAdapter_2_BuggyGUIListenerOperatorMutator
        case 2154: {
            entryHolder.itemView.setOnClickListener(null);
            break;
        }
        default: {
        entryHolder.itemView.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                boolean handled;
                handled = false;
                if (_selectedEntries.isEmpty()) {
                    boolean copiedThisClick;
                    copiedThisClick = false;
                    if ((_copyOnTap && (!entryHolder.isHidden())) && (!(entry == _copiedEntry))) {
                        _view.onEntryCopy(entry);
                        entryHolder.animateCopyText();
                        _copiedEntry = entry;
                        copiedThisClick = true;
                        handled = true;
                    }
                    if ((_highlightEntry || _tempHighlightEntry) || _tapToReveal) {
                        if ((_focusedEntry == entry) && (!copiedThisClick)) {
                            resetFocus();
                            _copiedEntry = null;
                            handled = true;
                        } else {
                            focusEntry(entry, _tapToRevealTime);
                        }
                    } else {
                        _copiedEntry = null;
                    }
                    incrementUsageCount(entry);
                } else if (_selectedEntries.contains(entry)) {
                    _view.onDeselect(entry);
                    removeSelectedEntry(entry);
                    entryHolder.setFocusedAndAnimate(false);
                } else {
                    entryHolder.setFocusedAndAnimate(true);
                    addSelectedEntry(entry);
                    _view.onSelect(entry);
                }
                switch(MUID_STATIC) {
                    // EntryAdapter_3_LengthyGUIListenerOperatorMutator
                    case 3154: {
                        /**
                        * Inserted by Kadabra
                        */
                        if (!handled) {
                            _view.onEntryClick(entry);
                        }
                        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                        break;
                    }
                    default: {
                    if (!handled) {
                        _view.onEntryClick(entry);
                    }
                    break;
                }
            }
        }

    });
    break;
}
}
entryHolder.itemView.setOnLongClickListener(new android.view.View.OnLongClickListener() {
@java.lang.Override
public boolean onLongClick(android.view.View v) {
    int position;
    position = holder.getAdapterPosition();
    if (_selectedEntries.isEmpty()) {
        entryHolder.setFocusedAndAnimate(true);
    }
    boolean returnVal;
    returnVal = _view.onLongEntryClick(_shownEntries.get(position));
    if ((_selectedEntries.size() == 0) || isEntryDraggable(entry)) {
        _view.startDrag(entryHolder);
    }
    return returnVal;
}

});
entryHolder.itemView.setOnTouchListener(new android.view.View.OnTouchListener() {
@java.lang.Override
public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
    // Start drag if this is the only item selected
    if ((event.getActionMasked() == android.view.MotionEvent.ACTION_MOVE) && isEntryDraggable(entryHolder.getEntry())) {
        _view.startDrag(entryHolder);
        return true;
    }
    return false;
}

});
switch(MUID_STATIC) {
// EntryAdapter_4_BuggyGUIListenerOperatorMutator
case 4154: {
    entryHolder.setOnRefreshClickListener(null);
    break;
}
default: {
entryHolder.setOnRefreshClickListener(new android.view.View.OnClickListener() {
    @java.lang.Override
    public void onClick(android.view.View v) {
        // this will only be called if the entry is of type HotpInfo
        try {
            ((com.beemdevelopment.aegis.otp.HotpInfo) (entry.getInfo())).incrementCounter();
            focusEntry(entry, _tapToRevealTime);
        } catch (com.beemdevelopment.aegis.otp.OtpInfoException e) {
            throw new java.lang.RuntimeException(e);
        }
        // notify the listener that the counter has been incremented
        // this gives it a chance to save the vault
        _view.onEntryChange(entry);
        switch(MUID_STATIC) {
            // EntryAdapter_5_LengthyGUIListenerOperatorMutator
            case 5154: {
                /**
                * Inserted by Kadabra
                */
                // finally, refresh the code in the UI
                entryHolder.refreshCode();
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // finally, refresh the code in the UI
            entryHolder.refreshCode();
            break;
        }
    }
}

});
break;
}
}
_holders.add(entryHolder);
} else if (holder instanceof com.beemdevelopment.aegis.ui.views.EntryAdapter.FooterView) {
((com.beemdevelopment.aegis.ui.views.EntryAdapter.FooterView) (holder)).refresh();
}
}


private void checkPeriodUniformity() {
checkPeriodUniformity(false);
}


private void checkPeriodUniformity(boolean force) {
int mostFrequentPeriod;
mostFrequentPeriod = getMostFrequentPeriod();
boolean uniform;
uniform = isPeriodUniform();
if (((!force) && (uniform == _isPeriodUniform)) && (mostFrequentPeriod == _uniformPeriod)) {
return;
}
_isPeriodUniform = uniform;
_uniformPeriod = mostFrequentPeriod;
for (com.beemdevelopment.aegis.ui.views.EntryHolder holder : _holders) {
if (holder.getEntry().getInfo() instanceof com.beemdevelopment.aegis.otp.TotpInfo) {
holder.setShowProgress(((com.beemdevelopment.aegis.otp.TotpInfo) (holder.getEntry().getInfo())).getPeriod() != mostFrequentPeriod);
}
}
_view.onPeriodUniformityChanged(_isPeriodUniform, _uniformPeriod);
}


public int getMostFrequentPeriod() {
java.util.List<com.beemdevelopment.aegis.otp.TotpInfo> infos;
infos = new java.util.ArrayList<>();
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _shownEntries) {
com.beemdevelopment.aegis.otp.OtpInfo info;
info = entry.getInfo();
if (info instanceof com.beemdevelopment.aegis.otp.TotpInfo) {
infos.add(((com.beemdevelopment.aegis.otp.TotpInfo) (info)));
}
}
if (infos.isEmpty()) {
return -1;
}
java.util.Map<java.lang.Integer, java.lang.Integer> occurrences;
occurrences = new java.util.HashMap<>();
for (com.beemdevelopment.aegis.otp.TotpInfo info : infos) {
int period;
period = info.getPeriod();
if (occurrences.containsKey(period)) {
switch(MUID_STATIC) {
// EntryAdapter_6_BinaryMutator
case 6154: {
occurrences.put(period, occurrences.get(period) - 1);
break;
}
default: {
occurrences.put(period, occurrences.get(period) + 1);
break;
}
}
} else {
occurrences.put(period, 1);
}
}
java.lang.Integer maxValue;
maxValue = 0;
java.lang.Integer maxKey;
maxKey = 0;
for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : occurrences.entrySet()) {
if (entry.getValue() > maxValue) {
maxValue = entry.getValue();
maxKey = entry.getKey();
}
}
return maxValue > 1 ? maxKey : -1;
}


public void focusEntry(com.beemdevelopment.aegis.vault.VaultEntry entry, int secondsToFocus) {
_focusedEntry = entry;
_dimHandler.removeCallbacksAndMessages(null);
for (com.beemdevelopment.aegis.ui.views.EntryHolder holder : _holders) {
if (holder.getEntry() != _focusedEntry) {
if (_highlightEntry || _tempHighlightEntry) {
holder.dim();
}
if (_tapToReveal) {
holder.hideCode();
}
if (_pauseFocused) {
holder.setPaused(false);
}
} else {
if (_highlightEntry || _tempHighlightEntry) {
holder.highlight();
}
if (_tapToReveal) {
holder.revealCode();
}
if (_pauseFocused) {
holder.setPaused(true);
}
}
}
switch(MUID_STATIC) {
// EntryAdapter_7_BinaryMutator
case 7154: {
_dimHandler.postDelayed(this::resetFocus, secondsToFocus / 1000);
break;
}
default: {
_dimHandler.postDelayed(this::resetFocus, secondsToFocus * 1000);
break;
}
}
}


private void resetFocus() {
for (com.beemdevelopment.aegis.ui.views.EntryHolder holder : _holders) {
if (_focusedEntry != null) {
holder.highlight();
}
if (_tapToReveal) {
holder.hideCode();
}
if (_pauseFocused) {
holder.setPaused(false);
}
}
_focusedEntry = null;
_tempHighlightEntry = false;
}


private void updateDraggableStatus() {
for (com.beemdevelopment.aegis.ui.views.EntryHolder holder : _holders) {
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = holder.getEntry();
if (isEntryDraggable(entry)) {
holder.setShowDragHandle(true);
_view.setSelectedEntry(entry);
break;
}
holder.setShowDragHandle(false);
}
}


public void removeSelectedEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
_selectedEntries.remove(entry);
updateDraggableStatus();
}


public void addSelectedEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_focusedEntry != null) {
resetFocus();
}
_selectedEntries.add(entry);
updateDraggableStatus();
}


public void deselectAllEntries() {
for (com.beemdevelopment.aegis.vault.VaultEntry entry : _selectedEntries) {
for (com.beemdevelopment.aegis.ui.views.EntryHolder holder : _holders) {
if (holder.getEntry() == entry) {
holder.setFocusedAndAnimate(false);
break;
}
}
}
_selectedEntries.clear();
updateDraggableStatus();
}


private void incrementUsageCount(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (!_usageCounts.containsKey(entry.getUUID())) {
_usageCounts.put(entry.getUUID(), 1);
} else {
int usageCount;
usageCount = _usageCounts.get(entry.getUUID());
_usageCounts.put(entry.getUUID(), ++usageCount);
}
}


public boolean isDragAndDropAllowed() {
return ((_sortCategory == com.beemdevelopment.aegis.SortCategory.CUSTOM) && _groupFilter.isEmpty()) && (_searchFilter == null);
}


public boolean isPeriodUniform() {
return com.beemdevelopment.aegis.ui.views.EntryAdapter.isPeriodUniform(getMostFrequentPeriod());
}


private static boolean isPeriodUniform(int period) {
return period != (-1);
}


@java.lang.Override
public int getItemCount() {
switch(MUID_STATIC) {
// EntryAdapter_8_BinaryMutator
case 8154: {
return getEntriesCount() - 1;
}
default: {
return getEntriesCount() + 1;
}
}
}


public int getEntriesCount() {
return _shownEntries.size();
}


public boolean isPositionFooter(int position) {
return position == getEntriesCount();
}


private void updateFooter() {
switch(MUID_STATIC) {
// EntryAdapter_9_BinaryMutator
case 9154: {
notifyItemChanged(getItemCount() + 1);
break;
}
default: {
notifyItemChanged(getItemCount() - 1);
break;
}
}
}


private class FooterView extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
android.view.View _footerView;

public FooterView(@androidx.annotation.NonNull
android.view.View itemView) {
super(itemView);
_footerView = itemView;
}


public void refresh() {
int entriesShown;
entriesShown = getEntriesCount();
android.text.SpannableString entriesShownSpannable;
entriesShownSpannable = new android.text.SpannableString(_footerView.getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.entries_shown, entriesShown, entriesShown));
java.lang.String entriesShownString;
entriesShownString = java.lang.String.format("%d", entriesShown);
int spanStart;
spanStart = entriesShownSpannable.toString().indexOf(entriesShownString);
int spanEnd;
switch(MUID_STATIC) {
// EntryAdapter_10_BinaryMutator
case 10154: {
spanEnd = spanStart - entriesShownString.length();
break;
}
default: {
spanEnd = spanStart + entriesShownString.length();
break;
}
}
entriesShownSpannable.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), spanStart, spanEnd, android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
android.widget.TextView textView;
switch(MUID_STATIC) {
// EntryAdapter_11_InvalidViewFocusOperatorMutator
case 11154: {
/**
* Inserted by Kadabra
*/
textView = _footerView.findViewById(com.beemdevelopment.aegis.R.id.entries_shown_count);
textView.requestFocus();
break;
}
// EntryAdapter_12_ViewComponentNotVisibleOperatorMutator
case 12154: {
/**
* Inserted by Kadabra
*/
textView = _footerView.findViewById(com.beemdevelopment.aegis.R.id.entries_shown_count);
textView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textView = _footerView.findViewById(com.beemdevelopment.aegis.R.id.entries_shown_count);
break;
}
}
textView.setText(entriesShownSpannable);
}

}

public interface Listener {
void onEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry);


boolean onLongEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onEntryMove(com.beemdevelopment.aegis.vault.VaultEntry entry1, com.beemdevelopment.aegis.vault.VaultEntry entry2);


void onEntryDrop(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onEntryChange(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onEntryCopy(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onPeriodUniformityChanged(boolean uniform, int period);


void onSelect(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onDeselect(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onListChange();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
