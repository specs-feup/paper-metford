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
package it.feio.android.omninotes;
import rx.android.schedulers.AndroidSchedulers;
import it.feio.android.omninotes.async.bus.PasswordRemovedEvent;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.LifecycleCallback;
import android.os.Bundle;
import android.view.ViewGroup;
import rx.schedulers.Schedulers;
import rx.Observable;
import de.greenrobot.event.EventBus;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_ANSWER;
import it.feio.android.omninotes.utils.PasswordHelper;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.utils.Security;
import android.util.DisplayMetrics;
import android.widget.EditText;
import it.feio.android.omninotes.models.PasswordValidator;
import android.annotation.SuppressLint;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_QUESTION;
import com.afollestad.materialdialogs.MaterialDialog;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.models.ONStyle;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordActivity extends it.feio.android.omninotes.BaseActivity {
    static final int MUID_STATIC = getMUID();
    private android.view.ViewGroup croutonHandle;

    private android.widget.EditText passwordCheck;

    private android.widget.EditText password;

    private android.widget.EditText question;

    private android.widget.EditText answer;

    private android.widget.EditText answerCheck;

    private it.feio.android.omninotes.PasswordActivity mActivity;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PasswordActivity_0_LengthyGUICreationOperatorMutator
            case 142: {
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
    android.util.DisplayMetrics metrics;
    metrics = getResources().getDisplayMetrics();
    int screenWidth;
    switch(MUID_STATIC) {
        // PasswordActivity_1_BinaryMutator
        case 1142: {
            screenWidth = ((int) (metrics.widthPixels / 0.8));
            break;
        }
        default: {
        screenWidth = ((int) (metrics.widthPixels * 0.8));
        break;
    }
}
int screenHeight;
switch(MUID_STATIC) {
    // PasswordActivity_2_BinaryMutator
    case 2142: {
        screenHeight = ((int) (metrics.heightPixels / 0.8));
        break;
    }
    default: {
    screenHeight = ((int) (metrics.heightPixels * 0.8));
    break;
}
}
setContentView(it.feio.android.omninotes.R.layout.activity_password);
getWindow().setLayout(screenWidth, screenHeight);
mActivity = this;
setActionBarTitle(getString(it.feio.android.omninotes.R.string.title_activity_password));
initViews();
}


@java.lang.Override
protected void onStart() {
super.onStart();
de.greenrobot.event.EventBus.getDefault().register(this, 1);
}


@java.lang.Override
public void onStop() {
super.onStop();
de.greenrobot.event.EventBus.getDefault().unregister(this);
}


private void initViews() {
switch(MUID_STATIC) {
// PasswordActivity_3_FindViewByIdReturnsNullOperatorMutator
case 3142: {
    croutonHandle = null;
    break;
}
// PasswordActivity_4_InvalidIDFindViewOperatorMutator
case 4142: {
    croutonHandle = findViewById(732221);
    break;
}
// PasswordActivity_5_InvalidViewFocusOperatorMutator
case 5142: {
    /**
    * Inserted by Kadabra
    */
    croutonHandle = findViewById(it.feio.android.omninotes.R.id.crouton_handle);
    croutonHandle.requestFocus();
    break;
}
// PasswordActivity_6_ViewComponentNotVisibleOperatorMutator
case 6142: {
    /**
    * Inserted by Kadabra
    */
    croutonHandle = findViewById(it.feio.android.omninotes.R.id.crouton_handle);
    croutonHandle.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
croutonHandle = findViewById(it.feio.android.omninotes.R.id.crouton_handle);
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_7_FindViewByIdReturnsNullOperatorMutator
case 7142: {
password = null;
break;
}
// PasswordActivity_8_InvalidIDFindViewOperatorMutator
case 8142: {
password = findViewById(732221);
break;
}
// PasswordActivity_9_InvalidViewFocusOperatorMutator
case 9142: {
/**
* Inserted by Kadabra
*/
password = findViewById(it.feio.android.omninotes.R.id.password);
password.requestFocus();
break;
}
// PasswordActivity_10_ViewComponentNotVisibleOperatorMutator
case 10142: {
/**
* Inserted by Kadabra
*/
password = findViewById(it.feio.android.omninotes.R.id.password);
password.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
password = findViewById(it.feio.android.omninotes.R.id.password);
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_11_FindViewByIdReturnsNullOperatorMutator
case 11142: {
passwordCheck = null;
break;
}
// PasswordActivity_12_InvalidIDFindViewOperatorMutator
case 12142: {
passwordCheck = findViewById(732221);
break;
}
// PasswordActivity_13_InvalidViewFocusOperatorMutator
case 13142: {
/**
* Inserted by Kadabra
*/
passwordCheck = findViewById(it.feio.android.omninotes.R.id.password_check);
passwordCheck.requestFocus();
break;
}
// PasswordActivity_14_ViewComponentNotVisibleOperatorMutator
case 14142: {
/**
* Inserted by Kadabra
*/
passwordCheck = findViewById(it.feio.android.omninotes.R.id.password_check);
passwordCheck.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
passwordCheck = findViewById(it.feio.android.omninotes.R.id.password_check);
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_15_FindViewByIdReturnsNullOperatorMutator
case 15142: {
question = null;
break;
}
// PasswordActivity_16_InvalidIDFindViewOperatorMutator
case 16142: {
question = findViewById(732221);
break;
}
// PasswordActivity_17_InvalidViewFocusOperatorMutator
case 17142: {
/**
* Inserted by Kadabra
*/
question = findViewById(it.feio.android.omninotes.R.id.question);
question.requestFocus();
break;
}
// PasswordActivity_18_ViewComponentNotVisibleOperatorMutator
case 18142: {
/**
* Inserted by Kadabra
*/
question = findViewById(it.feio.android.omninotes.R.id.question);
question.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
question = findViewById(it.feio.android.omninotes.R.id.question);
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_19_FindViewByIdReturnsNullOperatorMutator
case 19142: {
answer = null;
break;
}
// PasswordActivity_20_InvalidIDFindViewOperatorMutator
case 20142: {
answer = findViewById(732221);
break;
}
// PasswordActivity_21_InvalidViewFocusOperatorMutator
case 21142: {
/**
* Inserted by Kadabra
*/
answer = findViewById(it.feio.android.omninotes.R.id.answer);
answer.requestFocus();
break;
}
// PasswordActivity_22_ViewComponentNotVisibleOperatorMutator
case 22142: {
/**
* Inserted by Kadabra
*/
answer = findViewById(it.feio.android.omninotes.R.id.answer);
answer.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
answer = findViewById(it.feio.android.omninotes.R.id.answer);
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_23_FindViewByIdReturnsNullOperatorMutator
case 23142: {
answerCheck = null;
break;
}
// PasswordActivity_24_InvalidIDFindViewOperatorMutator
case 24142: {
answerCheck = findViewById(732221);
break;
}
// PasswordActivity_25_InvalidViewFocusOperatorMutator
case 25142: {
/**
* Inserted by Kadabra
*/
answerCheck = findViewById(it.feio.android.omninotes.R.id.answer_check);
answerCheck.requestFocus();
break;
}
// PasswordActivity_26_ViewComponentNotVisibleOperatorMutator
case 26142: {
/**
* Inserted by Kadabra
*/
answerCheck = findViewById(it.feio.android.omninotes.R.id.answer_check);
answerCheck.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
answerCheck = findViewById(it.feio.android.omninotes.R.id.answer_check);
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_27_InvalidIDFindViewOperatorMutator
case 27142: {
findViewById(732221).setOnClickListener((android.view.View v) -> {
if (com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, null) != null) {
it.feio.android.omninotes.utils.PasswordHelper.requestPassword(mActivity, (it.feio.android.omninotes.models.PasswordValidator.Result passwordConfirmed) -> {
if (passwordConfirmed.equals(it.feio.android.omninotes.models.PasswordValidator.Result.SUCCEED)) {
updatePassword(null, null, null);
}
});
} else {
de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.password_not_set, it.feio.android.omninotes.models.ONStyle.WARN, croutonHandle).show();
}
});
break;
}
default: {
switch(MUID_STATIC) {
// PasswordActivity_28_BuggyGUIListenerOperatorMutator
case 28142: {
findViewById(it.feio.android.omninotes.R.id.password_remove).setOnClickListener(null);
break;
}
default: {
findViewById(it.feio.android.omninotes.R.id.password_remove).setOnClickListener((android.view.View v) -> {
if (com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, null) != null) {
it.feio.android.omninotes.utils.PasswordHelper.requestPassword(mActivity, (it.feio.android.omninotes.models.PasswordValidator.Result passwordConfirmed) -> {
if (passwordConfirmed.equals(it.feio.android.omninotes.models.PasswordValidator.Result.SUCCEED)) {
updatePassword(null, null, null);
}
});
} else {
de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.password_not_set, it.feio.android.omninotes.models.ONStyle.WARN, croutonHandle).show();
}
});
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_29_InvalidIDFindViewOperatorMutator
case 29142: {
findViewById(732221).setOnClickListener((android.view.View v) -> {
if (checkData()) {
final java.lang.String passwordText;
passwordText = password.getText().toString();
final java.lang.String questionText;
questionText = question.getText().toString();
final java.lang.String answerText;
answerText = answer.getText().toString();
if (com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, null) != null) {
it.feio.android.omninotes.utils.PasswordHelper.requestPassword(mActivity, (it.feio.android.omninotes.models.PasswordValidator.Result passwordConfirmed) -> {
if (passwordConfirmed.equals(it.feio.android.omninotes.models.PasswordValidator.Result.SUCCEED)) {
updatePassword(passwordText, questionText, answerText);
}
});
} else {
updatePassword(passwordText, questionText, answerText);
}
}
});
break;
}
default: {
switch(MUID_STATIC) {
// PasswordActivity_30_BuggyGUIListenerOperatorMutator
case 30142: {
findViewById(it.feio.android.omninotes.R.id.password_confirm).setOnClickListener(null);
break;
}
default: {
findViewById(it.feio.android.omninotes.R.id.password_confirm).setOnClickListener((android.view.View v) -> {
if (checkData()) {
final java.lang.String passwordText;
passwordText = password.getText().toString();
final java.lang.String questionText;
questionText = question.getText().toString();
final java.lang.String answerText;
answerText = answer.getText().toString();
if (com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, null) != null) {
it.feio.android.omninotes.utils.PasswordHelper.requestPassword(mActivity, (it.feio.android.omninotes.models.PasswordValidator.Result passwordConfirmed) -> {
if (passwordConfirmed.equals(it.feio.android.omninotes.models.PasswordValidator.Result.SUCCEED)) {
updatePassword(passwordText, questionText, answerText);
}
});
} else {
updatePassword(passwordText, questionText, answerText);
}
}
});
break;
}
}
break;
}
}
switch(MUID_STATIC) {
// PasswordActivity_31_InvalidIDFindViewOperatorMutator
case 31142: {
findViewById(732221).setOnClickListener((android.view.View v) -> {
if (com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "").length() == 0) {
de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.password_not_set, it.feio.android.omninotes.models.ONStyle.WARN, croutonHandle).show();
return;
}
it.feio.android.omninotes.utils.PasswordHelper.resetPassword(this);
});
break;
}
default: {
switch(MUID_STATIC) {
// PasswordActivity_32_BuggyGUIListenerOperatorMutator
case 32142: {
findViewById(it.feio.android.omninotes.R.id.password_forgotten).setOnClickListener(null);
break;
}
default: {
findViewById(it.feio.android.omninotes.R.id.password_forgotten).setOnClickListener((android.view.View v) -> {
if (com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "").length() == 0) {
de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.password_not_set, it.feio.android.omninotes.models.ONStyle.WARN, croutonHandle).show();
return;
}
it.feio.android.omninotes.utils.PasswordHelper.resetPassword(this);
});
break;
}
}
break;
}
}
}


