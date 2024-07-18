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
import com.amaze.filemanager.adapters.data.AppDataParcelable;
import java.util.ArrayList;
import com.amaze.filemanager.adapters.holders.AppHolder;
import com.amaze.filemanager.ui.activities.MainActivity;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import com.amaze.filemanager.ui.views.FastScroller;
import androidx.fragment.app.Fragment;
import com.amaze.filemanager.adapters.glide.AppsAdapterPreloadModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.loader.content.Loader;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_APPLIST_SORTBY;
import java.lang.ref.WeakReference;
import com.amaze.filemanager.R;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_APPLIST_ISASCENDING;
import androidx.annotation.NonNull;
import java.util.List;
import com.amaze.filemanager.ui.theme.AppTheme;
import java.util.Collections;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.MenuItem;
import com.amaze.filemanager.GlideApp;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.asynchronous.loaders.AppListLoader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import android.view.View;
import com.amaze.filemanager.adapters.AppsRecyclerAdapter;
import androidx.loader.app.LoaderManager;
import com.amaze.filemanager.ui.provider.UtilitiesProvider;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.utils.GlideConstants;
import android.graphics.drawable.ColorDrawable;
import java.util.Objects;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AppsListFragment extends androidx.fragment.app.Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable>> , com.amaze.filemanager.ui.fragments.AdjustListViewForTv<com.amaze.filemanager.adapters.holders.AppHolder> {
    static final int MUID_STATIC = getMUID();
    public static final int ID_LOADER_APP_LIST = 0;

    private com.amaze.filemanager.adapters.AppsRecyclerAdapter adapter;

    private android.content.SharedPreferences sharedPreferences;

    private boolean isAscending;

    private int sortby;

    private android.view.View rootView;

    private com.amaze.filemanager.adapters.glide.AppsAdapterPreloadModel modelProvider;

    private androidx.recyclerview.widget.LinearLayoutManager linearLayoutManager;

    private com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<java.lang.String> preloader;

    private java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> appDataParcelableList;

    private com.amaze.filemanager.ui.views.FastScroller fastScroller;

    private boolean showSystemApps = false;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AppsListFragment_0_LengthyGUICreationOperatorMutator
            case 114: {
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
    setHasOptionsMenu(true);
}


@androidx.annotation.Nullable
@java.lang.Override
public android.view.View onCreateView(@androidx.annotation.NonNull
android.view.LayoutInflater inflater, @androidx.annotation.Nullable
android.view.ViewGroup container, @androidx.annotation.Nullable
android.os.Bundle savedInstanceState) {
    rootView = inflater.inflate(com.amaze.filemanager.R.layout.fragment_app_list, container, false);
    return rootView;
}


@java.lang.Override
public void onViewCreated(@androidx.annotation.NonNull
android.view.View view, android.os.Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    final com.amaze.filemanager.ui.activities.MainActivity mainActivity;
    mainActivity = ((com.amaze.filemanager.ui.activities.MainActivity) (getActivity()));
    java.util.Objects.requireNonNull(mainActivity);
    com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider;
    utilsProvider = mainActivity.getUtilsProvider();
    modelProvider = new com.amaze.filemanager.adapters.glide.AppsAdapterPreloadModel(this, false);
    com.bumptech.glide.util.ViewPreloadSizeProvider<java.lang.String> sizeProvider;
    sizeProvider = new com.bumptech.glide.util.ViewPreloadSizeProvider<>();
    preloader = new com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader<>(com.amaze.filemanager.GlideApp.with(this), modelProvider, sizeProvider, com.amaze.filemanager.utils.GlideConstants.MAX_PRELOAD_APPSADAPTER);
    linearLayoutManager = new androidx.recyclerview.widget.LinearLayoutManager(getContext());
    updateViews(mainActivity, utilsProvider);
    sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getActivity());
    isAscending = sharedPreferences.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_APPLIST_ISASCENDING, true);
    sortby = sharedPreferences.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_APPLIST_SORTBY, 0);
    switch(MUID_STATIC) {
        // AppsListFragment_1_InvalidViewFocusOperatorMutator
        case 1114: {
            /**
            * Inserted by Kadabra
            */
            fastScroller = rootView.findViewById(com.amaze.filemanager.R.id.fastscroll);
            fastScroller.requestFocus();
            break;
        }
        // AppsListFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2114: {
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
fastScroller.setPressedHandleColor(mainActivity.getAccent());
fastScroller.setRecyclerView(getRecyclerView(), 1);
mainActivity.getAppbar().getAppbarLayout().addOnOffsetChangedListener((com.google.android.material.appbar.AppBarLayout appBarLayout,int verticalOffset) -> {
    fastScroller.updateHandlePosition(verticalOffset, 112);
});
androidx.loader.app.LoaderManager.getInstance(this).initLoader(com.amaze.filemanager.ui.fragments.AppsListFragment.ID_LOADER_APP_LIST, null, this);
}


