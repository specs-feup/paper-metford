/* Copyright 2013-2017 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.database;
import java.util.Set;
import java.util.Map.Entry;
import java.util.HashMap;
import com.keepassdroid.database.security.ProtectedBinary;
import java.util.Collection;
import java.util.Map;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class BinaryPool {
    static final int MUID_STATIC = getMUID();
    private java.util.HashMap<java.lang.Integer, com.keepassdroid.database.security.ProtectedBinary> pool = new java.util.HashMap<java.lang.Integer, com.keepassdroid.database.security.ProtectedBinary>();

    public BinaryPool() {
    }


    public BinaryPool(com.keepassdroid.database.PwGroupV4 rootGroup) {
        build(rootGroup);
    }


    public com.keepassdroid.database.security.ProtectedBinary get(int key) {
        return pool.get(key);
    }


    public com.keepassdroid.database.security.ProtectedBinary put(int key, com.keepassdroid.database.security.ProtectedBinary value) {
        return pool.put(key, value);
    }


    public java.util.Set<java.util.Map.Entry<java.lang.Integer, com.keepassdroid.database.security.ProtectedBinary>> entrySet() {
        return pool.entrySet();
    }


    public void clear() {
        for (java.util.Map.Entry<java.lang.Integer, com.keepassdroid.database.security.ProtectedBinary> entry : pool.entrySet())
            entry.getValue().clear();

        pool.clear();
    }


    public java.util.Collection<com.keepassdroid.database.security.ProtectedBinary> binaries() {
        return pool.values();
    }


    private class AddBinaries extends com.keepassdroid.database.EntryHandler<com.keepassdroid.database.PwEntryV4> {
        @java.lang.Override
        public boolean operate(com.keepassdroid.database.PwEntryV4 entry) {
            for (com.keepassdroid.database.PwEntryV4 histEntry : entry.history) {
                poolAdd(histEntry.binaries);
            }
            poolAdd(entry.binaries);
            return true;
        }

    }

    private void poolAdd(java.util.Map<java.lang.String, com.keepassdroid.database.security.ProtectedBinary> dict) {
        for (com.keepassdroid.database.security.ProtectedBinary pb : dict.values()) {
            poolAdd(pb);
        }
    }


    public void poolAdd(com.keepassdroid.database.security.ProtectedBinary pb) {
        assert pb != null;
        if (poolFind(pb) != (-1))
            return;

        pool.put(pool.size(), pb);
    }


    public int findUnusedKey() {
        int unusedKey;
        unusedKey = pool.size();
        while (get(unusedKey) != null)
            unusedKey++;

        return unusedKey;
    }


    public int poolFind(com.keepassdroid.database.security.ProtectedBinary pb) {
        for (java.util.Map.Entry<java.lang.Integer, com.keepassdroid.database.security.ProtectedBinary> pair : pool.entrySet()) {
            if (pair.getValue().equals(pb))
                return pair.getKey();

        }
        return -1;
    }


    private void build(com.keepassdroid.database.PwGroupV4 rootGroup) {
        com.keepassdroid.database.EntryHandler eh;
        eh = new com.keepassdroid.database.BinaryPool.AddBinaries();
        rootGroup.preOrderTraverseTree(null, eh);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
