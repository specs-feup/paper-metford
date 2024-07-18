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
package it.feio.android.omninotes;
import it.feio.android.omninotes.models.Category;
import it.feio.android.omninotes.async.MainMenuTask;
import it.feio.android.omninotes.async.bus.NotesLoadedEvent;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.app.ActionBarDrawerToggle;
import de.greenrobot.event.EventBus;
import it.feio.android.omninotes.async.bus.DynamicNavigationReadyEvent;
import androidx.fragment.app.Fragment;
import android.animation.ValueAnimator;
import it.feio.android.omninotes.async.CategoryMenuTask;
import it.feio.android.omninotes.async.bus.NavigationUpdatedEvent;
import static it.feio.android.omninotes.async.bus.SwitchFragmentEvent.Direction.CHILDREN;
import androidx.core.view.GravityCompat;
import it.feio.android.omninotes.async.bus.NotesUpdatedEvent;
import androidx.drawerlayout.widget.DrawerLayout;
import it.feio.android.omninotes.models.NavigationItem;
import it.feio.android.omninotes.async.bus.CategoriesUpdatedEvent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.os.Handler;
import android.os.AsyncTask;
import it.feio.android.omninotes.async.bus.NavigationUpdatedNavDrawerClosedEvent;
import android.view.View;
import it.feio.android.omninotes.async.bus.SwitchFragmentEvent;
import android.view.LayoutInflater;
import it.feio.android.omninotes.helpers.LogDelegate;
import it.feio.android.omninotes.utils.Display;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NavigationDrawerFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    static final int BURGER = 0;

    static final int ARROW = 1;

    androidx.appcompat.app.ActionBarDrawerToggle mDrawerToggle;

    androidx.drawerlayout.widget.DrawerLayout mDrawerLayout;

    private it.feio.android.omninotes.MainActivity mActivity;

    private boolean alreadyInitialized;

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // NavigationDrawerFragment_0_LengthyGUICreationOperatorMutator
            case 140: {
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
    setRetainInstance(true);
}


@java.lang.Override
public void onStart() {
    super.onStart();
    de.greenrobot.event.EventBus.getDefault().register(this);
}


@java.lang.Override
public void onStop() {
    super.onStop();
    de.greenrobot.event.EventBus.getDefault().unregister(this);
}


@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    return inflater.inflate(it.feio.android.omninotes.R.layout.fragment_navigation_drawer, container, false);
}


@java.lang.Override
public void onActivityCreated(android.os.Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mActivity = ((it.feio.android.omninotes.MainActivity) (getActivity()));
    init();
}


private it.feio.android.omninotes.MainActivity getMainActivity() {
    return ((it.feio.android.omninotes.MainActivity) (getActivity()));
}


public void onEventMainThread(it.feio.android.omninotes.async.bus.DynamicNavigationReadyEvent event) {
    if (alreadyInitialized) {
        alreadyInitialized = false;
    } else {
        refreshMenus();
    }
}


public void onEvent(it.feio.android.omninotes.async.bus.CategoriesUpdatedEvent event) {
    refreshMenus();
}


public void onEventAsync(it.feio.android.omninotes.async.bus.NotesUpdatedEvent event) {
    alreadyInitialized = false;
}


public void onEvent(it.feio.android.omninotes.async.bus.NotesLoadedEvent event) {
    if (mDrawerLayout != null) {
        if (!it.feio.android.omninotes.NavigationDrawerFragment.isDoublePanelActive()) {
            mDrawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }
    if (getMainActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
        init();
    }
    refreshMenus();
    alreadyInitialized = true;
}


public void onEvent(it.feio.android.omninotes.async.bus.SwitchFragmentEvent event) {
    if (it.feio.android.omninotes.async.bus.SwitchFragmentEvent.Direction.CHILDREN.equals(event.getDirection())) {
        animateBurger(it.feio.android.omninotes.NavigationDrawerFragment.ARROW);
    } else {
        animateBurger(it.feio.android.omninotes.NavigationDrawerFragment.BURGER);
    }
}


public void onEvent(it.feio.android.omninotes.async.bus.NavigationUpdatedEvent navigationUpdatedEvent) {
    if (navigationUpdatedEvent.navigationItem.getClass().isAssignableFrom(it.feio.android.omninotes.models.NavigationItem.class)) {
        mActivity.getSupportActionBar().setTitle(((it.feio.android.omninotes.models.NavigationItem) (navigationUpdatedEvent.navigationItem)).getText());
    } else {
        mActivity.getSupportActionBar().setTitle(((it.feio.android.omninotes.models.Category) (navigationUpdatedEvent.navigationItem)).getName());
    }
    if (mDrawerLayout != null) {
        if (!it.feio.android.omninotes.NavigationDrawerFragment.isDoublePanelActive()) {
            mDrawerLayout.closeDrawer(androidx.core.view.GravityCompat.START);
        }
        new android.os.Handler().postDelayed(() -> de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.NavigationUpdatedNavDrawerClosedEvent(navigationUpdatedEvent.navigationItem)), 400);
    }
}


