package com.beemdevelopment.aegis.ui.dialogs;
import androidx.appcompat.app.AlertDialog;
import com.nulabinc.zxcvbn.Zxcvbn;
import android.content.DialogInterface;
import android.text.TextWatcher;
import java.util.ArrayList;
import android.content.res.ColorStateList;
import android.view.WindowManager;
import com.beemdevelopment.aegis.helpers.EditTextHelper;
import com.beemdevelopment.aegis.vault.slots.PasswordSlot;
import com.beemdevelopment.aegis.vault.slots.SlotException;
import android.text.method.PasswordTransformationMethod;
import com.beemdevelopment.aegis.R;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.Button;
import android.widget.ListView;
import com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask;
import com.beemdevelopment.aegis.importers.DatabaseImporter;
import android.app.Dialog;
import com.beemdevelopment.aegis.vault.VaultEntry;
import androidx.annotation.StringRes;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import android.widget.ProgressBar;
import com.beemdevelopment.aegis.Preferences;
import android.graphics.Color;
import java.util.stream.Collectors;
import com.beemdevelopment.aegis.helpers.PasswordStrengthHelper;
import java.util.concurrent.atomic.AtomicReference;
import android.widget.NumberPicker;
import android.view.View;
import android.content.ClipboardManager;
import android.widget.EditText;
import com.beemdevelopment.aegis.helpers.SimpleTextWatcher;
import android.widget.CheckBox;
import androidx.activity.ComponentActivity;
import android.view.LayoutInflater;
import android.text.InputType;
import com.beemdevelopment.aegis.vault.slots.Slot;
import com.google.android.material.textfield.TextInputEditText;
import android.content.ClipData;
import javax.crypto.Cipher;
import com.nulabinc.zxcvbn.Strength;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Dialogs {
    static final int MUID_STATIC = getMUID();
    private Dialogs() {
    }


    public static void secureDialog(android.app.Dialog dialog) {
        if (new com.beemdevelopment.aegis.Preferences(dialog.getContext()).isSecureScreenEnabled()) {
            dialog.getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE, android.view.WindowManager.LayoutParams.FLAG_SECURE);
        }
    }


    public static void showSecureDialog(android.app.Dialog dialog) {
        com.beemdevelopment.aegis.ui.dialogs.Dialogs.secureDialog(dialog);
        dialog.show();
    }


    public static void showDeleteEntriesDialog(android.content.Context context, java.util.List<com.beemdevelopment.aegis.vault.VaultEntry> services, android.content.DialogInterface.OnClickListener onDelete) {
        android.view.View view;
        view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_delete_entry, null);
        android.widget.TextView textMessage;
        switch(MUID_STATIC) {
            // Dialogs_0_InvalidViewFocusOperatorMutator
            case 114: {
                /**
                * Inserted by Kadabra
                */
                textMessage = view.findViewById(com.beemdevelopment.aegis.R.id.text_message);
                textMessage.requestFocus();
                break;
            }
            // Dialogs_1_ViewComponentNotVisibleOperatorMutator
            case 1114: {
                /**
                * Inserted by Kadabra
                */
                textMessage = view.findViewById(com.beemdevelopment.aegis.R.id.text_message);
                textMessage.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            textMessage = view.findViewById(com.beemdevelopment.aegis.R.id.text_message);
            break;
        }
    }
    android.widget.TextView textExplanation;
    switch(MUID_STATIC) {
        // Dialogs_2_InvalidViewFocusOperatorMutator
        case 2114: {
            /**
            * Inserted by Kadabra
            */
            textExplanation = view.findViewById(com.beemdevelopment.aegis.R.id.text_explanation);
            textExplanation.requestFocus();
            break;
        }
        // Dialogs_3_ViewComponentNotVisibleOperatorMutator
        case 3114: {
            /**
            * Inserted by Kadabra
            */
            textExplanation = view.findViewById(com.beemdevelopment.aegis.R.id.text_explanation);
            textExplanation.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        textExplanation = view.findViewById(com.beemdevelopment.aegis.R.id.text_explanation);
        break;
    }
}
java.lang.String entries;
entries = services.stream().map((com.beemdevelopment.aegis.vault.VaultEntry entry) -> java.lang.String.format("• %s", com.beemdevelopment.aegis.ui.dialogs.Dialogs.getVaultEntryName(context, entry))).collect(java.util.stream.Collectors.joining("\n"));
textExplanation.setText(context.getString(com.beemdevelopment.aegis.R.string.delete_entry_explanation, entries));
java.lang.String title;
java.lang.String message;
if (services.size() > 1) {
    title = context.getString(com.beemdevelopment.aegis.R.string.delete_entries);
    message = context.getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.delete_entries_description, services.size(), services.size());
} else {
    title = context.getString(com.beemdevelopment.aegis.R.string.delete_entry);
    message = context.getString(com.beemdevelopment.aegis.R.string.delete_entry_description);
}
textMessage.setText(message);
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(title).setView(view).setPositiveButton(android.R.string.yes, onDelete).setNegativeButton(android.R.string.no, null).create());
}


private static java.lang.String getVaultEntryName(android.content.Context context, com.beemdevelopment.aegis.vault.VaultEntry entry) {
if ((!entry.getIssuer().isEmpty()) && (!entry.getName().isEmpty())) {
    return java.lang.String.format("%s (%s)", entry.getIssuer(), entry.getName());
} else if (entry.getIssuer().isEmpty() && entry.getName().isEmpty()) {
    return context.getString(com.beemdevelopment.aegis.R.string.unknown_issuer);
} else if (entry.getIssuer().isEmpty()) {
    return entry.getName();
} else {
    return entry.getIssuer();
}
}


