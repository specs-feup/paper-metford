package com.beemdevelopment.aegis.ui;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ExitActivity extends android.app.Activity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // ExitActivity_0_LengthyGUICreationOperatorMutator
            case 171: {
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
    finishAndRemoveTask();
}


public static void exitAppAndRemoveFromRecents(android.content.Context context) {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // ExitActivity_1_NullIntentOperatorMutator
        case 1171: {
            intent = null;
            break;
        }
        // ExitActivity_2_InvalidKeyIntentOperatorMutator
        case 2171: {
            intent = new android.content.Intent((Context) null, com.beemdevelopment.aegis.ui.ExitActivity.class);
            break;
        }
        // ExitActivity_3_RandomActionIntentDefinitionOperatorMutator
        case 3171: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(context, com.beemdevelopment.aegis.ui.ExitActivity.class);
        break;
    }
}
switch(MUID_STATIC) {
    // ExitActivity_4_RandomActionIntentDefinitionOperatorMutator
    case 4171: {
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
    intent.addFlags(((android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK) | android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION) | android.content.Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    break;
}
}
context.startActivity(intent);
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
