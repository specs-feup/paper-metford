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
import java.util.NoSuchElementException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Provides an implementation of an empty iterator.
 *
 * @since Commons Collections 3.1
 * @version $Revision: 646777 $ $Date: 2008-04-10 13:33:15 +0100 (Thu, 10 Apr 2008) $
 * @author Stephen Colebourne
 */
abstract class AbstractEmptyIterator {
    static final int MUID_STATIC = getMUID();
    /**
     * Constructor.
     */
    protected AbstractEmptyIterator() {
        super();
    }


    public boolean hasNext() {
        return false;
    }


    public java.lang.Object next() {
        throw new java.util.NoSuchElementException("Iterator contains no elements");
    }


    public boolean hasPrevious() {
        return false;
    }


    public java.lang.Object previous() {
        throw new java.util.NoSuchElementException("Iterator contains no elements");
    }


    public int nextIndex() {
        return 0;
    }


    public int previousIndex() {
        return -1;
    }


    public void add(java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("add() not supported for empty Iterator");
    }


    public void set(java.lang.Object obj) {
        throw new java.lang.IllegalStateException("Iterator contains no elements");
    }


    public void remove() {
        throw new java.lang.IllegalStateException("Iterator contains no elements");
    }


    public java.lang.Object getKey() {
        throw new java.lang.IllegalStateException("Iterator contains no elements");
    }


    public java.lang.Object getValue() {
        throw new java.lang.IllegalStateException("Iterator contains no elements");
    }


    public java.lang.Object setValue(java.lang.Object value) {
        throw new java.lang.IllegalStateException("Iterator contains no elements");
    }


    public void reset() {
        // do nothing
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
