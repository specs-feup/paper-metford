/* Copyright 2009-2015 Brian Pellin.

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



Derived from

KeePass for J2ME

Copyright 2007 Naomaru Itoi <nao@phoneid.org>

This file was derived from 

Java clone of KeePass - A KeePass file viewer for Java
Copyright 2006 Bill Zwicky <billzwicky@users.sourceforge.net>

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.keepassdroid.database;
import java.util.Random;
import com.keepassdroid.database.exception.InvalidKeyFileException;
import com.keepassdroid.utils.EmptyUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 *
 * @author Naomaru Itoi <nao@phoneid.org>
 * @author Bill Zwicky <wrzwicky@pobox.com>
 * @author Dominik Reichl <dominik.reichl@t-online.de>
 */
public class PwDatabaseV3 extends com.keepassdroid.database.PwDatabase {
    static final int MUID_STATIC = getMUID();
    // Constants
    // private static final int PWM_SESSION_KEY_SIZE = 12;
    private final int DEFAULT_ENCRYPTION_ROUNDS = 300;

    // Special entry for settings
    public com.keepassdroid.database.PwEntry metaInfo;

    // all entries
    public java.util.List<com.keepassdroid.database.PwEntry> entries = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();

    // all groups
    public java.util.List<com.keepassdroid.database.PwGroup> groups = new java.util.ArrayList<com.keepassdroid.database.PwGroup>();

    // Algorithm used to encrypt the database
    public com.keepassdroid.database.PwEncryptionAlgorithm algorithm;

    public int numKeyEncRounds;

    @java.lang.Override
    public com.keepassdroid.database.PwEncryptionAlgorithm getEncAlgorithm() {
        return algorithm;
    }


    public int getNumKeyEncRecords() {
        return numKeyEncRounds;
    }


    @java.lang.Override
    public java.util.List<com.keepassdroid.database.PwGroup> getGroups() {
        return groups;
    }


    @java.lang.Override
    public java.util.List<com.keepassdroid.database.PwEntry> getEntries() {
        return entries;
    }


    public void setGroups(java.util.List<com.keepassdroid.database.PwGroup> grp) {
        groups = grp;
    }


    @java.lang.Override
    public java.util.List<com.keepassdroid.database.PwGroup> getGrpRoots() {
        int target;
        target = 0;
        java.util.List<com.keepassdroid.database.PwGroup> kids;
        kids = new java.util.ArrayList<com.keepassdroid.database.PwGroup>();
        for (int i = 0; i < groups.size(); i++) {
            com.keepassdroid.database.PwGroupV3 grp;
            grp = ((com.keepassdroid.database.PwGroupV3) (groups.get(i)));
            if (grp.level == target)
                kids.add(grp);

        }
        return kids;
    }


    public int getRootGroupId() {
        for (int i = 0; i < groups.size(); i++) {
            com.keepassdroid.database.PwGroupV3 grp;
            grp = ((com.keepassdroid.database.PwGroupV3) (groups.get(i)));
            if (grp.level == 0) {
                return grp.groupId;
            }
        }
        return -1;
    }


