package com.beemdevelopment.aegis.vault;
import com.beemdevelopment.aegis.util.UUIDMap;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import androidx.annotation.Nullable;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class Vault {
    static final int MUID_STATIC = getMUID();
    private static final int VERSION = 2;

    private com.beemdevelopment.aegis.util.UUIDMap<com.beemdevelopment.aegis.vault.VaultEntry> _entries = new com.beemdevelopment.aegis.util.UUIDMap<>();

    public org.json.JSONObject toJson() {
        return toJson(null);
    }


    public org.json.JSONObject toJson(@androidx.annotation.Nullable
    com.beemdevelopment.aegis.vault.Vault.EntryFilter filter) {
        try {
            org.json.JSONArray array;
            array = new org.json.JSONArray();
            for (com.beemdevelopment.aegis.vault.VaultEntry e : _entries) {
                if ((filter == null) || filter.includeEntry(e)) {
                    array.put(e.toJson());
                }
            }
            org.json.JSONObject obj;
            obj = new org.json.JSONObject();
            obj.put("version", com.beemdevelopment.aegis.vault.Vault.VERSION);
            obj.put("entries", array);
            return obj;
        } catch (org.json.JSONException e) {
            throw new java.lang.RuntimeException(e);
        }
    }


    public static com.beemdevelopment.aegis.vault.Vault fromJson(org.json.JSONObject obj) throws com.beemdevelopment.aegis.vault.VaultException {
        com.beemdevelopment.aegis.vault.Vault vault;
        vault = new com.beemdevelopment.aegis.vault.Vault();
        com.beemdevelopment.aegis.util.UUIDMap<com.beemdevelopment.aegis.vault.VaultEntry> entries;
        entries = vault.getEntries();
        try {
            int ver;
            ver = obj.getInt("version");
            if (ver > com.beemdevelopment.aegis.vault.Vault.VERSION) {
                throw new com.beemdevelopment.aegis.vault.VaultException("Unsupported version");
            }
            org.json.JSONArray array;
            array = obj.getJSONArray("entries");
            for (int i = 0; i < array.length(); i++) {
                com.beemdevelopment.aegis.vault.VaultEntry entry;
                entry = com.beemdevelopment.aegis.vault.VaultEntry.fromJson(array.getJSONObject(i));
                entries.add(entry);
            }
        } catch (com.beemdevelopment.aegis.vault.VaultEntryException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.vault.VaultException(e);
        }
        return vault;
    }


    public com.beemdevelopment.aegis.util.UUIDMap<com.beemdevelopment.aegis.vault.VaultEntry> getEntries() {
        return _entries;
    }


    public interface EntryFilter {
        boolean includeEntry(com.beemdevelopment.aegis.vault.VaultEntry entry);

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
