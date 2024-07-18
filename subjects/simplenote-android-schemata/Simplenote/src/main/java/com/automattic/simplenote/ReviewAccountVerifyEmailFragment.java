package com.automattic.simplenote;
import com.automattic.simplenote.models.Account;
import androidx.fragment.app.Fragment;
import com.automattic.simplenote.utils.AppLog;
import com.automattic.simplenote.utils.AccountVerificationEmailHandler;
import com.automattic.simplenote.utils.NetworkUtils;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogContent;
import com.automattic.simplenote.analytics.AnalyticsTracker;
import android.os.Bundle;
import android.view.ViewGroup;
import android.os.Handler;
import androidx.appcompat.widget.AppCompatButton;
import com.simperium.client.BucketObjectMissingException;
import android.text.Html;
import android.view.View;
import android.os.Looper;
import android.view.LayoutInflater;
import com.automattic.simplenote.utils.AccountNetworkUtils;
import com.simperium.client.Bucket;
import com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController;
import static com.automattic.simplenote.models.Account.KEY_EMAIL_VERIFICATION;
import com.automattic.simplenote.utils.BrowserUtils;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A {@link FullScreenDialogFragment} for reviewing an account and verifying an email address.  When
 * an account has not been confirmed through a verification email link, the review account interface
 * is shown.  If a verification email has been sent, the verify email interface is shown.
 */
public class ReviewAccountVerifyEmailFragment extends androidx.fragment.app.Fragment implements com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogContent {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String EXTRA_SENT_EMAIL = "EXTRA_SENT_EMAIL";

    private static final java.lang.String URL_SETTINGS_REDIRECT = "https://app.simplenote.com/settings/";

    private static final java.lang.String URL_VERIFY_EMAIL = "https://app.simplenote.com/account/verify-email/";

    private static final int TIMEOUT_SECONDS = 30;

    private androidx.appcompat.widget.AppCompatButton mButtonPrimary;

    private androidx.appcompat.widget.AppCompatButton mButtonSecondary;

    private com.simperium.client.Bucket<com.automattic.simplenote.models.Account> mBucketAccount;

    private com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController mDialogController;

    private android.widget.ImageView mImageIcon;

    private java.lang.String mEmail;

    private android.widget.TextView mTextSubtitle;

    private android.widget.TextView mTextTitle;

    private boolean mHasSentEmail;

