/* Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package org.apache.commons.collections.map;
import java.lang.ref.Reference;
import java.util.Set;
import java.util.ConcurrentModificationException;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.apache.commons.collections.MapIterator;
import java.lang.ref.WeakReference;
import java.lang.ref.SoftReference;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * An abstract implementation of a hash-based map that allows the entries to
 * be removed by the garbage collector.
 * <p>
 * This class implements all the features necessary for a subclass reference
 * hash-based map. Key-value entries are stored in instances of the
 * <code>ReferenceEntry</code> class which can be overridden and replaced.
 * The iterators can similarly be replaced, without the need to replace the KeySet,
 * EntrySet and Values view classes.
 * <p>
 * Overridable methods are provided to change the default hashing behaviour, and
 * to change how entries are added to and removed from the map. Hopefully, all you
 * need for unusual subclasses is here.
 * <p>
 * When you construct an <code>AbstractReferenceMap</code>, you can specify what
 * kind of references are used to store the map's keys and values.
 * If non-hard references are used, then the garbage collector can remove
 * mappings if a key or value becomes unreachable, or if the JVM's memory is
 * running low. For information on how the different reference types behave,
 * see {@link Reference}.
 * <p>
 * Different types of references can be specified for keys and values.
 * The keys can be configured to be weak but the values hard,
 * in which case this class will behave like a
 * <a href="http://java.sun.com/j2se/1.4/docs/api/java/util/WeakHashMap.html">
 * <code>WeakHashMap</code></a>. However, you can also specify hard keys and
 * weak values, or any other combination. The default constructor uses
 * hard keys and soft values, providing a memory-sensitive cache.
 * <p>
 * This {@link Map} implementation does <i>not</i> allow null elements.
 * Attempting to add a null key or value to the map will raise a
 * <code>NullPointerException</code>.
 * <p>
 * All the available iterators can be reset back to the start by casting to
 * <code>ResettableIterator</code> and calling <code>reset()</code>.
 * <p>
 * This implementation is not synchronized.
 * You can use {@link java.util.Collections#synchronizedMap} to
 * provide synchronized access to a <code>ReferenceMap</code>.
 *
 * @see java.lang.ref.Reference
 * @since Commons Collections 3.1 (extracted from ReferenceMap in 3.0)
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 * @author Paul Jack
 * @author Stephen Colebourne
 */
public abstract class AbstractReferenceMap extends org.apache.commons.collections.map.AbstractHashedMap {
    static final int MUID_STATIC = getMUID();
    /**
     * Constant indicating that hard references should be used
     */
    public static final int HARD = 0;

    /**
     * Constant indicating that soft references should be used
     */
    public static final int SOFT = 1;

    /**
     * Constant indicating that weak references should be used
     */
    public static final int WEAK = 2;

    /**
     * The reference type for keys.  Must be HARD, SOFT, WEAK.
     *
     * @serial  */
    protected int keyType;

    /**
     * The reference type for values.  Must be HARD, SOFT, WEAK.
     *
     * @serial  */
    protected int valueType;

    /**
     * Should the value be automatically purged when the associated key has been collected?
     */
    protected boolean purgeValues;

    /**
     * ReferenceQueue used to eliminate stale mappings.
     * See purge.
     */
    private transient java.lang.ref.ReferenceQueue queue;

    // -----------------------------------------------------------------------
    /**
     * Constructor used during deserialization.
     */
    protected AbstractReferenceMap() {
        super();
    }


    /**
     * Constructs a new empty map with the specified reference types,
     * load factor and initial capacity.
     *
     * @param keyType
     * 		the type of reference to use for keys;
     * 		must be {@link #HARD}, {@link #SOFT}, {@link #WEAK}
     * @param valueType
     * 		the type of reference to use for values;
     * 		must be {@link #HARD}, {@link #SOFT}, {@link #WEAK}
     * @param capacity
     * 		the initial capacity for the map
     * @param loadFactor
     * 		the load factor for the map
     * @param purgeValues
     * 		should the value be automatically purged when the
     * 		key is garbage collected
     */
    protected AbstractReferenceMap(int keyType, int valueType, int capacity, float loadFactor, boolean purgeValues) {
        super(capacity, loadFactor);
        org.apache.commons.collections.map.AbstractReferenceMap.verify("keyType", keyType);
        org.apache.commons.collections.map.AbstractReferenceMap.verify("valueType", valueType);
        this.keyType = keyType;
        this.valueType = valueType;
        this.purgeValues = purgeValues;
    }


