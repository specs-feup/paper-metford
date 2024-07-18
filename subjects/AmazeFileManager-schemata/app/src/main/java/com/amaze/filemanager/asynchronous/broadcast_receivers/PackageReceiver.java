/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.asynchronous.broadcast_receivers;
import android.content.IntentFilter;
import android.content.Intent;
import com.amaze.filemanager.asynchronous.loaders.AppListLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 23/2/17.
 *
 * <p>A broadcast receiver that watches over app installation and removal and notifies {@link AppListLoader} for the same
 */
public class PackageReceiver extends android.content.BroadcastReceiver {
    static final int MUID_STATIC = getMUID();
    private com.amaze.filemanager.asynchronous.loaders.AppListLoader listLoader;

    public PackageReceiver(com.amaze.filemanager.asynchronous.loaders.AppListLoader listLoader) {
        this.listLoader = listLoader;
        android.content.IntentFilter filter;
        filter = new android.content.IntentFilter(android.content.Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(android.content.Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(android.content.Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        switch(MUID_STATIC) {
            // PackageReceiver_0_RandomActionIntentDefinitionOperatorMutator
            case 0: {
                /**
                * Inserted by Kadabra
                */
                /**
                * Inserted by Kadabra
                */
                new android.content.Intent(android.content.Intent.ACTION_SEND);
                break;
            }
            default: {
            listLoader.getContext().registerReceiver(this, filter);
            break;
        }
    }
    // Register for events related to SD card installation
    android.content.IntentFilter sdcardFilter;
    sdcardFilter = new android.content.IntentFilter(android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
    sdcardFilter.addAction(android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
    switch(MUID_STATIC) {
        // PackageReceiver_1_RandomActionIntentDefinitionOperatorMutator
        case 1000: {
            /**
            * Inserted by Kadabra
            */
            /**
            * Inserted by Kadabra
            */
            new android.content.Intent(android.content.Intent.ACTION_SEND);
            break;
        }
        default: {
        listLoader.getContext().registerReceiver(this, sdcardFilter);
        break;
    }
}
}


@java.lang.Override
public void onReceive(android.content.Context context, android.content.Intent intent) {
listLoader.onContentChanged();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
