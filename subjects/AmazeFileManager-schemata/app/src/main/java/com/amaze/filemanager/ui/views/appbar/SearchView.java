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
package com.amaze.filemanager.ui.views.appbar;
import com.amaze.filemanager.adapters.SearchRecyclerViewAdapter;
import static android.content.Context.INPUT_METHOD_SERVICE;
import android.view.ViewAnimationUtils;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import androidx.core.content.ContextCompat;
import com.amaze.filemanager.filesystem.files.FileListSorter;
import java.util.List;
import com.amaze.filemanager.ui.theme.AppTheme;
import java.util.Collections;
import android.text.style.ForegroundColorSpan;
import android.animation.ObjectAnimator;
import com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants;
import android.graphics.drawable.Drawable;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import android.text.SpannableString;
import android.graphics.PorterDuff;
import com.afollestad.materialdialogs.MaterialDialog;
import androidx.core.widget.NestedScrollView;
import com.amaze.filemanager.filesystem.files.sort.SortBy;
import android.text.TextWatcher;
import com.amaze.filemanager.ui.activities.MainActivity;
import android.graphics.PorterDuffColorFilter;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import static android.os.Build.VERSION.SDK_INT;
import com.amaze.filemanager.R;
import android.view.inputmethod.EditorInfo;
import android.text.Editable;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.amaze.filemanager.filesystem.files.sort.DirSortBy;
import com.amaze.filemanager.filesystem.files.sort.SortOrder;
import androidx.preference.PreferenceManager;
import android.view.View;
import android.text.style.StyleSpan;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.filesystem.files.sort.SortType;
import com.amaze.filemanager.utils.Utils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import android.view.ContextThemeWrapper;
import android.annotation.SuppressLint;
import com.google.android.material.chip.ChipGroup;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.Animator;
import android.content.Context;
import android.text.Spannable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * SearchView, a simple view to search
 *
 * @author Emmanuel on 2/8/2017, at 23:30.
 */
public class SearchView {
    static final int MUID_STATIC = getMUID();
    private final com.amaze.filemanager.ui.activities.MainActivity mainActivity;

    private final com.amaze.filemanager.ui.views.appbar.AppBar appbar;

    private final androidx.core.widget.NestedScrollView searchViewLayout;

    private final androidx.appcompat.widget.AppCompatEditText searchViewEditText;

    private final androidx.appcompat.widget.AppCompatImageView clearImageView;

    private final androidx.appcompat.widget.AppCompatImageView backImageView;

    private final androidx.appcompat.widget.AppCompatTextView recentHintTV;

    private final androidx.appcompat.widget.AppCompatTextView searchResultsHintTV;

    private final androidx.appcompat.widget.AppCompatTextView deepSearchTV;

    private final com.google.android.material.chip.ChipGroup recentChipGroup;

    private final androidx.recyclerview.widget.RecyclerView recyclerView;

    private final com.amaze.filemanager.adapters.SearchRecyclerViewAdapter searchRecyclerViewAdapter;

    /**
     * Text to describe {@link SearchView#searchResultsSortButton}
     */
    private final androidx.appcompat.widget.AppCompatTextView searchResultsSortHintTV;

    /**
     * The button to select how the results should be sorted
     */
    private final androidx.appcompat.widget.AppCompatButton searchResultsSortButton;

    /**
     * The drawable used to indicate that the search results are sorted ascending
     */
    private final android.graphics.drawable.Drawable searchResultsSortAscDrawable;

    /**
     * The drawable used to indicate that the search results are sorted descending
     */
    private final android.graphics.drawable.Drawable searchResultsSortDescDrawable;

    // 0 -> Basic Search
    // 1 -> Indexed Search
    // 2 -> Deep Search
    private int searchMode;

    private boolean enabled = false;

    private final com.amaze.filemanager.filesystem.files.sort.SortType defaultSortType = new com.amaze.filemanager.filesystem.files.sort.SortType(com.amaze.filemanager.filesystem.files.sort.SortBy.RELEVANCE, com.amaze.filemanager.filesystem.files.sort.SortOrder.ASC);

