package de.danoeh.antennapod.fragment.preferences;
import de.danoeh.antennapod.fragment.InboxFragment;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.fragment.AllEpisodesFragment;
import android.os.Bundle;
import de.danoeh.antennapod.fragment.CompletedDownloadsFragment;
import de.danoeh.antennapod.fragment.PlaybackHistoryFragment;
import androidx.preference.PreferenceFragmentCompat;
import de.danoeh.antennapod.dialog.SwipeActionsDialog;
import de.danoeh.antennapod.fragment.FeedItemlistFragment;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.fragment.QueueFragment;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SwipePreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREF_SWIPE_QUEUE = "prefSwipeQueue";

    private static final java.lang.String PREF_SWIPE_INBOX = "prefSwipeInbox";

    private static final java.lang.String PREF_SWIPE_EPISODES = "prefSwipeEpisodes";

    private static final java.lang.String PREF_SWIPE_DOWNLOADS = "prefSwipeDownloads";

    private static final java.lang.String PREF_SWIPE_FEED = "prefSwipeFeed";

    private static final java.lang.String PREF_SWIPE_HISTORY = "prefSwipeHistory";

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_swipe);
        findPreference(de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment.PREF_SWIPE_QUEUE).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            new de.danoeh.antennapod.dialog.SwipeActionsDialog(requireContext(), de.danoeh.antennapod.fragment.QueueFragment.TAG).show(() -> {
            });
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment.PREF_SWIPE_INBOX).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            new de.danoeh.antennapod.dialog.SwipeActionsDialog(requireContext(), de.danoeh.antennapod.fragment.InboxFragment.TAG).show(() -> {
            });
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment.PREF_SWIPE_EPISODES).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            new de.danoeh.antennapod.dialog.SwipeActionsDialog(requireContext(), de.danoeh.antennapod.fragment.AllEpisodesFragment.TAG).show(() -> {
            });
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment.PREF_SWIPE_DOWNLOADS).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            new de.danoeh.antennapod.dialog.SwipeActionsDialog(requireContext(), de.danoeh.antennapod.fragment.CompletedDownloadsFragment.TAG).show(() -> {
            });
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment.PREF_SWIPE_FEED).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            new de.danoeh.antennapod.dialog.SwipeActionsDialog(requireContext(), de.danoeh.antennapod.fragment.FeedItemlistFragment.TAG).show(() -> {
            });
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.SwipePreferencesFragment.PREF_SWIPE_HISTORY).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            new de.danoeh.antennapod.dialog.SwipeActionsDialog(requireContext(), de.danoeh.antennapod.fragment.PlaybackHistoryFragment.TAG).show(() -> {
            });
            return true;
        });
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.swipeactions_label);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
