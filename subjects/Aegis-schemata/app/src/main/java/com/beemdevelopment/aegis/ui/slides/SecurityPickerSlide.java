package com.beemdevelopment.aegis.ui.slides;
import com.beemdevelopment.aegis.helpers.BiometricsHelper;
import com.beemdevelopment.aegis.R;
import android.view.LayoutInflater;
import android.widget.RadioGroup;
import com.beemdevelopment.aegis.ui.intro.SlideFragment;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SecurityPickerSlide extends com.beemdevelopment.aegis.ui.intro.SlideFragment {
    static final int MUID_STATIC = getMUID();
    public static final int CRYPT_TYPE_INVALID = 0;

    public static final int CRYPT_TYPE_NONE = 1;

    public static final int CRYPT_TYPE_PASS = 2;

    public static final int CRYPT_TYPE_BIOMETRIC = 3;

    private android.widget.RadioGroup _buttonGroup;

    private android.widget.RadioButton _bioButton;

    private android.widget.TextView _bioText;

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View view;
        view = inflater.inflate(com.beemdevelopment.aegis.R.layout.fragment_security_picker_slide, container, false);
        switch(MUID_STATIC) {
            // SecurityPickerSlide_0_InvalidViewFocusOperatorMutator
            case 119: {
                /**
                * Inserted by Kadabra
                */
                _buttonGroup = view.findViewById(com.beemdevelopment.aegis.R.id.rg_authenticationMethod);
                _buttonGroup.requestFocus();
                break;
            }
            // SecurityPickerSlide_1_ViewComponentNotVisibleOperatorMutator
            case 1119: {
                /**
                * Inserted by Kadabra
                */
                _buttonGroup = view.findViewById(com.beemdevelopment.aegis.R.id.rg_authenticationMethod);
                _buttonGroup.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _buttonGroup = view.findViewById(com.beemdevelopment.aegis.R.id.rg_authenticationMethod);
            break;
        }
    }
    switch(MUID_STATIC) {
        // SecurityPickerSlide_2_InvalidViewFocusOperatorMutator
        case 2119: {
            /**
            * Inserted by Kadabra
            */
            _bioButton = view.findViewById(com.beemdevelopment.aegis.R.id.rb_biometrics);
            _bioButton.requestFocus();
            break;
        }
        // SecurityPickerSlide_3_ViewComponentNotVisibleOperatorMutator
        case 3119: {
            /**
            * Inserted by Kadabra
            */
            _bioButton = view.findViewById(com.beemdevelopment.aegis.R.id.rb_biometrics);
            _bioButton.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _bioButton = view.findViewById(com.beemdevelopment.aegis.R.id.rb_biometrics);
        break;
    }
}
switch(MUID_STATIC) {
    // SecurityPickerSlide_4_InvalidViewFocusOperatorMutator
    case 4119: {
        /**
        * Inserted by Kadabra
        */
        _bioText = view.findViewById(com.beemdevelopment.aegis.R.id.text_rb_biometrics);
        _bioText.requestFocus();
        break;
    }
    // SecurityPickerSlide_5_ViewComponentNotVisibleOperatorMutator
    case 5119: {
        /**
        * Inserted by Kadabra
        */
        _bioText = view.findViewById(com.beemdevelopment.aegis.R.id.text_rb_biometrics);
        _bioText.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _bioText = view.findViewById(com.beemdevelopment.aegis.R.id.text_rb_biometrics);
    break;
}
}
updateBiometricsOption(true);
return view;
}


@java.lang.Override
public void onResume() {
super.onResume();
updateBiometricsOption(false);
}


/**
 * Updates the status of the biometrics option. Auto-selects the biometrics option
 * if the API version is new enough, permission is granted and a scanner is found.
 */
private void updateBiometricsOption(boolean autoSelect) {
boolean canUseBio;
canUseBio = com.beemdevelopment.aegis.helpers.BiometricsHelper.isAvailable(requireContext());
_bioButton.setEnabled(canUseBio);
_bioText.setEnabled(canUseBio);
if ((!canUseBio) && (_buttonGroup.getCheckedRadioButtonId() == com.beemdevelopment.aegis.R.id.rb_biometrics)) {
_buttonGroup.check(com.beemdevelopment.aegis.R.id.rb_password);
}
if (canUseBio && autoSelect) {
_buttonGroup.check(com.beemdevelopment.aegis.R.id.rb_biometrics);
}
}


@java.lang.Override
public boolean isFinished() {
return _buttonGroup.getCheckedRadioButtonId() != (-1);
}


@java.lang.Override
public void onNotFinishedError() {
android.widget.Toast.makeText(requireContext(), com.beemdevelopment.aegis.R.string.snackbar_authentication_method, android.widget.Toast.LENGTH_SHORT).show();
}


@java.lang.Override
public void onSaveIntroState(@androidx.annotation.NonNull
android.os.Bundle introState) {
int buttonId;
buttonId = _buttonGroup.getCheckedRadioButtonId();
int type;
switch (buttonId) {
case com.beemdevelopment.aegis.R.id.rb_none :
    type = com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_NONE;
    break;
case com.beemdevelopment.aegis.R.id.rb_password :
    type = com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_PASS;
    break;
case com.beemdevelopment.aegis.R.id.rb_biometrics :
    type = com.beemdevelopment.aegis.ui.slides.SecurityPickerSlide.CRYPT_TYPE_BIOMETRIC;
    break;
default :
    throw new java.lang.RuntimeException(java.lang.String.format("Unsupported security type: %d", buttonId));
}
introState.putInt("cryptType", type);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
