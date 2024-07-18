package com.beemdevelopment.aegis.ui.slides;
import com.beemdevelopment.aegis.helpers.BiometricsHelper;
import com.nulabinc.zxcvbn.Zxcvbn;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_INVALID;
import android.text.TextWatcher;
import androidx.biometric.BiometricPrompt;
import android.content.res.ColorStateList;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_NONE;
import com.beemdevelopment.aegis.helpers.EditTextHelper;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.vault.slots.SlotException;
import android.text.method.PasswordTransformationMethod;
import com.beemdevelopment.aegis.ui.dialogs.Dialogs;
import com.beemdevelopment.aegis.R;
import com.google.android.material.textfield.TextInputLayout;
import com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import com.beemdevelopment.aegis.vault.slots.BiometricSlot;
import android.graphics.Color;
import com.beemdevelopment.aegis.helpers.BiometricSlotInitializer;
import com.beemdevelopment.aegis.ui.intro.SlideFragment;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_BIOMETRIC;
import android.os.Bundle;
import android.view.ViewGroup;
import static com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_PASS;
import com.beemdevelopment.aegis.helpers.PasswordStrengthHelper;
import javax.crypto.SecretKey;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.view.LayoutInflater;
import com.beemdevelopment.aegis.vault.slots.Slot;
import javax.crypto.Cipher;
import com.beemdevelopment.aegis.vault.VaultFileCredentials;
import com.nulabinc.zxcvbn.Strength;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SecuritySetupSlide extends com.beemdevelopment.aegis.ui.intro.SlideFragment {
    static final int MUID_STATIC = getMUID();
    private android.widget.EditText _textPassword;

    private android.widget.EditText _textPasswordConfirm;

    private android.widget.CheckBox _checkPasswordVisibility;

    private android.widget.ProgressBar _barPasswordStrength;

    private android.widget.TextView _textPasswordStrength;

    private com.google.android.material.textfield.TextInputLayout _textPasswordWrapper;

    private int _cryptType;

    private com.beemdevelopment.aegis.vault.VaultFileCredentials _creds;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view;
        view = inflater.inflate(com.beemdevelopment.aegis.R.layout.fragment_security_setup_slide, container, false);
        switch(MUID_STATIC) {
            // SecuritySetupSlide_0_InvalidViewFocusOperatorMutator
            case 120: {
                /**
                * Inserted by Kadabra
                */
                _textPassword = view.findViewById(com.beemdevelopment.aegis.R.id.text_password);
                _textPassword.requestFocus();
                break;
            }
            // SecuritySetupSlide_1_ViewComponentNotVisibleOperatorMutator
            case 1120: {
                /**
                * Inserted by Kadabra
                */
                _textPassword = view.findViewById(com.beemdevelopment.aegis.R.id.text_password);
                _textPassword.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _textPassword = view.findViewById(com.beemdevelopment.aegis.R.id.text_password);
            break;
        }
    }
    switch(MUID_STATIC) {
        // SecuritySetupSlide_2_InvalidViewFocusOperatorMutator
        case 2120: {
            /**
            * Inserted by Kadabra
            */
            _textPasswordConfirm = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_confirm);
            _textPasswordConfirm.requestFocus();
            break;
        }
        // SecuritySetupSlide_3_ViewComponentNotVisibleOperatorMutator
        case 3120: {
            /**
            * Inserted by Kadabra
            */
            _textPasswordConfirm = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_confirm);
            _textPasswordConfirm.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _textPasswordConfirm = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_confirm);
        break;
    }
}
switch(MUID_STATIC) {
    // SecuritySetupSlide_4_InvalidViewFocusOperatorMutator
    case 4120: {
        /**
        * Inserted by Kadabra
        */
        _checkPasswordVisibility = view.findViewById(com.beemdevelopment.aegis.R.id.check_toggle_visibility);
        _checkPasswordVisibility.requestFocus();
        break;
    }
    // SecuritySetupSlide_5_ViewComponentNotVisibleOperatorMutator
    case 5120: {
        /**
        * Inserted by Kadabra
        */
        _checkPasswordVisibility = view.findViewById(com.beemdevelopment.aegis.R.id.check_toggle_visibility);
        _checkPasswordVisibility.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _checkPasswordVisibility = view.findViewById(com.beemdevelopment.aegis.R.id.check_toggle_visibility);
    break;
}
}
switch(MUID_STATIC) {
// SecuritySetupSlide_6_InvalidViewFocusOperatorMutator
case 6120: {
    /**
    * Inserted by Kadabra
    */
    _barPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
    _barPasswordStrength.requestFocus();
    break;
}
// SecuritySetupSlide_7_ViewComponentNotVisibleOperatorMutator
case 7120: {
    /**
    * Inserted by Kadabra
    */
    _barPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
    _barPasswordStrength.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
_barPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
break;
}
}
switch(MUID_STATIC) {
// SecuritySetupSlide_8_InvalidViewFocusOperatorMutator
case 8120: {
/**
* Inserted by Kadabra
*/
_textPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_strength);
_textPasswordStrength.requestFocus();
break;
}
// SecuritySetupSlide_9_ViewComponentNotVisibleOperatorMutator
case 9120: {
/**
* Inserted by Kadabra
*/
_textPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_strength);
_textPasswordStrength.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_strength);
break;
}
}
switch(MUID_STATIC) {
// SecuritySetupSlide_10_InvalidViewFocusOperatorMutator
case 10120: {
/**
* Inserted by Kadabra
*/
_textPasswordWrapper = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_wrapper);
_textPasswordWrapper.requestFocus();
break;
}
// SecuritySetupSlide_11_ViewComponentNotVisibleOperatorMutator
case 11120: {
/**
* Inserted by Kadabra
*/
_textPasswordWrapper = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_wrapper);
_textPasswordWrapper.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
_textPasswordWrapper = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_wrapper);
break;
}
}
_checkPasswordVisibility.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
if (isChecked) {
_textPassword.setTransformationMethod(null);
_textPasswordConfirm.setTransformationMethod(null);
_textPassword.clearFocus();
_textPasswordConfirm.clearFocus();
} else {
_textPassword.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
_textPasswordConfirm.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
}
});
_textPassword.addTextChangedListener(new android.text.TextWatcher() {
private com.nulabinc.zxcvbn.Zxcvbn _zxcvbn = new com.nulabinc.zxcvbn.Zxcvbn();

@java.lang.Override
public void onTextChanged(java.lang.CharSequence s, int start, int before, int count) {
com.nulabinc.zxcvbn.Strength strength;
strength = _zxcvbn.measure(_textPassword.getText());
_barPasswordStrength.setProgress(strength.getScore());
_barPasswordStrength.setProgressTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(com.beemdevelopment.aegis.helpers.PasswordStrengthHelper.getColor(strength.getScore()))));
_textPasswordStrength.setText(_textPassword.getText().length() != 0 ? com.beemdevelopment.aegis.helpers.PasswordStrengthHelper.getString(strength.getScore(), requireContext()) : "");
_textPasswordWrapper.setError(strength.getFeedback().getWarning());
strength.wipe();
}


