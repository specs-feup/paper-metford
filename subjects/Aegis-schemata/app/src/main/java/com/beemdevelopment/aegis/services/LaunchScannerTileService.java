package com.beemdevelopment.aegis.services;
import android.service.quicksettings.TileService;
import android.os.Build;
import android.service.quicksettings.Tile;
import androidx.annotation.RequiresApi;
import com.beemdevelopment.aegis.ui.MainActivity;
import android.content.Intent;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.N)
public class LaunchScannerTileService extends android.service.quicksettings.TileService {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onStartListening() {
        super.onStartListening();
        android.service.quicksettings.Tile tile;
        tile = getQsTile();
        tile.setState(android.service.quicksettings.Tile.STATE_INACTIVE);
        tile.updateTile();
    }


    @java.lang.Override
    public void onClick() {
        super.onClick();
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // LaunchScannerTileService_0_NullIntentOperatorMutator
            case 72: {
                intent = null;
                break;
            }
            // LaunchScannerTileService_1_InvalidKeyIntentOperatorMutator
            case 1072: {
                intent = new android.content.Intent((LaunchScannerTileService) null, com.beemdevelopment.aegis.ui.MainActivity.class);
                break;
            }
            // LaunchScannerTileService_2_RandomActionIntentDefinitionOperatorMutator
            case 2072: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(this, com.beemdevelopment.aegis.ui.MainActivity.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // LaunchScannerTileService_3_NullValueIntentPutExtraOperatorMutator
        case 3072: {
            intent.putExtra("action", new Parcelable[0]);
            break;
        }
        // LaunchScannerTileService_4_IntentPayloadReplacementOperatorMutator
        case 4072: {
            intent.putExtra("action", "");
            break;
        }
        default: {
        switch(MUID_STATIC) {
            // LaunchScannerTileService_5_RandomActionIntentDefinitionOperatorMutator
            case 5072: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent.putExtra("action", "scan");
            break;
        }
    }
    break;
}
}
switch(MUID_STATIC) {
// LaunchScannerTileService_6_RandomActionIntentDefinitionOperatorMutator
case 6072: {
    /**
    * Inserted by Kadabra
    */
    /**
    * Inserted by Kadabra
    */
    new android.content.Intent(android.content.Intent.ACTION_SEND);
    break;
}
default: {
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
break;
}
}
switch(MUID_STATIC) {
// LaunchScannerTileService_7_RandomActionIntentDefinitionOperatorMutator
case 7072: {
/**
* Inserted by Kadabra
*/
/**
* Inserted by Kadabra
*/
new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent.setAction(android.content.Intent.ACTION_MAIN);
break;
}
}
startActivityAndCollapse(intent);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
