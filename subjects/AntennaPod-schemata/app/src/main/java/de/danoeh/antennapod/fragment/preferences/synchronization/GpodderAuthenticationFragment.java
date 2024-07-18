package de.danoeh.antennapod.fragment.preferences.synchronization;
import java.util.Locale;
import io.reactivex.Completable;
import java.util.regex.Matcher;
import androidx.fragment.app.DialogFragment;
import android.view.inputmethod.InputMethodManager;
import de.danoeh.antennapod.core.sync.SynchronizationProviderViewData;
import com.google.android.material.button.MaterialButton;
import de.danoeh.antennapod.R;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.os.Build;
import android.widget.TextView;
import java.util.List;
import android.widget.ProgressBar;
import de.danoeh.antennapod.core.sync.SynchronizationSettings;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.regex.Pattern;
import android.widget.LinearLayout;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import de.danoeh.antennapod.core.service.download.AntennapodHttpClient;
import io.reactivex.Observable;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.core.sync.SynchronizationCredentials;
import android.view.View;
import android.widget.EditText;
import de.danoeh.antennapod.core.sync.SyncService;
import de.danoeh.antennapod.net.sync.gpoddernet.GpodnetService;
import de.danoeh.antennapod.core.util.FileNameGenerator;
import de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice;
import androidx.annotation.Nullable;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Guides the user through the authentication process.
 */
public class GpodderAuthenticationFragment extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = "GpodnetAuthActivity";

    private android.widget.ViewFlipper viewFlipper;

    private static final int STEP_DEFAULT = -1;

    private static final int STEP_HOSTNAME = 0;

    private static final int STEP_LOGIN = 1;

    private static final int STEP_DEVICE = 2;

    private static final int STEP_FINISH = 3;

    private int currentStep = -1;

    private de.danoeh.antennapod.net.sync.gpoddernet.GpodnetService service;

    private volatile java.lang.String username;

    private volatile java.lang.String password;

    private volatile de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice selectedDevice;

    private java.util.List<de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice> devices;

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
        android.view.View root;
        root = android.view.View.inflate(getContext(), de.danoeh.antennapod.R.layout.gpodnetauth_dialog, null);
        switch(MUID_STATIC) {
            // GpodderAuthenticationFragment_0_InvalidViewFocusOperatorMutator
            case 86: {
                /**
                * Inserted by Kadabra
                */
                viewFlipper = root.findViewById(de.danoeh.antennapod.R.id.viewflipper);
                viewFlipper.requestFocus();
                break;
            }
            // GpodderAuthenticationFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1086: {
                /**
                * Inserted by Kadabra
                */
                viewFlipper = root.findViewById(de.danoeh.antennapod.R.id.viewflipper);
                viewFlipper.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            viewFlipper = root.findViewById(de.danoeh.antennapod.R.id.viewflipper);
            break;
        }
    }
    advance();
    dialog.setView(root);
    return dialog.create();
}


private void setupHostView(android.view.View view) {
    final android.widget.Button selectHost;
    switch(MUID_STATIC) {
        // GpodderAuthenticationFragment_2_InvalidViewFocusOperatorMutator
        case 2086: {
            /**
            * Inserted by Kadabra
            */
            selectHost = view.findViewById(de.danoeh.antennapod.R.id.chooseHostButton);
            selectHost.requestFocus();
            break;
        }
        // GpodderAuthenticationFragment_3_ViewComponentNotVisibleOperatorMutator
        case 3086: {
            /**
            * Inserted by Kadabra
            */
            selectHost = view.findViewById(de.danoeh.antennapod.R.id.chooseHostButton);
            selectHost.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        selectHost = view.findViewById(de.danoeh.antennapod.R.id.chooseHostButton);
        break;
    }
}
final android.widget.EditText serverUrlText;
switch(MUID_STATIC) {
    // GpodderAuthenticationFragment_4_InvalidViewFocusOperatorMutator
    case 4086: {
        /**
        * Inserted by Kadabra
        */
        serverUrlText = view.findViewById(de.danoeh.antennapod.R.id.serverUrlText);
        serverUrlText.requestFocus();
        break;
    }
    // GpodderAuthenticationFragment_5_ViewComponentNotVisibleOperatorMutator
    case 5086: {
        /**
        * Inserted by Kadabra
        */
        serverUrlText = view.findViewById(de.danoeh.antennapod.R.id.serverUrlText);
        serverUrlText.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    serverUrlText = view.findViewById(de.danoeh.antennapod.R.id.serverUrlText);
    break;
}
}
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_6_BuggyGUIListenerOperatorMutator
case 6086: {
    selectHost.setOnClickListener(null);
    break;
}
default: {
selectHost.setOnClickListener((android.view.View v) -> {
    if (serverUrlText.getText().length() == 0) {
        return;
    }
    de.danoeh.antennapod.core.sync.SynchronizationCredentials.clear(getContext());
    de.danoeh.antennapod.core.sync.SynchronizationCredentials.setHosturl(serverUrlText.getText().toString());
    service = new de.danoeh.antennapod.net.sync.gpoddernet.GpodnetService(de.danoeh.antennapod.core.service.download.AntennapodHttpClient.getHttpClient(), de.danoeh.antennapod.core.sync.SynchronizationCredentials.getHosturl(), de.danoeh.antennapod.core.sync.SynchronizationCredentials.getDeviceID(), de.danoeh.antennapod.core.sync.SynchronizationCredentials.getUsername(), de.danoeh.antennapod.core.sync.SynchronizationCredentials.getPassword());
    getDialog().setTitle(de.danoeh.antennapod.core.sync.SynchronizationCredentials.getHosturl());
    advance();
});
break;
}
}
}


