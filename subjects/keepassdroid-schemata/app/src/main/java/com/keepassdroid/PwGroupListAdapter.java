/* Copyright 2009 Brian Pellin.

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
package com.keepassdroid;
import android.content.SharedPreferences;
import java.util.ArrayList;
import android.view.ViewGroup;
import com.keepassdroid.view.PwEntryView;
import android.preference.PreferenceManager;
import java.util.Comparator;
import android.view.View;
import com.keepassdroid.database.PwEntry;
import com.android.keepass.R;
import com.keepassdroid.view.PwGroupView;
import com.keepassdroid.database.PwGroup;
import java.util.List;
import android.widget.BaseAdapter;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwGroupListAdapter extends android.widget.BaseAdapter {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.GroupBaseActivity mAct;

    private com.keepassdroid.database.PwGroup mGroup;

    private java.util.List<com.keepassdroid.database.PwGroup> groupsForViewing;

    private java.util.List<com.keepassdroid.database.PwEntry> entriesForViewing;

    private java.util.Comparator<com.keepassdroid.database.PwEntry> entryComp = new com.keepassdroid.database.PwEntry.EntryNameComparator();

    private java.util.Comparator<com.keepassdroid.database.PwGroup> groupComp = new com.keepassdroid.database.PwGroup.GroupNameComparator();

    private android.content.SharedPreferences prefs;

    public PwGroupListAdapter(com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.PwGroup group) {
        mAct = act;
        mGroup = group;
        prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(act);
        filterAndSort();
    }


    @java.lang.Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        filterAndSort();
    }


    @java.lang.Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        filterAndSort();
    }


    private void filterAndSort() {
        entriesForViewing = new java.util.ArrayList<com.keepassdroid.database.PwEntry>();
        for (int i = 0; i < mGroup.childEntries.size(); i++) {
            com.keepassdroid.database.PwEntry entry;
            entry = mGroup.childEntries.get(i);
            if (!entry.isMetaStream()) {
                entriesForViewing.add(entry);
            }
        }
        boolean sortLists;
        sortLists = prefs.getBoolean(mAct.getString(com.android.keepass.R.string.sort_key), mAct.getResources().getBoolean(com.android.keepass.R.bool.sort_default));
        if (sortLists) {
            groupsForViewing = new java.util.ArrayList<com.keepassdroid.database.PwGroup>(mGroup.childGroups);
            java.util.Collections.sort(entriesForViewing, entryComp);
            java.util.Collections.sort(groupsForViewing, groupComp);
        } else {
            groupsForViewing = mGroup.childGroups;
        }
    }


    public int getCount() {
        switch(MUID_STATIC) {
            // PwGroupListAdapter_0_BinaryMutator
            case 215: {
                return groupsForViewing.size() - entriesForViewing.size();
            }
            default: {
            return groupsForViewing.size() + entriesForViewing.size();
            }
    }
}


public java.lang.Object getItem(int position) {
    return position;
}


public long getItemId(int position) {
    return position;
}


public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
    int size;
    size = groupsForViewing.size();
    if (position < size) {
        return createGroupView(position, convertView);
    } else {
        switch(MUID_STATIC) {
            // PwGroupListAdapter_1_BinaryMutator
            case 1215: {
                return createEntryView(position + size, convertView);
            }
            default: {
            return createEntryView(position - size, convertView);
            }
    }
}
}


private android.view.View createGroupView(int position, android.view.View convertView) {
com.keepassdroid.database.PwGroup group;
group = groupsForViewing.get(position);
com.keepassdroid.view.PwGroupView gv;
if ((convertView == null) || (!(convertView instanceof com.keepassdroid.view.PwGroupView))) {
    gv = com.keepassdroid.view.PwGroupView.getInstance(mAct, group);
} else {
    gv = ((com.keepassdroid.view.PwGroupView) (convertView));
    gv.convertView(group);
}
return gv;
}


private com.keepassdroid.view.PwEntryView createEntryView(int position, android.view.View convertView) {
com.keepassdroid.database.PwEntry entry;
entry = entriesForViewing.get(position);
com.keepassdroid.view.PwEntryView ev;
if ((convertView == null) || (!(convertView instanceof com.keepassdroid.view.PwEntryView))) {
    ev = com.keepassdroid.view.PwEntryView.getInstance(mAct, entry, position);
} else {
    ev = ((com.keepassdroid.view.PwEntryView) (convertView));
    ev.convertView(entry, position);
}
return ev;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
