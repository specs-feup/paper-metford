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
import android.animation.ArgbEvaluator;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.amaze.filemanager.ui.dialogs.GeneralDialogCreation;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CURRENT_TAB;
import java.util.ArrayList;
import android.view.animation.DecelerateInterpolator;
import com.amaze.filemanager.ui.activities.MainActivity;
import com.amaze.filemanager.ui.drag.DragToTrashListener;
import androidx.fragment.app.Fragment;
import org.slf4j.Logger;
import androidx.fragment.app.FragmentActivity;
import com.amaze.filemanager.ui.drag.TabFragmentSideDragListener;
import androidx.viewpager2.widget.ViewPager2;
import com.amaze.filemanager.R;
import android.view.HapticFeedbackConstants;
import androidx.annotation.NonNull;
import android.os.Build;
import static com.amaze.filemanager.utils.PreferenceUtils.DEFAULT_CURRENT_TAB;
import java.util.List;
import org.slf4j.LoggerFactory;
import android.graphics.Color;
import static com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SAVED_PATHS;
import android.content.SharedPreferences;
import com.amaze.filemanager.application.AppConfig;
import android.os.Bundle;
import android.view.ViewGroup;
import com.amaze.filemanager.database.TabHandler;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import static com.amaze.filemanager.utils.PreferenceUtils.DEFAULT_SAVED_PATHS;
import com.amaze.filemanager.ui.colors.UserColorPreferences;
import androidx.preference.PreferenceManager;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import com.amaze.filemanager.ui.ColorCircleDrawable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.amaze.filemanager.ui.views.Indicator;
import androidx.annotation.ColorInt;
import androidx.fragment.app.FragmentManager;
import com.amaze.filemanager.utils.DataUtils;
import android.view.LayoutInflater;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.database.models.explorer.Tab;
import android.graphics.drawable.ColorDrawable;
import com.amaze.filemanager.ui.ExtensionsKt;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class TabFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.ui.fragments.TabFragment.class);

    private static final java.lang.String KEY_PATH = "path";

    private static final java.lang.String KEY_POSITION = "pos";

    private static final java.lang.String KEY_FRAGMENT_0 = "tab0";

    private static final java.lang.String KEY_FRAGMENT_1 = "tab1";

    private boolean savePaths;

    private androidx.fragment.app.FragmentManager fragmentManager;

    private final java.util.List<androidx.fragment.app.Fragment> fragments = new java.util.ArrayList<>();

    private com.amaze.filemanager.ui.fragments.TabFragment.ScreenSlidePagerAdapter sectionsPagerAdapter;

    private androidx.viewpager2.widget.ViewPager2 viewPager;

    private android.content.SharedPreferences sharedPrefs;

    private java.lang.String path;

    /**
     * ink indicators for viewpager only for Lollipop+
     */
    private com.amaze.filemanager.ui.views.Indicator indicator;

    private androidx.appcompat.widget.AppCompatImageView circleDrawable1;

    /**
     * views for circlular drawables below android lollipop
     */
    private androidx.appcompat.widget.AppCompatImageView circleDrawable2;

    /**
     * color drawable for action bar background
     */
    private final android.graphics.drawable.ColorDrawable colorDrawable = new android.graphics.drawable.ColorDrawable();

    @androidx.annotation.ColorInt
    private int startColor;

    /**
     * colors relative to current visible tab
     */
    @androidx.annotation.ColorInt
    private int endColor;

    private android.view.ViewGroup rootView;

    private final android.animation.ArgbEvaluator evaluator = new android.animation.ArgbEvaluator();

    private androidx.constraintlayout.widget.ConstraintLayout dragPlaceholder;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        rootView = ((android.view.ViewGroup) (inflater.inflate(com.amaze.filemanager.R.layout.tabfragment, container, false)));
        fragmentManager = requireActivity().getSupportFragmentManager();
        switch(MUID_STATIC) {
            // TabFragment_0_InvalidViewFocusOperatorMutator
            case 113: {
                /**
                * Inserted by Kadabra
                */
                dragPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.drag_placeholder);
                dragPlaceholder.requestFocus();
                break;
            }
            // TabFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1113: {
                /**
                * Inserted by Kadabra
                */
                dragPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.drag_placeholder);
                dragPlaceholder.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            dragPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.drag_placeholder);
            break;
        }
    }
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        switch(MUID_STATIC) {
            // TabFragment_2_InvalidViewFocusOperatorMutator
            case 2113: {
                /**
                * Inserted by Kadabra
                */
                indicator = requireActivity().findViewById(com.amaze.filemanager.R.id.indicator);
                indicator.requestFocus();
                break;
            }
            // TabFragment_3_ViewComponentNotVisibleOperatorMutator
            case 3113: {
                /**
                * Inserted by Kadabra
                */
                indicator = requireActivity().findViewById(com.amaze.filemanager.R.id.indicator);
                indicator.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            indicator = requireActivity().findViewById(com.amaze.filemanager.R.id.indicator);
            break;
        }
    }
} else {
    switch(MUID_STATIC) {
        // TabFragment_4_InvalidViewFocusOperatorMutator
        case 4113: {
            /**
            * Inserted by Kadabra
            */
            circleDrawable1 = requireActivity().findViewById(com.amaze.filemanager.R.id.tab_indicator1);
            circleDrawable1.requestFocus();
            break;
        }
        // TabFragment_5_ViewComponentNotVisibleOperatorMutator
        case 5113: {
            /**
            * Inserted by Kadabra
            */
            circleDrawable1 = requireActivity().findViewById(com.amaze.filemanager.R.id.tab_indicator1);
            circleDrawable1.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        circleDrawable1 = requireActivity().findViewById(com.amaze.filemanager.R.id.tab_indicator1);
        break;
    }
}
switch(MUID_STATIC) {
    // TabFragment_6_InvalidViewFocusOperatorMutator
    case 6113: {
        /**
        * Inserted by Kadabra
        */
        circleDrawable2 = requireActivity().findViewById(com.amaze.filemanager.R.id.tab_indicator2);
        circleDrawable2.requestFocus();
        break;
    }
    // TabFragment_7_ViewComponentNotVisibleOperatorMutator
    case 7113: {
        /**
        * Inserted by Kadabra
        */
        circleDrawable2 = requireActivity().findViewById(com.amaze.filemanager.R.id.tab_indicator2);
        circleDrawable2.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    circleDrawable2 = requireActivity().findViewById(com.amaze.filemanager.R.id.tab_indicator2);
    break;
}
}
}
sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireActivity());
savePaths = sharedPrefs.getBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_SAVED_PATHS, com.amaze.filemanager.utils.PreferenceUtils.DEFAULT_SAVED_PATHS);
switch(MUID_STATIC) {
// TabFragment_8_InvalidViewFocusOperatorMutator
case 8113: {
/**
* Inserted by Kadabra
*/
viewPager = rootView.findViewById(com.amaze.filemanager.R.id.pager);
viewPager.requestFocus();
break;
}
// TabFragment_9_ViewComponentNotVisibleOperatorMutator
case 9113: {
/**
* Inserted by Kadabra
*/
viewPager = rootView.findViewById(com.amaze.filemanager.R.id.pager);
viewPager.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
viewPager = rootView.findViewById(com.amaze.filemanager.R.id.pager);
break;
}
}
if (getArguments() != null) {
path = getArguments().getString(com.amaze.filemanager.ui.fragments.TabFragment.KEY_PATH);
}
requireMainActivity().supportInvalidateOptionsMenu();
viewPager.registerOnPageChangeCallback(new com.amaze.filemanager.ui.fragments.TabFragment.OnPageChangeCallbackImpl());
sectionsPagerAdapter = new com.amaze.filemanager.ui.fragments.TabFragment.ScreenSlidePagerAdapter(requireActivity());
if (savedInstanceState == null) {
int lastOpenTab;
lastOpenTab = sharedPrefs.getInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CURRENT_TAB, com.amaze.filemanager.utils.PreferenceUtils.DEFAULT_CURRENT_TAB);
com.amaze.filemanager.ui.activities.MainActivity.currentTab = lastOpenTab;
refactorDrawerStorages(true);
viewPager.setAdapter(sectionsPagerAdapter);
try {
viewPager.setCurrentItem(lastOpenTab, true);
if ((circleDrawable1 != null) && (circleDrawable2 != null)) {
updateIndicator(viewPager.getCurrentItem());
}
} catch (java.lang.Exception e) {
LOG.warn("failed to set current viewpager item", e);
}
} else {
fragments.clear();
try {
fragments.add(0, fragmentManager.getFragment(savedInstanceState, com.amaze.filemanager.ui.fragments.TabFragment.KEY_FRAGMENT_0));
fragments.add(1, fragmentManager.getFragment(savedInstanceState, com.amaze.filemanager.ui.fragments.TabFragment.KEY_FRAGMENT_1));
} catch (java.lang.Exception e) {
LOG.warn("failed to clear fragments", e);
}
sectionsPagerAdapter = new com.amaze.filemanager.ui.fragments.TabFragment.ScreenSlidePagerAdapter(requireActivity());
viewPager.setAdapter(sectionsPagerAdapter);
int pos1;
pos1 = savedInstanceState.getInt(com.amaze.filemanager.ui.fragments.TabFragment.KEY_POSITION, 0);
com.amaze.filemanager.ui.activities.MainActivity.currentTab = pos1;
viewPager.setCurrentItem(pos1);
sectionsPagerAdapter.notifyDataSetChanged();
}
if (indicator != null)
indicator.setViewPager(viewPager);

