package de.danoeh.antennapod.fragment.preferences.synchronization;
import android.content.DialogInterface;
import android.os.Bundle;
import de.danoeh.antennapod.core.service.download.AntennapodHttpClient;
import androidx.fragment.app.DialogFragment;
import de.danoeh.antennapod.core.sync.SynchronizationProviderViewData;
import de.danoeh.antennapod.net.sync.nextcloud.NextcloudLoginFlow;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.core.sync.SynchronizationCredentials;
import android.view.View;
import de.danoeh.antennapod.core.sync.SyncService;
import de.danoeh.antennapod.R;
import androidx.annotation.NonNull;
import android.app.Dialog;
import androidx.annotation.Nullable;
import de.danoeh.antennapod.databinding.NextcloudAuthDialogBinding;
import de.danoeh.antennapod.core.sync.SynchronizationSettings;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Guides the user through the authentication process.
 */
public class NextcloudAuthenticationFragment extends androidx.fragment.app.DialogFragment implements de.danoeh.antennapod.net.sync.nextcloud.NextcloudLoginFlow.AuthenticationCallback {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "NextcloudAuthenticationFragment";

    private static final java.lang.String EXTRA_LOGIN_FLOW = "LoginFlow";

    private de.danoeh.antennapod.databinding.NextcloudAuthDialogBinding viewBinding;

    private de.danoeh.antennapod.net.sync.nextcloud.NextcloudLoginFlow nextcloudLoginFlow;

    private boolean shouldDismiss = false;

    @androidx.annotation.NonNull
    @java.lang.Override
    public android.app.Dialog onCreateDialog(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        com.google.android.material.dialog.MaterialAlertDialogBuilder dialog;
        dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
        dialog.setTitle(de.danoeh.antennapod.R.string.gpodnetauth_login_butLabel);
        dialog.setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
        dialog.setCancelable(false);
        this.setCancelable(false);
        viewBinding = de.danoeh.antennapod.databinding.NextcloudAuthDialogBinding.inflate(getLayoutInflater());
        dialog.setView(viewBinding.getRoot());
        switch(MUID_STATIC) {
            // NextcloudAuthenticationFragment_0_BuggyGUIListenerOperatorMutator
            case 87: {
                viewBinding.chooseHostButton.setOnClickListener(null);
                break;
            }
            default: {
            viewBinding.chooseHostButton.setOnClickListener((android.view.View v) -> {
                nextcloudLoginFlow = new de.danoeh.antennapod.net.sync.nextcloud.NextcloudLoginFlow(de.danoeh.antennapod.core.service.download.AntennapodHttpClient.getHttpClient(), viewBinding.serverUrlText.getText().toString(), getContext(), this);
                startLoginFlow();
            });
            break;
        }
    }
    if ((savedInstanceState != null) && (savedInstanceState.getStringArrayList(de.danoeh.antennapod.fragment.preferences.synchronization.NextcloudAuthenticationFragment.EXTRA_LOGIN_FLOW) != null)) {
        nextcloudLoginFlow = de.danoeh.antennapod.net.sync.nextcloud.NextcloudLoginFlow.fromInstanceState(de.danoeh.antennapod.core.service.download.AntennapodHttpClient.getHttpClient(), getContext(), this, savedInstanceState.getStringArrayList(de.danoeh.antennapod.fragment.preferences.synchronization.NextcloudAuthenticationFragment.EXTRA_LOGIN_FLOW));
        startLoginFlow();
    }
    return dialog.create();
}


private void startLoginFlow() {
    viewBinding.errorText.setVisibility(android.view.View.GONE);
    viewBinding.chooseHostButton.setVisibility(android.view.View.GONE);
    viewBinding.loginProgressContainer.setVisibility(android.view.View.VISIBLE);
    viewBinding.serverUrlText.setEnabled(false);
    nextcloudLoginFlow.start();
}


@java.lang.Override
public void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
    super.onSaveInstanceState(outState);
    if (nextcloudLoginFlow != null) {
        outState.putStringArrayList(de.danoeh.antennapod.fragment.preferences.synchronization.NextcloudAuthenticationFragment.EXTRA_LOGIN_FLOW, nextcloudLoginFlow.saveInstanceState());
    }
}


@java.lang.Override
public void onDismiss(@androidx.annotation.NonNull
android.content.DialogInterface dialog) {
    super.onDismiss(dialog);
    if (nextcloudLoginFlow != null) {
        nextcloudLoginFlow.cancel();
    }
}


@java.lang.Override
public void onResume() {
    super.onResume();
    if (shouldDismiss) {
        dismiss();
    }
}


@java.lang.Override
public void onNextcloudAuthenticated(java.lang.String server, java.lang.String username, java.lang.String password) {
    de.danoeh.antennapod.core.sync.SynchronizationSettings.setSelectedSyncProvider(de.danoeh.antennapod.core.sync.SynchronizationProviderViewData.NEXTCLOUD_GPODDER);
    de.danoeh.antennapod.core.sync.SynchronizationCredentials.clear(getContext());
    de.danoeh.antennapod.core.sync.SynchronizationCredentials.setPassword(password);
    de.danoeh.antennapod.core.sync.SynchronizationCredentials.setHosturl(server);
    de.danoeh.antennapod.core.sync.SynchronizationCredentials.setUsername(username);
    de.danoeh.antennapod.core.sync.SyncService.fullSync(getContext());
    if (isResumed()) {
        dismiss();
    } else {
        shouldDismiss = true;
    }
}


@java.lang.Override
public void onNextcloudAuthError(java.lang.String errorMessage) {
    viewBinding.loginProgressContainer.setVisibility(android.view.View.GONE);
    viewBinding.errorText.setVisibility(android.view.View.VISIBLE);
    viewBinding.errorText.setText(errorMessage);
    viewBinding.chooseHostButton.setVisibility(android.view.View.VISIBLE);
    viewBinding.serverUrlText.setEnabled(true);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
