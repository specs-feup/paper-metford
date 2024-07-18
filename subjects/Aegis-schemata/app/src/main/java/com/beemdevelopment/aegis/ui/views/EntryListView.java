package com.beemdevelopment.aegis.ui.views;
import com.bumptech.glide.ListPreloader;
import com.beemdevelopment.aegis.SortCategory;
import androidx.fragment.app.Fragment;
import com.beemdevelopment.aegis.helpers.ThemeHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.Button;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.ViewMode;
import com.beemdevelopment.aegis.vault.VaultEntry;
import java.util.TreeSet;
import java.util.List;
import java.util.UUID;
import com.bumptech.glide.Glide;
import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;
import java.util.Collections;
import java.util.stream.Collectors;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.beemdevelopment.aegis.helpers.UiRefresher;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.LayoutInflater;
import com.beemdevelopment.aegis.helpers.MetricsHelper;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import java.util.Collection;
import java.util.Map;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.helpers.SimpleItemTouchHelperCallback;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.chip.Chip;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.otp.TotpInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.beemdevelopment.aegis.Preferences;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DividerItemDecoration;
import android.view.MotionEvent;
import android.view.View;
import android.annotation.SuppressLint;
import com.google.android.material.chip.ChipGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.beemdevelopment.aegis.ui.glide.IconLoader;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntryListView extends androidx.fragment.app.Fragment implements com.beemdevelopment.aegis.ui.views.EntryAdapter.Listener {
    static final int MUID_STATIC = getMUID();
    private com.beemdevelopment.aegis.ui.views.EntryAdapter _adapter;

    private com.beemdevelopment.aegis.ui.views.EntryListView.Listener _listener;

    private com.beemdevelopment.aegis.helpers.SimpleItemTouchHelperCallback _touchCallback;

    private androidx.recyclerview.widget.ItemTouchHelper _touchHelper;

    private androidx.recyclerview.widget.RecyclerView _recyclerView;

    private androidx.recyclerview.widget.RecyclerView.ItemDecoration _dividerDecoration;

    private com.bumptech.glide.util.ViewPreloadSizeProvider<com.beemdevelopment.aegis.vault.VaultEntry> _preloadSizeProvider;

    private com.beemdevelopment.aegis.ui.views.TotpProgressBar _progressBar;

    private boolean _showProgress;

    private com.beemdevelopment.aegis.ViewMode _viewMode;

    private java.util.TreeSet<java.lang.String> _groups;

    private android.widget.LinearLayout _emptyStateView;

    private com.google.android.material.chip.Chip _groupChip;

    private java.util.List<java.lang.String> _groupFilter;

    private java.util.List<java.lang.String> _prefGroupFilter;

    private com.beemdevelopment.aegis.helpers.UiRefresher _refresher;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // EntryListView_0_LengthyGUICreationOperatorMutator
            case 153: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                // AFTER SUPER
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            // AFTER SUPER
            break;
        }
    }
    _adapter = new com.beemdevelopment.aegis.ui.views.EntryAdapter(this);
    _showProgress = false;
}


