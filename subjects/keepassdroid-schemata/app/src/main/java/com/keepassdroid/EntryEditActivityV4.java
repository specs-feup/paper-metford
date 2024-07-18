/* Copyright 2013-2015 Brian Pellin.

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
import com.keepassdroid.utils.Types;
import com.keepassdroid.app.App;
import com.keepassdroid.database.PwEntryV4;
import java.util.ArrayList;
import com.keepassdroid.database.security.ProtectedString;
import com.keepassdroid.database.PwEntry;
import com.keepassdroid.database.PwGroupId;
import android.widget.ImageButton;
import com.keepassdroid.database.PwGroupIdV4;
import java.util.Map.Entry;
import com.android.keepass.R;
import java.util.Iterator;
import java.util.UUID;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.keepassdroid.database.PwDatabaseV4;
import android.widget.CheckBox;
import com.keepassdroid.database.PwGroupV4;
import android.view.LayoutInflater;
import com.keepassdroid.view.EntryEditSection;
import android.widget.ScrollView;
import android.widget.RelativeLayout;
import java.util.Map;
import android.content.Context;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class EntryEditActivityV4 extends com.keepassdroid.EntryEditActivity {
    static final int MUID_STATIC = getMUID();
    private android.widget.ScrollView scroll;

    private android.view.LayoutInflater inflater;

    protected static void putParentId(android.content.Intent i, java.lang.String parentKey, com.keepassdroid.database.PwGroupV4 parent) {
        com.keepassdroid.database.PwGroupId id;
        id = parent.getId();
        com.keepassdroid.database.PwGroupIdV4 id4;
        id4 = ((com.keepassdroid.database.PwGroupIdV4) (id));
        switch(MUID_STATIC) {
            // EntryEditActivityV4_0_NullValueIntentPutExtraOperatorMutator
            case 218: {
                i.putExtra(parentKey, new Parcelable[0]);
                break;
            }
            // EntryEditActivityV4_1_IntentPayloadReplacementOperatorMutator
            case 1218: {
                i.putExtra(parentKey, (byte[]) null);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // EntryEditActivityV4_2_RandomActionIntentDefinitionOperatorMutator
                case 2218: {
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
                i.putExtra(parentKey, com.keepassdroid.utils.Types.UUIDtoBytes(id4.getId()));
                break;
            }
        }
        break;
    }
}
}


@java.lang.Override
protected com.keepassdroid.database.PwGroupId getParentGroupId(android.content.Intent i, java.lang.String key) {
byte[] buf;
buf = i.getByteArrayExtra(key);
java.util.UUID id;
id = com.keepassdroid.utils.Types.bytestoUUID(buf);
return new com.keepassdroid.database.PwGroupIdV4(id);
}


@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
inflater = ((android.view.LayoutInflater) (this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
super.onCreate(savedInstanceState);
switch(MUID_STATIC) {
    // EntryEditActivityV4_3_LengthyGUICreationOperatorMutator
    case 3218: {
        /**
        * Inserted by Kadabra
        */
        /**
        * Inserted by Kadabra
        */
        // AFTER SUPER
        try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
        break;
    }
    default: {
    // AFTER SUPER
    break;
}
}
switch(MUID_STATIC) {
// EntryEditActivityV4_4_InvalidViewFocusOperatorMutator
case 4218: {
    /**
    * Inserted by Kadabra
    */
    scroll = ((android.widget.ScrollView) (findViewById(com.android.keepass.R.id.entry_scroll)));
    scroll.requestFocus();
    break;
}
// EntryEditActivityV4_5_ViewComponentNotVisibleOperatorMutator
case 5218: {
    /**
    * Inserted by Kadabra
    */
    scroll = ((android.widget.ScrollView) (findViewById(com.android.keepass.R.id.entry_scroll)));
    scroll.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
scroll = ((android.widget.ScrollView) (findViewById(com.android.keepass.R.id.entry_scroll)));
break;
}
}
android.widget.ImageButton add;
switch(MUID_STATIC) {
// EntryEditActivityV4_6_InvalidViewFocusOperatorMutator
case 6218: {
/**
* Inserted by Kadabra
*/
add = ((android.widget.ImageButton) (findViewById(com.android.keepass.R.id.add_advanced)));
add.requestFocus();
break;
}
// EntryEditActivityV4_7_ViewComponentNotVisibleOperatorMutator
case 7218: {
/**
* Inserted by Kadabra
*/
add = ((android.widget.ImageButton) (findViewById(com.android.keepass.R.id.add_advanced)));
add.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
add = ((android.widget.ImageButton) (findViewById(com.android.keepass.R.id.add_advanced)));
break;
}
}
add.setVisibility(android.view.View.VISIBLE);
switch(MUID_STATIC) {
// EntryEditActivityV4_8_BuggyGUIListenerOperatorMutator
case 8218: {
add.setOnClickListener(null);
break;
}
default: {
add.setOnClickListener(new android.view.View.OnClickListener() {
@java.lang.Override
public void onClick(android.view.View v) {
android.widget.LinearLayout container;
switch(MUID_STATIC) {
    // EntryEditActivityV4_10_InvalidViewFocusOperatorMutator
    case 10218: {
        /**
        * Inserted by Kadabra
        */
        container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
        container.requestFocus();
        break;
    }
    // EntryEditActivityV4_11_ViewComponentNotVisibleOperatorMutator
    case 11218: {
        /**
        * Inserted by Kadabra
        */
        container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
        container.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
    break;
}
}
com.keepassdroid.view.EntryEditSection ees;
ees = ((com.keepassdroid.view.EntryEditSection) (inflater.inflate(com.android.keepass.R.layout.entry_edit_section, container, false)));
ees.setData("", new com.keepassdroid.database.security.ProtectedString(false, ""));
container.addView(ees);
switch(MUID_STATIC) {
// EntryEditActivityV4_9_LengthyGUIListenerOperatorMutator
case 9218: {
    /**
    * Inserted by Kadabra
    */
    // Scroll bottom
    scroll.post(new java.lang.Runnable() {
        @java.lang.Override
        public void run() {
            scroll.fullScroll(android.widget.ScrollView.FOCUS_DOWN);
        }

    });
    try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); };
    break;
}
default: {
// Scroll bottom
scroll.post(new java.lang.Runnable() {
    @java.lang.Override
    public void run() {
        scroll.fullScroll(android.widget.ScrollView.FOCUS_DOWN);
    }

});
break;
}
}
}

});
break;
}
}
android.widget.ImageButton iconPicker;
switch(MUID_STATIC) {
// EntryEditActivityV4_12_InvalidViewFocusOperatorMutator
case 12218: {
/**
* Inserted by Kadabra
*/
iconPicker = ((android.widget.ImageButton) (findViewById(com.android.keepass.R.id.icon_button)));
iconPicker.requestFocus();
break;
}
// EntryEditActivityV4_13_ViewComponentNotVisibleOperatorMutator
case 13218: {
/**
* Inserted by Kadabra
*/
iconPicker = ((android.widget.ImageButton) (findViewById(com.android.keepass.R.id.icon_button)));
iconPicker.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
iconPicker = ((android.widget.ImageButton) (findViewById(com.android.keepass.R.id.icon_button)));
break;
}
}
iconPicker.setVisibility(android.view.View.GONE);
android.view.View divider;
switch(MUID_STATIC) {
// EntryEditActivityV4_14_InvalidViewFocusOperatorMutator
case 14218: {
/**
* Inserted by Kadabra
*/
divider = ((android.view.View) (findViewById(com.android.keepass.R.id.divider_title)));
divider.requestFocus();
break;
}
// EntryEditActivityV4_15_ViewComponentNotVisibleOperatorMutator
case 15218: {
/**
* Inserted by Kadabra
*/
divider = ((android.view.View) (findViewById(com.android.keepass.R.id.divider_title)));
divider.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
divider = ((android.view.View) (findViewById(com.android.keepass.R.id.divider_title)));
break;
}
}
android.widget.RelativeLayout.LayoutParams lp_div;
lp_div = ((android.widget.RelativeLayout.LayoutParams) (divider.getLayoutParams()));
lp_div.addRule(android.widget.RelativeLayout.BELOW, com.android.keepass.R.id.entry_title);
android.view.View user_label;
switch(MUID_STATIC) {
// EntryEditActivityV4_16_InvalidViewFocusOperatorMutator
case 16218: {
/**
* Inserted by Kadabra
*/
user_label = ((android.view.View) (findViewById(com.android.keepass.R.id.entry_user_name_label)));
user_label.requestFocus();
break;
}
// EntryEditActivityV4_17_ViewComponentNotVisibleOperatorMutator
case 17218: {
/**
* Inserted by Kadabra
*/
user_label = ((android.view.View) (findViewById(com.android.keepass.R.id.entry_user_name_label)));
user_label.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
user_label = ((android.view.View) (findViewById(com.android.keepass.R.id.entry_user_name_label)));
break;
}
}
android.widget.RelativeLayout.LayoutParams lp;
lp = ((android.widget.RelativeLayout.LayoutParams) (user_label.getLayoutParams()));
lp.addRule(android.widget.RelativeLayout.BELOW, com.android.keepass.R.id.divider_title);
}


