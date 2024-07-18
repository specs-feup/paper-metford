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
import android.widget.Filter;
import android.widget.Filterable;
import java.util.List;
import it.feio.android.omninotes.utils.GeocodeHelper;
import android.content.Context;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PlacesAutoCompleteAdapter extends android.widget.ArrayAdapter<java.lang.String> implements android.widget.Filterable {
    static final int MUID_STATIC = getMUID();
    private static final int MIN_CHARS = 7;

    private java.util.List<java.lang.String> resultList;

    public PlacesAutoCompleteAdapter(android.content.Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }


    @java.lang.Override
    public int getCount() {
        return resultList.size();
    }


    @java.lang.Override
    public java.lang.String getItem(int index) {
        return resultList.get(index);
    }


    @java.lang.Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @java.lang.Override
            protected android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence constraint) {
                android.widget.Filter.FilterResults filterResults;
                filterResults = new android.widget.Filter.FilterResults();
                if ((constraint != null) && (constraint.length() > it.feio.android.omninotes.models.adapters.PlacesAutoCompleteAdapter.MIN_CHARS)) {
                    // Retrieve the autocomplete results.
                    resultList = it.feio.android.omninotes.utils.GeocodeHelper.autocomplete(constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }
                return filterResults;
            }


            @java.lang.Override
            protected void publishResults(java.lang.CharSequence constraint, android.widget.Filter.FilterResults results) {
                if ((results != null) && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

        };
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
