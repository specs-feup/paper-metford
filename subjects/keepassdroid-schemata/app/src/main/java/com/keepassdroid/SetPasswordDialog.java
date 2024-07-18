/* Copyright 2009-2022 Brian Pellin.

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
import com.keepassdroid.app.App;
import com.keepassdroid.utils.UriUtil;
import android.content.DialogInterface;
import com.keepassdroid.utils.EmptyUtils;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.keepassdroid.database.edit.OnFinish;
import android.net.Uri;
import android.view.View;
import com.android.keepass.R;
import android.widget.Button;
import android.app.Activity;
import com.keepassdroid.database.edit.SetPassword;
import android.widget.TextView;
import android.widget.Toast;
import com.keepassdroid.database.edit.FileOnFinish;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SetPasswordDialog extends com.keepassdroid.CancelDialog {
    static final int MUID_STATIC = getMUID();
    private byte[] masterKey;

    private android.net.Uri mKeyfile;

    private com.keepassdroid.database.edit.FileOnFinish mFinish;

    public SetPasswordDialog(android.app.Activity act) {
        super(act);
        setOwnerActivity(act);
    }


    public SetPasswordDialog(android.app.Activity act, com.keepassdroid.database.edit.FileOnFinish finish) {
        this(act);
        mFinish = finish;
    }


    public byte[] getKey() {
        return masterKey;
    }


    public android.net.Uri keyfile() {
        return mKeyfile;
    }


    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SetPasswordDialog_0_LengthyGUICreationOperatorMutator
            case 197: {
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
    setContentView(com.android.keepass.R.layout.set_password);
    setTitle(com.android.keepass.R.string.password_title);
    // Ok button
    android.widget.Button okButton;
    switch(MUID_STATIC) {
        // SetPasswordDialog_1_InvalidViewFocusOperatorMutator
        case 1197: {
            /**
            * Inserted by Kadabra
            */
            okButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.ok)));
            okButton.requestFocus();
            break;
        }
        // SetPasswordDialog_2_ViewComponentNotVisibleOperatorMutator
        case 2197: {
            /**
            * Inserted by Kadabra
            */
            okButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.ok)));
            okButton.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        okButton = ((android.widget.Button) (findViewById(com.android.keepass.R.id.ok)));
        break;
    }
}
switch(MUID_STATIC) {
    // SetPasswordDialog_3_BuggyGUIListenerOperatorMutator
    case 3197: {
        okButton.setOnClickListener(null);
        break;
    }
    default: {
    okButton.setOnClickListener(new android.view.View.OnClickListener() {
        public void onClick(android.view.View v) {
            android.widget.TextView passView;
            switch(MUID_STATIC) {
                // SetPasswordDialog_5_InvalidViewFocusOperatorMutator
                case 5197: {
                    /**
                    * Inserted by Kadabra
                    */
                    passView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_password)));
                    passView.requestFocus();
                    break;
                }
                // SetPasswordDialog_6_ViewComponentNotVisibleOperatorMutator
                case 6197: {
                    /**
                    * Inserted by Kadabra
                    */
                    passView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_password)));
                    passView.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                passView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_password)));
                break;
            }
        }
        java.lang.String pass;
        pass = passView.getText().toString();
        android.widget.TextView passConfView;
        switch(MUID_STATIC) {
            // SetPasswordDialog_7_InvalidViewFocusOperatorMutator
            case 7197: {
                /**
                * Inserted by Kadabra
                */
                passConfView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_conf_password)));
                passConfView.requestFocus();
                break;
            }
            // SetPasswordDialog_8_ViewComponentNotVisibleOperatorMutator
            case 8197: {
                /**
                * Inserted by Kadabra
                */
                passConfView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_conf_password)));
                passConfView.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            passConfView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_conf_password)));
            break;
        }
    }
    java.lang.String confpass;
    confpass = passConfView.getText().toString();
    // Verify that passwords match
    if (!pass.equals(confpass)) {
        // Passwords do not match
        android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.error_pass_match, android.widget.Toast.LENGTH_LONG).show();
        return;
    }
    android.widget.TextView keyfileView;
    switch(MUID_STATIC) {
        // SetPasswordDialog_9_InvalidViewFocusOperatorMutator
        case 9197: {
            /**
            * Inserted by Kadabra
            */
            keyfileView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_keyfile)));
            keyfileView.requestFocus();
            break;
        }
        // SetPasswordDialog_10_ViewComponentNotVisibleOperatorMutator
        case 10197: {
            /**
            * Inserted by Kadabra
            */
            keyfileView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_keyfile)));
            keyfileView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        keyfileView = ((android.widget.TextView) (findViewById(com.android.keepass.R.id.pass_keyfile)));
        break;
    }
}
android.net.Uri keyfile;
keyfile = com.keepassdroid.utils.UriUtil.parseDefaultFile(keyfileView.getText().toString());
mKeyfile = keyfile;
// Verify that a password or keyfile is set
if ((pass.length() == 0) && com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(keyfile)) {
    android.widget.Toast.makeText(getContext(), com.android.keepass.R.string.error_nopass, android.widget.Toast.LENGTH_LONG).show();
    return;
}
com.keepassdroid.database.edit.SetPassword sp;
sp = new com.keepassdroid.database.edit.SetPassword(getContext(), com.keepassdroid.app.App.getDB(), pass, keyfile, new com.keepassdroid.SetPasswordDialog.AfterSave(mFinish, new android.os.Handler()));
android.app.Activity act;
act = getOwnerActivity();
final com.keepassdroid.ProgressTask pt;
pt = new com.keepassdroid.ProgressTask(act, sp, com.android.keepass.R.string.saving_database);
boolean valid;
switch(MUID_STATIC) {
    // SetPasswordDialog_11_BuggyGUIListenerOperatorMutator
    case 11197: {
        valid = sp.validatePassword(getContext(), null);
        break;
    }
    default: {
    valid = sp.validatePassword(getContext(), new android.content.DialogInterface.OnClickListener() {
        @java.lang.Override
        public void onClick(android.content.DialogInterface dialog, int which) {
            switch(MUID_STATIC) {
                // SetPasswordDialog_12_LengthyGUIListenerOperatorMutator
                case 12197: {
                    /**
                    * Inserted by Kadabra
                    */
                    pt.run();
                    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
                    break;
                }
                default: {
                pt.run();
                break;
            }
        }
    }

});
break;
}
}
switch(MUID_STATIC) {
// SetPasswordDialog_4_LengthyGUIListenerOperatorMutator
case 4197: {
/**
* Inserted by Kadabra
*/
if (valid) {
    pt.run();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (valid) {
pt.run();
}
break;
}
}
}

});
break;
}
}
// Cancel button
android.widget.Button cancel;
switch(MUID_STATIC) {
// SetPasswordDialog_13_InvalidViewFocusOperatorMutator
case 13197: {
/**
* Inserted by Kadabra
*/
cancel = ((android.widget.Button) (findViewById(com.android.keepass.R.id.cancel)));
cancel.requestFocus();
break;
}
// SetPasswordDialog_14_ViewComponentNotVisibleOperatorMutator
case 14197: {
/**
* Inserted by Kadabra
*/
cancel = ((android.widget.Button) (findViewById(com.android.keepass.R.id.cancel)));
cancel.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cancel = ((android.widget.Button) (findViewById(com.android.keepass.R.id.cancel)));
break;
}
}
switch(MUID_STATIC) {
// SetPasswordDialog_15_BuggyGUIListenerOperatorMutator
case 15197: {
cancel.setOnClickListener(null);
break;
}
default: {
cancel.setOnClickListener(new android.view.View.OnClickListener() {
public void onClick(android.view.View v) {
cancel();
switch(MUID_STATIC) {
// SetPasswordDialog_16_LengthyGUIListenerOperatorMutator
case 16197: {
/**
* Inserted by Kadabra
*/
if (mFinish != null) {
mFinish.run();
}
try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
break;
}
default: {
if (mFinish != null) {
mFinish.run();
}
break;
}
}
}

});
break;
}
}
}


private class AfterSave extends com.keepassdroid.database.edit.OnFinish {
private com.keepassdroid.database.edit.FileOnFinish mFinish;

public AfterSave(com.keepassdroid.database.edit.FileOnFinish finish, android.os.Handler handler) {
super(finish, handler);
mFinish = finish;
}


@java.lang.Override
public void run() {
if (mSuccess) {
if (mFinish != null) {
mFinish.setFilename(mKeyfile);
}
dismiss();
} else {
displayMessage(((androidx.appcompat.app.AppCompatActivity) (getOwnerActivity())));
}
super.run();
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
