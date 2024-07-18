package com.automattic.simplenote.authentication;
import com.automattic.simplenote.utils.HtmlCompat;
import com.automattic.simplenote.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.ViewGroup;
import android.text.method.LinkMovementMethod;
import android.text.Spanned;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.annotation.Nullable;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ConfirmationFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String CONFIRMATION_EMAIL_KEY = "CONFIRMATION_EMAIL_KEY";

    public static com.automattic.simplenote.authentication.ConfirmationFragment newInstance(java.lang.String email) {
        com.automattic.simplenote.authentication.ConfirmationFragment confirmationFragment;
        confirmationFragment = new com.automattic.simplenote.authentication.ConfirmationFragment();
        android.os.Bundle bundle;
        bundle = new android.os.Bundle();
        bundle.putString(com.automattic.simplenote.authentication.ConfirmationFragment.CONFIRMATION_EMAIL_KEY, email);
        confirmationFragment.setArguments(bundle);
        return confirmationFragment;
    }


    @androidx.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, @androidx.annotation.Nullable
    android.view.ViewGroup container, @androidx.annotation.Nullable
    android.os.Bundle savedInstanceState) {
        android.view.View view;
        view = inflater.inflate(com.automattic.simplenote.R.layout.fragment_confirmation, container, false);
        initUi(view);
        return view;
    }


    private void initUi(android.view.View view) {
        initEmailConfirmation(((android.widget.TextView) (view.findViewById(com.automattic.simplenote.R.id.email_confirmation_text))));
        initSupport(((android.widget.TextView) (view.findViewById(com.automattic.simplenote.R.id.support_text))));
    }


    private void initEmailConfirmation(android.widget.TextView emailConfirmation) {
        java.lang.String boldEmail;
        boldEmail = ("<b>" + requireArguments().getString(com.automattic.simplenote.authentication.ConfirmationFragment.CONFIRMATION_EMAIL_KEY)) + "</b>";
        android.text.Spanned emailConfirmationText;
        emailConfirmationText = com.automattic.simplenote.utils.HtmlCompat.fromHtml(java.lang.String.format(getString(com.automattic.simplenote.R.string.email_confirmation_text), boldEmail));
        emailConfirmation.setText(emailConfirmationText);
    }


    private void initSupport(android.widget.TextView support) {
        java.lang.String supportEmail;
        supportEmail = getString(com.automattic.simplenote.R.string.support_email);
        java.lang.String link;
        link = ((("<a href='mailto:" + supportEmail) + "'>") + supportEmail) + "</a>";
        android.text.Spanned supportText;
        supportText = com.automattic.simplenote.utils.HtmlCompat.fromHtml(java.lang.String.format(getString(com.automattic.simplenote.R.string.support_text), link));
        support.setText(supportText);
        support.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