@java.lang.Override
public void onDestroy() {
    _adapter.destroy();
    super.onDestroy();
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    android.view.View view;
    view = inflater.inflate(com.beemdevelopment.aegis.R.layout.fragment_entry_list_view, container, false);
    switch(MUID_STATIC) {
        // EntryListView_1_InvalidViewFocusOperatorMutator
        case 1153: {
            /**
            * Inserted by Kadabra
            */
            _progressBar = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
            _progressBar.requestFocus();
            break;
        }
        // EntryListView_2_ViewComponentNotVisibleOperatorMutator
        case 2153: {
            /**
            * Inserted by Kadabra
            */
            _progressBar = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
            _progressBar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _progressBar = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
        break;
    }
}
switch(MUID_STATIC) {
    // EntryListView_3_InvalidViewFocusOperatorMutator
    case 3153: {
        /**
        * Inserted by Kadabra
        */
        _groupChip = view.findViewById(com.beemdevelopment.aegis.R.id.chip_group);
        _groupChip.requestFocus();
        break;
    }
    // EntryListView_4_ViewComponentNotVisibleOperatorMutator
    case 4153: {
        /**
        * Inserted by Kadabra
        */
        _groupChip = view.findViewById(com.beemdevelopment.aegis.R.id.chip_group);
        _groupChip.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _groupChip = view.findViewById(com.beemdevelopment.aegis.R.id.chip_group);
    break;
}
}
initializeGroupChip();
switch(MUID_STATIC) {
// EntryListView_5_InvalidViewFocusOperatorMutator
case 5153: {
    /**
    * Inserted by Kadabra
    */
    // set up the recycler view
    _recyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.rvKeyProfiles);
    _recyclerView.requestFocus();
    break;
}
// EntryListView_6_ViewComponentNotVisibleOperatorMutator
case 6153: {
    /**
    * Inserted by Kadabra
    */
    // set up the recycler view
    _recyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.rvKeyProfiles);
    _recyclerView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
// set up the recycler view
_recyclerView = view.findViewById(com.beemdevelopment.aegis.R.id.rvKeyProfiles);
break;
}
}
_recyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
@java.lang.Override
public void onScrolled(androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
super.onScrolled(recyclerView, dx, dy);
if (_listener != null) {
    _listener.onScroll(dx, dy);
}
}

});
_recyclerView.setOnTouchListener((android.view.View v,android.view.MotionEvent event) -> {
if (_listener != null) {
_listener.onEntryListTouch();
}
return false;
});
// set up icon preloading
_preloadSizeProvider = new com.bumptech.glide.util.ViewPreloadSizeProvider<>();
com.beemdevelopment.aegis.ui.views.EntryListView.IconPreloadProvider modelProvider;
modelProvider = new com.beemdevelopment.aegis.ui.views.EntryListView.IconPreloadProvider();
com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<com.beemdevelopment.aegis.vault.VaultEntry> preloader;
preloader = new com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<>(com.bumptech.glide.Glide.with(this), modelProvider, _preloadSizeProvider, 10);
_recyclerView.addOnScrollListener(preloader);
androidx.recyclerview.widget.LinearLayoutManager layoutManager;
layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(requireContext());
_recyclerView.setLayoutManager(layoutManager);
_touchCallback = new com.beemdevelopment.aegis.helpers.SimpleItemTouchHelperCallback(_adapter);
_touchHelper = new androidx.recyclerview.widget.ItemTouchHelper(_touchCallback);
_touchHelper.attachToRecyclerView(_recyclerView);
_recyclerView.setAdapter(_adapter);
int resId;
resId = com.beemdevelopment.aegis.R.anim.layout_animation_fall_down;
android.view.animation.LayoutAnimationController animation;
animation = android.view.animation.AnimationUtils.loadLayoutAnimation(requireContext(), resId);
_recyclerView.setLayoutAnimation(animation);
_refresher = new com.beemdevelopment.aegis.helpers.UiRefresher(new com.beemdevelopment.aegis.helpers.UiRefresher.Listener() {
@java.lang.Override
public void onRefresh() {
refresh(false);
}


@java.lang.Override
public long getMillisTillNextRefresh() {
return com.beemdevelopment.aegis.otp.TotpInfo.getMillisTillNextRotation(_adapter.getMostFrequentPeriod());
}

});
switch(MUID_STATIC) {
// EntryListView_7_InvalidViewFocusOperatorMutator
case 7153: {
/**
* Inserted by Kadabra
*/
_emptyStateView = view.findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
_emptyStateView.requestFocus();
break;
}
// EntryListView_8_ViewComponentNotVisibleOperatorMutator
case 8153: {
/**
* Inserted by Kadabra
*/
_emptyStateView = view.findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
_emptyStateView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_emptyStateView = view.findViewById(com.beemdevelopment.aegis.R.id.vEmptyList);
break;
}
}
return view;
}


public void setPreloadView(android.view.View view) {
_preloadSizeProvider.setView(view);
}


@java.lang.Override
public void onDestroyView() {
_refresher.destroy();
super.onDestroyView();
}


public void setGroupFilter(java.util.List<java.lang.String> groups, boolean animate) {
_groupFilter = groups;
_adapter.setGroupFilter(groups);
_touchCallback.setIsLongPressDragEnabled(_adapter.isDragAndDropAllowed());
updateEmptyState();
updateGroupChip();
if (animate) {
runEntriesAnimation();
}
}


public void setIsLongPressDragEnabled(boolean enabled) {
_touchCallback.setIsLongPressDragEnabled(enabled && _adapter.isDragAndDropAllowed());
}


