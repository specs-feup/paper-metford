package com.automattic.simplenote.authentication;
import com.simperium.android.AuthenticationActivity;
import android.app.AlertDialog;
import com.automattic.simplenote.utils.StrUtils;
import net.openid.appauth.AuthorizationRequest;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import net.openid.appauth.AuthorizationException;
import android.os.Bundle;
import android.content.Intent;
import com.automattic.simplenote.utils.WordPressUtils;
import android.net.Uri;
import com.automattic.simplenote.Simplenote;
import net.openid.appauth.AuthorizationResponse;
import com.automattic.simplenote.R;
import net.openid.appauth.AuthorizationService;
import androidx.annotation.NonNull;
import android.view.ContextThemeWrapper;
import androidx.annotation.StringRes;
import java.util.UUID;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimplenoteAuthenticationActivity extends com.simperium.android.AuthenticationActivity {
    static final int MUID_STATIC = getMUID();
    private static java.lang.String STATE_AUTH_STATE = "STATE_AUTH_STATE";

    private java.lang.String mAuthState;

    @java.lang.Override
    protected void buttonLoginClicked() {
        super.buttonLoginClicked();
    }


    @java.lang.Override
    protected void buttonSignupClicked() {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // SimplenoteAuthenticationActivity_0_NullIntentOperatorMutator
            case 67: {
                intent = null;
                break;
            }
            // SimplenoteAuthenticationActivity_1_InvalidKeyIntentOperatorMutator
            case 167: {
                intent = new android.content.Intent((SimplenoteAuthenticationActivity) null, com.automattic.simplenote.authentication.SimplenoteSignupActivity.class);
                break;
            }
            // SimplenoteAuthenticationActivity_2_RandomActionIntentDefinitionOperatorMutator
            case 267: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(this, com.automattic.simplenote.authentication.SimplenoteSignupActivity.class);
            break;
        }
    }
    startActivity(intent);
}


@java.lang.Override
protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if ((requestCode != com.automattic.simplenote.utils.WordPressUtils.OAUTH_ACTIVITY_CODE) || (data == null)) {
        return;
    }
    net.openid.appauth.AuthorizationResponse authorizationResponse;
    authorizationResponse = net.openid.appauth.AuthorizationResponse.fromIntent(data);
    net.openid.appauth.AuthorizationException authorizationException;
    authorizationException = net.openid.appauth.AuthorizationException.fromIntent(data);
    if (authorizationException != null) {
        android.net.Uri dataUri;
        dataUri = data.getData();
        if (dataUri == null) {
            return;
        }
        if (com.automattic.simplenote.utils.StrUtils.isSameStr(dataUri.getQueryParameter("code"), "1")) {
            showDialogError(com.automattic.simplenote.R.string.wpcom_log_in_error_unverified);
        } else {
            showDialogError(com.automattic.simplenote.R.string.wpcom_log_in_error_generic);
        }
    } else if (authorizationResponse != null) {
        // Save token and finish activity.
        boolean authSuccess;
        authSuccess = com.automattic.simplenote.utils.WordPressUtils.processAuthResponse(((com.automattic.simplenote.Simplenote) (getApplication())), authorizationResponse, mAuthState, true);
        if (!authSuccess) {
            showDialogError(com.automattic.simplenote.R.string.wpcom_log_in_error_generic);
        } else {
            com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.WPCC_LOGIN_SUCCEEDED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "wpcc_login_succeeded_signin_activity");
            finish();
        }
    }
}


@java.lang.Override
public void onLoginSheetCanceled() {
    super.onLoginSheetCanceled();
}


@java.lang.Override
public void onLoginSheetEmailClicked() {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // SimplenoteAuthenticationActivity_3_NullIntentOperatorMutator
        case 367: {
            intent = null;
            break;
        }
        // SimplenoteAuthenticationActivity_4_InvalidKeyIntentOperatorMutator
        case 467: {
            intent = new android.content.Intent((SimplenoteAuthenticationActivity) null, com.automattic.simplenote.authentication.SimplenoteCredentialsActivity.class);
            break;
        }
        // SimplenoteAuthenticationActivity_5_RandomActionIntentDefinitionOperatorMutator
        case 567: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(this, com.automattic.simplenote.authentication.SimplenoteCredentialsActivity.class);
        break;
    }
}
switch(MUID_STATIC) {
    // SimplenoteAuthenticationActivity_6_NullValueIntentPutExtraOperatorMutator
    case 667: {
        intent.putExtra(com.simperium.android.AuthenticationActivity.EXTRA_IS_LOGIN, new Parcelable[0]);
        break;
    }
    // SimplenoteAuthenticationActivity_7_IntentPayloadReplacementOperatorMutator
    case 767: {
        intent.putExtra(com.simperium.android.AuthenticationActivity.EXTRA_IS_LOGIN, true);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // SimplenoteAuthenticationActivity_8_RandomActionIntentDefinitionOperatorMutator
        case 867: {
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
        intent.putExtra(com.simperium.android.AuthenticationActivity.EXTRA_IS_LOGIN, true);
        break;
    }
}
break;
}
}
startActivity(intent);
finish();
}


@java.lang.Override
public void onLoginSheetOtherClicked() {
net.openid.appauth.AuthorizationRequest.Builder authRequestBuilder;
authRequestBuilder = com.automattic.simplenote.utils.WordPressUtils.getWordPressAuthorizationRequestBuilder();
// Set unique state value.
mAuthState = "app-" + java.util.UUID.randomUUID();
authRequestBuilder.setState(mAuthState);
net.openid.appauth.AuthorizationRequest request;
request = authRequestBuilder.build();
net.openid.appauth.AuthorizationService authService;
authService = new net.openid.appauth.AuthorizationService(this);
android.content.Intent authIntent;
switch(MUID_STATIC) {
// SimplenoteAuthenticationActivity_9_RandomActionIntentDefinitionOperatorMutator
case 967: {
authIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
authIntent = authService.getAuthorizationRequestIntent(request);
break;
}
}
startActivityForResult(authIntent, com.automattic.simplenote.utils.WordPressUtils.OAUTH_ACTIVITY_CODE);
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.WPCC_BUTTON_PRESSED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "wpcc_button_press_signin_activity");
}


@java.lang.Override
protected void onSaveInstanceState(@androidx.annotation.NonNull
android.os.Bundle outState) {
outState.putString(com.automattic.simplenote.authentication.SimplenoteAuthenticationActivity.STATE_AUTH_STATE, mAuthState);
super.onSaveInstanceState(outState);
}


@java.lang.Override
protected void onRestoreInstanceState(android.os.Bundle savedInstanceState) {
super.onRestoreInstanceState(savedInstanceState);
if (savedInstanceState.containsKey(com.automattic.simplenote.authentication.SimplenoteAuthenticationActivity.STATE_AUTH_STATE)) {
mAuthState = savedInstanceState.getString(com.automattic.simplenote.authentication.SimplenoteAuthenticationActivity.STATE_AUTH_STATE);
}
}


private void showDialogError(@androidx.annotation.StringRes
int message) {
if (isFinishing() || (message == 0)) {
return;
}
android.content.Context context;
context = new android.view.ContextThemeWrapper(this, getTheme());
new android.app.AlertDialog.Builder(context).setTitle(com.automattic.simplenote.R.string.simperium_dialog_title_error).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.WPCC_LOGIN_FAILED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "wpcc_login_failed_signin_activity");
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