@java.lang.Override
public void onCreateOptionsMenu(@androidx.annotation.NonNull
android.view.Menu menu, @androidx.annotation.NonNull
android.view.MenuInflater inflater) {
requireActivity().getMenuInflater().inflate(com.amaze.filemanager.R.menu.app_menu, menu);
menu.findItem(com.amaze.filemanager.R.id.checkbox_system_apps).setChecked(false);
super.onCreateOptionsMenu(menu, inflater);
}


@java.lang.Override
public boolean onOptionsItemSelected(@androidx.annotation.NonNull
android.view.MenuItem item) {
switch (item.getItemId()) {
    case com.amaze.filemanager.R.id.sort :
        showSortDialog(((com.amaze.filemanager.ui.activities.MainActivity) (requireActivity())).getAppTheme());
        return true;
    case com.amaze.filemanager.R.id.exit :
        requireActivity().finish();
        return true;
    case com.amaze.filemanager.R.id.checkbox_system_apps :
        item.setChecked(!item.isChecked());
        adapter.setData(appDataParcelableList, item.isChecked());
        showSystemApps = item.isChecked();
        return true;
    default :
        return super.onOptionsItemSelected(item);
}
}


private void updateViews(com.amaze.filemanager.ui.activities.MainActivity mainActivity, com.amaze.filemanager.ui.provider.UtilitiesProvider utilsProvider) {
mainActivity.getAppbar().setTitle(com.amaze.filemanager.R.string.apps);
mainActivity.hideFab();
mainActivity.getAppbar().getBottomBar().setVisibility(android.view.View.GONE);
mainActivity.supportInvalidateOptionsMenu();
if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.DARK)) {
    getActivity().getWindow().getDecorView().setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), com.amaze.filemanager.R.color.holo_dark_background));
} else if (utilsProvider.getAppTheme().equals(com.amaze.filemanager.ui.theme.AppTheme.BLACK)) {
    getActivity().getWindow().getDecorView().setBackgroundColor(com.amaze.filemanager.utils.Utils.getColor(getContext(), android.R.color.black));
}
int skin_color;
skin_color = mainActivity.getCurrentColorPreference().getPrimaryFirstTab();
int skinTwoColor;
skinTwoColor = mainActivity.getCurrentColorPreference().getPrimarySecondTab();
mainActivity.updateViews(new android.graphics.drawable.ColorDrawable(com.amaze.filemanager.ui.activities.MainActivity.currentTab == 1 ? skinTwoColor : skin_color));
getRecyclerView().addOnScrollListener(preloader);
getRecyclerView().setLayoutManager(linearLayoutManager);
}