com.amaze.filemanager.ui.colors.UserColorPreferences userColorPreferences;
userColorPreferences = requireMainActivity().getCurrentColorPreference();
// color of viewpager when current tab is 0
startColor = userColorPreferences.getPrimaryFirstTab();
// color of viewpager when current tab is 1
endColor = userColorPreferences.getPrimarySecondTab();
/* TODO
//update the views as there is any change in {@link MainActivity#currentTab}
//probably due to config change
colorDrawable.setColor(Color.parseColor(MainActivity.currentTab==1 ?
ThemedActivity.skinTwo : ThemedActivity.skin));
mainActivity.updateViews(colorDrawable);
 */
return rootView;
}


@java.lang.Override
public void onDestroyView() {
indicator = null// Free the strong reference
;// Free the strong reference

sharedPrefs.edit().putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CURRENT_TAB, com.amaze.filemanager.ui.activities.MainActivity.currentTab).apply();
super.onDestroyView();
}


public void updatePaths(int pos) {
// Getting old path from database before clearing
com.amaze.filemanager.database.TabHandler tabHandler;
tabHandler = com.amaze.filemanager.database.TabHandler.getInstance();
int i;
i = 1;
for (androidx.fragment.app.Fragment fragment : fragments) {
if (fragment instanceof com.amaze.filemanager.ui.fragments.MainFragment) {
com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
mainFragment = ((com.amaze.filemanager.ui.fragments.MainFragment) (fragment));
switch(MUID_STATIC) {
// TabFragment_10_BinaryMutator
case 10113: {
    if (((mainFragment.getMainFragmentViewModel() != null) && ((i + 1) == com.amaze.filemanager.ui.activities.MainActivity.currentTab)) && (i == pos)) {
        updateBottomBar(mainFragment);
        requireMainActivity().getDrawer().selectCorrectDrawerItemForPath(mainFragment.getCurrentPath());
        if (mainFragment.getMainFragmentViewModel().getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE) {
            tabHandler.update(new com.amaze.filemanager.database.models.explorer.Tab(i, mainFragment.getCurrentPath(), mainFragment.getMainFragmentViewModel().getHome()));
        } else {
            tabHandler.update(new com.amaze.filemanager.database.models.explorer.Tab(i, mainFragment.getMainFragmentViewModel().getHome(), mainFragment.getMainFragmentViewModel().getHome()));
        }
    }
    break;
}
default: {
if (((mainFragment.getMainFragmentViewModel() != null) && ((i - 1) == com.amaze.filemanager.ui.activities.MainActivity.currentTab)) && (i == pos)) {
    updateBottomBar(mainFragment);
    requireMainActivity().getDrawer().selectCorrectDrawerItemForPath(mainFragment.getCurrentPath());
    if (mainFragment.getMainFragmentViewModel().getOpenMode() == com.amaze.filemanager.fileoperations.filesystem.OpenMode.FILE) {
        tabHandler.update(new com.amaze.filemanager.database.models.explorer.Tab(i, mainFragment.getCurrentPath(), mainFragment.getMainFragmentViewModel().getHome()));
    } else {
        tabHandler.update(new com.amaze.filemanager.database.models.explorer.Tab(i, mainFragment.getMainFragmentViewModel().getHome(), mainFragment.getMainFragmentViewModel().getHome()));
    }
}
break;
}
}
i++;
}
}
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
super.onSaveInstanceState(outState);
if (sharedPrefs != null) {
sharedPrefs.edit().putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CURRENT_TAB, com.amaze.filemanager.ui.activities.MainActivity.currentTab).apply();
}
if (fragments.size() != 0) {
if (fragmentManager == null) {
return;
}
fragmentManager.executePendingTransactions();
fragmentManager.putFragment(outState, com.amaze.filemanager.ui.fragments.TabFragment.KEY_FRAGMENT_0, fragments.get(0));
fragmentManager.putFragment(outState, com.amaze.filemanager.ui.fragments.TabFragment.KEY_FRAGMENT_1, fragments.get(1));
outState.putInt(com.amaze.filemanager.ui.fragments.TabFragment.KEY_POSITION, viewPager.getCurrentItem());
}
}


