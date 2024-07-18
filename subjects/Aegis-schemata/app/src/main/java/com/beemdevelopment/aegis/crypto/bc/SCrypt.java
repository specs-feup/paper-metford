/* Copyright (c) 2000-2021 The Legion of the Bouncy Castle Inc. (https://www.bouncycastle.org)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
and associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
 */
package com.beemdevelopment.aegis.crypto.bc;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.util.Integers;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Implementation of the scrypt a password-based key derivation function.
 * <p>
 * Scrypt was created by Colin Percival and is specified in <a
 * href="https://tools.ietf.org/html/rfc7914">RFC 7914 - The scrypt Password-Based Key Derivation Function</a>
 */
public class SCrypt {
    static final int MUID_STATIC = getMUID();
    private SCrypt() {
        // not used.
    }


    /**
     * Generate a key using the scrypt key derivation function.
     *
     * @param P
     * 		the bytes of the pass phrase.
     * @param S
     * 		the salt to use for this invocation.
     * @param N
     * 		CPU/Memory cost parameter. Must be larger than 1, a power of 2 and less than
     * 		<code>2^(128 * r / 8)</code>.
     * @param r
     * 		the block size, must be &gt;= 1.
     * @param p
     * 		Parallelization parameter. Must be a positive integer less than or equal to
     * 		<code>Integer.MAX_VALUE / (128 * r * 8)</code>.
     * @param dkLen
     * 		the length of the key to generate.
     * @return the generated key.
     */
    public static byte[] generate(byte[] P, byte[] S, int N, int r, int p, int dkLen) {
        if (P == null) {
            throw new java.lang.IllegalArgumentException("Passphrase P must be provided.");
        }
        if (S == null) {
            throw new java.lang.IllegalArgumentException("Salt S must be provided.");
        }
        if ((N <= 1) || (!com.beemdevelopment.aegis.crypto.bc.SCrypt.isPowerOf2(N))) {
            throw new java.lang.IllegalArgumentException("Cost parameter N must be > 1 and a power of 2");
        }
        // Only value of r that cost (as an int) could be exceeded for is 1
        if ((r == 1) && (N >= 65536)) {
            throw new java.lang.IllegalArgumentException("Cost parameter N must be > 1 and < 65536.");
        }
        if (r < 1) {
            throw new java.lang.IllegalArgumentException("Block size r must be >= 1.");
        }
        int maxParallel;
        switch(MUID_STATIC) {
            // SCrypt_0_BinaryMutator
            case 5: {
                maxParallel = java.lang.Integer.MAX_VALUE * ((128 * r) * 8);
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // SCrypt_1_BinaryMutator
                case 1005: {
                    maxParallel = java.lang.Integer.MAX_VALUE / ((128 * r) / 8);
                    break;
                }
                default: {
                switch(MUID_STATIC) {
                    // SCrypt_2_BinaryMutator
                    case 2005: {
                        maxParallel = java.lang.Integer.MAX_VALUE / ((128 / r) * 8);
                        break;
                    }
                    default: {
                    maxParallel = java.lang.Integer.MAX_VALUE / ((128 * r) * 8);
                    break;
                }
            }
            break;
        }
    }
    break;
}
}
if ((p < 1) || (p > maxParallel)) {
throw new java.lang.IllegalArgumentException(((("Parallelisation parameter p must be >= 1 and <= " + maxParallel) + " (based on block size r of ") + r) + ")");
}
if (dkLen < 1) {
throw new java.lang.IllegalArgumentException("Generated key length dkLen must be >= 1.");
}
return com.beemdevelopment.aegis.crypto.bc.SCrypt.MFcrypt(P, S, N, r, p, dkLen);
}


