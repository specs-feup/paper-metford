package com.automattic.simplenote;
import androidx.appcompat.app.AlertDialog;
import com.automattic.simplenote.utils.DeleteAccountRequestHandler;
import com.automattic.simplenote.authentication.SimplenoteAuthenticationActivity;
import com.simperium.Simperium;
import androidx.core.app.ShareCompat;
import static com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_ASCENDING;
import android.app.Fragment;
import java.util.Comparator;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import com.simperium.client.BucketObjectNameInvalid;
import androidx.preference.PreferenceFragmentCompat;
import static android.app.Activity.RESULT_OK;
import java.util.List;
import static com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_DESCENDING;
import static com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_ASCENDING_LABEL;
import java.util.Collections;
import static com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_DESCENDING_LABEL;
import android.os.AsyncTask;
import androidx.preference.Preference;
import com.automattic.simplenote.utils.AuthUtils;
import com.simperium.client.Bucket;
import java.io.FileOutputStream;
import com.automattic.simplenote.models.Note;
import android.os.ParcelFileDescriptor;
import static com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_DESCENDING;
import static com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_DESCENDING_LABEL;
import org.json.JSONObject;
import com.automattic.simplenote.utils.BrowserUtils;
import static com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_ASCENDING_LABEL;
import com.simperium.client.User;
import android.content.DialogInterface;
import static com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY;
import android.net.Uri;
import com.automattic.simplenote.utils.AppLog;
import androidx.fragment.app.FragmentActivity;
import static com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_DESCENDING;
import java.lang.ref.WeakReference;
import com.automattic.simplenote.utils.NetworkUtils;
import android.widget.Toast;
import com.automattic.simplenote.models.Preferences;
import com.automattic.simplenote.utils.PrefUtils;
import com.automattic.simplenote.utils.HtmlCompat;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import static com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_ASCENDING;
import com.simperium.android.ProgressDialogFragment;
import androidx.preference.SwitchPreferenceCompat;
import android.os.Bundle;
import androidx.preference.ListPreference;
import static com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_ASCENDING;
import android.content.Intent;
import org.json.JSONArray;
import com.automattic.simplenote.utils.DialogUtils;
import com.simperium.client.BucketObjectMissingException;
import com.automattic.simplenote.utils.AppLog.Type;
import com.automattic.simplenote.utils.AccountNetworkUtils;
import com.automattic.simplenote.utils.SimplenoteProgressDialogFragment;
import androidx.appcompat.view.ContextThemeWrapper;
import static com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_ASCENDING_LABEL;
import static com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_DESCENDING_LABEL;
import android.content.Context;
import android.os.Parcelable;
import android.os.Parcelable;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A simple {@link Fragment} subclass.
 */
public class PreferencesFragment extends androidx.preference.PreferenceFragmentCompat implements com.simperium.client.User.StatusChangeListener , com.simperium.Simperium.OnUserCreatedListener {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String WEB_APP_URL = "https://app.simplenote.com";

    private static final int REQUEST_EXPORT_DATA = 9001;

    private static final int REQUEST_EXPORT_UNSYNCED = 9002;

    private static final int REQUEST_IMPORT_DATA = 9003;

    private com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> mPreferencesBucket;

    private androidx.preference.SwitchPreferenceCompat mAnalyticsSwitch;

    private com.automattic.simplenote.utils.SimplenoteProgressDialogFragment mProgressDialogFragment;

    public PreferencesFragment() {
        // Required empty public constructor
    }


    @java.lang.Override
    public void onCreatePreferences(android.os.Bundle savedInstanceState, java.lang.String rootKey) {
        addPreferencesFromResource(com.automattic.simplenote.R.xml.preferences);
    }