public static void showDiscardDialog(android.content.Context context, android.content.DialogInterface.OnClickListener onSave, android.content.DialogInterface.OnClickListener onDiscard) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(context.getString(com.beemdevelopment.aegis.R.string.discard_changes)).setMessage(context.getString(com.beemdevelopment.aegis.R.string.discard_changes_description)).setPositiveButton(com.beemdevelopment.aegis.R.string.save, onSave).setNegativeButton(com.beemdevelopment.aegis.R.string.discard, onDiscard).create());
}


public static void showSetPasswordDialog(androidx.activity.ComponentActivity activity, com.beemdevelopment.aegis.ui.dialogs.Dialogs.PasswordSlotListener listener) {
com.nulabinc.zxcvbn.Zxcvbn zxcvbn;
zxcvbn = new com.nulabinc.zxcvbn.Zxcvbn();
android.view.View view;
view = activity.getLayoutInflater().inflate(com.beemdevelopment.aegis.R.layout.dialog_password, null);
android.widget.EditText textPassword;
switch(MUID_STATIC) {
    // Dialogs_4_InvalidViewFocusOperatorMutator
    case 4114: {
        /**
        * Inserted by Kadabra
        */
        textPassword = view.findViewById(com.beemdevelopment.aegis.R.id.text_password);
        textPassword.requestFocus();
        break;
    }
    // Dialogs_5_ViewComponentNotVisibleOperatorMutator
    case 5114: {
        /**
        * Inserted by Kadabra
        */
        textPassword = view.findViewById(com.beemdevelopment.aegis.R.id.text_password);
        textPassword.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    textPassword = view.findViewById(com.beemdevelopment.aegis.R.id.text_password);
    break;
}
}
android.widget.EditText textPasswordConfirm;
switch(MUID_STATIC) {
// Dialogs_6_InvalidViewFocusOperatorMutator
case 6114: {
    /**
    * Inserted by Kadabra
    */
    textPasswordConfirm = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_confirm);
    textPasswordConfirm.requestFocus();
    break;
}
// Dialogs_7_ViewComponentNotVisibleOperatorMutator
case 7114: {
    /**
    * Inserted by Kadabra
    */
    textPasswordConfirm = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_confirm);
    textPasswordConfirm.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
textPasswordConfirm = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_confirm);
break;
}
}
android.widget.ProgressBar barPasswordStrength;
switch(MUID_STATIC) {
// Dialogs_8_InvalidViewFocusOperatorMutator
case 8114: {
/**
* Inserted by Kadabra
*/
barPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
barPasswordStrength.requestFocus();
break;
}
// Dialogs_9_ViewComponentNotVisibleOperatorMutator
case 9114: {
/**
* Inserted by Kadabra
*/
barPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
barPasswordStrength.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
barPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.progressBar);
break;
}
}
android.widget.TextView textPasswordStrength;
switch(MUID_STATIC) {
// Dialogs_10_InvalidViewFocusOperatorMutator
case 10114: {
/**
* Inserted by Kadabra
*/
textPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_strength);
textPasswordStrength.requestFocus();
break;
}
// Dialogs_11_ViewComponentNotVisibleOperatorMutator
case 11114: {
/**
* Inserted by Kadabra
*/
textPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_strength);
textPasswordStrength.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textPasswordStrength = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_strength);
break;
}
}
com.google.android.material.textfield.TextInputLayout textPasswordWrapper;
switch(MUID_STATIC) {
// Dialogs_12_InvalidViewFocusOperatorMutator
case 12114: {
/**
* Inserted by Kadabra
*/
textPasswordWrapper = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_wrapper);
textPasswordWrapper.requestFocus();
break;
}
// Dialogs_13_ViewComponentNotVisibleOperatorMutator
case 13114: {
/**
* Inserted by Kadabra
*/
textPasswordWrapper = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_wrapper);
textPasswordWrapper.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textPasswordWrapper = view.findViewById(com.beemdevelopment.aegis.R.id.text_password_wrapper);
break;
}
}
android.widget.CheckBox switchToggleVisibility;
switch(MUID_STATIC) {
// Dialogs_14_InvalidViewFocusOperatorMutator
case 14114: {
/**
* Inserted by Kadabra
*/
switchToggleVisibility = view.findViewById(com.beemdevelopment.aegis.R.id.check_toggle_visibility);
switchToggleVisibility.requestFocus();
break;
}
// Dialogs_15_ViewComponentNotVisibleOperatorMutator
case 15114: {
/**
* Inserted by Kadabra
*/
switchToggleVisibility = view.findViewById(com.beemdevelopment.aegis.R.id.check_toggle_visibility);
switchToggleVisibility.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
switchToggleVisibility = view.findViewById(com.beemdevelopment.aegis.R.id.check_toggle_visibility);
break;
}
}
switchToggleVisibility.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
if (isChecked) {
textPassword.setTransformationMethod(null);
textPasswordConfirm.setTransformationMethod(null);
textPassword.clearFocus();
textPasswordConfirm.clearFocus();
} else {
textPassword.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
textPasswordConfirm.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
}
});
androidx.appcompat.app.AlertDialog dialog;
dialog = new androidx.appcompat.app.AlertDialog.Builder(activity).setTitle(com.beemdevelopment.aegis.R.string.set_password).setView(view).setPositiveButton(android.R.string.ok, null).setNegativeButton(android.R.string.cancel, null).create();
dialog.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
final java.util.concurrent.atomic.AtomicReference<android.widget.Button> buttonOK;
buttonOK = new java.util.concurrent.atomic.AtomicReference<>();
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button button;
button = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
button.setEnabled(false);
buttonOK.set(button);
switch(MUID_STATIC) {
// Dialogs_16_BuggyGUIListenerOperatorMutator
case 16114: {
// replace the default listener
button.setOnClickListener(null);
break;
}
default: {
// replace the default listener
button.setOnClickListener((android.view.View v) -> {
if (!com.beemdevelopment.aegis.helpers.EditTextHelper.areEditTextsEqual(textPassword, textPasswordConfirm)) {
return;
}
char[] password;
password = com.beemdevelopment.aegis.helpers.EditTextHelper.getEditTextChars(textPassword);
com.beemdevelopment.aegis.vault.slots.PasswordSlot slot;
slot = new com.beemdevelopment.aegis.vault.slots.PasswordSlot();
com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask task;
task = new com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask(activity, (com.beemdevelopment.aegis.vault.slots.PasswordSlot passSlot,javax.crypto.SecretKey key) -> {
javax.crypto.Cipher cipher;
try {
cipher = com.beemdevelopment.aegis.vault.slots.Slot.createEncryptCipher(key);
} catch (com.beemdevelopment.aegis.vault.slots.SlotException e) {
listener.onException(e);
dialog.cancel();
return;
}
listener.onSlotResult(slot, cipher);
dialog.dismiss();
});
com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Params params;
params = new com.beemdevelopment.aegis.ui.tasks.KeyDerivationTask.Params(slot, password);
task.execute(activity.getLifecycle(), params);
});
break;
}
}
});
android.text.TextWatcher watcher;
watcher = new com.beemdevelopment.aegis.helpers.SimpleTextWatcher((android.text.Editable text) -> {
boolean equal;
equal = com.beemdevelopment.aegis.helpers.EditTextHelper.areEditTextsEqual(textPassword, textPasswordConfirm);
buttonOK.get().setEnabled(equal);
com.nulabinc.zxcvbn.Strength strength;
strength = zxcvbn.measure(textPassword.getText());
barPasswordStrength.setProgress(strength.getScore());
barPasswordStrength.setProgressTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(com.beemdevelopment.aegis.helpers.PasswordStrengthHelper.getColor(strength.getScore()))));
textPasswordStrength.setText(textPassword.getText().length() != 0 ? com.beemdevelopment.aegis.helpers.PasswordStrengthHelper.getString(strength.getScore(), activity) : "");
textPasswordWrapper.setError(strength.getFeedback().getWarning());
strength.wipe();
});
textPassword.addTextChangedListener(watcher);
textPasswordConfirm.addTextChangedListener(watcher);
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


