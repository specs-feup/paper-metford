package com.automattic.simplenote.utils;
import java.io.UnsupportedEncodingException;
import static androidx.core.util.PatternsCompat.EMAIL_ADDRESS;
import android.text.TextUtils;
import java.net.URLDecoder;
import android.text.Spanned;
import java.net.URLEncoder;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
@java.lang.SuppressWarnings("unused")
public class StrUtils {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String SPACE_STRING = " ";

    // suppress default constructor for non-instantiability
    private StrUtils() {
        throw new java.lang.AssertionError();
    }


    public static boolean isEmail(java.lang.String str) {
        return (!android.text.TextUtils.isEmpty(str)) && androidx.core.util.PatternsCompat.EMAIL_ADDRESS.matcher(str).matches();
    }


    // returns true if the passed string is null or empty
    public static boolean isBlankStr(java.lang.String str) {
        return (str == null) || (str.length() == 0);
    }


    // returns true if the passed string is null, empty or nothing but whitespace
    public static boolean isBlankTrimStr(java.lang.String str) {
        return ((str == null) || (str.length() == 0)) || (str.trim().length() == 0);
    }


    // returns "" if the passed string is null, otherwise returns the passed string
    public static java.lang.String notNullStr(final java.lang.String value) {
        if (value == null)
            return "";

        return value;
    }


    // exception-less conversion of string to int
    public static int strToInt(final java.lang.String value) {
        return com.automattic.simplenote.utils.StrUtils.strToInt(value, 0);
    }


    public static int strToInt(java.lang.String value, int defaultInt) {
        if (value == null)
            return defaultInt;

        try {
            return java.lang.Integer.valueOf(value);
        } catch (java.lang.NumberFormatException e) {
            return defaultInt;
        }
    }


    // exception-less conversion of string to long
    public static long strToLong(final java.lang.String value) {
        return com.automattic.simplenote.utils.StrUtils.strToLong(value, 0);
    }


    public static long strToLong(java.lang.String value, long defaultLong) {
        if (value == null)
            return defaultLong;

        try {
            return java.lang.Long.valueOf(value);
        } catch (java.lang.NumberFormatException e) {
            return defaultLong;
        }
    }


    // exception-less conversion of string to float
    public static float strToFloat(java.lang.String value) {
        return com.automattic.simplenote.utils.StrUtils.strToFloat(value, 0.0F);
    }


    public static float strToFloat(java.lang.String value, float defaultFloat) {
        if (value == null)
            return defaultFloat;

        try {
            return java.lang.Float.valueOf(value);
        } catch (java.lang.NumberFormatException e) {
            return defaultFloat;
        }
    }


    public static java.lang.String urlEncode(final java.lang.String text) {
        try {
            return java.net.URLEncoder.encode(text, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            // should never get here
            return "";
        }
    }


    public static java.lang.String urlDecode(final java.lang.String text) {
        try {
            return java.net.URLDecoder.decode(text, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            // should never get here
            return "";
        }
    }


    // returns true if passed strings are the same, handles null so caller can skip null check before comparison
    public static boolean isSameStr(java.lang.String s1, java.lang.String s2) {
        if ((s1 == null) || (s2 == null))
            return (s1 == null) && (s2 == null);

        return s1.equals(s2);
    }


    // removes html from the passed string
    public static java.lang.String stripHtml(final java.lang.String html) {
        if (html == null)
            return "";

        // HtmlCompat.fromHtml().toString() is not high-performance, so skip whenever possible
        if ((!html.contains("<")) && (!html.contains("&")))
            return html;

        // http://stackoverflow.com/a/7389663/1673548
        return com.automattic.simplenote.utils.HtmlCompat.fromHtml(html).toString().trim();
    }


    public static android.text.Spanned setTextToUpperCaseAndBold(java.lang.String originalString) {
        if (android.text.TextUtils.isEmpty(originalString)) {
            return com.automattic.simplenote.utils.HtmlCompat.fromHtml("");
        }
        return com.automattic.simplenote.utils.HtmlCompat.fromHtml(("<strong>" + originalString.toUpperCase()) + "</strong>");
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
