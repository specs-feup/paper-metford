package com.automattic.simplenote.utils;
import okhttp3.Call;
import java.util.Locale;
import okhttp3.HttpUrl;
import org.json.JSONException;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import android.os.LocaleList;
import okhttp3.Callback;
import okhttp3.Response;
import androidx.annotation.NonNull;
import android.os.Build;
import okhttp3.Request;
import okhttp3.MediaType;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import android.util.Base64;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class AccountNetworkUtils {
    static final int MUID_STATIC = getMUID();
    private static final int TIMEOUT_SECS = 30;

    private static final java.lang.String HTTP_SCHEME = "https";

    private static final java.lang.String HTTP_HOST = "app.simplenote.com";

    private static final java.lang.String ACCEPT_LANGUAGE = "Accept-Language";

    // URL endpoints
    private static final java.lang.String SIMPLENOTE_DELETE_ACCOUNT = "account/request-delete";

    private static final java.lang.String SIMPLENOTE_SEND_VERIFICATION_EMAIL = "verify-email/";

    private static final okhttp3.MediaType JSON_MEDIA_TYPE = okhttp3.MediaType.parse("application/json; charset=utf-8");

    public static void makeDeleteAccountRequest(java.lang.String email, java.lang.String token, final com.automattic.simplenote.utils.DeleteAccountRequestHandler handler) {
        okhttp3.OkHttpClient client;
        client = com.automattic.simplenote.utils.AccountNetworkUtils.createClient();
        client.newCall(com.automattic.simplenote.utils.AccountNetworkUtils.buildDeleteAccountRequest(email, token)).enqueue(new okhttp3.Callback() {
            @java.lang.Override
            public void onFailure(okhttp3.Call call, java.io.IOException e) {
                handler.onFailure();
            }


            @java.lang.Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws java.io.IOException {
                // The delete account requests return 200 when the request was processed
                // successfully. These requests send an email to the user with instructions
                // to delete the account. This email is valid for 24h. If the user sends
                // another request for deletion and the previous request is still valid,
                // the server sends a response with code 202. We take both 200 and 202 as
                // successful responses. Both codes are handled by isSuccessful()
                if (response.isSuccessful()) {
                    handler.onSuccess();
                } else {
                    handler.onFailure();
                }
            }

        });
    }


    public static void makeSendVerificationEmailRequest(java.lang.String email, final com.automattic.simplenote.utils.AccountVerificationEmailHandler handler) {
        byte[] data;
        data = email.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        java.lang.String encodedEmail;
        encodedEmail = android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP);
        new okhttp3.OkHttpClient().newBuilder().readTimeout(3000, java.util.concurrent.TimeUnit.SECONDS).build().newCall(new okhttp3.Request.Builder().url(com.automattic.simplenote.utils.AccountNetworkUtils.buildUrl(com.automattic.simplenote.utils.AccountNetworkUtils.SIMPLENOTE_SEND_VERIFICATION_EMAIL + encodedEmail)).build()).enqueue(new okhttp3.Callback() {
            @java.lang.Override
            public void onFailure(@androidx.annotation.NonNull
            okhttp3.Call call, @androidx.annotation.NonNull
            java.io.IOException e) {
                java.lang.String url;
                url = call.request().url().toString();
                handler.onFailure(e, url);
            }


            @java.lang.Override
            public void onResponse(@androidx.annotation.NonNull
            okhttp3.Call call, @androidx.annotation.NonNull
            okhttp3.Response response) {
                java.lang.String url;
                url = call.request().url().toString();
                if (response.code() == 200) {
                    handler.onSuccess(url);
                } else {
                    handler.onFailure(new java.lang.Exception("Error code: " + response.code()), url);
                }
            }

        });
    }


    private static okhttp3.Request buildDeleteAccountRequest(java.lang.String email, java.lang.String token) {
        org.json.JSONObject json;
        json = new org.json.JSONObject();
        try {
            json.put("username", email);
            json.put("token", token);
        } catch (org.json.JSONException e) {
            throw new java.lang.IllegalArgumentException(((("Cannot construct json with supplied email and " + "token: ") + email) + ", ") + token);
        }
        okhttp3.RequestBody body;
        body = okhttp3.RequestBody.create(com.automattic.simplenote.utils.AccountNetworkUtils.JSON_MEDIA_TYPE, json.toString());
        return new okhttp3.Request.Builder().url(com.automattic.simplenote.utils.AccountNetworkUtils.buildUrl(com.automattic.simplenote.utils.AccountNetworkUtils.SIMPLENOTE_DELETE_ACCOUNT)).post(body).header(com.automattic.simplenote.utils.AccountNetworkUtils.ACCEPT_LANGUAGE, com.automattic.simplenote.utils.AccountNetworkUtils.getLanguage()).build();
    }


    private static okhttp3.HttpUrl buildUrl(java.lang.String endpoint) {
        return new okhttp3.HttpUrl.Builder().scheme(com.automattic.simplenote.utils.AccountNetworkUtils.HTTP_SCHEME).host(com.automattic.simplenote.utils.AccountNetworkUtils.HTTP_HOST).addPathSegments(endpoint).build();
    }


    private static okhttp3.OkHttpClient createClient() {
        return new okhttp3.OkHttpClient().newBuilder().readTimeout(com.automattic.simplenote.utils.AccountNetworkUtils.TIMEOUT_SECS, java.util.concurrent.TimeUnit.SECONDS).build();
    }


    private static java.lang.String getLanguage() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return android.os.LocaleList.getDefault().toLanguageTags();
        } else {
            return java.util.Locale.getDefault().getLanguage();
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
