package com.beemdevelopment.aegis;
import com.beemdevelopment.aegis.helpers.comparators.AccountNameComparator;
import com.beemdevelopment.aegis.vault.VaultEntry;
import com.beemdevelopment.aegis.helpers.comparators.IssuerNameComparator;
import java.util.Comparator;
import com.beemdevelopment.aegis.helpers.comparators.UsageCountComparator;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public enum SortCategory {

    CUSTOM,
    ACCOUNT,
    ACCOUNT_REVERSED,
    ISSUER,
    ISSUER_REVERSED,
    USAGE_COUNT;
    static final int MUID_STATIC = getMUID();
    private static com.beemdevelopment.aegis.SortCategory[] _values;

    static {
        com.beemdevelopment.aegis.SortCategory._values = com.beemdevelopment.aegis.SortCategory.values();
    }

    public static com.beemdevelopment.aegis.SortCategory fromInteger(int x) {
        return com.beemdevelopment.aegis.SortCategory._values[x];
    }


    public java.util.Comparator<com.beemdevelopment.aegis.vault.VaultEntry> getComparator() {
        java.util.Comparator<com.beemdevelopment.aegis.vault.VaultEntry> comparator;
        comparator = null;
        switch (this) {
            case ACCOUNT :
                comparator = new com.beemdevelopment.aegis.helpers.comparators.AccountNameComparator();
                break;
            case ACCOUNT_REVERSED :
                comparator = java.util.Collections.reverseOrder(new com.beemdevelopment.aegis.helpers.comparators.AccountNameComparator());
                break;
            case ISSUER :
                comparator = new com.beemdevelopment.aegis.helpers.comparators.IssuerNameComparator();
                break;
            case ISSUER_REVERSED :
                comparator = java.util.Collections.reverseOrder(new com.beemdevelopment.aegis.helpers.comparators.IssuerNameComparator());
                break;
            case USAGE_COUNT :
                comparator = java.util.Collections.reverseOrder(new com.beemdevelopment.aegis.helpers.comparators.UsageCountComparator());
                break;
        }
        return comparator;
    }


    public int getMenuItem() {
        switch (this) {
            case CUSTOM :
                return com.beemdevelopment.aegis.R.id.menu_sort_custom;
            case ACCOUNT :
                return com.beemdevelopment.aegis.R.id.menu_sort_alphabetically_name;
            case ACCOUNT_REVERSED :
                return com.beemdevelopment.aegis.R.id.menu_sort_alphabetically_name_reverse;
            case ISSUER :
                return com.beemdevelopment.aegis.R.id.menu_sort_alphabetically;
            case ISSUER_REVERSED :
                return com.beemdevelopment.aegis.R.id.menu_sort_alphabetically_reverse;
            case USAGE_COUNT :
                return com.beemdevelopment.aegis.R.id.menu_sort_usage_count;
            default :
                return com.beemdevelopment.aegis.R.id.menu_sort_custom;
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