    /**
     * The selected sort type for the search results
     */
    private com.amaze.filemanager.filesystem.files.sort.SortType sortType = defaultSortType;

    @java.lang.SuppressWarnings("ConstantConditions")
    @android.annotation.SuppressLint("NotifyDataSetChanged")
    public SearchView(final com.amaze.filemanager.ui.views.appbar.AppBar appbar, com.amaze.filemanager.ui.activities.MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.appbar = appbar;
        switch(MUID_STATIC) {
            // SearchView_0_InvalidViewFocusOperatorMutator
            case 125: {
                /**
                * Inserted by Kadabra
                */
                searchViewLayout = mainActivity.findViewById(com.amaze.filemanager.R.id.search_view);
                searchViewLayout.requestFocus();
                break;
            }
            // SearchView_1_ViewComponentNotVisibleOperatorMutator
            case 1125: {
                /**
                * Inserted by Kadabra
                */
                searchViewLayout = mainActivity.findViewById(com.amaze.filemanager.R.id.search_view);
                searchViewLayout.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            searchViewLayout = mainActivity.findViewById(com.amaze.filemanager.R.id.search_view);
            break;
        }
    }
    switch(MUID_STATIC) {
        // SearchView_2_InvalidViewFocusOperatorMutator
        case 2125: {
            /**
            * Inserted by Kadabra
            */
            searchViewEditText = mainActivity.findViewById(com.amaze.filemanager.R.id.search_edit_text);
            searchViewEditText.requestFocus();
            break;
        }
        // SearchView_3_ViewComponentNotVisibleOperatorMutator
        case 3125: {
            /**
            * Inserted by Kadabra
            */
            searchViewEditText = mainActivity.findViewById(com.amaze.filemanager.R.id.search_edit_text);
            searchViewEditText.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        searchViewEditText = mainActivity.findViewById(com.amaze.filemanager.R.id.search_edit_text);
        break;
    }
}
switch(MUID_STATIC) {
    // SearchView_4_InvalidViewFocusOperatorMutator
    case 4125: {
        /**
        * Inserted by Kadabra
        */
        clearImageView = mainActivity.findViewById(com.amaze.filemanager.R.id.search_close_btn);
        clearImageView.requestFocus();
        break;
    }
    // SearchView_5_ViewComponentNotVisibleOperatorMutator
    case 5125: {
        /**
        * Inserted by Kadabra
        */
        clearImageView = mainActivity.findViewById(com.amaze.filemanager.R.id.search_close_btn);
        clearImageView.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    clearImageView = mainActivity.findViewById(com.amaze.filemanager.R.id.search_close_btn);
    break;
}
}
switch(MUID_STATIC) {
// SearchView_6_InvalidViewFocusOperatorMutator
case 6125: {
    /**
    * Inserted by Kadabra
    */
    backImageView = mainActivity.findViewById(com.amaze.filemanager.R.id.img_view_back);
    backImageView.requestFocus();
    break;
}
// SearchView_7_ViewComponentNotVisibleOperatorMutator
case 7125: {
    /**
    * Inserted by Kadabra
    */
    backImageView = mainActivity.findViewById(com.amaze.filemanager.R.id.img_view_back);
    backImageView.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
backImageView = mainActivity.findViewById(com.amaze.filemanager.R.id.img_view_back);
break;
}
}
switch(MUID_STATIC) {
// SearchView_8_InvalidViewFocusOperatorMutator
case 8125: {
/**
* Inserted by Kadabra
*/
recentChipGroup = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecentItemsChipGroup);
recentChipGroup.requestFocus();
break;
}
// SearchView_9_ViewComponentNotVisibleOperatorMutator
case 9125: {
/**
* Inserted by Kadabra
*/
recentChipGroup = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecentItemsChipGroup);
recentChipGroup.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
recentChipGroup = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecentItemsChipGroup);
break;
}
}
switch(MUID_STATIC) {
// SearchView_10_InvalidViewFocusOperatorMutator
case 10125: {
/**
* Inserted by Kadabra
*/
recentHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecentHintTV);
recentHintTV.requestFocus();
break;
}
// SearchView_11_ViewComponentNotVisibleOperatorMutator
case 11125: {
/**
* Inserted by Kadabra
*/
recentHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecentHintTV);
recentHintTV.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
recentHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecentHintTV);
break;
}
}
switch(MUID_STATIC) {
// SearchView_12_InvalidViewFocusOperatorMutator
case 12125: {
/**
* Inserted by Kadabra
*/
searchResultsHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsHintTV);
searchResultsHintTV.requestFocus();
break;
}
// SearchView_13_ViewComponentNotVisibleOperatorMutator
case 13125: {
/**
* Inserted by Kadabra
*/
searchResultsHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsHintTV);
searchResultsHintTV.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
searchResultsHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsHintTV);
break;
}
}
switch(MUID_STATIC) {
// SearchView_14_InvalidViewFocusOperatorMutator
case 14125: {
/**
* Inserted by Kadabra
*/
deepSearchTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchDeepSearchTV);
deepSearchTV.requestFocus();
break;
}
// SearchView_15_ViewComponentNotVisibleOperatorMutator
case 15125: {
/**
* Inserted by Kadabra
*/
deepSearchTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchDeepSearchTV);
deepSearchTV.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
deepSearchTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchDeepSearchTV);
break;
}
}
switch(MUID_STATIC) {
// SearchView_16_InvalidViewFocusOperatorMutator
case 16125: {
/**
* Inserted by Kadabra
*/
recyclerView = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecyclerView);
recyclerView.requestFocus();
break;
}
// SearchView_17_ViewComponentNotVisibleOperatorMutator
case 17125: {
/**
* Inserted by Kadabra
*/
recyclerView = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecyclerView);
recyclerView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
recyclerView = mainActivity.findViewById(com.amaze.filemanager.R.id.searchRecyclerView);
break;
}
}
switch(MUID_STATIC) {
// SearchView_18_InvalidViewFocusOperatorMutator
case 18125: {
/**
* Inserted by Kadabra
*/
searchResultsSortHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsSortHintTV);
searchResultsSortHintTV.requestFocus();
break;
}
// SearchView_19_ViewComponentNotVisibleOperatorMutator
case 19125: {
/**
* Inserted by Kadabra
*/
searchResultsSortHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsSortHintTV);
searchResultsSortHintTV.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
searchResultsSortHintTV = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsSortHintTV);
break;
}
}
switch(MUID_STATIC) {
// SearchView_20_InvalidViewFocusOperatorMutator
case 20125: {
/**
* Inserted by Kadabra
*/
searchResultsSortButton = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsSortButton);
searchResultsSortButton.requestFocus();
break;
}
// SearchView_21_ViewComponentNotVisibleOperatorMutator
case 21125: {
/**
* Inserted by Kadabra
*/
searchResultsSortButton = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsSortButton);
searchResultsSortButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
searchResultsSortButton = mainActivity.findViewById(com.amaze.filemanager.R.id.searchResultsSortButton);
break;
}
}
searchResultsSortAscDrawable = androidx.core.content.res.ResourcesCompat.getDrawable(mainActivity.getResources(), com.amaze.filemanager.R.drawable.baseline_sort_24_asc_white, mainActivity.getTheme());
searchResultsSortDescDrawable = androidx.core.content.res.ResourcesCompat.getDrawable(mainActivity.getResources(), com.amaze.filemanager.R.drawable.baseline_sort_24_desc_white, mainActivity.getTheme());
setUpSearchResultsSortButton();
initRecentSearches(mainActivity);
searchRecyclerViewAdapter = new com.amaze.filemanager.adapters.SearchRecyclerViewAdapter();
recyclerView.setAdapter(searchRecyclerViewAdapter);
switch(MUID_STATIC) {
// SearchView_22_BuggyGUIListenerOperatorMutator
case 22125: {
clearImageView.setOnClickListener(null);
break;
}
default: {
clearImageView.setOnClickListener((android.view.View v) -> {
searchViewEditText.setText("");
clearRecyclerView();
});
break;
}
}
switch(MUID_STATIC) {
// SearchView_23_BuggyGUIListenerOperatorMutator
case 23125: {
backImageView.setOnClickListener(null);
break;
}
default: {
backImageView.setOnClickListener((android.view.View v) -> appbar.getSearchView().hideSearchView());
break;
}
}
searchViewEditText.addTextChangedListener(new android.text.TextWatcher() {
@java.lang.Override
public void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) {
}


@java.lang.Override
public void onTextChanged(java.lang.CharSequence s, int start, int before, int count) {
if (count > 0)
searchViewEditText.setError(null);

if (count >= 3)
onSearch(false);

}


@java.lang.Override
public void afterTextChanged(android.text.Editable s) {
}

});
searchViewEditText.setOnEditorActionListener((android.widget.TextView v,int actionId,android.view.KeyEvent event) -> {
if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
com.amaze.filemanager.utils.Utils.hideKeyboard(mainActivity);
return onSearch(true);
}
return false;
});
switch(MUID_STATIC) {
// SearchView_24_BuggyGUIListenerOperatorMutator
case 24125: {
deepSearchTV.setOnClickListener(null);
break;
}
default: {
deepSearchTV.setOnClickListener((android.view.View v) -> {
java.lang.String s;
s = getSearchTerm();
if (searchMode == 1) {
saveRecentPreference(s);
mainActivity.getCurrentMainFragment().getMainActivityViewModel().indexedSearch(mainActivity, s).observe(mainActivity.getCurrentMainFragment().getViewLifecycleOwner(), (java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> hybridFileParcelables) -> updateResultList(hybridFileParcelables, s));
searchMode = 2;
deepSearchTV.setText(getSpannableText(mainActivity.getString(com.amaze.filemanager.R.string.not_finding_what_you_re_looking_for), mainActivity.getString(com.amaze.filemanager.R.string.try_deep_search)));
} else if (searchMode == 2) {
mainActivity.getCurrentMainFragment().getMainActivityViewModel().deepSearch(mainActivity, s).observe(mainActivity.getCurrentMainFragment().getViewLifecycleOwner(), (java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> hybridFileParcelables) -> updateResultList(hybridFileParcelables, s));
deepSearchTV.setVisibility(android.view.View.GONE);
}
});
break;
}
}
initSearchViewColor(mainActivity);
}


