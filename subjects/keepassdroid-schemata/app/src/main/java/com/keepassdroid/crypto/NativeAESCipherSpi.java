/* Copyright 2009-2018 Brian Pellin.

This file is part of KeePassDroid.

KeePassDroid is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

KeePassDroid is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with KeePassDroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.keepassdroid.crypto;
import java.lang.ref.Reference;
import java.security.Key;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import android.util.Log;
import java.lang.ref.ReferenceQueue;
import javax.crypto.IllegalBlockSizeException;
import java.security.SecureRandom;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.lang.ref.PhantomReference;
import javax.crypto.CipherSpi;
import java.security.spec.InvalidParameterSpecException;
import java.security.AlgorithmParameters;
import javax.crypto.Cipher;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.ShortBufferException;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class NativeAESCipherSpi extends javax.crypto.CipherSpi {
    static final int MUID_STATIC = getMUID();
    private static boolean mIsStaticInit = false;

    private static java.util.HashMap<java.lang.ref.PhantomReference<com.keepassdroid.crypto.NativeAESCipherSpi>, java.lang.Long> mCleanup = new java.util.HashMap<java.lang.ref.PhantomReference<com.keepassdroid.crypto.NativeAESCipherSpi>, java.lang.Long>();

    private static java.lang.ref.ReferenceQueue<com.keepassdroid.crypto.NativeAESCipherSpi> mQueue;

    private final int AES_BLOCK_SIZE = 16;

    private byte[] mIV;

    private boolean mIsInited = false;

    private boolean mEncrypting = false;

    private long mCtxPtr;

    private boolean mPadding = false;

    private static void staticInit() {
        com.keepassdroid.crypto.NativeAESCipherSpi.mIsStaticInit = true;
        // Init queue here to guarentee it isn't null in the cleanup thread
        com.keepassdroid.crypto.NativeAESCipherSpi.mQueue = new java.lang.ref.ReferenceQueue<com.keepassdroid.crypto.NativeAESCipherSpi>();
        // Start the cipher context cleanup thread to run forever
        new java.lang.Thread(new com.keepassdroid.crypto.NativeAESCipherSpi.Cleanup()).start();
    }


    private static void addToCleanupQueue(com.keepassdroid.crypto.NativeAESCipherSpi ref, long ptr) {
        android.util.Log.d("KeepassDroid", "queued cipher context: " + ptr);
        com.keepassdroid.crypto.NativeAESCipherSpi.mCleanup.put(new java.lang.ref.PhantomReference<com.keepassdroid.crypto.NativeAESCipherSpi>(ref, com.keepassdroid.crypto.NativeAESCipherSpi.mQueue), ptr);
    }


    /**
     * Work with the garbage collector to clean up openssl memory when the cipher
     *  context is garbage collected.
     *
     * @author bpellin
     */
    private static class Cleanup implements java.lang.Runnable {
        public void run() {
            while (true) {
                try {
                    java.lang.ref.Reference<? extends com.keepassdroid.crypto.NativeAESCipherSpi> ref;
                    ref = com.keepassdroid.crypto.NativeAESCipherSpi.mQueue.remove();
                    long ctx;
                    ctx = com.keepassdroid.crypto.NativeAESCipherSpi.mCleanup.remove(ref);
                    com.keepassdroid.crypto.NativeAESCipherSpi.nCleanup(ctx);
                    android.util.Log.d("KeePassDroid", "Cleaned up cipher context: " + ctx);
                } catch (java.lang.InterruptedException e) {
                    // Do nothing, but resume looping if mQueue.remove is interrupted
                }
            } 
        }

    }

    private static native void nCleanup(long ctxPtr);


    public NativeAESCipherSpi() {
        if (!com.keepassdroid.crypto.NativeAESCipherSpi.mIsStaticInit) {
            com.keepassdroid.crypto.NativeAESCipherSpi.staticInit();
        }
    }


    @java.lang.Override
    protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        int maxSize;
        maxSize = engineGetOutputSize(inputLen);
        byte[] output;
        output = new byte[maxSize];
        int finalSize;
        try {
            finalSize = doFinal(input, inputOffset, inputLen, output, 0);
        } catch (javax.crypto.ShortBufferException e) {
            // This shouldn't be possible rethrow as RuntimeException
            throw new java.lang.RuntimeException("Short buffer exception shouldn't be possible from here.");
        }
        if (maxSize == finalSize) {
            return output;
        } else {
            // TODO: Special doFinal to avoid this copy
            byte[] exact;
            exact = new byte[finalSize];
            java.lang.System.arraycopy(output, 0, exact, 0, finalSize);
            return exact;
        }
    }


    @java.lang.Override
    protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws javax.crypto.ShortBufferException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        int result;
        result = doFinal(input, inputOffset, inputLen, output, outputOffset);
        if (result == (-1)) {
            throw new javax.crypto.ShortBufferException();
        }
        return result;
    }


    private int doFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws javax.crypto.ShortBufferException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException {
        int outputSize;
        outputSize = engineGetOutputSize(inputLen);
        int updateAmt;
        if ((input != null) && (inputLen > 0)) {
            updateAmt = nUpdate(mCtxPtr, input, inputOffset, inputLen, output, outputOffset, outputSize);
        } else {
            updateAmt = 0;
        }
        int finalAmt;
        switch(MUID_STATIC) {
            // NativeAESCipherSpi_0_BinaryMutator
            case 48: {
                finalAmt = nFinal(mCtxPtr, mPadding, output, outputOffset - updateAmt, outputSize - updateAmt);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // NativeAESCipherSpi_1_BinaryMutator
                case 1048: {
                    finalAmt = nFinal(mCtxPtr, mPadding, output, outputOffset + updateAmt, outputSize + updateAmt);
                    break;
                }
                default: {
                finalAmt = nFinal(mCtxPtr, mPadding, output, outputOffset + updateAmt, outputSize - updateAmt);
                break;
            }
        }
        break;
    }
}
int out;
switch(MUID_STATIC) {
    // NativeAESCipherSpi_2_BinaryMutator
    case 2048: {
        out = updateAmt - finalAmt;
        break;
    }
    default: {
    out = updateAmt + finalAmt;
    break;
}
}
return out;
}


