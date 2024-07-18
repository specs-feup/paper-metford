package de.danoeh.antennapod.activity;
import de.danoeh.antennapod.storage.database.PodDBAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import de.danoeh.antennapod.error.CrashReportWriter;
import io.reactivex.Completable;
import android.app.Activity;
import android.os.Bundle;
import io.reactivex.schedulers.Schedulers;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.Nullable;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Shows the AntennaPod logo while waiting for the main activity to start.
 */
@android.annotation.SuppressLint("CustomSplashScreen")
public class SplashActivity extends android.app.Activity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SplashActivity_0_LengthyGUICreationOperatorMutator
            case 148: {
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
    final android.view.View content;
    switch(MUID_STATIC) {
        // SplashActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1148: {
            content = null;
            break;
        }
        // SplashActivity_2_InvalidIDFindViewOperatorMutator
        case 2148: {
            content = findViewById(732221);
            break;
        }
        // SplashActivity_3_InvalidViewFocusOperatorMutator
        case 3148: {
            /**
            * Inserted by Kadabra
            */
            content = findViewById(android.R.id.content);
            content.requestFocus();
            break;
        }
        // SplashActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4148: {
            /**
            * Inserted by Kadabra
            */
            content = findViewById(android.R.id.content);
            content.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        content = findViewById(android.R.id.content);
        break;
    }
}
content.getViewTreeObserver().addOnPreDrawListener(() -> false)// Keep splash screen active
;// Keep splash screen active

io.reactivex.Completable.create((io.reactivex.CompletableEmitter subscriber) -> {
    // Trigger schema updates
    de.danoeh.antennapod.storage.database.PodDBAdapter.getInstance().open();
    de.danoeh.antennapod.storage.database.PodDBAdapter.getInstance().close();
    subscriber.onComplete();
}).subscribeOn(io.reactivex.schedulers.Schedulers.io()).observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread()).subscribe(() -> {
    android.content.Intent intent;
    switch(MUID_STATIC) {
        // SplashActivity_5_InvalidKeyIntentOperatorMutator
        case 5148: {
            intent = new android.content.Intent((SplashActivity) null, de.danoeh.antennapod.activity.MainActivity.class);
            break;
        }
        // SplashActivity_6_RandomActionIntentDefinitionOperatorMutator
        case 6148: {
            intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        intent = new android.content.Intent(this, de.danoeh.antennapod.activity.MainActivity.class);
        break;
    }
}
startActivity(intent);
overridePendingTransition(0, 0);
finish();
}, (java.lang.Throwable error) -> {
error.printStackTrace();
de.danoeh.antennapod.error.CrashReportWriter.write(error);
android.widget.Toast.makeText(this, error.getLocalizedMessage(), android.widget.Toast.LENGTH_LONG).show();
finish();
});
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
