package de.danoeh.antennapod.ui.home;
import org.greenrobot.eventbus.ThreadMode;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import de.danoeh.antennapod.activity.MainActivity;
import de.danoeh.antennapod.fragment.SearchFragment;
import de.danoeh.antennapod.ui.home.sections.InboxSection;
import androidx.fragment.app.Fragment;
import de.danoeh.antennapod.ui.home.sections.QueueSection;
import androidx.core.content.ContextCompat;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.os.Build;
import androidx.appcompat.widget.Toolbar;
import de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection;
import de.danoeh.antennapod.ui.home.sections.DownloadsSection;
import android.content.pm.PackageManager;
import java.util.List;
import androidx.fragment.app.FragmentContainerView;
import de.danoeh.antennapod.event.FeedListUpdateEvent;
import de.danoeh.antennapod.ui.home.sections.AllowNotificationsSection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import android.content.SharedPreferences;
import android.util.Log;
import android.os.Bundle;
import android.view.ViewGroup;
import io.reactivex.schedulers.Schedulers;
import android.os.Handler;
import android.text.TextUtils;
import io.reactivex.Observable;
import de.danoeh.antennapod.databinding.HomeFragmentBinding;
import android.view.MenuItem;
import android.view.View;
import de.danoeh.antennapod.view.LiftOnScrollListener;
import android.os.Looper;
import de.danoeh.antennapod.ui.home.sections.SubscriptionsSection;
import android.Manifest;
import android.view.LayoutInflater;
import de.danoeh.antennapod.core.storage.DBReader;
import de.danoeh.antennapod.core.util.download.FeedUpdateManager;
import de.danoeh.antennapod.event.FeedUpdateRunningEvent;
import de.danoeh.antennapod.core.menuhandler.MenuItemUtils;
import java.util.Arrays;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows unread or recently published episodes
 */
