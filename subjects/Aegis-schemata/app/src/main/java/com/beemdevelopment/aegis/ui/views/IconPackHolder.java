package com.beemdevelopment.aegis.ui.views;
import com.beemdevelopment.aegis.R;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.beemdevelopment.aegis.icons.IconPack;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IconPackHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    private final android.widget.TextView _iconPackName;

    private final android.widget.TextView _iconPackInfo;

    private final android.widget.ImageView _buttonDelete;

    public IconPackHolder(final android.view.View view) {
        super(view);
        switch(MUID_STATIC) {
            // IconPackHolder_0_InvalidViewFocusOperatorMutator
            case 161: {
                /**
                * Inserted by Kadabra
                */
                _iconPackName = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack_name);
                _iconPackName.requestFocus();
                break;
            }
            // IconPackHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1161: {
                /**
                * Inserted by Kadabra
                */
                _iconPackName = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack_name);
                _iconPackName.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            _iconPackName = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack_name);
            break;
        }
    }
    switch(MUID_STATIC) {
        // IconPackHolder_2_InvalidViewFocusOperatorMutator
        case 2161: {
            /**
            * Inserted by Kadabra
            */
            _iconPackInfo = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack_info);
            _iconPackInfo.requestFocus();
            break;
        }
        // IconPackHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3161: {
            /**
            * Inserted by Kadabra
            */
            _iconPackInfo = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack_info);
            _iconPackInfo.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        _iconPackInfo = view.findViewById(com.beemdevelopment.aegis.R.id.text_icon_pack_info);
        break;
    }
}
switch(MUID_STATIC) {
    // IconPackHolder_4_InvalidViewFocusOperatorMutator
    case 4161: {
        /**
        * Inserted by Kadabra
        */
        _buttonDelete = view.findViewById(com.beemdevelopment.aegis.R.id.button_delete);
        _buttonDelete.requestFocus();
        break;
    }
    // IconPackHolder_5_ViewComponentNotVisibleOperatorMutator
    case 5161: {
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


public void setData(com.beemdevelopment.aegis.icons.IconPack pack) {
_iconPackName.setText(java.lang.String.format("%s (v%d)", pack.getName(), pack.getVersion()));
_iconPackInfo.setText(itemView.getResources().getQuantityString(com.beemdevelopment.aegis.R.plurals.icon_pack_info, pack.getIcons().size(), pack.getIcons().size()));
}


public void setOnDeleteClickListener(android.view.View.OnClickListener listener) {
_buttonDelete.setOnClickListener(listener);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
