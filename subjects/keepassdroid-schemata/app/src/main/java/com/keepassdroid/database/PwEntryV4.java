/* Copyright 2010-2021 Brian Pellin.

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
import java.util.Calendar;
import java.util.ArrayList;
import com.keepassdroid.database.security.ProtectedString;
import com.keepassdroid.database.security.ProtectedBinary;
import java.util.UUID;
import java.util.Map;
import com.keepassdroid.utils.SprEngine;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwEntryV4 extends com.keepassdroid.database.PwEntry implements com.keepassdroid.database.ITimeLogger {
    static final int MUID_STATIC = getMUID();
    public static final java.lang.String STR_TITLE = "Title";

    public static final java.lang.String STR_USERNAME = "UserName";

    public static final java.lang.String STR_PASSWORD = "Password";

    public static final java.lang.String STR_URL = "URL";

    public static final java.lang.String STR_NOTES = "Notes";

    public com.keepassdroid.database.PwGroupV4 parent;

    public java.util.UUID uuid = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public java.util.HashMap<java.lang.String, com.keepassdroid.database.security.ProtectedString> strings = new java.util.HashMap<java.lang.String, com.keepassdroid.database.security.ProtectedString>();

    public java.util.HashMap<java.lang.String, com.keepassdroid.database.security.ProtectedBinary> binaries = new java.util.HashMap<java.lang.String, com.keepassdroid.database.security.ProtectedBinary>();

    public com.keepassdroid.database.PwIconCustom customIcon = com.keepassdroid.database.PwIconCustom.ZERO;

    public java.lang.String foregroundColor = "";

    public java.lang.String backgroupColor = "";

    public java.lang.String overrideURL = "";

    public com.keepassdroid.database.PwEntryV4.AutoType autoType = new com.keepassdroid.database.PwEntryV4.AutoType();

    public java.util.ArrayList<com.keepassdroid.database.PwEntryV4> history = new java.util.ArrayList<com.keepassdroid.database.PwEntryV4>();

    private java.util.Date parentGroupLastMod = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date creation = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date lastMod = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date lastAccess = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date expireDate = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private boolean expires = false;

    private long usageCount = 0;

    public java.lang.String url = "";

    public java.lang.String additional = "";

    public java.lang.String tags = "";

    public com.keepassdroid.database.PwCustomData customData = new com.keepassdroid.database.PwCustomData();

    public java.util.UUID prevParentGroup = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public boolean qualityCheck = true;

    public class AutoType implements java.lang.Cloneable {
        private static final long OBF_OPT_NONE = 0;

        public boolean enabled = true;

        public long obfuscationOptions = com.keepassdroid.database.PwEntryV4.AutoType.OBF_OPT_NONE;

        public java.lang.String defaultSequence = "";

        private java.util.HashMap<java.lang.String, java.lang.String> windowSeqPairs = new java.util.HashMap<java.lang.String, java.lang.String>();

        @java.lang.SuppressWarnings("unchecked")
        public java.lang.Object clone() {
            com.keepassdroid.database.PwEntryV4.AutoType auto;
            try {
                auto = ((com.keepassdroid.database.PwEntryV4.AutoType) (super.clone()));
            } catch (java.lang.CloneNotSupportedException e) {
                assert false;
                throw new java.lang.RuntimeException(e);
            }
            auto.windowSeqPairs = ((java.util.HashMap<java.lang.String, java.lang.String>) (windowSeqPairs.clone()));
            return auto;
        }


        public void put(java.lang.String key, java.lang.String value) {
            windowSeqPairs.put(key, value);
        }


        public java.util.Set<java.util.Map.Entry<java.lang.String, java.lang.String>> entrySet() {
            return windowSeqPairs.entrySet();
        }

    }

    public PwEntryV4() {
    }


    public PwEntryV4(com.keepassdroid.database.PwGroupV4 p) {
        this(p, true, true);
    }


    public PwEntryV4(com.keepassdroid.database.PwGroupV4 p, boolean initId, boolean initDates) {
        parent = p;
        if (initId) {
            uuid = java.util.UUID.randomUUID();
        }
        if (initDates) {
            java.util.Calendar cal;
            cal = java.util.Calendar.getInstance();
            java.util.Date now;
            now = cal.getTime();
            creation = now;
            lastAccess = now;
            lastMod = now;
            expires = false;
        }
    }


    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public com.keepassdroid.database.PwEntry clone(boolean deepStrings) {
        com.keepassdroid.database.PwEntryV4 entry;
        entry = ((com.keepassdroid.database.PwEntryV4) (super.clone(deepStrings)));
        if (deepStrings) {
            entry.strings = ((java.util.HashMap<java.lang.String, com.keepassdroid.database.security.ProtectedString>) (strings.clone()));
        }
        return entry;
    }


    @java.lang.SuppressWarnings("unchecked")
    public com.keepassdroid.database.PwEntryV4 cloneDeep() {
        com.keepassdroid.database.PwEntryV4 entry;
        entry = ((com.keepassdroid.database.PwEntryV4) (clone(true)));
        entry.binaries = ((java.util.HashMap<java.lang.String, com.keepassdroid.database.security.ProtectedBinary>) (binaries.clone()));
        entry.history = ((java.util.ArrayList<com.keepassdroid.database.PwEntryV4>) (history.clone()));
        entry.autoType = ((com.keepassdroid.database.PwEntryV4.AutoType) (autoType.clone()));
        return entry;
    }


    @java.lang.Override
    public void assign(com.keepassdroid.database.PwEntry source) {
        if (!(source instanceof com.keepassdroid.database.PwEntryV4)) {
            throw new java.lang.RuntimeException("DB version mix.");
        }
        super.assign(source);
        com.keepassdroid.database.PwEntryV4 src;
        src = ((com.keepassdroid.database.PwEntryV4) (source));
        assign(src);
    }


    private void assign(com.keepassdroid.database.PwEntryV4 source) {
        parent = source.parent;
        uuid = source.uuid;
        strings = source.strings;
        binaries = source.binaries;
        customIcon = source.customIcon;
        foregroundColor = source.foregroundColor;
        backgroupColor = source.backgroupColor;
        overrideURL = source.overrideURL;
        autoType = source.autoType;
        history = source.history;
        parentGroupLastMod = source.parentGroupLastMod;
        creation = source.creation;
        lastMod = source.lastMod;
        lastAccess = source.lastAccess;
        expireDate = source.expireDate;
        expires = source.expires;
        usageCount = source.usageCount;
        url = source.url;
        additional = source.additional;
    }


    @java.lang.Override
    public java.lang.Object clone() {
        com.keepassdroid.database.PwEntryV4 newEntry;
        newEntry = ((com.keepassdroid.database.PwEntryV4) (super.clone()));
        return newEntry;
    }


    private java.lang.String decodeRefKey(boolean decodeRef, java.lang.String key, com.keepassdroid.database.PwDatabase db) {
        java.lang.String text;
        text = getString(key);
        if (decodeRef) {
            text = decodeRef(text, db);
        }
        return text;
    }


    private java.lang.String decodeRef(java.lang.String text, com.keepassdroid.database.PwDatabase db) {
        if (db == null) {
            return text;
        }
        com.keepassdroid.utils.SprEngine spr;
        spr = com.keepassdroid.utils.SprEngine.getInstance(db);
        return spr.compile(text, this, db);
    }


    @java.lang.Override
    public java.lang.String getUsername(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return decodeRefKey(decodeRef, com.keepassdroid.database.PwEntryV4.STR_USERNAME, db);
    }


    @java.lang.Override
    public java.lang.String getTitle(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return decodeRefKey(decodeRef, com.keepassdroid.database.PwEntryV4.STR_TITLE, db);
    }


    @java.lang.Override
    public java.lang.String getPassword(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return decodeRefKey(decodeRef, com.keepassdroid.database.PwEntryV4.STR_PASSWORD, db);
    }


    @java.lang.Override
    public java.util.Date getLastAccessTime() {
        return lastAccess;
    }


    @java.lang.Override
    public java.util.Date getCreationTime() {
        return creation;
    }


    @java.lang.Override
    public java.util.Date getExpiryTime() {
        return expireDate;
    }


    @java.lang.Override
    public java.util.Date getLastModificationTime() {
        return lastMod;
    }


    @java.lang.Override
    public void setTitle(java.lang.String title, com.keepassdroid.database.PwDatabase d) {
        com.keepassdroid.database.PwDatabaseV4 db;
        db = ((com.keepassdroid.database.PwDatabaseV4) (d));
        boolean protect;
        protect = db.memoryProtection.protectTitle;
        setString(com.keepassdroid.database.PwEntryV4.STR_TITLE, title, protect);
    }


    @java.lang.Override
    public void setUsername(java.lang.String user, com.keepassdroid.database.PwDatabase d) {
        com.keepassdroid.database.PwDatabaseV4 db;
        db = ((com.keepassdroid.database.PwDatabaseV4) (d));
        boolean protect;
        protect = db.memoryProtection.protectUserName;
        setString(com.keepassdroid.database.PwEntryV4.STR_USERNAME, user, protect);
    }


    @java.lang.Override
    public void setPassword(java.lang.String pass, com.keepassdroid.database.PwDatabase d) {
        com.keepassdroid.database.PwDatabaseV4 db;
        db = ((com.keepassdroid.database.PwDatabaseV4) (d));
        boolean protect;
        protect = db.memoryProtection.protectPassword;
        setString(com.keepassdroid.database.PwEntryV4.STR_PASSWORD, pass, protect);
    }


    @java.lang.Override
    public void setUrl(java.lang.String url, com.keepassdroid.database.PwDatabase d) {
        com.keepassdroid.database.PwDatabaseV4 db;
        db = ((com.keepassdroid.database.PwDatabaseV4) (d));
        boolean protect;
        protect = db.memoryProtection.protectUrl;
        setString(com.keepassdroid.database.PwEntryV4.STR_URL, url, protect);
    }


    @java.lang.Override
    public void setNotes(java.lang.String notes, com.keepassdroid.database.PwDatabase d) {
        com.keepassdroid.database.PwDatabaseV4 db;
        db = ((com.keepassdroid.database.PwDatabaseV4) (d));
        boolean protect;
        protect = db.memoryProtection.protectNotes;
        setString(com.keepassdroid.database.PwEntryV4.STR_NOTES, notes, protect);
    }


    public void setCreationTime(java.util.Date date) {
        creation = date;
    }


    public void setExpiryTime(java.util.Date date) {
        expireDate = date;
    }


    public void setLastAccessTime(java.util.Date date) {
        lastAccess = date;
    }


    public void setLastModificationTime(java.util.Date date) {
        lastMod = date;
    }


    @java.lang.Override
    public com.keepassdroid.database.PwGroupV4 getParent() {
        return parent;
    }


    @java.lang.Override
    public java.util.UUID getUUID() {
        return uuid;
    }


    @java.lang.Override
    public void setUUID(java.util.UUID u) {
        uuid = u;
    }


    public java.lang.String getString(java.lang.String key) {
        com.keepassdroid.database.security.ProtectedString value;
        value = strings.get(key);
        if (value == null)
            return new java.lang.String("");

        return value.toString();
    }


    public void setString(java.lang.String key, java.lang.String value, boolean protect) {
        com.keepassdroid.database.security.ProtectedString ps;
        ps = new com.keepassdroid.database.security.ProtectedString(protect, value);
        strings.put(key, ps);
    }


    public java.util.Date getLocationChanged() {
        return parentGroupLastMod;
    }


    public long getUsageCount() {
        return usageCount;
    }


    public void setLocationChanged(java.util.Date date) {
        parentGroupLastMod = date;
    }


    public void setUsageCount(long count) {
        usageCount = count;
    }


    @java.lang.Override
    public boolean expires() {
        return expires;
    }


    public void setExpires(boolean exp) {
        expires = exp;
    }


    @java.lang.Override
    public java.lang.String getNotes(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return decodeRefKey(decodeRef, com.keepassdroid.database.PwEntryV4.STR_NOTES, db);
    }


    @java.lang.Override
    public java.lang.String getUrl(boolean decodeRef, com.keepassdroid.database.PwDatabase db) {
        return decodeRefKey(decodeRef, com.keepassdroid.database.PwEntryV4.STR_URL, db);
    }


    @java.lang.Override
    public com.keepassdroid.database.PwIcon getIcon() {
        if ((customIcon == null) || customIcon.uuid.equals(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO)) {
            return super.getIcon();
        } else {
            return customIcon;
        }
    }


    public static boolean IsStandardString(java.lang.String key) {
        return (((key.equals(com.keepassdroid.database.PwEntryV4.STR_TITLE) || key.equals(com.keepassdroid.database.PwEntryV4.STR_USERNAME)) || key.equals(com.keepassdroid.database.PwEntryV4.STR_PASSWORD)) || key.equals(com.keepassdroid.database.PwEntryV4.STR_URL)) || key.equals(com.keepassdroid.database.PwEntryV4.STR_NOTES);
    }


    public void createBackup(com.keepassdroid.database.PwDatabaseV4 db) {
        com.keepassdroid.database.PwEntryV4 copy;
        copy = cloneDeep();
        copy.history = new java.util.ArrayList<com.keepassdroid.database.PwEntryV4>();
        history.add(copy);
        if (db != null)
            maintainBackups(db);

    }


    private boolean maintainBackups(com.keepassdroid.database.PwDatabaseV4 db) {
        boolean deleted;
        deleted = false;
        int maxItems;
        maxItems = db.historyMaxItems;
        if (maxItems >= 0) {
            while (history.size() > maxItems) {
                removeOldestBackup();
                deleted = true;
            } 
        }
        long maxSize;
        maxSize = db.historyMaxSize;
        if (maxSize >= 0) {
            while (true) {
                long histSize;
                histSize = 0;
                for (com.keepassdroid.database.PwEntryV4 entry : history) {
                    histSize += entry.getSize();
                }
                if (histSize > maxSize) {
                    removeOldestBackup();
                    deleted = true;
                } else {
                    break;
                }
            } 
        }
        return deleted;
    }


    private void removeOldestBackup() {
        java.util.Date min;
        min = null;
        int index;
        index = -1;
        for (int i = 0; i < history.size(); i++) {
            com.keepassdroid.database.PwEntry entry;
            entry = history.get(i);
            java.util.Date lastMod;
            lastMod = entry.getLastModificationTime();
            if ((min == null) || lastMod.before(min)) {
                index = i;
                min = lastMod;
            }
        }
        if (index != (-1)) {
            history.remove(index);
        }
    }


    private static final long FIXED_LENGTH_SIZE = 128;// Approximate fixed length size


    public long getSize() {
        long size;
        size = com.keepassdroid.database.PwEntryV4.FIXED_LENGTH_SIZE;
        for (java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString> pair : strings.entrySet()) {
            size += pair.getKey().length();
            size += pair.getValue().length();
        }
        for (java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedBinary> pair : binaries.entrySet()) {
            size += pair.getKey().length();
            size += pair.getValue().length();
        }
        size += autoType.defaultSequence.length();
        for (java.util.Map.Entry<java.lang.String, java.lang.String> pair : autoType.entrySet()) {
            size += pair.getKey().length();
            size += pair.getValue().length();
        }
        for (com.keepassdroid.database.PwEntryV4 entry : history) {
            size += entry.getSize();
        }
        size += overrideURL.length();
        size += tags.length();
        return size;
    }


    @java.lang.Override
    public void touch(boolean modified, boolean touchParents) {
        super.touch(modified, touchParents);
        ++usageCount;
    }


    @java.lang.Override
    public void touchLocation() {
        parentGroupLastMod = new java.util.Date();
    }


    @java.lang.Override
    public void setParent(com.keepassdroid.database.PwGroup parent) {
        this.parent = ((com.keepassdroid.database.PwGroupV4) (parent));
    }


    public boolean isSearchingEnabled() {
        if (parent != null) {
            return parent.isSearchEnabled();
        }
        return com.keepassdroid.database.PwGroupV4.DEFAULT_SEARCHING_ENABLED;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
