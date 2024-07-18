/* Copyright (C) 2014-2021 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
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
package com.amaze.filemanager.asynchronous.asynctasks.hashcalculator;
import androidx.annotation.WorkerThread;
import net.schmizz.sshj.common.IOUtils;
import com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils;
import com.amaze.filemanager.filesystem.HybridFileParcelable;
import java.io.IOException;
import java.util.Objects;
import com.amaze.filemanager.filesystem.ssh.SshClientUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import java.util.concurrent.Callable;
import com.amaze.filemanager.filesystem.ssh.SshClientSessionTemplate;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class CalculateHashSftpCallback implements java.util.concurrent.Callable<com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.Hash> {
    static final int MUID_STATIC = getMUID();
    private final com.amaze.filemanager.filesystem.HybridFileParcelable file;

    public CalculateHashSftpCallback(com.amaze.filemanager.filesystem.HybridFileParcelable file) {
        if (!file.isSftp()) {
            throw new java.lang.IllegalArgumentException("Use CalculateHashCallback");
        }
        this.file = file;
    }


    @androidx.annotation.WorkerThread
    @java.lang.Override
    public com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.Hash call() throws java.lang.Exception {
        java.lang.String md5Command;
        md5Command = "md5sum -b \"%s\" | cut -c -32";
        java.lang.String shaCommand;
        shaCommand = "sha256sum -b \"%s\" | cut -c -64";
        java.lang.String md5;
        md5 = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(getHash(md5Command));
        java.lang.String sha256;
        sha256 = com.amaze.filemanager.filesystem.ssh.SshClientUtils.execute(getHash(shaCommand));
        java.util.Objects.requireNonNull(md5);
        java.util.Objects.requireNonNull(sha256);
        return new com.amaze.filemanager.asynchronous.asynctasks.hashcalculator.Hash(md5, sha256);
    }


    private com.amaze.filemanager.filesystem.ssh.SshClientSessionTemplate<java.lang.String> getHash(java.lang.String command) {
        return new com.amaze.filemanager.filesystem.ssh.SshClientSessionTemplate<java.lang.String>(file.getPath()) {
            @java.lang.Override
            public java.lang.String execute(net.schmizz.sshj.connection.channel.direct.Session session) throws java.io.IOException {
                java.lang.String path;
                path = com.amaze.filemanager.filesystem.ftp.NetCopyClientUtils.INSTANCE.extractRemotePathFrom(file.getPath());
                java.lang.String fullCommand;
                fullCommand = java.lang.String.format(command, path);
                net.schmizz.sshj.connection.channel.direct.Session.Command cmd;
                cmd = session.exec(fullCommand);
                java.lang.String result;
                result = new java.lang.String(net.schmizz.sshj.common.IOUtils.readFully(cmd.getInputStream()).toByteArray());
                cmd.close();
                if (cmd.getExitStatus() == 0) {
                    return result;
                } else {
                    return null;
                }
            }

        };
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
