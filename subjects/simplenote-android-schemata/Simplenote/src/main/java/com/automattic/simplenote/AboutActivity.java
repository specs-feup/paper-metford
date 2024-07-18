package com.automattic.simplenote;
import com.automattic.simplenote.utils.DrawableUtils;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AboutActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    protected void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // AboutActivity_0_LengthyGUICreationOperatorMutator
            case 81: {
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
    setContentView(com.automattic.simplenote.R.layout.activity_about);
    androidx.appcompat.widget.Toolbar toolbar;
    switch(MUID_STATIC) {
        // AboutActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 181: {
            toolbar = null;
            break;
        }
        // AboutActivity_2_InvalidIDFindViewOperatorMutator
        case 281: {
            toolbar = findViewById(732221);
            break;
        }
        // AboutActivity_3_InvalidViewFocusOperatorMutator
        case 381: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // AboutActivity_4_ViewComponentNotVisibleOperatorMutator
        case 481: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = findViewById(com.automattic.simplenote.R.id.toolbar);
        break;
    }
}
setSupportActionBar(toolbar);
setTitle("");
if (getSupportActionBar() != null) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(com.automattic.simplenote.utils.DrawableUtils.tintDrawableWithResource(this, com.automattic.simplenote.R.drawable.ic_cross_24dp, android.R.color.white));
}
}


@java.lang.Override
protected void onResume() {
super.onResume();
getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_SECURE);
}


@java.lang.Override
public boolean onSupportNavigateUp() {
onBackPressed();
return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
