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
package it.feio.android.omninotes.models;
import java.util.Locale;
import it.feio.android.omninotes.R;
import android.widget.Button;
import android.os.Bundle;
import android.text.TextUtils;
import it.feio.android.checklistview.utils.AlphaManager;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import android.widget.TextView;
import android.os.Parcelable;
import androidx.core.view.ViewPropertyAnimatorCompat;
import static androidx.core.view.ViewCompat.animate;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
// private Runnable mHideRunnable = new Runnable() {
// @Override
// public void run() {
// hideUndoBar(false);
// }
// };
public class UndoBarController {
    static final int MUID_STATIC = getMUID();
    private android.view.View mBarView;

    private android.widget.TextView mMessageView;

    private androidx.core.view.ViewPropertyAnimatorCompat mBarAnimator;

    // private Handler mHideHandler = new Handler();
    private it.feio.android.omninotes.models.UndoBarController.UndoListener mUndoListener;

    // State objects
    private android.os.Parcelable mUndoToken;

    private java.lang.CharSequence mUndoMessage;

    private android.widget.Button mButtonView;

    private boolean isVisible;

    public interface UndoListener {
        void onUndo(android.os.Parcelable token);

    }

    public UndoBarController(android.view.View undoBarView, it.feio.android.omninotes.models.UndoBarController.UndoListener undoListener) {
        mBarView = undoBarView;
        // mBarAnimator = mBarView.animate();
        mBarAnimator = androidx.core.view.ViewCompat.animate(mBarView);
        mUndoListener = undoListener;
        switch(MUID_STATIC) {
            // UndoBarController_0_InvalidViewFocusOperatorMutator
            case 72: {
                /**
                * Inserted by Kadabra
                */
                mMessageView = mBarView.findViewById(it.feio.android.omninotes.R.id.undobar_message);
                mMessageView.requestFocus();
                break;
            }
            // UndoBarController_1_ViewComponentNotVisibleOperatorMutator
            case 1072: {
                /**
                * Inserted by Kadabra
                */
                mMessageView = mBarView.findViewById(it.feio.android.omninotes.R.id.undobar_message);
                mMessageView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mMessageView = mBarView.findViewById(it.feio.android.omninotes.R.id.undobar_message);
            break;
        }
    }
    switch(MUID_STATIC) {
        // UndoBarController_2_InvalidViewFocusOperatorMutator
        case 2072: {
            /**
            * Inserted by Kadabra
            */
            mButtonView = mBarView.findViewById(it.feio.android.omninotes.R.id.undobar_button);
            mButtonView.requestFocus();
            break;
        }
        // UndoBarController_3_ViewComponentNotVisibleOperatorMutator
        case 3072: {
            /**
            * Inserted by Kadabra
            */
            mButtonView = mBarView.findViewById(it.feio.android.omninotes.R.id.undobar_button);
            mButtonView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mButtonView = mBarView.findViewById(it.feio.android.omninotes.R.id.undobar_button);
        break;
    }
}
mButtonView.setText(mButtonView.getText().toString().toUpperCase(java.util.Locale.getDefault()));
switch(MUID_STATIC) {
    // UndoBarController_4_BuggyGUIListenerOperatorMutator
    case 4072: {
        mButtonView.setOnClickListener(null);
        break;
    }
    default: {
    mButtonView.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View view) {
            hideUndoBar(false);
            switch(MUID_STATIC) {
                // UndoBarController_5_LengthyGUIListenerOperatorMutator
                case 5072: {
                    /**
                    * Inserted by Kadabra
                    */
                    mUndoListener.onUndo(mUndoToken);
                    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                    break;
                }
                default: {
                mUndoListener.onUndo(mUndoToken);
                break;
            }
        }
    }

});
break;
}
}
hideUndoBar(false);
}


public void showUndoBar(boolean immediate, java.lang.CharSequence message, android.os.Parcelable undoToken) {
mUndoToken = undoToken;
mUndoMessage = message;
mMessageView.setText(mUndoMessage);
// mHideHandler.removeCallbacks(mHideRunnable);
// mHideHandler.postDelayed(mHideRunnable,
// mBarView.getResources().getInteger(R.integer.undobar_hide_delay));
mBarView.setVisibility(android.view.View.VISIBLE);
if (immediate) {
// mBarView.setAlpha(1);
it.feio.android.checklistview.utils.AlphaManager.setAlpha(mBarView, 1);
} else {
mBarAnimator.cancel();
mBarAnimator.alpha(1).setDuration(mBarView.getResources().getInteger(android.R.integer.config_shortAnimTime)).setListener(null);
}
isVisible = true;
}


public void hideUndoBar(boolean immediate) {
// mHideHandler.removeCallbacks(mHideRunnable);
if (immediate) {
mBarView.setVisibility(android.view.View.GONE);
it.feio.android.checklistview.utils.AlphaManager.setAlpha(mBarView, 0);
mUndoMessage = null;
mUndoToken = null;
} else {
mBarAnimator.cancel();
mBarAnimator.alpha(0).setDuration(mBarView.getResources().getInteger(android.R.integer.config_shortAnimTime)).setListener(new androidx.core.view.ViewPropertyAnimatorListenerAdapter() {
@java.lang.Override
public void onAnimationEnd(android.view.View view) {
    super.onAnimationEnd(view);
    mBarView.setVisibility(android.view.View.GONE);
    mUndoMessage = null;
    mUndoToken = null;
}

});
}
isVisible = false;
}


public void onSaveInstanceState(android.os.Bundle outState) {
outState.putCharSequence("undo_message", mUndoMessage);
outState.putParcelable("undo_token", mUndoToken);
}


public void onRestoreInstanceState(android.os.Bundle savedInstanceState) {
if (savedInstanceState != null) {
mUndoMessage = savedInstanceState.getCharSequence("undo_message");
mUndoToken = savedInstanceState.getParcelable("undo_token");
if ((mUndoToken != null) || (!android.text.TextUtils.isEmpty(mUndoMessage))) {
showUndoBar(true, mUndoMessage, mUndoToken);
}
}
}


public boolean isVisible() {
return isVisible;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
