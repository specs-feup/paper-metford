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
import com.keepassdroid.utils.StrUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public abstract class PwGroup {
    static final int MUID_STATIC = getMUID();
    public java.util.List<com.keepassdroid.database.PwGroup> childGroups = new java.util.ArrayList<com.keepassdroid.database.PwGroup>();

    public java.util.List<com.keepassdroid.database.PwEntry> childEntries = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();

    public java.lang.String name = "";

    public com.keepassdroid.database.PwIconStandard icon;

    public abstract com.keepassdroid.database.PwGroup getParent();


    public abstract void setParent(com.keepassdroid.database.PwGroup parent);


    public abstract com.keepassdroid.database.PwGroupId getId();


    public abstract void setId(com.keepassdroid.database.PwGroupId id);


    public abstract java.lang.String getName();


    public abstract java.util.Date getLastMod();


    public com.keepassdroid.database.PwIcon getIcon() {
        return icon;
    }


    public void sortGroupsByName() {
        java.util.Collections.sort(childGroups, new com.keepassdroid.database.PwGroup.GroupNameComparator());
    }


    public static class GroupNameComparator implements java.util.Comparator<com.keepassdroid.database.PwGroup> {
        public int compare(com.keepassdroid.database.PwGroup object1, com.keepassdroid.database.PwGroup object2) {
            return object1.getName().compareToIgnoreCase(object2.getName());
        }

    }

    public abstract void setLastAccessTime(java.util.Date date);


    public abstract void setLastModificationTime(java.util.Date date);


    public void sortEntriesByName() {
        java.util.Collections.sort(childEntries, new com.keepassdroid.database.PwEntry.EntryNameComparator());
    }


    public void initNewGroup(java.lang.String nm, com.keepassdroid.database.PwGroupId newId) {
        setId(newId);
        name = nm;
    }


    public boolean isContainedIn(com.keepassdroid.database.PwGroup container) {
        com.keepassdroid.database.PwGroup cur;
        cur = this;
        while (cur != null) {
            if (cur == container) {
                return true;
            }
            cur = cur.getParent();
        } 
        return false;
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


    public void searchEntries(com.keepassdroid.database.SearchParameters sp, java.util.List<com.keepassdroid.database.PwEntry> listStorage) {
        if (sp == null) {
            return;
        }
        if (listStorage == null) {
            return;
        }
        java.util.List<java.lang.String> terms;
        terms = com.keepassdroid.utils.StrUtil.splitSearchTerms(sp.searchString);
        if ((terms.size() <= 1) || sp.regularExpression) {
            searchEntriesSingle(sp, listStorage);
            return;
        }
        // Search longest term first
        java.util.Comparator<java.lang.String> stringLengthComparator;
        stringLengthComparator = new java.util.Comparator<java.lang.String>() {
            @java.lang.Override
            public int compare(java.lang.String lhs, java.lang.String rhs) {
                switch(MUID_STATIC) {
                    // PwGroup_0_BinaryMutator
                    case 183: {
                        return lhs.length() + rhs.length();
                    }
                    default: {
                    return lhs.length() - rhs.length();
                    }
            }
        }

    };
    java.util.Collections.sort(terms, stringLengthComparator);
    java.lang.String fullSearch;
    fullSearch = sp.searchString;
    java.util.List<com.keepassdroid.database.PwEntry> pg;
    pg = this.childEntries;
    for (int i = 0; i < terms.size(); i++) {
        java.util.List<com.keepassdroid.database.PwEntry> pgNew;
        pgNew = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
        sp.searchString = terms.get(i);
        boolean negate;
        negate = false;
        if (sp.searchString.startsWith("-")) {
            sp.searchString = sp.searchString.substring(1);
            negate = sp.searchString.length() > 0;
        }
        if (!searchEntriesSingle(sp, pgNew)) {
            pg = null;
            break;
        }
        java.util.List<com.keepassdroid.database.PwEntry> complement;
        complement = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
        if (negate) {
            for (com.keepassdroid.database.PwEntry entry : pg) {
                if (!pgNew.contains(entry)) {
                    complement.add(entry);
                }
            }
            pg = complement;
        } else {
            pg = pgNew;
        }
    }
    if (pg != null) {
        listStorage.addAll(pg);
    }
    sp.searchString = fullSearch;
}


private boolean searchEntriesSingle(com.keepassdroid.database.SearchParameters spIn, java.util.List<com.keepassdroid.database.PwEntry> listStorage) {
    com.keepassdroid.database.SearchParameters sp;
    sp = ((com.keepassdroid.database.SearchParameters) (spIn.clone()));
    com.keepassdroid.database.EntryHandler<com.keepassdroid.database.PwEntry> eh;
    if (sp.searchString.length() <= 0) {
        eh = new com.keepassdroid.database.EntrySearchHandlerAll(sp, listStorage);
    } else {
        eh = com.keepassdroid.database.EntrySearchHandler.getInstance(this, sp, listStorage);
    }
    if (!preOrderTraverseTree(null, eh)) {
        return false;
    }
    return true;
}


public boolean preOrderTraverseTree(com.keepassdroid.database.GroupHandler<com.keepassdroid.database.PwGroup> groupHandler, com.keepassdroid.database.EntryHandler<com.keepassdroid.database.PwEntry> entryHandler) {
    if (entryHandler != null) {
        for (com.keepassdroid.database.PwEntry entry : childEntries) {
            if (!entryHandler.operate(entry))
                return false;

        }
    }
    for (com.keepassdroid.database.PwGroup group : childGroups) {
        if ((groupHandler != null) && (!groupHandler.operate(group)))
            return false;

        group.preOrderTraverseTree(groupHandler, entryHandler);
    }
    return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