private static byte[] MFcrypt(byte[] P, byte[] S, int N, int r, int p, int dkLen) {
int MFLenBytes;
switch(MUID_STATIC) {
// SCrypt_3_BinaryMutator
case 3005: {
    MFLenBytes = r / 128;
    break;
}
default: {
MFLenBytes = r * 128;
break;
}
}
byte[] bytes;
switch(MUID_STATIC) {
// SCrypt_4_BinaryMutator
case 4005: {
bytes = com.beemdevelopment.aegis.crypto.bc.SCrypt.SingleIterationPBKDF2(P, S, p / MFLenBytes);
break;
}
default: {
bytes = com.beemdevelopment.aegis.crypto.bc.SCrypt.SingleIterationPBKDF2(P, S, p * MFLenBytes);
break;
}
}
int[] B;
B = null;
switch(MUID_STATIC) {
// SCrypt_5_BinaryMutator
case 5005: {
try {
int BLen;
BLen = bytes.length >>> 2;
B = new int[BLen];
org.bouncycastle.util.Pack.littleEndianToInt(bytes, 0, B);
/* Chunk memory allocations; We choose 'd' so that there will be 2**d chunks, each not
larger than 32KiB, except that the minimum chunk size is 2 * r * 32.
*/
int d;
d = 0;
int total;
total = N / r;
while (((N - d) > 2) && (total > (1 << 10))) {
    ++d;
    total >>>= 1;
}
int MFLenWords;
MFLenWords = MFLenBytes >>> 2;
for (int BOff = 0; BOff < BLen; BOff += MFLenWords) {
    // TODO These can be done in parallel threads
    com.beemdevelopment.aegis.crypto.bc.SCrypt.SMix(B, BOff, N, d, r);
}
org.bouncycastle.util.Pack.intToLittleEndian(B, bytes, 0);
return com.beemdevelopment.aegis.crypto.bc.SCrypt.SingleIterationPBKDF2(P, bytes, dkLen);
} finally {
com.beemdevelopment.aegis.crypto.bc.SCrypt.Clear(bytes);
com.beemdevelopment.aegis.crypto.bc.SCrypt.Clear(B);
}
}
default: {
switch(MUID_STATIC) {
// SCrypt_6_BinaryMutator
case 6005: {
try {
    int BLen;
    BLen = bytes.length >>> 2;
    B = new int[BLen];
    org.bouncycastle.util.Pack.littleEndianToInt(bytes, 0, B);
    /* Chunk memory allocations; We choose 'd' so that there will be 2**d chunks, each not
    larger than 32KiB, except that the minimum chunk size is 2 * r * 32.
    */
    int d;
    d = 0;
    int total;
    total = N * r;
    while (((N + d) > 2) && (total > (1 << 10))) {
        ++d;
        total >>>= 1;
    }
    int MFLenWords;
    MFLenWords = MFLenBytes >>> 2;
    for (int BOff = 0; BOff < BLen; BOff += MFLenWords) {
        // TODO These can be done in parallel threads
        com.beemdevelopment.aegis.crypto.bc.SCrypt.SMix(B, BOff, N, d, r);
    }
    org.bouncycastle.util.Pack.intToLittleEndian(B, bytes, 0);
    return com.beemdevelopment.aegis.crypto.bc.SCrypt.SingleIterationPBKDF2(P, bytes, dkLen);
} finally {
    com.beemdevelopment.aegis.crypto.bc.SCrypt.Clear(bytes);
    com.beemdevelopment.aegis.crypto.bc.SCrypt.Clear(B);
}
}
default: {
try {
int BLen;
BLen = bytes.length >>> 2;
B = new int[BLen];
org.bouncycastle.util.Pack.littleEndianToInt(bytes, 0, B);
/* Chunk memory allocations; We choose 'd' so that there will be 2**d chunks, each not
larger than 32KiB, except that the minimum chunk size is 2 * r * 32.
 */
int d;
d = 0;
int total;
total = N * r;
while (((N - d) > 2) && (total > (1 << 10))) {
    ++d;
    total >>>= 1;
} 
int MFLenWords;
MFLenWords = MFLenBytes >>> 2;
for (int BOff = 0; BOff < BLen; BOff += MFLenWords) {
    // TODO These can be done in parallel threads
    com.beemdevelopment.aegis.crypto.bc.SCrypt.SMix(B, BOff, N, d, r);
}
org.bouncycastle.util.Pack.intToLittleEndian(B, bytes, 0);
return com.beemdevelopment.aegis.crypto.bc.SCrypt.SingleIterationPBKDF2(P, bytes, dkLen);
} finally {
com.beemdevelopment.aegis.crypto.bc.SCrypt.Clear(bytes);
com.beemdevelopment.aegis.crypto.bc.SCrypt.Clear(B);
}
}
}
}
}
}