public class HomeFragment extends androidx.fragment.app.Fragment implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "HomeFragment";

    public static final java.lang.String PREF_NAME = "PrefHomeFragment";

    public static final java.lang.String PREF_HIDDEN_SECTIONS = "PrefHomeSectionsString";

    public static final java.lang.String PREF_DISABLE_NOTIFICATION_PERMISSION_NAG = "DisableNotificationPermissionNag";

    private static final java.lang.String KEY_UP_ARROW = "up_arrow";

    private boolean displayUpArrow;

    private de.danoeh.antennapod.databinding.HomeFragmentBinding viewBinding;

    private io.reactivex.disposables.Disposable disposable;

    @androidx.annotation.NonNull
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewBinding = de.danoeh.antennapod.databinding.HomeFragmentBinding.inflate(inflater);
        viewBinding.toolbar.inflateMenu(de.danoeh.antennapod.R.menu.home);
        viewBinding.toolbar.setOnMenuItemClickListener(this);
        if (savedInstanceState != null) {
            displayUpArrow = savedInstanceState.getBoolean(de.danoeh.antennapod.ui.home.HomeFragment.KEY_UP_ARROW);
        }
        viewBinding.homeScrollView.setOnScrollChangeListener(new de.danoeh.antennapod.view.LiftOnScrollListener(viewBinding.appbar));
        ((de.danoeh.antennapod.activity.MainActivity) (requireActivity())).setupToolbarToggle(viewBinding.toolbar, displayUpArrow);
        populateSectionList();
        updateWelcomeScreenVisibility();
        viewBinding.swipeRefresh.setDistanceToTriggerSync(getResources().getInteger(de.danoeh.antennapod.R.integer.swipe_refresh_distance));
        viewBinding.swipeRefresh.setOnRefreshListener(() -> {
            de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext());
            new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> viewBinding.swipeRefresh.setRefreshing(false), getResources().getInteger(de.danoeh.antennapod.R.integer.swipe_to_refresh_duration_in_ms));
        });
        return viewBinding.getRoot();
    }


    private void populateSectionList() {
        viewBinding.homeContainer.removeAllViews();
        if ((android.os.Build.VERSION.SDK_INT >= 33) && (androidx.core.content.ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED)) {
            android.content.SharedPreferences prefs;
            prefs = getContext().getSharedPreferences(de.danoeh.antennapod.ui.home.HomeFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
            if (!prefs.getBoolean(de.danoeh.antennapod.ui.home.HomeFragment.PREF_DISABLE_NOTIFICATION_PERMISSION_NAG, false)) {
                addSection(new de.danoeh.antennapod.ui.home.sections.AllowNotificationsSection());
            }
        }
        java.util.List<java.lang.String> hiddenSections;
        hiddenSections = de.danoeh.antennapod.ui.home.HomeFragment.getHiddenSections(getContext());
        java.lang.String[] sectionTags;
        sectionTags = getResources().getStringArray(de.danoeh.antennapod.R.array.home_section_tags);
        for (java.lang.String sectionTag : sectionTags) {
            if (hiddenSections.contains(sectionTag)) {
                continue;
            }
            addSection(getSection(sectionTag));
        }
    }


    private void addSection(androidx.fragment.app.Fragment section) {
        androidx.fragment.app.FragmentContainerView containerView;
        containerView = new androidx.fragment.app.FragmentContainerView(getContext());
        containerView.setId(android.view.View.generateViewId());
        viewBinding.homeContainer.addView(containerView);
        getChildFragmentManager().beginTransaction().add(containerView.getId(), section).commit();
    }


    private androidx.fragment.app.Fragment getSection(java.lang.String tag) {
        switch (tag) {
            case de.danoeh.antennapod.ui.home.sections.QueueSection.TAG :
                return new de.danoeh.antennapod.ui.home.sections.QueueSection();
            case de.danoeh.antennapod.ui.home.sections.InboxSection.TAG :
                return new de.danoeh.antennapod.ui.home.sections.InboxSection();
            case de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection.TAG :
                return new de.danoeh.antennapod.ui.home.sections.EpisodesSurpriseSection();
            case de.danoeh.antennapod.ui.home.sections.SubscriptionsSection.TAG :
                return new de.danoeh.antennapod.ui.home.sections.SubscriptionsSection();
            case de.danoeh.antennapod.ui.home.sections.DownloadsSection.TAG :
                return new de.danoeh.antennapod.ui.home.sections.DownloadsSection();
            default :
                return null;
        }
    }


    public static java.util.List<java.lang.String> getHiddenSections(android.content.Context context) {
        android.content.SharedPreferences prefs;
        prefs = context.getSharedPreferences(de.danoeh.antennapod.ui.home.HomeFragment.PREF_NAME, android.content.Context.MODE_PRIVATE);
        java.lang.String hiddenSectionsString;
        hiddenSectionsString = prefs.getString(de.danoeh.antennapod.ui.home.HomeFragment.PREF_HIDDEN_SECTIONS, "");
        return new java.util.ArrayList<>(java.util.Arrays.asList(android.text.TextUtils.split(hiddenSectionsString, ",")));
    }


    @org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEventMainThread(de.danoeh.antennapod.event.FeedUpdateRunningEvent event) {
        de.danoeh.antennapod.core.menuhandler.MenuItemUtils.updateRefreshMenuItem(viewBinding.toolbar.getMenu(), de.danoeh.antennapod.R.id.refresh_item, event.isFeedUpdateRunning);
    }


    @java.lang.Override
    public boolean onMenuItemClick(android.view.MenuItem item) {
        if (item.getItemId() == de.danoeh.antennapod.R.id.homesettings_items) {
            switch(MUID_STATIC) {
                // HomeFragment_0_BuggyGUIListenerOperatorMutator
                case 162: {
                    de.danoeh.antennapod.ui.home.HomeSectionsSettingsDialog.open(getContext(), null);
                    break;
                }
                default: {
                de.danoeh.antennapod.ui.home.HomeSectionsSettingsDialog.open(getContext(), (android.content.DialogInterface dialogInterface,int i) -> populateSectionList());
                break;
            }
        }
        return true;
    } else if (item.getItemId() == de.danoeh.antennapod.R.id.refresh_item) {
        de.danoeh.antennapod.core.util.download.FeedUpdateManager.runOnceOrAsk(requireContext());
        return true;
    } else if (item.getItemId() == de.danoeh.antennapod.R.id.action_search) {
        ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadChildFragment(de.danoeh.antennapod.fragment.SearchFragment.newInstance());
        return true;
    }
    return super.onOptionsItemSelected(item);
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
    outState.putBoolean(de.danoeh.antennapod.ui.home.HomeFragment.KEY_UP_ARROW, displayUpArrow);
    super.onSaveInstanceState(outState);
}


@java.lang.Override
public void onStart() {
    super.onStart();
    org.greenrobot.eventbus.EventBus.getDefault().register(this);
}


@java.lang.Override
public void onStop() {
    super.onStop();
    org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
}


@org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
public void onFeedListChanged(de.danoeh.antennapod.event.FeedListUpdateEvent event) {
    updateWelcomeScreenVisibility();
}


private void updateWelcomeScreenVisibility() {
    if (disposable != null) {
        disposable.dispose();
    }
    disposable = io.reactivex.Observable.fromCallable(() -> de.danoeh.antennapod.core.storage.DBReader.getNavDrawerData(de.danoeh.antennapod.storage.preferences.UserPreferences.getSubscriptionsFilter()).items.size()).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((java.lang.Integer numSubscriptions) -> {
        viewBinding.welcomeContainer.setVisibility(numSubscriptions == 0 ? android.view.View.VISIBLE : android.view.View.GONE);
        viewBinding.homeContainer.setVisibility(numSubscriptions == 0 ? android.view.View.GONE : android.view.View.VISIBLE);
    }, (java.lang.Throwable error) -> android.util.Log.e(de.danoeh.antennapod.ui.home.HomeFragment.TAG, android.util.Log.getStackTraceString(error)));
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
