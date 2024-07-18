package de.danoeh.antennapod.ui.home.sections;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.activity.result.contract.ActivityResultContracts;
import de.danoeh.antennapod.activity.MainActivity;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import de.danoeh.antennapod.databinding.HomeSectionNotificationBinding;
import de.danoeh.antennapod.ui.home.HomeFragment;
import android.Manifest;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AllowNotificationsSection extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    de.danoeh.antennapod.databinding.HomeSectionNotificationBinding viewBinding;

    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestPermissionLauncher = registerForActivityResult(new androidx.activity.result.contract.ActivityResultContracts.RequestPermission(), (java.lang.Boolean isGranted) -> {
        if (isGranted) {
            ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadFragment(de.danoeh.antennapod.ui.home.HomeFragment.TAG, null);
        } else {
            viewBinding.openSettingsButton.setVisibility(android.view.View.VISIBLE);
            viewBinding.allowButton.setVisibility(android.view.View.GONE);
            android.widget.Toast.makeText(getContext(), de.danoeh.antennapod.R.string.notification_permission_denied, android.widget.Toast.LENGTH_LONG).show();
        }
    });

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        viewBinding = de.danoeh.antennapod.databinding.HomeSectionNotificationBinding.inflate(inflater);
        switch(MUID_STATIC) {
            // AllowNotificationsSection_0_BuggyGUIListenerOperatorMutator
            case 159: {
                viewBinding.allowButton.setOnClickListener(null);
                break;
            }
            default: {
            viewBinding.allowButton.setOnClickListener((android.view.View v) -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
                }
            });
            break;
        }
    }
    switch(MUID_STATIC) {
        // AllowNotificationsSection_1_BuggyGUIListenerOperatorMutator
        case 1159: {
            viewBinding.openSettingsButton.setOnClickListener(null);
            break;
        }
        default: {
        viewBinding.openSettingsButton.setOnClickListener((android.view.View view) -> {
            android.content.Intent intent;
            switch(MUID_STATIC) {
                // AllowNotificationsSection_2_InvalidKeyIntentOperatorMutator
                case 2159: {
                    intent = new android.content.Intent((String) null);
                    break;
                }
                // AllowNotificationsSection_3_RandomActionIntentDefinitionOperatorMutator
                case 3159: {
                    intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                    break;
                }
                default: {
                intent = new android.content.Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                break;
            }
        }
        android.net.Uri uri;
        uri = android.net.Uri.fromParts("package", getContext().getPackageName(), null);
        switch(MUID_STATIC) {
            // AllowNotificationsSection_4_RandomActionIntentDefinitionOperatorMutator
            case 4159: {
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
            intent.setData(uri);
            break;
        }
    }
    startActivity(intent);
});
break;
}
}
switch(MUID_STATIC) {
// AllowNotificationsSection_5_BuggyGUIListenerOperatorMutator
case 5159: {
viewBinding.denyButton.setOnClickListener(null);
break;
}
default: {
viewBinding.denyButton.setOnClickListener((android.view.View v) -> {
com.google.android.material.dialog.MaterialAlertDialogBuilder builder;
builder = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
builder.setMessage(de.danoeh.antennapod.R.string.notification_permission_deny_warning);
switch(MUID_STATIC) {
    // AllowNotificationsSection_6_BuggyGUIListenerOperatorMutator
    case 6159: {
        builder.setPositiveButton(de.danoeh.antennapod.R.string.deny_label, null);
        break;
    }
    default: {
    builder.setPositiveButton(de.danoeh.antennapod.R.string.deny_label, (android.content.DialogInterface dialog,int which) -> {
        getContext().getSharedPreferences(de.danoeh.antennapod.ui.home.HomeFragment.PREF_NAME, android.content.Context.MODE_PRIVATE).edit().putBoolean(de.danoeh.antennapod.ui.home.HomeFragment.PREF_DISABLE_NOTIFICATION_PERMISSION_NAG, true).apply();
        ((de.danoeh.antennapod.activity.MainActivity) (getActivity())).loadFragment(de.danoeh.antennapod.ui.home.HomeFragment.TAG, null);
    });
    break;
}
}
builder.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
builder.show();
});
break;
}
}
return viewBinding.getRoot();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
