/* Copyright 2009-2012 Brian Pellin.

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
package com.keepassdroid.view;
import com.keepassdroid.app.App;
import android.view.Menu;
import android.view.ContextMenu;
import android.view.MenuItem;
import com.keepassdroid.GroupActivity;
import android.view.View;
import com.keepassdroid.database.PwGroupV3;
import com.android.keepass.R;
import android.view.ContextMenu.ContextMenuInfo;
import com.keepassdroid.database.PwGroup;
import android.widget.ImageView;
import com.keepassdroid.settings.PrefsUtil;
import android.widget.TextView;
import com.keepassdroid.GroupBaseActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwGroupView extends com.keepassdroid.view.ClickView {
    static final int MUID_STATIC = getMUID();
    protected com.keepassdroid.database.PwGroup mPw;

    protected com.keepassdroid.GroupBaseActivity mAct;

    protected android.widget.TextView mTv;

    protected static final int MENU_OPEN = android.view.Menu.FIRST;

    public static com.keepassdroid.view.PwGroupView getInstance(com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.PwGroup pw) {
        if (pw instanceof com.keepassdroid.database.PwGroupV3) {
            return new com.keepassdroid.view.PwGroupViewV3(act, pw);
        } else {
            return new com.keepassdroid.view.PwGroupView(act, pw);
        }
    }


    protected PwGroupView(com.keepassdroid.GroupBaseActivity act, com.keepassdroid.database.PwGroup pw) {
        super(act);
        mAct = act;
        android.view.View gv;
        gv = android.view.View.inflate(act, com.android.keepass.R.layout.group_list_entry, null);
        switch(MUID_STATIC) {
            // PwGroupView_0_InvalidViewFocusOperatorMutator
            case 17: {
                /**
                * Inserted by Kadabra
                */
                mTv = ((android.widget.TextView) (gv.findViewById(com.android.keepass.R.id.group_text)));
                mTv.requestFocus();
                break;
            }
            // PwGroupView_1_ViewComponentNotVisibleOperatorMutator
            case 1017: {
                /**
                * Inserted by Kadabra
                */
                mTv = ((android.widget.TextView) (gv.findViewById(com.android.keepass.R.id.group_text)));
                mTv.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            mTv = ((android.widget.TextView) (gv.findViewById(com.android.keepass.R.id.group_text)));
            break;
        }
    }
    float size;
    size = com.keepassdroid.settings.PrefsUtil.getListTextSize(act);
    mTv.setTextSize(size);
    android.widget.TextView label;
    switch(MUID_STATIC) {
        // PwGroupView_2_InvalidViewFocusOperatorMutator
        case 2017: {
            /**
            * Inserted by Kadabra
            */
            label = ((android.widget.TextView) (gv.findViewById(com.android.keepass.R.id.group_label)));
            label.requestFocus();
            break;
        }
        // PwGroupView_3_ViewComponentNotVisibleOperatorMutator
        case 3017: {
            /**
            * Inserted by Kadabra
            */
            label = ((android.widget.TextView) (gv.findViewById(com.android.keepass.R.id.group_label)));
            label.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        label = ((android.widget.TextView) (gv.findViewById(com.android.keepass.R.id.group_label)));
        break;
    }
}
switch(MUID_STATIC) {
    // PwGroupView_4_BinaryMutator
    case 4017: {
        label.setTextSize(size + 8);
        break;
    }
    default: {
    label.setTextSize(size - 8);
    break;
}
}
populateView(gv, pw);
android.widget.LinearLayout.LayoutParams lp;
lp = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.FILL_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
addView(gv, lp);
}


private void populateView(android.view.View gv, com.keepassdroid.database.PwGroup pw) {
mPw = pw;
android.widget.ImageView iv;
switch(MUID_STATIC) {
// PwGroupView_5_InvalidViewFocusOperatorMutator
case 5017: {
    /**
    * Inserted by Kadabra
    */
    iv = ((android.widget.ImageView) (gv.findViewById(com.android.keepass.R.id.group_icon)));
    iv.requestFocus();
    break;
}
// PwGroupView_6_ViewComponentNotVisibleOperatorMutator
case 6017: {
    /**
    * Inserted by Kadabra
    */
    iv = ((android.widget.ImageView) (gv.findViewById(com.android.keepass.R.id.group_icon)));
    iv.setVisibility(android.view.View.INVISIBLE);
    break;
}
default: {
iv = ((android.widget.ImageView) (gv.findViewById(com.android.keepass.R.id.group_icon)));
break;
}
}
com.keepassdroid.app.App.getDB().drawFactory.assignDrawableTo(iv, getResources(), pw.getIcon());
mTv.setText(pw.getName());
}


public void convertView(com.keepassdroid.database.PwGroup pw) {
populateView(this, pw);
}


public void onClick() {
launchGroup();
}


private void launchGroup() {
com.keepassdroid.GroupActivity.Launch(mAct, mPw);
}


@java.lang.Override
public void onCreateMenu(android.view.ContextMenu menu, android.view.ContextMenu.ContextMenuInfo menuInfo) {
menu.add(0, com.keepassdroid.view.PwGroupView.MENU_OPEN, 0, com.android.keepass.R.string.menu_open);
}


@java.lang.Override
public boolean onContextItemSelected(android.view.MenuItem item) {
switch (item.getItemId()) {
case com.keepassdroid.view.PwGroupView.MENU_OPEN :
launchGroup();
return true;
default :
return false;
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