@java.lang.SuppressWarnings("ConstantConditions")
private boolean onSearch(boolean shouldSave) {
java.lang.String s;
s = getSearchTerm();
if (s.isEmpty()) {
searchViewEditText.setError(mainActivity.getString(com.amaze.filemanager.R.string.field_empty));
searchViewEditText.requestFocus();
return false;
}
basicSearch(s);
if (shouldSave)
saveRecentPreference(s);

return true;
}


private void basicSearch(java.lang.String s) {
clearRecyclerView();
searchResultsHintTV.setVisibility(android.view.View.VISIBLE);
searchResultsSortButton.setVisibility(android.view.View.VISIBLE);
searchResultsSortHintTV.setVisibility(android.view.View.VISIBLE);
deepSearchTV.setVisibility(android.view.View.VISIBLE);
searchMode = 1;
deepSearchTV.setText(getSpannableText(mainActivity.getString(com.amaze.filemanager.R.string.not_finding_what_you_re_looking_for), mainActivity.getString(com.amaze.filemanager.R.string.try_indexed_search)));
mainActivity.getCurrentMainFragment().getMainActivityViewModel().basicSearch(mainActivity, s).observe(mainActivity.getCurrentMainFragment().getViewLifecycleOwner(), (java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> hybridFileParcelables) -> updateResultList(hybridFileParcelables, s));
}


