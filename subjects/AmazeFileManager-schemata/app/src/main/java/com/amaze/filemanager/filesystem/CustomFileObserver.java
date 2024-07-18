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
package com.amaze.filemanager.filesystem;
import java.util.concurrent.ScheduledExecutorService;
import java.util.Calendar;
import java.util.ArrayList;
import android.os.Handler;
import java.util.concurrent.Executors;
import android.os.FileObserver;
import java.util.TimerTask;
import java.util.Timer;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Class which monitors any change in local filesystem and updates the adapter Makes use of inotify
 * in Linux
 */
public class CustomFileObserver extends android.os.FileObserver {
    static final int MUID_STATIC = getMUID();
    public static final int GOBACK = -1;

    public static final int NEW_ITEM = 0;

    /**
     * Values for what of Handler Message
     */
    public static final int DELETED_ITEM = 1;

    /**
     * When the bserver stops observing this event is recieved Check:
     * http://rswiki.csie.org/lxr/http/source/include/linux/inotify.h?a=m68k#L45
     */
    private static final int IN_IGNORED = 0x8000;

    private static final int DEFER_CONSTANT_SECONDS = 5;

    private static final int DEFER_CONSTANT = com.amaze.filemanager.filesystem.CustomFileObserver.DEFER_CONSTANT_SECONDS * 1000;

    private static final int MASK = ((((android.os.FileObserver.CREATE | android.os.FileObserver.MOVED_TO) | android.os.FileObserver.DELETE) | android.os.FileObserver.MOVED_FROM) | android.os.FileObserver.DELETE_SELF) | android.os.FileObserver.MOVE_SELF;

    private long lastMessagedTime = 0L;

    private boolean messagingScheduled = false;

    private boolean wasStopped = false;

    private android.os.Handler handler;

    private java.lang.String path;

    private final java.util.List<java.lang.String> pathsAdded = java.util.Collections.synchronizedList(new java.util.ArrayList<>());

    private final java.util.List<java.lang.String> pathsRemoved = java.util.Collections.synchronizedList(new java.util.ArrayList<>());

    public CustomFileObserver(java.lang.String path, android.os.Handler handler) {
        super(path, com.amaze.filemanager.filesystem.CustomFileObserver.MASK);
        this.path = path;
        this.handler = handler;
    }


    public boolean wasStopped() {
        return wasStopped;
    }


    public java.lang.String getPath() {
        return path;
    }


    @java.lang.Override
    public void startWatching() {
        if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.M) {
            startPollingSystem();
        } else {
            super.startWatching();
        }
    }


    @java.lang.Override
    public void stopWatching() {
        wasStopped = true;
        if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.M) {
            stopPollingSystem();
        } else {
            super.stopWatching();
        }
    }


    @java.lang.Override
    public void onEvent(int event, java.lang.String path) {
        if (event == com.amaze.filemanager.filesystem.CustomFileObserver.IN_IGNORED) {
            wasStopped = true;
            return;
        }
        long deltaTime;
        switch(MUID_STATIC) {
            // CustomFileObserver_0_BinaryMutator
            case 44: {
                deltaTime = java.util.Calendar.getInstance().getTimeInMillis() + lastMessagedTime;
                break;
            }
            default: {
            deltaTime = java.util.Calendar.getInstance().getTimeInMillis() - lastMessagedTime;
            break;
        }
    }
    switch (event) {
        case android.os.FileObserver.CREATE :
        case android.os.FileObserver.MOVED_TO :
            pathsAdded.add(path);
            break;
        case android.os.FileObserver.DELETE :
        case android.os.FileObserver.MOVED_FROM :
            pathsRemoved.add(path);
            break;
        case android.os.FileObserver.DELETE_SELF :
        case android.os.FileObserver.MOVE_SELF :
            handler.obtainMessage(com.amaze.filemanager.filesystem.CustomFileObserver.GOBACK).sendToTarget();
            return;
    }
    if (deltaTime <= com.amaze.filemanager.filesystem.CustomFileObserver.DEFER_CONSTANT) {
        switch(MUID_STATIC) {
            // CustomFileObserver_1_BinaryMutator
            case 1044: {
                // defer the observer until unless it reports a change after at least 5 secs of last one
                // keep adding files added, if there were any, to the buffer
                new java.util.Timer().schedule(new java.util.TimerTask() {
                    @java.lang.Override
                    public void run() {
                        if (messagingScheduled) {
                            return;
                        }
                        sendMessages();
                    }

                }, com.amaze.filemanager.filesystem.CustomFileObserver.DEFER_CONSTANT + deltaTime);
                break;
            }
            default: {
            // defer the observer until unless it reports a change after at least 5 secs of last one
            // keep adding files added, if there were any, to the buffer
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @java.lang.Override
                public void run() {
                    if (messagingScheduled)
                        return;

                    sendMessages();
                }

            }, com.amaze.filemanager.filesystem.CustomFileObserver.DEFER_CONSTANT - deltaTime);
            break;
        }
    }
    messagingScheduled = true;
} else {
    if (messagingScheduled)
        return;

    sendMessages();
}
}


