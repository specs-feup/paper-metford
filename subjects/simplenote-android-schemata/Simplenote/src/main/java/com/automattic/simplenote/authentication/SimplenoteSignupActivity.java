package com.automattic.simplenote.authentication;
import com.automattic.simplenote.R;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimplenoteSignupActivity extends androidx.appcompat.app.AppCompatActivity {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String SIGNUP_FRAGMENT_TAG = "signup";

    @java.lang.Override
    protected void onCreate(@androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SimplenoteSignupActivity_0_LengthyGUICreationOperatorMutator
            case 64: {
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
    setContentView(com.automattic.simplenote.R.layout.activity_signup);
    initContainer();
    initToolbar();
}


private void initContainer() {
    androidx.fragment.app.Fragment fragment;
    fragment = getSupportFragmentManager().findFragmentByTag(com.automattic.simplenote.authentication.SimplenoteSignupActivity.SIGNUP_FRAGMENT_TAG);
    if (fragment == null) {
        fragment = new com.automattic.simplenote.authentication.SignupFragment();
    }
    getSupportFragmentManager().beginTransaction().replace(com.automattic.simplenote.R.id.fragment_container, fragment, com.automattic.simplenote.authentication.SimplenoteSignupActivity.SIGNUP_FRAGMENT_TAG).commit();
}


private void initToolbar() {
    androidx.appcompat.widget.Toolbar toolbar;
    switch(MUID_STATIC) {
        // SimplenoteSignupActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 164: {
            toolbar = null;
            break;
        }
        // SimplenoteSignupActivity_2_InvalidIDFindViewOperatorMutator
        case 264: {
            toolbar = findViewById(732221);
            break;
        }
        // SimplenoteSignupActivity_3_InvalidViewFocusOperatorMutator
        case 364: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.simperium.R.id.toolbar);
            toolbar.requestFocus();
            break;
        }
        // SimplenoteSignupActivity_4_ViewComponentNotVisibleOperatorMutator
        case 464: {
            /**
            * Inserted by Kadabra
            */
            toolbar = findViewById(com.simperium.R.id.toolbar);
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = findViewById(com.simperium.R.id.toolbar);
        break;
    }
}
setSupportActionBar(toolbar);
if (getSupportActionBar() != null) {
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
}
}


@java.lang.Override
public boolean onOptionsItemSelected(android.view.MenuItem item) {
if (item.getItemId() == android.R.id.home) {
    onBackPressed();
    return true;
} else {
    return super.onOptionsItemSelected(item);
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