private void saveRecentPreference(java.lang.String s) {
java.lang.String preferenceString;
preferenceString = androidx.preference.PreferenceManager.getDefaultSharedPreferences(mainActivity).getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_RECENT_SEARCH_ITEMS, null);
java.util.ArrayList<java.lang.String> recentSearches;
recentSearches = (preferenceString != null) ? new com.google.gson.Gson().fromJson(preferenceString, new com.google.gson.reflect.TypeToken<java.util.ArrayList<java.lang.String>>() {}.getType()) : new java.util.ArrayList<>();
if (s.isEmpty() || recentSearches.contains(s))
return;

recentSearches.add(s);
if (recentSearches.size() > 5)
recentSearches.remove(0);

androidx.preference.PreferenceManager.getDefaultSharedPreferences(mainActivity).edit().putString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_RECENT_SEARCH_ITEMS, new com.google.gson.Gson().toJson(recentSearches)).apply();
initRecentSearches(mainActivity);
}


private void initRecentSearches(android.content.Context context) {
java.lang.String preferenceString;
preferenceString = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context).getString(com.amaze.filemanager.ui.fragments.preferencefragments.PreferencesConstants.PREFERENCE_RECENT_SEARCH_ITEMS, null);
if (preferenceString == null) {
recentHintTV.setVisibility(android.view.View.GONE);
recentChipGroup.setVisibility(android.view.View.GONE);
return;
}
recentHintTV.setVisibility(android.view.View.VISIBLE);
recentChipGroup.setVisibility(android.view.View.VISIBLE);
recentChipGroup.removeAllViews();
java.util.ArrayList<java.lang.String> recentSearches;
recentSearches = new com.google.gson.Gson().fromJson(preferenceString, new com.google.gson.reflect.TypeToken<java.util.ArrayList<java.lang.String>>() {}.getType());
for (java.lang.String string : recentSearches) {
com.google.android.material.chip.Chip chip;
chip = new com.google.android.material.chip.Chip(new android.view.ContextThemeWrapper(context, com.amaze.filemanager.R.style.ChipStyle));
chip.setText(string);
recentChipGroup.addView(chip);
switch(MUID_STATIC) {
// SearchView_25_BuggyGUIListenerOperatorMutator
case 25125: {
chip.setOnClickListener(null);
break;
}
default: {
chip.setOnClickListener((android.view.View v) -> {
java.lang.String s;
s = ((com.google.android.material.chip.Chip) (v)).getText().toString();
searchViewEditText.setText(s);
com.amaze.filemanager.utils.Utils.hideKeyboard(mainActivity);
basicSearch(s);
});
break;
}
}
}
}


