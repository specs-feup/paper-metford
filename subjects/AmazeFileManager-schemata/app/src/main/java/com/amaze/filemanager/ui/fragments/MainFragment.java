/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.ui.fragments;
import android.provider.DocumentsContract;
import com.amaze.filemanager.filesystem.files.FileUtils;
import java.util.ArrayList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.fragment.app.Fragment;
import com.amaze.filemanager.asynchronous.asynctasks.DeleteTask;
import com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask;
import com.amaze.filemanager.filesystem.CustomFileObserver;
import kotlin.collections.CollectionsKt;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.content.pm.ShortcutInfoCompat;
import static com.amaze.filemanager.filesystem.FileProperties.ANDROID_DATA_DIRS;
import androidx.appcompat.widget.AppCompatTextView;
import com.amaze.filemanager.filesystem.HybridFile;
import com.amaze.filemanager.adapters.RecyclerAdapter;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import android.media.RingtoneManager;
import com.amaze.filemanager.utils.DataUtils;
import android.view.LayoutInflater;
import com.afollestad.materialdialogs.DialogAction;
import com.amaze.filemanager.filesystem.SafRootHolder;
import java.util.Map;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amaze.filemanager.ui.activities.MainActivityViewModel;
import com.amaze.filemanager.utils.GenericExtKt;
import com.amaze.filemanager.ui.drag.RecyclerAdapterDragListener;
import android.net.Uri;
import org.slf4j.Logger;
import androidx.fragment.app.FragmentActivity;
import com.amaze.filemanager.utils.OTGUtil;
import static android.os.Build.VERSION_CODES.Q;
import com.amaze.filemanager.R;
import androidx.lifecycle.ViewModelProvider;
import android.content.UriPermission;
import android.content.SharedPreferences;
import com.amaze.filemanager.filesystem.files.MediaConnectionUtils;
import android.text.TextUtils;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.Intent;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.database.models.explorer.Tab;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.annotation.RequiresApi;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ClipDescription;
import android.content.Context;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import java.util.HashMap;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.amaze.filemanager.utils.BottomBarButtonPath;
import java.util.List;
import com.amaze.filemanager.adapters.holders.ItemViewHolder;
import com.amaze.filemanager.ui.theme.AppTheme;
import java.util.HashSet;
import android.graphics.Color;
import android.os.AsyncTask;
import com.amaze.filemanager.filesystem.files.EncryptDecryptUtils;
import android.content.BroadcastReceiver;
import com.amaze.filemanager.database.SortHandler;
import androidx.appcompat.widget.AppCompatImageView;
import kotlin.collections.ArraysKt;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import com.amaze.filemanager.ui.icons.MimeTypes;
import androidx.fragment.app.FragmentManager;
import com.amaze.filemanager.ui.drag.TabFragmentBottomDragListener;
import com.amaze.filemanager.ui.views.WarnableTextInputValidator;
import com.amaze.filemanager.ui.ExtensionsKt;
import java.io.File;
import android.content.ClipData;
import androidx.annotation.Nullable;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_DIVIDERS;
import android.text.format.Formatter;
import com.amaze.filemanager.ui.activities.MainActivity;
import com.amaze.filemanager.ui.views.FastScroller;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HIDDENFILES;
import static android.os.Build.VERSION.SDK_INT;
import android.view.KeyEvent;
import com.amaze.filemanager.filesystem.files.CryptUtil;
import android.widget.Toast;
import org.slf4j.LoggerFactory;
import com.amaze.filemanager.filesystem.FileProperties;
import android.view.ViewTreeObserver;
import jcifs.smb.SmbException;
import android.content.IntentFilter;
import androidx.core.graphics.drawable.IconCompat;
import static com.amaze.filemanager.filesystem.FileProperties.ANDROID_DEVICE_DATA_DIRS;
import com.amaze.filemanager.application.AppConfig;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import com.amaze.filemanager.ui.views.DividerItemDecoration;
import com.amaze.filemanager.asynchronous.handlers.FileHandler;
import android.view.View;
import jcifs.smb.SmbFile;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import com.amaze.filemanager.ui.views.CustomScrollLinearLayoutManager;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;
import com.amaze.filemanager.ui.views.CustomScrollGridLayoutManager;
import androidx.activity.result.ActivityResultLauncher;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MainFragment extends androidx.fragment.app.Fragment implements com.amaze.filemanager.utils.BottomBarButtonPath , android.view.ViewTreeObserver.OnGlobalLayoutListener , com.amaze.filemanager.ui.fragments.AdjustListViewForTv<com.amaze.filemanager.adapters.holders.ItemViewHolder> {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.ui.fragments.MainFragment.class);

    private static final java.lang.String KEY_FRAGMENT_MAIN = "main";

    public androidx.swiperefreshlayout.widget.SwipeRefreshLayout mSwipeRefreshLayout;

    public com.amaze.filemanager.adapters.RecyclerAdapter adapter;

    private android.content.SharedPreferences sharedPref;

    // ATTRIBUTES FOR APPEARANCE AND COLORS
    private androidx.recyclerview.widget.LinearLayoutManager mLayoutManager;

    private androidx.recyclerview.widget.GridLayoutManager mLayoutManagerGrid;

    private com.amaze.filemanager.ui.views.DividerItemDecoration dividerItemDecoration;

    private com.google.android.material.appbar.AppBarLayout mToolbarContainer;

    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout nofilesview;

    private androidx.recyclerview.widget.RecyclerView listView;

    private com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider;

    private java.util.HashMap<java.lang.String, android.os.Bundle> scrolls = new java.util.HashMap<>();

    private android.view.View rootView;

    private com.amaze.filemanager.ui.views.FastScroller fastScroller;

    private com.amaze.filemanager.filesystem.CustomFileObserver customFileObserver;

    // defines the current visible tab, default either 0 or 1
    // private int mCurrentTab;
    private com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel mainFragmentViewModel;

    private com.amaze.filemanager.ui.activities.MainActivityViewModel mainActivityViewModel;

    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> handleDocumentUriForRestrictedDirectories = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(), (androidx.activity.result.ActivityResult result) -> {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            if ((result.getData() != null) && (getContext() != null)) {
                getContext().getContentResolver().takePersistableUriPermission(result.getData().getData(), android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION | android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                com.amaze.filemanager.filesystem.SafRootHolder.setUriRoot(result.getData().getData());
                loadlist(result.getData().getDataString(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE, true);
            } else if (getContext() != null) {
                com.amaze.filemanager.application.AppConfig.toast(requireContext(), getString(com.amaze.filemanager.R.string.operation_unsuccesful));
            }
        }
    });

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // MainFragment_0_LengthyGUICreationOperatorMutator
            case 115: {
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
    mainFragmentViewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel.class);
    mainActivityViewModel = new androidx.lifecycle.ViewModelProvider(requireMainActivity()).get(com.amaze.filemanager.ui.activities.MainActivityViewModel.class);
    utilsProvider = requireMainActivity().getUtilsProvider();
    sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity());
    mainFragmentViewModel.initBundleArguments(getArguments());
    mainFragmentViewModel.initIsList();
    mainFragmentViewModel.initColumns(sharedPref);
    mainFragmentViewModel.initSortModes(com.amaze.filemanager.database.SortHandler.getSortType(getContext(), getCurrentPath()), sharedPref);
    mainFragmentViewModel.setAccentColor(requireMainActivity().getAccent());
    mainFragmentViewModel.setPrimaryColor(requireMainActivity().getCurrentColorPreference().getPrimaryFirstTab());
    mainFragmentViewModel.setPrimaryTwoColor(requireMainActivity().getCurrentColorPreference().getPrimarySecondTab());
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    rootView = inflater.inflate(com.amaze.filemanager.R.layout.main_frag, container, false);
    return rootView;
}