public void onEvent(it.feio.android.omninotes.async.bus.PasswordRemovedEvent passwordRemovedEvent) {
passwordCheck.setText("");
password.setText("");
question.setText("");
answer.setText("");
answerCheck.setText("");
de.keyboardsurfer.android.widget.crouton.Crouton crouton;
crouton = de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.password_successfully_removed, it.feio.android.omninotes.models.ONStyle.ALERT, croutonHandle);
crouton.setLifecycleCallback(new de.keyboardsurfer.android.widget.crouton.LifecycleCallback() {
@java.lang.Override
public void onDisplayed() {
// Does nothing!
}


@java.lang.Override
public void onRemoved() {
onBackPressed();
}

});
crouton.show();
}


@android.annotation.SuppressLint("CommitPrefEdits")
private void updatePassword(java.lang.String passwordText, java.lang.String questionText, java.lang.String answerText) {
if (passwordText == null) {
if (com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, "").length() == 0) {
de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.password_not_set, it.feio.android.omninotes.models.ONStyle.WARN, croutonHandle).show();
return;
}
new com.afollestad.materialdialogs.MaterialDialog.Builder(mActivity).content(it.feio.android.omninotes.R.string.agree_unlocking_all_notes).positiveText(it.feio.android.omninotes.R.string.ok).onPositive((com.afollestad.materialdialogs.MaterialDialog dialog,com.afollestad.materialdialogs.DialogAction which) -> it.feio.android.omninotes.utils.PasswordHelper.removePassword()).build().show();
} else if (passwordText.length() == 0) {
de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.empty_password, it.feio.android.omninotes.models.ONStyle.WARN, croutonHandle).show();
} else {
rx.Observable.from(it.feio.android.omninotes.db.DbHelper.getInstance().getNotesWithLock(true)).subscribeOn(rx.schedulers.Schedulers.newThread()).observeOn(rx.android.schedulers.AndroidSchedulers.mainThread()).doOnSubscribe(() -> com.pixplicity.easyprefs.library.Prefs.edit().putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD, it.feio.android.omninotes.utils.Security.md5(passwordText)).putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_QUESTION, questionText).putString(it.feio.android.omninotes.utils.ConstantsBase.PREF_PASSWORD_ANSWER, it.feio.android.omninotes.utils.Security.md5(answerText)).apply()).doOnNext((it.feio.android.omninotes.models.Note note) -> it.feio.android.omninotes.db.DbHelper.getInstance().updateNote(note, false)).doOnCompleted(() -> {
de.keyboardsurfer.android.widget.crouton.Crouton crouton;
crouton = de.keyboardsurfer.android.widget.crouton.Crouton.makeText(mActivity, it.feio.android.omninotes.R.string.password_successfully_changed, it.feio.android.omninotes.models.ONStyle.CONFIRM, croutonHandle);
crouton.setLifecycleCallback(new de.keyboardsurfer.android.widget.crouton.LifecycleCallback() {
@java.lang.Override
public void onDisplayed() {
// Does nothing!
}


@java.lang.Override
public void onRemoved() {
onBackPressed();
}

});
crouton.show();
}).subscribe();
}
}


