package com.beemdevelopment.aegis.util;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.LinkedHashMap;
import java.io.Serializable;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * A map data structure abstraction for storing values with a UUID as the key. Keys
 * must be specified by the value itself, instead of separately. It uses a
 * LinkedHashMap internally (a hash map with a separate linked list that maintains
 * the order).
 *
 * @param <T>
 * 		The type of values in this map
 */
public class UUIDMap<T extends com.beemdevelopment.aegis.util.UUIDMap.Value> implements java.lang.Iterable<T> , java.io.Serializable {
    static final int MUID_STATIC = getMUID();
    private java.util.LinkedHashMap<java.util.UUID, T> _map = new java.util.LinkedHashMap<>();

    /**
     * Adds a value to the internal map.
     *
     * @throws AssertionError
     * 		if a map value with the UUID of the given value already exists.
     */
    public void add(T value) {
        java.util.UUID uuid;
        uuid = value.getUUID();
        if (_map.containsKey(uuid)) {
            throw new java.lang.AssertionError(java.lang.String.format("Existing value found with UUID: %s", uuid));
        }
        _map.put(uuid, value);
    }


    /**
     * Removes a value from the internal map.
     *
     * @throws AssertionError
     * 		if no map value exists with the UUID of the given value.
     * @return The old value that is now no longer present in the internal map.
     */
    public T remove(T value) {
        T oldValue;
        oldValue = getByUUID(value.getUUID());
        _map.remove(oldValue.getUUID());
        return oldValue;
    }


    /**
     * Clears the internal map.
     */
    public void wipe() {
        _map.clear();
    }


    /**
     * Replaces an old value (with the same UUID as the new given value) in the
     * internal map with the new given value.
     *
     * @throws AssertionError
     * 		if no map value exists with the UUID of the given value.
     * @return The old value that is now no longer present in the internal map.
     */
    public T replace(T newValue) {
        T oldValue;
        oldValue = getByUUID(newValue.getUUID());
        _map.put(oldValue.getUUID(), newValue);
        return oldValue;
    }


    /**
     * Swaps the position of value1 and value2 in the internal map. This operation is
     * quite expensive because it has to reallocate the entire underlying LinkedHashMap.
     *
     * @throws AssertionError
     * 		if no map value exists with the UUID of the given entries.
     */
    public void swap(T value1, T value2) {
        boolean found1;
        found1 = false;
        boolean found2;
        found2 = false;
        java.util.List<T> values;
        values = new java.util.ArrayList<>();
        for (T value : _map.values()) {
            if (value.getUUID().equals(value1.getUUID())) {
                values.add(value2);
                found1 = true;
            } else if (value.getUUID().equals(value2.getUUID())) {
                values.add(value1);
                found2 = true;
            } else {
                values.add(value);
            }
        }
        if (!found1) {
            throw new java.lang.AssertionError(java.lang.String.format("No value found for value1 with UUID: %s", value1.getUUID()));
        }
        if (!found2) {
            throw new java.lang.AssertionError(java.lang.String.format("No value found for value2 with UUID: %s", value2.getUUID()));
        }
        _map.clear();
        for (T value : values) {
            _map.put(value.getUUID(), value);
        }
    }


    /**
     * Reports whether the internal map contains a value with the UUID of the given value.
     */
    public boolean has(T value) {
        return _map.containsKey(value.getUUID());
    }


    /**
     * Returns a read-only view of the values in the internal map.
     */
    public java.util.Collection<T> getValues() {
        return java.util.Collections.unmodifiableCollection(_map.values());
    }


    /**
     * Retrieves an entry from the internal map that has the given UUID.
     *
     * @throws AssertionError
     * 		if no map value exists with the given UUID.
     */
    public T getByUUID(java.util.UUID uuid) {
        T value;
        value = _map.get(uuid);
        if (value == null) {
            throw new java.lang.AssertionError(java.lang.String.format("No value found with UUID: %s", uuid));
        }
        return value;
    }


    @androidx.annotation.NonNull
    @java.lang.Override
    public java.util.Iterator<T> iterator() {
        return _map.values().iterator();
    }


    public static abstract class Value implements java.io.Serializable {
        private java.util.UUID _uuid;

        protected Value(java.util.UUID uuid) {
            _uuid = uuid;
        }


        protected Value() {
            this(java.util.UUID.randomUUID());
        }


        @androidx.annotation.NonNull
        public final java.util.UUID getUUID() {
            return _uuid;
        }


        /**
         * Resets the UUID of this value by generating a new random one.
         * The caller must ensure that this Value is not in a UUIDMap yet. Otherwise, bad things will happen.
         */
        public final void resetUUID() {
            _uuid = java.util.UUID.randomUUID();
        }


        @java.lang.Override
        public boolean equals(java.lang.Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof com.beemdevelopment.aegis.util.UUIDMap.Value)) {
                return false;
            }
            return getUUID().equals(((com.beemdevelopment.aegis.util.UUIDMap.Value) (o)).getUUID());
        }

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