public void setIsCopyOnTapEnabled(boolean enabled) {
_adapter.setIsCopyOnTapEnabled(enabled);
}


public void setActionModeState(boolean enabled, com.beemdevelopment.aegis.vault.VaultEntry entry) {
_touchCallback.setSelectedEntry(entry);
_touchCallback.setIsLongPressDragEnabled(enabled && _adapter.isDragAndDropAllowed());
if (enabled) {
_adapter.addSelectedEntry(entry);
} else {
_adapter.deselectAllEntries();
}
}


public void setSortCategory(com.beemdevelopment.aegis.SortCategory sortCategory, boolean apply) {
_adapter.setSortCategory(sortCategory, apply);
_touchCallback.setIsLongPressDragEnabled(_adapter.isDragAndDropAllowed());
if (apply) {
runEntriesAnimation();
}
}


public void setUsageCounts(java.util.Map<java.util.UUID, java.lang.Integer> usageCounts) {
_adapter.setUsageCounts(usageCounts);
}


public java.util.Map<java.util.UUID, java.lang.Integer> getUsageCounts() {
return _adapter.getUsageCounts();
}


public void setSearchFilter(java.lang.String search) {
_adapter.setSearchFilter(search);
_touchCallback.setIsLongPressDragEnabled(_adapter.isDragAndDropAllowed());
}


public void setSelectedEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
_touchCallback.setSelectedEntry(entry);
}


public void setViewMode(com.beemdevelopment.aegis.ViewMode mode) {
_viewMode = mode;
updateDividerDecoration();
_adapter.setViewMode(_viewMode);
}


public void startDrag(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
_touchHelper.startDrag(viewHolder);
}


public void refresh(boolean hard) {
if (_showProgress) {
_progressBar.restart();
}
_adapter.refresh(hard);
}


public void setListener(com.beemdevelopment.aegis.ui.views.EntryListView.Listener listener) {
_listener = listener;
}


@java.lang.Override
public void onEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_listener != null) {
_listener.onEntryClick(entry);
}
}


public boolean onLongEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_listener != null) {
_listener.onLongEntryClick(entry);
}
return true;
}


@java.lang.Override
public void onEntryMove(com.beemdevelopment.aegis.vault.VaultEntry entry1, com.beemdevelopment.aegis.vault.VaultEntry entry2) {
if (_listener != null) {
_listener.onEntryMove(entry1, entry2);
}
}


@java.lang.Override
public void onEntryDrop(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_listener != null) {
_listener.onEntryDrop(entry);
}
}


@java.lang.Override
public void onEntryChange(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_listener != null) {
_listener.onEntryChange(entry);
}
}


@java.lang.Override
public void onEntryCopy(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_listener != null) {
_listener.onEntryCopy(entry);
}
}


@java.lang.Override
public void onSelect(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_listener != null) {
_listener.onSelect(entry);
}
}


@java.lang.Override
public void onDeselect(com.beemdevelopment.aegis.vault.VaultEntry entry) {
if (_listener != null) {
_listener.onDeselect(entry);
}
}


@java.lang.Override
public void onPeriodUniformityChanged(boolean isUniform, int period) {
setShowProgress(isUniform);
if (_showProgress) {
_progressBar.setVisibility(android.view.View.VISIBLE);
_progressBar.setPeriod(period);
_progressBar.start();
_refresher.start();
} else {
_progressBar.setVisibility(android.view.View.GONE);
_progressBar.stop();
_refresher.stop();
}
}


@java.lang.Override
public void onListChange() {
if (_listener != null) {
_listener.onListChange();
}
}


public void setPrefGroupFilter(java.util.List<java.lang.String> groupFilter) {
_prefGroupFilter = groupFilter;
}


public void setCodeGroupSize(com.beemdevelopment.aegis.Preferences.CodeGrouping codeGrouping) {
_adapter.setCodeGroupSize(codeGrouping);
}


public void setShowAccountName(boolean showAccountName) {
_adapter.setShowAccountName(showAccountName);
}


public void setShowIcon(boolean showIcon) {
_adapter.setShowIcon(showIcon);
}


public void setHighlightEntry(boolean highlightEntry) {
_adapter.setHighlightEntry(highlightEntry);
}


public void setPauseFocused(boolean pauseFocused) {
_adapter.setPauseFocused(pauseFocused);
}


