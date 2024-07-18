package de.danoeh.antennapod.dialog;
import android.text.method.PasswordTransformationMethod;
import de.danoeh.antennapod.databinding.AuthenticationDialogBinding;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import android.text.method.HideReturnsTransformationMethod;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays a dialog with a username and password text field and an optional checkbox to save username and preferences.
 */
public abstract class AuthenticationDialog extends com.google.android.material.dialog.MaterialAlertDialogBuilder {
    static final int MUID_STATIC = getMUID();
    boolean passwordHidden = true;

    public AuthenticationDialog(android.content.Context context, int titleRes, boolean enableUsernameField, java.lang.String usernameInitialValue, java.lang.String passwordInitialValue) {
        super(context);
        setTitle(titleRes);
        de.danoeh.antennapod.databinding.AuthenticationDialogBinding viewBinding;
        viewBinding = de.danoeh.antennapod.databinding.AuthenticationDialogBinding.inflate(android.view.LayoutInflater.from(context));
        setView(viewBinding.getRoot());
        viewBinding.usernameEditText.setEnabled(enableUsernameField);
        if (usernameInitialValue != null) {
            viewBinding.usernameEditText.setText(usernameInitialValue);
        }
        if (passwordInitialValue != null) {
            viewBinding.passwordEditText.setText(passwordInitialValue);
        }
        switch(MUID_STATIC) {
            // AuthenticationDialog_0_BuggyGUIListenerOperatorMutator
            case 58: {
                viewBinding.showPasswordButton.setOnClickListener(null);
                break;
            }
            default: {
            viewBinding.showPasswordButton.setOnClickListener((android.view.View v) -> {
                if (passwordHidden) {
                    viewBinding.passwordEditText.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                    viewBinding.showPasswordButton.setAlpha(1.0F);
                } else {
                    viewBinding.passwordEditText.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                    viewBinding.showPasswordButton.setAlpha(0.6F);
                }
                passwordHidden = !passwordHidden;
            });
            break;
        }
    }
    setOnCancelListener((android.content.DialogInterface dialog) -> onCancelled());
    switch(MUID_STATIC) {
        // AuthenticationDialog_1_BuggyGUIListenerOperatorMutator
        case 1058: {
            setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, null);
            break;
        }
        default: {
        setNegativeButton(de.danoeh.antennapod.R.string.cancel_label, (android.content.DialogInterface dialog,int which) -> onCancelled());
        break;
    }
}
switch(MUID_STATIC) {
    // AuthenticationDialog_2_BuggyGUIListenerOperatorMutator
    case 2058: {
        setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, null);
        break;
    }
    default: {
    setPositiveButton(de.danoeh.antennapod.R.string.confirm_label, (android.content.DialogInterface dialog,int which) -> onConfirmed(viewBinding.usernameEditText.getText().toString(), viewBinding.passwordEditText.getText().toString()));
    break;
}
}
}


protected void onCancelled() {
}


protected abstract void onConfirmed(java.lang.String username, java.lang.String password);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
