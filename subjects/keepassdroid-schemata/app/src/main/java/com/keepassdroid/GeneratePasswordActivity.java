/* Copyright 2010 Tolga Onbay, Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid;
import android.widget.CheckBox;
import com.android.keepass.R;
import android.widget.Button;
import android.app.Activity;
import com.keepassdroid.password.PasswordGenerator;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Toast;
import com.android.keepass.KeePass;
import android.view.View;
import android.widget.EditText;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GeneratePasswordActivity extends com.keepassdroid.LockCloseActivity {
    static final int MUID_STATIC = getMUID();
    private static final int[] BUTTON_IDS = new int[]{ com.android.keepass.R.id.btn_length6, com.android.keepass.R.id.btn_length8, com.android.keepass.R.id.btn_length12, com.android.keepass.R.id.btn_length16 };

    public static void Launch(android.app.Activity act) {
        android.content.Intent i;
        switch(MUID_STATIC) {
            // GeneratePasswordActivity_0_NullIntentOperatorMutator
            case 198: {
                i = null;
                break;
            }
            // GeneratePasswordActivity_1_InvalidKeyIntentOperatorMutator
            case 1198: {
                i = new android.content.Intent((Activity) null, com.keepassdroid.GeneratePasswordActivity.class);
                break;
            }
            // GeneratePasswordActivity_2_RandomActionIntentDefinitionOperatorMutator
            case 2198: {
                i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            i = new android.content.Intent(act, com.keepassdroid.GeneratePasswordActivity.class);
            break;
        }
    }
    act.startActivityForResult(i, 0);
}


private android.view.View.OnClickListener lengthButtonsListener = new android.view.View.OnClickListener() {
    public void onClick(android.view.View v) {
        android.widget.Button button;
        button = ((android.widget.Button) (v));
        android.widget.EditText editText;
        switch(MUID_STATIC) {
            // GeneratePasswordActivity_4_InvalidViewFocusOperatorMutator
            case 4198: {
                /**
                * Inserted by Kadabra
                */
                editText = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.length)));
                editText.requestFocus();
                break;
            }
            // GeneratePasswordActivity_5_ViewComponentNotVisibleOperatorMutator
            case 5198: {
                /**
                * Inserted by Kadabra
                */
                editText = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.length)));
                editText.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            editText = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.length)));
            break;
        }
    }
    switch(MUID_STATIC) {
        // GeneratePasswordActivity_3_LengthyGUIListenerOperatorMutator
        case 3198: {
            /**
            * Inserted by Kadabra
            */
            editText.setText(button.getText());
            try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
            break;
        }
        default: {
        editText.setText(button.getText());
        break;
    }
}
}

};