public void showSortDialog(com.amaze.filemanager.ui.theme.AppTheme appTheme) {
final com.amaze.filemanager.ui.activities.MainActivity mainActivity;
mainActivity = ((com.amaze.filemanager.ui.activities.MainActivity) (getActivity()));
if (mainActivity == null) {
    return;
}
java.lang.ref.WeakReference<com.amaze.filemanager.ui.fragments.AppsListFragment> appsListFragment;
appsListFragment = new java.lang.ref.WeakReference<>(this);
int accentColor;
accentColor = mainActivity.getAccent();
java.lang.String[] sort;
sort = getResources().getStringArray(com.amaze.filemanager.R.array.sortbyApps);
com.afollestad.materialdialogs.MaterialDialog.Builder builder;
builder = new com.afollestad.materialdialogs.MaterialDialog.Builder(mainActivity).theme(appTheme.getMaterialDialogTheme()).items(sort).itemsCallbackSingleChoice(sortby, (com.afollestad.materialdialogs.MaterialDialog dialog,android.view.View view,int which,java.lang.CharSequence text) -> true).negativeText(com.amaze.filemanager.R.string.ascending).positiveColor(accentColor).positiveText(com.amaze.filemanager.R.string.descending).negativeColor(accentColor).onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
    final com.amaze.filemanager.ui.fragments.AppsListFragment $this;
    $this = appsListFragment.get();
    if ($this == null) {
        return;
    }
    $this.saveAndReload(dialog.getSelectedIndex(), true);
    dialog.dismiss();
}).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> {
    final com.amaze.filemanager.ui.fragments.AppsListFragment $this;
    $this = appsListFragment.get();
    if ($this == null) {
        return;
    }
    $this.saveAndReload(dialog.getSelectedIndex(), false);
    dialog.dismiss();
}).title(com.amaze.filemanager.R.string.sort_by);
builder.build().show();
}


private void saveAndReload(int newSortby, boolean newIsAscending) {
sortby = newSortby;
isAscending = newIsAscending;
sharedPreferences.edit().putBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_APPLIST_ISASCENDING, newIsAscending).putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_APPLIST_SORTBY, newSortby).apply();
androidx.loader.app.LoaderManager.getInstance(this).restartLoader(com.amaze.filemanager.ui.fragments.AppsListFragment.ID_LOADER_APP_LIST, null, this);
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.loader.content.Loader<java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable>> onCreateLoader(int id, android.os.Bundle args) {
return new com.amaze.filemanager.asynchronous.loaders.AppListLoader(getContext(), sortby, isAscending);
}


@java.lang.Override
public void onLoadFinished(@androidx.annotation.NonNull
androidx.loader.content.Loader<java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable>> loader, java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> data) {
getSpinner().setVisibility(android.view.View.GONE);
if (data.isEmpty()) {
    getRecyclerView().setVisibility(android.view.View.GONE);
    rootView.findViewById(com.amaze.filemanager.R.id.empty_text_view).setVisibility(android.view.View.VISIBLE);
} else {
    appDataParcelableList = new java.util.ArrayList<>(data);
    java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable> adapterList;
    adapterList = new java.util.ArrayList<>();
    for (com.amaze.filemanager.adapters.data.AppDataParcelable appDataParcelable : data) {
        if ((!showSystemApps) && appDataParcelable.isSystemApp()) {
            continue;
        }
        adapterList.add(appDataParcelable);
    }
    adapter = new com.amaze.filemanager.adapters.AppsRecyclerAdapter(this, modelProvider, false, this, adapterList);
    getRecyclerView().setVisibility(android.view.View.VISIBLE);
    getRecyclerView().setAdapter(adapter);
}
}


@java.lang.Override
public void onLoaderReset(@androidx.annotation.NonNull
androidx.loader.content.Loader<java.util.List<com.amaze.filemanager.adapters.data.AppDataParcelable>> loader) {
adapter.setData(java.util.Collections.emptyList(), true);
}