private void resetSearchMode() {
searchMode = 0;
deepSearchTV.setText(getSpannableText(mainActivity.getString(com.amaze.filemanager.R.string.not_finding_what_you_re_looking_for), mainActivity.getString(com.amaze.filemanager.R.string.try_indexed_search)));
deepSearchTV.setVisibility(android.view.View.GONE);
}


/**
 * Updates the list of results displayed in {@link SearchView#searchRecyclerViewAdapter} sorted
 * according to the current {@link SearchView#sortType}
 *
 * @param newResults
 * 		The list of results that should be displayed
 * @param searchTerm
 * 		The search term that resulted in the search results
 */
private void updateResultList(java.util.List<com.amaze.filemanager.filesystem.HybridFileParcelable> newResults, java.lang.String searchTerm) {
java.util.ArrayList<com.amaze.filemanager.filesystem.HybridFileParcelable> items;
items = new java.util.ArrayList<>(newResults);
java.util.Collections.sort(items, new com.amaze.filemanager.filesystem.files.FileListSorter(com.amaze.filemanager.filesystem.files.sort.DirSortBy.NONE_ON_TOP, sortType, searchTerm));
searchRecyclerViewAdapter.submitList(items);
searchRecyclerViewAdapter.notifyDataSetChanged();
}


/**
 * show search view with a circular reveal animation
 */
