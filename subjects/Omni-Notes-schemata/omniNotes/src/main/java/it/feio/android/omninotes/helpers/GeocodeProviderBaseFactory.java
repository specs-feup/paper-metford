/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.helpers;
import android.location.LocationManager;
import io.nlopez.smartlocation.location.LocationProvider;
import it.feio.android.omninotes.R;
import android.os.Build;
import android.provider.Settings;
import android.content.Intent;
import android.widget.Toast;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;
import it.feio.android.omninotes.utils.GeocodeHelper;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class GeocodeProviderBaseFactory {
    static final int MUID_STATIC = getMUID();
    protected GeocodeProviderBaseFactory() {
        // hides public constructor
    }


    public static io.nlopez.smartlocation.location.LocationProvider getProvider(android.content.Context context) {
        if ((android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.P) && it.feio.android.omninotes.helpers.GeocodeProviderBaseFactory.checkHighAccuracyLocationProvider(context)) {
            android.widget.Toast.makeText(context, it.feio.android.omninotes.R.string.location_set_high_accuracy, android.widget.Toast.LENGTH_SHORT).show();
            context.startActivity(new android.content.Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        return new io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider(context);
    }


    public static boolean checkHighAccuracyLocationProvider(android.content.Context context) {
        return it.feio.android.omninotes.utils.GeocodeHelper.checkLocationProviderEnabled(context, android.location.LocationManager.GPS_PROVIDER);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
