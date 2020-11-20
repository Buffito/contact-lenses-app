package com.unipi.p15173.contactlenses;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setLanguage();
        setTitle(R.string.title_activity_settings);
    }

    public void setLanguage() {
        try {
            if (sharedPreferences.getString("lang", "English").equals("Ελληνικά")) {
                lang = "el";
            } else {
                lang = "en";
            }
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        } catch (NullPointerException e) {
            Log.d("exception caught", e.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setLanguage();
    }

    public static class SettingsFragment extends androidx.preference.PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref_general, rootKey);
        }
    }

}