    @java.lang.Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        androidx.preference.Preference authenticatePreference;
        authenticatePreference = findPreference("pref_key_authenticate");
        com.automattic.simplenote.Simplenote currentApp;
        currentApp = ((com.automattic.simplenote.Simplenote) (getActivity().getApplication()));
        com.simperium.Simperium simperium;
        simperium = currentApp.getSimperium();
        simperium.setUserStatusChangeListener(this);
        simperium.setOnUserCreatedListener(this);
        mPreferencesBucket = currentApp.getPreferencesBucket();
        authenticatePreference.setSummary(currentApp.getSimperium().getUser().getEmail());
        if (simperium.needsAuthorization()) {
            authenticatePreference.setTitle(com.automattic.simplenote.R.string.log_in);
        } else {
            authenticatePreference.setTitle(com.automattic.simplenote.R.string.log_out);
        }
        authenticatePreference.setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
            @java.lang.Override
            public boolean onPreferenceClick(androidx.preference.Preference preference) {
                if (!isAdded()) {
                    return false;
                }
                com.automattic.simplenote.Simplenote currentApp;
                currentApp = ((com.automattic.simplenote.Simplenote) (getActivity().getApplication()));
                if (currentApp.getSimperium().needsAuthorization()) {
                    android.content.Intent loginIntent;
                    switch(MUID_STATIC) {
                        // PreferencesFragment_0_InvalidKeyIntentOperatorMutator
                        case 79: {
                            loginIntent = new android.content.Intent((androidx.fragment.app.FragmentActivity) null, com.automattic.simplenote.authentication.SimplenoteAuthenticationActivity.class);
                            break;
                        }
                        // PreferencesFragment_1_RandomActionIntentDefinitionOperatorMutator
                        case 179: {
                            loginIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                            break;
                        }
                        default: {
                        loginIntent = new android.content.Intent(getActivity(), com.automattic.simplenote.authentication.SimplenoteAuthenticationActivity.class);
                        break;
                    }
                }
                startActivityForResult(loginIntent, com.simperium.Simperium.SIGNUP_SIGNIN_REQUEST);
            } else {
                new com.automattic.simplenote.PreferencesFragment.LogOutTask(com.automattic.simplenote.PreferencesFragment.this).execute();
            }
            return true;
        }

    });
    androidx.preference.Preference deleteAppPreference;
    deleteAppPreference = findPreference("pref_key_delete_account");
    deleteAppPreference.setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
        @java.lang.Override
        public boolean onPreferenceClick(androidx.preference.Preference preference) {
            com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.USER_ACCOUNT_DELETE_REQUESTED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "preferences_delete_account_button");
            showDeleteAccountDialog();
            return true;
        }

    });
    findPreference("pref_key_help").setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
        @java.lang.Override
        public boolean onPreferenceClick(androidx.preference.Preference preference) {
            try {
                com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), "https://simplenote.com/help");
            } catch (java.lang.Exception e) {
                toast(com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG);
            }
            return true;
        }

    });
    findPreference("pref_key_website").setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
        @java.lang.Override
        public boolean onPreferenceClick(androidx.preference.Preference preference) {
            try {
                com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), "http://simplenote.com");
            } catch (java.lang.Exception e) {
                toast(com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG);
            }
            return true;
        }

    });
    findPreference("pref_key_about").setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
        @java.lang.Override
        public boolean onPreferenceClick(androidx.preference.Preference preference) {
            startActivity(new android.content.Intent(getActivity(), com.automattic.simplenote.AboutActivity.class));
            return true;
        }

    });
    findPreference("pref_key_import").setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
        @java.lang.Override
        public boolean onPreferenceClick(androidx.preference.Preference preference) {
            com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SETTINGS_IMPORT_NOTES, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_NOTE, "preferences_import_data_button");
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // PreferencesFragment_2_InvalidKeyIntentOperatorMutator
                case 279: {
                    intent = new android.content.Intent((String) null);
                    break;
                }
                // PreferencesFragment_3_RandomActionIntentDefinitionOperatorMutator
                case 379: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(android.content.Intent.ACTION_OPEN_DOCUMENT);
                break;
            }
        }
        switch(MUID_STATIC) {
            // PreferencesFragment_4_RandomActionIntentDefinitionOperatorMutator
            case 479: {
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
            intent.addCategory(android.content.Intent.CATEGORY_OPENABLE);
            break;
        }
    }
    switch(MUID_STATIC) {
        // PreferencesFragment_5_RandomActionIntentDefinitionOperatorMutator
        case 579: {
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
        intent.setType("*/*");
        break;
    }
}
switch(MUID_STATIC) {
    // PreferencesFragment_6_NullValueIntentPutExtraOperatorMutator
    case 679: {
        intent.putExtra(android.content.Intent.EXTRA_MIME_TYPES, new Parcelable[0]);
        break;
    }
    // PreferencesFragment_7_IntentPayloadReplacementOperatorMutator
    case 779: {
        intent.putExtra(android.content.Intent.EXTRA_MIME_TYPES, (java.lang.String[]) null);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // PreferencesFragment_8_RandomActionIntentDefinitionOperatorMutator
        case 879: {
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
        intent.putExtra(android.content.Intent.EXTRA_MIME_TYPES, new java.lang.String[]{ "text/*", "application/json" });
        break;
    }
}
break;
}
}
startActivityForResult(intent, com.automattic.simplenote.PreferencesFragment.REQUEST_IMPORT_DATA);
return true;
}

});
findPreference("pref_key_export").setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
@java.lang.Override
public boolean onPreferenceClick(androidx.preference.Preference preference) {
android.content.Intent intent;
switch(MUID_STATIC) {
// PreferencesFragment_9_InvalidKeyIntentOperatorMutator
case 979: {
intent = new android.content.Intent((String) null);
break;
}
// PreferencesFragment_10_RandomActionIntentDefinitionOperatorMutator
case 1079: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT);
break;
}
}
switch(MUID_STATIC) {
// PreferencesFragment_11_RandomActionIntentDefinitionOperatorMutator
case 1179: {
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
intent.addCategory(android.content.Intent.CATEGORY_OPENABLE);
break;
}
}
switch(MUID_STATIC) {
// PreferencesFragment_12_RandomActionIntentDefinitionOperatorMutator
case 1279: {
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
intent.setType("application/json");
break;
}
}
switch(MUID_STATIC) {
// PreferencesFragment_13_NullValueIntentPutExtraOperatorMutator
case 1379: {
intent.putExtra(android.content.Intent.EXTRA_TITLE, new Parcelable[0]);
break;
}
// PreferencesFragment_14_IntentPayloadReplacementOperatorMutator
case 1479: {
intent.putExtra(android.content.Intent.EXTRA_TITLE, "");
break;
}
default: {
switch(MUID_STATIC) {
// PreferencesFragment_15_RandomActionIntentDefinitionOperatorMutator
case 1579: {
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
intent.putExtra(android.content.Intent.EXTRA_TITLE, getString(com.automattic.simplenote.R.string.export_file));
break;
}
}
break;
}
}
startActivityForResult(intent, com.automattic.simplenote.PreferencesFragment.REQUEST_EXPORT_DATA);
return true;
}

});
final androidx.preference.ListPreference themePreference;
themePreference = findPreference(com.automattic.simplenote.utils.PrefUtils.PREF_THEME);
themePreference.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
@java.lang.Override
public boolean onPreferenceChange(androidx.preference.Preference preference, java.lang.Object newValue) {
updateTheme(requireActivity(), java.lang.Integer.parseInt(newValue.toString()));
return true;
}


