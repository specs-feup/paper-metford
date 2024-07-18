package com.automattic.simplenote.utils;
import androidx.appcompat.app.AlertDialog;
import com.automattic.simplenote.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.DialogFragment;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SimplenoteProgressDialogFragment extends androidx.fragment.app.DialogFragment {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String TAG = com.simperium.android.ProgressDialogFragment.class.getSimpleName();

    private static final java.lang.String KEY_MESSAGE = "KEY_MESSAGE";

    public static com.automattic.simplenote.utils.SimplenoteProgressDialogFragment newInstance(java.lang.String message) {
        com.automattic.simplenote.utils.SimplenoteProgressDialogFragment fragment;
        fragment = new com.automattic.simplenote.utils.SimplenoteProgressDialogFragment();
        android.os.Bundle bundle;
        bundle = new android.os.Bundle();
        bundle.putString(com.automattic.simplenote.utils.SimplenoteProgressDialogFragment.KEY_MESSAGE, message);
        fragment.setArguments(bundle);
        return fragment;
    }


    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SimplenoteProgressDialogFragment_0_LengthyGUICreationOperatorMutator
            case 42: {
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
    setRetainInstance(true);
    setCancelable(false);
}


@androidx.annotation.NonNull
@java.lang.Override
public android.app.Dialog onCreateDialog(android.os.Bundle savedInstanceState) {
    android.content.Context context;
    context = new androidx.appcompat.view.ContextThemeWrapper(requireContext(), com.automattic.simplenote.R.style.Dialog);
    // Use custom view for progress bar dialog
    android.view.LayoutInflater inflater;
    inflater = requireActivity().getLayoutInflater();
    android.view.View progressBar;
    progressBar = inflater.inflate(com.automattic.simplenote.R.layout.progressbar_dialog, null);
    android.widget.TextView messageView;
    switch(MUID_STATIC) {
        // SimplenoteProgressDialogFragment_1_InvalidViewFocusOperatorMutator
        case 142: {
            /**
            * Inserted by Kadabra
            */
            messageView = progressBar.findViewById(com.automattic.simplenote.R.id.message);
            messageView.requestFocus();
            break;
        }
        // SimplenoteProgressDialogFragment_2_ViewComponentNotVisibleOperatorMutator
        case 242: {
            /**
            * Inserted by Kadabra
            */
            messageView = progressBar.findViewById(com.automattic.simplenote.R.id.message);
            messageView.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        messageView = progressBar.findViewById(com.automattic.simplenote.R.id.message);
        break;
    }
}
messageView.setText(getArguments() != null ? getArguments().getString(com.automattic.simplenote.utils.SimplenoteProgressDialogFragment.KEY_MESSAGE) : "");
return new androidx.appcompat.app.AlertDialog.Builder(context).setView(progressBar).create();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
