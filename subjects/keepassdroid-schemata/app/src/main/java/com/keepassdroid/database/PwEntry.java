/* Copyright 2009-2014 Brian Pellin.

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
import com.keepassdroid.database.iterator.EntrySearchStringIterator;
import java.util.UUID;
import java.util.Comparator;
import com.keepassdroid.utils.SprEngine;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class PwEntry implements java.lang.Cloneable {
    static final int MUID_STATIC = getMUID();
    protected static final java.lang.String PMS_TAN_ENTRY = "<TAN>";

    public static class EntryNameComparator implements java.util.Comparator<com.keepassdroid.database.PwEntry> {
        public int compare(com.keepassdroid.database.PwEntry object1, com.keepassdroid.database.PwEntry object2) {
            return object1.getTitle().compareToIgnoreCase(object2.getTitle());
        }

    }

    public com.keepassdroid.database.PwIconStandard icon = com.keepassdroid.database.PwIconStandard.FIRST;

    public PwEntry() {
    }


    public static com.keepassdroid.database.PwEntry getInstance(com.keepassdroid.database.PwGroup parent) {
        return com.keepassdroid.database.PwEntry.getInstance(parent, true, true);
    }


    public static com.keepassdroid.database.PwEntry getInstance(com.keepassdroid.database.PwGroup parent, boolean initId, boolean initDates) {
        if (parent instanceof com.keepassdroid.database.PwGroupV3) {
            return new com.keepassdroid.database.PwEntryV3(((com.keepassdroid.database.PwGroupV3) (parent)));
        } else if (parent instanceof com.keepassdroid.database.PwGroupV4) {
            return new com.keepassdroid.database.PwEntryV4(((com.keepassdroid.database.PwGroupV4) (parent)));
        } else {
            throw new java.lang.RuntimeException("Unknown PwGroup instance.");
        }
    }


    @java.lang.Override
    public java.lang.Object clone() {
        com.keepassdroid.database.PwEntry newEntry;
        try {
            newEntry = ((com.keepassdroid.database.PwEntry) (super.clone()));
        } catch (java.lang.CloneNotSupportedException e) {
            assert false;
            throw new java.lang.RuntimeException("Clone should be supported");
        }
        return newEntry;
    }


    public com.keepassdroid.database.PwEntry clone(boolean deepStrings) {
        return ((com.keepassdroid.database.PwEntry) (clone()));
    }


    public void assign(com.keepassdroid.database.PwEntry source) {
        icon = source.icon;
    }


    public abstract java.util.UUID getUUID();


    public abstract void setUUID(java.util.UUID u);


    public java.lang.String getTitle() {
        return getTitle(false, null);
    }


    public java.lang.String getUsername() {
        return getUsername(false, null);
    }


    public java.lang.String getPassword() {
        return getPassword(false, null);
    }


    public java.lang.String getUrl() {
        return getUrl(false, null);
    }


    public java.lang.String getNotes() {
        return getNotes(false, null);
    }


    public abstract java.lang.String getTitle(boolean decodeRef, com.keepassdroid.database.PwDatabase db);


    public abstract java.lang.String getUsername(boolean decodeRef, com.keepassdroid.database.PwDatabase db);


    public abstract java.lang.String getPassword(boolean decodeRef, com.keepassdroid.database.PwDatabase db);


    public abstract java.lang.String getUrl(boolean decodeRef, com.keepassdroid.database.PwDatabase db);


    public abstract java.lang.String getNotes(boolean decodeRef, com.keepassdroid.database.PwDatabase db);


    public abstract java.util.Date getCreationTime();


    public abstract java.util.Date getLastModificationTime();


    public abstract java.util.Date getLastAccessTime();


    public abstract java.util.Date getExpiryTime();


    public abstract boolean expires();


    public abstract com.keepassdroid.database.PwGroup getParent();


    public abstract void setTitle(java.lang.String title, com.keepassdroid.database.PwDatabase db);


    public abstract void setUsername(java.lang.String user, com.keepassdroid.database.PwDatabase db);


    public abstract void setPassword(java.lang.String pass, com.keepassdroid.database.PwDatabase db);


    public abstract void setUrl(java.lang.String url, com.keepassdroid.database.PwDatabase db);


    public abstract void setNotes(java.lang.String notes, com.keepassdroid.database.PwDatabase db);


    public abstract void setCreationTime(java.util.Date create);


    public abstract void setLastModificationTime(java.util.Date mod);


    public abstract void setLastAccessTime(java.util.Date access);


    public abstract void setExpires(boolean exp);


    public abstract void setExpiryTime(java.util.Date expires);


    public com.keepassdroid.database.PwIcon getIcon() {
        return icon;
    }


    public boolean isTan() {
        return getTitle().equals(com.keepassdroid.database.PwEntry.PMS_TAN_ENTRY) && (getUsername().length() > 0);
    }


    public java.lang.String getDisplayTitle() {
        if (isTan()) {
            return (com.keepassdroid.database.PwEntry.PMS_TAN_ENTRY + " ") + getUsername();
        } else {
            return getTitle();
        }
    }


    public boolean isMetaStream() {
        return false;
    }


    public com.keepassdroid.database.iterator.EntrySearchStringIterator stringIterator() {
        return com.keepassdroid.database.iterator.EntrySearchStringIterator.getInstance(this);
    }


    public void touch(boolean modified, boolean touchParents) {
        java.util.Date now;
        now = new java.util.Date();
        setLastAccessTime(now);
        if (modified) {
            setLastModificationTime(now);
        }
        com.keepassdroid.database.PwGroup parent;
        parent = getParent();
        if (touchParents && (parent != null)) {
            parent.touch(modified, true);
        }
    }


    public void touchLocation() {
    }


    public abstract void setParent(com.keepassdroid.database.PwGroup parent);


    public boolean isSearchingEnabled() {
        return false;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
