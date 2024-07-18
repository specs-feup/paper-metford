/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.feio.android.omninotes.utils;
import rx.android.schedulers.AndroidSchedulers;
import it.feio.android.omninotes.async.bus.PasswordRemovedEvent;
import rx.schedulers.Schedulers;
import android.os.Handler;
import rx.Observable;
import de.greenrobot.event.EventBus;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_ANSWER;
import lombok.experimental.UtilityClass;
import com.pixplicity.easyprefs.library.Prefs;
import android.view.View;
import android.widget.EditText;
import it.feio.android.omninotes.R;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.app.Activity;
import com.afollestad.materialdialogs.DialogAction;
import it.feio.android.omninotes.models.PasswordValidator;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_QUESTION;
import com.afollestad.materialdialogs.MaterialDialog;
import it.feio.android.omninotes.db.DbHelper;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@lombok.experimental.UtilityClass
public class PasswordHelper {
    static final int MUID_STATIC = getMUID();
    public static void requestPassword(final android.app.Activity mActivity, final it.feio.android.omninotes.models.PasswordValidator mPasswordValidator) {
        android.view.LayoutInflater inflater;
        inflater = mActivity.getLayoutInflater();
        final android.view.View v;
        v = inflater.inflate(it.feio.android.omninotes.R.layout.password_request_dialog_layout, null);
        final android.widget.EditText passwordEditText;
        switch(MUID_STATIC) {
            // PasswordHelper_0_InvalidViewFocusOperatorMutator
            case 89: {
                /**
                * Inserted by Kadabra
                */
                passwordEditText = v.findViewById(it.feio.android.omninotes.R.id.password_request);
                passwordEditText.requestFocus();
                break;
            }
            // PasswordHelper_1_ViewComponentNotVisibleOperatorMutator
            case 1089: {
                /**
                * Inserted by Kadabra
                */
                passwordEditText = v.findViewById(it.feio.android.omninotes.R.id.password_request);
                passwordEditText.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            passwordEditText = v.findViewById(it.feio.android.omninotes.R.id.password_request);
            break;
        }
    }
    com.afollestad.materialdialogs.MaterialDialog dialog;
    dialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(mActivity).autoDismiss(false).title(it.feio.android.omninotes.R.string.insert_security_password).customView(v, false).positiveText(it.feio.android.omninotes.R.string.ok).positiveColorRes(it.feio.android.omninotes.R.color.colorPrimary).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog12,com.afollestad.materialdialogs.DialogAction which) -> {
        java.lang.String storedPassword;
        storedPassword = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "");
        java.lang.String password;
        password = passwordEditText.getText().toString();
        boolean result;
        result = it.feio.android.omninotes.utils.Security.md5(password).equals(storedPassword);
        // In case password is ok dialog is dismissed and result sent to callback
        if (result) {
            it.feio.android.omninotes.utils.KeyboardUtils.hideKeyboard(passwordEditText);
            dialog12.dismiss();
            mPasswordValidator.onPasswordValidated(it.feio.android.omninotes.models.PasswordValidator.Result.SUCCEED);
            // If password is wrong the auth flow is not interrupted and simply a message is shown
        } else {
            passwordEditText.setError(mActivity.getString(it.feio.android.omninotes.R.string.wrong_password));
        }
    }).neutralText(mActivity.getResources().getString(it.feio.android.omninotes.R.string.password_forgot)).onNeutral((com.afollestad.materialdialogs.MaterialDialog dialog13,com.afollestad.materialdialogs.DialogAction which) -> {
        it.feio.android.omninotes.utils.PasswordHelper.resetPassword(mActivity);
        mPasswordValidator.onPasswordValidated(it.feio.android.omninotes.models.PasswordValidator.Result.RESTORE);
        dialog13.dismiss();
    }).build();
    dialog.setOnCancelListener((android.content.DialogInterface dialog1) -> {
        it.feio.android.omninotes.utils.KeyboardUtils.hideKeyboard(passwordEditText);
        dialog1.dismiss();
        mPasswordValidator.onPasswordValidated(it.feio.android.omninotes.models.PasswordValidator.Result.FAIL);
    });
    passwordEditText.setOnEditorActionListener((android.widget.TextView textView,int actionId,android.view.KeyEvent keyEvent) -> {
        if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
            dialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).callOnClick();
            return true;
        }
        return false;
    });
    dialog.show();
    new android.os.Handler().postDelayed(() -> it.feio.android.omninotes.utils.KeyboardUtils.showKeyboard(passwordEditText), 100);
}