    /**
     * Initialise this subclass during construction, cloning or deserialization.
     */
    protected void init() {
        queue = new java.lang.ref.ReferenceQueue();
    }


    // -----------------------------------------------------------------------
    /**
     * Checks the type int is a valid value.
     *
     * @param name
     * 		the name for error messages
     * @param type
     * 		the type value to check
     * @throws IllegalArgumentException
     * 		if the value if invalid
     */
    private static void verify(java.lang.String name, int type) {
        if ((type < org.apache.commons.collections.map.AbstractReferenceMap.HARD) || (type > org.apache.commons.collections.map.AbstractReferenceMap.WEAK)) {
            throw new java.lang.IllegalArgumentException(name + " must be HARD, SOFT, WEAK.");
        }
    }


    // -----------------------------------------------------------------------
    /**
     * Gets the size of the map.
     *
     * @return the size
     */
    public int size() {
        purgeBeforeRead();
        return super.size();
    }


    /**
     * Checks whether the map is currently empty.
     *
     * @return true if the map is currently size zero
     */
    public boolean isEmpty() {
        purgeBeforeRead();
        return super.isEmpty();
    }


    /**
     * Checks whether the map contains the specified key.
     *
     * @param key
     * 		the key to search for
     * @return true if the map contains the key
     */
    public boolean containsKey(java.lang.Object key) {
        purgeBeforeRead();
        java.util.Map.Entry entry;
        entry = getEntry(key);
        if (entry == null) {
            return false;
        }
        return entry.getValue() != null;
    }


    /**
     * Checks whether the map contains the specified value.
     *
     * @param value
     * 		the value to search for
     * @return true if the map contains the value
     */
    public boolean containsValue(java.lang.Object value) {
        purgeBeforeRead();
        if (value == null) {
            return false;
        }
        return super.containsValue(value);
    }


    /**
     * Gets the value mapped to the key specified.
     *
     * @param key
     * 		the key
     * @return the mapped value, null if no match
     */
    public java.lang.Object get(java.lang.Object key) {
        purgeBeforeRead();
        java.util.Map.Entry entry;
        entry = getEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }


    /**
     * Puts a key-value mapping into this map.
     * Neither the key nor the value may be null.
     *
     * @param key
     * 		the key to add, must not be null
     * @param value
     * 		the value to add, must not be null
     * @return the value previously mapped to this key, null if none
     * @throws NullPointerException
     * 		if either the key or value is null
     */
    public java.lang.Object put(java.lang.Object key, java.lang.Object value) {
        if (key == null) {
            throw new java.lang.NullPointerException("null keys not allowed");
        }
        if (value == null) {
            throw new java.lang.NullPointerException("null values not allowed");
        }
        purgeBeforeWrite();
        return super.put(key, value);
    }


    /**
     * Removes the specified mapping from this map.
     *
     * @param key
     * 		the mapping to remove
     * @return the value mapped to the removed key, null if key not in map
     */
    public java.lang.Object remove(java.lang.Object key) {
        if (key == null) {
            return null;
        }
        purgeBeforeWrite();
        return super.remove(key);
    }


    /**
     * Clears this map.
     */
    public void clear() {
        super.clear();
        while (queue.poll() != null) {
        } // drain the queue
        // drain the queue

    }


    // -----------------------------------------------------------------------
    /**
     * Gets a MapIterator over the reference map.
     * The iterator only returns valid key/value pairs.
     *
     * @return a map iterator
     */
    public org.apache.commons.collections.MapIterator mapIterator() {
        return new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceMapIterator(this);
    }


    /**
     * Returns a set view of this map's entries.
     * An iterator returned entry is valid until <code>next()</code> is called again.
     * The <code>setValue()</code> method on the <code>toArray</code> entries has no effect.
     *
     * @return a set view of this map's entries
     */
    public java.util.Set entrySet() {
        if (entrySet == null) {
            entrySet = new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntrySet(this);
        }
        return entrySet;
    }