public void setPagingEnabled(boolean isPaging) {
viewPager.setUserInputEnabled(isPaging);
}


public void setCurrentItem(int index) {
viewPager.setCurrentItem(index);
}


private class OnPageChangeCallbackImpl extends androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback {
@java.lang.Override
public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
mainFragment = requireMainActivity().getCurrentMainFragment();
if (((mainFragment == null) || (mainFragment.getMainFragmentViewModel() == null)) || mainFragment.getMainActivity().getListItemSelected()) {
return// we do not want to update toolbar colors when ActionMode is activated
;// we do not want to update toolbar colors when ActionMode is activated

}
// during the config change
@androidx.annotation.ColorInt
int color;
switch(MUID_STATIC) {
// TabFragment_11_BinaryMutator
case 11113: {
color = ((int) (evaluator.evaluate(position - positionOffset, startColor, endColor)));
break;
}
default: {
color = ((int) (evaluator.evaluate(position + positionOffset, startColor, endColor)));
break;
}
}
colorDrawable.setColor(color);
requireMainActivity().updateViews(colorDrawable);
}


@java.lang.Override
public void onPageSelected(int p1) {
requireMainActivity().getAppbar().getAppbarLayout().animate().translationY(0).setInterpolator(new android.view.animation.DecelerateInterpolator(2)).start();
com.amaze.filemanager.ui.activities.MainActivity.currentTab = p1;
if (sharedPrefs != null) {
sharedPrefs.edit().putInt(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_CURRENT_TAB, com.amaze.filemanager.ui.activities.MainActivity.currentTab).apply();
}
androidx.fragment.app.Fragment fragment;
fragment = fragments.get(p1);
if (fragment instanceof com.amaze.filemanager.ui.fragments.MainFragment) {
com.amaze.filemanager.ui.fragments.MainFragment ma;
ma = ((com.amaze.filemanager.ui.fragments.MainFragment) (fragment));
if (ma.getCurrentPath() != null) {
requireMainActivity().getDrawer().selectCorrectDrawerItemForPath(ma.getCurrentPath());
updateBottomBar(ma);
}
}
if ((circleDrawable1 != null) && (circleDrawable2 != null))
updateIndicator(p1);

}