private void updateTheme(android.app.Activity activity, int index) {
java.lang.CharSequence[] entries;
entries = themePreference.getEntries();
themePreference.setSummary(entries[index]);
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SETTINGS_THEME_UPDATED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "theme_preference");
}

});
final androidx.preference.Preference stylePreference;
stylePreference = findPreference("pref_key_style");
stylePreference.setSummary(com.automattic.simplenote.utils.PrefUtils.isPremium(requireContext()) ? com.automattic.simplenote.utils.PrefUtils.getStyleNameFromIndexSelected(requireContext()) : com.automattic.simplenote.utils.PrefUtils.getStyleNameDefault(requireContext()));
stylePreference.setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
@java.lang.Override
public boolean onPreferenceClick(androidx.preference.Preference preference) {
startActivity(new android.content.Intent(requireContext(), com.automattic.simplenote.StyleActivity.class));
return true;
}

});
final androidx.preference.Preference membershipPreference;
membershipPreference = findPreference("pref_key_membership");
membershipPreference.setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
@java.lang.Override
public boolean onPreferenceClick(androidx.preference.Preference preference) {
((com.automattic.simplenote.PreferencesActivity) (requireActivity())).openBrowserForMembership(getView());
return true;
}

});
if (com.automattic.simplenote.utils.PrefUtils.isPremium(requireContext())) {
membershipPreference.setLayoutResource(com.automattic.simplenote.R.layout.preference_default);
membershipPreference.setSummary(com.automattic.simplenote.R.string.membership_premium);
} else {
membershipPreference.setLayoutResource(com.automattic.simplenote.R.layout.preference_button);
membershipPreference.setSummary(com.automattic.simplenote.R.string.membership_free);
}
final androidx.preference.ListPreference sortPreference;
sortPreference = findPreference(com.automattic.simplenote.utils.PrefUtils.PREF_SORT_ORDER);
sortPreference.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
@java.lang.Override
public boolean onPreferenceChange(androidx.preference.Preference preference, java.lang.Object newValue) {
int index;
index = java.lang.Integer.parseInt(newValue.toString());
java.lang.CharSequence[] entries;
entries = sortPreference.getEntries();
sortPreference.setSummary(entries[index]);
if (!sortPreference.getValue().equals(newValue)) {
switch (index) {
case com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_ASCENDING :
trackSortOrder(com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_ASCENDING_LABEL);
break;
case com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_DESCENDING :
trackSortOrder(com.automattic.simplenote.utils.PrefUtils.ALPHABETICAL_DESCENDING_LABEL);
break;
case com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_ASCENDING :
trackSortOrder(com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_ASCENDING_LABEL);
break;
case com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_DESCENDING :
trackSortOrder(com.automattic.simplenote.utils.PrefUtils.DATE_CREATED_DESCENDING_LABEL);
break;
case com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_ASCENDING :
trackSortOrder(com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_ASCENDING_LABEL);
break;
case com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_DESCENDING :
trackSortOrder(com.automattic.simplenote.utils.PrefUtils.DATE_MODIFIED_DESCENDING_LABEL);
break;
}
}
return true;
}

});
androidx.preference.SwitchPreferenceCompat switchPreference;
switchPreference = findPreference("pref_key_condensed_note_list");
switchPreference.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
@java.lang.Override
public boolean onPreferenceChange(androidx.preference.Preference preference, java.lang.Object o) {
if (((androidx.preference.SwitchPreferenceCompat) (preference)).isChecked()) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SETTINGS_LIST_CONDENSED_ENABLED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "condensed_list_preference");
}
return true;
}

});
// Add the hyperlink to the analytics summary
androidx.preference.Preference analyticsSummaryPreference;
analyticsSummaryPreference = findPreference("pref_key_analytics_enabled_summary");
java.lang.String formattedSummary;
formattedSummary = java.lang.String.format(getString(com.automattic.simplenote.R.string.share_analytics_summary), "<a href=\"https://automattic.com/cookies\">", "</a>");
analyticsSummaryPreference.setSummary(com.automattic.simplenote.utils.HtmlCompat.fromHtml(formattedSummary));
mAnalyticsSwitch = findPreference("pref_key_analytics_switch");
mAnalyticsSwitch.setOnPreferenceChangeListener(new androidx.preference.Preference.OnPreferenceChangeListener() {
@java.lang.Override
public boolean onPreferenceChange(androidx.preference.Preference preference, java.lang.Object newValue) {
try {
boolean isChecked;
isChecked = ((boolean) (newValue));
com.automattic.simplenote.models.Preferences prefs;
prefs = mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY);
prefs.setAnalyticsEnabled(isChecked);
prefs.save();
} catch (com.simperium.client.BucketObjectMissingException e) {
e.printStackTrace();
}
return true;
}

});
updateAnalyticsSwitchState();
findPreference("pref_key_logs").setOnPreferenceClickListener(new androidx.preference.Preference.OnPreferenceClickListener() {
@java.lang.Override
public boolean onPreferenceClick(androidx.preference.Preference preference) {
startActivity(androidx.core.app.ShareCompat.IntentBuilder.from(requireActivity()).setText(com.automattic.simplenote.utils.AppLog.get()).setType("text/plain").createChooserIntent());
return true;
}

});
}