private static void showTextInputDialog(android.content.Context context, @androidx.annotation.StringRes
int titleId, @androidx.annotation.StringRes
int messageId, @androidx.annotation.StringRes
int hintId, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener, android.content.DialogInterface.OnCancelListener cancelListener, boolean isSecret) {
final java.util.concurrent.atomic.AtomicReference<android.widget.Button> buttonOK;
buttonOK = new java.util.concurrent.atomic.AtomicReference<>();
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_text_input, null);
com.google.android.material.textfield.TextInputEditText input;
switch(MUID_STATIC) {
// Dialogs_17_InvalidViewFocusOperatorMutator
case 17114: {
/**
* Inserted by Kadabra
*/
input = view.findViewById(com.beemdevelopment.aegis.R.id.text_input);
input.requestFocus();
break;
}
// Dialogs_18_ViewComponentNotVisibleOperatorMutator
case 18114: {
/**
* Inserted by Kadabra
*/
input = view.findViewById(com.beemdevelopment.aegis.R.id.text_input);
input.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
input = view.findViewById(com.beemdevelopment.aegis.R.id.text_input);
break;
}
}
input.addTextChangedListener(new com.beemdevelopment.aegis.helpers.SimpleTextWatcher((android.text.Editable text) -> {
if (buttonOK.get() != null) {
buttonOK.get().setEnabled(!text.toString().isEmpty());
}
}));
com.google.android.material.textfield.TextInputLayout inputLayout;
switch(MUID_STATIC) {
// Dialogs_19_InvalidViewFocusOperatorMutator
case 19114: {
/**
* Inserted by Kadabra
*/
inputLayout = view.findViewById(com.beemdevelopment.aegis.R.id.text_input_layout);
inputLayout.requestFocus();
break;
}
// Dialogs_20_ViewComponentNotVisibleOperatorMutator
case 20114: {
/**
* Inserted by Kadabra
*/
inputLayout = view.findViewById(com.beemdevelopment.aegis.R.id.text_input_layout);
inputLayout.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
inputLayout = view.findViewById(com.beemdevelopment.aegis.R.id.text_input_layout);
break;
}
}
if (isSecret) {
input.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
}
inputLayout.setHint(hintId);
androidx.appcompat.app.AlertDialog.Builder builder;
builder = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(titleId).setView(view).setPositiveButton(android.R.string.ok, null);
if (cancelListener != null) {
builder.setOnCancelListener(cancelListener);
}
if (messageId != 0) {
builder.setMessage(messageId);
}
androidx.appcompat.app.AlertDialog dialog;
dialog = builder.create();
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button button;
button = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
button.setEnabled(false);
buttonOK.set(button);
switch(MUID_STATIC) {
// Dialogs_21_BuggyGUIListenerOperatorMutator
case 21114: {
button.setOnClickListener(null);
break;
}
default: {
button.setOnClickListener((android.view.View v) -> {
char[] text;
text = com.beemdevelopment.aegis.helpers.EditTextHelper.getEditTextChars(input);
listener.onTextInputResult(text);
dialog.dismiss();
});
break;
}
}
});
dialog.setCanceledOnTouchOutside(true);
dialog.getWindow().setSoftInputMode(android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


private static void showTextInputDialog(android.content.Context context, @androidx.annotation.StringRes
int titleId, @androidx.annotation.StringRes
int hintId, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener, boolean isSecret) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(context, titleId, 0, hintId, listener, null, isSecret);
}