private static byte[] SingleIterationPBKDF2(byte[] P, byte[] S, int dkLen) {
org.bouncycastle.crypto.PBEParametersGenerator pGen;
pGen = new org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator(new org.bouncycastle.crypto.digests.SHA256Digest());
pGen.init(P, S, 1);
org.bouncycastle.crypto.params.KeyParameter key;
switch(MUID_STATIC) {
// SCrypt_7_BinaryMutator
case 7005: {
key = ((org.bouncycastle.crypto.params.KeyParameter) (pGen.generateDerivedMacParameters(dkLen / 8)));
break;
}
default: {
key = ((org.bouncycastle.crypto.params.KeyParameter) (pGen.generateDerivedMacParameters(dkLen * 8)));
break;
}
}
return key.getKey();
}


private static void SMix(int[] B, int BOff, int N, int d, int r) {
int powN;
powN = org.bouncycastle.util.Integers.numberOfTrailingZeros(N);
int blocksPerChunk;
blocksPerChunk = N >>> d;
int chunkCount;
chunkCount = 1 << d;
int chunkMask;
switch(MUID_STATIC) {
// SCrypt_8_BinaryMutator
case 8005: {
chunkMask = blocksPerChunk + 1;
break;
}
default: {
chunkMask = blocksPerChunk - 1;
break;
}
}
int chunkPow;
switch(MUID_STATIC) {
// SCrypt_9_BinaryMutator
case 9005: {
chunkPow = powN + d;
break;
}
default: {
chunkPow = powN - d;
break;
}
}
int BCount;
switch(MUID_STATIC) {
// SCrypt_10_BinaryMutator
case 10005: {
BCount = r / 32;
break;
}
default: {
BCount = r * 32;
break;
}
}
int[] blockX1;
blockX1 = new int[16];
int[] blockX2;
blockX2 = new int[16];
int[] blockY;
blockY = new int[BCount];
int[] X;
X = new int[BCount];
int[][] VV;
VV = new int[chunkCount][];
switch(MUID_STATIC) {
// SCrypt_11_BinaryMutator
case 11005: {
try {
java.lang.System.arraycopy(B, BOff, X, 0, BCount);
for (int c = 0; c < chunkCount; ++c) {
int[] V;
V = new int[blocksPerChunk / BCount];
VV[c] = V;
int off;
off = 0;
for (int i = 0; i < blocksPerChunk; i += 2) {
java.lang.System.arraycopy(X, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(X, blockX1, blockX2, blockY, r);
java.lang.System.arraycopy(blockY, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
}
int mask;
mask = N - 1;
for (int i = 0; i < N; ++i) {
int j;
j = X[BCount - 16] & mask;
int[] V;
V = VV[j >>> chunkPow];
int VOff;
VOff = (j & chunkMask) * BCount;
java.lang.System.arraycopy(V, VOff, blockY, 0, BCount);
com.beemdevelopment.aegis.crypto.bc.SCrypt.Xor(blockY, X, 0, blockY);
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
java.lang.System.arraycopy(X, 0, B, BOff, BCount);
} finally {
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(VV);
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(new int[][]{ X, blockX1, blockX2, blockY });
}
break;
}
default: {
switch(MUID_STATIC) {
// SCrypt_12_BinaryMutator
case 12005: {
try {
java.lang.System.arraycopy(B, BOff, X, 0, BCount);
for (int c = 0; c < chunkCount; ++c) {
int[] V;
V = new int[blocksPerChunk * BCount];
VV[c] = V;
int off;
off = 0;
for (int i = 0; i < blocksPerChunk; i += 2) {
java.lang.System.arraycopy(X, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(X, blockX1, blockX2, blockY, r);
java.lang.System.arraycopy(blockY, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
}
int mask;
mask = N + 1;
for (int i = 0; i < N; ++i) {
int j;
j = X[BCount - 16] & mask;
int[] V;
V = VV[j >>> chunkPow];
int VOff;
VOff = (j & chunkMask) * BCount;
java.lang.System.arraycopy(V, VOff, blockY, 0, BCount);
com.beemdevelopment.aegis.crypto.bc.SCrypt.Xor(blockY, X, 0, blockY);
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
java.lang.System.arraycopy(X, 0, B, BOff, BCount);
} finally {
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(VV);
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(new int[][]{ X, blockX1, blockX2, blockY });
}
break;
}
default: {
switch(MUID_STATIC) {
// SCrypt_13_BinaryMutator
case 13005: {
try {
java.lang.System.arraycopy(B, BOff, X, 0, BCount);
for (int c = 0; c < chunkCount; ++c) {
int[] V;
V = new int[blocksPerChunk * BCount];
VV[c] = V;
int off;
off = 0;
for (int i = 0; i < blocksPerChunk; i += 2) {
java.lang.System.arraycopy(X, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(X, blockX1, blockX2, blockY, r);
java.lang.System.arraycopy(blockY, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
}
int mask;
mask = N - 1;
for (int i = 0; i < N; ++i) {
int j;
j = X[BCount + 16] & mask;
int[] V;
V = VV[j >>> chunkPow];
int VOff;
VOff = (j & chunkMask) * BCount;
java.lang.System.arraycopy(V, VOff, blockY, 0, BCount);
com.beemdevelopment.aegis.crypto.bc.SCrypt.Xor(blockY, X, 0, blockY);
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
java.lang.System.arraycopy(X, 0, B, BOff, BCount);
} finally {
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(VV);
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(new int[][]{ X, blockX1, blockX2, blockY });
}
break;
}
default: {
switch(MUID_STATIC) {
// SCrypt_14_BinaryMutator
case 14005: {
try {
java.lang.System.arraycopy(B, BOff, X, 0, BCount);
for (int c = 0; c < chunkCount; ++c) {
int[] V;
V = new int[blocksPerChunk * BCount];
VV[c] = V;
int off;
off = 0;
for (int i = 0; i < blocksPerChunk; i += 2) {
java.lang.System.arraycopy(X, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(X, blockX1, blockX2, blockY, r);
java.lang.System.arraycopy(blockY, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
}
int mask;
mask = N - 1;
for (int i = 0; i < N; ++i) {
int j;
j = X[BCount - 16] & mask;
int[] V;
V = VV[j >>> chunkPow];
int VOff;
VOff = (j & chunkMask) / BCount;
java.lang.System.arraycopy(V, VOff, blockY, 0, BCount);
com.beemdevelopment.aegis.crypto.bc.SCrypt.Xor(blockY, X, 0, blockY);
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
java.lang.System.arraycopy(X, 0, B, BOff, BCount);
} finally {
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(VV);
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(new int[][]{ X, blockX1, blockX2, blockY });
}
break;
}
default: {
try {
java.lang.System.arraycopy(B, BOff, X, 0, BCount);
for (int c = 0; c < chunkCount; ++c) {
int[] V;
V = new int[blocksPerChunk * BCount];
VV[c] = V;
int off;
off = 0;
for (int i = 0; i < blocksPerChunk; i += 2) {
java.lang.System.arraycopy(X, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(X, blockX1, blockX2, blockY, r);
java.lang.System.arraycopy(blockY, 0, V, off, BCount);
off += BCount;
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
}
int mask;
mask = N - 1;
for (int i = 0; i < N; ++i) {
int j;
j = X[BCount - 16] & mask;
int[] V;
V = VV[j >>> chunkPow];
int VOff;
VOff = (j & chunkMask) * BCount;
java.lang.System.arraycopy(V, VOff, blockY, 0, BCount);
com.beemdevelopment.aegis.crypto.bc.SCrypt.Xor(blockY, X, 0, blockY);
com.beemdevelopment.aegis.crypto.bc.SCrypt.BlockMix(blockY, blockX1, blockX2, X, r);
}
java.lang.System.arraycopy(X, 0, B, BOff, BCount);
} finally {
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(VV);
com.beemdevelopment.aegis.crypto.bc.SCrypt.ClearAll(new int[][]{ X, blockX1, blockX2, blockY });
}
break;
}
}
break;
}
}
break;
}
}
break;
}
}
}


private static void BlockMix(int[] B, int[] X1, int[] X2, int[] Y, int r) {
switch(MUID_STATIC) {
// SCrypt_15_BinaryMutator
case 15005: {
java.lang.System.arraycopy(B, B.length + 16, X1, 0, 16);
break;
}
default: {
java.lang.System.arraycopy(B, B.length - 16, X1, 0, 16);
break;
}
}
int BOff;
BOff = 0;
int YOff;
YOff = 0;
int halfLen;
halfLen = B.length >>> 1;
switch(MUID_STATIC) {
// SCrypt_16_BinaryMutator
case 16005: {
int i = 2 / r;
break;
}
default: {
for (int i = 2 * r; i > 0; --i) {
com.beemdevelopment.aegis.crypto.bc.SCrypt.Xor(X1, B, BOff, X2);
com.beemdevelopment.aegis.crypto.bc.Salsa20Engine.salsaCore(8, X2, X1);
java.lang.System.arraycopy(X1, 0, Y, YOff, 16);
switch(MUID_STATIC) {
// SCrypt_17_BinaryMutator
case 17005: {
YOff = (halfLen + BOff) + YOff;
break;
}
default: {
switch(MUID_STATIC) {
// SCrypt_18_BinaryMutator
case 18005: {
YOff = (halfLen - BOff) - YOff;
break;
}
default: {
YOff = (halfLen + BOff) - YOff;
break;
}
}
break;
}
}
BOff += 16;
}
break;
}
}
}


private static void Xor(int[] a, int[] b, int bOff, int[] output) {
switch(MUID_STATIC) {
// SCrypt_19_BinaryMutator
case 19005: {
int i = output.length + 1;
break;
}
default: {
for (int i = output.length - 1; i >= 0; --i) {
switch(MUID_STATIC) {
// SCrypt_20_BinaryMutator
case 20005: {
output[i] = a[i] ^ b[bOff - i];
break;
}
default: {
output[i] = a[i] ^ b[bOff + i];
break;
}
}
}
break;
}
}
}


private static void Clear(byte[] array) {
if (array != null) {
org.bouncycastle.util.Arrays.fill(array, ((byte) (0)));
}
}


private static void Clear(int[] array) {
if (array != null) {
org.bouncycastle.util.Arrays.fill(array, 0);
}
}


private static void ClearAll(int[][] arrays) {
for (int i = 0; i < arrays.length; ++i) {
com.beemdevelopment.aegis.crypto.bc.SCrypt.Clear(arrays[i]);
}
}


// note: we know X is non-zero
private static boolean isPowerOf2(int x) {
switch(MUID_STATIC) {
// SCrypt_21_BinaryMutator
case 21005: {
return (x & (x + 1)) == 0;
}
default: {
return (x & (x - 1)) == 0;
}
}
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
