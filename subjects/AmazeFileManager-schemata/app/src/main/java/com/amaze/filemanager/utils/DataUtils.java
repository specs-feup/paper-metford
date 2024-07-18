/* Copyright (C) 2014-2020 Arpit Khurana <arpitkh96@gmail.com>, Vishal Nehra <vishalmeham2@gmail.com>,
Emmanuel Messulam<emmanuelbendavid@gmail.com>, Raymond Lai <airwave209gt at gmail.com> and Contributors.

This file is part of Amaze File Manager.

Amaze File Manager is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amaze.filemanager.utils;
import java.util.LinkedList;
import com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue;
import com.amaze.filemanager.application.AppConfig;
import java.util.ArrayList;
import android.text.TextUtils;
import com.cloudrail.si.services.GoogleDrive;
import android.view.MenuItem;
import org.slf4j.Logger;
import com.amaze.filemanager.fileoperations.filesystem.OpenMode;
import com.cloudrail.si.services.Box;
import com.googlecode.concurrenttrees.radix.ConcurrentRadixTree;
import com.googlecode.concurrenttrees.radixinverted.InvertedRadixTree;
import com.amaze.filemanager.adapters.data.LayoutElementParcelable;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.services.OneDrive;
import com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree;
import java.util.List;
import org.slf4j.LoggerFactory;
import com.cloudrail.si.services.Dropbox;
import com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory;
import androidx.annotation.Nullable;
import java.util.Collections;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Singleton class to handle data for various services
 */
// Central data being used across activity,fragments and classes
public class DataUtils {
    static final int MUID_STATIC = getMUID();
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(com.amaze.filemanager.utils.DataUtils.class);

    private com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue> hiddenfiles = new com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<>(new com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory());

    public static final int LIST = 0;

    public static final int GRID = 1;

    private com.googlecode.concurrenttrees.radixinverted.InvertedRadixTree<java.lang.Integer> filesGridOrList = new com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree<>(new com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory());

    private java.util.LinkedList<java.lang.String> history = new java.util.LinkedList<>();

    private java.util.ArrayList<java.lang.String> storages = new java.util.ArrayList<>();

    private com.googlecode.concurrenttrees.radixinverted.InvertedRadixTree<java.lang.Integer> tree = new com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree<>(new com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory());

    private java.util.ArrayList<java.lang.String[]> servers = new java.util.ArrayList<>();

    private java.util.ArrayList<java.lang.String[]> books = new java.util.ArrayList<>();

    private java.util.ArrayList<com.cloudrail.si.interfaces.CloudStorage> accounts = new java.util.ArrayList<>(4);

    /**
     * List of checked items to persist when drag and drop from one tab to another
     */
    private java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> checkedItemsList;

    private com.amaze.filemanager.utils.DataUtils.DataChangeListener dataChangeListener;

    private DataUtils() {
    }


    private static class DataUtilsHolder {
        private static final com.amaze.filemanager.utils.DataUtils INSTANCE = new com.amaze.filemanager.utils.DataUtils();
    }

    public static com.amaze.filemanager.utils.DataUtils getInstance() {
        return com.amaze.filemanager.utils.DataUtils.DataUtilsHolder.INSTANCE;
    }


    public int containsServer(java.lang.String[] a) {
        return contains(a, servers);
    }


    public int containsServer(java.lang.String path) {
        synchronized(servers) {
            if (servers == null)
                return -1;

            int i;
            i = 0;
            for (java.lang.String[] x : servers) {
                if (x[1].equals(path))
                    return i;

                i++;
            }
        }
        return -1;
    }


    public int containsBooks(java.lang.String[] a) {
        return contains(a, books);
    }


    /* public int containsAccounts(CloudEntry cloudEntry) {
    return contains(a, accounts);
    }
     */
    /**
     * Checks whether cloud account of certain type is present or not
     *
     * @param serviceType
     * 		the {@link OpenMode} of account to check
     * @return the index of account, -1 if not found
     */
    public synchronized int containsAccounts(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType) {
        int i;
        i = 0;
        for (com.cloudrail.si.interfaces.CloudStorage storage : accounts) {
            switch (serviceType) {
                case BOX :
                    if (storage instanceof com.cloudrail.si.services.Box)
                        return i;

                    break;
                case DROPBOX :
                    if (storage instanceof com.cloudrail.si.services.Dropbox)
                        return i;

                    break;
                case GDRIVE :
                    if (storage instanceof com.cloudrail.si.services.GoogleDrive)
                        return i;

                    break;
                case ONEDRIVE :
                    if (storage instanceof com.cloudrail.si.services.OneDrive)
                        return i;

                    break;
                default :
                    return -1;
            }
            i++;
        }
        return -1;
    }