@java.lang.Override
protected void fillData() {
super.fillData();
com.keepassdroid.database.PwEntryV4 entry;
entry = ((com.keepassdroid.database.PwEntryV4) (mEntry));
android.widget.LinearLayout container;
switch(MUID_STATIC) {
// EntryEditActivityV4_18_InvalidViewFocusOperatorMutator
case 18218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.requestFocus();
break;
}
// EntryEditActivityV4_19_ViewComponentNotVisibleOperatorMutator
case 19218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
break;
}
}
if (entry.strings.size() > 0) {
for (java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString> pair : entry.strings.entrySet()) {
java.lang.String key;
key = pair.getKey();
if (!com.keepassdroid.database.PwEntryV4.IsStandardString(key)) {
com.keepassdroid.view.EntryEditSection ees;
ees = ((com.keepassdroid.view.EntryEditSection) (inflater.inflate(com.android.keepass.R.layout.entry_edit_section, container, false)));
ees.setData(key, pair.getValue());
container.addView(ees);
}
}
}
}


@java.lang.SuppressWarnings("unchecked")
@java.lang.Override
protected com.keepassdroid.database.PwEntry populateNewEntry() {
com.keepassdroid.database.PwEntryV4 newEntry;
newEntry = ((com.keepassdroid.database.PwEntryV4) (mEntry.clone(true)));
newEntry.history = ((java.util.ArrayList<com.keepassdroid.database.PwEntryV4>) (newEntry.history.clone()));
newEntry.createBackup(((com.keepassdroid.database.PwDatabaseV4) (com.keepassdroid.app.App.getDB().pm)));
newEntry = ((com.keepassdroid.database.PwEntryV4) (super.populateNewEntry(newEntry)));
java.util.Map<java.lang.String, com.keepassdroid.database.security.ProtectedString> strings;
strings = newEntry.strings;
// Delete all new standard strings
java.util.Iterator<java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString>> iter;
iter = strings.entrySet().iterator();
while (iter.hasNext()) {
java.util.Map.Entry<java.lang.String, com.keepassdroid.database.security.ProtectedString> pair;
pair = iter.next();
if (!com.keepassdroid.database.PwEntryV4.IsStandardString(pair.getKey())) {
iter.remove();
}
} 
android.widget.LinearLayout container;
switch(MUID_STATIC) {
// EntryEditActivityV4_20_InvalidViewFocusOperatorMutator
case 20218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.requestFocus();
break;
}
// EntryEditActivityV4_21_ViewComponentNotVisibleOperatorMutator
case 21218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
break;
}
}
for (int i = 0; i < container.getChildCount(); i++) {
android.view.View view;
view = container.getChildAt(i);
android.widget.TextView keyView;
switch(MUID_STATIC) {
// EntryEditActivityV4_22_InvalidViewFocusOperatorMutator
case 22218: {
/**
* Inserted by Kadabra
*/
keyView = ((android.widget.TextView) (view.findViewById(com.android.keepass.R.id.title)));
keyView.requestFocus();
break;
}
// EntryEditActivityV4_23_ViewComponentNotVisibleOperatorMutator
case 23218: {
/**
* Inserted by Kadabra
*/
keyView = ((android.widget.TextView) (view.findViewById(com.android.keepass.R.id.title)));
keyView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
keyView = ((android.widget.TextView) (view.findViewById(com.android.keepass.R.id.title)));
break;
}
}
java.lang.String key;
key = keyView.getText().toString();
android.widget.TextView valueView;
switch(MUID_STATIC) {
// EntryEditActivityV4_24_InvalidViewFocusOperatorMutator
case 24218: {
/**
* Inserted by Kadabra
*/
valueView = ((android.widget.TextView) (view.findViewById(com.android.keepass.R.id.value)));
valueView.requestFocus();
break;
}
// EntryEditActivityV4_25_ViewComponentNotVisibleOperatorMutator
case 25218: {
/**
* Inserted by Kadabra
*/
valueView = ((android.widget.TextView) (view.findViewById(com.android.keepass.R.id.value)));
valueView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
valueView = ((android.widget.TextView) (view.findViewById(com.android.keepass.R.id.value)));
break;
}
}
java.lang.String value;
value = valueView.getText().toString();
android.widget.CheckBox cb;
switch(MUID_STATIC) {
// EntryEditActivityV4_26_InvalidViewFocusOperatorMutator
case 26218: {
/**
* Inserted by Kadabra
*/
cb = ((android.widget.CheckBox) (view.findViewById(com.android.keepass.R.id.protection)));
cb.requestFocus();
break;
}
// EntryEditActivityV4_27_ViewComponentNotVisibleOperatorMutator
case 27218: {
/**
* Inserted by Kadabra
*/
cb = ((android.widget.CheckBox) (view.findViewById(com.android.keepass.R.id.protection)));
cb.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
cb = ((android.widget.CheckBox) (view.findViewById(com.android.keepass.R.id.protection)));
break;
}
}
boolean protect;
protect = cb.isChecked();
strings.put(key, new com.keepassdroid.database.security.ProtectedString(protect, value));
}
return newEntry;
}


