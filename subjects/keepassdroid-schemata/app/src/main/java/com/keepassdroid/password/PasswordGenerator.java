/* Copyright 2010 Tolga Onbay, Brian Pellin.

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
package com.keepassdroid.password;
import com.android.keepass.R;
import java.security.SecureRandom;
import android.content.Context;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PasswordGenerator {
    static final int MUID_STATIC = getMUID();
    private static final java.lang.String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final java.lang.String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";

    private static final java.lang.String DIGIT_CHARS = "0123456789";

    private static final java.lang.String MINUS_CHAR = "-";

    private static final java.lang.String UNDERLINE_CHAR = "_";

    private static final java.lang.String SPACE_CHAR = " ";

    private static final java.lang.String SPECIAL_CHARS = "!\"#$%&\'*+,./:;=?@\\^`";

    private static final java.lang.String BRACKET_CHARS = "[]{}()<>";

    private android.content.Context cxt;

    public PasswordGenerator(android.content.Context cxt) {
        this.cxt = cxt;
    }


    public java.lang.String generatePassword(int length, boolean upperCase, boolean lowerCase, boolean digits, boolean minus, boolean underline, boolean space, boolean specials, boolean brackets) throws java.lang.IllegalArgumentException {
        // Desired password length is 0 or less
        if (length <= 0) {
            throw new java.lang.IllegalArgumentException(cxt.getString(com.android.keepass.R.string.error_wrong_length));
        }
        // No option has been checked
        if ((((((((!upperCase) && (!lowerCase)) && (!digits)) && (!minus)) && (!underline)) && (!space)) && (!specials)) && (!brackets)) {
            throw new java.lang.IllegalArgumentException(cxt.getString(com.android.keepass.R.string.error_pass_gen_type));
        }
        java.lang.String characterSet;
        characterSet = getCharacterSet(upperCase, lowerCase, digits, minus, underline, space, specials, brackets);
        int size;
        size = characterSet.length();
        java.lang.StringBuffer buffer;
        buffer = new java.lang.StringBuffer();
        java.security.SecureRandom random// use more secure variant of Random!
        ;// use more secure variant of Random!

        random = new java.security.SecureRandom();
        if (size > 0) {
            for (int i = 0; i < length; i++) {
                char c;
                c = characterSet.charAt(((char) (random.nextInt(size))));
                buffer.append(c);
            }
        }
        return buffer.toString();
    }


    public java.lang.String getCharacterSet(boolean upperCase, boolean lowerCase, boolean digits, boolean minus, boolean underline, boolean space, boolean specials, boolean brackets) {
        java.lang.StringBuffer charSet;
        charSet = new java.lang.StringBuffer();
        if (upperCase) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.UPPERCASE_CHARS);
        }
        if (lowerCase) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.LOWERCASE_CHARS);
        }
        if (digits) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.DIGIT_CHARS);
        }
        if (minus) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.MINUS_CHAR);
        }
        if (underline) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.UNDERLINE_CHAR);
        }
        if (space) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.SPACE_CHAR);
        }
        if (specials) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.SPECIAL_CHARS);
        }
        if (brackets) {
            charSet.append(com.keepassdroid.password.PasswordGenerator.BRACKET_CHARS);
        }
        return charSet.toString();
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
