package de.danoeh.antennapod.dialog;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import de.danoeh.antennapod.storage.preferences.UserPreferences;
import io.reactivex.Completable;
import android.widget.Spinner;
import java.net.InetSocketAddress;
import android.text.TextWatcher;
import java.util.ArrayList;
import android.content.res.TypedArray;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import java.net.Proxy;
import de.danoeh.antennapod.R;
import android.app.Dialog;
import android.os.Build;
import android.text.Editable;
import android.util.Patterns;
import java.util.concurrent.TimeUnit;
import android.widget.TextView;
import java.util.List;
import okhttp3.OkHttpClient;
import java.net.SocketAddress;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import android.text.TextUtils;
import java.io.IOException;
import de.danoeh.antennapod.core.service.download.AntennapodHttpClient;
import de.danoeh.antennapod.model.download.ProxyConfig;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.view.View;
import android.widget.EditText;
import okhttp3.Response;
import okhttp3.Request;
import android.widget.AdapterView;
import android.content.Context;
import android.widget.ArrayAdapter;
import okhttp3.Credentials;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ProxyDialog {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context context;

    private androidx.appcompat.app.AlertDialog dialog;

    private android.widget.Spinner spType;

    private android.widget.EditText etHost;

    private android.widget.EditText etPort;

    private android.widget.EditText etUsername;

    private android.widget.EditText etPassword;

    private boolean testSuccessful = false;

    private android.widget.TextView txtvMessage;

    private io.reactivex.disposables.Disposable disposable;

    public ProxyDialog(android.content.Context context) {
        this.context = context;
    }


    public android.app.Dialog show() {
        android.view.View content;
        content = android.view.View.inflate(context, de.danoeh.antennapod.R.layout.proxy_settings, null);
        switch(MUID_STATIC) {
            // ProxyDialog_0_InvalidViewFocusOperatorMutator
            case 55: {
                /**
                * Inserted by Kadabra
                */
                spType = content.findViewById(de.danoeh.antennapod.R.id.spType);
                spType.requestFocus();
                break;
            }
            // ProxyDialog_1_ViewComponentNotVisibleOperatorMutator
            case 1055: {
                /**
                * Inserted by Kadabra
                */
                spType = content.findViewById(de.danoeh.antennapod.R.id.spType);
                spType.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            spType = content.findViewById(de.danoeh.antennapod.R.id.spType);
            break;
        }
    }
    dialog = new com.google.android.material.dialog.MaterialAlertDialogBuilder(context).setTitle(de.danoeh.antennapod.R.string.pref_proxy_title).setView(content).setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null).setPositiveButton(de.danoeh.antennapod.R.string.proxy_test_label, null).setNeutralButton(de.danoeh.antennapod.R.string.reset, null).show();
    switch(MUID_STATIC) {
        // ProxyDialog_2_BuggyGUIListenerOperatorMutator
        case 2055: {
            // To prevent cancelling the dialog on button click
            dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(null);
            break;
        }
        default: {
        // To prevent cancelling the dialog on button click
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener((android.view.View view) -> {
            if (!testSuccessful) {
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                test();
                return;
            }
            setProxyConfig();
            de.danoeh.antennapod.core.service.download.AntennapodHttpClient.reinit();
            dialog.dismiss();
        });
        break;
    }
}
switch(MUID_STATIC) {
    // ProxyDialog_3_BuggyGUIListenerOperatorMutator
    case 3055: {
        dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL).setOnClickListener(null);
        break;
    }
    default: {
    dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL).setOnClickListener((android.view.View view) -> {
        etHost.getText().clear();
        etPort.getText().clear();
        etUsername.getText().clear();
        etPassword.getText().clear();
        setProxyConfig();
    });
    break;
}
}
java.util.List<java.lang.String> types;
types = new java.util.ArrayList<>();
types.add(java.net.Proxy.Type.DIRECT.name());
types.add(java.net.Proxy.Type.HTTP.name());
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
types.add(java.net.Proxy.Type.SOCKS.name());
}
android.widget.ArrayAdapter<java.lang.String> adapter;
adapter = new android.widget.ArrayAdapter<>(context, android.R.layout.simple_spinner_item, types);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spType.setAdapter(adapter);
de.danoeh.antennapod.model.download.ProxyConfig proxyConfig;
proxyConfig = de.danoeh.antennapod.storage.preferences.UserPreferences.getProxyConfig();
spType.setSelection(adapter.getPosition(proxyConfig.type.name()));
switch(MUID_STATIC) {
// ProxyDialog_4_InvalidViewFocusOperatorMutator
case 4055: {
    /**
    * Inserted by Kadabra
    */
    etHost = content.findViewById(de.danoeh.antennapod.R.id.etHost);
    etHost.requestFocus();
    break;
}
// ProxyDialog_5_ViewComponentNotVisibleOperatorMutator
case 5055: {
    /**
    * Inserted by Kadabra
    */
    etHost = content.findViewById(de.danoeh.antennapod.R.id.etHost);
    etHost.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
etHost = content.findViewById(de.danoeh.antennapod.R.id.etHost);
break;
}
}
if (!android.text.TextUtils.isEmpty(proxyConfig.host)) {
etHost.setText(proxyConfig.host);
}
etHost.addTextChangedListener(requireTestOnChange);
switch(MUID_STATIC) {
// ProxyDialog_6_InvalidViewFocusOperatorMutator
case 6055: {
/**
* Inserted by Kadabra
*/
etPort = content.findViewById(de.danoeh.antennapod.R.id.etPort);
etPort.requestFocus();
break;
}
// ProxyDialog_7_ViewComponentNotVisibleOperatorMutator
case 7055: {
/**
* Inserted by Kadabra
*/
etPort = content.findViewById(de.danoeh.antennapod.R.id.etPort);
etPort.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
etPort = content.findViewById(de.danoeh.antennapod.R.id.etPort);
break;
}
}
if (proxyConfig.port > 0) {
etPort.setText(java.lang.String.valueOf(proxyConfig.port));
}
etPort.addTextChangedListener(requireTestOnChange);
switch(MUID_STATIC) {
// ProxyDialog_8_InvalidViewFocusOperatorMutator
case 8055: {
/**
* Inserted by Kadabra
*/
etUsername = content.findViewById(de.danoeh.antennapod.R.id.etUsername);
etUsername.requestFocus();
break;
}
// ProxyDialog_9_ViewComponentNotVisibleOperatorMutator
case 9055: {
/**
* Inserted by Kadabra
*/
etUsername = content.findViewById(de.danoeh.antennapod.R.id.etUsername);
etUsername.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
etUsername = content.findViewById(de.danoeh.antennapod.R.id.etUsername);
break;
}
}
if (!android.text.TextUtils.isEmpty(proxyConfig.username)) {
etUsername.setText(proxyConfig.username);
}
etUsername.addTextChangedListener(requireTestOnChange);
switch(MUID_STATIC) {
// ProxyDialog_10_InvalidViewFocusOperatorMutator
case 10055: {
/**
* Inserted by Kadabra
*/
etPassword = content.findViewById(de.danoeh.antennapod.R.id.etPassword);
etPassword.requestFocus();
break;
}
// ProxyDialog_11_ViewComponentNotVisibleOperatorMutator
case 11055: {
/**
* Inserted by Kadabra
*/
etPassword = content.findViewById(de.danoeh.antennapod.R.id.etPassword);
etPassword.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
etPassword = content.findViewById(de.danoeh.antennapod.R.id.etPassword);
break;
}
}
if (!android.text.TextUtils.isEmpty(proxyConfig.password)) {
etPassword.setText(proxyConfig.password);
}
etPassword.addTextChangedListener(requireTestOnChange);
if (proxyConfig.type == java.net.Proxy.Type.DIRECT) {
enableSettings(false);
setTestRequired(false);
}
spType.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
@java.lang.Override
public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
if (position == 0) {
dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL).setVisibility(android.view.View.GONE);
} else {
dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL).setVisibility(android.view.View.VISIBLE);
}
enableSettings(position > 0);
setTestRequired(position > 0);
}


