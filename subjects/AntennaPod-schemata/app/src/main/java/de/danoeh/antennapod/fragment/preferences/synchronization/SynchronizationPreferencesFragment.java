package de.danoeh.antennapod.fragment.preferences.synchronization;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import android.text.format.DateUtils;
import de.danoeh.antennapod.core.sync.SynchronizationProviderViewData;
import de.danoeh.antennapod.event.SyncServiceEvent;
import de.danoeh.antennapod.R;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.widget.ImageView;
import androidx.preference.PreferenceFragmentCompat;
import android.widget.TextView;
import android.widget.ListAdapter;
import de.danoeh.antennapod.activity.PreferenceActivity;
import de.danoeh.antennapod.core.sync.SynchronizationSettings;
import androidx.core.text.HtmlCompat;
import android.os.Bundle;
import android.view.ViewGroup;
import de.danoeh.antennapod.dialog.AuthenticationDialog;
import android.text.Spanned;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.core.sync.SynchronizationCredentials;
import android.view.View;
import de.danoeh.antennapod.core.sync.SyncService;
import androidx.preference.Preference;
import android.view.LayoutInflater;
import com.google.android.material.snackbar.Snackbar;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SynchronizationPreferencesFragment extends androidx.preference.PreferenceFragmentCompat {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String PREFERENCE_SYNCHRONIZATION_DESCRIPTION = "preference_synchronization_description";

    private static final java.lang.String PREFERENCE_GPODNET_SETLOGIN_INFORMATION = "pref_gpodnet_setlogin_information";

    private static final java.lang.String PREFERENCE_SYNC = "pref_synchronization_sync";

    private static final java.lang.String PREFERENCE_FORCE_FULL_SYNC = "pref_synchronization_force_full_sync";

    private static final java.lang.String PREFERENCE_LOGOUT = "pref_synchronization_logout";

    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(de.danoeh.antennapod.R.xml.preferences_synchronization);
        setupScreen();
        updateScreen();
    }


    @java.lang.Override
    public void onStart() {
        super.onStart();
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.synchronization_pref);
        updateScreen();
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
    }


    @java.lang.Override
    public void onStop() {
        super.onStop();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
        ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setSubtitle("");
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN, sticky = true)
    public void syncStatusChanged(de.danoeh.antennapod.event.SyncServiceEvent event) {
        if (!de.danoeh.antennapod.core.sync.SynchronizationSettings.isProviderConnected()) {
            return;
        }
        updateScreen();
        if ((event.getMessageResId() == de.danoeh.antennapod.R.string.sync_status_error) || (event.getMessageResId() == de.danoeh.antennapod.R.string.sync_status_success)) {
            updateLastSyncReport(de.danoeh.antennapod.core.sync.SynchronizationSettings.isLastSyncSuccessful(), de.danoeh.antennapod.core.sync.SynchronizationSettings.getLastSyncAttempt());
        } else {
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setSubtitle(event.getMessageResId());
        }
    }


    private void setupScreen() {
        final android.app.Activity activity;
        activity = getActivity();
        findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_GPODNET_SETLOGIN_INFORMATION).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.dialog.AuthenticationDialog dialog;
            dialog = new de.danoeh.antennapod.dialog.AuthenticationDialog(activity, de.danoeh.antennapod.R.string.pref_gpodnet_setlogin_information_title, false, de.danoeh.antennapod.core.sync.SynchronizationCredentials.getUsername(), null) {
                @java.lang.Override
                protected void onConfirmed(java.lang.String username, java.lang.String password) {
                    de.danoeh.antennapod.core.sync.SynchronizationCredentials.setPassword(password);
                }

            };
            dialog.show();
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_SYNC).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.core.sync.SyncService.syncImmediately(getActivity().getApplicationContext());
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_FORCE_FULL_SYNC).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.core.sync.SyncService.fullSync(getContext());
            return true;
        });
        findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_LOGOUT).setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
            de.danoeh.antennapod.core.sync.SynchronizationCredentials.clear(getContext());
            com.google.android.material.snackbar.Snackbar.make(getView(), de.danoeh.antennapod.R.string.pref_synchronization_logout_toast, com.google.android.material.snackbar.Snackbar.LENGTH_LONG).show();
            de.danoeh.antennapod.core.sync.SynchronizationSettings.setSelectedSyncProvider(null);
            updateScreen();
            return true;
        });
    }


    private void updateScreen() {
        final boolean loggedIn;
        loggedIn = de.danoeh.antennapod.core.sync.SynchronizationSettings.isProviderConnected();
        androidx.preference.Preference preferenceHeader;
        preferenceHeader = findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_SYNCHRONIZATION_DESCRIPTION);
        if (loggedIn) {
            de.danoeh.antennapod.core.sync.SynchronizationProviderViewData selectedProvider;
            selectedProvider = de.danoeh.antennapod.core.sync.SynchronizationProviderViewData.fromIdentifier(getSelectedSyncProviderKey());
            preferenceHeader.setTitle("");
            preferenceHeader.setSummary(selectedProvider.getSummaryResource());
            preferenceHeader.setIcon(selectedProvider.getIconResource());
            preferenceHeader.setOnPreferenceClickListener(null);
        } else {
            preferenceHeader.setTitle(de.danoeh.antennapod.R.string.synchronization_choose_title);
            preferenceHeader.setSummary(de.danoeh.antennapod.R.string.synchronization_summary_unchoosen);
            preferenceHeader.setIcon(de.danoeh.antennapod.R.drawable.ic_cloud);
            preferenceHeader.setOnPreferenceClickListener((androidx.preference.Preference preference) -> {
                chooseProviderAndLogin();
                return true;
            });
        }
        androidx.preference.Preference gpodnetSetLoginPreference;
        gpodnetSetLoginPreference = findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_GPODNET_SETLOGIN_INFORMATION);
        gpodnetSetLoginPreference.setVisible(isProviderSelected(de.danoeh.antennapod.core.sync.SynchronizationProviderViewData.GPODDER_NET));
        gpodnetSetLoginPreference.setEnabled(loggedIn);
        findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_SYNC).setEnabled(loggedIn);
        findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_FORCE_FULL_SYNC).setEnabled(loggedIn);
        findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_LOGOUT).setEnabled(loggedIn);
        if (loggedIn) {
            java.lang.String summary;
            summary = getString(de.danoeh.antennapod.R.string.synchronization_login_status, de.danoeh.antennapod.core.sync.SynchronizationCredentials.getUsername(), de.danoeh.antennapod.core.sync.SynchronizationCredentials.getHosturl());
            android.text.Spanned formattedSummary;
            formattedSummary = androidx.core.text.HtmlCompat.fromHtml(summary, androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY);
            findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_LOGOUT).setSummary(formattedSummary);
            updateLastSyncReport(de.danoeh.antennapod.core.sync.SynchronizationSettings.isLastSyncSuccessful(), de.danoeh.antennapod.core.sync.SynchronizationSettings.getLastSyncAttempt());
        } else {
            findPreference(de.danoeh.antennapod.fragment.preferences.synchronization.SynchronizationPreferencesFragment.PREFERENCE_LOGOUT).setSummary(null);
            ((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setSubtitle(null);
        }
    }


    private void chooseProviderAndLogin() {
        com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
        builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
        builder.setTitle(de.danoeh.antennapod.R.string.dialog_choose_sync_service_title);
        de.danoeh.antennapod.core.sync.SynchronizationProviderViewData[] providers;
        providers = de.danoeh.antennapod.core.sync.SynchronizationProviderViewData.values();
        android.widget.ListAdapter adapter;
        adapter = new android.widget.ArrayAdapter<de.danoeh.antennapod.core.sync.SynchronizationProviderViewData>(getContext(), de.danoeh.antennapod.R.layout.alertdialog_sync_provider_chooser, providers) {
            ViewHolder holder;

            class ViewHolder {
                android.widget.ImageView icon;

                android.widget.TextView title;
            }

            public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
                final android.view.LayoutInflater inflater;
                inflater = android.view.LayoutInflater.from(getContext());
                if (convertView == null) {
                    convertView = inflater.inflate(de.danoeh.antennapod.R.layout.alertdialog_sync_provider_chooser, null);
                    holder = new ViewHolder();
                    switch(MUID_STATIC) {
                        // SynchronizationPreferencesFragment_0_InvalidViewFocusOperatorMutator
                        case 88: {
                            /**
                            * Inserted by Kadabra
                            */
                            holder.icon = ((android.widget.ImageView) (convertView.findViewById(de.danoeh.antennapod.R.id.icon)));
                            holder.icon.requestFocus();
                            break;
                        }
                        // SynchronizationPreferencesFragment_1_ViewComponentNotVisibleOperatorMutator
                        case 1088: {
                            /**
                            * Inserted by Kadabra
                            */
                            holder.icon = ((android.widget.ImageView) (convertView.findViewById(de.danoeh.antennapod.R.id.icon)));
                            holder.icon.setVisibility(android.view.View.INVISIBLE);
                            break;
                        }
                        default: {
                        holder.icon = ((android.widget.ImageView) (convertView.findViewById(de.danoeh.antennapod.R.id.icon)));
                        break;
                    }
                }
                switch(MUID_STATIC) {
                    // SynchronizationPreferencesFragment_2_InvalidViewFocusOperatorMutator
                    case 2088: {
                        /**
                        * Inserted by Kadabra
                        */
                        holder.title = ((android.widget.TextView) (convertView.findViewById(de.danoeh.antennapod.R.id.title)));
                        holder.title.requestFocus();
                        break;
                    }
                    // SynchronizationPreferencesFragment_3_ViewComponentNotVisibleOperatorMutator
                    case 3088: {
                        /**
                        * Inserted by Kadabra
                        */
                        holder.title = ((android.widget.TextView) (convertView.findViewById(de.danoeh.antennapod.R.id.title)));
                        holder.title.setVisibility(android.view.View.INVISIBLE);
                        break;
                    }
                    default: {
                    holder.title = ((android.widget.TextView) (convertView.findViewById(de.danoeh.antennapod.R.id.title)));
                    break;
                }
            }
            convertView.setTag(holder);
        } else {
            holder = ((ViewHolder) (convertView.getTag()));
        }
        de.danoeh.antennapod.core.sync.SynchronizationProviderViewData synchronizationProviderViewData;
        synchronizationProviderViewData = getItem(position);
        holder.title.setText(synchronizationProviderViewData.getSummaryResource());
        holder.icon.setImageResource(synchronizationProviderViewData.getIconResource());
        return convertView;
    }

};
switch(MUID_STATIC) {
    // SynchronizationPreferencesFragment_4_BuggyGUIListenerOperatorMutator
    case 4088: {
        builder.setAdapter(adapter, null);
        break;
    }
    default: {
    builder.setAdapter(adapter, (android.content.DialogInterface dialog,int which) -> {
        switch (providers[which]) {
            case GPODDER_NET :
                new de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment().show(getChildFragmentManager(), de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment.TAG);
                break;
            case NEXTCLOUD_GPODDER :
                new de.danoeh.antennapod.fragment.preferences.synchronization.NextcloudAuthenticationFragment().show(getChildFragmentManager(), de.danoeh.antennapod.fragment.preferences.synchronization.NextcloudAuthenticationFragment.TAG);
                break;
            default :
                break;
        }
        updateScreen();
    });
    break;
}
}
builder.show();
}


private boolean isProviderSelected(@androidx.annotation.NonNull
de.danoeh.antennapod.core.sync.SynchronizationProviderViewData provider) {
java.lang.String selectedSyncProviderKey;
selectedSyncProviderKey = getSelectedSyncProviderKey();
return provider.getIdentifier().equals(selectedSyncProviderKey);
}


private java.lang.String getSelectedSyncProviderKey() {
return de.danoeh.antennapod.core.sync.SynchronizationSettings.getSelectedSyncProviderKey();
}


private void updateLastSyncReport(boolean successful, long lastTime) {
java.lang.String status;
status = java.lang.String.format("%1$s (%2$s)", getString(successful ? de.danoeh.antennapod.R.string.gpodnetsync_pref_report_successful : de.danoeh.antennapod.R.string.gpodnetsync_pref_report_failed), android.text.format.DateUtils.getRelativeDateTimeString(getContext(), lastTime, android.text.format.DateUtils.MINUTE_IN_MILLIS, android.text.format.DateUtils.WEEK_IN_MILLIS, android.text.format.DateUtils.FORMAT_SHOW_TIME));
((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setSubtitle(status);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