@java.lang.Override
@java.lang.SuppressWarnings("PMD.NPathComplexity")
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mainFragmentViewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel.class);
    switch(MUID_STATIC) {
        // MainFragment_1_InvalidViewFocusOperatorMutator
        case 1115: {
            /**
            * Inserted by Kadabra
            */
            listView = rootView.findViewById(com.amaze.filemanager.R.id.listView);
            listView.requestFocus();
            break;
        }
        // MainFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2115: {
            /**
            * Inserted by Kadabra
            */
            listView = rootView.findViewById(com.amaze.filemanager.R.id.listView);
            listView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        listView = rootView.findViewById(com.amaze.filemanager.R.id.listView);
        break;
    }
}
mToolbarContainer = requireMainActivity().getAppbar().getAppbarLayout();
switch(MUID_STATIC) {
    // MainFragment_3_InvalidViewFocusOperatorMutator
    case 3115: {
        /**
        * Inserted by Kadabra
        */
        fastScroller = rootView.findViewById(com.amaze.filemanager.R.id.fastscroll);
        fastScroller.requestFocus();
        break;
    }
    // MainFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4115: {
        /**
        * Inserted by Kadabra
        */
        fastScroller = rootView.findViewById(com.amaze.filemanager.R.id.fastscroll);
        fastScroller.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    fastScroller = rootView.findViewById(com.amaze.filemanager.R.id.fastscroll);
    break;
}
}
fastScroller.setPressedHandleColor(mainFragmentViewModel.getAccentColor());
android.view.View.OnTouchListener onTouchListener;
onTouchListener = (android.view.View view1,android.view.MotionEvent motionEvent) -> {
if ((adapter != null) && mainFragmentViewModel.getStopAnims()) {
    stopAnimation();
    mainFragmentViewModel.setStopAnims(false);
}
return false;
};
listView.setOnTouchListener(onTouchListener);
// listView.setOnDragListener(new MainFragmentDragListener());
mToolbarContainer.setOnTouchListener(onTouchListener);
switch(MUID_STATIC) {
// MainFragment_5_InvalidViewFocusOperatorMutator
case 5115: {
    /**
    * Inserted by Kadabra
    */
    mSwipeRefreshLayout = rootView.findViewById(com.amaze.filemanager.R.id.activity_main_swipe_refresh_layout);
    mSwipeRefreshLayout.requestFocus();
    break;
}
// MainFragment_6_ViewComponentNotVisibleOperatorMutator
case 6115: {
    /**
    * Inserted by Kadabra
    */
    mSwipeRefreshLayout = rootView.findViewById(com.amaze.filemanager.R.id.activity_main_swipe_refresh_layout);
    mSwipeRefreshLayout.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mSwipeRefreshLayout = rootView.findViewById(com.amaze.filemanager.R.id.activity_main_swipe_refresh_layout);
break;
}
}
mSwipeRefreshLayout.setOnRefreshListener(() -> updateList(true));
// String itemsstring = res.getString(R.string.items);// TODO: 23/5/2017 use or delete
mToolbarContainer.setBackgroundColor(com.amaze.filemanager.ui.activities.MainActivity.currentTab == 1 ? mainFragmentViewModel.getPrimaryTwoColor() : mainFragmentViewModel.getPrimaryColor());
// listView.setPadding(listView.getPaddingLeft(), paddingTop, listView.getPaddingRight(),
// listView.getPaddingBottom());
setHasOptionsMenu(false);
initNoFileLayout();
com.amaze.filemanager.filesystem.HybridFile f;
f = new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, mainFragmentViewModel.getCurrentPath());
f.generateMode(getActivity());
getMainActivity().getAppbar().getBottomBar().setClickListener();
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT) && (!mainFragmentViewModel.isList())) {
listView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.grid_background_light));
} else {
listView.setBackgroundDrawable(null);
}
listView.setHasFixedSize(true);
if (mainFragmentViewModel.isList()) {
mLayoutManager = new com.amaze.filemanager.ui.views.CustomScrollLinearLayoutManager(getContext());
listView.setLayoutManager(mLayoutManager);
} else {
if (mainFragmentViewModel.getColumns() == null)
mLayoutManagerGrid = new com.amaze.filemanager.ui.views.CustomScrollGridLayoutManager(getActivity(), 3);
else
mLayoutManagerGrid = new com.amaze.filemanager.ui.views.CustomScrollGridLayoutManager(getActivity(), mainFragmentViewModel.getColumns());

setGridLayoutSpanSizeLookup(mLayoutManagerGrid);
listView.setLayoutManager(mLayoutManagerGrid);
}
// use a linear layout manager
// View footerView = getActivity().getLayoutInflater().inflate(R.layout.divider, null);// TODO:
// 23/5/2017 use or delete
dividerItemDecoration = new com.amaze.filemanager.ui.views.DividerItemDecoration(requireActivity(), false, getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_DIVIDERS));
listView.addItemDecoration(dividerItemDecoration);
mSwipeRefreshLayout.setColorSchemeColors(mainFragmentViewModel.getAccentColor());
androidx.recyclerview.widget.DefaultItemAnimator animator;
animator = new androidx.recyclerview.widget.DefaultItemAnimator();
listView.setItemAnimator(animator);
mToolbarContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
loadViews();
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
androidx.fragment.app.FragmentManager fragmentManager;
fragmentManager = requireActivity().getSupportFragmentManager();
fragmentManager.executePendingTransactions();
fragmentManager.putFragment(outState, com.amaze.filemanager.ui.fragments.MainFragment.KEY_FRAGMENT_MAIN, this);
}


public void stopAnimation() {
if (!adapter.stoppedAnimation) {
for (int j = 0; j < listView.getChildCount(); j++) {
android.view.View v;
v = listView.getChildAt(j);
if (v != null)
    v.clearAnimation();

}
}
adapter.stoppedAnimation = true;
}


void setGridLayoutSpanSizeLookup(androidx.recyclerview.widget.GridLayoutManager mLayoutManagerGrid) {
mLayoutManagerGrid.setSpanSizeLookup(new androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
@java.lang.Override
public int getSpanSize(int position) {
switch (adapter.getItemViewType(position)) {
    case com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FILES :
    case com.amaze.filemanager.adapters.RecyclerAdapter.TYPE_HEADER_FOLDERS :
        return (mainFragmentViewModel.getColumns() == 0) || (mainFragmentViewModel.getColumns() == (-1)) ? 3 : mainFragmentViewModel.getColumns();
    default :
        return 1;
}
}

});
}


void switchToGrid() {
mainFragmentViewModel.setList(false);
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
// will always be grid, set alternate white background
listView.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.grid_background_light));
}
if (mLayoutManagerGrid == null)
if ((mainFragmentViewModel.getColumns() == (-1)) || (mainFragmentViewModel.getColumns() == 0))
mLayoutManagerGrid = new com.amaze.filemanager.ui.views.CustomScrollGridLayoutManager(getActivity(), 3);
else
mLayoutManagerGrid = new com.amaze.filemanager.ui.views.CustomScrollGridLayoutManager(getActivity(), mainFragmentViewModel.getColumns());


setGridLayoutSpanSizeLookup(mLayoutManagerGrid);
listView.setLayoutManager(mLayoutManagerGrid);
listView.clearOnScrollListeners();
mainFragmentViewModel.setAdapterListItems(null);
mainFragmentViewModel.setIconList(null);
adapter = null;
}


void switchToList() {
mainFragmentViewModel.setList(true);
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
listView.setBackgroundDrawable(null);
}
if (mLayoutManager == null)
mLayoutManager = new com.amaze.filemanager.ui.views.CustomScrollLinearLayoutManager(getActivity());

listView.setLayoutManager(mLayoutManager);
listView.clearOnScrollListeners();
mainFragmentViewModel.setAdapterListItems(null);
mainFragmentViewModel.setIconList(null);
adapter = null;
}


public void switchView() {
boolean isPathLayoutGrid;
isPathLayoutGrid = com.amaze.filemanager.utils.DataUtils.getInstance().getListOrGridForPath(mainFragmentViewModel.getCurrentPath(), com.amaze.filemanager.utils.DataUtils.LIST) == com.amaze.filemanager.utils.DataUtils.GRID;
reloadListElements(false, isPathLayoutGrid);
}


private void loadViews() {
if (mainFragmentViewModel.getCurrentPath() != null) {
if (mainFragmentViewModel.getListElements().size() == 0) {
loadlist(mainFragmentViewModel.getCurrentPath(), true, mainFragmentViewModel.getOpenMode(), false);
} else {
reloadListElements(true, !mainFragmentViewModel.isList());
}
} else {
loadlist(mainFragmentViewModel.getHome(), true, mainFragmentViewModel.getOpenMode(), false);
}
}


private android.content.BroadcastReceiver receiver2 = new android.content.BroadcastReceiver() {
@java.lang.Override
public void onReceive(android.content.Context context, android.content.Intent intent) {
// load the list on a load broadcast
// local file system don't need an explicit load, we've set an observer to
// take actions on creation/moving/deletion/modification of file on current path
if (getCurrentPath() != null) {
mainActivityViewModel.evictPathFromListCache(getCurrentPath());
}
updateList(false);
}

};