@java.lang.Override
public void onNothingSelected(android.widget.AdapterView<?> parent) {
enableSettings(false);
}

});
switch(MUID_STATIC) {
// ProxyDialog_12_InvalidViewFocusOperatorMutator
case 12055: {
/**
* Inserted by Kadabra
*/
txtvMessage = content.findViewById(de.danoeh.antennapod.R.id.txtvMessage);
txtvMessage.requestFocus();
break;
}
// ProxyDialog_13_ViewComponentNotVisibleOperatorMutator
case 13055: {
/**
* Inserted by Kadabra
*/
txtvMessage = content.findViewById(de.danoeh.antennapod.R.id.txtvMessage);
txtvMessage.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtvMessage = content.findViewById(de.danoeh.antennapod.R.id.txtvMessage);
break;
}
}
checkValidity();
return dialog;
}


private void setProxyConfig() {
final java.lang.String type;
type = ((java.lang.String) (spType.getSelectedItem()));
final java.net.Proxy.Type typeEnum;
typeEnum = java.net.Proxy.Type.valueOf(type);
final java.lang.String host;
host = etHost.getText().toString();
final java.lang.String port;
port = etPort.getText().toString();
java.lang.String username;
username = etUsername.getText().toString();
if (android.text.TextUtils.isEmpty(username)) {
username = null;
}
java.lang.String password;
password = etPassword.getText().toString();
if (android.text.TextUtils.isEmpty(password)) {
password = null;
}
int portValue;
portValue = 0;
if (!android.text.TextUtils.isEmpty(port)) {
portValue = java.lang.Integer.parseInt(port);
}
de.danoeh.antennapod.model.download.ProxyConfig config;
config = new de.danoeh.antennapod.model.download.ProxyConfig(typeEnum, host, portValue, username, password);
de.danoeh.antennapod.storage.preferences.UserPreferences.setProxyConfig(config);
de.danoeh.antennapod.core.service.download.AntennapodHttpClient.setProxyConfig(config);
}


private final android.text.TextWatcher requireTestOnChange = new android.text.TextWatcher() {
@java.lang.Override
public void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) {
}