@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
// GeneratePasswordActivity_6_LengthyGUICreationOperatorMutator
case 6198: {
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
setContentView(com.android.keepass.R.layout.generate_password);
setResult(com.android.keepass.KeePass.EXIT_NORMAL);
for (int id : com.keepassdroid.GeneratePasswordActivity.BUTTON_IDS) {
android.widget.Button button;
switch(MUID_STATIC) {
// GeneratePasswordActivity_7_InvalidViewFocusOperatorMutator
case 7198: {
    /**
    * Inserted by Kadabra
    */
    button = ((android.widget.Button) (findViewById(id)));
    button.requestFocus();
    break;
}
// GeneratePasswordActivity_8_ViewComponentNotVisibleOperatorMutator
case 8198: {
    /**
    * Inserted by Kadabra
    */
    button = ((android.widget.Button) (findViewById(id)));
    button.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
button = ((android.widget.Button) (findViewById(id)));
break;
}
}
button.setOnClickListener(lengthButtonsListener);
}
android.widget.Button genPassButton;
switch(MUID_STATIC) {
// GeneratePasswordActivity_9_InvalidViewFocusOperatorMutator
case 9198: {
/**
* Inserted by Kadabra
*/
genPassButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.generate_password_button)));
genPassButton.requestFocus();
break;
}
// GeneratePasswordActivity_10_ViewComponentNotVisibleOperatorMutator
case 10198: {
/**
* Inserted by Kadabra
*/
genPassButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.generate_password_button)));
genPassButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
genPassButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.generate_password_button)));
break;
}
}
switch(MUID_STATIC) {
// GeneratePasswordActivity_11_BuggyGUIListenerOperatorMutator
case 11198: {
genPassButton.setOnClickListener(null);
break;
}
default: {
genPassButton.setOnClickListener(new android.view.View.OnClickListener() {
public void onClick(android.view.View v) {
switch(MUID_STATIC) {
// GeneratePasswordActivity_12_LengthyGUIListenerOperatorMutator
case 12198: {
    /**
    * Inserted by Kadabra
    */
    fillPassword();
    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
    break;
}
default: {
fillPassword();
break;
}
}
}

});
break;
}
}
android.widget.Button acceptButton;
switch(MUID_STATIC) {
// GeneratePasswordActivity_13_InvalidViewFocusOperatorMutator
case 13198: {
/**
* Inserted by Kadabra
*/
acceptButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.accept_button)));
acceptButton.requestFocus();
break;
}
// GeneratePasswordActivity_14_ViewComponentNotVisibleOperatorMutator
case 14198: {
/**
* Inserted by Kadabra
*/
acceptButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.accept_button)));
acceptButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
acceptButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.accept_button)));
break;
}
}
switch(MUID_STATIC) {
// GeneratePasswordActivity_15_BuggyGUIListenerOperatorMutator
case 15198: {
acceptButton.setOnClickListener(null);
break;
}
default: {
acceptButton.setOnClickListener(new android.view.View.OnClickListener() {
public void onClick(android.view.View v) {
android.widget.EditText password;
switch(MUID_STATIC) {
// GeneratePasswordActivity_17_InvalidViewFocusOperatorMutator
case 17198: {
/**
* Inserted by Kadabra
*/
password = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.password)));
password.requestFocus();
break;
}
// GeneratePasswordActivity_18_ViewComponentNotVisibleOperatorMutator
case 18198: {
/**
* Inserted by Kadabra
*/
password = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.password)));
password.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
password = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.password)));
break;
}
}
android.content.Intent intent;
switch(MUID_STATIC) {
// GeneratePasswordActivity_19_RandomActionIntentDefinitionOperatorMutator
case 19198: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// GeneratePasswordActivity_20_NullValueIntentPutExtraOperatorMutator
case 20198: {
intent.putExtra("com.keepassdroid.password.generated_password", new Parcelable[0]);
break;
}
// GeneratePasswordActivity_21_IntentPayloadReplacementOperatorMutator
case 21198: {
intent.putExtra("com.keepassdroid.password.generated_password", "");
break;
}
default: {
switch(MUID_STATIC) {
// GeneratePasswordActivity_22_RandomActionIntentDefinitionOperatorMutator
case 22198: {
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
intent.putExtra("com.keepassdroid.password.generated_password", password.getText().toString());
break;
}
}
break;
}
}
setResult(com.keepassdroid.EntryEditActivity.RESULT_OK_PASSWORD_GENERATOR, intent);
switch(MUID_STATIC) {
// GeneratePasswordActivity_16_LengthyGUIListenerOperatorMutator
case 16198: {
/**
* Inserted by Kadabra
*/
finish();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
finish();
break;
}
}
}

});
break;
}
}
android.widget.Button cancelButton;
switch(MUID_STATIC) {
// GeneratePasswordActivity_23_InvalidViewFocusOperatorMutator
case 23198: {
/**
* Inserted by Kadabra
*/
cancelButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.cancel_button)));
cancelButton.requestFocus();
break;
}
// GeneratePasswordActivity_24_ViewComponentNotVisibleOperatorMutator
case 24198: {
/**
* Inserted by Kadabra
*/
cancelButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.cancel_button)));
cancelButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cancelButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.cancel_button)));
break;
}
}
switch(MUID_STATIC) {
// GeneratePasswordActivity_25_BuggyGUIListenerOperatorMutator
case 25198: {
cancelButton.setOnClickListener(null);
break;
}
default: {
cancelButton.setOnClickListener(new android.view.View.OnClickListener() {
public void onClick(android.view.View v) {
setResult(android.app.Activity.RESULT_CANCELED);
switch(MUID_STATIC) {
// GeneratePasswordActivity_26_LengthyGUIListenerOperatorMutator
case 26198: {
/**
* Inserted by Kadabra
*/
finish();
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
finish();
break;
}
}
}

});
break;
}
}
// Pre-populate a password to possibly save the user a few clicks
fillPassword();
}


private void fillPassword() {
android.widget.EditText txtPassword;
switch(MUID_STATIC) {
// GeneratePasswordActivity_27_InvalidViewFocusOperatorMutator
case 27198: {
/**
* Inserted by Kadabra
*/
txtPassword = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.password)));
txtPassword.requestFocus();
break;
}
// GeneratePasswordActivity_28_ViewComponentNotVisibleOperatorMutator
case 28198: {
/**
* Inserted by Kadabra
*/
txtPassword = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.password)));
txtPassword.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
txtPassword = ((android.widget.EditText) (findViewById(com.android.keepass.R.id.password)));
break;
}
}
txtPassword.setText(generatePassword());
}


public java.lang.String generatePassword() {
java.lang.String password;
password = "";
try {
int length;
length = java.lang.Integer.valueOf(((android.widget.EditText) (findViewById(com.android.keepass.R.id.length))).getText().toString());
((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_uppercase))).isChecked();
com.keepassdroid.password.PasswordGenerator generator;
generator = new com.keepassdroid.password.PasswordGenerator(this);
password = generator.generatePassword(length, ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_uppercase))).isChecked(), ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_lowercase))).isChecked(), ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_digits))).isChecked(), ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_minus))).isChecked(), ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_underline))).isChecked(), ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_space))).isChecked(), ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_specials))).isChecked(), ((android.widget.CheckBox) (findViewById(com.android.keepass.R.id.cb_brackets))).isChecked());
} catch (java.lang.NumberFormatException e) {
android.widget.Toast.makeText(this, com.android.keepass.R.string.error_wrong_length, android.widget.Toast.LENGTH_LONG).show();
} catch (java.lang.IllegalArgumentException e) {
android.widget.Toast.makeText(this, e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
}
return password;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