    /**
     * Returns a set view of this map's keys.
     *
     * @return a set view of this map's keys
     */
    public java.util.Set keySet() {
        if (keySet == null) {
            keySet = new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceKeySet(this);
        }
        return keySet;
    }


    /**
     * Returns a collection view of this map's values.
     *
     * @return a set view of this map's values
     */
    public java.util.Collection values() {
        if (values == null) {
            values = new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceValues(this);
        }
        return values;
    }


    // -----------------------------------------------------------------------
    /**
     * Purges stale mappings from this map before read operations.
     * <p>
     * This implementation calls {@link #purge()} to maintain a consistent state.
     */
    protected void purgeBeforeRead() {
        purge();
    }


    /**
     * Purges stale mappings from this map before write operations.
     * <p>
     * This implementation calls {@link #purge()} to maintain a consistent state.
     */
    protected void purgeBeforeWrite() {
        purge();
    }


    /**
     * Purges stale mappings from this map.
     * <p>
     * Note that this method is not synchronized!  Special
     * care must be taken if, for instance, you want stale
     * mappings to be removed on a periodic basis by some
     * background thread.
     */
    protected void purge() {
        java.lang.ref.Reference ref;
        ref = queue.poll();
        while (ref != null) {
            purge(ref);
            ref = queue.poll();
        } 
    }


    /**
     * Purges the specified reference.
     *
     * @param ref
     * 		the reference to purge
     */
    protected void purge(java.lang.ref.Reference ref) {
        // The hashCode of the reference is the hashCode of the
        // mapping key, even if the reference refers to the
        // mapping value...
        int hash;
        hash = ref.hashCode();
        int index;
        index = hashIndex(hash, data.length);
        org.apache.commons.collections.map.AbstractHashedMap.HashEntry previous;
        previous = null;
        org.apache.commons.collections.map.AbstractHashedMap.HashEntry entry;
        entry = data[index];
        while (entry != null) {
            if (((org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry) (entry)).purge(ref)) {
                if (previous == null) {
                    data[index] = entry.next;
                } else {
                    previous.next = entry.next;
                }
                this.size--;
                return;
            }
            previous = entry;
            entry = entry.next;
        } 
    }


    // -----------------------------------------------------------------------
    /**
     * Gets the entry mapped to the key specified.
     *
     * @param key
     * 		the key
     * @return the entry, null if no match
     */
    protected org.apache.commons.collections.map.AbstractHashedMap.HashEntry getEntry(java.lang.Object key) {
        if (key == null) {
            return null;
        } else {
            return super.getEntry(key);
        }
    }


    /**
     * Gets the hash code for a MapEntry.
     * Subclasses can override this, for example to use the identityHashCode.
     *
     * @param key
     * 		the key to get a hash code for, may be null
     * @param value
     * 		the value to get a hash code for, may be null
     * @return the hash code, as per the MapEntry specification
     */
    protected int hashEntry(java.lang.Object key, java.lang.Object value) {
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }


    /**
     * Compares two keys, in internal converted form, to see if they are equal.
     * <p>
     * This implementation converts the key from the entry to a real reference
     * before comparison.
     *
     * @param key1
     * 		the first key to compare passed in from outside
     * @param key2
     * 		the second key extracted from the entry via <code>entry.key</code>
     * @return true if equal
     */
    protected boolean isEqualKey(java.lang.Object key1, java.lang.Object key2) {
        key2 = (keyType > org.apache.commons.collections.map.AbstractReferenceMap.HARD) ? ((java.lang.ref.Reference) (key2)).get() : key2;
        return (key1 == key2) || key1.equals(key2);
    }


    /**
     * Creates a ReferenceEntry instead of a HashEntry.
     *
     * @param next
     * 		the next entry in sequence
     * @param hashCode
     * 		the hash code to use
     * @param key
     * 		the key to store
     * @param value
     * 		the value to store
     * @return the newly created entry
     */
    protected org.apache.commons.collections.map.AbstractHashedMap.HashEntry createEntry(org.apache.commons.collections.map.AbstractHashedMap.HashEntry next, int hashCode, java.lang.Object key, java.lang.Object value) {
        return new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry(this, next, hashCode, key, value);
    }


    /**
     * Creates an entry set iterator.
     *
     * @return the entrySet iterator
     */
    protected java.util.Iterator createEntrySetIterator() {
        return new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntrySetIterator(this);
    }


