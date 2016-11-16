package com.example.splashscreen;

import android.preference.PreferenceActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class OptionsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {  
		
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.user_settings);
		
	}
	
	

	
}
