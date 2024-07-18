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
package org.apache.commons.collections.keyvalue;
import org.apache.commons.collections.KeyValue;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Abstract pair class to assist with creating <code>KeyValue</code>
 * and {@link java.util.Map.Entry Map.Entry} implementations.
 *
 * @since Commons Collections 3.0
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 * @author James Strachan
 * @author Michael A. Smith
 * @author Neil O'Toole
 * @author Stephen Colebourne
 */
public abstract class AbstractKeyValue implements org.apache.commons.collections.KeyValue {
    static final int MUID_STATIC = getMUID();
    /**
     * The key
     */
    protected java.lang.Object key;

    /**
     * The value
     */
    protected java.lang.Object value;

    /**
     * Constructs a new pair with the specified key and given value.
     *
     * @param key
     * 		the key for the entry, may be null
     * @param value
     * 		the value for the entry, may be null
     */
    protected AbstractKeyValue(java.lang.Object key, java.lang.Object value) {
        super();
        this.key = key;
        this.value = value;
    }


    /**
     * Gets the key from the pair.
     *
     * @return the key
     */
    public java.lang.Object getKey() {
        return key;
    }


    /**
     * Gets the value from the pair.
     *
     * @return the value
     */
    public java.lang.Object getValue() {
        return value;
    }


    /**
     * Gets a debugging String view of the pair.
     *
     * @return a String view of the entry
     */
    public java.lang.String toString() {
        return new java.lang.StringBuffer().append(getKey()).append('=').append(getValue()).toString();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