private android.content.BroadcastReceiver decryptReceiver = new android.content.BroadcastReceiver() {
@java.lang.Override
public void onReceive(android.content.Context context, android.content.Intent intent) {
if (mainFragmentViewModel.isEncryptOpen() && (mainFragmentViewModel.getEncryptBaseFile() != null)) {
com.amaze.filemanager.filesystem.files.FileUtils.openFile(mainFragmentViewModel.getEncryptBaseFile().getFile(), requireMainActivity(), sharedPref);
mainFragmentViewModel.setEncryptOpen(false);
}
}

};

public void home() {
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);
}


/**
 * method called when list item is clicked in the adapter
 *
 * @param isBackButton
 * 		is it the back button aka '..'
 * @param position
 * 		the position
 * @param layoutElementParcelable
 * 		the list item
 * @param imageView
 * 		the check icon that is to be animated
 */
public void onListItemClicked(boolean isBackButton, int position, com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElementParcelable, androidx.appcompat.widget.AppCompatImageView imageView) {
if (requireMainActivity().getListItemSelected()) {
if (isBackButton) {
requireMainActivity().setListItemSelected(false);
if (requireMainActivity().getActionModeHelper().getActionMode() != null) {
    requireMainActivity().getActionModeHelper().getActionMode().finish();
}
requireMainActivity().getActionModeHelper().setActionMode(null);
} else {
// the first {goback} item if back navigation is enabled
registerListItemChecked(position, imageView);
}
} else if (isBackButton) {
goBackItemClick();
} else {
// hiding search view if visible
if (requireMainActivity().getAppbar().getSearchView().isEnabled()) {
requireMainActivity().getAppbar().getSearchView().hideSearchView();
}
java.lang.String path;
path = (!layoutElementParcelable.hasSymlink()) ? layoutElementParcelable.desc : layoutElementParcelable.symlink;
if (layoutElementParcelable.isDirectory) {
if (layoutElementParcelable.getMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN) {
    // don't open file hierarchy for trash bin
    adapter.toggleChecked(position, imageView);
} else {
    computeScroll();
    loadlist(path, false, mainFragmentViewModel.getOpenMode(), false);
}
} else if (layoutElementParcelable.desc.endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION) || layoutElementParcelable.desc.endsWith(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION)) {
// decrypt the file
mainFragmentViewModel.setEncryptOpen(true);
mainFragmentViewModel.initEncryptBaseFile((getActivity().getExternalCacheDir().getPath() + "/") + layoutElementParcelable.generateBaseFile().getName(getMainActivity()).replace(com.amaze.filemanager.filesystem.files.CryptUtil.CRYPT_EXTENSION, "").replace(com.amaze.filemanager.filesystem.files.CryptUtil.AESCRYPT_EXTENSION, ""));
com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.decryptFile(getContext(), getMainActivity(), this, mainFragmentViewModel.getOpenMode(), layoutElementParcelable.generateBaseFile(), getActivity().getExternalCacheDir().getPath(), utilsProvider, true);
} else if (getMainActivity().mReturnIntent) {
// are we here to return an intent to another app
returnIntentResults(new com.amaze.filemanager.filesystem.HybridFileParcelable[]{ layoutElementParcelable.generateBaseFile() });
} else {
layoutElementParcelable.generateBaseFile().openFile(getMainActivity(), false);
com.amaze.filemanager.utils.DataUtils.getInstance().addHistoryFile(layoutElementParcelable.desc);
}
}
}


public void registerListItemChecked(int position, androidx.appcompat.widget.AppCompatImageView imageView) {
com.amaze.filemanager.ui.activities.MainActivity mainActivity;
mainActivity = requireMainActivity();
if (mainActivity.mReturnIntent && (!mainActivity.getIntent().getBooleanExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE, false))) {
// Only one item should be checked
java.util.ArrayList<java.lang.Integer> checkedItemsIndex;
checkedItemsIndex = adapter.getCheckedItemsIndex();
if (checkedItemsIndex.contains(position)) {
// The clicked item was the only item checked so it can be unchecked
adapter.toggleChecked(position, imageView);
} else {
// The clicked item was not checked so we have to uncheck all currently checked items
for (java.lang.Integer index : checkedItemsIndex) {
    adapter.toggleChecked(index, imageView);
}
// Now we check the clicked item
adapter.toggleChecked(position, imageView);
}
} else
adapter.toggleChecked(position, imageView);

}


public void updateTabWithDb(com.amaze.filemanager.database.models.explorer.Tab tab) {
mainFragmentViewModel.setCurrentPath(tab.path);
mainFragmentViewModel.setHome(tab.home);
loadlist(mainFragmentViewModel.getCurrentPath(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN, false);
}


/**
 * Returns the intent with uri corresponding to specific {@link HybridFileParcelable} back to
 * external app
 */
public void returnIntentResults(com.amaze.filemanager.filesystem.HybridFileParcelable[] baseFiles) {
requireMainActivity().mReturnIntent = false;
java.util.HashMap<com.amaze.filemanager.filesystem.HybridFileParcelable, android.net.Uri> resultUris;
resultUris = new java.util.HashMap<>();
java.util.ArrayList<java.lang.String> failedPaths;
failedPaths = new java.util.ArrayList<>();
for (com.amaze.filemanager.filesystem.HybridFileParcelable baseFile : baseFiles) {
@androidx.annotation.Nullable
android.net.Uri resultUri;
resultUri = com.amaze.filemanager.utils.Utils.getUriForBaseFile(requireActivity(), baseFile);
if (resultUri != null) {
resultUris.put(baseFile, resultUri);
com.amaze.filemanager.ui.fragments.MainFragment.LOG.debug((resultUri + "\t") + com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory()));
} else {
failedPaths.add(baseFile.getPath());
}
}
if (!resultUris.isEmpty()) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainFragment_7_NullIntentOperatorMutator
case 7115: {
    intent = null;
    break;
}
// MainFragment_8_RandomActionIntentDefinitionOperatorMutator
case 8115: {
    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// MainFragment_9_RandomActionIntentDefinitionOperatorMutator
case 9115: {
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
intent.setFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
break;
}
}
if (resultUris.size() == 1) {
switch(MUID_STATIC) {
// MainFragment_10_RandomActionIntentDefinitionOperatorMutator
case 10115: {
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
intent.setAction(android.content.Intent.ACTION_SEND);
break;
}
}
java.util.Map.Entry<com.amaze.filemanager.filesystem.HybridFileParcelable, android.net.Uri> result;
result = resultUris.entrySet().iterator().next();
android.net.Uri resultUri;
resultUri = result.getValue();
com.amaze.filemanager.filesystem.HybridFileParcelable resultBaseFile;
resultBaseFile = result.getKey();
if (requireMainActivity().mRingtonePickerIntent) {
switch(MUID_STATIC) {
// MainFragment_11_RandomActionIntentDefinitionOperatorMutator
case 11115: {
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
intent.setDataAndType(resultUri, com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(resultBaseFile.getPath(), resultBaseFile.isDirectory()));
break;
}
}
switch(MUID_STATIC) {
// MainFragment_12_NullValueIntentPutExtraOperatorMutator
case 12115: {
intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_PICKED_URI, new Parcelable[0]);
break;
}
// MainFragment_13_IntentPayloadReplacementOperatorMutator
case 13115: {
intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_PICKED_URI, (android.net.Uri) null);
break;
}
default: {
switch(MUID_STATIC) {
// MainFragment_14_RandomActionIntentDefinitionOperatorMutator
case 14115: {
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
intent.putExtra(android.media.RingtoneManager.EXTRA_RINGTONE_PICKED_URI, resultUri);
break;
}
}
break;
}
}
} else {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.debug("pickup file");
switch(MUID_STATIC) {
// MainFragment_15_RandomActionIntentDefinitionOperatorMutator
case 15115: {
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
intent.setDataAndType(resultUri, com.amaze.filemanager.ui.icons.MimeTypes.getExtension(resultBaseFile.getPath()));
break;
}
}
}
} else {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.debug("pickup multiple files");
// Build ClipData
java.util.ArrayList<android.content.ClipData.Item> uriDataClipItems;
uriDataClipItems = new java.util.ArrayList<>();
java.util.HashSet<java.lang.String> mimeTypes;
mimeTypes = new java.util.HashSet<>();
for (java.util.Map.Entry<com.amaze.filemanager.filesystem.HybridFileParcelable, android.net.Uri> result : resultUris.entrySet()) {
com.amaze.filemanager.filesystem.HybridFileParcelable baseFile;
baseFile = result.getKey();
android.net.Uri uri;
uri = result.getValue();
mimeTypes.add(com.amaze.filemanager.ui.icons.MimeTypes.getMimeType(baseFile.getPath(), baseFile.isDirectory()));
uriDataClipItems.add(new android.content.ClipData.Item(uri));
}
android.content.ClipData clipData;
clipData = new android.content.ClipData(android.content.ClipDescription.MIMETYPE_TEXT_URILIST, mimeTypes.toArray(new java.lang.String[0]), uriDataClipItems.remove(0));
for (android.content.ClipData.Item item : uriDataClipItems) {
clipData.addItem(item);
}
intent.setClipData(clipData);
switch(MUID_STATIC) {
// MainFragment_16_RandomActionIntentDefinitionOperatorMutator
case 16115: {
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
intent.setAction(android.content.Intent.ACTION_SEND_MULTIPLE);
break;
}
}
switch(MUID_STATIC) {
// MainFragment_17_RandomActionIntentDefinitionOperatorMutator
case 17115: {
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
intent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, new java.util.ArrayList<>(resultUris.values()));
break;
}
}
}
requireActivity().setResult(androidx.fragment.app.FragmentActivity.RESULT_OK, intent);
}
if (!failedPaths.isEmpty()) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Unable to get URIs from baseFiles {}", failedPaths);
}
requireActivity().finish();
}


