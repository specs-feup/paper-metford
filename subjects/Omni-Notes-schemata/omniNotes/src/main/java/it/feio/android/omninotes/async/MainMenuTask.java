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
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_DYNAMIC_MENU;
import it.feio.android.omninotes.utils.Navigation;
import java.util.ArrayList;
import android.content.res.TypedArray;
import it.feio.android.omninotes.models.views.NonScrollableListView;
import de.greenrobot.event.EventBus;
import it.feio.android.omninotes.MainActivity;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.fragment.app.Fragment;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.async.bus.NavigationUpdatedEvent;
import java.lang.ref.WeakReference;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.models.adapters.NavDrawerAdapter;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_SHOW_UNCATEGORIZED;
import java.util.List;
import it.feio.android.omninotes.models.NavigationItem;
import it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MainMenuTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.util.List<it.feio.android.omninotes.models.NavigationItem>> {
    static final int MUID_STATIC = getMUID();
    private final java.lang.ref.WeakReference<androidx.fragment.app.Fragment> fragmentWeakReference;

    private final java.lang.ref.WeakReference<it.feio.android.omninotes.MainActivity> mainActivity;

    private it.feio.android.omninotes.models.views.NonScrollableListView navDrawer;

    public MainMenuTask(androidx.fragment.app.Fragment fragment) {
        fragmentWeakReference = new java.lang.ref.WeakReference<>(fragment);
        mainActivity = new java.lang.ref.WeakReference<>(((it.feio.android.omninotes.MainActivity) (fragment.getActivity())));
    }


    @java.lang.Override
    protected java.util.List<it.feio.android.omninotes.models.NavigationItem> doInBackground(java.lang.Void... params) {
        return buildMainMenu();
    }


    @java.lang.Override
    protected void onPostExecute(final java.util.List<it.feio.android.omninotes.models.NavigationItem> items) {
        switch(MUID_STATIC) {
            // MainMenuTask_0_InvalidViewFocusOperatorMutator
            case 30: {
                /**
                * Inserted by Kadabra
                */
                navDrawer = mainActivity.get().findViewById(it.feio.android.omninotes.R.id.drawer_nav_list);
                navDrawer.requestFocus();
                break;
            }
            // MainMenuTask_1_ViewComponentNotVisibleOperatorMutator
            case 1030: {
                /**
                * Inserted by Kadabra
                */
                navDrawer = mainActivity.get().findViewById(it.feio.android.omninotes.R.id.drawer_nav_list);
                navDrawer.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            navDrawer = mainActivity.get().findViewById(it.feio.android.omninotes.R.id.drawer_nav_list);
            break;
        }
    }
    if (isAlive()) {
        navDrawer.setAdapter(new it.feio.android.omninotes.models.adapters.NavDrawerAdapter(mainActivity.get(), items));
        navDrawer.setOnItemClickListener((android.widget.AdapterView<?> arg0,android.view.View arg1,int position,long arg3) -> {
            java.lang.String navigation;
            navigation = fragmentWeakReference.get().getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list_codes)[items.get(position).getArrayIndex()];
            updateNavigation(position, navigation);
        });
        navDrawer.justifyListViewHeightBasedOnChildren();
    }
}


private void updateNavigation(int position, java.lang.String navigation) {
    if (mainActivity.get().updateNavigation(navigation)) {
        navDrawer.setItemChecked(position, true);
        navDrawer.setItemChecked(0, false)// Called to force redraw
        ;// Called to force redraw

        switch(MUID_STATIC) {
            // MainMenuTask_2_RandomActionIntentDefinitionOperatorMutator
            case 2030: {
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
            mainActivity.get().getIntent().setAction(android.content.Intent.ACTION_MAIN);
            break;
        }
    }
    de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.NavigationUpdatedEvent(navDrawer.getItemAtPosition(position)));
}
}


private boolean isAlive() {
return (((fragmentWeakReference.get() != null) && fragmentWeakReference.get().isAdded()) && (fragmentWeakReference.get().getActivity() != null)) && (!fragmentWeakReference.get().getActivity().isFinishing());
}


private java.util.List<it.feio.android.omninotes.models.NavigationItem> buildMainMenu() {
if (!isAlive()) {
    return new java.util.ArrayList<>();
}
java.lang.String[] mNavigationArray;
mNavigationArray = mainActivity.get().getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list);
android.content.res.TypedArray mNavigationIconsArray;
mNavigationIconsArray = mainActivity.get().getResources().obtainTypedArray(it.feio.android.omninotes.R.array.navigation_list_icons);
android.content.res.TypedArray mNavigationIconsSelectedArray;
mNavigationIconsSelectedArray = mainActivity.get().getResources().obtainTypedArray(it.feio.android.omninotes.R.array.navigation_list_icons_selected);
final java.util.List<it.feio.android.omninotes.models.NavigationItem> items;
items = new java.util.ArrayList<>();
for (int i = 0; i < mNavigationArray.length; i++) {
    if (!checkSkippableItem(i)) {
        it.feio.android.omninotes.models.NavigationItem item;
        item = new it.feio.android.omninotes.models.NavigationItem(i, mNavigationArray[i], mNavigationIconsArray.getResourceId(i, 0), mNavigationIconsSelectedArray.getResourceId(i, 0));
        items.add(item);
    }
}
return items;
}


private boolean checkSkippableItem(int i) {
boolean skippable;
skippable = false;
boolean dynamicMenu;
dynamicMenu = com.pixplicity.easyprefs.library.Prefs.getBoolean(it.feio.android.omninotes.utils.ConstantsBase.PREF_DYNAMIC_MENU, true);
it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable dynamicNavigationLookupTable;
dynamicNavigationLookupTable = null;
if (dynamicMenu) {
    dynamicNavigationLookupTable = it.feio.android.omninotes.models.misc.DynamicNavigationLookupTable.getInstance();
}
switch (i) {
    case it.feio.android.omninotes.utils.Navigation.REMINDERS :
        if (dynamicMenu && (dynamicNavigationLookupTable.getReminders() == 0)) {
            skippable = true;
        }
        break;
    case it.feio.android.omninotes.utils.Navigation.UNCATEGORIZED :
        boolean showUncategorized;
        showUncategorized = com.pixplicity.easyprefs.library.Prefs.getBoolean(it.feio.android.omninotes.utils.ConstantsBase.PREF_SHOW_UNCATEGORIZED, false);
        if ((!showUncategorized) || (dynamicMenu && (dynamicNavigationLookupTable.getUncategorized() == 0))) {
            skippable = true;
        }
        break;
    case it.feio.android.omninotes.utils.Navigation.ARCHIVE :
        if (dynamicMenu && (dynamicNavigationLookupTable.getArchived() == 0)) {
            skippable = true;
        }
        break;
    case it.feio.android.omninotes.utils.Navigation.TRASH :
        if (dynamicMenu && (dynamicNavigationLookupTable.getTrashed() == 0)) {
            skippable = true;
        }
        break;
}
return skippable;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