public void setTapToReveal(boolean tapToReveal) {
_adapter.setTapToReveal(tapToReveal);
}


public void setTapToRevealTime(int number) {
_adapter.setTapToRevealTime(number);
}


public void addEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
addEntry(entry, false);
}


@android.annotation.SuppressLint("ClickableViewAccessibility")
public void addEntry(com.beemdevelopment.aegis.vault.VaultEntry entry, boolean focusEntry) {
int position;
position = _adapter.addEntry(entry);
updateEmptyState();
androidx.recyclerview.widget.LinearLayoutManager layoutManager;
layoutManager = ((androidx.recyclerview.widget.LinearLayoutManager) (_recyclerView.getLayoutManager()));
if (focusEntry && (position >= 0)) {
if ((_recyclerView.canScrollVertically(1) && (position > layoutManager.findLastCompletelyVisibleItemPosition())) || (_recyclerView.canScrollVertically(-1) && (position < layoutManager.findFirstCompletelyVisibleItemPosition()))) {
androidx.recyclerview.widget.RecyclerView.OnScrollListener scrollListener;
scrollListener = new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
@java.lang.Override
public void onScrollStateChanged(@androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView recyclerView, int newState) {
    if (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE) {
        _recyclerView.removeOnScrollListener(this);
        _recyclerView.setOnTouchListener(null);
        tempHighlightEntry(entry);
    }
}

};
_recyclerView.addOnScrollListener(scrollListener);
_recyclerView.setOnTouchListener((android.view.View v,android.view.MotionEvent event) -> {
if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
    _recyclerView.removeOnScrollListener(scrollListener);
    _recyclerView.stopScroll();
    _recyclerView.setOnTouchListener(null);
}
return false;
});
_recyclerView.smoothScrollToPosition(position);
} else {
tempHighlightEntry(entry);
}
}
}


public void tempHighlightEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
_adapter.setTempHighlightEntry(true);
final int secondsToFocus;
secondsToFocus = 3;
_adapter.focusEntry(entry, secondsToFocus);
}


public void addEntries(java.util.Collection<com.beemdevelopment.aegis.vault.VaultEntry> entries) {
_adapter.addEntries(entries);
updateEmptyState();
}


public void removeEntry(com.beemdevelopment.aegis.vault.VaultEntry entry) {
_adapter.removeEntry(entry);
updateEmptyState();
}


public void removeEntry(java.util.UUID uuid) {
_adapter.removeEntry(uuid);
updateEmptyState();
}


public void clearEntries() {
_adapter.clearEntries();
}


public void replaceEntry(java.util.UUID uuid, com.beemdevelopment.aegis.vault.VaultEntry newEntry) {
_adapter.replaceEntry(uuid, newEntry);
}


public void runEntriesAnimation() {
final android.view.animation.LayoutAnimationController controller;
controller = android.view.animation.AnimationUtils.loadLayoutAnimation(requireContext(), com.beemdevelopment.aegis.R.anim.layout_animation_fall_down);
_recyclerView.setLayoutAnimation(controller);
_recyclerView.scheduleLayoutAnimation();
}


private void addChipTo(com.google.android.material.chip.ChipGroup chipGroup, java.lang.String group) {
com.google.android.material.chip.Chip chip;
chip = ((com.google.android.material.chip.Chip) (getLayoutInflater().inflate(com.beemdevelopment.aegis.R.layout.chip_material, null, false)));
chip.setText(group == null ? getString(com.beemdevelopment.aegis.R.string.no_group) : group);
chip.setCheckable(true);
chip.setChecked((_groupFilter != null) && _groupFilter.contains(group));
chip.setCheckedIconVisible(true);
chip.setOnCheckedChangeListener((android.widget.CompoundButton group1,boolean checkedId) -> {
java.util.List<java.lang.String> groupFilter;
groupFilter = com.beemdevelopment.aegis.ui.views.EntryListView.getGroupFilter(chipGroup);
setGroupFilter(groupFilter, true);
});
chip.setTag(group == null ? new java.lang.Object() : null);
chipGroup.addView(chip);
}


