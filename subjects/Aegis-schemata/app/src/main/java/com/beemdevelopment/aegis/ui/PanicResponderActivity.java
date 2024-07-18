package com.beemdevelopment.aegis.ui;
import com.beemdevelopment.aegis.vault.VaultRepository;
import com.beemdevelopment.aegis.R;
import android.os.Bundle;
import info.guardianproject.trustedintents.TrustedIntents;
import com.beemdevelopment.aegis.crypto.pins.GuardianProjectFDroidRSA2048;
import android.content.Intent;
import android.widget.Toast;
import com.beemdevelopment.aegis.BuildConfig;
import info.guardianproject.GuardianProjectRSA4096;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PanicResponderActivity extends com.beemdevelopment.aegis.ui.AegisActivity {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String PANIC_TRIGGER_ACTION = "info.guardianproject.panic.action.TRIGGER";

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // PanicResponderActivity_0_LengthyGUICreationOperatorMutator
            case 170: {
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
    if (!_prefs.isPanicTriggerEnabled()) {
        android.widget.Toast.makeText(this, com.beemdevelopment.aegis.R.string.panic_trigger_ignore_toast, android.widget.Toast.LENGTH_SHORT).show();
        finish();
        return;
    }
    android.content.Intent intent;
    if (!com.beemdevelopment.aegis.BuildConfig.TEST.get()) {
        info.guardianproject.trustedintents.TrustedIntents trustedIntents;
        trustedIntents = info.guardianproject.trustedintents.TrustedIntents.get(this);
        trustedIntents.addTrustedSigner(info.guardianproject.GuardianProjectRSA4096.class);
        trustedIntents.addTrustedSigner(com.beemdevelopment.aegis.crypto.pins.GuardianProjectFDroidRSA2048.class);
        switch(MUID_STATIC) {
            // PanicResponderActivity_1_RandomActionIntentDefinitionOperatorMutator
            case 1170: {
                intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            intent = trustedIntents.getIntentFromTrustedSender(this);
            break;
        }
    }
} else {
    switch(MUID_STATIC) {
        // PanicResponderActivity_2_RandomActionIntentDefinitionOperatorMutator
        case 2170: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = getIntent();
        break;
    }
}
}
if ((intent != null) && com.beemdevelopment.aegis.ui.PanicResponderActivity.PANIC_TRIGGER_ACTION.equals(intent.getAction())) {
com.beemdevelopment.aegis.vault.VaultRepository.deleteFile(this);
_vaultManager.lock(false);
finishApp();
return;
}
finish();
}


private void finishApp() {
com.beemdevelopment.aegis.ui.ExitActivity.exitAppAndRemoveFromRecents(this);
finishAndRemoveTask();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