    /**
     * Creates an key set iterator.
     *
     * @return the keySet iterator
     */
    protected java.util.Iterator createKeySetIterator() {
        return new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceKeySetIterator(this);
    }


    /**
     * Creates an values iterator.
     *
     * @return the values iterator
     */
    protected java.util.Iterator createValuesIterator() {
        return new org.apache.commons.collections.map.AbstractReferenceMap.ReferenceValuesIterator(this);
    }


    // -----------------------------------------------------------------------
    /**
     * EntrySet implementation.
     */
    static class ReferenceEntrySet extends org.apache.commons.collections.map.AbstractHashedMap.EntrySet {
        protected ReferenceEntrySet(org.apache.commons.collections.map.AbstractHashedMap parent) {
            super(parent);
        }


        public java.lang.Object[] toArray() {
            return toArray(new java.lang.Object[0]);
        }


        public java.lang.Object[] toArray(java.lang.Object[] arr) {
            // special implementation to handle disappearing entries
            java.util.ArrayList list;
            list = new java.util.ArrayList();
            java.util.Iterator iterator;
            iterator = iterator();
            while (iterator.hasNext()) {
                java.util.Map.Entry e;
                e = ((java.util.Map.Entry) (iterator.next()));
                list.add(new org.apache.commons.collections.keyvalue.DefaultMapEntry(e.getKey(), e.getValue()));
            } 
            return list.toArray(arr);
        }

    }

    // -----------------------------------------------------------------------
    /**
     * KeySet implementation.
     */
    static class ReferenceKeySet extends org.apache.commons.collections.map.AbstractHashedMap.KeySet {
        protected ReferenceKeySet(org.apache.commons.collections.map.AbstractHashedMap parent) {
            super(parent);
        }


        public java.lang.Object[] toArray() {
            return toArray(new java.lang.Object[0]);
        }


        public java.lang.Object[] toArray(java.lang.Object[] arr) {
            // special implementation to handle disappearing keys
            java.util.List list;
            list = new java.util.ArrayList(parent.size());
            for (java.util.Iterator it = iterator(); it.hasNext();) {
                list.add(it.next());
            }
            return list.toArray(arr);
        }

    }

    // -----------------------------------------------------------------------
    /**
     * Values implementation.
     */
    static class ReferenceValues extends org.apache.commons.collections.map.AbstractHashedMap.Values {
        protected ReferenceValues(org.apache.commons.collections.map.AbstractHashedMap parent) {
            super(parent);
        }


        public java.lang.Object[] toArray() {
            return toArray(new java.lang.Object[0]);
        }


        public java.lang.Object[] toArray(java.lang.Object[] arr) {
            // special implementation to handle disappearing values
            java.util.List list;
            list = new java.util.ArrayList(parent.size());
            for (java.util.Iterator it = iterator(); it.hasNext();) {
                list.add(it.next());
            }
            return list.toArray(arr);
        }

    }

    // -----------------------------------------------------------------------
    /**
     * A MapEntry implementation for the map.
     * <p>
     * If getKey() or getValue() returns null, it means
     * the mapping is stale and should be removed.
     *
     * @since Commons Collections 3.1
     */
    protected static class ReferenceEntry extends org.apache.commons.collections.map.AbstractHashedMap.HashEntry {
        /**
         * The parent map
         */
        protected final org.apache.commons.collections.map.AbstractReferenceMap parent;

        /**
         * Creates a new entry object for the ReferenceMap.
         *
         * @param parent
         * 		the parent map
         * @param next
         * 		the next entry in the hash bucket
         * @param hashCode
         * 		the hash code of the key
         * @param key
         * 		the key
         * @param value
         * 		the value
         */
        public ReferenceEntry(org.apache.commons.collections.map.AbstractReferenceMap parent, org.apache.commons.collections.map.AbstractHashedMap.HashEntry next, int hashCode, java.lang.Object key, java.lang.Object value) {
            super(next, hashCode, null, null);
            this.parent = parent;
            this.key = toReference(parent.keyType, key, hashCode);
            this.value = toReference(parent.valueType, value, hashCode)// the key hashCode is passed in deliberately
            ;// the key hashCode is passed in deliberately

        }