private void initializeGroupChip() {
android.view.View view;
view = getLayoutInflater().inflate(com.beemdevelopment.aegis.R.layout.dialog_select_groups, null);
com.google.android.material.bottomsheet.BottomSheetDialog dialog;
dialog = new com.google.android.material.bottomsheet.BottomSheetDialog(requireContext());
dialog.setContentView(view);
com.google.android.material.chip.ChipGroup chipGroup;
switch(MUID_STATIC) {
// EntryListView_9_InvalidViewFocusOperatorMutator
case 9153: {
/**
* Inserted by Kadabra
*/
chipGroup = view.findViewById(com.beemdevelopment.aegis.R.id.groupChipGroup);
chipGroup.requestFocus();
break;
}
// EntryListView_10_ViewComponentNotVisibleOperatorMutator
case 10153: {
/**
* Inserted by Kadabra
*/
chipGroup = view.findViewById(com.beemdevelopment.aegis.R.id.groupChipGroup);
chipGroup.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
chipGroup = view.findViewById(com.beemdevelopment.aegis.R.id.groupChipGroup);
break;
}
}
android.widget.Button clearButton;
switch(MUID_STATIC) {
// EntryListView_11_InvalidViewFocusOperatorMutator
case 11153: {
/**
* Inserted by Kadabra
*/
clearButton = view.findViewById(com.beemdevelopment.aegis.R.id.btnClear);
clearButton.requestFocus();
break;
}
// EntryListView_12_ViewComponentNotVisibleOperatorMutator
case 12153: {
/**
* Inserted by Kadabra
*/
clearButton = view.findViewById(com.beemdevelopment.aegis.R.id.btnClear);
clearButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
clearButton = view.findViewById(com.beemdevelopment.aegis.R.id.btnClear);
break;
}
}
android.widget.Button saveButton;
switch(MUID_STATIC) {
// EntryListView_13_InvalidViewFocusOperatorMutator
case 13153: {
/**
* Inserted by Kadabra
*/
saveButton = view.findViewById(com.beemdevelopment.aegis.R.id.btnSave);
saveButton.requestFocus();
break;
}
// EntryListView_14_ViewComponentNotVisibleOperatorMutator
case 14153: {
/**
* Inserted by Kadabra
*/
saveButton = view.findViewById(com.beemdevelopment.aegis.R.id.btnSave);
saveButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
saveButton = view.findViewById(com.beemdevelopment.aegis.R.id.btnSave);
break;
}
}
switch(MUID_STATIC) {
// EntryListView_15_BuggyGUIListenerOperatorMutator
case 15153: {
clearButton.setOnClickListener(null);
break;
}
default: {
clearButton.setOnClickListener((android.view.View v) -> {
chipGroup.clearCheck();
java.util.List<java.lang.String> groupFilter;
groupFilter = java.util.Collections.emptyList();
if (_listener != null) {
_listener.onSaveGroupFilter(groupFilter);
}
setGroupFilter(groupFilter, true);
dialog.dismiss();
});
break;
}
}
switch(MUID_STATIC) {
// EntryListView_16_BuggyGUIListenerOperatorMutator
case 16153: {
saveButton.setOnClickListener(null);
break;
}
default: {
saveButton.setOnClickListener((android.view.View v) -> {
java.util.List<java.lang.String> groupFilter;
groupFilter = com.beemdevelopment.aegis.ui.views.EntryListView.getGroupFilter(chipGroup);
if (_listener != null) {
_listener.onSaveGroupFilter(groupFilter);
}
setGroupFilter(groupFilter, true);
dialog.dismiss();
});
break;
}
}
switch(MUID_STATIC) {
// EntryListView_17_BuggyGUIListenerOperatorMutator
case 17153: {
_groupChip.setOnClickListener(null);
break;
}
default: {
_groupChip.setOnClickListener((android.view.View v) -> {
chipGroup.removeAllViews();
for (java.lang.String group : _groups) {
addChipTo(chipGroup, group);
}
addChipTo(chipGroup, null);
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
});
break;
}
}
}


private static java.util.List<java.lang.String> getGroupFilter(com.google.android.material.chip.ChipGroup chipGroup) {
return chipGroup.getCheckedChipIds().stream().map((java.lang.Integer i) -> {
com.google.android.material.chip.Chip chip;
switch(MUID_STATIC) {
// EntryListView_18_InvalidViewFocusOperatorMutator
case 18153: {
/**
* Inserted by Kadabra
*/
chip = chipGroup.findViewById(i);
chip.requestFocus();
break;
}
// EntryListView_19_ViewComponentNotVisibleOperatorMutator
case 19153: {
/**
* Inserted by Kadabra
*/
chip = chipGroup.findViewById(i);
chip.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
chip = chipGroup.findViewById(i);
break;
}
}
if (chip.getTag() != null) {
return null;
}
return chip.getText().toString();
}).collect(java.util.stream.Collectors.toList());
}