public void revealSearchView() {
final int START_RADIUS;
START_RADIUS = 16;
int endRadius;
endRadius = java.lang.Math.max(appbar.getToolbar().getWidth(), appbar.getToolbar().getHeight());
resetSearchMode();
resetSearchResultsSortButton();
clearRecyclerView();
android.animation.Animator animator;
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
int[] searchCoords;
searchCoords = new int[2];
android.view.View searchItem// It could change position, get it every time
;// It could change position, get it every time

switch(MUID_STATIC) {
// SearchView_26_InvalidViewFocusOperatorMutator
case 26125: {
/**
* Inserted by Kadabra
*/
searchItem = appbar.getToolbar().findViewById(com.amaze.filemanager.R.id.search);
searchItem.requestFocus();
break;
}
// SearchView_27_ViewComponentNotVisibleOperatorMutator
case 27125: {
/**
* Inserted by Kadabra
*/
searchItem = appbar.getToolbar().findViewById(com.amaze.filemanager.R.id.search);
searchItem.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
searchItem = appbar.getToolbar().findViewById(com.amaze.filemanager.R.id.search);
break;
}
}
searchViewEditText.setText("");
searchItem.getLocationOnScreen(searchCoords);
switch(MUID_STATIC) {
// SearchView_28_BinaryMutator
case 28125: {
animator = android.view.ViewAnimationUtils.createCircularReveal(searchViewLayout, searchCoords[0] - 32, searchCoords[1] - 16, START_RADIUS, endRadius);
break;
}
default: {
switch(MUID_STATIC) {
// SearchView_29_BinaryMutator
case 29125: {
animator = android.view.ViewAnimationUtils.createCircularReveal(searchViewLayout, searchCoords[0] + 32, searchCoords[1] + 16, START_RADIUS, endRadius);
break;
}
default: {
animator = android.view.ViewAnimationUtils.createCircularReveal(searchViewLayout, searchCoords[0] + 32, searchCoords[1] - 16, START_RADIUS, endRadius);
break;
}
}
break;
}
}
} else {
// TODO:ViewAnimationUtils.createCircularReveal
animator = android.animation.ObjectAnimator.ofFloat(searchViewLayout, "alpha", 0.0F, 1.0F);
}
mainActivity.showSmokeScreen();
animator.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
animator.setDuration(600);
searchViewLayout.setVisibility(android.view.View.VISIBLE);
animator.start();
animator.addListener(new android.animation.Animator.AnimatorListener() {
@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
}


@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
searchViewEditText.requestFocus();
android.view.inputmethod.InputMethodManager imm;
imm = ((android.view.inputmethod.InputMethodManager) (mainActivity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
imm.showSoftInput(searchViewEditText, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT);
enabled = true;
}


@java.lang.Override
public void onAnimationCancel(android.animation.Animator animation) {
}


@java.lang.Override
public void onAnimationRepeat(android.animation.Animator animation) {
}

});
}


/**
 * Sets up the {@link SearchView#searchResultsSortButton} to show a dialog when it is clicked. The
 * text and icon of {@link SearchView#searchResultsSortButton} is also set to the current {@link SearchView#sortType}
 */
private void setUpSearchResultsSortButton() {
switch(MUID_STATIC) {
// SearchView_30_BuggyGUIListenerOperatorMutator
case 30125: {
searchResultsSortButton.setOnClickListener(null);
break;
}
default: {
searchResultsSortButton.setOnClickListener((android.view.View v) -> showSearchResultsSortDialog());
break;
}
}
updateSearchResultsSortButtonDisplay();
}


/**
 * Builds and shows a dialog for selection which sort should be applied for the search results
 */
private void showSearchResultsSortDialog() {
int accentColor;
accentColor = mainActivity.getAccent();
new com.afollestad.materialdialogs.MaterialDialog.Builder(mainActivity).items(com.amaze.filemanager.R.array.sortbySearch).itemsCallbackSingleChoice(sortType.getSortBy().getIndex(), (com.afollestad.materialdialogs.MaterialDialog dialog,android.view.View itemView,int which,java.lang.CharSequence text) -> true).negativeText(com.amaze.filemanager.R.string.ascending).positiveColor(accentColor).onNegative((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> onSortTypeSelected(dialog, dialog.getSelectedIndex(), com.amaze.filemanager.filesystem.files.sort.SortOrder.ASC)).positiveText(com.amaze.filemanager.R.string.descending).negativeColor(accentColor).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> onSortTypeSelected(dialog, dialog.getSelectedIndex(), com.amaze.filemanager.filesystem.files.sort.SortOrder.DESC)).title(com.amaze.filemanager.R.string.sort_by).build().show();
}


