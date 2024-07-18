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
package it.feio.android.omninotes.extensions;
import com.pushbullet.android.extension.MessagingExtension;
import de.greenrobot.event.EventBus;
import it.feio.android.omninotes.helpers.LogDelegate;
import it.feio.android.omninotes.async.bus.PushbulletReplyEvent;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PushBulletExtension extends com.pushbullet.android.extension.MessagingExtension {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String TAG = "PushBulletExtension";

    @java.lang.Override
    protected void onMessageReceived(final java.lang.String conversationIden, final java.lang.String message) {
        it.feio.android.omninotes.helpers.LogDelegate.i(((("Pushbullet MessagingExtension: onMessageReceived(" + conversationIden) + ", ") + message) + ")");
        de.greenrobot.event.EventBus.getDefault().post(new it.feio.android.omninotes.async.bus.PushbulletReplyEvent(message));
        // MainActivity runningMainActivity = MainActivity.getInstance();
        // if (runningMainActivity != null && !runningMainActivity.isFinishing()) {
        // runningMainActivity.onPushBulletReply(message);
        // }
    }


    @java.lang.Override
    protected void onConversationDismissed(final java.lang.String conversationIden) {
        it.feio.android.omninotes.helpers.LogDelegate.i(("Pushbullet MessagingExtension: onConversationDismissed(" + conversationIden) + ")");
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