        /**
         * Gets the key from the entry.
         * This method dereferences weak and soft keys and thus may return null.
         *
         * @return the key, which may be null if it was garbage collected
         */
        public java.lang.Object getKey() {
            return parent.keyType > org.apache.commons.collections.map.AbstractReferenceMap.HARD ? ((java.lang.ref.Reference) (key)).get() : key;
        }


        /**
         * Gets the value from the entry.
         * This method dereferences weak and soft value and thus may return null.
         *
         * @return the value, which may be null if it was garbage collected
         */
        public java.lang.Object getValue() {
            return parent.valueType > org.apache.commons.collections.map.AbstractReferenceMap.HARD ? ((java.lang.ref.Reference) (value)).get() : value;
        }


        /**
         * Sets the value of the entry.
         *
         * @param obj
         * 		the object to store
         * @return the previous value
         */
        public java.lang.Object setValue(java.lang.Object obj) {
            java.lang.Object old;
            old = getValue();
            if (parent.valueType > org.apache.commons.collections.map.AbstractReferenceMap.HARD) {
                ((java.lang.ref.Reference) (value)).clear();
            }
            value = toReference(parent.valueType, obj, hashCode);
            return old;
        }


        /**
         * Compares this map entry to another.
         * <p>
         * This implementation uses <code>isEqualKey</code> and
         * <code>isEqualValue</code> on the main map for comparison.
         *
         * @param obj
         * 		the other map entry to compare to
         * @return true if equal, false if not
         */
        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if ((obj instanceof java.util.Map.Entry) == false) {
                return false;
            }
            java.util.Map.Entry entry;
            entry = ((java.util.Map.Entry) (obj));
            java.lang.Object entryKey// convert to hard reference
            ;// convert to hard reference

            entryKey = entry.getKey();
            java.lang.Object entryValue// convert to hard reference
            ;// convert to hard reference

            entryValue = entry.getValue();
            if ((entryKey == null) || (entryValue == null)) {
                return false;
            }
            // compare using map methods, aiding identity subclass
            // note that key is direct access and value is via method
            return parent.isEqualKey(entryKey, key) && parent.isEqualValue(entryValue, getValue());
        }


        /**
         * Gets the hashcode of the entry using temporary hard references.
         * <p>
         * This implementation uses <code>hashEntry</code> on the main map.
         *
         * @return the hashcode of the entry
         */
        public int hashCode() {
            return parent.hashEntry(getKey(), getValue());
        }


        /**
         * Constructs a reference of the given type to the given referent.
         * The reference is registered with the queue for later purging.
         *
         * @param type
         * 		HARD, SOFT or WEAK
         * @param referent
         * 		the object to refer to
         * @param hash
         * 		the hash code of the <i>key</i> of the mapping;
         * 		this number might be different from referent.hashCode() if
         * 		the referent represents a value and not a key
         */
        protected java.lang.Object toReference(int type, java.lang.Object referent, int hash) {
            switch (type) {
                case org.apache.commons.collections.map.AbstractReferenceMap.HARD :
                    return referent;
                case org.apache.commons.collections.map.AbstractReferenceMap.SOFT :
                    return new org.apache.commons.collections.map.AbstractReferenceMap.SoftRef(hash, referent, parent.queue);
                case org.apache.commons.collections.map.AbstractReferenceMap.WEAK :
                    return new org.apache.commons.collections.map.AbstractReferenceMap.WeakRef(hash, referent, parent.queue);
                default :
                    throw new java.lang.Error();
            }
        }


        /**
         * Purges the specified reference
         *
         * @param ref
         * 		the reference to purge
         * @return true or false
         */
        boolean purge(java.lang.ref.Reference ref) {
            boolean r;
            r = (parent.keyType > org.apache.commons.collections.map.AbstractReferenceMap.HARD) && (key == ref);
            r = r || ((parent.valueType > org.apache.commons.collections.map.AbstractReferenceMap.HARD) && (value == ref));
            if (r) {
                if (parent.keyType > org.apache.commons.collections.map.AbstractReferenceMap.HARD) {
                    ((java.lang.ref.Reference) (key)).clear();
                }
                if (parent.valueType > org.apache.commons.collections.map.AbstractReferenceMap.HARD) {
                    ((java.lang.ref.Reference) (value)).clear();
                } else if (parent.purgeValues) {
                    value = null;
                }
            }
            return r;
        }


