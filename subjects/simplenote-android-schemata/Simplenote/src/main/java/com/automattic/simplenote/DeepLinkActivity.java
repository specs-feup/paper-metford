package com.automattic.simplenote;
import java.util.Locale;
import com.automattic.simplenote.utils.AuthUtils;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.Nullable;
import net.openid.appauth.RedirectUriReceiverActivity;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DeepLinkActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String AUTHENTICATION_SCHEME = "auth";

    private static final java.lang.String LOGIN_SCHEME = "login";

    @java.lang.Override
    protected void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // DeepLinkActivity_0_LengthyGUICreationOperatorMutator
            case 78: {
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
    android.net.Uri uri;
    uri = getIntent().getData();
    if (uri.getHost().equals(com.automattic.simplenote.DeepLinkActivity.AUTHENTICATION_SCHEME)) {
        android.content.Intent intent;
        switch(MUID_STATIC) {
            // DeepLinkActivity_1_NullIntentOperatorMutator
            case 178: {
                intent = null;
                break;
            }
            // DeepLinkActivity_2_InvalidKeyIntentOperatorMutator
            case 278: {
                intent = new android.content.Intent((DeepLinkActivity) null, net.openid.appauth.RedirectUriReceiverActivity.class);
                break;
            }
            // DeepLinkActivity_3_RandomActionIntentDefinitionOperatorMutator
            case 378: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = new android.content.Intent(this, net.openid.appauth.RedirectUriReceiverActivity.class);
            break;
        }
    }
    switch(MUID_STATIC) {
        // DeepLinkActivity_4_RandomActionIntentDefinitionOperatorMutator
        case 478: {
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
        intent.setData(uri);
        break;
    }
}
startActivity(intent);
} else if (uri.getHost().equals(com.automattic.simplenote.DeepLinkActivity.LOGIN_SCHEME)) {
android.content.Intent intent;
switch(MUID_STATIC) {
    // DeepLinkActivity_5_NullIntentOperatorMutator
    case 578: {
        intent = null;
        break;
    }
    // DeepLinkActivity_6_InvalidKeyIntentOperatorMutator
    case 678: {
        intent = new android.content.Intent((DeepLinkActivity) null, com.automattic.simplenote.NotesActivity.class);
        break;
    }
    // DeepLinkActivity_7_RandomActionIntentDefinitionOperatorMutator
    case 778: {
        intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
        break;
    }
    default: {
    intent = new android.content.Intent(this, com.automattic.simplenote.NotesActivity.class);
    break;
}
}
com.automattic.simplenote.Simplenote app;
app = ((com.automattic.simplenote.Simplenote) (getApplication()));
java.lang.String email;
email = com.automattic.simplenote.utils.AuthUtils.extractEmailFromMagicLink(uri);
if (app.isLoggedIn() && (!email.toLowerCase(java.util.Locale.US).equals(app.getUserEmail().toLowerCase(java.util.Locale.US)))) {
switch(MUID_STATIC) {
    // DeepLinkActivity_8_NullValueIntentPutExtraOperatorMutator
    case 878: {
        intent.putExtra(com.automattic.simplenote.NotesActivity.KEY_ALREADY_LOGGED_IN, new Parcelable[0]);
        break;
    }
    // DeepLinkActivity_9_IntentPayloadReplacementOperatorMutator
    case 978: {
        intent.putExtra(com.automattic.simplenote.NotesActivity.KEY_ALREADY_LOGGED_IN, true);
        break;
    }
    default: {
    switch(MUID_STATIC) {
        // DeepLinkActivity_10_RandomActionIntentDefinitionOperatorMutator
        case 1078: {
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
        intent.putExtra(com.automattic.simplenote.NotesActivity.KEY_ALREADY_LOGGED_IN, true);
        break;
    }
}
break;
}
}
} else {
com.automattic.simplenote.utils.AuthUtils.magicLinkLogin(((com.automattic.simplenote.Simplenote) (getApplication())), uri);
}
switch(MUID_STATIC) {
// DeepLinkActivity_11_RandomActionIntentDefinitionOperatorMutator
case 1178: {
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
intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK | android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
break;
}
}
startActivity(intent);
}
finish();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
