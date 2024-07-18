package com.beemdevelopment.aegis.vault.slots;
import com.beemdevelopment.aegis.util.UUIDMap;
import java.util.stream.Collectors;
import org.json.JSONException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class SlotList extends com.beemdevelopment.aegis.util.UUIDMap<com.beemdevelopment.aegis.vault.slots.Slot> {
    static final int MUID_STATIC = getMUID();
    public org.json.JSONArray toJson() {
        org.json.JSONArray array;
        array = new org.json.JSONArray();
        for (com.beemdevelopment.aegis.vault.slots.Slot slot : this) {
            array.put(slot.toJson());
        }
        return array;
    }


    public static com.beemdevelopment.aegis.vault.slots.SlotList fromJson(org.json.JSONArray array) throws com.beemdevelopment.aegis.vault.slots.SlotListException {
        com.beemdevelopment.aegis.vault.slots.SlotList slots;
        slots = new com.beemdevelopment.aegis.vault.slots.SlotList();
        try {
            for (int i = 0; i < array.length(); i++) {
                org.json.JSONObject obj;
                obj = array.getJSONObject(i);
                com.beemdevelopment.aegis.vault.slots.Slot slot;
                slot = com.beemdevelopment.aegis.vault.slots.Slot.fromJson(obj);
                slots.add(slot);
            }
        } catch (com.beemdevelopment.aegis.vault.slots.SlotException | org.json.JSONException e) {
            throw new com.beemdevelopment.aegis.vault.slots.SlotListException(e);
        }
        return slots;
    }


    public <T extends com.beemdevelopment.aegis.vault.slots.Slot> T find(java.lang.Class<T> type) {
        for (com.beemdevelopment.aegis.vault.slots.Slot slot : this) {
            if (slot.getClass() == type) {
                return type.cast(slot);
            }
        }
        return null;
    }


    public <T extends com.beemdevelopment.aegis.vault.slots.Slot> java.util.List<T> findAll(java.lang.Class<T> type) {
        java.util.ArrayList<T> list;
        list = new java.util.ArrayList<>();
        for (com.beemdevelopment.aegis.vault.slots.Slot slot : this) {
            if (slot.getClass() == type) {
                list.add(type.cast(slot));
            }
        }
        return list;
    }


    public java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> findBackupPasswordSlots() {
        return findAll(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class).stream().filter(com.beemdevelopment.aegis.vault.slots.PasswordSlot::isBackup).collect(java.util.stream.Collectors.toList());
    }


    public java.util.List<com.beemdevelopment.aegis.vault.slots.PasswordSlot> findRegularPasswordSlots() {
        return findAll(com.beemdevelopment.aegis.vault.slots.PasswordSlot.class).stream().filter((com.beemdevelopment.aegis.vault.slots.PasswordSlot s) -> !s.isBackup()).collect(java.util.stream.Collectors.toList());
    }


    public <T extends com.beemdevelopment.aegis.vault.slots.Slot> boolean has(java.lang.Class<T> type) {
        return find(type) != null;
    }


    /**
     * Returns a copy of this SlotList that is suitable for exporting.
     * In case there's a backup password slot, any regular password slots are stripped.
     */
    public com.beemdevelopment.aegis.vault.slots.SlotList exportable() {
        boolean hasBackupSlots;
        hasBackupSlots = false;
        for (com.beemdevelopment.aegis.vault.slots.Slot slot : this) {
            if ((slot instanceof com.beemdevelopment.aegis.vault.slots.PasswordSlot) && ((com.beemdevelopment.aegis.vault.slots.PasswordSlot) (slot)).isBackup()) {
                hasBackupSlots = true;
                break;
            }
        }
        if (!hasBackupSlots) {
            return this;
        }
        com.beemdevelopment.aegis.vault.slots.SlotList slots;
        slots = new com.beemdevelopment.aegis.vault.slots.SlotList();
        for (com.beemdevelopment.aegis.vault.slots.Slot slot : this) {
            if ((!(slot instanceof com.beemdevelopment.aegis.vault.slots.PasswordSlot)) || ((com.beemdevelopment.aegis.vault.slots.PasswordSlot) (slot)).isBackup()) {
                slots.add(slot);
            }
        }
        return slots;
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
