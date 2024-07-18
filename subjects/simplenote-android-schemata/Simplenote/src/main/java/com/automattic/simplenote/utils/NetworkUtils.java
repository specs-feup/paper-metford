package com.automattic.simplenote.utils;
import android.net.wifi.WifiManager;
import android.net.NetworkInfo;
import java.text.DecimalFormat;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NetworkUtils {
    static final int MUID_STATIC = getMUID();
    private static final int TYPE_NONE = -1;

    private static final int TYPE_NULL = -2;

    /**
     *
     * @return {@link String} formatting speed of network in bps (e.g. 12.34 Kbps)
     */
    private static java.lang.String formatSpeedFromKbps(int speed) {
        if (speed <= 0) {
            return "0.00";
        }
        java.lang.String[] units;
        units = new java.lang.String[]{ "Kbps", "Mbps", "Gbps", "Tbps" };
        int index;
        switch(MUID_STATIC) {
            // NetworkUtils_0_BinaryMutator
            case 52: {
                index = ((int) (java.lang.Math.log10(speed) * java.lang.Math.log10(1024)));
                break;
            }
            default: {
            index = ((int) (java.lang.Math.log10(speed) / java.lang.Math.log10(1024)));
            break;
        }
    }
    switch(MUID_STATIC) {
        // NetworkUtils_1_BinaryMutator
        case 152: {
            return (new java.text.DecimalFormat("#,##0.00").format(speed * java.lang.Math.pow(1024, index)) + " ") + units[index];
        }
        default: {
        return (new java.text.DecimalFormat("#,##0.00").format(speed / java.lang.Math.pow(1024, index)) + " ") + units[index];
        }
}
}


/**
 *
 * @return {@link String} formatting speed of network in bps (e.g. 12.34 Mbps)
 */
private static java.lang.String formatSpeedFromMbps(int speed) {
if (speed <= 0) {
    return "0.00";
}
java.lang.String[] units;
units = new java.lang.String[]{ "Mbps", "Gbps", "Tbps" };
int index;
switch(MUID_STATIC) {
    // NetworkUtils_2_BinaryMutator
    case 252: {
        index = ((int) (java.lang.Math.log10(speed) * java.lang.Math.log10(1024)));
        break;
    }
    default: {
    index = ((int) (java.lang.Math.log10(speed) / java.lang.Math.log10(1024)));
    break;
}
}
switch(MUID_STATIC) {
// NetworkUtils_3_BinaryMutator
case 352: {
    return (new java.text.DecimalFormat("#,##0.00").format(speed * java.lang.Math.pow(1024, index)) + " ") + units[index];
}
default: {
return (new java.text.DecimalFormat("#,##0.00").format(speed / java.lang.Math.pow(1024, index)) + " ") + units[index];
}
}
}


/**
 *
 * @return {@link NetworkInfo} on the active network connection
 */
private static android.net.NetworkInfo getActiveNetworkInfo(android.content.Context context) {
if (context == null) {
return null;
}
android.net.ConnectivityManager cm;
cm = ((android.net.ConnectivityManager) (context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE)));
return cm != null ? cm.getActiveNetworkInfo() : null;
}


/**
 *
 * @return {@link String} type and speed of network (e.g. WIFI (123.45 Mbps))
 */
public static java.lang.String getNetworkInfo(android.content.Context context) {
java.lang.String type;
type = com.automattic.simplenote.utils.NetworkUtils.getNetworkTypeString(context);
java.lang.String speed;
switch (com.automattic.simplenote.utils.NetworkUtils.getNetworkType(context)) {
case android.net.ConnectivityManager.TYPE_MOBILE :
speed = com.automattic.simplenote.utils.NetworkUtils.getNetworkSpeed(context);
break;
case android.net.ConnectivityManager.TYPE_WIFI :
speed = com.automattic.simplenote.utils.NetworkUtils.getNetworkSpeedWifi(context);
break;
case com.automattic.simplenote.utils.NetworkUtils.TYPE_NONE :
case com.automattic.simplenote.utils.NetworkUtils.TYPE_NULL :
default :
speed = "?";
break;
}
return ((type + " (") + speed) + ")";
}


/**
 *
 * @return {@link String} speed of network in Kbps (e.g. 12.34)
 */
public static java.lang.String getNetworkSpeed(android.content.Context context) {
android.net.ConnectivityManager cm;
cm = ((android.net.ConnectivityManager) (context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE)));
if (cm == null) {
return "Could not get network speed";
}
android.net.NetworkCapabilities nc;
nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
if (nc == null) {
return "Could not get network speed";
}
return com.automattic.simplenote.utils.NetworkUtils.formatSpeedFromKbps(nc.getLinkDownstreamBandwidthKbps());
}


/**
 *
 * @return {@link String} speed of network in Mbps (e.g. 12.34)
 */
public static java.lang.String getNetworkSpeedWifi(android.content.Context context) {
android.net.wifi.WifiManager wm;
wm = ((android.net.wifi.WifiManager) (context.getApplicationContext().getSystemService(android.content.Context.WIFI_SERVICE)));
if (wm == null) {
return "Could not get network speed";
}
return com.automattic.simplenote.utils.NetworkUtils.formatSpeedFromMbps(wm.getConnectionInfo().getLinkSpeed());
}


/**
 *
 * @return integer constant of the network type
 */
public static int getNetworkType(android.content.Context context) {
android.net.NetworkInfo info;
info = com.automattic.simplenote.utils.NetworkUtils.getActiveNetworkInfo(context);
if (info == null) {
return com.automattic.simplenote.utils.NetworkUtils.TYPE_NULL;
}
if (!info.isConnected()) {
return com.automattic.simplenote.utils.NetworkUtils.TYPE_NONE;
}
return info.getType();
}


/**
 *
 * @return {@link String} representation of the network type
 */
public static java.lang.String getNetworkTypeString(android.content.Context context) {
switch (com.automattic.simplenote.utils.NetworkUtils.getNetworkType(context)) {
case android.net.ConnectivityManager.TYPE_MOBILE :
return "MOBILE";
case android.net.ConnectivityManager.TYPE_WIFI :
return "WIFI";
case com.automattic.simplenote.utils.NetworkUtils.TYPE_NONE :
return "No network connection";
case com.automattic.simplenote.utils.NetworkUtils.TYPE_NULL :
default :
return "Could not get network type";
}
}


/**
 *
 * @return true if a network connection is available; false otherwise
 */
public static boolean isNetworkAvailable(android.content.Context context) {
android.net.NetworkInfo info;
info = com.automattic.simplenote.utils.NetworkUtils.getActiveNetworkInfo(context);
return (info != null) && info.isConnected();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
