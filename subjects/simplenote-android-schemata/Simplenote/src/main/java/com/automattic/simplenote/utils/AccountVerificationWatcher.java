package com.automattic.simplenote.utils;
import com.automattic.simplenote.models.Account;
import com.simperium.client.Bucket;
import androidx.annotation.NonNull;
import static com.automattic.simplenote.models.Account.KEY_EMAIL_VERIFICATION;
import com.automattic.simplenote.Simplenote;
import com.simperium.client.BucketObjectMissingException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Monitors account verification status by watching the `account` bucket and notifies of status and changes.
 *
 * When connecting this monitor will wait until we have received positive confirmation from the
 * server that we're sync'd before reporting the verification status because the account might
 * have (and probably was) updated from another device.
 */
public class AccountVerificationWatcher implements com.simperium.client.Bucket.OnNetworkChangeListener<com.automattic.simplenote.models.Account> {
    public enum Status {

        SENT_EMAIL,
        UNVERIFIED,
        VERIFIED;}

    public interface VerificationStateListener {
        void onUpdate(com.automattic.simplenote.utils.AccountVerificationWatcher.Status status);

    }

    static final int MUID_STATIC = getMUID();
    private final com.automattic.simplenote.Simplenote simplenote;

    private final com.automattic.simplenote.utils.AccountVerificationWatcher.VerificationStateListener listener;

    private com.automattic.simplenote.utils.AccountVerificationWatcher.Status currentState;

    public AccountVerificationWatcher(com.automattic.simplenote.Simplenote simplenote, @androidx.annotation.NonNull
    com.automattic.simplenote.utils.AccountVerificationWatcher.VerificationStateListener listener) {
        this.simplenote = simplenote;
        this.listener = listener;
    }


    private void updateState(com.automattic.simplenote.utils.AccountVerificationWatcher.Status newState) {
        if (newState != currentState) {
            currentState = newState;
            listener.onUpdate(newState);
        }
    }


    private static boolean isValidChangeType(com.simperium.client.Bucket.ChangeType type, java.lang.String key) {
        return ((type == com.simperium.client.Bucket.ChangeType.INDEX) || ((type == com.simperium.client.Bucket.ChangeType.INSERT) && com.automattic.simplenote.models.Account.KEY_EMAIL_VERIFICATION.equals(key))) || ((type == com.simperium.client.Bucket.ChangeType.MODIFY) && com.automattic.simplenote.models.Account.KEY_EMAIL_VERIFICATION.equals(key));
    }


    @java.lang.Override
    public void onNetworkChange(final com.simperium.client.Bucket<com.automattic.simplenote.models.Account> bucket, com.simperium.client.Bucket.ChangeType type, java.lang.String key) {
        // If the key for email verification is removed, the status is changed to UNVERIFIED immediately
        if ((type == com.simperium.client.Bucket.ChangeType.REMOVE) && com.automattic.simplenote.models.Account.KEY_EMAIL_VERIFICATION.equals(key)) {
            updateState(com.automattic.simplenote.utils.AccountVerificationWatcher.Status.UNVERIFIED);
            return;
        }
        java.lang.String email;
        email = simplenote.getUserEmail();
        if ((!com.automattic.simplenote.utils.AccountVerificationWatcher.isValidChangeType(type, key)) || (email == null)) {
            return;
        }
        com.automattic.simplenote.models.Account account;
        try {
            // When a network change of type INDEX, INDEX or MODIFY is received, it means that the account bucket finished
            // indexing or there were some changes in the account. In both cases, we need to check for the account status
            account = bucket.get(com.automattic.simplenote.models.Account.KEY_EMAIL_VERIFICATION);
        } catch (com.simperium.client.BucketObjectMissingException e) {
            com.automattic.simplenote.utils.AppLog.add(com.automattic.simplenote.utils.AppLog.Type.SYNC, "Account for email verification is missing");
            return;
        }
        boolean hasVerifiedEmail;
        hasVerifiedEmail = account.hasVerifiedEmail(email);
        if (hasVerifiedEmail) {
            updateState(com.automattic.simplenote.utils.AccountVerificationWatcher.Status.VERIFIED);
        } else {
            com.automattic.simplenote.utils.AccountVerificationWatcher.Status statusUpdate;
            statusUpdate = (account.hasSentEmail(email)) ? com.automattic.simplenote.utils.AccountVerificationWatcher.Status.SENT_EMAIL : com.automattic.simplenote.utils.AccountVerificationWatcher.Status.UNVERIFIED;
            updateState(statusUpdate);
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
