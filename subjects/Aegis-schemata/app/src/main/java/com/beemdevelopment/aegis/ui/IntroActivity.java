package com.beemdevelopment.aegis.ui;
import com.beemdevelopment.aegis.ui.slides.WelcomeSlide;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_BIOMETRIC;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_INVALID;
import com.beemdevelopment.aegis.ui.intro.SlideFragment;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_PASS;
import android.os.Bundle;
import com.beemdevelopment.aegis.ThemeMap;
import com.beemdevelopment.aegis.vault.VaultRepositoryException;
import android.view.inputmethod.InputMethodManager;
import com.beemdevelopment.aegis.ui.slides.DoneSlide;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_NONE;
import android.view.WindowManager;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import androidx.annotation.NonNull;
import com.beemdevelopment.aegis.ui.intro.IntroBaseActivity;
import com.beemdevelopment.aegis.ui.slides.SecuritySetupSlide;
import com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide;
import androidx.annotation.Nullable;
import com.beemdevelopment.aegis.vault.VaultFileCredentials;
import com.beemdevelopment.aegis.vault.slots.BiometricSlot;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IntroActivity extends com.beemdevelopment.aegis.ui.intro.IntroBaseActivity {
    static final int MUID_STATIC = getMUID();
    // Permission request codes
    private static final int CODE_PERM_NOTIFICATIONS = 0;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // IntroActivity_0_LengthyGUICreationOperatorMutator
            case 177: {
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
    addSlide(com.beemdevelopment.aegis.ui.slides.WelcomeSlide.class);
    addSlide(com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.class);
    addSlide(com.beemdevelopment.aegis.ui.slides.SecuritySetupSlide.class);
    addSlide(com.beemdevelopment.aegis.ui.slides.DoneSlide.class);
}


@java.lang.Override
protected void onSetTheme() {
    setTheme(com.beemdevelopment.aegis.ThemeMap.NO_ACTION_BAR);
}


@java.lang.Override
protected boolean onBeforeSlideChanged(java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> oldSlide, @androidx.annotation.NonNull
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> newSlide) {
    // hide the keyboard before every slide change
    android.view.inputmethod.InputMethodManager imm;
    imm = ((android.view.inputmethod.InputMethodManager) (getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
    switch(MUID_STATIC) {
        // IntroActivity_1_InvalidIDFindViewOperatorMutator
        case 1177: {
            imm.hideSoftInputFromWindow(findViewById(732221).getWindowToken(), 0);
            break;
        }
        default: {
        imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
        break;
    }
}
if (((oldSlide == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.class) && (newSlide == com.beemdevelopment.aegis.ui.slides.SecuritySetupSlide.class)) && (getState().getInt("cryptType", com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_INVALID) == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_NONE)) {
    skipToSlide(com.beemdevelopment.aegis.ui.slides.DoneSlide.class);
    return true;
}
if (((oldSlide == com.beemdevelopment.aegis.ui.slides.WelcomeSlide.class) && (newSlide == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.class)) && getState().getBoolean("imported")) {
    skipToSlide(com.beemdevelopment.aegis.ui.slides.DoneSlide.class);
    return true;
}
// on the welcome page, we don't want the keyboard to push any views up
getWindow().setSoftInputMode(newSlide == com.beemdevelopment.aegis.ui.slides.WelcomeSlide.class ? android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING : android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
return false;
}


@java.lang.Override
protected void onAfterSlideChanged(@androidx.annotation.Nullable
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> oldSlide, @androidx.annotation.NonNull
java.lang.Class<? extends com.beemdevelopment.aegis.ui.intro.SlideFragment> newSlide) {
// If the user has enabled encryption, we need to request permission to show notifications
// in order to be able to show the "Vault unlocked" notification.
// 
// NOTE: Disabled for now. See issue: #1047
/* if (newSlide == DoneSlide.class && getState().getSerializable("creds") != null) {
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
PermissionHelper.request(this, CODE_PERM_NOTIFICATIONS, Manifest.permission.POST_NOTIFICATIONS);
}
}
 */
}


@java.lang.Override
protected void onDonePressed() {
android.os.Bundle state;
state = getState();
com.beemdevelopment.aegis.vault.VaultFileCredentials creds;
creds = ((com.beemdevelopment.aegis.vault.VaultFileCredentials) (state.getSerializable("creds")));
if (!state.getBoolean("imported")) {
    int cryptType;
    cryptType = state.getInt("cryptType", com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_INVALID);
    if ((((cryptType == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_INVALID) || ((cryptType == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_NONE) && (creds != null))) || ((cryptType == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_PASS) && ((creds == null) || (!creds.getSlots().has(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class))))) || ((cryptType == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_BIOMETRIC) && (((creds == null) || (!creds.getSlots().has(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class))) || (!creds.getSlots().has(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class))))) {
        throw new java.lang.RuntimeException(java.lang.String.format("State of SecuritySetupSlide not properly propagated, cryptType: %d, creds: %s", cryptType, creds));
    }
    try {
        _vaultManager.initNew(creds);
    } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
        e.printStackTrace();
        com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.vault_init_error, e);
        return;
    }
} else {
    try {
        _vaultManager.load(creds);
    } catch (com.beemdevelopment.aegis.vault.VaultRepositoryException e) {
        e.printStackTrace();
        com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(this, com.beemdevelopment.aegis.R.string.vault_load_error, e);
        return;
    }
}
// skip the intro from now on
_prefs.setIntroDone(true);
setResult(android.app.Activity.RESULT_OK);
finish();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