    @java.lang.Override
    public boolean onConfirmClicked(com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController controller) {
        if (!com.automattic.simplenote.utils.NetworkUtils.isNetworkAvailable(requireContext())) {
            android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.error_network_required, android.widget.Toast.LENGTH_LONG).show();
            return false;
        }
        if (mHasSentEmail) {
            android.widget.Toast.makeText(requireContext(), com.automattic.simplenote.R.string.toast_email_sent, android.widget.Toast.LENGTH_SHORT).show();
        } else {
            showVerifyEmail();
        }
        sendVerificationEmail();
        return false;
    }


    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        mBucketAccount = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getAccountBucket();
        android.view.View layout;
        layout = inflater.inflate(com.automattic.simplenote.R.layout.fragment_review_account_verify_email, container, false);
        mHasSentEmail = (getArguments() != null) && getArguments().getBoolean(com.automattic.simplenote.ReviewAccountVerifyEmailFragment.EXTRA_SENT_EMAIL);
        mEmail = ((com.automattic.simplenote.Simplenote) (requireActivity().getApplication())).getSimperium().getUser().getEmail();
        switch(MUID_STATIC) {
            // ReviewAccountVerifyEmailFragment_0_InvalidViewFocusOperatorMutator
            case 96: {
                /**
                * Inserted by Kadabra
                */
                mImageIcon = layout.findViewById(com.automattic.simplenote.R.id.image);
                mImageIcon.requestFocus();
                break;
            }
            // ReviewAccountVerifyEmailFragment_1_ViewComponentNotVisibleOperatorMutator
            case 196: {
                /**
                * Inserted by Kadabra
                */
                mImageIcon = layout.findViewById(com.automattic.simplenote.R.id.image);
                mImageIcon.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mImageIcon = layout.findViewById(com.automattic.simplenote.R.id.image);
            break;
        }
    }
    mImageIcon.setImageResource(mHasSentEmail ? com.automattic.simplenote.R.drawable.ic_mail_24dp : com.automattic.simplenote.R.drawable.ic_warning_24dp);
    mImageIcon.setContentDescription(getString(mHasSentEmail ? com.automattic.simplenote.R.string.description_mail : com.automattic.simplenote.R.string.description_warning));
    @androidx.annotation.StringRes
    int title;
    title = (mHasSentEmail) ? com.automattic.simplenote.R.string.fullscreen_verify_email_title : com.automattic.simplenote.R.string.fullscreen_review_account_title;
    switch(MUID_STATIC) {
        // ReviewAccountVerifyEmailFragment_2_InvalidViewFocusOperatorMutator
        case 296: {
            /**
            * Inserted by Kadabra
            */
            mTextTitle = layout.findViewById(com.automattic.simplenote.R.id.text_title);
            mTextTitle.requestFocus();
            break;
        }
        // ReviewAccountVerifyEmailFragment_3_ViewComponentNotVisibleOperatorMutator
        case 396: {
            /**
            * Inserted by Kadabra
            */
            mTextTitle = layout.findViewById(com.automattic.simplenote.R.id.text_title);
            mTextTitle.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mTextTitle = layout.findViewById(com.automattic.simplenote.R.id.text_title);
        break;
    }
}
mTextTitle.setText(title);
@androidx.annotation.StringRes
int subtitle;
subtitle = (mHasSentEmail) ? com.automattic.simplenote.R.string.fullscreen_verify_email_subtitle : com.automattic.simplenote.R.string.fullscreen_review_account_subtitle;
switch(MUID_STATIC) {
    // ReviewAccountVerifyEmailFragment_4_InvalidViewFocusOperatorMutator
    case 496: {
        /**
        * Inserted by Kadabra
        */
        mTextSubtitle = layout.findViewById(com.automattic.simplenote.R.id.text_subtitle);
        mTextSubtitle.requestFocus();
        break;
    }
    // ReviewAccountVerifyEmailFragment_5_ViewComponentNotVisibleOperatorMutator
    case 596: {
        /**
        * Inserted by Kadabra
        */
        mTextSubtitle = layout.findViewById(com.automattic.simplenote.R.id.text_subtitle);
        mTextSubtitle.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    mTextSubtitle = layout.findViewById(com.automattic.simplenote.R.id.text_subtitle);
    break;
}
}
mTextSubtitle.setText(android.text.Html.fromHtml(java.lang.String.format(getResources().getString(subtitle), mEmail)));
switch(MUID_STATIC) {
// ReviewAccountVerifyEmailFragment_6_InvalidViewFocusOperatorMutator
case 696: {
    /**
    * Inserted by Kadabra
    */
    mButtonPrimary = layout.findViewById(com.automattic.simplenote.R.id.button_primary);
    mButtonPrimary.requestFocus();
    break;
}
// ReviewAccountVerifyEmailFragment_7_ViewComponentNotVisibleOperatorMutator
case 796: {
    /**
    * Inserted by Kadabra
    */
    mButtonPrimary = layout.findViewById(com.automattic.simplenote.R.id.button_primary);
    mButtonPrimary.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
mButtonPrimary = layout.findViewById(com.automattic.simplenote.R.id.button_primary);
break;
}
}
switch(MUID_STATIC) {
// ReviewAccountVerifyEmailFragment_8_BuggyGUIListenerOperatorMutator
case 896: {
mButtonPrimary.setOnClickListener(null);
break;
}
default: {
mButtonPrimary.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
    com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.VERIFICATION_CONFIRM_BUTTON_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "verification_confirm");
    switch(MUID_STATIC) {
        // ReviewAccountVerifyEmailFragment_9_LengthyGUIListenerOperatorMutator
        case 996: {
            /**
            * Inserted by Kadabra
            */
            onConfirmClicked(mDialogController);
            try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
            break;
        }
        default: {
        onConfirmClicked(mDialogController);
        break;
    }
}
}

});
break;
}
}
mButtonPrimary.setVisibility(mHasSentEmail ? android.view.View.GONE : android.view.View.VISIBLE);
switch(MUID_STATIC) {
// ReviewAccountVerifyEmailFragment_10_InvalidViewFocusOperatorMutator
case 1096: {
/**
* Inserted by Kadabra
*/
mButtonSecondary = layout.findViewById(com.automattic.simplenote.R.id.button_secondary);
mButtonSecondary.requestFocus();
break;
}
// ReviewAccountVerifyEmailFragment_11_ViewComponentNotVisibleOperatorMutator
case 1196: {
/**
* Inserted by Kadabra
*/
mButtonSecondary = layout.findViewById(com.automattic.simplenote.R.id.button_secondary);
mButtonSecondary.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
mButtonSecondary = layout.findViewById(com.automattic.simplenote.R.id.button_secondary);
break;
}
}
switch(MUID_STATIC) {
// ReviewAccountVerifyEmailFragment_12_BuggyGUIListenerOperatorMutator
case 1296: {
mButtonSecondary.setOnClickListener(null);
break;
}
default: {
mButtonSecondary.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// ReviewAccountVerifyEmailFragment_13_LengthyGUIListenerOperatorMutator
case 1396: {
/**
* Inserted by Kadabra
*/
if (mHasSentEmail) {
    com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.VERIFICATION_RESEND_EMAIL_BUTTON_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "verification_resend_email");
    onConfirmClicked(mDialogController);
} else {
    com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.VERIFICATION_CHANGE_EMAIL_BUTTON_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "verification_change_email");
    com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.ReviewAccountVerifyEmailFragment.URL_SETTINGS_REDIRECT);
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (mHasSentEmail) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.VERIFICATION_RESEND_EMAIL_BUTTON_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "verification_resend_email");
onConfirmClicked(mDialogController);
} else {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.VERIFICATION_CHANGE_EMAIL_BUTTON_TAPPED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "verification_change_email");
com.automattic.simplenote.utils.BrowserUtils.launchBrowserOrShowError(requireContext(), com.automattic.simplenote.ReviewAccountVerifyEmailFragment.URL_SETTINGS_REDIRECT);
}
break;
}
}
}

});
break;
}
}
mButtonSecondary.setText(mHasSentEmail ? com.automattic.simplenote.R.string.fullscreen_verify_email_button_secondary : com.automattic.simplenote.R.string.fullscreen_review_account_button_secondary);
return layout;
}