private void showProgressDialogDeleteAccount() {
androidx.fragment.app.FragmentActivity activity;
activity = getActivity();
if (activity == null) {
return;
}
mProgressDialogFragment = com.automattic.simplenote.utils.SimplenoteProgressDialogFragment.newInstance(getString(com.automattic.simplenote.R.string.requesting_message));
mProgressDialogFragment.show(activity.getSupportFragmentManager(), com.simperium.android.ProgressDialogFragment.TAG);
}


private void closeProgressDialogDeleteAccount() {
if ((mProgressDialogFragment != null) && (!mProgressDialogFragment.isHidden())) {
mProgressDialogFragment.dismiss();
mProgressDialogFragment = null;
}
}


private void showDeleteAccountDialog() {
android.content.Context context;
context = getContext();
if (context == null) {
return;
}
final com.automattic.simplenote.utils.DeleteAccountRequestHandler deleteAccountHandler;
deleteAccountHandler = new com.automattic.simplenote.PreferencesFragment.DeleteAccountRequestHandlerImpl(this);
com.automattic.simplenote.Simplenote currentApp;
currentApp = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication()));
com.simperium.Simperium simperium;
simperium = currentApp.getSimperium();
java.lang.String userEmail;
userEmail = simperium.getUser().getEmail();
final androidx.appcompat.app.AlertDialog dialogDeleteAccount;
switch(MUID_STATIC) {
// PreferencesFragment_16_BuggyGUIListenerOperatorMutator
case 1679: {
dialogDeleteAccount = new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(context, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.delete_account).setMessage(getString(com.automattic.simplenote.R.string.delete_account_email_message, userEmail)).setPositiveButton(com.automattic.simplenote.R.string.delete_account, null).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).create();
break;
}
default: {
dialogDeleteAccount = new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(context, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.delete_account).setMessage(getString(com.automattic.simplenote.R.string.delete_account_email_message, userEmail)).setPositiveButton(com.automattic.simplenote.R.string.delete_account, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
androidx.fragment.app.FragmentActivity activity;
activity = getActivity();
if (activity == null) {
return;
}
showProgressDialogDeleteAccount();
switch(MUID_STATIC) {
// PreferencesFragment_17_LengthyGUIListenerOperatorMutator
case 1779: {
/**
* Inserted by Kadabra
*/
// makeDeleteAccountRequest can throw an exception when it cannot build
// the JSON object. In those cases, we show the error dialog since
// it can be related to memory constraints or something else that is
// just a transient fault
try {
if (com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, "Making request to delete account");
java.lang.String userToken;
userToken = simperium.getUser().getAccessToken();
com.automattic.simplenote.utils.AccountNetworkUtils.makeDeleteAccountRequest(userEmail, userToken, deleteAccountHandler);
} else {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, "No connectivity to make request to delete account");
closeProgressDialogDeleteAccount();
showDialogDeleteAccountNoConnectivity();
}
} catch (java.lang.IllegalArgumentException exception) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, ("Error trying to make request " + "to delete account. Error: ") + exception.getMessage());
closeProgressDialogDeleteAccount();
showDialogDeleteAccountError();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
// makeDeleteAccountRequest can throw an exception when it cannot build
// the JSON object. In those cases, we show the error dialog since
// it can be related to memory constraints or something else that is
// just a transient fault
try {
if (com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, "Making request to delete account");
java.lang.String userToken;
userToken = simperium.getUser().getAccessToken();
com.automattic.simplenote.utils.AccountNetworkUtils.makeDeleteAccountRequest(userEmail, userToken, deleteAccountHandler);
} else {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, "No connectivity to make request to delete account");
closeProgressDialogDeleteAccount();
showDialogDeleteAccountNoConnectivity();
}
} catch (java.lang.IllegalArgumentException exception) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, ("Error trying to make request " + "to delete account. Error: ") + exception.getMessage());
closeProgressDialogDeleteAccount();
showDialogDeleteAccountError();
}
break;
}
}
}

}).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).create();
break;
}
}
dialogDeleteAccount.setOnShowListener(new android.content.DialogInterface.OnShowListener() {
@java.lang.Override
public void onShow(android.content.DialogInterface dialog) {
androidx.fragment.app.FragmentActivity activity;
activity = getActivity();
if (activity == null) {
return;
}
int colorRed;
colorRed = androidx.core.content.ContextCompat.getColor(activity, com.automattic.simplenote.R.color.text_button_red);
dialogDeleteAccount.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setTextColor(colorRed);
}

});
dialogDeleteAccount.show();
}