private void updateGroupChip() {
if (_groupFilter.isEmpty()) {
_groupChip.setText(com.beemdevelopment.aegis.R.string.groups);
} else {
_groupChip.setText(java.lang.String.format("%s (%d)", getString(com.beemdevelopment.aegis.R.string.groups), _groupFilter.size()));
}
}


private void setShowProgress(boolean showProgress) {
_showProgress = showProgress;
updateDividerDecoration();
}


public void setGroups(java.util.TreeSet<java.lang.String> groups) {
_groups = groups;
_groupChip.setVisibility(_groups.isEmpty() ? android.view.View.GONE : android.view.View.VISIBLE);
updateDividerDecoration();
if (_prefGroupFilter != null) {
java.util.List<java.lang.String> groupFilter;
groupFilter = cleanGroupFilter(_prefGroupFilter);
_prefGroupFilter = null;
if (!groupFilter.isEmpty()) {
setGroupFilter(groupFilter, false);
}
} else if (_groupFilter != null) {
java.util.List<java.lang.String> groupFilter;
groupFilter = cleanGroupFilter(_groupFilter);
if (!_groupFilter.equals(groupFilter)) {
setGroupFilter(groupFilter, true);
}
}
}


private java.util.List<java.lang.String> cleanGroupFilter(java.util.List<java.lang.String> groupFilter) {
return groupFilter.stream().filter((java.lang.String g) -> (g == null) || _groups.contains(g)).collect(java.util.stream.Collectors.toList());
}


private void updateDividerDecoration() {
if (_dividerDecoration != null) {
_recyclerView.removeItemDecoration(_dividerDecoration);
}
float height;
height = _viewMode.getDividerHeight();
if (_showProgress && (height == 0)) {
_dividerDecoration = new com.beemdevelopment.aegis.ui.views.EntryListView.CompactDividerDecoration();
} else {
_dividerDecoration = new com.beemdevelopment.aegis.ui.views.EntryListView.VerticalSpaceItemDecoration(height);
}
_recyclerView.addItemDecoration(_dividerDecoration);
}


private void updateEmptyState() {
if (_adapter.getEntriesCount() > 0) {
_recyclerView.setVisibility(android.view.View.VISIBLE);
_emptyStateView.setVisibility(android.view.View.GONE);
} else {
_recyclerView.setVisibility(android.view.View.GONE);
_emptyStateView.setVisibility(android.view.View.VISIBLE);
}
}


