package com.example.saroj.moviesapp.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.saroj.moviesapp.R;

public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_screen);
    }
}