/**
 * Checks correctness of form data
 */
private boolean checkData() {
boolean res;
res = true;
if ((password.getText().length() == passwordCheck.getText().length()) && (passwordCheck.getText().length() == 0)) {
return true;
}
boolean passwordOk;
passwordOk = password.getText().toString().length() > 0;
boolean passwordCheckOk;
passwordCheckOk = (passwordCheck.getText().toString().length() > 0) && password.getText().toString().equals(passwordCheck.getText().toString());
boolean questionOk;
questionOk = question.getText().toString().length() > 0;
boolean answerOk;
answerOk = answer.getText().toString().length() > 0;
boolean answerCheckOk;
answerCheckOk = (answerCheck.getText().toString().length() > 0) && answer.getText().toString().equals(answerCheck.getText().toString());
if (((((!passwordOk) || (!passwordCheckOk)) || (!questionOk)) || (!answerOk)) || (!answerCheckOk)) {
res = false;
if (!passwordOk) {
password.setError(getString(it.feio.android.omninotes.R.string.settings_password_not_matching));
}
if (!passwordCheckOk) {
passwordCheck.setError(getString(it.feio.android.omninotes.R.string.settings_password_not_matching));
}
if (!questionOk) {
question.setError(getString(it.feio.android.omninotes.R.string.settings_password_question));
}
if (!answerOk) {
answer.setError(getString(it.feio.android.omninotes.R.string.settings_answer_not_matching));
}
if (!answerCheckOk) {
answerCheck.setError(getString(it.feio.android.omninotes.R.string.settings_answer_not_matching));
}
}
return res;
}


@java.lang.Override
public void onBackPressed() {
setResult(android.app.Activity.RESULT_OK);
finish();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
