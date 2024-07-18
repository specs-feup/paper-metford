package de.danoeh.antennapod.fragment.preferences.about;
import androidx.viewpager2.widget.ViewPager2;
import de.danoeh.antennapod.R;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import android.view.View;
import de.danoeh.antennapod.activity.PreferenceActivity;
import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import android.util.Log;
/**
 * Displays the 'about->Contributors' pager screen.
 */
public class ContributorsPagerFragment extends androidx.fragment.app.Fragment {
    static final int MUID_STATIC = getMUID();
    private static final int POS_DEVELOPERS = 0;

    private static final int POS_TRANSLATORS = 1;

    private static final int POS_SPECIAL_THANKS = 2;

    private static final int TOTAL_COUNT = 3;

    @java.lang.Override
    public android.view.View onCreateView(@androidx.annotation.NonNull
    android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        android.view.View rootView;
        rootView = inflater.inflate(de.danoeh.antennapod.R.layout.pager_fragment, container, false);
        androidx.viewpager2.widget.ViewPager2 viewPager;
        switch(MUID_STATIC) {
            // ContributorsPagerFragment_0_InvalidViewFocusOperatorMutator
            case 91: {
                /**
                * Inserted by Kadabra
                */
                viewPager = rootView.findViewById(de.danoeh.antennapod.R.id.viewpager);
                viewPager.requestFocus();
                break;
            }
            // ContributorsPagerFragment_1_ViewComponentNotVisibleOperatorMutator
            case 1091: {
                /**
                * Inserted by Kadabra
                */
                viewPager = rootView.findViewById(de.danoeh.antennapod.R.id.viewpager);
                viewPager.setVisibility(android.view.View.INVISIBLE);
                break;
            }
            default: {
            viewPager = rootView.findViewById(de.danoeh.antennapod.R.id.viewpager);
            break;
        }
    }
    viewPager.setAdapter(new de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.StatisticsPagerAdapter(this));
    // Give the TabLayout the ViewPager
    com.google.android.material.tabs.TabLayout tabLayout;
    switch(MUID_STATIC) {
        // ContributorsPagerFragment_2_InvalidViewFocusOperatorMutator
        case 2091: {
            /**
            * Inserted by Kadabra
            */
            tabLayout = rootView.findViewById(de.danoeh.antennapod.R.id.sliding_tabs);
            tabLayout.requestFocus();
            break;
        }
        // ContributorsPagerFragment_3_ViewComponentNotVisibleOperatorMutator
        case 3091: {
            /**
            * Inserted by Kadabra
            */
            tabLayout = rootView.findViewById(de.danoeh.antennapod.R.id.sliding_tabs);
            tabLayout.setVisibility(android.view.View.INVISIBLE);
            break;
        }
        default: {
        tabLayout = rootView.findViewById(de.danoeh.antennapod.R.id.sliding_tabs);
        break;
    }
}
new com.google.android.material.tabs.TabLayoutMediator(tabLayout, viewPager, (com.google.android.material.tabs.TabLayout.Tab tab,int position) -> {
    switch (position) {
        case de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.POS_DEVELOPERS :
            tab.setText(de.danoeh.antennapod.R.string.developers);
            break;
        case de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.POS_TRANSLATORS :
            tab.setText(de.danoeh.antennapod.R.string.translators);
            break;
        case de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.POS_SPECIAL_THANKS :
            tab.setText(de.danoeh.antennapod.R.string.special_thanks);
            break;
        default :
            break;
    }
}).attach();
rootView.findViewById(de.danoeh.antennapod.R.id.toolbar).setVisibility(android.view.View.GONE);
return rootView;
}


@java.lang.Override
public void onStart() {
super.onStart();
((de.danoeh.antennapod.activity.PreferenceActivity) (getActivity())).getSupportActionBar().setTitle(de.danoeh.antennapod.R.string.contributors);
}


public static class StatisticsPagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
StatisticsPagerAdapter(@androidx.annotation.NonNull
androidx.fragment.app.Fragment fragment) {
    super(fragment);
}


@androidx.annotation.NonNull
@java.lang.Override
public androidx.fragment.app.Fragment createFragment(int position) {
    switch (position) {
        case de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.POS_TRANSLATORS :
            return new de.danoeh.antennapod.fragment.preferences.about.TranslatorsFragment();
        case de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.POS_SPECIAL_THANKS :
            return new de.danoeh.antennapod.fragment.preferences.about.SpecialThanksFragment();
        default :
        case de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.POS_DEVELOPERS :
            return new de.danoeh.antennapod.fragment.preferences.about.DevelopersFragment();
    }
}


@java.lang.Override
public int getItemCount() {
    return de.danoeh.antennapod.fragment.preferences.about.ContributorsPagerFragment.TOTAL_COUNT;
}

}

public static int getMUID(){   String propertyValue = "-1";   try {   java.lang.Process process = Runtime.getRuntime().exec("getprop debug.MUID");
    InputStream inputStream = process.getInputStream();   BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));   propertyValue = reader.readLine();  reader.close();  inputStream.close();  } catch (IOException e) {  Log.e("ERROR", String.valueOf(e));  }  return Integer.parseInt(propertyValue);  }
}