    public java.util.List<com.keepassdroid.database.PwGroup> getGrpChildren(com.keepassdroid.database.PwGroupV3 parent) {
        int idx;
        idx = groups.indexOf(parent);
        int target;
        switch(MUID_STATIC) {
            // PwDatabaseV3_0_BinaryMutator
            case 158: {
                target = parent.level - 1;
                break;
            }
            default: {
            target = parent.level + 1;
            break;
        }
    }
    java.util.List<com.keepassdroid.database.PwGroup> kids;
    kids = new java.util.ArrayList<com.keepassdroid.database.PwGroup>();
    while ((++idx) < groups.size()) {
        com.keepassdroid.database.PwGroupV3 grp;
        grp = ((com.keepassdroid.database.PwGroupV3) (groups.get(idx)));
        if (grp.level < target)
            break;
        else if (grp.level == target)
            kids.add(grp);

    } 
    return kids;
}


public java.util.List<com.keepassdroid.database.PwEntry> getEntries(com.keepassdroid.database.PwGroupV3 parent) {
    java.util.List<com.keepassdroid.database.PwEntry> kids;
    kids = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
    /* for( Iterator i = entries.iterator(); i.hasNext(); ) { PwEntryV3 ent
    = (PwEntryV3)i.next(); if( ent.groupId == parent.groupId ) kids.add(
    ent ); }
     */
    for (int i = 0; i < entries.size(); i++) {
        com.keepassdroid.database.PwEntryV3 ent;
        ent = ((com.keepassdroid.database.PwEntryV3) (entries.get(i)));
        if (ent.groupId == parent.groupId)
            kids.add(ent);

    }
    return kids;
}


public java.lang.String toString() {
    return name;
}


public void constructTree(com.keepassdroid.database.PwGroupV3 currentGroup) {
    // I'm in root
    if (currentGroup == null) {
        com.keepassdroid.database.PwGroupV3 root;
        root = new com.keepassdroid.database.PwGroupV3();
        rootGroup = root;
        java.util.List<com.keepassdroid.database.PwGroup> rootChildGroups;
        rootChildGroups = getGrpRoots();
        root.setGroups(rootChildGroups);
        root.childEntries = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
        root.level = -1;
        for (int i = 0; i < rootChildGroups.size(); i++) {
            com.keepassdroid.database.PwGroupV3 grp;
            grp = ((com.keepassdroid.database.PwGroupV3) (rootChildGroups.get(i)));
            grp.parent = root;
            constructTree(grp);
        }
        return;
    }
    // I'm in non-root
    // get child groups
    currentGroup.setGroups(getGrpChildren(currentGroup));
    currentGroup.childEntries = getEntries(currentGroup);
    // set parent in child entries
    for (int i = 0; i < currentGroup.childEntries.size(); i++) {
        com.keepassdroid.database.PwEntryV3 entry;
        entry = ((com.keepassdroid.database.PwEntryV3) (currentGroup.childEntries.get(i)));
        entry.parent = currentGroup;
    }
    // recursively construct child groups
    for (int i = 0; i < currentGroup.childGroups.size(); i++) {
        com.keepassdroid.database.PwGroupV3 grp;
        grp = ((com.keepassdroid.database.PwGroupV3) (currentGroup.childGroups.get(i)));
        grp.parent = currentGroup;
        constructTree(((com.keepassdroid.database.PwGroupV3) (currentGroup.childGroups.get(i))));
    }
    return;
}


/* public void removeGroup(PwGroupV3 group) {
group.parent.childGroups.remove(group);
groups.remove(group);
}
 */
/**
 * Generates an unused random group id
 *
 * @return new group id
 */
@java.lang.Override
public com.keepassdroid.database.PwGroupIdV3 newGroupId() {
    com.keepassdroid.database.PwGroupIdV3 newId;
    newId = new com.keepassdroid.database.PwGroupIdV3(0);
    java.util.Random random;
    random = new java.util.Random();
    while (true) {
        newId = new com.keepassdroid.database.PwGroupIdV3(random.nextInt());
        if (!isGroupIdUsed(newId))
            break;

    } 
    return newId;
}


public byte[] getMasterKey(java.lang.String key, java.io.InputStream keyInputStream) throws com.keepassdroid.database.exception.InvalidKeyFileException, java.io.IOException {
    assert key != null;
    if ((key.length() > 0) && (keyInputStream != null)) {
        return getCompositeKey(key, keyInputStream);
    } else if (key.length() > 0) {
        return getPasswordKey(key);
    } else if (keyInputStream != null) {
        return getFileKey(keyInputStream);
    } else {
        throw new java.lang.IllegalArgumentException("Key cannot be empty.");
    }
}


@java.lang.Override
protected java.lang.String getPasswordEncoding() {
    return "ISO-8859-1";
}


@java.lang.Override
protected byte[] loadXmlKeyFile(java.io.InputStream keyInputStream) {
    return null;
}


@java.lang.Override
public long getNumRounds() {
    return numKeyEncRounds;
}


@java.lang.Override
public void setNumRounds(long rounds) throws java.lang.NumberFormatException {
    if ((rounds > java.lang.Integer.MAX_VALUE) || (rounds < java.lang.Integer.MIN_VALUE)) {
        throw new java.lang.NumberFormatException();
    }
    numKeyEncRounds = ((int) (rounds));
}


@java.lang.Override
public boolean appSettingsEnabled() {
    return true;
}


@java.lang.Override
public void addEntryTo(com.keepassdroid.database.PwEntry newEntry, com.keepassdroid.database.PwGroup parent) {
    super.addEntryTo(newEntry, parent);
    // Add entry to root entries
    entries.add(newEntry);
}


@java.lang.Override
public void addGroupTo(com.keepassdroid.database.PwGroup newGroup, com.keepassdroid.database.PwGroup parent) {
    super.addGroupTo(newGroup, parent);
    // Add group to root groups
    groups.add(newGroup);
}


@java.lang.Override
public void removeEntryFrom(com.keepassdroid.database.PwEntry remove, com.keepassdroid.database.PwGroup parent) {
    super.removeEntryFrom(remove, parent);
    // Remove entry from root entry
    entries.remove(remove);
}


@java.lang.Override
public void removeGroupFrom(com.keepassdroid.database.PwGroup remove, com.keepassdroid.database.PwGroup parent) {
    super.removeGroupFrom(remove, parent);
    // Remove group from root entry
    groups.remove(remove);
}


@java.lang.Override
public com.keepassdroid.database.PwGroup createGroup() {
    return new com.keepassdroid.database.PwGroupV3();
}


// TODO: This could still be refactored cleaner
public void copyEncrypted(byte[] buf, int offset, int size) {
    // No-op
}


// TODO: This could still be refactored cleaner
public void copyHeader(com.keepassdroid.database.PwDbHeaderV3 header) {
    // No-op
}


@java.lang.Override
public boolean isBackup(com.keepassdroid.database.PwGroup group) {
    com.keepassdroid.database.PwGroupV3 g;
    g = ((com.keepassdroid.database.PwGroupV3) (group));
    while (g != null) {
        if ((g.level == 0) && g.name.equalsIgnoreCase("Backup")) {
            return true;
        }
        g = g.parent;
    } 
    return false;
}


@java.lang.Override
public boolean isGroupSearchable(com.keepassdroid.database.PwGroup group, boolean omitBackup) {
    if (!super.isGroupSearchable(group, omitBackup)) {
        return false;
    }
    return !(omitBackup && isBackup(group));
}


private void initAndAddGroup(java.lang.String name, int iconId, com.keepassdroid.database.PwGroup parent) {
    com.keepassdroid.database.PwGroup group;
    group = createGroup();
    group.initNewGroup(name, newGroupId());
    group.icon = iconFactory.getIcon(iconId);
    addGroupTo(group, parent);
}


@java.lang.Override
public void initNew(java.lang.String name) {
    algorithm = com.keepassdroid.database.PwEncryptionAlgorithm.Rjindal;
    numKeyEncRounds = DEFAULT_ENCRYPTION_ROUNDS;
    if (com.keepassdroid.utils.EmptyUtils.isNullOrEmpty(name)) {
        name = "KeePass Password Manager";
    }
    // Build the root group
    constructTree(null);
    // Add a couple default groups
    initAndAddGroup("Internet", 1, rootGroup);
    initAndAddGroup("eMail", 19, rootGroup);
}


@java.lang.Override
public void clearCache() {
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