private void showDialogDeleteAccountNoConnectivity() {
androidx.fragment.app.FragmentActivity activity;
activity = getActivity();
if (activity == null) {
return;
}
androidx.appcompat.app.AlertDialog dialogDeleteAccountConfirmation;
switch(MUID_STATIC) {
// PreferencesFragment_18_BuggyGUIListenerOperatorMutator
case 1879: {
dialogDeleteAccountConfirmation = new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(activity, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.error).setMessage(com.automattic.simplenote.R.string.simperium_dialog_message_network).setPositiveButton(android.R.string.ok, null).create();
break;
}
default: {
dialogDeleteAccountConfirmation = new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(activity, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.error).setMessage(com.automattic.simplenote.R.string.simperium_dialog_message_network).setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
switch(MUID_STATIC) {
// PreferencesFragment_19_LengthyGUIListenerOperatorMutator
case 1979: {
/**
* Inserted by Kadabra
*/
dialogInterface.dismiss();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
dialogInterface.dismiss();
break;
}
}
}

}).create();
break;
}
}
dialogDeleteAccountConfirmation.show();
}


private void showDialogDeleteAccountError() {
android.content.Context context;
context = getContext();
if (context == null) {
return;
}
com.automattic.simplenote.utils.DialogUtils.showDialogWithEmail(context, getString(com.automattic.simplenote.R.string.error_ocurred_message));
}


private void showDeleteAccountConfirmationDialog() {
androidx.fragment.app.FragmentActivity activity;
activity = getActivity();
if (activity == null) {
return;
}
com.automattic.simplenote.Simplenote currentApp;
currentApp = ((com.automattic.simplenote.Simplenote) (activity.getApplication()));
com.simperium.Simperium simperium;
simperium = currentApp.getSimperium();
java.lang.String userEmail;
userEmail = simperium.getUser().getEmail();
androidx.appcompat.app.AlertDialog dialogDeleteAccountConfirmation;
switch(MUID_STATIC) {
// PreferencesFragment_20_BuggyGUIListenerOperatorMutator
case 2079: {
dialogDeleteAccountConfirmation = new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(activity, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.request_received).setMessage(getString(com.automattic.simplenote.R.string.account_deletion_message, userEmail)).setPositiveButton(android.R.string.ok, null).create();
break;
}
default: {
dialogDeleteAccountConfirmation = new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(activity, com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.request_received).setMessage(getString(com.automattic.simplenote.R.string.account_deletion_message, userEmail)).setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
switch(MUID_STATIC) {
// PreferencesFragment_21_LengthyGUIListenerOperatorMutator
case 2179: {
/**
* Inserted by Kadabra
*/
dialogInterface.dismiss();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
dialogInterface.dismiss();
break;
}
}
}

}).create();
break;
}
}
dialogDeleteAccountConfirmation.show();
}


