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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwGroupV4 extends com.keepassdroid.database.PwGroup implements com.keepassdroid.database.ITimeLogger {
    static final int MUID_STATIC = getMUID();
    // public static final int FOLDER_ICON = 48;
    public static final boolean DEFAULT_SEARCHING_ENABLED = true;

    public com.keepassdroid.database.PwGroupV4 parent = null;

    public java.util.UUID uuid = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public java.lang.String notes = "";

    public com.keepassdroid.database.PwIconCustom customIcon = com.keepassdroid.database.PwIconCustom.ZERO;

    public boolean isExpanded = true;

    public java.lang.String defaultAutoTypeSequence = "";

    public java.lang.Boolean enableAutoType = null;

    public java.lang.Boolean enableSearching = null;

    public java.util.UUID lastTopVisibleEntry = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    private java.util.Date parentGroupLastMod = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date creation = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date lastMod = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date lastAccess = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private java.util.Date expireDate = com.keepassdroid.database.PwDatabaseV4.DEFAULT_NOW;

    private boolean expires = false;

    private long usageCount = 0;

    public java.util.UUID prevParentGroup = com.keepassdroid.database.PwDatabaseV4.UUID_ZERO;

    public com.keepassdroid.database.PwCustomData customData = new com.keepassdroid.database.PwCustomData();

    public java.lang.String tags = "";

    public PwGroupV4() {
    }


    public PwGroupV4(boolean createUUID, boolean setTimes, java.lang.String name, com.keepassdroid.database.PwIconStandard icon) {
        if (createUUID) {
            uuid = java.util.UUID.randomUUID();
        }
        if (setTimes) {
            creation = lastMod = lastAccess = new java.util.Date();
        }
        this.name = name;
        this.icon = icon;
    }


    public void AddGroup(com.keepassdroid.database.PwGroupV4 subGroup, boolean takeOwnership) {
        AddGroup(subGroup, takeOwnership, false);
    }


    public void AddGroup(com.keepassdroid.database.PwGroupV4 subGroup, boolean takeOwnership, boolean updateLocationChanged) {
        if (subGroup == null)
            throw new java.lang.RuntimeException("subGroup");

        childGroups.add(subGroup);
        if (takeOwnership)
            subGroup.parent = this;

        if (updateLocationChanged)
            subGroup.parentGroupLastMod = new java.util.Date(java.lang.System.currentTimeMillis());

    }


    public void AddEntry(com.keepassdroid.database.PwEntryV4 pe, boolean takeOwnership) {
        AddEntry(pe, takeOwnership, false);
    }


    public void AddEntry(com.keepassdroid.database.PwEntryV4 pe, boolean takeOwnership, boolean updateLocationChanged) {
        assert pe != null;
        childEntries.add(pe);
        if (takeOwnership)
            pe.parent = this;

        if (updateLocationChanged)
            pe.setLocationChanged(new java.util.Date(java.lang.System.currentTimeMillis()));

    }


    @java.lang.Override
    public com.keepassdroid.database.PwGroup getParent() {
        return parent;
    }


    public void buildChildGroupsRecursive(java.util.List<com.keepassdroid.database.PwGroup> list) {
        list.add(this);
        for (int i = 0; i < childGroups.size(); i++) {
            com.keepassdroid.database.PwGroupV4 child;
            child = ((com.keepassdroid.database.PwGroupV4) (childGroups.get(i)));
            child.buildChildGroupsRecursive(list);
        }
    }


    public void buildChildEntriesRecursive(java.util.List<com.keepassdroid.database.PwEntry> list) {
        for (int i = 0; i < childEntries.size(); i++) {
            list.add(childEntries.get(i));
        }
        for (int i = 0; i < childGroups.size(); i++) {
            com.keepassdroid.database.PwGroupV4 child;
            child = ((com.keepassdroid.database.PwGroupV4) (childGroups.get(i)));
            child.buildChildEntriesRecursive(list);
        }
    }


    @java.lang.Override
    public com.keepassdroid.database.PwGroupId getId() {
        return new com.keepassdroid.database.PwGroupIdV4(uuid);
    }


    @java.lang.Override
    public void setId(com.keepassdroid.database.PwGroupId id) {
        com.keepassdroid.database.PwGroupIdV4 id4;
        id4 = ((com.keepassdroid.database.PwGroupIdV4) (id));
        uuid = id4.getId();
    }


    @java.lang.Override
    public java.lang.String getName() {
        return name;
    }


    @java.lang.Override
    public java.util.Date getLastMod() {
        return parentGroupLastMod;
    }


    public java.util.Date getCreationTime() {
        return creation;
    }


    public java.util.Date getExpiryTime() {
        return expireDate;
    }


    public java.util.Date getLastAccessTime() {
        return lastAccess;
    }


    public java.util.Date getLastModificationTime() {
        return lastMod;
    }


    public java.util.Date getLocationChanged() {
        return parentGroupLastMod;
    }


    public long getUsageCount() {
        return usageCount;
    }


    public void setCreationTime(java.util.Date date) {
        creation = date;
    }


    public void setExpiryTime(java.util.Date date) {
        expireDate = date;
    }


    @java.lang.Override
    public void setLastAccessTime(java.util.Date date) {
        lastAccess = date;
    }


    @java.lang.Override
    public void setLastModificationTime(java.util.Date date) {
        lastMod = date;
    }


    public void setLocationChanged(java.util.Date date) {
        parentGroupLastMod = date;
    }


    public void setUsageCount(long count) {
        usageCount = count;
    }


    public boolean expires() {
        return expires;
    }


    public void setExpires(boolean exp) {
        expires = exp;
    }


    @java.lang.Override
    public void setParent(com.keepassdroid.database.PwGroup prt) {
        parent = ((com.keepassdroid.database.PwGroupV4) (prt));
    }


    @java.lang.Override
    public com.keepassdroid.database.PwIcon getIcon() {
        if ((customIcon == null) || customIcon.uuid.equals(com.keepassdroid.database.PwDatabaseV4.UUID_ZERO)) {
            return super.getIcon();
        } else {
            return customIcon;
        }
    }


    @java.lang.Override
    public void initNewGroup(java.lang.String nm, com.keepassdroid.database.PwGroupId newId) {
        super.initNewGroup(nm, newId);
        lastAccess = lastMod = creation = parentGroupLastMod = new java.util.Date();
    }


    public boolean isSearchEnabled() {
        com.keepassdroid.database.PwGroupV4 group;
        group = this;
        while (group != null) {
            java.lang.Boolean search;
            search = group.enableSearching;
            if (search != null) {
                return search;
            }
            group = group.parent;
        } 
        // If we get to the root group and its null, default to true
        return true;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