@java.lang.Override
public void onPageScrollStateChanged(int state) {
// nothing to do
}

}

private class ScreenSlidePagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
public ScreenSlidePagerAdapter(androidx.fragment.app.FragmentActivity fragmentActivity) {
super(fragmentActivity);
}


@java.lang.Override
public int getItemCount() {
return fragments.size();
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.fragment.app.Fragment createFragment(int position) {
androidx.fragment.app.Fragment f;
f = fragments.get(position);
return f;
}

}

private void addNewTab(int num, java.lang.String path) {
addTab(new com.amaze.filemanager.database.models.explorer.Tab(num, path, path), "");
}


/**
 * Fetches new storage paths from drawer and apply to tabs This method will just create tabs in UI
 * change paths in database. Calls should implement updating each tab's list for new paths.
 *
 * @param addTab
 * 		whether new tabs should be added to ui or just change values in database
 */
public void refactorDrawerStorages(boolean addTab) {
com.amaze.filemanager.database.TabHandler tabHandler;
tabHandler = com.amaze.filemanager.database.TabHandler.getInstance();
com.amaze.filemanager.database.models.explorer.Tab tab1;
tab1 = tabHandler.findTab(1);
com.amaze.filemanager.database.models.explorer.Tab tab2;
tab2 = tabHandler.findTab(2);
com.amaze.filemanager.database.models.explorer.Tab[] tabs;
tabs = tabHandler.getAllTabs();
java.lang.String firstTabPath;
firstTabPath = requireMainActivity().getDrawer().getFirstPath();
java.lang.String secondTabPath;
secondTabPath = requireMainActivity().getDrawer().getSecondPath();
if ((((tabs == null) || (tabs.length < 1)) || (tab1 == null)) || (tab2 == null)) {
// creating tabs in db for the first time, probably the first launch of
// app, or something got corrupted
java.lang.String currentFirstTab;
currentFirstTab = (com.amaze.filemanager.utils.Utils.isNullOrEmpty(firstTabPath)) ? "/" : firstTabPath;
java.lang.String currentSecondTab;
currentSecondTab = (com.amaze.filemanager.utils.Utils.isNullOrEmpty(secondTabPath)) ? firstTabPath : secondTabPath;
if (addTab) {
addNewTab(1, currentSecondTab);
addNewTab(2, currentFirstTab);
}
tabHandler.addTab(new com.amaze.filemanager.database.models.explorer.Tab(1, currentSecondTab, currentSecondTab)).blockingAwait();
tabHandler.addTab(new com.amaze.filemanager.database.models.explorer.Tab(2, currentFirstTab, currentFirstTab)).blockingAwait();
if (currentFirstTab.equalsIgnoreCase("/")) {
sharedPrefs.edit().putBoolean(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_ROOTMODE, true).apply();
}
} else if ((path != null) && (path.length() != 0)) {
if (com.amaze.filemanager.ui.activities.MainActivity.currentTab == 0) {
addTab(tab1, path);
addTab(tab2, "");
}
if (com.amaze.filemanager.ui.activities.MainActivity.currentTab == 1) {
addTab(tab1, "");
addTab(tab2, path);
}
} else {
addTab(tab1, "");
addTab(tab2, "");
}
}