@java.lang.Override
public void onActivityResult(int requestCode, int resultCode, android.content.Intent resultData) {
if ((resultCode != android.app.Activity.RESULT_OK) || (resultData == null)) {
return;
}
if (resultData.getData() == null) {
toast(com.automattic.simplenote.R.string.export_message_failure);
return;
}
switch (requestCode) {
case com.automattic.simplenote.PreferencesFragment.REQUEST_EXPORT_DATA :
exportData(resultData.getData(), false);
break;
case com.automattic.simplenote.PreferencesFragment.REQUEST_EXPORT_UNSYNCED :
exportData(resultData.getData(), true);
break;
case com.automattic.simplenote.PreferencesFragment.REQUEST_IMPORT_DATA :
importData(resultData.getData());
break;
}
}


private boolean hasUnsyncedNotes() {
com.automattic.simplenote.Simplenote application;
application = ((com.automattic.simplenote.Simplenote) (getActivity().getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> notesBucket;
notesBucket = application.getNotesBucket();
com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> notesCursor;
notesCursor = notesBucket.allObjects();
while (notesCursor.moveToNext()) {
com.automattic.simplenote.models.Note note;
note = notesCursor.getObject();
if (note.isNew() || note.isModified()) {
return true;
}
} 
return false;
}


private void logOut() {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACTION, "Tapped logout button (PreferencesFragment)");
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.USER_SIGNED_OUT, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "preferences_sign_out_button");
com.automattic.simplenote.utils.AuthUtils.logOut(((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())));
getActivity().finish();
}


@java.lang.Override
public void onUserStatusChange(com.simperium.client.User.Status status) {
if (isAdded() && (status == com.simperium.client.User.Status.AUTHORIZED)) {
// User signed in
getActivity().runOnUiThread(new java.lang.Runnable() {
public void run() {
androidx.preference.Preference authenticatePreference;
authenticatePreference = findPreference("pref_key_authenticate");
authenticatePreference.setTitle(com.automattic.simplenote.R.string.log_out);
}

});
com.automattic.simplenote.Simplenote app;
app = ((com.automattic.simplenote.Simplenote) (getActivity().getApplication()));
com.automattic.simplenote.analytics.AnalyticsTracker.refreshMetadata(app.getSimperium().getUser().getEmail());
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.USER_SIGNED_IN, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "signed_in_from_preferences_activity");
}
}


@java.lang.Override
public void onUserCreated(com.simperium.client.User user) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.USER_ACCOUNT_CREATED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "account_created_from_preferences_activity");
}


private void exportData(android.net.Uri uri, boolean isUnsyncedNotes) {
com.automattic.simplenote.Simplenote currentApp;
currentApp = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication()));
com.simperium.client.Bucket<com.automattic.simplenote.models.Note> noteBucket;
noteBucket = currentApp.getNotesBucket();
org.json.JSONObject account;
account = new org.json.JSONObject();
com.simperium.client.Bucket.ObjectCursor<com.automattic.simplenote.models.Note> cursor;
cursor = noteBucket.allObjects();
try {
org.json.JSONArray activeNotes;
activeNotes = new org.json.JSONArray();
org.json.JSONArray trashedNotes;
trashedNotes = new org.json.JSONArray();
java.util.Comparator<java.lang.String> comparator;
comparator = new java.util.Comparator<java.lang.String>() {
@java.lang.Override
public int compare(java.lang.String text1, java.lang.String text2) {
return text1.compareToIgnoreCase(text2);
}

};
while (cursor.moveToNext()) {
com.automattic.simplenote.models.Note note;
note = cursor.getObject();
if ((isUnsyncedNotes && (!note.isNew())) && (!note.isModified())) {
continue;
}
org.json.JSONObject noteJson;
noteJson = new org.json.JSONObject();
noteJson.put("id", note.getSimperiumKey());
noteJson.put("content", note.getContent());
noteJson.put("creationDate", note.getCreationDateString());
noteJson.put("lastModified", note.getModificationDateString());
if (note.isPinned()) {
noteJson.put("pinned", note.isPinned());
}
if (note.isMarkdownEnabled()) {
noteJson.put("markdown", note.isMarkdownEnabled());
}
if (note.getTags().size() > 0) {
java.util.List<java.lang.String> tags;
tags = note.getTags();
java.util.Collections.sort(tags, comparator);
noteJson.put("tags", new org.json.JSONArray(tags));
}
if (!note.getPublishedUrl().isEmpty()) {
noteJson.put("publicURL", note.getPublishedUrl());
}
if (note.isDeleted()) {
trashedNotes.put(noteJson);
} else {
activeNotes.put(noteJson);
}
} 
account.put("activeNotes", activeNotes);
account.put("trashedNotes", trashedNotes);
android.os.ParcelFileDescriptor parcelFileDescriptor;
parcelFileDescriptor = requireContext().getContentResolver().openFileDescriptor(uri, "w");
if (parcelFileDescriptor != null) {
java.io.FileOutputStream fileOutputStream;
fileOutputStream = new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor());
fileOutputStream.write(account.toString(2).replace("\\/", "/").getBytes());
parcelFileDescriptor.close();
toast(com.automattic.simplenote.R.string.export_message_success);
} else {
toast(com.automattic.simplenote.R.string.export_message_failure);
}
} catch (java.lang.Exception e) {
toast(com.automattic.simplenote.R.string.export_message_failure);
}
}


