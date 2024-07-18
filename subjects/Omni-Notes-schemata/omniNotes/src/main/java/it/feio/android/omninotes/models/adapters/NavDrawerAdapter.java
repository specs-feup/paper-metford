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
package it.feio.android.omninotes.models.adapters;
import android.view.ViewGroup;
import android.graphics.Typeface;
import it.feio.android.omninotes.MainActivity;
import com.pixplicity.easyprefs.library.Prefs;
import android.view.View;
import it.feio.android.omninotes.R;
import android.view.LayoutInflater;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.widget.ImageView;
import com.neopixl.pixlui.components.textview.TextView;
import java.util.List;
import android.widget.BaseAdapter;
import it.feio.android.omninotes.models.NavigationItem;
import java.util.Arrays;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NavDrawerAdapter extends android.widget.BaseAdapter {
    static final int MUID_STATIC = getMUID();
    private android.app.Activity mActivity;

    private java.util.List<it.feio.android.omninotes.models.NavigationItem> items;

    private android.view.LayoutInflater inflater;

    public NavDrawerAdapter(android.app.Activity mActivity, java.util.List<it.feio.android.omninotes.models.NavigationItem> items) {
        this.mActivity = mActivity;
        this.items = items;
        inflater = ((android.view.LayoutInflater) (mActivity.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
    }


    @java.lang.Override
    public int getCount() {
        return items.size();
    }


    @java.lang.Override
    public java.lang.Object getItem(int position) {
        return items.get(position);
    }


    @java.lang.Override
    public long getItemId(int position) {
        return position;
    }


    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        it.feio.android.omninotes.models.adapters.NoteDrawerAdapterViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(it.feio.android.omninotes.R.layout.drawer_list_item, parent, false);
            holder = new it.feio.android.omninotes.models.adapters.NoteDrawerAdapterViewHolder();
            switch(MUID_STATIC) {
                // NavDrawerAdapter_0_InvalidViewFocusOperatorMutator
                case 59: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.imgIcon = convertView.findViewById(it.feio.android.omninotes.R.id.icon);
                    holder.imgIcon.requestFocus();
                    break;
                }
                // NavDrawerAdapter_1_ViewComponentNotVisibleOperatorMutator
                case 1059: {
                    /**
                    * Inserted by Kadabra
                    */
                    holder.imgIcon = convertView.findViewById(it.feio.android.omninotes.R.id.icon);
                    holder.imgIcon.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                holder.imgIcon = convertView.findViewById(it.feio.android.omninotes.R.id.icon);
                break;
            }
        }
        switch(MUID_STATIC) {
            // NavDrawerAdapter_2_InvalidViewFocusOperatorMutator
            case 2059: {
                /**
                * Inserted by Kadabra
                */
                holder.txtTitle = convertView.findViewById(it.feio.android.omninotes.R.id.title);
                holder.txtTitle.requestFocus();
                break;
            }
            // NavDrawerAdapter_3_ViewComponentNotVisibleOperatorMutator
            case 3059: {
                /**
                * Inserted by Kadabra
                */
                holder.txtTitle = convertView.findViewById(it.feio.android.omninotes.R.id.title);
                holder.txtTitle.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            holder.txtTitle = convertView.findViewById(it.feio.android.omninotes.R.id.title);
            break;
        }
    }
    convertView.setTag(holder);
} else {
    holder = ((it.feio.android.omninotes.models.adapters.NoteDrawerAdapterViewHolder) (convertView.getTag()));
}
// Set the results into TextViews
holder.txtTitle.setText(items.get(position).getText());
if (isSelected(position)) {
    holder.imgIcon.setImageResource(items.get(position).getIconSelected());
    holder.txtTitle.setTypeface(null, android.graphics.Typeface.BOLD);
    int color;
    color = mActivity.getResources().getColor(it.feio.android.omninotes.R.color.colorPrimaryDark);
    holder.txtTitle.setTextColor(color);
    holder.imgIcon.getDrawable().mutate().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_ATOP);
} else {
    holder.imgIcon.setImageResource(items.get(position).getIcon());
    holder.txtTitle.setTypeface(null, android.graphics.Typeface.NORMAL);
    holder.txtTitle.setTextColor(mActivity.getResources().getColor(it.feio.android.omninotes.R.color.drawer_text));
}
return convertView;
}


private boolean isSelected(int position) {
// Getting actual navigation selection
java.lang.String[] navigationListCodes;
navigationListCodes = mActivity.getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list_codes);
// Managing temporary navigation indicator when coming from a widget
java.lang.String navigationTmp;
navigationTmp = (it.feio.android.omninotes.MainActivity.class.isAssignableFrom(mActivity.getClass())) ? ((it.feio.android.omninotes.MainActivity) (mActivity)).getNavigationTmp() : null;
java.lang.String navigation;
navigation = (navigationTmp != null) ? navigationTmp : com.pixplicity.easyprefs.library.Prefs.getString(it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION, navigationListCodes[0]);
// Finding selected item from standard navigation items or tags
int index;
index = java.util.Arrays.asList(navigationListCodes).indexOf(navigation);
if (index == (-1)) {
    return false;
}
java.lang.String navigationLocalized;
navigationLocalized = mActivity.getResources().getStringArray(it.feio.android.omninotes.R.array.navigation_list)[index];
return navigationLocalized.equals(items.get(position).getText());
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}/**
 * Holder object
 *
 * @author fede
 */
class NoteDrawerAdapterViewHolder {
    android.widget.ImageView imgIcon;

    com.neopixl.pixlui.components.textview.TextView txtTitle;
}