com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask loadFilesListTask;

/**
 * This loads a path into the MainFragment.
 *
 * @param providedPath
 * 		the path to be loaded
 * @param back
 * 		if we're coming back from any directory and want the scroll to be restored
 * @param providedOpenMode
 * 		the mode in which the directory should be opened
 * @param forceReload
 * 		whether use cached list or force reload the list items
 */
public void loadlist(final java.lang.String providedPath, final boolean back, final com.amaze.filemanager.fileoperations.filesystem.OpenMode providedOpenMode, boolean forceReload) {
if (mainFragmentViewModel == null) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Viewmodel not available to load the data");
return;
}
if (((getMainActivity() != null) && (getMainActivity().getActionModeHelper() != null)) && (getMainActivity().getActionModeHelper().getActionMode() != null)) {
getMainActivity().getActionModeHelper().getActionMode().finish();
}
mSwipeRefreshLayout.setRefreshing(true);
if ((loadFilesListTask != null) && (loadFilesListTask.getStatus() == android.os.AsyncTask.Status.RUNNING)) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Existing load list task running, cancel current");
loadFilesListTask.cancel(true);
}
com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode;
openMode = providedOpenMode;
java.lang.String actualPath;
actualPath = com.amaze.filemanager.filesystem.FileProperties.remapPathForApi30OrAbove(providedPath, false);
if ((android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) && kotlin.collections.ArraysKt.any(com.amaze.filemanager.filesystem.FileProperties.ANDROID_DATA_DIRS, providedPath::contains)) {
openMode = loadPathInQ(actualPath, providedPath, providedOpenMode);
} else if (actualPath.startsWith("/") && (com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE.equals(openMode) || com.amaze.filemanager.fileoperations.filesystem.OpenMode.ANDROID_DATA.equals(openMode))) {
openMode = com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
}
loadFilesListTask = new com.amaze.filemanager.asynchronous.asynctasks.LoadFilesListTask(getActivity(), actualPath, this, openMode, getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB), getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_HIDDENFILES), forceReload, (androidx.core.util.Pair<com.amaze.filemanager.fileoperations.filesystem.OpenMode, java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable>> data) -> {
mSwipeRefreshLayout.setRefreshing(false);
if ((data != null) && (data.second != null)) {
boolean isPathLayoutGrid;
isPathLayoutGrid = com.amaze.filemanager.utils.DataUtils.getInstance().getListOrGridForPath(providedPath, com.amaze.filemanager.utils.DataUtils.LIST) == com.amaze.filemanager.utils.DataUtils.GRID;
setListElements(data.second, back, providedPath, data.first, isPathLayoutGrid);
} else {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Load list operation cancelled");
}
});
loadFilesListTask.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
}


@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.Q)
private com.amaze.filemanager.fileoperations.filesystem.OpenMode loadPathInQ(java.lang.String actualPath, java.lang.String providedPath, com.amaze.filemanager.fileoperations.filesystem.OpenMode providedMode) {
if (com.amaze.filemanager.utils.GenericExtKt.containsPath(com.amaze.filemanager.filesystem.FileProperties.ANDROID_DEVICE_DATA_DIRS, providedPath) && (!com.amaze.filemanager.fileoperations.filesystem.OpenMode.ANDROID_DATA.equals(providedMode))) {
return com.amaze.filemanager.fileoperations.filesystem.OpenMode.ANDROID_DATA;
} else if (actualPath.startsWith("/")) {
return com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
} else if (actualPath.equals(providedPath)) {
return providedMode;
} else {
boolean hasAccessToSpecialFolder;
hasAccessToSpecialFolder = false;
java.util.List<android.content.UriPermission> uriPermissions;
uriPermissions = requireContext().getContentResolver().getPersistedUriPermissions();
if ((uriPermissions != null) && (uriPermissions.size() > 0)) {
for (android.content.UriPermission p : uriPermissions) {
if (p.isReadPermission() && actualPath.startsWith(p.getUri().toString())) {
hasAccessToSpecialFolder = true;
com.amaze.filemanager.filesystem.SafRootHolder.setUriRoot(p.getUri());
break;
}
}
}
if (!hasAccessToSpecialFolder) {
android.content.Intent intent;
switch(MUID_STATIC) {
// MainFragment_18_RandomActionIntentDefinitionOperatorMutator
case 18115: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_OPEN_DOCUMENT_TREE).putExtra(android.provider.DocumentsContract.EXTRA_INITIAL_URI, android.net.Uri.parse(com.amaze.filemanager.filesystem.FileProperties.remapPathForApi30OrAbove(providedPath, true)));
break;
}
}
com.afollestad.materialdialogs.MaterialDialog d;
d = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showBasicDialog(requireMainActivity(), com.amaze.filemanager.R.string.android_data_prompt_saf_access, com.amaze.filemanager.R.string.android_data_prompt_saf_access_title, android.R.string.ok, android.R.string.cancel);
switch(MUID_STATIC) {
// MainFragment_19_BuggyGUIListenerOperatorMutator
case 19115: {
d.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener(null);
break;
}
default: {
d.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).setOnClickListener((android.view.View v) -> {
com.amaze.filemanager.ui.ExtensionsKt.runIfDocumentsUIExists(intent, requireMainActivity(), () -> handleDocumentUriForRestrictedDirectories.launch(intent));
d.dismiss();
});
break;
}
}
d.show();
// At this point LoadFilesListTask will be triggered.
// No harm even give OpenMode.FILE here, it loads blank when it doesn't; and after the
// UriPermission is granted loadlist will be called again
return com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE;
} else {
return com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE;
}
}
}


void initNoFileLayout() {
switch(MUID_STATIC) {
// MainFragment_20_InvalidViewFocusOperatorMutator
case 20115: {
/**
* Inserted by Kadabra
*/
nofilesview = rootView.findViewById(com.amaze.filemanager.R.id.nofilelayout);
nofilesview.requestFocus();
break;
}
// MainFragment_21_ViewComponentNotVisibleOperatorMutator
case 21115: {
/**
* Inserted by Kadabra
*/
nofilesview = rootView.findViewById(com.amaze.filemanager.R.id.nofilelayout);
nofilesview.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
nofilesview = rootView.findViewById(com.amaze.filemanager.R.id.nofilelayout);
break;
}
}
nofilesview.setColorSchemeColors(mainFragmentViewModel.getAccentColor());
nofilesview.setOnRefreshListener(() -> {
loadlist(mainFragmentViewModel.getCurrentPath(), false, mainFragmentViewModel.getOpenMode(), false);
nofilesview.setRefreshing(false);
});
nofilesview.findViewById(com.amaze.filemanager.R.id.no_files_relative).setOnKeyListener((android.view.View v,int keyCode,android.view.KeyEvent event) -> {
if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_DPAD_RIGHT) {
requireMainActivity().getFAB().requestFocus();
} else if (event.getKeyCode() == android.view.KeyEvent.KEYCODE_BACK) {
requireMainActivity().onBackPressed();
} else {
return false;
}
}
return true;
});
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.LIGHT)) {
((androidx.appcompat.widget.AppCompatImageView) (nofilesview.findViewById(com.amaze.filemanager.R.id.image))).setColorFilter(android.graphics.Color.parseColor("#666666"));
} else if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
nofilesview.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), android.R.color.black));
((androidx.appcompat.widget.AppCompatTextView) (nofilesview.findViewById(com.amaze.filemanager.R.id.nofiletext))).setTextColor(android.graphics.Color.WHITE);
} else {
nofilesview.setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.holo_dark_background));
((androidx.appcompat.widget.AppCompatTextView) (nofilesview.findViewById(com.amaze.filemanager.R.id.nofiletext))).setTextColor(android.graphics.Color.WHITE);
}
}


