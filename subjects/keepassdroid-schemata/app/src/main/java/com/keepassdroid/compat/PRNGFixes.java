package com.keepassdroid.compat;
import java.io.DataOutputStream;
import java.util.Locale;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.io.DataInputStream;
import java.security.Security;
import java.io.FileInputStream;
import com.keepassdroid.utils.StrUtil;
import java.io.FileOutputStream;
import android.os.Process;
import android.os.Build;
import java.io.BufferedReader;
import java.io.File;
import java.security.SecureRandomSpi;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Fixes for the output of the default PRNG having low entropy.
 *
 * The fixes need to be applied via {@link #apply()} before any use of Java
 * Cryptography Architecture primitives. A good place to invoke them is in the
 * application's {@code onCreate}.
 */
public final class PRNGFixes {
    static final int MUID_STATIC = getMUID();
    private static final byte[] BUILD_FINGERPRINT_AND_DEVICE_SERIAL = com.keepassdroid.compat.PRNGFixes.getBuildFingerprintAndDeviceSerial();

    private static int sdkVersion = android.os.Build.VERSION.SDK_INT;

    /**
     * Hidden constructor to prevent instantiation.
     */
    private PRNGFixes() {
    }


    /**
     * Applies all fixes.
     *
     * @throws SecurityException
     * 		if a fix is needed but could not be applied.
     */
    public static void apply() {
        try {
            if (com.keepassdroid.compat.PRNGFixes.supportedOnThisDevice()) {
                com.keepassdroid.compat.PRNGFixes.applyOpenSSLFix();
                com.keepassdroid.compat.PRNGFixes.installLinuxPRNGSecureRandom();
            }
        } catch (java.lang.Exception e) {
            // Do nothing, do the best we can to implement the workaround
        }
    }


    private static boolean supportedOnThisDevice() {
        // Blacklist on samsung devices
        if (com.keepassdroid.utils.StrUtil.indexOfIgnoreCase(android.os.Build.MANUFACTURER, "samsung", java.util.Locale.ENGLISH) >= 0) {
            return false;
        }
        if (com.keepassdroid.compat.PRNGFixes.sdkVersion > android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        }
        if (com.keepassdroid.compat.PRNGFixes.onSELinuxEnforce()) {
            return false;
        }
        java.io.File urandom;
        urandom = new java.io.File("/dev/urandom");
        // Test permissions
        if (!(urandom.canRead() && urandom.canWrite())) {
            return false;
        }
        // Test actually writing to urandom
        try {
            java.io.FileOutputStream fos;
            fos = new java.io.FileOutputStream(urandom);
            fos.write(0);
        } catch (java.lang.Exception e) {
            return false;
        }
        return true;
    }


    private static boolean onSELinuxEnforce() {
        try {
            java.lang.ProcessBuilder builder;
            builder = new java.lang.ProcessBuilder("getenforce");
            builder.redirectErrorStream(true);
            java.lang.Process process;
            process = builder.start();
            java.io.BufferedReader reader;
            reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
            process.waitFor();
            java.lang.String output;
            output = reader.readLine();
            if (output == null) {
                return false;
            }
            return output.toLowerCase(java.util.Locale.US).startsWith("enforcing");
        } catch (java.lang.Exception e) {
            return false;
        }
    }


    /**
     * Applies the fix for OpenSSL PRNG having low entropy. Does nothing if the
     * fix is not needed.
     *
     * @throws SecurityException
     * 		if the fix is needed but could not be applied.
     */
    private static void applyOpenSSLFix() throws java.lang.SecurityException {
        if ((com.keepassdroid.compat.PRNGFixes.sdkVersion < android.os.Build.VERSION_CODES.JELLY_BEAN) || (com.keepassdroid.compat.PRNGFixes.sdkVersion > android.os.Build.VERSION_CODES.JELLY_BEAN_MR2)) {
            // No need to apply the fix
            return;
        }
        try {
            // Mix in the device- and invocation-specific seed.
            java.lang.Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto").getMethod("RAND_seed", byte[].class).invoke(null, com.keepassdroid.compat.PRNGFixes.generateSeed());
            // Mix output of Linux PRNG into OpenSSL's PRNG
            int bytesRead;
            bytesRead = ((java.lang.Integer) (java.lang.Class.forName("org.apache.harmony.xnet.provider.jsse.NativeCrypto").getMethod("RAND_load_file", java.lang.String.class, long.class).invoke(null, "/dev/urandom", 1024)));
            if (bytesRead != 1024) {
                throw new java.io.IOException("Unexpected number of bytes read from Linux PRNG: " + bytesRead);
            }
        } catch (java.lang.Exception e) {
            throw new java.lang.SecurityException("Failed to seed OpenSSL PRNG", e);
        }
    }


    /**
     * Installs a Linux PRNG-backed {@code SecureRandom} implementation as the
     * default. Does nothing if the implementation is already the default or if
     * there is not need to install the implementation.
     *
     * @throws SecurityException
     * 		if the fix is needed but could not be applied.
     */
    private static void installLinuxPRNGSecureRandom() throws java.lang.SecurityException {
        if (com.keepassdroid.compat.PRNGFixes.sdkVersion > android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // No need to apply the fix
            return;
        }
        // Install a Linux PRNG-based SecureRandom implementation as the
        // default, if not yet installed.
        java.security.Provider[] secureRandomProviders;
        secureRandomProviders = java.security.Security.getProviders("SecureRandom.SHA1PRNG");
        if (((secureRandomProviders == null) || (secureRandomProviders.length < 1)) || (!com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandomProvider.class.equals(secureRandomProviders[0].getClass()))) {
            java.security.Security.insertProviderAt(new com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandomProvider(), 1);
        }
        // Assert that new SecureRandom() and
        // SecureRandom.getInstance("SHA1PRNG") return a SecureRandom backed
        // by the Linux PRNG-based SecureRandom implementation.
        java.security.SecureRandom rng1;
        rng1 = new java.security.SecureRandom();
        if (!com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandomProvider.class.equals(rng1.getProvider().getClass())) {
            throw new java.lang.SecurityException("new SecureRandom() backed by wrong Provider: " + rng1.getProvider().getClass());
        }
        java.security.SecureRandom rng2;
        try {
            rng2 = java.security.SecureRandom.getInstance("SHA1PRNG");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.SecurityException("SHA1PRNG not available", e);
        }
        if (!com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandomProvider.class.equals(rng2.getProvider().getClass())) {
            throw new java.lang.SecurityException(("SecureRandom.getInstance(\"SHA1PRNG\") backed by wrong" + " Provider: ") + rng2.getProvider().getClass());
        }
    }


    /**
     * {@code Provider} of {@code SecureRandom} engines which pass through
     * all requests to the Linux PRNG.
     */
    private static class LinuxPRNGSecureRandomProvider extends java.security.Provider {
        public LinuxPRNGSecureRandomProvider() {
            super("LinuxPRNG", 1.0, "A Linux-specific random number provider that uses" + " /dev/urandom");
            // Although /dev/urandom is not a SHA-1 PRNG, some apps
            // explicitly request a SHA1PRNG SecureRandom and we thus need to
            // prevent them from getting the default implementation whose output
            // may have low entropy.
            put("SecureRandom.SHA1PRNG", com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.class.getName());
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }

    }

    /**
     * {@link SecureRandomSpi} which passes all requests to the Linux PRNG
     * ({@code /dev/urandom}).
     */
    public static class LinuxPRNGSecureRandom extends java.security.SecureRandomSpi {
        /* IMPLEMENTATION NOTE: Requests to generate bytes and to mix in a seed
        are passed through to the Linux PRNG (/dev/urandom). Instances of
        this class seed themselves by mixing in the current time, PID, UID,
        build fingerprint, and hardware serial number (where available) into
        Linux PRNG.

        Concurrency: Read requests to the underlying Linux PRNG are
        serialized (on sLock) to ensure that multiple threads do not get
        duplicated PRNG output.
         */
        private static final java.io.File URANDOM_FILE = new java.io.File("/dev/urandom");

        private static final java.lang.Object sLock = new java.lang.Object();

        /**
         * Input stream for reading from Linux PRNG or {@code null} if not yet
         * opened.
         *
         * @GuardedBy("sLock")  */
        private static java.io.DataInputStream sUrandomIn;

        /**
         * Output stream for writing to Linux PRNG or {@code null} if not yet
         * opened.
         *
         * @GuardedBy("sLock")  */
        private static java.io.OutputStream sUrandomOut;

        /**
         * Whether this engine instance has been seeded. This is needed because
         * each instance needs to seed itself if the client does not explicitly
         * seed it.
         */
        private boolean mSeeded;

        @java.lang.Override
        protected void engineSetSeed(byte[] bytes) {
            try {
                java.io.OutputStream out;
                synchronized(com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sLock) {
                    out = getUrandomOutputStream();
                }
                out.write(bytes);
                out.flush();
                mSeeded = true;
            } catch (java.io.IOException e) {
                throw new java.lang.SecurityException("Failed to mix seed into " + com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.URANDOM_FILE, e);
            }
        }


        @java.lang.Override
        protected void engineNextBytes(byte[] bytes) {
            if (!mSeeded) {
                // Mix in the device- and invocation-specific seed.
                engineSetSeed(com.keepassdroid.compat.PRNGFixes.generateSeed());
            }
            try {
                java.io.DataInputStream in;
                synchronized(com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sLock) {
                    in = getUrandomInputStream();
                }
                synchronized(in) {
                    in.readFully(bytes);
                }
            } catch (java.io.IOException e) {
                throw new java.lang.SecurityException("Failed to read from " + com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.URANDOM_FILE, e);
            }
        }


        @java.lang.Override
        protected byte[] engineGenerateSeed(int size) {
            byte[] seed;
            seed = new byte[size];
            engineNextBytes(seed);
            return seed;
        }


        private java.io.DataInputStream getUrandomInputStream() {
            synchronized(com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sLock) {
                if (com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sUrandomIn == null) {
                    // NOTE: Consider inserting a BufferedInputStream between
                    // DataInputStream and FileInputStream if you need higher
                    // PRNG output performance and can live with future PRNG
                    // output being pulled into this process prematurely.
                    try {
                        com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sUrandomIn = new java.io.DataInputStream(new java.io.FileInputStream(com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.URANDOM_FILE));
                    } catch (java.io.IOException e) {
                        throw new java.lang.SecurityException(("Failed to open " + com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.URANDOM_FILE) + " for reading", e);
                    }
                }
                return com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sUrandomIn;
            }
        }


        private java.io.OutputStream getUrandomOutputStream() {
            synchronized(com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sLock) {
                if (com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sUrandomOut == null) {
                    try {
                        com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sUrandomOut = new java.io.FileOutputStream(com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.URANDOM_FILE);
                    } catch (java.io.IOException e) {
                        throw new java.lang.SecurityException(("Failed to open " + com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.URANDOM_FILE) + " for writing", e);
                    }
                }
                return com.keepassdroid.compat.PRNGFixes.LinuxPRNGSecureRandom.sUrandomOut;
            }
        }

    }

    /**
     * Generates a device- and invocation-specific seed to be mixed into the
     * Linux PRNG.
     */
    private static byte[] generateSeed() {
        try {
            java.io.ByteArrayOutputStream seedBuffer;
            seedBuffer = new java.io.ByteArrayOutputStream();
            java.io.DataOutputStream seedBufferOut;
            seedBufferOut = new java.io.DataOutputStream(seedBuffer);
            seedBufferOut.writeLong(java.lang.System.currentTimeMillis());
            seedBufferOut.writeLong(java.lang.System.nanoTime());
            seedBufferOut.writeInt(android.os.Process.myPid());
            seedBufferOut.writeInt(android.os.Process.myUid());
            seedBufferOut.write(com.keepassdroid.compat.PRNGFixes.BUILD_FINGERPRINT_AND_DEVICE_SERIAL);
            seedBufferOut.close();
            return seedBuffer.toByteArray();
        } catch (java.io.IOException e) {
            throw new java.lang.SecurityException("Failed to generate seed", e);
        }
    }


    /**
     * Gets the hardware serial number of this device.
     *
     * @return serial number or {@code null} if not available.
     */
    private static java.lang.String getDeviceSerialNumber() {
        // We're using the Reflection API because Build.SERIAL is only available
        // since API Level 9 (Gingerbread, Android 2.3).
        try {
            return ((java.lang.String) (android.os.Build.class.getField("SERIAL").get(null)));
        } catch (java.lang.Exception ignored) {
            return null;
        }
    }


    private static byte[] getBuildFingerprintAndDeviceSerial() {
        java.lang.StringBuilder result;
        result = new java.lang.StringBuilder();
        java.lang.String fingerprint;
        fingerprint = android.os.Build.FINGERPRINT;
        if (fingerprint != null) {
            result.append(fingerprint);
        }
        java.lang.String serial;
        serial = com.keepassdroid.compat.PRNGFixes.getDeviceSerialNumber();
        if (serial != null) {
            result.append(serial);
        }
        try {
            return result.toString().getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.RuntimeException("UTF-8 encoding not supported");
        }
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
