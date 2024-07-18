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
package org.apache.commons.collections;
import java.util.Iterator;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Defines an iterator that operates over a <code>Map</code>.
 * <p>
 * This iterator is a special version designed for maps. It can be more
 * efficient to use this rather than an entry set iterator where the option
 * is available, and it is certainly more convenient.
 * <p>
 * A map that provides this interface may not hold the data internally using
 * Map Entry objects, thus this interface can avoid lots of object creation.
 * <p>
 * In use, this iterator iterates through the keys in the map. After each call
 * to <code>next()</code>, the <code>getValue()</code> method provides direct
 * access to the value. The value can also be set using <code>setValue()</code>.
 * <pre>
 * MapIterator it = map.mapIterator();
 * while (it.hasNext()) {
 *   Object key = it.next();
 *   Object value = it.getValue();
 *   it.setValue(newValue);
 * }
 * </pre>
 *
 * @since Commons Collections 3.0
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 * @author Stephen Colebourne
 */
public interface MapIterator extends java.util.Iterator {
    static final int MUID_STATIC = getMUID();
    /**
     * Checks to see if there are more entries still to be iterated.
     *
     * @return <code>true</code> if the iterator has more elements
     */
    boolean hasNext();


    /**
     * Gets the next <em>key</em> from the <code>Map</code>.
     *
     * @return the next key in the iteration
     * @throws java.util.NoSuchElementException
     * 		if the iteration is finished
     */
    java.lang.Object next();


    // -----------------------------------------------------------------------
    /**
     * Gets the current key, which is the key returned by the last call
     * to <code>next()</code>.
     *
     * @return the current key
     * @throws IllegalStateException
     * 		if <code>next()</code> has not yet been called
     */
    java.lang.Object getKey();


    /**
     * Gets the current value, which is the value associated with the last key
     * returned by <code>next()</code>.
     *
     * @return the current value
     * @throws IllegalStateException
     * 		if <code>next()</code> has not yet been called
     */
    java.lang.Object getValue();


    // -----------------------------------------------------------------------
    /**
     * Removes the last returned key from the underlying <code>Map</code> (optional operation).
     * <p>
     * This method can be called once per call to <code>next()</code>.
     *
     * @throws UnsupportedOperationException
     * 		if remove is not supported by the map
     * @throws IllegalStateException
     * 		if <code>next()</code> has not yet been called
     * @throws IllegalStateException
     * 		if <code>remove()</code> has already been called
     * 		since the last call to <code>next()</code>
     */
    void remove();


    /**
     * Sets the value associated with the current key (optional operation).
     *
     * @param value
     * 		the new value
     * @return the previous value
     * @throws UnsupportedOperationException
     * 		if setValue is not supported by the map
     * @throws IllegalStateException
     * 		if <code>next()</code> has not yet been called
     * @throws IllegalStateException
     * 		if <code>remove()</code> has been called since the
     * 		last call to <code>next()</code>
     */
    java.lang.Object setValue(java.lang.Object value);


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