public void init() {
    it.feio.android.omninotes.helpers.LogDelegate.v("Started navigation drawer initialization");
    switch(MUID_STATIC) {
        // NavigationDrawerFragment_1_InvalidViewFocusOperatorMutator
        case 1140: {
            /**
            * Inserted by Kadabra
            */
            mDrawerLayout = mActivity.findViewById(it.feio.android.omninotes.R.id.drawer_layout);
            mDrawerLayout.requestFocus();
            break;
        }
        // NavigationDrawerFragment_2_ViewComponentNotVisibleOperatorMutator
        case 2140: {
            /**
            * Inserted by Kadabra
            */
            mDrawerLayout = mActivity.findViewById(it.feio.android.omninotes.R.id.drawer_layout);
            mDrawerLayout.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mDrawerLayout = mActivity.findViewById(it.feio.android.omninotes.R.id.drawer_layout);
        break;
    }
}
mDrawerLayout.setFocusableInTouchMode(false);
android.view.View leftDrawer;
switch(MUID_STATIC) {
    // NavigationDrawerFragment_3_InvalidViewFocusOperatorMutator
    case 3140: {
        /**
        * Inserted by Kadabra
        */
        leftDrawer = getView().findViewById(it.feio.android.omninotes.R.id.left_drawer);
        leftDrawer.requestFocus();
        break;
    }
    // NavigationDrawerFragment_4_ViewComponentNotVisibleOperatorMutator
    case 4140: {
        /**
        * Inserted by Kadabra
        */
        leftDrawer = getView().findViewById(it.feio.android.omninotes.R.id.left_drawer);
        leftDrawer.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    leftDrawer = getView().findViewById(it.feio.android.omninotes.R.id.left_drawer);
    break;
}
}
int leftDrawerBottomPadding;
leftDrawerBottomPadding = it.feio.android.omninotes.utils.Display.getNavigationBarHeightKitkat(getActivity());
leftDrawer.setPadding(leftDrawer.getPaddingLeft(), leftDrawer.getPaddingTop(), leftDrawer.getPaddingRight(), leftDrawerBottomPadding);
// ActionBarDrawerToggle± ties together the the proper interactions
// between the sliding drawer and the action bar app icon
mDrawerToggle = new androidx.appcompat.app.ActionBarDrawerToggle(mActivity, mDrawerLayout, getMainActivity().getToolbar(), it.feio.android.omninotes.R.string.drawer_open, it.feio.android.omninotes.R.string.drawer_close) {
public void onDrawerClosed(android.view.View view) {
    mActivity.supportInvalidateOptionsMenu();
}


public void onDrawerOpened(android.view.View drawerView) {
    mActivity.commitPending();
    mActivity.finishActionMode();
}

};
if (it.feio.android.omninotes.NavigationDrawerFragment.isDoublePanelActive()) {
mDrawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_OPEN);
}
// Styling options
mDrawerLayout.setDrawerShadow(it.feio.android.omninotes.R.drawable.drawer_shadow, androidx.core.view.GravityCompat.START);
mDrawerLayout.addDrawerListener(mDrawerToggle);
mDrawerToggle.setDrawerIndicatorEnabled(true);
it.feio.android.omninotes.helpers.LogDelegate.v("Finished navigation drawer initialization");
}


private void refreshMenus() {
buildMainMenu();
it.feio.android.omninotes.helpers.LogDelegate.v("Finished main menu initialization");
buildCategoriesMenu();
it.feio.android.omninotes.helpers.LogDelegate.v("Finished categories menu initialization");
mDrawerToggle.syncState();
}


private void buildCategoriesMenu() {
it.feio.android.omninotes.async.CategoryMenuTask task;
task = new it.feio.android.omninotes.async.CategoryMenuTask(this);
task.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
}


private void buildMainMenu() {
it.feio.android.omninotes.async.MainMenuTask task;
task = new it.feio.android.omninotes.async.MainMenuTask(this);
task.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
}


void animateBurger(int targetShape) {
if (mDrawerToggle != null) {
if ((targetShape != it.feio.android.omninotes.NavigationDrawerFragment.BURGER) && (targetShape != it.feio.android.omninotes.NavigationDrawerFragment.ARROW)) {
    return;
}
android.animation.ValueAnimator anim;
switch(MUID_STATIC) {
    // NavigationDrawerFragment_5_BinaryMutator
    case 5140: {
        anim = android.animation.ValueAnimator.ofFloat((targetShape - 1) % 2, targetShape);
        break;
    }
    default: {
    anim = android.animation.ValueAnimator.ofFloat((targetShape + 1) % 2, targetShape);
    break;
}
}
anim.addUpdateListener((android.animation.ValueAnimator valueAnimator) -> {
float slideOffset;
slideOffset = ((java.lang.Float) (valueAnimator.getAnimatedValue()));
mDrawerToggle.onDrawerSlide(mDrawerLayout, slideOffset);
});
anim.setInterpolator(new android.view.animation.DecelerateInterpolator());
anim.setDuration(500);
anim.start();
}
}


public static boolean isDoublePanelActive() {
// Resources resources = OmniNotes.getAppContext().getResources();
// return resources.getDimension(R.dimen.navigation_drawer_width) == resources.getDimension(R.dimen
// .navigation_drawer_reserved_space);
return false;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