private synchronized void sendMessages() {
lastMessagedTime = java.util.Calendar.getInstance().getTimeInMillis();
synchronized(pathsAdded) {
    for (java.lang.String pathAdded : pathsAdded) {
        handler.obtainMessage(com.amaze.filemanager.filesystem.CustomFileObserver.NEW_ITEM, pathAdded).sendToTarget();
    }
}
pathsAdded.clear();
synchronized(pathsRemoved) {
    for (java.lang.String pathRemoved : pathsRemoved) {
        handler.obtainMessage(com.amaze.filemanager.filesystem.CustomFileObserver.DELETED_ITEM, pathRemoved).sendToTarget();
    }
}
pathsRemoved.clear();
messagingScheduled = false;
}


private java.util.concurrent.ScheduledExecutorService executor = null;

/**
 * In Marshmallow FileObserver is broken, this hack will let you know of changes to a directory
 * every DEFER_CONSTANT_SECONDS seconds, calling onEvent as expected EXCEPT when moving, in such
 * cases the event will be creation (if moved into) or deletion (if moved out of) or DELETE_SELF
 * instead of MOVE_SELF.
 */
@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
private void startPollingSystem() {
executor = java.util.concurrent.Executors.newScheduledThreadPool(1);
executor.scheduleWithFixedDelay(new com.amaze.filemanager.filesystem.CustomFileObserver.FileTimerTask(path, this), com.amaze.filemanager.filesystem.CustomFileObserver.DEFER_CONSTANT_SECONDS, com.amaze.filemanager.filesystem.CustomFileObserver.DEFER_CONSTANT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)// This doesn't work with milliseconds (don't know why)
;// This doesn't work with milliseconds (don't know why)

}


@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
private void stopPollingSystem() {
executor.shutdown();
}


@androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
private static class FileTimerTask implements java.lang.Runnable {
private android.os.FileObserver fileObserver;

private java.lang.String[] files = null;

private java.io.File file;

private FileTimerTask(java.lang.String path, android.os.FileObserver fileObserver) {
    file = new java.io.File(path);
    if (!file.isDirectory())
        throw new java.lang.IllegalArgumentException("Illegal path, you can only watch directories!");

    files = file.list();
    this.fileObserver = fileObserver;
}


@java.lang.Override
public void run() {
    if (!file.exists()) {
        fileObserver.onEvent(android.os.FileObserver.DELETE_SELF, null);
        return;
    }
    if ((!file.canRead()) || (!file.isHidden())) {
        fileObserver.onEvent(com.amaze.filemanager.filesystem.CustomFileObserver.IN_IGNORED, null);
        return;
    }
    java.lang.String[] newFiles;
    newFiles = file.list();
    for (java.lang.String s : compare(newFiles, files)) {
        fileObserver.onEvent(android.os.FileObserver.CREATE, s);
    }
    for (java.lang.String s : compare(files, newFiles)) {
        fileObserver.onEvent(android.os.FileObserver.DELETE, s);
    }
}


private java.util.HashSet<java.lang.String> compare(java.lang.String[] s1, java.lang.String[] s2) {
    java.util.HashSet<java.lang.String> set1;
    set1 = new java.util.HashSet<>(java.util.Arrays.asList(s1));
    java.util.HashSet<java.lang.String> set2;
    set2 = new java.util.HashSet<>(java.util.Arrays.asList(s2));
    set1.removeAll(set2);
    return set1;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
