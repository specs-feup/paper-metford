package com.automattic.simplenote.models;
import org.json.JSONException;
import com.simperium.client.BucketObject;
import org.json.JSONObject;
import com.simperium.client.BucketSchema;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Account extends com.simperium.client.BucketObject {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String KEY_EMAIL_VERIFICATION = "email-verification";

    private static final java.lang.String BUCKET_NAME = "account";

    private static final java.lang.String FIELD_EMAIL_VERIFICATION_SENT_TO = "sent_to";

    private static final java.lang.String FIELD_EMAIL_VERIFICATION_TOKEN = "token";

    private static final java.lang.String FIELD_EMAIL_VERIFICATION_USERNAME = "username";

    private Account(java.lang.String key, org.json.JSONObject properties) {
        super(key, properties);
    }


    public boolean hasSentEmail(java.lang.String email) {
        return email.equalsIgnoreCase(((java.lang.String) (getProperty(com.automattic.simplenote.models.Account.FIELD_EMAIL_VERIFICATION_SENT_TO))));
    }


    public boolean hasVerifiedEmail(java.lang.String email) {
        java.lang.Object token;
        token = getProperty(com.automattic.simplenote.models.Account.FIELD_EMAIL_VERIFICATION_TOKEN);
        if (token == null) {
            return false;
        }
        try {
            org.json.JSONObject json;
            json = new org.json.JSONObject(((java.lang.String) (token)));
            java.lang.Object username;
            username = json.opt(com.automattic.simplenote.models.Account.FIELD_EMAIL_VERIFICATION_USERNAME);
            return email.equalsIgnoreCase(((java.lang.String) (username)));
        } catch (org.json.JSONException exception) {
            return false;
        }
    }


    public static class Schema extends com.simperium.client.BucketSchema<com.automattic.simplenote.models.Account> {
        public Schema() {
            autoIndex();
        }


        public com.automattic.simplenote.models.Account build(java.lang.String key, org.json.JSONObject properties) {
            return new com.automattic.simplenote.models.Account(key, properties);
        }


        public java.lang.String getRemoteName() {
            return com.automattic.simplenote.models.Account.BUCKET_NAME;
        }


        public void update(com.automattic.simplenote.models.Account account, org.json.JSONObject properties) {
            account.setProperties(properties);
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