public void deleteAdvancedString(android.view.View view) {
com.keepassdroid.view.EntryEditSection section;
section = ((com.keepassdroid.view.EntryEditSection) (view.getParent()));
android.widget.LinearLayout container;
switch(MUID_STATIC) {
// EntryEditActivityV4_28_InvalidViewFocusOperatorMutator
case 28218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.requestFocus();
break;
}
// EntryEditActivityV4_29_ViewComponentNotVisibleOperatorMutator
case 29218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
break;
}
}
for (int i = 0; i < container.getChildCount(); i++) {
com.keepassdroid.view.EntryEditSection ees;
ees = ((com.keepassdroid.view.EntryEditSection) (container.getChildAt(i)));
if (ees == section) {
container.removeViewAt(i);
container.invalidate();
break;
}
}
}


@java.lang.Override
protected boolean validateBeforeSaving() {
if (!super.validateBeforeSaving()) {
return false;
}
android.widget.LinearLayout container;
switch(MUID_STATIC) {
// EntryEditActivityV4_30_InvalidViewFocusOperatorMutator
case 30218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.requestFocus();
break;
}
// EntryEditActivityV4_31_ViewComponentNotVisibleOperatorMutator
case 31218: {
/**
* Inserted by Kadabra
*/
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
container.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
container = ((android.widget.LinearLayout) (findViewById(com.android.keepass.R.id.advanced_container)));
break;
}
}
for (int i = 0; i < container.getChildCount(); i++) {
com.keepassdroid.view.EntryEditSection ees;
ees = ((com.keepassdroid.view.EntryEditSection) (container.getChildAt(i)));
android.widget.TextView keyView;
switch(MUID_STATIC) {
// EntryEditActivityV4_32_InvalidViewFocusOperatorMutator
case 32218: {
/**
* Inserted by Kadabra
*/
keyView = ((android.widget.TextView) (ees.findViewById(com.android.keepass.R.id.title)));
keyView.requestFocus();
break;
}
// EntryEditActivityV4_33_ViewComponentNotVisibleOperatorMutator
case 33218: {
/**
* Inserted by Kadabra
*/
keyView = ((android.widget.TextView) (ees.findViewById(com.android.keepass.R.id.title)));
keyView.setVisibility(android.view.View.INVISIBLE);
break;
}
default: {
keyView = ((android.widget.TextView) (ees.findViewById(com.android.keepass.R.id.title)));
break;
}
}
java.lang.CharSequence key;
key = keyView.getText();
if ((key == null) || (key.length() == 0)) {
android.widget.Toast.makeText(this, com.android.keepass.R.string.error_string_key, android.widget.Toast.LENGTH_LONG).show();
return false;
}
}
return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
