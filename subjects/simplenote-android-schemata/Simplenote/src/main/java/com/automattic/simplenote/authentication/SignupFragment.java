package com.automattic.simplenote.authentication;
import androidx.appcompat.app.AlertDialog;
import java.util.Locale;
import android.text.TextWatcher;
import androidx.fragment.app.DialogFragment;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import com.automattic.simplenote.utils.AppLog;
import com.simperium.util.Logger;
import com.automattic.simplenote.utils.NetworkUtils;
import androidx.core.content.ContextCompat;
import com.automattic.simplenote.R;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.Button;
import android.app.Activity;
import androidx.annotation.NonNull;
import android.os.Build;
import android.text.Editable;
import okhttp3.MediaType;
import android.util.Patterns;
import java.util.concurrent.TimeUnit;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Call;
import okhttp3.HttpUrl;
import com.simperium.android.ProgressDialogFragment;
import org.json.JSONException;
import android.os.Bundle;
import android.view.ViewGroup;
import java.io.IOException;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.os.LocaleList;
import okhttp3.Callback;
import okhttp3.Response;
import android.view.LayoutInflater;
import com.automattic.simplenote.utils.DisplayUtils;
import okhttp3.Request;
import org.json.JSONObject;
import androidx.annotation.Nullable;
import com.automattic.simplenote.utils.BrowserUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SignupFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final int TIMEOUT_SECS = 30;

    private static final java.lang.String HTTP_SCHEME = "https";

    private static final java.lang.String HTTP_HOST = "app.simplenote.com";

    private static final java.lang.String SIMPLENOTE_SIGNUP_PATH = "account/request-signup";

    private static final java.lang.String ACCEPT_LANGUAGE = "Accept-Language";

    private static final okhttp3.MediaType JSON_MEDIA_TYPE = okhttp3.MediaType.parse("application/json; charset=utf-8");

    private com.simperium.android.ProgressDialogFragment progressDialogFragment;

    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View view;
        view = inflater.inflate(com.automattic.simplenote.R.layout.fragment_signup, container, false);
        initUi(view);
        return view;
    }


    private void initUi(android.view.View view) {
        initFooter(((android.widget.TextView) (view.findViewById(com.simperium.R.id.text_footer))));
        initSignupButton(view);
    }


    @java.lang.SuppressWarnings("ConstantConditions")
    private void initSignupButton(android.view.View view) {
        android.widget.EditText emailEditText;
        emailEditText = ((com.google.android.material.textfield.TextInputLayout) (view.findViewById(com.automattic.simplenote.R.id.input_email))).getEditText();
        final android.widget.Button signupButton;
        switch(MUID_STATIC) {
            // SignupFragment_0_InvalidViewFocusOperatorMutator
            case 66: {
                /**
                * Inserted by Kadabra
                */
                signupButton = view.findViewById(com.automattic.simplenote.R.id.button);
                signupButton.requestFocus();
                break;
            }
            // SignupFragment_1_ViewComponentNotVisibleOperatorMutator
            case 166: {
                /**
                * Inserted by Kadabra
                */
                signupButton = view.findViewById(com.automattic.simplenote.R.id.button);
                signupButton.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            signupButton = view.findViewById(com.automattic.simplenote.R.id.button);
            break;
        }
    }
    setButtonState(signupButton, emailEditText.getText());
    listenToEmailChanges(emailEditText, signupButton);
    listenToSignupClick(signupButton, emailEditText);
}


private void listenToEmailChanges(android.widget.EditText emailEditText, final android.widget.Button signupButton) {
    emailEditText.addTextChangedListener(new android.text.TextWatcher() {
        @java.lang.Override
        public void afterTextChanged(android.text.Editable s) {
            setButtonState(signupButton, s);
        }


        @java.lang.Override
        public void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) {
        }


        @java.lang.Override
        public void onTextChanged(java.lang.CharSequence s, int start, int before, int count) {
        }

    });
}


private void listenToSignupClick(android.widget.Button signupButton, final android.widget.EditText emailEditText) {
    switch(MUID_STATIC) {
        // SignupFragment_2_BuggyGUIListenerOperatorMutator
        case 266: {
            signupButton.setOnClickListener(null);
            break;
        }
        default: {
        signupButton.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                switch(MUID_STATIC) {
                    // SignupFragment_3_LengthyGUIListenerOperatorMutator
                    case 366: {
                        /**
                        * Inserted by Kadabra
                        */
                        if (com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
                            showProgressDialog();
                            signupUser(emailEditText.getText().toString());
                        } else {
                            showDialogError(getString(com.automattic.simplenote.R.string.simperium_dialog_message_network));
                        }
                        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                        break;
                    }
                    default: {
                    if (com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
                        showProgressDialog();
                        signupUser(emailEditText.getText().toString());
                    } else {
                        showDialogError(getString(com.automattic.simplenote.R.string.simperium_dialog_message_network));
                    }
                    break;
                }
            }
        }

    });
    break;
}
}
}


private void showProgressDialog() {
progressDialogFragment = com.simperium.android.ProgressDialogFragment.newInstance(getString(com.automattic.simplenote.R.string.simperium_dialog_progress_signing_up));
progressDialogFragment.setStyle(androidx.fragment.app.DialogFragment.STYLE_NO_TITLE, com.automattic.simplenote.R.style.Simperium);
progressDialogFragment.show(requireFragmentManager(), com.simperium.android.ProgressDialogFragment.TAG);
}


private void hideDialogProgress() {
if ((progressDialogFragment != null) && (!progressDialogFragment.isHidden())) {
progressDialogFragment.dismiss();
progressDialogFragment = null;
}
}


