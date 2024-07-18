/* Copyright (C) 2013-2023 Federico Iosue (federico@iosue.it)

This program is free software: you can redistribute it and/or modify
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
package it.feio.android.omninotes.intro;
import android.graphics.Color;
import it.feio.android.omninotes.R;
import android.os.Bundle;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IntroSlide3 extends it.feio.android.omninotes.intro.IntroFragment {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.introBackground.setBackgroundColor(android.graphics.Color.parseColor("#8bc34a"));
        binding.introTitle.setText(it.feio.android.omninotes.R.string.categories);
        binding.introImage.setImageResource(it.feio.android.omninotes.R.drawable.slide3);
        binding.introDescription.setText(it.feio.android.omninotes.R.string.tour_listactivity_tag_detail);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
