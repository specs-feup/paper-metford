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
package it.feio.android.omninotes.widget;
import it.feio.android.omninotes.models.Category;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.os.Bundle;
import java.util.ArrayList;
import it.feio.android.omninotes.models.adapters.CategoryBaseAdapter;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import it.feio.android.omninotes.R;
import android.widget.Button;
import android.app.Activity;
import it.feio.android.omninotes.db.DbHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import android.os.Parcelable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class WidgetConfigurationActivity extends android.app.Activity {
    static final int MUID_STATIC = getMUID();
    private android.widget.Spinner categorySpinner;

    private int mAppWidgetId = android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

    private java.lang.String sqlCondition;

    private android.widget.RadioGroup mRadioGroup;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // WidgetConfigurationActivity_0_LengthyGUICreationOperatorMutator
            case 108: {
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
    setResult(android.app.Activity.RESULT_CANCELED);
    setContentView(it.feio.android.omninotes.R.layout.activity_widget_configuration);
    switch(MUID_STATIC) {
        // WidgetConfigurationActivity_1_FindViewByIdReturnsNullOperatorMutator
        case 1108: {
            mRadioGroup = null;
            break;
        }
        // WidgetConfigurationActivity_2_InvalidIDFindViewOperatorMutator
        case 2108: {
            mRadioGroup = findViewById(732221);
            break;
        }
        // WidgetConfigurationActivity_3_InvalidViewFocusOperatorMutator
        case 3108: {
            /**
            * Inserted by Kadabra
            */
            mRadioGroup = findViewById(it.feio.android.omninotes.R.id.widget_config_radiogroup);
            mRadioGroup.requestFocus();
            break;
        }
        // WidgetConfigurationActivity_4_ViewComponentNotVisibleOperatorMutator
        case 4108: {
            /**
            * Inserted by Kadabra
            */
            mRadioGroup = findViewById(it.feio.android.omninotes.R.id.widget_config_radiogroup);
            mRadioGroup.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        mRadioGroup = findViewById(it.feio.android.omninotes.R.id.widget_config_radiogroup);
        break;
    }
}
mRadioGroup.setOnCheckedChangeListener((android.widget.RadioGroup group,int checkedId) -> {
    switch (checkedId) {
        case it.feio.android.omninotes.R.id.widget_config_notes :
            categorySpinner.setEnabled(false);
            break;
        case it.feio.android.omninotes.R.id.widget_config_categories :
            categorySpinner.setEnabled(true);
            break;
        default :
            it.feio.android.omninotes.helpers.LogDelegate.e("Wrong element choosen: " + checkedId);
    }
});
switch(MUID_STATIC) {
    // WidgetConfigurationActivity_5_FindViewByIdReturnsNullOperatorMutator
    case 5108: {
        categorySpinner = null;
        break;
    }
    // WidgetConfigurationActivity_6_InvalidIDFindViewOperatorMutator
    case 6108: {
        categorySpinner = findViewById(732221);
        break;
    }
    // WidgetConfigurationActivity_7_InvalidViewFocusOperatorMutator
    case 7108: {
        /**
        * Inserted by Kadabra
        */
        categorySpinner = findViewById(it.feio.android.omninotes.R.id.widget_config_spinner);
        categorySpinner.requestFocus();
        break;
    }
    // WidgetConfigurationActivity_8_ViewComponentNotVisibleOperatorMutator
    case 8108: {
        /**
        * Inserted by Kadabra
        */
        categorySpinner = findViewById(it.feio.android.omninotes.R.id.widget_config_spinner);
        categorySpinner.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    categorySpinner = findViewById(it.feio.android.omninotes.R.id.widget_config_spinner);
    break;
}
}
categorySpinner.setEnabled(false);
it.feio.android.omninotes.db.DbHelper db;
db = it.feio.android.omninotes.db.DbHelper.getInstance();
java.util.ArrayList<it.feio.android.omninotes.models.Category> categories;
categories = db.getCategories();
categorySpinner.setAdapter(new it.feio.android.omninotes.models.adapters.CategoryBaseAdapter(this, categories));
android.widget.Button configOkButton;
switch(MUID_STATIC) {
// WidgetConfigurationActivity_9_FindViewByIdReturnsNullOperatorMutator
case 9108: {
    configOkButton = null;
    break;
}
// WidgetConfigurationActivity_10_InvalidIDFindViewOperatorMutator
case 10108: {
    configOkButton = findViewById(732221);
    break;
}
// WidgetConfigurationActivity_11_InvalidViewFocusOperatorMutator
case 11108: {
    /**
    * Inserted by Kadabra
    */
    configOkButton = findViewById(it.feio.android.omninotes.R.id.widget_config_confirm);
    configOkButton.requestFocus();
    break;
}
// WidgetConfigurationActivity_12_ViewComponentNotVisibleOperatorMutator
case 12108: {
    /**
    * Inserted by Kadabra
    */
    configOkButton = findViewById(it.feio.android.omninotes.R.id.widget_config_confirm);
    configOkButton.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
configOkButton = findViewById(it.feio.android.omninotes.R.id.widget_config_confirm);
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigurationActivity_13_BuggyGUIListenerOperatorMutator
case 13108: {
configOkButton.setOnClickListener(null);
break;
}
default: {
configOkButton.setOnClickListener((android.view.View v) -> {
if (mRadioGroup.getCheckedRadioButtonId() == it.feio.android.omninotes.R.id.widget_config_notes) {
    sqlCondition = ((((" WHERE " + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " IS NOT 1 AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS") + " NOT 1 ";
} else {
    it.feio.android.omninotes.models.Category tag;
    tag = ((it.feio.android.omninotes.models.Category) (categorySpinner.getSelectedItem()));
    sqlCondition = ((((((((((" WHERE " + it.feio.android.omninotes.db.DbHelper.TABLE_NOTES) + ".") + it.feio.android.omninotes.db.DbHelper.KEY_CATEGORY) + " = ") + tag.getId()) + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_ARCHIVED) + " IS NOT 1") + " AND ") + it.feio.android.omninotes.db.DbHelper.KEY_TRASHED) + " IS NOT 1";
}
android.widget.CheckBox showThumbnailsCheckBox;
switch(MUID_STATIC) {
    // WidgetConfigurationActivity_14_InvalidIDFindViewOperatorMutator
    case 14108: {
        showThumbnailsCheckBox = findViewById(732221);
        break;
    }
    // WidgetConfigurationActivity_15_InvalidViewFocusOperatorMutator
    case 15108: {
        /**
        * Inserted by Kadabra
        */
        showThumbnailsCheckBox = findViewById(it.feio.android.omninotes.R.id.show_thumbnails);
        showThumbnailsCheckBox.requestFocus();
        break;
    }
    // WidgetConfigurationActivity_16_ViewComponentNotVisibleOperatorMutator
    case 16108: {
        /**
        * Inserted by Kadabra
        */
        showThumbnailsCheckBox = findViewById(it.feio.android.omninotes.R.id.show_thumbnails);
        showThumbnailsCheckBox.setVisibility(android.view.View.INVISIBLE);
        break;
    }
    default: {
    showThumbnailsCheckBox = findViewById(it.feio.android.omninotes.R.id.show_thumbnails);
    break;
}
}
android.widget.CheckBox showTimestampsCheckBox;
switch(MUID_STATIC) {
// WidgetConfigurationActivity_17_InvalidIDFindViewOperatorMutator
case 17108: {
    showTimestampsCheckBox = findViewById(732221);
    break;
}
// WidgetConfigurationActivity_18_InvalidViewFocusOperatorMutator
case 18108: {
    /**
    * Inserted by Kadabra
    */
    showTimestampsCheckBox = findViewById(it.feio.android.omninotes.R.id.show_timestamps);
    showTimestampsCheckBox.requestFocus();
    break;
}
// WidgetConfigurationActivity_19_ViewComponentNotVisibleOperatorMutator
case 19108: {
    /**
    * Inserted by Kadabra
    */
    showTimestampsCheckBox = findViewById(it.feio.android.omninotes.R.id.show_timestamps);
    showTimestampsCheckBox.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
showTimestampsCheckBox = findViewById(it.feio.android.omninotes.R.id.show_timestamps);
break;
}
}
// Updating the ListRemoteViewsFactory parameter to get the list of notes
it.feio.android.omninotes.widget.ListRemoteViewsFactory.updateConfiguration(mAppWidgetId, sqlCondition, showThumbnailsCheckBox.isChecked(), showTimestampsCheckBox.isChecked());
android.content.Intent resultValue;
switch(MUID_STATIC) {
// WidgetConfigurationActivity_20_RandomActionIntentDefinitionOperatorMutator
case 20108: {
resultValue = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
resultValue = new android.content.Intent();
break;
}
}
switch(MUID_STATIC) {
// WidgetConfigurationActivity_21_NullValueIntentPutExtraOperatorMutator
case 21108: {
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, new Parcelable[0]);
break;
}
// WidgetConfigurationActivity_22_IntentPayloadReplacementOperatorMutator
case 22108: {
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
break;
}
default: {
switch(MUID_STATIC) {
// WidgetConfigurationActivity_23_RandomActionIntentDefinitionOperatorMutator
case 23108: {
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
resultValue.putExtra(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
break;
}
}
break;
}
}
setResult(android.app.Activity.RESULT_OK, resultValue);
finish();
});
break;
}
}
// Checks if no tags are available and then disable that option
if (categories.isEmpty()) {
mRadioGroup.setVisibility(android.view.View.GONE);
categorySpinner.setVisibility(android.view.View.GONE);
}
android.content.Intent intent;
switch(MUID_STATIC) {
// WidgetConfigurationActivity_24_RandomActionIntentDefinitionOperatorMutator
case 24108: {
intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
break;
}
default: {
intent = getIntent();
break;
}
}
android.os.Bundle extras;
extras = intent.getExtras();
if (extras != null) {
mAppWidgetId = extras.getInt(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID, android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID);
}
// If they gave us an intent without the widget ID, just bail.
if (mAppWidgetId == android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID) {
finish();
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