public interface Listener {
void onEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onEntryMove(com.beemdevelopment.aegis.vault.VaultEntry entry1, com.beemdevelopment.aegis.vault.VaultEntry entry2);


void onEntryDrop(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onEntryChange(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onEntryCopy(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onLongEntryClick(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onScroll(int dx, int dy);


void onSelect(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onDeselect(com.beemdevelopment.aegis.vault.VaultEntry entry);


void onListChange();


void onSaveGroupFilter(java.util.List<java.lang.String> groupFilter);


void onEntryListTouch();

}

private class CompactDividerDecoration extends com.google.android.material.divider.MaterialDividerItemDecoration {
public CompactDividerDecoration() {
super(requireContext(), androidx.recyclerview.widget.DividerItemDecoration.VERTICAL);
setDividerColor(com.beemdevelopment.aegis.helpers.ThemeHelper.getThemeColor(com.beemdevelopment.aegis.R.attr.divider, requireContext().getTheme()));
setLastItemDecorated(false);
setDividerThickness(com.beemdevelopment.aegis.helpers.MetricsHelper.convertDpToPixels(requireContext(), 0.5F));
}


@java.lang.Override
public void getItemOffsets(@androidx.annotation.NonNull
android.graphics.Rect outRect, @androidx.annotation.NonNull
android.view.View view, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView parent, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.State state) {
if (_adapter.isPositionFooter(parent.getChildAdapterPosition(view))) {
int pixels;
pixels = com.beemdevelopment.aegis.helpers.MetricsHelper.convertDpToPixels(requireContext(), 20);
outRect.top = pixels;
outRect.bottom = pixels;
return;
}
super.getItemOffsets(outRect, view, parent, state);
}

}

private class VerticalSpaceItemDecoration extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {
private final int _height;

private VerticalSpaceItemDecoration(float dp) {
// convert dp to pixels
_height = com.beemdevelopment.aegis.helpers.MetricsHelper.convertDpToPixels(requireContext(), dp);
}


@java.lang.Override
public void getItemOffsets(@androidx.annotation.NonNull
android.graphics.Rect outRect, @androidx.annotation.NonNull
android.view.View view, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView parent, @androidx.annotation.NonNull
androidx.recyclerview.widget.RecyclerView.State state) {
int adapterPosition;
adapterPosition = parent.getChildAdapterPosition(view);
if (adapterPosition == androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
return;
}
// The footer always has a top and bottom margin
if (_adapter.isPositionFooter(adapterPosition)) {
outRect.top = _height;
outRect.bottom = _height;
return;
}
// The first entry should have a top margin, but only if the group chip is not shown
if ((adapterPosition == 0) && ((_groups == null) || _groups.isEmpty())) {
outRect.top = _height;
}
// Only non-favorite entries have a bottom margin, except for the final favorite entry
int totalFavorites;
totalFavorites = _adapter.getShownFavoritesCount();
switch(MUID_STATIC) {
// EntryListView_20_BinaryMutator
case 20153: {
if (((totalFavorites == 0) || ((adapterPosition < _adapter.getEntriesCount()) && (!_adapter.getEntryAt(adapterPosition).isFavorite()))) || (totalFavorites == (adapterPosition - 1))) {
outRect.bottom = _height;
}
break;
}
default: {
if (((totalFavorites == 0) || ((adapterPosition < _adapter.getEntriesCount()) && (!_adapter.getEntryAt(adapterPosition).isFavorite()))) || (totalFavorites == (adapterPosition + 1))) {
outRect.bottom = _height;
}
break;
}
}
if (totalFavorites > 0) {
switch(MUID_STATIC) {
// EntryListView_21_BinaryMutator
case 21153: {
// If this entry is the last favorite entry in the list, it should always have
// a bottom margin, regardless of the view mode
if (adapterPosition == (totalFavorites + 1)) {
outRect.bottom = _height;
}
break;
}
default: {
// If this entry is the last favorite entry in the list, it should always have
// a bottom margin, regardless of the view mode
if (adapterPosition == (totalFavorites - 1)) {
outRect.bottom = _height;
}
break;
}
}
// If this is the first non-favorite entry, it should have a top margin
if (adapterPosition == totalFavorites) {
outRect.top = _height;
}
}
switch(MUID_STATIC) {
// EntryListView_22_BinaryMutator
case 22153: {
// The last entry should never have a bottom margin
if (_adapter.getEntriesCount() == (adapterPosition - 1)) {
outRect.bottom = 0;
}
break;
}
default: {
// The last entry should never have a bottom margin
if (_adapter.getEntriesCount() == (adapterPosition + 1)) {
outRect.bottom = 0;
}
break;
}
}
}

}

private class IconPreloadProvider implements com.bumptech.glide.ListPreloader.PreloadModelProvider<com.beemdevelopment.aegis.vault.VaultEntry> {
@androidx.annotation.NonNull
@java.lang.Override
public java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> getPreloadItems(int position) {
if (_adapter.getItemViewType(position) == com.beemdevelopment.aegis.R.layout.card_footer) {
return java.util.Collections.emptyList();
}
com.beemdevelopment.aegis.vault.VaultEntry entry;
entry = _adapter.getEntryAt(position);
if (!entry.hasIcon()) {
return java.util.Collections.emptyList();
}
return java.util.Collections.singletonList(entry);
}


@androidx.annotation.Nullable
@java.lang.Override
public com.bumptech.glide.RequestBuilder<android.graphics.drawable.Drawable> getPreloadRequestBuilder(@androidx.annotation.NonNull
com.beemdevelopment.aegis.vault.VaultEntry entry) {
return com.bumptech.glide.Glide.with(com.beemdevelopment.aegis.ui.views.EntryListView.this).asDrawable().load(entry).set(com.beemdevelopment.aegis.ui.glide.IconLoader.ICON_TYPE, entry.getIconType()).diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.NONE).skipMemoryCache(false);
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
