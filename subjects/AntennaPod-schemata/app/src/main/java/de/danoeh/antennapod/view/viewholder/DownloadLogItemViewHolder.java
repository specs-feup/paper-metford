package de.danoeh.antennapod.view.viewholder;
import de.danoeh.antennapod.R;
import android.text.Layout;
import android.view.LayoutInflater;
import android.os.Build;
import android.view.ViewGroup;
import de.danoeh.antennapod.ui.common.CircularProgressBar;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.joanzapata.iconify.widget.IconTextView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DownloadLogItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    static final int MUID_STATIC = getMUID();
    public final android.view.View secondaryActionButton;

    public final android.widget.ImageView secondaryActionIcon;

    public final de.danoeh.antennapod.ui.common.CircularProgressBar secondaryActionProgress;

    public final com.joanzapata.iconify.widget.IconTextView icon;

    public final android.widget.TextView title;

    public final android.widget.TextView status;

    public final android.widget.TextView reason;

    public final android.widget.TextView tapForDetails;

    public DownloadLogItemViewHolder(android.content.Context context, android.view.ViewGroup parent) {
        super(android.view.LayoutInflater.from(context).inflate(de.danoeh.antennapod.R.layout.downloadlog_item, parent, false));
        switch(MUID_STATIC) {
            // DownloadLogItemViewHolder_0_InvalidViewFocusOperatorMutator
            case 1: {
                /**
                * Inserted by Kadabra
                */
                status = itemView.findViewById(de.danoeh.antennapod.R.id.status);
                status.requestFocus();
                break;
            }
            // DownloadLogItemViewHolder_1_ViewComponentNotVisibleOperatorMutator
            case 1001: {
                /**
                * Inserted by Kadabra
                */
                status = itemView.findViewById(de.danoeh.antennapod.R.id.status);
                status.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            status = itemView.findViewById(de.danoeh.antennapod.R.id.status);
            break;
        }
    }
    switch(MUID_STATIC) {
        // DownloadLogItemViewHolder_2_InvalidViewFocusOperatorMutator
        case 2001: {
            /**
            * Inserted by Kadabra
            */
            icon = itemView.findViewById(de.danoeh.antennapod.R.id.txtvIcon);
            icon.requestFocus();
            break;
        }
        // DownloadLogItemViewHolder_3_ViewComponentNotVisibleOperatorMutator
        case 3001: {
            /**
            * Inserted by Kadabra
            */
            icon = itemView.findViewById(de.danoeh.antennapod.R.id.txtvIcon);
            icon.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        icon = itemView.findViewById(de.danoeh.antennapod.R.id.txtvIcon);
        break;
    }
}
switch(MUID_STATIC) {
    // DownloadLogItemViewHolder_4_InvalidViewFocusOperatorMutator
    case 4001: {
        /**
        * Inserted by Kadabra
        */
        reason = itemView.findViewById(de.danoeh.antennapod.R.id.txtvReason);
        reason.requestFocus();
        break;
    }
    // DownloadLogItemViewHolder_5_ViewComponentNotVisibleOperatorMutator
    case 5001: {
        /**
        * Inserted by Kadabra
        */
        reason = itemView.findViewById(de.danoeh.antennapod.R.id.txtvReason);
        reason.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    reason = itemView.findViewById(de.danoeh.antennapod.R.id.txtvReason);
    break;
}
}
switch(MUID_STATIC) {
// DownloadLogItemViewHolder_6_InvalidViewFocusOperatorMutator
case 6001: {
    /**
    * Inserted by Kadabra
    */
    tapForDetails = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTapForDetails);
    tapForDetails.requestFocus();
    break;
}
// DownloadLogItemViewHolder_7_ViewComponentNotVisibleOperatorMutator
case 7001: {
    /**
    * Inserted by Kadabra
    */
    tapForDetails = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTapForDetails);
    tapForDetails.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
tapForDetails = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTapForDetails);
break;
}
}
switch(MUID_STATIC) {
// DownloadLogItemViewHolder_8_InvalidViewFocusOperatorMutator
case 8001: {
/**
* Inserted by Kadabra
*/
secondaryActionButton = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionButton);
secondaryActionButton.requestFocus();
break;
}
// DownloadLogItemViewHolder_9_ViewComponentNotVisibleOperatorMutator
case 9001: {
/**
* Inserted by Kadabra
*/
secondaryActionButton = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionButton);
secondaryActionButton.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
secondaryActionButton = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionButton);
break;
}
}
switch(MUID_STATIC) {
// DownloadLogItemViewHolder_10_InvalidViewFocusOperatorMutator
case 10001: {
/**
* Inserted by Kadabra
*/
secondaryActionProgress = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
secondaryActionProgress.requestFocus();
break;
}
// DownloadLogItemViewHolder_11_ViewComponentNotVisibleOperatorMutator
case 11001: {
/**
* Inserted by Kadabra
*/
secondaryActionProgress = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
secondaryActionProgress.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
secondaryActionProgress = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionProgress);
break;
}
}
switch(MUID_STATIC) {
// DownloadLogItemViewHolder_12_InvalidViewFocusOperatorMutator
case 12001: {
/**
* Inserted by Kadabra
*/
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
secondaryActionIcon.requestFocus();
break;
}
// DownloadLogItemViewHolder_13_ViewComponentNotVisibleOperatorMutator
case 13001: {
/**
* Inserted by Kadabra
*/
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
secondaryActionIcon.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
secondaryActionIcon = itemView.findViewById(de.danoeh.antennapod.R.id.secondaryActionIcon);
break;
}
}
switch(MUID_STATIC) {
// DownloadLogItemViewHolder_14_InvalidViewFocusOperatorMutator
case 14001: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.requestFocus();
break;
}
// DownloadLogItemViewHolder_15_ViewComponentNotVisibleOperatorMutator
case 15001: {
/**
* Inserted by Kadabra
*/
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
title.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
title = itemView.findViewById(de.danoeh.antennapod.R.id.txtvTitle);
break;
}
}
if (android.os.Build.VERSION.SDK_INT >= 23) {
title.setHyphenationFrequency(android.text.Layout.HYPHENATION_FREQUENCY_FULL);
}
itemView.setTag(this);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