private void importData(android.net.Uri uri) {
try {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.IMPORT, ("Importing notes from " + uri) + ".");
androidx.fragment.app.FragmentActivity activity;
activity = getActivity();
if (activity == null) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.IMPORT, "Could not import notes since activity is null");
return;
}
com.automattic.simplenote.Importer.fromUri(activity, uri);
toast(com.automattic.simplenote.R.string.import_message_success);
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.IMPORT, "Notes imported correctly!");
} catch (com.automattic.simplenote.Importer.ImportException e) {
switch (e.getReason()) {
case FileError :
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.IMPORT, "File error while importing note. Exception: " + e.getMessage());
toast(com.automattic.simplenote.R.string.import_error_file);
break;
case ParseError :
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.IMPORT, "Parse error while importing note. Exception: " + e.getMessage());
toast(com.automattic.simplenote.R.string.import_error_parse);
break;
case UnknownExportType :
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.IMPORT, "Unknown error while importing note. Exception: " + e.getMessage());
toast(com.automattic.simplenote.R.string.import_unknown);
break;
}
}
}


private void trackSortOrder(java.lang.String label) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.SETTINGS_SEARCH_SORT_MODE, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_SETTING, label);
}


private void updateAnalyticsSwitchState() {
try {
com.automattic.simplenote.models.Preferences prefs;
prefs = mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY);
mAnalyticsSwitch.setChecked(prefs.getAnalyticsEnabled());
} catch (com.simperium.client.BucketObjectMissingException e) {
// The preferences object doesn't exist for this user yet, create it
try {
com.automattic.simplenote.models.Preferences prefs;
prefs = mPreferencesBucket.newObject(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY);
prefs.setAnalyticsEnabled(true);
prefs.save();
} catch (com.simperium.client.BucketObjectNameInvalid bucketObjectNameInvalid) {
bucketObjectNameInvalid.printStackTrace();
}
}
}