private void addTab(@androidx.annotation.NonNull
com.amaze.filemanager.database.models.explorer.Tab tab, java.lang.String path) {
com.amaze.filemanager.ui.fragments.MainFragment main;
main = new com.amaze.filemanager.ui.fragments.MainFragment();
android.os.Bundle b;
b = new android.os.Bundle();
if ((path != null) && (path.length() != 0)) {
b.putString("lastpath", path);
b.putInt("openmode", com.amaze.filemanager.fileoperations.filesystem.OpenMode.UNKNOWN.ordinal());
} else {
b.putString("lastpath", tab.getOriginalPath(savePaths, requireMainActivity().getPrefs()));
}
b.putString("home", tab.home);
b.putInt("no", tab.tabNumber);
main.setArguments(b);
fragments.add(main);
sectionsPagerAdapter.notifyDataSetChanged();
viewPager.setOffscreenPageLimit(4);
}


public androidx.fragment.app.Fragment getCurrentTabFragment() {
if (fragments.size() == 2)
return fragments.get(viewPager.getCurrentItem());
else
return null;

}


public androidx.fragment.app.Fragment getFragmentAtIndex(int pos) {
if ((fragments.size() == 2) && (pos < 2))
return fragments.get(pos);
else
return null;

}


// updating indicator color as per the current viewpager tab
void updateIndicator(int index) {
if ((index != 0) && (index != 1))
return;

int accentColor;
accentColor = requireMainActivity().getAccent();
circleDrawable1.setImageDrawable(new com.amaze.filemanager.ui.ColorCircleDrawable(accentColor));
circleDrawable2.setImageDrawable(new com.amaze.filemanager.ui.ColorCircleDrawable(android.graphics.Color.GRAY));
}


public androidx.constraintlayout.widget.ConstraintLayout getDragPlaceholder() {
return this.dragPlaceholder;
}


public void updateBottomBar(com.amaze.filemanager.ui.fragments.MainFragment mainFragment) {
if ((mainFragment == null) || (mainFragment.getMainFragmentViewModel() == null)) {
LOG.warn("Failed to update bottom bar: main fragment not available");
return;
}
requireMainActivity().getAppbar().getBottomBar().updatePath(mainFragment.getCurrentPath(), mainFragment.getMainFragmentViewModel().getOpenMode(), mainFragment.getMainFragmentViewModel().getFolderCount(), mainFragment.getMainFragmentViewModel().getFileCount(), mainFragment);
}


public void initLeftRightAndTopDragListeners(boolean destroy, boolean shouldInvokeLeftAndRight) {
if (shouldInvokeLeftAndRight) {
initLeftAndRightDragListeners(destroy);
}
for (androidx.fragment.app.Fragment fragment : fragments) {
if (fragment instanceof com.amaze.filemanager.ui.fragments.MainFragment) {
com.amaze.filemanager.ui.fragments.MainFragment m;
m = ((com.amaze.filemanager.ui.fragments.MainFragment) (fragment));
m.initTopAndEmptyAreaDragListeners(destroy);
}
}
}