public static void resetPassword(final android.app.Activity mActivity) {
    android.view.View layout;
    layout = mActivity.getLayoutInflater().inflate(it.feio.android.omninotes.R.layout.password_reset_dialog_layout, null);
    final android.widget.EditText answerEditText;
    switch(MUID_STATIC) {
        // PasswordHelper_2_InvalidViewFocusOperatorMutator
        case 2089: {
            /**
            * Inserted by Kadabra
            */
            answerEditText = layout.findViewById(it.feio.android.omninotes.R.id.reset_password_answer);
            answerEditText.requestFocus();
            break;
        }
        // PasswordHelper_3_ViewComponentNotVisibleOperatorMutator
        case 3089: {
            /**
            * Inserted by Kadabra
            */
            answerEditText = layout.findViewById(it.feio.android.omninotes.R.id.reset_password_answer);
            answerEditText.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        answerEditText = layout.findViewById(it.feio.android.omninotes.R.id.reset_password_answer);
        break;
    }
}
com.afollestad.materialdialogs.MaterialDialog dialog;
dialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(mActivity).title(com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_QUESTION, "")).customView(layout, false).autoDismiss(false).contentColorRes(it.feio.android.omninotes.R.color.text_color).positiveText(it.feio.android.omninotes.R.string.ok).onPositive((com.afollestad.materialdialogs.MaterialDialog dialogElement,com.afollestad.materialdialogs.DialogAction which) -> {
    // When positive button is pressed answer correctness is checked
    java.lang.String oldAnswer;
    oldAnswer = com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_ANSWER, "");
    java.lang.String answer1;
    answer1 = answerEditText.getText().toString();
    // The check is done on password's hash stored in preferences
    boolean result;
    result = it.feio.android.omninotes.utils.Security.md5(answer1).equals(oldAnswer);
    if (result) {
        dialogElement.dismiss();
        it.feio.android.omninotes.utils.PasswordHelper.removePassword();
    } else {
        answerEditText.setError(mActivity.getString(it.feio.android.omninotes.R.string.wrong_answer));
    }
}).build();
dialog.show();
answerEditText.setOnEditorActionListener((android.widget.TextView textView,int actionId,android.view.KeyEvent keyEvent) -> {
    if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
        dialog.getActionButton(com.afollestad.materialdialogs.DialogAction.POSITIVE).callOnClick();
        return true;
    }
    return false;
});
new android.os.Handler().postDelayed(() -> it.feio.android.omninotes.utils.KeyboardUtils.showKeyboard(answerEditText), 100);
}


public static void removePassword() {
rx.Observable.from(it.feio.android.omninotes.db.DbHelper.getInstance().getNotesWithLock(true)).subscribeOn(rx.schedulers.Schedulers.newThread()).observeOn(rx.android.schedulers.AndroidSchedulers.mainThread()).doOnNext((it.feio.android.omninotes.models.Note note) -> {
    note.setLocked(false);
    it.feio.android.omninotes.db.DbHelper.getInstance().updateNote(note, false);
}).doOnCompleted(() -> {
    com.pixplicity.easyprefs.library.Prefs.edit().remove(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD).remove(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_QUESTION).remove(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_ANSWER).remove("settings_password_access").apply();
    de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.PasswordRemovedEvent());
}).subscribe();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