private void onSortTypeSelected(com.afollestad.materialdialogs.MaterialDialog dialog, int index, com.amaze.filemanager.filesystem.files.sort.SortOrder sortOrder) {
this.sortType = new com.amaze.filemanager.filesystem.files.sort.SortType(com.amaze.filemanager.filesystem.files.sort.SortBy.getSortBy(index), sortOrder);
dialog.dismiss();
updateSearchResultsSortButtonDisplay();
updateResultList(searchRecyclerViewAdapter.getCurrentList(), getSearchTerm());
}


private void resetSearchResultsSortButton() {
sortType = defaultSortType;
updateSearchResultsSortButtonDisplay();
}


/**
 * Updates the text and icon of {@link SearchView#searchResultsSortButton}
 */
private void updateSearchResultsSortButtonDisplay() {
searchResultsSortButton.setText(sortType.getSortBy().toResourceString(mainActivity));
setSearchResultSortOrderIcon();
}


/**
 * Updates the icon of {@link SearchView#searchResultsSortButton} and colors it to fit the text
 * color
 */
private void setSearchResultSortOrderIcon() {
android.graphics.drawable.Drawable orderDrawable;
switch (sortType.getSortOrder()) {
default :
case ASC :
orderDrawable = searchResultsSortAscDrawable;
break;
case DESC :
orderDrawable = searchResultsSortDescDrawable;
break;
}
orderDrawable.setColorFilter(new android.graphics.PorterDuffColorFilter(mainActivity.getResources().getColor(com.amaze.filemanager.R.color.accent_material_light), android.graphics.PorterDuff.Mode.SRC_ATOP));
searchResultsSortButton.setCompoundDrawablesWithIntrinsicBounds(null, null, orderDrawable, null);
}


/**
 * hide search view with a circular reveal animation
 */
