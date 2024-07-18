/* Copyright 2009-2020 Brian Pellin.

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
package com.keepassdroid.search;
import com.keepassdroid.PwGroupListAdapter;
import com.android.keepass.R;
import com.keepassdroid.app.App;
import android.app.SearchManager;
import com.keepassdroid.view.GroupViewOnlyView;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import com.android.keepass.KeePass;
import com.keepassdroid.GroupBaseActivity;
import com.keepassdroid.view.GroupEmptyView;
import com.keepassdroid.Database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SearchResults extends com.keepassdroid.GroupBaseActivity {
    static final int MUID_STATIC = getMUID();
    private com.keepassdroid.Database mDb;

    // private String mQuery;
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch(MUID_STATIC) {
            // SearchResults_0_LengthyGUICreationOperatorMutator
            case 193: {
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
    if (isFinishing()) {
        return;
    }
    setResult(com.android.keepass.KeePass.EXIT_NORMAL);
    mDb = com.keepassdroid.app.App.getDB();
    // Likely the app has been killed exit the activity
    if (!mDb.Loaded()) {
        finish();
    }
    performSearch(getSearchStr(getIntent()));
}


private void performSearch(java.lang.String query) {
    query(query.trim());
}


private void query(java.lang.String query) {
    mGroup = mDb.Search(query);
    if ((mGroup == null) || (mGroup.childEntries.size() < 1)) {
        setContentView(new com.keepassdroid.view.GroupEmptyView(this));
    } else {
        setContentView(new com.keepassdroid.view.GroupViewOnlyView(this));
    }
    androidx.appcompat.widget.Toolbar toolbar;
    switch(MUID_STATIC) {
        // SearchResults_1_InvalidViewFocusOperatorMutator
        case 1193: {
            /**
            * Inserted by Kadabra
            */
            toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
            toolbar.requestFocus();
            break;
        }
        // SearchResults_2_ViewComponentNotVisibleOperatorMutator
        case 2193: {
            /**
            * Inserted by Kadabra
            */
            toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
            toolbar.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        toolbar = ((androidx.appcompat.widget.Toolbar) (findViewById(com.android.keepass.R.id.toolbar)));
        break;
    }
}
setSupportActionBar(toolbar);
setGroupTitle();
setListAdapter(new com.keepassdroid.PwGroupListAdapter(this, mGroup));
}


/* @Override
protected void onNewIntent(Intent intent) {
super.onNewIntent(intent);

mQuery = getSearchStr(intent);
performSearch();
//mGroup = processSearchIntent(intent);
//assert(mGroup != null);
}
 */
private java.lang.String getSearchStr(android.content.Intent queryIntent) {
// get and process search query here
final java.lang.String queryAction;
queryAction = queryIntent.getAction();
if (android.content.Intent.ACTION_SEARCH.equals(queryAction)) {
    return queryIntent.getStringExtra(android.app.SearchManager.QUERY);
}
return "";
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
