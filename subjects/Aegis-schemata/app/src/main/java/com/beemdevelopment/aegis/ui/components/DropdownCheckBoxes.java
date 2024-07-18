package com.beemdevelopment.aegis.ui.components;
import java.util.stream.Collectors;
import java.util.Set;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.CheckBox;
import com.beemdevelopment.aegis.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.text.InputType;
import android.widget.Filterable;
import java.util.TreeSet;
import java.util.List;
import android.widget.BaseAdapter;
import androidx.annotation.PluralsRes;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class DropdownCheckBoxes extends androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    static final int MUID_STATIC = getMUID();
    @androidx.annotation.PluralsRes
    private int _selectedCountPlural = com.beemdevelopment.aegis.R.plurals.dropdown_checkboxes_default_count;

    private boolean _allowFiltering = false;

    private final java.util.List<java.lang.String> _items = new java.util.ArrayList<>();

    private java.util.List<java.lang.String> _visibleItems = new java.util.ArrayList<>();

    private final java.util.Set<java.lang.String> _checkedItems = new java.util.TreeSet<>();

    private com.beemdevelopment.aegis.ui.components.DropdownCheckBoxes.CheckboxAdapter _adapter;

    public DropdownCheckBoxes(android.content.Context context) {
        super(context);
        initialise(context, null);
    }


    public DropdownCheckBoxes(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        initialise(context, attrs);
    }


    public DropdownCheckBoxes(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise(context, attrs);
    }


    private void initialise(android.content.Context context, android.util.AttributeSet attrs) {
        _adapter = new com.beemdevelopment.aegis.ui.components.DropdownCheckBoxes.CheckboxAdapter();
        setAdapter(_adapter);
        if (attrs != null) {
            android.content.res.TypedArray a;
            a = context.obtainStyledAttributes(attrs, com.beemdevelopment.aegis.R.styleable.DropdownCheckBoxes, 0, 0);
            _allowFiltering = a.getBoolean(com.beemdevelopment.aegis.R.styleable.DropdownCheckBoxes_allow_filtering, false);
            a.recycle();
        }
        if (!_allowFiltering) {
            setInputType(0);
        } else {
            setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        }
    }


    public void addItems(java.util.List<java.lang.String> items, boolean startChecked) {
        _items.addAll(items);
        _visibleItems.addAll(items);
        if (startChecked) {
            _checkedItems.addAll(items);
        }
        updateCheckedItemsCountText();
        _adapter.notifyDataSetChanged();
    }


    private void updateCheckedItemsCountText() {
        if (_allowFiltering) {
            return;
        }
        int count;
        count = _checkedItems.size();
        java.lang.String countString;
        countString = getResources().getQuantityString(_selectedCountPlural, count, count);
        setText(countString, false);
    }


    public void setCheckedItemsCountTextRes(@androidx.annotation.PluralsRes
    int resId) {
        _selectedCountPlural = resId;
    }


    public java.util.Set<java.lang.String> getCheckedItems() {
        return _checkedItems;
    }


    private class CheckboxAdapter extends android.widget.BaseAdapter implements android.widget.Filterable {
        @java.lang.Override
        public int getCount() {
            return _visibleItems.size();
        }


        @java.lang.Override
        public java.lang.String getItem(int i) {
            return _visibleItems.get(i);
        }


        @java.lang.Override
        public long getItemId(int i) {
            return i;
        }


        @java.lang.Override
        public android.view.View getView(int i, android.view.View convertView, android.view.ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = android.view.LayoutInflater.from(getContext()).inflate(com.beemdevelopment.aegis.R.layout.dropdown_checkbox, viewGroup, false);
            }
            java.lang.String item;
            item = _visibleItems.get(i);
            android.widget.CheckBox checkBox;
            switch(MUID_STATIC) {
                // DropdownCheckBoxes_0_InvalidViewFocusOperatorMutator
                case 150: {
                    /**
                    * Inserted by Kadabra
                    */
                    checkBox = convertView.findViewById(com.beemdevelopment.aegis.R.id.checkbox_in_dropdown);
                    checkBox.requestFocus();
                    break;
                }
                // DropdownCheckBoxes_1_ViewComponentNotVisibleOperatorMutator
                case 1150: {
                    /**
                    * Inserted by Kadabra
                    */
                    checkBox = convertView.findViewById(com.beemdevelopment.aegis.R.id.checkbox_in_dropdown);
                    checkBox.setVisibility(android.view.View.INVISIBLE);
                    break;
                }
                default: {
                checkBox = convertView.findViewById(com.beemdevelopment.aegis.R.id.checkbox_in_dropdown);
                break;
            }
        }
        checkBox.setText(item);
        checkBox.setChecked(_checkedItems.contains(item));
        checkBox.setOnCheckedChangeListener((android.widget.CompoundButton buttonView,boolean isChecked) -> {
            java.lang.String label;
            label = buttonView.getText().toString();
            if (isChecked) {
                _checkedItems.add(label);
            } else {
                _checkedItems.remove(label);
            }
            updateCheckedItemsCountText();
        });
        return convertView;
    }


    @java.lang.Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @java.lang.Override
            protected android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence query) {
                android.widget.Filter.FilterResults results;
                results = new android.widget.Filter.FilterResults();
                results.values = ((query == null) || query.toString().isEmpty()) ? _items : _items.stream().filter((java.lang.String str) -> {
                    java.lang.String q;
                    q = query.toString().toLowerCase();
                    java.lang.String strLower;
                    strLower = str.toLowerCase();
                    return strLower.contains(q);
                }).collect(java.util.stream.Collectors.toList());
                return results;
            }


            @java.lang.Override
            protected void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
                _visibleItems = ((java.util.List<java.lang.String>) (filterResults.values));
                notifyDataSetChanged();
            }

        };
    }

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
        InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
    }