@java.lang.Override
public void adjustListViewForTv(@androidx.annotation.NonNull
com.amaze.filemanager.adapters.holders.AppHolder viewHolder, @androidx.annotation.NonNull
com.amaze.filemanager.ui.activities.MainActivity mainActivity) {
switch(MUID_STATIC) {
    // AppsListFragment_3_BinaryMutator
    case 3114: {
        try {
            int[] location;
            location = new int[2];
            viewHolder.rl.getLocationOnScreen(location);
            android.util.Log.i(getClass().getSimpleName(), (("Current x and y " + location[0]) + " ") + location[1]);
            if (location[1] < mainActivity.getAppbar().getAppbarLayout().getHeight()) {
                getRecyclerView().scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() + 5, 0));
            } else if ((location[1] + viewHolder.rl.getHeight()) >= getContext().getResources().getDisplayMetrics().heightPixels) {
                getRecyclerView().scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() - 1));
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            android.util.Log.w(getClass().getSimpleName(), "Failed to adjust scrollview for tv", e);
        }
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // AppsListFragment_4_BinaryMutator
        case 4114: {
            try {
                int[] location;
                location = new int[2];
                viewHolder.rl.getLocationOnScreen(location);
                android.util.Log.i(getClass().getSimpleName(), (("Current x and y " + location[0]) + " ") + location[1]);
                if (location[1] < mainActivity.getAppbar().getAppbarLayout().getHeight()) {
                    getRecyclerView().scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
                } else if ((location[1] - viewHolder.rl.getHeight()) >= getContext().getResources().getDisplayMetrics().heightPixels) {
                    getRecyclerView().scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() - 1));
                }
            } catch (java.lang.IndexOutOfBoundsException e) {
                android.util.Log.w(getClass().getSimpleName(), "Failed to adjust scrollview for tv", e);
            }
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // AppsListFragment_5_BinaryMutator
            case 5114: {
                try {
                    int[] location;
                    location = new int[2];
                    viewHolder.rl.getLocationOnScreen(location);
                    android.util.Log.i(getClass().getSimpleName(), (("Current x and y " + location[0]) + " ") + location[1]);
                    if (location[1] < mainActivity.getAppbar().getAppbarLayout().getHeight()) {
                        getRecyclerView().scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
                    } else if ((location[1] + viewHolder.rl.getHeight()) >= getContext().getResources().getDisplayMetrics().heightPixels) {
                        getRecyclerView().scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() - 5, adapter.getItemCount() - 1));
                    }
                } catch (java.lang.IndexOutOfBoundsException e) {
                    android.util.Log.w(getClass().getSimpleName(), "Failed to adjust scrollview for tv", e);
                }
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // AppsListFragment_6_BinaryMutator
                case 6114: {
                    try {
                        int[] location;
                        location = new int[2];
                        viewHolder.rl.getLocationOnScreen(location);
                        android.util.Log.i(getClass().getSimpleName(), (("Current x and y " + location[0]) + " ") + location[1]);
                        if (location[1] < mainActivity.getAppbar().getAppbarLayout().getHeight()) {
                            getRecyclerView().scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
                        } else if ((location[1] + viewHolder.rl.getHeight()) >= getContext().getResources().getDisplayMetrics().heightPixels) {
                            getRecyclerView().scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() + 1));
                        }
                    } catch (java.lang.IndexOutOfBoundsException e) {
                        android.util.Log.w(getClass().getSimpleName(), "Failed to adjust scrollview for tv", e);
                    }
                    break;
                }
                default: {
                try {
                    int[] location;
                    location = new int[2];
                    viewHolder.rl.getLocationOnScreen(location);
                    android.util.Log.i(getClass().getSimpleName(), (("Current x and y " + location[0]) + " ") + location[1]);
                    if (location[1] < mainActivity.getAppbar().getAppbarLayout().getHeight()) {
                        getRecyclerView().scrollToPosition(java.lang.Math.max(viewHolder.getAdapterPosition() - 5, 0));
                    } else if ((location[1] + viewHolder.rl.getHeight()) >= getContext().getResources().getDisplayMetrics().heightPixels) {
                        getRecyclerView().scrollToPosition(java.lang.Math.min(viewHolder.getAdapterPosition() + 5, adapter.getItemCount() - 1));
                    }
                } catch (java.lang.IndexOutOfBoundsException e) {
                    android.util.Log.w(getClass().getSimpleName(), "Failed to adjust scrollview for tv", e);
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


private androidx.recyclerview.widget.RecyclerView getRecyclerView() {
return rootView.findViewById(com.amaze.filemanager.R.id.list_view);
}


private me.zhanghai.android.materialprogressbar.MaterialProgressBar getSpinner() {
return rootView.findViewById(com.amaze.filemanager.R.id.loading_spinner);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