/**
 * Loading adapter after getting a list of elements
 *
 * @param bitmap
 * 		the list of objects for the adapter
 * @param back
 * 		if we're coming back from any directory and want the scroll to be restored
 * @param path
 * 		the path for the adapter
 * @param openMode
 * 		the type of file being created
 * @param results
 * 		is the list of elements a result from search
 * @param grid
 * 		whether to set grid view or list view
 */
public void setListElements(java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> bitmap, boolean back, java.lang.String path, final com.amaze.filemanager.fileoperations.filesystem.OpenMode openMode, boolean grid) {
if (bitmap != null) {
mainFragmentViewModel.setListElements(bitmap);
mainFragmentViewModel.setCurrentPath(path);
mainFragmentViewModel.setOpenMode(openMode);
reloadListElements(back, grid);
} else {
// list loading cancelled
// TODO: Add support for cancelling list loading
loadlist(mainFragmentViewModel.getHome(), true, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);
}
}


public void reloadListElements(boolean back, boolean grid) {
if (isAdded()) {
boolean isOtg;
isOtg = (com.amaze.filemanager.utils.OTGUtil.PREFIX_OTG + "/").equals(mainFragmentViewModel.getCurrentPath());
if ((((getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_GOBACK_BUTTON) && (!"/".equals(mainFragmentViewModel.getCurrentPath()))) && (((mainFragmentViewModel.getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE) || (mainFragmentViewModel.getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.ROOT)) || (mainFragmentViewModel.getIsCloudOpenMode() && (!mainFragmentViewModel.getIsOnCloudRoot())))) && (!isOtg)) && ((mainFragmentViewModel.getListElements().size() == 0) || (!mainFragmentViewModel.getListElements().get(0).size.equals(getString(com.amaze.filemanager.R.string.goback))))) {
mainFragmentViewModel.getListElements().add(0, getBackElement());
}
if (mainFragmentViewModel.getListElements().size() == 0) {
nofilesview.setVisibility(android.view.View.VISIBLE);
listView.setVisibility(android.view.View.GONE);
mSwipeRefreshLayout.setEnabled(false);
} else {
mSwipeRefreshLayout.setEnabled(true);
nofilesview.setVisibility(android.view.View.GONE);
listView.setVisibility(android.view.View.VISIBLE);
}
if (grid && mainFragmentViewModel.isList()) {
switchToGrid();
} else if ((!grid) && (!mainFragmentViewModel.isList())) {
switchToList();
}
if (adapter == null) {
final java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> listElements;
listElements = mainFragmentViewModel.getListElements();
adapter = new com.amaze.filemanager.adapters.RecyclerAdapter(requireMainActivity(), this, utilsProvider, sharedPref, listView, listElements, requireContext(), grid);
} else {
adapter.setItems(listView, mainFragmentViewModel.getListElements());
}
mainFragmentViewModel.setStopAnims(true);
if ((mainFragmentViewModel.getOpenMode() != com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM) && (mainFragmentViewModel.getOpenMode() != com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN)) {
com.amaze.filemanager.utils.DataUtils.getInstance().addHistoryFile(mainFragmentViewModel.getCurrentPath());
}
listView.setAdapter(adapter);
if (!mainFragmentViewModel.getAddHeader()) {
listView.removeItemDecoration(dividerItemDecoration);
mainFragmentViewModel.setAddHeader(true);
}
if (mainFragmentViewModel.getAddHeader() && mainFragmentViewModel.isList()) {
dividerItemDecoration = new com.amaze.filemanager.ui.views.DividerItemDecoration(requireMainActivity(), true, getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_DIVIDERS));
listView.addItemDecoration(dividerItemDecoration);
mainFragmentViewModel.setAddHeader(false);
}
if (back && scrolls.containsKey(mainFragmentViewModel.getCurrentPath())) {
android.os.Bundle b;
b = scrolls.get(mainFragmentViewModel.getCurrentPath());
int index;
index = b.getInt("index");
int top;
top = b.getInt("top");
if (mainFragmentViewModel.isList()) {
mLayoutManager.scrollToPositionWithOffset(index, top);
} else {
mLayoutManagerGrid.scrollToPositionWithOffset(index, top);
}
}
requireMainActivity().updatePaths(mainFragmentViewModel.getNo());
requireMainActivity().showFab();
requireMainActivity().getAppbar().getAppbarLayout().setExpanded(true);
listView.stopScroll();
fastScroller.setRecyclerView(listView, mainFragmentViewModel.isList() ? 1 : (mainFragmentViewModel.getColumns() == 0) || (mainFragmentViewModel.getColumns() == (-1)) ? 3 : mainFragmentViewModel.getColumns());
mToolbarContainer.addOnOffsetChangedListener((com.google.android.material.appbar.AppBarLayout appBarLayout,int verticalOffset) -> {
fastScroller.updateHandlePosition(verticalOffset, 112);
});
fastScroller.registerOnTouchListener(() -> {
if (mainFragmentViewModel.getStopAnims() && (adapter != null)) {
stopAnimation();
mainFragmentViewModel.setStopAnims(false);
}
});
startFileObserver();
listView.post(() -> {
java.lang.String fileName;
fileName = requireMainActivity().getScrollToFileName();
if (fileName != null)
mainFragmentViewModel.getScrollPosition(fileName).observe(getViewLifecycleOwner(), (java.lang.Integer scrollPosition) -> {
if (scrollPosition != (-1)) {
switch(MUID_STATIC) {
// MainFragment_22_BinaryMutator
case 22115: {
listView.scrollToPosition(java.lang.Math.min(scrollPosition - 4, adapter.getItemCount() - 1));
break;
}
default: {
switch(MUID_STATIC) {
// MainFragment_23_BinaryMutator
case 23115: {
listView.scrollToPosition(java.lang.Math.min(scrollPosition + 4, adapter.getItemCount() + 1));
break;
}
default: {
listView.scrollToPosition(java.lang.Math.min(scrollPosition + 4, adapter.getItemCount() - 1));
break;
}
}
break;
}
}
}
adapter.notifyItemChanged(scrollPosition);
});

});
} else {
// fragment not added
initNoFileLayout();
}
}


private com.amaze.filemanager.adapters.data.LayoutElementParcelable getBackElement() {
if (mainFragmentViewModel.getBack() == null) {
mainFragmentViewModel.setBack(new com.amaze.filemanager.adapters.data.LayoutElementParcelable(requireContext(), true, getString(com.amaze.filemanager.R.string.goback), getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB)));
}
return mainFragmentViewModel.getBack();
}


/**
 * Method will resume any decryption tasks like registering decryption receiver or deleting any
 * pending opened files in application cache
 */
private void resumeDecryptOperations() {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
switch(MUID_STATIC) {
// MainFragment_24_RandomActionIntentDefinitionOperatorMutator
case 24115: {
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
requireMainActivity().registerReceiver(decryptReceiver, new android.content.IntentFilter(com.amaze.filemanager.filesystem.files.EncryptDecryptUtils.DECRYPT_BROADCAST));
break;
}
}
if ((!mainFragmentViewModel.isEncryptOpen()) && (!com.amaze.filemanager.utils.Utils.isNullOrEmpty(mainFragmentViewModel.getEncryptBaseFiles()))) {
// we've opened the file and are ready to delete it
new com.amaze.filemanager.asynchronous.asynctasks.DeleteTask(requireMainActivity(), true).execute(mainFragmentViewModel.getEncryptBaseFiles());
mainFragmentViewModel.setEncryptBaseFiles(new java.util.ArrayList<>());
}
}
}


private void startFileObserver() {
switch (mainFragmentViewModel.getOpenMode()) {
case ROOT :
case FILE :
if (((customFileObserver != null) && (!customFileObserver.wasStopped())) && customFileObserver.getPath().equals(getCurrentPath())) {
return;
}
java.io.File file;
file = null;
if (mainFragmentViewModel.getCurrentPath() != null) {
file = new java.io.File(mainFragmentViewModel.getCurrentPath());
}
if (((file != null) && file.isDirectory()) && file.canRead()) {
if (customFileObserver != null) {
// already a watcher instantiated, first it should be stopped
customFileObserver.stopWatching();
}
customFileObserver = new com.amaze.filemanager.filesystem.CustomFileObserver(mainFragmentViewModel.getCurrentPath(), new com.amaze.filemanager.asynchronous.handlers.FileHandler(this, listView, getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB)));
customFileObserver.startWatching();
}
break;
default :
break;
}
}


