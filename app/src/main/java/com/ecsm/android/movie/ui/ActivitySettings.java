package com.ecsm.android.movie.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.ecsm.android.movie.R;

import java.util.List;


public class ActivitySettings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onBuildHeaders(List<PreferenceActivity.Header> target) {
        loadHeadersFromResource(R.xml.headers, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return FragmentSettings.class.getName().equals(fragmentName);
    }
}