private static class LogOutTask extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Boolean> {
private java.lang.ref.WeakReference<com.automattic.simplenote.PreferencesFragment> mPreferencesFragmentReference;

LogOutTask(com.automattic.simplenote.PreferencesFragment fragment) {
mPreferencesFragmentReference = new java.lang.ref.WeakReference<>(fragment);
}


@java.lang.Override
protected java.lang.Boolean doInBackground(java.lang.Void... voids) {
com.automattic.simplenote.PreferencesFragment fragment;
fragment = mPreferencesFragmentReference.get();
return (fragment == null) || fragment.hasUnsyncedNotes();
}


@java.lang.Override
protected void onPostExecute(java.lang.Boolean hasUnsyncedNotes) {
final com.automattic.simplenote.PreferencesFragment fragment;
fragment = mPreferencesFragmentReference.get();
if (fragment == null) {
return;
}
// Safety first! Check if any notes are unsynced and warn the user if so.
if (hasUnsyncedNotes) {
switch(MUID_STATIC) {
// PreferencesFragment_22_BuggyGUIListenerOperatorMutator
case 2279: {
new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(fragment.requireContext(), com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.unsynced_notes).setMessage(com.automattic.simplenote.R.string.unsynced_notes_message).setPositiveButton(com.automattic.simplenote.R.string.log_out_anyway, null).setNeutralButton(com.automattic.simplenote.R.string.export_unsynced_notes, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
android.content.Intent intent;
intent = new android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT);
intent.addCategory(android.content.Intent.CATEGORY_OPENABLE);
intent.setType("application/json");
intent.putExtra(android.content.Intent.EXTRA_TITLE, fragment.getString(com.automattic.simplenote.R.string.export_file));
fragment.startActivityForResult(intent, com.automattic.simplenote.PreferencesFragment.REQUEST_EXPORT_UNSYNCED);
}

}).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).show();
break;
}
default: {
switch(MUID_STATIC) {
// PreferencesFragment_24_BuggyGUIListenerOperatorMutator
case 2479: {
new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(fragment.requireContext(), com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.unsynced_notes).setMessage(com.automattic.simplenote.R.string.unsynced_notes_message).setPositiveButton(com.automattic.simplenote.R.string.log_out_anyway, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
/**
* Inserted by Kadabra
*/
switch(MUID_STATIC) {
// PreferencesFragment_23_LengthyGUIListenerOperatorMutator
case 2379: {
/**
* Inserted by Kadabra
*/
fragment.logOut();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {;
fragment.logOut();
/**
* Inserted by Kadabra
*/
break;
}
};
}

}).setNeutralButton(com.automattic.simplenote.R.string.export_unsynced_notes, null).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).show();
break;
}
default: {
new androidx.appcompat.app.AlertDialog.Builder(new androidx.appcompat.view.ContextThemeWrapper(fragment.requireContext(), com.automattic.simplenote.R.style.Dialog)).setTitle(com.automattic.simplenote.R.string.unsynced_notes).setMessage(com.automattic.simplenote.R.string.unsynced_notes_message).setPositiveButton(com.automattic.simplenote.R.string.log_out_anyway, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
switch(MUID_STATIC) {
// PreferencesFragment_23_LengthyGUIListenerOperatorMutator
case 2379: {
/**
* Inserted by Kadabra
*/
fragment.logOut();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
fragment.logOut();
break;
}
}
}

}).setNeutralButton(com.automattic.simplenote.R.string.export_unsynced_notes, new android.content.DialogInterface.OnClickListener() {
@java.lang.Override
public void onClick(android.content.DialogInterface dialogInterface, int i) {
android.content.Intent intent;
switch(MUID_STATIC) {
// PreferencesFragment_26_InvalidKeyIntentOperatorMutator
case 2679: {
intent = new android.content.Intent((String) null);
break;
}
// PreferencesFragment_27_RandomActionIntentDefinitionOperatorMutator
case 2779: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent(android.content.Intent.ACTION_CREATE_DOCUMENT);
break;
}
}
switch(MUID_STATIC) {
// PreferencesFragment_28_RandomActionIntentDefinitionOperatorMutator
case 2879: {
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
intent.addCategory(android.content.Intent.CATEGORY_OPENABLE);
break;
}
}
switch(MUID_STATIC) {
// PreferencesFragment_29_RandomActionIntentDefinitionOperatorMutator
case 2979: {
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
intent.setType("application/json");
break;
}
}
switch(MUID_STATIC) {
// PreferencesFragment_30_NullValueIntentPutExtraOperatorMutator
case 3079: {
intent.putExtra(android.content.Intent.EXTRA_TITLE, new Parcelable[0]);
break;
}
// PreferencesFragment_31_IntentPayloadReplacementOperatorMutator
case 3179: {
intent.putExtra(android.content.Intent.EXTRA_TITLE, "");
break;
}
default: {
switch(MUID_STATIC) {
// PreferencesFragment_32_RandomActionIntentDefinitionOperatorMutator
case 3279: {
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
intent.putExtra(android.content.Intent.EXTRA_TITLE, fragment.getString(com.automattic.simplenote.R.string.export_file));
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// PreferencesFragment_25_LengthyGUIListenerOperatorMutator
case 2579: {
/**
* Inserted by Kadabra
*/
fragment.startActivityForResult(intent, com.automattic.simplenote.PreferencesFragment.REQUEST_EXPORT_UNSYNCED);
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
fragment.startActivityForResult(intent, com.automattic.simplenote.PreferencesFragment.REQUEST_EXPORT_UNSYNCED);
break;
}
}
}

}).setNegativeButton(com.automattic.simplenote.R.string.cancel, null).show();
break;
}
}
break;
}
}
} else {
fragment.logOut();
}
}

}

private void toast(int stringId) {
toast(stringId, android.widget.Toast.LENGTH_SHORT);
}


private void toast(int stringId, int length) {
android.widget.Toast.makeText(requireContext(), getString(stringId), length).show();
}


static class DeleteAccountRequestHandlerImpl implements com.automattic.simplenote.utils.DeleteAccountRequestHandler {
final java.lang.ref.WeakReference<com.automattic.simplenote.PreferencesFragment> preferencesFragment;

DeleteAccountRequestHandlerImpl(com.automattic.simplenote.PreferencesFragment fragment) {
this.preferencesFragment = new java.lang.ref.WeakReference<>(fragment);
}


@java.lang.Override
public void onSuccess() {
final com.automattic.simplenote.PreferencesFragment fragment;
fragment = preferencesFragment.get();
if (fragment == null) {
return;
}
androidx.fragment.app.FragmentActivity activity;
activity = fragment.getActivity();
if (activity == null) {
return;
}
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, "Request to delete account was successful");
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.USER_ACCOUNT_DELETE_REQUESTED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "delete_account_request_success");
activity.runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
fragment.closeProgressDialogDeleteAccount();
fragment.showDeleteAccountConfirmationDialog();
}

});
}


@java.lang.Override
public void onFailure() {
final com.automattic.simplenote.PreferencesFragment fragment;
fragment = preferencesFragment.get();
if (fragment == null) {
return;
}
androidx.fragment.app.FragmentActivity activity;
activity = fragment.getActivity();
if (activity == null) {
return;
}
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, "Failure while calling server to delete account");
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.USER_ACCOUNT_DELETE_REQUESTED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "delete_account_request_failure");
activity.runOnUiThread(new java.lang.Runnable() {
@java.lang.Override
public void run() {
fragment.closeProgressDialogDeleteAccount();
fragment.showDialogDeleteAccountError();
}

});
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