private void initLeftAndRightDragListeners(boolean destroy) {
final com.amaze.filemanager.ui.fragments.MainFragment mainFragment;
mainFragment = requireMainActivity().getCurrentMainFragment();
android.view.View leftPlaceholder;
switch(MUID_STATIC) {
// TabFragment_12_InvalidViewFocusOperatorMutator
case 12113: {
/**
* Inserted by Kadabra
*/
leftPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_drag_left);
leftPlaceholder.requestFocus();
break;
}
// TabFragment_13_ViewComponentNotVisibleOperatorMutator
case 13113: {
/**
* Inserted by Kadabra
*/
leftPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_drag_left);
leftPlaceholder.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
leftPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_drag_left);
break;
}
}
android.view.View rightPlaceholder;
switch(MUID_STATIC) {
// TabFragment_14_InvalidViewFocusOperatorMutator
case 14113: {
/**
* Inserted by Kadabra
*/
rightPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_drag_right);
rightPlaceholder.requestFocus();
break;
}
// TabFragment_15_ViewComponentNotVisibleOperatorMutator
case 15113: {
/**
* Inserted by Kadabra
*/
rightPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_drag_right);
rightPlaceholder.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
rightPlaceholder = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_drag_right);
break;
}
}
androidx.appcompat.widget.AppCompatImageView dragToTrash;
switch(MUID_STATIC) {
// TabFragment_16_InvalidViewFocusOperatorMutator
case 16113: {
/**
* Inserted by Kadabra
*/
dragToTrash = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_trash_bottom);
dragToTrash.requestFocus();
break;
}
// TabFragment_17_ViewComponentNotVisibleOperatorMutator
case 17113: {
/**
* Inserted by Kadabra
*/
dragToTrash = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_trash_bottom);
dragToTrash.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
dragToTrash = rootView.findViewById(com.amaze.filemanager.R.id.placeholder_trash_bottom);
break;
}
}
com.amaze.filemanager.utils.DataUtils dataUtils;
dataUtils = com.amaze.filemanager.utils.DataUtils.getInstance();
if (destroy) {
leftPlaceholder.setOnDragListener(null);
rightPlaceholder.setOnDragListener(null);
dragToTrash.setOnDragListener(null);
leftPlaceholder.setVisibility(android.view.View.GONE);
rightPlaceholder.setVisibility(android.view.View.GONE);
com.amaze.filemanager.ui.ExtensionsKt.hideFade(dragToTrash, 150);
} else {
leftPlaceholder.setVisibility(android.view.View.VISIBLE);
rightPlaceholder.setVisibility(android.view.View.VISIBLE);
com.amaze.filemanager.ui.ExtensionsKt.showFade(dragToTrash, 150);
leftPlaceholder.setOnDragListener(new com.amaze.filemanager.ui.drag.TabFragmentSideDragListener(() -> {
if (viewPager.getCurrentItem() == 1) {
if (mainFragment != null) {
dataUtils.setCheckedItemsList(mainFragment.adapter.getCheckedItems());
requireMainActivity().getActionModeHelper().disableActionMode();
}
viewPager.setCurrentItem(0, true);
}
return null;
}));
rightPlaceholder.setOnDragListener(new com.amaze.filemanager.ui.drag.TabFragmentSideDragListener(() -> {
if (viewPager.getCurrentItem() == 0) {
if (mainFragment != null) {
dataUtils.setCheckedItemsList(mainFragment.adapter.getCheckedItems());
requireMainActivity().getActionModeHelper().disableActionMode();
}
viewPager.setCurrentItem(1, true);
}
return null;
}));
dragToTrash.setOnDragListener(new com.amaze.filemanager.ui.drag.DragToTrashListener(() -> {
if (mainFragment != null) {
com.amaze.filemanager.ui.dialogs.GeneralDialogCreation.deleteFilesDialog(requireContext(), requireMainActivity(), mainFragment.adapter.getCheckedItems(), requireMainActivity().getAppTheme());
} else {
com.amaze.filemanager.application.AppConfig.toast(requireContext(), getString(com.amaze.filemanager.R.string.operation_unsuccesful));
}
return null;
}, () -> {
dragToTrash.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS, android.view.HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
return null;
}));
}
}


@androidx.annotation.NonNull
private com.amaze.filemanager.ui.activities.MainActivity requireMainActivity() {
return ((com.amaze.filemanager.ui.activities.MainActivity) (requireActivity()));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