/**
 * Show dialog to rename a file
 *
 * @param f
 * 		the file to rename
 */
public void rename(final com.amaze.filemanager.filesystem.HybridFileParcelable f) {
com.afollestad.materialdialogs.MaterialDialog renameDialog;
renameDialog = com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.showNameDialog(getMainActivity(), "", f.getName(getMainActivity()), getResources().getString(com.amaze.filemanager.R.string.rename), getResources().getString(com.amaze.filemanager.R.string.save), null, getResources().getString(com.amaze.filemanager.R.string.cancel), (com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
androidx.appcompat.widget.AppCompatEditText textfield;
switch(MUID_STATIC) {
// MainFragment_25_InvalidViewFocusOperatorMutator
case 25115: {
/**
* Inserted by Kadabra
*/
textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
textfield.requestFocus();
break;
}
// MainFragment_26_ViewComponentNotVisibleOperatorMutator
case 26115: {
/**
* Inserted by Kadabra
*/
textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
textfield.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textfield = dialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
break;
}
}
java.lang.String name1;
name1 = textfield.getText().toString().trim();
getMainActivity().mainActivityHelper.rename(mainFragmentViewModel.getOpenMode(), f.getPath(), mainFragmentViewModel.getCurrentPath(), name1, f.isDirectory(), getActivity(), getMainActivity().isRootExplorer());
}, (java.lang.String text) -> {
boolean isValidFilename;
isValidFilename = com.amaze.filemanager.filesystem.FileProperties.isValidFilename(text);
if ((!isValidFilename) || text.startsWith(" ")) {
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.invalid_name);
} else if (text.length() < 1) {
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState(com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR, com.amaze.filemanager.R.string.field_empty);
}
return new com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState();
});
// place cursor at the starting of edit text by posting a runnable to edit text
// this is done because in case android has not populated the edit text layouts yet, it'll
// reset calls to selection if not posted in message queue
androidx.appcompat.widget.AppCompatEditText textfield;
switch(MUID_STATIC) {
// MainFragment_27_InvalidViewFocusOperatorMutator
case 27115: {
/**
* Inserted by Kadabra
*/
textfield = renameDialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
textfield.requestFocus();
break;
}
// MainFragment_28_ViewComponentNotVisibleOperatorMutator
case 28115: {
/**
* Inserted by Kadabra
*/
textfield = renameDialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
textfield.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textfield = renameDialog.getCustomView().findViewById(com.amaze.filemanager.R.id.singleedittext_input);
break;
}
}
textfield.post(() -> {
if (!f.isDirectory()) {
textfield.setSelection(f.getNameString(getContext()).length());
}
});
}


public void computeScroll() {
android.view.View vi;
vi = listView.getChildAt(0);
int top;
top = (vi == null) ? 0 : vi.getTop();
int index;
if (mainFragmentViewModel.isList())
index = mLayoutManager.findFirstVisibleItemPosition();
else
index = mLayoutManagerGrid.findFirstVisibleItemPosition();

android.os.Bundle b;
b = new android.os.Bundle();
b.putInt("index", index);
b.putInt("top", top);
scrolls.put(mainFragmentViewModel.getCurrentPath(), b);
}


public void goBack() {
if ((mainFragmentViewModel.getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM) || (mainFragmentViewModel.getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN)) {
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);
return;
}
com.amaze.filemanager.filesystem.HybridFile currentFile;
currentFile = new com.amaze.filemanager.filesystem.HybridFile(mainFragmentViewModel.getOpenMode(), mainFragmentViewModel.getCurrentPath());
if (requireMainActivity().getListItemSelected()) {
adapter.toggleChecked(false);
} else if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB.equals(mainFragmentViewModel.getOpenMode())) {
if ((mainFragmentViewModel.getSmbPath() != null) && (!mainFragmentViewModel.getSmbPath().equals(mainFragmentViewModel.getCurrentPath()))) {
java.lang.StringBuilder path;
path = new java.lang.StringBuilder(currentFile.getSmbFile().getParent());
if ((mainFragmentViewModel.getCurrentPath() != null) && (mainFragmentViewModel.getCurrentPath().indexOf('?') > 0))
path.append(mainFragmentViewModel.getCurrentPath().substring(mainFragmentViewModel.getCurrentPath().indexOf('?')));

loadlist(path.toString().replace("%3D", "="), true, mainFragmentViewModel.getOpenMode(), false);
} else
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);

} else if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.SFTP.equals(mainFragmentViewModel.getOpenMode())) {
if (currentFile.getParent(requireContext()) == null) {
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);
} else if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE.equals(mainFragmentViewModel.getOpenMode())) {
loadlist(currentFile.getParent(getContext()), true, currentFile.getMode(), false);
} else {
java.lang.String parent;
parent = currentFile.getParent(getContext());
if (parent == null)
parent = mainFragmentViewModel.getHome();
// fall back by traversing back to home folder
// fall back by traversing back to home folder

loadlist(parent, true, mainFragmentViewModel.getOpenMode(), false);
}
} else if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.FTP.equals(mainFragmentViewModel.getOpenMode())) {
if (mainFragmentViewModel.getCurrentPath() != null) {
java.lang.String parent;
parent = currentFile.getParent(getContext());
// Hack.
if ((parent != null) && parent.contains("://")) {
loadlist(parent, true, mainFragmentViewModel.getOpenMode(), false);
} else {
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);
}
} else {
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);
}
} else if (("/".equals(mainFragmentViewModel.getCurrentPath()) || ((mainFragmentViewModel.getHome() != null) && mainFragmentViewModel.getHome().equals(mainFragmentViewModel.getCurrentPath()))) || mainFragmentViewModel.getIsOnCloudRoot()) {
getMainActivity().exit();
} else if (com.amaze.filemanager.fileoperations.filesystem.OpenMode.DOCUMENT_FILE.equals(mainFragmentViewModel.getOpenMode()) && (!currentFile.getPath().startsWith("content://"))) {
if (kotlin.collections.CollectionsKt.contains(com.amaze.filemanager.filesystem.FileProperties.ANDROID_DEVICE_DATA_DIRS, currentFile.getParent(getContext()))) {
loadlist(currentFile.getParent(getContext()), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.ANDROID_DATA, false);
} else {
loadlist(currentFile.getParent(getContext()), true, mainFragmentViewModel.getOpenMode(), false);
}
} else if (com.amaze.filemanager.filesystem.files.FileUtils.canGoBack(getContext(), currentFile)) {
loadlist(currentFile.getParent(getContext()), true, mainFragmentViewModel.getOpenMode(), false);
} else {
requireMainActivity().exit();
}
}


public void reauthenticateSmb() {
if (mainFragmentViewModel.getSmbPath() != null) {
try {
requireMainActivity().runOnUiThread(() -> {
int i;
com.amaze.filemanager.application.AppConfig.toast(requireContext(), getString(com.amaze.filemanager.R.string.unknown_error));
if ((i = com.amaze.filemanager.utils.DataUtils.getInstance().containsServer(mainFragmentViewModel.getSmbPath())) != (-1)) {
requireMainActivity().showSMBDialog(com.amaze.filemanager.utils.DataUtils.getInstance().getServers().get(i)[0], mainFragmentViewModel.getSmbPath(), true);
}
});
} catch (java.lang.Exception e) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("failure when reauthenticating smb connection", e);
}
}
}


public void goBackItemClick() {
if ((mainFragmentViewModel.getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.CUSTOM) || (mainFragmentViewModel.getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.TRASH_BIN)) {
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);
return;
}
com.amaze.filemanager.filesystem.HybridFile currentFile;
currentFile = new com.amaze.filemanager.filesystem.HybridFile(mainFragmentViewModel.getOpenMode(), mainFragmentViewModel.getCurrentPath());
if (requireMainActivity().getListItemSelected()) {
adapter.toggleChecked(false);
} else if (mainFragmentViewModel.getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB) {
if ((mainFragmentViewModel.getCurrentPath() != null) && (!mainFragmentViewModel.getCurrentPath().equals(mainFragmentViewModel.getSmbPath()))) {
java.lang.StringBuilder path;
path = new java.lang.StringBuilder(currentFile.getSmbFile().getParent());
if (mainFragmentViewModel.getCurrentPath().indexOf('?') > 0)
path.append(mainFragmentViewModel.getCurrentPath().substring(mainFragmentViewModel.getCurrentPath().indexOf('?')));

loadlist(path.toString(), true, com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB, false);
} else
loadlist(mainFragmentViewModel.getHome(), false, com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, false);

} else if ("/".equals(mainFragmentViewModel.getCurrentPath()) || mainFragmentViewModel.getIsOnCloudRoot()) {
requireMainActivity().exit();
} else if (com.amaze.filemanager.filesystem.files.FileUtils.canGoBack(getContext(), currentFile)) {
loadlist(currentFile.getParent(getContext()), true, mainFragmentViewModel.getOpenMode(), false);
} else
requireMainActivity().exit();

}


