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
package org.apache.commons.collections.iterators;
import java.util.Iterator;
import org.apache.commons.collections.ResettableIterator;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Provides an implementation of an empty iterator.
 * <p>
 * This class provides an implementation of an empty iterator.
 * This class provides for binary compatability between Commons Collections
 * 2.1.1 and 3.1 due to issues with <code>IteratorUtils</code>.
 *
 * @since Commons Collections 2.1.1 and 3.1
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 * @author Stephen Colebourne
 */
public class EmptyIterator extends org.apache.commons.collections.iterators.AbstractEmptyIterator implements org.apache.commons.collections.ResettableIterator {
    static final int MUID_STATIC = getMUID();
    /**
     * Singleton instance of the iterator.
     *
     * @since Commons Collections 3.1
     */
    public static final org.apache.commons.collections.ResettableIterator RESETTABLE_INSTANCE = new org.apache.commons.collections.iterators.EmptyIterator();

    /**
     * Singleton instance of the iterator.
     *
     * @since Commons Collections 2.1.1 and 3.1
     */
    public static final java.util.Iterator INSTANCE = org.apache.commons.collections.iterators.EmptyIterator.RESETTABLE_INSTANCE;

    /**
     * Constructor.
     */
    protected EmptyIterator() {
        super();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