public static void showTextInputDialog(android.content.Context context, @androidx.annotation.StringRes
int titleId, @androidx.annotation.StringRes
int hintId, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(context, titleId, hintId, listener, false);
}


public static void showPasswordInputDialog(android.content.Context context, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(context, com.beemdevelopment.aegis.R.string.set_password, com.beemdevelopment.aegis.R.string.password, listener, true);
}


public static void showPasswordInputDialog(android.content.Context context, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener, android.content.DialogInterface.OnCancelListener cancelListener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(context, com.beemdevelopment.aegis.R.string.set_password, 0, com.beemdevelopment.aegis.R.string.password, listener, cancelListener, true);
}


public static void showPasswordInputDialog(android.content.Context context, @androidx.annotation.StringRes
int messageId, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(context, com.beemdevelopment.aegis.R.string.set_password, messageId, com.beemdevelopment.aegis.R.string.password, listener, null, true);
}


public static void showPasswordInputDialog(android.content.Context context, @androidx.annotation.StringRes
int messageId, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener, android.content.DialogInterface.OnCancelListener cancelListener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(context, com.beemdevelopment.aegis.R.string.set_password, messageId, com.beemdevelopment.aegis.R.string.password, listener, cancelListener, true);
}


public static void showPasswordInputDialog(android.content.Context context, @androidx.annotation.StringRes
int titleId, @androidx.annotation.StringRes
int messageId, com.beemdevelopment.aegis.ui.dialogs.Dialogs.TextInputListener listener, android.content.DialogInterface.OnCancelListener cancelListener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showTextInputDialog(context, titleId, messageId, com.beemdevelopment.aegis.R.string.password, listener, cancelListener, true);
}


public static void showCheckboxDialog(android.content.Context context, @androidx.annotation.StringRes
int titleId, @androidx.annotation.StringRes
int messageId, @androidx.annotation.StringRes
int checkboxMessageId, com.beemdevelopment.aegis.ui.dialogs.Dialogs.CheckboxInputListener listener) {
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_checkbox, null);
android.widget.CheckBox checkBox;
switch(MUID_STATIC) {
// Dialogs_22_InvalidViewFocusOperatorMutator
case 22114: {
/**
* Inserted by Kadabra
*/
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox);
checkBox.requestFocus();
break;
}
// Dialogs_23_ViewComponentNotVisibleOperatorMutator
case 23114: {
/**
* Inserted by Kadabra
*/
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox);
checkBox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox);
break;
}
}
checkBox.setText(checkboxMessageId);
androidx.appcompat.app.AlertDialog.Builder builder;
switch(MUID_STATIC) {
// Dialogs_24_BuggyGUIListenerOperatorMutator
case 24114: {
builder = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(titleId).setView(view).setNegativeButton(com.beemdevelopment.aegis.R.string.no, null).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog1,int which) -> listener.onCheckboxInputResult(checkBox.isChecked()));
break;
}
default: {
switch(MUID_STATIC) {
// Dialogs_25_BuggyGUIListenerOperatorMutator
case 25114: {
builder = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(titleId).setView(view).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog1,int which) -> listener.onCheckboxInputResult(false)).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, null);
break;
}
default: {
builder = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(titleId).setView(view).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog1,int which) -> listener.onCheckboxInputResult(false)).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog1,int which) -> listener.onCheckboxInputResult(checkBox.isChecked()));
break;
}
}
break;
}
}
if (messageId != 0) {
builder.setMessage(messageId);
}
androidx.appcompat.app.AlertDialog dialog;
dialog = builder.create();
final java.util.concurrent.atomic.AtomicReference<android.widget.Button> buttonOK;
buttonOK = new java.util.concurrent.atomic.AtomicReference<>();
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button button;
button = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
button.setEnabled(false);
buttonOK.set(button);
});
checkBox.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> buttonOK.get().setEnabled(isChecked));
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


public static void showTapToRevealTimeoutPickerDialog(android.content.Context context, int currentValue, com.beemdevelopment.aegis.ui.dialogs.Dialogs.NumberInputListener listener) {
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_number_picker, null);
android.widget.NumberPicker numberPicker;
switch(MUID_STATIC) {
// Dialogs_26_InvalidViewFocusOperatorMutator
case 26114: {
/**
* Inserted by Kadabra
*/
numberPicker = view.findViewById(com.beemdevelopment.aegis.R.id.numberPicker);
numberPicker.requestFocus();
break;
}
// Dialogs_27_ViewComponentNotVisibleOperatorMutator
case 27114: {
/**
* Inserted by Kadabra
*/
numberPicker = view.findViewById(com.beemdevelopment.aegis.R.id.numberPicker);
numberPicker.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
numberPicker = view.findViewById(com.beemdevelopment.aegis.R.id.numberPicker);
break;
}
}
numberPicker.setMinValue(1);
numberPicker.setMaxValue(60);
numberPicker.setValue(currentValue);
numberPicker.setWrapSelectorWheel(true);
androidx.appcompat.app.AlertDialog dialog;
switch(MUID_STATIC) {
// Dialogs_28_BuggyGUIListenerOperatorMutator
case 28114: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.set_number).setView(view).setPositiveButton(android.R.string.ok, null).create();
break;
}
default: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.set_number).setView(view).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog1,int which) -> listener.onNumberInputResult(numberPicker.getValue())).create();
break;
}
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


