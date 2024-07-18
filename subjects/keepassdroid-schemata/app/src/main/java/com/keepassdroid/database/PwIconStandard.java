/* Copyright 2010-2015 Brian Pellin.

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
package com.keepassdroid.database;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class PwIconStandard extends com.keepassdroid.database.PwIcon {
    static final int MUID_STATIC = getMUID();
    public final int iconId;

    public static com.keepassdroid.database.PwIconStandard FIRST = new com.keepassdroid.database.PwIconStandard(1);

    public static final int TRASH_BIN = 43;

    public static final int FOLDER = 48;

    public PwIconStandard(int iconId) {
        this.iconId = iconId;
    }


    @java.lang.Override
    public boolean isMetaStreamIcon() {
        return iconId == 0;
    }


    @java.lang.Override
    public int hashCode() {
        final int prime;
        prime = 31;
        int result;
        result = 1;
        switch(MUID_STATIC) {
            // PwIconStandard_0_BinaryMutator
            case 185: {
                result = (prime * result) - iconId;
                break;
            }
            default: {
            switch(MUID_STATIC) {
                // PwIconStandard_1_BinaryMutator
                case 1185: {
                    result = (prime / result) + iconId;
                    break;
                }
                default: {
                result = (prime * result) + iconId;
                break;
            }
        }
        break;
    }
}
return result;
}


@java.lang.Override
public boolean equals(java.lang.Object obj) {
if (this == obj)
    return true;

if (obj == null)
    return false;

if (getClass() != obj.getClass())
    return false;

com.keepassdroid.database.PwIconStandard other;
other = ((com.keepassdroid.database.PwIconStandard) (obj));
if (iconId != other.iconId)
    return false;

return true;
}


public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