@java.lang.Override
public void onTextChanged(java.lang.CharSequence s, int start, int before, int count) {
}


@java.lang.Override
public void afterTextChanged(android.text.Editable s) {
setTestRequired(true);
}

};

private void enableSettings(boolean enable) {
etHost.setEnabled(enable);
etPort.setEnabled(enable);
etUsername.setEnabled(enable);
etPassword.setEnabled(enable);
}


private boolean checkValidity() {
boolean valid;
valid = true;
if (spType.getSelectedItemPosition() > 0) {
valid = checkHost();
}
valid &= checkPort();
return valid;
}


private boolean checkHost() {
java.lang.String host;
host = etHost.getText().toString();
if (host.length() == 0) {
etHost.setError(context.getString(de.danoeh.antennapod.R.string.proxy_host_empty_error));
return false;
}
if ((!"localhost".equals(host)) && (!android.util.Patterns.DOMAIN_NAME.matcher(host).matches())) {
etHost.setError(context.getString(de.danoeh.antennapod.R.string.proxy_host_invalid_error));
return false;
}
return true;
}


private boolean checkPort() {
int port;
port = getPort();
if ((port < 0) || (port > 65535)) {
etPort.setError(context.getString(de.danoeh.antennapod.R.string.proxy_port_invalid_error));
return false;
}
return true;
}


private int getPort() {
java.lang.String port;
port = etPort.getText().toString();
if (port.length() > 0) {
try {
return java.lang.Integer.parseInt(port);
} catch (java.lang.NumberFormatException e) {
// ignore
}
}
return 0;
}


private void setTestRequired(boolean required) {
if (required) {
testSuccessful = false;
dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setText(de.danoeh.antennapod.R.string.proxy_test_label);
} else {
testSuccessful = true;
dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setText(android.R.string.ok);
}
dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setEnabled(true);
}


private void test() {
if (disposable != null) {
disposable.dispose();
}
if (!checkValidity()) {
setTestRequired(true);
return;
}
android.content.res.TypedArray res;
res = context.getTheme().obtainStyledAttributes(new int[]{ android.R.attr.textColorPrimary });
int textColorPrimary;
textColorPrimary = res.getColor(0, 0);
res.recycle();
java.lang.String checking;
checking = context.getString(de.danoeh.antennapod.R.string.proxy_checking);
txtvMessage.setTextColor(textColorPrimary);
txtvMessage.setText("{fa-circle-o-notch spin} " + checking);
txtvMessage.setVisibility(android.view.View.VISIBLE);
disposable = io.reactivex.Completable.create((io.reactivex.CompletableEmitter emitter) -> {
java.lang.String type;
type = ((java.lang.String) (spType.getSelectedItem()));
java.lang.String host;
host = etHost.getText().toString();
java.lang.String port;
port = etPort.getText().toString();
java.lang.String username;
username = etUsername.getText().toString();
java.lang.String password;
password = etPassword.getText().toString();
int portValue;
portValue = 8080;
if (!android.text.TextUtils.isEmpty(port)) {
portValue = java.lang.Integer.parseInt(port);
}
java.net.SocketAddress address;
address = java.net.InetSocketAddress.createUnresolved(host, portValue);
java.net.Proxy.Type proxyType;
proxyType = java.net.Proxy.Type.valueOf(type.toUpperCase(java.util.Locale.US));
okhttp3.OkHttpClient.Builder builder;
builder = de.danoeh.antennapod.core.service.download.AntennapodHttpClient.newBuilder().connectTimeout(10, java.util.concurrent.TimeUnit.SECONDS).proxy(new java.net.Proxy(proxyType, address));
if (!android.text.TextUtils.isEmpty(username)) {
builder.proxyAuthenticator((okhttp3.Route route,okhttp3.Response response) -> {
java.lang.String credentials;
credentials = okhttp3.Credentials.basic(username, password);
return response.request().newBuilder().header("Proxy-Authorization", credentials).build();
});
}
okhttp3.OkHttpClient client;
client = builder.build();
okhttp3.Request request;
request = new okhttp3.Request.Builder().url("https://www.example.com").head().build();
try (okhttp3.Response response = client.newCall(request).execute()) {
if (response.isSuccessful()) {
emitter.onComplete();
} else {
emitter.onError(new java.io.IOException(response.message()));
}
} catch (java.io.IOException e) {
emitter.onError(e);
}
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> {
txtvMessage.setTextColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.icon_green));
java.lang.String message;
message = java.lang.String.format("%s %s", "{fa-check}", context.getString(de.danoeh.antennapod.R.string.proxy_test_successful));
txtvMessage.setText(message);
setTestRequired(false);
}, (java.lang.Throwable error) -> {
error.printStackTrace();
txtvMessage.setTextColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(context, de.danoeh.antennapod.R.attr.icon_red));
java.lang.String message;
message = java.lang.String.format("%s %s: %s", "{fa-close}", context.getString(de.danoeh.antennapod.R.string.proxy_test_failed), error.getMessage());
txtvMessage.setText(message);
setTestRequired(true);
});
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
