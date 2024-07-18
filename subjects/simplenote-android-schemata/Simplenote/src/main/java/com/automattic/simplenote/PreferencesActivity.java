package com.automattic.simplenote;
import static com.automattic.simplenote.PreferencesFragment.WEB_APP_URL;
import android.os.Bundle;
import static com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY;
import com.automattic.simplenote.viewmodels.IapViewModel;
import com.simperium.client.BucketObjectMissingException;
import org.wordpress.passcodelock.PasscodePreferenceFragmentCompat;
import dagger.hilt.android.AndroidEntryPoint;
import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.preference.Preference;
import org.wordpress.passcodelock.PasscodePreferenceFragment;
import androidx.fragment.app.FragmentManager;
import static com.automattic.simplenote.utils.DisplayUtils.disableScreenshotsIfLocked;
import com.simperium.client.Bucket;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.automattic.simplenote.models.Preferences;
import com.automattic.simplenote.billing.SubscriptionBottomSheetDialog;
import com.automattic.simplenote.utils.BrowserUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@dagger.hilt.android.AndroidEntryPoint
public class PreferencesActivity extends com.automattic.simplenote.ThemedAppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private org.wordpress.passcodelock.PasscodePreferenceFragmentCompat mPasscodePreferenceFragment;

    private com.automattic.simplenote.PreferencesFragment mPreferencesFragment;

    private com.simperium.client.Bucket<com.automattic.simplenote.models.Preferences> mPreferencesBucket;

    private com.automattic.simplenote.viewmodels.IapViewModel mViewModel;

    private android.view.View mIapBanner;

    private android.view.View mIapThankYouBanner;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PreferencesActivity_0_LengthyGUICreationOperatorMutator
            case 74: {
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
    setContentView(com.automattic.simplenote.R.layout.activity_preferences);
    androidx.appcompat.widget.Toolbar toolbar;
    switch(MUID_STATIC) {
        // PreferencesActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 174: {
            toolbar = null;
            break;
        }
        // PreferencesActivity_2_InvalidIDFindViewOperatorMutator
        case 274: {
            toolbar = findViewById(732221);
            break;
        }
        // PreferencesActivity_3_InvalidViewFocusOperatorMutator
        case 374: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // PreferencesActivity_4_ViewComponentNotVisibleOperatorMutator
        case 474: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
        break;
    }
}
setSupportActionBar(toolbar);
setTitle(com.automattic.simplenote.R.string.settings);
if (getSupportActionBar() != null) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
}
switch(MUID_STATIC) {
    // PreferencesActivity_5_FindViewByIdReturnsNullOperatorMutator
    case 574: {
        mIapBanner = null;
        break;
    }
    // PreferencesActivity_6_InvalidIDFindViewOperatorMutator
    case 674: {
        mIapBanner = findViewById(732221);
        break;
    }
    // PreferencesActivity_7_InvalidViewFocusOperatorMutator
    case 774: {
        /**
        * Inserted by Kadabra
        */
        mIapBanner = findViewById(com.automattic.simplenote.R.id.iap_banner);
        mIapBanner.requestFocus();
        break;
    }
    // PreferencesActivity_8_ViewComponentNotVisibleOperatorMutator
    case 874: {
        /**
        * Inserted by Kadabra
        */
        mIapBanner = findViewById(com.automattic.simplenote.R.id.iap_banner);
        mIapBanner.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mIapBanner = findViewById(com.automattic.simplenote.R.id.iap_banner);
    break;
}
}
switch(MUID_STATIC) {
// PreferencesActivity_9_FindViewByIdReturnsNullOperatorMutator
case 974: {
    mIapThankYouBanner = null;
    break;
}
// PreferencesActivity_10_InvalidIDFindViewOperatorMutator
case 1074: {
    mIapThankYouBanner = findViewById(732221);
    break;
}
// PreferencesActivity_11_InvalidViewFocusOperatorMutator
case 1174: {
    /**
    * Inserted by Kadabra
    */
    mIapThankYouBanner = findViewById(com.automattic.simplenote.R.id.iap_thank_you_banner);
    mIapThankYouBanner.requestFocus();
    break;
}
// PreferencesActivity_12_ViewComponentNotVisibleOperatorMutator
case 1274: {
    /**
    * Inserted by Kadabra
    */
    mIapThankYouBanner = findViewById(com.automattic.simplenote.R.id.iap_thank_you_banner);
    mIapThankYouBanner.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mIapThankYouBanner = findViewById(com.automattic.simplenote.R.id.iap_thank_you_banner);
break;
}
}
com.automattic.simplenote.Simplenote currentApp;
currentApp = ((com.automattic.simplenote.Simplenote) (getApplication()));
mPreferencesBucket = currentApp.getPreferencesBucket();
switch(MUID_STATIC) {
// PreferencesActivity_13_InvalidIDFindViewOperatorMutator
case 1374: {
try {
    if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() == null) {
        mIapThankYouBanner.setVisibility(android.view.View.GONE);
        mViewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.automattic.simplenote.viewmodels.IapViewModel.class);
        findViewById(732221).setOnClickListener((android.view.View view) -> mViewModel.onIapBannerClicked());
        mViewModel.getPlansBottomSheetVisibility().observe(this, (java.lang.Boolean isVisible) -> {
            com.google.android.material.bottomsheet.BottomSheetDialogFragment fragment;
            fragment = ((com.google.android.material.bottomsheet.BottomSheetDialogFragment) (getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG())));
            if (isVisible) {
                if (fragment == null) {
                    fragment = new com.automattic.simplenote.billing.SubscriptionBottomSheetDialog();
                }
                if (!((fragment.getDialog() != null) && fragment.getDialog().isShowing())) {
                    fragment.show(getSupportFragmentManager(), com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG());
                }
            } else if ((fragment != null) && fragment.isVisible()) {
                fragment.dismiss();
            }
        });
        mViewModel.getSnackbarMessage().observe(this, (com.automattic.simplenote.viewmodels.IapViewModel.IapSnackbarMessage message) -> {
            com.google.android.material.snackbar.Snackbar.make(findViewById(com.automattic.simplenote.R.id.main_parent_view), message.getMessageResId(), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
        });
        mViewModel.getIapBannerVisibility().observe(this, (java.lang.Boolean isVisible) -> {
            if (isVisible) {
                mIapBanner.setVisibility(android.view.View.GONE);
                mIapBanner.setVisibility(android.view.View.VISIBLE);
            } else {
                mIapBanner.setVisibility(android.view.View.GONE);
                try {
                    if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() != null) {
                        mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
                    }
                } catch (com.simperium.client.BucketObjectMissingException e) {
                    mIapThankYouBanner.setVisibility(android.view.View.GONE);
                }
            }
        });
    } else {
        mIapBanner.setVisibility(android.view.View.GONE);
        mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
    }
} catch (com.simperium.client.BucketObjectMissingException e) {
    mIapBanner.setVisibility(android.view.View.GONE);
    mIapThankYouBanner.setVisibility(android.view.View.GONE);
}
break;
}
default: {
switch(MUID_STATIC) {
// PreferencesActivity_14_BuggyGUIListenerOperatorMutator
case 1474: {
    try {
        if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() == null) {
            mIapThankYouBanner.setVisibility(android.view.View.GONE);
            mViewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.automattic.simplenote.viewmodels.IapViewModel.class);
            findViewById(com.automattic.simplenote.R.id.iap_banner).setOnClickListener(null);
            mViewModel.getPlansBottomSheetVisibility().observe(this, (java.lang.Boolean isVisible) -> {
                com.google.android.material.bottomsheet.BottomSheetDialogFragment fragment;
                fragment = ((com.google.android.material.bottomsheet.BottomSheetDialogFragment) (getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG())));
                if (isVisible) {
                    if (fragment == null) {
                        fragment = new com.automattic.simplenote.billing.SubscriptionBottomSheetDialog();
                    }
                    if (!((fragment.getDialog() != null) && fragment.getDialog().isShowing())) {
                        fragment.show(getSupportFragmentManager(), com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG());
                    }
                } else if ((fragment != null) && fragment.isVisible()) {
                    fragment.dismiss();
                }
            });
            mViewModel.getSnackbarMessage().observe(this, (com.automattic.simplenote.viewmodels.IapViewModel.IapSnackbarMessage message) -> {
                com.google.android.material.snackbar.Snackbar.make(findViewById(com.automattic.simplenote.R.id.main_parent_view), message.getMessageResId(), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
            });
            mViewModel.getIapBannerVisibility().observe(this, (java.lang.Boolean isVisible) -> {
                if (isVisible) {
                    mIapBanner.setVisibility(android.view.View.GONE);
                    mIapBanner.setVisibility(android.view.View.VISIBLE);
                } else {
                    mIapBanner.setVisibility(android.view.View.GONE);
                    try {
                        if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() != null) {
                            mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
                        }
                    } catch (com.simperium.client.BucketObjectMissingException e) {
                        mIapThankYouBanner.setVisibility(android.view.View.GONE);
                    }
                }
            });
        } else {
            mIapBanner.setVisibility(android.view.View.GONE);
            mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
        }
    } catch (com.simperium.client.BucketObjectMissingException e) {
        mIapBanner.setVisibility(android.view.View.GONE);
        mIapThankYouBanner.setVisibility(android.view.View.GONE);
    }
    break;
}
default: {
switch(MUID_STATIC) {
    // PreferencesActivity_15_InvalidIDFindViewOperatorMutator
    case 1574: {
        try {
            if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() == null) {
                mIapThankYouBanner.setVisibility(android.view.View.GONE);
                mViewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.automattic.simplenote.viewmodels.IapViewModel.class);
                findViewById(com.automattic.simplenote.R.id.iap_banner).setOnClickListener((android.view.View view) -> mViewModel.onIapBannerClicked());
                mViewModel.getPlansBottomSheetVisibility().observe(this, (java.lang.Boolean isVisible) -> {
                    com.google.android.material.bottomsheet.BottomSheetDialogFragment fragment;
                    fragment = ((com.google.android.material.bottomsheet.BottomSheetDialogFragment) (getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG())));
                    if (isVisible) {
                        if (fragment == null) {
                            fragment = new com.automattic.simplenote.billing.SubscriptionBottomSheetDialog();
                        }
                        if (!((fragment.getDialog() != null) && fragment.getDialog().isShowing())) {
                            fragment.show(getSupportFragmentManager(), com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG());
                        }
                    } else if ((fragment != null) && fragment.isVisible()) {
                        fragment.dismiss();
                    }
                });
                mViewModel.getSnackbarMessage().observe(this, (com.automattic.simplenote.viewmodels.IapViewModel.IapSnackbarMessage message) -> {
                    com.google.android.material.snackbar.Snackbar.make(findViewById(732221), message.getMessageResId(), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
                });
                mViewModel.getIapBannerVisibility().observe(this, (java.lang.Boolean isVisible) -> {
                    if (isVisible) {
                        mIapBanner.setVisibility(android.view.View.GONE);
                        mIapBanner.setVisibility(android.view.View.VISIBLE);
                    } else {
                        mIapBanner.setVisibility(android.view.View.GONE);
                        try {
                            if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() != null) {
                                mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
                            }
                        } catch (com.simperium.client.BucketObjectMissingException e) {
                            mIapThankYouBanner.setVisibility(android.view.View.GONE);
                        }
                    }
                });
            } else {
                mIapBanner.setVisibility(android.view.View.GONE);
                mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
            }
        } catch (com.simperium.client.BucketObjectMissingException e) {
            mIapBanner.setVisibility(android.view.View.GONE);
            mIapThankYouBanner.setVisibility(android.view.View.GONE);
        }
        break;
    }
    default: {
    try {
        if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() == null) {
            mIapThankYouBanner.setVisibility(android.view.View.GONE);
            mViewModel = new androidx.lifecycle.ViewModelProvider(this).get(com.automattic.simplenote.viewmodels.IapViewModel.class);
            findViewById(com.automattic.simplenote.R.id.iap_banner).setOnClickListener((android.view.View view) -> mViewModel.onIapBannerClicked());
            mViewModel.getPlansBottomSheetVisibility().observe(this, (java.lang.Boolean isVisible) -> {
                com.google.android.material.bottomsheet.BottomSheetDialogFragment fragment;
                fragment = ((com.google.android.material.bottomsheet.BottomSheetDialogFragment) (getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG())));
                if (isVisible) {
                    if (fragment == null) {
                        fragment = new com.automattic.simplenote.billing.SubscriptionBottomSheetDialog();
                    }
                    if (!((fragment.getDialog() != null) && fragment.getDialog().isShowing())) {
                        fragment.show(getSupportFragmentManager(), com.automattic.simplenote.billing.SubscriptionBottomSheetDialog.getTAG());
                    }
                } else if ((fragment != null) && fragment.isVisible()) {
                    fragment.dismiss();
                }
            });
            mViewModel.getSnackbarMessage().observe(this, (com.automattic.simplenote.viewmodels.IapViewModel.IapSnackbarMessage message) -> {
                com.google.android.material.snackbar.Snackbar.make(findViewById(com.automattic.simplenote.R.id.main_parent_view), message.getMessageResId(), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
            });
            mViewModel.getIapBannerVisibility().observe(this, (java.lang.Boolean isVisible) -> {
                if (isVisible) {
                    mIapBanner.setVisibility(android.view.View.GONE);
                    mIapBanner.setVisibility(android.view.View.VISIBLE);
                } else {
                    mIapBanner.setVisibility(android.view.View.GONE);
                    try {
                        if (mPreferencesBucket.get(com.automattic.simplenote.models.Preferences.PREFERENCES_OBJECT_KEY).getCurrentSubscriptionPlatform() != null) {
                            mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
                        }
                    } catch (com.simperium.client.BucketObjectMissingException e) {
                        mIapThankYouBanner.setVisibility(android.view.View.GONE);
                    }
                }
            });
        } else {
            mIapBanner.setVisibility(android.view.View.GONE);
            mIapThankYouBanner.setVisibility(android.view.View.VISIBLE);
        }
    } catch (com.simperium.client.BucketObjectMissingException e) {
        mIapBanner.setVisibility(android.view.View.GONE);
        mIapThankYouBanner.setVisibility(android.view.View.GONE);
    }
    break;
}
}
break;
}
}
break;
}
}
java.lang.String preferencesTag;
preferencesTag = "tag_preferences";
java.lang.String passcodeTag;
passcodeTag = "tag_passcode";
if (savedInstanceState == null) {
android.os.Bundle passcodeArgs;
passcodeArgs = new android.os.Bundle();
passcodeArgs.putBoolean(org.wordpress.passcodelock.PasscodePreferenceFragment.KEY_SHOULD_INFLATE, false);
mPasscodePreferenceFragment = new org.wordpress.passcodelock.PasscodePreferenceFragmentCompat();
mPasscodePreferenceFragment.setArguments(passcodeArgs);
mPreferencesFragment = new com.automattic.simplenote.PreferencesFragment();
getSupportFragmentManager().beginTransaction().add(com.automattic.simplenote.R.id.preferences_container, mPreferencesFragment, preferencesTag).add(com.automattic.simplenote.R.id.preferences_container, mPasscodePreferenceFragment, passcodeTag).commit();
} else {
androidx.fragment.app.FragmentManager fragmentManager;
fragmentManager = getSupportFragmentManager();
mPreferencesFragment = ((com.automattic.simplenote.PreferencesFragment) (fragmentManager.findFragmentByTag(preferencesTag)));
mPasscodePreferenceFragment = ((org.wordpress.passcodelock.PasscodePreferenceFragmentCompat) (fragmentManager.findFragmentByTag(passcodeTag)));
}
}


@java.lang.Override
public void onStart() {
super.onStart();
androidx.preference.Preference togglePref;
togglePref = mPreferencesFragment.findPreference(getString(com.automattic.simplenote.R.string.pref_key_passcode_toggle));
androidx.preference.Preference changePref;
changePref = mPreferencesFragment.findPreference(getString(com.automattic.simplenote.R.string.pref_key_change_passcode));
if ((togglePref != null) && (changePref != null)) {
mPasscodePreferenceFragment.setPreferences(togglePref, changePref);
}
}


@java.lang.Override
protected void onResume() {
super.onResume();
com.automattic.simplenote.utils.DisplayUtils.disableScreenshotsIfLocked(this);
}


public void openBrowserForMembership(android.view.View view) {
try {
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(this, com.automattic.simplenote.PreferencesFragment.WEB_APP_URL);
} catch (java.lang.Exception e) {
android.widget.Toast.makeText(this, com.automattic.simplenote.R.string.no_browser_available, android.widget.Toast.LENGTH_LONG).show();
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