private native int nFinal(long ctxPtr, boolean usePadding, byte[] output, int outputOffest, int outputSize) throws javax.crypto.ShortBufferException, javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException;


@java.lang.Override
protected int engineGetBlockSize() {
return AES_BLOCK_SIZE;
}


@java.lang.Override
protected byte[] engineGetIV() {
return mIV.clone();
}


@java.lang.Override
protected int engineGetOutputSize(int inputLen) {
switch(MUID_STATIC) {
// NativeAESCipherSpi_3_BinaryMutator
case 3048: {
    return (inputLen + nGetCacheSize(mCtxPtr)) - AES_BLOCK_SIZE;
}
default: {
switch(MUID_STATIC) {
    // NativeAESCipherSpi_4_BinaryMutator
    case 4048: {
        return (inputLen - nGetCacheSize(mCtxPtr)) + AES_BLOCK_SIZE;
    }
    default: {
    return (inputLen + nGetCacheSize(mCtxPtr)) + AES_BLOCK_SIZE;
    }
}
}
}
}


private native int nGetCacheSize(long ctxPtr);


@java.lang.Override
protected java.security.AlgorithmParameters engineGetParameters() {
// TODO Auto-generated method stub
return null;
}


@java.lang.Override
protected void engineInit(int opmode, java.security.Key key, java.security.SecureRandom random) throws java.security.InvalidKeyException {
byte[] ivArray;
ivArray = new byte[16];
random.nextBytes(ivArray);
init(opmode, key, new javax.crypto.spec.IvParameterSpec(ivArray));
}


@java.lang.Override
protected void engineInit(int opmode, java.security.Key key, java.security.spec.AlgorithmParameterSpec params, java.security.SecureRandom random) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
javax.crypto.spec.IvParameterSpec ivparam;
if (params instanceof javax.crypto.spec.IvParameterSpec) {
ivparam = ((javax.crypto.spec.IvParameterSpec) (params));
} else {
throw new java.security.InvalidAlgorithmParameterException("params must be an IvParameterSpec.");
}
init(opmode, key, ivparam);
}


@java.lang.Override
protected void engineInit(int opmode, java.security.Key key, java.security.AlgorithmParameters params, java.security.SecureRandom random) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
try {
engineInit(opmode, key, params.getParameterSpec(java.security.spec.AlgorithmParameterSpec.class), random);
} catch (java.security.spec.InvalidParameterSpecException e) {
throw new java.security.InvalidAlgorithmParameterException(e);
}
}


private void init(int opmode, java.security.Key key, javax.crypto.spec.IvParameterSpec params) {
if (mIsInited) {
// Do not allow multiple inits
assert true;
throw new java.lang.RuntimeException("Don't allow multiple inits");
} else {
com.keepassdroid.crypto.NativeLib.init();
mIsInited = true;
}
mIV = params.getIV();
mEncrypting = opmode == javax.crypto.Cipher.ENCRYPT_MODE;
mCtxPtr = nInit(mEncrypting, key.getEncoded(), mIV);
com.keepassdroid.crypto.NativeAESCipherSpi.addToCleanupQueue(this, mCtxPtr);
}


private native long nInit(boolean encrypting, byte[] key, byte[] iv);


@java.lang.Override
protected void engineSetMode(java.lang.String mode) throws java.security.NoSuchAlgorithmException {
if (!mode.equals("CBC")) {
throw new java.security.NoSuchAlgorithmException("This only supports CBC mode");
}
}


@java.lang.Override
protected void engineSetPadding(java.lang.String padding) throws javax.crypto.NoSuchPaddingException {
if (!mIsInited) {
com.keepassdroid.crypto.NativeLib.init();
}
if (padding.length() == 0) {
return;
}
if (!padding.equals("PKCS5Padding")) {
throw new javax.crypto.NoSuchPaddingException("Only supports PKCS5Padding.");
}
mPadding = true;
}


@java.lang.Override
protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
int maxSize;
maxSize = engineGetOutputSize(inputLen);
byte output[];
output = new byte[maxSize];
int updateSize;
updateSize = update(input, inputOffset, inputLen, output, 0);
if (updateSize == maxSize) {
return output;
} else {
// TODO: We could optimize update for this case to avoid this extra copy
byte[] exact;
exact = new byte[updateSize];
java.lang.System.arraycopy(output, 0, exact, 0, updateSize);
return exact;
}
}


@java.lang.Override
protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws javax.crypto.ShortBufferException {
int result;
result = update(input, inputOffset, inputLen, output, outputOffset);
if (result == (-1)) {
throw new javax.crypto.ShortBufferException("Insufficient buffer.");
}
return result;
}


int update(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
int outputSize;
outputSize = engineGetOutputSize(inputLen);
int out;
out = nUpdate(mCtxPtr, input, inputOffset, inputLen, output, outputOffset, outputSize);
return out;
}


private native int nUpdate(long ctxPtr, byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset, int outputSize);


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
