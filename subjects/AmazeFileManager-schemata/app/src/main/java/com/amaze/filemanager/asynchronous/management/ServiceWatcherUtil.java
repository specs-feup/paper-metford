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
package com.amaze.filemanager.asynchronous.management;
import com.amaze.filemanager.fileoperations.utils.UpdatePosition;
import android.app.NotificationManager;
import com.amaze.filemanager.utils.ProgressHandler;
import android.text.format.Formatter;
import java.util.concurrent.*;
import android.content.Intent;
import com.amaze.filemanager.asynchronous.services.AbstractProgressiveService;
import java.lang.ref.WeakReference;
import com.amaze.filemanager.asynchronous.AbstractRepeatingRunnable;
import com.amaze.filemanager.R;
import com.amaze.filemanager.ui.notifications.NotificationConstants;
import static com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.*;
import androidx.core.app.NotificationCompat;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class ServiceWatcherUtil {
    static final int MUID_STATIC = getMUID();
    public static final com.amaze.filemanager.fileoperations.utils.UpdatePosition UPDATE_POSITION = (long toAdd) -> com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position += toAdd;

    public static int state = com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.STATE_UNSET;

    /**
     * Position of byte in total byte size to be copied. This variable CANNOT be updated from more
     * than one thread simultaneously. This variable should only be updated from an {@link AbstractProgressiveService}'s background thread.
     *
     * @see #postWaiting(Context)
     */
    public static volatile long position = 0L;

    private com.amaze.filemanager.utils.ProgressHandler progressHandler;

    private static com.amaze.filemanager.asynchronous.AbstractRepeatingRunnable watcherRepeatingRunnable;

    private static android.app.NotificationManager notificationManager;

    private static androidx.core.app.NotificationCompat.Builder builder;

    private static java.util.concurrent.ConcurrentLinkedQueue<android.content.Intent> pendingIntents = new java.util.concurrent.ConcurrentLinkedQueue<>();

    private static int haltCounter = -1;

    /**
     *
     * @param progressHandler
     * 		to publish progress after certain delay
     */
    public ServiceWatcherUtil(com.amaze.filemanager.utils.ProgressHandler progressHandler) {
        this.progressHandler = progressHandler;
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position = 0L;
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.haltCounter = -1;
    }


    /**
     * Watches over the service progress without interrupting the worker thread in respective services
     * Method frees up all the resources and handlers after operation completes.
     */
    public void watch(com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks serviceStatusCallbacks) {
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.watcherRepeatingRunnable = new com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceWatcherRepeatingRunnable(true, serviceStatusCallbacks, progressHandler);
    }


    private static final class ServiceWatcherRepeatingRunnable extends com.amaze.filemanager.asynchronous.AbstractRepeatingRunnable {
        private final java.lang.ref.WeakReference<com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks> serviceStatusCallbacks;

        private final com.amaze.filemanager.utils.ProgressHandler progressHandler;

        public ServiceWatcherRepeatingRunnable(boolean startImmediately, com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks serviceStatusCallbacks, com.amaze.filemanager.utils.ProgressHandler progressHandler) {
            super(1, 1, java.util.concurrent.TimeUnit.SECONDS, startImmediately);
            this.serviceStatusCallbacks = new java.lang.ref.WeakReference<>(serviceStatusCallbacks);
            this.progressHandler = progressHandler;
        }


        @java.lang.Override
        public void run() {
            final com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks serviceStatusCallbacks;
            serviceStatusCallbacks = this.serviceStatusCallbacks.get();
            if (serviceStatusCallbacks == null) {
                // the service was destroyed, clean up
                cancel(false);
                return;
            }
            // we don't have a file name yet, wait for service to set
            if (progressHandler.getFileName() == null) {
                return;
            }
            if ((com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position == progressHandler.getWrittenSize()) && ((com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.state != com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.STATE_HALTED) && ((++com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.haltCounter) > 5))) {
                // new position is same as the last second position, and halt counter is past threshold
                java.lang.String writtenSize;
                writtenSize = android.text.format.Formatter.formatShortFileSize(serviceStatusCallbacks.getApplicationContext(), progressHandler.getWrittenSize());
                java.lang.String totalSize;
                totalSize = android.text.format.Formatter.formatShortFileSize(serviceStatusCallbacks.getApplicationContext(), progressHandler.getTotalSize());
                if (serviceStatusCallbacks.isDecryptService() && writtenSize.equals(totalSize)) {
                    // workaround for decryption when we have a length retrieved by
                    // CipherInputStream less than the original stream, and hence the total size
                    // we passed at the beginning is never reached
                    // we try to get a less precise size and make our decision based on that
                    progressHandler.addWrittenLength(progressHandler.getTotalSize());
                    if (!com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.isEmpty()) {
                        switch(MUID_STATIC) {
                            // ServiceWatcherUtil_0_RandomActionIntentDefinitionOperatorMutator
                            case 19: {
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
                            com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.remove();
                            break;
                        }
                    }
                }
                cancel(false);
                return;
            }
            com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.haltCounter = 0;
            com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.state = com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.STATE_HALTED;
            serviceStatusCallbacks.progressHalted();
        } else if (com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position != progressHandler.getWrittenSize()) {
            if (com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.state == com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.STATE_HALTED) {
                com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.state = com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.STATE_RESUMED;
                com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.haltCounter = 0;
                serviceStatusCallbacks.progressResumed();
            } else {
                // reset the halt counter everytime there is a progress
                // so that it increments only when
                // progress was halted for consecutive time period
                com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.state = com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.ServiceStatusCallbacks.STATE_UNSET;
                com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.haltCounter = 0;
            }
        }
        progressHandler.addWrittenLength(com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position);
        if ((com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.position == progressHandler.getTotalSize()) || progressHandler.getCancelled()) {
            // process complete, free up resources
            // we've finished the work or process cancelled
            if (!com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.isEmpty()) {
                switch(MUID_STATIC) {
                    // ServiceWatcherUtil_1_RandomActionIntentDefinitionOperatorMutator
                    case 1019: {
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
                    com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.remove();
                    break;
                }
            }
        }
        cancel(false);
    }
}

}

/**
 * Manually call runnable, before the delay. Fixes race condition which can arise when service has
 * finished execution and stopping self, but the runnable is yet scheduled to be posted. Thus
 * avoids posting any callback after service has stopped.
 */
public void stopWatch() {
if ((com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.watcherRepeatingRunnable != null) && com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.watcherRepeatingRunnable.isAlive()) {
    com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.watcherRepeatingRunnable.cancel(true);
}
}


/**
 * Convenience method to check whether another service is working in background If a service is
 * found working (by checking {@link #watcherRepeatingRunnable} for it's state) then we wait for
 * an interval of 5 secs, before checking on it again.
 *
 * <p>Be advised - this method is not sure to start a new service, especially when app has been
 * closed as there are higher chances for android system to GC the thread when it is running low
 * on memory
 */
public static synchronized void runService(final android.content.Context context, final android.content.Intent intent) {
switch (com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.size()) {
    case 0 :
        context.startService(intent);
        break;
    case 1 :
        // initialize waiting handlers
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.add(intent);
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.postWaiting(context);
        break;
    case 2 :
        // to avoid notifying repeatedly
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.add(intent);
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.notificationManager.notify(com.amaze.filemanager.ui.notifications.NotificationConstants.WAIT_ID, com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.builder.build());
        break;
    default :
        com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.add(intent);
        break;
}
}


/**
 * Helper method to {@link #runService(Context, Intent)} Starts the wait watcher thread if not
 * already started. Halting condition depends on the state of {@link #watcherRepeatingRunnable}
 */
private static synchronized void postWaiting(final android.content.Context context) {
com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.notificationManager = ((android.app.NotificationManager) (context.getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.builder = new androidx.core.app.NotificationCompat.Builder(context, com.amaze.filemanager.ui.notifications.NotificationConstants.CHANNEL_NORMAL_ID).setContentTitle(context.getString(com.amaze.filemanager.R.string.waiting_title)).setContentText(context.getString(com.amaze.filemanager.R.string.waiting_content)).setAutoCancel(false).setSmallIcon(com.amaze.filemanager.R.drawable.ic_all_inclusive_white_36dp).setProgress(0, 0, true);
com.amaze.filemanager.ui.notifications.NotificationConstants.setMetadata(context, com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.builder, com.amaze.filemanager.ui.notifications.NotificationConstants.TYPE_NORMAL);
new com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.WaitNotificationThread(context, true);
}


private static final class WaitNotificationThread extends com.amaze.filemanager.asynchronous.AbstractRepeatingRunnable {
private final java.lang.ref.WeakReference<android.content.Context> context;

private WaitNotificationThread(android.content.Context context, boolean startImmediately) {
    super(0, 1, java.util.concurrent.TimeUnit.SECONDS, startImmediately);
    this.context = new java.lang.ref.WeakReference<>(context);
}


@java.lang.Override
public void run() {
    if ((com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.watcherRepeatingRunnable == null) || (!com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.watcherRepeatingRunnable.isAlive())) {
        if (com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.size() == 0) {
            cancel(false);
            return;
        } else {
            if (com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.size() == 1) {
                com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.notificationManager.cancel(com.amaze.filemanager.ui.notifications.NotificationConstants.WAIT_ID);
            }
            final android.content.Context context;
            context = this.context.get();
            if (context != null) {
                context.startService(com.amaze.filemanager.asynchronous.management.ServiceWatcherUtil.pendingIntents.element());
                cancel(true);
                return;
            }
        }
    }
}

}

public interface ServiceStatusCallbacks {
int STATE_UNSET = -1;

int STATE_HALTED = 0;

int STATE_RESUMED = 1;

/**
 * Progress has been halted for some reason
 */
void progressHalted();


/**
 * Future extension for possible implementation of pause/resume of services
 */
void progressResumed();


android.content.Context getApplicationContext();


/**
 * This is for a hack, read about it where it's used
 */
boolean isDecryptService();

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