        /**
         * Gets the next entry in the bucket.
         *
         * @return the next entry in the bucket
         */
        protected org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry next() {
            return ((org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry) (next));
        }

    }

    // -----------------------------------------------------------------------
    /**
     * The EntrySet iterator.
     */
    static class ReferenceEntrySetIterator implements java.util.Iterator {
        /**
         * The parent map
         */
        final org.apache.commons.collections.map.AbstractReferenceMap parent;

        // These fields keep track of where we are in the table.
        int index;

        org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry entry;

        org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry previous;

        java.lang.Object nextKey;

        // These Object fields provide hard references to the
        // current and next entry; this assures that if hasNext()
        // returns true, next() will actually return a valid element.
        java.lang.Object nextValue;

        java.lang.Object currentKey;

        java.lang.Object currentValue;

        int expectedModCount;

        public ReferenceEntrySetIterator(org.apache.commons.collections.map.AbstractReferenceMap parent) {
            super();
            this.parent = parent;
            index = (parent.size() != 0) ? parent.data.length : 0;
            // have to do this here!  size() invocation above
            // may have altered the modCount.
            expectedModCount = parent.modCount;
        }


        public boolean hasNext() {
            checkMod();
            while (nextNull()) {
                org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry e;
                e = entry;
                int i;
                i = index;
                while ((e == null) && (i > 0)) {
                    i--;
                    e = ((org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry) (parent.data[i]));
                } 
                entry = e;
                index = i;
                if (e == null) {
                    currentKey = null;
                    currentValue = null;
                    return false;
                }
                nextKey = e.getKey();
                nextValue = e.getValue();
                if (nextNull()) {
                    entry = entry.next();
                }
            } 
            return true;
        }


        private void checkMod() {
            if (parent.modCount != expectedModCount) {
                throw new java.util.ConcurrentModificationException();
            }
        }


        private boolean nextNull() {
            return (nextKey == null) || (nextValue == null);
        }


        protected org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry nextEntry() {
            checkMod();
            if (nextNull() && (!hasNext())) {
                throw new java.util.NoSuchElementException();
            }
            previous = entry;
            entry = entry.next();
            currentKey = nextKey;
            currentValue = nextValue;
            nextKey = null;
            nextValue = null;
            return previous;
        }


        protected org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntry currentEntry() {
            checkMod();
            return previous;
        }


        public java.lang.Object next() {
            return nextEntry();
        }


        public void remove() {
            checkMod();
            if (previous == null) {
                throw new java.lang.IllegalStateException();
            }
            parent.remove(currentKey);
            previous = null;
            currentKey = null;
            currentValue = null;
            expectedModCount = parent.modCount;
        }

    }

    /**
     * The keySet iterator.
     */
    static class ReferenceKeySetIterator extends org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntrySetIterator {
        ReferenceKeySetIterator(org.apache.commons.collections.map.AbstractReferenceMap parent) {
            super(parent);
        }


        public java.lang.Object next() {
            return nextEntry().getKey();
        }

    }

    /**
     * The values iterator.
     */
    static class ReferenceValuesIterator extends org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntrySetIterator {
        ReferenceValuesIterator(org.apache.commons.collections.map.AbstractReferenceMap parent) {
            super(parent);
        }


        public java.lang.Object next() {
            return nextEntry().getValue();
        }

    }

    /**
     * The MapIterator implementation.
     */
    static class ReferenceMapIterator extends org.apache.commons.collections.map.AbstractReferenceMap.ReferenceEntrySetIterator implements org.apache.commons.collections.MapIterator {
        protected ReferenceMapIterator(org.apache.commons.collections.map.AbstractReferenceMap parent) {
            super(parent);
        }


        public java.lang.Object next() {
            return nextEntry().getKey();
        }


        public java.lang.Object getKey() {
            org.apache.commons.collections.map.AbstractHashedMap.HashEntry current;
            current = currentEntry();
            if (current == null) {
                throw new java.lang.IllegalStateException(org.apache.commons.collections.map.AbstractHashedMap.GETKEY_INVALID);
            }
            return current.getKey();
        }


        public java.lang.Object getValue() {
            org.apache.commons.collections.map.AbstractHashedMap.HashEntry current;
            current = currentEntry();
            if (current == null) {
                throw new java.lang.IllegalStateException(org.apache.commons.collections.map.AbstractHashedMap.GETVALUE_INVALID);
            }
            return current.getValue();
        }


        public java.lang.Object setValue(java.lang.Object value) {
            org.apache.commons.collections.map.AbstractHashedMap.HashEntry current;
            current = currentEntry();
            if (current == null) {
                throw new java.lang.IllegalStateException(org.apache.commons.collections.map.AbstractHashedMap.SETVALUE_INVALID);
            }
            return current.setValue(value);
        }

    }

    // -----------------------------------------------------------------------
    // These two classes store the hashCode of the key of
    // of the mapping, so that after they're dequeued a quick
    // lookup of the bucket in the table can occur.
    /**
     * A soft reference holder.
     */
    static class SoftRef extends java.lang.ref.SoftReference {
        /**
         * the hashCode of the key (even if the reference points to a value)
         */
        private int hash;

        public SoftRef(int hash, java.lang.Object r, java.lang.ref.ReferenceQueue q) {
            super(r, q);
            this.hash = hash;
        }


        public int hashCode() {
            return hash;
        }

    }

    /**
     * A weak reference holder.
     */
    static class WeakRef extends java.lang.ref.WeakReference {
        /**
         * the hashCode of the key (even if the reference points to a value)
         */
        private int hash;

        public WeakRef(int hash, java.lang.Object r, java.lang.ref.ReferenceQueue q) {
            super(r, q);
            this.hash = hash;
        }


        public int hashCode() {
            return hash;
        }

    }

    // -----------------------------------------------------------------------
    /**
     * Replaces the superclass method to store the state of this class.
     * <p>
     * Serialization is not one of the JDK's nicest topics. Normal serialization will
     * initialise the superclass before the subclass. Sometimes however, this isn't
     * what you want, as in this case the <code>put()</code> method on read can be
     * affected by subclass state.
     * <p>
     * The solution adopted here is to serialize the state data of this class in
     * this protected method. This method must be called by the
     * <code>writeObject()</code> of the first serializable subclass.
     * <p>
     * Subclasses may override if they have a specific field that must be present
     * on read before this implementation will work. Generally, the read determines
     * what must be serialized here, if anything.
     *
     * @param out
     * 		the output stream
     */
    protected void doWriteObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.writeInt(keyType);
        out.writeInt(valueType);
        out.writeBoolean(purgeValues);
        out.writeFloat(loadFactor);
        out.writeInt(data.length);
        for (org.apache.commons.collections.MapIterator it = mapIterator(); it.hasNext();) {
            out.writeObject(it.next());
            out.writeObject(it.getValue());
        }
        out.writeObject(null)// null terminate map
        ;// null terminate map

        // do not call super.doWriteObject() as code there doesn't work for reference map
    }


    /**
     * Replaces the superclassm method to read the state of this class.
     * <p>
     * Serialization is not one of the JDK's nicest topics. Normal serialization will
     * initialise the superclass before the subclass. Sometimes however, this isn't
     * what you want, as in this case the <code>put()</code> method on read can be
     * affected by subclass state.
     * <p>
     * The solution adopted here is to deserialize the state data of this class in
     * this protected method. This method must be called by the
     * <code>readObject()</code> of the first serializable subclass.
     * <p>
     * Subclasses may override if the subclass has a specific field that must be present
     * before <code>put()</code> or <code>calculateThreshold()</code> will work correctly.
     *
     * @param in
     * 		the input stream
     */
    protected void doReadObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
        this.keyType = in.readInt();
        this.valueType = in.readInt();
        this.purgeValues = in.readBoolean();
        this.loadFactor = in.readFloat();
        int capacity;
        capacity = in.readInt();
        init();
        data = new org.apache.commons.collections.map.AbstractHashedMap.HashEntry[capacity];
        while (true) {
            java.lang.Object key;
            key = in.readObject();
            if (key == null) {
                break;
            }
            java.lang.Object value;
            value = in.readObject();
            put(key, value);
        } 
        threshold = calculateThreshold(data.length, loadFactor);
        // do not call super.doReadObject() as code there doesn't work for reference map
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
