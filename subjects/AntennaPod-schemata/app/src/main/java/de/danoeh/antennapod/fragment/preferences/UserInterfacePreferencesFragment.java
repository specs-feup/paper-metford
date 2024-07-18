package de.danoeh.antennapod.fragment.preferences;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import android.os.Bundle;
import de.danoeh.antennapod.event.PlayerStatusEvent;
import org.greenrobot.eventbus.EventBus;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.dialog.DrawerPreferencesDialog;
import androidx.preference.Preference;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.dialog.FeedSortDialog;
import android.widget.ListView;
import de.danoeh.antennapod.event.UnreadItemsUpdateEvent;
import android.os.Build;
import de.danoeh.antennapod.dialog.SubscriptionsFilterDialog;
import androidx.preference.PreferenceFragmentCompat;
import java.util.List;
import com.google.android.material.snackbar.Snackbar;
import de.danoeh.antennapod.activity.PreferenceActivity;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class UserInterfacePreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREF_SWIPE = "prefSwipe";

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_user_interface);
        setupInterfaceScreen();
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.user_interface_label);
    }


    private void setupInterfaceScreen() {
        androidx.preference.Preference.OnPreferenceChangeListener restartApp;
        restartApp = (androidx.preference.Preference preference,java.lang.Object newValue) -> {
            androidx.core.app.ActivityCompat.recreate(getActivity());
            return true;
        };
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_THEME).setOnPreferenceChangeListener(restartApp);
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_THEME_BLACK).setOnPreferenceChangeListener(restartApp);
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_TINTED_COLORS).setOnPreferenceChangeListener(restartApp);
        if (android.os.Build.VERSION.SDK_INT < 31) {
            findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_TINTED_COLORS).setVisible(false);
        }
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_SHOW_TIME_LEFT).setOnPreferenceChangeListener((androidx.preference.Preference preference,java.lang.Object newValue) -> {
            de.danoeh.antennapod.storage.preferences.UserPreferences.setShowRemainTimeSetting(((java.lang.Boolean) (newValue)));
            org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.UnreadItemsUpdateEvent());
            org.greenrobot.eventbus.EventBus.getDefault().post(new de.danoeh.antennapod.event.PlayerStatusEvent());
            return true;
        });
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_HIDDEN_DRAWER_ITEMS).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.DrawerPreferencesDialog.show(getContext(), null);
            return true;
        });
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_COMPACT_NOTIFICATION_BUTTONS).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            showNotificationButtonsDialog();
            return true;
        });
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_FILTER_FEED).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.SubscriptionsFilterDialog.showDialog(requireContext());
            return true;
        });
        findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_DRAWER_FEED_ORDER).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.FeedSortDialog.showDialog(requireContext());
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.UserInterfacePreferencesFragment.PREF_SWIPE).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).openScreen(de.danoeh.antennapod.R.xml.preferences_swipe);
            return true;
        });
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            findPreference(de.danoeh.antennapod.storage.preferences.UserPreferences.PREF_EXPANDED_NOTIFICATION).setVisible(false);
        }
    }


    private void showNotificationButtonsDialog() {
        final android.content.Context context;
        context = getActivity();
        final java.util.List<java.lang.Integer> preferredButtons;
        preferredButtons = de.danoeh.antennapod.storage.preferences.UserPreferences.getCompactNotificationButtons();
        final java.lang.String[] allButtonNames;
        allButtonNames = context.getResources().getStringArray(de.danoeh.antennapod.R.array.compact_notification_buttons_options);
        boolean[] checked// booleans default to false in java
        ;// booleans default to false in java

        checked = new boolean[allButtonNames.length];
        for (int i = 0; i < checked.length; i++) {
            if (preferredButtons.contains(i)) {
                checked[i] = true;
            }
        }
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context);
        builder.setTitle(java.lang.String.format(context.getResources().getString(de.danoeh.antennapod.R.string.pref_compact_notification_buttons_dialog_title), 2));
        builder.setMultiChoiceItems(allButtonNames, checked, (android.content.DialogInterface dialog,int which,boolean isChecked) -> {
            checked[which] = isChecked;
            if (isChecked) {
                if (preferredButtons.size() < 2) {
                    preferredButtons.add(which);
                } else {
                    // Only allow a maximum of two selections. This is because the notification
                    // on the lock screen can only display 3 buttons, and the play/pause button
                    // is always included.
                    checked[which] = false;
                    android.widget.ListView selectionView;
                    selectionView = ((androidx.appcompat.app.AlertDialog) (dialog)).getListView();
                    selectionView.setItemChecked(which, false);
                    com.google.android.material.snackbar.Snackbar.make(selectionView, java.lang.String.format(context.getResources().getString(de.danoeh.antennapod.R.string.pref_compact_notification_buttons_dialog_error), 2), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
                }
            } else {
                preferredButtons.remove(((java.lang.Integer) (which)));
            }
        });
        switch(MUID_STATIC) {
            // UserInterfacePreferencesFragment_0_BuggyGUIListenerOperatorMutator
            case 103: {
                builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
                break;
            }
            default: {
            builder.setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> de.danoeh.antennapod.storage.preferences.UserPreferences.setCompactNotificationButtons(preferredButtons));
            break;
        }
    }
    builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
    builder.create().show();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