private void setupLoginView(android.view.View view) {
final android.widget.EditText username;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_7_InvalidViewFocusOperatorMutator
case 7086: {
/**
* Inserted by Kadabra
*/
username = view.findViewById(de.danoeh.antennapod.R.id.etxtUsername);
username.requestFocus();
break;
}
// GpodderAuthenticationFragment_8_ViewComponentNotVisibleOperatorMutator
case 8086: {
/**
* Inserted by Kadabra
*/
username = view.findViewById(de.danoeh.antennapod.R.id.etxtUsername);
username.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
username = view.findViewById(de.danoeh.antennapod.R.id.etxtUsername);
break;
}
}
final android.widget.EditText password;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_9_InvalidViewFocusOperatorMutator
case 9086: {
/**
* Inserted by Kadabra
*/
password = view.findViewById(de.danoeh.antennapod.R.id.etxtPassword);
password.requestFocus();
break;
}
// GpodderAuthenticationFragment_10_ViewComponentNotVisibleOperatorMutator
case 10086: {
/**
* Inserted by Kadabra
*/
password = view.findViewById(de.danoeh.antennapod.R.id.etxtPassword);
password.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
password = view.findViewById(de.danoeh.antennapod.R.id.etxtPassword);
break;
}
}
final android.widget.Button login;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_11_InvalidViewFocusOperatorMutator
case 11086: {
/**
* Inserted by Kadabra
*/
login = view.findViewById(de.danoeh.antennapod.R.id.butLogin);
login.requestFocus();
break;
}
// GpodderAuthenticationFragment_12_ViewComponentNotVisibleOperatorMutator
case 12086: {
/**
* Inserted by Kadabra
*/
login = view.findViewById(de.danoeh.antennapod.R.id.butLogin);
login.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
login = view.findViewById(de.danoeh.antennapod.R.id.butLogin);
break;
}
}
final android.widget.TextView txtvError;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_13_InvalidViewFocusOperatorMutator
case 13086: {
/**
* Inserted by Kadabra
*/
txtvError = view.findViewById(de.danoeh.antennapod.R.id.credentialsError);
txtvError.requestFocus();
break;
}
// GpodderAuthenticationFragment_14_ViewComponentNotVisibleOperatorMutator
case 14086: {
/**
* Inserted by Kadabra
*/
txtvError = view.findViewById(de.danoeh.antennapod.R.id.credentialsError);
txtvError.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvError = view.findViewById(de.danoeh.antennapod.R.id.credentialsError);
break;
}
}
final android.widget.ProgressBar progressBar;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_15_InvalidViewFocusOperatorMutator
case 15086: {
/**
* Inserted by Kadabra
*/
progressBar = view.findViewById(de.danoeh.antennapod.R.id.progBarLogin);
progressBar.requestFocus();
break;
}
// GpodderAuthenticationFragment_16_ViewComponentNotVisibleOperatorMutator
case 16086: {
/**
* Inserted by Kadabra
*/
progressBar = view.findViewById(de.danoeh.antennapod.R.id.progBarLogin);
progressBar.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progressBar = view.findViewById(de.danoeh.antennapod.R.id.progBarLogin);
break;
}
}
final android.widget.TextView createAccountWarning;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_17_InvalidViewFocusOperatorMutator
case 17086: {
/**
* Inserted by Kadabra
*/
createAccountWarning = view.findViewById(de.danoeh.antennapod.R.id.createAccountWarning);
createAccountWarning.requestFocus();
break;
}
// GpodderAuthenticationFragment_18_ViewComponentNotVisibleOperatorMutator
case 18086: {
/**
* Inserted by Kadabra
*/
createAccountWarning = view.findViewById(de.danoeh.antennapod.R.id.createAccountWarning);
createAccountWarning.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
createAccountWarning = view.findViewById(de.danoeh.antennapod.R.id.createAccountWarning);
break;
}
}
if (de.danoeh.antennapod.core.sync.SynchronizationCredentials.getHosturl().startsWith("http://")) {
createAccountWarning.setVisibility(android.view.View.VISIBLE);
}
password.setOnEditorActionListener((android.widget.TextView v,int actionID,android.view.KeyEvent event) -> (actionID == android.view.inputmethod.EditorInfo.IME_ACTION_GO) && login.performClick());
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_19_BuggyGUIListenerOperatorMutator
case 19086: {
login.setOnClickListener(null);
break;
}
default: {
login.setOnClickListener((android.view.View v) -> {
final java.lang.String usernameStr;
usernameStr = username.getText().toString();
final java.lang.String passwordStr;
passwordStr = password.getText().toString();
if (usernameHasUnwantedChars(usernameStr)) {
txtvError.setText(de.danoeh.antennapod.R.string.gpodnetsync_username_characters_error);
txtvError.setVisibility(android.view.View.VISIBLE);
return;
}
login.setEnabled(false);
progressBar.setVisibility(android.view.View.VISIBLE);
txtvError.setVisibility(android.view.View.GONE);
android.view.inputmethod.InputMethodManager inputManager;
inputManager = ((android.view.inputmethod.InputMethodManager) (getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
inputManager.hideSoftInputFromWindow(login.getWindowToken(), android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS);
io.reactivex.Completable.fromAction(() -> {
service.setCredentials(usernameStr, passwordStr);
service.login();
devices = service.getDevices();
this.username = usernameStr;
this.password = passwordStr;
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> {
login.setEnabled(true);
progressBar.setVisibility(android.view.View.GONE);
advance();
}, (java.lang.Throwable error) -> {
login.setEnabled(true);
progressBar.setVisibility(android.view.View.GONE);
txtvError.setText(error.getCause().getMessage());
txtvError.setVisibility(android.view.View.VISIBLE);
});
});
break;
}
}
}


private void setupDeviceView(android.view.View view) {
final android.widget.EditText deviceName;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_20_InvalidViewFocusOperatorMutator
case 20086: {
/**
* Inserted by Kadabra
*/
deviceName = view.findViewById(de.danoeh.antennapod.R.id.deviceName);
deviceName.requestFocus();
break;
}
// GpodderAuthenticationFragment_21_ViewComponentNotVisibleOperatorMutator
case 21086: {
/**
* Inserted by Kadabra
*/
deviceName = view.findViewById(de.danoeh.antennapod.R.id.deviceName);
deviceName.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
deviceName = view.findViewById(de.danoeh.antennapod.R.id.deviceName);
break;
}
}
final android.widget.LinearLayout devicesContainer;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_22_InvalidViewFocusOperatorMutator
case 22086: {
/**
* Inserted by Kadabra
*/
devicesContainer = view.findViewById(de.danoeh.antennapod.R.id.devicesContainer);
devicesContainer.requestFocus();
break;
}
// GpodderAuthenticationFragment_23_ViewComponentNotVisibleOperatorMutator
case 23086: {
/**
* Inserted by Kadabra
*/
devicesContainer = view.findViewById(de.danoeh.antennapod.R.id.devicesContainer);
devicesContainer.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
devicesContainer = view.findViewById(de.danoeh.antennapod.R.id.devicesContainer);
break;
}
}
deviceName.setText(generateDeviceName());
com.google.android.material.button.MaterialButton createDeviceButton;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_24_InvalidViewFocusOperatorMutator
case 24086: {
/**
* Inserted by Kadabra
*/
createDeviceButton = view.findViewById(de.danoeh.antennapod.R.id.createDeviceButton);
createDeviceButton.requestFocus();
break;
}
// GpodderAuthenticationFragment_25_ViewComponentNotVisibleOperatorMutator
case 25086: {
/**
* Inserted by Kadabra
*/
createDeviceButton = view.findViewById(de.danoeh.antennapod.R.id.createDeviceButton);
createDeviceButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
createDeviceButton = view.findViewById(de.danoeh.antennapod.R.id.createDeviceButton);
break;
}
}
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_26_BuggyGUIListenerOperatorMutator
case 26086: {
createDeviceButton.setOnClickListener(null);
break;
}
default: {
createDeviceButton.setOnClickListener((android.view.View v) -> createDevice(view));
break;
}
}
for (de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice device : devices) {
android.view.View row;
row = android.view.View.inflate(getContext(), de.danoeh.antennapod.R.layout.gpodnetauth_device_row, null);
android.widget.Button selectDeviceButton;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_27_InvalidViewFocusOperatorMutator
case 27086: {
/**
* Inserted by Kadabra
*/
selectDeviceButton = row.findViewById(de.danoeh.antennapod.R.id.selectDeviceButton);
selectDeviceButton.requestFocus();
break;
}
// GpodderAuthenticationFragment_28_ViewComponentNotVisibleOperatorMutator
case 28086: {
/**
* Inserted by Kadabra
*/
selectDeviceButton = row.findViewById(de.danoeh.antennapod.R.id.selectDeviceButton);
selectDeviceButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
selectDeviceButton = row.findViewById(de.danoeh.antennapod.R.id.selectDeviceButton);
break;
}
}
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_29_BuggyGUIListenerOperatorMutator
case 29086: {
selectDeviceButton.setOnClickListener(null);
break;
}
default: {
selectDeviceButton.setOnClickListener((android.view.View v) -> {
selectedDevice = device;
advance();
});
break;
}
}
selectDeviceButton.setText(device.getCaption());
devicesContainer.addView(row);
}
}


