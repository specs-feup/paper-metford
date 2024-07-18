/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.async;
import it.feio.android.omninotes.models.Category;
import it.feio.android.omninotes.models.adapters.CategoryBaseAdapter;
import it.feio.android.omninotes.models.views.NonScrollableListView;
import de.greenrobot.event.EventBus;
import it.feio.android.omninotes.MainActivity;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import android.view.View;
import it.feio.android.omninotes.async.bus.NavigationUpdatedEvent;
import java.lang.ref.WeakReference;
import it.feio.android.omninotes.R;
import android.view.LayoutInflater;
import android.app.Activity;
import java.util.List;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.models.ONStyle;
import android.content.Context;
import it.feio.android.omninotes.SettingsActivity;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CategoryMenuTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.util.List<it.feio.android.omninotes.models.Category>> {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<androidx.fragment.app.Fragment> mFragmentWeakReference;

    private final it.feio.android.omninotes.MainActivity mainActivity;

    private it.feio.android.omninotes.models.views.NonScrollableListView mDrawerCategoriesList;

    private android.view.View settingsView;

    private android.view.View settingsViewCat;

    private it.feio.android.omninotes.models.views.NonScrollableListView mDrawerList;

    public CategoryMenuTask(androidx.fragment.app.Fragment mFragment) {
        mFragmentWeakReference = new java.lang.ref.WeakReference<>(mFragment);
        this.mainActivity = ((it.feio.android.omninotes.MainActivity) (mFragment.getActivity()));
    }


    @java.lang.Override
    protected void onPreExecute() {
        super.onPreExecute();
        switch(MUID_STATIC) {
            // CategoryMenuTask_0_InvalidViewFocusOperatorMutator
            case 29: {
                /**
                * Inserted by Kadabra
                */
                mDrawerList = mainActivity.findViewById(it.feio.android.omninotes.R.id.drawer_nav_list);
                mDrawerList.requestFocus();
                break;
            }
            // CategoryMenuTask_1_ViewComponentNotVisibleOperatorMutator
            case 1029: {
                /**
                * Inserted by Kadabra
                */
                mDrawerList = mainActivity.findViewById(it.feio.android.omninotes.R.id.drawer_nav_list);
                mDrawerList.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mDrawerList = mainActivity.findViewById(it.feio.android.omninotes.R.id.drawer_nav_list);
            break;
        }
    }
    android.view.LayoutInflater inflater;
    inflater = ((android.view.LayoutInflater) (mainActivity.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
    switch(MUID_STATIC) {
        // CategoryMenuTask_2_InvalidViewFocusOperatorMutator
        case 2029: {
            /**
            * Inserted by Kadabra
            */
            settingsView = mainActivity.findViewById(it.feio.android.omninotes.R.id.settings_view);
            settingsView.requestFocus();
            break;
        }
        // CategoryMenuTask_3_ViewComponentNotVisibleOperatorMutator
        case 3029: {
            /**
            * Inserted by Kadabra
            */
            settingsView = mainActivity.findViewById(it.feio.android.omninotes.R.id.settings_view);
            settingsView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        settingsView = mainActivity.findViewById(it.feio.android.omninotes.R.id.settings_view);
        break;
    }
}
switch(MUID_STATIC) {
    // CategoryMenuTask_4_InvalidViewFocusOperatorMutator
    case 4029: {
        /**
        * Inserted by Kadabra
        */
        // Settings view when categories are available
        mDrawerCategoriesList = mainActivity.findViewById(it.feio.android.omninotes.R.id.drawer_tag_list);
        mDrawerCategoriesList.requestFocus();
        break;
    }
    // CategoryMenuTask_5_ViewComponentNotVisibleOperatorMutator
    case 5029: {
        /**
        * Inserted by Kadabra
        */
        // Settings view when categories are available
        mDrawerCategoriesList = mainActivity.findViewById(it.feio.android.omninotes.R.id.drawer_tag_list);
        mDrawerCategoriesList.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    // Settings view when categories are available
    mDrawerCategoriesList = mainActivity.findViewById(it.feio.android.omninotes.R.id.drawer_tag_list);
    break;
}
}
if ((mDrawerCategoriesList.getAdapter() == null) && (mDrawerCategoriesList.getFooterViewsCount() == 0)) {
settingsViewCat = inflater.inflate(it.feio.android.omninotes.R.layout.drawer_category_list_footer, null);
mDrawerCategoriesList.addFooterView(settingsViewCat);
} else {
switch(MUID_STATIC) {
    // CategoryMenuTask_6_BinaryMutator
    case 6029: {
        settingsViewCat = mDrawerCategoriesList.getChildAt(mDrawerCategoriesList.getChildCount() + 1);
        break;
    }
    default: {
    settingsViewCat = mDrawerCategoriesList.getChildAt(mDrawerCategoriesList.getChildCount() - 1);
    break;
}
}
}
}


@java.lang.Override
protected java.util.List<it.feio.android.omninotes.models.Category> doInBackground(java.lang.Void... params) {
if (isAlive()) {
return buildCategoryMenu();
} else {
cancel(true);
return java.util.Collections.emptyList();
}
}


@java.lang.Override
@java.lang.Deprecated
protected void onPostExecute(final java.util.List<it.feio.android.omninotes.models.Category> categories) {
if (isAlive()) {
mDrawerCategoriesList.setAdapter(new it.feio.android.omninotes.models.adapters.CategoryBaseAdapter(mainActivity, categories, mainActivity.getNavigationTmp()));
if (categories.isEmpty()) {
setWidgetVisibility(settingsViewCat, false);
setWidgetVisibility(settingsView, true);
} else {
setWidgetVisibility(settingsViewCat, true);
setWidgetVisibility(settingsView, false);
}
mDrawerCategoriesList.justifyListViewHeightBasedOnChildren();
}
}


private void setWidgetVisibility(android.view.View view, boolean visible) {
if (view != null) {
view.setVisibility(visible ? android.view.View.VISIBLE : android.view.View.GONE);
}
}


private boolean isAlive() {
return (((mFragmentWeakReference.get() != null) && mFragmentWeakReference.get().isAdded()) && (mFragmentWeakReference.get().getActivity() != null)) && (!mFragmentWeakReference.get().getActivity().isFinishing());
}


private java.util.List<it.feio.android.omninotes.models.Category> buildCategoryMenu() {
java.util.List<it.feio.android.omninotes.models.Category> categories;
categories = it.feio.android.omninotes.db.DbHelper.getInstance().getCategories();
android.view.View settings;
settings = (categories.isEmpty()) ? settingsView : settingsViewCat;
if (settings == null) {
return categories;
}
mainActivity.runOnUiThread(() -> {
switch(MUID_STATIC) {
// CategoryMenuTask_7_BuggyGUIListenerOperatorMutator
case 7029: {
    settings.setOnClickListener(null);
    break;
}
default: {
settings.setOnClickListener((android.view.View v) -> {
    android.content.Intent settingsIntent;
    switch(MUID_STATIC) {
        // CategoryMenuTask_8_InvalidKeyIntentOperatorMutator
        case 8029: {
            settingsIntent = new android.content.Intent((it.feio.android.omninotes.MainActivity) null, it.feio.android.omninotes.SettingsActivity.class);
            break;
        }
        // CategoryMenuTask_9_RandomActionIntentDefinitionOperatorMutator
        case 9029: {
            settingsIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        settingsIntent = new android.content.Intent(mainActivity, it.feio.android.omninotes.SettingsActivity.class);
        break;
    }
}
mainActivity.startActivity(settingsIntent);
});
break;
}
}
buildCategoryMenuClickEvent();
buildCategoryMenuLongClickEvent();
});
return categories;
}


private void buildCategoryMenuLongClickEvent() {
mDrawerCategoriesList.setOnItemLongClickListener((android.widget.AdapterView<?> arg0,android.view.View view,int position,long arg3) -> {
if (mDrawerCategoriesList.getAdapter() != null) {
java.lang.Object item;
item = mDrawerCategoriesList.getAdapter().getItem(position);
// Ensuring that clicked item is not the ListView header
if (item != null) {
mainActivity.editTag(((it.feio.android.omninotes.models.Category) (item)));
}
} else {
mainActivity.showMessage(it.feio.android.omninotes.R.string.category_deleted, it.feio.android.omninotes.models.ONStyle.ALERT);
}
return true;
});
}


private void buildCategoryMenuClickEvent() {
mDrawerCategoriesList.setOnItemClickListener((android.widget.AdapterView<?> arg0,android.view.View arg1,int position,long arg3) -> {
java.lang.Object item;
item = mDrawerCategoriesList.getAdapter().getItem(position);
if (mainActivity.updateNavigation(java.lang.String.valueOf(((it.feio.android.omninotes.models.Category) (item)).getId()))) {
mDrawerCategoriesList.setItemChecked(position, true);
// Forces redraw
if (mDrawerList != null) {
mDrawerList.setItemChecked(0, false);
de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.NavigationUpdatedEvent(mDrawerCategoriesList.getItemAtPosition(position)));
}
}
});
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