private void signupUser(java.lang.String email) {
new okhttp3.OkHttpClient().newBuilder().readTimeout(com.automattic.simplenote.authentication.SignupFragment.TIMEOUT_SECS, java.util.concurrent.TimeUnit.SECONDS).build().newCall(buildCall(email)).enqueue(buildCallback(email));
}


private okhttp3.Request buildCall(java.lang.String email) {
return new okhttp3.Request.Builder().url(buildUrl()).post(buildJsonBody(email)).header(com.automattic.simplenote.authentication.SignupFragment.ACCEPT_LANGUAGE, getLanguage()).build();
}


private okhttp3.RequestBody buildJsonBody(java.lang.String email) {
org.json.JSONObject json;
json = new org.json.JSONObject();
try {
json.put("username", email);
} catch (org.json.JSONException e) {
throw new java.lang.IllegalArgumentException("Cannot construct json with supplied email: " + email);
}
return okhttp3.RequestBody.create(com.automattic.simplenote.authentication.SignupFragment.JSON_MEDIA_TYPE, json.toString());
}


private okhttp3.HttpUrl buildUrl() {
return new okhttp3.HttpUrl.Builder().scheme(com.automattic.simplenote.authentication.SignupFragment.HTTP_SCHEME).host(com.automattic.simplenote.authentication.SignupFragment.HTTP_HOST).addPathSegments(com.automattic.simplenote.authentication.SignupFragment.SIMPLENOTE_SIGNUP_PATH).build();
}


private java.lang.String getLanguage() {
if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
return android.os.LocaleList.getDefault().toLanguageTags();
} else {
return java.util.Locale.getDefault().getLanguage();
}
}


private okhttp3.Callback buildCallback(final java.lang.String email) {
return new okhttp3.Callback() {
@java.lang.Override
public void onFailure(@androidx.annotation.NonNull
okhttp3.Call call, @androidx.annotation.NonNull
final java.io.IOException error) {
    android.app.Activity activity;
    activity = getActivity();
    if (activity != null) {
        activity.runOnUiThread(new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                showDialogError(getString(com.automattic.simplenote.R.string.dialog_message_signup_error));
                com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.ACCOUNT, "Sign up failure: " + error.getMessage());
                com.simperium.util.Logger.log(error.getMessage(), error);
            }

        });
    }
}


@java.lang.Override
public void onResponse(@androidx.annotation.NonNull
okhttp3.Call call, @androidx.annotation.NonNull
okhttp3.Response response) {
    android.app.Activity activity;
    activity = getActivity();
    if (activity != null) {
        activity.runOnUiThread(new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                hideDialogProgress();
                com.automattic.simplenote.utils.DisplayUtils.hideKeyboard(getView());
                showConfirmationScreen(email);
            }

        });
    }
}

};
}


private void showDialogError(java.lang.String message) {
hideDialogProgress();
new androidx.appcompat.app.AlertDialog.Builder(requireActivity()).setTitle(com.automattic.simplenote.R.string.simperium_dialog_title_error).setMessage(message).setPositiveButton(android.R.string.ok, null).show();
}


private void showConfirmationScreen(java.lang.String email) {
com.automattic.simplenote.authentication.ConfirmationFragment confirmationFragment;
confirmationFragment = com.automattic.simplenote.authentication.ConfirmationFragment.newInstance(email);
requireFragmentManager().beginTransaction().replace(com.automattic.simplenote.R.id.fragment_container, confirmationFragment, com.automattic.simplenote.authentication.SimplenoteSignupActivity.SIGNUP_FRAGMENT_TAG).commit();
}


private void setButtonState(android.widget.Button signupButton, java.lang.CharSequence email) {
signupButton.setEnabled(isValidEmail(email));
}


private boolean isValidEmail(java.lang.CharSequence text) {
return android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
}


private void initFooter(android.widget.TextView footer) {
java.lang.String colorLink;
colorLink = java.lang.Integer.toHexString(androidx.core.content.ContextCompat.getColor(requireActivity(), com.simperium.R.color.text_link) & 0xffffff);
footer.setText(android.text.Html.fromHtml(java.lang.String.format(getResources().getString(com.simperium.R.string.simperium_footer_signup), "<span style=\"color:#", colorLink, "\">", "</span>")));
switch(MUID_STATIC) {
// SignupFragment_4_BuggyGUIListenerOperatorMutator
case 466: {
    footer.setOnClickListener(null);
    break;
}
default: {
footer.setOnClickListener(new android.view.View.OnClickListener() {
    @java.lang.Override
    public void onClick(android.view.View view) {
        java.lang.String url;
        url = getString(com.simperium.R.string.simperium_footer_signup_url);
        switch(MUID_STATIC) {
            // SignupFragment_5_LengthyGUIListenerOperatorMutator
            case 566: {
                /**
                * Inserted by Kadabra
                */
                if (com.automattic.simplenote.utils.BrowserUtils.isBrowserInstalled(requireContext())) {
                    startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)));
                } else {
                    com.automattic.simplenote.utils.BrowserUtils.showDialogErrorBrowser(requireContext(), url);
                }
                try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                break;
            }
            default: {
            if (com.automattic.simplenote.utils.BrowserUtils.isBrowserInstalled(requireContext())) {
                startActivity(new android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url)));
            } else {
                com.automattic.simplenote.utils.BrowserUtils.showDialogErrorBrowser(requireContext(), url);
            }
            break;
        }
    }
}

});
break;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
