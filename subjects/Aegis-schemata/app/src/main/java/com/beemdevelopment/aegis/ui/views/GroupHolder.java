package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.R;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GroupHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    private android.widget.TextView _slotName;

    private android.widget.ImageView _buttonDelete;

    public GroupHolder(final android.view.View view) {
        super(view);
        switch(MUID_STATIC) {
            // GroupHolder_0_InvalidViewFocusOperatorMutator
            case 152: {
                /**
                * Inserted by Kadabra
                */
                _slotName = view.findViewById(com.beemdevelopment.aegis.R.id.text_group_name);
                _slotName.requestFocus();
                break;
            }
            // GroupHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1152: {
                /**
                * Inserted by Kadabra
                */
                _slotName = view.findViewById(com.beemdevelopment.aegis.R.id.text_group_name);
                _slotName.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _slotName = view.findViewById(com.beemdevelopment.aegis.R.id.text_group_name);
            break;
        }
    }
    switch(MUID_STATIC) {
        // GroupHolder_2_InvalidViewFocusOperatorMutator
        case 2152: {
            /**
            * Inserted by Kadabra
            */
            _buttonDelete = view.findViewById(com.beemdevelopment.aegis.R.id.button_delete);
            _buttonDelete.requestFocus();
            break;
        }
        // GroupHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3152: {
            /**
            * Inserted by Kadabra
            */
            _buttonDelete = view.findViewById(com.beemdevelopment.aegis.R.id.button_delete);
            _buttonDelete.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _buttonDelete = view.findViewById(com.beemdevelopment.aegis.R.id.button_delete);
        break;
    }
}
}


public void setData(java.lang.String groupName) {
_slotName.setText(groupName);
}


public void setOnDeleteClickListener(android.view.View.OnClickListener listener) {
_buttonDelete.setOnClickListener(listener);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