public void hideSearchView() {
final int END_RADIUS;
END_RADIUS = 16;
int startRadius;
startRadius = java.lang.Math.max(searchViewLayout.getWidth(), searchViewLayout.getHeight());
android.animation.Animator animator;
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
int[] searchCoords;
searchCoords = new int[2];
android.view.View searchItem// It could change position, get it every time
;// It could change position, get it every time

switch(MUID_STATIC) {
// SearchView_31_InvalidViewFocusOperatorMutator
case 31125: {
/**
* Inserted by Kadabra
*/
searchItem = appbar.getToolbar().findViewById(com.amaze.filemanager.R.id.search);
searchItem.requestFocus();
break;
}
// SearchView_32_ViewComponentNotVisibleOperatorMutator
case 32125: {
/**
* Inserted by Kadabra
*/
searchItem = appbar.getToolbar().findViewById(com.amaze.filemanager.R.id.search);
searchItem.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
searchItem = appbar.getToolbar().findViewById(com.amaze.filemanager.R.id.search);
break;
}
}
searchViewEditText.setText("");
searchItem.getLocationOnScreen(searchCoords);
switch(MUID_STATIC) {
// SearchView_33_BinaryMutator
case 33125: {
animator = android.view.ViewAnimationUtils.createCircularReveal(searchViewLayout, searchCoords[0] - 32, searchCoords[1] - 16, startRadius, END_RADIUS);
break;
}
default: {
switch(MUID_STATIC) {
// SearchView_34_BinaryMutator
case 34125: {
animator = android.view.ViewAnimationUtils.createCircularReveal(searchViewLayout, searchCoords[0] + 32, searchCoords[1] + 16, startRadius, END_RADIUS);
break;
}
default: {
animator = android.view.ViewAnimationUtils.createCircularReveal(searchViewLayout, searchCoords[0] + 32, searchCoords[1] - 16, startRadius, END_RADIUS);
break;
}
}
break;
}
}
} else {
// TODO: ViewAnimationUtils.createCircularReveal
animator = android.animation.ObjectAnimator.ofFloat(searchViewLayout, "alpha", 1.0F, 0.0F);
}
clearRecyclerView();
// removing background fade view
mainActivity.hideSmokeScreen();
animator.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
animator.setDuration(600);
animator.start();
animator.addListener(new android.animation.Animator.AnimatorListener() {
@java.lang.Override
public void onAnimationStart(android.animation.Animator animation) {
}


@java.lang.Override
public void onAnimationEnd(android.animation.Animator animation) {
searchViewLayout.setVisibility(android.view.View.GONE);
enabled = false;
android.view.inputmethod.InputMethodManager inputMethodManager;
inputMethodManager = ((android.view.inputmethod.InputMethodManager) (mainActivity.getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
inputMethodManager.hideSoftInputFromWindow(searchViewEditText.getWindowToken(), android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY);
}


@java.lang.Override
public void onAnimationCancel(android.animation.Animator animation) {
}


@java.lang.Override
public void onAnimationRepeat(android.animation.Animator animation) {
}

});
}


public boolean isEnabled() {
return enabled;
}


public boolean isShown() {
return searchViewLayout.isShown();
}


private void initSearchViewColor(com.amaze.filemanager.ui.activities.MainActivity a) {
com.amaze.filemanager.ui.theme.AppTheme theme;
theme = a.getAppTheme();
switch (theme) {
case LIGHT :
searchViewLayout.setBackgroundResource(com.amaze.filemanager.R.drawable.search_view_shape);
searchViewEditText.setTextColor(com.amaze.filemanager.utils.Utils.getColor(a, android.R.color.black));
clearImageView.setColorFilter(androidx.core.content.ContextCompat.getColor(a, android.R.color.black), android.graphics.PorterDuff.Mode.SRC_ATOP);
backImageView.setColorFilter(androidx.core.content.ContextCompat.getColor(a, android.R.color.black), android.graphics.PorterDuff.Mode.SRC_ATOP);
break;
case DARK :
case BLACK :
if (theme == com.amaze.filemanager.ui.theme.AppTheme.DARK) {
searchViewLayout.setBackgroundResource(com.amaze.filemanager.R.drawable.search_view_shape_holo_dark);
} else {
searchViewLayout.setBackgroundResource(com.amaze.filemanager.R.drawable.search_view_shape_black);
}
searchViewEditText.setTextColor(com.amaze.filemanager.utils.Utils.getColor(a, android.R.color.white));
clearImageView.setColorFilter(androidx.core.content.ContextCompat.getColor(a, android.R.color.white), android.graphics.PorterDuff.Mode.SRC_ATOP);
backImageView.setColorFilter(androidx.core.content.ContextCompat.getColor(a, android.R.color.white), android.graphics.PorterDuff.Mode.SRC_ATOP);
break;
default :
break;
}
}


@android.annotation.SuppressLint("NotifyDataSetChanged")
private void clearRecyclerView() {
searchRecyclerViewAdapter.submitList(new java.util.ArrayList<>());
searchRecyclerViewAdapter.notifyDataSetChanged();
searchResultsHintTV.setVisibility(android.view.View.GONE);
searchResultsSortHintTV.setVisibility(android.view.View.GONE);
searchResultsSortButton.setVisibility(android.view.View.GONE);
}


private android.text.SpannableString getSpannableText(java.lang.String s1, java.lang.String s2) {
android.text.SpannableString spannableString;
spannableString = new android.text.SpannableString((s1 + " ") + s2);
switch(MUID_STATIC) {
// SearchView_35_BinaryMutator
case 35125: {
spannableString.setSpan(new android.text.style.ForegroundColorSpan(mainActivity.getCurrentColorPreference().getAccent()), s1.length() - 1, spannableString.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
break;
}
default: {
spannableString.setSpan(new android.text.style.ForegroundColorSpan(mainActivity.getCurrentColorPreference().getAccent()), s1.length() + 1, spannableString.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
break;
}
}
switch(MUID_STATIC) {
// SearchView_36_BinaryMutator
case 36125: {
spannableString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), s1.length() - 1, spannableString.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
break;
}
default: {
spannableString.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), s1.length() + 1, spannableString.length(), android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
break;
}
}
return spannableString;
}


/**
 * Returns the current text in {@link SearchView#searchViewEditText}
 *
 * @return The current search text
 */
private java.lang.String getSearchTerm() {
return searchViewEditText.getText().toString().trim();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