@java.lang.Override
public void beforeTextChanged(java.lang.CharSequence s, int start, int count, int after) {
}


@java.lang.Override
public void afterTextChanged(android.text.Editable s) {
}

});
return view;
}


@java.lang.Override
public void onResume() {
super.onResume();
_cryptType = getState().getInt("cryptType", com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_INVALID);
if ((_cryptType == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_INVALID) || (_cryptType == com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_NONE)) {
throw new java.lang.RuntimeException(java.lang.String.format("State of SecuritySetupSlide not properly propagated, cryptType: %d", _cryptType));
}
_creds = new com.beemdevelopment.aegis.vault.VaultFileCredentials();
}


private void showBiometricPrompt() {
com.beemdevelopment.aegis.helpers.BiometricSlotInitializer initializer;
initializer = new com.beemdevelopment.aegis.helpers.BiometricSlotInitializer(this, new com.beemdevelopment.aegis.ui.slides.SecuritySetupSlide.BiometricsListener());
androidx.biometric.BiometricPrompt.PromptInfo info;
info = new androidx.biometric.BiometricPrompt.PromptInfo.Builder().setTitle(getString(com.beemdevelopment.aegis.R.string.set_up_biometric)).setNegativeButtonText(getString(android.R.string.cancel)).build();
initializer.authenticate(info);
}


