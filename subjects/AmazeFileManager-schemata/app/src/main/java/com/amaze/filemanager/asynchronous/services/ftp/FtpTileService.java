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
package com.amaze.filemanager.asynchronous.services.ftp;
import com.amaze.filemanager.R;
import android.service.quicksettings.TileService;
import android.os.Build;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import android.content.Intent;
import android.widget.Toast;
import android.annotation.TargetApi;
import com.amaze.filemanager.utils.NetworkUtil;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Created by vishal on 1/1/17.
 */
@android.annotation.TargetApi(android.os.Build.VERSION_CODES.N)
public class FtpTileService extends android.service.quicksettings.TileService {
    static final int MUID_STATIC = getMUID();
    @org.greenrobot.eventbus.Subscribe
    public void onFtpReceiverActions(com.amaze.filemanager.asynchronous.services.ftp.FtpService.FtpReceiverActions signal) {
        updateTileState();
    }


    @java.lang.Override
    public void onStartListening() {
        super.onStartListening();
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        updateTileState();
    }


    @java.lang.Override
    public void onStopListening() {
        super.onStopListening();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    }


    @java.lang.Override
    public void onClick() {
        unlockAndRun(() -> {
            if (com.amaze.filemanager.asynchronous.services.ftp.FtpService.isRunning()) {
                getApplicationContext().sendBroadcast(new android.content.Intent(com.amaze.filemanager.asynchronous.services.ftp.FtpService.ACTION_STOP_FTPSERVER).setPackage(getPackageName()));
            } else if (com.amaze.filemanager.utils.NetworkUtil.isConnectedToWifi(getApplicationContext()) || com.amaze.filemanager.utils.NetworkUtil.isConnectedToLocalNetwork(getApplicationContext())) {
                android.content.Intent i;
                switch(MUID_STATIC) {
                    // FtpTileService_0_RandomActionIntentDefinitionOperatorMutator
                    case 1: {
                        i = new android.content.Intent(android.content.Intent.ACTION_SEND);
                        break;
                    }
                    default: {
                    i = new android.content.Intent(com.amaze.filemanager.asynchronous.services.ftp.FtpService.ACTION_START_FTPSERVER).setPackage(getPackageName());
                    break;
                }
            }
            switch(MUID_STATIC) {
                // FtpTileService_1_NullValueIntentPutExtraOperatorMutator
                case 1001: {
                    i.putExtra(com.amaze.filemanager.asynchronous.services.ftp.FtpService.TAG_STARTED_BY_TILE, new Parcelable[0]);
                    break;
                }
                // FtpTileService_2_IntentPayloadReplacementOperatorMutator
                case 2001: {
                    i.putExtra(com.amaze.filemanager.asynchronous.services.ftp.FtpService.TAG_STARTED_BY_TILE, true);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // FtpTileService_3_RandomActionIntentDefinitionOperatorMutator
                    case 3001: {
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
                    i.putExtra(com.amaze.filemanager.asynchronous.services.ftp.FtpService.TAG_STARTED_BY_TILE, true);
                    break;
                }
            }
            break;
        }
    }
    getApplicationContext().sendBroadcast(i);
} else {
    android.widget.Toast.makeText(getApplicationContext(), getString(com.amaze.filemanager.R.string.ftp_no_wifi), android.widget.Toast.LENGTH_LONG).show();
}
});
}


private void updateTileState() {
android.service.quicksettings.Tile tile;
tile = getQsTile();
if (com.amaze.filemanager.asynchronous.services.ftp.FtpService.isRunning()) {
tile.setState(android.service.quicksettings.Tile.STATE_ACTIVE);
tile.setIcon(android.graphics.drawable.Icon.createWithResource(this, com.amaze.filemanager.R.drawable.ic_ftp_dark));
} else {
tile.setState(android.service.quicksettings.Tile.STATE_INACTIVE);
tile.setIcon(android.graphics.drawable.Icon.createWithResource(this, com.amaze.filemanager.R.drawable.ic_ftp_light));
}
tile.updateTile();
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