private void createDevice(android.view.View view) {
final android.widget.EditText deviceName;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_30_InvalidViewFocusOperatorMutator
case 30086: {
/**
* Inserted by Kadabra
*/
deviceName = view.findViewById(de.danoeh.antennapod.R.id.deviceName);
deviceName.requestFocus();
break;
}
// GpodderAuthenticationFragment_31_ViewComponentNotVisibleOperatorMutator
case 31086: {
/**
* Inserted by Kadabra
*/
deviceName = view.findViewById(de.danoeh.antennapod.R.id.deviceName);
deviceName.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
deviceName = view.findViewById(de.danoeh.antennapod.R.id.deviceName);
break;
}
}
final android.widget.TextView txtvError;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_32_InvalidViewFocusOperatorMutator
case 32086: {
/**
* Inserted by Kadabra
*/
txtvError = view.findViewById(de.danoeh.antennapod.R.id.deviceSelectError);
txtvError.requestFocus();
break;
}
// GpodderAuthenticationFragment_33_ViewComponentNotVisibleOperatorMutator
case 33086: {
/**
* Inserted by Kadabra
*/
txtvError = view.findViewById(de.danoeh.antennapod.R.id.deviceSelectError);
txtvError.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvError = view.findViewById(de.danoeh.antennapod.R.id.deviceSelectError);
break;
}
}
final android.widget.ProgressBar progBarCreateDevice;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_34_InvalidViewFocusOperatorMutator
case 34086: {
/**
* Inserted by Kadabra
*/
progBarCreateDevice = view.findViewById(de.danoeh.antennapod.R.id.progbarCreateDevice);
progBarCreateDevice.requestFocus();
break;
}
// GpodderAuthenticationFragment_35_ViewComponentNotVisibleOperatorMutator
case 35086: {
/**
* Inserted by Kadabra
*/
progBarCreateDevice = view.findViewById(de.danoeh.antennapod.R.id.progbarCreateDevice);
progBarCreateDevice.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
progBarCreateDevice = view.findViewById(de.danoeh.antennapod.R.id.progbarCreateDevice);
break;
}
}
java.lang.String deviceNameStr;
deviceNameStr = deviceName.getText().toString();
if (isDeviceInList(deviceNameStr)) {
return;
}
progBarCreateDevice.setVisibility(android.view.View.VISIBLE);
txtvError.setVisibility(android.view.View.GONE);
deviceName.setEnabled(false);
io.reactivex.Observable.fromCallable(() -> {
java.lang.String deviceId;
deviceId = generateDeviceId(deviceNameStr);
service.configureDevice(deviceId, deviceNameStr, de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice.DeviceType.MOBILE);
return new de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice(deviceId, deviceNameStr, de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice.DeviceType.MOBILE.toString(), 0);
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe((de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice device) -> {
progBarCreateDevice.setVisibility(android.view.View.GONE);
selectedDevice = device;
advance();
}, (java.lang.Throwable error) -> {
deviceName.setEnabled(true);
progBarCreateDevice.setVisibility(android.view.View.GONE);
txtvError.setText(error.getMessage());
txtvError.setVisibility(android.view.View.VISIBLE);
});
}


private java.lang.String generateDeviceName() {
java.lang.String baseName;
baseName = getString(de.danoeh.antennapod.R.string.gpodnetauth_device_name_default, android.os.Build.MODEL);
java.lang.String name;
name = baseName;
int num;
num = 1;
while (isDeviceInList(name)) {
name = ((baseName + " (") + num) + ")";
num++;
} 
return name;
}


private java.lang.String generateDeviceId(java.lang.String name) {
// devices names must be of a certain form:
// https://gpoddernet.readthedocs.org/en/latest/api/reference/general.html#devices
return de.danoeh.antennapod.core.util.FileNameGenerator.generateFileName(name).replaceAll("\\W", "_").toLowerCase(java.util.Locale.US);
}


private boolean isDeviceInList(java.lang.String name) {
if (devices == null) {
return false;
}
java.lang.String id;
id = generateDeviceId(name);
for (de.danoeh.antennapod.net.sync.gpoddernet.model.GpodnetDevice device : devices) {
if (device.getId().equals(id) || device.getCaption().equals(name)) {
return true;
}
}
return false;
}


private void setupFinishView(android.view.View view) {
final android.widget.Button sync;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_36_InvalidViewFocusOperatorMutator
case 36086: {
/**
* Inserted by Kadabra
*/
sync = view.findViewById(de.danoeh.antennapod.R.id.butSyncNow);
sync.requestFocus();
break;
}
// GpodderAuthenticationFragment_37_ViewComponentNotVisibleOperatorMutator
case 37086: {
/**
* Inserted by Kadabra
*/
sync = view.findViewById(de.danoeh.antennapod.R.id.butSyncNow);
sync.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
sync = view.findViewById(de.danoeh.antennapod.R.id.butSyncNow);
break;
}
}
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_38_BuggyGUIListenerOperatorMutator
case 38086: {
sync.setOnClickListener(null);
break;
}
default: {
sync.setOnClickListener((android.view.View v) -> {
dismiss();
de.danoeh.antennapod.core.sync.SyncService.sync(getContext());
});
break;
}
}
}