public void updateList(boolean forceReload) {
computeScroll();
loadlist(mainFragmentViewModel.getCurrentPath(), true, mainFragmentViewModel.getOpenMode(), forceReload);
}


@java.lang.Override
public void onResume() {
super.onResume();
switch(MUID_STATIC) {
// MainFragment_29_RandomActionIntentDefinitionOperatorMutator
case 29115: {
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
requireActivity().registerReceiver(receiver2, new android.content.IntentFilter(com.amaze.filemanager.ui.activities.MainActivity.KEY_INTENT_LOAD_LIST));
break;
}
}
resumeDecryptOperations();
startFileObserver();
}


@java.lang.Override
public void onPause() {
super.onPause();
requireActivity().unregisterReceiver(receiver2);
if (customFileObserver != null) {
customFileObserver.stopWatching();
}
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
requireActivity().unregisterReceiver(decryptReceiver);
}
}


public java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> addToSmb(@androidx.annotation.NonNull
jcifs.smb.SmbFile[] mFile, @androidx.annotation.NonNull
java.lang.String path, boolean showHiddenFiles) throws jcifs.smb.SmbException {
java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> smbFileList;
smbFileList = new java.util.ArrayList<>();
java.lang.String extraParams;
extraParams = android.net.Uri.parse(path).getQuery();
if (mainFragmentViewModel.getSearchHelper().size() > 500) {
mainFragmentViewModel.getSearchHelper().clear();
}
for (jcifs.smb.SmbFile aMFile : mFile) {
if ((com.amaze.filemanager.utils.DataUtils.getInstance().isFileHidden(aMFile.getPath()) || aMFile.isHidden()) && (!showHiddenFiles)) {
continue;
}
java.lang.String name;
name = aMFile.getName();
switch(MUID_STATIC) {
// MainFragment_30_BinaryMutator
case 30115: {
name = (aMFile.isDirectory() && name.endsWith("/")) ? name.substring(0, name.length() + 1) : name;
break;
}
default: {
name = (aMFile.isDirectory() && name.endsWith("/")) ? name.substring(0, name.length() - 1) : name;
break;
}
}
if (path.equals(mainFragmentViewModel.getSmbPath())) {
if (name.endsWith("$"))
continue;

}
if (aMFile.isDirectory()) {
switch(MUID_STATIC) {
// MainFragment_31_BinaryMutator
case 31115: {
mainFragmentViewModel.setFolderCount(mainFragmentViewModel.getFolderCount() - 1);
break;
}
default: {
mainFragmentViewModel.setFolderCount(mainFragmentViewModel.getFolderCount() + 1);
break;
}
}
android.net.Uri.Builder aMFilePathBuilder;
aMFilePathBuilder = android.net.Uri.parse(aMFile.getPath()).buildUpon();
if (!android.text.TextUtils.isEmpty(extraParams))
aMFilePathBuilder.query(extraParams);

com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElement;
layoutElement = new com.amaze.filemanager.adapters.data.LayoutElementParcelable(requireContext(), name, aMFilePathBuilder.build().toString(), "", "", "", 0, false, aMFile.lastModified() + "", true, getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB), com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB);
mainFragmentViewModel.getSearchHelper().add(layoutElement.generateBaseFile());
smbFileList.add(layoutElement);
} else {
switch(MUID_STATIC) {
// MainFragment_32_BinaryMutator
case 32115: {
mainFragmentViewModel.setFileCount(mainFragmentViewModel.getFileCount() - 1);
break;
}
default: {
mainFragmentViewModel.setFileCount(mainFragmentViewModel.getFileCount() + 1);
break;
}
}
com.amaze.filemanager.adapters.data.LayoutElementParcelable layoutElement;
layoutElement = new com.amaze.filemanager.adapters.data.LayoutElementParcelable(requireContext(), name, aMFile.getPath(), "", "", android.text.format.Formatter.formatFileSize(getContext(), aMFile.length()), aMFile.length(), false, aMFile.lastModified() + "", false, getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SHOW_THUMB), com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB);
layoutElement.setMode(com.amaze.filemanager.fileoperations.filesystem.OpenMode.SMB);
mainFragmentViewModel.getSearchHelper().add(layoutElement.generateBaseFile());
smbFileList.add(layoutElement);
}
}
return smbFileList;
}


@java.lang.Override
public void onDestroy() {
super.onDestroy();
// not guaranteed to be called unless we call #finish();
// please move code to onStop
}


public void hide(java.lang.String path) {
com.amaze.filemanager.utils.DataUtils.getInstance().addHiddenFile(path);
java.io.File file;
file = new java.io.File(path);
if (file.isDirectory()) {
java.io.File f1;
f1 = new java.io.File((path + "/") + ".nomedia");
if (!f1.exists()) {
try {
requireMainActivity().mainActivityHelper.mkFile(new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, path), new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, f1.getPath()), this);
} catch (java.lang.Exception e) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("failure when hiding file", e);
}
}
com.amaze.filemanager.filesystem.files.MediaConnectionUtils.scanFile(requireMainActivity(), new com.amaze.filemanager.filesystem.HybridFile[]{ new com.amaze.filemanager.filesystem.HybridFile(com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE, path) });
}
}


