package de.danoeh.antennapod.preferences;
import de.danoeh.antennapod.R;
import android.util.AttributeSet;
import androidx.preference.SwitchPreferenceCompat;
import de.danoeh.antennapod.ui.common.ThemeUtils;
import android.graphics.Typeface;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class MasterSwitchPreference extends androidx.preference.SwitchPreferenceCompat {
    static final int MUID_STATIC = getMUID();
    public MasterSwitchPreference(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public MasterSwitchPreference(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public MasterSwitchPreference(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public MasterSwitchPreference(android.content.Context context) {
        super(context);
    }


    @java.lang.Override
    public void onBindViewHolder(androidx.preference.PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setBackgroundColor(de.danoeh.antennapod.ui.common.ThemeUtils.getColorFromAttr(getContext(), de.danoeh.antennapod.R.attr.colorSurfaceVariant));
        android.widget.TextView title;
        switch(MUID_STATIC) {
            // MasterSwitchPreference_0_InvalidViewFocusOperatorMutator
            case 75: {
                /**
                * Inserted by Kadabra
                */
                title = ((android.widget.TextView) (holder.findViewById(android.R.id.title)));
                title.requestFocus();
                break;
            }
            // MasterSwitchPreference_1_ViewComponentNotVisibleOperatorMutator
            case 1075: {
                /**
                * Inserted by Kadabra
                */
                title = ((android.widget.TextView) (holder.findViewById(android.R.id.title)));
                title.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            title = ((android.widget.TextView) (holder.findViewById(android.R.id.title)));
            break;
        }
    }
    if (title != null) {
        title.setTypeface(title.getTypeface(), android.graphics.Typeface.BOLD);
    }
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