@java.lang.Override
public void onDestroyView() {
super.onDestroyView();
}


@java.lang.Override
public boolean onDismissClicked(com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController controller) {
com.automattic.simplenote.analytics.AnalyticsTracker.track(com.automattic.simplenote.analytics.AnalyticsTracker.Stat.VERIFICATION_DISMISSED, com.automattic.simplenote.analytics.AnalyticsTracker.CATEGORY_USER, "verification_dismissed");
return false;
}


@java.lang.Override
public void onResume() {
super.onResume();
new android.os.Handler(android.os.Looper.getMainLooper()).post(new java.lang.Runnable() {
@java.lang.Override
public void run() {
dismissIfVerified();
}

});
}


@java.lang.Override
public void onViewCreated(com.automattic.simplenote.FullScreenDialogFragment.FullScreenDialogController controller) {
mDialogController = controller;
}


private void dismissIfVerified() {
if (isDetached() || isRemoving()) {
return;
}
try {
com.automattic.simplenote.models.Account account;
account = mBucketAccount.get(com.automattic.simplenote.models.Account.KEY_EMAIL_VERIFICATION);
if (account.hasVerifiedEmail(mEmail)) {
mDialogController.dismiss();
}
} catch (com.simperium.client.BucketObjectMissingException bucketObjectMissingException) {
// Do nothing if account cannot be retrieved.
}
}


public static android.os.Bundle newBundle(boolean hasSentEmail) {
android.os.Bundle bundle;
bundle = new android.os.Bundle();
bundle.putBoolean(com.automattic.simplenote.ReviewAccountVerifyEmailFragment.EXTRA_SENT_EMAIL, hasSentEmail);
return bundle;
}


private void sendVerificationEmail() {
com.automattic.simplenote.utils.AccountNetworkUtils.makeSendVerificationEmailRequest(mEmail, new com.automattic.simplenote.utils.AccountVerificationEmailHandler() {
@java.lang.Override
public void onSuccess(@androidx.annotation.NonNull
java.lang.String url) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.AUTH, ("Email sent (200 - " + url) + ")");
}


@java.lang.Override
public void onFailure(@androidx.annotation.NonNull
java.lang.Exception e, @androidx.annotation.NonNull
java.lang.String url) {
com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.AUTH, ((("Verification email error (" + e.getMessage()) + " - ") + url) + ")");
}

});
mHasSentEmail = true;
}


private void showVerifyEmail() {
mImageIcon.setImageResource(com.automattic.simplenote.R.drawable.ic_mail_24dp);
mTextTitle.setText(com.automattic.simplenote.R.string.fullscreen_verify_email_title);
mTextSubtitle.setText(android.text.Html.fromHtml(java.lang.String.format(getResources().getString(com.automattic.simplenote.R.string.fullscreen_verify_email_subtitle), mEmail)));
mButtonPrimary.setVisibility(android.view.View.GONE);
mButtonSecondary.setText(com.automattic.simplenote.R.string.fullscreen_verify_email_button_secondary);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