public void addShortcut(com.amaze.filemanager.adapters.data.LayoutElementParcelable path) {
// Adding shortcut for MainActivity
// on Home screen
final android.content.Context ctx;
ctx = requireContext();
if (!androidx.core.content.pm.ShortcutManagerCompat.isRequestPinShortcutSupported(requireContext())) {
android.widget.Toast.makeText(getActivity(), getString(com.amaze.filemanager.R.string.add_shortcut_not_supported_by_launcher), android.widget.Toast.LENGTH_SHORT).show();
return;
}
android.content.Intent shortcutIntent;
switch(MUID_STATIC) {
// MainFragment_33_NullIntentOperatorMutator
case 33115: {
shortcutIntent = null;
break;
}
// MainFragment_34_InvalidKeyIntentOperatorMutator
case 34115: {
shortcutIntent = new android.content.Intent((Context) null, com.amaze.filemanager.ui.activities.MainActivity.class);
break;
}
// MainFragment_35_RandomActionIntentDefinitionOperatorMutator
case 35115: {
shortcutIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
shortcutIntent = new android.content.Intent(ctx, com.amaze.filemanager.ui.activities.MainActivity.class);
break;
}
}
switch(MUID_STATIC) {
// MainFragment_36_NullValueIntentPutExtraOperatorMutator
case 36115: {
shortcutIntent.putExtra("path", new Parcelable[0]);
break;
}
// MainFragment_37_IntentPayloadReplacementOperatorMutator
case 37115: {
shortcutIntent.putExtra("path", "");
break;
}
default: {
switch(MUID_STATIC) {
// MainFragment_38_RandomActionIntentDefinitionOperatorMutator
case 38115: {
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
shortcutIntent.putExtra("path", path.desc);
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// MainFragment_39_RandomActionIntentDefinitionOperatorMutator
case 39115: {
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
shortcutIntent.setAction(android.content.Intent.ACTION_MAIN);
break;
}
}
switch(MUID_STATIC) {
// MainFragment_40_RandomActionIntentDefinitionOperatorMutator
case 40115: {
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
shortcutIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
break;
}
}
// Using file path as shortcut id.
androidx.core.content.pm.ShortcutInfoCompat info;
info = new androidx.core.content.pm.ShortcutInfoCompat.Builder(ctx, path.desc).setActivity(requireMainActivity().getComponentName()).setIcon(androidx.core.graphics.drawable.IconCompat.createWithResource(ctx, com.amaze.filemanager.R.mipmap.ic_launcher)).setIntent(shortcutIntent).setLongLabel(path.desc).setShortLabel(new java.io.File(path.desc).getName()).build();
androidx.core.content.pm.ShortcutManagerCompat.requestPinShortcut(ctx, info, null);
}


@java.lang.Override
public void onDetach() {
super.onDetach();
}


@androidx.annotation.Nullable
public com.amaze.filemanager.ui.activities.MainActivity getMainActivity() {
return ((com.amaze.filemanager.ui.activities.MainActivity) (getActivity()));
}


@androidx.annotation.NonNull
public com.amaze.filemanager.ui.activities.MainActivity requireMainActivity() {
return ((com.amaze.filemanager.ui.activities.MainActivity) (requireActivity()));
}


@androidx.annotation.Nullable
public java.util.List<com.amaze.filemanager.adapters.data.LayoutElementParcelable> getElementsList() {
return mainFragmentViewModel.getListElements();
}


public void initTopAndEmptyAreaDragListeners(boolean destroy) {
if (destroy) {
mToolbarContainer.setOnDragListener(null);
listView.stopScroll();
listView.setOnDragListener(null);
nofilesview.setOnDragListener(null);
} else {
mToolbarContainer.setOnDragListener(new com.amaze.filemanager.ui.drag.TabFragmentBottomDragListener(() -> {
smoothScrollListView(true);
return null;
}, () -> {
stopSmoothScrollListView();
return null;
}));
listView.setOnDragListener(new com.amaze.filemanager.ui.drag.RecyclerAdapterDragListener(adapter, null, mainFragmentViewModel.getDragAndDropPreference(), this));
nofilesview.setOnDragListener(new com.amaze.filemanager.ui.drag.RecyclerAdapterDragListener(adapter, null, mainFragmentViewModel.getDragAndDropPreference(), this));
}
}


public void smoothScrollListView(boolean upDirection) {
if (listView != null) {
if (upDirection) {
listView.smoothScrollToPosition(0);
} else {
listView.smoothScrollToPosition(mainFragmentViewModel.getAdapterListItems().size());
}
}
}


public void stopSmoothScrollListView() {
if (listView != null) {
listView.stopScroll();
}
}


@androidx.annotation.Nullable
public java.lang.String getCurrentPath() {
if (mainFragmentViewModel == null) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Viewmodel not available to get current path");
return null;
}
return mainFragmentViewModel.getCurrentPath();
}


@java.lang.Override
public void changePath(@androidx.annotation.NonNull
java.lang.String path) {
loadlist(path, false, mainFragmentViewModel.getOpenMode(), false);
}


@java.lang.Override
public java.lang.String getPath() {
return getCurrentPath();
}


@java.lang.Override
public int getRootDrawable() {
return com.amaze.filemanager.R.drawable.ic_root_white_24px;
}


private boolean getBoolean(java.lang.String key) {
return requireMainActivity().getBoolean(key);
}


@java.lang.Override
public void onGlobalLayout() {
if (mainFragmentViewModel.getColumns() == null) {
int screenWidth;
screenWidth = listView.getWidth();
int dpToPx;
dpToPx = com.amaze.filemanager.utils.Utils.dpToPx(requireContext(), 115);
if (dpToPx == 0) {
// HACK to fix a crash see #3249
dpToPx = 1;
}
switch(MUID_STATIC) {
// MainFragment_41_BinaryMutator
case 41115: {
mainFragmentViewModel.setColumns(screenWidth * dpToPx);
break;
}
default: {
mainFragmentViewModel.setColumns(screenWidth / dpToPx);
break;
}
}
if (!mainFragmentViewModel.isList()) {
mLayoutManagerGrid.setSpanCount(mainFragmentViewModel.getColumns());
}
}
// TODO: This trigger causes to lose selected items in case of grid view,
// but is necessary to adjust columns for grid view when screen is rotated
/* if (!mainFragmentViewModel.isList()) {
loadViews();
}
 */
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
mToolbarContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
} else {
mToolbarContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
}
}


@androidx.annotation.Nullable
public com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel getMainFragmentViewModel() {
if (isAdded()) {
if (mainFragmentViewModel == null) {
mainFragmentViewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.amaze.filemanager.ui.fragments.data.MainFragmentViewModel.class);
}
return mainFragmentViewModel;
} else {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.error("Failed to get viewmodel, fragment not yet added");
return null;
}
}


@androidx.annotation.Nullable
public com.amaze.filemanager.ui.activities.MainActivityViewModel getMainActivityViewModel() {
if (isAdded()) {
if (mainActivityViewModel == null) {
mainActivityViewModel = new androidx.lifecycle.ViewModelProvider(requireMainActivity()).get(com.amaze.filemanager.ui.activities.MainActivityViewModel.class);
}
return mainActivityViewModel;
} else {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.error("Failed to get viewmodel, fragment not yet added");
return null;
}
}


@java.lang.Override
public void adjustListViewForTv(@androidx.annotation.NonNull
com.amaze.filemanager.adapters.holders.ItemViewHolder viewHolder, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity mainActivity) {
switch(MUID_STATIC) {
// MainFragment_42_BinaryMutator
case 42115: {
try {
int[] location;
location = new int[2];
viewHolder.baseItemView.getLocationOnScreen(location);
com.amaze.filemanager.ui.fragments.MainFragment.LOG.info((("Current x and y " + location[0]) + " ") + location[1]);
if (location[1] < requireMainActivity().getAppbar().getAppbarLayout().getHeight()) {
listView.scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() + 5, 0));
} else if ((location[1] + viewHolder.baseItemView.getHeight()) > requireContext().getResources().getDisplayMetrics().heightPixels) {
listView.scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() - 1));
}
} catch (java.lang.IndexOutOfBoundsException e) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Failed to adjust scrollview for tv", e);
}
break;
}
default: {
switch(MUID_STATIC) {
// MainFragment_43_BinaryMutator
case 43115: {
try {
int[] location;
location = new int[2];
viewHolder.baseItemView.getLocationOnScreen(location);
com.amaze.filemanager.ui.fragments.MainFragment.LOG.info((("Current x and y " + location[0]) + " ") + location[1]);
if (location[1] < requireMainActivity().getAppbar().getAppbarLayout().getHeight()) {
listView.scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
} else if ((location[1] - viewHolder.baseItemView.getHeight()) > requireContext().getResources().getDisplayMetrics().heightPixels) {
listView.scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() - 1));
}
} catch (java.lang.IndexOutOfBoundsException e) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Failed to adjust scrollview for tv", e);
}
break;
}
default: {
switch(MUID_STATIC) {
// MainFragment_44_BinaryMutator
case 44115: {
try {
int[] location;
location = new int[2];
viewHolder.baseItemView.getLocationOnScreen(location);
com.amaze.filemanager.ui.fragments.MainFragment.LOG.info((("Current x and y " + location[0]) + " ") + location[1]);
if (location[1] < requireMainActivity().getAppbar().getAppbarLayout().getHeight()) {
listView.scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
} else if ((location[1] + viewHolder.baseItemView.getHeight()) > requireContext().getResources().getDisplayMetrics().heightPixels) {
listView.scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() - 5, adapter.getItemCount() - 1));
}
} catch (java.lang.IndexOutOfBoundsException e) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Failed to adjust scrollview for tv", e);
}
break;
}
default: {
switch(MUID_STATIC) {
// MainFragment_45_BinaryMutator
case 45115: {
try {
int[] location;
location = new int[2];
viewHolder.baseItemView.getLocationOnScreen(location);
com.amaze.filemanager.ui.fragments.MainFragment.LOG.info((("Current x and y " + location[0]) + " ") + location[1]);
if (location[1] < requireMainActivity().getAppbar().getAppbarLayout().getHeight()) {
listView.scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
} else if ((location[1] + viewHolder.baseItemView.getHeight()) > requireContext().getResources().getDisplayMetrics().heightPixels) {
listView.scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() + 1));
}
} catch (java.lang.IndexOutOfBoundsException e) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Failed to adjust scrollview for tv", e);
}
break;
}
default: {
try {
int[] location;
location = new int[2];
viewHolder.baseItemView.getLocationOnScreen(location);
com.amaze.filemanager.ui.fragments.MainFragment.LOG.info((("Current x and y " + location[0]) + " ") + location[1]);
if (location[1] < requireMainActivity().getAppbar().getAppbarLayout().getHeight()) {
listView.scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
} else if ((location[1] + viewHolder.baseItemView.getHeight()) > requireContext().getResources().getDisplayMetrics().heightPixels) {
listView.scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() - 1));
}
} catch (java.lang.IndexOutOfBoundsException e) {
com.amaze.filemanager.ui.fragments.MainFragment.LOG.warn("Failed to adjust scrollview for tv", e);
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
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