public static void showBackupVersionsPickerDialog(android.content.Context context, int currentVersionCount, com.beemdevelopment.aegis.ui.dialogs.Dialogs.NumberInputListener listener) {
final int max;
max = 30;
java.lang.String[] numbers;
switch(MUID_STATIC) {
// Dialogs_29_BinaryMutator
case 29114: {
numbers = new java.lang.String[max * 5];
break;
}
default: {
numbers = new java.lang.String[max / 5];
break;
}
}
for (int i = 0; i < numbers.length; i++) {
switch(MUID_STATIC) {
// Dialogs_30_BinaryMutator
case 30114: {
numbers[i] = java.lang.Integer.toString((i * 5) - 5);
break;
}
default: {
switch(MUID_STATIC) {
// Dialogs_31_BinaryMutator
case 31114: {
numbers[i] = java.lang.Integer.toString((i / 5) + 5);
break;
}
default: {
numbers[i] = java.lang.Integer.toString((i * 5) + 5);
break;
}
}
break;
}
}
}
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_number_picker, null);
android.widget.NumberPicker numberPicker;
switch(MUID_STATIC) {
// Dialogs_32_InvalidViewFocusOperatorMutator
case 32114: {
/**
* Inserted by Kadabra
*/
numberPicker = view.findViewById(com.beemdevelopment.aegis.R.id.numberPicker);
numberPicker.requestFocus();
break;
}
// Dialogs_33_ViewComponentNotVisibleOperatorMutator
case 33114: {
/**
* Inserted by Kadabra
*/
numberPicker = view.findViewById(com.beemdevelopment.aegis.R.id.numberPicker);
numberPicker.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
numberPicker = view.findViewById(com.beemdevelopment.aegis.R.id.numberPicker);
break;
}
}
numberPicker.setDisplayedValues(numbers);
switch(MUID_STATIC) {
// Dialogs_34_BinaryMutator
case 34114: {
numberPicker.setMaxValue(numbers.length + 1);
break;
}
default: {
numberPicker.setMaxValue(numbers.length - 1);
break;
}
}
numberPicker.setMinValue(0);
switch(MUID_STATIC) {
// Dialogs_35_BinaryMutator
case 35114: {
numberPicker.setValue((currentVersionCount / 5) + 1);
break;
}
default: {
switch(MUID_STATIC) {
// Dialogs_36_BinaryMutator
case 36114: {
numberPicker.setValue((currentVersionCount * 5) - 1);
break;
}
default: {
numberPicker.setValue((currentVersionCount / 5) - 1);
break;
}
}
break;
}
}
numberPicker.setWrapSelectorWheel(false);
androidx.appcompat.app.AlertDialog dialog;
switch(MUID_STATIC) {
// Dialogs_37_BuggyGUIListenerOperatorMutator
case 37114: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.set_number).setView(view).setPositiveButton(android.R.string.ok, null).create();
break;
}
default: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.set_number).setView(view).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog1,int which) -> listener.onNumberInputResult(numberPicker.getValue())).create();
break;
}
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


public static void showErrorDialog(android.content.Context context, @androidx.annotation.StringRes
int message, java.lang.Exception e) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(context, message, e, null);
}


public static void showErrorDialog(android.content.Context context, java.lang.String message, java.lang.Exception e) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(context, message, e, null);
}


public static void showErrorDialog(android.content.Context context, @androidx.annotation.StringRes
int message, java.lang.CharSequence error) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(context, message, error, null);
}


public static void showErrorDialog(android.content.Context context, @androidx.annotation.StringRes
int message, java.lang.Exception e, android.content.DialogInterface.OnClickListener listener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(context, message, e.toString(), listener);
}


public static void showErrorDialog(android.content.Context context, java.lang.String message, java.lang.Exception e, android.content.DialogInterface.OnClickListener listener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(context, message, e.toString(), listener);
}


public static void showErrorDialog(android.content.Context context, @androidx.annotation.StringRes
int message, java.lang.CharSequence error, android.content.DialogInterface.OnClickListener listener) {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(context, context.getString(message), error, listener);
}


