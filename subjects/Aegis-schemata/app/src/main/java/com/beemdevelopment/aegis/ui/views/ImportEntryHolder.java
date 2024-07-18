package com.beemdevelopment.aegis.ui.views;
import android.widget.CheckBox;
import com.beemdevelopment.aegis.R;
import com.beemdevelopment.aegis.ui.models.ImportEntry;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ImportEntryHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements com.beemdevelopment.aegis.ui.models.ImportEntry.Listener {
    static final int MUID_STATIC = getMUID();
    private android.widget.TextView _issuer;

    private android.widget.TextView _accountName;

    private android.widget.CheckBox _checkbox;

    private com.beemdevelopment.aegis.ui.models.ImportEntry _data;

    public ImportEntryHolder(final android.view.View view) {
        super(view);
        switch(MUID_STATIC) {
            // ImportEntryHolder_0_InvalidViewFocusOperatorMutator
            case 159: {
                /**
                * Inserted by Kadabra
                */
                _issuer = view.findViewById(com.beemdevelopment.aegis.R.id.profile_issuer);
                _issuer.requestFocus();
                break;
            }
            // ImportEntryHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1159: {
                /**
                * Inserted by Kadabra
                */
                _issuer = view.findViewById(com.beemdevelopment.aegis.R.id.profile_issuer);
                _issuer.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _issuer = view.findViewById(com.beemdevelopment.aegis.R.id.profile_issuer);
            break;
        }
    }
    switch(MUID_STATIC) {
        // ImportEntryHolder_2_InvalidViewFocusOperatorMutator
        case 2159: {
            /**
            * Inserted by Kadabra
            */
            _accountName = view.findViewById(com.beemdevelopment.aegis.R.id.profile_account_name);
            _accountName.requestFocus();
            break;
        }
        // ImportEntryHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3159: {
            /**
            * Inserted by Kadabra
            */
            _accountName = view.findViewById(com.beemdevelopment.aegis.R.id.profile_account_name);
            _accountName.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _accountName = view.findViewById(com.beemdevelopment.aegis.R.id.profile_account_name);
        break;
    }
}
switch(MUID_STATIC) {
    // ImportEntryHolder_4_InvalidViewFocusOperatorMutator
    case 4159: {
        /**
        * Inserted by Kadabra
        */
        _checkbox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_import_entry);
        _checkbox.requestFocus();
        break;
    }
    // ImportEntryHolder_5_ViewComponentNotVisibleOperatorMutator
    case 5159: {
        /**
        * Inserted by Kadabra
        */
        _checkbox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_import_entry);
        _checkbox.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    _checkbox = view.findViewById(com.beemdevelopment.aegis.R.id.checkbox_import_entry);
    break;
}
}
switch(MUID_STATIC) {
// ImportEntryHolder_6_BuggyGUIListenerOperatorMutator
case 6159: {
    view.setOnClickListener(null);
    break;
}
default: {
view.setOnClickListener((android.view.View v) -> _data.setIsChecked(!_data.isChecked()));
break;
}
}
}


public void setData(com.beemdevelopment.aegis.ui.models.ImportEntry data) {
_data = data;
android.content.Context context;
context = itemView.getContext();
_issuer.setText(!_data.getEntry().getIssuer().isEmpty() ? _data.getEntry().getIssuer() : context.getString(com.beemdevelopment.aegis.R.string.unknown_issuer));
_accountName.setText(!_data.getEntry().getName().isEmpty() ? _data.getEntry().getName() : context.getString(com.beemdevelopment.aegis.R.string.unknown_account_name));
_checkbox.setChecked(_data.isChecked());
}


public com.beemdevelopment.aegis.ui.models.ImportEntry getData() {
return _data;
}


@java.lang.Override
public void onCheckedChanged(boolean value) {
_checkbox.setChecked(value);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