    public void clear() {
        hiddenfiles = new com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<>(new com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory());
        filesGridOrList = new com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree<>(new com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory());
        history.clear();
        storages = new java.util.ArrayList<>();
        tree = new com.googlecode.concurrenttrees.radixinverted.ConcurrentInvertedRadixTree<>(new com.googlecode.concurrenttrees.radix.node.concrete.DefaultCharArrayNodeFactory());
        servers = new java.util.ArrayList<>();
        books = new java.util.ArrayList<>();
        accounts = new java.util.ArrayList<>();
    }


    public void registerOnDataChangedListener(com.amaze.filemanager.utils.DataUtils.DataChangeListener l) {
        dataChangeListener = l;
        clear();
    }


    int contains(java.lang.String a, java.util.ArrayList<java.lang.String[]> b) {
        int i;
        i = 0;
        for (java.lang.String[] x : b) {
            if (x[1].equals(a))
                return i;

            i++;
        }
        return -1;
    }


    int contains(java.lang.String[] a, java.util.ArrayList<java.lang.String[]> b) {
        if (b == null)
            return -1;

        int i;
        i = 0;
        for (java.lang.String[] x : b) {
            if (x[0].equals(a[0]) && x[1].equals(a[1]))
                return i;

            i++;
        }
        return -1;
    }


    public void removeBook(int i) {
        synchronized(books) {
            if (books.size() > i)
                books.remove(i);

        }
    }