public static void showErrorDialog(android.content.Context context, java.lang.String message, java.lang.CharSequence error, android.content.DialogInterface.OnClickListener listener) {
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_error, null);
android.widget.TextView textDetails;
switch(MUID_STATIC) {
// Dialogs_38_InvalidViewFocusOperatorMutator
case 38114: {
/**
* Inserted by Kadabra
*/
textDetails = view.findViewById(com.beemdevelopment.aegis.R.id.error_details);
textDetails.requestFocus();
break;
}
// Dialogs_39_ViewComponentNotVisibleOperatorMutator
case 39114: {
/**
* Inserted by Kadabra
*/
textDetails = view.findViewById(com.beemdevelopment.aegis.R.id.error_details);
textDetails.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textDetails = view.findViewById(com.beemdevelopment.aegis.R.id.error_details);
break;
}
}
textDetails.setText(error);
android.widget.TextView textMessage;
switch(MUID_STATIC) {
// Dialogs_40_InvalidViewFocusOperatorMutator
case 40114: {
/**
* Inserted by Kadabra
*/
textMessage = view.findViewById(com.beemdevelopment.aegis.R.id.error_message);
textMessage.requestFocus();
break;
}
// Dialogs_41_ViewComponentNotVisibleOperatorMutator
case 41114: {
/**
* Inserted by Kadabra
*/
textMessage = view.findViewById(com.beemdevelopment.aegis.R.id.error_message);
textMessage.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
textMessage = view.findViewById(com.beemdevelopment.aegis.R.id.error_message);
break;
}
}
textMessage.setText(message);
androidx.appcompat.app.AlertDialog dialog;
switch(MUID_STATIC) {
// Dialogs_42_BuggyGUIListenerOperatorMutator
case 42114: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.error_occurred).setView(view).setCancelable(false).setPositiveButton(android.R.string.ok, null).setNeutralButton(com.beemdevelopment.aegis.R.string.details, (android.content.DialogInterface dialog1,int which) -> {
textDetails.setVisibility(android.view.View.VISIBLE);
}).create();
break;
}
default: {
switch(MUID_STATIC) {
// Dialogs_43_BuggyGUIListenerOperatorMutator
case 43114: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.error_occurred).setView(view).setCancelable(false).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog1,int which) -> {
if (listener != null) {
listener.onClick(dialog1, which);
}
}).setNeutralButton(com.beemdevelopment.aegis.R.string.details, null).create();
break;
}
default: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.error_occurred).setView(view).setCancelable(false).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog1,int which) -> {
if (listener != null) {
listener.onClick(dialog1, which);
}
}).setNeutralButton(com.beemdevelopment.aegis.R.string.details, (android.content.DialogInterface dialog1,int which) -> {
textDetails.setVisibility(android.view.View.VISIBLE);
}).create();
break;
}
}
break;
}
}
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button button;
button = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL);
switch(MUID_STATIC) {
// Dialogs_44_BuggyGUIListenerOperatorMutator
case 44114: {
button.setOnClickListener(null);
break;
}
default: {
button.setOnClickListener((android.view.View v) -> {
if (textDetails.getVisibility() == android.view.View.GONE) {
textDetails.setVisibility(android.view.View.VISIBLE);
button.setText(com.beemdevelopment.aegis.R.string.copy);
} else {
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
if (clipboard != null) {
android.content.ClipData clip;
clip = android.content.ClipData.newPlainText("text/plain", error);
clipboard.setPrimaryClip(clip);
}
}
});
break;
}
}
});
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


public static void showBackupErrorDialog(android.content.Context context, com.beemdevelopment.aegis.Preferences.BackupResult backupRes, android.content.DialogInterface.OnClickListener listener) {
java.lang.String system;
system = context.getString(backupRes.isBuiltIn() ? com.beemdevelopment.aegis.R.string.backup_system_builtin : com.beemdevelopment.aegis.R.string.backup_system_android);
java.lang.String message;
message = context.getString(com.beemdevelopment.aegis.R.string.backup_error_dialog_details, system, backupRes.getElapsedSince(context));
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showErrorDialog(context, message, backupRes.getError(), listener);
}


public static void showMultiMessageDialog(android.content.Context context, @androidx.annotation.StringRes
int title, java.lang.String message, java.util.List<java.lang.CharSequence> messages, android.content.DialogInterface.OnClickListener listener) {
switch(MUID_STATIC) {
// Dialogs_45_BuggyGUIListenerOperatorMutator
case 45114: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(android.R.string.ok, null).setNeutralButton(context.getString(com.beemdevelopment.aegis.R.string.details), (android.content.DialogInterface dialog,int which) -> {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDetailedMultiMessageDialog(context, title, messages, listener);
}).create());
break;
}
default: {
switch(MUID_STATIC) {
// Dialogs_46_BuggyGUIListenerOperatorMutator
case 46114: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
if (listener != null) {
listener.onClick(dialog, which);
}
}).setNeutralButton(context.getString(com.beemdevelopment.aegis.R.string.details), null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(title).setMessage(message).setCancelable(false).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog,int which) -> {
if (listener != null) {
listener.onClick(dialog, which);
}
}).setNeutralButton(context.getString(com.beemdevelopment.aegis.R.string.details), (android.content.DialogInterface dialog,int which) -> {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showDetailedMultiMessageDialog(context, title, messages, listener);
}).create());
break;
}
}
break;
}
}
}


public static <T extends java.lang.Throwable> void showMultiErrorDialog(android.content.Context context, @androidx.annotation.StringRes
int title, java.lang.String message, java.util.List<T> errors, android.content.DialogInterface.OnClickListener listener) {
java.util.List<java.lang.CharSequence> messages;
messages = new java.util.ArrayList<>();
for (java.lang.Throwable e : errors) {
messages.add(e.toString());
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showMultiMessageDialog(context, title, message, messages, listener);
}


private static void showDetailedMultiMessageDialog(android.content.Context context, @androidx.annotation.StringRes
int title, java.util.List<java.lang.CharSequence> messages, android.content.DialogInterface.OnClickListener listener) {
android.text.SpannableStringBuilder builder;
builder = new android.text.SpannableStringBuilder();
for (java.lang.CharSequence message : messages) {
builder.append(message);
builder.append("\n\n");
}
androidx.appcompat.app.AlertDialog dialog;
switch(MUID_STATIC) {
// Dialogs_47_BuggyGUIListenerOperatorMutator
case 47114: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(title).setMessage(builder).setCancelable(false).setPositiveButton(android.R.string.ok, null).setNeutralButton(android.R.string.copy, null).create();
break;
}
default: {
dialog = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(title).setMessage(builder).setCancelable(false).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog1,int which) -> {
if (listener != null) {
listener.onClick(dialog1, which);
}
}).setNeutralButton(android.R.string.copy, null).create();
break;
}
}
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button button;
button = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL);
switch(MUID_STATIC) {
// Dialogs_48_BuggyGUIListenerOperatorMutator
case 48114: {
button.setOnClickListener(null);
break;
}
default: {
button.setOnClickListener((android.view.View v) -> {
android.content.ClipboardManager clipboard;
clipboard = ((android.content.ClipboardManager) (context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)));
android.content.ClipData clip;
clip = android.content.ClipData.newPlainText("text/plain", builder.toString());
clipboard.setPrimaryClip(clip);
android.widget.Toast.makeText(context, com.beemdevelopment.aegis.R.string.errors_copied, android.widget.Toast.LENGTH_SHORT).show();
});
break;
}
}
});
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