private void deriveKey() {
com.beemdevelopment.aegis.vault.slots.PasswordSlot slot;
slot = new com.beemdevelopment.aegis.vault.slots.PasswordSlot();
com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Params params;
params = new com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Params(slot, com.beemdevelopment.aegis.helpers.EditTextHelper.getEditTextChars(_textPassword));
com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask task;
task = new com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask(requireContext(), new com.beemdevelopment.aegis.ui.slides.SecuritySetupSlide.PasswordDerivationListener());
task.execute(getLifecycle(), params);
}


@java.lang.Override
public boolean isFinished() {
switch (_cryptType) {
case com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_NONE :
return true;
case com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_BIOMETRIC :
if (!_creds.getSlots().has(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class)) {
return false;
}
// intentional fallthrough
case com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_PASS :
if (com.beemdevelopment.aegis.helpers.EditTextHelper.areEditTextsEqual(_textPassword, _textPasswordConfirm)) {
return _creds.getSlots().has(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class);
}
return false;
default :
return false;
}
}


@java.lang.Override
public void onNotFinishedError() {
if (!com.beemdevelopment.aegis.helpers.EditTextHelper.areEditTextsEqual(_textPassword, _textPasswordConfirm)) {
android.widget.Toast.makeText(requireContext(), com.beemdevelopment.aegis.R.string.password_equality_error, android.widget.Toast.LENGTH_SHORT).show();
} else if (_cryptType != com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_BIOMETRIC) {
deriveKey();
} else if (!_creds.getSlots().has(com.beemdevelopment.aegis.vault.slots.BiometricSlot.class)) {
showBiometricPrompt();
}
}


@java.lang.Override
public void onSaveIntroState(@androidx.annotation.NonNull
android.os.Bundle introState) {
introState.putSerializable("creds", _creds);
}


private class PasswordDerivationListener implements com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Callback {
@java.lang.Override
public void onTaskFinished(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.SecretKey key) {
try {
javax.crypto.Cipher cipher;
cipher = com.beemdevelopment.aegis.vault.slots.Slot.createEncryptCipher(key);
slot.setKey(_creds.getKey(), cipher);
_creds.getSlots().add(slot);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
e.printStackTrace();
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.enable_encryption_error, e);
return;
}
goToNextSlide();
}

}

private class BiometricsListener implements com.beemdevelopment.aegis.helpers.BiometricSlotInitializer.Listener {
@java.lang.Override
public void onInitializeSlot(com.beemdevelopment.aegis.vault.slots.BiometricSlot slot, javax.crypto.Cipher cipher) {
try {
slot.setKey(_creds.getKey(), cipher);
_creds.getSlots().add(slot);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
e.printStackTrace();
onSlotInitializationFailed(0, e.toString());
return;
}
deriveKey();
}


@java.lang.Override
public void onSlotInitializationFailed(int errorCode, @androidx.annotation.NonNull
java.lang.CharSequence errString) {
if (!com.beemdevelopment.aegis.helpers.BiometricsHelper.isCanceled(errorCode)) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(requireContext(), com.beemdevelopment.aegis.R.string.encryption_enable_biometrics_error, errString);
}
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
