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
import static it.feio.android.omninotes.utils.ConstantsBase.FACEBOOK_COMMUNITY;
import it.feio.android.omninotes.R;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
public class IntroSlide6 extends it.feio.android.omninotes.intro.IntroFragment {
    static final int MUID_STATIC = getMUID();
    @java.lang.Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.introBackground.setBackgroundColor(android.graphics.Color.parseColor("#222222"));
        binding.introTitle.setText(it.feio.android.omninotes.R.string.tour_listactivity_final_title);
        binding.introImage.setVisibility(android.view.View.GONE);
        binding.introImageSmall.setImageResource(it.feio.android.omninotes.R.drawable.facebook);
        binding.introImageSmall.setVisibility(android.view.View.VISIBLE);
        binding.introImageSmall.setOnClickListener((android.view.View v) -> {
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse(it.feio.android.omninotes.utils.ConstantsBase.FACEBOOK_COMMUNITY));
            startActivity(intent);
        });
        binding.introDescription.setText(it.feio.android.omninotes.R.string.tour_community);
    }


    public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
            InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
        }