public static void showTimeSyncWarningDialog(android.content.Context context, android.content.DialogInterface.OnClickListener listener) {
com.beemdevelopment.aegis.Preferences prefs;
prefs = new com.beemdevelopment.aegis.Preferences(context);
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_time_sync, null);
android.widget.CheckBox checkBox;
switch(MUID_STATIC) {
// Dialogs_49_InvalidViewFocusOperatorMutator
case 49114: {
/**
* Inserted by Kadabra
*/
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.check_warning_disable);
checkBox.requestFocus();
break;
}
// Dialogs_50_ViewComponentNotVisibleOperatorMutator
case 50114: {
/**
* Inserted by Kadabra
*/
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.check_warning_disable);
checkBox.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
checkBox = view.findViewById(com.beemdevelopment.aegis.R.id.check_warning_disable);
break;
}
}
switch(MUID_STATIC) {
// Dialogs_51_BuggyGUIListenerOperatorMutator
case 51114: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.time_sync_warning_title).setView(view).setCancelable(false).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, null).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog,int which) -> {
if (checkBox.isChecked()) {
prefs.setIsTimeSyncWarningEnabled(false);
}
}).create());
break;
}
default: {
switch(MUID_STATIC) {
// Dialogs_52_BuggyGUIListenerOperatorMutator
case 52114: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.time_sync_warning_title).setView(view).setCancelable(false).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog,int which) -> {
if (checkBox.isChecked()) {
prefs.setIsTimeSyncWarningEnabled(false);
}
if (listener != null) {
listener.onClick(dialog, which);
}
}).setNegativeButton(com.beemdevelopment.aegis.R.string.no, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.time_sync_warning_title).setView(view).setCancelable(false).setPositiveButton(com.beemdevelopment.aegis.R.string.yes, (android.content.DialogInterface dialog,int which) -> {
if (checkBox.isChecked()) {
prefs.setIsTimeSyncWarningEnabled(false);
}
if (listener != null) {
listener.onClick(dialog, which);
}
}).setNegativeButton(com.beemdevelopment.aegis.R.string.no, (android.content.DialogInterface dialog,int which) -> {
if (checkBox.isChecked()) {
prefs.setIsTimeSyncWarningEnabled(false);
}
}).create());
break;
}
}
break;
}
}
}


public static void showImportersDialog(android.content.Context context, boolean isDirect, com.beemdevelopment.aegis.ui.dialogs.Dialogs.ImporterListener listener) {
java.util.List<com.beemdevelopment.aegis.importers.DatabaseImporter.Definition> importers;
importers = com.beemdevelopment.aegis.importers.DatabaseImporter.getImporters(isDirect);
java.util.List<java.lang.String> names;
names = importers.stream().map(com.beemdevelopment.aegis.importers.DatabaseImporter.Definition::getName).collect(java.util.stream.Collectors.toList());
int i;
i = 0;
if (!isDirect) {
i = names.indexOf(context.getString(com.beemdevelopment.aegis.R.string.app_name));
}
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_importers, null);
android.widget.TextView helpText;
switch(MUID_STATIC) {
// Dialogs_53_InvalidViewFocusOperatorMutator
case 53114: {
/**
* Inserted by Kadabra
*/
helpText = view.findViewById(com.beemdevelopment.aegis.R.id.text_importer_help);
helpText.requestFocus();
break;
}
// Dialogs_54_ViewComponentNotVisibleOperatorMutator
case 54114: {
/**
* Inserted by Kadabra
*/
helpText = view.findViewById(com.beemdevelopment.aegis.R.id.text_importer_help);
helpText.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
helpText = view.findViewById(com.beemdevelopment.aegis.R.id.text_importer_help);
break;
}
}
com.beemdevelopment.aegis.ui.dialogs.Dialogs.setImporterHelpText(helpText, importers.get(i), isDirect);
android.widget.ListView listView;
switch(MUID_STATIC) {
// Dialogs_55_InvalidViewFocusOperatorMutator
case 55114: {
/**
* Inserted by Kadabra
*/
listView = view.findViewById(com.beemdevelopment.aegis.R.id.list_importers);
listView.requestFocus();
break;
}
// Dialogs_56_ViewComponentNotVisibleOperatorMutator
case 56114: {
/**
* Inserted by Kadabra
*/
listView = view.findViewById(com.beemdevelopment.aegis.R.id.list_importers);
listView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
listView = view.findViewById(com.beemdevelopment.aegis.R.id.list_importers);
break;
}
}
listView.setAdapter(new android.widget.ArrayAdapter<>(context, com.beemdevelopment.aegis.R.layout.card_importer, names));
listView.setItemChecked(i, true);
listView.setOnItemClickListener((android.widget.AdapterView<?> parent,android.view.View view1,int position,long id) -> {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.setImporterHelpText(helpText, importers.get(position), isDirect);
});
switch(MUID_STATIC) {
// Dialogs_57_BuggyGUIListenerOperatorMutator
case 57114: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.choose_application).setView(view).setPositiveButton(android.R.string.ok, null).create());
break;
}
default: {
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.choose_application).setView(view).setPositiveButton(android.R.string.ok, (android.content.DialogInterface dialog1,int which) -> {
listener.onImporterSelectionResult(importers.get(listView.getCheckedItemPosition()));
}).create());
break;
}
}
}