private void advance() {
if (currentStep < de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment.STEP_FINISH) {
android.view.View view;
switch(MUID_STATIC) {
// GpodderAuthenticationFragment_39_BinaryMutator
case 39086: {
view = viewFlipper.getChildAt(currentStep - 1);
break;
}
default: {
view = viewFlipper.getChildAt(currentStep + 1);
break;
}
}
if (currentStep == de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment.STEP_DEFAULT) {
setupHostView(view);
} else if (currentStep == de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment.STEP_HOSTNAME) {
setupLoginView(view);
} else if (currentStep == de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment.STEP_LOGIN) {
if ((username == null) || (password == null)) {
throw new java.lang.IllegalStateException("Username and password must not be null here");
} else {
setupDeviceView(view);
}
} else if (currentStep == de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment.STEP_DEVICE) {
if (selectedDevice == null) {
throw new java.lang.IllegalStateException("Device must not be null here");
} else {
de.danoeh.antennapod.core.sync.SynchronizationSettings.setSelectedSyncProvider(de.danoeh.antennapod.core.sync.SynchronizationProviderViewData.GPODDER_NET);
de.danoeh.antennapod.core.sync.SynchronizationCredentials.setUsername(username);
de.danoeh.antennapod.core.sync.SynchronizationCredentials.setPassword(password);
de.danoeh.antennapod.core.sync.SynchronizationCredentials.setDeviceID(selectedDevice.getId());
setupFinishView(view);
}
}
if (currentStep != de.danoeh.antennapod.fragment.preferences.synchronization.GpodderAuthenticationFragment.STEP_DEFAULT) {
viewFlipper.showNext();
}
currentStep++;
} else {
dismiss();
}
}


private boolean usernameHasUnwantedChars(java.lang.String username) {
java.util.regex.Pattern special;
special = java.util.regex.Pattern.compile("[!@#$%&*()+=|<>?{}\\[\\]~]");
java.util.regex.Matcher containsUnwantedChars;
containsUnwantedChars = special.matcher(username);
return containsUnwantedChars.find();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
