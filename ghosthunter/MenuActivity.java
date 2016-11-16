package com.example.splashscreen;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MenuActivity extends Activity {
	
	// MediaPlayer mp; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
//        mp= MediaPlayer.create(MenuActivity.this, R.raw.gb);
//        mp.setLooping(true);
//        mp.start();
        
        
        
        //ADDED THIS <--- JISU
        
        SharedPreferences prefs = PreferenceManager
        	    .getDefaultSharedPreferences(MenuActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
        
        
    }
    
    public void startGame(View view) {
    	Intent intent = new Intent(MenuActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
    }
    
    public void startOptions(View view) {
    	Intent intent = new Intent(MenuActivity.this, OptionsActivity.class);
    	startActivity(intent);
    }
    
    protected void onDestroy () { 
    super.onDestroy();	
    }
    
    @Override 
    public void onPause() {
    	super.onPause();
//    	mp.release();
    }
    

    
    
}