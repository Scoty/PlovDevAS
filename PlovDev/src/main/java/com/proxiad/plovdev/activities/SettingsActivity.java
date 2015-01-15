package com.proxiad.plovdev.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.proxiad.plovdev.R;

@SuppressWarnings("deprecation")
// the absurd is that the PreferenceFragment is NOT in the support lib...
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	// used without getter and setter
	public static final String KEY_PREF_LIST_LANGUAGE = "pref_list_language";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getActionBar().setTitle(R.string.title_activity_settings);
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Toast toast = Toast.makeText(this, getString(R.string.toast_text), Toast.LENGTH_SHORT);
		TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
		if (textView != null) {
			textView.setGravity(Gravity.CENTER);
		}
		toast.show();
	}
}