public static void showPartialGoogleAuthImportWarningDialog(android.content.Context context, java.util.List<java.lang.Integer> missingIndexes, int entries, java.util.List<java.lang.CharSequence> scanningErrors, android.content.DialogInterface.OnClickListener dismissHandler) {
java.lang.String missingIndexesAsString;
switch(MUID_STATIC) {
// Dialogs_58_BinaryMutator
case 58114: {
missingIndexesAsString = missingIndexes.stream().map((java.lang.Integer index) -> context.getString(com.beemdevelopment.aegis.R.string.missing_qr_code_descriptor, index - 1)).collect(java.util.stream.Collectors.joining("\n"));
break;
}
default: {
missingIndexesAsString = missingIndexes.stream().map((java.lang.Integer index) -> context.getString(com.beemdevelopment.aegis.R.string.missing_qr_code_descriptor, index + 1)).collect(java.util.stream.Collectors.joining("\n"));
break;
}
}
android.view.View view;
view = android.view.LayoutInflater.from(context).inflate(com.beemdevelopment.aegis.R.layout.dialog_error, null);
android.widget.TextView errorDetails;
switch(MUID_STATIC) {
// Dialogs_59_InvalidViewFocusOperatorMutator
case 59114: {
/**
* Inserted by Kadabra
*/
errorDetails = view.findViewById(com.beemdevelopment.aegis.R.id.error_details);
errorDetails.requestFocus();
break;
}
// Dialogs_60_ViewComponentNotVisibleOperatorMutator
case 60114: {
/**
* Inserted by Kadabra
*/
errorDetails = view.findViewById(com.beemdevelopment.aegis.R.id.error_details);
errorDetails.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
errorDetails = view.findViewById(com.beemdevelopment.aegis.R.id.error_details);
break;
}
}
for (java.lang.CharSequence error : scanningErrors) {
errorDetails.append(error);
errorDetails.append("\n\n");
}
androidx.appcompat.app.AlertDialog.Builder builder;
switch(MUID_STATIC) {
// Dialogs_61_BuggyGUIListenerOperatorMutator
case 61114: {
builder = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.partial_google_auth_import).setMessage(context.getString(com.beemdevelopment.aegis.R.string.partial_google_auth_import_warning, missingIndexesAsString)).setView(view).setCancelable(false).setPositiveButton(context.getString(com.beemdevelopment.aegis.R.string.import_partial_export_anyway, entries), null).setNegativeButton(android.R.string.cancel, null);
break;
}
default: {
builder = new androidx.appcompat.app.AlertDialog.Builder(context).setTitle(com.beemdevelopment.aegis.R.string.partial_google_auth_import).setMessage(context.getString(com.beemdevelopment.aegis.R.string.partial_google_auth_import_warning, missingIndexesAsString)).setView(view).setCancelable(false).setPositiveButton(context.getString(com.beemdevelopment.aegis.R.string.import_partial_export_anyway, entries), (android.content.DialogInterface dialog,int which) -> {
dismissHandler.onClick(dialog, which);
}).setNegativeButton(android.R.string.cancel, null);
break;
}
}
if (scanningErrors.size() > 0) {
builder.setNeutralButton(com.beemdevelopment.aegis.R.string.show_error_details, null);
}
androidx.appcompat.app.AlertDialog dialog;
dialog = builder.create();
dialog.setOnShowListener((android.content.DialogInterface d) -> {
android.widget.Button btnNeutral;
btnNeutral = dialog.getButton(android.content.DialogInterface.BUTTON_NEUTRAL);
if (btnNeutral != null) {
switch(MUID_STATIC) {
// Dialogs_62_BuggyGUIListenerOperatorMutator
case 62114: {
btnNeutral.setOnClickListener(null);
break;
}
default: {
btnNeutral.setOnClickListener((android.view.View b) -> {
errorDetails.setVisibility(android.view.View.VISIBLE);
btnNeutral.setVisibility(android.view.View.GONE);
});
break;
}
}
}
});
com.beemdevelopment.aegis.ui.dialogs.Dialogs.showSecureDialog(dialog);
}


private static void setImporterHelpText(android.widget.TextView view, com.beemdevelopment.aegis.importers.DatabaseImporter.Definition definition, boolean isDirect) {
if (isDirect) {
view.setText(view.getResources().getString(com.beemdevelopment.aegis.R.string.importer_help_direct, definition.getName()));
} else {
view.setText(definition.getHelp());
}
}


public interface CheckboxInputListener {
void onCheckboxInputResult(boolean checkbox);

}

public interface NumberInputListener {
void onNumberInputResult(int number);

}

public interface TextInputListener {
void onTextInputResult(char[] text);

}

public interface PasswordSlotListener {
void onSlotResult(com.beemdevelopment.aegis.vault.slots.PasswordSlot slot, javax.crypto.Cipher cipher);


void onException(java.lang.Exception e);

}

public interface ImporterListener {
void onImporterSelectionResult(com.beemdevelopment.aegis.importers.DatabaseImporter.Definition definition);

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