    public synchronized void removeAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType) {
        for (com.cloudrail.si.interfaces.CloudStorage storage : accounts) {
            switch (serviceType) {
                case BOX :
                    if (storage instanceof com.cloudrail.si.services.Box) {
                        accounts.remove(storage);
                        return;
                    }
                    break;
                case DROPBOX :
                    if (storage instanceof com.cloudrail.si.services.Dropbox) {
                        accounts.remove(storage);
                        return;
                    }
                    break;
                case GDRIVE :
                    if (storage instanceof com.cloudrail.si.services.GoogleDrive) {
                        accounts.remove(storage);
                        return;
                    }
                    break;
                case ONEDRIVE :
                    if (storage instanceof com.cloudrail.si.services.OneDrive) {
                        accounts.remove(storage);
                        return;
                    }
                    break;
                default :
                    return;
            }
        }
    }


    public void removeServer(int i) {
        synchronized(servers) {
            if (servers.size() > i)
                servers.remove(i);

        }
    }


    public void addBook(java.lang.String[] i) {
        if (containsBooks(i) != (-1)) {
            return;
        }
        synchronized(books) {
            books.add(i);
        }
    }


    /**
     *
     * @param i
     * 		The bookmark name and path.
     * @param refreshdrawer
     * 		boolean flag to indicate if drawer refresh is desired.
     * @return True if operation successful, false if failure.
     */
    public boolean addBook(final java.lang.String[] i, boolean refreshdrawer) {
        if (containsBooks(i) != (-1)) {
            // book exists
            return false;
        } else {
            synchronized(books) {
                books.add(i);
            }
            if (dataChangeListener != null) {
                dataChangeListener.onBookAdded(i, refreshdrawer);
            }
            return true;
        }
    }


    public void addAccount(com.cloudrail.si.interfaces.CloudStorage storage) {
        accounts.add(storage);
    }


    public void addServer(java.lang.String[] i) {
        servers.add(i);
    }


    public void addHiddenFile(final java.lang.String i) {
        synchronized(hiddenfiles) {
            hiddenfiles.put(i, com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue.SINGLETON);
        }
        if (dataChangeListener != null) {
            dataChangeListener.onHiddenFileAdded(i);
        }
    }


    public void removeHiddenFile(final java.lang.String i) {
        synchronized(hiddenfiles) {
            hiddenfiles.remove(i);
        }
        if (dataChangeListener != null) {
            dataChangeListener.onHiddenFileRemoved(i);
        }
    }


    public void setHistory(java.util.LinkedList<java.lang.String> s) {
        history.clear();
        history.addAll(s);
    }


    public java.util.LinkedList<java.lang.String> getHistory() {
        return history;
    }


    public void addHistoryFile(final java.lang.String i) {
        history.push(i);
        if (dataChangeListener != null) {
            dataChangeListener.onHistoryAdded(i);
        }
    }


    public void sortBook() {
        java.util.Collections.sort(books, new com.amaze.filemanager.utils.BookSorter());
    }


    public synchronized void setServers(java.util.ArrayList<java.lang.String[]> servers) {
        if (servers != null)
            this.servers = servers;

    }


    public synchronized void setBooks(java.util.ArrayList<java.lang.String[]> books) {
        if (books != null)
            this.books = books;

    }


    public synchronized void setAccounts(java.util.ArrayList<com.cloudrail.si.interfaces.CloudStorage> accounts) {
        if (accounts != null)
            this.accounts = accounts;

    }


    public synchronized java.util.ArrayList<java.lang.String[]> getServers() {
        return servers;
    }


    public synchronized java.util.ArrayList<java.lang.String[]> getBooks() {
        return books;
    }


    public synchronized java.util.ArrayList<com.cloudrail.si.interfaces.CloudStorage> getAccounts() {
        return accounts;
    }


    public synchronized com.cloudrail.si.interfaces.CloudStorage getAccount(com.amaze.filemanager.fileoperations.filesystem.OpenMode serviceType) {
        for (com.cloudrail.si.interfaces.CloudStorage storage : accounts) {
            switch (serviceType) {
                case BOX :
                    if (storage instanceof com.cloudrail.si.services.Box)
                        return storage;

                    break;
                case DROPBOX :
                    if (storage instanceof com.cloudrail.si.services.Dropbox)
                        return storage;

                    break;
                case GDRIVE :
                    if (storage instanceof com.cloudrail.si.services.GoogleDrive)
                        return storage;

                    break;
                case ONEDRIVE :
                    if (storage instanceof com.cloudrail.si.services.OneDrive)
                        return storage;

                    break;
                default :
                    com.amaze.filemanager.utils.DataUtils.LOG.error("Unable to determine service type of storage {}", storage);
                    return null;
            }
        }
        return null;
    }


    public boolean isFileHidden(java.lang.String path) {
        try {
            return getHiddenFiles().getValueForExactKey(path) != null;
        } catch (java.lang.IllegalStateException e) {
            com.amaze.filemanager.utils.DataUtils.LOG.warn("failed to get hidden file", e);
            return false;
        }
    }


    public com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue> getHiddenFiles() {
        return hiddenfiles;
    }


    public synchronized void setHiddenFiles(com.googlecode.concurrenttrees.radix.ConcurrentRadixTree<com.googlecode.concurrenttrees.radix.node.concrete.voidvalue.VoidValue> hiddenfiles) {
        if (hiddenfiles != null)
            this.hiddenfiles = hiddenfiles;

    }


    public synchronized void setGridfiles(java.util.ArrayList<java.lang.String> gridfiles) {
        if (gridfiles != null) {
            for (java.lang.String gridfile : gridfiles) {
                setPathAsGridOrList(gridfile, com.amaze.filemanager.utils.DataUtils.GRID);
            }
        }
    }


    public synchronized void setListfiles(java.util.ArrayList<java.lang.String> listfiles) {
        if (listfiles != null) {
            for (java.lang.String gridfile : listfiles) {
                setPathAsGridOrList(gridfile, com.amaze.filemanager.utils.DataUtils.LIST);
            }
        }
    }


    public void setPathAsGridOrList(java.lang.String path, int value) {
        filesGridOrList.put(path, value);
    }


    public int getListOrGridForPath(java.lang.String path, int defaultValue) {
        java.lang.Integer value;
        value = filesGridOrList.getValueForLongestKeyPrefixing(path);
        return value != null ? value : defaultValue;
    }


    public void clearHistory() {
        history.clear();
        if (dataChangeListener != null) {
            com.amaze.filemanager.application.AppConfig.getInstance().runInBackground(() -> dataChangeListener.onHistoryCleared());
        }
    }


    public synchronized java.util.List<java.lang.String> getStorages() {
        return storages;
    }


    public synchronized void setStorages(java.util.ArrayList<java.lang.String> storages) {
        this.storages = storages;
    }


    public boolean putDrawerPath(android.view.MenuItem item, java.lang.String path) {
        if (!android.text.TextUtils.isEmpty(path)) {
            try {
                tree.put(path, item.getItemId());
                return true;
            } catch (java.lang.IllegalStateException e) {
                com.amaze.filemanager.utils.DataUtils.LOG.warn("failed to put drawer path", e);
                return false;
            }
        }
        return false;
    }


    /**
     *
     * @param path
     * 		the path to find
     * @return the id of the longest containing MenuMetadata.path in getDrawerMetadata() or null
     */
    @androidx.annotation.Nullable
    public java.lang.Integer findLongestContainingDrawerItem(java.lang.CharSequence path) {
        return tree.getValueForLongestKeyPrefixing(path);
    }


    public java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> getCheckedItemsList() {
        return this.checkedItemsList;
    }


    public void setCheckedItemsList(java.util.ArrayList<com.amaze.filemanager.adapters.data.LayoutElementParcelable> layoutElementParcelables) {
        this.checkedItemsList = layoutElementParcelables;
    }


    /**
     * Callbacks to do original changes in database (and ui if required) The callbacks are called in a
     * main thread
     */
    public interface DataChangeListener {
        void onHiddenFileAdded(java.lang.String path);


        void onHiddenFileRemoved(java.lang.String path);


        void onHistoryAdded(java.lang.String path);


        void onBookAdded(java.lang.String[] path, boolean refreshdrawer);


        void onHistoryCleared();

    }

    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
