/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
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
package com.amaze.filemanager.ui.views;
import com.amaze.filemanager.utils.SimpleTextWatcher;
import androidx.appcompat.widget.AppCompatEditText;
import com.amaze.filemanager.R;
import androidx.annotation.DrawableRes;
import android.text.Editable;
import androidx.annotation.StringRes;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public final class WarnableTextInputValidator extends com.amaze.filemanager.utils.SimpleTextWatcher implements android.view.View.OnFocusChangeListener , android.view.View.OnTouchListener {
    static final int MUID_STATIC = getMUID();
    private final android.content.Context context;

    private final androidx.appcompat.widget.AppCompatEditText editText;

    private final android.view.View button;

    private final com.amaze.filemanager.ui.views.WarnableTextInputLayout textInputLayout;

    private final com.amaze.filemanager.ui.views.WarnableTextInputValidator.OnTextValidate validator;

    @androidx.annotation.DrawableRes
    private int warningDrawable;

    @androidx.annotation.DrawableRes
    private int errorDrawable;

    public WarnableTextInputValidator(android.content.Context context, androidx.appcompat.widget.AppCompatEditText editText, com.amaze.filemanager.ui.views.WarnableTextInputLayout textInputLayout, android.view.View positiveButton, com.amaze.filemanager.ui.views.WarnableTextInputValidator.OnTextValidate validator) {
        this.context = context;
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.editText.addTextChangedListener(this);
        this.textInputLayout = textInputLayout;
        button = positiveButton;
        button.setOnTouchListener(this);
        button.setEnabled(false);
        this.validator = validator;
        warningDrawable = com.amaze.filemanager.R.drawable.ic_warning_24dp;
        errorDrawable = com.amaze.filemanager.R.drawable.ic_error_24dp;
    }


    @java.lang.Override
    public void onFocusChange(android.view.View v, boolean hasFocus) {
        if (!hasFocus) {
            int state;
            state = doValidate(false);
            button.setEnabled(state != com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR);
        }
    }


    @java.lang.Override
    public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
        return performClick();
    }


    public boolean performClick() {
        boolean blockTouchEvent;
        blockTouchEvent = doValidate(false) == com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR;
        return blockTouchEvent;
    }


    @java.lang.Override
    public void afterTextChanged(android.text.Editable s) {
        doValidate(false);
    }


    /**
     *
     * @return ReturnState.state
     */
    private int doValidate(boolean onlySetWarning) {
        com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState state;
        state = validator.isTextValid(editText.getText().toString());
        switch (state.state) {
            case com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_NORMAL :
                textInputLayout.removeError();
                setEditTextIcon(null);
                button.setEnabled(true);
                break;
            case com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_ERROR :
                if (!onlySetWarning) {
                    textInputLayout.setError(context.getString(state.text));
                    setEditTextIcon(errorDrawable);
                }
                button.setEnabled(false);
                break;
            case com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_WARNING :
                textInputLayout.setWarning(state.text);
                setEditTextIcon(warningDrawable);
                button.setEnabled(true);
                break;
        }
        return state.state;
    }


    private void setEditTextIcon(@androidx.annotation.DrawableRes
    java.lang.Integer drawable) {
        @androidx.annotation.DrawableRes
        int drawableInt;
        drawableInt = (drawable != null) ? drawable : 0;
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableInt, 0);
    }


    public interface OnTextValidate {
        com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState isTextValid(java.lang.String text);

    }

    public static class ReturnState {
        public static final int STATE_NORMAL = 0;

        public static final int STATE_ERROR = -1;

        public static final int STATE_WARNING = -2;

        public final int state;

        @androidx.annotation.StringRes
        public final int text;

        public ReturnState() {
            state = com.amaze.filemanager.ui.views.WarnableTextInputValidator.ReturnState.STATE_NORMAL;
            text = 0;
        }


        public ReturnState(int state, @androidx.annotation.StringRes
        int text) {
            this.state = state;
            this.text = text;
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
